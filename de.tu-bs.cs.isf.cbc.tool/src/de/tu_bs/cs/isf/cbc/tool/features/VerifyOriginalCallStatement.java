package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.List;
import org.eclipse.core.resources.IProject;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public class VerifyOriginalCallStatement extends MyAbstractAsynchronousCustomFeature {

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
		long startTime = System.nanoTime();
		monitor.beginTask("Verify original-call statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				
				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				proven = executeVerification(project, uri, statement, vars, conds, renaming, formula, false, monitor);
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
		monitor.done();
	}

	private boolean executeVerification(IProject project, URI uri, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		DataCollector.checkForId(statement);
		boolean proven = false;
		VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());
		String callingFeature = uri.segment(uri.segmentCount() - 3) + "";
		String callingClass = uri.segment(uri.segmentCount() - 2) + "";
		String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.trimFileExtension().segmentCount() - 1), true, callingClass, false, null);
		String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, uri.trimFileExtension().segment(uri.segmentCount() - 1), true, callingClass, true, null);
		
		Console.println("--------------- Triggered variational verification ---------------");

		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());		
		if (featureConfigs != null) {
			String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					genCode.generate(project.getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
					String configName = "";
					for (String s : featureConfigs[i]) configName += s;
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri.toPlatformString(true), formula, new FileUtil(uri.toPlatformString(true)), configName);
					List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i], callingMethod);
					List<JavaVariables> refinementsVars = verifyStmt.generateJavaVariablesForRefinements(variants[i], callingMethod);
					proven = prove.proveStatementWithKey(null, refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, true);
				}
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}
}