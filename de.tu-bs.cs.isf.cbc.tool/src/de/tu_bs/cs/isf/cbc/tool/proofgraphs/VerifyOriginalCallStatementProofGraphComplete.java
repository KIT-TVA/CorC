package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.proorepository.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.proorepository.IProofRepository;
import de.tu_bs.cs.isf.cbc.statistics.FileNameManager;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyOriginalCallStatementProofGraphComplete extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyOriginalCallStatementProofGraphComplete(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify an original-call statement as proof graph (complete)";
	}

	@Override
	public String getDescription() {
		return "Verifies an original-call statement as partial proof (continue) using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof OriginalStatement) {
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

		VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
		VerifyStatementProofGraphComplete verifyGraphStatement = new VerifyStatementProofGraphComplete(getFeatureProvider());
		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
				IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
				String location = fileHandler.getLocationString(getDiagram().eResource().getURI().toPlatformString(true));
				IProofRepository proofRepo = new FileSystemProofRepository(location + "/proofRepository/");

				try {
					ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
					Console.println(String.format("Successfully loaded proof graph for %s:%s", callingFeature, callingMethod), Colors.GREEN);
					
					ProofGraph graph = collection.getGraphForMethod(callingMethod);
					//Load proof from repository


					List<List<String>> paths = new ArrayList<>();
					List<List<String>> forks = new ArrayList<>();
					List<String> localPathList = new ArrayList<>();
					ProofNode node = new ProofNode(callingFeature,callingMethod, graph.getIdForFeature(callingFeature));
					localPathList.add(node.getFeature());
					verifyGraphStatement.findPaths(graph, node, paths, localPathList);
					localPathList.clear();
					localPathList.add(node.getFeature());
					verifyGraphStatement.findForks(graph, node, forks, localPathList);

					Set<List<String>> toProve = new HashSet<>();
					for (List<String> path : paths) {
						String[] featureConfig = VerifyFeatures.findValidProduct(path, project);
						toProve.add(new ArrayList<>(Arrays.asList(featureConfig)));
					}
					
					Console.println("Minimal amount of produts for valid proof: ");
					toProve.forEach(l -> {
						Console.println("\t -" + Arrays.toString(l.toArray()));
					});
					
					Set<String[]> toProveConverted = verifyGraphStatement.convertArrays(toProve);
					
					boolean proven = true;
					for (String[] featureConfig : toProveConverted) {
						List<String> forkToUse = new ArrayList<>();
						int bestForkLength = 0;
						for (List<String> fork : forks) {
							boolean legalFork = true;
							for (String feat : fork) {
								if (!Arrays.stream(featureConfig).toList().contains(feat)) {
									legalFork = false;
								}
							}
							
							if (legalFork && bestForkLength < fork.size()) {
								bestForkLength = fork.size();
								forkToUse = fork;
							}
						}
						
						List<UUID> uuids = forkToUse.stream().map(f -> graph.getIdForFeature(f)).toList();
						
						FileNameManager manager = new FileNameManager();
						String name = manager.getFileName(null, location, statement, "") + ".proof";
						proofRepo.getPartialProofForId(forkToUse, uuids, location, name);

						GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
						genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						genCode.generate(project.getLocation(), 
								callingFeature, 
								callingClass, 
								callingMethod, 
								featureConfig);

						String[][] variantWrapper = {featureConfig};
						String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass)[0];
						List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, callingMethod);
						List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, callingMethod);
						ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_COMPLETE);
						if(!prove.proveStatementWithKey(null, refinements, refinementVars, false, false, callingMethod, "", callingClass, true)) {
							proven = false;
						}
					}
					statement.setProven(proven);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1_000_000;
		RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.add(duration + ""); //PG DEBUG
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
	}
}
