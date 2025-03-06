package de.tu_bs.cs.isf.cbc.parser.data;

public interface IFieldOrMethod {

	public Type getType();

	public enum Type {
		FIELD, METHOD;
	}
}
