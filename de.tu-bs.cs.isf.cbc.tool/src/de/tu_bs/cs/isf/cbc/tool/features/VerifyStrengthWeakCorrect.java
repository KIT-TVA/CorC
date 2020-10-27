package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyStrengthWeakCorrect extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyStrengthWeakCorrect(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify strengthening and weakening are correct.";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the precondition and post implies post.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof StrengthWeakStatement) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify strengtening and weakening", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof StrengthWeakStatement) {
				StrengthWeakStatement statement = (StrengthWeakStatement) bo;
				if (statement.getParent() != null) {
					AbstractStatement parent = statement.getParent();
					JavaVariables vars = null;
					GlobalConditions conds = null;
					Renaming renaming = null;
					for (Shape shape : getDiagram().getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							vars = (JavaVariables) obj;
						} else if (obj instanceof GlobalConditions) {
							conds = (GlobalConditions) obj;
						} else if (obj instanceof Renaming) {
							renaming = (Renaming) obj;
						}
					}
					boolean prove1 = false;
					boolean prove2 = false;
					prove1 = ProveWithKey.provePreImplPreWithKey(parent.getPreCondition(), statement.getPreCondition(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
					prove2 = ProveWithKey.provePostImplPostWithKey(parent.getPostCondition(), statement.getPostCondition(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
					
					if (prove1 && prove2) {
						statement.setProven(true);
					} else {
						statement.setProven(false);
					}
					updatePictogramElement(((Shape)pes[0]).getContainer());
				}
			}
		}
		monitor.done();
	}
}