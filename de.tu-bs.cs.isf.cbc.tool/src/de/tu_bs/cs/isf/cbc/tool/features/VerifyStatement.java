package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import helper.ClassUtil;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyStatement extends MyAbstractAsynchronousCustomFeature {

	private IFileUtil fileHandler;

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement";
	}

	@Override
	public String getDescription() {
		return "Verifies a statement using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement || bo instanceof OriginalStatement
					|| bo instanceof MethodStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		verifyStatement(context, monitor, false);
	}

	void verifyStatement(ICustomContext context, IProgressMonitor monitor, boolean inlining) {
		long startTime = System.nanoTime();
		monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				boolean returnStatement = bo instanceof ReturnStatement;
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}

				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				try {
					if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}

				if (isVariational) {
					proven = executeVariationalVerification(project, uri, statement, vars, conds, renaming, formula, returnStatement, monitor);
				} else {
					proven = executeNormalVerification(statement, vars, conds, renaming, formula, returnStatement, monitor);
				}
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
		monitor.done();
	}

	private boolean executeNormalVerification(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		boolean proven = false;
		Console.println("--------------- Triggered verification ---------------");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			URI uri = getDiagram().eResource().getURI();
			String platformUri = uri.toPlatformString(true);
			String callingClass = uri.segment(uri.segmentCount() - 2) + "";
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, platformUri, formula, new FileUtil(platformUri), "");
			proven = prove.proveStatementWithKey(returnStatement, false, 0, callingClass, true);
		} else {
			Console.println("Statement is not in correct format.");
		}
		return proven;
	}

	private boolean executeVariationalVerification(IProject project, URI uri, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		boolean proven = false;
		String[][] featureConfigs = null;
		String[][] featureConfigsRelevant = null;
		String callingFeature = uri.segment(uri.segmentCount() - 3) + "";
		String callingClass = uri.segment(uri.segmentCount() - 2) + "";
		String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		String stmt = statement.getName();
		String varM = "";
		if (!stmt.contains("original(") && stmt.matches("/.*\\(.*\\).*/gm")) { // contains method call and not original
			varM = handleVarM(Parser.extractMethodNameFromStatemtent(stmt), callingClass, vars);
			if (varM.endsWith("."))	varM = "";
			featureConfigs = VerifyFeatures.verifyConfig(uri, varM, false, callingClass, false);
			featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, varM, false, callingClass, true);
		} else { // original or normal statement
			featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.trimFileExtension().segmentCount() - 1), true, callingClass, false);
			featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, uri.trimFileExtension().segment(uri.segmentCount() - 1), true, callingClass, true);
		}
		Console.println("--------------- Triggered variational verification ---------------");

		// do for found configurations
		String varMParts[] = varM.split("\\.");
		if (featureConfigs != null) {
			String[] variants = generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, varM.contains(".") ? varMParts[0] : callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					generate(project.getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
					String configName = "";
					for (String s : featureConfigs[i]) configName += s;
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri.toPlatformString(true), formula, new FileUtil(uri.toPlatformString(true)), configName);
					if (!varM.equals("")) {
						List<CbCFormula> refinements = generateCbCFormulasForRefinements(variants[i], varMParts[1].toLowerCase());
						List<JavaVariables> refinementsVars = generateJavaVariablesForRefinements(variants[i], varMParts[1].toLowerCase());
						proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, i, callingMethod, varM, callingClass, true);
					} else {
						List<CbCFormula> refinements = generateCbCFormulasForRefinements(variants[i], callingMethod);
						List<JavaVariables> refinementsVars = generateJavaVariablesForRefinements(variants[i], callingMethod);
						proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, i, callingMethod, varMParts[0], callingClass, true);
					}
				}
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}

	public void generate(IPath location, String callingFeature, String callingClass, String callingMethod, String[] config) {
		String output = "++++++++++ Next configuration: ";
		for (int j = 0; j < config.length; j++) {
			if (j == config.length - 1) {
				output += config[j];
			} else {
				output += config[j] + ", ";
			}
		}
		Console.println(output + " ++++++++++");
		deleteExistingClasses(location + "/src_gen/");
		writeFile(location + "/src_gen/" + callingClass + ".java", "public class " + callingClass + " {\n}");
		generateClasses(location + "/src_gen/", config, callingFeature, callingClass, callingMethod.toLowerCase());
		resolveRemainingExplicitOriginalInCondition(location + "/src_gen/");
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
		List<IFile> classFiles = ClassUtil.getAllCbCClassFiles(project);
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
			for (String feature : config) {
				for (IFile cbcclassFile : classFiles) {
					if (cbcclassFile.getFullPath().toString().contains(className + ".cbcclass") && cbcclassFile.getFullPath().toString().contains("/features/" + feature)) {
						String cbcclassPath = project.getLocationURI().toString().substring(6);
						Resource resource = ClassUtil.getClassModelResource(cbcclassPath, className, cbcclassFile.getFullPath().segment(2));
						for (EObject obj : resource.getContents()) {
							if (obj instanceof ModelClass) {
								ModelClass mc = (ModelClass) obj;
								for (Condition c : mc.getClassInvariants()) {
									String newInv = "/*@ invariant " + c.getName() + "; @*/";
									if (!codeInvariants.contains(newInv)) {
										codeInvariants += "    " + newInv + "\n";
									}
								}
								for (Field f : mc.getFields()) {
									String newField = f.getVisibility().toString().toLowerCase() + (f.getVisibility().equals(Visibility.PRIVATE) ? " /*@spec_public@*/ " : "") + (f.isIsStatic() ? " static " : " ") + f.getType() + " " + f.getName() + ";";
									if (!codeFields.contains(newField)) {
										codeFields += "    " + newField + " // " + resource.getURI().segment(8) + "\n";
									} else {
										Console.println("ERROR: The field " + newField + " is defined in more than one implementation of " + className + ".");
									}
								}
							}
						}
					}
				}
			}

			Collection<Diagram> diagrams = getDiagrams();
			List<String> methods = new ArrayList<String>();
			boolean handledCallingMethod = false;
			for (String feature : config) {
				for (Diagram dia : diagrams) {
					URI diagramUri = dia.eResource().getURI();
					if (diagramUri.segment(diagramUri.segmentCount() - 3).equals(feature)
							&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
							&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z]*")) {
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
				/*if (feature.equals("Interest") && className.equals("Account")) {
					methods.add("/*@\r\n"
							+ "	@ normal_behavior\r\n"
							+ "	@ requires true;\r\n"
							+ "	@ ensures (this.balance >= 0 ==> \\result >= 0) && (this.balance <= 0 ==> \\result <= 0);\r\n"
							+ "	@ assignable \\nothing;\r\n"
							+ "	@\r\n"
							+ "	public /*@helper pure@ int interestCalculate() {\r\n"
							+ "		int ret;\r\n"
							+ "		ret = this.balance * this.INTEREST_RATE / 36500;\r\n"
							+ "		return ret;\r\n"
							+ "\r\n"
							+ "	}");
				}*/
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
		if (returnVariable != null) {
			localVariables.add(returnVariable.getName().replace("non-null", ""));
		}
		globalConditions = null;
		return ConstructCodeBlock.constructCodeBlockForExport(formula, globalConditions, renaming, localVariables, returnVariable, signatureString);
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
					if (lines.get(++i).contains("original ") && lines.get(i).contains("@ requires")) {
						String temp = lines.get(i);
						String[] splittedSignatureLine = lines.get(i + 4).split("\\(")[0].split(" ");
						String methodName = splittedSignatureLine[splittedSignatureLine.length];
						for (int j = 0; j < lines.size(); j++) {
							if (lines.get(j).contains(" original_" + methodName) && lines.get(j).contains("\\{")) {
								String newCondition = lines.get(j - 4).replace("\t", "").replace("@ requires ", "").trim().replace("\n", "");
								temp = temp.replace("original", newCondition.substring(0, newCondition.length() - 1));
							}
						}
						method.add(temp);
					} else {
						method.add(lines.get(i));
					}
					if (lines.get(++i).contains("original ") && lines.get(i).contains("@ ensures")) {
						String temp = lines.get(i);
						String[] splittedSignatureLine = lines.get(i + 3).split("\\(")[0].split(" ");
						String methodName = splittedSignatureLine[splittedSignatureLine.length - 1];
						for (int j = 0; j < lines.size(); j++) {
							if (lines.get(j).contains(" original_" + methodName) && lines.get(j).contains("{")) {
								String newCondition = lines.get(j - 3).replace("\t", "").replace("@ ensures ", "").trim().replace("\n", "");
								temp = temp.replace("original", newCondition.substring(0, newCondition.length() - 1));
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

	public String handleVarM(String methodCall, String callingClass, JavaVariables vars) {
		if (!methodCall.contains(".")) { // Call without class reference
			return callingClass + "." + methodCall;
		} else if (methodCall.contains("this.")) { // Call with this reference
			return methodCall.replace("this.", "." + callingClass);
		} else if (Character.isUpperCase(methodCall.charAt(0))) { // Call with static class reference
			return methodCall;
		} else { // Call with reference to a variable/object
			String type = methodCall.split("\\.")[0];
			for (JavaVariable var : vars.getVariables()) {
				if (var.getName().split(" ")[1].equals(type)) {
					return methodCall.replace(type, var.getName().split(" ")[0]);
				}
			}
			for (Field f : vars.getFields()) {
				if (f.getName().equals(type)) {
					return methodCall.replace(type, f.getType());
				}
			}
		}
		return methodCall;
	}

	public List<CbCFormula> generateCbCFormulasForRefinements(String refs, String methodName) {
		List<CbCFormula> formulas = new ArrayList<CbCFormula>();
		if (refs == null) return formulas;
		String[] variants = refs.split(",");
		Collection<Diagram> diagrams = getDiagrams();
		for (String variant : variants) {
			boolean variantFound = false;
			String className = variant.split("\\.")[0];
			String featureName = variant.split("\\.")[1];
			for (Diagram dia : diagrams) {
				if (variantFound) break;
				URI diagramUri = dia.eResource().getURI();
				if (diagramUri.segment(diagramUri.segmentCount() - 3).equalsIgnoreCase(featureName)
						&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
						&& diagramUri.trimFileExtension().lastSegment().equals(methodName)
						&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z]*")) {
					for (Shape shape : dia.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof CbCFormula) {
							formulas.add((CbCFormula) obj);
							variantFound = true;
							break;
						}
					}
				}
			}
		}
		return formulas;
	}
	
	public List<JavaVariables> generateJavaVariablesForRefinements(String refs, String methodName) {
		List<JavaVariables> variables = new ArrayList<JavaVariables>();
		if (refs == null) return variables;
		String[] variants = refs.split(",");
		Collection<Diagram> diagrams = getDiagrams();
		for (String variant : variants) {
			boolean variantFound = false;
			String className = variant.split("\\.")[0];
			String featureName = variant.split("\\.")[1];
			for (Diagram dia : diagrams) {
				if (variantFound) break;
				URI diagramUri = dia.eResource().getURI();
				if (diagramUri.segment(diagramUri.segmentCount() - 3).equalsIgnoreCase(featureName)
						&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
						&& diagramUri.trimFileExtension().lastSegment().equals(methodName)
						&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z]*")) {
					for (Shape shape : dia.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							variables.add((JavaVariables) obj);
							variantFound = true;
							break;
						}
					}
				}
			}
		}
		return variables;
	}


	public String[] generateVariantsStringFromFeatureConfigs(String[][] featureConfigs, String callingFeature,	String className) {
		String[] variants = new String[featureConfigs.length];
		for (int i = 0; i < featureConfigs.length; i++) {
			for (int j = featureConfigs[i].length - 1; j >= 0; j--) {
				if (!featureConfigs[i][j].equals(callingFeature)) {
					if (variants[i] == null) {
						variants[i] = className + "." + featureConfigs[i][j].toLowerCase();
					} else {
						variants[i] += "," + className + "." + featureConfigs[i][j].toLowerCase();
					}
				}
			}
		}
		return variants;
	}

	public JavaVariables readFieldsFromClass(String location) {
		JavaVariables vars = CbcmodelFactory.eINSTANCE.createJavaVariables();

		String className = location.split("/")[location.split("/").length - 1].replace(".java", "");
		URI uri = getDiagram().eResource().getURI();
		fileHandler = new FileUtil(uri.toPlatformString(true));
		File file = fileHandler.getClassFile(className);
		List<String> lines = fileHandler.readFileInList(file.getAbsolutePath());
		int i = 2;
		while (lines.get(i).contains(";")) {
			String field = lines.get(i++).replace(";", "");
			Field f = CbcclassFactory.eINSTANCE.createField();
			String split[] = field.trim().split(" ");
			int pointer = 1;

			switch (split[0].toLowerCase()) {
			case "private":
				f.setVisibility(Visibility.PRIVATE);
				break;
			case "public":
				f.setVisibility(Visibility.PUBLIC);
				break;
			case "protected":
				f.setVisibility(Visibility.PROTECTED);
				break;
			case "package":
				f.setVisibility(Visibility.PACKAGE);
				break;
			default:
				f.setVisibility(Visibility.PUBLIC);
				pointer = 0;
				break;
			}

			if (Arrays.stream(split).anyMatch("static"::equalsIgnoreCase)) {
				f.setIsStatic(true);
				pointer++;
			} else
				f.setIsStatic(false);
			if (Arrays.stream(split).anyMatch("final"::equalsIgnoreCase)) {
				f.setIsFinal(true);
				pointer++;
			} else {
				f.setIsFinal(false);
			}
			f.setType(split[pointer++]);
			f.setName(split[pointer]);
			vars.getFields().add(f);
		}
		return vars;
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