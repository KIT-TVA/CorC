package de.tu_bs.cs.isf.cbc.tool.proofs;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public abstract class KeYProofStrategy {
	
	public Set<List<String>> generateFeatureConfigurations(IKeYProof proof) {
		URI uri = proof.getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		String[] featuresConfig = VerifyFeatures.findValidProduct(List.of(proof.getCallingFeature()), project);

		return Set.of(Arrays.asList(featuresConfig));
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

	public abstract boolean prove(IKeYProof proof, List<String> featureConfig);
	public abstract String getProofType();
	
}
