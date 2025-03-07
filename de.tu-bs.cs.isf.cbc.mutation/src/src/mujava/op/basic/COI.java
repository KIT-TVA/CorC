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
package src.mujava.op.basic;

import java.io.IOException;
import java.io.PrintWriter;

import openjava.mop.FileEnvironment;
import openjava.mop.OJSystem;
import openjava.ptree.BinaryExpression;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.Expression;
import openjava.ptree.FieldAccess;
import openjava.ptree.ParseTreeException;
import openjava.ptree.UnaryExpression;
import openjava.ptree.Variable;

/**
 * <p>
 * Generate COI (Conditional Operator Insertion) mutants -- insert logical
 * operators (and-&&, or-||, and with no conditional evaluation-&, or with no
 * conditional evaluation-|, not equivalent-^)
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class COI extends MethodLevelMutator {
	public COI(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit) {
		super(file_env, comp_unit);
	}

	public void visit(UnaryExpression p) {
		// NO OP
	}

	public void visit(Variable p) throws ParseTreeException {
		if (getType(p) == OJSystem.BOOLEAN) {
			outputToFile(p);
		}
	}

	public void visit(FieldAccess p) throws ParseTreeException {
		if (getType(p) == OJSystem.BOOLEAN) {
			outputToFile(p);
		}
	}

	public void visit(BinaryExpression p) throws ParseTreeException {
		Expression left = p.getLeft();
		left.accept(this);
		Expression right = p.getRight();
		right.accept(this);

		if (getType(p) == OJSystem.BOOLEAN) {
			outputToFile(p);
		}
	}

	/**
	 * Output COI mutants to files
	 * 
	 * @param original
	 */
	public void outputToFile(BinaryExpression original) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("COI");
		String mutant_dir = getMuantID("COI");

		try {
			PrintWriter out = getPrintWriter(f_name);
			COI_Writer writer = new COI_Writer(mutant_dir, out);
			writer.setMutant(original);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}

	/**
	 * Output COI mutants to files
	 * 
	 * @param original
	 */
	public void outputToFile(Variable original) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("COI");
		String mutant_dir = getMuantID("COI");

		try {
			PrintWriter out = getPrintWriter(f_name);
			COI_Writer writer = new COI_Writer(mutant_dir, out);
			writer.setMutant(original);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}

	/**
	 * Output COI mutants to files
	 * 
	 * @param original
	 */
	public void outputToFile(FieldAccess original) {
		if (comp_unit == null)
			return;

		String f_name;
		num++;
		f_name = getSourceName("COI");
		String mutant_dir = getMuantID("COI");

		try {
			PrintWriter out = getPrintWriter(f_name);
			COI_Writer writer = new COI_Writer(mutant_dir, out);
			writer.setMutant(original);
			writer.setMethodSignature(currentMethodSignature);
			comp_unit.accept(writer);
			out.flush();
			out.close();
		} catch (IOException e) {
			System.err.println("fails to create " + f_name);
		} catch (ParseTreeException e) {
			System.err.println("errors during printing " + f_name);
			e.printStackTrace();
		}
	}
}
