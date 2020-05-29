package de.tubs.carsten.robot.util;

import java.io.File;

public class FileHelper {

	public static File createDir(String dir){
		File result = new File(dir);
		if(!result.exists()){
			result.mkdirs();
		}
		return result;
	}
	
	public static void deleteRecursevly(File f){
		if(f.isDirectory()){
			for (File subFile : f.listFiles()) {
				deleteRecursevly(subFile);
			}
		}
		f.delete();
	}
}
