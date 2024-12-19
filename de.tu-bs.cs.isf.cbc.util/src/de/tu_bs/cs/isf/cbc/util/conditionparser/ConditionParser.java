package de.tu_bs.cs.isf.cbc.util.conditionparser;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.exceptions.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.Token;
import de.tu_bs.cs.isf.cbc.util.TokenType;
import de.tu_bs.cs.isf.cbc.util.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a post condition given in JavaDL syntax into an AST.
 * Grammar:
 * There are 4 non-terminals: Condition, Predicate, Atom and Math. If a name is wrapped inside brackets '{name}' 
 * then it means that name stands for multiple objects.
 * +===========================================================================================================+
 * + M :-> {ID} {OP} M | {INT} {OP} M | {ID} | {INT}													       +
 * + A :-> (C) | {ID} {REL} M | M {REL} {ID} | {ID} = {BOOL} | {ID} != {BOOL} | !{bool/{ID}} | {bool/{ID}} | M +
 * + P :-> A -> P | A <-> P | A & P | A or P | A {REL} P | A | {KEY_Q}{ID}{ID};(P)	                           +
 * + C :-> P & C | P															                               +
 * +===========================================================================================================+
 * {ID} = All idenitifiers + old keyword='{KEY}({ID})', {REL} = {<, <=, =, !=, >=, >}, {OP} = {+, -, *, /, %}, 
 * {BOOL} = {false, true}
 * {KEY_Q} = {forall, exists}
 * Math expression are treated as part of identifiers and are not handled separately.
 * Note:
 * - This parser doesn't support CorC's special words for representing predicates that have the following syntax: 
 * <word> with word being a identifier for some function. If you wish to support this as well
 * you'll need to add a new rule to the grammar of this parser.
 * @author Fynn
 */
public class ConditionParser {
	private Tokenizer tokenizer;
	private Token curToken;
	private Token nextToken;
	
	public ConditionParser() {
		curToken = null;
		nextToken = null;
	}
	
	// TODO: Check this for all possibilities
	private String removeCorCKeywords(String postCondition) {
		int start = postCondition.indexOf("<");
		if (start == -1) {
			return postCondition;
		}
		String helper = postCondition.substring(start, postCondition.length());
		int end = helper.indexOf(">") + start;
		String toCheck = postCondition;
		String word = null;
		int len = 0;
		
		while (start != -1 && end != -1) {
			if (start >= end) {
				break;
			}
			//check if there is a single word between '<' and '>'
			word = toCheck.substring(start + 1, end);
			len = word.length();
			word = word.replaceAll("\\W", word);
			if (word.length() == len) {
				// means it is a keyword
				if (postCondition.charAt(start + postCondition.length() - toCheck.length() - 1) == '.') {
					start--;
				}
				postCondition = postCondition.substring(0, start + postCondition.length() - toCheck.length()) 
						+ postCondition.substring(end + 1 + postCondition.length() - toCheck.length(), postCondition.length());
				start = end;
			}
			toCheck = toCheck.substring(start + 1, toCheck.length());
			start = toCheck.indexOf("<");
			if (start == -1) {
				break;
			}
			helper = toCheck.substring(start, toCheck.length());
			end = helper.indexOf(">") + start;
		}
		return postCondition;
	}
	
	private String prepareCondition(String postCondition) {
		// TODO: Implement support for CorC specific keywords of the form '<keyword>'
		// postCondition = removeCorCKeywords(postCondition);
		postCondition = postCondition.replaceAll("\\\\result", "result");
		postCondition = postCondition.replaceAll("\\r", "");
		postCondition = postCondition.replaceAll("\\n", "");
		postCondition = postCondition.replaceAll("\\t", "");
		return postCondition;
	}
	
	public Node parse(String postCondition) throws UnexpectedTokenException {
		postCondition = prepareCondition(postCondition);
		tokenizer = new Tokenizer(postCondition);
		//var allTokens = tokenizer.genTokens();
		nextToken();
		nextToken();
		Node resultTree = parseCondition();
		if (hasToken()) {
			return null;		
		}
		return resultTree;
	}
	
	private boolean hasToken() {
		return this.nextToken != null;
	}
		
