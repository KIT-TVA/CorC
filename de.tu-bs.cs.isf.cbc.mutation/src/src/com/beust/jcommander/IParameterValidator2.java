/**
 * Copyright (C) 2011 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package src.com.beust.jcommander;

public interface IParameterValidator2 extends IParameterValidator {

	/**
	 * Validate the parameter.
	 *
	 * @param name
	 *            The name of the parameter (e.g. "-host").
	 * @param value
	 *            The value of the parameter that we need to validate
	 * @param pd
	 *            The description of this parameter
	 *
	 * @throws ParameterException
	 *             Thrown if the value of the parameter is invalid.
	 */
	void validate(String name, String value, ParameterDescription pd) throws ParameterException;

}
