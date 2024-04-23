package de.kit.tva.lost.tests;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import de.kit.tva.lost.models.LOSTLexer;
import de.kit.tva.lost.models.LOSTParser;
import de.kit.tva.lost.models.LOSTParser.ProgramContext;
import de.kit.tva.lost.models.LOSTTranslator;

public class LOSTTranslatorTests {
	private LOSTTranslator translator;
	
	private ProgramContext genTree(String input) {
		LOSTLexer lexer = new LOSTLexer(CharStreams.fromString(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LOSTParser parser = new LOSTParser(tokens);
		return parser.program();
	}
	
	@Test
	public void composition() {
		String input = "F(pre: x = 0,post: x = 2)\n"
			   +"\tC(intm: x = 1)\n"
			   +"\t\tx += 1;\n"
			   +"\t\tx += 1;\n";
		translator = new LOSTTranslator(genTree(input));

		translator.translate();
	}
}
