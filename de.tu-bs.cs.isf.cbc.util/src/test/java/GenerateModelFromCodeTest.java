package test.java;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.ClassOrInterfaceCollector;
import de.tu_bs.cs.isf.cbc.util.GenerateModelFromCode;
import de.tu_bs.cs.isf.cbc.util.StatementsCollector;

public class GenerateModelFromCodeTest {
	private GenerateModelFromCode generateModelFromCode;
	private ClassOrInterfaceCollector collector;
	private String javaClass;
	
	@BeforeEach
	void before() {
		this.generateModelFromCode = new GenerateModelFromCode();
		this.collector = new ClassOrInterfaceCollector();
		this.javaClass = null;
	}
	
	@Test
	void testAddFieldToList() {
		javaClass = "public class Main {\n" +
                "private int[][][] field1;\n" +
                "protected String field2;\n" +
                "static int[] field3;\n" +
                "}";
		
		Field field1 = CbcclassFactory.eINSTANCE.createField();
		field1.setName("field1");
		field1.setType("int[][][]");
		field1.setVisibility(Visibility.PRIVATE);
		
		Field field2 = CbcclassFactory.eINSTANCE.createField();
		field2.setName("field2");
		field2.setType("String");
		field2.setVisibility(Visibility.PROTECTED);
		
		Field field3 = CbcclassFactory.eINSTANCE.createField();
		field3.setName("field3");
		field3.setType("int[]");
		field3.setIsStatic(true);
		
		EList<Field> testFields = new BasicEList<Field>();
		testFields.add(field1);
		testFields.add(field2);
		testFields.add(field3);
		
		CompilationUnit compilationUnit = StaticJavaParser.parse(javaClass);
		collector.visit(compilationUnit, null);
		
		for (FieldDeclaration fieldDeclaration : collector.getFields()) {
			generateModelFromCode.addFieldToList(fieldDeclaration);
		}
		
		assertEquals(testFields.size(), generateModelFromCode.getFields().size());
		
		for (int i = 0; i < testFields.size(); i++) {
			assertEquals(testFields.get(i).getName(), generateModelFromCode.getFields().get(i).getName());
			assertEquals(testFields.get(i).getType(), generateModelFromCode.getFields().get(i).getType());
			assertEquals(testFields.get(i).getVisibility(), generateModelFromCode.getFields().get(i).getVisibility());
			assertEquals(testFields.get(i).isIsStatic(), generateModelFromCode.getFields().get(i).isIsStatic());
		}
	}
	
	@Test
	void testFillVariableList() {
		javaClass = "public class Main {\n" +
                "public int[][] uselessMethod(int a, int[][] b, String s, float f) {\n" +
				"return b;\n" +
                "}\n" +
				"private void print(String s) {\n" +
                "System.out.println(s);\n" +
				"}\n" +
                "}";
		
		JavaVariable var1 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var1.setName("int a");
		var1.setKind(VariableKind.PARAM);
		
		JavaVariable var2 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var2.setName("int[][] b");
		var2.setKind(VariableKind.PARAM);
		
		JavaVariable var3 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var3.setName("String s");
		var3.setKind(VariableKind.PARAM);
		
		JavaVariable var4 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var4.setName("float f");
		var4.setKind(VariableKind.PARAM);
		
		JavaVariable var5 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var5.setName("int[][] ret");
		var5.setKind(VariableKind.RETURN);
		
		JavaVariable var6 = CbcmodelFactory.eINSTANCE.createJavaVariable();
		var6.setName("String s");
		var6.setKind(VariableKind.PARAM);
		
		JavaVariables testVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		testVariables.getVariables().add(var1);
		testVariables.getVariables().add(var2);
		testVariables.getVariables().add(var3);
		testVariables.getVariables().add(var4);
		testVariables.getVariables().add(var5);
		testVariables.getVariables().add(var6);
		
		CompilationUnit compilationUnit = StaticJavaParser.parse(javaClass);
		collector.visit(compilationUnit, null);
		
		JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();
		
		for (MethodDeclaration methodDeclaration : collector.getMethods()) {
			generateModelFromCode.fillVariableList(variables, methodDeclaration);
		}
		
		assertEquals(testVariables.getVariables().size(), variables.getVariables().size());
		
		for (int i = 0; i < variables.getVariables().size(); i++) {
			assertEquals(testVariables.getVariables().get(i).getName(), variables.getVariables().get(i).getName());
			assertEquals(testVariables.getVariables().get(i).getKind(), variables.getVariables().get(i).getKind());
		}
	}
	
	@Test
	void testHandleListOfStatements() throws ExecutionException {
		javaClass = "public class Main {\n" +
	            "private String field;\n" +
	            "private String field2;\n" +
	            "private static int[] field3;\n" +
	            "\n" +
	            "/*comment for method test*/\n" +
	            "public int test(int[][][] a, bool f, List<String> list) {\n" +
	            "//inner comment method test\n" +
	            "for (int a = 3; a < 99; a++) {\n" +
	            "int b = 1;\n" +
	            "}\n" +
	            "switch(x) { case x < 5 && x > 4: case 6: a=100; break; case 9: a=33; break; default: System.out.println(\"Hello World\");};\n" +
	            "int hallo = 0;\n" +
	            "System.out.println(\"Hello World\");\n" +
	            "return 1;\n" +
	            "}\n" +
	            "\n" +
	            "private class inner {\n" +
	            "int j = 1;\n" +
	            "}\n" +
	            "\n" +
	            "private class inner2 {\n" +
	            "int x = 1;\n" +
	            "}\n" +
	            "\n" +
	            "private class inner3 {\n" +
	            "int y = 1;\n" +
	            "}\n" +
	            "\n" +
	            "public int test2() {\n" +
	            "return 1;\n" +
	            "}\n" +
	            "\n" +
	            "/*orphan comment*/\n" +
	            "}";
		
		CompilationUnit compilationUnit = StaticJavaParser.parse(javaClass);
		collector.visit(compilationUnit, null);
		
		//IFile mockedFile = mock(IFile.class);
		//generateModelFromCode.setupProjectStructure(mockedFile);
		
		for (MethodDeclaration methodDeclaration : collector.getMethods()) {
			Method method = CbcclassFactory.eINSTANCE.createMethod();
			
			Resource cbcmodelResource = generateModelFromCode.setupProjectForCbCModel(method, methodDeclaration.getNameAsString());
			
			EList<Statement> listOfStatements = new BasicEList<Statement>();
			//TODO: check if assert statements are collected
			StatementsCollector statementsCollector = new StatementsCollector();
			statementsCollector.visit(methodDeclaration, null);
			for (int j = 0; j < statementsCollector.getStatements().size(); j++) {
				listOfStatements.add(null);
			}
			
			CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
			formula.setName(methodDeclaration.getNameAsString());
			AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
			statement.setName("statement");
			formula.setStatement(statement);
			
			generateModelFromCode.handleListOfStatements(cbcmodelResource, listOfStatements, formula.getStatement());
		}
		
	}
	

}
