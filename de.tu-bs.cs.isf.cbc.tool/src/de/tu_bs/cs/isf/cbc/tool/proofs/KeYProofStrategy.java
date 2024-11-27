package de.tu_bs.cs.isf.cbc.tool.proofs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;

public abstract class KeYProofStrategy {
	
	public Set<List<String>> generateFeatureConfigurations(IKeYProof proof) {
		return new HashSet<List<String>>();
	}

	public void generateCode(IKeYProof proof, List<String> featureConfig) {
			URI uri = proof.getDiagram().eResource().getURI();
			IProject project = FileUtil.getProjectFromFileInProject(uri);

			GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(proof.getFeatureProvider());
			genCode.setProofTypeInfo(0, this.getProofType());
			genCode.generate(project.getLocation(), 
					proof.getCallingFeature(), 
					proof.getCallingClass(), 
					proof.getCallingMethod(), 
					featureConfig
				);
	}
	
	public boolean proveWithoutVariation(IKeYProof proof) {
		StatDataCollector.checkForId(proof.getStatement());
		Console.println("Starting verification...\n");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(proof.getStatement().getName())) {
			URI uri = proof.getDiagram().eResource().getURI();
			String platformUri = uri.toPlatformString(true);
			String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
			ProveWithKey prove = new ProveWithKey(proof.getStatement(), proof.getDiagram(), proof.getProgressMonitor(), new FileUtil(platformUri), new ArrayList<>(), 0, KeYInteraction.ABSTRACT_PROOF_FULL);
			return prove.proveStatementWithKey(proof.isReturnStatement(), false, callingClass, true);
		} else {
			Console.println("Statement is not in correct format.");
			return false;
		}
	}

	public abstract boolean prove(IKeYProof proof, List<String> featureConfig);
	public abstract String getProofType();
	
}
