package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.exceptions.FileHandlerException;
import de.tu_bs.cs.isf.cbc.mutation.util.FileReader;
import de.tu_bs.cs.isf.cbc.mutation.util.JavaDirectoryLoader;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import src.mujava.MutationSystem;

public abstract class Mutator {
	public static String PW_RENAME = "Pre Boundaries";
	public static String PS_RENAME = "Post Boundaries";
	public static String FOLDER_NAME = "mutations";

	protected String[] operators;
	protected int mutationCount;
	private Diagram originalDiagram;
	protected IFolder mutationFolder;
	protected String[] mutants; 
	private List<Diagram> mutantDiagrams;
	protected URI classUri;
	protected String mutantBaseName;
	protected String className;
	private boolean hasClass;
	
	public Mutator(List<String> operators) {
		this.setMutantDiagrams(new ArrayList<Diagram>());
		this.operators = new String[operators.size()];
		this.mutationCount = 0;
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).equals(PW_RENAME)) {
				this.operators[i] = "PW";
			} else if (operators.get(i).equals(PS_RENAME)) {
				this.operators[i] = "PS";
			} else {
				this.operators[i] = operators.get(i);
			}
		}
	}
	
	public IFolder getMutationFolder() {
		return this.mutationFolder;
	}

	public Diagram getOriginalDiagram() {
		return originalDiagram;
	}
	
	private void setOriginalDiagram(Diagram diagram) {
		this.originalDiagram = diagram;
	}

	public List<Diagram> getMutantDiagrams() {
		return mutantDiagrams;
	}

	private void setMutantDiagrams(List<Diagram> mutantDiagrams) {
		this.mutantDiagrams = mutantDiagrams;
	}

	public boolean hasClass() {
		return hasClass;
	}

	private void setHasClass(boolean hasClass) {
		this.hasClass = hasClass;
	}

	
	public void mutate(Diagram diagram, Condition condition) throws Exception {}
	protected void generateDiagrams() throws Exception {}

	protected void setup(Diagram diagram) throws CoreException, FileHandlerException {
		this.setOriginalDiagram(diagram);
		this.mutantBaseName = this.getOriginalDiagram().getName() + "Mutant";
		classUri = getOriginalDiagram().eResource().getURI();
		className = FeatureUtil.getInstance().getCallingClass(classUri);
		className = className.isEmpty() ? "NoClass" : className;
		deleteMutationFiles();
		mutationFolder = getClassFolder(getOriginalDiagram()).getFolder(FOLDER_NAME);
		//mutationFolder = FileHandler.getInstance().getFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
		FileHandler.getInstance().deleteFolder(mutationFolder);
		//FileHandler.getInstance().createFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
		mutationFolder.create(true, true, null);
		mutationFolder.refreshLocal(2, null);
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
			codes[i] = mergeElseIfs(new FileReader(f).readLines());
			i++;
		}
		return codes;
	}
	
	private String mergeElseIfs(String code) {
		String[] lines = code.split("\\n");
		String[] out = new String[lines.length];
		int offset = 0;
		for (int i = 0; i < lines.length - 1; i++) {
			if (lines[i].contains("else {") && lines[i+1].contains("if")) {
				out[i] = lines[i].substring(0, lines[i].length() - 1) + lines[i+1].stripLeading();
				i++;
				while (!lines[++i].contains("}")) {
					out[i-1] = lines[i];
				}
				offset -= 2;
			} else {
				out[i+offset] = lines[i];
			}
		}
		code = Arrays.stream(out).filter(l -> l != null).collect(Collectors.joining("\n"));
		return code;
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

	protected void unproveEverything(EObject cur) {
		if (cur instanceof AbstractStatement) {
			((AbstractStatement)cur).setProven(false);
		}
		for (EObject child : cur.eContents()) {
			unproveEverything(child);
		}
	}
	
	private void deleteMutationFiles() throws CoreException {
		List<IFile> mutantFiles = FileUtil.getFilesContainingString(this.mutantBaseName, FileUtil.getProject(classUri));
		for (IFile mutantFile : mutantFiles) {
			mutantFile.delete(true, null);
		}
	}

	private IFolder getClassFolder(Diagram diagram) throws FileHandlerException {
		URI diagUri = diagram.eResource().getURI();
		IProject project = FileUtil.getProject(diagUri);
		ModelClass clazz = FileHandler.getInstance().getClassOf(diagram);
		String folderPath = ""; 
		if (clazz == null) {
			folderPath = diagUri.trimSegments(1).toPlatformString(true);
			folderPath = folderPath.substring(folderPath.indexOf("/") + 1, folderPath.length());
			folderPath = folderPath.substring(folderPath.indexOf("/") + 1, folderPath.length());
			setHasClass(false);
		} else {
			for (int i = clazz.eResource().getURI().segmentCount() - 2; i >= 0; i--) {
				if (clazz.eResource().getURI().segment(i).equals(project.getName())) {
					//folderPath = "/" + clazz.eResource().getURI().segment(i-1) + folderPath;
					break;
				}
				folderPath = "/" + clazz.eResource().getURI().segment(i) + folderPath;
			}
			setHasClass(true);
		}
		return project.getFolder(folderPath);
	}

}
