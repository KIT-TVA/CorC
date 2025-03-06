package de.tu_bs.cs.isf.lattice.startup;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.tu_bs.cs.isf.lattice.builder.LatticeNature;
import de.tu_bs.cs.isf.lattice.jobs.LatticeParseJob;

public class Startup implements IStartup {

	private static final Bundle BUNDLE = FrameworkUtil.getBundle(Startup.class);
	private static final ILog LOGGER = Platform.getLog(BUNDLE);

	private Map<String, IProject> projectsToParse = new HashMap<>();

	@Override
	public void earlyStartup() {
		log("Startup execution");

		// Parse lattice
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			if (project.isOpen()) {
				try {
					if (project.isNatureEnabled(LatticeNature.NATURE_ID)) {
						log("Project: " + project.toString());

						final Job parseJob = new LatticeParseJob(project);
						parseJob.schedule();
					}
				} catch (CoreException e) {
					log("Nature ID CoreException", e);
				}
			}
		}

		// Listen for opened projects
		IResourceChangeListener rcl = new ProjectListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(rcl, IResourceChangeEvent.POST_CHANGE);
	}

	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Exception e) {
		LOGGER.log(new Status((e == null ? Status.INFO : Status.ERROR), BUNDLE.getSymbolicName(), msg, e));
	}

	private final class ProjectListener implements IResourceChangeListener {
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				log("Visitor added.");
				final IResourceDelta delta = event.getDelta();
				visitDelta(delta);
				visitLatticeChanged(delta);
			}
		}
	}

	private void visitLatticeChanged(IResourceDelta delta) {
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		// Checking src folders of all projects
		for (IProject project : projects) {
			if (project.isOpen()) {
				try {
					if (project.isNatureEnabled(LatticeNature.NATURE_ID)) {
						final String projectSrcPath = project.getName().concat("/lattice");
						final IResourceDelta srcDelta = delta.findMember(new Path(projectSrcPath));
						if (srcDelta == null) {
							// changed file lies not within this project
							continue;
						}

						try {
							// Check all files beneath
							srcDelta.accept(new IResourceDeltaVisitor() {

								@Override
								public boolean visit(IResourceDelta delta) throws CoreException {
									final IResource resource = delta.getResource();
									if (delta.getKind() == IResourceDelta.CHANGED && resource != null
											&& resource.getType() == IResource.FILE) {
										final IFile file = (IFile) resource;
										log("Visiting file: " + delta.getKind() + " " + delta.getFlags() + " "
												+ resource.toString());
										if (file.getFileExtension().equals("dot")) {
											log("Need to-reparse lattice file.");

											final Job parseJob = new LatticeParseJob(project);
											parseJob.schedule();
										}
									}
									return true;
								}
							});
						} catch (CoreException e) {
							log("Error while visiting changed lattice files.", e);
						}
					}
				} catch (CoreException e) {
					log("Nature ID CoreException", e);
				}
			}
		}
	}

	private void visitDelta(IResourceDelta delta) {
		try {
			delta.accept(new IResourceDeltaVisitor() {
				public boolean visit(IResourceDelta delta) throws CoreException {
					log("Delta: " + delta.getKind() + " " + delta.getResource().getType() + " "
							+ delta.getResource().isAccessible());
					if (delta.getKind() == IResourceDelta.ADDED && delta.getResource().getType() == IResource.PROJECT) {
						// Project is opened but not accessible
						IProject project = (IProject) delta.getResource();
						assert !project.isAccessible();
						projectsToParse.put(project.getName(), project);
					}

					if (delta.getKind() == IResourceDelta.CHANGED
							&& delta.getResource().getType() == IResource.PROJECT) {
						// Project is opened and accessible and possibly needs to be parsed for the
						// first time
						IProject project = (IProject) delta.getResource();
						assert project.isAccessible();
						final String key = project.getName();
						if (projectsToParse.containsKey(key) && project.isAccessible()) {
							final String osString = project.getRawLocation().toOSString();
							log("Need to parse the project: " + osString.toString());

							final Job parseJob = new LatticeParseJob(project);
							parseJob.schedule();

							projectsToParse.remove(key);
						}
					}

					// only continue for the workspace root
					return delta.getResource().getType() == IResource.ROOT;
				}
			});
		} catch (CoreException e) {
			// handle error
		}
	}
}
