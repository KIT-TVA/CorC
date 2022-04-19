package workspace_behavior;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * Class for initializing a workspaceListener
 * 
 * @author David
 */
public class Initialize_Listener implements org.eclipse.ui.IStartup {

	@Override
	public void earlyStartup() {
		System.out.println("Listener initialized");
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResourceChangeListener listener = new IResourceChangeListener() {
			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				switch (event.getType()) {
				case IResourceChangeEvent.POST_CHANGE:
					try {
						event.getDelta().accept(new ProjectDelta());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		};
		workspace.addResourceChangeListener(listener);
	}
}
