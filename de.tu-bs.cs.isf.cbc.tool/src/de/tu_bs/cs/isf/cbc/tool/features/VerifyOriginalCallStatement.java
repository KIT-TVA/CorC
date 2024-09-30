package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;

public class VerifyOriginalCallStatement extends MyAbstractAsynchronousCustomFeature {
	private String proofType = KeYInteraction.ABSTRACT_PROOF_FULL;

	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyOriginalCallStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify an original-call statement";
	}

	@Override
	public String getDescription() {
		return "Verifies an original-call statement using the pre and post condition.";
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
		Console.clear();
		long startTime = System.nanoTime();
		monitor.beginTask("Verify original-call statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				proven = executeVerification(project, statement, getDiagram(), false, monitor);
				statement.setProven(proven);
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

	private boolean executeVerification(IProject project, AbstractStatement statement, Diagram diagram,
			boolean returnStatement, IProgressMonitor monitor) {
		StatDataCollector.checkForId(statement);
		boolean proven = false;
		URI uri = diagram.eResource().getURI();
		VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());
		String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
		String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
		String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
		String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, callingMethod, true, callingClass, false, null);
		String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, callingMethod, true, callingClass, true,
				null);

		Console.println("Starting variational verification...\n");

		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(
				super.getFeatureProvider());
		if (featureConfigs != null) {
			String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant,
					callingFeature, callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					genCode.setProofTypeInfo(i, proofType);
					if (!genCode.generate(project.getLocation(), callingFeature, callingClass, callingMethod,
							featureConfigs[i]))
						continue;
					ProveWithKey prove = new ProveWithKey(statement, diagram, monitor,
							new FileUtil(uri.toPlatformString(true)), featureConfigs[i], i, proofType);
					List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i],
							callingMethod);
					List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[i],
							callingMethod);
					proven = prove.proveStatementWithKey(null, refinements, refinementsVars, returnStatement, false,
							callingMethod, "", callingClass, true);
				}
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}
}
