package de.tu_bs.cs.isf.lattice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public class Lattices {
	
	private static final Map<String, Lattice> lattices = new ConcurrentHashMap<>();
	
	public static void addLatticeForProject(final IProject project, IFile file) {
		Lattice lattice;
		try {
			lattice = LatticeBuilder.buildLatticeFromDot(file.getContents());
			lattices.put(project.getName(), lattice);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Lattice getLatticeForProject(final IProject project) {
		return lattices.get(project.getName());
	}
	
	public static Lattice getLatticeForProject(final String projectName) {
		return lattices.get(projectName);
	}
}
