package de.kit.tva.lost.models;

import org.eclipse.swt.program.Program;

import de.kit.tva.lost.interfaces.AbstractModel;

public class UiModel extends AbstractModel {
	private static final String HelpUrl = "https://www.overleaf.com/read/cbnncfypyvqj#47edca";

	public void showHelp() {
		Program.launch(HelpUrl);
		notifyListeners();
	}
}
