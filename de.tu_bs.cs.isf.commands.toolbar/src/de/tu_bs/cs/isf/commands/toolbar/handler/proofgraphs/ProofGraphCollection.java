package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProofGraphCollection {

	private final Map<String, ProofGraph> graphs;
	
	public ProofGraphCollection() {
		this.graphs = new HashMap<String, ProofGraph>();
	}
	
	public ProofGraph getGraphForMethod(String method) {
		this.graphs.computeIfAbsent(method, __ -> new ProofGraph());
		return this.graphs.get(method);
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