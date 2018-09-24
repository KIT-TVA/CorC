package de.tu_bs.cs.isf.toolkit.support.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.expressions.Expression;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.resource.java.util.JavaResourceUtil;
import org.emftext.language.java.statements.Statement;
import org.emftext.language.java.variables.LocalVariable;


/**
 * Class to compare two methods
 * @author Tobias
 *
 */
public class CompareMethodBodies {
	
	/**
	 * Enum for the decision
	 * @author Tobias
	 *
	 */
	public enum Decision {
		INHERIT, OVERRIDE, OVERRIDECOND, EXTEND, EXTENDSUPER
	}

	/*
	 * threshold for the use of conditionals
	 */
	private static final double THRESHOLD_CONDITIONAL = 0.8;

	/**
	 * compares two methods and decides how the method should be handled
	 * @param firstMethod	the first method
	 * @param secondMethod	the second method
	 * @return	the decision for the two methods
	 */
	public static Decision compareMethodBodies(String firstMethod, String secondMethod) {
		List<String> firstTokens = new ArrayList<>();
		List<String> secondTokens = new ArrayList<>();
		List<String> sameTokens = new ArrayList<>();
		
		readBodyWithJaMoPP(firstTokens, firstMethod);
		readBodyWithJaMoPP(secondTokens, secondMethod);
//		readBodies(firstTokens, secondTokens, firstMethod, secondMethod);
		OuterLoop: for (String firstToken : firstTokens) {
			for (String secondToken : secondTokens) {
				if(firstToken.equals(secondToken) || LevenshteinCompare.calculateSimilarity(firstToken, secondToken) >= 0.9) {
					sameTokens.add(firstToken);
					continue OuterLoop;
				}
			}
		}
		List<String> secondTokensCopy = new ArrayList<>();
		secondTokensCopy.addAll(secondTokens);
		firstTokens.removeAll(sameTokens);
		secondTokens.removeAll(sameTokens);
		
		boolean useSuper = true;
		boolean begin = true;
		boolean end = false;
		for (int i = 0; i < secondTokensCopy.size(); i++) {
			if (!secondTokens.contains(secondTokensCopy.get(i)) && begin) {
				begin = false;
			} else if (secondTokens.contains(secondTokensCopy.get(i)) && !begin && !end) {
				end = true;
			} else if (!secondTokens.contains(secondTokensCopy.get(i)) && end) {
				useSuper = false;
			}
		}
		
		if (firstTokens.isEmpty() && secondTokens.isEmpty()) {
			return Decision.INHERIT;
		} else if (firstTokens.isEmpty() && !secondTokens.isEmpty()) {
			if (useSuper) {
				return Decision.EXTENDSUPER;
			} else {
				return Decision.EXTEND;
			}
		} else {
			double sizeCommon = sameTokens.size();
			double sizeOwn = firstTokens.size() + secondTokens.size();
			if (sizeCommon / (sizeCommon + sizeOwn) > THRESHOLD_CONDITIONAL) {
				return Decision.OVERRIDECOND;
			} else {
				return Decision.OVERRIDE;
			}
		}
	}
	
	/**
	 * reads two method bodies and saves the content in the two passed lists
	 * @param firstTokens	return list with the content of the first method
	 * @param secondTokens	return list with the content of the second method
	 * @param firstMethod	the first method which is processed
	 * @param secondMethod	the second method which is processed
	 * @param trim			boolean if whitespace should be trimmed
	 */
	public static void readBodies(List<String> firstTokens, List<String> secondTokens, String firstMethod, String secondMethod, boolean trim) {
		int indexBegin = firstMethod.indexOf("{");
		int indexEnd = firstMethod.lastIndexOf("}");
		if (indexBegin != -1 && indexEnd != -1) {
			firstMethod = firstMethod.substring(indexBegin + 1, indexEnd);
		}
		StringTokenizer tokenizer = new StringTokenizer(firstMethod, "\n");
		
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (trim) {
				token = token.trim();
			}
			if (!token.isEmpty()) {
				firstTokens.add(token);
			}
		}
		
