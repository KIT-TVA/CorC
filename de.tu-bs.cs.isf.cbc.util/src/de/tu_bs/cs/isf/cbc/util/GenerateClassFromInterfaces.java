package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.exceptions.NotImplementedException;

public class GenerateClassFromInterfaces {
	private List<Method> methods = new ArrayList<Method>();
	private List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
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
	
	private void verifyImplications(String path) throws NotImplementedException {
		for (Method abstractMethod : abstractMethods) {
			for (Method concreteMethod : concreteMethods) {
				if (abstractMethod.getMethodName().equals(concreteMethod.getMethodName())) {
					for (int i = 0; i < abstractMethod.getPreCondition().size(); i++) {
						JavaVariables vars = parametersToJavaVars(abstractMethod);
						GlobalConditions conds = CbcmodelFactory.eINSTANCE.createGlobalConditions();
						// TODO: pass a diagram into ProveWithKey for this to work.
						throw new NotImplementedException("");
						/*
						ProveWithKey proveImplication = new ProveWithKey(null, diagram, null, new FileUtil(path), "/src_key");
						proveImplication.proveCImpliesCWithKey(createConditionForKeY(abstractMethod.getPreCondition().get(i), vars, conds), createConditionForKeY(concreteMethod.getPreCondition().get(i), vars, conds));
						proveImplication.proveCImpliesCWithKey(createConditionForKeY(concreteMethod.getPostCondition().get(i), vars, conds), createConditionForKeY(abstractMethod.getPostCondition().get(i), vars, conds));*/
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
		for (FieldDeclaration field : fields) {
			for (VariableDeclarator var : field.getVariables()) {
				builder.append(var.getTypeAsString() + " " + var.getNameAsString());
				builder.append("\n");
			}
		}
		
		checkForConsistency(concreteMethods);

		for (Method method : concreteMethods) {
			builder.append(getJMLSpecification(method.getMethodDeclaration()));
			//TODO: Was wird hier gemacht?
			builder.append(method.createSignature());
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
				builder.append(getJMLSpecification(method.getMethodDeclaration()));
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

	private String getJMLSpecification(MethodDeclaration methodDeclaration) {
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
		CompilationUnit compilationUnit = StaticJavaParser.parse(javaFileContent);
		if (compilationUnit.getChildNodes().isEmpty()) {
			return;
		}
		
		ClassOrInterfaceCollector collector = new ClassOrInterfaceCollector();
		collector.visit(compilationUnit, null);
		
		if (collector.getClassOrInterfaceDeclaration().isInterface()) {
			generateClassForKey(javaFileContent.replaceFirst("interface", "class"), collector.getClassOrInterfaceDeclaration().getNameAsString());
			
			for (FieldDeclaration fieldDeclaration : collector.getFields()) {
				if(!fields.contains(fieldDeclaration)) {
					fields.add(fieldDeclaration);
				}
			}
			
			for (MethodDeclaration methodDeclaration : collector.getMethods()) {
				handleMethod(methodDeclaration, collector.getClassOrInterfaceDeclaration().getNameAsString(), methodDeclaration.isAbstract());
			}
		}	
	}

	private void handleMethod(MethodDeclaration methodDeclaration, String className, boolean isAbstract) {
		//parse JML contract to pre- and postconditions of cbcFormula
		String jmlAnnotation = getJMLSpecification(methodDeclaration);
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
		Method method = new Method(methodDeclaration, methodDeclaration.getNameAsString(), className, isAbstract, preCondition, postCondition,
				null, getParameters(methodDeclaration), methodDeclaration.getTypeAsString());
		methods.add(method);
	}

	private List<String> getParameters(MethodDeclaration methodDeclaration) {
		List<String> parameters = new ArrayList<String>();
		// add parameters to variables
		for (Parameter p : methodDeclaration.getParameters()) {
			parameters.add(getParameter(p));
		}
		return parameters;
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

	private String cbcWorkaroundForOldKeyword(String jmlAnnotation, JavaVariables variables, GlobalConditions conditions) {
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
	//TODO: kann das so stimmen?
	public String getParameter(Parameter p) {
		return p.getTypeAsString() + " " + p.getNameAsString();
	}
}