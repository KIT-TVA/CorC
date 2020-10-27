package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.Parser;

public class UpdateConditionsOfChildren {

	public static void updateConditionsofChildren(Condition condition) {
		AbstractStatement statement = (AbstractStatement) condition.eContainer();
		if (statement instanceof CompositionStatement
				|| statement instanceof SmallRepetitionStatement
				|| statement instanceof SelectionStatement) {
			if (statement.getParent() != null) {
				updateRefinedStatement(statement.getParent(), statement);
			}
		} else if (statement.getRefinement() != null) {
			AbstractStatement refinedStatement = statement.getRefinement();
			updateRefinedStatement(statement, refinedStatement);
		}
	}

	public static void updateRefinedStatement(AbstractStatement parentStatement, AbstractStatement refinedStatement) {
		Condition preParent = parentStatement.getPreCondition();
		Condition postParent = parentStatement.getPostCondition();
//		refinedStatement.setProven(false);
		if (refinedStatement instanceof SkipStatement) {
			SkipStatement childSkip = (SkipStatement) refinedStatement;

			if (!childSkip.getPreCondition().getName().equals(preParent.getName())
					|| !childSkip.getPostCondition().getName().equals(postParent.getName())) {
				refinedStatement.setProven(false);
			}

			childSkip.getPreCondition().setName(preParent.getName());
			childSkip.getPostCondition().setName(postParent.getName());

		} else if (refinedStatement instanceof CompositionStatement) {
			CompositionStatement childCompo = (CompositionStatement) refinedStatement;
			AbstractStatement firstStatement = childCompo.getFirstStatement();
			AbstractStatement secondStatement = childCompo.getSecondStatement();

			if (!firstStatement.getPreCondition().getName().equals(preParent.getName())
					|| !firstStatement.getPostCondition().getName()
							.equals(childCompo.getIntermediateCondition().getName())
					|| !secondStatement.getPreCondition().getName()
							.equals(childCompo.getIntermediateCondition().getName())
					|| !secondStatement.getPostCondition().getName().equals(postParent.getName())) {
				refinedStatement.setProven(false);
			}

			firstStatement.getPreCondition().setName(preParent.getName());
			firstStatement.getPostCondition().setName(childCompo.getIntermediateCondition().getName());
			secondStatement.getPreCondition().setName(childCompo.getIntermediateCondition().getName());
			secondStatement.getPostCondition().setName(postParent.getName());

			if (firstStatement.getRefinement() != null) {
				updateRefinedStatement(firstStatement, firstStatement.getRefinement());
			}
			if (secondStatement.getRefinement() != null) {
				updateRefinedStatement(secondStatement, secondStatement.getRefinement());
			}
			
		} else if (refinedStatement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement childRep = (SmallRepetitionStatement) refinedStatement;
			AbstractStatement loopStatement = childRep.getLoopStatement();

			if (!loopStatement.getPreCondition().getName()
					.equals("(" + childRep.getInvariant().getName() + ") & (" + childRep.getGuard().getName() + ")")
					|| !loopStatement.getPostCondition().getName().equals(childRep.getInvariant().getName())) {
				refinedStatement.setProven(false);
				childRep.setVariantProven(false);
			}
			if (!loopStatement.getPostCondition().getName().equals(childRep.getInvariant().getName())
					|| !childRep.getPreCondition().getName().equals(preParent)) {
				childRep.setPreProven(false);
			}
			if (!loopStatement.getPreCondition().getName()
					.equals("(" + childRep.getInvariant().getName() + ") & (" + childRep.getGuard().getName() + ")")
					|| !childRep.getPostCondition().getName().equals(postParent)) {
				childRep.setPostProven(false);
			}

			childRep.getPreCondition().setName(preParent.getName());
			childRep.getPostCondition().setName(postParent.getName());

			loopStatement.getPreCondition()
					.setName("(" + childRep.getInvariant().getName() + ") & (" + childRep.getGuard().getName() + ")");
			loopStatement.getPostCondition().setName(childRep.getInvariant().getName());

			if (loopStatement.getRefinement() != null) {
				updateRefinedStatement(loopStatement, loopStatement.getRefinement());
			}

		} else if (refinedStatement instanceof SelectionStatement) {
			SelectionStatement childSel = (SelectionStatement) refinedStatement;

			for (int i = 0; i < childSel.getCommands().size(); i++) {
				AbstractStatement childStatement = childSel.getCommands().get(i);
				Condition childGuard = childSel.getGuards().get(i);

				String preCondParent = Parser.getConditionFromCondition(preParent.getName());
				String modifiablePreParent = Parser.getModifieableVarsFromCondition(preParent.getName())
						.equals("\\\\everything;") ? ""
								: "modifiable(" + Parser.getModifieableVarsFromCondition(preParent.getName()) + ");";

				if (!childStatement.getPreCondition().getName()
						.equals(modifiablePreParent + "(" + preCondParent + ") & (" + childGuard.getName() + ")")
						|| !childStatement.getPostCondition().getName().equals(postParent.getName())) {
					refinedStatement.setProven(false);
				}
				if (!childStatement.getPreCondition().getName()
						.equals("(" + preParent.getName() + ") & (" + childGuard.getName() + ")")) {
					childSel.setPreProve(false);
				}

				childStatement.getPreCondition()
						.setName(modifiablePreParent + "(" + preCondParent + ") & (" + childGuard.getName() + ")");
				childStatement.getPostCondition().setName(postParent.getName());

				if (childStatement.getRefinement() != null) {
					updateRefinedStatement(childStatement, childStatement.getRefinement());
				}
			}

		} else if (refinedStatement instanceof ReturnStatement) {
			ReturnStatement childReturn = (ReturnStatement) refinedStatement;
			CbCFormula formula = getFormula(parentStatement);
			if (formula != null) {
				if (!childReturn.getPreCondition().getName().equals(preParent.getName()) || !childReturn
						.getPostCondition().getName().equals(formula.getStatement().getPostCondition().getName())) {
					refinedStatement.setProven(false);
				}

				childReturn.getPreCondition().setName(preParent.getName());
				childReturn.getPostCondition().setName(formula.getStatement().getPostCondition().getName());
			}

		} else if (refinedStatement instanceof StrengthWeakStatement) {
			StrengthWeakStatement childStrengthWeak = (StrengthWeakStatement) refinedStatement;
			refinedStatement.setProven(false);

			if (childStrengthWeak.getRefinement() != null) {
				updateRefinedStatement(childStrengthWeak, childStrengthWeak.getRefinement());
			}

		} else if (refinedStatement instanceof AbstractStatement) {
			AbstractStatement childAbstract = (AbstractStatement) refinedStatement;

			if (!childAbstract.getPreCondition().getName().equals(preParent.getName())
					|| !childAbstract.getPostCondition().getName().equals(postParent.getName())) {
				refinedStatement.setProven(false);
			}

			childAbstract.getPreCondition().setName(preParent.getName());
			childAbstract.getPostCondition().setName(postParent.getName());

		}
	}

