package de.tu_bs.cs.isf.cbc.tool.exceptions;

/**
 * Can be thrown when any exception in class PreConditionSolver occurs.
 * @author Fynn
 */
public class PreConditionSolverException extends Exception {
	private static final long serialVersionUID = 1L;

	public PreConditionSolverException(String msg) {
		super("PreConditionSolverException: " + msg);
	}
}
