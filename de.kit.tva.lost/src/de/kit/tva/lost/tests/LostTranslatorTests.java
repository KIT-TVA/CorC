package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.kit.tva.lost.models.lost.LostTranslator;

public class LostTranslatorTests {
	private LostTranslator translator = new LostTranslator();
	private String PREFIX = "D(name:test)\n\t";

	@Test
	public void signature() throws Exception {
		String input = "D(sig: public void test())\n\tF(pre: a; post: b)\n\t\tx;";

		assertTrue(translator.translate(input, false));
	}

	@Test
	public void complexSignature() throws Exception {
		String input = "D(sig: protected String[] test(int param, long[] param2))\n\tF(pre: a; post: b)\n\t\tx;";

		assertTrue(translator.translate(input, false));
	}

	@Test
	public void arrayStatement() throws Exception {
		String input = "F(pre: x; post: y)\n" + "\t\t{\n\t\ttmp = new int[data.length];\n\t\t}";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void corcKeyword() throws Exception {
		String input = "F(pre: (\\exists int k; (k <= 0 & \\old(x).length -> a)); post: y)\n" + "\t\tx;";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void complexCondition() throws Exception {
		String input = "F(pre: x; post: y)\n" + "\t\tL(inv:x; guard: data.length; var: i)\n" + "\t\t\tx;";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void complexCondition2() throws Exception {
		String input = "F(pre: true; post: (\\exists int z;(a)) & (\\forall int k; (b -> (\\exists int z; (d = \\old(data)[lol])))) & data[data] = newTop)\n"
				+ "\t\t\tx;";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void multiLineExpression() throws Exception {
		String input = "F(pre: x; post: y)\n" + "\t\t{\n\t\ta;\n\t\tb;\n\t\t}";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void composition() throws Exception {
		String input = "F(pre: x = 0; post: x & y(a, b) )\n" + "\tC(intm: x = 1)\n" + "\t\tx += 1;\n" + "\t\tx += 1;\n";

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void nestedComposition() throws Exception {
		String input = new String("F(pre: (x+1 == (quatschMitKuchen + 3)); post: y)\n" + "\tC(intm: s*2/3)\n"
				+ "\t\tC(intm: y->3)\n" + "\t\t\tx[2] = 3;\n" + "\t\t\ty = 1;\n" + "\t\treturn x;\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void selectionStatement() throws Exception {
		String input = new String("F(pre: x; post: y)\n" + "\tS(guard: x>=0; guard: x<0)\n" + "\t\treturn true;\n"
				+ "\t\treturn false;\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void repetionStatement() throws Exception {
		String input = new String(
				"F(pre: x; post: y)\n" + "\tL(inv: y > 0; guard: x % 2 == 0; var: i)\n" + "\t\tx += 1;");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void returnStatement() throws Exception {
		String input = new String("F(pre: (\\forall int i; (a + i = 0)); post: y)\n" + "\tR: x;\n");

		assertTrue(translator.translate(PREFIX + input, false));
	}

	@Test
	public void originalStatement() throws Exception {
		String input = new String("F(pre: x; post: y)\n" + "\tO: original(x);\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void skipStatement() throws Exception {
		String input = new String("F(pre: x; post: y)\n" + "\tskip\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void methodStatement() throws Exception {
		String input = new String("F(pre: x; post: y)\n" + "\tM: x();\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void variables() throws Exception {
		String input = new String(
				"Vars\n" + "\tLOCAL int a\n" + "\tRETURN String b\n" + "\tF(pre: x; post: y)\n" + "\t\tM: x();\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void globalConditions() throws Exception {
		String input = new String("GlobalConditions\n" + "\t(s - 2(x+1))\n" + "\tx > 1000000000000\n"
				+ "\tF(pre: x; post: y)\n" + "\t\tM: x();\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void renaming() throws Exception {
		String input = new String("Renaming\n" + "\tint postCon -> (wurstBraten(x+1))\n"
				+ "\tF(pre: x; post: postCon)\n" + "\t\tM: x();\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void mix1() throws Exception {
		String input = new String("F(pre: x; post: y)\n" + "\tC(intm: x = 0)\n" + "\t\tM: x;\n"
				+ "\t\tL(inv: x; guard: y; var: i)\n" + "\t\t\tR: x;");

		assertTrue(translator.translate(PREFIX + input, false));

	}

	@Test
	public void array() throws Exception {
		String input = new String("Vars\n" + "\tPUBLIC int[] A\n" + "\tF(pre: x; post: postCon)\n" + "\t\tA[i] = 0;\n");

		assertTrue(translator.translate(PREFIX + input, false));

	}
}
