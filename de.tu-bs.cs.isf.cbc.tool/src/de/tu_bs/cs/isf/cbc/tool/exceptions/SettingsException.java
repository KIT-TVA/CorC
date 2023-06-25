package de.tu_bs.cs.isf.cbc.tool.exceptions;

public class SettingsException extends Exception {
	private static final long serialVersionUID = 1L;

	public SettingsException(String msg) {
		super("SettingsException: " + msg);
	}
}
