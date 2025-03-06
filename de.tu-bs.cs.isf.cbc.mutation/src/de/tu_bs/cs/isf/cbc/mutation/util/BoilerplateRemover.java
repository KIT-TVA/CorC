package de.tu_bs.cs.isf.cbc.mutation.util;

public class BoilerplateRemover {
	public String removeIn(String input) {
		input = input.trim();
		if (input.contains("else if")) {
			return input.substring("} else if (".length(), input.length() - ") {".length());
		} else if (input.contains("if")) {
			return input.substring("if (".length(), input.length() - ") {".length());
		} else if (input.contains("while")) {
			return input.substring("while (".length(), input.length() - ") {".length());
		} else {
			return input;
		}
	}
}
