package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
import org.emftext.commons.layout.LayoutInformation;
import org.emftext.language.java.arrays.ArrayDimension;
import org.emftext.language.java.classifiers.impl.ClassImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.FieldImpl;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.resource.java.util.JavaResourceUtil;
import org.emftext.language.java.statements.ForLoop;
import org.emftext.language.java.statements.LocalVariableStatement;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.statements.WhileLoop;
import org.emftext.language.java.statements.impl.BlockImpl;
import org.emftext.language.java.statements.impl.ConditionImpl;
import org.emftext.language.java.statements.impl.DefaultSwitchCaseImpl;
import org.emftext.language.java.statements.impl.EmptyStatementImpl;
import org.emftext.language.java.statements.impl.ExpressionStatementImpl;
import org.emftext.language.java.statements.impl.NormalSwitchCaseImpl;
import org.emftext.language.java.statements.impl.ReturnImpl;
import org.emftext.language.java.statements.impl.SwitchImpl;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.impl.VoidImpl;
import org.emftext.language.java.variables.LocalVariable;
import org.emftext.language.java.variables.impl.VariableImpl;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility;
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

	public void execute(IFile iFile) {

		ArrayList<String> jmlMethodConditions = new ArrayList<String>();

		String javaFileContent = readFileToString(iFile.getLocation().toPortableString());

		readJMLAnnotations(javaFileContent, jmlMethodConditions);

		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(javaFileContent);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;

		if (compilationUnit.getClassifiers().isEmpty()
				|| compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return;
		}
		packageName = compilationUnit.getNamespacesAsString().substring(0, compilationUnit.getNamespacesAsString().length()-1);
		setupProjectStructure(iFile);
		
		ModelClass modelClass = instantiateModelClass();
		modelClass.setJavaClassURI(URI.createFileURI(iFile.getProjectRelativePath().toPortableString()).toFileString());
		modelClass.setPackage(packageName);
		
		if (compilationUnit.getClassifiers().get(0) instanceof ClassImpl) {
			ClassImpl javaClass = (ClassImpl) compilationUnit.getClassifiers().get(0);

			for (Member member : javaClass.getMembers()) {
				if (member instanceof FieldImpl) {
					addFieldToList((FieldImpl) member);
				}

				if (member instanceof ClassMethod) {
					ClassMethod classMethod = (ClassMethod) member;
					String methodName = classMethod.getName();
					
					Method method = CbcclassFactory.eINSTANCE.createMethod();
					
					Resource cbcmodelResource = setupProjectForCbCModel(method, methodName);

					JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
					fillVariableList(variables, classMethod);

					//String signature = buildSignatureString(classMethod, variables);
					settingSignature(classMethod, variables, method);

					// get global conditions from existing diagram
					GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
					for (EObject obj : cbcmodelResource.getContents()) {
						if (obj instanceof GlobalConditions) {
							conditions = (GlobalConditions) obj;
						}
					}

					CbCFormula formula = createFormula(classMethod.getName());
					formula.setClassName(className);
					formula.setMethodName(method.getName());
					method.setCbcStartTriple(formula);
					formula.setMethodObj(method);
					variables.eSet(CbcmodelPackage.eINSTANCE.getJavaVariables_Fields(), fields);
					
					//parse JML contract to pre- and postconditions of cbcFormula
					String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
							+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \nothing;\r\n"
							+ "	  @*/";
					String jmlAnnotation = classMethod.getAnnotationsAndModifiers().get(0).getLayoutInformations()
							.get(0).getHiddenTokenText();
					if (!jmlAnnotation.contains("/*@"))
						jmlAnnotation = defaultAnnotation;
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

						addConditionsToFormula(formula, currentJmlPart, variables, method, conditions);

					} while (index != -1);
					
					cbcmodelResource.getContents().clear();
					cbcmodelResource.getContents().add(formula);
					cbcmodelResource.getContents().add(variables);
					cbcmodelResource.getContents().add(conditions);
					methods.add(method);

					EList<Statement> listOfStatements = new BasicEList<Statement>();
					for (int j = 0; j < classMethod.getStatements().size(); j++) {
						listOfStatements.add(null);
					}
					Collections.copy(listOfStatements, classMethod.getStatements());
					handleListOfStatements(cbcmodelResource, listOfStatements, formula.getStatement());					
					
					cbcmodelResources.add(cbcmodelResource);
				}
			}
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Methods(), methods);
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_Fields(), fields);
			modelClass.eSet(CbcclassPackage.eINSTANCE.getModelClass_ClassInvariants(), invariants);
			cbcclassResource.getContents().add(modelClass);
			saveResource(cbcclassResource);
			// TODO: generate class diagram from model
			
			for(Resource cbcmodelResource: cbcmodelResources) {
				saveResource(cbcmodelResource);
				GenerateDiagramFromModel gdfm = new GenerateDiagramFromModel();
				gdfm.execute(cbcmodelResource);
			}
			
			
		}

	}

	private ModelClass instantiateModelClass() {
		ModelClass modelClass = null;
		for (EObject obj : cbcclassResource.getContents()) {
			if(obj instanceof ModelClass) {
				modelClass = (ModelClass) obj;
				modelClass.getMethods().clear();
				modelClass.getClassInvariants().clear();
				modelClass.getFields();
			}
		}
		
		if(modelClass == null) {
			modelClass = CbcclassFactory.eINSTANCE.createModelClass();
			modelClass.setName(className);
		}
		return modelClass;
	}

	private Resource setupProjectForCbCModel(Method method, String methodName) {
		Resource cbcmodelResource;

		URI cbcDiagramUri = URI.createFileURI(folder.getLocation() + "\\" + methodName + ".cbcmodel");
		

		if (!folder.getFile(methodName + ".cbcmodel").exists()) {

			m.put("cbcmodel", new XMIResourceFactoryImpl());
			cbcmodelResource = rs
					.createResource(URI.createFileURI(folder.getLocation() + "\\" + methodName + ".cbcmodel"));
			method.setCbcDiagramURI(cbcDiagramUri.toFileString());
		} else {
			IFile cbcmodelFile = folder.getFile(methodName + ".cbcmodel");
			cbcmodelResource = GetDiagramUtil.getResourceFromFile(cbcmodelFile, rs);
		}
		return cbcmodelResource;
	}

	private void fillVariableList(JavaVariables variables, ClassMethod classMethod) {
		// add parameters to variables
		for (Parameter p : classMethod.getParameters()) {
			addToVariables((VariableImpl) p, variables, VariableKind.PARAM);
		}

		TypeReference type = classMethod.getTypeReference();
		String typeString = JavaResourceUtil.getText(type);
		if (!(type instanceof VoidImpl)) {
			String arrayDimensions = "";
			if (classMethod.getArrayDimensionsBefore() != null) {
				for (ArrayDimension ad : classMethod.getArrayDimensionsBefore()) {
					for (LayoutInformation li : ad.getLayoutInformations()) {
						arrayDimensions = arrayDimensions + li.getVisibleTokenText();
					}
				}
			}
			JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			typeString = JavaResourceUtil.getText(type) + arrayDimensions;
			variable.setName(typeString + " result");
			variable.setKind(VariableKind.RETURN);
			variables.getVariables().add(variable);
		}

	}

	private void addFieldToList(FieldImpl fieldImpl) {
		String arrayTokens = "";
		if (fieldImpl.getArrayDimensionsBefore().size() > 0) {
			for (int k = 0; k < fieldImpl.getArrayDimensionsBefore().size(); k++) {
				for (int j = 0; j < fieldImpl.getArrayDimensionsBefore().get(k).getLayoutInformations().size(); j++) {
					arrayTokens = arrayTokens + fieldImpl.getArrayDimensionsBefore().get(k).getLayoutInformations()
							.get(j).getVisibleTokenText();
				}
			}
		}
		Field field = CbcclassFactory.eINSTANCE.createField();
		String type;
		if (fieldImpl.getTypeReference().getLayoutInformations().size() > 0) {
			type = fieldImpl.getTypeReference().getLayoutInformations().get(0).getVisibleTokenText();
		} else {
			type = fieldImpl.getTypeReference().getPureClassifierReference().getLayoutInformations().get(0)
					.getVisibleTokenText();
		}
		field.setName(fieldImpl.getName());
		field.setType(type + arrayTokens);
		if (fieldImpl.isPrivate()) {
			field.setVisibility(Visibility.PRIVATE);
		} else if (fieldImpl.isProtected()) {
			field.setVisibility(Visibility.PROTECTED);
		}

		if (fieldImpl.isStatic()) {
			field.setIsStatic(true);
		}
		fields.add(field);

	}

	private void setupProjectStructure(IFile iFile) {
		className = iFile.getName().split("\\.")[0];
		folder = iFile.getProject().getFolder(iFile.getParent().getProjectRelativePath().append("\\" + className));
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
					.createResource(URI.createFileURI(folder.getLocation() + "\\" + className + ".cbcclass"));
		} else {
			IFile cbcclassFile = folder.getFile(className + ".cbcclass");
			cbcclassResource = GetDiagramUtil.getResourceFromFile(cbcclassFile, rs);
		}

	}

	private void saveResource(Resource r) {
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

	private String buildSignatureString(ClassMethod classMethod, JavaVariables variables) {
		String signature = "";
		if (classMethod.isPublic()) {
			signature += "public ";
		} else if (classMethod.isPrivate()) {
			signature += "private ";
		} else if (classMethod.isProtected()) {
			signature += "protected ";
		}
		if (classMethod.isStatic()) {
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

		signature += returnType + " " + classMethod.getName() + "(" + sjParameters.toString() + ")";

		return signature;
	}
	
	private void settingSignature(ClassMethod classMethod, JavaVariables variables, Method method) {
		String signature = "";
		if (classMethod.isPublic()) {
			method.setVisibility(Visibility.PUBLIC);
		} else if (classMethod.isPrivate()) {
			method.setVisibility(Visibility.PRIVATE);
		} else if (classMethod.isProtected()) {
			method.setVisibility(Visibility.PROTECTED);
		}
		if (classMethod.isStatic()) {
			method.setIsStatic(true);
		}
		
		method.setReturnType("void");

		for (JavaVariable v : variables.getVariables()) {
			if (v.getKind().equals(VariableKind.PARAM)) {
				de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter param = CbcclassFactory.eINSTANCE.createParameter();
				String[] nameSplitted = v.getName().split(" ");
				param.setType(nameSplitted[nameSplitted.length-2]);
				param.setName(nameSplitted[nameSplitted.length-1]);
				method.getParameters().add(param);
			} else if (v.getKind().equals(VariableKind.RETURN)) {
				method.setReturnType(v.getName().substring(0, v.getName().indexOf(' ')));
			}
		}
	}

	/**
	 * adds the pre and post condition from jmlAnnotation to formula
	 * 
	 * @param formula
	 * @param jmlAnnotation contains pre and post condition for formula
	 * @param variables
	 * @param conditions
	 */
	private void addConditionsToFormula(CbCFormula formula, String jmlAnnotation, JavaVariables variables,
			Method method, GlobalConditions conditions) {
		jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);

		jmlAnnotation = cbcWorkaroundForOldKeyword(jmlAnnotation, variables, conditions);

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
			if (jmlAnnotation.contains("\\result")) {
				currentPost = currentPost.replace("\\result", "result");
			}
			post = post + " & " + currentPost;
			startPost = jmlAnnotation.indexOf("ensures", endPost);
		}
		// delete first &
		post = post.substring(2);
		Condition postCond = CbcmodelFactory.eINSTANCE.createCondition();
		postCond.setName(post);
		formula.getPostCondition().setName(post);
		formula.getStatement().getPostCondition().setName(post);
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
			for(Field f: variables.getFields()) {
				if(f.getName().equals(name)) {
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
	 * @param jmlAnnotation with required symbols
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
	 * @param jmlAnnotation part where to look for next semicolon
	 * @param startForAll   start position
	 * @param endForAll     end position
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
	 * @param startPosition index of requires or ensures
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
	 * @param start         new part in brackets starts here(not the exact position
	 *                      of "(", after "(")
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
	 * @param jmlAnnotation contains loop variant and invariant
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
	 * @param file                java code with JML annotations
	 * @param jmlMethodConditions list for pre/post conditions for methods
	 * @param jmlLoopConditions   list for conditions for loops
	 */
	private void readJMLAnnotations(String file, ArrayList<String> jmlMethodConditions) {

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
	 * @param names   list of already used names
	 * @param potName name of method
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
	 * @param statements list of statements from java code
	 * @param parent     the statements from the list should be connected to that
	 *                   statement
	 */
	public void handleListOfStatements(Resource r, EList<Statement> statements, AbstractStatement parent) {
		if (statements.size() > 1) {
			CompositionStatement composition = createComposition();
			parent.setRefinement(composition);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, composition);
			handleStatement(r, statements.get(0), composition.getFirstStatement());
			BasicEList<Statement> newStatementList = new BasicEList<Statement>();
			for (int i = 1; i < statements.size(); i++) {
				newStatementList.add(statements.get(i));
			}
			handleListOfStatements(r, newStatementList, composition.getSecondStatement());
		} else if (statements.size() == 1) {
			handleStatement(r, statements.get(0), parent);
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
	private void handleStatement(Resource r, Statement statement, AbstractStatement parent) {
		if (statement instanceof LocalVariableStatement) {
			LocalVariableStatement variableStatement = (LocalVariableStatement) statement;
			LocalVariable variable = variableStatement.getVariable();
			String text = JavaResourceUtil.getText(variable);
			if (text.contains("=")) {
				String firstPart = text.substring(0, text.indexOf("="));
				int index = firstPart.lastIndexOf(variable.getName());
				text = text.substring(index);
				AbstractStatement s = createStatement(text + ";");
				parent.setRefinement(s);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
			} else {
				SkipStatement skipStatement = createSkipStatement();
				parent.setRefinement(skipStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
			}
			addToVariables((VariableImpl) variable, (JavaVariables) r.getContents().get(1), VariableKind.LOCAL);
		} else if (statement instanceof WhileLoop) {
			WhileLoop loop = (WhileLoop) statement;
			Expression condition = loop.getCondition();
			String conditionString = JavaResourceUtil.getText(condition);
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
			addLoopConditions(r, repStatement, jmlLoopConditions.get(position));
			position++;
			parent.setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, repStatement);
			if (loop.getStatement() instanceof BlockImpl) {
				BlockImpl block = (BlockImpl) loop.getStatement();
				handleListOfStatements(r, block.getStatements(), repStatement.getLoopStatement());
			}
		} else if (statement instanceof ConditionImpl) {
			ConditionImpl conditionImpl = (ConditionImpl) statement;
			Expression condition1 = conditionImpl.getCondition();
			// also nicht mehrere else ifs
			if (!(conditionImpl.getElseStatement() instanceof ConditionImpl)) {
				String conditionString = JavaResourceUtil.getText(condition1);
				conditionString = conditionString.replace("==", "=");
				conditionString = conditionString.replace("&&", "&");
				conditionString = conditionString.replace("||", "|");
				SelectionStatement selStatement = createSimpleSelection(conditionString,
						("!" + "(" + conditionString + ")"));
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				if (conditionImpl.getStatement() instanceof BlockImpl) {
					BlockImpl block = (BlockImpl) conditionImpl.getStatement();
					handleListOfStatements(r, block.getStatements(), selStatement.getCommands().get(0));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					parent.setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
				}
				if (conditionImpl.getElseStatement() instanceof BlockImpl) {
					BlockImpl block = (BlockImpl) conditionImpl.getElseStatement();
					handleListOfStatements(r, block.getStatements(), selStatement.getCommands().get(1));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					selStatement.getCommands().get(1).setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(selStatement.getCommands().get(1), skipStatement);
				}
			} else {
				SelectionStatement selStatement = createMultiSelection(JavaResourceUtil.getText(condition1));
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				if (conditionImpl.getStatement() instanceof BlockImpl) {
					BlockImpl block = (BlockImpl) conditionImpl.getStatement();
					handleListOfStatements(r, block.getStatements(), selStatement.getCommands().get(0));
				}
				int i = 1;
				while (conditionImpl.getElseStatement() instanceof ConditionImpl) {
					ConditionImpl nextCondition = (ConditionImpl) conditionImpl.getElseStatement();
					Expression condition = nextCondition.getCondition();
					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(JavaResourceUtil.getText(condition));
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					if (nextCondition.getStatement() instanceof BlockImpl) {
						BlockImpl block = (BlockImpl) nextCondition.getStatement();
						handleListOfStatements(r, block.getStatements(), selStatement.getCommands().get(i));
					}
					i++;
					conditionImpl = nextCondition;
				}

				if (conditionImpl.getElseStatement() instanceof BlockImpl) {
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
					BlockImpl block = (BlockImpl) conditionImpl.getElseStatement();
					handleListOfStatements(r, block.getStatements(), selStatement.getCommands().get(i));
				}
			}

		} else if (statement instanceof ReturnImpl) {
			ReturnImpl returnImpl = (ReturnImpl) statement;
			ReturnStatement retStatement = createReturnStatement(
					"result = " + JavaResourceUtil.getText(returnImpl.getReturnValue()) + ";");
			parent.setRefinement(retStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, retStatement);
		} else if (statement instanceof ExpressionStatementImpl) {
			ExpressionStatementImpl exprStatement = (ExpressionStatementImpl) statement;
			AbstractStatement s = createStatement(JavaResourceUtil.getText(exprStatement.getExpression()) + ";");
			parent.setRefinement(s);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
		} else if (statement instanceof ForLoop) {
			ForLoop loop = (ForLoop) statement;

			CompositionStatement composition = createComposition();
			parent.setRefinement(composition);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, composition);

			// Initialization as first part of composition
			String init = JavaResourceUtil.getText(loop.getInit());
			AbstractStatement s = createStatement(init + ";");
			composition.getFirstStatement().setRefinement(s);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getFirstStatement(), s);

			// new Composition for actual repetition block and loop variable update
			CompositionStatement composition2 = createComposition();
			composition.getSecondStatement().setRefinement(composition2);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getSecondStatement(), composition2);
			String conditionString = JavaResourceUtil.getText(loop.getCondition());
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
			addLoopConditions(r, repStatement, jmlLoopConditions.get(position));
			position++;
			composition2.getFirstStatement().setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getFirstStatement(), repStatement);

			// loop variable update, prüfen, ob ich mehrere updates haben kann
			String update = JavaResourceUtil.getText(loop.getUpdates().get(0));
			AbstractStatement updateStatement = createStatement(update + ";");
			composition2.getSecondStatement().setRefinement(updateStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getSecondStatement(), updateStatement);

			if (loop.getStatement() instanceof BlockImpl) {
				BlockImpl block = (BlockImpl) loop.getStatement();
				handleListOfStatements(r, block.getStatements(), repStatement.getLoopStatement());
			}
		} else if (statement instanceof SwitchImpl) {
			SwitchImpl switchCase = (SwitchImpl) statement;
			String switchVariable = JavaResourceUtil.getText(switchCase.getVariable());
			Expression firstCondition = null;
			NormalSwitchCaseImpl sc = null;

			if (switchCase.getCases().get(0) instanceof NormalSwitchCaseImpl) {
				sc = (NormalSwitchCaseImpl) switchCase.getCases().get(0);
				firstCondition = sc.getCondition();
			}

			SelectionStatement selStatement = createMultiSelection(
					switchVariable + " = " + JavaResourceUtil.getText(firstCondition));
			parent.setRefinement(selStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
			handleListOfStatements(r, sc.getStatements(), selStatement.getCommands().get(0));

			for (int i = 1; i < switchCase.getCases().size(); i++) {
				if (switchCase.getCases().get(i) instanceof NormalSwitchCaseImpl) {
					NormalSwitchCaseImpl normalCase = (NormalSwitchCaseImpl) switchCase.getCases().get(i);
					Expression condition = normalCase.getCondition();

					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(switchVariable + " = " + JavaResourceUtil.getText(condition));
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					handleListOfStatements(r, normalCase.getStatements(), nextStatement);

				} else if (switchCase.getCases().get(i) instanceof DefaultSwitchCaseImpl) {
					DefaultSwitchCaseImpl defaultCase = (DefaultSwitchCaseImpl) switchCase.getCases().get(i);
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
					handleListOfStatements(r, defaultCase.getStatements(), nextStatement);
				}
			}
		} else if (statement instanceof EmptyStatementImpl) {
			SkipStatement skipStatement = createSkipStatement();
			parent.setRefinement(skipStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
		}

	}

	// adds variable to the list of JavaVariables
	public void addToVariables(VariableImpl variable, JavaVariables variableList, VariableKind kind) {
		String arrayTokens = "";
		if (variable.getArrayDimensionsBefore().size() > 0) {
			for (int k = 0; k < variable.getArrayDimensionsBefore().size(); k++) {
				for (int j = 0; j < variable.getArrayDimensionsBefore().get(k).getLayoutInformations().size(); j++) {
					arrayTokens = arrayTokens + variable.getArrayDimensionsBefore().get(k).getLayoutInformations()
							.get(j).getVisibleTokenText();
				}
			}
		}
		JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		String type;
		if (variable.getTypeReference().getLayoutInformations().size() > 0) {
			type = variable.getTypeReference().getLayoutInformations().get(0).getVisibleTokenText();
		} else {
			type = variable.getTypeReference().getPureClassifierReference().getLayoutInformations().get(0)
					.getVisibleTokenText();
		}
		javaVariable.setName(type + arrayTokens + " " + variable.getName());
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

}