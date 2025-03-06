package de.tu_bs.cs.isf.cbc.parser.javaparser.examples;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import de.tu_bs.cs.isf.cbc.parser.javaparser.code.CommentReportEntry;

public class CommentReporterStarter extends AbstractJavaParserExample {

	public static void main(String[] args) throws Exception {
		CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
		List<CommentReportEntry> comments = cu
				.getAllContainedComments().stream().map(p -> new CommentReportEntry(p.getClass().getSimpleName(),
						p.getContent(), p.getRange().get().begin.line, !p.getCommentedNode().isPresent()))
				.collect(Collectors.toList());
		comments.forEach(System.out::println);
	}

}
