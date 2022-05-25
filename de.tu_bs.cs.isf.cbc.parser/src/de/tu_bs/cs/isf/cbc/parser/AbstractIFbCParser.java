package de.tu_bs.cs.isf.cbc.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.UnaryExpr;

import de.tu_bs.cs.isf.cbc.cbcmodel.MethodLink;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.Field;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCBinaryExpression;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCDeclassifyEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAccessEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAssignEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCNewEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class AbstractIFbCParser {

	protected static IFbCReferenceEntity parseMethodCallExpr(	final Map<String, IFbCReferenceEntity> diagramVariables,
																final Map<String, String> changedTypes,
																final Map<String, JavaClass> javaClassesForProject,
																final MethodCallExpr methodCallExpr,
																final String projectName,
																final Method constructingMethod)
			throws IFbCException {
		final Lattice lattice = Lattices.getLatticeForProject(projectName);
		final Optional<Expression> scope = methodCallExpr.getScope();
		String methodCallName = methodCallExpr.getNameAsString();

		// if (!scope.isPresent() || !scope.get().isNameExpr()) {
		// throw new IFbCException(
		// "No scope set for method call, cannot determine the origin of the method.");
		// }

		if (scope.isPresent()) {
			if (scope.get().isNameExpr()) {
				// only one scope, i.e. f.doIt()

				final String scopeName = scope.get().asNameExpr().getNameAsString();
				if (diagramVariables.get(scopeName) != null) {
					final IFbCReferenceEntity declarationFromDiagram = diagramVariables.get(scopeName);
					final String declarationName = declarationFromDiagram.getName();
					final List<IFbCReferenceEntity> scopes = new ArrayList<>();
					scopes.add(declarationFromDiagram);

					final JavaClass javaClass;
					// Has the type of the variable changed at runtime?

					// DEBUG
					System.out.println(
							"Checking if the type has changed for variable (single scope): " + declarationName);
					changedTypes.forEach((k, v) -> {
						System.out.println("Changed type for reference/scope " + k + " to type: " + v);
					});

					if (changedTypes.containsKey(declarationName)) {
						System.out.println("Using type " + changedTypes.get(declarationName) + " instead of type "
								+ declarationFromDiagram.getType() + " from diagram for variable " + declarationName);
						javaClass = javaClassesForProject.get(changedTypes.get(declarationName));
						System.out.println(javaClass.toString());
					} else {
						javaClass = javaClassesForProject.get(declarationFromDiagram.getType());
						System.out.println(javaClass.toString());
					}

					return JavaStatementParser.parseMethodEntity(methodCallExpr, javaClass, changedTypes, scopes,
							diagramVariables, javaClassesForProject, projectName, constructingMethod);
				} else if (javaClassesForProject.get(scopeName) != null) {
					// Static method call from scope == java class
					final JavaClass javaClass = javaClassesForProject.get(scopeName);
					final List<IFbCReferenceEntity> scopes = new ArrayList<>();
					final IFbCReferenceEntity scopeEntity = new IFbCReferenceEntity(scopeName,
							lattice.getBottom().getName(), MDF.MUTABLE, scopeName);
					scopes.add(scopeEntity);
					return JavaStatementParser.parseMethodEntity(methodCallExpr, javaClass, changedTypes, scopes,
							diagramVariables, javaClassesForProject, projectName, constructingMethod);
				} else {
					throw new IFbCException(scopeName + " is not defined in the list of variables of this diagram.");
				}
			} else if (scope.get().isFieldAccessExpr()) {
				// multiple scopes, i.e. f.a.doIt()

				final FieldAccessExpr fieldAccessExpr = scope.get().asFieldAccessExpr();
				final List<String> scopeTokens = new LinkedList<>(
						Arrays.asList(fieldAccessExpr.toString().split("\\.")));
				final String declarationName = fieldAccessExpr.toString();

				if (scopeTokens.size() < 2) {
					throw new IFbCException("Field access should exist of a minimum of two tokens, got "
							+ scopeTokens.size() + ": " + scopeTokens);
				}

				// Get scopes and scope entity declaring were the method is declared
				IFbCReferenceEntity previousEntity = null;
				final List<IFbCReferenceEntity> scopes = new ArrayList<>(scopeTokens.size() - 1);
				for (String token : scopeTokens) {
					String type;
					String securityLevel;
					MDF mdf;

					if (previousEntity == null) {

						if (diagramVariables.get(token) != null) {
							final IFbCReferenceEntity declarationFromDiagram = diagramVariables.get(token);
							type = declarationFromDiagram.getType();
							securityLevel = declarationFromDiagram.getSecurityLevel();
							mdf = declarationFromDiagram.getMutationModifier();
						} else if (javaClassesForProject.get(token) != null) {
							// Static method call from scope == java class
							final JavaClass javaClass = javaClassesForProject.get(token);
							type = javaClass.getClassName();
							securityLevel = lattice.getBottom().getName();
							mdf = MDF.IMMUTABLE;
						} else {
							throw new IFbCException(token
									+ " is not defined in the list of variables of this diagram and is not a class defined in the project.");
						}
					} else {
						final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
						final Optional<Field> field = javaClass.getFieldForFieldName(token);
						if (!field.isPresent()) {
							throw new IFbCException(
									"Cannot find field " + token + " in type " + previousEntity.getType() + ".");
						}
						type = field.get().getFieldType();
						securityLevel = field.get().getSecurityLevel();
						mdf = field.get().getMutationModifier();
					}

					previousEntity = new IFbCReferenceEntity(token, securityLevel, mdf, type);
					scopes.add(previousEntity);
				}

				final JavaClass javaClass;
				// Has the type of the variable changed at runtime?

				// DEBUG
				System.out
						.println("Checking if the type has changed for variable (multiple scopes): " + declarationName);
				changedTypes.forEach((k, v) -> {
					System.out.println("Changed type for reference/scope " + k + " to type: " + v);
				});

				if (changedTypes.containsKey(declarationName)) {
					System.out.println("Using type " + changedTypes.get(declarationName) + " instead of type "
							+ previousEntity.getType() + " from diagram for variable " + declarationName);
					javaClass = javaClassesForProject.get(changedTypes.get(declarationName));
				} else {
					javaClass = javaClassesForProject.get(previousEntity.getType());
				}
				if (javaClass == null) {
					throw new IFbCException(changedTypes.containsKey(declarationName)
							? changedTypes.get(declarationName)
							: previousEntity.getType() + " is not defined in the src directory of this project.");
				}

				return JavaStatementParser.parseMethodEntity(methodCallExpr, javaClass, changedTypes, scopes,
						diagramVariables, javaClassesForProject, projectName, constructingMethod);
			} else if (scope.get().isMethodCallExpr()) {
				// multiple scopes, i.e. f.a.doIt()

				final MethodCallExpr methodCall = scope.get().asMethodCallExpr();
				final String methodCallString = methodCall.toString();
				final List<String> scopeTokens = new LinkedList<>(Arrays.asList(methodCallString.split("\\.")));

				if (scopeTokens.size() < 2) {
					throw new IFbCException("Method call should exist of a minimum of two tokens, got "
							+ scopeTokens.size() + ": " + scopeTokens);
				}

				final String declarationName = methodCallString.substring(0, methodCallString.lastIndexOf('.'));
				final String scopeMethodName = scopeTokens.get(scopeTokens.size() - 1);
				scopeTokens.remove(scopeTokens.size() - 1);

				// Get scopes and scope entity declaring were the method is declared
				IFbCReferenceEntity previousEntity = null;
				final List<IFbCReferenceEntity> scopes = new ArrayList<>(scopeTokens.size() - 1);
				for (String token : scopeTokens) {
					String type;
					String securityLevel;
					MDF mdf;

					if (previousEntity == null) {
						final IFbCReferenceEntity IFbCReferenceEntity = diagramVariables.get(token);
						type = IFbCReferenceEntity.getType();
						securityLevel = IFbCReferenceEntity.getSecurityLevel();
						mdf = IFbCReferenceEntity.getMutationModifier();
					} else {
						final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
						System.out.println("Current class: " + javaClass.toString());
						final Method method = javaClass.getMethodForMethodCallExpr(methodCall, diagramVariables);

						if (method == null) {
							throw new IFbCException("Method " + methodCall.getNameAsString() + " with parameters "
									+ methodCall.getArguments().toString()
									+ " is not defined or types are not matching.");
						}

						type = method.getReturnType();
						securityLevel = method.getSecurityLevel();
						mdf = method.getMutationModifier();
					}

					previousEntity = new IFbCReferenceEntity(token, securityLevel, mdf, type);
					scopes.add(previousEntity);
				}

				final JavaClass javaClass;
				// Has the type of the variable changed at runtime?

				// DEBUG
				System.out
						.println("Checking if the type has changed for variable (multiple scopes): " + declarationName);
				changedTypes.forEach((k, v) -> {
					System.out.println("Changed type for reference/scope " + k + " to type: " + v);
				});

				if (changedTypes.containsKey(declarationName)) {
					System.out.println("Using type " + changedTypes.get(declarationName) + " instead of type "
							+ previousEntity.getType() + " from diagram for variable " + declarationName);
					javaClass = javaClassesForProject.get(changedTypes.get(declarationName));
				} else {
					javaClass = javaClassesForProject.get(previousEntity.getType());
				}
				if (javaClass == null) {
					throw new IFbCException(changedTypes.containsKey(declarationName)
							? changedTypes.get(declarationName)
							: previousEntity.getType() + " is not defined in the src directory of this project.");
				}

				return JavaStatementParser.parseMethodEntity(methodCallExpr, javaClass, changedTypes, scopes,
						diagramVariables, javaClassesForProject, projectName, constructingMethod);
			} else {
				throw new IFbCException("Expression not yet supported as scope: " + scope.toString());
			}
		} else {
			System.out.println("Method call has no scope: " + methodCallExpr.toString());
			if (methodCallName.equals("declassify")) {
				if (methodCallExpr.getArguments().size() != 1) {
					throw new IFbCException("declassify() can only have one parameter argument: "
							+ methodCallExpr.getArguments().toString());
				}
				final Expression argument = methodCallExpr.getArgument(0);
				if (argument.isNameExpr()) {
					final String argumentName = argument.asNameExpr().getNameAsString();
					final IFbCReferenceEntity entity = diagramVariables.get(argumentName);
					if (entity == null) {
						throw new IFbCException("Argument " + argumentName + " is not defined in diagram variables.");
					}
					return new IFbCDeclassifyEntity(entity.getType(), entity.getName(), entity.getSecurityLevel(),
							entity.getMutationModifier());
				} else if (argument.isFieldAccessExpr()) {
					final FieldAccessExpr fieldAccessExpr = argument.asFieldAccessExpr();
					final IFbCFieldAccessEntity fieldAccessEntity = parseFieldAccessEntity(fieldAccessExpr, diagramVariables, javaClassesForProject);
					if (fieldAccessEntity == null) {
						throw new IFbCException("Argument " + argument.toString() + " is not a working field access expression.");
					}
					return new IFbCDeclassifyEntity(fieldAccessEntity.getType(), fieldAccessEntity.getName(), fieldAccessEntity.getSecurityLevel(),
							fieldAccessEntity.getCombinedMDF());
				} else {
					throw new IFbCException("Expression not yet supported in declassify(): " + argument.toString());
				}
			} else {
				if (constructingMethod == null) {
					throw new IFbCException("Static method call but no MethodLink element in diagram defined.");
				}
				final String definingClassName = constructingMethod.getDefiningClass();
				final JavaClass clazz = JavaClasses.getJavaClassesForProject(projectName).get(definingClassName);
				if (clazz == null) {
					throw new IFbCException("No class for name " + definingClassName + " defined in source code.");
				}

				return JavaStatementParser.parseMethodEntity(methodCallExpr, clazz, changedTypes, null,
						diagramVariables, javaClassesForProject, projectName, constructingMethod);
			}
		}
	}

	protected static IFbCFieldAssignEntity parseFieldAssignEntity(	final FieldAccessExpr fieldAccessExpr,
																	final Map<String, IFbCReferenceEntity> diagramVariables,
																	final Map<String, JavaClass> javaClassesForProject)
			throws IFbCException {
		final List<String> scopeTokens = new LinkedList<>(Arrays.asList(fieldAccessExpr.toString().split("\\.")));

		if (scopeTokens.size() < 2) {
			throw new IFbCException("Field access should exist of a minimum of two tokens, got " + scopeTokens.size()
					+ ": " + scopeTokens);
		}

		final String valueName = scopeTokens.get(scopeTokens.size() - 1);
		scopeTokens.remove(scopeTokens.size() - 1);
		IFbCReferenceEntity previousEntity = null;
		final List<IFbCReferenceEntity> scopes = new ArrayList<>(scopeTokens.size() - 1);
		for (String token : scopeTokens) {
			String type;
			String securityLevel;
			MDF mdf;

			if (previousEntity == null) {
				final IFbCReferenceEntity IFbCReferenceEntity = diagramVariables.get(token);
				type = IFbCReferenceEntity.getType();
				securityLevel = IFbCReferenceEntity.getSecurityLevel();
				mdf = IFbCReferenceEntity.getMutationModifier();
			} else {
				final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
				final Optional<Field> field = javaClass.getFieldForFieldName(token);
				if (!field.isPresent()) {
					throw new IFbCException(
							"Cannot find field " + token + " in type " + previousEntity.getType() + ".");
				}
				type = field.get().getFieldType();
				securityLevel = field.get().getSecurityLevel();
				mdf = field.get().getMutationModifier();
			}

			previousEntity = new IFbCReferenceEntity(token, securityLevel, mdf, type);
			scopes.add(previousEntity);
		}

		final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
		final Optional<Field> field = javaClass.getFieldForFieldName(valueName);
		if (!field.isPresent()) {
			throw new IFbCException("Cannot find field " + valueName + " in type " + previousEntity.getType() + ".");
		}

		return new IFbCFieldAssignEntity(valueName, field.get().getSecurityLevel(), field.get().getMutationModifier(),
				field.get().getFieldType(), scopes);
	}

	protected static IFbCMethodEntity parseMethodEntity(final MethodCallExpr methodCallExpr,
														final JavaClass javaClass,
														final Map<String, String> changedTypes,
														final List<IFbCReferenceEntity> scopes,
														final Map<String, IFbCReferenceEntity> diagramVariables,
														final Map<String, JavaClass> javaClassesForProject,
														final String projectName,
														final Method constructingMethod)
			throws IFbCException {
		final Lattice lattice = Lattices.getLatticeForProject(projectName);

		// Get all parameters of method call
		final List<ParameterDefinition> parameters = new ArrayList<>(methodCallExpr.getArguments().size());
		for (Expression a : methodCallExpr.getArguments()) {
			if (!a.isNameExpr() 
					&& !a.isLiteralExpr() 
					&& !a.isMethodCallExpr() 
					&& !a.isFieldAccessExpr() 
					&& !a.isIntegerLiteralExpr() 
					&& !a.isUnaryExpr()) {
				throw new IFbCException(
						"Argument of method call should be name, literal or method call, got: " + a.toString());
			}
			if (a.isNameExpr()) {
				final NameExpr nameExpr = a.asNameExpr();
				final IFbCReferenceEntity entity = diagramVariables.get(nameExpr.getNameAsString());
				if (entity == null) {
					throw new IFbCException(
							"Cannot find variable " + nameExpr.getNameAsString() + " in diagram variables.");
				}
				final ParameterDefinition param = new ParameterDefinition(nameExpr.getNameAsString(),
						entity.getSecurityLevel(), entity.getMutationModifier(), entity.getType());
				parameters.add(param);
			} else if (a.isLiteralExpr() || a.isIntegerLiteralExpr()) {
				final LiteralExpr literalExpr = a.asLiteralExpr();
				final String literalString = literalExpr.toString();
				final String literalType = getLiteralType(literalExpr);
				final ParameterDefinition param = new ParameterDefinition(literalString, lattice.getBottom().getName(),
						MDF.IMMUTABLE, literalType);
				parameters.add(param);
			} else if (a.isMethodCallExpr()) {
				final MethodCallExpr methodCallParameterExpr = a.asMethodCallExpr();
				final IFbCMethodEntity parsedMethodCall = (IFbCMethodEntity) parseMethodCallExpr(diagramVariables,
						changedTypes, javaClassesForProject, methodCallParameterExpr, projectName, constructingMethod);
				if (parsedMethodCall.getDefinedMethod().getVoidMethod()) {
					throw new IFbCException("Method " + parsedMethodCall.getName()
							+ " used as a parameter is void and has no return type.");
				}
				final ParameterDefinition param = new ParameterDefinition(methodCallParameterExpr.getNameAsString(),
						parsedMethodCall.getSecurityLevel(), parsedMethodCall.getMutationModifier(),
						parsedMethodCall.getType(), parsedMethodCall);
				parameters.add(param);
			} else if (a.isFieldAccessExpr()) {
				final FieldAccessExpr fieldAccessExpr = a.asFieldAccessExpr();
				final IFbCFieldAccessEntity fieldAccessEntity = parseFieldAccessEntity(fieldAccessExpr, diagramVariables, javaClassesForProject);
				final ParameterDefinition param = new ParameterDefinition(fieldAccessExpr.getNameAsString(),
						fieldAccessEntity.getSecurityLevel(), fieldAccessEntity.getMutationModifier(),
						fieldAccessEntity.getType(), fieldAccessEntity);
				parameters.add(param);
			} else if (a.isUnaryExpr()) {
				final UnaryExpr unaryExpr = a.asUnaryExpr();
				final String literalString = unaryExpr.toString();
				final String literalType = getLiteralType(unaryExpr.getExpression().asLiteralExpr());
				final ParameterDefinition param = new ParameterDefinition(literalString, lattice.getBottom().getName(),
						MDF.IMMUTABLE, literalType);
				parameters.add(param);
			}
		}

		System.out.println("Current class: " + javaClass.toString());
		final Method definedMethod = javaClass.getMethodByNameAndParameterSize(methodCallExpr.getNameAsString(),
				parameters);
		if (definedMethod == null) {
			throw new IFbCException("Method " + methodCallExpr.getNameAsString() + " with parameters "
					+ methodCallExpr.getArguments().toString() + " is not defined or types are not matching.");
		}

		if (scopes == null || scopes.isEmpty()) {
			// static method
			return new IFbCMethodEntity(definedMethod.getName(), definedMethod.getSecurityLevel(),
					definedMethod.getMutationModifier(), definedMethod.getReturnType(), javaClass.getClassName(),
					scopes, "", // static method has no receiver
					null, // static method has no receiver
					parameters, definedMethod, methodCallExpr.toString());
		} else {
			// non-static method
			final IFbCReferenceEntity definingScope = scopes.get(scopes.size() - 1);
			return new IFbCMethodEntity(definedMethod.getName(), definedMethod.getSecurityLevel(),
					definedMethod.getMutationModifier(), definedMethod.getReturnType(), javaClass.getClassName(),
					scopes, definingScope.getSecurityLevel(), // receiver SL at runtime
					definingScope.getMutationModifier(), // receiver MDF at runtime
					parameters, definedMethod, methodCallExpr.toString());
		}
	}

	public static String getLiteralType(final LiteralExpr literalExpr) {
		if (literalExpr.isBooleanLiteralExpr()) {
			return "boolean";
		} else if (literalExpr.isCharLiteralExpr()) {
			return "char";
		} else if (literalExpr.isDoubleLiteralExpr()) {
			return "double";
		} else if (literalExpr.isIntegerLiteralExpr()) {
			return "int";
		} else if (literalExpr.isLongLiteralExpr()) {
			return "long";
		} else if (literalExpr.isNullLiteralExpr()) {
			return "Object";
		} else if (literalExpr.isStringLiteralExpr()) {
			return "String";
		}
		return "NotSupported";
	}

	protected static IFbCFieldAccessEntity parseFieldAccessEntity(	final FieldAccessExpr fieldAccessExpr,
																	final Map<String, IFbCReferenceEntity> diagramVariables,
																	final Map<String, JavaClass> javaClassesForProject)
			throws IFbCException {
		final List<String> scopeTokens = new LinkedList<>(Arrays.asList(fieldAccessExpr.toString().split("\\.")));

		if (scopeTokens.size() < 2) {
			throw new IFbCException("Field access should exist of a minimum of two tokens, got " + scopeTokens.size()
					+ ": " + scopeTokens);
		}

		final String valueName = scopeTokens.get(scopeTokens.size() - 1);
		scopeTokens.remove(scopeTokens.size() - 1);
		IFbCReferenceEntity previousEntity = null;
		final List<IFbCReferenceEntity> scopes = new ArrayList<>(scopeTokens.size() - 1);
		for (String token : scopeTokens) {
			String type;
			String securityLevel;
			MDF mdf;

			if (previousEntity == null) {
				final IFbCReferenceEntity IFbCReferenceEntity = diagramVariables.get(token);
				type = IFbCReferenceEntity.getType();
				securityLevel = IFbCReferenceEntity.getSecurityLevel();
				mdf = IFbCReferenceEntity.getMutationModifier();
			} else {
				final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
				final Optional<Field> field = javaClass.getFieldForFieldName(token);
				if (!field.isPresent()) {
					throw new IFbCException(
							"Cannot find field " + token + " in type " + previousEntity.getType() + ".");
				}
				type = field.get().getFieldType();
				securityLevel = field.get().getSecurityLevel();
				mdf = field.get().getMutationModifier();
			}

			previousEntity = new IFbCReferenceEntity(token, securityLevel, mdf, type);
			scopes.add(previousEntity);
		}

		final JavaClass javaClass = javaClassesForProject.get(previousEntity.getType());
		final Optional<Field> field = javaClass.getFieldForFieldName(valueName);
		if (!field.isPresent()) {
			throw new IFbCException("Cannot find field " + valueName + " in type " + previousEntity.getType() + ".");
		}

		return new IFbCFieldAccessEntity(valueName, field.get().getSecurityLevel(), field.get().getMutationModifier(),
				field.get().getFieldType(), scopes);
	}

	protected static IFbCNewEntity parseNewEntity(	final ObjectCreationExpr objectCreationExpr,
													final Map<String, IFbCReferenceEntity> diagramVariables,
													final Map<String, JavaClass> javaClassesForProject,
													final Lattice lattice)
			throws IFbCException {
		// Get all parameters of object creation
		final List<ParameterDefinition> parameters = new ArrayList<>(objectCreationExpr.getArguments().size());
		for (Expression a : objectCreationExpr.getArguments()) {
			if (a.isNameExpr()) {
				final NameExpr nameExpr = a.asNameExpr();
				final IFbCReferenceEntity entity = diagramVariables.get(nameExpr.getNameAsString());
				if (entity == null) {
					throw new IFbCException(
							"Cannot find variable " + nameExpr.getNameAsString() + " in diagram variables.");
				}
				final ParameterDefinition param = new ParameterDefinition(nameExpr.getNameAsString(),
						entity.getSecurityLevel(), entity.getMutationModifier(), entity.getType());
				parameters.add(param);
			} else if (a.isObjectCreationExpr()) {
				final ObjectCreationExpr paramObjectCreation = a.asObjectCreationExpr();
				final IFbCNewEntity paramNewEntitiy = parseNewEntity(paramObjectCreation, diagramVariables,
						javaClassesForProject, lattice);
				final ParameterDefinition param = new ParameterDefinition(paramNewEntitiy.getName(),
						paramNewEntitiy.getSecurityLevel(), MDF.IMMUTABLE, paramNewEntitiy.getType(), paramNewEntitiy);
				parameters.add(param);
			} else if (a.isNullLiteralExpr()) {
				final ParameterDefinition param = new ParameterDefinition("null", lattice.getBottom().getName(),
						MDF.IMMUTABLE, "null");
				parameters.add(param);
			} else {
				throw new IFbCException("Arugment of object creation not implemented yet: " + a.toString());
			}
		}

		return new IFbCNewEntity("new " + objectCreationExpr.getTypeAsString(), "none", MDF.IMMUTABLE,
				objectCreationExpr.getTypeAsString(), parameters);
	}

	static String analyzeSLForBinaryExpression(	final BinaryExpr binaryExpr,
												final Lattice lattice,
												final String projectName,
												final Map<String, IFbCReferenceEntity> diagramVariables,
												final Map<String, String> changedTypes,
												final Method constructingMethod)
			throws IFbCException {
		final Expression left = binaryExpr.getLeft();
		final Expression right = binaryExpr.getRight();

		final String leftSl = JavaConditionParser.analyzeSLForExpression(left, lattice, projectName, diagramVariables,
				changedTypes, constructingMethod);
		final String rightSl = JavaConditionParser.analyzeSLForExpression(right, lattice, projectName, diagramVariables,
				changedTypes, constructingMethod);
		final Node leftSlNode = lattice.getNodePerName(leftSl);
		final Node rightSlNode = lattice.getNodePerName(rightSl);

		if (leftSlNode == null || rightSlNode == null) {
			System.out.println("Could not build lub() of leftSl (" + leftSl + ") and rightSl (" + rightSl
					+ ") since one or both SLs are not defined in the current lattice for this project. Returning lattice top for safety reasons.");
			return "";
		}
		return LeastUpperBound.leastUpperBound(Arrays.asList(leftSlNode, rightSlNode), lattice).getName();
	}
	
	static IFbCBinaryExpression parseBinaryExpression(final BinaryExpr binaryExpr,
			final Lattice lattice,
			final String projectName,
			final Map<String, IFbCReferenceEntity> diagramVariables,
			final Map<String, String> changedTypes,
			final Method constructingMethod)
					throws IFbCException {
		final Expression left = binaryExpr.getLeft();
		final Expression right = binaryExpr.getRight();
		
		final String leftSl = JavaConditionParser.analyzeSLForExpression(left, lattice, projectName, diagramVariables,
		changedTypes, constructingMethod);
		final String rightSl = JavaConditionParser.analyzeSLForExpression(right, lattice, projectName, diagramVariables,
		changedTypes, constructingMethod);
		final Node leftSlNode = lattice.getNodePerName(leftSl);
		final Node rightSlNode = lattice.getNodePerName(rightSl);
		
		if (leftSlNode == null || rightSlNode == null) {
			throw new IFbCException("Could not build lub() of leftSl (" + leftSl + ") and rightSl (" + rightSl
		+ ") since one or both SLs are not defined in the current lattice for this project. Returning lattice top for safety reasons.");
		}
		
		String sl = LeastUpperBound.leastUpperBound(Arrays.asList(leftSlNode, rightSlNode), lattice).getName();
		String type = "";
		if (binaryExpr.getLeft().isNameExpr()) {
			type = diagramVariables.get(binaryExpr.getLeft().toString()).getType();
		} else if (binaryExpr.getLeft().isFieldAccessExpr()) {
			final FieldAccessExpr fieldAccessExpr = binaryExpr.getLeft().asFieldAccessExpr();
			final IFbCFieldAccessEntity fieldAccessEntity = parseFieldAccessEntity(fieldAccessExpr, diagramVariables,
					JavaClasses.getJavaClassesForProject(projectName));
			type = fieldAccessEntity.getType();
		}
		if (isBoolean(binaryExpr.getOperator())) {
			type = "boolean";
		}
		
		return new IFbCBinaryExpression(binaryExpr.toString(), sl, MDF.IMMUTABLE, type);
}

	private static boolean isBoolean(Operator operator) {
		switch(operator) {
		case LESS: 
		case BINARY_AND:
		case BINARY_OR:
		case EQUALS:
		case GREATER:
		case GREATER_EQUALS:
		case LESS_EQUALS:
		case NOT_EQUALS:
		case OR:
		case XOR: return true;
		default:
			return false;
		}
	}

	static String analyzeSLForExpression(	final Expression expression,
											final Lattice lattice,
											final String projectName,
											final Map<String, IFbCReferenceEntity> diagramVariables,
											final Map<String, String> changedTypes,
											final Method constructingMethod)
			throws IFbCException {
		if (expression.isBinaryExpr()) {
			return AbstractIFbCParser.analyzeSLForBinaryExpression(expression.asBinaryExpr(), lattice, projectName,
					diagramVariables, changedTypes, constructingMethod);
		}
		final String exprString = expression.toString();
		if (expression.isLiteralExpr()) {
			System.out.println("Expression " + exprString + " is literal expression");
			return lattice.getBottom().getName();
		}
		if (expression.isNameExpr()) {
			System.out.println("Expression " + exprString + " is name expression");
			if (changedTypes.get(exprString) != null) {
				return changedTypes.get(exprString);
			}
			final IFbCReferenceEntity referenceEntity = diagramVariables.get(exprString);
			return referenceEntity.getSecurityLevel();
		}
		if (expression.isFieldAccessExpr()) {
			System.out.println("Expression " + exprString + " is field access expression");
			final FieldAccessExpr fieldAccessExpr = expression.asFieldAccessExpr();
			final IFbCFieldAccessEntity fieldAccessEntity = parseFieldAccessEntity(fieldAccessExpr, diagramVariables,
					JavaClasses.getJavaClassesForProject(projectName));

			return fieldAccessEntity.getSecurityLevel();
		}
		if (expression.isMethodCallExpr()) {
			System.out.println("Expression " + exprString + " is method call expression");
			final MethodCallExpr methodCallExpr = expression.asMethodCallExpr();
			final IFbCReferenceEntity methodCallEntity = parseMethodCallExpr(diagramVariables, changedTypes,
					JavaClasses.getJavaClassesForProject(projectName), methodCallExpr, projectName, constructingMethod);
			final String returnType;
			final String returnSL;
			if (methodCallEntity instanceof IFbCMethodEntity) {
				final IFbCMethodEntity methodEntity = (IFbCMethodEntity) methodCallEntity;
				returnType = methodEntity.getType();
				returnSL = methodEntity.getSecurityLevel();
			} else if (methodCallEntity instanceof IFbCDeclassifyEntity) {
				final IFbCDeclassifyEntity declassifyEntity = (IFbCDeclassifyEntity) methodCallEntity;
				returnType = declassifyEntity.getType();
				returnSL = declassifyEntity.getSecurityLevel();
			} else {
				returnType = "";
				returnSL = "";
			}

			return returnSL;
		}

		System.out.println("Exception " + exprString + " is not supported.");
		return null;
	}
}
