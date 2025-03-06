package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;

public class ExtractMethodStubsFeature extends MyAbstractAsynchronousCustomFeature {

	public ExtractMethodStubsFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Generate Method Stubs";
	}

	@Override
	public String getDescription() {
		return "Generates the method stubs from the diagram.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
		JavaVariables vars = extractor.getVars();
		Renaming renaming = extractor.getRenaming();
		CbCFormula formula = extractor.getFormula();
		URI uri = getDiagram().eResource().getURI();
		String location = FileUtil.getProjectLocation(uri) + "/code-gen/MethodStubs.java";
		String code = ConstructCodeBlock.constructMethodStubsForExport(formula, renaming, vars);
		writeFile(location, code);
	}

	private void writeFile(String location, String code) {
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			}
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(code);
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(javaFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
