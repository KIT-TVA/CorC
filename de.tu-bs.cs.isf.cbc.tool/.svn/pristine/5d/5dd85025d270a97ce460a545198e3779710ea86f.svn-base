package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyVariant2 extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyVariant2(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the variant (splitted)";
	}

	@Override
	public String getDescription() {
		return "Verifies the variant of a repetition statement.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(RepetitionStatementImpl.class) || bo.getClass().equals(SmallRepetitionStatementImpl.class))) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify variant", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
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
				boolean prove = false;
				String code = ConstructCodeBlock.constructCodeBlockAndVerify2(statement);
				Condition invariant = null;
				Condition guard = null;
				Variant variant = null;
				if (statement instanceof RepetitionStatement) {
					RepetitionStatement repStatement = (RepetitionStatement) statement;
					invariant = repStatement.getInvariant();
					guard = repStatement.getGuard();
					variant = repStatement.getVariant();
				} else if (statement instanceof SmallRepetitionStatement) {
					SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
					invariant = repStatement.getInvariant();
					guard = repStatement.getGuard();
					variant = repStatement.getVariant();

				}
//				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(code)) {
					prove = ProveWithKey.proveVariant2WithKey(code, invariant, guard, variant, vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
//				} else {
//					System.out.println("Statement is not in correct format.");
//				}
				if (prove) {
					if (statement instanceof RepetitionStatement) {
						RepetitionStatement repStatement = (RepetitionStatement) statement;
						repStatement.setVariantProven(true);
					} else if (statement instanceof SmallRepetitionStatement) {
						SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
						repStatement.setVariantProven(true);
					}
				} else {
					if (statement instanceof RepetitionStatement) {
						RepetitionStatement repStatement = (RepetitionStatement) statement;
						repStatement.setVariantProven(false);
					} else if (statement instanceof SmallRepetitionStatement) {
						SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
						repStatement.setVariantProven(false);
					}
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		monitor.done();
	}
}