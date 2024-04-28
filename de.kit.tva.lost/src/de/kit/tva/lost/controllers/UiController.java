package de.kit.tva.lost.controllers;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUiView;

public class UiController implements Controller {
    protected LostUiView view;
    private CodeView codeView;

    public UiController(LostUiView view, CodeView codeView) {
	this.view = view;
	this.codeView = codeView;
	createModelObservers();
	addViewListeners();
	initModel();
    }

    @Override
    public void createModelObservers() {
    }

    @Override
    public void addViewListeners() {
	codeView.addListener(view);
    }

    @Override
    public void initModel() {
	// TODO Auto-generated method stub

    }
}
