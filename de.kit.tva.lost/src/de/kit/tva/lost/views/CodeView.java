package de.kit.tva.lost.views;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

import de.kit.tva.lost.interfaces.AbstractView;
import de.kit.tva.lost.models.TranslatorErrorListenerModel.CodeColor;

public class CodeView extends AbstractView {
    private StyledText codeField;

    public CodeView(StyledText codeField) {
	this.codeField = codeField;
    }

    public void updateCode(String code) {
	this.codeField.setText(code);
	notifyListeners();
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

    public void updateCodeColor(CodeColor codeColor) {
	if (codeColor.relStartIndex == -1) {
	    this.codeField.setForeground(codeColor.colorToSet);
	} else {
	    setPartialCodeColor(codeColor);
	}
    }

    public void disableCodeIfNecessary(boolean basicViewEnabled) {
	this.codeField.setEnabled(!basicViewEnabled);
    }

    private void setPartialCodeColor(CodeColor codeColor) {
	StyleRange sr = new StyleRange();
	if (!calcAbsIndicies(codeColor))
	    return;
	sr.start = codeColor.relStartIndex;
	sr.length = codeColor.relEndIndex - codeColor.relStartIndex;
	sr.foreground = codeColor.colorToSet;
	if (sr.start + sr.length > codeField.getText().length()) {
	    sr.start--;
	}
	this.codeField.setStyleRange(sr);
    }

    private boolean calcAbsIndicies(CodeColor codeColor) {
	var lines = this.codeField.getText().split("\\n");
	if (codeColor.line >= lines.length)
	    return false;
	for (int i = 0; i < codeColor.line; ++i) {
	    codeColor.relStartIndex += lines[i].length();
	    codeColor.relEndIndex += lines[i].length();
	}
	return true;
    }

}
