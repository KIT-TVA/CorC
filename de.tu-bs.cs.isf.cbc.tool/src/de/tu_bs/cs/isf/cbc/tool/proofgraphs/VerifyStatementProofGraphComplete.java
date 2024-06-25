package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.xtext.util.Files;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyStatementProofGraphComplete extends MyAbstractAsynchronousCustomFeature {

	public VerifyStatementProofGraphComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (completion)";
	}

	@Override
	public String getDescription() {
		return "Proofs this statement with the partial proof of the proof nodes";
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
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);

				try {
					ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
					Console.println(String.format("Successfully loaded proof graph for %s:%s", callingFeature, callingMethod), Colors.GREEN);
					
					ProofGraph graph = collection.getGraphForMethod(callingMethod);
					//Load proof from repository
					this.loadProofFileFromRepo(graph.getIdForFeature(callingFeature));
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
					List<List<String>> featureForceProof = new ArrayList<>();
					if (varMethodCalls != null && !varMethodCalls.isEmpty()) {
						for (String method : varMethodCalls) {
							featureForceProof.addAll(generateAllPaths(collection.getGraphForMethod(method)));
						}
					}
					
					VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
					//TODO: Maybe smarter way? reuse featureconfigs?
					Set<List<String>> toProve = new HashSet<>();
					for (ProofNode node : edgesToProve) {
						
						//more than one edge after prove
						if (graph.getAdjacencyList().get(node).size() > 1) {
							//create new partial proof and safe
						}
						
						List<String> features = List.of(callingFeature, node.getFeature());
						String[] featureConfig = VerifyFeatures.findValidProduct(features, project);
						toProve.add(new ArrayList<>(Arrays.asList(featureConfig)));
					}
					
					for (List<String> path : featureForceProof) {
						
						String[] featureConfig = VerifyFeatures.findValidProduct(path, project);
						toProve.add(new ArrayList<>(Arrays.asList(featureConfig)));
					}
					
					Console.println("Minimal amount of produts for valid proof: ");
					toProve.forEach(l -> {
						Console.println("\t -" + Arrays.toString(l.toArray()));
					});
					
					Set<String[]> toProveConverted = convertArrays(toProve);
					
					boolean proven = true;
					for (String[] featureConfig : toProveConverted) {
						GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
						genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						genCode.generate(project.getLocation(), 
								callingFeature, 
								callingClass, 
								callingMethod, 
								featureConfig);

						IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
						String[][] variantWrapper = {featureConfig};
						String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass)[0];
						List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, callingMethod);
						List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, callingMethod);
						ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						if(!prove.proveStatementWithKey(null, refinements, refinementVars, false, false, callingMethod, "", callingClass, true)) {
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
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
	}
	
	private void loadProofFileFromRepo(UUID uuid) {
		URI uri = this.getDiagram().eResource().getURI();
		IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true)) + "/Statement1.key";
		
		IProofRepository proofRepository = new FileSystemProofRepository();
		String proofFile = proofRepository.getPartialProofForId(uuid);
		Files.writeStringIntoFile(location, proofFile);
	}

	private List<List<String>> generateAllPaths(ProofGraph graph) {
		List<List<String>> paths = new ArrayList<List<String>>();
		
		Set<ProofNode> pathStarts = graph.getAdjacencyList().keySet();
		
		graph.getAdjacencyList().forEach((__, entry) -> {
			pathStarts.removeAll(entry);
		});
		
		for (ProofNode node : pathStarts) {
			dfs(graph, node, paths, new ArrayList<String>());
		}
		
		return paths;
	}
	
	private void dfs(ProofGraph graph, ProofNode node, List<List<String>> paths, List<String> localPathList) {
		localPathList.add(node.getFeature());
		if (graph.getAdjacencyList().get(node).isEmpty()) {
			paths.add(localPathList);
			return;
		}

		for (ProofNode cn : graph.getAdjacencyList().get(node)) {
			dfs(graph, cn, paths, localPathList);
		}
	}
	
	private Set<String[]> convertArrays(Set<List<String>> old) {
		Set<String[]> newSet = new HashSet<String[]>();
		
		for(List<String> oldList : old) {
			String[] array = new String[oldList.size()];
			for (int i = 0; i < oldList.size(); i++)
				array[i] = oldList.get(i);
			newSet.add(array);
		}
		
		return newSet;
	}
	
}