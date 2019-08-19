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
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl;

public class ConstructCodeBlock {

	private static boolean handleInnerLoops = true;
	private static boolean withInvariants = false;
	private static Renaming renaming = null;
	private static int positionIndex = 0;
	
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
	    +"\t/*@\n"
	    +"\t@ normal_behavior\n"
	    +"\t@ requires " + pre.replaceAll(System.getProperty("line.separator"), "") + ";\n"
	    +"\t@ ensures " + post.replaceAll(System.getProperty("line.separator"), "") + ";\n"
	    +"\t@*/\n"
	    +"\tpublic static void " + formula.getName()+ "(" + parameters + ") {\n");
		positionIndex = 2;
		for (int i = 0; i < positionIndex; i++) {
			code.append("\t");
		}
	    if (formula.getStatement().getRefinement() != null) {
    		code.append(constructCodeBlockOfChildStatement(formula.getStatement().getRefinement()));
    	} else {
    		code.append(constructCodeBlockOfChildStatement(formula.getStatement()));
    	}
    	code.append("\n\t}\n}");
    	return code.toString();
	}
    
    private static String constructCodeBlockOfChildStatement(AbstractStatement refinement) {
    	if (refinement.getClass().equals(AbstractStatementImpl.class)) {
    		String allStatements =  refinement.getName().replace("\r\n", "");
    		allStatements = allStatements.replace(" ", "");
    		allStatements = allStatements.replace("=", " = ");

    		String abstractStatementSplit[] = allStatements.split(";");
    		String statements;
    		if (abstractStatementSplit.length > 1) {
				statements = abstractStatementSplit[0] + ";\n";
				for (int i = 1; i < abstractStatementSplit.length; i++) {
					for (int j = 0; j < positionIndex; j++) {
						statements = statements + "\t";
					}
					statements = statements + (abstractStatementSplit[i] + ";\n");
				}
    		} else {
    			statements = allStatements + "\n";
    		}
    		//return statements;
    		return statements;
    		//return refinement.getName()  + "\n";
    	} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
    		return ";\n";
    	} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
    		return "return " + refinement.getName()  + "\n";
    	} else if (refinement.getClass().equals(MethodStatementImpl.class)) {
    		return refinement.getName()  + "();\n";
    	}else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
    		return constructSelection((SelectionStatement) refinement);
    	} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
    		return constructComposition((CompositionStatement) refinement);
    	} else if (refinement.getClass().equals(Composition3StatementImpl.class)) {
    		return constructComposition3((Composition3Statement) refinement);
    	} else if (refinement.getClass().equals(RepetitionStatementImpl.class)) {
    		return constructRepetition((RepetitionStatement) refinement);
    	} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
    		return constructSmallRepetition((SmallRepetitionStatement) refinement);
    	} else if (refinement.getClass().equals(StrengthWeakStatementImpl.class)) {
    		if (refinement.getRefinement() != null) {
    			return constructCodeBlockOfChildStatement(refinement.getRefinement());
    		} else {
    			return refinement.getName() + ";\n";
    		}
    	}
		return null;
	}


	private static String constructSelection(SelectionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (!statement.getCommands().isEmpty()) {
			String guard = statement.getGuards().get(0).getName();
			//guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			buffer.append("if (" + guard +") {\n");
			positionIndex++;
			if (statement.getCommands().get(0).getRefinement() != null) {
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0).getRefinement()));
	    		positionIndex--;
	    		for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
	    		buffer.append("}");
	    	} else {
	    		for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(0)));
	    		positionIndex--;
	    		for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
	    		buffer.append("}");
	    	}
		}
		
		for (int i = 1; i < statement.getCommands().size(); i++) {
			String guard = statement.getGuards().get(i).getName();
			//guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			buffer.append(" else if (" + guard +") {\n");
			positionIndex++;
			if (statement.getCommands().get(i).getRefinement() != null) {
				for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i).getRefinement()));
	    		positionIndex--;
	    		for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
	    		buffer.append("}");
	    	} else {
	    		for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i)) + "}");
	    		positionIndex--;
	    		for (int j = 0; j < positionIndex; j++) {
					buffer.append("\t");
				}
	    		buffer.append("}");
	    	}
			
		}
		
		buffer.append("\n");
		return buffer.toString();
	}
	
	private static String constructComposition(CompositionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (statement.getFirstStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getFirstStatement()));
    	}
		
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
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
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
		}
		if (statement.getSecondStatement().getRefinement() != null) {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement().getRefinement()));
    	} else {
    		buffer.append(constructCodeBlockOfChildStatement(statement.getSecondStatement()));
    	}
		for (int i = 0; i < positionIndex; i++) {
			buffer.append("\t");
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
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("//@ loop_invariant " + invariant.replaceAll("\\r\\n", "") + ";\n");
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
			}
			String guard = statement.getGuard().getName();
			 
			//guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			buffer.append("while (" + guard + "){\n");
			positionIndex++;
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement()));
	    	} else {
	    		buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement()));
	    	}
			positionIndex--;
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			buffer.append("}\n");
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
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
				buffer.append("//@ loop_invariant " + invariant.replaceAll("\\r\\n", "") + ";\n");
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
			}
			String guard = statement.getGuard().getName();
			//guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			buffer.append("while (" + guard + ") {\n");
			positionIndex++;
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement().getRefinement()));
			} else {
				buffer.append(constructCodeBlockOfChildStatement(statement.getLoopStatement()));
			}
			positionIndex--;
			for (int i = 0; i < positionIndex; i++) {
				buffer.append("\t");
			}
			buffer.append("}\n");
		}
		return buffer.toString();
    }
	
	private static String rewriteConditionToJML(String condition) {
		condition = condition.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		condition = condition.replace("->", "==>");
		condition = condition.replace("<->", "<==>");
		condition = condition.replace("&", "&&");
		condition = condition.replace("|", "||");
		return condition;
    }
	
	private static String rewriteGuardToJavaCode(String guard) {
		guard = guard.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		guard = guard.replace("&", "&&");
		guard = guard.replace("|", "||");
		return guard;
	}
	
//	private static Boolean isEqualityComparison(String condition) {
//		boolean result = false;
//		Pattern pat = Pattern.compile("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)");
//		Matcher mat = pat.matcher(condition);
//		if (mat.find()) {
//            result = true;
//        } 
//		return result;
//	}
	
	private static String useRenamingCondition(String toRename) {
		if (renaming != null) {
			for (Rename rename : renaming.getRename()) {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}
}
