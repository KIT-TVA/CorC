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
import org.eclipse.graphiti.mm.pictograms.Diagram;

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
	protected URI classUri;
	
	public Mutator(List<String> operators) {
		this.operators = new String[operators.size()];
		this.mutationCount = 0;
		for (int i = 0; i < operators.size(); i++) {
			this.operators[i] = operators.get(i);
		}
	}
	
	public void mutate(Diagram diagram) throws Exception {}
	public void generateDiagrams() throws Exception {}

	protected void setup() {
		mutationFolder = FileHandler.getInstance().getFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
		FileHandler.getInstance().deleteFolder(mutationFolder);
		FileHandler.getInstance().createFolder(originalDiagram.eResource().getURI(), FOLDER_NAME);
	}

	protected File generateFile(String location, String name, String code) throws IOException {
		location = location + File.separator + "src" + File.separator + name + ".java";
		File f = new File(location);
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		fw.write(code);
		fw.close();
		FileUtils.copyFile(f, new File(MutationSystem.CLASS_PATH + File.separator + f.getName()));
		return f;
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
}
