package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kit.tva.lost.views.CodeView;

class CodeViewTests {
	private static StyledText codeField;

	private CodeView instance;
	
	@BeforeAll
	public static void setUp() {
		var display = new Display();
		var shell = new Shell(display);
		codeField = new StyledText(shell, 0);
	}
	
	@BeforeEach
	public void init() {
		instance = new CodeView(codeField);
	}

	@Test
	public void setCaretToInBetweenPosition() {
		instance.updateCode("abcdefg");
		instance.setCaretOffset(3);
		
		assertEquals(3, codeField.getCaretOffset());
	}
	
	@Test
	public void setCaretToInvalidPosition() {
		instance.setCaretOffset(-1);
		
		assertEquals(0, codeField.getCaretOffset());
	}
}
