package de.tu_bs.cs.isf.lattice;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class Lattices {
	
	private static final Map<String, Lattice> lattices = new ConcurrentHashMap<>();
	
	public static void addLatticeForProject(final IProject project, IFile file) {
		final Lattice lattice = LatticeBuilder.buildLatticeFromDotWithGEF(new File(file.getLocation().toOSString()));
		lattices.put(project.getName(), lattice);
	}
	
	public static Lattice getLatticeForProject(final IProject project) {
		return lattices.get(project.getName());
	}
	
	public static Lattice getLatticeForProject(final String projectName) {
		return lattices.get(projectName);
	}
}
