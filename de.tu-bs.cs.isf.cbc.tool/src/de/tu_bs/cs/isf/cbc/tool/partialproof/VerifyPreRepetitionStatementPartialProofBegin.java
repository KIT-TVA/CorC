package de.tu_bs.cs.isf.cbc.tool.partialproof;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPreRepetitionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public class VerifyPreRepetitionStatementPartialProofBegin extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPreRepetitionStatementPartialProofBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the precondition of repetition statement as partial proof (begin)";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the invariant of the repetition statement as partial proof (begin).";
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
		VerifyPreRepetitionStatement feature = new VerifyPreRepetitionStatement(super.getFeatureProvider());
		feature.setProofType(KeYInteraction.ABSTRACT_PROOF_BEGIN);
		feature.execute(context, monitor);
	}
}