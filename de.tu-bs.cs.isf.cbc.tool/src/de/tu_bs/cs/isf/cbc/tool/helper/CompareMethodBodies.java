package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.resource.java.util.JavaResourceUtil;


/**
 * Class to compare two methods
 * @author Tobias
 *
 */
public class CompareMethodBodies {

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
