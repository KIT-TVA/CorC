package de.kit.tva.lost.controllers;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.interfaces.TestListener;
import de.kit.tva.lost.interfaces.Testee;
import de.kit.tva.lost.interfaces.ViewType;
import de.kit.tva.lost.models.CodeModel;
import de.kit.tva.lost.models.DiagramResourceModelException;
import de.kit.tva.lost.models.LostTester;
import de.kit.tva.lost.views.LostUiView;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

public class TestController implements Controller {
    private LostUiView uiView;
    private LostTester lostTester;
    private CodeModel codeModel;

    public TestController(LostUiView uiView, CodeModel codeModel, LostTester lostTester) {
	this.uiView = uiView;
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
	    }

	    @Override
	    public void testDone(Testee testee) {
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
		} catch (DiagramResourceModelException | IOException | CoreException | SettingsException e1) {
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
