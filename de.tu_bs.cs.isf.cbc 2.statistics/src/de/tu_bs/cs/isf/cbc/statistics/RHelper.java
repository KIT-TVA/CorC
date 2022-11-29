package de.tu_bs.cs.isf.cbc.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

public class RHelper {
	
	private static final String PLUGIN_ID ="de.tu_bs.cs.isf.cbc.statistics";
	
	

	public String generatePDF(String fileName, List<StatisticsEntry> entries) {
		String rootLocation = getAbsoluteFileRootLocation();
		String pathToPDF = rootLocation + fileName + ".pdf";
		String header = "pdf(file=\""+ pathToPDF.replaceAll("\\\\", "/") +"\")\r\n";
		
		if(!generate(header, rootLocation, fileName, entries))
			return null;
		else
			return pathToPDF;
	}
	
	public String generatePNG(String fileName, List<StatisticsEntry> entries) {
		String rootLocation = getAbsoluteFileRootLocation();
		String pathToPNG = rootLocation + fileName + ".png";
		String header = "png(filename=\""+ pathToPNG.replaceAll("\\\\", "/") +"\")\r\n";
		
		if(!generate(header, rootLocation, fileName, entries))
			return null;
		else
			return pathToPNG;
	}
	
	private boolean generate(String header, String rootLocation, String fileName, List<StatisticsEntry> entries) {
		File directory = new File(rootLocation);
		if (! directory.exists()){
	        directory.mkdir();
	    }

		String code = header + generateRCodeBody(entries);

		String rFileLocation = rootLocation + fileName + ".R";
		String errorFileLocation = rootLocation + fileName + "-errorlog.txt";

		try {
			writeToFile(rFileLocation, code, true);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false; // if this does not work ... well
		}

		// TODO: alter process information for builder -> RScript is in PATH
		ProcessBuilder rProcessBuilder = new ProcessBuilder("Rscript", rFileLocation);
		try {
			Process rProcess = rProcessBuilder.start();
			int returnCode = rProcess.waitFor();
			
			StringBuilder textBuilder = new StringBuilder();
			try (Reader reader = new BufferedReader(new InputStreamReader(rProcess.getErrorStream(),
					Charset.forName(StandardCharsets.UTF_8.name())))) {
				int c = 0;
				while ((c = reader.read()) != -1) {
					textBuilder.append((char) c);
				}
			}
			writeToFile(errorFileLocation, textBuilder.toString(), true);
			
			if(returnCode == 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			String errorOutput = "Rscript not found. Please install and make sure to add to PATH variable.\n"
					+ "  -> Eclipse may have to be restarted.";
			System.out.println(errorOutput);
			e.printStackTrace();
			return false;
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
	
	
	
	private String getAbsoluteFileRootLocation() {
		IPath pluginStateFolderPath = Platform.getStateLocation(Platform.getBundle(PLUGIN_ID));
		File pluginStateFolder = pluginStateFolderPath.toFile();

		String folderName = "generatedDiagrams";
		return pluginStateFolder.getAbsolutePath() + File.separator + folderName + File.separator;
	}
	
	private String generateRCodeBody(List<StatisticsEntry> entries) {
		List<String> diagramNames = new LinkedList<String>();
		for (StatisticsEntry entry : entries) {
			String entryDiagramName = entry.getMapping().getCorcDiagramName();
			boolean alreadyInDiagram = false;
			for (String name : diagramNames) {
				if (name.equals(entryDiagramName)) {
					alreadyInDiagram = true;
				}
			}
			if (!alreadyInDiagram) {
				diagramNames.add(entryDiagramName);
			}
		}
		
		String xAxis = "diagram <- c(";
		String yAxis = "time <- c(" ;
		
		// filling the axes
		for (String diagramName : diagramNames) {
			float totalTime = 0;
			for (StatisticsEntry entry : entries) {
				if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
					totalTime = totalTime + entry.getData().getAutoModeTimeInMillis();
				}
			}
			xAxis = xAxis + "\"" + diagramName +"\", ";
			yAxis = yAxis + totalTime + ", ";
		}
		xAxis = xAxis.substring(0, xAxis.length()-2) + ")\r\n";
		yAxis = yAxis.substring(0, yAxis.length()-2) + ")\r\n";
		
		String margins = "linch <-  max(strwidth(diagram, \"inch\")+0.4, na.rm = TRUE)\r\n"
				+ "par(mai=c(linch,1.02,0.82,0.42))\r\n";
		
//		String plotCommand = "mean(diagram)\r\nmean(time)\r\nplot(diagram,time)";
//		String plotCommand = "barplot(time,xlab = \"Diagrams\", ylab = \"Auto Mode Time in ms\", names.arg=diagram, las=2)\r\n";
		String plotCommand = "barplot(time, ylab = \"Auto Mode Time in ms\", names.arg=diagram, las=2)\r\n";
		
		return xAxis + yAxis + margins + plotCommand;
	}
	
}
