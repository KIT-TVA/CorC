package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.IFileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ParserException;

public class UpdateModifiableOfConditions {
	private static Condition currentPost = null;
	private static JavaVariables vars = null;
	private static IFileUtil fileUtil = null;

	public static void updateAssignmentStatement(AbstractStatement statement, IFileUtil fileHandler) {
		fileUtil = fileHandler;
		updateAssignmentStatement(statement);
		
	}
	
	public static void updateAssignmentStatement(AbstractStatement statement) {
		copyModifiableVariables(statement.getPreCondition(), statement.getPostCondition());
		if (statement.getName().contains(";")
				&& CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			List<String> modifiableVariables = getModifiableVariables(statement.getPreCondition());
			
			try {
				List<String> variablesInStatement = Parser.findAllVariables(statement, vars, fileUtil);
				if(!modifiableVariables.contains("\\everything")) {
					for (String var : variablesInStatement) {
						if (!modifiableVariables.contains(var)) {
							if(modifiableVariables.contains("\\nothing"))
								modifiableVariables = Lists.newArrayList();
							for (Field f : vars.getFields()) {
								if (f.getName().equals(var) && f.getType().contains("[]") && !var.contains("[")) {
									var = var + "[*]";
								}
							}
							modifiableVariables.add(var);
						}
					}
				} 

				addModifiableVariablesToStatement(statement.getPostCondition(), modifiableVariables);
				currentPost = statement.getPostCondition();
				
				//if container is select, add to it postconditions of other commands
				/*if(statement.eContainer() instanceof SelectionStatement) {
					SelectionStatement selectionStatement = (SelectionStatement) statement.eContainer();
					for(int i = 0; i < selectionStatement.getCommands().size(); i++) {
						AbstractStatement command = selectionStatement.getCommands().get(i);
						if(command.getPostCondition() != currentPost) {
							copyModifiableVariables(command.getPostCondition(), currentPost);
						}
					}
				}*/
				
				updateParent(statement);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
	}

	private static void updateParent(AbstractStatement statement) {
		
		/*if(statement instanceof SelectionStatement) {
			SelectionStatement selectionStatement = (SelectionStatement) statement;
			for(int i = 0; i < selectionStatement.getCommands().size(); i++) {
				AbstractStatement command = selectionStatement.getCommands().get(i);
				if(command.getPostCondition() != currentPost) {
					copySelectionModifiableVariables(command.getPostCondition(), currentPost);
				}
			}
		}*/
		
		EObject parent = null;
		if (statement.getParent() != null) {
			parent = statement.getParent().eContainer();
		} else if (statement.eContainer() != null) {
			parent = statement.eContainer();
		}
		if (parent != null) {
			if (parent instanceof CompositionStatement) {
				CompositionStatement compoStatement = (CompositionStatement) parent;
				if (statement.getParent().equals(compoStatement.getFirstStatement())) {
					copyModifiableVariables(currentPost, compoStatement.getFirstStatement().getPostCondition());
					copyModifiableVariables(currentPost, compoStatement.getSecondStatement().getPreCondition());
					copyModifiableVariables(currentPost, compoStatement.getIntermediateCondition());
					updatePreChild(compoStatement.getSecondStatement());
					copyModifiableVariables(currentPost, compoStatement.getSecondStatement().getPostCondition());
					updateParent(compoStatement);
				} else if (statement.getParent().equals(compoStatement.getSecondStatement())) {
					copyModifiableVariables(currentPost, compoStatement.getSecondStatement().getPostCondition());
					updateParent(compoStatement);
				}
			} else if (parent instanceof CbCFormula) {
				CbCFormula formula = (CbCFormula) parent;
				copyModifiableVariables(currentPost, formula.getStatement().getPostCondition());
			} else if (parent instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) parent;
				copyModifiableVariables(currentPost, repStatement.getLoopStatement().getPostCondition());
				copyModifiableVariables(repStatement.getPreCondition(), repStatement.getPostCondition());
				updateParent(repStatement);
			} else if (parent instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) parent;//pre and post of selection are always null
				copyModifiableVariables(selStatement.getPreCondition(), selStatement.getPostCondition());//?
				if(statement.eContainer() instanceof SelectionStatement) {//abstract stmnt. embedded in Selection 
					for (AbstractStatement subStatement : selStatement.getCommands()) {//update postcondition of the parent extraselection
						if (!statement.equals(subStatement)) {//parent.equals(subStatement)
							copySelectionModifiableVariables(subStatement.getPostCondition(), currentPost);
						}
					}
				} else {//Abstract stmnt is child of Selection
					for (AbstractStatement subStatement : selStatement.getCommands()) {//update postcondition of the parent extraselection
						if (statement.eContainer().equals(subStatement)) {//update post condition of parent
							copyModifiableVariables(currentPost, subStatement.getPostCondition());
						}
					}
					for (AbstractStatement subStatement : selStatement.getCommands()) {//update postcondition of the parent extraselection
						if (!statement.eContainer().equals(subStatement)) {//parent.equals(subStatement)
							copySelectionModifiableVariables(subStatement.getPostCondition(), currentPost);
						}
					}
				}
				
				//show all modifiables of Selection
				copyModifiableVariables(currentPost, selStatement.getCommands().get(0).getPostCondition());
				
				updateParent(selStatement);
			} else if (statement.getParent() instanceof StrengthWeakStatement) {
				StrengthWeakStatement parentStatement = (StrengthWeakStatement) statement.getParent();
				copyModifiableVariables(currentPost, parentStatement.getPostCondition());
				updateParent(parentStatement);
			}
		}

	}

