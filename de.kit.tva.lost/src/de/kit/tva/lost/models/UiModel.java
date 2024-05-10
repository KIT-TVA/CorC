package de.kit.tva.lost.models;

import org.eclipse.swt.program.Program;

import de.kit.tva.lost.interfaces.AbstractModel;

public class UiModel extends AbstractModel {
    private static final String HelpUrl = "https://www.overleaf.com/project/656b8666476444a5fb0b6666";

    public void showHelp() {
	Program.launch(HelpUrl);
	notifyListeners();
    }
}
