package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import de.ovgu.featureide.fm.core.base.IFeature;

public class ProofNode {
	
	private final IFeature feature;
	private final String method;
	
	
	public ProofNode(IFeature feature, String method) {
		this.feature = feature;
		this.method = method;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public ProofNode getWithGeneralMethod() {
		return new ProofNode(feature, "GENERAL");
	}
	public IFeature getFeature() {
		return this.feature;
	}
	
	@Override
	public String toString() {
		return this.feature.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProofNode node) {
			//Console.println(String.format("%s and %s => %s", feature.getName(), node.getFeature().getName(), feature.getName().equals(node.getFeature().getName())));
			return feature.getName().equals(node.getFeature().getName()) && this.method.equals(node.getMethod());
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