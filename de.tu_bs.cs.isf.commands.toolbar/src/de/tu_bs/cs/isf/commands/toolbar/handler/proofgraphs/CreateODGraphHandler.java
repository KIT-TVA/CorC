package de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

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
import de.tu_bs.cs.isf.cbc.util.Console;

public class CreateODGraphHandler extends AbstractHandler implements IHandler {

	private static final int MAX_DEPTH = 50; 

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
		Path featureModelPath = Paths.get(project.getLocation() + "/model.xml");
		IFeatureModel featureModel = FeatureModelManager.load(featureModelPath);
		
		Console.println("Feature Order: ");
		for (String feature : featureModel.getFeatureOrderList()) {
			Console.println(feature);
		}
		
		ProofGraphCollection graphCollection = new ProofGraphCollection();
		for (IFeature feature : featureModel.getFeatures()) {

			
			String featurePath = project.getLocation() + "/features/" + feature.getName();
			File featureDir = new File(featurePath);
			
			if (featureDir.exists()) {
				for (File featureClass : featureDir.listFiles()) {
					Console.println(featureClass.getName());
					for (File featureFile : featureClass.listFiles()) {
					Console.println(featureFile.getName());
					//Find cbcmodel
					if (featureFile.getName().endsWith(".cbcmodel")) {
						String methodName = featureFile.getName().split("\\.")[0];
						ResourceSet rs = new ResourceSetImpl(); 
						Resource cbcRes = rs.getResource(URI.createFileURI(featureFile.getAbsolutePath()), true);
						
						
						ProofGraph graph = graphCollection.getGraphForMethod(methodName);

						//graph.getNode(feature, methodName).addImplementedMethod(methodName);
						ProofNode nodeA = new ProofNode(feature, methodName);
						graph.createNode(feature, methodName);
						graph.addImplementedMethod(feature, methodName);
						for (EObject cont : cbcRes.getContents()) {
							if (cont instanceof CbCFormula cbcForm) {
								
								if (runDFS(cbcForm.getStatement(), 0)) {
									int index = Math.max(featureModel.getFeatureOrderList().indexOf(feature.getName()) - 1, 0);
									IFeature featureB = featureModel.getFeature(featureModel.getFeatureOrderList().get(index));
									graph.createEdge(nodeA, new ProofNode(featureB, methodName));
									/*If there are optional features in feature order also add edges to higher features in order*/
									while(!featureB.getStructure().isMandatory() && index >= 0) {
										index = Math.max(0, index-1);
										featureB = featureModel.getFeature(featureModel.getFeatureOrderList().get(index));
										graph.createEdge(nodeA, new ProofNode(featureB, methodName));
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
		}

		graphCollection.clean();

		String mermaid = graphCollection.toMermaid();
		Console.println(mermaid);

		StringSelection stringSelection = new StringSelection(mermaid);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	private boolean runDFS(AbstractStatement statement, int depth) {
			if (depth > MAX_DEPTH || statement == null) 
				return false;
			
			if (statement instanceof OriginalStatement) {
				return true;
			}

			if (statement instanceof CompositionStatement compStatement) {
				return runDFS(compStatement.getFirstStatement().getRefinement(), ++depth)
				 || runDFS(compStatement.getSecondStatement().getRefinement(), ++depth);
			} else if (statement instanceof SmallRepetitionStatement repStatement) {
				return runDFS(repStatement.getLoopStatement().getRefinement(), ++depth);
			} else if (statement instanceof SelectionStatement selecStatement) {
				for (AbstractStatement stmnt : selecStatement.getCommands()) {
					if(runDFS(stmnt.getRefinement(), ++depth)) { 
						return true;
					}
				}
				return false;
			} else if (statement instanceof MethodStatement methodStatement){
				return runDFS(methodStatement.getRefinement(), ++depth);
			} else {
				return runDFS(statement.getRefinement(), ++depth);
			}
		}
}