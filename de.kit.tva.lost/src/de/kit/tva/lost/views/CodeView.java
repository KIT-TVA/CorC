package de.kit.tva.lost.views;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;

import de.kit.tva.lost.interfaces.AbstractView;
import de.kit.tva.lost.interfaces.CodeColor;
import de.kit.tva.lost.interfaces.LostStyle;
import styles.DefaultStyle;

public class CodeView extends AbstractView {
    private SyntaxHighlighter syntaxHighlighter;
    private String previousCode;
    private StyledText codeField;

    public CodeView(StyledText codeField) {
	this.codeField = codeField;
	this.syntaxHighlighter = new SyntaxHighlighter(this.getCodeField(), new DefaultStyle());
	this.previousCode = "";
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

    public void changeStyle(LostStyle style) {
	this.syntaxHighlighter.changeStyle(style);
    }

    public void updateCodeColor(CodeColor codeColor) {
	if (codeColor.info.relStartIndex == -1) {
	    this.codeField.setForeground(codeColor.colorToSet);
	} else {
	    setPartialCodeColor(codeColor, false);
	}
    }

    public void disableCodeIfNecessary(boolean basicViewEnabled) {
	this.codeField.setEnabled(!basicViewEnabled);
    }

    private void setPartialCodeColor(CodeColor codeColor, boolean isAbsolute) {
	StyleRange sr = new StyleRange();
	if (!isAbsolute) {
	    if (!calcAbsIndicies(codeColor))
		return;
	}
	sr.start = codeColor.info.relStartIndex;
	sr.length = codeColor.info.relEndIndex - codeColor.info.relStartIndex;
	sr.foreground = codeColor.colorToSet;
	if (sr.start + sr.length > codeField.getText().length()) {
	    sr.start--;
	}
	if (sr.start >= 0 && sr.length > 0) {
	    this.codeField.setStyleRange(sr);
	}
    }

    private boolean calcAbsIndicies(CodeColor codeColor) {
	var lines = this.codeField.getText().split("\\n");
	if (codeColor.info.line >= lines.length)
	    return false;
	for (int i = 0; i < codeColor.info.line; ++i) {
	    codeColor.info.relStartIndex += lines[i].length();
	    codeColor.info.relEndIndex += lines[i].length();
	}
	return true;
    }

    public void highlight() {
	if (!syntaxHighlighter.applyHighlights())
	    return;
	for (var highlight : syntaxHighlighter.getHighlights()) {
	    setPartialCodeColor(highlight, true);
	}
    }

}
