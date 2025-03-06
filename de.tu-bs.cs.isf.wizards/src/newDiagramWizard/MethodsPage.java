package newDiagramWizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class MethodsPage extends WizardPage {
	private Composite container;
	private List<String> methodNames;
	private List<String> selectedMethods;

	public MethodsPage(List<String> methodNames) {
		super("Select a Method");
		setTitle("Select a Method:");
		this.methodNames = methodNames;
		this.selectedMethods = new ArrayList<String>();
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new RowLayout(SWT.VERTICAL));

		for (int i = 0; i < methodNames.size(); i++) {
			Button b = new Button(container, SWT.CHECK);
			b.setText(methodNames.get(i));
			b.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (selectedMethods.contains(b.getText())) {
						selectedMethods.remove(b.getText());
						if (selectedMethods.isEmpty()) {
							setPageComplete(false);
						}
					} else {
						selectedMethods.add(b.getText());
						setPageComplete(true);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					System.out.println("do nothing");
				}
			});
		}
		setControl(container);
		setPageComplete(false);

	}

	public List<String> getSelectedMethods() {
		return this.selectedMethods;
	}

}