package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.List;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * Class for showing Notifications for critical proofs
 * 
 * @author David
 */
public class NotificationPopup extends AbstractNotificationPopup {

	/**
	 * @param display
	 */
	private List<URI> uris;
	private String type;

	public NotificationPopup(Display display, List<URI> uris, String type) {
		super(display);
		this.uris = uris;
		this.type = type;
	}

	@Override
	protected void createContentArea(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.verticalSpacing = 20;
		container.setLayout(gridLayout);

		// featureLabel
		Label featureLabel = new Label(container, SWT.PUSH);
		featureLabel.setText("Your Condition-changes affected\nthe following methods");
		Group buttonGroup = new Group(container, SWT.PUSH);
		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 1;
		buttonGroup.setLayout(buttonGroupLayout);
		buttonGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		for (int i = 0; i < uris.size(); i++) {
			String output = "Feature: " + uris.get(i).segment(uris.get(i).segmentCount() - 3) + ", Method: "
					+ uris.get(i).trimFileExtension().lastSegment();
			Button newButton = new Button(buttonGroup, SWT.DEFAULT);
			newButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			newButton.setText(output);
			final int index = i;
			newButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage activePage = window.getActivePage();
					URIEditorInput t = new URIEditorInput(uris.get(index));
					IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
							.getDefaultEditor(uris.get(index).toString());
					try {
						activePage.openEditor(t, desc.getId());
					} catch (PartInitException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	protected String getPopupShellTitle() {
		if (type.equals("original")) {
			return "Critical Original-Calls";
		} else if (type.equals("method")) {
			return "Critical Method-Calls";
		} else if (type.equals("compositionTechnique")) {
			return "Critical Methods due Composition";
		}
		return "";
	}
}
