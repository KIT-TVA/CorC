package de.tu_bs.cs.isf.cbc.tool.partialproof;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyVariantWithInnerLoops;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;

public class VerifyVariantWithInnerLoopsPartialProofComplete extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyVariantWithInnerLoopsPartialProofComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the variant (splitted) as partial proof (continue)";
	}

	@Override
	public String getDescription() {
		return "Verifies the variant of a repetition statement as partial proof (continue).";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(SmallRepetitionStatementImpl.class))) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		VerifyVariantWithInnerLoops feature = new VerifyVariantWithInnerLoops(super.getFeatureProvider());
		feature.setProofType(KeYInteraction.ABSTRACT_PROOF_COMPLETE);
		feature.execute(context, monitor);
	}
}