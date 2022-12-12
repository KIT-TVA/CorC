package de.tu_bs.cs.isf.cbc.statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class CSVHelper {
	
	private static final String delimiter = ";";



	public String generateCSVFile(String fileLocation, List<StatisticsEntry> entries) {
		List<String> lines = new ArrayList<>();
		lines.add("Diagram"+ delimiter
				+"Key File"+ delimiter
				+"Automode Time"+ delimiter
				+"Number of Branches"+ delimiter
				+"Number of Nodes"+ delimiter
				+"Total Rule Applications"+ delimiter
				+"Time per Step"+ delimiter
				+"Time"+ delimiter
				+"Successfully proven");
		
		for(StatisticsEntry entry : entries) {
			List<String> newLineEntries = new ArrayList<>();
			newLineEntries.add(entry.getMapping().getCorcDiagramPath());
			newLineEntries.add(entry.getMapping().getKeyFilePath());

			newLineEntries.add(""+entry.getData().getAutoModeTimeInMillis());
			newLineEntries.add(""+entry.getData().getNumberOfBranches());
			newLineEntries.add(""+entry.getData().getNumberOfNodes());
			newLineEntries.add(""+entry.getData().getTotalRuleApps());
			newLineEntries.add(""+entry.getData().getTimePerStepInMillis());
			newLineEntries.add(""+entry.getData().getTimeInMillis());
			newLineEntries.add(""+entry.getData().isIsProven());
			
			lines.add(String.join(delimiter, newLineEntries));
		}
		
		String csvCode = String.join(System.lineSeparator(), lines);
		
		if(!fileLocation.endsWith("csv"))
			fileLocation += ".csv";
		
		try {
			writeToFile(fileLocation, csvCode, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Refresh the workspace in case the user decides to store the csv file within the workspace
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			return null;
		} catch (CoreException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
	private void writeToFile(String fileLocation, String content, boolean overwrite) throws IOException {
		File rFile = new File(fileLocation);
		if (!rFile.createNewFile()) {
			if (!overwrite)
				return;
		}
		FileWriter myWriter = new FileWriter(rFile);
		myWriter.write(content);
		myWriter.close();
	}

}
