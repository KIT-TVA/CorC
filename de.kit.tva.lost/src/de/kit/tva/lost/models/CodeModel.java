package de.kit.tva.lost.models;

import de.kit.tva.lost.interfaces.AbstractModel;

public class CodeModel extends AbstractModel {
    private String code;
    private int curOffset = 0;

    public String getCode() {
	return code;
    }

    public void setCurOffset(int offset) {
	if (offset <= code.length()) {
	    this.curOffset = offset;
	}
	notifyListeners();
    }

    private void decrementCurOffset() {
	if (curOffset > 0) {
	    this.curOffset--;
	}
	notifyListeners();
    }

    private void incrementCurOffset() {
	if (curOffset < code.length()) {
	    this.curOffset++;
	}
	notifyListeners();
    }

    public int getCurOffset() {
	return curOffset;
    }

    public void setCode(String newCode) {
	code = newCode;
	notifyListeners();
    }

    public void addChar(char character) {
	if (character == '\r') {
	    handleEnter();
	} else if (character == '\b') {
	    handleBackspace();
	} else if (Character.isIdentifierIgnorable(character)) {
	    // Ignore formatting chars.
	} else {
	    handleNormalKey(character);
	}

    }

    private void handleEnter() {
	if (offsetInMiddleOfText()) {
	    removeCharFromMiddle('\r');
	} else {
	    removeCharFromEnd('\r');
	}
	incrementCurOffset();
    }

    private void handleBackspace() {
	if (offsetInMiddleOfText()) {
	    this.setCode(code.substring(0, curOffset - 1) + code.substring(curOffset, code.length()));
	    decrementCurOffset();
	} else if (offsetAtEnd()) {
	    if (code.length() > 0) {
		this.setCode(this.code.substring(0, code.length() - 1));
		decrementCurOffset();
	    }
	} else {
	    // offset is at the start, no character to remove.
	}
    }

    private void handleNormalKey(char character) {
	if (curOffset >= this.code.length()) {
	    this.setCode(code + character);
	} else {
	    this.setCode(code.substring(0, curOffset) + character + code.substring(curOffset, code.length()));
	}
	incrementCurOffset();
    }

    private boolean offsetInMiddleOfText() {
	return curOffset > 0 && curOffset < this.code.length();
    }

    private boolean offsetAtEnd() {
	return curOffset > 0 && curOffset >= code.length();
    }

    private void removeCharFromMiddle(char character) {
	this.setCode(this.code.substring(0, curOffset) + '\n' + createNewLine(curOffset)
		+ this.code.substring(curOffset, this.code.length()));
	this.setCurOffset(this.code.length());
    }

    private void removeCharFromEnd(char character) {
	this.setCode(code + '\n' + createNewLine(curOffset));
	this.setCurOffset(this.code.length());
    }

    private String createNewLine(int offset) {
	long prevTabCount = getTabAmountOfPrevLine(offset);
	var newLine = tabbedLine(prevTabCount);
	return newLine;
    }

    private long getTabAmountOfPrevLine(int offset) {
	String prevLine = code.substring(0, offset);
	int lastIndex = prevLine.lastIndexOf('\n') == -1 ? 0 : prevLine.lastIndexOf('\n');
	prevLine = prevLine.substring(lastIndex, prevLine.length());
	return prevLine.chars().filter(c -> c == '\t').count();
    }

    private String tabbedLine(long num) {
	var newLine = new StringBuilder();
	for (int i = 0; i < num; i++) {
	    newLine.append('\t');
	}
	return newLine.toString();
    }
}
