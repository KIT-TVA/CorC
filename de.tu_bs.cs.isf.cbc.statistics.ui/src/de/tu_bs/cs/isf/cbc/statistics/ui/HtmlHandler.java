package de.tu_bs.cs.isf.cbc.statistics.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

import de.tu_bs.cs.isf.cbc.statistics.RHelper;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry;

public class HtmlHandler {

	// String with placeholder
//	private String beforeEntrys = "";
//	private String betweenEntrysAndDiagrams = ""; 
//	private String afterDiagrams = ""; 
	private int numberOfDiagrams;
	private List<StatisticsEntry> entries = new LinkedList<StatisticsEntry>();
	private HashMap<StatisticsEntry, String> configEntries = new HashMap<StatisticsEntry, String>();
	private List<String> configs = new ArrayList<String>();
	private List<IFile> selectedDiagramFiles = new LinkedList<IFile>();

	private String placeholderPlainStatistics = "";
	private String placeholderGeneratedDiagram = "";
	private String lineBreak = "<br />";
	private boolean configView;

	public void setDiagramPaths(List<?> paths) {

	}

	public void setData(int numberOfDiagrams, List<StatisticsEntry> entries, List<IFile> selectedDiagramFiles) {

		this.numberOfDiagrams = numberOfDiagrams;
		this.entries = entries;
		this.selectedDiagramFiles = selectedDiagramFiles;

	}
	
	public void setDataSPL(int numberOfDiagrams, final HashMap<StatisticsEntry, String> configEntries, final List<String> configs, final List<IFile> selectedDiagramFiles) {
		this.numberOfDiagrams = numberOfDiagrams;
		this.configEntries = configEntries;
		this.configs = configs;
		this.selectedDiagramFiles = selectedDiagramFiles;
	}

	public String getHtmlStringSPL() {
		boolean generatedDiagram = false;
		String diagramName = "";
		List<StatisticsEntry> entriesPerDiagram = new LinkedList<StatisticsEntry>();

		if (configEntries == null || configEntries.isEmpty()) {
			return null;
		}
		diagramName = configEntries.keySet().stream().findFirst().get().getMapping().getCorcDiagramName();
		final var configNames = configEntries.values().stream().distinct().sorted().toList();
		for (String config : configNames) {
			createConfigName(config);
			this.entries.clear();
			for (var entry : configEntries.keySet()) {
				if (!configEntries.get(entry).equals(config)) {
					continue;
				}
				this.entries.add(entry);
			}
			if (numberOfDiagrams == 1) {
				entriesPerDiagram = entries;
				placeholderPlainStatistics = placeholderPlainStatistics + "<h3 class\"text\">Diagram: " + diagramName + "</h3>";
				createPlaceholderPlainStringForOneDiagram(diagramName, entriesPerDiagram);
				placeholderGeneratedDiagram = "<div class=\"text block\">\r\n"
						+ "                <p>No diagrams generated. For diagram generation, please select more than one CorC diagram</p>\r\n" + "            </div>";
			} else if (numberOfDiagrams > 1) {
				
				for (IFile file : selectedDiagramFiles) {
					diagramName = file.getName().substring(0, file.getName().indexOf(".diagram"));
					for (StatisticsEntry entry : entries) {
						if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
							entriesPerDiagram.add(entry);
						}
					}
					placeholderPlainStatistics = placeholderPlainStatistics + "<h3 class\"text\">Diagram: " + diagramName + "</h3>";
					createPlaceholderPlainStringForOneDiagram(diagramName, entriesPerDiagram);
					entriesPerDiagram = new LinkedList<StatisticsEntry>();
				}
				if (generatedDiagram) {
					continue;
				}
				generatedDiagram = true;
				RHelper helper = new RHelper(configEntries);
				helper.setConfigView(this.configView);
				String pathToPNG = helper.generatePNG("statistics-png", entries);
				String pathToPDF = helper.generatePDF("statistics-pdf", entries);
				
//				helper.setStatisticsFileStringForDiagrams(entries);
//				List<String> diagramPaths = new LinkedList<String>();
				if(pathToPDF != null) {
					placeholderGeneratedDiagram += "<a class=\"text\" href=\" "+ pathToPDF + "\">Open as PDF</a>\r\n<br>\r\n";
				}
				
				if(pathToPNG != null) {
					placeholderGeneratedDiagram += "<img class=\"\" src=\"" + pathToPNG + "\">\r\n";
				}
				else {
					URL errorImageURI = Platform.getBundle("de.tu_bs.cs.isf.cbc.statistics.ui").getEntry("icons/how-to-environment-R.png");
					try {
						pathToPNG = new File(FileLocator.resolve(errorImageURI).toURI()).getAbsolutePath();
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}

					placeholderGeneratedDiagram += "<p>Cannot generate diagrams, probably because R is not installed.<br><strong>Please install R</strong> and add it to your operating system's environment path:</p>";
					placeholderGeneratedDiagram += "<img class=\"\" src=\"" + pathToPNG + "\">\r\n";
				}
			}
		}
	
//		TODO: abfangen keine entries

