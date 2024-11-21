package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.cbc.tool.helper.GetProjectUtil;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateInformationFlow;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

/**
 * Class that updates information flow (type system checking and promotion)
 * 
 * @author Patrick
 *
 */
public class UpdateInformationFlowFeature extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public UpdateInformationFlowFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Update Information Flow";
	}

	@Override
	public String getDescription() {
		return "Updates the information flow of this statement.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class))) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Update Information Flow", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				final IProject project = GetProjectUtil.getProjectForDiagram(getDiagram());
				final Lattice lattice = Lattices.getLatticeForProject(project);
				if (lattice == null) {
					System.out.println("ERROR: no lattice found for project " + project.getName());
					return;
				}
				
				try {
					UpdateInformationFlow.updateInformationFlow(project.getName(), statement, lattice);
				} catch (IFbCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		monitor.done();
	}
}