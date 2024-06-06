package de.kit.tva.lost.views;

import java.util.List;

import org.eclipse.swt.custom.StyledText;

import de.kit.tva.lost.interfaces.Testee;

public class LostTesterView {
    private StyledText codeField;

    public LostTesterView(StyledText codeField) {
	this.codeField = codeField;
    }

    private String[] getCodeUntilLine(String code, int line) {
	var splitted = code.split("\\n");
	String out[] = { "", "" };
	for (int i = 0; i < line; ++i) {
	    out[0] += splitted[i] + "\n";
	}
	for (int i = 0; i < splitted.length; ++i) {
	    out[1] += splitted[i] + "\n";
	}
	return out;
    }

    public void showResults(List<Testee> testees) {
	for (var testee : testees) {
	    var codeSplit = getCodeUntilLine(codeField.getText(), testee.get().getStart().getLine());
	    codeField.setText(codeSplit[0] + "#" + codeSplit[1]);
	}
    }
}