		String htmlStyleTag = "<style>\r\n" + "        body{\r\n" + "            margin: 0;\r\n"
				+ "            padding: 0;\r\n" + "        }\r\n" + "        .container{\r\n" + "        }\r\n"
				+ "        .text{\r\n" + "            font-family: sans-serif;\r\n" + "        }\r\n"
				+ "        .block{\r\n" + "            margin-left: 25px;\r\n" + "            margin-bottom: 45px;\r\n"
				+ "        }\r\n" + "        .horizontal-line{\r\n" + "            height: 5px;\r\n"
				+ "            background-color: #f3f3f3;\r\n" + "            border: none;\r\n" + "        }\r\n"
				+ "        .inner-box{\r\n" + "            padding: 10px;\r\n" + "        }\r\n"
				+ "        .left-margin-class{\r\n" + "            margin-left: 20px;\r\n" + "        }\r\n"
				+ "    </style>";

		return "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
				+ "    <title>WebCorCStatistics Viewer</title>\r\n" + "\r\n" + htmlStyleTag + "</head>\r\n"
				+ "<body>\r\n" + "    <div class=\"container\">\r\n" + "        <div class=\"inner-box\">\r\n"
				+ "            <h1 class=\"text\" style=\"color:DarkGreen\">Plain Statistics</h1>\r\n"
				+ "            <div class=\"text block\">\r\n" + "                <p>" + placeholderPlainStatistics
				+ "</p>\r\n" + "            </div>\r\n" + "        </div>\r\n"
				+ "        <hr class=\"horizontal-line\">\r\n" + "        <div class=\"inner-box\">\r\n"
				+ "            <h1 class=\"text\" style=\"color:DarkGreen\">Generated Diagram</h1>\r\n"
//				+ "            <img class=\"\" src=\"file:///D:/Uni/Bachelorarbeit/HTMLTemplateCode/test.png\">\r\n"
				+ placeholderGeneratedDiagram + "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
	}
	
	public String getHtmlString() {
		String diagramName = "";
		List<StatisticsEntry> entriesPerDiagram = new LinkedList<StatisticsEntry>();

		if (entries == null || entries.isEmpty()) {
			
		}
		else {
			
			if (numberOfDiagrams == 1) {
				entriesPerDiagram = entries;
				diagramName = entries.get(0).getMapping().getCorcDiagramName();
				placeholderPlainStatistics = placeholderPlainStatistics + "<h3 class\"text\">Diagram: " + diagramName + "</h3>";
				createPlaceholderPlainStringForOneDiagram(diagramName, entriesPerDiagram);
				
				placeholderGeneratedDiagram = "<div class=\"text block\">\r\n"
						+ "                <p>No diagrams generated. For diagram generation, please select more than one CorC diagram</p>\r\n" + "            </div>";
			} else if (numberOfDiagrams > 1) {
				
				for (IFile file : selectedDiagramFiles) {
					diagramName = file.getName().substring(0, file.getName().indexOf(".diagram"));
					for (StatisticsEntry entry : entries) {
						if (entry.getMapping().getCorcDiagramName().equals(diagramName)) {
							entriesPerDiagram.add(entry);
						}
					}
					placeholderPlainStatistics = placeholderPlainStatistics + "<h3 class\"text\">Diagram: " + diagramName + "</h3>";
					createPlaceholderPlainStringForOneDiagram(diagramName, entriesPerDiagram);
					entriesPerDiagram = new LinkedList<StatisticsEntry>();
				}
				
				RHelper helper = new RHelper();
				String pathToPNG = helper.generatePNG("statistics-png", entries);
				String pathToPDF = helper.generatePDF("statistics-pdf", entries);
				
//				helper.setStatisticsFileStringForDiagrams(entries);
//				List<String> diagramPaths = new LinkedList<String>();
				if(pathToPDF != null) {
					placeholderGeneratedDiagram += "<a class=\"text\" href=\" "+ pathToPDF + "\">Open as PDF</a>\r\n<br>\r\n";
				}
				
				if(pathToPNG != null) {
					placeholderGeneratedDiagram += "<img class=\"\" src=\"" + pathToPNG + "\">\r\n";
				}
				else {
					URL errorImageURI = Platform.getBundle("de.tu_bs.cs.isf.cbc.statistics.ui").getEntry("icons/how-to-environment-R.png");
					try {
						pathToPNG = new File(FileLocator.resolve(errorImageURI).toURI()).getAbsolutePath();
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}

					placeholderGeneratedDiagram += "<p>Cannot generate diagrams, probably because R is not installed.<br><strong>Please install R</strong> and add it to your operating system's environment path:</p>";
					placeholderGeneratedDiagram += "<img class=\"\" src=\"" + pathToPNG + "\">\r\n";
				}
			}
		}
//		TODO: abfangen keine entries

		String htmlStyleTag = "<style>\r\n" + "        body{\r\n" + "            margin: 0;\r\n"
				+ "            padding: 0;\r\n" + "        }\r\n" + "        .container{\r\n" + "        }\r\n"
				+ "        .text{\r\n" + "            font-family: sans-serif;\r\n" + "        }\r\n"
				+ "        .block{\r\n" + "            margin-left: 25px;\r\n" + "            margin-bottom: 45px;\r\n"
				+ "        }\r\n" + "        .horizontal-line{\r\n" + "            height: 5px;\r\n"
				+ "            background-color: #f3f3f3;\r\n" + "            border: none;\r\n" + "        }\r\n"
				+ "        .inner-box{\r\n" + "            padding: 10px;\r\n" + "        }\r\n"
				+ "        .left-margin-class{\r\n" + "            margin-left: 20px;\r\n" + "        }\r\n"
				+ "    </style>";

		return "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
				+ "    <title>WebCorCStatistics Viewer</title>\r\n" + "\r\n" + htmlStyleTag + "</head>\r\n"
				+ "<body>\r\n" + "    <div class=\"container\">\r\n" + "        <div class=\"inner-box\">\r\n"
				+ "            <h2 class=\"text\">Plain Statistics</h2>\r\n"
				+ "            <div class=\"text block\">\r\n" + "                <p>" + placeholderPlainStatistics
				+ "</p>\r\n" + "            </div>\r\n" + "        </div>\r\n"
				+ "        <hr class=\"horizontal-line\">\r\n" + "        <div class=\"inner-box\">\r\n"
				+ "            <h2 class=\"text\">Generated Diagram</h2>\r\n"
//				+ "            <img class=\"\" src=\"file:///D:/Uni/Bachelorarbeit/HTMLTemplateCode/test.png\">\r\n"
				+ placeholderGeneratedDiagram + "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
	}
	
	private void createConfigName(String config) {
		/*for (int i = 0; i < config.length()-1; i++) {
			if (Character.isLowerCase(config.charAt(i)) && Character.isUpperCase(config.charAt(i+1))) {
				config = config.substring(0, i+1) + ", " + config.substring(i+1, config.length());
			}
		}
		config = "[" + config + "]"; */
		placeholderPlainStatistics = placeholderPlainStatistics + "<h2 class\"text\" style=\"color:rgb(10,10,200);\">Configuration: " + config + "</h2>";
	}

	private void createPlaceholderPlainStringForOneDiagram(String diagramName,
			List<StatisticsEntry> entriesPerDiagram) {

		//placeholderPlainStatistics = placeholderPlainStatistics + "<h3 class\"text\">Diagram: " + diagramName + "</h3>";

		if (entriesPerDiagram.isEmpty()) {
			placeholderPlainStatistics = placeholderPlainStatistics
					+ "<p class= \"left-margin-class\">No data found. Please generate statistics by verifying statements.</p>";
		} else {
			float totalAutomodeTime = 0;
			// TODO: sort entriesPerDiagram: alphabetic names 
			for (StatisticsEntry entry : entriesPerDiagram) {

				String KeYFile = entry.getMapping().getKeyFilePath();
				KeYFile = KeYFile.substring(KeYFile.lastIndexOf(File.separator) + 1, KeYFile.length());
				
				placeholderPlainStatistics = placeholderPlainStatistics + "<div class=\"left-margin-class\">"
						+ "<h4 class=\"text\">KeY file: " + KeYFile + "</h4>" + "<p class= \"left-margin-class\">"
						+ "Automode Time: " + entry.getData().getAutoModeTimeInMillis() + "ms" + lineBreak
						+ "Number of Branches: " + entry.getData().getNumberOfBranches() + lineBreak
						+ "Number of Nodes: " + entry.getData().getNumberOfNodes() + lineBreak
						+ "total Rule Applications: " + entry.getData().getTotalRuleApps() + lineBreak
						+ "Time per Step: " + entry.getData().getTimePerStepInMillis() + "ms" + lineBreak + "Time: "
						+ entry.getData().getTimeInMillis() + "ms" + lineBreak
						+ "Successfully proven: " + entry.getData().isIsProven() + "</p>" + "</div>";
				totalAutomodeTime = totalAutomodeTime + entry.getData().getAutoModeTimeInMillis();
			}
			placeholderPlainStatistics = placeholderPlainStatistics + "Total automode time needed: " + totalAutomodeTime + "ms" 
			+ lineBreak			+ lineBreak			+ lineBreak;
		}
	}

	public void setConfigView(boolean configView) {
		this.configView = configView;
	}

}
