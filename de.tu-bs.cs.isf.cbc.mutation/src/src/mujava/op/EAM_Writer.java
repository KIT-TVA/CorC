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

import openjava.ptree.ArrayAccess;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.FieldAccess;
import openjava.ptree.Leaf;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.TypeName;
import openjava.ptree.Variable;
import src.mujava.op.util.MutantCodeWriter;

/**
 * <p>
 * Output and log EAM mutants to files
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class EAM_Writer extends MutantCodeWriter {
	MethodCall original = null;
	MethodCall mutant = null;

	/**
	 * Set original source code and mutated code
	 * 
	 * @param original
	 * @param mutant
	 */
	public void setMutant(MethodCall original, MethodCall mutant) {
		this.mutant = mutant;
		this.original = original;
	}

	public EAM_Writer(String file_name, PrintWriter out) {
		super(file_name, out);
	}

	public void visit(MethodCall p) throws ParseTreeException {
		if (isSameObject(p, original)) {
			// -------------------------------------------------------------
			mutated_line = line_num;
			visit(mutant);
			writeLog(removeNewline(original.toString() + " => " + mutant.toString()));
			// -------------------------------------------------------------
		} else {
			Expression expr = p.getReferenceExpr();
			TypeName reftype = p.getReferenceType();

			if (expr != null) {
				if (expr instanceof Leaf || expr instanceof ArrayAccess || expr instanceof FieldAccess
						|| expr instanceof MethodCall || expr instanceof Variable) {
					expr.accept(this);
				} else {
					writeParenthesis(expr);
				}
				out.print(".");
			} else if (reftype != null) {
				reftype.accept(this);
				out.print(".");
			}

			String name = p.getName();
			out.print(name);

			ExpressionList args = p.getArguments();
			writeArguments(args);
		}
	}
}
