package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.Predicate;
import de.tu_bs.cs.isf.cbc.util.PredicateDefinition;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser.SelectionInfo;

public class VerifyStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {
	
	private static final String PREDICATES_DEF = "/predicates.def";

	public VerifyStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (begin)";
	}

	@Override
	public String getDescription() {
		return "Creates the partial proof for all feature nodes in this method";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		//TODO: Only show when SPL (check for variational)
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement)) {
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
		PictogramElement[] pes = context.getPictogramElements();
		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {

				boolean proven = true;
				VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
				String preFormula = Parser.getConditionFromCondition(statement.getPreCondition().getName());
				String postFormula = Parser.getConditionFromCondition(statement.getPostCondition().getName());
				
				if (preFormula.contains("original") || postFormula.contains("original")) {
					VerifyOriginalCallStatementProofGraphBegin verify = new VerifyOriginalCallStatementProofGraphBegin(getFeatureProvider());
					verify.execute(context);
				} else {
					Console.println("No Variation within statement. Instantly starting verification!");
					URI uri = this.getDiagram().eResource().getURI();
					FileHandler.instance.deleteFolder(uri, "tests");
					IProject project = FileUtil.getProjectFromFileInProject(uri);
					String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
					String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
					String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
					IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
					
					String[] featureConfig = VerifyFeatures.findValidProduct(List.of(callingFeature), project);
					
					for (Predicate pred : readPredicates()) {
						for (PredicateDefinition def: pred.definitions) {
							if (!def.presenceCondition.equals("true")) {
								Console.println("Found presence condition for variable predicate:" + def.presenceCondition);
								IFeatureModel featureModel = FeatureModelManager.load(Paths.get(project.getLocation() + "/model.xml"));
								
								PresenceConditionParser parser = new PresenceConditionParser(def.presenceCondition, featureModel.getFeatureOrderList());
								List<Set<SelectionInfo>> conditions = parser.parseCondition();
								
								for (Set<SelectionInfo> allSelections : conditions) {
									String[] predicateConfig = VerifyFeatures.getSolutionForConditions(allSelections, project);
									Console.println("Caused by this predicate condition the following configurations need to be proven:");
									Console.println("\t-" + Arrays.toString(predicateConfig));
								}
							}
						}
					}
					
					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
						genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						genCode.generate(project.getLocation(), 
								callingFeature, 
								callingClass, 
								callingMethod, 
								featureConfig);

						String[][] variantWrapper = {featureConfig};
						String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass)[0];
						List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, callingMethod);
						List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, callingMethod);
						ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_FULL);
						if(!prove.proveStatementWithKey(null, refinements, refinementVars, bo instanceof ReturnStatement, false, callingMethod, "", callingClass, true)) {
							proven = false;
						}
					}
					statement.setProven(proven);
				}

				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1_000_000;
				startTime = System.nanoTime();
				RunEvaluationForStatementPP.WHOLE_RUNTIME_START.add(duration + ""); //PG DEBUG

				Console.println("\nVerification done."); 
				Console.println("Time needed: " + duration + "ms");
			}
		}
	
	private List<Predicate> readPredicates() {
		URI uri = this.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		FileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		String filePath = project.getRawLocation() + PREDICATES_DEF;

		List<Predicate> readPredicates = fileHandler.readPredicates(filePath);
		
		Console.println("Found predicates:");
		readPredicates.forEach(def -> Console.println(def.name));
		
		return readPredicates;
	}
}
	