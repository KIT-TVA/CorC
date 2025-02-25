package de.tu_bs.cs.isf.cbc.tool.proofs;

import de.tu_bs.cs.isf.cbc.util.FileHandler;

public final class KeYProofProver {

	private final IKeYProof keyProof;
	private final KeYProofStrategy proofStrategy;
	
	public KeYProofProver(IKeYProof proof, KeYProofStrategy proofStrategy) {
		this.keyProof = proof;
		this.proofStrategy = proofStrategy;
	}

	public boolean prove() {
		if (!FileHandler.instance.isSPL(this.keyProof.getDiagram().eResource().getURI())) {
			return this.proofStrategy.proveWithoutVariation(keyProof);
		} else {
			return this.proofStrategy.generateFeatureConfigurations(keyProof)
				.stream().map(featureConfig -> {
					this.proofStrategy.generateCode(keyProof, featureConfig);
					return this.proofStrategy.prove(keyProof, featureConfig);
				}).allMatch(val -> val == true);

		}
	}
}