	private static CbCFormula getFormula(AbstractStatement statement) {
		if (statement.getParent() != null) {
			return getFormula(statement.getParent());
		}
		EObject parent = statement.eContainer();
		if (parent != null && parent instanceof AbstractStatement) {
			return getFormula((AbstractStatement) parent);
		} else if (parent != null && parent instanceof CbCFormula) {
			return (CbCFormula) parent;
		}
		return null;
	}

	public static void setAllStatementsUnproven(AbstractStatement statement) {
		statement.setProven(false);
		if (statement instanceof CompositionStatement) {
			CompositionStatement childCompo = (CompositionStatement) statement;
			AbstractStatement firstStatement = childCompo.getFirstStatement();
			AbstractStatement secondStatement = childCompo.getSecondStatement();

			firstStatement.setProven(false);
			if (firstStatement.getRefinement() != null) {
				setAllStatementsUnproven(firstStatement.getRefinement());
			}
			secondStatement.setProven(false);
			if (secondStatement.getRefinement() != null) {
				setAllStatementsUnproven(secondStatement.getRefinement());
			}

		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement childRep = (SmallRepetitionStatement) statement;
			AbstractStatement loopStatement = childRep.getLoopStatement();

			childRep.setPreProven(false);
			childRep.setPostProven(false);
			childRep.setVariantProven(false);

			loopStatement.setProven(false);
			if (loopStatement.getRefinement() != null) {
				setAllStatementsUnproven(loopStatement.getRefinement());
			}

		} else if (statement instanceof SelectionStatement) {
			SelectionStatement childSel = (SelectionStatement) statement;
			childSel.setPreProve(false);
			for (int i = 0; i < childSel.getCommands().size(); i++) {
				AbstractStatement childStatement = childSel.getCommands().get(i);

				childStatement.setProven(false);
				if (childStatement.getRefinement() != null) {
					setAllStatementsUnproven(childStatement.getRefinement());
				}
			}

		} else if (statement instanceof AbstractStatement) {
			if (statement.getRefinement() != null) {
				setAllStatementsUnproven(statement.getRefinement());
			}
		}
	}
}