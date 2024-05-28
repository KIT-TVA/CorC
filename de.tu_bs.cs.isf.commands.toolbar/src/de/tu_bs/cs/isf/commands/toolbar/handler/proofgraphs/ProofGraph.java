package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.Variables;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.cnf.solver.AdvancedSatSolver;
import de.ovgu.featureide.fm.core.analysis.cnf.solver.ISimpleSatSolver.SatResult;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.tu_bs.cs.isf.cbc.util.Console;

public class ProofGraph {
	
	// See if important (multiple edges get removed because set)
	private final Map<ProofNode, Set<ProofNode>> adjacencyList;
	private final Map<String, Set<String>> featureMap;
	private final Map<String, UUID> idMap;
	private final transient IFeatureModel featureModel;
	
	public ProofGraph(IFeatureModel featureModel) {
		this.adjacencyList = new HashMap<ProofNode, Set<ProofNode>>();
		this.featureMap = new HashMap<String, Set<String>>();
		this.idMap = new HashMap<String, UUID>();
		this.featureModel = featureModel;
	}
	
	public void createNode(IFeature feature, String method) {
		this.adjacencyList.computeIfAbsent(new ProofNode(feature.getName(), method, getIdForFeature(feature.getName())), a -> new HashSet<ProofNode>());
	}
	
	public UUID getIdForFeature(String feature) {
		idMap.computeIfAbsent(feature, __ -> UUID.randomUUID());
		return idMap.get(feature);
	}
	
	public void createEdge(ProofNode nodeA, ProofNode nodeB) {
		this.adjacencyList.computeIfAbsent(nodeA, __ -> new HashSet<ProofNode>());
		this.adjacencyList.get(nodeA).add(nodeB);
	}
	
	public void addImplementedMethod(String feature, String method) {
		featureMap.computeIfAbsent(feature, __ -> new HashSet<String>());
		featureMap.get(feature).add(method);
	}
	
	public Set<String> getImplementedMethods(String feature) {
		featureMap.computeIfAbsent(feature, __ -> new HashSet<String>());
		return Set.copyOf(featureMap.get(feature));
	}
	
	public Map<ProofNode, Set<ProofNode>> getAdjacencyList() {
		return Map.copyOf(adjacencyList);
	}
	
	public String toMermaid() {
		StringBuilder builder = new StringBuilder();
		builder.append("graph\n");
		this.adjacencyList.forEach((key, entry) -> {
			builder.append(key.toString() + "\n");
			entry.forEach(val -> {
					builder.append(key.toString() + "-->" + val.toString() + "\n");
			});
		});
		return builder.toString();
	}
	
