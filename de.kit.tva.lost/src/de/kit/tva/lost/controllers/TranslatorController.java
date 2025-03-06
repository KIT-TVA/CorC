package de.kit.tva.lost.controllers;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.PlatformUI;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.interfaces.ViewType;
import de.kit.tva.lost.models.codeviews.CodeModel;
import de.kit.tva.lost.models.diagrams.DiagramResourceModelException;
import de.kit.tva.lost.models.diagrams.DiagramTranslator;
import de.kit.tva.lost.models.diagrams.DiagramTranslatorException;
import de.kit.tva.lost.models.lost.TranslatorErrorListenerModel;
import de.kit.tva.lost.models.lost.TranslatorModel;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUiView;

public class TranslatorController implements Controller {
    TranslatorModel translatorModel;
    DiagramTranslator diagramTranslatorModel;
    CodeModel codeModel;
    LostUiView uiView;
    CodeView codeView;

    public TranslatorController(LostUiView uiView, CodeView codeView, CodeModel codeModel,
	    TranslatorModel translatorModel, DiagramTranslator diagramTranslatorModel) {
	this.translatorModel = translatorModel;
	this.diagramTranslatorModel = diagramTranslatorModel;
	this.codeView = codeView;
	this.uiView = uiView;
	this.codeModel = codeModel;
	createModelObservers();
	addViewListeners();
	initModel();
    }

    @Override
    public void createModelObservers() {
	TranslatorErrorListenerModel.getInstance().addListener(() -> {
	    codeView.updateCodeColor(TranslatorErrorListenerModel.getInstance().getCodeColor(), false);
	});
    }

    @Override
    public void addViewListeners() {
	handleTranslateButton();
	handleLoadButton();
    }

    @Override
    public void initModel() {
    }

    private void handleTranslateButton() {
	uiView.getTranslateButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		codeModel.switchView(ViewType.EXTENDED);
		uiView.getBasicViewButton().setSelection(false);
		uiView.getExtendedViewButton().setSelection(true);
		try {
		    if (!translatorModel.translate(codeView.getCodeFieldText())) {
			return;
		    }
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
		// Not relevant
	    }

	});

    }

    private void handleLoadButton() {
	uiView.getLoadButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		codeModel.switchView(ViewType.EXTENDED);
		uiView.getBasicViewButton().setSelection(false);
		uiView.getExtendedViewButton().setSelection(true);
		var currentDiagName = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			.getActiveEditor().getEditorInput().getAdapter(IResource.class).getName().split("\\.")[0];
		try {
		    if (!diagramTranslatorModel.load(currentDiagName)) {
			return;
		    }
		    codeModel.setCode(diagramTranslatorModel.getTranslatedCode());
		} catch (DiagramTranslatorException | DiagramResourceModelException | IOException e1) {
		    e1.printStackTrace();
		}
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
		// Not relevant
	    }

	});

    }

}
