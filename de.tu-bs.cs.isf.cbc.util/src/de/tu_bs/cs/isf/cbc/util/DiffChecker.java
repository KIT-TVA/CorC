package de.tu_bs.cs.isf.cbc.util;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DiffChecker {
	private String originalString;
	private String newString;
	private Queue<LinePair> diffLines;

	public DiffChecker() {
		this.diffLines = new ConcurrentLinkedQueue<LinePair>();
	}

	public void check(String originalStr, String newStr) {
		originalString = originalStr;
		newString = newStr;
		originalStr = preprocess(originalStr);
		newStr = preprocess(newStr);
		String[] originalLinesA = originalStr.split("\\n");
		String[] newLinesA = newStr.split("\\n");
		String[] originalLines = Arrays.asList(originalLinesA).stream().filter(line -> !line.trim().isEmpty())
				.toArray(String[]::new);
		String[] newLines = Arrays.asList(newLinesA).stream()
				.filter(line -> !line.trim().isEmpty() && !line.equals("null")).toArray(String[]::new);

		int max = originalLines.length > newLines.length ? originalLines.length : newLines.length;
		int min = originalLines.length < max ? originalLines.length : newLines.length;
		for (int i = 0; i < min; i++) {
			if (!originalLines[i].equals(newLines[i])) {
				diffLines.add(new LinePair((String) originalLines[i], (String) newLines[i]));
			}
		}
		if (originalLines.length < max) {
			for (int i = originalLines.length; i < max; i++) {
				diffLines.add(new LinePair(LinePair.NONE, (String) newLines[i]));
			}
		} else if (newLines.length < max) {
			for (int i = newLines.length; i < max; i++) {
				diffLines.add(new LinePair((String) originalLines[i], LinePair.NONE));
			}
		}
	}

	public LinePair nextDiff() {
		var diffLine = diffLines.poll();
		String[] lines = this.newString.split("\\n");
		String newLine = LinePair.NONE;
		String originalLine = LinePair.NONE;
		for (String line : lines) {
			String processedLine = preprocess(line);
			if (processedLine.equals(diffLine.newLine)) {
				newLine = line;
			}
		}
		lines = this.originalString.split("\\n");
		for (String line : lines) {
			String processedLine = preprocess(line);
			if (processedLine.equals(diffLine.originalLine)) {
				originalLine = line;
			}
		}
		return new LinePair(originalLine, newLine);
	}

	private String preprocess(String str) {
		String out = "";
		for (int i = 0; i < str.length(); i++) {
			if (!Arrays.asList('{', '}').contains(str.charAt(i))
					&& (str.charAt(i) == '\n' || !Character.isWhitespace(str.charAt(i)))) {
				out += str.charAt(i);
			}
		}
		return out;
	}
}
