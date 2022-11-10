package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import helper.ClassUtil;

public class GenerateProductVariantHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			List<IFile> fileList = strucSelection.toList();
			if (fileList.size() != 1) {
				throw new ExecutionException("Select only one File.");
			}
			IFile iFile = fileList.get(0);
			if (!iFile.getFileExtension().equals("xml")) {
				throw new ExecutionException("Select .xml file of feature configuration.");
			}
			
			String dirPath = iFile.getLocation().toString();
			dirPath = dirPath.substring(0, dirPath.indexOf("/configs/"));
			dirPath += "/products-gen/" + iFile.getName().replace("." + iFile.getFileExtension(), "");
			deleteExistingClasses(dirPath);
			
			FileUtil fileHandler = new FileUtil("");			
			List<String> unorderedConfig = new ArrayList<String>();
			for (String line : fileHandler.readFileInList(iFile.getLocation().toString())) {
				if (line.contains("\"selected\"")) {
					String feature = line.split(" name=\"")[1].replace("\"/>", "");
					unorderedConfig.add(feature);
				}
			}

			IFeatureModel featModel = FeatureModelManager.load(Paths.get(iFile.getLocation().toString().substring(0, iFile.getLocation().toString().indexOf("/configs")) + "/model.xml"));
			List<String> config = featModel.getFeatureOrderList();
			for (int i = 0; i < config.size();) {	
				String feature = config.get(i);
				if (!unorderedConfig.contains(feature)) {
					config.remove(feature);
				} else {
					i++;
				}
			}
			
			String[] path = iFile.getLocation().toString().split("/");
			IProject project = FileUtil.getProjectLocationS("/" + path[path.length-3] + "/" + path[path.length-2] + "/" + path[path.length-1]);
			List<IFile> classFiles = ClassUtil.getFilesOfType(project, ".cbcclass");
			List<IFile> modelFiles = ClassUtil.getFilesOfType(project, ".cbcmodel");
			List<String> otherClasses = new ArrayList<String>();
			for (IFile cbcclassFile : classFiles) {
				if (cbcclassFile.getFullPath().toString().contains(".cbcclass")) {
					String[] classNameParts = cbcclassFile.getFullPath().toString().split("/");
					String className = classNameParts[classNameParts.length - 1].replace(".cbcclass", "");
					for (String feature : config) {
						if (cbcclassFile.getFullPath().toString().endsWith("/" + feature + "/" + className + "/" + className + ".cbcclass") && !otherClasses.contains(className)) {
							otherClasses.add(className);
						}
					}
				}
			}
			
			for (String className : otherClasses) {
				String codeFields = "";
				String codeInvariants = "";
				String location = dirPath + "/" + className + ".java";
				String helperLocation = iFile.getLocation().toString().replace("configs/" + path[path.length-1], "src/").replace("products-gen", "src") + className + "_helper.java";
				String classHeader = "public class " + className + " {\n";
				boolean alreadyInherited = false;
				for (int i = config.size()-1; i >= 0; i--) {
					String feature = config.get(i);
					for (IFile cbcclassFile : classFiles) {
						if (cbcclassFile.getFullPath().toString().contains(className + ".cbcclass") && cbcclassFile.getFullPath().toString().contains("/features/" + feature)) {
							String cbcclassPath = project.getLocationURI().toString().substring(6);
							Resource resource = ClassUtil.getClassModelResource(cbcclassPath, className, cbcclassFile.getFullPath().segment(2));
							for (EObject obj : resource.getContents()) {
								if (obj instanceof ModelClass) {
									ModelClass mc = (ModelClass) obj;
									if (mc.getInheritsFrom() != null && !alreadyInherited) {
										classHeader = "public class " + className + " extends " + mc.getInheritsFrom().getName() + " {\n";
										alreadyInherited = true;
									}
									for (Condition c : mc.getClassInvariants()) {
										String newInv = "/*@ invariant " + c.getName() + "; @*/";
										if (!codeInvariants.contains(newInv)) {
											codeInvariants += "    " + newInv + "\n";
										}
									}
									for (Field f : mc.getFields()) {
										String newField = f.getVisibility().toString().toLowerCase() + (f.getVisibility().equals(Visibility.PRIVATE) ? " /*@spec_public@*/ " : "") + (f.isIsStatic() ? " static " : " ") + f.getType() + " " + f.getName() + ";";
										if (!codeFields.contains(newField)) {
											codeFields += "    " + newField + "\n";
										} else {
											Console.println("ERROR: The field " + newField + " is defined in more than one implementation of " + className + ".");
										}
									}
								}
							}
						}
					}
				}

				List<String> methods = new ArrayList<String>();
				//for (int i = config.size()-1; i >= 0; i--) {
				for (int i = 0; i < config.size(); i++) {
					String feature = config.get(i);
					for (IFile methodFile : modelFiles) {
						if (methodFile.getFullPath().toString().contains("/features/" + feature + "/" + className)) {
							Resource resource = GetDiagramUtil.getResourceFromFile(methodFile, new ResourceSetImpl());
							String methodName = methodFile.getName().replace("." + methodFile.getFileExtension(), "");
							String pattern = ".* " + methodName + "\\(.*\\) \\{.*";
							boolean addedToList = false;
							String oldVersionOfMethod = "";
							String newVersionOfMethod = generateMethodForClass(resource, methodName) + "\n";
							for (int j = methods.size() - 1; j >= 0; j--) {
								String method = methods.get(j);
								if (!addedToList && method.replace("\n", "").replace("\t", "").matches(pattern)) {
									oldVersionOfMethod = method;
									methods.remove(method);
									if (newVersionOfMethod.contains("original(") || newVersionOfMethod.contains("original_" + methodName)) {
										for (int k = 0; k < methods.size(); k++) {
											String temp = methods.get(k).replace("original_" + methodName + "(", "original_original_" + methodName + "(");
											methods.remove(k);
											methods.add(k, temp);
										}
										oldVersionOfMethod = oldVersionOfMethod.replace("original_" + methodName + "(", "original_original_" + methodName + "(");
										oldVersionOfMethod = oldVersionOfMethod.replace(" " + methodName + "(", " original_" + methodName + "(");
										newVersionOfMethod = newVersionOfMethod.replace("original(", "original_" + methodName + "(");
										newVersionOfMethod = applyCompositionTechnqiue(newVersionOfMethod, oldVersionOfMethod, resource);
									}
									addedToList = true;
								}
							}
							if (!oldVersionOfMethod.equals("")) methods.add(oldVersionOfMethod);
							methods.add(newVersionOfMethod);
						}
					}
				}
				
				String methodCode = "";
				for (String otherMethod : methods) {
					methodCode += "\n" + otherMethod;
				}
				String helperCode = "";
				File javaFile = new File(helperLocation);
				if (javaFile.exists()) {
					if (javaFile != null) {
						helperCode = "\n// Code from " + helperLocation + "\n";
						List<String> lines = fileHandler.readFileInList(javaFile.getAbsolutePath());
						int i = 1;
						while (!lines.get(i).contains("//begin")) {
							i++;
						}
						for (int j = i + 1; j < lines.size()-1; j++) {
							helperCode = helperCode + lines.get(j) + "\n";
						}		
						helperCode = helperCode + "// End of code from " + helperLocation + "\n";
					}
				}		
				writeFile(location, classHeader + codeFields + "\n" + codeInvariants + "\n" + methodCode + helperCode + "}");
			}
		}
		return null;
	}	
	
	private String applyCompositionTechnqiue(String newVersionOfMethod, String oldVersionOfMethod, Resource resource) {
		CompositionTechnique ct= ((CbCFormula) resource.getContents().get(0)).getCompositionTechnique();
		String[] oldParts = oldVersionOfMethod.split("\n");
		String[] newParts = newVersionOfMethod.split("\n");
		newParts[2] = applyCompositionTechnique(oldParts[2], newParts[2], ct);
		newParts[3] = applyCompositionTechnique(oldParts[3], newParts[3], ct);
		newVersionOfMethod = "";
		for (String s : newParts) {
			newVersionOfMethod += s + "\n";
		}
		return newVersionOfMethod;
	}

	private String applyCompositionTechnique(String oldCond, String newCond, CompositionTechnique ct) {
		switch (ct) {
		case CONTRACT_OVERRIDING:
			return newCond;
		case CONJUNCTIVE_CONTRACTING:
			if (newCond.contains("@ ensures ")) {
				return newCond.substring(0, newCond.length()-2) + " & " + oldCond.substring(0, oldCond.length()-2).replace("\t@ ensures", "") + ";";
			} else {
				return newCond.substring(0, newCond.length()-2) + " & " + oldCond.substring(0, oldCond.length()-2).replace("\t@ requires", "") + ";";
			}
		case EXPLICIT_CONTRACTING:
			Pattern pattern = Pattern.compile("original");
			Matcher matcher = pattern.matcher(newCond);
			return matcher.replaceAll(Matcher.quoteReplacement(oldCond.substring(0, oldCond.length()-2).replace("\t@ requires", "")));
		}
		return "";
	}

	private void deleteExistingClasses(String classDirectory) {
		File dir = new File(classDirectory);
		String[] filesInDir = dir.list();
		if (filesInDir != null) {
			for (String s : filesInDir) {
				File currentFile = new File(dir.getPath(), s);
				currentFile.delete();
			}
		}
	}
	
	public void writeFile(String location, String code) {
		File javaFile = new File(location);
		File dir = new File(location.substring(0, location.lastIndexOf("/")));

		try {
			if (!javaFile.exists()) {
				dir.mkdirs();
			}
			String content = code;
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
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
	
	private String generateMethodForClass(Resource res, String methodName) {
		JavaVariables vars = (JavaVariables) res.getContents().get(1);
		Renaming renaming = null;
		CbCFormula formula = (CbCFormula) res.getContents().get(0);
		GlobalConditions globalConditions = null;

		String signatureString = formula.getMethodObj().getSignature().replaceFirst(formula.getMethodObj().getName(), methodName);
		JavaVariable returnVariable = null;
		int counter = 0;
		LinkedList<String> localVariables = new LinkedList<String>();
		for (int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
			if (currentVariable.getKind() == VariableKind.RETURN || currentVariable.getKind() == VariableKind.RETURNPARAM) {
				counter++;
				if (!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split(" ")[0])) {
					Console.println("Method return type and variable type does not match.");
					return "";
				}
				if (counter > 1) {
					Console.println("Too much variables of kind RETURN.");
					return "";
				}
				returnVariable = currentVariable;
			} else if (currentVariable.getKind() == VariableKind.LOCAL) {
				localVariables.add(currentVariable.getName().replace("non-null", ""));
			}
		}
		for (Parameter param : vars.getParams()) {
			if (param.getName().equals("ret")) {
				returnVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				returnVariable.setKind(VariableKind.RETURNPARAM);
				returnVariable.setName(param.getType() + " " + param.getName());
			}
		}
		if (returnVariable != null) {
			localVariables.add(returnVariable.getName().replace("non-null", ""));
		}
		globalConditions = null;
		return ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString);
	}
}