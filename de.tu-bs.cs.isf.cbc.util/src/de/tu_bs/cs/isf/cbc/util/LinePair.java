package de.tu_bs.cs.isf.cbc.util;

public class LinePair {
	public static String NONE = "none";
	public String originalLine;
	public String newLine;

	public LinePair(String o, String n) {
		originalLine = o.trim();
		newLine = n.trim();
	}
}

