package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;

public class VerifyMethodCallStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature{

	public VerifyMethodCallStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}


	@Override
	public String getName() {
		return "Verify a method-call statement as proof graph (begin)";
	}

	@Override
	public String getDescription() {
		return "Verifies an method-call statement as partial proof (continue) using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof MethodStatement) {
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
				ProofGraphCollection collection;
				try {
					collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
				
				Console.println(String.format("Successfully loaded proof graph for %s:%s", callingFeature, callingMethod), Colors.GREEN);
				ProofGraph graph = collection.getGraphForMethod(callingMethod);
				
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
				DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
				JavaVariables vars = extractor.getVars();
				String varM = handleVarM(Parser.extractMethodNameFromStatemtent(statement.getName()), callingClass, vars);
				String varMParts[] = varM.split("\\.");
				String[][] variantWrapper = {featureConfig};
				String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, varM.contains(".") ? varMParts[0] : callingClass)[0];
				List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, varMParts[1].toLowerCase());
				List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, varMParts[1].toLowerCase());
				ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
				boolean proven = prove.proveStatementWithKey(refinements, refinements, refinementVars, false, false, callingMethod, varM, callingClass, true);


				statement.setProven(proven);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1_000_000;
				startTime = System.nanoTime();
				RunEvaluationForStatementPP.WHOLE_RUNTIME_START.add(duration + ""); //PG DEBUG
				Console.println("\nVerification done."); 
				Console.println("Time needed: " + duration + "ms");

				String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true));
				IProofRepository proofRepo = new FileSystemProofRepository(location + "/proofRepository/");
				FileNameManager manager = new FileNameManager();
				String name = manager.getFileName(null, location, statement, "") + ".proof";
				List<UUID> uuids = features.stream().map(f -> graph.getIdForFeature(f)).toList();
				
				String folderName = "";
				
				if (features.size() != 1) {
					folderName += "/";
					for (String feat : featureConfig) {
						folderName += feat;
					}
					folderName += "/";
				}
				
				proofRepo.savePartialProofForId(features, uuids, location + folderName + name);

			} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		}
			}
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		Console.println("\nFile operations done."); 
		Console.println("Time needed: " + duration + "ms");
	}
	
	private String handleVarM(String methodCall, String callingClass, JavaVariables vars) {
		if (!methodCall.contains(".")) { // Call without class reference
			return callingClass + "." + methodCall;
		} else if (methodCall.contains("this.")) { // Call with this reference
			return methodCall.replace("this.", "." + callingClass);
		} else if (Character.isUpperCase(methodCall.charAt(0))) { // Call with static class reference
			return methodCall;
		} else { // Call with reference to a variable/object
			String type = methodCall.split("\\.")[0];
			for (JavaVariable var : vars.getVariables()) {
				if (var.getName().split(" ")[1].equals(type)) {
					return methodCall.replace(type, var.getName().split(" ")[0]);
				}
			}
			for (Field f : vars.getFields()) {
				if (f.getName().equals(type)) {
					return methodCall.replace(type, f.getType());
				}
			}
		}
		return methodCall;
	}
}
