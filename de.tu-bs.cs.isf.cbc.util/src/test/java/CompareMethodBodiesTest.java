package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;

public class CompareMethodBodiesTest {
	String parameters;

	@Test
	void testAssertStmt() {
		parameters = "int i = 1;\n" + "assert(i>=0) : \"impossible\";\n";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}

	@Test
	void testBreakStmt() {
		parameters = "int i = 5;\n" + "switch(i) {\n" + "case 1: break;\n" + "case 3: break;\n" + "case 5: break;\n"
				+ "default: break;\n" + "}";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}

	@Test
	void testContinueStmt() {
		parameters = "int i = 0;\n" + "for (int j = 0, k = 0; j < 10; j++) {\n" + "if (j < 8) {\n" + "continue;\n"
				+ "}\n" + "}\n";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}

	@Test
	void testDoStmt() {
		parameters = "do {\n" + "i++;\n" + "}\n" + "while (i < 10);";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}

	@Test
	void testForStmt() {
		parameters = "int i = 0;\n" + "for (int j = 0, k = 0; j < 10; j++) { i++; }\n";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}

}
