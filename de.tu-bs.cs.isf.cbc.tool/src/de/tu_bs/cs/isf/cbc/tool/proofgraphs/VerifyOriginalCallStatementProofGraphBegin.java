package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;

public class VerifyOriginalCallStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyOriginalCallStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify an original-call statement as proof graph (begin)";
	}

	@Override
	public String getDescription() {
		return "Verifies an original-call statement as partial proof (continue) using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof OriginalStatement) {
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
		//TODO: Hier die t-resolved proofs generieren?
		VerifyStatementProofGraphBegin feature = new VerifyStatementProofGraphBegin(super.getFeatureProvider());
		feature.execute(context, monitor);
	}
}
