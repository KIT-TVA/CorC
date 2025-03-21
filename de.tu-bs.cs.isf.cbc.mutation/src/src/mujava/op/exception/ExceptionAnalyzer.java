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

import openjava.mop.FileEnvironment;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class ExceptionAnalyzer extends src.mujava.op.util.Mutator {

	public ExceptionAnalyzer(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit) {
		super(file_env, comp_unit);
	}
}