	// C :-> P & C | P
	private Node parseCondition() throws UnexpectedTokenException {
		var p = parsePredicate();
		if (curToken == null) {
			return p;
			//return p;
		} else if (curToken.getType() == TokenType.AND) {
			nextToken();
			var c = parseCondition();
			return new BinaryNode(TokenType.AND, p, c);
		} else {
			return p;
		}
	}
	
	// P :-> A -> P | A <-> P | A & P | A or P | A {REL} P | A | {KEY_Q}{ID}{ID};(P)
	private Node parsePredicate() throws UnexpectedTokenException {
		var a = parseAtom();
		if (curToken == null) {
			return a;
		} else if (curToken.getType() == TokenType.IMPL) {
			nextToken();
			var p = parsePredicate();
			return new BinaryNode(TokenType.IMPL, a, p);
		} else if (curToken.getType() == TokenType.EQUI) {
			nextToken();
			var p = parsePredicate();
			return new BinaryNode(TokenType.EQUI, a, p);
		} else if (curToken.getType() == TokenType.AND) {
			nextToken();
			var p = parsePredicate();
			return new BinaryNode(TokenType.AND, a, p);
		} else if (curToken.getType() == TokenType.OR) {
			nextToken();
			var p = parsePredicate();
			return new BinaryNode(TokenType.OR, a, p);
		} else if (curToken.getType() == TokenType.REL) {
			nextToken();
			var p = parsePredicate();
			return new RelNode(TokenType.REL, curToken.getValue(), a, p);
		} else if (curToken.getType() == TokenType.KEY) {
			// {KEY_Q}{ID}{ID};((P)->A)
			if (!Arrays.asList("forall", "exists").contains(curToken.getValue())) {
				return null;
			}
			QuantorNode qn = new QuantorNode(curToken.getValue());
			nextToken();
			if (curToken.getType() != TokenType.IDENT) {
				return null;
			}
			qn.addIteratorType(curToken);
			nextToken();
			if (curToken.getType() != TokenType.IDENT) {
				return null;
			}
			qn.addIterator(curToken);
			nextToken();
			if (curToken.getType() != TokenType.SEMICOLON) {
				return null;
			}
			nextToken();
			var p = parsePredicate();
			qn.addConditions(p);
			return qn;
		} else {
			return a;
		}
	}
	
	// M :-> {ID} {OP} M | {INT} {OP} M | {ID} | {INT}
	private Node parseM() throws UnexpectedTokenException {
		// TODO: this doesn't account for associativity.
		OpNode opNode = new OpNode();
		if (curToken == null) {
			return null;
		} else if (curToken.getType() == TokenType.IDENT 
				|| curToken.getType() == TokenType.NUMBER) 
		{
			opNode.addLeft(curToken);
			if (nextToken == null || nextToken.getType() != TokenType.OP) {
				if (nextToken != null && nextToken.getValue().startsWith("-") && !nextToken.getValue().contains(">")) {
					nextToken.setValue(nextToken.getValue().substring(1, nextToken.getValue().length()));
					opNode.addOperator(new Token(TokenType.OP, "-"));
					nextToken();
					var m = parseM();
					opNode.addRight(m);
					return opNode;
					
				} else {
					var prev = curToken;
					nextToken();
					return new SingleNode(prev.getType(), prev.getValue());
				}
			}
			nextToken();
			opNode.addOperator(curToken);
			nextToken();
			var m = parseM();
			opNode.addRight(m);
			return opNode;
		} else if (curToken.getType() == TokenType.KEY) {
			if (Arrays.asList("exists", "forall").contains(curToken.getValue())) {
				return null;
			}
			opNode.addLeft(curToken);
			if (nextToken == null || nextToken.getType() != TokenType.OP) {
				var prev = curToken;
				nextToken();
				return new SingleNode(prev.getType(), prev.getValue());
			}
			nextToken();
			opNode.addOperator(curToken);
			nextToken();
			var m = parseM();
			opNode.addRight(m);
			return opNode;
		}
		else {
			return null;
		}
	}
	