	private static void updatePreChild(AbstractStatement statement) {
		if (statement.getRefinement() != null) {
			AbstractStatement refinement = statement.getRefinement();
			copyModifiableVariables(statement.getPreCondition(), refinement.getPreCondition());
			copyModifiableVariables(statement.getPreCondition(),
					((AbstractStatement) refinement.eContainer()).getPreCondition());
			if (refinement instanceof CompositionStatement) {
				CompositionStatement subCompoStatement = (CompositionStatement) refinement;
				copyModifiableVariables(((AbstractStatement) subCompoStatement.eContainer()).getPreCondition(),
						subCompoStatement.getFirstStatement().getPreCondition());
				updatePreChild(subCompoStatement.getFirstStatement());
				copyModifiableVariables(currentPost, subCompoStatement.getSecondStatement().getPostCondition());
			} else if (refinement instanceof SelectionStatement) {
				SelectionStatement selStatement = (SelectionStatement) refinement;
				for (AbstractStatement subSelStatement : selStatement.getCommands()) {
					copyModifiableVariables(statement.getPreCondition(), subSelStatement.getPreCondition());
					if (subSelStatement instanceof AbstractStatement) {
						updateAssignmentStatement(subSelStatement);
					} else {
						copyModifiableVariables(statement.getPreCondition(), subSelStatement.getPostCondition());
					}

					updatePreChild(subSelStatement);
				}
			} else if (refinement instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repStatement = (SmallRepetitionStatement) refinement;
				AbstractStatement loopStatement = repStatement.getLoopStatement();
				copyModifiableVariables(repStatement.getPreCondition(), loopStatement.getPreCondition());
				if (loopStatement instanceof AbstractStatement) {
					updateAssignmentStatement(loopStatement);
				} else {
					copyModifiableVariables(statement.getPreCondition(), loopStatement.getPostCondition());
				}
				updatePreChild(loopStatement);
			} else if (refinement instanceof StrengthWeakStatement) {
				updatePreChild(refinement);
			} else if (refinement instanceof AbstractStatement) {
				copyModifiableVariables(statement.getPreCondition(), statement.getPostCondition());
				updateAssignmentStatement(refinement);
			}
		}
	}

	private static void copySelectionModifiableVariables(Condition copyFromCondition, Condition copyToCondition) {
		if (copyFromCondition != null && copyToCondition != null
				&& copyFromCondition.getName().contains("modifiable(")) {
			String variables = null;
			String[] splittedCondition = copyFromCondition.getName().split(";", 2);
			if (splittedCondition.length > 1) {
				variables = splittedCondition[0];
			}
			String conditionString = null;
			String variables2 = "";
			if(copyToCondition.getName().contains("modifiable")) {
				conditionString = copyToCondition.getName().split(";", 2)[1];
			    variables2 = copyToCondition.getName().split(";", 2)[0];
			} else {
				conditionString = copyToCondition.getName();
			}
			
			if (!variables2.isEmpty()) {
				if(!variables2.contains("nothing") && !variables.contains("nothing")) {
					variables = variables.substring(0, variables.indexOf(')')) + ","  + variables2.substring(variables2.indexOf('(') + 1, variables2.indexOf(')')) + ")";						
					String[] v = variables.substring(variables.indexOf('(') + 1, variables.indexOf(')')).split(",");
					v = Arrays.stream(v).distinct().toArray(String[]::new);
					variables = "modifiable(" + v[0];					
					for(int i = 1; i < v.length; i++) {
						variables = variables + "," + v[i];
					}
					variables = variables + ")";
				} else if(!variables2.contains("nothing") && variables.contains("nothing")) {
					variables = variables2;
				}
			} 
			
			if (variables != null) {
				copyToCondition
						.setName(variables + ";" + System.getProperty("line.separator") + conditionString.trim());
			} else {
				copyToCondition.setName(conditionString.trim());
			}
		}
	}
	
