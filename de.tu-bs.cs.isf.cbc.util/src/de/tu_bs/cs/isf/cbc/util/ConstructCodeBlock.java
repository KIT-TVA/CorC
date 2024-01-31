package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl;
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
	private static String line;
	private static BufferedReader br;
	private static JavaVariable returnVariable = null;

	public static String constructCodeBlockAndVerify(AbstractStatement statement) {
		handleInnerLoops = true;
		withInvariants = false;
		StringBuffer code = new StringBuffer();

		if (statement instanceof SmallRepetitionStatement) {
			code.append(constructSmallRepetition((SmallRepetitionStatement) statement));
		}
		return code.toString();
	}

	public static String constructCodeBlockAndVerify2(AbstractStatement statement) {
		handleInnerLoops = true;
		withInvariants = false;
		StringBuffer code = new StringBuffer();

		if (statement instanceof SmallRepetitionStatement) {
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

		if (statement instanceof SmallRepetitionStatement) {
			SmallRepetitionStatement repStatement = (SmallRepetitionStatement) statement;
			if (repStatement.getLoopStatement().getRefinement() != null) {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement().getRefinement()));
			} else {
				code.append(constructCodeBlockOfChildStatement(repStatement.getLoopStatement()));
			}
		}
		return code.toString();
	}
	
	public static StringBuffer getJmlAnnotations(StringBuffer buffer, BufferedReader br) throws IOException {
        buffer.setLength(0);
        while(line != null && line.contains("@")) {//get jml annotations
        	buffer.append(line);
        	buffer.append("\n");
        	line = br.readLine();
        }
        return buffer;
	}
	
	public static void constructGlobalVariables() throws IOException {
		/*LinkedList<String> newGlobalVariables = new LinkedList<String>();
		if(vars != null && !vars.getVariables().isEmpty()) {
			for(int i = 0; i < vars.getVariables().size(); i++) {
				if(vars.getVariables().get(i).getKind() == VariableKind.GLOBAL) {
					newGlobalVariables.add(
							"\tprivate " + "static " + vars.getVariables().get(i).getName() + "; //" + signature.getMethodSignature());
				}
			}
		}*/	
		
		//StringBuffer globalVariables = new StringBuffer();
		
       // while(line != null && !line.contains("@")) {//if file exists
        //	if(!line.contains(signature.getMethodSignature()) && !line.isEmpty()) {
            	//Iterator<String> itr = newGlobalVariables.iterator();
        		//globalVariables.append(line);
        		//globalVariables.append("\n");
        		//while(itr.hasNext()) {
        			//String s = itr.next();
        			//if(s.length() > 2) {
	        			//if(s.substring(0, line.indexOf(';')).equals(line.substring(0, line.indexOf(';')))) {
	        				
	    	        	//	Console.println("**************************************************************************");
	    	        	//	Console.println("The variable " + s.substring(15, line.indexOf(';')) + " exists already.");
	       	        	//	Console.println("Implemented from the " + line.substring(line.indexOf(';') + 4, line.indexOf('(')) + " diagram.");
	    	        	//	Console.println("**************************************************************************");
	        				
	    	        	//	newGlobalVariables.remove(s);
	        			//	break;
	        			//}
        			//}
        	//	}
     //   	}
     //   	line = br.readLine();
    //    }
        
	//	if(!newGlobalVariables.isEmpty()) {
	//		newGlobalVariables.forEach(e -> {globalVariables.append(e + "\n");});
	//	}
		
	//	return globalVariables.toString();
	}
	
	public static StringBuffer editCodeBlockForExport(
			String methodCode, File javaFile, MethodSignature signature, String vars) throws IOException {
		
		StringBuffer newCode = new StringBuffer();
        StringBuffer jmlCode = new StringBuffer();
        StringBuffer copyMethod = new StringBuffer();
        
		br = new BufferedReader(new FileReader(javaFile));					
		
		line = br.readLine();
		newCode.append(line);
		newCode.append("\n\n");
        
		br.readLine();
        line = br.readLine();
        
 //       String globalVariables = constructGlobalVariables(vars, signature);
 //       constructGlobalVariables();
        while(line != null && !line.contains("@")) {
        	line = br.readLine();
        }
        
        if(vars.length() > 2) {
        	newCode.append(vars);
        	newCode.append("\n");
        }
		
        while(line != null) {
        	jmlCode = getJmlAnnotations(jmlCode, br);
        	String s = signature.getMethodSignature();//.replace("static ", "");
//        	s = s.trim().substring(s.indexOf(' ') + 1);
	        if(line.contains(s)) {//delete old implementation
				line = br.readLine();
	        	int counter = 1;
				while(line != null) {
					if(line.contains("{")) {
						++counter;
					} 
					if(line.contains("}")) {
						--counter;
					}
	                if(counter == 0) {
	                	br.readLine();
	                	line = br.readLine();
	                	break;
	                }
					line = br.readLine();
				}
	        } else if(!line.equals("}")){//copy implemented methods
	        	newCode.append(jmlCode);
	        	copyMethod.append(line);
	        	copyMethod.append("\n");
	    		line = br.readLine();
	    		int counter = 1;
	    		while(line != null) {
	    			if(line.contains("{")) {
						++counter;
					} 
	    			if(line.contains("}")) {
						--counter;
					}
	    			if(counter == 0) {
	    				copyMethod.append("\t}\n\n");
	    				newCode.append(copyMethod);
	    				copyMethod.setLength(0);
	    				br.readLine();
	    				line = br.readLine();
	    				break;
	    			}
	    			copyMethod.append(line);
	    			copyMethod.append("\n");
	    			line = br.readLine();
	    		}
	        } else {
	        	line = br.readLine();
	        }
        }
        newCode.append(methodCode);
		br.close();
		return newCode;
	}
	
	private static StringBuffer insertTabs(StringBuffer s) {
		for (int i = 0; i < positionIndex; i++) {
			s.append("\t");
		}
		return s;
	}
	
	public static String constructCodeBlockForExport(
			CbCFormula formula, Renaming renaming, LinkedList<String> vars, JavaVariable returnVar, MethodSignature signature) {
		handleInnerLoops = true;
		withInvariants = true;
		
		String modifiableVariables = Parser
				.getModifieableVarsFromCondition2(formula.getStatement().getPostCondition().getName(),vars);
		String postCondition = Parser.getConditionFromCondition(formula.getStatement().getPostCondition().getName());

		String pre = createConditionJMLString(formula.getStatement().getPreCondition().getName(), renaming,
				Parser.KEYWORD_JML_PRE);
		pre = useRenamingCondition(pre);
		
		String post = createConditionJMLString(postCondition, renaming, Parser.KEYWORD_JML_POST);
		post = useRenamingCondition(post);
		
		if(returnVar != null) {
			String returnValueName = returnVar.getName().split(" ")[1];
			post = post.replaceAll("(?<=\\W)" + returnValueName + "(?=\\W)", "\\\\result");
			returnVariable = returnVar; 
		}
		
		StringBuffer code = new StringBuffer();
		System.out.println(System.getProperties());
		code.append("\t/*@\n" + "\t@ normal_behavior\n" //+ "@ requires "
				+ pre.replaceAll(System.getProperty("line.separator"), "")// + ";\n" //+ "@ ensures "
				+ post.replaceAll(System.getProperty("line.separator"), "")/* + ";\n"*/ + "\t@ assignable "
				+ modifiableVariables + ";\n" + "\t@*/\n" + "\tpublic "+ signature.getMethodSignature() 
				+ " {\n");

		positionIndex = 2;//2
		code = insertTabs(code);
		
		for(String var : vars) {//declare variables
			code.append(var + ";\n");
			code = insertTabs(code);
		}
		
		String s;
		if (formula.getStatement().getRefinement() != null) {
			s = constructCodeBlockOfChildStatement(formula.getStatement().getRefinement());
			if (renaming != null) 
				s = useRenamingCondition(s);
			code.append(s);
		} else {
			s = constructCodeBlockOfChildStatement(formula.getStatement());
			if (renaming != null) 
				s = useRenamingCondition(s);
			code.append(s); 
		}
		
		code.append("\n\t}");//}

		returnVariable = null;
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
				|| refinement.getClass().equals(ReturnStatementImpl.class)) {
			return "";
		} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
			return traverseSelection((SelectionStatement) refinement);
		} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
			return traverseComposition((CompositionStatement) refinement);
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
		

	public static String constructMethodStubsForExport(CbCFormula formula, Renaming renaming, JavaVariables vars, String feature, String project) {
		handleInnerLoops = true;
		withInvariants = false;
		
		String modifiableVariables = Parser
				.getModifieableVarsFromCondition(formula.getStatement().getPostCondition().getName());
		if (vars != null) {
			for (JavaVariable actVar: vars.getVariables()) {
				if (actVar.getKind().getName() != "PARAM") {
					String splitName[] = actVar.getName().split(" ");
					modifiableVariables = modifiableVariables.replaceAll("," + splitName[splitName.length-1],"");
					modifiableVariables = modifiableVariables.replaceAll(splitName[splitName.length-1] + ";",";");
					modifiableVariables = modifiableVariables.replaceAll(splitName[splitName.length-1] + ",","");
				}
			}
		}
		
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
			case RETURNPARAM:
				String[] splitNameAndType2 = var.getName().split(" ");
				// get type of variable not whole name
				returnValueType = splitNameAndType2[0];
				// get only the name
				String returnValueName2 = splitNameAndType2[1];
				post = post.replaceAll("(?<=\\W)" + returnValueName2 + "(?=\\W)", "\\\\result");
				if (parameters.equals("")) {
					parameters += var.getName();
				} else {
					parameters += ", " + var.getName();
				}
				break;
			default:
				break;
			}
		}		
		
		StringBuffer code = new StringBuffer();
		System.out.println(System.getProperties());
		code.append("    /*@\n    @ normal_behavior\n" + pre + post + "    @ assignable "
				+ modifiableVariables + ";\n" + "    @*/\n" + "    public static " + returnValueType + " " + feature.toLowerCase()
				+ "(" + parameters + ") {\n    }");

		// traverse through tree and add stubs for called methods to the String
		/*if (formula.getStatement() != null) {
			code.append(constructMethodStubOfChildStatement(formula.getStatement().getRefinement()));
		} else {
			code.append(constructMethodStubOfChildStatement(formula.getStatement()));
		}*/

		return code.toString();
	}



	private static String constructCodeBlockOfChildStatement(AbstractStatement refinement) {
		if (refinement.getClass().equals(AbstractStatementImpl.class)) {
			// behandlung von AbstractStatementImpl nur von Tobi
			String allStatements = refinement.getName().replace("\r\n", "");
			allStatements = allStatements.trim();
			allStatements = allStatements.replaceAll("\\s+", " ");
			//allStatements = allStatements.split("\\w\\=\\w")[0]+ " = " + allStatements.split("\\w\\=\\w")[1];
			allStatements = allStatements.replace("/ =", " /= ");
			allStatements = allStatements.replace("+ =", " += ");
			allStatements = allStatements.replace("- =", " -= ");
			allStatements = allStatements.replace("* =", " *= ");

			String abstractStatementSplit[] = allStatements.split(";");
			String statements;
			if (abstractStatementSplit.length > 1) {
				statements = abstractStatementSplit[0].trim() + ";\n";
				for (int i = 1; i < abstractStatementSplit.length; i++) {
					for (int j = 0; j < positionIndex; j++) {
						statements = statements + "\t";
					}
					statements = statements + (abstractStatementSplit[i].trim() + ";\n");
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
			if(returnVariable != null) {//In case of void method with "return;", returnVariable will be null
				String returnString = returnStatement(returnVariable.getName().split(" ")[1], refinement.getName().trim());
				if(returnString.isEmpty()) {
					return "return " + refinement.getName() + "\n";
				}				
				for(int i = 0; i < positionIndex; i++) {
					returnString = returnString + "\t";
				}
				returnString = returnString + "return " + returnVariable.getName().split(" ")[1] + ";\n";
				return returnString; 
			}
			return "return " + refinement.getName() + "\n";
		} else if (refinement.getClass().equals(SelectionStatementImpl.class)) {
			return constructSelection((SelectionStatement) refinement);
		} else if (refinement.getClass().equals(CompositionStatementImpl.class)) {
			return constructComposition((CompositionStatement) refinement);
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

	private static String returnStatement(String variableName, String refinementName) {
		String s = "";
		if(!refinementName.trim().split(";")[0].equals(variableName)
				&& !refinementName.trim().split(";")[0].equals("this." + variableName)) {
			if(refinementName.contains("=") 
					&& refinementName.charAt(refinementName.indexOf('=') + 1) != '='
					&& refinementName.charAt(refinementName.indexOf('=') - 1) != '>'
					&& refinementName.charAt(refinementName.indexOf('=') - 1) != '<') {
				//s = variableName + refinementName.replace(refinementName.subSequence(0, refinementName.indexOf('=')), "") + "\n";
				s = refinementName + "\n";
				if(!refinementName.trim().substring(0, refinementName.indexOf('=') - 1).equals(variableName)) {
					for(int i = 0; i < positionIndex; i++) {
						s = s + "\t";
					}
					s = s + variableName + " = " + refinementName.trim().split("=")[0] + ";\n";	
				} 
			} else {
				s = variableName + " = " + refinementName + "\n";
			}
		}
		return s;
	}
	
	private static String constructSelection(SelectionStatement statement) {
		StringBuffer buffer = new StringBuffer();

		if (!statement.getCommands().isEmpty()) {
			String guard = statement.getGuards().get(0).getName();

			guard = rewriteGuardToJavaCode(guard);

			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
				guard = "false";
			
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
			
			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
				guard = "false";
			
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
				buffer.append(constructCodeBlockOfChildStatement(statement.getCommands().get(i)));
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
	
	private static String constructSmallRepetition(SmallRepetitionStatement statement) {
		StringBuffer buffer = new StringBuffer();
		if (handleInnerLoops) {
			if (withInvariants) {
				String invariant = statement.getInvariant().getName();
				invariant = Parser.rewriteConditionToJML(invariant);
				//invariant = useRenamingCondition(invariant);
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
			
			if(guard.trim().equals("TRUE"))
				guard = "true";
			if(guard.trim().equals("FALSE"))
				guard = "false";
			
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
				//jmlCondition = useRenamingCondition(jmlCondition);
			}
			jmlCondition = jmlCondition.replaceAll(System.getProperty("line.separator"), "");
			jmlCondition = "\t@ " + postOrPre + " " + jmlCondition + ";\n";
			return jmlCondition;
		}

	}

	private static String rewriteGuardToJavaCode(String guard) {
		guard = guard.replaceAll("(?<!<|>|!|=)(\\s*=\\s*)(?!<|>|=)", " == ");
		guard = guard.replace("&", "&&");
		guard = guard.replace("|", "||");
		guard = guard.replaceAll("\\s+TRUE\\s*|TRUE\\s+", " true ");
		guard = guard.replaceAll("\\s+FALSE\\s*|FALSE\\s+", " false ");
		guard = guard.trim();
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