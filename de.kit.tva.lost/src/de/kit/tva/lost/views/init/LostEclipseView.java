package de.kit.tva.lost.views.init;

import javax.inject.Inject;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import de.kit.tva.lost.controllers.CodeController;
import de.kit.tva.lost.controllers.TranslatorController;
import de.kit.tva.lost.models.CodeModel;
import de.kit.tva.lost.models.TranslatorModel;
import de.kit.tva.lost.views.CodeView;
import de.kit.tva.lost.views.LostUI;
import de.kit.tva.lost.views.TranslatorView;

/*  
 * Creates the view. Also provides methods to access relevant UI controls.
 * */
public class LostEclipseView extends ViewPart {
    public LostEclipseView() {
    }

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "lost.views.LostView";

    @Inject
    IWorkbench workbench;

    private static final int STYLE = 0;
    private StyledText codeField;

    @Override
    public void createPartControl(Composite parent) {
	LostUI ui = new LostUI(parent, STYLE);
	codeField = ui.getCodeField();
	initMVCs(ui);
    }

    private void initMVCs(final LostUI ui) {
	// Models
	CodeModel codeModel = new CodeModel();
	TranslatorModel translatorModel = new TranslatorModel();
	// Views
	CodeView codeView = new CodeView(ui.getCodeField());
	TranslatorView translatorView = new TranslatorView(ui.getTranslateButton());
	// Controllers
	new CodeController(codeView, codeModel);
	new TranslatorController(codeView, translatorView, translatorModel);
    }

    @Override
    public void setFocus() {
	codeField.setFocus();
    }
}
