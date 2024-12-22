package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.util.UUID;

public class ProofNode {
	
	private final String feature;
	private final String method;
	private final UUID uuid;
	
	
	public ProofNode(String feature, String method, UUID uuid) {
		this.feature = feature;
		this.method = method;
		this.uuid = uuid;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public UUID getId() {
		return this.uuid;
	}
	
	public ProofNode getWithGeneralMethod() {
		return new ProofNode(feature, "GENERAL", uuid);
	}
	public String getFeature() {
		return this.feature;
	}
	
	@Override
	public String toString() {
		return this.feature;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProofNode node) {
			//Console.println(String.format("%s and %s => %s", feature.getName(), node.getFeature().getName(), feature.getName().equals(node.getFeature().getName())));
			return feature.equals(node.getFeature()) && this.method.equals(node.getMethod());
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		int result = 17;

		if (feature != null) {
			result = 31 * result * feature.hashCode();
		}
		
		if (method != null) {
			result = 31 * result * method.hashCode();
		}	
		
		return result;
	}
}