package de.tu_bs.cs.isf.cbc.exceptions;

public class FeatureCallerException extends Exception {
	private static final long serialVersionUID = 1L;

	public FeatureCallerException(String msg) {
		super("FeaturesException: " + msg);
	}
}
