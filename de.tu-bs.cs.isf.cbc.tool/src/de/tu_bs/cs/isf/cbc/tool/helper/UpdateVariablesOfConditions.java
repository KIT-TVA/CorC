package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ParserException;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;

public class UpdateVariablesOfConditions {

	public static void updateAssignmentStatement(AbstractStatement statement) {
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			EMap<String, EList<String>> preVariables = statement.getPreCondition().getConfToVarsMap();
			Map<String, List<String>> declaredVariables = getDeclaredVariables(statement);
			copyVariables(statement.getPreCondition(), statement.getPostCondition());
			try {
				Map<String, Set<String>> variablesInStatement = Parser.findAllVariables(statement);
				for (String leftVariable : variablesInStatement.keySet()) {
					Set<String> confidentialityLevels = new HashSet<String>();
					confidentialityLevels.add(statement.getContext());
					if (!statement.getName().contains("declassify")) {
						confidentialityLevels.addAll(collectConfidentialitiesOfVariables(preVariables, variablesInStatement.get(leftVariable), declaredVariables));
					}
					String confOfLeftVariable = collectConfidentialitiesOfLeftVariables(preVariables, leftVariable);
					if (confOfLeftVariable == null) {
						confidentialityLevels.add(collectConfidentialitiesOfVariablesInDeclaredVariables(declaredVariables, leftVariable));
					} else {
						confidentialityLevels.add(confOfLeftVariable);
					}
					List<Node> nodes = LeastUpperBound.getLattice().getNodesPerName(confidentialityLevels);
					Node leastUpperBound = LeastUpperBound.leastUpperBound(nodes);
					updateConfToVarsMap(statement.getPostCondition(), leftVariable, leastUpperBound.getName());
				}
				updateParent(statement);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String collectConfidentialitiesOfLeftVariables(EMap<String, EList<String>> preVariables, String leftVariable) {
		String returnConfidentiality = null;
		for (String confidentiality : preVariables.keySet()) {
				if (preVariables.get(confidentiality).contains(leftVariable)) {
					returnConfidentiality = confidentiality;
			}
		}
		return returnConfidentiality;
	}
	
	private static String collectConfidentialitiesOfVariablesInDeclaredVariables(Map<String, List<String>> declaredVariables, String leftVariable) {
		String returnConfidentiality = LeastUpperBound.getLattice().getBottom().getName();
		for (String confidentiality : declaredVariables.keySet()) {
				if (declaredVariables.get(confidentiality).contains(leftVariable)) {
					returnConfidentiality = confidentiality;
			}
		}
		return returnConfidentiality;
	}
	
	private static Set<String> collectConfidentialitiesOfVariables(EMap<String, EList<String>> preVariables, Set<String> variables, Map<String, List<String>> declaredVariables) {
		Set<String> confidentialitySet = new HashSet<String>();
		for (String confidentiality : preVariables.keySet()) {
			if (variables != null) {
				for (String variable : variables) {
					if (preVariables.get(confidentiality).contains(variable)) {
						confidentialitySet.add(confidentiality);
					}
				}
			}
		}
		for (String variable : variables) {
			confidentialitySet.add(collectConfidentialitiesOfVariablesInDeclaredVariables(declaredVariables, variable));
		}
		return confidentialitySet;
	}

	private static void updateConfToVarsMap(Condition postCondition, String leftVariable, String confidentiality) {
		EMap<String, EList<String>> postVariables = postCondition.getConfToVarsMap();
		for (EList<String> variablesList : postVariables.values()) {
			if (variablesList.contains(leftVariable)) {
				variablesList.remove(leftVariable);
			}
		}
		if (postVariables.containsKey(confidentiality)) {
			postVariables.get(confidentiality).add(leftVariable);
		} else {
			EList<String> newList = new BasicEList<String>();
			newList.add(leftVariable);
			postVariables.put(confidentiality, newList);
		}
	}

	public static void updateConfidentiality(AbstractStatement statement, String conf) {
//		String oldConf = statement.getContext();
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
			String subConf = getContextForSubStatement(childRep.getPreCondition(), childRep.getGuard());
			loopStatement.setContext(subConf);
			if (loopStatement.getRefinement() != null) {
				updateConfidentiality(loopStatement.getRefinement(), subConf);
			}
			
		} else if (statement instanceof SelectionStatement) {
			SelectionStatement childSel = (SelectionStatement) statement;
			
			for (int i = 0; i < childSel.getCommands().size(); i++) {
				AbstractStatement childStatement = childSel.getCommands().get(i);
				String subConf = getContextForSubStatement(childSel.getPreCondition(), childSel.getGuards().get(i));
				childStatement.setContext(subConf);
				if (childStatement.getRefinement() != null) {
					updateConfidentiality(childStatement.getRefinement(), subConf);
				}
			}
			
		} else if (statement instanceof AbstractStatement) {
			if (statement.getRefinement() != null) {
				updateConfidentiality(statement.getRefinement(), conf);
			}
//			if (oldConf == null || !oldConf.equals(conf)) {
				updateAssignmentStatement(statement);
//			}
		}
	}
	
	public static String getContextForSubStatement(Condition pre, Condition guard) {
		EMap<String, EList<String>> preVariables = pre.getConfToVarsMap();
		Set<String> guardVariables = Parser.parseRightVariables(guard.getName());
		
		Set<String> confidentialityLevels = new HashSet<String>();
		confidentialityLevels.add(((AbstractStatement) guard.eContainer()).getContext());
		confidentialityLevels.addAll(collectConfidentialitiesOfVariables(preVariables, guardVariables, getDeclaredVariables((AbstractStatement) guard.eContainer())));
		List<Node> nodes = LeastUpperBound.getLattice().getNodesPerName(confidentialityLevels);
		Node leastUpperBound = LeastUpperBound.leastUpperBound(nodes);
		return leastUpperBound.getName();
	}

	public static void updateParent(AbstractStatement statement) {
		if (statement.getParent() != null) {
			AbstractStatement statementToCopyVariables = getStatementToCopyVariables(statement);
			if (statement.getParent().eContainer() instanceof CompositionStatement) {
				CompositionStatement compoStatement = (CompositionStatement) statement.getParent().eContainer();
				if (statement.getParent().equals(compoStatement.getFirstStatement())) {
					copyVariables(statementToCopyVariables.getPostCondition(), compoStatement.getFirstStatement().getPostCondition());
					copyVariables(statementToCopyVariables.getPostCondition(), compoStatement.getSecondStatement().getPreCondition());
					copyVariables(statementToCopyVariables.getPostCondition(), compoStatement.getIntermediateCondition());
					updatePreChild(compoStatement.getSecondStatement());
					updateParent(compoStatement);
				} else if (statement.getParent().equals(compoStatement.getSecondStatement())) {
					copyVariables(statementToCopyVariables.getPostCondition(), compoStatement.getSecondStatement().getPostCondition());
					copyVariables(statementToCopyVariables.getPostCondition(), compoStatement.getPostCondition());
					updateParent(compoStatement);
				}
			} else if (statement.getParent().eContainer() instanceof CbCFormula) {
				CbCFormula formula = (CbCFormula) statement.getParent().eContainer();
				copyVariables(statementToCopyVariables.getPostCondition(), formula.getStatement().getPostCondition());
			} else if (statement.getParent().eContainer() instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement.getParent().eContainer();
				copyVariables(statementToCopyVariables.getPostCondition(), repStatement.getLoopStatement().getPostCondition());
				copyScopedVariables(repStatement.getPreCondition(), statementToCopyVariables.getPostCondition(), repStatement.getPostCondition());
				updateParent(repStatement);
			} else if (statement.getParent().eContainer() instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) statement.getParent().eContainer();
				copyScopedVariablesSel(selStatement, selStatement.getPreCondition(), selStatement.getPostCondition());
				for (AbstractStatement subStatement : selStatement.getCommands()) {
					if(statement.getParent().equals(subStatement)) {
						copyVariables(statementToCopyVariables.getPostCondition(), subStatement.getPostCondition());
					}
				}
				updateParent(selStatement);
			} else if (statement.getParent() instanceof StrengthWeakStatement) {
				StrengthWeakStatement parentStatement = (StrengthWeakStatement) statement.getParent();
				copyVariables(statementToCopyVariables.getPostCondition(), parentStatement.getPostCondition());
				updateParent(parentStatement);
			}
		}
	}

