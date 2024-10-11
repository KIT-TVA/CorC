package de.kit.tva.lost.controllers;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import de.kit.tva.lost.interfaces.CodeListener;
import de.kit.tva.lost.interfaces.Controller;
import de.kit.tva.lost.models.codeviews.CodeModel;
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
	this.model.addListener(new CodeListener() {
	    @Override
	    public void update() {
		view.setCaretOffset(model.getCurOffset());
	    }

	    @Override
	    public void updateCode() {
		view.updateCode(model.getCode());
		view.highlight();
	    }

	    @Override
	    public void updateView() {
		view.disableCodeIfNecessary(model.basicViewEnabled());
		if (model.basicViewEnabled()) {
		    view.updateCode(model.getViewCode());
		} else {
		    view.updateCode(model.getCode());
		}
		if (!model.basicViewEnabled()) {
		    view.highlight();
		}
	    }
	});
    }

    @Override
    public void addViewListeners() {
	handleKeyPress();
	handleMouseClick();
	handleCaret();
    }

    @Override
    public void initModel() {
	model.setCode("");
    }

    private void handleKeyPress() {
	view.getCodeField().addKeyListener(new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		model.addChar(e.character);
		model.setCurOffset(((StyledText) e.widget).getCaretOffset());
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }
	});

    }

    private void handleMouseClick() {
	view.getCodeField().addMouseListener(new MouseListener() {
	    @Override
	    public void mouseDoubleClick(MouseEvent e) {
	    }

	    @Override
	    public void mouseDown(MouseEvent e) {
		model.setCurOffset(((StyledText) e.widget).getCaretOffset());
	    }

	    @Override
	    public void mouseUp(MouseEvent e) {
	    }
	});

    }

    private void handleCaret() {
	view.getCodeField().addCaretListener(e -> view.setCaretOffset(e.caretOffset));
    }
}
