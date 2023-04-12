package newDiagramWizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

public class SelectMethodWizard extends Wizard {
	private MethodsPage page;
	private List<String> methodNames;
	private List<String> selectedMethods;
	
	public SelectMethodWizard (List<String> methodNames) {
        super();
        this.methodNames = methodNames;
        setNeedsProgressMonitor(true);
    }
	
	@Override
    public String getWindowTitle() {
        return "Create Diagram";
    }
	
	@Override
    public void addPages() {
        page = new MethodsPage(this.methodNames);
        addPage(page);
    }

	@Override
	public boolean performFinish() {
		this.selectedMethods = page.getSelectedMethods();
		return true;
	}
	
	public List<String> getMethodNames() {
		return this.selectedMethods;
	}
	
}
