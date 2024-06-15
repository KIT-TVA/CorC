package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.junit.jupiter.api.Test;

import de.kit.tva.lost.models.DiagramResourceModelException;
import de.kit.tva.lost.models.LostTranslator;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class LostTranslatorTests {
    private LostTranslator translator = new LostTranslator();
    private String PREFIX = "D(name:test)\n\t";

    @Test
    public void arrayStatement() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: x, post: y)\n" + "\t\t{\n\t\ttmp = new int[data.length];\n\t\t}";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void corcKeyword() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: (\\exists int k; (k <= 0 & \\old(x).length -> a)), post: y)\n" + "\t\tx;";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void complexCondition() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: x, post: y)\n" + "\t\tL(inv:x, guard: data.length, var: i)\n" + "\t\t\tx;";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void complexCondition2()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: true, post: (\\exists int z;(a)) & (\\forall int k; (b -> (\\exists int z; (d = \\old(data)[lol])))) & data[data] = newTop)\n"
		+ "\t\t\tx;";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void multiLineExpression()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: x, post: y)\n" + "\t\t{\n\t\ta;\n\t\tb;\n\t\t}";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void composition() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = "F(pre: x = 0,post: x & y(a, b) )\n" + "\tC(intm: x = 1)\n" + "\t\tx += 1;\n" + "\t\tx += 1;\n";

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void nestedComposition()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: (x+1 == (quatschMitKuchen + 3)),post: y)\n" + "\tC(intm: s*2/3)\n"
		+ "\t\tC(intm: y->3)\n" + "\t\t\tx[2] = 3;\n" + "\t\t\ty = 1;\n" + "\t\treturn x;\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void selectionStatement()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String(
		"F(pre: x,post: y)\n" + "\tS(guard: x>=0,guard: x<0)\n" + "\t\treturn true;\n" + "\t\treturn false;\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void repetionStatement()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: x,post: y)\n" + "\tL(inv: y > 0,guard: x % 2 == 0,var: i)\n" + "\t\tx += 1;");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void returnStatement() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: (\\forall int i; (a + i = 0)),post: y)\n" + "\tR: x;\n");

	assertTrue(translator.translate(PREFIX + input, false));
    }

    @Test
    public void originalStatement()
	    throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: x,post: y)\n" + "\tO: original(x);\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void skipStatement() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: x,post: y)\n" + "\tskip\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void methodStatement() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: x,post: y)\n" + "\tM: x();\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void variables() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String(
		"Vars\n" + "\tLOCAL int a\n" + "\tRETURN String b\n" + "\tF(pre: x,post: y)\n" + "\t\tM: x();\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void globalConditions() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("GlobalConditions\n" + "\t(s - 2(x+1))\n" + "\tx > 1000000000000\n"
		+ "\tF(pre: x,post: y)\n" + "\t\tM: x();\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void renaming() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String(
		"Renaming\n" + "\tpostCon -> (wurstBraten(x+1))\n" + "\tF(pre: x,post: postCon)\n" + "\t\tM: x();\n");

	assertTrue(translator.translate(PREFIX + input, false));

    }

    @Test
    public void mix1() throws DiagramResourceModelException, IOException, CoreException, SettingsException {
	String input = new String("F(pre: x,post: y)\n" + "\tC(intm: x = 0)\n" + "\t\tM: x;\n"
		+ "\t\tL(inv: x,guard: y,var: i)\n" + "\t\t\tR: x;");

	assertTrue(translator.translate(PREFIX + input, false));

    }
}
