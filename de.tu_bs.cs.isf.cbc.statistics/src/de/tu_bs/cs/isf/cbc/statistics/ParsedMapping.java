package de.tu_bs.cs.isf.cbc.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

import org.eclipse.core.runtime.Path;

public class ParsedMapping {
	public String keyFilePath;
	public String diagramName;
	public CbCFormula formula;
	public AbstractStatement statement;
	public String statementId;
	public String diagramPath;
	public String problemHash;
	
	private IFile file;
	private String fileContent;
	private IFeatureProvider fp;
	
	public ParsedMapping(IFile file, String fileContent, IFeatureProvider fp) {
		this.file = file;
		this.fileContent = fileContent;
		this.fp = fp;
		parse();
	}
	
	private void parse() {
		final ResourceSet rSet = new ResourceSetImpl();
		this.keyFilePath = file.getLocation().toOSString();
		this.diagramName = StatDataCollector.getFolderName(this.keyFilePath);
		this.diagramPath = Path.fromOSString(parseDiagramPathFromProofFile(file)).toPortableString();
		var diagramFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(Path.fromPortableString(this.diagramPath));
		var diagram = GetDiagramUtil.getDiagramFromFile(diagramFile, rSet);
		formula = null;
		for (Shape shape : diagram.getChildren()) {
			Object obj = fp.getBusinessObjectForPictogramElement(shape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
				break;
			}
		}	
		var refinementName = file.getName().substring(0, file.getName().indexOf("."));
		if (refinementName.equals("helper")) {
			return;
		}
		AbstractStatement statement = findRefinement(formula.getStatement(), refinementName, new ArrayList<EObject>());
		this.statement = statement;
		if (statement.getId() == null || statement.getId().isEmpty()) {
			throw new RuntimeException(
					"Error: Statistics Data Collector - Please right click on the Diagram within the project explorer to add IDs.");
		} else {
			this.statementId = statement.getId();
		}
		String problem = parseProblem();
		this.problemHash = StatDataCollector.getHashFromProblem(problem);
	}
	
	private String parseDiagramPathFromProofFile(IFile file) {
		String location = file.getLocation().toOSString();
		String diagramPath;
		String helper = location.substring(location.indexOf("prove"), location.length());
		int i = -1;
		while (Character.isJavaIdentifierPart(helper.charAt(++i)))
				;
		diagramPath = location.substring(0, location.length() - helper.length() + i).replaceAll("prove", "");
		return diagramPath + ".diagram";
	}
	
	private AbstractStatement findRefinement(EObject refinement, String refinementName, List<EObject> visited) {
		updateVisited(visited, refinement);
		if (getRefinementName(refinement, visited).equals(refinementName)) {
			return (AbstractStatement)refinement;
		}
		for (EObject c : refinement.eContents()) {
			EObject found = null;
			if ((found = findRefinement(c, refinementName, visited)) != null) {
				return (AbstractStatement)found;
			}
		}
		return null;
	}
	
	private void updateVisited(List<EObject> visited, EObject refinement) {
		if (!visited.contains(refinement)) { 
			visited.add(refinement);
		}
	}
	
	private String getRefinementName(EObject refinement, List<EObject> visited) {
		String refinementName = refinement.eClass().getName().equals("AbstractStatement") ? "Statement" : refinement.eClass().getName();
		int counter = 0;
		for (EObject s : visited) {
			var curName = s.eClass().getName().equals("AbstractStatement") ? "Statement" : s.eClass().getName();
			if (curName.equals(refinementName)) {
				counter++;
			}
			if (s == refinement) {
				return refinementName + counter;
			}
		}
		return refinementName + counter + 1;
	}
	
	private String parseProblem() {
		String problem = fileContent.substring(fileContent.indexOf("\\javaSource"), fileContent.length());
		problem = problem.substring(0, problem.indexOf("\\proof")).trim();
		return problem;
	}
}
