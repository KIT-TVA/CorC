package de.tu_bs.cs.isf.commands.toolbar.handler.family;

public class MetaMethodException extends Exception {
	private static final long serialVersionUID = 1L;

	public MetaMethodException(String msg) {
		super("MetaMethodException: " + msg);
	}
}
