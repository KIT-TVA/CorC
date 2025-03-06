package de.tu_bs.cs.isf.commands.toolbar.handler.family;

public class CodeMergeException extends Exception {
	private static final long serialVersionUID = 1L;

	public CodeMergeException(String msg) {
		super("CodeMergeException: " + msg);
	}
}
