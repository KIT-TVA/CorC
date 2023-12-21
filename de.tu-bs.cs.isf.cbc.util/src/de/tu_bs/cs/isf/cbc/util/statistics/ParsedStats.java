package de.tu_bs.cs.isf.cbc.util.statistics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ParsedStats {
	public int nodes;
	public int autoModeTimeInMillis;
	public int timeInMillis;
	public float timePerStepInMillis;
	public int branches;
	public int totalRuleApps;
	public Date date;
	public boolean isProven;
	
	private final String fileContent;

	public ParsedStats(String fileContent) throws ParseException {
		this.fileContent = fileContent;
		parse();
	}
	
	private void parse() throws ParseException {
		if (!hasData()) {
			throw new ParseException("", 0);
		}
		var dataBlock = parseDataBlock();
		parseNodes(dataBlock);
		parseAutoModeTime(dataBlock);
		parseTime(dataBlock);
		parseTimePerStep(dataBlock);
		parseBranches(dataBlock);
		parseTotalRuleApps(dataBlock);
		parseDate();
		parseProven(dataBlock);
	}

	private boolean hasData() {
		return fileContent.contains("\\proof");
	}
	
	private String parseDataBlock() {
		var data = fileContent.substring(fileContent.indexOf("\\proof"), fileContent.length());
		data = data.substring(data.indexOf("{") + 1, data.lastIndexOf("}"));
		return data;
	}
	
	private void parseNodes(String dataBlock) {
		dataBlock = dataBlock.substring(dataBlock.indexOf("autoModeTime"), dataBlock.lastIndexOf("))"));
		dataBlock = dataBlock.substring(dataBlock.indexOf("\n")+1, dataBlock.length()).trim();
		this.nodes = (int)dataBlock.chars().filter(c -> c == '\n').count() + 1; 
	}
	
	private void parseTime(String dataBlock) {
		this.timeInMillis = this.autoModeTimeInMillis;
	}
	
	private void parseTimePerStep(String dataBlock) {
		this.timePerStepInMillis = (float)this.autoModeTimeInMillis / (float)(this.nodes - 1);
	}

	private void parseAutoModeTime(String dataBlock) {
		var autoMode = dataBlock.substring(dataBlock.indexOf("autoModeTime"), dataBlock.length());
		autoMode = autoMode.substring(autoMode.indexOf("\"")+1, autoMode.indexOf(")"));
		autoMode = autoMode.substring(0, autoMode.indexOf("\""));
		this.autoModeTimeInMillis = Integer.parseInt(autoMode);
	}
	
	private void parseBranches(String dataBlock) {
		this.branches = countWord("branch", dataBlock) != 1 ? countWord("branch", dataBlock) - 1 : 1;
	}
	
	private void parseTotalRuleApps(String dataBlock) {
		this.totalRuleApps = countWord("rule", dataBlock);
	}
	
	private void parseDate() throws ParseException {
		String dateStr = fileContent.substring(fileContent.indexOf("{") + 1, fileContent.indexOf("}"));
		dateStr = dateStr.substring(dateStr.lastIndexOf("#") + 1, dateStr.indexOf("[")).trim();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		this.date = dateFormat.parse(dateStr); 
	}
	
	private void parseProven(String dataBlock) {
		this.isProven = this.branches == countWord("closeTrue", dataBlock) + countWord("closeFalse", dataBlock);
	}
	
	private int countWord(String word, String data) {
		int count = 0;
		while (data.contains(word)) {
			count++;
			data = data.replaceFirst(word, "");
		}
		return count;
	}
}
