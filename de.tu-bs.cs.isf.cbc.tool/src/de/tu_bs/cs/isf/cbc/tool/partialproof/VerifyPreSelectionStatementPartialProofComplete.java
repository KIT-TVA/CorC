package de.tu_bs.cs.isf.cbc.tool.partialproof;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPreSelectionStatement;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;

public class VerifyPreSelectionStatementPartialProofComplete extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPreSelectionStatementPartialProofComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the precondition of selection statement as partial proof (continue)";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the alle guards of the selection statement as partial proof (continue).";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo.getClass().equals(SelectionStatementImpl.class)) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		VerifyPreSelectionStatement feature = new VerifyPreSelectionStatement(super.getFeatureProvider());
		feature.setProofType(KeYInteraction.ABSTRACT_PROOF_COMPLETE);
		feature.execute(context, monitor);
	}
}