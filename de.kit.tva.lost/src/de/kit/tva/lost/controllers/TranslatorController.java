package de.kit.tva.lost.controllers;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.models.DiagramResourceModelException;
import de.kit.tva.lost.models.TranslatorErrorListenerModel;
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
	TranslatorErrorListenerModel.getInstance().addListener(() -> {
	    codeView.updateCodeColor(TranslatorErrorListenerModel.getInstance().getCodeColor());
	});
    }

    @Override
    public void addViewListeners() {
	translatorView.getTranslateButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		try {
		    if (!translatorModel.translate(codeView.getCodeFieldText())) {
			return;
		    }
		} catch (DiagramResourceModelException | IOException | CoreException e1) {
		    e1.printStackTrace();
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
