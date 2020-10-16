package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
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

public class GenerateModelWithoutContract {

	public GenerateModelWithoutContract() {
	}

	public void execute(IFile iFile) {
		String file = readFileToString(iFile.getLocation().toPortableString());

		String javaFile = file;

		EObject abstractSyntaxTreeRoot = JavaResourceUtil.getResourceContent(javaFile);
		CompilationUnit compilationUnit = (CompilationUnit) abstractSyntaxTreeRoot;

		if (compilationUnit.getClassifiers().isEmpty()) {
			return;
		}
		if (compilationUnit.getClassifiers().get(0).getMembers().isEmpty()) {
			return;
		}

		List<String> names = new ArrayList<String>();
		if (compilationUnit.getClassifiers().get(0) instanceof ClassImpl) {
			ClassImpl javaClass = (ClassImpl) compilationUnit.getClassifiers().get(0);
			JavaVariables globalVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
			// new cbcmodel is created for each method in class
			for (Member member : javaClass.getMembers()) {
				if (member instanceof FieldImpl) {
					FieldImpl globalVariable = (FieldImpl) member;
					String arrayTokens = "";
					if (globalVariable.getArrayDimensionsBefore().size() > 0) {
						for (int k = 0; k < globalVariable.getArrayDimensionsBefore().size(); k++) {
							for (int j = 0; j < globalVariable.getArrayDimensionsBefore().get(k).getLayoutInformations()
									.size(); j++) {
								arrayTokens = arrayTokens + globalVariable.getArrayDimensionsBefore().get(k)
										.getLayoutInformations().get(j).getVisibleTokenText();
							}
						}
					}
					JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
					String type;
					if (globalVariable.getTypeReference().getLayoutInformations().size() > 0) {
						type = globalVariable.getTypeReference().getLayoutInformations().get(0).getVisibleTokenText();
					} else {
						type = globalVariable.getTypeReference().getPureClassifierReference().getLayoutInformations()
								.get(0).getVisibleTokenText();
					}
					javaVariable.setName(type + arrayTokens + " " + globalVariable.getName());
					globalVariables.getVariables().add(javaVariable);
				}
				if (member instanceof ClassMethod) {
					ClassMethod classMethod = (ClassMethod) member;

					JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();

					// add parameters to variables
					for (Parameter p : classMethod.getParameters()) {
						addToVariables((VariableImpl) p, variables);
					}
					// add global variables to variables
					for (int j = 0; j < globalVariables.getVariables().size(); j++) {
						JavaVariable newVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
						newVariable.setName(globalVariables.getVariables().get(j).getName());
						variables.getVariables().add(newVariable);
					}

					TypeReference type = classMethod.getTypeReference();
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
						variable.setName(JavaResourceUtil.getText(type) + arrayDimensions + " result");
						variables.getVariables().add(variable);
					}

					// Initialize the Model
					CbcmodelPackage.eINSTANCE.eClass();
					// Register Resource Factory for respective Model
					Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
					Map<String, Object> m = reg.getExtensionToFactoryMap();
					m.put("cbcmodel", new XMIResourceFactoryImpl());

					String potentialName = classMethod.getName();
					String name = javaClass.getName() + "_" + findName(names, potentialName);
					ResourceSet rs = new ResourceSetImpl();
					IPath path = iFile.getLocation().removeLastSegments(1);

				

					// Create Resource and load respective Model Instance
					Resource r = rs.createResource(URI.createFileURI(path + "\\" + name + ".cbcmodel"));
					CbCFormula formula = createFormula(classMethod.getName());
					GlobalConditions conditions = CbcmodelFactory.eINSTANCE.createGlobalConditions();
					JavaVariables variables2 = CbcmodelFactory.eINSTANCE.createJavaVariables();
					for (JavaVariable jv : variables.getVariables()) {
						JavaVariable var = CbcmodelFactory.eINSTANCE.createJavaVariable();
						var.setName(jv.getName());
						variables2.getVariables().add(var);
					}

					r.getContents().add(formula);
					r.getContents().add(variables2);
					r.getContents().add(conditions);

					EList<Statement> listOfStatements = new BasicEList<Statement>();
					for (int j = 0; j < classMethod.getStatements().size(); j++) {
						listOfStatements.add(null);
					}
					Collections.copy(listOfStatements, classMethod.getStatements());
					handleListOfStatements(r, listOfStatements, formula.getStatement());

					// add types to old variables in variable table
					for (JavaVariable variable : variables2.getVariables()) {
						if (variable.getName().startsWith("old_")) {
							String oldVariableName = variable.getName().substring(4);
							for (JavaVariable variable2 : variables2.getVariables()) {
								int indexName = variable2.getName().indexOf(" " + oldVariableName);
								if (indexName != -1) {
									String typeOfVariable = variable2.getName().substring(0, indexName);
									variable.setName(typeOfVariable + " " + variable.getName());
									break;
								}

							}
						}
					}

					try {
						r.save(Collections.EMPTY_MAP);
					} catch (IOException e) {
						e.printStackTrace();
					}
					GenerateDiagramFromModel gdfm = new GenerateDiagramFromModel();
					gdfm.execute(r);
				}
			}
		}
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
			addToVariables((VariableImpl) variable, (JavaVariables) r.getContents().get(1));
		} else if (statement instanceof WhileLoop) {
			WhileLoop loop = (WhileLoop) statement;
			Expression condition = loop.getCondition();
			String conditionString = JavaResourceUtil.getText(condition);
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
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
		} else if(statement instanceof EmptyStatementImpl) {
			SkipStatement skipStatement = createSkipStatement();
			parent.setRefinement(skipStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
		}

	}

	// adds variable to the list of JavaVariables
	public void addToVariables(VariableImpl variable, JavaVariables variableList) {
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

	// for more else ifs
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
