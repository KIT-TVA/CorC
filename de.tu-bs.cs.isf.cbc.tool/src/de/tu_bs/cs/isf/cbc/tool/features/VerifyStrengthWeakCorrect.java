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
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.Console;
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
public class VerifyStrengthWeakCorrect extends MyAbstractAsynchronousCustomFeature {
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
	public VerifyStrengthWeakCorrect(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify strengthening and weakening are correct.";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the precondition and post implies post.";
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
		monitor.beginTask("Verify strengtening and weakening", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof StrengthWeakStatement) {
				StrengthWeakStatement statement = (StrengthWeakStatement) bo;
				if (statement.getParent() != null) {
					AbstractStatement parent = statement.getParent();
					StatDataCollector.checkForId(statement);
					boolean proven1 = false;
					boolean proven2 = false;
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
						String[][] featureConfigs = VerifyFeatures.verifyConfig(uri,
								uri.segment(uri.segmentCount() - 1), true, callingClass, false, null);
						GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(
								super.getFeatureProvider());
						for (int i = 0; i < featureConfigs.length; i++) {
							genCode.generate(
									FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI())
											.getLocation(),
									callingFeature, callingClass, callingMethod, featureConfigs[i]);
							String configName = "";

							String configChain = "";
							for (String s : featureConfigs[i]) {
								configName += s;
								configChain += s + ",";
							}
							refinements = new VerifyStatement(null).generateCbCFormulasForRefinements(
									configChain.substring(0, configChain.length() - 1), callingMethod);

							prove.setConfigName(configName);
							proven1 = prove.proveCImpliesCWithKey(refinements, parent.getPreCondition(),
									statement.getPreCondition());
							proven2 = prove.proveCImpliesCWithKey(refinements, statement.getPostCondition(),
									parent.getPostCondition());
						}
					} else {
						Console.println("Starting verification...\n");
						proven1 = prove.proveCImpliesCWithKey(refinements, parent.getPreCondition(),
								statement.getPreCondition());
						proven2 = prove.proveCImpliesCWithKey(refinements, statement.getPostCondition(),
								parent.getPostCondition());
					}
					Console.println("\nVerification done.");

					if (proven1 && proven2) {
						statement.setProven(true);
					} else {
						statement.setProven(false);
					}
					updatePictogramElement(((Shape) pes[0]).getContainer());
				}
			}
		}
		// reset proof type since partial proofs also call this method.
		proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
		monitor.done();
	}
}
