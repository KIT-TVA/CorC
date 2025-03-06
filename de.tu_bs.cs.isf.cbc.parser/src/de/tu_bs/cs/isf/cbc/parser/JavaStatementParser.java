package de.tu_bs.cs.isf.cbc.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.DotPrinter;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCBinaryExpression;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCDeclassifyEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCAssignment;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCMethod;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCReference;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCReturn;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCStatement;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

public class JavaStatementParser extends AbstractIFbCParser {

	public static List<IFbCStatement> parseJavaStatement(final String projectName, final String statement,
			final Map<String, IFbCReferenceEntity> diagramVariables, final Map<String, String> changedTypes,
			final Method constructingMethod) {
		final List<IFbCStatement> statemements = new ArrayList<>();
		final CompilationUnit cu = StaticJavaParser.parse("class TestClass { void testMethod() { " + statement + "} }");
		DotPrinter printer = new DotPrinter(true);
		try (FileWriter fileWriter = new FileWriter("ast.dot");
				final PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.print(printer.output(cu));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Search wrapping testMesthod()
		cu.accept(new VoidVisitorAdapter<List<IFbCStatement>>() {

			@Override
			public void visit(MethodDeclaration n, List<IFbCStatement> statements) {
				super.visit(n, statements);
				// System.out.println("Method name: " + n.getNameAsString());
				if (!n.getNameAsString().equals("testMethod")) {
					System.out.println(n.getNameAsString() + "is not the desired method");
					return;
				}

				if (!n.getBody().isPresent()) {
					System.out.println("No body present");
					return;
				}

				// Analyze Statements
				n.getBody().get().getStatements().forEach(s -> {
					System.out.println(s.toString());

					if (s.isExpressionStmt()) {
						final ExpressionStmt exprStmt = s.asExpressionStmt();
						final Expression expr = exprStmt.getExpression();

						try {
							final IFbCStatement statement = checkExpression(expr, projectName, diagramVariables,
									changedTypes, constructingMethod);
							statements.add(statement);
						} catch (IFbCException e) {
							System.out.println("Error parsing expression " + expr + ": " + e.getMessage());
						}
					} else if (s.isReturnStmt()) {
						System.out.println("Creating IFbCReturnStatement");
						final ReturnStmt returnStmt = s.asReturnStmt();
						final Optional<Expression> optionalExpression = returnStmt.getExpression();
						if (optionalExpression.isPresent()) {
							final Expression expr = optionalExpression.get();

							try {
								final IFbCStatement statement = checkExpression(expr, projectName, diagramVariables,
										changedTypes, constructingMethod);
								if (statement != null) {
									final IFbCReturn ifbcReturn = new IFbCReturn(statement.getStatement(), statement);
									statements.add(ifbcReturn);
								}
							} catch (IFbCException e) {
								System.out.println("Error parsing expression " + expr + ": " + e.getMessage());
							}
						} else {
							System.out.println("Return statement without expression, not parsing any further.");
						}
					} else {
						System.out.println("Currently no support for statement " + s.toString());
					}
				});
			}

		}, statemements);

		return statemements;
	}

	private static IFbCStatement checkExpression(final Expression expr, final String projectName,
			final Map<String, IFbCReferenceEntity> diagramVariables, final Map<String, String> changedTypes,
			final Method constructingMethod) throws IFbCException {
		final Lattice lattice = Lattices.getLatticeForProject(projectName);
		final Map<String, JavaClass> javaClassesForProject = JavaClasses.getJavaClassesForProject(projectName);
		if (javaClassesForProject == null) {
			throw new IFbCException("JavaClasses is null.");
		}

		// Assignment
		if (expr.isAssignExpr()) {
			final AssignExpr assignExpr = expr.asAssignExpr();
			final Operator operator = assignExpr.getOperator();
			final Expression value = assignExpr.getValue();
			final Expression target = assignExpr.getTarget();

			System.out.println("Operator: " + operator.toString());
			System.out.println("Target: " + target.toString());
			System.out.println("Value: " + value.toString());

			IFbCReferenceEntity targetEntity = null;
			IFbCReferenceEntity valueEntity = null;

			/**
			 * ANALYZE TARGET OF ASSIGNMENT
			 */

			// Target is (possibly nested) field access expression
			if (target.isFieldAccessExpr()) {
				// Target ist a field access expression, meaning we have a field assing
				final FieldAccessExpr fieldAccessExpr = target.asFieldAccessExpr();
				targetEntity = parseFieldAssignEntity(fieldAccessExpr, diagramVariables, javaClassesForProject);
			} else if (target.isNameExpr()) {
				// Target ist just a name reference
				targetEntity = diagramVariables.get(target.toString());
			} else {
				throw new IFbCException("Expression not yet supported as target: " + target.toString());
			}

			/**
			 * ANALYZE VALUE OF ASSIGNMENT
			 */

			if (value.isNameExpr()) {
				// Value is just a name reference
				valueEntity = diagramVariables.get(value.toString());
			} else if (value.isMethodCallExpr()) {
				// Value is a method call
				final MethodCallExpr methodCallExpr = value.asMethodCallExpr();
				valueEntity = parseMethodCallExpr(diagramVariables, changedTypes, javaClassesForProject, methodCallExpr,
						projectName, constructingMethod);
			} else if (value.isFieldAccessExpr()) {
				// Assignment value is field access
				final FieldAccessExpr fieldAccessExpr = value.asFieldAccessExpr();
				valueEntity = JavaStatementParser.parseFieldAccessEntity(fieldAccessExpr, diagramVariables,
						javaClassesForProject);
			} else if (value.isObjectCreationExpr()) {
				final ObjectCreationExpr objectCreationExpr = value.asObjectCreationExpr();
				valueEntity = JavaStatementParser.parseNewEntity(objectCreationExpr, diagramVariables,
						javaClassesForProject, lattice);
			} else if (value.isBooleanLiteralExpr()) {
				valueEntity = new IFbCReferenceEntity("false", lattice.getBottom().getName(), MDF.IMMUTABLE,
						"BooleanLiteral", true);
			} else if (value.isBinaryExpr()) {
				BinaryExpr binaryExpr = value.asBinaryExpr();
				IFbCBinaryExpression binaryExprEntity = parseBinaryExpression(binaryExpr, lattice, projectName,
						diagramVariables, changedTypes, constructingMethod);
				valueEntity = binaryExprEntity;
			} else if (value.isIntegerLiteralExpr()) {
				IntegerLiteralExpr integerExp = value.asIntegerLiteralExpr();
				valueEntity = new IFbCReferenceEntity(integerExp.getValue(), lattice.getBottom().getName(),
						MDF.IMMUTABLE, "int", false);
			} else {
				throw new IFbCException("Expression not yet supported as value: " + value.toString());
			}

			if (targetEntity == null || valueEntity == null) {
				throw new IFbCException("targetEntity or valueEntity is null.");
			}

			return new IFbCAssignment(targetEntity, valueEntity, expr.toString());
		} else if (expr.isMethodCallExpr()) {

			final MethodCallExpr methodCallExpr = expr.asMethodCallExpr();
			final IFbCReferenceEntity methodCall = parseMethodCallExpr(diagramVariables, changedTypes,
					javaClassesForProject, methodCallExpr, projectName, constructingMethod);
			if (methodCall instanceof IFbCDeclassifyEntity) {
				System.out.println("Method call outside of assignment should not be declassify(), ignoring this call.");
				return null;
			}
			return new IFbCMethod(methodCall.getName(), (IFbCMethodEntity) methodCall);
		} else if (expr.isNameExpr()) {
			// Expression could be a simple name expression as part of a return statement
			final IFbCReferenceEntity entity = diagramVariables.get(expr.toString());
			return new IFbCReference(entity);
		} else if (expr.isBooleanLiteralExpr()) {
			// Expression could be a boolean literal as part of a return statement
			final BooleanLiteralExpr booleanLiteralExpr = expr.asBooleanLiteralExpr();
			final IFbCReferenceEntity entity = new IFbCReferenceEntity("return " + booleanLiteralExpr.toString(),
					lattice.getBottom().getName(), MDF.IMMUTABLE, "Boolean");
			return new IFbCReference(entity);
		} else if (expr.isBinaryExpr()) {
			final BinaryExpr binaryExpr = expr.asBinaryExpr();
			final String binaryExprSL = analyzeSLForBinaryExpression(binaryExpr, lattice, projectName, diagramVariables,
					changedTypes, constructingMethod);
			final IFbCBinaryExpression binaryExprEntity = new IFbCBinaryExpression("return " + binaryExpr.toString(),
					binaryExprSL, MDF.IMMUTABLE, "BinaryExpression");
			return new IFbCReference(binaryExprEntity);
		} else if (expr.isNullLiteralExpr()) {
			final IFbCReferenceEntity entity = new IFbCReferenceEntity("return " + null, lattice.getBottom().getName(),
					MDF.CAPSULE, "null");
			return new IFbCReference(entity);
		} else {
			System.out.println("Support for expression " + expr.toString() + " is not implemented.");
		}

		// TODO: return other expressions for other cases
		return null;
	}

}