		indexBegin = secondMethod.indexOf("{");
		indexEnd = secondMethod.lastIndexOf("}");
		if (indexBegin != -1 && indexEnd != -1) {
			secondMethod = secondMethod.substring(indexBegin + 1, indexEnd);
		}
		tokenizer = new StringTokenizer(secondMethod, "\n");
		
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (trim) {
				token = token.trim();
			}
			if (!token.isEmpty()) {
				secondTokens.add(token);
			}
		}
	}
	
	/**
	 * reads the body of a method using JaMoPP
	 * @param tokens	the statements of the methods in a list
	 * @param method	the method as a String
	 */
	public static void readBodyWithJaMoPP(List<String> tokens, String method) {
		method = "class Read {\n" + method + "\n}";
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(method);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;
		if (compilationUnit.getClassifiers().isEmpty()) {
			return;
		}
		if (compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return;
		}
		if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof ClassMethod) {
			ClassMethod classMethod = (ClassMethod) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			for (Statement statement : classMethod.getStatements()) {
				getChildStatements(tokens, statement);
			}
		} else if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof InterfaceMethod){
			InterfaceMethod interfaceMethod = (InterfaceMethod) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			tokens.add(JavaResourceUtil.getText(interfaceMethod));
		} else if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof Constructor){
			Constructor constructor = (Constructor) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			for (Statement statement : constructor.getStatements()) {
				getChildStatements(tokens, statement);
			}
		}
	}

	/**
	 * Gets the child statements of a statement and saves the child statements in the list
	 * @param tokens	the list where the child statements are saved
	 * @param statement	the statement which is traversed
	 */
	private static void getChildStatements(List<String> tokens, Statement statement) {
		for (EObject obj : statement.eContents()) {
			if (obj instanceof Statement) {
				Statement childStatement = (Statement) obj;
				getChildStatements(tokens, childStatement);
			} else if (obj instanceof Expression) {
				Expression expression = (Expression) obj;
//				getChildExpressions(expression);
				tokens.add(JavaResourceUtil.getText(expression));
			} else if (obj instanceof OrdinaryParameter){
				OrdinaryParameter parameter = (OrdinaryParameter) obj;
				tokens.add(JavaResourceUtil.getText(parameter));
			} else if (obj instanceof LocalVariable){
				LocalVariable variable = (LocalVariable) obj;
				tokens.add(JavaResourceUtil.getText(variable));
			}
		}
	}
	
	/**
	 * Gets the child expressions of a expression and saves the child expressions in the list
	 * @param tokens		the list where the child expressions are saved
	 * @param expressions	the expression which is traversed
	 */
	private static void getChildExpressions(List<String> tokens, Expression expression) {
		for (EObject obj : expression.eContents()) {
			 if (obj instanceof Expression) {
				Expression childExpression = (Expression) obj;
//				System.out.println(childExpression);
//				System.out.println(JavaResourceUtil.getText(childExpression));
				getChildExpressions(tokens, childExpression);
			}
		}
	}
	
	/**
	 * reads an assert statement and test if it is in a correct format
	 * @param method	the method including the assert statement
	 * @return			boolean if the assert statement is in the correct format
	 */
	public static boolean readAndTestAssertWithJaMoPP(String method) {
		method = "class Read {\n void test() {\n assert " + method + ";\n}\n}";
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(method);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;
		if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof ClassMethod) {
			ClassMethod classMethod = (ClassMethod) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			if (classMethod.getStatements().size() == 1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * reads a method and test if it is in a correct format
	 * @param method	the method
	 * @return			boolean if the method is in the correct format
	 */
	public static boolean readAndTestMethodBodyWithJaMoPP(String method) {
		method = "class Read {\n" + method + "\n}";
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(method);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;
		if (compilationUnit.getClassifiers().isEmpty()) {
			return false;
		}
		if (compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return false;
		}
		if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof ClassMethod) {
			ClassMethod classMethod = (ClassMethod) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			if (!classMethod.getStatements().isEmpty()) {
				return true;
			}
		} else if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof InterfaceMethod){
			return true;
		} else if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof Constructor){
			Constructor constructor = (Constructor) compilationUnit.getClassifiers().get(0).getMembers().get(0);
			if (!constructor.getStatements().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean readAndTestMethodBodyWithJaMoPP2(String statements) {
		String method = "class Read {\n public static void read() {\n" + statements + "\n}\n}";
		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(method);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;
		if (compilationUnit != null && compilationUnit.getClassifiers() != null) {
			if (compilationUnit.getClassifiers().isEmpty()) {
				return false;
			}
			if (compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
				return false;
			}
			if (compilationUnit.getClassifiers().get(0).getMembers().get(0) instanceof ClassMethod) {
				ClassMethod classMethod = (ClassMethod) compilationUnit.getClassifiers().get(0).getMembers().get(0);
				int counterStatements = 0;
				for (int i = 0; i < statements.length(); i++) {
					if (statements.charAt(i) == ';') counterStatements++;
				}
				if (classMethod.getStatements().size() == counterStatements) {
					return true;
				}
			}
		}
		return false;
	}
}
