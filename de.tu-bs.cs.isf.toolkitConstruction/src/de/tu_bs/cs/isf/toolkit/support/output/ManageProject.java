package de.tu_bs.cs.isf.toolkit.support.output;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

/**
 * Helper class to create the files for a taxonomy
 * @author Tobias
 *
 */
public class ManageProject {
	
	/**
	 * The content of the info file
	 */
	private static String infoFileContent = "";
	
	/**
	 * Creates a java project for the taxonomy
	 * @param name	the name of the project
	 * @return	the package where java classes can be created
	 */
	public static IPackageFragment createProject(String name) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject("de.tu-bs.cs.isf." + name);
		try {
			if (!project.exists()) {
				project.create(null);
				project.open(null);
				
				IProjectDescription description = project.getDescription();
				description.setNatureIds(new String[] { JavaCore.NATURE_ID });
				project.setDescription(description, null);
				
				IJavaProject javaProject = JavaCore.create(project); 
				
				IFolder binFolder = project.getFolder("bin");
				binFolder.create(false, true, null);
				javaProject.setOutputLocation(binFolder.getFullPath(), null);
				
				List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
				IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
				LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
				for (LibraryLocation element : locations) {
				 entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
				}
				javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
				
				IFolder sourceFolder = project.getFolder("src");
				sourceFolder.create(false, true, null);
				IFolder infoFolder = project.getFolder("info");
				infoFolder.create(false, true, null);
				IFile infoFile = infoFolder.getFile("InfoFile.txt");
				String content = "Information about the Toolkit construction.\n" + infoFileContent;
				infoFileContent = "";
				InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
				infoFile.create(stream, false, null);
				
				IPackageFragmentRoot packageRoot = javaProject.getPackageFragmentRoot(sourceFolder);
				IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
				IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
				System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
				newEntries[oldEntries.length] = JavaCore.newSourceEntry(packageRoot.getPath());
				javaProject.setRawClasspath(newEntries, null);
				
				IPackageFragment pack = javaProject.getPackageFragmentRoot(sourceFolder).createPackageFragment("de.tu_bs.cs.isf." + name, false, null);
				return pack;
			} else {
				IJavaProject javaProject = JavaCore.create(project); 
				IFolder sourceFolder = project.getFolder("src");
				IPackageFragment pack = javaProject.getPackageFragmentRoot(sourceFolder).getPackageFragment("de.tu_bs.cs.isf." + name);
				IFolder infoFolder = project.getFolder("info");
				IFile infoFile = infoFolder.getFile("InfoFile.txt");
				String content = "Information about the Toolkit construction.\n" + infoFileContent;
				infoFileContent = "";
				InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
				infoFile.setContents(stream, true, false, null);
				return pack;
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates a new Java class for the toolkit
	 * @param pack		the package of the Java class
	 * @param className	the name of the Java class
	 * @param content	the content of the class as String
	 */
	public static IFile createJavaFile(IPackageFragment pack, String className, String content) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + pack.getElementName() + ";\n");
		buffer.append("\n");
		buffer.append(content);
		
		try {
			ICompilationUnit unit = pack.createCompilationUnit(className + ".java", buffer.toString(), true, null);
			IFile file = (IFile) unit.getCorrespondingResource();
			return file;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Adds a line to the infoFileContent String for the info file
	 * @param content	the content that should be added
	 */
	public static void addToInfoFileContent(String content) {
		infoFileContent += content + "\n";
		Console.println(content);
	}
}
