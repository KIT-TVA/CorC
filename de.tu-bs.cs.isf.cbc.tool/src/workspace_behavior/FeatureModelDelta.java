package workspace_behavior;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

/**
 * Class for recognizing changes in the Feature-model
 * 
 * @author David
 */
public class FeatureModelDelta {
	private IFeatureModel featureModel;
	private IFolder featureFolder;
	private String featureName;
	private IProject project;
	private List<Job> jobList = new ArrayList<Job>();
	private boolean createParentMethods;

	public FeatureModelDelta(IResource res) {
		Path path = Paths.get(res.getLocationURI());
		project = res.getProject();
		ProjectStructure Ps = new ProjectStructure(project);
		featureModel = FeatureModelManager.load(path);

		for (int i = 0; i < featureModel.getFeatureOrderList().size(); i++) {
			featureName = featureModel.getFeatureOrderList().get(i);
			featureFolder = project.getFolder("features/" + featureName);
			if (!featureFolder.exists()) {
				final Display display = PlatformUI.getWorkbench().getDisplay();
				Shell s = new Shell(display, SWT.ON_TOP);
				MessageBox dialog = new MessageBox(s, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				dialog.setText("Feature: " + featureName);
				dialog.setMessage("Do you want to create the Parents-methods?");
				int result = dialog.open();
				if (result == 64) {
					createParentMethods = true;
				}
				if (result == 128) {
					createParentMethods = false;
				}
				System.out.println(result);
				Job job = new Job("Add missing Project structure for Feature " + i) {
					String jobFeatureName = featureName;
					boolean createParentMethods2 = createParentMethods;

					@Override
					public IStatus run(IProgressMonitor monitor) {
						FeatureDelta fd = new FeatureDelta(featureModel, jobFeatureName, project);

						System.out.println("featureDeltaname" + fd.getName());
						try {
							Ps.update(fd, createParentMethods2);
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return Status.OK_STATUS;
					}
				};
				job.setRule(ResourcesPlugin.getWorkspace().getRoot());// no 2 jobs can run at the śame with the same
																		// rule
				jobList.add(job);
			}
		}
		for (int i = 0; i < jobList.size(); i++) {
			jobList.get(i).schedule();
		}
	}
}
