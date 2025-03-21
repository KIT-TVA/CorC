/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package src.mujava.op;

import java.io.PrintWriter;

import openjava.ptree.MethodDeclaration;
import openjava.ptree.Parameter;
import openjava.ptree.ParseTreeException;
import openjava.ptree.StatementList;
import src.mujava.op.util.MutantCodeWriter;

/**
 * <p>
 * Output and log PPD mutants to files
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class PPD_Writer extends MutantCodeWriter {
	Parameter original = null;
	Parameter mutant = null;
	MethodDeclaration targetMethod = null;
	StatementList targetStmts = null;

	/**
	 * Set original source code and mutated code
	 * 
	 * @param m
	 *            -- method to be mutated
	 * @param original
	 * @param mutant
	 */
	public void setMutant(MethodDeclaration m, Parameter original, Parameter mutant) {
		this.targetMethod = m;
		this.original = original;
		this.mutant = mutant;
		this.targetStmts = m.getBody();
	}

	public PPD_Writer(String file_name, PrintWriter out) {
		super(file_name, out);
	}

	/**
	 * Log mutated line
	 */
	public void visit(StatementList p) throws ParseTreeException {
		if (isSameObject(p, targetStmts)) {
			mutated_line = line_num;
			writeTab();
			out.println(mutant.toString() + " = (" + mutant.getTypeSpecifier().getName() + ")_" + original.getVariable()
					+ ";");
			line_num++;
			writeLog(removeNewline(original.toString() + " => " + mutant.toString()));
		}
		writeList(p);
	}

	public void visit(Parameter p) throws ParseTreeException {
		if (isSameObject(p, original)) {
			// -------------------------------------------------------------
			// mutated_line = line_num;
			Parameter temp = (Parameter) p.makeRecursiveCopy();
			temp.setVariable("_" + p.getVariable());
			visit(temp);
			// writeLog(removeNewline(original.toString()+" => "+mutant.toString()));
			// -------------------------------------------------------------
		} else {
			super.visit(p);
		}
	}
}
