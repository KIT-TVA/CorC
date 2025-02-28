package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.List;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.SWT;
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
 * Sample Notification Popup Class
 */
public class SampleNotificationPopup extends AbstractNotificationPopup {

	/**
	 * @param display
	 */
	private List<URI> uris;

	public SampleNotificationPopup(Display display, List<URI> uris) {
		super(display);
		this.uris = uris;
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
		featureLabel.setText("Your Condition-changes affected:");

		Group buttonGroup = new Group(container, SWT.PUSH);
		GridLayout buttonGroupLayout = new GridLayout();
		buttonGroupLayout.numColumns = 2;
		buttonGroup.setLayout(buttonGroupLayout);

		for (int i = 0; i < uris.size(); i++) {
			String output = "Method " + uris.get(i).trimFileExtension().lastSegment() + " in Feature "
					+ uris.get(i).segment(uris.get(i).segmentCount() - 3);
			Button newButton = new Button(buttonGroup, SWT.DEFAULT);
			newButton.setText(output);
			final int index = i;
			newButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					System.out.println(index);
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
		return "Notification Popup";
	}
}
