package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.resources.IProject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.DefaultReconnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.cbc.tool.helper.GetProjectUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateInformationFlow;
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

/**
 * Class thats does reconnections
 * @author Tobias
 *
 */
public class ReconnectionFeature extends DefaultReconnectionFeature {

	/**
	 * Costructor of the class
	 * @param fp	The FeatureProvider
	 */
	public ReconnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canReconnect(IReconnectionContext context) {
		Connection connection = context.getConnection();
		Anchor sourceAnchor = connection.getStart();
		Anchor targetAnchor = connection.getEnd();
		if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_SOURCE)) {
			sourceAnchor = context.getNewAnchor();
		} else {
			targetAnchor = context.getNewAnchor();
		}

		if (sourceAnchor != null && targetAnchor != null) {
			Object sourceDomainObject = getBusinessObjectForPictogramElement(sourceAnchor.getParent());
			Object targetDomainObject = getBusinessObjectForPictogramElement(targetAnchor.getParent());
			if (sourceDomainObject instanceof CbCFormula) {
				sourceDomainObject = ((CbCFormula) sourceDomainObject).getStatement();
			}
			if (targetDomainObject instanceof CbCFormula) {
				return false;
			}
			if (sourceDomainObject instanceof AbstractStatement && targetDomainObject instanceof AbstractStatement) {
				AbstractStatement targetStatement = (AbstractStatement) targetDomainObject;
				AbstractStatement sourceStatement = (AbstractStatement) sourceDomainObject;
				
				if (checkLoop(sourceStatement, targetStatement)) {
					return false;
				}
				if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_SOURCE)) {
					if (sourceStatement.getRefinement() != null) {
						return false;
					}
					if (sourceStatement.getClass().equals(AbstractStatementImpl.class)) {
						if (sourceStatement.eContainer() != null && !sourceStatement.eContainer().getClass().equals(AbstractStatementImpl.class)) {
							return true;
						}
					} else if (sourceStatement instanceof StrengthWeakStatement && !(sourceAnchor.getParent() instanceof ContainerShape)) {
						return true;
					}
				} else {
					if (targetStatement.getParent() != null) {
						return false;
					}
					if (targetStatement.eContainer() == null && targetAnchor.getParent() instanceof ContainerShape) {
						return true;
					}
				}
				if (sourceStatement.equals(targetStatement)) {
					return false;
				}
			}
		}
		return false;
	}
	
	@Override
	public void postReconnect(IReconnectionContext context) {
		Connection connection = context.getConnection();
		Anchor sourceAnchor = connection.getStart();
		Anchor targetAnchor = connection.getEnd();
		if (context.getReconnectType().equals(ReconnectionContext.RECONNECT_SOURCE)) {
			sourceAnchor = context.getNewAnchor();
		} else {
			targetAnchor = context.getNewAnchor();
		}

		AbstractStatement sourceObject;
		if (getBusinessObjectForPictogramElement(sourceAnchor.getParent()) instanceof CbCFormula) {
			sourceObject = ((CbCFormula) getBusinessObjectForPictogramElement(sourceAnchor.getParent())).getStatement();
		} else {
			sourceObject = (AbstractStatement) getBusinessObjectForPictogramElement(sourceAnchor.getParent());
		}
		AbstractStatement targetObject = (AbstractStatement) getBusinessObjectForPictogramElement(targetAnchor.getParent());
		
		sourceObject.setRefinement(targetObject);
		UpdateConditionsOfChildren.updateRefinedStatement(sourceObject, targetObject);
		
		//Start of IFbC
		final IProject project = GetProjectUtil.getProjectForDiagram(getDiagram());
		final Lattice lattice = Lattices.getLatticeForProject(project);
		if (lattice != null) {
			try {
				UpdateInformationFlow.updateInformationFlow(project.getName(), sourceObject, lattice);
			} catch (IFbCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * checks if the connection creates a loop in the taxonomy
	 * @param sourceStatement	the source statement of the connection
	 * @param targetStatement	the target statement of the connection
	 * @return	boolean if the connection creates a loop
	 */
	private boolean checkLoop(AbstractStatement sourceStatement, AbstractStatement targetStatement) {
		boolean isParent = false;
		if (targetStatement.getRefinement() != null) {
			if (targetStatement.getRefinement().equals(sourceStatement)) {
				return true;
			}
		}
		if (sourceStatement.getParent() != null) {
			isParent = checkLoop((AbstractStatement) sourceStatement.getParent(), targetStatement);
			if (isParent) {
				return true;
			}
		}
		return false;
	}
}
