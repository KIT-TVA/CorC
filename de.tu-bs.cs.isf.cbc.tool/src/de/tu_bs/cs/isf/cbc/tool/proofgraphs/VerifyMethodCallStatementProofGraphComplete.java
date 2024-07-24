package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyMethodCallStatementProofGraphComplete extends MyAbstractAsynchronousCustomFeature{

	public VerifyMethodCallStatementProofGraphComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a method-call statement as proof graph (complete)";
	}

	@Override
	public String getDescription() {
		return "Verifies a method-call statement as partial proof (continue) using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof MethodStatement) {
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
		VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
		VerifyStatementProofGraphComplete verifyGraphStatement = new VerifyStatementProofGraphComplete(getFeatureProvider());

		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
				IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
				String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true));
				IProofRepository proofRepo = new FileSystemProofRepository(location + "/proofRepository/");

				try {
					ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
					Console.println(String.format("Successfully loaded proof graph for %s:%s", callingFeature, callingMethod), Colors.GREEN);
					
					ProofGraph graph = collection.getGraphForMethod(callingMethod);
					//Load proof from repository

					Set<ProofNode> edgesToProve = graph.getNodesForFeature(callingFeature, callingMethod);
					if (!edgesToProve.isEmpty()) {
						Console.println("Starting proof on proof graph:");
						for(ProofNode node : edgesToProve) {
							Console.println("\t" + callingFeature +  " --> " + node.getFeature());
						}
					} else {
						Console.println("No variation points - proof can just go on");
					}

					Set<String> varMethodCalls = graph.getVarMethodCalls().get(new ProofNode(callingFeature, callingMethod, graph.getIdForFeature(callingFeature)));
					List<List<String>> varMethodCallPaths = new ArrayList<>();
					if (varMethodCalls != null && !varMethodCalls.isEmpty()) {
						for (String method : varMethodCalls) {
							varMethodCallPaths.addAll(verifyGraphStatement.generateAllPaths(collection.getGraphForMethod(method)));
						}
					}
					
					Set<List<String>> toProve = new HashSet<>();

					List<List<String>> currentGraphPaths = new ArrayList<>();
					List<String> localPathList = new ArrayList<>();
					localPathList.add(callingFeature);
					verifyGraphStatement.findPaths(graph, new ProofNode(callingFeature, callingMethod, graph.getIdForFeature(callingFeature)), currentGraphPaths,localPathList);
					
					for (List<String> path : currentGraphPaths) {
						for (List<String> path2 : varMethodCallPaths) {
							Set<String> paths = new HashSet<>();
							paths.addAll(path);
							paths.addAll(path2);
							String[] featureConfig = VerifyFeatures.findValidProduct(new ArrayList<String>(paths), project);
							toProve.add(new ArrayList<>(Arrays.asList(featureConfig)));
						}
					}
					
					Console.println("Minimal amount of produts for valid proof: ");
					toProve.forEach(l -> {
						Console.println("\t -" + Arrays.toString(l.toArray()));
					});
					
					Set<String[]> toProveConverted = verifyGraphStatement.convertArrays(toProve);
					
					boolean proven = true;
					for (String[] featureConfig : toProveConverted) {

						FileNameManager manager = new FileNameManager();
						String name = manager.getFileName(null, location, statement, "") + ".proof";
						proofRepo.getPartialProofForId(List.of(callingFeature), List.of(graph.getIdForFeature(callingFeature)), location, name);

						GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
						genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						genCode.generate(project.getLocation(), 
								callingFeature, 
								callingClass, 
								callingMethod, 
								featureConfig);

						DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
						JavaVariables vars = extractor.getVars();
						String varM = handleVarM(Parser.extractMethodNameFromStatemtent(statement.getName()), callingClass, vars);
						String varMParts[] = varM.split("\\.");
						String[][] variantWrapper = {featureConfig};
						String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, varM.contains(".") ? varMParts[0] : callingClass)[0];
						List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, varMParts[1].toLowerCase());
						List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, varMParts[1].toLowerCase());
						ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						if(!prove.proveStatementWithKey(refinements, refinements, refinementVars, false, false, callingMethod, varM, callingClass, true)) {
							proven = false;
						}

					}
					statement.setProven(proven);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.add(duration + ""); //PG DEBUG
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
	}
	private String handleVarM(String methodCall, String callingClass, JavaVariables vars) {
		if (!methodCall.contains(".")) { // Call without class reference
			return callingClass + "." + methodCall;
		} else if (methodCall.contains("this.")) { // Call with this reference
			return methodCall.replace("this.", "." + callingClass);
		} else if (Character.isUpperCase(methodCall.charAt(0))) { // Call with static class reference
			return methodCall;
		} else { // Call with reference to a variable/object
			String type = methodCall.split("\\.")[0];
			for (JavaVariable var : vars.getVariables()) {
				if (var.getName().split(" ")[1].equals(type)) {
					return methodCall.replace(type, var.getName().split(" ")[0]);
				}
			}
			for (Field f : vars.getFields()) {
				if (f.getName().equals(type)) {
					return methodCall.replace(type, f.getType());
				}
			}
		}
		return methodCall;
	}
}
