package de.tu_bs.cs.isf.cbc.parser.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.JavaFileParser;
import de.tu_bs.cs.isf.cbc.parser.data.ClassSubtypes;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;

public class JavaFileParserTest {

	private static SimpleDateFormat df = new SimpleDateFormat();

	public static void main(String[] args) {
		final File parserTestClass = new File("src/de/tu_bs/cs/isf/cbc/parser/test/ParserTestClass.java");
		final JavaFileParser parser = new JavaFileParser();

		try {
			final Map<String, JavaClass> clazzes = parser.parseFile(parserTestClass);
			clazzes.forEach((name, clazz) -> {
				System.out.println("Class name: " + clazz.getClassName());
				System.out.println("Source file: " + clazz.getSourceFile());
				System.out.println("Parse time: " + df.format(clazz.getParseTime()) + " (Milliseconds: "
						+ clazz.getParseTime() + ")");
				System.out.println();
				final ClassSubtypes subtypes = clazz.getSubtypes();
				if (!subtypes.getExtendedTypes().isEmpty() || !subtypes.getImplementedTypes().isEmpty()) {
					System.out.println("Subtypes:");
					subtypes.getImplementedTypes().forEach(i -> System.out.println("\t- implements Interface: " + i));
					subtypes.getExtendedTypes().forEach(e -> System.out.println("\t- extends Class: " + e));
					System.out.println();
				}
				final List<IFieldOrMethod> fields = clazz.getFieldsAndMethods();
				if (!fields.isEmpty()) {
					System.out.println("Fields and Methods: ");
					fields.forEach(f -> System.out
							.println("\t - " + (f.getType().equals(IFieldOrMethod.Type.FIELD) ? "Field" : "Method")
									+ " declaration: " + f.toString()));
					System.out.println();
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
