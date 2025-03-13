package de.kit.tva.lost.boot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import de.kit.tva.lost.views.LostUiView;
import jakarta.inject.Inject;

public class Bootstrap extends ViewPart {
	public Bootstrap() {
	}

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "lost.views.LostView";

	@Inject
	IWorkbench workbench;

	private static final int STYLE = 0;
	private LostUiView ui;
	private StyledText codeField;

	@Override
	public void createPartControl(Composite parent) {
		ui = new LostUiView(parent, STYLE);
		ui.getExtendedViewButton().setAlignment(SWT.CENTER);
		ui.getBasicViewButton().setAlignment(SWT.CENTER);
		codeField = ui.getCodeField();
	}

	@Override
	public void setFocus() {
		codeField.setFocus();
	}
}
