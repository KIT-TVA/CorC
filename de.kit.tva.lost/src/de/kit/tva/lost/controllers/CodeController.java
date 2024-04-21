package de.kit.tva.lost.controllers;

import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.interfaces.Listener;
import de.kit.tva.lost.models.CodeModel;
import de.kit.tva.lost.views.CodeView;

public class CodeController implements Controller {
	protected CodeModel model;
	protected CodeView view;
	
	public CodeController(CodeView view, CodeModel model) {
		this.model = model;
		this.view = view;
		createModelObservers();
		addViewListeners();
		initModel();
	}
	

	@Override
	public void createModelObservers() {
		this.model.addListener(() -> {
			view.updateCode(model.getCode());
			view.setCaretOffset(model.getCurOffset());
			});
	}

	@Override
	public void addViewListeners() {
		// TODO:  add code listener -> User modifiys code in editor
		// TODO: Caret listener doesn't work correctly when clicking with mouse in middle of text and tryign to remove/add a char.
		view.getCodeField().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				model.addChar(e.character);
				model.setCurOffset(((StyledText)e.widget).getCaretOffset());
			}
			@Override
			public void keyReleased(KeyEvent e) {  }
		});
		
		view.getCodeField().addCaretListener(e -> view.setCaretOffset(e.caretOffset));
	}

	@Override
	public void initModel() {
		model.setCode("");
	}
}
