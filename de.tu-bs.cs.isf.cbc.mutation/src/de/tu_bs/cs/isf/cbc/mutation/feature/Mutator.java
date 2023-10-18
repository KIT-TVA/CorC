package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.mutation.util.FileReader;
import de.tu_bs.cs.isf.cbc.mutation.util.JavaDirectoryLoader;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import src.mujava.MutationSystem;

public abstract class Mutator {
	public static String FOLDER_NAME = "mutations";
	protected String[] operators;
	protected int mutationCount;
	protected Diagram originalDiagram;
	protected IFolder mutationFolder;
	protected String[] mutants; 
	protected String[] mutantNames;
	protected URI classUri;
	
	public Mutator(List<String> operators) {
		this.operators = new String[operators.size()];
		this.mutationCount = 0;
		for (int i = 0; i < operators.size(); i++) {
			this.operators[i] = operators.get(i);
		}
	}
	
	public void mutate(Diagram diagram, Condition condition) throws Exception {}
	protected void generateDiagrams() throws Exception {}

	protected void setup(Diagram diagram) {
		this.originalDiagram = diagram;
		mutationFolder = FileHandler.getInstance().getFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
		FileHandler.getInstance().deleteFolder(mutationFolder);
		FileHandler.getInstance().createFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
	}


	protected File[] getMutants() {
		JavaDirectoryLoader loader = new JavaDirectoryLoader();
		File[] mutantFiles = loader.loadData(MutationSystem.MUTANT_HOME);
		return mutantFiles;
	}

	protected String[] readCode(File[] mutants) throws FileNotFoundException {
		String[] codes = new String[mutants.length];
		int i = 0;
		for (File f : mutants) {
			codes[i] = new FileReader(f).readLines();
			i++;
		}
		return codes;
	}

	protected void cleanUp() throws CoreException {
		FileHandler.getInstance().deleteFolder(classUri, FOLDER_NAME);
		this.mutationFolder.refreshLocal(2, null);
	}
	
	protected CbCFormula getFormulaFrom(Resource resource) {
		CbCFormula formula = null;
		for (EObject o : resource.getContents()) {
			if (o instanceof CbCFormula) {
				formula = (CbCFormula)o;
				break;
			}
		}
		return formula;
	}
}
