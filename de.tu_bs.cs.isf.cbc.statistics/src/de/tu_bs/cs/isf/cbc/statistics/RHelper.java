package de.tu_bs.cs.isf.cbc.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import de.tu_bs.cs.isf.cbc.tool.helper.Features;


public class RHelper {
	
	private static final String PLUGIN_ID ="de.tu_bs.cs.isf.cbc.statistics";
    private static HashMap<StatisticsEntry, String> configEntries;
	private boolean isSpl;
	private boolean configView;	
	private boolean fullDataSet;
	private Features features;
    
    public RHelper() {
    	this.configEntries = null;
    	this.isSpl = false;
    }
    
    public RHelper(final HashMap<StatisticsEntry, String> configEntries, final Features features) {
    	this.configEntries = configEntries;
    	this.features = features;
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
		DiagramAxes axes = new DiagramAxes(false, false);
		for (String diagramName : diagramNames) {
			createPlotForDiagram(diagramName, axes, entries);
		}
		return builtPlot(axes);
	}
	
	private void createPlotForDiagram(String diagramName, DiagramAxes axes, final List<StatisticsEntry> entries) {
			float totalTime = 0;
			for (StatisticsEntry entry : entries) {
				if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
					totalTime = totalTime + entry.getData().getAutoModeTimeInMillis();
				}
			}
			axes.addData(diagramName, "" + totalTime);
	}
	
	private List<StatisticsEntry> getEntriesForDiagram(final List<StatisticsEntry> entries, String diagramName) {
			final List<StatisticsEntry> diagramEntries = new ArrayList<StatisticsEntry>();
			for (StatisticsEntry entry : entries) {
				if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
					diagramEntries.add(entry);
				}
			}
			return diagramEntries;
	}

	
	private String createAvgPlot(final List<String> diagramNames) {
		DiagramAxes axes = new DiagramAxes(false, true);
		int numEntries = 0;
		var configs = extractConfigs(this.configEntries);
		
		for (String diagramName : diagramNames) {
			numEntries = 0;
			float avgTotalTime = 0;
			for (final String config : configs) {
				for (final StatisticsEntry entry : this.configEntries.keySet()) {
					if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
						avgTotalTime = avgTotalTime + entry.getData().getAutoModeTimeInMillis();
						numEntries++;
					}
				}
			}
			avgTotalTime /= numEntries; 
			axes.addData(diagramName + " [" + numEntries + " files]", avgTotalTime + "");
		}
		return builtPlot(axes);
	}
	
	private List<String> extractConfigs(final HashMap<StatisticsEntry, String> configEntries) {
		var configs = configEntries.values().stream().distinct().toList();
		if (configs.size() != this.features.getSize()) {
			this.fullDataSet = false;
		} else {
			this.fullDataSet = true;
		}
		return configs;
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
		DiagramAxes axes = new DiagramAxes(false, false);
		var configs = extractConfigs(this.configEntries);
		
		for (String diagramName : diagramNames) {
			for (final String config : configs) {
				float totalTime = 0;
				for (final StatisticsEntry entry : this.configEntries.keySet()) {
					if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
						totalTime = totalTime + entry.getData().getAutoModeTimeInMillis();
					}
				}
				axes.addData(getInitials(config) + " [" + diagramName + "]", totalTime + ""); 
			}
		}
		return builtPlot(axes);
	}

	private String createProofStepPlot(final List<String> diagramNames, final List<StatisticsEntry> entries) {
		DiagramAxes axes = new DiagramAxes(true, false);
		var configs = extractConfigs(this.configEntries);
		
		if (isSPL()) {
			for (String diagramName : diagramNames) {
				for (final String config : configs) {
					int proofSteps = 0;
					for (final StatisticsEntry entry : this.configEntries.keySet()) {
						if (entry.getMapping().getCorcDiagramName().equals(diagramName) && config.equals(this.configEntries.get(entry))) {
							var fileName = extractKeyFileName(entry.getMapping().getKeyFilePath());
							proofSteps += entry.getData().getTotalRuleApps();
						}
					}
					axes.addData(getInitials(config) + " [" + diagramName + "]", "" + proofSteps);
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
				axes.addData(diagramName, "" + proofSteps);
			}
		}
		return builtPlot(axes, true);
	}
	
	private String extractKeyFileName(String path) {
		int delim = path.lastIndexOf(".");
		while (Character.isJavaIdentifierPart(path.charAt(--delim)))
			;
		path = path.substring(delim + 1, path.length());
		return path.substring(0, path.indexOf('.'));
	}

	private String builtPlot(DiagramAxes axes, boolean stepPlot) {
		String xAxis = axes.getX().substring(0, axes.getX().length()-2) + ")\r\n";
		String yAxis = axes.getY().substring(0, axes.getY().length()-2) + ")\r\n";
		
		String margins = "linch <-  max(strwidth(diagram, \"inch\")+0.4, na.rm = TRUE)\r\n"
				+ "par(mai=c(linch,1.02,0.82,0.42))\r\n";
		
		String colors = createColors(axes.getDataNum());
		
		final String plotCommand;
		if (stepPlot) {
			plotCommand = "barplot(steps, ylab = \"Total number of proof steps\", col=" + colors + ", names.arg=diagram, las=2, ylim=c(0, ceiling(max(steps, na.rm=TRUE)) + ceiling(max(steps, na.rm=TRUE)*0.3)))\r\n";
		} else {
			if (axes.isAverageData()) {
				plotCommand = "barplot(time, ylab = \"Average AutoMode time [ms]\", col=" + colors + ", names.arg=diagram, las=2, ylim=c(0, ceiling(max(time, na.rm=TRUE)) + ceiling(max(time, na.rm=TRUE)*0.3)))\r\n";
			} else {
				plotCommand = "barplot(time, ylab = \"AutoMode time [ms]\", col=" + colors + ", names.arg=diagram, las=2, ylim=c(0, ceiling(max(time, na.rm=TRUE)) + ceiling(max(time, na.rm=TRUE)*0.3)))\r\n";
			}
		}
		String legend = "legend(\"topright\", legend=c(\"Fully proven\", \"Partially proven\"), fill = c(\"green\", \"red\"))\r\n";
		return xAxis + yAxis + margins + plotCommand + legend;
	}
	
	private String createColors(int dataNum) {
		String colors = "c(";
		String color = this.fullDataSet ? "green" : "red";
		for (int i = 0; i < dataNum-1; i++) {
			colors += "\"" + color + "\", ";
		}
		colors += "\"" + color + "\")";
		return colors;
	}
	
	private String builtPlot(DiagramAxes axes) {
		return builtPlot(axes, false);
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
