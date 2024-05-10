package de.kit.tva.lost.controllers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.models.CodeModel;
import de.kit.tva.lost.models.UiModel;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUiView;

public class UiController implements Controller {
    protected LostUiView view;
    private CodeView codeView;
    private UiModel uiModel;
    private CodeModel codeModel;

    public UiController(LostUiView view, CodeView codeView, UiModel uiModel, CodeModel codeModel) {
	this.view = view;
	this.codeView = codeView;
	this.uiModel = uiModel;
	this.codeModel = codeModel;
	createModelObservers();
	addViewListeners();
	initModel();
    }

    @Override
    public void createModelObservers() {
	uiModel.addListener(view);
    }

    @Override
    public void addViewListeners() {
	codeView.addListener(view);
	view.getHelpButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		uiModel.showHelp();
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {

	    }

	});

	view.getBasicViewButton().addSelectionListener(new SelectionListener() {

	    @Override
	    public void widgetSelected(SelectionEvent e) {
		codeModel.switchView();
		codeView.updateCode(codeModel.getViewCode());
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
	    }

	});

	/*
	 * view.getExtendedViewButton().addSelectionListener(new SelectionListener() {
	 * 
	 * @Override public void widgetSelected(SelectionEvent e) {
	 * codeModel.switchView(); codeView.updateCode(codeModel.getViewCode()); }
	 * 
	 * @Override public void widgetDefaultSelected(SelectionEvent e) { }
	 * 
	 * });
	 */
    }

    @Override
    public void initModel() {

    }
}
