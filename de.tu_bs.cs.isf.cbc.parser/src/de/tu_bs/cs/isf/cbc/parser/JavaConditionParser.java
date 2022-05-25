package de.tu_bs.cs.isf.cbc.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Delayed;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.DotPrinter;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodLink;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCDeclassifyEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAccessEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

public class JavaConditionParser extends AbstractIFbCParser {

	public static String parseJavaCondition(final String projectName,
											final String condition,
											final Map<String, IFbCReferenceEntity> diagramVariables,
											final Map<String, String> changedTypes,
											final Method constructingMethod) {
		final String string = wrapIntoJavaClass(condition);
		final CompilationUnit cu = StaticJavaParser
				.parse(string);
		DotPrinter printer = new DotPrinter(true);
		try (FileWriter fileWriter = new FileWriter("ast.dot");
				final PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.print(printer.output(cu));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		final Lattice lattice = Lattices.getLatticeForProject(projectName);
		
		final List<String> sl = new ArrayList<>();
		cu.accept(new VoidVisitorAdapter<List<String>>() {

			@Override
			public void visit(IfStmt i, List<String> sl) {
				super.visit(i, sl);

				// For stuff like negations extract the expression from the unary expression and forget about the unary operator
				Expression condition = i.getCondition();
				if (condition.isUnaryExpr()) {
					condition = condition.asUnaryExpr().getExpression();
				}
				if (condition.isBinaryExpr()) {
					try {
						sl.add(AbstractIFbCParser.analyzeSLForBinaryExpression(condition.asBinaryExpr(), lattice, projectName, diagramVariables, changedTypes, constructingMethod));
					} catch (IFbCException e) {
						System.out.println("Error parsing binary condition " + condition.toString() + ": " + e.getMessage());
					}
				} else if (condition.isMethodCallExpr()) {
					try {
						sl.add(analyzeSLForExpression(condition.asMethodCallExpr(), lattice, projectName, diagramVariables, changedTypes, constructingMethod));
					} catch (IFbCException e) {
						System.out.println("Error parsing method call exception Object " + condition.toString() + ": " + e.getMessage());
					}
				} else {
					System.out.println(condition + " is no binary or method call expression.");
					return;
				}
			}

		}, sl);

		return sl.isEmpty() ? null : sl.get(0);
	}

	private static String wrapIntoJavaClass(final String condition) {
		return "class TestClass { void testMethod() { if (" + condition + " ) {} } }";
	}

	public static Map<String, String> parseSpecification(String condition) {
		final String string = wrapIntoJavaClass(condition);
		final CompilationUnit cu = StaticJavaParser.parse(string);
		
		final Map<String, String> specification = new HashMap<>();
		cu.accept(new VoidVisitorAdapter<Map<String, String>>() {

			@Override
			public void visit(IfStmt i, Map<String, String> specification) {
				super.visit(i, specification);
				final Expression condition = i.getCondition();
				if (condition.isBinaryExpr()) {
					final BinaryExpr binaryExpr = condition.asBinaryExpr();			
					parseSpecificationFromBinary(binaryExpr, specification);
				} else {
					System.out.println(condition + " is no binary expression.");
					return;
				}
			}
			

			
			private void parseSpecificationFromBinary(final BinaryExpr binaryExpr, 
			                                          final Map<String, String> specification) {

				final Expression left = binaryExpr.getLeft();
				final Expression right = binaryExpr.getRight();
				
				if (binaryExpr.getOperator().equals(Operator.AND)) {					
					parseSpecificationFromExpression(left, specification);
					parseSpecificationFromExpression(right, specification);
				}
				
				if (binaryExpr.getOperator().equals(Operator.EQUALS)) {
					if (!left.isNameExpr() || !right.isNameExpr()) {
						System.out.println("Only name expression allowed in specification. (left: " + left + ", right: " + right + ")" );
					}
					
					final NameExpr referenceName = left.asNameExpr();
					final NameExpr securityLevel = right.asNameExpr();
					specification.put(referenceName.getNameAsString(), securityLevel.getNameAsString());
				} else {
					System.out.println("Unsupported operator for specification, only '==' is allowed.");
				}
			}
			
			private void parseSpecificationFromExpression(final Expression expr, 
			                                              final Map<String, String> specification) {		
				if (expr.isBinaryExpr()) {
					parseSpecificationFromBinary(expr.asBinaryExpr(), specification);
				} else {
					System.out.println(expr + " is no binary expression.");
				}
			}

		}, specification);

		return specification;
	}
}
