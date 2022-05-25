package de.tu_bs.cs.isf.cbc.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

public class FileUtil {
	private static String platformString = "/GCorCO/src/MainDoIt.diagram"; //platform:/resource/GCorC/src/MainDoIt.diagram;

	public static IFile getClassFile(String className) {
		IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
		if (fileResource != null) {
			IProject project = fileResource.getProject();
			return traverseFolders(project, className);

		}
		return null;
	}

	private static IFile traverseFolders(IContainer folder, String className) {
		try {
			IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					return traverseFolders((IContainer) resource, className);
				} else if (resource instanceof IFile) {

					final IFile file = (IFile) resource;
					if (file.getName().equals(className + ".java")) {
						return file;
					}
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> readFileInList(String path) {

		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		}

		catch (IOException e) {

			// do something
			e.printStackTrace();
		}
		return lines;
	}

	public static File writeFile(String problem, String location, int numberFile, boolean override) {
		File keyFile = new File(location + "/prove" + numberFile + ".key");
		File keyHelperFile = new File(location + "/helper.key");
		if (!keyFile.exists() || override) {
			try {
				keyFile.getParentFile().mkdirs();
				keyFile.createNewFile();
				FileWriter fw = new FileWriter(keyFile);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(problem);
				bw.close();

				if (!keyHelperFile.exists()) {
					keyHelperFile.createNewFile();
				}
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IPath iLocation = Path.fromOSString(keyFile.getAbsolutePath());
				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
				ifile.refreshLocal(0, null);
				return keyFile;
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IProject getProject(URI uri) {
		uri = uri.trimFragment();
		String uriPath = uri.toPlatformString(true);

		uriPath = uriPath.substring(1, uriPath.length());
		int positionOfSlash = uriPath.indexOf('/') + 1;
		uriPath = uriPath.substring(positionOfSlash, uriPath.length());
		IProject thisProject = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getFile(new Path(uriPath)).exists()) {
				thisProject = p;
			}
		}
		return thisProject;
	}

	public static String generateComposedClass(IProject project, String className, String methodStub,
			String methodPreCondition, String methodPostCondition, List<String> assignables) {
		String assignableString = "";
		if (!assignables.isEmpty()) {
			assignableString = "\n@assignable " + String.join(",", assignables) + ";";
		}
		String content = "public class Generated" + className + "{\n\n /*@\n@ public normal_behavior\n@requires "
				+ methodPreCondition + ";\n@ensures " + methodPostCondition + ";" + assignableString + "\n" + "@*/\n"
				+ methodStub + "\n}";
		File generatedClass = new File(project.getLocation().toOSString() + "/src_gen/Generated" + className + ".java");
		try {
			generatedClass.getParentFile().mkdirs();
			generatedClass.createNewFile();
			FileWriter fw = new FileWriter(generatedClass);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(content);
			bw.close();

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(generatedClass.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
			return "Generated" + className;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return "";

	}

}
