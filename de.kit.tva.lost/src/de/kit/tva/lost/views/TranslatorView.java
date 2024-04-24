package de.kit.tva.lost.views;

import org.eclipse.swt.widgets.Button;

import de.kit.tva.lost.interfaces.View;

public class TranslatorView extends View {
	private Button translateButton;
	
	public TranslatorView(Button translateButton) {
		this.translateButton = translateButton;
	}
	
	public Button getTranslateButton() {
		return this.translateButton;
	}
}
