package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {

	public VerifyStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (begin)";
	}

	@Override
	public String getDescription() {
		return "Creates the partial proof for all feature nodes in this method";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		//TODO: Only show when SPL (check for variational)
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		long startTime = System.nanoTime();
		PictogramElement[] pes = context.getPictogramElements();
		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
				
				GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
				
				List<String> features = List.of(callingFeature);
				String[] featureConfig = VerifyFeatures.findValidProduct(features, project);
				
				Console.println(Arrays.toString(featureConfig));
				
				genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_BEGIN);

				genCode.generate(project.getLocation(), 
						callingFeature, callingClass, callingMethod, 
						featureConfig);


				VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
				IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
				String[][] variantWrapper = {featureConfig};
				String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass)[0];
				List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, callingMethod);
				List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, callingMethod);
				ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
				boolean proven = prove.proveStatementWithKey(null, refinements, refinementVars, false, false, callingMethod, "", callingClass, true);

				statement.setProven(proven);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1_000_000;
				startTime = System.nanoTime();
				Console.println("\nVerification done."); 
				Console.println("Time needed: " + duration + "ms");
				//Somehow find Statement.key or always Statement1? TODO: Think about it
				String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true)) + "/Statement2.key";

				//Save to Proof repository
				/*
				try {
					ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
					ProofNode node = collection.getProofNode(callingMethod, callingFeature);
					String proofText;
					proofText = Files.readString(java.nio.file.Path.of(location));
					IProofRepository proofRepo = new FileSystemProofRepository();
					proofRepo.savePartialProofForId(node.getId(), proofText);
					Console.print("Saved proof to Proof Repository as " + node.getId() + ".proof", Colors.GREEN);
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
			}
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		Console.println("\nFile operations done."); 
		Console.println("Time needed: " + duration + "ms");
	}
	
	

}