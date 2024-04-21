package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.eclipse.swt.SWT;

import de.kit.tva.lost.models.CodeModel;

public class CodeModelTest {
	private CodeModel instance = new CodeModel();
	
	@BeforeEach
	public void reset() {
		instance.setCode("");
	}
	
	@Test
	public void addEnter() {
		instance.addChar('\r');
		
		assertEquals("\n", instance.getCode());
	}
	
	@Test
	public void addTabbedEnter() {
		instance.setCode("\t");
		
		instance.addChar('\r');
		
		assertEquals("\t\n\t", instance.getCode());
	}
	
	@Test
	public void addTabbedEnterTwice() {
		instance.setCode("F\n"
						+"\tC\n"
						+"\t\treturn;");
		
		instance.addChar('\r');
		
		assertEquals("F\n"
						+"\tC\n"
						+"\t\treturn;\n"
							+"\t\t", instance.getCode());
	}
	
	@Test
	public void removeChar() {
		instance.setCode("a");
		
		instance.addChar('\b');
		
		assertEquals("", instance.getCode());
	}
	
	@Test
	public void addIdentifierChar() {
		instance.addChar('b');
		
		assertEquals("b", instance.getCode());
	}
	
	@Test
	public void addBracket() {
		instance.addChar('(');
		
		assertEquals("(", instance.getCode());
	}
	
	@Test
	public void dontAddSpecialChar() {
		instance.addChar((char)SWT.SHIFT);
		
		assertEquals("", instance.getCode());
	}
	
	@Test
	public void normalBackspaceOffset() {
		instance.setCode("abc");
	
		instance.addChar('\b');
		
		assertEquals(2, instance.getCurOffset());
	}
	
	@Test
	public void emptyBackSpaceOffset() {
		instance.addChar('\b');
		
		assertEquals(0, instance.getCurOffset());
	}
	
	@Test
	public void oneLetterBackSpaceOffset() {
		instance.setCode("a");
		
		instance.addChar('\b');
		
		assertEquals(0, instance.getCurOffset());
	}
	
}
