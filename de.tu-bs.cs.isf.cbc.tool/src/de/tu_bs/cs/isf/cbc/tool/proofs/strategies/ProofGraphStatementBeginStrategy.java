package de.tu_bs.cs.isf.cbc.tool.proofs.strategies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

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

public final class ProofGraphStatementBeginStrategy extends KeYProofStrategy implements ProofGraphStrategy {

	@Override
	public Set<List<String>> generateFeatureConfigurations(IKeYProof proof) {
		List<Set<SelectionInfo>> predicateInfo = generatePredicateInfo(proof);
		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		Set<List<String>> toProve = new HashSet<List<String>>();

		String[] featuresConfig = VerifyFeatures.findValidProduct(List.of(proof.getCallingFeature()), project);
		toProve.add(Arrays.asList(featuresConfig));

		Console.println("Caused by this predicate condition the following configurations need to be proven:");
		for (Set<SelectionInfo> allSelections : predicateInfo) {
			List<String> predicateConfig = Arrays
					.asList(VerifyFeatures.getSolutionForConditions(allSelections, project));
			toProve.add(predicateConfig);
			Console.println("\t -");
			predicateConfig.forEach(feature -> Console.print(", " + feature + ", ", Colors.BLACK));
		}

		return toProve;
	}

	public boolean prove(IKeYProof proof, List<String> featureConfig) {
		URI uri = proof.getDiagram().eResource().getURI();
		IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));

		ProveWithKey prove = new ProveWithKey(proof.getStatement(), proof.getDiagram(), proof.getProgressMonitor(),
				fileHandler, featureConfig, 0, this.getProofType());

		return prove.proveStatementWithKey(null, proof.getCbcFormulas(), proof.getJavaVariables(),
				proof.isReturnStatement(), false, proof.getCallingMethod(), "", proof.getCallingClass(), true);
	}

	@Override
	public String getProofType() {
		return KeYInteraction.ABSTRACT_PROOF_FULL;
	}
}