	private static void updatePreChild(AbstractStatement statement) {
		if (statement.getRefinement() != null) {
			AbstractStatement refinement = statement.getRefinement();
			copyVariables(statement.getPreCondition(), refinement.getPreCondition());
			if (refinement instanceof CompositionStatement) {
				CompositionStatement subCompoStatement = (CompositionStatement) refinement;
				copyVariables(subCompoStatement.getPreCondition(), subCompoStatement.getFirstStatement().getPreCondition());
				updatePreChild(subCompoStatement.getFirstStatement());
			} else if (refinement instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) refinement;
				for (AbstractStatement subSelStatement : selStatement.getCommands()) {
					copyVariables(selStatement.getPreCondition(), subSelStatement.getPreCondition());
					updatePreChild(subSelStatement);
				}
			} else if (refinement instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) refinement;
				copyVariables(repStatement.getPreCondition(), repStatement.getLoopStatement().getPreCondition());
				updatePreChild(repStatement.getLoopStatement());
			} else if (refinement instanceof StrengthWeakStatement) {
				updatePreChild(refinement);
			} else if (refinement instanceof AbstractStatement) {
				updateAssignmentStatement(refinement);
			}
		}
	}

	private static AbstractStatement getStatementToCopyVariables(AbstractStatement statement) {
		if (statement instanceof CompositionStatement) {
			return ((CompositionStatement) statement).getSecondStatement();
		}
		return statement;
	}

