package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tokenizer for strings representing predicate formulae.
 * @author Fynn
 */
public class Tokenizer {
	private String string;
	private int cursor;
	private char current;
	
	public Tokenizer(String input) {
		// clean up input
		input = input.replaceAll("\\r\\W", "");
		input = input.replaceAll("\\n\\W", "");
		input = input.replaceAll("\\t\\W", "");
		string = input;
		cursor = 0;
		if (!input.isEmpty()) {
			current = input.charAt(0);
		}
	}
	
	private boolean hasChar() {
		return cursor < string.length();
	}
	
	public boolean getNext() {
		cursor++;
		if (!hasChar()) {
			return false;
		}
		current = string.charAt(cursor);
		return true;
	}
	
	private Token makeNumber() {
		String number = "";
		while (hasChar() && (Character.isDigit(current) || current == '-')) {
			number += current;
			getNext();
		}
		return new Token(TokenType.NUMBER, number);
	}
	
	private String makeInnerBracket() {
		String innerBracket = "";
		if (current != '[') {
			return innerBracket;
		}
		while (current != ']') {
			innerBracket += current;
			getNext();
		}
		innerBracket += current;
		getNext();
		return innerBracket;
	}
	
	private boolean isArray() {
		return current == '[';
	}
	
	/**
	 * Everything that starts with a word is considered an identifier (arrays, methodcalls, ...)
	 * @return
	 */
	private Token makeIdentifier() {
		String value = "";
		while (hasChar()) {
			if (isArray()) {
				value += makeInnerBracket();
			} else if (bracketCount(value) > 0
					|| Character.isAlphabetic(current) 
					|| current == '_' 
					|| current == '('
					|| current == ','
					|| current == '.') {
				value += current;
				getNext(); 
			} else {
				break;
			}
		}
		if (Arrays.asList("false", "true").contains(value)) {
			return new Token(TokenType.BOOL, value);
		} else {
			return new Token(TokenType.IDENT, value);
		}
	}
	
	private int bracketCount(String str) {
		var oB = str.chars().filter(c -> c == '(').count();
		var cB = str.chars().filter(c -> c == ')').count();
		return (int)(oB - cB);
	}
	
	private Token makeKeyWord() {
		String value = "";
		getNext();
		while (hasChar() && Character.isAlphabetic(current)) {
			value += current;
			getNext();
		}
		if (current == '(') {
			getNext();
			value += "_";
			var tok = makeIdentifier();
			value += tok.getValue();
			getNext();
		}
		return new Token(TokenType.KEY, value);
	}
	
	private Token makeImplicationOrRel() {
		final Token t;
		String value = "" + current;
		char lookAhead1;
		char lookAhead2;
		
		
		if (!hasChar()) {
			return null;
		}
		lookAhead1 = string.charAt(cursor+1);
		if (value.equals("-") && lookAhead1 == '>') {
			getNext();
			value += current;
			t = new Token(TokenType.IMPL, value);
		} else if (value.equals("<") && lookAhead1 == '-') {
			if (string.length() <= cursor + 2) {
				return null;
			}
			lookAhead2 = string.charAt(cursor+2);
			if (lookAhead2 == '>') {
				getNext();
				value += current;
				getNext();
				value += current;
				t = new Token(TokenType.EQUI, value);
			} else {
				t = new Token(TokenType.REL, value);
			}
		} else if (lookAhead1 == '=') {
			t = new Token(TokenType.REL, value + lookAhead1);
			getNext();
		} else if (value.equals("-")) {
			t = new Token(TokenType.OP, value);
		} else {
			t = new Token(TokenType.REL, value);
		}
		getNext();
		return t;
	}
	
	public String getString() {
		return this.string;
	}
	
	public boolean hasToken() {
		return hasChar();
	}
	
	/**
	 * For generating a stream of tokens.
	 * @return
	 */
	public Token genNext() {
		if (!hasChar()) {
			return null;
		}
		String last = current + "";
		if (Arrays.asList("+", "*", "/", "%").contains("" + current)) {
			getNext();
			return new Token(TokenType.OP, last);
		}
		else if (Arrays.asList("=", ">", "!").contains("" + current)) {
			getNext();
			if (current == '=') {
				last += "=";
				getNext();
			}
			return new Token(TokenType.REL, last);
		}
		else if (current == '(') {
			getNext();
			return new Token(TokenType.OBRACKET, last);
		}
		else if (current == ')') {
			getNext();
			return new Token(TokenType.CBRACKET, last);
		}
		else if (current == '.') {
			getNext();
			return new Token(TokenType.DOT, last);
		}
		else if (current == '&') {
			getNext();
			return new Token(TokenType.AND, last);
		}
		else if (current == '|') {
			getNext();
			return new Token(TokenType.OR, last);
		}
		else if (Character.isDigit(current)) {
			return makeNumber();
		}
		else if (Character.isLetter(current)) {
			return makeIdentifier();
		} 
		else if (current == '\\') {
			return makeKeyWord();
		} 
		else if (Arrays.asList("<", "-").contains("" + current)) {
			if (cursor + 1 == string.length()) {
				// error
				return null;
			}
			if (current == '-' && Character.isDigit(string.charAt(cursor + 1))) {
				return makeNumber();
			} else {
				return makeImplicationOrRel();
			}
		}
		else if (current == ' ') {
			getNext();
			return genNext();
		}
		else if (current == ';') {
			getNext();
			return new Token(TokenType.SEMICOLON, ";");
		}
		else {
			// not implemented
			return null;
		}
	}
	
	public List<Token> genTokens() {
		var tokens = new ArrayList<Token>();
		while (hasChar()) {
			if (Arrays.asList("+", "*", "/", "%").contains("" + current)) {
				tokens.add(new Token(TokenType.OP, "" + current));
				getNext();
			}
			else if (Arrays.asList("=", ">", "!").contains("" + current)) {
				tokens.add(new Token(TokenType.REL, "" + current));
				getNext();
			}
			else if (current == '(') {
				tokens.add(new Token(TokenType.OBRACKET, ""));
				getNext();
			}
			else if (current == ')') {
				tokens.add(new Token(TokenType.CBRACKET, ""));
				getNext();
			}
			else if (current == '.') {
				tokens.add(new Token(TokenType.DOT, ""));
				getNext();
			}
			else if (current == '&') {
				tokens.add(new Token(TokenType.AND, ""));
				getNext();
			}
			else if (current == '|') {
				tokens.add(new Token(TokenType.OR, ""));
				getNext();
			}
			else if (Character.isDigit(current)) {
				tokens.add(makeNumber());
			}
			else if (Character.isLetter(current)) {
				tokens.add(makeIdentifier());
			} 
			else if (current == '\\') {
				tokens.add(makeKeyWord());
			} 
			else if (Arrays.asList("<", "-").contains("" + current)) {
				tokens.add(makeImplicationOrRel());
			}
			else if (current == ' ') {
				getNext();
			}
			else if (current == ';') {
				tokens.add(new Token(TokenType.SEMICOLON, ";"));
				getNext();
			}
			else {
				// not implemented
				return null;
			}
		}
		return tokens;
	}

}
