package de.tu_bs.cs.isf.cbc.tool.proofs.strategies;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.presenceconditionparser.PresenceConditionParser.SelectionInfo;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public final class ProofGraphOriginalStatementCompleteStrategy extends KeYProofStrategy implements ProofGraphStrategy{
	
	private final List<List<String>> forks = new ArrayList<>();
	private final List<List<String>> paths = new ArrayList<>();
	private ProofGraph graph; 
	
	@Override
	public Set<List<String>> generateFeatureConfigurations(IKeYProof proof) {
		List<Set<SelectionInfo>> predicateInfo = generatePredicateInfo(proof);
		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		Set<List<String>> toProve = new HashSet<List<String>>();
		try {
			ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
			Console.println(String.format("Successfully loaded proof graph for %s:%s", proof.getCallingFeature(), proof.getCallingMethod()), Colors.GREEN);
			graph = collection.getGraphForMethod(proof.getCallingMethod());

			List<String> localPathList = new ArrayList<>();
			ProofNode node = new ProofNode(proof.getCallingFeature(), proof.getCallingMethod(), graph.getIdForFeature(proof.getCallingFeature()));
			localPathList.add(node.getFeature());
			findPaths(graph, node, paths, localPathList);
			localPathList.clear();
			localPathList.add(node.getFeature());
			findForks(graph, node, forks, localPathList);

			for (List<String> path : paths) {
				String[] featureConfig = VerifyFeatures.findValidProduct(path, project);
				toProve.add(new ArrayList<>(Arrays.asList(featureConfig)));
			}
			
			Console.println("Minimal amount of produts for valid proof: ");
			toProve.forEach(l -> {
				Console.println("\t -" + Arrays.toString(l.toArray()));
			});

			Console.println("Caused by this predicate condition the following configurations need to be proven:");
			for (Set<SelectionInfo> allSelections : predicateInfo) {
				List<String> predicateConfig = Arrays.asList(VerifyFeatures.getSolutionForConditions(allSelections, project));
				
				toProve.forEach(originalImpliedConfig -> {
					Set<String> union = new HashSet<String>();
					union.addAll(predicateConfig);
					union.addAll(originalImpliedConfig);
					if (VerifyFeatures.isValidConfiguration(union, project)) {
						toProve.add(new ArrayList<>(union));
					}
				});
				
				Console.println("\t -");
				predicateConfig.forEach(feature -> Console.print(", " + feature + ", ", Colors.BLACK));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return toProve;
	}
	

	public boolean prove(IKeYProof proof, List<String> featureConfig) {
		URI uri = proof.getDiagram().eResource().getURI();
		IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
		String location = fileHandler.getLocationString(uri.toPlatformString(true));
		IProofRepository proofRepo = new FileSystemProofRepository(location + "/proofRepository/");
		List<String> forkToUse = new ArrayList<>();

		int bestForkLength = 0;
		for (List<String> fork : forks) {
			boolean legalFork = true;
			for (String feat : fork) {
				if (featureConfig.contains(feat)) {
					legalFork = false;
				}
			}
							
			if (legalFork && bestForkLength < fork.size()) {
				bestForkLength = fork.size();
				forkToUse = fork;
			}
		}
						
		List<UUID> uuids = forkToUse.stream().map(f -> graph.getIdForFeature(f)).toList();

		FileNameManager manager = new FileNameManager();
		String name = manager.getFileName(null, location, proof.getStatement(), "") + ".proof";
		proofRepo.getPartialProofForId(forkToUse, uuids, location, name);

		ProveWithKey prove = new ProveWithKey(
				proof.getStatement(), 
				proof.getDiagram(), 
				proof.getProgressMonitor(), 
				fileHandler,
				featureConfig,
				0, 
				this.getProofType()
			);

		return prove.proveStatementWithKey(null, 
				proof.getCbcFormulas(),
				proof.getJavaVariables(),
				false, 
				false, 
				proof.getCallingMethod(), 
				"", 
				proof.getCallingClass(), 
				true
			);	
	}

	@Override
	public String getProofType() {
		return KeYInteraction.ABSTRACT_PROOF_COMPLETE;
	}

}
