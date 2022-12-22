package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import helper.ClassUtil;

public class GenerateCodeForVariationalVerification extends MyAbstractAsynchronousCustomFeature{
	
	private IFileUtil fileHandler;
	private String[] config;
	public String predicatesPath = "";
	
	public GenerateCodeForVariationalVerification(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	protected void execute(ICustomContext context, IProgressMonitor monitor) {
				
	}	
	
	public void generate(IPath location, String callingFeature, String callingClass, String callingMethod, String[] config) {
		this.config = config;
		printConfigToConsole(config, false);
		deleteExistingClasses(location + "/src_gen/");
		writeFile(location + "/src_gen/" + callingClass + ".java", "public class " + callingClass + " {\n}");
		generateClasses(location + "/src_gen/", config, callingFeature, callingClass, callingMethod.toLowerCase());
		resolveRemainingExplicitOriginalInCondition(location + "/src_gen/");
	}
	
	public void printConfigToConsole(String[] conf, boolean proofBegin) {
		String output = "++++++++++ Next configuration: ";
		for (int j = 0; j < conf.length; j++) {
			if (j == conf.length - 1) {
				output += conf[j];
			} else {
				output += conf[j] + ", ";
			}
		}
		Console.println(output + " ++++++++++");
		if (proofBegin) Console.println("  Proof is not executed due to existing proof begin.");
		return;
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

	private void generateClasses(String classLocation, String[] config, String callingFeature, String callingClass, String callingMethod) {
		URI uri = getDiagram().eResource().getURI();
		IProject project = FileUtil.getProjectFromFileInProject(uri);
		List<IFile> classFiles = ClassUtil.getFilesOfType(project, ".cbcclass");
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
			String location = classLocation + className + ".java";
			String helperLocation = classLocation.replace("src_gen", "src") + className + "_helper.java";
			writeFile(location, "public class " + className + " {\n}");
			boolean alreadyInherited = false;
			for (String feature : config) {
				for (IFile cbcclassFile : classFiles) {
					if (cbcclassFile.getFullPath().toString().contains(className + ".cbcclass") && cbcclassFile.getFullPath().toString().contains("/features/" + feature)) {
						String cbcclassPath = project.getLocationURI().toString().substring(6);
						Resource resource = ClassUtil.getClassModelResource(cbcclassPath, className, cbcclassFile.getFullPath().segment(2));
						for (EObject obj : resource.getContents()) {
							if (obj instanceof ModelClass) {
								ModelClass mc = (ModelClass) obj;
								if (mc.getInheritsFrom() != null && !alreadyInherited) {
									writeFile(location, "public class " + className + " extends " + mc.getInheritsFrom().getName() + " {\n}");
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
			boolean handledCallingMethod = false;
			for (String feature : config) {
				for (Diagram dia : getDiagrams()) {
					URI diagramUri = dia.eResource().getURI();
					if (diagramUri.segment(diagramUri.segmentCount() - 3).equals(feature)
							&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
							&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z0-9]*")) {
						if (!dia.getName().equalsIgnoreCase(callingMethod) || !handledCallingMethod) {
							String pattern = ".* " + dia.getName() + "\\(.*\\) \\{.*";
							boolean addedToList = false;
							String oldVersionOfMethod = "";
							String newVersionOfMethod = generateMethodForClass(dia, dia.getName()) + "\n";
							for (int i = methods.size() - 1; i >= 0; i--) {
								String method = methods.get(i);
								if (!addedToList && method.replace("\n", "").replace("\t", "").matches(pattern)) {
									oldVersionOfMethod = method;
									methods.remove(method);
									if (newVersionOfMethod.contains("original(") || newVersionOfMethod.contains("original_" + dia.getName())) {
										for (int j = 0; j < methods.size(); j++) {
											String temp = methods.get(j).replace("original_" + dia.getName() + "(", "original_original_" + dia.getName() + "(");
											methods.remove(j);
											methods.add(j, temp);
										}
										oldVersionOfMethod = oldVersionOfMethod.replace("original_" + dia.getName() + "(", "original_original_" + dia.getName() + "(");
										oldVersionOfMethod = oldVersionOfMethod.replace(" " + dia.getName() + "(", " original_" + dia.getName() + "(");
										newVersionOfMethod = newVersionOfMethod.replace("original(", "original_" + dia.getName() + "(");
										if (diagramUri.segment(diagramUri.segmentCount() - 3).equals(callingFeature) 
												&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(callingClass) 
												&& diagramUri.segment(diagramUri.segmentCount() - 1).equals(callingMethod + ".diagram")) {
											handledCallingMethod = true;
										}
									}
									addedToList = true;
								}
							}
							if (!oldVersionOfMethod.equals("")) methods.add(oldVersionOfMethod);
							methods.add(newVersionOfMethod);
						}
					}
				}
			}
			String methodCode = "";
			for (String otherMethod : methods) {
				methodCode += "\n" + otherMethod;
			}
			String helperCode = "";
			File javaFile = new File(helperLocation);
			File dir = new File(helperLocation.substring(0, helperLocation.lastIndexOf("/")));
			if (!javaFile.exists()) {
				dir.mkdir();
			}
			String helperClassName = helperLocation.split("/")[helperLocation.split("/").length - 1].replace(".java", "");
			fileHandler = new FileUtil(uri.toPlatformString(true));
			File file = fileHandler.getClassFile(helperClassName);
			if (file != null) {
				helperCode = "\n// Code from " + helperLocation + "\n";
				List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
				int i = 1;
				while (!lines.get(i).contains("//begin")) {
					i++;
				}
				for (int j = i + 1; j < lines.size()-1; j++) {
					helperCode = helperCode + lines.get(j) + "\n";
				}		
				helperCode = helperCode + "// End of code from " + helperLocation + "\n";
			}			
			writeFile(location, codeFields + "\n" + codeInvariants + "\n" + methodCode + helperCode);
		}
	}
	
	private String generateMethodForClass(Diagram diagram, String methodName) {
		diagram.eResource().getAllContents();
		JavaVariables vars = null;
		Renaming renaming = null;
		CbCFormula formula = null;
		GlobalConditions globalConditions = null;
		for (Shape shape : diagram.getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
				predicatesPath = formula.eResource().getURI().toString();
			} else if (obj instanceof GlobalConditions) {
				globalConditions = (GlobalConditions) obj;
			}
		}

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
		return ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString, config);
	}

	private void resolveRemainingExplicitOriginalInCondition(String path) {
		File dir = new File(path);
		String[] filesInDir = dir.list();
		for (String file : filesInDir) {
			File currentFile = new File(dir.getPath(), file);
			List<String> lines = fileHandler.readFileInList(currentFile.getAbsolutePath());
			String content = "";
			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				if (line.contains("@") && !line.contains("invariant")) {
					List<String> method = new ArrayList<String>();
					method.add(line);
					method.add(lines.get(++i));
					
					int depth = 0;
					if ((lines.get(++i).contains("original ") || lines.get(i).contains("original;") || lines.get(i).contains("\\original_pre") || lines.get(i).contains("\\original_post")) && lines.get(i).contains("@ requires")) {
						String temp = lines.get(i);
						String[] splittedSignatureLine = lines.get(i + 4).split("\\(")[0].split(" ");
						String methodName = splittedSignatureLine[splittedSignatureLine.length - 1];
						while (temp.contains("original ") || temp.contains("original;")  || temp.contains("\\original_pre") || temp.contains("\\original_post")) {
							depth++;
							for (int j = 0; j < lines.size(); j++) {
								String originalMethod = methodName;
								for (int k = 0; k < depth; k++) originalMethod = "original_" + originalMethod; 
								if (lines.get(j).contains(" " + originalMethod) && lines.get(j).contains("{")) {
									if (temp.contains("\\original_pre")) {
										String newCondition = lines.get(j - 4).replace("\t", "").replace("@ requires ", "").trim().replace("\n", "");
										temp = temp.replace("\\original_pre", newCondition.substring(0, newCondition.length() - 1));
									} else if (temp.contains("\\original_post")){
										String newCondition = lines.get(j - 3).replace("\t", "").replace("@ ensures ", "").trim().replace("\n", "");
										temp = temp.replace("\\original_post", newCondition.substring(0, newCondition.length() - 1));
									} else {
										String newCondition = lines.get(j - 4).replace("\t", "").replace("@ requires ", "").trim().replace("\n", "");
										temp = temp.replace("original", newCondition.substring(0, newCondition.length() - 1));
									}
								}
							}
						}
						method.add(temp);
					} else {
						method.add(lines.get(i));
					}
					depth = 0;
					if ((lines.get(++i).contains("original ") || lines.get(i).contains("original;") || lines.get(i).contains("\\original_pre") || lines.get(i).contains("\\original_post")) && lines.get(i).contains("@ ensures")) {
						String temp = lines.get(i);
						String[] splittedSignatureLine = lines.get(i + 3).split("\\(")[0].split(" ");
						String methodName = splittedSignatureLine[splittedSignatureLine.length - 1];
						while (temp.contains("original ") || temp.contains("original;") || temp.contains("\\original_pre") || temp.contains("\\original_post")) {
							depth++;
							for (int j = 0; j < lines.size(); j++) {
								String originalMethod = methodName;
								for (int k = 0; k < depth; k++) originalMethod = "original_" + originalMethod; 
								if (lines.get(j).contains(" " + originalMethod) && lines.get(j).contains("{")) {
									if (temp.contains("\\original_pre")) {
										String newCondition = lines.get(j - 4).replace("\t", "").replace("@ requires ", "").trim().replace("\n", "");
										temp = temp.replace("\\original_pre", newCondition.substring(0, newCondition.length() - 1));
									} else if (temp.contains("\\original_post")){
										String newCondition = lines.get(j - 3).replace("\t", "").replace("@ ensures ", "").trim().replace("\n", "");
										temp = temp.replace("\\original_post", newCondition.substring(0, newCondition.length() - 1));
									} else {
										String newCondition = lines.get(j - 4).replace("\t", "").replace("@ requires ", "").trim().replace("\n", "");
										temp = temp.replace("original", newCondition.substring(0, newCondition.length() - 1));
									}
								}
							}
						}
						method.add(temp);
					} else {
						method.add(lines.get(i));
					}
					for (String methodLine : method) {
						content = content + "\n" + methodLine;
					}
					content = content + "\n" + lines.get(++i) + "\n" + lines.get(++i);
				} else {
					content = content + "\n" + line;
				}
			}
			writeFile(path + file, content.substring(1));
		}
	}
	
	public void writeFile(String location, String code) {
		File javaFile = new File(location);
		File dir = new File(location.substring(0, location.lastIndexOf("/")));

		try {
			if (!javaFile.exists()) {
				dir.mkdir();
			}
			String content = code;
			if (!code.contains("public class")) {
				String className = location.split("/")[location.split("/").length - 1].replace(".java", "");
				URI uri = getDiagram().eResource().getURI();
				fileHandler = new FileUtil(uri.toPlatformString(true));
				File file = fileHandler.getClassFile(className);
				if (file == null) {
					file = fileHandler.getClassFile(className);
				}
				List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
				content = lines.get(0);
				int counter = 1;
				while (lines.get(counter).contains(";")) {
					content += "\n" + lines.get(counter++);
				}
				content += "\n\n" + code;
				for (int i = 1; i < lines.size(); i++) {
					String line = lines.get(i) + "\n";
					content += line;
				}
			}
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

	private Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = GetDiagramUtil.getDiagrams(project);
			}
		}
		return result;
	}	
}