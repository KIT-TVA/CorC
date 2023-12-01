package de.tu_bs.cs.isf.cbc.exceptions;

public class FileHandlerException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FileHandlerException(String msg) {
		super("FileHandlerException: " + msg);
	}
}
