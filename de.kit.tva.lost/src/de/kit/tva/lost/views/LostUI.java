package de.kit.tva.lost.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.graphics.RGB;

public class LostUI extends Composite {
	private LocalResourceManager localResourceManager;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private StyledText codeField;
	private Button btnTranslate;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public LostUI(Composite parent, int style) {
		super(parent, SWT.NONE);
		createResourceManager();
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		
		Composite grpHeader = new Composite(this, SWT.NONE);
		GridData gdGrpHeader = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gdGrpHeader.widthHint = 462;
		grpHeader.setLayoutData(gdGrpHeader);
		grpHeader.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		formToolkit.adapt(grpHeader);
		formToolkit.paintBordersFor(grpHeader);
		GridLayout glGrpHeader = new GridLayout(3, false);
		glGrpHeader.marginTop = 5;
		glGrpHeader.marginHeight = 0;
		grpHeader.setLayout(glGrpHeader);
		
		Composite grpSyntax = new Composite(grpHeader, SWT.NONE);
		grpSyntax.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		formToolkit.adapt(grpSyntax);
		formToolkit.paintBordersFor(grpSyntax);
		
		Button btnExhaustive = new Button(grpSyntax, SWT.RADIO);
		btnExhaustive.setBounds(129, 0, 77, 16);
		formToolkit.adapt(btnExhaustive, true, true);
		btnExhaustive.setText("Exhaustive");
		
		Button btnBasic = new Button(grpSyntax, SWT.RADIO);
		btnBasic.setBounds(0, 0, 48, 16);
		formToolkit.adapt(btnBasic, true, true);
		btnBasic.setText("Basic");
		
		Button btnExtended = new Button(grpSyntax, SWT.RADIO);
		btnExtended.setSelection(true);
		btnExtended.setBounds(53, 0, 70, 16);
		formToolkit.adapt(btnExtended, true, true);
		btnExtended.setText("Extended");

		btnTranslate = new Button(grpHeader, SWT.TOGGLE);
		btnTranslate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnTranslate.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 8, SWT.NORMAL)));
		formToolkit.adapt(btnTranslate, true, true);
		btnTranslate.setText("Translate");
		
		Button btnMonitor = new Button(grpHeader, SWT.TOGGLE);
		btnMonitor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnMonitor.setFont(localResourceManager.create(FontDescriptor.createFrom("Segoe UI", 8, SWT.NORMAL)));
		formToolkit.adapt(btnMonitor, true, true);
		btnMonitor.setText("Monitor");
		
		codeField = new StyledText(this, SWT.BORDER);
		codeField.setFont(localResourceManager.create(FontDescriptor.createFrom("Cascadia Code SemiBold", 9, SWT.NORMAL)));
		codeField.setBottomMargin(5);
		codeField.setLeftMargin(5);
		codeField.setRightMargin(5);
		codeField.setTabStops(new int[] {16});
		GridData gdCodeField = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdCodeField.heightHint = 216;
		codeField.setLayoutData(gdCodeField);
		formToolkit.adapt(codeField);
		formToolkit.paintBordersFor(codeField);
	}
	
	public StyledText getCodeField() {
		return this.codeField;
	}
	
	public Button getTranslateButton() {
		return this.btnTranslate;
	}
	
	private void createResourceManager() {
		localResourceManager = new LocalResourceManager(JFaceResources.getResources(),this);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	
	
}
