package de.kit.tva.lost.views;

import org.eclipse.swt.custom.StyledText;

import de.kit.tva.lost.interfaces.View;

public class CodeView extends View {
	private StyledText codeField;
	
	public CodeView(StyledText codeField) {
		this.codeField = codeField;
	}

	public void updateCode(String code) {
		this.codeField.setText(code);
	}

	public StyledText getCodeField() {
		return codeField;
	}
	
	public void setCaretOffset(int offset) {
		this.codeField.setCaretOffset(offset);
	}
	
	public int getCaretOffset() {
		return this.codeField.getCaretOffset();
	}
	
	public String getCodeFieldText() {
		return this.codeField.getText();
	}
}
