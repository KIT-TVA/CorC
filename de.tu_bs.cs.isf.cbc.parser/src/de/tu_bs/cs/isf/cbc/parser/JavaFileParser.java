package de.tu_bs.cs.isf.cbc.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import de.tu_bs.cs.isf.cbc.parser.data.ClassSubtypes;
import de.tu_bs.cs.isf.cbc.parser.data.ConstructorDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;

public class JavaFileParser {

	public Map<String, JavaClass> parseFile(final File file) throws FileNotFoundException {
		final Map<String, JavaClass> clazzes = new ConcurrentHashMap<>();
		final CompilationUnit cu = StaticJavaParser.parse(file);
		final Map<String, ClassSubtypes> subtypes = new ConcurrentHashMap<>();
		final Map<String, List<IFieldOrMethod>> fieldsAndMethods = new ConcurrentHashMap<>();
		final Map<String, ConstructorDefinition> constructors = new ConcurrentHashMap<>();
		parseClassSubtypes(cu, subtypes);
		parseConstructors(cu, constructors);
		parseFields(cu, fieldsAndMethods);
		parseMethods(cu, fieldsAndMethods);
		fieldsAndMethods.forEach((k, v) -> {
			final JavaClass clazz = new JavaClass(k, file.getPath(), System.currentTimeMillis(), v, subtypes.get(k),
					constructors.get(k));
			clazzes.put(k, clazz);
		});

		return clazzes;
	}

	private void parseClassSubtypes(final CompilationUnit cu, final Map<String, ClassSubtypes> subtypes) {
		final ClassCollector classCollector = new ClassCollector();
		classCollector.visit(cu, subtypes);
	}

	private void parseConstructors(final CompilationUnit cu, final Map<String, ConstructorDefinition> constructors) {
		final ConstructorCollector constructorCollector = new ConstructorCollector();
		constructorCollector.visit(cu, constructors);
	}

	private void parseFields(final CompilationUnit cu, final Map<String, List<IFieldOrMethod>> fieldsAndMethods) {
		final FieldCollector fieldCollector = new FieldCollector();
		fieldCollector.visit(cu, fieldsAndMethods);
	}

	private void parseMethods(final CompilationUnit cu, final Map<String, List<IFieldOrMethod>> fieldsAndMethods) {
		final MethodCollector methodCollector = new MethodCollector();
		methodCollector.visit(cu, fieldsAndMethods);
	}
}
