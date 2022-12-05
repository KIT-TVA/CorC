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
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
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
		long startTime = System.nanoTime();
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement statement = (SmallRepetitionStatement) bo;
				AbstractStatement parent = statement.getParent();
				JavaVariables vars = null;
				GlobalConditions conds = null;
				CbCFormula formula = null;
				Renaming renaming = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if(obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				if (!DataCollector.checkForId(statement)) return;
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
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString), null, 0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
				if (isVariational) {
					Console.println("--------------- Triggered variational verification ---------------");
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
							if (i > 0) {
								genCode.printConfigToConsole(featureConfigs[i], true);
								continue;
							}
							genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
							prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString), featureConfigs[i], i, KeYInteraction.ABSTRACT_PROOF_BEGIN);
							List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i], callingMethod);
							String configName = "";
							for (String s : featureConfigs[i]) configName += s;
							prove.setConfigName(configName);
							proven = prove.proveCImpliesCWithKey(refinements, parent.getPreCondition(), statement.getInvariant(), callingClass);
							}
					}
				} else {
					Console.println("--------------- Triggered verification ---------------");
					String callingClass = uri.segment(uri.segmentCount() - 2) + "";
					proven = prove.proveCImpliesCWithKey(null, parent.getPreCondition(), statement.getInvariant(), callingClass);
				}		
				Console.println("--------------- Verification completed --------------- ");
								
				if (proven) {
					statement.setPreProven(true);
				} else {
					statement.setPreProven(false);
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
		monitor.done();
	}
}