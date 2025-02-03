package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.util.Colors;
import de.tu_bs.cs.isf.cbc.util.Console;

public class CreateODGraphHandler extends AbstractHandler implements IHandler {

	private static final int MAX_DEPTH = 500; 

	private final Set<String> varMethodCalls = new HashSet<String>();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Console.println("Creation of OD Graph started!");
		
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		
		if (selection != null && selection instanceof StructuredSelection structuredSelection) {
			if (structuredSelection.size() != 1) {
				throw new ExecutionException("Select one project only.");
			}
			if (structuredSelection.getFirstElement() instanceof IProject project) {
				createGraphForProject(project);
			}
		}
		
		return null;
	}
	
	private void createGraphForProject(IProject project) {
		long start = System.currentTimeMillis();
		Path featureModelPath = Paths.get(project.getLocation() + "/model.xml");
		IFeatureModel featureModel = FeatureModelManager.load(featureModelPath);
		
		Console.println("Feature Order: ");
		for (String feature : featureModel.getFeatureOrderList()) {
			Console.println(feature);
		}
		
		ProofGraphCollection graphCollection = new ProofGraphCollection(featureModel);

		Set<String> methods = new HashSet<String>();
		for (IFeature feature : featureModel.getFeatures()) {
			String featurePath = project.getLocation() + "/features/" + feature.getName();
			File featureDir = new File(featurePath);
			if (featureDir.exists()) {
				for (File featureClass : featureDir.listFiles()) {
					for (File featureFile : featureClass.listFiles()) {
						if (featureFile.getName().endsWith(".cbcmodel")) {
							methods.add(featureFile.getName().split("\\.")[0]);
						}
					}
				}
			}
		}
		
		for (IFeature feature : featureModel.getFeatures()) {

			Console.println("Now generating feature: " + feature, Colors.GREEN);
			String featurePath = project.getLocation() + "/features/" + feature.getName();
			File featureDir = new File(featurePath);
			
			
			if (featureDir.exists()) {
				for (File featureClass : featureDir.listFiles()) {
					for (File featureFile : featureClass.listFiles()) {
					//Find cbcmodel
					if (featureFile.getName().endsWith(".cbcmodel")) {
						String methodName = featureFile.getName().split("\\.")[0];
						ResourceSet rs = new ResourceSetImpl(); 
						Resource cbcRes = rs.getResource(URI.createFileURI(featureFile.getAbsolutePath()), true);
						
						
						ProofGraph graph = graphCollection.getGraphForMethod(methodName);

						//graph.getNode(feature, methodName).addImplementedMethod(methodName);
						ProofNode nodeA = new ProofNode(feature.getName(), methodName, graph.getIdForFeature(feature.getName()));
						graph.createNode(feature, methodName);
						graph.addImplementedMethod(feature.getName(), methodName);
						for (EObject cont : cbcRes.getContents()) {
							if (cont instanceof CbCFormula cbcForm) {
								findVarMethodCalls(cbcForm.getStatement(), 0);
								if (!this.varMethodCalls.isEmpty()) {
									Set<String> varM = new HashSet<String>();
									for (String m : varMethodCalls) {
										for (String m2 : methods) {
											Console.println("If " + m + " contains " + m2);
											if (m.contains(m2)) {
												varM.add(m2);
											}
										}
									}
									
									if (!varM.isEmpty()) {
										Console.println("Found (Variational) Method Calls:");
										graph.setVarMethodCalls(nodeA, varM);
										varM.forEach(m -> {
											Console.println("\t-" + m);
										});

									}
																	}
								if (hasOriginalCall(cbcForm.getStatement(), 0)) {
									int index = Math.max(featureModel.getFeatureOrderList().indexOf(feature.getName()) - 1, 0);
									IFeature featureB = featureModel.getFeature(featureModel.getFeatureOrderList().get(index));
									graph.createEdge(nodeA, new ProofNode(featureB.getName(), methodName, graph.getIdForFeature(featureB.getName())));
									/*If there are optional features in feature order also add edges to higher features in order*/
									while(!featureB.getStructure().isMandatory() && index >= 0) {
										index = Math.max(0, index-1);
										featureB = featureModel.getFeature(featureModel.getFeatureOrderList().get(index));
										graph.createEdge(nodeA, new ProofNode(featureB.getName(), methodName, graph.getIdForFeature(featureB.getName())));
									}
								}
							}
						}
					}
				}
				}
				
			} else {
				Console.println("Feature Dir does not exist for " + feature.getName());
			}
			this.varMethodCalls.clear();
		}

		graphCollection.clean();

		String mermaid = graphCollection.toMermaid();
		Console.println(mermaid);

		StringSelection stringSelection = new StringSelection(mermaid);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		// save graph
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
			String jsonString = gson.toJson(graphCollection);
			File graphFile = new File(project.getRawLocation() + "/graph.json");

			PrintWriter out = new PrintWriter(graphFile);
			out.println(jsonString);
			out.close();
			project.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (JsonIOException | IOException | CoreException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		Console.println("Graph Creation Time: " + (end - start));
	}
	
	private void findVarMethodCalls(AbstractStatement statement, int depth) {
		if (depth > MAX_DEPTH || statement == null) {
			return;
		}
		if (statement instanceof MethodStatement methodStatement){
			findVarMethodCalls(methodStatement.getRefinement(), ++depth);
			this.varMethodCalls.add(methodStatement.getName().split("\\(")[0]);
		}

		
		if (statement instanceof CompositionStatement compStatement) {

			findVarMethodCalls(compStatement.getFirstStatement().getRefinement(), ++depth);
			findVarMethodCalls(compStatement.getSecondStatement().getRefinement(), ++depth);

		} else if (statement instanceof SmallRepetitionStatement repStatement) {
			findVarMethodCalls(repStatement.getLoopStatement().getRefinement(), ++depth);
		} else if (statement instanceof SelectionStatement selecStatement) {
			for (AbstractStatement stmnt : selecStatement.getCommands()) {
				findVarMethodCalls(stmnt.getRefinement(), ++depth);
			}
		} else if (statement instanceof OriginalStatement origiStatement) {
				findVarMethodCalls(origiStatement.getRefinement(), ++depth);
			} else {
			findVarMethodCalls(statement.getRefinement(), ++depth);
			}

		
	}
	
	private boolean hasOriginalCall(AbstractStatement statement, int depth) {
			if (depth > MAX_DEPTH || statement == null) 
				return false;
			
			if (statement instanceof OriginalStatement) {
				return true;
			}

			if (statement instanceof CompositionStatement compStatement) {
				return hasOriginalCall(compStatement.getFirstStatement().getRefinement(), ++depth)
				 || hasOriginalCall(compStatement.getSecondStatement().getRefinement(), ++depth);
			} else if (statement instanceof SmallRepetitionStatement repStatement) {
				return hasOriginalCall(repStatement.getLoopStatement().getRefinement(), ++depth);
			} else if (statement instanceof SelectionStatement selecStatement) {
				for (AbstractStatement stmnt : selecStatement.getCommands()) {
					if(hasOriginalCall(stmnt.getRefinement(), ++depth)) { 
						return true;
					}
				}
				return false;
			} else if (statement instanceof MethodStatement methodStatement){
				return hasOriginalCall(methodStatement.getRefinement(), ++depth);
			} else {
				return hasOriginalCall(statement.getRefinement(), ++depth);
			}
		}
}