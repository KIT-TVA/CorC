package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

public class GenerateModelFromCode {
	// Content of Class
	private ArrayList<String> jmlLoopConditions = new ArrayList<String>();
	private EList<Condition> invariants = new BasicEList<Condition>();
	private EList<Field> fields = new BasicEList<Field>();
	private EList<Method> methods = new BasicEList<Method>();

	private int position = 0;

	// Stuff to change and create corc diagrams
	private Resource cbcclassResource;
	private List<Resource> cbcmodelResources = new ArrayList<Resource>();
	private ResourceSet rs = new ResourceSetImpl();
	private Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	private Map<String, Object> m = reg.getExtensionToFactoryMap();

	// Info of java file
	private String className;
	private IFolder folder;
	private String packageName;

	public GenerateModelFromCode() {
	}

	public void execute(IFile iFile) throws ExecutionException {

		ArrayList<String> jmlMethodConditions = new ArrayList<String>();

		String javaFileContent = readFileToString(iFile.getLocation().toPortableString());

		readJMLAnnotations(javaFileContent, jmlMethodConditions);

		CompilationUnit compilationUnit = StaticJavaParser.parse(javaFileContent);
		if (compilationUnit.getChildNodes().isEmpty()) {
			return;
		}

		ClassOrInterfaceCollector collector = new ClassOrInterfaceCollector();
		collector.visit(compilationUnit, null);

		/*
		 * if (compilationUnit.getNamespacesAsString() != null &&
		 * !compilationUnit.getNamespacesAsString().isEmpty()) { packageName =
		 * compilationUnit.getNamespacesAsString().substring(0,
		 * compilationUnit.getNamespacesAsString().length()-1); }
		 */

		setupProjectStructure(iFile);

		ModelClass modelClass = instantiateModelClass(null);
		modelClass.setJavaClassURI(URI.createFileURI(iFile.getProjectRelativePath().toPortableString()).toFileString());
		modelClass.setPackage(packageName);

		if (!collector.getClassOrInterfaceDeclaration().isInterface()) {
			for (FieldDeclaration fieldDeclaration : collector.getFields()) {
				addFieldToList(fieldDeclaration);
			}

			for (MethodDeclaration methodDeclaration : collector.getMethods()) {
				Method method = CbcclassFactory.eINSTANCE.createMethod();

				Resource cbcmodelResource = setupProjectForCbCModel(method, methodDeclaration.getNameAsString());

				JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
				fillVariableList(variables, methodDeclaration);

				// String signature = buildSignatureString(classMethod, variables);
				settingSignature(methodDeclaration, variables, method);

				// get global conditions from existing diagram
				GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
				for (EObject obj : cbcmodelResource.getContents()) {
					if (obj instanceof GlobalConditions) {
						conditions = (GlobalConditions) obj;
					}
				}

				CbCFormula formula = createFormula(methodDeclaration.getNameAsString());
				formula.setClassName(className);
				formula.setMethodName(method.getName());
				method.setCbcStartTriple(formula);
				formula.setMethodObj(method);
				variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), fields);

				// parse JML contract to pre- and postconditions of cbcFormula
				String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
						+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \\nothing;\r\n"
						+ "	  @*/";
				String jmlAnnotation;
				Optional<Comment> comment = methodDeclaration.getComment();
				if (!comment.isPresent()) {
					jmlAnnotation = defaultAnnotation;
				} else {
					jmlAnnotation = comment.get().toString();
					if (!jmlAnnotation.contains("/*@")) {
						jmlAnnotation = defaultAnnotation;
					}
				}
				int index = 0;

				do {
					String currentJmlPart = "";
					index = jmlAnnotation.indexOf("also");
					if (index != -1) {
						currentJmlPart = jmlAnnotation.substring(0, index);
					} else {
						currentJmlPart = jmlAnnotation;
					}
					jmlAnnotation = jmlAnnotation.substring(index + 4);

					addConditionsToFormula(formula, currentJmlPart, variables, conditions);

				} while (index != -1);

				cbcmodelResource.getContents().clear();
				cbcmodelResource.getContents().add(formula);
				cbcmodelResource.getContents().add(variables);
				cbcmodelResource.getContents().add(conditions);
				methods.add(method);

				EList<Statement> listOfStatements = new BasicEList<Statement>();
				EList<Statement> listOfAssertStatements = new BasicEList<Statement>();
				StatementsCollector statementsCollector = new StatementsCollector();
				statementsCollector.visit(methodDeclaration, null);
				for (int j = 0; j < statementsCollector.getStatements().size(); j++) {
					listOfStatements.add(null);
				}
				for (int u = 0; u < statementsCollector.getAssertStatements().size(); u++) {
					listOfAssertStatements.add(null);
				}
				Collections.copy(listOfStatements, statementsCollector.getStatements());
				Collections.copy(listOfAssertStatements, statementsCollector.getAssertStatements());
				handleListOfStatements(cbcmodelResource, listOfStatements, listOfAssertStatements,
						formula.getStatement());
				for (int i = 0; i < variables.getVariables().size(); i++) {
					JavaVariable var = variables.getVariables().get(i);
					if (var.getKind().equals(VariableKind.PARAM) || var.getKind().equals(VariableKind.RETURN)) {
						variables.getVariables().remove(var);
						i--;
					}
				}
				cbcmodelResources.add(cbcmodelResource);
			}
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Methods(), methods);
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Fields(), fields);
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_ClassInvariants(), invariants);
			cbcclassResource.getContents().add(modelClass);
			saveResource(cbcclassResource);
			// TODO: generate class diagram from model

			for (Resource cbcmodelResource : cbcmodelResources) {
				saveResource(cbcmodelResource);
				GenerateDiagramFromModel gdfm = new GenerateDiagramFromModel();
				gdfm.execute(cbcmodelResource);
			}
		}
	}

	public ModelClass instantiateModelClass(String methodName) throws ExecutionException {
		ModelClass modelClass = null;

		if (methodName == null) {
			for (EObject obj : cbcclassResource.getContents()) {
				if (obj instanceof ModelClass) {
					modelClass = (ModelClass) obj;
					modelClass.getMethods().clear();
					modelClass.getClassInvariants().clear();
					modelClass.getFields();
				}
			}

			if (modelClass == null) {
				modelClass = CbcclassFactory.eINSTANCE.createModelClass();
				modelClass.setName(className);
			}
		} else {
			for (EObject obj : cbcclassResource.getContents()) {
				if (obj instanceof ModelClass) {
					modelClass = (ModelClass) obj;
				}
			}

			if (modelClass == null) {
				modelClass = CbcclassFactory.eINSTANCE.createModelClass();
				modelClass.setName(className);
			} else {
				for (Method m : modelClass.getMethods()) {
					if (m.getName().equals(methodName)) {
						boolean answer = MessageDialog.openQuestion(
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Confirm",
								"The selected Diagram already exists and will be replaced. Do you still want to continue?");
						if (!answer) {
							throw new ExecutionException("The selected Diagram already exists.");
						}
						modelClass.getMethods().remove(m);
						this.methods.remove(m);
						break;
					}
				}
			}
		}
		return modelClass;
	}

	public Resource setupProjectForCbCModel(Method method, String methodName) {
		Resource cbcmodelResource;

		// URI cbcDiagramUri = URI.createFileURI(folder.getLocation() + "\\" +
		// methodName + ".cbcmodel");
		URI cbcDiagramUri = URI.createFileURI(folder.getLocation() + File.separator + methodName + ".cbcmodel");

		if (!folder.getFile(methodName + ".cbcmodel").exists()) {

			m.put("cbcmodel", new XMIResourceFactoryImpl());
			cbcmodelResource = rs
					// .createResource(URI.createFileURI(folder.getLocation() + "\\" + methodName +
					// ".cbcmodel"));
					.createResource(
							URI.createFileURI(folder.getLocation() + File.separator + methodName + ".cbcmodel"));
			method.setCbcDiagramURI(cbcDiagramUri.toFileString());
		} else {
			IFile cbcmodelFile = folder.getFile(methodName + ".cbcmodel");
			cbcmodelResource = GetDiagramUtil.getResourceFromFile(cbcmodelFile, rs);
		}
		return cbcmodelResource;
	}

	public void fillVariableList(JavaVariables variables, MethodDeclaration methodDec) {
		// add parameters to variables

		for (Parameter p : methodDec.getParameters()) {
			addToVariables(new VariableDeclarator(p.getType(), p.getName()), variables, VariableKind.PARAM);
		}

		com.github.javaparser.ast.type.Type type = methodDec.getType();
		if (!type.isVoidType()) {
			JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			String typeString = methodDec.getTypeAsString();
			variable.setName(typeString + " ret");
			variable.setKind(VariableKind.RETURN);
			variables.getVariables().add(variable);
		}

	}

	public void addFieldToList(FieldDeclaration fieldDec) {
		Field field = CbcclassFactory.eINSTANCE.createField();

		NodeList<VariableDeclarator> varDec = fieldDec.getVariables();
		field.setName(varDec.get(0).getNameAsString());
		field.setType(varDec.get(0).getTypeAsString());

		if (fieldDec.isPrivate()) {
			field.setVisibility(Visibility.PRIVATE);
		} else if (fieldDec.isProtected()) {
			field.setVisibility(Visibility.PROTECTED);
		}

		if (fieldDec.isStatic()) {
			field.setIsStatic(true);
		}
		fields.add(field);
	}

	public void setupProjectStructure(IFile iFile) {
		className = iFile.getName().split("\\.")[0];
		// folder =
		// iFile.getProject().getFolder(iFile.getParent().getProjectRelativePath().append("\\"
		// + className));
		folder = iFile.getProject()
				.getFolder(iFile.getParent().getProjectRelativePath().append(File.separator + className));
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!folder.getFile(className + ".cbcclass").exists()) {

			m.put("cbcclass", new XMIResourceFactoryImpl());
			cbcclassResource = rs
					// .createResource(URI.createFileURI(folder.getLocation() + "\\" + className +
					// ".cbcclass"));
					.createResource(URI.createFileURI(folder.getLocation() + File.separator + className + ".cbcclass"));
		} else {
			IFile cbcclassFile = folder.getFile(className + ".cbcclass");
			cbcclassResource = GetDiagramUtil.getResourceFromFile(cbcclassFile, rs);
		}

	}

	public static void saveResource(Resource r) {
		try {
			r.save(Collections.EMPTY_MAP);
			r.setTrackingModification(true);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(r.getURI().toFileString());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.getParent().refreshLocal(1, null);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
		}
	}

	private String buildSignatureString(MethodDeclaration methodDec, JavaVariables variables) {
		String signature = "";
		if (methodDec.isPublic()) {
			signature += "public ";
		} else if (methodDec.isPrivate()) {
			signature += "private ";
		} else if (methodDec.isProtected()) {
			signature += "protected ";
		}
		if (methodDec.isStatic()) {
			signature += "static ";
		}

		StringJoiner sjParameters = new StringJoiner(", ");
		String returnType = "void";

		for (JavaVariable v : variables.getVariables()) {
			if (v.getKind().equals(VariableKind.PARAM)) {
				sjParameters.add(v.getName());
			} else if (v.getKind().equals(VariableKind.RETURN)) {
				returnType = v.getName().substring(0, v.getName().indexOf(' '));
			}
		}

		signature += returnType + " " + methodDec.getNameAsString() + "(" + sjParameters.toString() + ")";

		return signature;
	}

	public void settingSignature(MethodDeclaration methodDec, JavaVariables variables, Method method) {
		if (methodDec.isPublic()) {
			method.setVisibility(Visibility.PUBLIC);
		} else if (methodDec.isPrivate()) {
			method.setVisibility(Visibility.PRIVATE);
		} else if (methodDec.isProtected()) {
			method.setVisibility(Visibility.PROTECTED);
		}
		if (methodDec.isStatic()) {
			method.setIsStatic(true);
		}

		method.setReturnType("void");
		method.setName(methodDec.getNameAsString());

		for (JavaVariable v : variables.getVariables()) {
			if (v.getKind().equals(VariableKind.PARAM)) {
				de.tu_bs.cs.isf.cbc.cbcclass.Parameter param = CbcclassFactory.eINSTANCE.createParameter();
				String[] nameSplitted = v.getName().split(" ");
				param.setType(nameSplitted[nameSplitted.length - 2]);
				param.setName(nameSplitted[nameSplitted.length - 1]);
				method.getParameters().add(param);
			} else if (v.getKind().equals(VariableKind.RETURN)) {
				method.setReturnType(v.getName().substring(0, v.getName().indexOf(' ')));
				de.tu_bs.cs.isf.cbc.cbcclass.Parameter param = CbcclassFactory.eINSTANCE.createParameter();
				String[] nameSplitted = v.getName().split(" ");
				param.setType(nameSplitted[nameSplitted.length - 2]);
				param.setName(nameSplitted[nameSplitted.length - 1]);
				method.getParameters().add(param);
			}
		}
	}

	/**
	 * adds the pre and post condition from jmlAnnotation to formula
	 * 
	 * @param formula
	 * @param jmlAnnotation
	 *            contains pre and post condition for formula
	 * @param variables
	 * @param conditions
	 */
	public void addConditionsToFormula(CbCFormula formula, String jmlAnnotation, JavaVariables variables,
			GlobalConditions conditions) {
		jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);

		// jmlAnnotation = cbcWorkaroundForOldKeyword(jmlAnnotation, variables,
		// conditions);

		// adds pre condition
		int startPre = jmlAnnotation.indexOf("requires");
		String pre = "";

		while (startPre != -1) {
			int endPre = findEnd(jmlAnnotation, startPre);
			pre = pre + " & " + jmlAnnotation.substring(startPre + 9, endPre);
			startPre = jmlAnnotation.indexOf("requires", endPre);
		}
		// delete first &
		pre = pre.substring(2);
		Condition preCond = CbcmodelFactory.eINSTANCE.createCondition();
		preCond.setName(pre);
		formula.getPreCondition().setName(pre);
		formula.getStatement().getPreCondition().setName(pre);

		// adds post condition
		int startPost = jmlAnnotation.indexOf("ensures");
		String post = "";
		while (startPost != -1) {
			int endPost = findEnd(jmlAnnotation, startPost);
			String currentPost = jmlAnnotation.substring(startPost + 8, endPost);
			// if (jmlAnnotation.contains("\\result")) {
			// currentPost = currentPost.replace("\\result", "result");
			// }
			post = post + " & " + currentPost;
			startPost = jmlAnnotation.indexOf("ensures", endPost);
		}
		// delete first &
		post = post.substring(2);
		Condition postCond = CbcmodelFactory.eINSTANCE.createCondition();
		postCond.setName(post);
		formula.getPostCondition().setName(post);
		formula.getStatement().getPostCondition().setName(post);

		// adds modifiables
		int startMod = jmlAnnotation.indexOf("assignable");
		int endMod = findEnd(jmlAnnotation, startMod);
		for (String assign : jmlAnnotation.substring(startMod + 11, endMod).split(",")) {
			formula.getStatement().getPostCondition().getModifiables().add(assign);
		}
	}

	private String cbcWorkaroundForOldKeyword(String jmlAnnotation, JavaVariables variables,
			GlobalConditions conditions) {
		int old = jmlAnnotation.indexOf("\\old");
		while (old != -1) {
			int endOld = findEndOfBracket(jmlAnnotation, old + 5);
			String oldPart = jmlAnnotation.substring(old + 5, endOld);
			String name = "";
			String rest = "";
			int index = oldPart.indexOf(".");
			if (index != -1) {
				name = oldPart.substring(0, index);
				rest = oldPart.substring(index);
			} else {
				name = oldPart;
			}
			String newVariableName = "old_" + name;
			jmlAnnotation = jmlAnnotation.replace("\\old" + "(" + oldPart + ")", newVariableName + rest);
			old = jmlAnnotation.indexOf("\\old", endOld + 5);
			JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			// find type of old variable
			String typeOfVariable = "";
			for (JavaVariable var : variables.getVariables()) {
				int indexName = var.getName().indexOf(" " + name);
				if (indexName != -1) {
					typeOfVariable = var.getName().substring(0, indexName);
					break;
				}
			}
			for (Field f : variables.getFields()) {
				if (f.getName().equals(name)) {
					typeOfVariable = f.getType();
				}
			}
			variable.setName(typeOfVariable + " " + newVariableName);
			variables.getVariables().add(variable);

			boolean conditionAlreadyExists = false;
			String newCondition = newVariableName + " = " + name;
			for (Condition c : conditions.getConditions()) {
				if (c.getName().equals(newCondition)) {
					conditionAlreadyExists = true;
				}
			}
			if (!conditionAlreadyExists) {
				Condition cond = CbcmodelFactory.eINSTANCE.createCondition();
				cond.setName(newCondition);

				conditions.getConditions().add(cond);
			}
		}
		return jmlAnnotation;

	}

	/**
	 * replaces special symbols(&& -> &, forall, ...)
	 * 
	 * @param jmlAnnotation
	 *            with required symbols
	 * @return
	 */
	private String replaceSpecialSymbols(String jmlAnnotation) {
		jmlAnnotation = jmlAnnotation.replace("&&", "&");
		jmlAnnotation = jmlAnnotation.replace("==>", "->");
		jmlAnnotation = jmlAnnotation.replace("<==>", "<->");
		jmlAnnotation = jmlAnnotation.replace("||", "|");
		jmlAnnotation = jmlAnnotation.replace("==", "=");
		jmlAnnotation = jmlAnnotation.replace("@", "");
		jmlAnnotation = jmlAnnotation.replace("\r\n\t", "");

		// replace parts of JML with forall
		// replace (\forall T x; a; b) by (\forall T x; ((a) -> (b)) and (\forall T x;
		// a) by (\forall T x; (a))
		int startForAll = jmlAnnotation.indexOf("\\forall");
		while (startForAll != -1) {
			int endForAll = findEndOfBracket(jmlAnnotation, startForAll);
			int findFirstSemic = findNextSemic(jmlAnnotation, startForAll, endForAll);
			int findSecondSemic = findNextSemic(jmlAnnotation, findFirstSemic + 1, endForAll);
			String firstPart = jmlAnnotation.substring(0, findFirstSemic + 1);
			if (findSecondSemic != -1) {
				String secondPart = jmlAnnotation.substring(findFirstSemic + 1, findSecondSemic);
				String thirdPart = jmlAnnotation.substring(findSecondSemic + 1, endForAll);
				jmlAnnotation = firstPart + "(" + secondPart + ") -> (" + thirdPart + ")"
						+ jmlAnnotation.substring(endForAll);
			} else {
				String secondPart = jmlAnnotation.substring(findFirstSemic + 1, endForAll);
				jmlAnnotation = firstPart + "(" + secondPart + ")" + jmlAnnotation.substring(endForAll);
			}
			startForAll = jmlAnnotation.indexOf("\\forall", startForAll + 7);
		}

		// replace parts of JML with exists
		// replace (\exists T x; a; b) by (\exists T x; (a) & (b)) and (\exists T x; a)
		// by (\exists T x;(a))
		int startExists = jmlAnnotation.indexOf("\\exists");
		while (startExists != -1) {
			int endExists = findEndOfBracket(jmlAnnotation, startExists);
			int findFirstSemic = findNextSemic(jmlAnnotation, startExists, endExists);
			int findSecondSemic = findNextSemic(jmlAnnotation, findFirstSemic + 1, endExists);
			String firstPart = jmlAnnotation.substring(0, findFirstSemic + 1);
			if (findSecondSemic != -1) {
				String secondPart = jmlAnnotation.substring(findFirstSemic + 1, findSecondSemic);
				String thirdPart = jmlAnnotation.substring(findSecondSemic + 1, endExists);
				jmlAnnotation = firstPart + "(" + secondPart + ") & (" + thirdPart + ")"
						+ jmlAnnotation.substring(endExists);
			} else {
				String secondPart = jmlAnnotation.substring(findFirstSemic + 1, endExists);
				jmlAnnotation = firstPart + "(" + secondPart + ")" + jmlAnnotation.substring(endExists);
			}
			startExists = jmlAnnotation.indexOf("\\exists", startExists + 7);
		}
		return jmlAnnotation;
	}

	/**
	 * finds next semicolon, corresponding to part between start and end position,
	 * which is a forall or exists part
	 * 
	 * @param jmlAnnotation
	 *            part where to look for next semicolon
	 * @param startForAll
	 *            start position
	 * @param endForAll
	 *            end position
	 * @return
	 */
	private int findNextSemic(String jmlAnnotation, int start, int end) {
		int index = jmlAnnotation.indexOf(";", start);
		if (index != -1 && index < end) {
			int leftBracket = jmlAnnotation.indexOf("(", start);
			// left bracket, check if semicolon at index position
			// belongs to another forall or exists part
			if (leftBracket != -1 && leftBracket < index) {
				int rightBracket = findEndOfBracket(jmlAnnotation, leftBracket + 1);
				return findNextSemic(jmlAnnotation, rightBracket + 1, end);
			} else {
				return index;
			}
		} else {
			return -1;
		}
	}

	/**
	 * finds the end position of the part belonging to 'requires' or 'ensures'
	 * 
	 * @param jmlAnnotation
	 * @param startPosition
	 *            index of requires or ensures
	 * @return
	 */
	private int findEnd(String jmlAnnotation, int startPosition) {
		int possibleEnd = jmlAnnotation.indexOf(";", startPosition);
		int bracketOpen = jmlAnnotation.indexOf("(", startPosition);
		while (bracketOpen != -1 && possibleEnd > bracketOpen) {
			int findBracketClose = findEndOfBracket(jmlAnnotation, bracketOpen + 1);
			possibleEnd = jmlAnnotation.indexOf(";", findBracketClose);
			bracketOpen = jmlAnnotation.indexOf("(", findBracketClose);
		}
		return possibleEnd;
	}

	/**
	 * finds the position of the bracket ')' in jmlAnnotation which belongs to the
	 * bracket '(' right before starting position
	 * 
	 * @param jmlAnnotation
	 * @param start
	 *            new part in brackets starts here(not the exact position of "(",
	 *            after "(")
	 * @return end of the part
	 */
	private int findEndOfBracket(String jmlAnnotation, int start) {
		int nextBracketClose = jmlAnnotation.indexOf(")", start);
		int nextBracketOpen = jmlAnnotation.indexOf("(", start);
		while (nextBracketOpen != -1 && nextBracketOpen < nextBracketClose) {
			// nextBracketClose does not belong to start bracket, find next possible bracket
			// close
			nextBracketOpen = jmlAnnotation.indexOf("(", nextBracketOpen + 1);
			nextBracketClose = jmlAnnotation.indexOf(")", nextBracketClose + 1);
		}
		return nextBracketClose;
	}

	/**
	 * adds loop variant and invariant from jmlAnnotation to
	 * 
	 * @param r
	 * 
	 * @param repStatement
	 * @param jmlAnnotation
	 *            contains loop variant and invariant
	 */
	private void addLoopConditions(Resource r, SmallRepetitionStatement repStatement, String jmlAnnotation) {
		// adds invariant
		int startInvariant = jmlAnnotation.indexOf("loop_invariant");
		int endInvariant = findEnd(jmlAnnotation, startInvariant);
		String invariant = jmlAnnotation.substring(startInvariant + 15, endInvariant);
		int old = invariant.indexOf("\\old");
		String newVariableName = "";
		ArrayList<String> additionalPre = new ArrayList<String>();
		// new variable old_name
		while (old != -1) {
			int endOld = findEndOfBracket(invariant, old + 5);
			String oldPart = invariant.substring(old + 5, endOld);
			String name = "";
			String rest = "";
			int index = oldPart.indexOf(".");
			if (index != -1) {
				name = oldPart.substring(0, index);
				rest = oldPart.substring(index);
			} else {
				name = oldPart;
			}
			newVariableName = "old_" + name;
			newVariableName = "\\old" + "(" + oldPart + ")";
			invariant = invariant.replace("\\old" + "(" + oldPart + ")", newVariableName + rest);
			additionalPre.add(newVariableName + " = " + name);
			old = invariant.indexOf("\\old", endOld + 5);
			JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			variable.setName(newVariableName);
			JavaVariables variableList = (JavaVariables) r.getContents().get(1);
			variableList.getVariables().add(variable);
		}
		CbCFormula formula = (CbCFormula) r.getContents().get(0);
		for (String addPre : additionalPre) {
			formula.getStatement().getPreCondition()
					.setName(formula.getStatement().getPreCondition().getName() + " & " + addPre);
		}
		UpdateConditionsOfChildren.updateRefinedStatement(formula.getStatement(),
				formula.getStatement().getRefinement());

		repStatement.getInvariant().setName(invariant);

		// adds variant
		int startVariant = jmlAnnotation.indexOf("decreases");
		int endVariant = jmlAnnotation.indexOf(";", startVariant);
		repStatement.getVariant().setName(jmlAnnotation.substring(startVariant + 10, endVariant));
	}

	/**
	 * Finds the JML annotations in the file and adds the JML blocks before methods
	 * to the list jmlMethodConditions and adds the JML blocks before a loop to the
	 * list jmlLoopConditions.
	 * 
	 * @param file
	 *            java code with JML annotations
	 * @param jmlMethodConditions
	 *            list for pre/post conditions for methods
	 * @param jmlLoopConditions
	 *            list for conditions for loops
	 */
	public void readJMLAnnotations(String file, ArrayList<String> jmlMethodConditions) {

		Map<String, String> mapJmlMethodConditions = new HashMap<String, String>();
		int startJML1 = file.indexOf("/*@");
		int startJML2 = file.indexOf("//@");
		int startJML, endJML;
		while (startJML1 != -1 || startJML2 != -1) {
			if (startJML2 == -1 || (startJML1 != -1 && startJML1 < startJML2)) {
				startJML = startJML1;
				endJML = file.indexOf("*/", startJML);
			} else {
				startJML = startJML2;
				endJML = file.indexOf("\n", startJML);
				int nextComment = file.indexOf("//@", endJML);
				while (nextComment == endJML + 2) {
					endJML = file.indexOf("\n", nextComment);
					nextComment = file.indexOf("//@", endJML);
				}
			}
			String jmlAnnotation = file.substring(startJML, endJML);
			if (jmlAnnotation.contains("loop_invariant")) {
				jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);
				jmlLoopConditions.add(jmlAnnotation);
			} else if (jmlAnnotation.contains("normal_behavior")) {
				String methodName = parseNextMethodName(file.substring(endJML));
				mapJmlMethodConditions.put(methodName, jmlAnnotation);
			} else if (jmlAnnotation.contains("invariant")) {
				addInvariantToList(jmlAnnotation);
			}
			file = file.substring(endJML);
			startJML1 = file.indexOf("/*@");
			startJML2 = file.indexOf("//@");
		}
	}

	private void addInvariantToList(String jmlAnnotation) {
		jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);
		int beginInv = jmlAnnotation.indexOf("invariant");
		int endInv;
		String newInv;
		if (jmlAnnotation.startsWith("//")) {
			while (beginInv != -1) {
				endInv = jmlAnnotation.indexOf("//", beginInv) - 1;
				if (endInv == -2) {
					endInv = jmlAnnotation.length() - 2;
				}
				newInv = jmlAnnotation.substring(beginInv + 10, endInv);
				Condition inv = CbcmodelFactory.eINSTANCE.createCondition();
				inv.setName(newInv);
				invariants.add(inv);
				beginInv = jmlAnnotation.indexOf("invariant", endInv);
			}
		} else {
			// line comment, Ende festlegen, im Moment ;*/ Ende
			while (beginInv != -1) {
				endInv = findEnd(jmlAnnotation, beginInv);
				newInv = jmlAnnotation.substring(beginInv + 10, endInv);
				Condition inv = CbcmodelFactory.eINSTANCE.createCondition();
				inv.setName(newInv);
				invariants.add(inv);
				beginInv = jmlAnnotation.indexOf("invariant", endInv);
			}
		}

	}

	private String parseNextMethodName(String file) {

		return null;
	}

	/**
	 * Determines name for diagram and model. If there are methods with the same
	 * name, number consecutively.
	 * 
	 * @param names
	 *            list of already used names
	 * @param potName
	 *            name of method
	 * @return unique name
	 */
	private String findName(List<String> names, String potentialName) {
		int i = 2;
		String retName = potentialName;
		for (String name : names) {
			if (name.equals(retName)) {
				retName = potentialName + i;
				i++;
			}
		}
		names.add(retName);
		return retName;
	}

	// returns the file with name fileName as a String
	public String readFileToString(String file) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			int i;
			String s = "";
			while ((i = br.read()) != -1) {
				s = s + (char) i;
			}
			br.close();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Handles a list of statements: if there are more than one statement, creates
	 * CompositionStatement and handles rest of the list
	 * 
	 * @param r
	 * @param statements
	 *            list of statements from java code
	 * @param parent
	 *            the statements from the list should be connected to that statement
	 */
	public void handleListOfStatements(Resource r, EList<Statement> statements, EList<Statement> assertStatements,
			AbstractStatement parent) throws ExecutionException {
		if (statements.size() > 1) {
			CompositionStatement composition = createComposition();
			parent.setRefinement(composition);

			handleStatement(r, statements.get(0), assertStatements, composition.getFirstStatement());
			int i = 0;
			if (!assertStatements.isEmpty()) { // assert statements contain intermediate conditions
				AssertStmt assertSt = assertStatements.get(0).asAssertStmt();
				Condition intermediate = CbcmodelFactory.eINSTANCE.createCondition();
				intermediate.setName(assertSt.toString());
				composition.setIntermediateCondition(intermediate);
				i = 1;
			}
			UpdateConditionsOfChildren.updateRefinedStatement(parent, composition);

			BasicEList<Statement> newAssertStatementList = new BasicEList<Statement>();
			while (i < assertStatements.size()) {
				newAssertStatementList.add(assertStatements.get(i));
				i++;
			}

			BasicEList<Statement> newStatementList = new BasicEList<Statement>();
			int j = 1;
			while (j < statements.size()) {
				newStatementList.add(statements.get(j));
				j++;
			}
			handleListOfStatements(r, newStatementList, newAssertStatementList, composition.getSecondStatement());// Todo:
																													// throws
																													// Error
		} else if (statements.size() == 1) {
			handleStatement(r, statements.get(0), assertStatements, parent);

		} else {
			SkipStatement skipStatement = createSkipStatement();
			parent.setRefinement(skipStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
		}
	}

	/**
	 * Adds statement to resource r, statement is connected to parent and handled,
	 * depending on the type of statement
	 * 
	 * @param r
	 * @param statement
	 * @param parent
	 */
	private void handleStatement(Resource r, Statement statement, EList<Statement> assertStatements,
			AbstractStatement parent) throws ExecutionException {
		if (statement.isExpressionStmt()) {
			if (statement.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
				VariableDeclarationExpr variableStmt = statement.asExpressionStmt().getExpression()
						.asVariableDeclarationExpr();
				NodeList<VariableDeclarator> varDec = variableStmt.getVariables();
				String text = varDec.get(0).toString();
				if (text.contains("=")) {
					String firstPart = text.substring(0, text.indexOf("="));
					int index = firstPart.lastIndexOf(varDec.get(0).getNameAsString());
					text = text.substring(index);
					AbstractStatement s = createStatement(text + ";");
					parent.setRefinement(s);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
				} else {
					SkipStatement skipStatement = createSkipStatement();
					parent.setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
				}
				addToVariables(varDec.get(0), (JavaVariables) r.getContents().get(1), VariableKind.LOCAL);
			} else {
				ExpressionStmt expressionStmt = statement.asExpressionStmt();
				AbstractStatement s = createStatement(expressionStmt.getExpression().toString() + ";");
				parent.setRefinement(s);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
			}
		} else if (statement.isWhileStmt()) {
			WhileStmt whileStmt = statement.asWhileStmt();
			String conditionString = whileStmt.getCondition().toString();
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);

			if (jmlLoopConditions.size() != 0) {
				addLoopConditions(r, repStatement, jmlLoopConditions.get(position));
				position++;
			}

			parent.setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, repStatement);
			if (whileStmt.getBody().isBlockStmt()) {
				EList<Statement> whileBlock = new BasicEList<Statement>();
				for (Statement stmt : whileStmt.getBody().asBlockStmt().getStatements()) {
					whileBlock.add(stmt);
				}
				handleListOfStatements(r, whileBlock, assertStatements, repStatement.getLoopStatement());
			}
		} else if (statement.isIfStmt()) {
			IfStmt ifStat = statement.asIfStmt();
			Optional<Statement> elseStat = ifStat.getElseStmt();
			// also nicht mehrere else ifs
			if (elseStat.isPresent() && !elseStat.get().isIfStmt()) {
				String conditionString = ifStat.getCondition().toString();
				conditionString = conditionString.replace("==", "=");
				conditionString = conditionString.replace("&&", "&");
				conditionString = conditionString.replace("||", "|");
				SelectionStatement selStatement = createSimpleSelection(conditionString,
						("!" + "(" + conditionString + ")"));
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				EList<Statement> ifBlock = new BasicEList<Statement>();
				if (ifStat.getThenStmt().isBlockStmt()) {
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						ifBlock.add(stmt);
					}
					handleListOfStatements(r, ifBlock, assertStatements, selStatement.getCommands().get(0));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					parent.setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
				}
				EList<Statement> elseBlock = new BasicEList<Statement>();
				if (elseStat.get().isBlockStmt()) {
					for (Statement stmt : elseStat.get().asBlockStmt().getStatements()) {
						elseBlock.add(stmt);
					}
					handleListOfStatements(r, elseBlock, assertStatements, selStatement.getCommands().get(1));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					selStatement.getCommands().get(1).setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(selStatement.getCommands().get(1), skipStatement);
				}
			} else {
				SelectionStatement selStatement = createMultiSelection(ifStat.getCondition().toString());
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				EList<Statement> selBlock = new BasicEList<Statement>();
				if (ifStat.getThenStmt().isBlockStmt()) {
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						selBlock.add(stmt);
					}
					handleListOfStatements(r, selBlock, assertStatements, selStatement.getCommands().get(0));
				}
				int i = 1;
				while (ifStat.getElseStmt().isPresent() && ifStat.getElseStmt().get().isIfStmt()) {
					IfStmt nextCondition = elseStat.get().asIfStmt();
					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(nextCondition.getCondition().toString());
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					if (ifStat.getThenStmt().isBlockStmt()) {
						EList<Statement> privBlockStmts = new BasicEList<Statement>();
						for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
							privBlockStmts.add(stmt);
						}
						handleListOfStatements(r, privBlockStmts, assertStatements, selStatement.getCommands().get(i));
					}
					i++;
					ifStat = nextCondition;
				}

				if (ifStat.getElseStmt().isPresent() && ifStat.getElseStmt().get().isBlockStmt()) {
					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					String condition = "";
					for (Condition guard : selStatement.getGuards()) {
						condition = condition + "!(" + guard.getName() + ") & ";
					}
					condition = condition.substring(0, condition.length() - 3);
					conditionNext.setName(condition);
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					EList<Statement> privBlockStmts = new BasicEList<Statement>();
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						privBlockStmts.add(stmt);
					}
					handleListOfStatements(r, privBlockStmts, assertStatements, selStatement.getCommands().get(i));
				}
			}

		} else if (statement.isReturnStmt()) {
			ReturnStmt returnStmt = statement.asReturnStmt();
			if (returnStmt.getExpression().isPresent()) {
				ReturnStatement retStatement = createReturnStatement(
						"ret = " + returnStmt.getExpression().get().toString() + ";");// "\\result = " +
																						// JavaResourceUtil.getText(returnImpl.getReturnValue())
																						// + ";");
				parent.setRefinement(retStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, retStatement);
			}
		} else if (statement.isForStmt()) {
			ForStmt forStmt = statement.asForStmt();

			CompositionStatement composition = createComposition();
			parent.setRefinement(composition);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, composition);

			// Initialization as first part of composition
			String init = forStmt.getInitialization().get(0).toString();
			AbstractStatement s = createStatement(init + ";");
			composition.getFirstStatement().setRefinement(s);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getFirstStatement(), s);

			// new Composition for actual repetition block and loop variable update
			CompositionStatement composition2 = createComposition();
			composition.getSecondStatement().setRefinement(composition2);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getSecondStatement(), composition2);
			String conditionString = "";
			if (forStmt.getCompare().isPresent()) {
				conditionString = forStmt.getCompare().toString();
			}
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
			if (position < jmlLoopConditions.size())
				addLoopConditions(r, repStatement, jmlLoopConditions.get(position++));
			composition2.getFirstStatement().setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getFirstStatement(), repStatement);

			// loop variable update, pr�fen, ob ich mehrere updates haben kann
			String update = forStmt.getUpdate().get(0).toString();
			AbstractStatement updateStatement = createStatement(update + ";");
			composition2.getSecondStatement().setRefinement(updateStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getSecondStatement(), updateStatement);

			EList<Statement> forBlock = new BasicEList<Statement>();
			if (forStmt.getBody().isBlockStmt()) {
				for (Statement stmt : forStmt.getBody().asBlockStmt().getStatements()) {
					forBlock.add(stmt);
				}
				handleListOfStatements(r, forBlock, assertStatements, repStatement.getLoopStatement());
			}
		} else if (statement.isSwitchStmt()) {
			SwitchStmt switchStmt = statement.asSwitchStmt();
			String switchVariable = switchStmt.getSelector().toString();
			Expression firstCondition = null;
			SwitchEntry sc = null;

			if (switchStmt.getEntry(0).getType().equals(SwitchEntry.Type.STATEMENT_GROUP)) {
				sc = switchStmt.getEntry(0);
				Optional<Expression> label = sc.getLabels().getFirst();
				if (label.isPresent()) {
					firstCondition = label.get();
				}
			}
			SelectionStatement selStatement = createMultiSelection(switchVariable + " = " + firstCondition.toString());
			parent.setRefinement(selStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
			EList<Statement> switchStmts = new BasicEList<Statement>();
			for (Statement stmt : sc.getStatements()) {
				switchStmts.add(stmt);
			}
			handleListOfStatements(r, switchStmts, assertStatements, selStatement.getCommands().get(0));

			for (int i = 1; i < switchStmt.getEntries().size(); i++) {
				if (switchStmt.getEntry(i).getType().equals(SwitchEntry.Type.STATEMENT_GROUP)
						&& switchStmt.getEntry(i).getLabels().isNonEmpty()) {
					SwitchEntry normalCase = switchStmt.getEntry(i);
					Expression condition = null;
					Optional<Expression> label = normalCase.getLabels().getFirst();
					if (label.isPresent()) {
						condition = label.get();
					}

					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(switchVariable + " = " + condition.toString());
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					EList<Statement> nonEmptySwitchStmts = new BasicEList<Statement>();
					for (Statement stmt : normalCase.getStatements()) {
						nonEmptySwitchStmts.add(stmt);
					}
					handleListOfStatements(r, nonEmptySwitchStmts, assertStatements, nextStatement);

				} else if (switchStmt.getEntry(i).getType().equals(SwitchEntry.Type.STATEMENT_GROUP)) {
					SwitchEntry defaultCase = switchStmt.getEntry(i);
					String defaultCondition = "";
					for (Condition guard : selStatement.getGuards()) {
						defaultCondition = defaultCondition + "!(" + guard.getName() + ") & ";
					}
					defaultCondition = defaultCondition.substring(0, defaultCondition.length() - 3);

					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(defaultCondition);
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					EList<Statement> maybeEmtpySwtichStmt = new BasicEList<Statement>();
					for (Statement stmt : defaultCase.getStatements()) {
						maybeEmtpySwtichStmt.add(stmt);
					}
					handleListOfStatements(r, maybeEmtpySwtichStmt, assertStatements, nextStatement);
				}
			}
		} else if (statement.isEmptyStmt()) {
			SkipStatement skipStatement = createSkipStatement();
			parent.setRefinement(skipStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
		}

	}

	// adds variable to the list of JavaVariables
	public void addToVariables(VariableDeclarator varDec, JavaVariables variableList, VariableKind kind) {
		JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		javaVariable.setName(varDec.getTypeAsString() + " " + varDec.getNameAsString());
		javaVariable.setKind(kind);
		variableList.getVariables().add(javaVariable);
	}

	public CbCFormula createFormula(String name) {
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		formula.setName(name);
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		formula.setStatement(statement);
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName("pre");
		statement.setPreCondition(preCondition);
		Condition preCondition2 = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition2.setName("pre");
		formula.setPreCondition(preCondition2);
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName("post");
		statement.setPostCondition(postCondition);
		Condition postCondition2 = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition2.setName("post");
		formula.setPostCondition(postCondition2);
		return formula;
	}

	public CompositionStatement createComposition() {
		CompositionStatement compoStatement = CbcmodelFactory.eINSTANCE.createCompositionStatement();
		compoStatement.setName("compositionStatement");
		AbstractStatement statement1 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement1.setName("statement1");
		compoStatement.setFirstStatement(statement1);
		Condition pre1 = CbcmodelFactory.eINSTANCE.createCondition();
		pre1.setName("");
		statement1.setPreCondition(pre1);
		Condition post1 = CbcmodelFactory.eINSTANCE.createCondition();
		post1.setName("");
		statement1.setPostCondition(post1);

		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("intermediateCond");
		compoStatement.setIntermediateCondition(condition);
		AbstractStatement statement2 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement2.setName("statement2");
		compoStatement.setSecondStatement(statement2);
		Condition pre2 = CbcmodelFactory.eINSTANCE.createCondition();
		pre2.setName("");
		statement2.setPreCondition(pre2);
		Condition post2 = CbcmodelFactory.eINSTANCE.createCondition();
		post2.setName("");
		statement2.setPostCondition(post2);
		return compoStatement;
	}

	public SmallRepetitionStatement createRepetition(String guard) {
		SmallRepetitionStatement repetitionStatement = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
		repetitionStatement.setName("repetitionStatement");
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("loop");
		repetitionStatement.setLoopStatement(statement);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(guard);
		repetitionStatement.setGuard(condition);
		Condition invariant = CbcmodelFactory.eINSTANCE.createCondition();
		invariant.setName("invariant");
		repetitionStatement.setInvariant(invariant);
		Variant variant = CbcmodelFactory.eINSTANCE.createVariant();
		variant.setName("variant");
		repetitionStatement.setVariant(variant);

		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);

		Condition preRep = CbcmodelFactory.eINSTANCE.createCondition();
		preRep.setName("");
		repetitionStatement.setPreCondition(preRep);
		Condition postRep = CbcmodelFactory.eINSTANCE.createCondition();
		postRep.setName("");
		repetitionStatement.setPostCondition(postRep);
		return repetitionStatement;
	}

	public SelectionStatement createSimpleSelection(String guard1, String guard2) {
		SelectionStatement selectionStatement = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		selectionStatement.setName("selectionStatement");
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement1");
		selectionStatement.getCommands().add(statement);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(guard1);
		selectionStatement.getGuards().add(condition);
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);

		AbstractStatement statement2 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement2.setName("statement2");
		selectionStatement.getCommands().add(statement2);
		Condition condition2 = CbcmodelFactory.eINSTANCE.createCondition();
		condition2.setName(guard2);
		selectionStatement.getGuards().add(condition2);
		Condition pre2 = CbcmodelFactory.eINSTANCE.createCondition();
		pre2.setName("");
		statement2.setPreCondition(pre2);
		Condition post2 = CbcmodelFactory.eINSTANCE.createCondition();
		post2.setName("");
		statement2.setPostCondition(post2);
		return selectionStatement;
	}

	// for several else ifs
	public SelectionStatement createMultiSelection(String guard1) {
		SelectionStatement selectionStatement = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		selectionStatement.setName("selectionStatement");
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement1");
		selectionStatement.getCommands().add(statement);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(guard1);
		selectionStatement.getGuards().add(condition);
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);
		return selectionStatement;
	}

	public AbstractStatement createStatement(String name) {
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName(name);
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);
		return statement;
	}

	public ReturnStatement createReturnStatement(String name) {
		ReturnStatement returnStatement = CbcmodelFactory.eINSTANCE.createReturnStatement();
		returnStatement.setName(name);
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		returnStatement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		returnStatement.setPostCondition(post);
		return returnStatement;
	}

	public SkipStatement createSkipStatement() {
		SkipStatement statement = CbcmodelFactory.eINSTANCE.createSkipStatement();
		statement.setName(";");
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("{}");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("{}");
		statement.setPostCondition(post);
		return statement;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public String getClassName() {
		return this.className;
	}

	public EList<Field> getFields() {
		return this.fields;
	}

	public EList<Method> getMethods() {
		return this.methods;
	}

	public List<Resource> getCbcmodelResources() {
		return this.cbcmodelResources;
	}

	public EList<Condition> getInvariants() {
		return this.invariants;
	}

	public Resource getCbcclassResource() {
		return this.cbcclassResource;
	}

}
