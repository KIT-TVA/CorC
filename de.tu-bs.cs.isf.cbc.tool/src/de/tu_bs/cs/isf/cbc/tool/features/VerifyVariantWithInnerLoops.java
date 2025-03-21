package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;

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
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyVariantWithInnerLoops extends MyAbstractAsynchronousCustomFeature {
	private String proofType = KeYInteraction.ABSTRACT_PROOF_FULL;

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyVariantWithInnerLoops(IFeatureProvider fp) {
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
			if (bo != null && (bo.getClass().equals(SmallRepetitionStatementImpl.class))) {
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
				DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
				JavaVariables vars = extractor.getVars();
				GlobalConditions conds = extractor.getConds();
				Renaming renaming = extractor.getRenaming();
				CbCFormula formula = extractor.getFormula();
				StatDataCollector.checkForId(statement);
				boolean proven = false;
				String code = ConstructCodeBlock.constructCodeBlockAndVerify(statement, true);
				Condition invariant = null;
				Condition guard = null;
				Variant variant = null;
				if (statement instanceof SmallRepetitionStatement) {
					SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
					invariant = repStatement.getInvariant();
					guard = repStatement.getGuard();
					variant = repStatement.getVariant();

				}
				String uriString = getDiagram().eResource().getURI().toPlatformString(true);
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				try {
					if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
				ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString),
						new ArrayList<>(), 0, proofType);
				if (isVariational) {
					Console.println("Starting variational verification...\n");
					String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
					String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
					String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
					String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount() - 1),
							true, callingClass, false, null);
					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(
							super.getFeatureProvider());
					for (int i = 0; i < featureConfigs.length; i++) {
						prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString),
								featureConfigs[i], i, proofType);
						genCode.generate(
								FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(),
								callingFeature, callingClass, callingMethod, featureConfigs[i]);
						String configName = "";
						for (String s : featureConfigs[i])
							configName += s;
						prove.setConfigName(configName);
						proven = prove.proveVariantWithKey(code, invariant, guard, variant);
					}
				} else {
					Console.println("Starting verification...\n");
					proven = prove.proveVariantWithKey(code, invariant, guard, variant);
				}
				Console.println("\nVerification done.");

				if (proven) {
					if (statement instanceof SmallRepetitionStatement) {
						SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
						repStatement.setVariantProven(true);
					}
				} else {
					if (statement instanceof SmallRepetitionStatement) {
						SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
						repStatement.setVariantProven(false);
					}
				}
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		// reset proof type since partial proofs also call this method.
		proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
		monitor.done();
	}
}
