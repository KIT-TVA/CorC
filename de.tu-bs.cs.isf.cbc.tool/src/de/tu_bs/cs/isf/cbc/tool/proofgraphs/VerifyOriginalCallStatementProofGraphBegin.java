package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
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
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraph;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;

public class VerifyOriginalCallStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {
	
	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyOriginalCallStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify an original-call statement as proof graph (begin)";
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
		
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement statement) {
				URI uri = this.getDiagram().eResource().getURI();
				FileHandler.instance.deleteFolder(uri, "tests");
				
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
				String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
				String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
				try {
				ProofGraphCollection collection = ProofGraphCollection.loadFromJson(project.getRawLocation() + "/graph.json");
				Console.println(String.format("Successfully loaded proof graph for %s:%s", callingFeature, callingMethod), Colors.GREEN);
				ProofGraph graph = collection.getGraphForMethod(callingMethod);

				ProofNode node = new ProofNode(callingFeature, callingMethod, graph.getIdForFeature(callingFeature));
				List<List<String>> pathsToFork = new ArrayList<>();
				List<String> localPathList = new ArrayList<>();
				localPathList.add(callingFeature);

				this.findForks(graph, node, pathsToFork, localPathList);

				GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
				
				Console.println(Arrays.toString(pathsToFork.toArray()));
				
				for (List<String> path : pathsToFork) {
					Console.println("Creating " + path.get(path.size() - 1) + "-resolved proof");
					String[] featureConfig = VerifyFeatures.findValidProduct(path, project);
					
					Console.println(Arrays.toString(featureConfig));
					
					genCode.setProofTypeInfo(0, KeYInteraction.ABSTRACT_PROOF_BEGIN);

					genCode.generateWithRestriction(project.getLocation(), 
							callingFeature, callingClass, callingMethod, 
							featureConfig, path.get(path.size() - 1));
					
					IFileUtil fileHandler = new FileUtil(uri.toPlatformString(true));
					String[][] variantWrapper = {featureConfig};
					String variant = verifyStatement.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass)[0];
					List<CbCFormula> refinements = verifyStatement.generateCbCFormulasForRefinements(variant, callingMethod);
					List<JavaVariables> refinementVars = verifyStatement.generateJavaVariablesForRefinements(variant, callingMethod);
					ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, fileHandler, featureConfig, 0, KeYInteraction.ABSTRACT_T_RESOLVED_PROOF);
					boolean proven = prove.proveStatementWithKey(null, refinements, refinementVars, false, false, callingMethod, "", callingClass, true);

					statement.setProven(proven);
					long endTime = System.nanoTime();
					long duration = (endTime - startTime) / 1_000_000;
					startTime = System.nanoTime();
					Console.println("\nVerification done."); 
					Console.println("Time needed: " + duration + "ms");
				}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		
			}
		}
	}

	public void findForks(ProofGraph graph, ProofNode node, List<List<String>> paths, List<String> localPathList) {
		for (ProofNode cn : graph.getAdjacencyList().get(node)) {
			localPathList.add(cn.getFeature());
			findForks(graph, cn, paths, localPathList);
			localPathList.remove(cn.getFeature());
		}

		if (graph.getAdjacencyList().get(node).size() >= 1) { //TODO: Change to 2 
			paths.add(List.copyOf(localPathList));
			return;
		}


	}
}
