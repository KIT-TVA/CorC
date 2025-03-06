package de.tu_bs.cs.isf.cbc.tool.partialproof;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStrengthWeakCorrect;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;

public class VerifyStrengthWeakCorrectPartialProofBegin extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyStrengthWeakCorrectPartialProofBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify strengthening and weakening are correct as partial proof (begin)";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the precondition and post implies post as partial proof (begin).";
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
		VerifyStrengthWeakCorrect feature = new VerifyStrengthWeakCorrect(super.getFeatureProvider());
		feature.setProofType(KeYInteraction.ABSTRACT_PROOF_BEGIN);
		feature.execute(context, monitor);
	}
}