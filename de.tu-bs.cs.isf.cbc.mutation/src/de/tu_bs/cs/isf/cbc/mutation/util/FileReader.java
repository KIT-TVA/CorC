package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.tu_bs.cs.isf.cbc.tool.helper.CodeHandler;

public class FileReader {
	private Scanner scanner;
	private File f;

	public FileReader(File f) {
		this.f = f;
	}

	public String readLines() throws FileNotFoundException {
		String code = "";
		scanner = new Scanner(f);
		code = clear();
		code = CodeHandler.indentCode(code, 0);
		scanner.close();
		return code;
	}
	
	private String clear() {
		String code = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (isMuJavaComment(line)) {
				continue;
			}
			code += line + "\n";
		}
		return code;
	}

	private boolean isMuJavaComment(String line) {
		return line.contains("//");
	}
}
