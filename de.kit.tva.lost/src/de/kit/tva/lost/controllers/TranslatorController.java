package de.kit.tva.lost.controllers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.models.TranslatorModel;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.TranslatorView;

public class TranslatorController implements Controller {
    TranslatorModel translatorModel;
    TranslatorView translatorView;
    CodeView codeView;

    public TranslatorController(CodeView codeView, TranslatorView translatorView, TranslatorModel translatorModel) {
	this.translatorModel = translatorModel;
	this.translatorView = translatorView;
	this.codeView = codeView;
	createModelObservers();
	addViewListeners();
	initModel();
    }

    @Override
    public void createModelObservers() {
	// Empty since the button doesn't have to know anything about the model.
    }

    @Override
    public void addViewListeners() {
	translatorView.getTranslateButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		if (translatorModel.translate(codeView.getCodeFieldText())) {
		    // success
		    var i = 2;
		    // TODO: Either update existing Diagram or create new one.
		} else {
		    // failure
		    // TODO: Error highlighting in code
		}
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
		// Not relevant
	    }

	});
    }

    @Override
    public void initModel() {
	translatorModel = new TranslatorModel();
    }

}