	public void cleanGraph() {
		this.adjacencyList.forEach((caller, called) -> {
			//########## STEP 1 - Remove edges to features that don't implement methods ########
			/*
			List<ProofNode> toRemove = new ArrayList<ProofNode>();
			called.forEach(val -> {
				List<String> intersection = caller.getImplementedMethods().stream().filter(method -> val.getImplementedMethods().contains(method)).toList();
				// No common methods
				if (intersection.isEmpty()) {
					toRemove.add(val);
				}
			});
			this.adjacencyList.get(caller).removeAll(toRemove);
			*/
			

			List<ProofNode> toRemove = new ArrayList<ProofNode>();
			called.forEach(val -> {
				//########## STEP 1 - Remove connections to methods that don't implement########
				List<String> intersection = this.getImplementedMethods(caller.getFeature()).stream().filter(method -> this.getImplementedMethods(val.getFeature()).contains(method)).toList();
				if (intersection.isEmpty()) {
					toRemove.add(val);
				}
				
				//########## STEP 2 - If features can't exist together remove########
				Configuration configuration = new Configuration(new FeatureModelFormula(this.featureModel));
				configuration.setManual(caller.getFeature(), Selection.SELECTED);
				configuration.setManual(val.getFeature(), Selection.SELECTED);
				CNF cnf = configuration.getFeatureModelFormula().getCNF();
				AdvancedSatSolver solver = new AdvancedSatSolver(cnf);
				Variables vars = cnf.getVariables();
				for (SelectableFeature feature : configuration.getFeatures()) {
					if (feature.getSelection() != Selection.UNDEFINED) {
						solver.assignmentPush(
								vars.getVariable(
										feature.getFeature().getName(), 
										feature.getSelection() == Selection.SELECTED));
					}
				}
				
				if (solver.hasSolution() == SatResult.FALSE) {
					toRemove.add(val);
				}

			});
			
			this.adjacencyList.get(caller).removeAll(toRemove);
			
			
		});
		
		this.adjacencyList.forEach((caller, called) -> {
			List<ProofNode> toRemove = new ArrayList<ProofNode>();
			called.forEach(val -> {
				Configuration configuration = new Configuration(new FeatureModelFormula(this.featureModel));
				configuration.setManual(caller.getFeature(), Selection.SELECTED);
				configuration.setManual(val.getFeature(), Selection.UNSELECTED);
				CNF cnf = configuration.getFeatureModelFormula().getCNF();
				AdvancedSatSolver solver = new AdvancedSatSolver(cnf);
				Variables vars = cnf.getVariables();
				for (SelectableFeature current : configuration.getFeatures()) {
					if (current.getSelection() != Selection.UNDEFINED) {
						solver.assignmentPush(
							vars.getVariable(
									current.getFeature().getName(), 
									current.getSelection() == Selection.SELECTED));
					}
				}
				
				if (solver.hasSolution() == SatResult.FALSE) {
					int index = featureModel.getFeatureOrderList().indexOf(val.getFeature());
					for (int i = index - 1; i >= 0; i--) {
						String featToRemove = featureModel.getFeature(featureModel.getFeatureOrderList().get(i)).getName();
						this.featureMap.get(featToRemove).forEach(method -> toRemove.add(new ProofNode(featToRemove, method, getIdForFeature(featToRemove)))); 
					}
				}
			});
			
			this.adjacencyList.get(caller).removeAll(toRemove);
		});
	}

	public String toMermaidWithMethods() {
		StringBuilder builder = new StringBuilder();
		builder.append("graph\n");
		/*
		List<Set<String>> methods = this.adjacencyList.keySet().stream().map(ProofNode::getImplementedMethods).toList();
		Set<String> reducedMethods = new HashSet<String>();
		for (Set<String> method : methods) {
			reducedMethods.addAll(method);
		}
		
		reducedMethods.forEach(method -> builder.append(method + "(("+ method + "))\n"));
		
		this.adjacencyList.forEach((key, entry) -> {
			builder.append(key.toString() + "\n");

			key.getImplementedMethods().forEach(method -> {
				builder.append(key.toString() + "-->" + method + "(("+ method + "))\n");
			});
			entry.forEach(val -> builder.append(key.toString() + "-->" + val.toString() + "\n"));
		});*/
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProofGraph graph) {
			List<ProofNode> sortedA = new ArrayList<>(this.adjacencyList.keySet().stream().map(ProofNode::getWithGeneralMethod).toList());
			List<ProofNode> sortedB = new ArrayList<>(graph.adjacencyList.keySet().stream().map(ProofNode::getWithGeneralMethod).toList());
			Collections.sort(sortedA, Comparator.comparing(ProofNode::toString));
			Collections.sort(sortedB, Comparator.comparing(ProofNode::toString));
			if (!sortedA.equals(sortedB)) {
				//Console.println(String.format("Different keySets (%s, %s)", sortedA, sortedB));
				return false;
			}
			boolean isEqual = true;
			for (ProofNode key : this.adjacencyList.keySet()) {
				if (graph.adjacencyList.get(key) != null) {
					
					List<ProofNode> sortedAEdges = new ArrayList<>(this.adjacencyList.get(key).stream().map(ProofNode::getWithGeneralMethod).toList());
					List<ProofNode> sortedBEdges = new ArrayList<>(graph.adjacencyList.get(key).stream().map(ProofNode::getWithGeneralMethod).toList());
					Collections.sort(sortedAEdges, Comparator.comparing(ProofNode::toString));
					Collections.sort(sortedBEdges, Comparator.comparing(ProofNode::toString)); 
					if (!sortedAEdges.equals(sortedBEdges)) {
						isEqual = false;
					}

				}
			}
			
			if (isEqual) {
				Console.println("Same result set and trees are equal");
			}
			return isEqual;
		}

		return super.equals(obj);
	}
}