package src.mujava.op.basic;

import java.io.PrintWriter;

import openjava.ptree.BinaryExpression;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.UnaryExpression;
import src.mujava.op.util.TraditionalMutantCodeWriter;

public class PS_Writer extends TraditionalMutantCodeWriter {
	MethodDeclaration original;
	MethodDeclaration mutant;

	public PS_Writer(String file_name, PrintWriter out) {
		super(file_name, out);
	}

	/**
	 * Set original source code
	 * 
	 * @param exp1
	 */

	/**
	 * Log mutated line
	 */
	public void visit(MethodDeclaration p) throws ParseTreeException {
			super.visit(p);
			// -----------------------------------------------------------
			mutated_line = line_num;
			String log_str = p.toString() + " => " + p.toString();
			writeLog(removeNewline(log_str));
	}
}
