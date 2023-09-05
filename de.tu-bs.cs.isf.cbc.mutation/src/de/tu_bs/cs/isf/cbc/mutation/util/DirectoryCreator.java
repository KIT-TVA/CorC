package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;

public class DirectoryCreator {
	public static void makeDir(File dir){
	    System.out.println("\nMake " + dir.getAbsolutePath() + " directory...");
	    boolean newly_made = dir.mkdir();
	    if(!newly_made){
	      System.out.println(dir.getAbsolutePath() + " directory exists already.");
	    }else{
	      System.out.println("Making " + dir.getAbsolutePath() + " directory " + " ...done.");
	    }
	  }
}
