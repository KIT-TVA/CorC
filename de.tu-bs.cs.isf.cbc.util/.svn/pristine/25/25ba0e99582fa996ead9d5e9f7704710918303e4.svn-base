package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;

public class ConstructCodeBlock {

	private static boolean handleInnerLoops = true;
	private static boolean withInvariants = false;
	private static Renaming renaming = null;
	
	public static String constructCodeBlockAndVerify(AbstractStatement statement) {
		handleInnerLoops = true;
		withInvariants = false;
		StringBuffer code = new StringBuffer();

    	if (statement instanceof RepetitionStatement) {
			code.append(constructRepetition((RepetitionStatement) statement));
		} else if (statement instanceof SmallRepetitionStatement) {
			code.append(constructSmallRepetition((SmallRepetitionStatement) statement));
		}
    	return code.toString();
	}
	
	public static String constructCodeBlockAndVerify2(AbstractStatement statement) {
		handleInnerLoops = true;
		withInvariants = false;
    	StringBuffer code = new StringBuffer();

    	if (statement instanceof RepetitionStatement) {
    		RepetitionStatement repStatement = (RepetitionStatement) statement;
			if (repStatement.getLoopStatement().getRefinement() != null) {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement().getRefinement()));
	    	} else {
	    		code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement()));
	    	}
		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
			if (repStatement.getLoopStatement().getRefinement() != null) {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement().getRefinement()));
	    	} else {
	    		code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement()));
	    	}
		}
    	return code.toString();
    }
	
	public static String constructCodeBlockAndVerify3(AbstractStatement statement) {
		handleInnerLoops = false;
		withInvariants = false;
    	StringBuffer code = new StringBuffer();

    	if (statement instanceof RepetitionStatement) {
    		RepetitionStatement repStatement = (RepetitionStatement) statement;
			if (repStatement.getLoopStatement().getRefinement() != null) {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement().getRefinement()));
	    	} else {
	    		code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement()));
	    	}
		} else if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
			if (repStatement.getLoopStatement().getRefinement() != null) {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement().getRefinement()));
	    	} else {
	    		code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement()));
	    	}
		}
    	return code.toString();
    }
	
	public static String constructCodeBlockForExport(CbCFormula formula, Renaming renaming, JavaVariables vars) {
		handleInnerLoops = true;
		withInvariants = true;
		String pre = formula.getStatement().getPreCondition().getName();
		pre = rewriteConditionToJML(pre);
		String post = formula.getStatement().getPostCondition().getName();
		post = rewriteConditionToJML(post);
		if(renaming != null) {
			ConstructCodeBlock.renaming = renaming;
			pre = useRenamingCondition(pre);
			post = useRenamingCondition(post);
		}
		String parameters = "";
		if (vars != null && !vars.getVariables().isEmpty()) {
			parameters += vars.getVariables().get(0).getName();
			for (int i = 1; i < vars.getVariables().size(); i++) {
				parameters += ", " + vars.getVariables().get(i).getName();
			}
		}
		StringBuffer code = new StringBuffer();
		code.append("public class Debug {\n"
	    +"/*@\n"
	    +"@ normal_behavior\n"
	    +"@ requires " + pre + ";\n"
	    +"@ ensures " + post + ";\n"
	    +"@*/\n"
	    +"public static void " + formula.getName()+ "(" + parameters + ") {\n");
    	
	    if (formula.getStatement().getRefinement() != null) {
    		code.append(constructCodeBlockOfChildStatement(formula.getStatement().getRefinement()));
    	} else {
    		code.append(constructCodeBlockOfChildStatement(formula.getStatement()));
    	}
    	code.append("}}");
    	return code.toString();
	}
    
    private static String constructCodeBlockOfChildStatement(AbstractStatement refinement) {
    	if (refinement.getClass().equals(AbstractStatementImpl.class)) {
    		return refinement.getName()  + "\n";
    	} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
    		return "; \n";
    	} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
    		return constructSelection((SelectionStatement) refinement);
    	} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
    		return constructComposition((CompositionStatement) refinement);
    	} else if (refinement.getClass().equals(Composition3StatementImpl.class)) {
    		return constructComposition3((Composition3Statement) refinement);
    	} else if (refinement.getClass().equals(RepetitionStatementImpl.class)) {
    		return constructRepetition((RepetitionStatement) refinement);
    	} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
    		return constructSmallRepetition((SmallRepetitionStatement) refinement);
    	}
		return null;
	}

    

	private static String constructSelection(SelectionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (!statement.getCommands().isEmpty()) {
			String guard = statement.getGuards().get(0).getName();
			guard = guard.replaceAll("\\s=\\s", "==");
			buffer.append("if (" + guard +"){\n");
			if (statement.getCommands().get(0).getRefinement() != null) {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0).getRefinement()) + "}");
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0)) + "}");
	    	}
			
		}
		for (int i = 1; i < statement.getCommands().size(); i++) {
			String guard = statement.getGuards().get(i).getName();
			guard = guard.replaceAll("\\s=\\s", "==");
			buffer.append("else if (" + guard +"){\n");
			if (statement.getCommands().get(i).getRefinement() != null) {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i).getRefinement()) + "}");
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i)) + "}");
	    	}
		}
		return buffer.toString();
	}
	
	private static String constructComposition(CompositionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (statement.getFirstStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement()));
    	}
		if (statement.getSecondStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement()));
    	}
		return buffer.toString();
	}
	
	private static String constructComposition3(Composition3Statement statement) {
		StringBuffer buffer = new StringBuffer();
		if (statement.getFirstStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement()));
    	}
		if (statement.getSecondStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement()));
    	}
		if (statement.getThirdStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getThirdStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getThirdStatement()));
    	}
		return buffer.toString();
	}
	
	private static String constructRepetition(RepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if (statement.getStartStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getStartStatement().getRefinement()));
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getStartStatement()));
	    	}
			if(withInvariants) {
				String invariant = statement.getInvariant().getName();
				invariant = rewriteConditionToJML(invariant);
				invariant = useRenamingCondition(invariant);
				buffer.append("//@ loop_invariant " + invariant + ";\n");
				buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
			}
			String guard = statement.getGuard().getName();
			guard = guard.replaceAll("\\s=\\s", "==");
			buffer.append("while(" + guard + "){\n");
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement()));
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement()));
	    	}
			buffer.append("}\n");
			if (statement.getEndStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getEndStatement().getRefinement()));
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getEndStatement()));
	    	}
		}
		return buffer.toString();
    }  

	private static String constructSmallRepetition(SmallRepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if(withInvariants) {
				String invariant = statement.getInvariant().getName();
				invariant = rewriteConditionToJML(invariant);
				invariant = useRenamingCondition(invariant);
				buffer.append("//@ loop_invariant " + invariant + ";\n");
				buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
			}
			String guard = statement.getGuard().getName();
			guard = guard.replaceAll("\\s=\\s", "==");
			buffer.append("while(" + guard + "){\n");
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement()));
			} else {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement()));
			}
			buffer.append("}\n");
		}
		return buffer.toString();
    }
	
	private static String rewriteConditionToJML(String condition) {
		condition = condition.replaceAll("->", "==>");
		condition = condition.replaceAll("<->", "<==>");
		condition = condition.replaceAll("\\s=\\s", "==");
		return condition;
    }
	
	private static String useRenamingCondition(String toRename) {
		if (renaming != null) {
			for (Rename rename : renaming.getRename()) {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}
}
