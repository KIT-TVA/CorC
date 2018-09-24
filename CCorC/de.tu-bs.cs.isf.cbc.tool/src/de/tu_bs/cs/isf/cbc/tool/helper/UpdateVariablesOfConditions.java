package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ParserException;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;

public class UpdateVariablesOfConditions {

	public static void updateAssignmentStatement(AbstractStatement statement) {
		
		if (statement.getName().contains(";") && CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			List<String> preVariables = getHighVariables(statement.getPreCondition());
			List<String> declaredVariables = getDeclaredHighVariables(statement);
			copyHighVariables(statement.getPreCondition(), statement.getPostCondition());
			try {
				Map<String, Set<String>> variablesInStatement = Parser.findAllVariables(statement);
				for (String leftVariable : variablesInStatement.keySet()) {
					if (statement.getContext().equals(Confidentiality.HIGH)) {
						addHighVariable(statement.getPostCondition(), leftVariable);
					} else {
						if (!statement.getName().contains("declassify")) {
							for (String rightVariable : variablesInStatement.get(leftVariable)) {
								System.out.println(leftVariable);
								if (preVariables != null && preVariables.contains(rightVariable) || declaredVariables.contains(leftVariable)) {
									addHighVariable(statement.getPostCondition(), leftVariable);
									break;
								}
							}
							if (declaredVariables.contains(leftVariable)) {
								addHighVariable(statement.getPostCondition(), leftVariable);
							}
						}
					}
				}
				updateParent(statement);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateConfidentiality(AbstractStatement statement, Confidentiality conf) {
		statement.setContext(conf);
		if (statement instanceof CompositionStatement) {
			CompositionStatement childCompo = (CompositionStatement) statement;
			AbstractStatement firstStatement = childCompo.getFirstStatement();
			AbstractStatement secondStatement = childCompo.getSecondStatement();
			firstStatement.setContext(conf);
			if (firstStatement.getRefinement() != null) {
				updateConfidentiality(firstStatement.getRefinement(), conf);
			}
			secondStatement.setContext(conf);
			if (secondStatement.getRefinement() != null) {
				updateConfidentiality(secondStatement.getRefinement(), conf);
			}
			
		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement childRep = (SmallRepetitionStatement) statement;
			AbstractStatement loopStatement = childRep.getLoopStatement();
			if (conf.equals(Confidentiality.LOW)) {
				conf = getContextForSubStatement(childRep.getPreCondition(), childRep.getGuard());
			}
			loopStatement.setContext(conf);
			if (loopStatement.getRefinement() != null) {
				updateConfidentiality(loopStatement.getRefinement(), conf);
			}
			
		} else if (statement instanceof SelectionStatement) {
			SelectionStatement childSel = (SelectionStatement) statement;
			
			for (int i = 0; i < childSel.getCommands().size(); i++) {
				AbstractStatement childStatement = childSel.getCommands().get(i);
				if (conf.equals(Confidentiality.LOW)) {
					conf = getContextForSubStatement(childSel.getPreCondition(), childSel.getGuards().get(i));
				}
				childStatement.setContext(conf);
				if (childStatement.getRefinement() != null) {
					updateConfidentiality(childStatement.getRefinement(), conf);
				}
			}
			
		} else if (statement instanceof AbstractStatement) {
			if (statement.getRefinement() != null) {
				updateConfidentiality(statement.getRefinement(), conf);
			}
			updateAssignmentStatement(statement);
		}
	}
	
	private static Confidentiality getContextForSubStatement(Condition pre, Condition guard) {
		List<String> highVariables = getHighVariables(pre);
		List<String> declaredHighVariables = getDeclaredHighVariables((AbstractStatement) guard.eContainer());
		Set<String> guardVariables = Parser.parseRightVariables(guard.getName());
		for (String guardVariable : guardVariables) {
			if (highVariables != null && (highVariables.contains(guardVariable) || declaredHighVariables.contains(guardVariable))) {
				return Confidentiality.HIGH;
			}
		}
		return Confidentiality.LOW;
	}

	public static void updateParent(AbstractStatement statement) {
		if (statement.getParent() != null) {
			AbstractStatement statementToCopyVariables = getStatementToCopyHighVariables(statement);
			if (statement.getParent().eContainer() instanceof CompositionStatement) {
				CompositionStatement compoStatement = (CompositionStatement) statement.getParent().eContainer();
				if (statement.getParent().equals(compoStatement.getFirstStatement())) {
					copyHighVariables(statementToCopyVariables.getPostCondition(), compoStatement.getFirstStatement().getPostCondition());
					copyHighVariables(statementToCopyVariables.getPostCondition(), compoStatement.getSecondStatement().getPreCondition());
					copyHighVariables(statementToCopyVariables.getPostCondition(), compoStatement.getIntermediateCondition());
					updatePreChild(compoStatement.getSecondStatement());
					updateParent(compoStatement);
				} else if (statement.getParent().equals(compoStatement.getSecondStatement())) {
					copyHighVariables(statementToCopyVariables.getPostCondition(), compoStatement.getSecondStatement().getPostCondition());
					copyHighVariables(statementToCopyVariables.getPostCondition(), compoStatement.getPostCondition());
					updateParent(compoStatement);
				}
			} else if (statement.getParent().eContainer() instanceof CbCFormula) {
				CbCFormula formula = (CbCFormula) statement.getParent().eContainer();
				copyHighVariables(statementToCopyVariables.getPostCondition(), formula.getStatement().getPostCondition());
			} else if (statement.getParent().eContainer() instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement.getParent().eContainer();
				copyHighVariables(statementToCopyVariables.getPostCondition(), repStatement.getLoopStatement().getPostCondition());
				copyHighVariables(repStatement.getPreCondition(), repStatement.getPostCondition());
				updateParent(repStatement);
			} else if (statement.getParent().eContainer() instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) statement.getParent().eContainer();
				copyHighVariables(selStatement.getPreCondition(), selStatement.getPostCondition());
				for (AbstractStatement subStatement : selStatement.getCommands()) {
					if(statement.getParent().equals(subStatement)) {
						copyHighVariables(statementToCopyVariables.getPostCondition(), subStatement.getPostCondition());
					}
				}
				updateParent(selStatement);
			} else if (statement.getParent() instanceof StrengthWeakStatement) {
				StrengthWeakStatement parentStatement = (StrengthWeakStatement) statement.getParent();
				copyHighVariables(statementToCopyVariables.getPostCondition(), parentStatement.getPostCondition());
				updateParent(parentStatement);
			}
		}
	}

	private static void updatePreChild(AbstractStatement statement) {
		if (statement.getRefinement() != null) {
			AbstractStatement refinement = statement.getRefinement();
			copyHighVariables(statement.getPreCondition(), refinement.getPreCondition());
			if (refinement instanceof CompositionStatement) {
				CompositionStatement subCompoStatement = (CompositionStatement) refinement;
				copyHighVariables(subCompoStatement.getPreCondition(), subCompoStatement.getFirstStatement().getPreCondition());
				updatePreChild(subCompoStatement.getFirstStatement());
			} else if (refinement instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) refinement;
				for (AbstractStatement subSelStatement : selStatement.getCommands()) {
					copyHighVariables(selStatement.getPreCondition(), subSelStatement.getPreCondition());
					updatePreChild(subSelStatement);
				}
			} else if (refinement instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) refinement;
				copyHighVariables(repStatement.getPreCondition(), repStatement.getLoopStatement().getPreCondition());
				updatePreChild(repStatement.getLoopStatement());
			} else if (refinement instanceof StrengthWeakStatement) {
				updatePreChild(refinement);
			} else if (refinement instanceof AbstractStatement) {
				updateAssignmentStatement(refinement);
			}
		}
	}

