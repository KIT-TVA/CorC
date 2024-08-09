package de.tu_bs.cs.isf.cbc.tool.features;

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
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPreSelectionStatement extends MyAbstractAsynchronousCustomFeature {
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
	public VerifyPreSelectionStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the precondition of selection statement";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the alle guards of the selection statement.";
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
		long startTime = System.nanoTime();
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SelectionStatement) {
				SelectionStatement statement = (SelectionStatement) bo;
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
				if (isVariational) {
					Console.println("Starting variational verification...\n");
					String callingClass = uri.segment(uri.segmentCount()-2) + "";
					String callingFeature = uri.segment(uri.segmentCount()-3) + "";
					String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
					String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false, null);				
					String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, uri.trimFileExtension().segment(uri.segmentCount() - 1), true, callingClass, true, null);

					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
					VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());
					
					if (featureConfigs != null) {
						String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
						for (int i = 0; i < variants.length; i++) {
							genCode.setProofTypeInfo(i, proofType);
							if(!genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i])) continue;
							ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString), featureConfigs[i], i, KeYInteraction.ABSTRACT_PROOF_FULL);
							List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i], callingMethod);
							String configName = "";
							for (String s : featureConfigs[i]) configName += s;
							prove.setConfigName(configName);
							proven = prove.provePreSelWithKey(refinements, statement.getGuards(), parent.getPreCondition());
						}
					}
				} else {
					ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString), null, 0, proofType);
					Console.println("Starting verification...\n");
					String callingClass = uri.segment(uri.segmentCount() - 2) + "";
					proven = prove.provePreSelWithKey(null, statement.getGuards(), parent.getPreCondition());
				}		
				if (proven) {
					statement.setPreProve(true);
				} else {
					statement.setPreProve(false);
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		// reset proof type since partial proofs also call this method.
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		if (proofType.equals(KeYInteraction.ABSTRACT_PROOF_BEGIN)) {
			RunEvaluationForStatementPP.WHOLE_RUNTIME_START.add(duration + ""); //PG DEBUG
		} else {
			RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.add(duration + ""); //PG DEBUG
		}
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
		monitor.done();	

		proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
	}
}
