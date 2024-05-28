package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class ProofGraphCollection {

	private final Map<String, ProofGraph> graphs;
	private final transient IFeatureModel featureModel;
	
	public ProofGraphCollection(IFeatureModel featureModel) {
		this.graphs = new HashMap<String, ProofGraph>();
		this.featureModel = featureModel;
	}
	
	public static ProofGraphCollection loadFromJson(String path) throws FileNotFoundException {

		BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
        ProofGraphCollection proofGraphCollection = gson.fromJson(bufferedReader, ProofGraphCollection.class);
        //KEEP IN MIND: If there are any problems (clean used o.a. restore feature model)
        return proofGraphCollection;
	}
	
	public ProofGraph getGraphForMethod(String method) {
		this.graphs.computeIfAbsent(method, __ -> new ProofGraph(this.featureModel));
		return this.graphs.get(method);
	}
	
	public ProofNode getProofNode(String method, String feature) {
		for (ProofNode node : this.graphs.get(method).getAdjacencyList().keySet()) {
			if (node.getFeature().equals(feature)) {
				return node;
			}
		}
		
		return null;
	}
	
	public Set<String> getMethods() {
		return this.graphs.keySet();
	}
	
	public void clean() {
		graphs.forEach((__, graph) -> graph.cleanGraph());
	}
	
	public String toMermaid() {

		StringBuilder builder = new StringBuilder("graph" + "\n");
		
		List<String> removedMethod = new ArrayList<String>();
		this.graphs.forEach((method, graph) -> {
			if (!removedMethod.contains(method)) {
				List<String> additionalMethods = new ArrayList<String>();
				Map<String, ProofGraph> mapWithout = new HashMap<>(Map.copyOf(graphs));
				mapWithout.remove(method);
				mapWithout.forEach((key, value) -> {
					if (graph.equals(value)) {
						additionalMethods.add(key);
					}
				});

				if (!additionalMethods.isEmpty()) {
					for (String cMethod : additionalMethods) {
						String methodName = cMethod + "((" + cMethod + "))";
						builder.append(methodName + "\n");
					}
				}
				
				String methodName= method + "((" + method + "))";
				builder.append(methodName + "\n");
				graph.getAdjacencyList().forEach((key, entry) -> {
					builder.append(key.toString()  + "::" + method + "\n");
					if (graph.getAdjacencyList().get(key).isEmpty()) {
						builder.append(key.toString() + "::" + method + "-->" + methodName + "\n");
						for (String cMethod : additionalMethods) {
							builder.append(key.toString()+  "::" + method + "-->" + cMethod +"((" + cMethod + "))"+ "\n");
							removedMethod.add(cMethod);
						}
					}

					entry.forEach(val -> {
						builder.append(key.toString() + "::" + method + "-->" + val.toString() + "::" + method + "\n");
					});
					

				});

			}
		});
		
		return builder.toString();
	}
}