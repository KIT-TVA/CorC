package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emftext.commons.layout.LayoutInformation;
import org.emftext.language.java.arrays.ArrayDimension;
import org.emftext.language.java.classifiers.impl.ClassImpl;
import org.emftext.language.java.classifiers.impl.InterfaceImpl;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.resource.java.util.JavaResourceUtil;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.impl.VoidImpl;
import org.emftext.language.java.variables.impl.VariableImpl;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

public class GenerateClassFromInterfaces {
	// Content of Class
	private ArrayList<String> jmlLoopConditions = new ArrayList<String>();
	private List<Method> methods = new ArrayList<Method>();
	private List<String> fields = new ArrayList<String>();
	private String newTraitName;
	private List<String> composedTraits = new ArrayList<>();
	private List<Map.Entry<String,String>> removedMethods = new ArrayList<>();
	private IProject project;
	List<Method> abstractMethods;
	List<Method> concreteMethods;

	public GenerateClassFromInterfaces() {
	}

	public void execute(IFile file) {
		project = FileUtil.getProjectFromFileInProject(URI.createURI(file.getFullPath().toPortableString()));
		handleTraitFiles(Collections.singletonList(file));
	}
	
	public void execute(IProject project) {
		this.project = project;
		List<IFile> traitCompositionFiles = FileUtil.getFilesFromProject(project, ".tc");
		Iterator<IFile> itr = traitCompositionFiles.iterator();
		while(itr.hasNext()){
		    if (itr.next().getLocation().toPortableString().contains("/bin/"))
		    	itr.remove();
		}
		
		handleTraitFiles(traitCompositionFiles);
	}

