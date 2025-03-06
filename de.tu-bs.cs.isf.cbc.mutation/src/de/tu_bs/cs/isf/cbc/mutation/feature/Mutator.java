package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
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
import de.tu_bs.cs.isf.cbc.mutation.op.cbc.CbCMutationOp;
import de.tu_bs.cs.isf.cbc.mutation.op.cbc.NOOP;
import de.tu_bs.cs.isf.cbc.mutation.util.CodeRepresentationAssigner;
import de.tu_bs.cs.isf.cbc.mutation.util.FileReader;
import de.tu_bs.cs.isf.cbc.mutation.util.JavaDirectoryLoader;
import de.tu_bs.cs.isf.cbc.mutation.util.MutationInformation;
import de.tu_bs.cs.isf.cbc.mutation.util.MutationInformationTripel;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import src.mujava.MutationSystem;

public abstract class Mutator {
	private static final String PW_RENAME = "Pre Boundaries";
	private static final String PS_RENAME = "Post Boundaries";
	private static final String FOLDER_NAME = "mutations";

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
	protected List<MutationInformationTripel> mutantInfos;

	public static Mutator get(List<String> operators) {
		if (operators.stream().anyMatch(o -> !(CbCMutationOp.get(o) instanceof NOOP))) {
			return new CbCMutator(operators);
		} else {
			return new StatementMutator(operators);
		}
	}

	public static String getPwRename() {
		return PW_RENAME;
	}

	public static String getPsRename() {
		return PS_RENAME;
	}

	public static String getFolderName() {
		return FOLDER_NAME;
	}

	public List<MutationInformationTripel> getMutationInfos() {
		return this.mutantInfos;
	}

	public IFolder getMutationFolder() {
		return this.mutationFolder;
	}

	public Diagram getOriginalDiagram() {
		return originalDiagram;
	}

	public List<Diagram> getMutantDiagrams() {
		return mutantDiagrams;
	}

	public boolean hasClass() {
		return hasClass;
	}

	public void mutate(Diagram diagram, Condition condition) throws Exception {
	}

	protected void saveMutationInformation() {
		try {
			MutationInformation mi = new MutationInformation(this);
			mi.save();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected void generateDiagrams() throws Exception {
	}

	protected Mutator(List<String> operators) {
		this.mutantInfos = new ArrayList<MutationInformationTripel>();
		this.setMutantDiagrams(new ArrayList<>());
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

	protected void setup(Diagram diagram) throws CoreException, FileHandlerException {
		CodeRepresentationAssigner crAssigner = new CodeRepresentationAssigner();
		crAssigner.assignAll(diagram);
		this.setOriginalDiagram(diagram);
		this.mutantBaseName = this.getOriginalDiagram().getName() + "Mutant";
		classUri = getOriginalDiagram().eResource().getURI();
		className = FeatureUtil.getInstance().getCallingClass(classUri);
		className = className.isEmpty() ? "NoClass" : className;
		deleteMutationFiles();
		mutationFolder = getClassFolder(getOriginalDiagram()).getFolder(FOLDER_NAME);
		FileHandler.instance.deleteFolder(mutationFolder);
		mutationFolder.create(true, true, null);
		mutationFolder.refreshLocal(2, null);
	}

	protected File[] getMutants() {
		JavaDirectoryLoader loader = new JavaDirectoryLoader();
		File[] mutantFiles = loader.loadData(MutationSystem.MUTANT_HOME);
		return mutantFiles;
	}

	protected List<String> getMutantCodes() throws FileNotFoundException, CoreException {
		var mutantCodes = new ArrayList<String>();
		var mutants = getMutants();
		for (var mutant : mutants) {
			mutantCodes.add(new FileReader(mutant).readLines());
		}
		cleanUp();
		return mutantCodes;
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

	private void setHasClass(boolean hasClass) {
		this.hasClass = hasClass;
	}

	private void setMutantDiagrams(List<Diagram> mutantDiagrams) {
		this.mutantDiagrams = mutantDiagrams;
	}

	private void setOriginalDiagram(Diagram diagram) {
		this.originalDiagram = diagram;
	}

	private String mergeElseIfs(String code) {
		String[] lines = code.split("\\n");
		var out = new ArrayList<String>();
		for (int i = 0; i < lines.length - 1; i++) {
			if (lines[i].contains("else {") && lines[i + 1].contains("if")) {
				out.add(lines[i].substring(0, lines[i].length() - 1) + lines[i + 1].stripLeading());
				i++;
				while (!lines[++i].contains("} ")) {
					out.add(lines[i]);
				}
			} else {
				out.add(lines[i]);
			}
		}
		code = out.stream().collect(Collectors.joining("\n"));
		return code;

	}

	protected void cleanUp() throws CoreException {
		FileHandler.instance.deleteFolder(classUri, FOLDER_NAME);
		this.mutationFolder.refreshLocal(2, null);
	}

	protected CbCFormula getFormulaFrom(Resource resource) {
		CbCFormula formula = null;
		for (EObject o : resource.getContents()) {
			if (o instanceof CbCFormula) {
				formula = (CbCFormula) o;
				break;
			}
		}
		return formula;
	}

	protected void unproveEverything(EObject cur) {
		if (cur instanceof AbstractStatement) {
			((AbstractStatement) cur).setProven(false);
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
		boolean rootFolderSeen = false;
		URI diagUri = diagram.eResource().getURI();
		IProject project = FileUtil.getProject(diagUri);
		ModelClass clazz = FileHandler.instance.getClassOf(diagram);
		String folderPath = "";
		if (clazz == null) {
			folderPath = diagUri.trimSegments(1).toPlatformString(true);
			folderPath = folderPath.substring(folderPath.indexOf("/") + 1, folderPath.length());
			folderPath = folderPath.substring(folderPath.indexOf("/") + 1, folderPath.length());
			setHasClass(false);
		} else {
			for (int i = clazz.eResource().getURI().segmentCount() - 2; i >= 0; i--) {
				if (clazz.eResource().getURI().segment(i).equals("src"))
					rootFolderSeen = true;
				if (clazz.eResource().getURI().segment(i).equals("features"))
					rootFolderSeen = true;
				if (rootFolderSeen && clazz.eResource().getURI().segment(i).equals(project.getName())) {
					break;
				}
				folderPath = "/" + clazz.eResource().getURI().segment(i) + folderPath;
			}
			setHasClass(true);
		}
		return project.getFolder(folderPath);
	}

}
