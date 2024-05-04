package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class GenerateModelWithoutContract {

	public GenerateModelWithoutContract() {
	}

	public void execute(IFile iFile) {
		String javaFileContent = readFileToString(iFile.getLocation().toPortableString());

		CompilationUnit compilationUnit = StaticJavaParser.parse(javaFileContent);
		if (compilationUnit.getChildNodes().isEmpty()) {
			return;
		}
		
		ClassOrInterfaceCollector collector = new ClassOrInterfaceCollector();
		collector.visit(compilationUnit, null);

		List<String> names = new ArrayList<String>();
		if (!collector.getClassOrInterfaceDeclaration().isInterface()) {
			JavaVariables globalVariables = CbcmodelFactory.eINSTANCE.createJavaVariables();
			// new cbcmodel is created for each method in class
			for (FieldDeclaration fieldDeclaration : collector.getFields()) {
				NodeList<VariableDeclarator> vars = fieldDeclaration.getVariables();
				JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				javaVariable.setName(vars.get(0).getTypeAsString() + " " + vars.get(0).getNameAsString());
				globalVariables.getVariables().add(javaVariable);
			}
			for (MethodDeclaration methodDeclaration : collector.getMethods()) {
				JavaVariables variables = CbcmodelFactory.eINSTANCE.createJavaVariables();

				// add parameters to variables
				for (Parameter p : methodDeclaration.getParameters()) {
					addToVariables(new VariableDeclarator(p.getType(), p.getName()), variables);
				}
				// add global variables to variables
				for (int j = 0; j < globalVariables.getVariables().size(); j++) {
					JavaVariable newVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
					newVariable.setName(globalVariables.getVariables().get(j).getName());
					variables.getVariables().add(newVariable);
				}

				if (!methodDeclaration.getType().isVoidType()) {
					JavaVariable variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
					variable.setName(methodDeclaration.getTypeAsString() + " result");
					variables.getVariables().add(variable);
				}

				// Initialize the Model
				CbcmodelPackage.eINSTANCE.eClass();
				// Register Resource Factory for respective Model
				Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
				Map<String, Object> m = reg.getExtensionToFactoryMap();
				m.put("cbcmodel", new XMIResourceFactoryImpl());

				String potentialName = methodDeclaration.getNameAsString();
				String name = collector.getClassOrInterfaceDeclaration().getNameAsString() + "_" + findName(names, potentialName);
				ResourceSet rs = new ResourceSetImpl();
				IPath path = iFile.getLocation().removeLastSegments(1);

			

				// Create Resource and load respective Model Instance
				Resource r = rs.createResource(URI.createFileURI(path + "\\" + name + ".cbcmodel"));
				CbCFormula formula = createFormula(methodDeclaration.getNameAsString());
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

				if (methodDeclaration.getBody().isPresent()) {
					EList<Statement> listOfStatements = new BasicEList<Statement>();
					for (int j = 0; j < methodDeclaration.getBody().get().getStatements().size(); j++) {
						listOfStatements.add(null);
					}
					Collections.copy(listOfStatements, methodDeclaration.getBody().get().getStatements());
					handleListOfStatements(r, listOfStatements, formula.getStatement());
				}

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
		if (statement.isExpressionStmt()) {
			if (statement.asExpressionStmt().getExpression().isVariableDeclarationExpr()) {
				VariableDeclarationExpr variableStmt = statement.asExpressionStmt().getExpression().asVariableDeclarationExpr();
				NodeList<VariableDeclarator> varDec = variableStmt.getVariables();
				String text = varDec.get(0).toString();
				if (text.contains("=")) {
					String firstPart = text.substring(0, text.indexOf("="));
					int index = firstPart.lastIndexOf(varDec.get(0).getNameAsString());
					text = text.substring(index);
					AbstractStatement s = createStatement(text + ";");
					parent.setRefinement(s);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
				} else {
					SkipStatement skipStatement = createSkipStatement();
					parent.setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
				}
				addToVariables(varDec.get(0), (JavaVariables) r.getContents().get(1));
			} else {
				ExpressionStmt expressionStmt = statement.asExpressionStmt();
				AbstractStatement s = createStatement(expressionStmt.getExpression().toString() + ";");
				parent.setRefinement(s);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
			}
		} else if (statement.isWhileStmt()) {
			WhileStmt whileStmt = statement.asWhileStmt();
			String conditionString = whileStmt.getCondition().toString();
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
			parent.setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, repStatement);
			if (whileStmt.getBody().isBlockStmt()) {
				EList<Statement> whileBlock = new BasicEList<Statement>();
				for (Statement stmt : whileStmt.getBody().asBlockStmt().getStatements()) {
					whileBlock.add(stmt);
				}
				handleListOfStatements(r, whileBlock, repStatement.getLoopStatement());
			}
		} else if (statement.isIfStmt()) {
			IfStmt ifStat = statement.asIfStmt();
			Optional<Statement> elseStat = ifStat.getElseStmt();
			// also nicht mehrere else ifs
			if (elseStat.isPresent() && !elseStat.get().isIfStmt()) {
				String conditionString = ifStat.getCondition().toString();
				conditionString = conditionString.replace("==", "=");
				conditionString = conditionString.replace("&&", "&");
				conditionString = conditionString.replace("||", "|");
				SelectionStatement selStatement = createSimpleSelection(conditionString,
						("!" + "(" + conditionString + ")"));
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				EList<Statement> ifBlock = new BasicEList<Statement>();
				if (ifStat.getThenStmt().isBlockStmt()) {
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						ifBlock.add(stmt);
					}
					handleListOfStatements(r, ifBlock, selStatement.getCommands().get(0));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					parent.setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
				}
				EList<Statement> elseBlock = new BasicEList<Statement>();
				if (elseStat.get().isBlockStmt()) {
					for (Statement stmt : elseStat.get().asBlockStmt().getStatements()) {
						elseBlock.add(stmt);
					}
					handleListOfStatements(r, elseBlock, selStatement.getCommands().get(1));
				} else {
					SkipStatement skipStatement = createSkipStatement();
					selStatement.getCommands().get(1).setRefinement(skipStatement);
					UpdateConditionsOfChildren.updateRefinedStatement(selStatement.getCommands().get(1), skipStatement);
				}
			} else {
				SelectionStatement selStatement = createMultiSelection(ifStat.getCondition().toString());
				parent.setRefinement(selStatement);
				UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
				EList<Statement> selBlock = new BasicEList<Statement>();
				if (ifStat.getThenStmt().isBlockStmt()) {
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						selBlock.add(stmt);
					}
					handleListOfStatements(r, selBlock, selStatement.getCommands().get(0));
				}
				int i = 1;
				while (ifStat.getElseStmt().isPresent() && ifStat.getElseStmt().get().isIfStmt()) {
					IfStmt nextCondition = elseStat.get().asIfStmt();
					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(nextCondition.getCondition().toString());
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					if (ifStat.getThenStmt().isBlockStmt()) {
						EList<Statement> privBlockStmts = new BasicEList<Statement>();
						for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
							privBlockStmts.add(stmt);
						}
						handleListOfStatements(r, privBlockStmts, selStatement.getCommands().get(i));
					}
					i++;
					ifStat = nextCondition;
				}
				if (ifStat.getElseStmt().isPresent() && ifStat.getElseStmt().get().isBlockStmt()) {
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
					EList<Statement> privBlockStmts = new BasicEList<Statement>();
					for (Statement stmt : ifStat.getThenStmt().asBlockStmt().getStatements()) {
						privBlockStmts.add(stmt);
					}
					handleListOfStatements(r, privBlockStmts, selStatement.getCommands().get(i));
				}
			}

		} else if (statement.isReturnStmt()) {
			ReturnStmt returnStmt = statement.asReturnStmt();
			ReturnStatement retStatement = createReturnStatement(
					"result = " + returnStmt.getExpression().get().toString() + ";");
			parent.setRefinement(retStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, retStatement);
		} else if (statement.isExpressionStmt()) {
			ExpressionStmt expressionStmt = statement.asExpressionStmt();
			AbstractStatement s = createStatement(expressionStmt.getExpression().toString() + ";");
			parent.setRefinement(s);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, s);
		} else if (statement.isForStmt()) {
			ForStmt forStmt = statement.asForStmt();

			CompositionStatement composition = createComposition();
			parent.setRefinement(composition);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, composition);

			// Initialization as first part of composition
			String init = forStmt.getInitialization().get(0).toString();
			AbstractStatement s = createStatement(init + ";");
			composition.getFirstStatement().setRefinement(s);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getFirstStatement(), s);

			// new Composition for actual repetition block and loop variable update
			CompositionStatement composition2 = createComposition();
			composition.getSecondStatement().setRefinement(composition2);
			UpdateConditionsOfChildren.updateRefinedStatement(composition.getSecondStatement(), composition2);
			String conditionString = "";
			if (forStmt.getCompare().isPresent()) {
				conditionString = forStmt.getCompare().toString();
			}
			conditionString = conditionString.replace("==", "=");
			conditionString = conditionString.replace("&&", "&");
			conditionString = conditionString.replace("||", "|");
			SmallRepetitionStatement repStatement = createRepetition(conditionString);
			composition2.getFirstStatement().setRefinement(repStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getFirstStatement(), repStatement);

			// loop variable update, prüfen, ob ich mehrere updates haben kann
			String update = forStmt.getUpdate().get(0).toString();
			AbstractStatement updateStatement = createStatement(update + ";");
			composition2.getSecondStatement().setRefinement(updateStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(composition2.getSecondStatement(), updateStatement);

			EList<Statement> forBlock = new BasicEList<Statement>();
			if (forStmt.getBody().isBlockStmt()) {
				for (Statement stmt : forStmt.getBody().asBlockStmt().getStatements()) {
					forBlock.add(stmt);
				}
				handleListOfStatements(r, forBlock, repStatement.getLoopStatement());
			}
		} else if (statement.isSwitchStmt()) {
			SwitchStmt switchStmt = statement.asSwitchStmt();
			String switchVariable = switchStmt.getSelector().toString();
			Expression firstCondition = null;
			SwitchEntry sc = null;

			if (switchStmt.getEntry(0).getType().equals(SwitchEntry.Type.STATEMENT_GROUP)) {
				sc = switchStmt.getEntry(0);
				Optional<Expression> label = sc.getLabels().getFirst();
				if (label.isPresent()) {
					firstCondition = label.get();
				}
			}
			SelectionStatement selStatement = createMultiSelection(
					switchVariable + " = " + firstCondition.toString());
			parent.setRefinement(selStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
			EList<Statement> switchStmts = new BasicEList<Statement>();
			for (Statement stmt : sc.getStatements()) {
				switchStmts.add(stmt);
			}
			handleListOfStatements(r, switchStmts, selStatement.getCommands().get(0));

			for (int i = 1; i < switchStmt.getEntries().size(); i++) {
				if (switchStmt.getEntry(i).getType().equals(SwitchEntry.Type.STATEMENT_GROUP) && switchStmt.getEntry(i).getLabels().isNonEmpty()) {
					SwitchEntry normalCase = switchStmt.getEntry(i);
					Expression condition = null;
					Optional<Expression> label = normalCase.getLabels().getFirst();
					if (label.isPresent()) {
						condition = label.get();
					}
					
					AbstractStatement nextStatement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
					nextStatement.setName("statement");
					selStatement.getCommands().add(nextStatement);
					Condition conditionNext = CbcmodelFactory.eINSTANCE.createCondition();
					conditionNext.setName(switchVariable + " = " + condition.toString());
					selStatement.getGuards().add(conditionNext);
					Condition nextPre = CbcmodelFactory.eINSTANCE.createCondition();
					nextPre.setName("");
					nextStatement.setPreCondition(nextPre);
					Condition nextPost = CbcmodelFactory.eINSTANCE.createCondition();
					nextPost.setName("");
					nextStatement.setPostCondition(nextPost);
					UpdateConditionsOfChildren.updateRefinedStatement(parent, selStatement);
					UpdateConditionsOfChildren.updateConditionsofChildren(nextPre);
					EList<Statement> nonEmptySwitchStmts = new BasicEList<Statement>();
					for (Statement stmt : normalCase.getStatements()) {
						nonEmptySwitchStmts.add(stmt);
					}
					handleListOfStatements(r, nonEmptySwitchStmts, nextStatement);

				} else if (switchStmt.getEntry(i).getType().equals(SwitchEntry.Type.STATEMENT_GROUP)) {
					SwitchEntry defaultCase = switchStmt.getEntry(i);
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
					EList<Statement> maybeEmtpySwtichStmt = new BasicEList<Statement>();
					for (Statement stmt : defaultCase.getStatements()) {
						maybeEmtpySwtichStmt.add(stmt);
					}
					handleListOfStatements(r, maybeEmtpySwtichStmt, nextStatement);
				}
			}
		} else if(statement.isEmptyStmt()) {
			SkipStatement skipStatement = createSkipStatement();
			parent.setRefinement(skipStatement);
			UpdateConditionsOfChildren.updateRefinedStatement(parent, skipStatement);
		}

	}

	// adds variable to the list of JavaVariables
	public void addToVariables(VariableDeclarator varDec, JavaVariables variableList) {
		JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
		javaVariable.setName(varDec.getTypeAsString() + " " + varDec.getNameAsString());
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
