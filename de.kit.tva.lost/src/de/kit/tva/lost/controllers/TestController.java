package de.kit.tva.lost.controllers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.interfaces.Result;
import de.kit.tva.lost.interfaces.TestListener;
import de.kit.tva.lost.interfaces.ViewType;
import de.kit.tva.lost.models.codeviews.CodeModel;
import de.kit.tva.lost.models.lost.LostTester;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUiView;

public class TestController implements Controller {
    private LostUiView uiView;
    private CodeView codeView;
    private LostTester lostTester;
    private CodeModel codeModel;

    public TestController(LostUiView uiView, CodeView codeView, CodeModel codeModel, LostTester lostTester) {
	this.uiView = uiView;
	this.codeView = codeView;
	this.lostTester = lostTester;
	this.codeModel = codeModel;
	createModelObservers();
	addViewListeners();
	initModel();
    }

    @Override
    public void createModelObservers() {
	lostTester.addListener(new TestListener() {
	    @Override
	    public void testsDone() {
		uiView.getExtendedViewButton().setSelection(false);
		uiView.getBasicViewButton().setSelection(true);
		codeModel.switchView(ViewType.BASIC);
		var viewCode = lostTester.getResults(codeModel.getViewCode());
		codeModel.setViewCode(viewCode);
		codeModel.viewChanged();
		codeView.updateCodeColors(lostTester.getHighlights(), true);
	    }

	    @Override
	    public void testDone(Result testee) {
		// lostTesterView.showResult(time, lostTester)
	    }

	    @Override
	    public void update() {

	    }
	});
    }

    @Override
    public void addViewListeners() {
	handleTestButton();
    }

    @Override
    public void initModel() {
    }

    private void handleTestButton() {
	uiView.getTestButton().addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		try {
		    lostTester.test(codeModel.getCode());
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent e) {
		// not relevant
	    }
	});
    }

}
