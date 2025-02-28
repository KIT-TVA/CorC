package de.tu_bs.cs.isf.cbc.util;

import java.util.Arrays;

/**
 * Since Key Version 3.12, to access array length in a JML contract, we need to
 * use length(array) instead of array.length
 */
public final class KeYFunctionReplacer {
	private static KeYFunctionReplacer instance;

	private KeYFunctionReplacer() {
	}

	public static KeYFunctionReplacer getInstance() {
		if (instance == null) {
			instance = new KeYFunctionReplacer();
		}
		return instance;
	}

	public String replaceIn(String content) {
		int nextMatch = content.indexOf(".length");
		String identifier;

		while (nextMatch != -1) {
			var i = new UInt(nextMatch - 1);
			identifier = parseIdentifier(content, i);
			content = content.substring(0, i.get()) + "length(" + identifier + ")"
					+ content.substring(nextMatch + ".length".length(), content.length());
			nextMatch = content.indexOf(".length");
		}
		return content;
	}

	public String restoreIn(String content) {
		int nextMatch = content.indexOf("length(");
		String identifier;

		while (nextMatch != -1) {
			var matchEnd = CodeHandler.findClosingBracketIndex(content, nextMatch + "length".length(), '(');
			identifier = content.substring(nextMatch + "length(".length(), matchEnd);
			content = content.substring(0, nextMatch) + identifier + ".length"
					+ content.substring(matchEnd + 1, content.length());
			nextMatch = content.indexOf("length(");
		}
		return content;
	}

	private String parseIdentifierForwards(String content, UInt index) {
		String identifier = "";

		return identifier;

	}

	private String parseIdentifier(String content, UInt index) {
		String identifier = "";
		boolean reachedStart = false;
		while (index.get() >= 0 && Character.isJavaIdentifierPart(content.charAt(index.get()))
				|| Arrays.asList('.', ')').contains(content.charAt(index.get()))) {
			identifier += content.charAt(index.get());
			if (!index.decrease()) {
				reachedStart = true;
				break;
			}
		}
		if (!reachedStart)
			index.increase();
		identifier = new StringBuilder(identifier).reverse().toString();
		if (index.get() - "\\old(".length() >= 0) {
			if (content.substring(index.get() - "\\old(".length(), index.get()).equals("\\old(")) {
				identifier = "\\old(" + identifier;
				index.set(index.get() - "\\old(".length());
			}
		}
		return identifier;
	}

	private class UInt {
		private int value;

		public UInt(int value) {
			if (value < 0) {
				value *= -1;
			}
			this.value = value;
		}

		public int get() {
			return this.value;
		}

		public void set(int newValue) {
			if (value < 0) {
				value *= -1;
			}
			this.value = newValue;
		}

		public void increase() {
			value++;
		}

		public boolean decrease() {
			if (value > 0) {
				value--;
				return true;
			}
			return false;
		}
	}

}
