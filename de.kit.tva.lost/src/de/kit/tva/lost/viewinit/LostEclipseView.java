package de.kit.tva.lost.viewinit;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

import de.kit.tva.lost.controllers.CodeController;
import de.kit.tva.lost.models.CodeModel;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUI;

import org.eclipse.ui.*;

import javax.inject.Inject;

/*  
 * Creates the view. Also provides methods to access relevant UI controls.
 * */
public class LostEclipseView extends ViewPart {
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "lost.views.LostView";

	@Inject IWorkbench workbench;
	
	private static final int STYLE = 0;
	private StyledText codeField;
	
	@Override
	public void createPartControl(Composite parent) {
		CodeModel codeModel = new CodeModel();
		LostUI ui = new LostUI(parent, STYLE);
		codeField = ui.getCodeField();
		CodeView codeView = new CodeView(codeField);
		new CodeController(codeView, codeModel);
	}

	@Override
	public void setFocus() {
		codeField.setFocus();
	}
}
