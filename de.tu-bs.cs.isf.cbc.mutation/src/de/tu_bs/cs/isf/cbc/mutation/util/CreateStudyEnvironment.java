package de.tu_bs.cs.isf.cbc.mutation.util;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import de.tu_bs.cs.isf.cbc.mutation.feature.Framework;
import de.tu_bs.cs.isf.cbc.mutation.feature.JavaDirectoryLoader;

public class CreateStudyEnvironment {

	public static String config = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\MutationFramework\\src\\Utils\\EnvConfig";
	
	public static void main(String[] args) {
		Framework.config = config;
		
		JavaDirectoryLoader loader = new JavaDirectoryLoader();
		File[] filesToCopy = loader.loadData(Framework.getTargetConfigString("Source"));
		
		//Files may have the same name and we want to get all Files into one folder
		//Therefore those name conflicts need to be solved.
		//The first step is identifying conflicts through set addition.
		//Refactoring has to be done manually
		Set<String> fileSet = new TreeSet<String>();
		for(File f: filesToCopy) {
			if(!fileSet.add(f.getName())) {
				System.out.println(f.getAbsolutePath());
			}
		}
		if(fileSet.size() != filesToCopy.length)
			System.err.println("Some Files share the same name! Detected number of cases: " + (filesToCopy.length - fileSet.size()));
		
		//Copy Files to target Directory
		String src = Framework.getTargetConfigString("JavaTarget");
		for(File f: filesToCopy) {
			try {
				FileUtils.copyFile(f, new File(src + File.separator + f.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//Get Archives from Source
		loader.fileEnding = ".jar";
		filesToCopy = loader.loadData(Framework.getTargetConfigString("Source"));
		
		//Copy Archives to target directory
		String arch = Framework.getTargetConfigString("ArchiveTarget");
		for(File f: filesToCopy) {
			try {
				FileUtils.copyFile(f, new File(arch + File.separator + f.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Done!");
	}

}
