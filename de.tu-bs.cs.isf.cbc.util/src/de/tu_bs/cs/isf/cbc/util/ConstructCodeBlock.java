package de.tu_bs.cs.isf.cbc.util;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
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

	// tobi
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

		String modifiableVariables = Parser
				.getModifieableVarsFromCondition(formula.getStatement().getPostCondition().getName());
		String postCondition = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());

		String pre = createConditionJMLString(formula.getStatement().getPreCondition().getName(), renaming,
				Parser.KEYWORD_JML_PRE);
		String post = createConditionJMLString(postCondition, renaming, Parser.KEYWORD_JML_POST);

		String parameters = "";
		if (vars != null && !vars.getVariables().isEmpty()) {
			parameters += vars.getVariables().get(0).getName();
			for (int i = 1; i < vars.getVariables().size(); i++) {
				parameters += ", " + vars.getVariables().get(i).getName();
			}
		}
		StringBuffer code = new StringBuffer();
		System.out.println(System.getProperties());
		code.append("public class Debug {\n" + "/*@\n" + "@ normal_behavior\n" //+ "@ requires "
				+ pre.replaceAll(System.getProperty("line.separator"), "")// + ";\n" //+ "@ ensures "
				+ post.replaceAll(System.getProperty("line.separator"), "")/* + ";\n"*/ + "@ assignable "
				+ modifiableVariables + ";\n" + "@*/\n" + "public static void " + formula.getName() + "(" + parameters
				+ ") {\n");

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

	// tabea
	public static String constructMethodStubsForExport(CbCFormula formula, Renaming renaming, JavaVariables vars) {
		handleInnerLoops = true;
		withInvariants = false;

		String modifiableVariables = Parser
				.getModifieableVarsFromCondition(formula.getStatement().getPostCondition().getName());
		String postCondition = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());

		String pre = createConditionJMLString(formula.getStatement().getPreCondition().getName(), renaming,
				Parser.KEYWORD_JML_PRE);
		String post = createConditionJMLString(postCondition, renaming, Parser.KEYWORD_JML_POST);

		String returnValueType = "void";
		String parameters = "";
		for (JavaVariable var : vars.getVariables()) {
			switch (var.getKind()) {
			case PARAM:
				if (parameters.equals("")) {
					parameters += var.getName();
				} else {
					parameters += ", " + var.getName();
				}
				break;
			case RETURN:
				String[] splitNameAndType = var.getName().split(" ");
				// get type of variable not whole name
				returnValueType = splitNameAndType[0];
				// get only the name
				String returnValueName = splitNameAndType[1];
				post = post.replaceAll("(?<=\\W)" + returnValueName + "(?=\\W)", "\\\\result");
				break;
			default:
				break;
			}
		}
		StringBuffer code = new StringBuffer();
		System.out.println(System.getProperties());
		code.append("public class MethodStubs {\n/*@\n@ normal_behavior\n" + pre + post + "@assignable "
				+ modifiableVariables + ";\n" + "@*/\n" + "public static " + returnValueType + " " + formula.getName()
				+ "(" + parameters + ") {\n" + "}");

		// traverse through tree and add stubs for called methods to the String
		if (formula.getStatement() != null) {
			code.append(constructMethodStubOfChildStatement(formula.getStatement().getRefinement()));
		} else {
			code.append(constructMethodStubOfChildStatement(formula.getStatement()));
		}

		code.append("\n}");
		return code.toString();
	}

	// tabea
	private static String constructMethodStubOfChildStatement(AbstractStatement refinement) {
		if (refinement.getClass().equals(AbstractStatementImpl.class)) {
			return extractMethodNameFromStatement(refinement.getName());
		} else if (refinement.getClass().equals(SkipStatementImpl.class)
				|| refinement.getClass().equals(ReturnStatementImpl.class)
				|| refinement.getClass().equals(MethodStatementImpl.class)) {
			return "";
		} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
			return traverseSelection((SelectionStatement) refinement);
		} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
			return traverseComposition((CompositionStatement) refinement);
		} else if (refinement.getClass().equals(Composition3StatementImpl.class)) {
			return traverseComposition3((Composition3Statement) refinement);
		} else if (refinement.getClass().equals(RepetitionStatementImpl.class)) {
			return traverseRepetition((RepetitionStatement) refinement);
		} else if (refinement.getClass().equals(SmallRepetitionStatementImpl.class)) {
			return traverseSmallRepetition((SmallRepetitionStatement) refinement);
		} else if (refinement.getClass().equals(StrengthWeakStatementImpl.class)) {
			if (refinement.getRefinement() != null) {
				return constructCodeBlockOfChildStatement(refinement.getRefinement());
			} else {
				return "";
			}
		}
		return null;
	}

	private static String constructCodeBlockOfChildStatement(AbstractStatement refinement) {
		if (refinement.getClass().equals(AbstractStatementImpl.class)) {
			// behandlung von AbstractStatementImpl nur von Tobi
			String allStatements = refinement.getName().replace("\r\n", "");
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
			// return statements;
			return statements;
			// return refinement.getName() + "\n";
		} else if (refinement.getClass().equals(SkipStatementImpl.class)) {
			return ";\n";
		} else if (refinement.getClass().equals(ReturnStatementImpl.class)) {
			return "return " + refinement.getName() + "\n";
		} else if (refinement.getClass().equals(MethodStatementImpl.class)) {
			return refinement.getName() + "();\n";
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

			guard = rewriteGuardToJavaCode(guard);

			buffer.append("if (" + guard + ") {\n");

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
			// guard = guard.replaceAll("\\s=\\s", "==");
			guard = rewriteGuardToJavaCode(guard);
			buffer.append(" else if (" + guard + ") {\n");
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

	private static String traverseSelection(SelectionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		for (AbstractStatement command : statement.getCommands()) {
			if (command.getRefinement() != null) {
				buffer.append(constructMethodStubOfChildStatement(command.getRefinement()));
			} else {
				buffer.append(constructMethodStubOfChildStatement(command));
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

	private static String traverseComposition(CompositionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (statement.getFirstStatement().getRefinement() != null) {
			buffer.append(constructMethodStubOfChildStatement(statement.getFirstStatement().getRefinement()));
		} else {
			buffer.append(constructMethodStubOfChildStatement(statement.getFirstStatement()));
		}
		if (statement.getSecondStatement().getRefinement() != null) {
			buffer.append(constructMethodStubOfChildStatement(statement.getSecondStatement().getRefinement()));
		} else {
			buffer.append(constructMethodStubOfChildStatement(statement.getSecondStatement()));
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

	private static String traverseComposition3(Composition3Statement statement) {
		StringBuffer buffer = new StringBuffer();
		if (statement.getFirstStatement().getRefinement() != null) {
			buffer.append(constructMethodStubOfChildStatement(statement.getFirstStatement().getRefinement()));
		} else {
			buffer.append(constructMethodStubOfChildStatement(statement.getFirstStatement()));
		}
		if (statement.getSecondStatement().getRefinement() != null) {
			buffer.append(constructMethodStubOfChildStatement(statement.getSecondStatement().getRefinement()));
		} else {
			buffer.append(constructMethodStubOfChildStatement(statement.getSecondStatement()));
		}
		if (statement.getThirdStatement().getRefinement() != null) {
			buffer.append(constructMethodStubOfChildStatement(statement.getThirdStatement().getRefinement()));
		} else {
			buffer.append(constructMethodStubOfChildStatement(statement.getThirdStatement()));
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
			
			if (withInvariants) {
				String invariant = statement.getInvariant().getName();
				invariant = Parser.rewriteConditionToJML(invariant);
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

			// guard = guard.replaceAll("\\s=\\s", "==");
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

	private static String traverseRepetition(RepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if (statement.getStartStatement().getRefinement() != null) {
				buffer.append(constructMethodStubOfChildStatement(statement.getStartStatement().getRefinement()));
			} else {
				buffer.append(constructMethodStubOfChildStatement(statement.getStartStatement()));
			}

			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructMethodStubOfChildStatement(statement.getLoopStatement().getRefinement()));
			} else {
				buffer.append(constructMethodStubOfChildStatement(statement.getLoopStatement()));
			}
			if (statement.getEndStatement().getRefinement() != null) {
				buffer.append(constructMethodStubOfChildStatement(statement.getEndStatement().getRefinement()));
			} else {
				buffer.append(constructMethodStubOfChildStatement(statement.getEndStatement()));
			}
		}
		return buffer.toString();
	}

	private static String constructSmallRepetition(SmallRepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if (withInvariants) {
				String invariant = statement.getInvariant().getName();
				invariant = Parser.rewriteConditionToJML(invariant);
				invariant = useRenamingCondition(invariant);
				buffer.append("//@ loop_invariant " + invariant.replaceAll("\\r\\n", "") + ";\n");
				for (int i = 0; i < positionIndex; i++) {
					buffer.append("\t");
				}
				buffer.append("//@ decreases " + statement.getVariant().getName() + ";\n");
			}
			String guard = statement.getGuard().getName();
			// guard = guard.replaceAll("\\s=\\s", "==");
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

	private static String traverseSmallRepetition(SmallRepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if (statement.getLoopStatement().getRefinement() != null) {
				buffer.append(constructMethodStubOfChildStatement(statement.getLoopStatement().getRefinement()));
			} else {
				buffer.append(constructMethodStubOfChildStatement(statement.getLoopStatement()));
			}
		}
		return buffer.toString();
	}

	private static String createConditionJMLString(String condition, Renaming renaming, String postOrPre) {
		if (condition.equals("")) {
			return condition;
		} else {
			String jmlCondition = Parser.rewriteConditionToJML(condition);
			if (renaming != null) {
				ConstructCodeBlock.renaming = renaming;
				jmlCondition = useRenamingCondition(jmlCondition);
			}
			jmlCondition = jmlCondition.replaceAll(System.getProperty("line.separator"), "");
			jmlCondition = "@ " + postOrPre + " " + jmlCondition + ";\n";
			return jmlCondition;
		}

	}

	private static String rewriteGuardToJavaCode(String guard) {
		guard = guard.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		guard = guard.replace("&", "&&");
		guard = guard.replace("|", "||");
		return guard;
	}

	public static String useRenamingCondition(String toRename) {
		if (renaming != null) {
			for (Rename rename : renaming.getRename()) {
				toRename = toRename.replaceAll(rename.getNewName(), rename.getFunction());
			}
		}
		return toRename;
	}

	private static String extractMethodNameFromStatement(String statement) {
		// statement contains method call
		if (statement.contains(");")) {
			String methodName = statement;
			// replace xy.method() oder XY xy = new XY()
			if (methodName.contains(".") || methodName.contains("=")) {
				methodName = methodName.replaceFirst("([\\w\\[\\]]*\\s?)*(?:( )?=( )?)(?:new )?(?:\\w*\\.)?|\\w*(?:.)",
						"");
			}
			methodName = methodName.replace(";", "");
			return "\npublic static void " + methodName + "{\n }";
		} else {
			return "";
		}
	}
}