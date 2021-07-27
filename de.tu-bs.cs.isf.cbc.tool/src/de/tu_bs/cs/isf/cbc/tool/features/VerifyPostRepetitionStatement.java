package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPostRepetitionStatement extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPostRepetitionStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the postcondition of repetition statement";
	}

	@Override
	public String getDescription() {
		return "Verifies that the invariant and !guard implies the postcondition of the parent statement.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo.getClass().equals(SmallRepetitionStatementImpl.class)) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement statement = (SmallRepetitionStatement) bo;
				AbstractStatement parent = statement.getParent();
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof MethodClass) {
						formula = (CbCFormula) obj;
					}
				}
				boolean proven = false;
				String uriString = getDiagram().eResource().getURI().toPlatformString(true);
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString));
				proven = prove.provePostRepetitionWithKey(statement.getInvariant(), statement.getGuard(), parent.getPostCondition());
				if (proven) {
					statement.setPostProven(true);
				} else {
					statement.setPostProven(false);
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		monitor.done();
	}
}