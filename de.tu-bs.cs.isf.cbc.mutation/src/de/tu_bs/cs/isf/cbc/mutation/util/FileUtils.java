package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {

	public static List<File> dirs;
	
	public static List<File> getDirs(String path) {
		dirs = new LinkedList<File>();
		File parentDir = new File(path);
		
		recDirs(parentDir);
		
		return dirs;
	}
	
	public static void recDirs(File parentDir) {
		for (File f: parentDir.listFiles()) {
			if (f.isDirectory()) {
				dirs.add(f);
				recDirs(f);
			}
		}
	}
}
