package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.kit.tva.lost.models.codeviews.BasicCodeView;

public class BasicViewTests {
    private BasicCodeView bcv = new BasicCodeView();
    private String PREFIX = "D(name: test)\n\t";

    @Test
    public void compositionSelection() {
	String input = "F(pre: a; post: b)\n"
		+ "\t\tC(intm:x)\n\t\t\tS(guard: a; guard: b)\n\t\t\t\ta;\n\t\t\t\tb;\n\t\t\tc;\n";

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals(
		"Diagram(name: test)\n\tFormula\n\t\tComposition\n\t\t\tSelection\n\t\t\t\ta;\n\t\t\t\tb;\n\t\t\tc;\n",
		basicCode);
    }

    @Test
    public void corcKeyword() {
	String input = "F(pre: (\\exists int k; (k <= 0 & \\old(x).length -> a)); post: y)\n" + "\t\tx;";

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tx;\n", basicCode);
    }

    @Test
    public void complexCondition() {
	String input = "F(pre: x; post: y)\n" + "\t\tL(inv:x; guard: data.length; var: i)\n" + "\t\t\tx;";

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tLoop\n\t\t\tx;\n", basicCode);
    }

    @Test
    public void multiLineExpression() {
	String input = "F(pre: x; post: y)\n" + "\t\t{\n\t\ta;\n\t\tb;\n\t\t}";

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\t{\n\t\t\ta;\n\t\t\tb;\n\t\t}\n", basicCode);
    }

    @Test
    public void composition() {
	String input = "F(pre: x = 0; post: x & y(a, b))\n" + "\t\tC(intm: x = 1)\n" + "\t\t\tx += 1;\n"
		+ "\t\t\tx += 1;\n";

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tComposition\n\t\t\tx += 1;\n\t\t\tx += 1;\n", basicCode);
    }

    @Test
    public void nestedComposition() {
	String input = new String("F(pre: (x+1 == (quatschMitKuchen + 3)); post: y)\n" + "\tC(intm: s*2/3)\n"
		+ "\t\tC(intm: y->3)\n" + "\t\t\tx[2] = 3;\n" + "\t\t\ty = 1;\n" + "\t\treturn x;\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals(
		"Diagram(name: test)\n\tFormula\n\t\tComposition\n\t\t\tComposition\n\t\t\t\tx[2] = 3;\n\t\t\t\ty = 1;\n\t\t\treturn x;\n",
		basicCode);
    }

    @Test
    public void selectionStatement() {
	String input = new String("F(pre: x; post: y)\n" + "\tS(guard: x>=0; guard: x<0)\n" + "\t\treturn true;\n"
		+ "\t\treturn false;\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tSelection\n\t\t\treturn true;\n\t\t\treturn false;\n",
		basicCode);
    }

    @Test
    public void repetionStatement() {
	String input = new String(
		"F(pre: x; post: y)\n" + "\tL(inv: y > 0; guard: x % 2 == 0; var: i)\n" + "\t\tx += 1;");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tLoop\n\t\t\tx += 1;\n", basicCode);
    }

    @Test
    public void returnStatement() {
	String input = new String("F(pre: (\\forall int i; (a + i = 0)); post: y)\n" + "\tR: x;\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tR: x;\n", basicCode);
    }

    @Test
    public void originalStatement() {
	String input = new String("F(pre: x; post: y)\n" + "\tO: x;\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tO: x;\n", basicCode);
    }

    @Test
    public void skipStatement() {
	String input = new String("F(pre: x; post: y)\n" + "\tskip\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tskip\n", basicCode);
    }

    @Test
    public void methodStatement() {
	String input = new String("F(pre: x; post: y)\n" + "\tM: x();\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tM: x();\n", basicCode);
    }

    @Test
    public void variables() {
	String input = new String(
		"Vars\n" + "\tLOCAL int a\n" + "\tRETURN String b\n" + "\tF(pre: x; post: y)\n" + "\t\tM: x();\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tM: x();\n", basicCode);
    }

    @Test
    public void globalConditions() {
	String input = new String("GlobalConditions\n" + "\t(s - 2(x+1))\n" + "\tx > 1000000000000\n"
		+ "\tF(pre: x; post: y)\n" + "\t\tM: x();\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tM: x();\n", basicCode);
    }

    @Test
    public void renaming() {
	String input = new String(
		"Renaming\n" + "\tpostCon -> (wurstBraten(x+1))\n" + "\tF(pre: x; post: postCon)\n" + "\t\tM: x();\n");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tM: x();\n", basicCode);
    }

    @Test
    public void mix1() {
	String input = new String("F(pre: x; post: y)\n" + "\tC(intm: x = 0)\n" + "\t\tM: x;\n"
		+ "\t\tL(inv: x; guard: y; var: i)\n" + "\t\t\tR: x;");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\tComposition\n\t\t\tM: x;\n\t\t\tLoop\n\t\t\t\tR: x;\n",
		basicCode);
    }

    @Test
    public void abstractReturn() {
	String input = new String("F(pre: x; post: x & y(a, b))\n" + "\treturn x;");

	var basicCode = bcv.transform(PREFIX + input);

	assertEquals("Diagram(name: test)\n\tFormula\n\t\treturn x;\n", basicCode);
    }
}
