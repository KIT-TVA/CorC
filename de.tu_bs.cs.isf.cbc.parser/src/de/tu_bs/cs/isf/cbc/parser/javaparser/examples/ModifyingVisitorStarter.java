package de.tu_bs.cs.isf.cbc.parser.javaparser.examples;

import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import de.tu_bs.cs.isf.cbc.parser.javaparser.code.IntegerLiteralModifier;

public class ModifyingVisitorStarter extends AbstractJavaParserExample {

	public static void main(String[] args) throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
		ModifierVisitor<?> numericLiteralVisitor = new IntegerLiteralModifier();
		numericLiteralVisitor.visit(cu, null);
		System.out.println(cu.toString());
	}

}
