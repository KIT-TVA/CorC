package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Util class for testing purposes.
 * @author Fynn
 */
public class FileHandler {
	private static URI projectPath = null;
	
	public static void clearLog(final URI projectPath) {
		FileHandler.projectPath = projectPath;
		var logFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\log.txt");
		if (logFile.exists()) {
			logFile.delete();
		} 
	}
	
	public static void log(final URI projectPath, String text) {
		FileHandler.projectPath = projectPath;
		try {
			var logFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\log.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} 
			if (text.charAt(text.length()-1) != '\n') text += "\n";
			Files.write(Paths.get(logFile.getPath()), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}
	
	public static void log(String text) {	
		if (projectPath == null) {
			return;
		}
		try {
			var logFile = new File(FileUtil.getProjectLocation(FileHandler.projectPath) + "\\tests\\log.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} 
			if (text.charAt(text.length()-1) != '\n') text += "\n";
			Files.write(Paths.get(logFile.getPath()), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}
		
	public static boolean writeToFile(String location, String className, String content) {
		try {
			var dir = new File(location);	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			var javaFile = new File(location + "\\" + className + ".java");
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			} 
			Files.write(Paths.get(javaFile.getPath()), new byte[] {}, StandardOpenOption.TRUNCATE_EXISTING);
			Files.write(Paths.get(javaFile.getPath()), content.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean createFile(final URI projectPath, String className, String code) {
		try {
			var dir = new File(FileUtil.getProjectLocation(projectPath) + "\\tests");	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".java");
			if (!javaFile.exists()){
				javaFile.createNewFile();
		    }
			writeToFile(projectPath, className, code);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public static boolean deleteFolder(final URI projectPath, final String folderName) {
			var project = FileUtil.getProject(projectPath);
			var folder = project.getFolder(folderName);
			if (!folder.exists()) {
				return false;
			}
			var files = FileUtil.getFiles(folder, "");
			for (var file : files) {
				deleteSpecificFile(file.getLocation().toOSString());
			}
			var folderObj = new File(FileUtil.getProjectLocation(projectPath) + "/" + folderName);
			try {
				Files.delete(Paths.get(folderObj.getPath()));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}
	
	public static boolean deleteSpecificFile(final String fullFilePath) {
		try {
			var javaFile = new File(fullFilePath);
			if (!javaFile.exists()){
				return false;
		    }
			Files.delete(Paths.get(javaFile.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean deleteFile(final URI projectPath, String className) {
		try {
			var dir = new File(FileUtil.getProjectLocation(projectPath) + "\\tests");	
			if (!dir.exists()) {
				return false;
			}
			var javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".java");
			if (!javaFile.exists()){
				return false;
		    }
			Files.delete(Paths.get(javaFile.getPath()));
			javaFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\" + className + ".class");
			if (javaFile.exists()){
				Files.delete(Paths.get(javaFile.getPath()));
		    }
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean writeToFile(final URI projectPath, final String className, String content) {
		writeToFile(FileUtil.getProjectLocation(projectPath) + "\\tests", className, content);
		return true;
	}
	
	private static IFile findDiagram(IContainer folder, String diagramName) {
		try {
			IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					IFile foundFile = findDiagram((IContainer) resource, diagramName);
					if (foundFile != null) {
						return foundFile;
					}
				} else if (resource instanceof IFile) {

					final IFile file = (IFile) resource;
					if (file.getName().equals(diagramName + ".diagram")) {
						return file;
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Diagram loadDiagramFromClass(final URI projectPath, final String folderName, final String className, final String diagramName) {
		if (className.isBlank() || diagramName.isBlank()) {
			return null;
		}
		final ResourceSet rSet = new ResourceSetImpl();
		final IContainer folder = FileUtil.getProject(projectPath).getFolder(folderName);
		if (folder == null) {
			return null;
		}
		final IFile file = FileUtil.getProject(projectPath).getFolder("src/" + className).getFile(diagramName + ".diagram");
		if (file == null) {
			return null;
		}
		return GetDiagramUtil.getDiagramFromFile(file, rSet);
	}
	
	public static Diagram loadDiagramFromClass(final URI projectPath, final String folderName, final String diagramName) {
		if (diagramName.isBlank()) {
			return null;
		}
		final ResourceSet rSet = new ResourceSetImpl();
		final IContainer folder = FileUtil.getProject(projectPath).getFolder(folderName);
		if (folder == null) {
			return null;
		}
		final IFile file = findDiagram(folder, diagramName);
		if (file == null) {
			return null;
		}
		return GetDiagramUtil.getDiagramFromFile(file, rSet);
	}
	
	public static boolean isSPL(final IProject project) {
		try {
			if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
				return true;
			} else {
				return false;
			}
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}	
		/*
		final var projectLocation = FileUtil.getProjectLocation(projectPath);
		final var modelFile = new File(projectLocation + "/model.xml");
		final List<String> lines;
			
		try {
			lines = Files.readAllLines(Paths.get(modelFile.getPath()), StandardCharsets.UTF_8);
			for (var line : lines) {
				if (line.equals("<featureModel>")) {
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;*/
	}
	
	public static boolean isSPL(final URI projectPath) {
		final var project = FileUtil.getProject(projectPath);
		return isSPL(project);
	}
	
	public static boolean saveConfig(final URI projectPath, final CbCFormula formula, final Features features, boolean isTestCase) {
		// get className
		final String className = ClassHandler.getClassName(formula);
		// get current config name
		final String currentConfigName = features.getCurConfigName();
		// copy test contents to new file with config name
		String code;
		if (isTestCase) {
			code = ClassHandler.classExists(projectPath, className + "Test");
		} else {
			code = ClassHandler.classExists(projectPath, className);
		}
		if (!createFile(projectPath, currentConfigName, code)) {
			return false;
		}
		return true;
	}
}