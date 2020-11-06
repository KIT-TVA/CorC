package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

public class VerifyWithKeYHandler extends AbstractHandler implements IHandler {

	private static int counter = 0;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = null;
		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getName().contains("Userstudy")) {
				project = p;
			}
		}
		if (project != null) {
			String location = project.getLocation() + "/src/";
			File javaFile = new File(location + "FirstExercise.java");
			if (!javaFile.exists()) {
				javaFile = new File(location + "SecondExercise.java");
			}
			try {
				File javaFileCopy = new File(location + "saved/Ex" + counter +  ".txt");
				Files.copy(javaFile.toPath(), javaFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);  
				IPath iLocation = Path.fromOSString(javaFileCopy.getAbsolutePath()); 
				IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
				ifile.refreshLocal(0, null);
				KeYInteraction.startKeYProofFirstContract(javaFile, counter);
				counter++;
			} catch (IOException | CoreException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
}