//	private static List<String> getHighVariables(Condition condition) {
//		String variables = null;
//		List<String> variablesAsList = null;
//		if(condition.getName().split(";").length>1) variables = condition.getName().split(";")[0];
//		if(variables != null) {
//			variables = variables.substring(variables.indexOf("(")+1, variables.indexOf(")"));
//			variables = variables.replace(" ", "");
//			variables = variables.replace(System.getProperty("line.separator"), "");
//			variablesAsList = Lists.newArrayList(variables.split(","));
//		}
//		return variablesAsList;
//	}
	
//	private static void addHighVariable(Condition condition, String variable) {
//		String variables = null;
//		String conditionString = null;
//		String[] splittedCondition = condition.getName().split(";");
//		if(splittedCondition.length>1) {
//			variables = splittedCondition[0];
//			conditionString = splittedCondition[1];
//		} else {
//			conditionString = splittedCondition[0];
//		}
//		if(variables != null) {
//			variables = variables.substring(variables.indexOf("(")+1, variables.indexOf(")"));
//			variables = variables.replace(" ", "");
//			variables = variables.replace(System.getProperty("line.separator"), "");
//			List<String> variablesAsList = Lists.newArrayList(variables.split(","));
//			if (!variablesAsList.contains(variable)) {
//				variablesAsList.add(variable);
//			}
//			Collections.sort(variablesAsList);
//			variables = "high(" + String.join(",", variablesAsList) + ")";
//		} else {
//			variables = "high(" + variable + ")";
//		}
//		condition.setName(variables + "; " + conditionString);
//	}
	
	private static void copyVariables(Condition preCondition, Condition postCondition) {
		postCondition.getConfToVarsMap().clear();
		for (String confidentiality : preCondition.getConfToVarsMap().keySet()) {
			EList<String> copiedList = new BasicEList<String>();
			EList<String> oldList = preCondition.getConfToVarsMap().get(confidentiality);
			copiedList.addAll(oldList);
			postCondition.getConfToVarsMap().put(confidentiality, copiedList);
		}
	}
	
	private static void copyScopedVariables(Condition preCondition, Condition oldCondition, Condition newCondition) {
		List<String> variablesList = new ArrayList<String>();
		for (List<String> variables : preCondition.getConfToVarsMap().values()) {
			variablesList.addAll(variables);
		}
		newCondition.getConfToVarsMap().clear();
		for (String confidentiality : oldCondition.getConfToVarsMap().keySet()) {
			EList<String> copiedList = new BasicEList<String>();
			EList<String> oldList = oldCondition.getConfToVarsMap().get(confidentiality);
			for (String nextVariable : oldList) {
				if (variablesList.contains(nextVariable)) {
					copiedList.add(nextVariable);
				}
			}
			newCondition.getConfToVarsMap().put(confidentiality, copiedList);
		}
	}
	
	private static void copyScopedVariablesSel(SelectionStatement selStatement, Condition preCondition, Condition newCondition) {
		List<String> variablesList = new ArrayList<String>();
		for (List<String> variables : preCondition.getConfToVarsMap().values()) {
			variablesList.addAll(variables);
		}
		newCondition.getConfToVarsMap().clear();
		for (String variable : variablesList) {
			Set<String> confidentialities = new HashSet<String>();
			for (AbstractStatement command : selStatement.getCommands()) {
				EMap<String, EList<String>> confsMap = command.getPostCondition().getConfToVarsMap();
				for (String confidentiality : confsMap.keySet()) {
					if (confsMap.get(confidentiality).contains(variable)) {
						confidentialities.add(confidentiality);
					}
				}
			}
			if (confidentialities.isEmpty()) {
				confidentialities.add(collectConfidentialitiesOfVariablesInDeclaredVariables(getDeclaredVariables(selStatement), variable));
			}
			List<Node> nodes = LeastUpperBound.getLattice().getNodesPerName(confidentialities);
			Node leastUpperBound = LeastUpperBound.leastUpperBound(nodes);
			updateConfToVarsMap(newCondition, variable, leastUpperBound.getName());
		}
	}
	
	private static Map<String, List<String>> getDeclaredVariables(AbstractStatement statement) {
		JavaVariables variables = null;
		Map<String, List<String>> variablesAsMap = new HashMap<String,List<String>>();
		if (statement.eResource() != null) {
			for (EObject object : statement.eResource().getContents()) {
				if (object instanceof JavaVariables) {
					variables = (JavaVariables) object;
				}
			}
			if (variables != null) {
				for (JavaVariable variable : variables.getVariables()) {
					if (variablesAsMap.containsKey(variable.getConfidentiality())) {
						variablesAsMap.get(variable.getConfidentiality()).add(variable.getName());
					} else {
						List<String> newList = new ArrayList<String>();
						newList.add(variable.getName());
						variablesAsMap.put(variable.getConfidentiality(), newList);
					}
				}
			}
		}
		return variablesAsMap;
	}
}
