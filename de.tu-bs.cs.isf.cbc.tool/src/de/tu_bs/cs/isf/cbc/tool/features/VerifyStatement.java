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
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyStatement extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement";
	}

	@Override
	public String getDescription() {
		return "Verifies a statement using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement || bo instanceof ReturnStatement)) {
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
		monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
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
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				boolean prove = false;
//				try {
//					getDiagram().eResource().save(Collections.EMPTY_MAP);
//					statement.eResource().save(Collections.EMPTY_MAP);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
					prove = ProveWithKey.proveStatementWithKey2(formula.getClassName(), statement, vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
				} else {
					Console.println("Statement is not in correct format.");
				}
				if (prove) {
					statement.setProven(true);
				} else {
					statement.setProven(false);
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		monitor.done();
	}
}