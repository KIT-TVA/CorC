package de.tu_bs.cs.isf.cbc.tool.proofs.strategies;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.proofrepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proofrepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;
import de.tu_bs.cs.isf.cbc.tool.proofs.IKeYProof;
import de.tu_bs.cs.isf.cbc.tool.proofs.KeYProofStrategy;
import de.tu_bs.cs.isf.cbc.tool.proofs.ProofGraphStrategy;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class ProofGraphOriginalStatementBeginStrategy extends KeYProofStrategy implements ProofGraphStrategy{

	private static HashMap<List<String>, List<String>> pathSizeStorage = new HashMap<List<String>, List<String>>();
	
	
	@Override
	public void generateCode(IKeYProof proof, List<String> featureConfig) {
		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(proof.getFeatureProvider());
		genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		
		

		genCode.generateWithRestriction(project.getLocation(), 
				proof.getCallingFeature(), proof.getCallingClass(), proof.getCallingMethod(), 
				featureConfig, pathSizeStorage.get(featureConfig).get(pathSizeStorage.get(featureConfig).size() - 1));
	}
	
	@Override
	public Set<List<String>> generateFeatureConfigurations(IKeYProof proof) {
		Set<List<String>> toProve = new HashSet<List<String>>();
		pathSizeStorage.clear();
		try {
		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
		Console.println(String.format("Successfully loaded proof graph for %s:%s", proof.getCallingFeature(), proof.getCallingMethod()), Colors.GREEN);
		ProofGraph graph = collection.getGraphForMethod(proof.getCallingMethod());

		ProofNode node = new ProofNode(proof.getCallingFeature(), proof.getCallingMethod(), graph.getIdForFeature(proof.getCallingFeature()));
		List<List<String>> pathsToFork = new ArrayList<>();
		List<String> localPathList = new ArrayList<>();
		localPathList.add(proof.getCallingFeature());

		findForks(graph, node, pathsToFork, localPathList);
		
		if (pathsToFork.isEmpty()) {
			pathsToFork.add(List.of(proof.getCallingFeature()));
		}
		Console.println(Arrays.toString(pathsToFork.toArray()));

		for (List<String> path : pathsToFork) {
			Console.println("Creating " + path.get(path.size() - 1) + "-resolved proof");
			String[] featureConfig = VerifyFeatures.findValidProduct(path, project);
			List<String> featureConfigList = new ArrayList<String>(Arrays.asList(featureConfig));
			pathSizeStorage.put(featureConfigList, path);
			toProve.add(featureConfigList);
			
			Console.println(Arrays.toString(featureConfig));
		}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return toProve;

	}

	@Override
	public boolean prove(IKeYProof proof, List<String> featureConfig) {
		try {

		URI uri = proof.getDiagram().eResource().getURI();
		IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
		ProofGraph graph = collection.getGraphForMethod(proof.getCallingMethod());
		Console.println(pathSizeStorage.get(featureConfig).size());

		ProveWithKey prove = new ProveWithKey(
				proof.getStatement(), 
				proof.getDiagram(), 
				proof.getProgressMonitor(), 
				fileHandler, 
				featureConfig, 
				0, 
				pathSizeStorage.get(featureConfig).size() == 1 ? KeYInteraction.ABSTRACT_PROOF_BEGIN : KeYInteraction.ABSTRACT_T_RESOLVED_PROOF
				);

		boolean proven =  prove.proveStatementWithKey(null, 
				proof.getCbcFormulas(),
				proof.getJavaVariables(),
				false, 
				false, 
				proof.getCallingMethod(),
				"",
				proof.getCallingClass(),
				true
			);
		
		String location = fileHandler.getLocationString(proof.getDiagram().eResource().getURI().toPlatformString(true));
		IProofRepository proofRepo = new FileSystemProofRepository(location + "/proofRepository/");
		FileNameManager manager = new FileNameManager();
		String name = manager.getFileName(null, location, proof.getStatement(), "") + ".proof";
		List<String> path = pathSizeStorage.get(featureConfig);
		List<UUID> uuids = path.stream().map(f -> graph.getIdForFeature(f)).toList();
		
		String folderName = "";
		
		if (path.size() != 1) {
			folderName += "/";
			for (String feat : featureConfig) {
				folderName += feat;
			}
			folderName += "/";
		}
		
		proofRepo.savePartialProofForId(path, uuids, location + folderName + name);

		return proven;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public String getProofType() {
		return KeYInteraction.ABSTRACT_PROOF_BEGIN;
	}
}
