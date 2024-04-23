package de.kit.tva.lost.tests;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import de.kit.tva.lost.models.LOSTLexer;
import de.kit.tva.lost.models.LOSTParser;

public class LOSTGrammarTests {
	private void genTree(String input) {
		LOSTLexer lexer = new LOSTLexer(CharStreams.fromString(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		LOSTParser parser = new LOSTParser(tokens);
		LOSTParser.ProgramContext tree = parser.program();
		printTree(input, tree.toStringTree());
	}
	
	private void printTree(String input, String tree) {
		System.out.println("\n+--------+");
		System.out.println("+ Input: +");
		System.out.println("+--------+");
		System.out.println(input);
		System.out.println("Tree:");
		System.out.println(tree);
		System.out.println("END");
	}
	
	@Test
	public void compositionStatement() {
		String input = new String(
				"F(pre: x = 0,post: x = 2)\n"
			   +"\tC(intm: x = 1)\n"
			   +"\t\tx += 1;\n"
			   +"\t\tx += 1;\n"
				);
		genTree(input);
	}
	
	@Test
	public void nestedComposition() {
		String input = new String(
				"F(pre: (x+1 == (quatschMitKuchen + 3)),post: y)\n"
			   +"\tC(intm: s*2/3)\n"
			   +"\t\tC(intm: y->3)\n"
			   +"\t\t\tx[2] = 3;\n"
			   +"\t\t\ty = 1;\n"
			   +"\t\treturn x;\n"
				);
		genTree(input);
	}
	
	@Test
	public void selectionStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
				+"\tS(guard: x>=0,guard: x<0)\n"
				+"\t\treturn true;\n"
				+"\t\treturn false;\n"
		);
		genTree(input);
	}
	
	@Test
	public void repetionStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tL(inv: y > 0,guard: x % 2 == 0,var: i)\n"
			   +"\t\tx += 1;"
		);
		genTree(input);
	}
	
	@Test
	public void returnStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tR: x;\n"
		);
		genTree(input);
	}
	
	@Test
	public void originalStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tO: x;\n"
		);
		genTree(input);
	}
	
	@Test
	public void skipStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tskip\n"
		);
		genTree(input);
	}

	@Test
	public void methodStatement() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tM: x();\n"
		);
		genTree(input);
	}
	
	@Test
	public void variables() {
		String input = new String(
				"Vars\n"
			   +"\tint a;\n"
			   +"\tString b;\n"
			   +"F(pre: x,post: y)\n"
			   +"\tM: x();\n"
		);
		genTree(input);
	}
	
	@Test
	public void globalConditions() {
		String input = new String(
				"GlobalConditions\n"
			   +"\t(s - 2(x+1))\n"
			   +"\tx > 1000000000000\n"
			   +"F(pre: x,post: y)\n"
			   +"\tM: x();\n"
		);
		genTree(input);
	}

	@Test
	public void renaming() {
		String input = new String(
			   "Renaming\n"
			   +"\tpostCon -> (wurstBraten(x+1))\n"
			   +"F(pre: x,post: postCon)\n"
			   +"\tM: x();\n"
		);
		genTree(input);
	}
	
	@Test
	public void mix1() {
		String input = new String(
				"F(pre: x,post: y)\n"
			   +"\tC(intm: x = 0)\n"
			   +"\t\tM: x;\n"
			   +"\t\tL(inv: x,guard: y,var: i)\n"
			   +"\t\t\tR: x;"
		);
		genTree(input);
	}
}
