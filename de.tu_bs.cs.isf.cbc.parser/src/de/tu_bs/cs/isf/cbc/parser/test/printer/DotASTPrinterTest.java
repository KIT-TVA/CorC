package de.tu_bs.cs.isf.cbc.parser.test.printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DotPrinter;

public class DotASTPrinterTest {

	/**
	 * https://javaparser.org/inspecting-an-ast/ ^ dot -Tpdf ast.dot > ast.pdf
	 * 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final CompilationUnit cu = StaticJavaParser
				.parse(new File("src/de/tu_bs/cs/isf/cbc/parser/test/printer/PrinterTestClass.java"));
		DotPrinter printer = new DotPrinter(true);
		try (FileWriter fileWriter = new FileWriter("ast.dot");
				final PrintWriter printWriter = new PrintWriter(fileWriter)) {
			printWriter.print(printer.output(cu));
		}
	}
}
