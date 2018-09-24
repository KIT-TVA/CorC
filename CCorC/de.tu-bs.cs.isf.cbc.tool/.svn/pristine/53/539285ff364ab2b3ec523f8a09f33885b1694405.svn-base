package de.tu_bs.cs.isf.cbc.tool.patterns;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;

/**
 * Class that creates the graphical representation of the parent hierarchy between Algorithms
 * @author Tobias
 *
 */
public class ConnectionPattern extends AbstractConnectionPattern {
	
	@Override
	public String getCreateName() {
		return "Refinement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Firsty, select the abstract, then the refined.";
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context instanceof IAddConnectionContext;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addConContext = (IAddConnectionContext) context;
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		Connection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		Polyline polyline = gaService.createPlainPolyline(connection);
		polyline.setForeground(manageColor(IColorConstant.BLACK));

		ConnectionDecorator cd;
		cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
		createArrow(cd);
		
		return connection;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		// Defines the start of the connection; allowed are objects that may
		// contain other objects
		Object domainObject = getBusinessObjectForPictogramElement(context.getSourcePictogramElement());
		if (domainObject != null && domainObject.getClass().equals(SkipStatementImpl.class)) {
			return false;
		}
		if (domainObject != null && (domainObject instanceof AbstractStatement || domainObject instanceof CbCFormula)) {
			Object parentContainerObject = getBusinessObjectForPictogramElement(((Shape)context.getSourcePictogramElement()).getContainer());
			if (parentContainerObject != null && (parentContainerObject.getClass().equals(AbstractStatementImpl.class)
					|| parentContainerObject.getClass().equals(MethodStatementImpl.class))) {
				return false;
			}
			if (!(context.getSourcePictogramElement() instanceof ContainerShape)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		PictogramElement sourcePictogramElement = context.getSourcePictogramElement();
		PictogramElement targetPictogramElement = context.getTargetPictogramElement();

		if (sourcePictogramElement == null || targetPictogramElement == null || targetPictogramElement.equals(getDiagram()) || !(targetPictogramElement instanceof ContainerShape)) {
			return false;
		}
		
		Object sourceDomainObject = getBusinessObjectForPictogramElement(sourcePictogramElement);
		Object targetDomainObject = getBusinessObjectForPictogramElement(targetPictogramElement);

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
			if (sourceStatement.getRefinement() != null) {
				return false;
			}
			if (targetStatement.getParent() != null) {
				return false;
			}
			if (sourceStatement.equals(targetStatement)) {
				return false;
			}
		}
		return true;
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

	@Override
	public Connection create(ICreateConnectionContext context) {
		Anchor sourceAnchor = context.getSourceAnchor();
		Anchor targetAnchor = context.getTargetAnchor();

		if (targetAnchor == null) {
			Shape shape = (Shape) context.getTargetPictogramElement();
			while (shape.getAnchors().isEmpty()) {
				shape = shape.getContainer();
			}
			targetAnchor = shape.getAnchors().get(0);
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
		
		AddConnectionContext addContext = new AddConnectionContext(sourceAnchor, targetAnchor);
		Connection connection = (Connection) getFeatureProvider().addIfPossible(addContext);

		return connection;
	}
	
	/**
	 * Helper method to create an arrow at the end of the connection
	 * @param gaContainer	the connection decorator
	 * @return	A polyline that is formed like an arrow
	 */
	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		Polyline polyline = Graphiti.getGaCreateService().createPlainPolyline(gaContainer,
				new int[] { -15, 10, 0, 0, -15, -10 });
		polyline.setForeground(manageColor(IColorConstant.BLACK));
		return polyline;
	}
}
