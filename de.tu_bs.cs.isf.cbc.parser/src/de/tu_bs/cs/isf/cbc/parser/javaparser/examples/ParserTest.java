package de.tu_bs.cs.isf.cbc.parser.javaparser.examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ParserTest extends AbstractJavaParserExample {
	
	public static void main(String[] args) throws Exception {
		CompilationUnit compilationUnit = StaticJavaParser.parse(new File(FILE_PATH));

		VoidVisitor<?> methodNameVisitor = new MethodNamePrinter();
		methodNameVisitor.visit(compilationUnit,  null);
		
		List<String> methodNames = new ArrayList<>();
		VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
		methodNameCollector.visit(compilationUnit,  methodNames);
		methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));
	}
	
	private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {

		@Override
		public void visit(MethodDeclaration md, Void arg) {
			super.visit(md, arg);
			System.out.println("Method Name Printed: " + md.getName());
		}
	}
	
	private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {
		
		@Override
		public void visit(MethodDeclaration md, List<String> collector) {
			super.visit(md, collector);
			collector.add(md.getNameAsString());
		}
	}

}
