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

package src.mujava.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */

public class ExtensionFilter implements FilenameFilter {
	String mask = null;

	public ExtensionFilter() {
	}

	public ExtensionFilter(String str) {
		mask = str;
	}

	public boolean accept(File dir, String name) {
		if (mask != null) {
			if (name.indexOf("$") >= 0)
				return false;
			int index = name.lastIndexOf(".");
			String ext_name = name.substring(index + 1, name.length());
			if (ext_name.equals(mask)) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
