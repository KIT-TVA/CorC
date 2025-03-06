package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPostRepetitionStatement extends MyAbstractAsynchronousCustomFeature {
	private String proofType = KeYInteraction.ABSTRACT_PROOF_FULL;

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}
	private static List<CbCFormula> refinements;
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
		long startTime = System.nanoTime();
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement statement = (SmallRepetitionStatement) bo;
				AbstractStatement parent = statement.getParent();
				StatDataCollector.checkForId(statement);
				boolean proven = false;
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
					String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri,
							uri.trimFileExtension().segment(uri.segmentCount() - 1), true, callingClass, true, null);

					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(
							super.getFeatureProvider());
					VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());

					if (featureConfigs != null) {
						String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant,
								callingFeature, callingClass);
						for (int i = 0; i < variants.length; i++) {
							genCode.setProofTypeInfo(i, proofType);
							if (!genCode.generate(
									FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI())
											.getLocation(),
									callingFeature, callingClass, callingMethod, featureConfigs[i]))
								continue;
							prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString),
									featureConfigs[i], i, KeYInteraction.ABSTRACT_PROOF_FULL);
							List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i],
									callingMethod);
							String configName = "";
							for (String s : featureConfigs[i])
								configName += s;
							prove.setConfigName(configName);
							proven = prove.provePostRepetitionWithKey(refinements, statement.getInvariant(),
									statement.getGuard(), parent.getPostCondition());
						}
					}
				} else {
					Console.println("Starting verification...\n");
					proven = prove.provePostRepetitionWithKey(null, statement.getInvariant(), statement.getGuard(),
							parent.getPostCondition());
				}
				if (proven) {
					statement.setPostProven(true);
				} else {
					statement.setPostProven(false);
				}
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		// reset proof type since partial proofs also call this method.
		proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("\nVerification done.");
		Console.println("Time needed: " + duration + "ms");
		monitor.done();
	}
}
