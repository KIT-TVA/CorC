package de.kit.tva.lost.models.codeviews;

import de.kit.tva.lost.interfaces.CodeModelNotifier;
import de.kit.tva.lost.interfaces.CodeView;
import de.kit.tva.lost.interfaces.ViewType;

public class CodeModel extends CodeModelNotifier {
	private String viewCode;
	private String code;
	private boolean outOfContext;
	private int curOffset = 0;
	private boolean basicView = false;

	public CodeModel() {
		viewCode = "";
		code = "";
	}

	public String getCode() {
		return code;
	}

	public void setCurOffset(int offset) {
		if (offset <= code.length()) {
			this.curOffset = offset;
		}
		notifyListeners();
	}

	public boolean basicViewEnabled() {
		return this.basicView;
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
		codeChanged();
	}

	public String getViewCode() {
		return this.viewCode;
	}

	public void setViewCode(String newViewCode) {
		if (getNonTaggedViewCode(this.viewCode).equals(newViewCode))
			return;
		this.viewCode = newViewCode;
		if (isTagged(newViewCode) && isTagged(this.viewCode)) {
			this.viewCode = mergeTags(this.viewCode);
		}
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

	private boolean isTagged(String viewCode) {
		return viewCode.contains(" <-");
	}

	private String getNonTaggedViewCode(String viewCode) {
		while (viewCode != null && isTagged(viewCode)) {
			int nextMatch = viewCode.indexOf(" <-");
			var helper = viewCode.substring(nextMatch, viewCode.length());
			int nextNewLine = helper.indexOf('\n') + nextMatch;
			viewCode = viewCode.substring(0, nextMatch) + viewCode.substring(nextNewLine, viewCode.length());
		}
		return viewCode;
	}

	private String mergeTags(String viewCode) {
		// TODO
		return viewCode;
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
		for (int i = 0; i < getTabAmountOfPrevLine(curOffset); i++)
			incrementCurOffset();
	}

	private void removeCharFromEnd(char character) {
		var newLine = createNewLine(curOffset);
		if (outOfContext)
			this.setCode(code.trim() + '\n');
		if (this.code.trim().isEmpty()) {
			return;
		}
		this.setCode(code + '\n' + newLine);
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
		if (outOfContext(prevLine))
			return 1;
		return prevLine.chars().filter(c -> c == '\t').count();
	}

	private boolean outOfContext(String prevLine) {
		if (prevLine.trim().isEmpty()) {
			this.setCode(this.getCode().trim());
			this.outOfContext = true;
			return true;
		}
		this.outOfContext = false;
		return false;
	}

	private String tabbedLine(long num) {
		var newLine = new StringBuilder();
		for (int i = 0; i < num; i++) {
			newLine.append('\t');
		}
		return newLine.toString();
	}

	public void switchView(ViewType viewType) {
		if (viewType == ViewType.BASIC) {
			this.basicView = true;
		} else {
			this.basicView = false;
		}
		applyView();
	}

	private void applyView() {
		CodeView codeView;
		if (basicView) {
			codeView = new BasicCodeView();
			this.setViewCode(codeView.transform(this.code));
		} else {
			codeView = new ExtendedCodeView();
			this.setCode(codeView.transform(this.code));
		}
		viewChanged();
	}
}
