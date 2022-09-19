package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
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

	public static void setVars(JavaVariables vars) {
		UpdateModifiableOfConditions.vars = vars;
	}
	
	public static void updateAssignmentStatement(AbstractStatement statement, IFileUtil fileHandler) {
		fileUtil = fileHandler;
		updateAssignmentStatement(statement);
	}
	
	private static void updateAssignmentStatement(AbstractStatement statement) {
		copyModifiableVariables(statement.getPreCondition(), statement.getPostCondition());
		if (statement.getName().contains(";") && CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			try {
				List<String> variablesInStatement = Parser.findAllVariables(statement, vars, fileUtil);
				List<String> modifiableVariables = new ArrayList<>(statement.getPreCondition().getModifiables());
				for (String var : variablesInStatement) {
					if (!modifiableVariables.contains(var)) {
						for (Field f : vars.getFields()) {
							if (f.getName().equals(var) && f.getType().contains("[]") && !var.contains("[")) {
								var = var + "[*]";
							}
						}
						modifiableVariables.add(var);
					}
				}
				statement.getPostCondition().getModifiables().clear();
				for (String mv : modifiableVariables) {
					if (!statement.getPostCondition().getModifiables().contains(mv)) {
						statement.getPostCondition().getModifiables().add(mv);
					}
				}
				ECollections.sort(statement.getPostCondition().getModifiables());
				currentPost = statement.getPostCondition();
				updateParent(statement);
			} catch (ParserException e) {
				e.printStackTrace();
			}
		}
	}

	private static void updateParent(AbstractStatement statement) {
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
			copyModifiableVariables(statement.getPreCondition(), ((AbstractStatement) refinement.eContainer()).getPreCondition());
			if (refinement instanceof CompositionStatement) {
				CompositionStatement subCompoStatement = (CompositionStatement) refinement;
				copyModifiableVariables(((AbstractStatement) subCompoStatement.eContainer()).getPreCondition(), subCompoStatement.getFirstStatement().getPreCondition());
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
		if (copyFromCondition != null && copyToCondition != null && !copyFromCondition.getModifiables().isEmpty()) {
			for (String c : copyFromCondition.getModifiables()) {
				if (!copyToCondition.getModifiables().contains(c)) {
					copyToCondition.getModifiables().add(c);
				}
			}
		}
	}
	
	private static void copyModifiableVariables(Condition copyFromCondition, Condition copyToCondition) {
		if (copyFromCondition != null && copyToCondition != null && !copyFromCondition.getModifiables().isEmpty()) {
			List<String> temp = new ArrayList<>();
			for (String s: copyFromCondition.getModifiables()) {
				temp.add(s);
			}
			copyToCondition.getModifiables().clear();
			copyToCondition.getModifiables().addAll(temp);
		}
	}
}