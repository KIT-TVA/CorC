package de.tu_bs.cs.isf.cbc.util;

public class Renamer {
	private String pattern;
	private String replacement;
	
	/**
	 * Instantiate the renaming helper class.
	 * 
	 * @param from	the String name to be replaced
	 * @param to	the String name to be replaced with
	 */
	Renamer(String from, String to) {
		this.pattern = "(^|[^a-zA-Z0-9_])" + from + "[(]";
		this.replacement = "$1" + to + "(";
	}
	
	public String rename(String str)  {
		return str.replaceAll(pattern, replacement);
	}
}