	private void handleTraitFiles(List<IFile> traitCompositionFiles) {
		List<IFile> javaFiles = FileUtil.getJavaFilesFromProject(project);
		Iterator<IFile> itr = javaFiles.iterator();
		while(itr.hasNext()){
		    if (itr.next().getLocation().toPortableString().contains("/src_key/"))
		    	itr.remove();
		}
		
		

		for (IFile javaFile : javaFiles) {
			String javaFileContent = readFileToString(javaFile.getLocation().toPortableString());
			readClass(javaFileContent);
		}
		
		for (IFile traitCompositionFile : traitCompositionFiles) {
			String traitCompositionFileContent = readFileToString(traitCompositionFile.getLocation().toPortableString());
			parseTraitCompositionFile(traitCompositionFileContent);
			IFile location = null;
			try {
				location = generateJavaClassForKey();
				verifyImplications(location.getFullPath().toPortableString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void verifyImplications(String path) {
		int proof = 0;
		for (Method abstractMethod : abstractMethods) {
			for (Method concreteMethod : concreteMethods) {
				if (abstractMethod.getMethodName().equals(concreteMethod.getMethodName())) {
					for (int i = 0; i < abstractMethod.getPreCondition().size(); i++) {
						JavaVariables vars = parametersToJavaVars(abstractMethod);
						GlobalConditions conds = CbcmodelFactory.eINSTANCE.createGlobalConditions();
						ProveWithKey proveImplication = new ProveWithKey(null, vars, conds, null, null, path, null, new FileUtil(path), "/src_key");
						proveImplication.proveCImpliesCWithKey(createConditionForKeY(abstractMethod.getPreCondition().get(i), vars, conds), createConditionForKeY(concreteMethod.getPreCondition().get(i), vars, conds), proof++);
						proveImplication.proveCImpliesCWithKey(createConditionForKeY(concreteMethod.getPostCondition().get(i), vars, conds), createConditionForKeY(abstractMethod.getPostCondition().get(i), vars, conds), proof++);
					}
				}
			}
		}
	}

	private Condition createConditionForKeY(String stringCondition, JavaVariables javaVars, GlobalConditions conds) {
		stringCondition = replaceSpecialSymbols(stringCondition);
		String resultKW = "\\\\result";
		stringCondition = stringCondition.replaceAll(resultKW, "result");
		stringCondition = cbcWorkaroundForOldKeyword(stringCondition, javaVars, conds);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName(stringCondition);
		return condition;
	}

	private JavaVariables parametersToJavaVars(Method abstractMethod) {
		JavaVariables javaVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		for (String parameter : abstractMethod.getParameters()) {
			JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			javaVariable.setName(parameter);
			javaVariable.setKind(VariableKind.PARAM);
			javaVariables.getVariables().add(javaVariable);
		}
		if (!abstractMethod.getReturnType().equals("void")) {
			JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
			javaVariable.setName(abstractMethod.getReturnType() + " result");
			javaVariable.setKind(VariableKind.RETURN);
			javaVariables.getVariables().add(javaVariable);
		}
		JavaVariable javaVariableSelf = CbcmodelFactory.eINSTANCE.createJavaVariable();
		javaVariableSelf.setName(newTraitName + " self");
		javaVariableSelf.setKind(VariableKind.LOCAL);
		javaVariables.getVariables().add(javaVariableSelf);
		JavaVariable javaVariableOld = CbcmodelFactory.eINSTANCE.createJavaVariable();
		javaVariableOld.setName(newTraitName + " old_this");
		javaVariableOld.setKind(VariableKind.LOCAL);
		javaVariables.getVariables().add(javaVariableOld);
		return javaVariables;
	}

	private IFile generateJavaClassForKey() throws Exception {
		String allMethodsAsString = collectMethodsOfAllComposedTraits();
		IFile location = generateClassForKey(allMethodsAsString, newTraitName);
		return location;
	}
	
	private IFile generateClassForKey(String content, String fileName) {
		IFolder srcFolder = project.getFolder("src_key");
		if (!srcFolder.exists()) {
			try {
				srcFolder.create(false, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		IFile srcFile = project.getFile("src_key/" + fileName + ".java");
		InputStream stream = createFileContentInputStream(content);
		
		if (!srcFile.exists()) {
			try {
				srcFile.create(stream, false, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else {
			try {
				srcFile.setContents(stream, false, false, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return srcFile;
	}

	private String collectMethodsOfAllComposedTraits() throws Exception {
		StringBuilder builder = new StringBuilder();
		abstractMethods = getMethods(true);
		concreteMethods = getMethods(false);
		builder.append(buildClassHeader());
		for (String field : fields) {
			builder.append(field);
			builder.append("\n");
		}
		
		checkForConsistency(concreteMethods);

		for (Method method : concreteMethods) {
			builder.append(getJMLSpecification(method.getEmfMethod()));
			builder.append(JavaResourceUtil.getText(method.getEmfMethod()));
			builder.append("\n");
		}
		
		for (Method method : abstractMethods) {
			boolean isImplemented = false;
			for (Method concreteMethod : concreteMethods) {
				if (concreteMethod.getMethodName().equals(method.getMethodName())) {
					isImplemented = true;
				}
			}
			if (!isImplemented) {
				builder.append(getJMLSpecification(method.getEmfMethod()));
				builder.append(method.createSignature() + ";");
				builder.append("\n");
			}
		}
		builder.append("}");
		return builder.toString();
	}

	private void checkForConsistency(List<Method> concreteMethods) throws Exception {
		for (int i = 0; i < concreteMethods.size(); i++) {
			for (int j = i+1; j < concreteMethods.size(); j++) {
				if (concreteMethods.get(i).getMethodName().equals(concreteMethods.get(j).getMethodName())) {
					throw new Exception(concreteMethods.get(i).getMethodName() + " implemented in " + concreteMethods.get(i).getClassName() 
							+ " and " + concreteMethods.get(j).getClassName());
				}
			}
		}
	}

	private String buildClassHeader() {
		String returnString = "public class " + newTraitName + " {\n\n";
		return returnString;
	}

	private String getJMLSpecification(org.emftext.language.java.members.Method emfMethod) {
		String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
				+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \\nothing;\r\n"
				+ "	  @*/";
		String jmlAnnotation = emfMethod.getAnnotationsAndModifiers().get(0).getLayoutInformations()
				.get(0).getHiddenTokenText();
		if (!jmlAnnotation.contains("/*@"))
			jmlAnnotation = defaultAnnotation;
		return jmlAnnotation;
	}

	private List<Method> getMethods(boolean isAbstract) {
		List<Method> returnList = new ArrayList<>(); 
		for (Method method : methods) {
			if (composedTraits.contains(method.getClassName()) 
					&& (method.isAbstract() == isAbstract && (isAbstract == isMadeAbstract(method)))) {
				returnList.add(method);
			}
		}
		return returnList;
	}

	private boolean isMadeAbstract(Method method) {
		if (method.isAbstract()) return true;
		for (Entry<String,String> entry : removedMethods) {
			if (method.getClassName().equals(entry.getKey())
					&& method.getMethodName().equals(entry.getValue())) {
				return true;
			}
		}
		return false;
	}

	private InputStream createFileContentInputStream(String content) {
	    InputStream targetStream = new ByteArrayInputStream(content.getBytes());
		return targetStream;
	}

	private void parseTraitCompositionFile(String traitCompositionFileContent) {
		traitCompositionFileContent = traitCompositionFileContent.replace("\n", "").replace("\r", "");
		String[] splitForTraitName = traitCompositionFileContent.split("=");
		composedTraits.clear();
		removedMethods.clear();
		if (splitForTraitName.length == 1) return;
		newTraitName = splitForTraitName[0].trim();
		String[] splitTraits = splitForTraitName[1].split("\\+");
		if (splitTraits.length == 1) return;
		for (String trait : splitTraits) {
			if (trait.contains("makeAbstract")) {
				String splitted[] = trait.split("\\[");
				if (splitted.length == 1) return;
				composedTraits.add(splitted[0].trim());
				String abstractMethods = splitted[1].replace("]", "").replace("makeAbstract ", "");
				for (String abstractMethod : abstractMethods.split(",")) {
					removedMethods.add(createEntry(splitted[0].trim(), abstractMethod.trim()));
				}
			} else {
				composedTraits.add(trait.trim());
			}
		}	
	}

	private Entry<String, String> createEntry(String trait, String abstractMethod) {
		return new AbstractMap.SimpleEntry<>(trait, abstractMethod);
	}

	private void readClass(String javaFileContent) {
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(javaFileContent);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;

		if (compilationUnit.getClassifiers().isEmpty()
				|| compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return;
		}
		
		if (compilationUnit.getClassifiers().get(0) instanceof ClassImpl) {
//			ClassImpl javaClass = (ClassImpl) compilationUnit.getClassifiers().get(0);
//			for (Member member : javaClass.getMembers()) {
//				if (member instanceof ClassMethod) {
//					handleMethod((ClassMethod) member, javaClass.getName(), false);
//				}
//				
//			}
		} else if (compilationUnit.getClassifiers().get(0) instanceof InterfaceImpl) {
			InterfaceImpl javaInterfaceWithDefault = (InterfaceImpl) compilationUnit.getClassifiers().get(0);
			generateClassForKey(javaFileContent.replaceFirst("interface", "class"), javaInterfaceWithDefault.getName());
			
			for (Member member : javaInterfaceWithDefault.getMembers()) {
				if (member instanceof Field) {
					if (!fields.contains(JavaResourceUtil.getText(member))) {
						fields.add(JavaResourceUtil.getText(member));
					}
				} else if (member instanceof ClassMethod) {
					handleMethod((ClassMethod) member, javaInterfaceWithDefault.getName(), false);
				} else if (member instanceof InterfaceMethod) {
					handleMethod((InterfaceMethod) member, javaInterfaceWithDefault.getName(), true);
				}
			}
		}
	}

	private void handleMethod(org.emftext.language.java.members.Method emfMethod, String className, boolean isAbstract) {
		//parse JML contract to pre- and postconditions of cbcFormula
		String defaultAnnotation = "	/*@\r\n" + "	  @ public normal_behavior\r\n"
				+ "	  @ requires true;\r\n" + "	  @ ensures true;\r\n" + "	  @ assignable \\nothing;\r\n"
				+ "	  @*/";
		String jmlAnnotation = emfMethod.getAnnotationsAndModifiers().get(0).getLayoutInformations()
				.get(0).getHiddenTokenText();
		if (!jmlAnnotation.contains("/*@"))
			jmlAnnotation = defaultAnnotation;
		int index = 0;

		List<String> preCondition = new ArrayList<>();
		List<String> postCondition = new ArrayList<>();
		do {
			String currentJmlPart = "";
			index = jmlAnnotation.indexOf("also");
			if (index != -1) {
				currentJmlPart = jmlAnnotation.substring(0, index);
			} else {
				currentJmlPart = jmlAnnotation;
			}
			jmlAnnotation = jmlAnnotation.substring(index + 4);

			preCondition.add(getPreCondition(currentJmlPart));
			postCondition.add(getPostCondition(currentJmlPart));

		} while (index != -1);
		Method method = new Method(emfMethod, emfMethod.getName(), className, isAbstract, preCondition, postCondition,
				null, getParameters(emfMethod), getReturnType(emfMethod));
		methods.add(method);
	}

	private List<String> getParameters(org.emftext.language.java.members.Method classMethod) {
		List<String> parameters = new ArrayList<String>();
		// add parameters to variables
		for (Parameter p : classMethod.getParameters()) {
			parameters.add(getParameter((VariableImpl) p));
		}
		return parameters;
	}
	
	private String getReturnType(org.emftext.language.java.members.Method classMethod) {
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
			typeString = JavaResourceUtil.getText(type) + arrayDimensions;
			return typeString;
		}
		return "void";
	}


	/**
	 * adds the pre and post condition from jmlAnnotation to formula
	 * 
	 * @param formula
	 * @param jmlAnnotation contains pre and post condition for formula
	 * @param variables
	 * @param conditions
	 */
	private String getPreCondition(String jmlAnnotation) {
		//jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);

		//jmlAnnotation = cbcWorkaroundForOldKeyword(jmlAnnotation, variables, conditions);

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
		return pre;
	}
	
	private String getPostCondition(String jmlAnnotation) {
		//jmlAnnotation = replaceSpecialSymbols(jmlAnnotation);
		//jmlAnnotation = cbcWorkaroundForOldKeyword(jmlAnnotation, variables, conditions);
		// adds post condition
		int startPost = jmlAnnotation.indexOf("ensures");
		String post = "";
		while (startPost != -1) {
			int endPost = findEnd(jmlAnnotation, startPost);
			String currentPost = jmlAnnotation.substring(startPost + 8, endPost);
			post = post + " & " + currentPost;
			startPost = jmlAnnotation.indexOf("ensures", endPost);
		}
		// delete first &
		post = post.substring(2);
		return post;
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
			if (!typeOfVariable.isEmpty()) {
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
		jmlAnnotation = jmlAnnotation.replace("= true", "= TRUE");
		jmlAnnotation = jmlAnnotation.replace("= false", "= FALSE");

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
				jmlAnnotation = firstPart + "((" + secondPart + ") -> (" + thirdPart + "))"
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
				jmlAnnotation = firstPart + "((" + secondPart + ") & (" + thirdPart + "))"
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
			s = s.replaceAll("\\bdefault\\b", " ");
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// adds variable to the list of JavaVariables
	public String getParameter(VariableImpl variable) {
		String arrayTokens = "";
		if (variable.getArrayDimensionsBefore().size() > 0) {
			for (int k = 0; k < variable.getArrayDimensionsBefore().size(); k++) {
				for (int j = 0; j < variable.getArrayDimensionsBefore().get(k).getLayoutInformations().size(); j++) {
					arrayTokens = arrayTokens + variable.getArrayDimensionsBefore().get(k).getLayoutInformations()
							.get(j).getVisibleTokenText();
				}
			}
		}
		String type;
		if (variable.getTypeReference().getLayoutInformations().size() > 0) {
			type = variable.getTypeReference().getLayoutInformations().get(0).getVisibleTokenText();
		} else {
			type = variable.getTypeReference().getPureClassifierReference().getLayoutInformations().get(0)
					.getVisibleTokenText();
		}
		return type + arrayTokens + " " + variable.getName();
	}
}