	// A :-> (C) | {ID} {REL} M | M {REL} {ID} | {ID} = {BOOL} | {ID} != {BOOL} | !{bool/{ID}} | {bool/{ID}} | M
	private Node parseAtom() throws UnexpectedTokenException {
		var m = parseM();
		if (curToken == null) {
			return m;
		}
		else if (curToken.getType() == TokenType.REL) {
			if (curToken.getValue().equals("!")) {
				nextToken();
				var prev = curToken;
				nextToken();
				return new NotNode(new SingleNode(prev.getType(), prev.getValue()));
			}
			RelNode relNode = new RelNode();
			relNode.addIdentifierNode(m);
			relNode.addRel(curToken.getValue());
			nextToken();
			var a = parseAtom();
			relNode.addBoundaryNode(a);
			return relNode;
		} 	
		else if (curToken.getType() == TokenType.IDENT || curToken.getType() == TokenType.KEY) {
			RelNode relNode = new RelNode();
			if (Arrays.asList("exists", "forall").contains(curToken.getValue())) {
				return null;
			}
			if (nextToken == null || nextToken.getType() != TokenType.REL) {
				var prev = curToken;
				nextToken();
				return new SingleNode(prev.getType(), prev.getValue());
			}
			relNode.addIdentifier(curToken);
			nextToken();
			relNode.addRel(curToken.getValue());
			nextToken();
			if (curToken.getType() != TokenType.IDENT && 
					curToken.getType() != TokenType.NUMBER &&
					curToken.getType() != TokenType.BOOL &&
					curToken.getType() != TokenType.KEY) {
				Console.println("ParserError: Wrong token type.");
				return null;
			}
			m = parseM();
			if (m != null) {
				relNode.addBoundaryNode(m);
			} else {
				relNode.addBoundary(curToken);
			}
			nextToken();
			return relNode;
		} else if (curToken.getType() == TokenType.OBRACKET) {
			nextToken();
			var condition = parseCondition();
			if (condition == null) {
				return null;
			}
			if (curToken.getType() == TokenType.CBRACKET) {
				nextToken();
				return condition;
			} else {
				return null;
			}
		} else if (curToken.getType() == TokenType.BOOL) {
			SingleNode s = new SingleNode(TokenType.BOOL, curToken.getValue());
			nextToken();
			return s;
		}
		else {
			return m;
		}
	}
	
	public void nextToken() throws UnexpectedTokenException {
		curToken = nextToken;
		nextToken = tokenizer.genNext();
	}
	
	//+++++++++++++++++++++++++++++++++STATIC (OLD) CONDITION PARSING+++++++++++++++++++++++++++++++++
	
	/**
	 * Returns a string of all preconditions in JavaDL syntax or empty string, when there is no precondition.
	 * @param globalConditions
	 * @param formula
	 * @return
	 */
	public static String parseConditions(final GlobalConditions globalConditions, final Condition preCondition) {
		// By definition we know that the root formula does contain the strongest precondition,
		// therefore we only need to parse it's preconditions as any following refinement
		// can't have more (=stronger) requirements.
		var preCon = preCondition.getName().trim();
		if (preCon.equals("true")) {
			preCon = "";
		}
		// add invariants 
		List<String> invariants = globalConditions == null ? new ArrayList<String>() : globalConditions.getConditions().stream()
				.map(c -> c.getName())
				.toList();
		String invariantsStr = "";
		// non-null conditions can be discarded because the generator always generates values for complex data types.
		final Pattern p = Pattern.compile("[A-Z]\\w*\\.\\w+");
		int counter = 0;
		for (int i = 0; i < invariants.size(); i++) {
			if (!invariants.get(i).contains("null") && !invariants.get(i).contains("self")) {
				Matcher m = p.matcher(invariants.get(i));
				String con = invariants.get(i);
				while (m.find()) {
					return "";
					
					//con = con.substring(0, m.start()) + con.substring(m.start() + m.group(0).indexOf('.') + 1, con.length());
				}
				if (counter > 0) {
					invariantsStr += " & " + con;
				} else {
					invariantsStr += con;
				}
				counter++;
			}
		}
		//invariantsStr = invariantsStr.replaceAll("this\\.", "");
		if (preCon.isEmpty()) {
			return invariantsStr;
		} else {	
			if (invariantsStr.isEmpty()) {
				return preCon;
			} else {
				return preCon + " & " + invariantsStr;
			}
		}
	}
}
