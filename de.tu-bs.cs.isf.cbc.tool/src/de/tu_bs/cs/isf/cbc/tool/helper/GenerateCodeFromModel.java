package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

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
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class GenerateCodeFromModel extends MyAbstractAsynchronousCustomFeature {

	public GenerateCodeFromModel(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Generate Code";
	}

	@Override
	public String getDescription() {
		return "Generates the code from the diagram.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		JavaVariables vars = null;
		Renaming renaming = null;
		CbCFormula formula = null;
		GlobalConditions globalConditions = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof GlobalConditions) {
				globalConditions = (GlobalConditions) obj;
			}
		} 
		
		String signatureString = formula.getMethodObj() != null ? formula.getMethodObj().getSignature() : ("public void " + formula.getName().toLowerCase() + " ()");
		
		JavaVariable returnVariable = null;
		int counter = 0;
		LinkedList<String> localVariables = new LinkedList<String>();
		for(int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
		if(currentVariable.getKind() == VariableKind.RETURN) {
			counter++;
			if(!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split(" ")[0])) {
				Console.println("Method return type and variable type does not match.");
				return;
			}
			if(counter > 1) {
				Console.println("Too much variables of kind RETURN.");
				return;
			}
			returnVariable = currentVariable;
		}else if(currentVariable.getKind() == VariableKind.LOCAL) {
			localVariables.add(currentVariable.getName().replace("non-null", ""));
		}
		}
		String globalVariables ="";
		for (Field field : vars.getFields()) {
			globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " /*@spec_public@*/ " + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");
		}
		
		URI uri = getDiagram().eResource().getURI();
		String location = FileUtil.getProjectLocation(uri);
		for(int i = 2; i < uri.segments().length - 2; i++) {
			location += File.separator + uri.segment(i);
		}
		location += File.separator + (formula.getClassName().equals("") ? ("Class" + formula.getName()) : formula.getClassName()) + ".java";
		String code = ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString, new String[0]);
		writeFile(location, code, formula.getMethodObj() != null ? formula.getMethodObj().getParentClass().getPackage() : "", formula.getClassName().equals("") ? ("Class" + formula.getName()) : formula.getClassName(), signatureString, globalVariables);
	}

	private void writeFile(String location, String code, String packageName, String className,  String signature, String globalVariables) {
		StringBuffer newCode = new StringBuffer();
		newCode.setLength(0);
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			} else {
				newCode = ConstructCodeBlock.editCodeBlockForExport(code, javaFile, signature, globalVariables);
			}
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			if(newCode.length() == 0) {
				if (packageName != null && !packageName.isEmpty()) {
					bw.write("package " + packageName + "\n\n");
				}
				if(globalVariables.isEmpty())
					bw.write("public class " + className + " {\n\n" + code + "\n}"); 
				else {
					bw.write("public class " + className + " {\n\n" + globalVariables + "\n" + code + "\n}");
				}
			}
			else {
				newCode.append("\n}");
				bw.write(newCode.toString());
			}
			
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