package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Util class for testing purposes.
 * @author Fynn
 */
public class Util {
	private static URI projectPath = null;
	
	public static void clearLog(final URI projectPath) {
		Util.projectPath = projectPath;
		var logFile = new File(FileUtil.getProjectLocation(projectPath) + "\\tests\\log.txt");
		if (logFile.exists()) {
			logFile.delete();
		} 
	}
	
	public static void log(final URI projectPath, String text) {
		Util.projectPath = projectPath;
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
			var logFile = new File(FileUtil.getProjectLocation(Util.projectPath) + "\\tests\\log.txt");
			if (!logFile.exists()) {
				logFile.createNewFile();
			} 
			if (text.charAt(text.length()-1) != '\n') text += "\n";
			Files.write(Paths.get(logFile.getPath()), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}
}
