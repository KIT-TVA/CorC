package de.kit.tva.lost.models;

public class TranslatorException extends Exception {
	private static final long serialVersionUID = -3806329546135967433L;

		public TranslatorException(String msg) {
			super("TranslatorException: " + msg);
		}
}
