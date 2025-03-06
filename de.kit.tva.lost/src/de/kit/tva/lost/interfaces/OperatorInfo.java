package de.kit.tva.lost.interfaces;

public class OperatorInfo {
    public int line, relStartIndex, relEndIndex;

    public OperatorInfo(int line, int startIndex, int endIndex) {
	this.line = line;
	this.relStartIndex = startIndex;
	this.relEndIndex = endIndex;
    }
}