	private static void copyModifiableVariables(Condition copyFromCondition, Condition copyToCondition) {
		if (copyFromCondition != null && copyToCondition != null
				&& copyFromCondition.getName().contains("modifiable(")) {
			/*if(copyToCondition.eContainer().eContainer().eContainer() instanceof SelectionStatement) {
				String variables = null;
				String[] splittedCondition = copyFromCondition.getName().split(";", 2);
				if (splittedCondition.length > 1) {
					variables = splittedCondition[0];
				}
				String conditionString = null;
				String[] splittedCondition2 = copyToCondition.getName().split(";", 2);
				if (splittedCondition2.length > 1) {
					conditionString = splittedCondition2[1];
					String variables2 = splittedCondition2[0];
					if(!variables2.contains("nothing") && !variables.contains("nothing")) {
						variables = variables.substring(0, variables.indexOf(')')) + ","  + variables2.substring(variables2.indexOf('(') + 1, variables2.indexOf(')')) + ")";						
					} else if(!variables2.contains("nothing") && variables.contains("nothing")) {
						variables = variables2;
					}
				} else {
					conditionString = splittedCondition2[0];
				}
				if (variables != null) {
					copyToCondition
							.setName(variables + ";" + System.getProperty("line.separator") + conditionString.trim());
				} else {
					copyToCondition.setName(conditionString.trim());
				}
			} else*/ {
				String variables = null;
				String[] splittedCondition = copyFromCondition.getName().split(";", 2);
				if (splittedCondition.length > 1) {
					variables = splittedCondition[0];
				}
				String conditionString = null;
				
				if(copyToCondition.getName().contains("modifiable")) {
					conditionString = copyToCondition.getName().split(";", 2)[1];
				} else {
					conditionString = copyToCondition.getName();
				}
				
				if (variables != null) {
					copyToCondition
							.setName(variables + ";" + System.getProperty("line.separator") + conditionString.trim());
				} else {
					copyToCondition.setName(conditionString.trim());
				}	
			}
		}

	}

	private static void addModifiableVariablesToStatement(Condition condition, List<String> modifiableVariables) {
		String variables = "";
		String conditionString = condition.getName();
		if (condition.getName().contains("modifiable(")) {
			String[] splittedCondition = condition.getName().split(";", 2);
			if (splittedCondition.length > 1) {
				variables = splittedCondition[0];
				conditionString = splittedCondition[1];
			}
		}
		Collections.sort(modifiableVariables);
		if (modifiableVariables.size() == 1) {
			variables = "modifiable(" + modifiableVariables.get(0) + "); ";

		} else if (modifiableVariables.size() > 1) {
			variables = "modifiable(" + String.join(",", modifiableVariables) + "); ";
		}
		if(variables.contains("nothing")) {
			condition.setName(variables + conditionString);
		} else {
			condition.setName(variables + conditionString);
		}
	}

	public static List<String> getModifiableVariables(Condition condition) {
		String variables = null;
		List<String> variablesAsList = Lists.newArrayList();
		if (condition.getName().contains("modifiable")) {
			if(condition.getName().split(";").length > 1)
				variables = condition.getName().split(";")[0];
		}
		if(variables != null && (variables.equals("modifiable(\\nothing)") || variables.equals("modifiable(\\everything)"))) {
			return Lists.newArrayList(variables.substring(variables.indexOf("(") + 1, variables.indexOf(")")));
		}
		if (variables != null) {
			variables = variables.substring(1);
			variables = variables.substring(variables.indexOf("(") + 1, variables.indexOf(")"));
			variables = variables.replace(" ", "");
			variables = variables.replace(System.getProperty("line.separator"), "");
			variablesAsList = Lists.newArrayList(variables.split(","));
		}
		return variablesAsList;
	}

	public static void setVars(JavaVariables vars) {
		UpdateModifiableOfConditions.vars = vars;
	}

}
