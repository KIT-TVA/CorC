package de.tu_bs.cs.isf.cbc.tool.proofs;

import java.util.List;
import java.util.Set;

public final class KeYProofProver {

	private final IKeYProof keyProof;
	private final KeYProofStrategy proofStrategy;

	public KeYProofProver(IKeYProof proof, KeYProofStrategy proofStrategy) {
		this.keyProof = proof;
		this.proofStrategy = proofStrategy;
	}

	public boolean prove() {
		final Set<List<String>> featureConfigurations = this.proofStrategy.generateFeatureConfigurations(keyProof);
		if (featureConfigurations.isEmpty()) {
			return this.proofStrategy.proveWithoutVariation(keyProof);
		} else {
			return this.proofStrategy.generateFeatureConfigurations(keyProof).stream().map(featureConfig -> {
				this.proofStrategy.generateCode(keyProof, featureConfig);
				return this.proofStrategy.prove(keyProof, featureConfig);
			}).allMatch(val -> val == true);

		}
	}

}
