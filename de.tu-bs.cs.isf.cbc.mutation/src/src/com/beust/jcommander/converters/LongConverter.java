/**
 * Copyright (C) 2010 the original author or authors.
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

package src.com.beust.jcommander.converters;

import src.com.beust.jcommander.ParameterException;

/**
 * Convert a string to a long.
 * 
 * @author cbeust
 */
public class LongConverter extends BaseConverter<Long> {

	public LongConverter(String optionName) {
		super(optionName);
	}

	public Long convert(String value) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			throw new ParameterException(getErrorString(value, "a long"));
		}
	}

}
