package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;

public class CompareMethodBodiesTest {
	
	@Test
	void testAssertStmt() {
		String parameters = 
				  "int i = 1;\n" 
				+ "assert(i>=0) : \"impossible\";\n";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}
	
	@Test
	void testForStmt() {
		String parameters = 
				  "int i = 0;\n"
				+ "for (int j = 0, k = 0; j < 10; j++) { i++; }\n";
		assertEquals(CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(parameters), true);
	}
	
	
}
