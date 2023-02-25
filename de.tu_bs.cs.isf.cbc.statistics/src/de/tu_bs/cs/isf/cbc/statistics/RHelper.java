package de.tu_bs.cs.isf.cbc.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;


public class RHelper {
	
	private static final String PLUGIN_ID ="de.tu_bs.cs.isf.cbc.statistics";
    private static HashMap<StatisticsEntry, String> configEntries;
	private boolean isSpl;
	private boolean configView;	
    
    public RHelper() {
    	this.configEntries = null;
    	this.isSpl = false;
    }
    
    public RHelper(final HashMap<StatisticsEntry, String> configEntries) {
    	this.configEntries = configEntries;
    	this.isSpl = true;
    }

	public String generatePDF(String fileName, List<StatisticsEntry> entries, DiagramType type) {
		String rootLocation = getAbsoluteFileRootLocation();
		String pathToPDF = rootLocation + fileName + ".pdf";
		String header = "pdf(file=\""+ pathToPDF.replaceAll("\\\\", "/") +"\")\r\n";
		
		if(!generate(header, rootLocation, fileName, entries, type))
			return null;
		else
			return pathToPDF;
	}
	
	public String generatePNG(String fileName, List<StatisticsEntry> entries, final DiagramType type) {
		String rootLocation = getAbsoluteFileRootLocation();
		String pathToPNG = rootLocation + fileName + ".png";
		String header = "png(filename=\""+ pathToPNG.replaceAll("\\\\", "/") +"\")\r\n";
		
		if(!generate(header, rootLocation, fileName, entries, type))
			return null;
		else
			return pathToPNG;
	}
	
	private boolean generate(String header, String rootLocation, String fileName, List<StatisticsEntry> entries, DiagramType type) {
		File directory = new File(rootLocation);
		if (! directory.exists()){
	        directory.mkdir();
	    }

		String code = header + generateRCodeBody(entries, type);

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
	
	private String createPlot(final List<String> diagramNames, final List<StatisticsEntry> entries) {
		String xAxis = "diagram <- c(";
		String yAxis = "time <- c(" ;
		
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
		return builtPlot(xAxis, yAxis);
	}
	
	private String createAvgPlot(final List<String> diagramNames) {
		String xAxis = "diagram <- c(";
		String yAxis = "time <- c(" ;
		int numEntries = 0;
		
		for (String diagramName : diagramNames) {
			numEntries = 0;
			float avgTotalTime = 0;
			for (final String config : this.configEntries.values().stream().distinct().toList()) {
				for (final StatisticsEntry entry : this.configEntries.keySet()) {
					if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
						avgTotalTime = avgTotalTime + entry.getData().getAutoModeTimeInMillis();
						numEntries++;
					}
				}
			}
			avgTotalTime /= numEntries; 
			xAxis = xAxis + "\"" + diagramName + " [" + numEntries + " files]" + "\", ";
			yAxis = yAxis + avgTotalTime + ", ";
		}
		return builtPlot(xAxis, yAxis);
	}
	
	private String getInitials(String str) {
		String initials = "";
		for (int i = 0; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				initials += str.charAt(i);
			}
		}
		return initials;
	}
	
	private String createConfigPlot(final List<String> diagramNames) {
		String xAxis = "diagram <- c(";
		String yAxis = "time <- c(" ;
		
		for (String diagramName : diagramNames) {
			for (final String config : this.configEntries.values().stream().distinct().toList()) {
				float totalTime = 0;
				for (final StatisticsEntry entry : this.configEntries.keySet()) {
					if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
						totalTime = totalTime + entry.getData().getAutoModeTimeInMillis();
					}
				}
				xAxis = xAxis + "\"" + getInitials(config) + " [" + diagramName + "]" +"\", ";
				yAxis = yAxis + totalTime + ", ";
			}
		}
		return builtPlot(xAxis, yAxis);
	}

	private String createProofStepPlot(final List<String> diagramNames, final List<StatisticsEntry> entries) {
		String xAxis = "diagram <- c(";
		String yAxis = "steps <- c(" ;
		
		if (isSPL()) {
			for (String diagramName : diagramNames) {
				for (final String config : this.configEntries.values().stream().distinct().toList()) {
					int proofSteps = 0;
					for (final StatisticsEntry entry : this.configEntries.keySet()) {
						if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
							proofSteps += entry.getData().getTotalRuleApps();
						}
					}
					xAxis = xAxis + "\"" + getInitials(config) + " [" + diagramName + "]" +"\", ";
					yAxis = yAxis + proofSteps + ", ";
				}
			}
		} else {
			for (String diagramName : diagramNames) {
				int proofSteps = 0;
				for (StatisticsEntry entry : entries) {
					if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
							proofSteps += entry.getData().getTotalRuleApps();
					}
				}
				xAxis = xAxis + "\"" + diagramName +"\", ";
				yAxis = yAxis + proofSteps + ", ";
			}
		}
		return builtPlot(xAxis, yAxis, true);
	}

	private String builtPlot(String xAxis, String yAxis, boolean stepPlot) {
		xAxis = xAxis.substring(0, xAxis.length()-2) + ")\r\n";
		yAxis = yAxis.substring(0, yAxis.length()-2) + ")\r\n";
		
		String margins = "linch <-  max(strwidth(diagram, \"inch\")+0.4, na.rm = TRUE)\r\n"
				+ "par(mai=c(linch,1.02,0.82,0.42))\r\n";
		
		final String plotCommand;
		if (stepPlot) {
			plotCommand = "barplot(steps, ylab = \"Proof Steps\", names.arg=diagram, las=2, ylim=c(0, ceiling(max(steps, na.rm=TRUE)) + ceiling(max(steps, na.rm=TRUE)*0.1)))\r\n";
		} else {
			plotCommand = "barplot(time, ylab = \"AutoMode Time [ms]\", names.arg=diagram, las=2, ylim=c(0, ceiling(max(time, na.rm=TRUE)) + ceiling(max(time, na.rm=TRUE)*0.1)))\r\n";
		}
		return xAxis + yAxis + margins + plotCommand;
	}
	
	private String builtPlot(String xAxis, String yAxis) {
		return builtPlot(xAxis, yAxis, false);
	}
	
	
	private String generateRCodeBody(List<StatisticsEntry> entries, DiagramType type) {
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
		
		String plotsStr = "";
		if (type == DiagramType.PROOF_STEPS) {
			plotsStr = createProofStepPlot(diagramNames, entries);
		} else if (type == DiagramType.AUTOMODE) {
			if (isSPL()) {
				if (this.configView) {
					plotsStr = createConfigPlot(diagramNames);
				} else {
					plotsStr = createAvgPlot(diagramNames);
				}
			} else {
				plotsStr = createPlot(diagramNames, entries);
			}
		}
		return plotsStr;
	}

	public void setSPL(boolean b) {
		this.isSpl = b;
	}
	
	public boolean isSPL() {
		return this.isSpl;
	}

	public void setConfigView(boolean configView) {
		this.configView = configView;
	}
	
}
