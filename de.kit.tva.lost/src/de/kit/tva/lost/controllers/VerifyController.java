package de.kit.tva.lost.controllers;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.interfaces.Result;
import de.kit.tva.lost.interfaces.VerifyListener;
import de.kit.tva.lost.interfaces.ViewType;
import de.kit.tva.lost.models.codeviews.CodeModel;
import de.kit.tva.lost.models.lost.LostVerifier;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUiView;

public class VerifyController implements Controller {
	private LostUiView uiView;
	private CodeView codeView;
	private LostVerifier lostVerifier;
	private CodeModel codeModel;

	public VerifyController(LostUiView uiView, CodeView codeView, CodeModel codeModel, LostVerifier lostVerifier) {
		this.uiView = uiView;
		this.codeView = codeView;
		this.lostVerifier = lostVerifier;
		this.codeModel = codeModel;
		createModelObservers();
		addViewListeners();
		initModel();
	}

	@Override
	public void createModelObservers() {
		lostVerifier.addListener(new VerifyListener() {
			@Override
			public void verificationDone() {
				uiView.getExtendedViewButton().setSelection(false);
				uiView.getBasicViewButton().setSelection(true);
				codeModel.switchView(ViewType.BASIC);
				var viewCode = lostVerifier.getResults(codeModel.getViewCode());
				codeModel.setViewCode(viewCode);
				codeModel.viewChanged();
				codeView.updateCodeColors(lostVerifier.getHighlights(), true);
			}

			@Override
			public void verificationDone(Result testee) {
				// lostVerifierView.showResult(time, lostVerifier)
			}

			@Override
			public void update() {

			}
		});
	}

	@Override
	public void addViewListeners() {
		handleVerifyButton();
	}

	@Override
	public void initModel() {
	}

	private void handleVerifyButton() {
		uiView.getVerifyButton().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					lostVerifier.verify(codeModel.getCode());
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
