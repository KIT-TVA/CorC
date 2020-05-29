package de.tubs.carsten.robot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class KeyResultHelper {

	private static final String[] ignoreList = new String[]{"a0"};
	
	private static final int METHOD_POS = 3;
	private static final int CLOSED = 6;
	private static final int NUMBER_OF_NODES = 7;
	private static final int RUNTIME = 9;
	
	public static SimianResult readSimianResult(File resultFile) throws IOException{
		SimianResult sr = new SimianResult(ignoreList);
		try(BufferedReader br = new BufferedReader(new FileReader(resultFile))){
			br.readLine(); //Remove header
			String line;
			while((line = br.readLine()) != null){
				String[] values = line.split(";");
				String method = values[METHOD_POS].trim();
				String closed = values[CLOSED].trim();
				String methodName = method.substring(0, method.indexOf("("));
				int nodes = Integer.parseInt(values[NUMBER_OF_NODES].trim());
				int runtime = Integer.parseInt(values[RUNTIME].trim());
				boolean closedBool = "Closed".equalsIgnoreCase(closed);
				sr.addLine(methodName, nodes, runtime, closedBool);
			}
		}
		return sr;
	}
}
