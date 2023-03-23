package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class JavaFile {
	private URI projectPath;
	private String className;
	
	public JavaFile(URI projectPath, String className) {
		this.projectPath = projectPath;
		this.className = className;
	}
	
	public boolean write(String content) {
		write(FileUtil.getProjectLocation(projectPath) + "\\tests", content);
		return true;
	}

	public boolean write(String location, String content) {
		try {
			var dir = new File(location);	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			var javaFile = new File(location + "/" + className + ".java");
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
	
	public boolean create(String code) {
		return create("tests", code);
	}
	
	public boolean create(final String folderName, final String code) {
		var project = FileUtil.getProject(projectPath);
		var folder = project.getFolder(folderName);
		try {
			if (!folder.exists()) {
				folder.create(true, true, null);
			}
			var javaFile = new File(folder.getLocation().toOSString() + "/" + className + ".java");
			if (!javaFile.exists()){
				javaFile.createNewFile();
		    }
			write(folder.getLocation().toOSString(), code);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean delete() {
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

	public boolean delete(final String fullFilePath) {
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
}
