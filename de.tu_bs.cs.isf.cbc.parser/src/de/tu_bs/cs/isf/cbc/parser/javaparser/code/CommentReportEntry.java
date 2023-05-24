package de.tu_bs.cs.isf.cbc.parser.javaparser.code;

public class CommentReportEntry {
	private String type;
	private String text;
	private int lineNumber;
	private boolean isOprhan;

	public CommentReportEntry(String type, String text, int lineNumber, boolean isOprhan) {
		super();
		this.type = type;
		this.text = text;
		this.lineNumber = lineNumber;
		this.isOprhan = isOprhan;
	}
	
	@Override
	public String toString() {
		return lineNumber + "|" + type + "|" + isOprhan + "|" + text.replaceAll("\\n",  "").trim();
	}
}
