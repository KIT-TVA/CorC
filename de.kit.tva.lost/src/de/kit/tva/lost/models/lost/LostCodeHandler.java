package de.kit.tva.lost.models.lost;

import de.kit.tva.lost.interfaces.CodeColor;
import de.kit.tva.lost.interfaces.OperatorInfo;

public class LostCodeHandler {

    public String[] getCodeUntilStatement(String code, String statementName, String skipSymbol) {
	String out[] = { "", "" };
	var index = getNextIndex(code, statementName, skipSymbol);
	out[0] = code.substring(0, index + statementName.length());
	out[1] = code.substring(index + statementName.length(), code.length());
	return out;
    }

    public String createInfoForStatement(CodeColor codeColor, String viewCode, String statement, String skipSymbol) {
	int absStart = getNextIndex(viewCode, statement, skipSymbol);
	int absEnd = absStart + statement.length();
	codeColor.info = new OperatorInfo(-1, absStart, absEnd);
	return viewCode;
    }

    private int getNextIndex(String code, String statementName, String skipSymbol) {
	int index = code.indexOf(statementName);
	var line = code.substring(index, code.length());
	line = line.substring(0, line.indexOf('\n'));
	while (line.contains(skipSymbol)) {
	    line = blackenLine(line);
	    code = code.substring(0, index) + line + code.substring(index + line.length(), code.length());
	    index = code.indexOf(statementName);
	    if (index == -1)
		break;
	    line = code.substring(index, code.length());
	    line = line.substring(0, line.indexOf('\n'));
	}
	return index;
    }

    private String blackenLine(String line) {
	var newLine = "";
	for (int i = 0; i < line.length(); ++i) {
	    newLine += "X";
	}
	return newLine;
    }
}
