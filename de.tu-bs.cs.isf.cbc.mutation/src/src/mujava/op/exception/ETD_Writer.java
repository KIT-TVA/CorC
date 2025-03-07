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
package src.mujava.op.exception;

import java.io.PrintWriter;

import openjava.ptree.ParseTreeException;
import openjava.ptree.ThrowStatement;
import src.mujava.op.util.MutantCodeWriter;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class ETD_Writer extends MutantCodeWriter {
	ThrowStatement mutant = null;

	public ETD_Writer(String file_name, PrintWriter out) {
		super(file_name, out);
	}

	public void setMutant(ThrowStatement p) {
		mutant = p;
	}

	public void visit(ThrowStatement p) throws ParseTreeException {
		if (isSameObject(p, mutant)) {
			// -------------------------
			mutated_line = line_num;
			writeLog(p.getExpression().toString() + " is deleted.");
			// -------------------------
			// NO Writing
		} else {
			super.visit(p);
		}
	}
}