	private static AbstractStatement getStatementToCopyHighVariables(AbstractStatement statement) {
		if (statement instanceof CompositionStatement) {
			return ((CompositionStatement) statement).getSecondStatement();
		}
		return statement;
	}

	private static List<String> getHighVariables(Condition condition) {
		String variables = null;
		List<String> variablesAsList = null;
		if(condition.getName().split(";").length>1) variables = condition.getName().split(";")[0];
		if(variables != null) {
			variables = variables.substring(variables.indexOf("(")+1, variables.indexOf(")"));
			variables = variables.replace(" ", "");
			variables = variables.replace(System.getProperty("line.separator"), "");
			variablesAsList = Lists.newArrayList(variables.split(","));
		}
		return variablesAsList;
	}
	
	private static void addHighVariable(Condition condition, String variable) {
		String variables = null;
		String conditionString = null;
		String[] splittedCondition = condition.getName().split(";");
		if(splittedCondition.length>1) {
			variables = splittedCondition[0];
			conditionString = splittedCondition[1];
		} else {
			conditionString = splittedCondition[0];
		}
		if(variables != null) {
			variables = variables.substring(variables.indexOf("(")+1, variables.indexOf(")"));
			variables = variables.replace(" ", "");
			variables = variables.replace(System.getProperty("line.separator"), "");
			List<String> variablesAsList = Lists.newArrayList(variables.split(","));
			if (!variablesAsList.contains(variable)) {
				variablesAsList.add(variable);
			}
			Collections.sort(variablesAsList);
			variables = "high(" + String.join(",", variablesAsList) + ")";
		} else {
			variables = "high(" + variable + ")";
		}
		condition.setName(variables + ";" + conditionString);
	}
	
	private static void copyHighVariables(Condition preCondition, Condition postCondition) {
		String variables = null;
		String[] splittedCondition = preCondition.getName().split(";");
		if(splittedCondition.length>1) {
			variables = splittedCondition[0];
		}
		String conditionString = null;
		conditionString = postCondition.getNameSplit();
		if(variables != null) {
			postCondition.setName(variables + ";" + System.getProperty("line.separator") + conditionString.trim());
		} else {
			postCondition.setName(conditionString.trim());
		}
	}
	
	private static List<String> getDeclaredHighVariables(AbstractStatement statement) {
		JavaVariables variables = null;
		List<String> variablesAsList = new ArrayList<String>();
		if (statement.eResource() != null) {
			for (EObject object : statement.eResource().getContents()) {
				if (object instanceof JavaVariables) {
					variables = (JavaVariables) object;
				}
			}
			for (JavaVariable variable : variables.getVariables()) {
				if (variable.getConfidentiality().equals(Confidentiality.HIGH)) {
					variablesAsList.add(variable.getName());
				}
			}
		}
		return variablesAsList;
	}
}
