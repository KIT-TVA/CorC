package de.tu_bs.cs.isf.lattice.jobs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Lattices;

public class LatticeParseJob extends Job {

	private final IProject project;

	public LatticeParseJob(final IProject project) {
		super("Parsing lattice for project " + project.getName());
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final IFolder latticeFolder = project.getFolder("lattice");
		if (latticeFolder == null || !latticeFolder.exists()) {
			return new Status(IStatus.ERROR, "de.tu_bs.cs.isf.lattice", "No lattice folder in project.");
		}
		final IFile file = latticeFolder.getFile(Lattice.LATTICE_DOT);
		if (file == null || !file.exists()) {
			return new Status(IStatus.ERROR, "de.tu_bs.cs.isf.lattice", "No lattice file " + Lattice.LATTICE_DOT + " present.");
		}
		
		Lattices.addLatticeForProject(project, file);
		
		return Status.OK_STATUS;
	}

}
