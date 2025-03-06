package de.tu_bs.cs.isf.cbc.util;

/**
 * Contains the definition for a token used in the condition tokenizer and
 * parser.
 * 
 * @author Fynn
 */
public class Token {
	private TokenType type;
	private String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getRep() {
		return "TOKEN {\n\ttype=" + type.name() + "\n\tvalue=" + value + "\n}";
	}

	public void setValue(String newValue) {
		value = newValue;
	}
}
