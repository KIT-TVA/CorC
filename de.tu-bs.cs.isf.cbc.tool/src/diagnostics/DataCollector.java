package diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

public class DataCollector {
	public static final String EXPR_DELIM = "\n";
	public static final String TEST_SYMBOL = "[TESTCASE]";
	public static final String PATHS_SYMBOL  = "[PATHS]";
	public static final String DATA_DELIM = "#";
	public static final int EXPR_SIZE = 3;
	public static final String FOLDER_APPENDIX = "_diagnostics";
	private final URI projectPath;
	private final List<Path> paths;
	private final LinkedHashMap<String, List<Path>> configPaths;
	private final LinkedHashMap<String, Float> testCases;
	private DataType dataType;
	private final String folderName;

	public DataCollector(final URI projectPath, final String diagramName) {
		this.projectPath = projectPath;
		this.paths = new ArrayList<Path>();
		this.configPaths = new LinkedHashMap<String, List<Path>>();
		this.testCases = new LinkedHashMap<String, Float>();
		this.dataType = DataType.NONE;
		this.folderName = diagramName + "_diagnostics";
	}
	
	public void finish() {
		FileHandler.saveDiagnosticData(projectPath, this);
	}
		
	public boolean addPathTime(final String pathName, final float executionTime) {
		this.dataType = DataType.PATH;
		if (pathName == null) {
			return false;
		}
		for (var path : paths) {
			if (path.getName().equals(pathName)) {
				return false;
			}
		}
		paths.add(new Path(pathName, executionTime));
		return true;
	}
	
	public boolean addConfigPathTime(final String configName, final String pathName, final float executionTime) {
		this.dataType = DataType.PATH;
		if (configName == null || pathName == null) {
			return false;
		}
		for (var config : configPaths.keySet()) {
			if (config.equals(configName)) {
				configPaths.get(config).add(new Path(pathName, executionTime));
				return true;
			}
		}
		configPaths.put(configName, new ArrayList<Path>());
		configPaths.get(configName).add(new Path(pathName, executionTime));
		return true;
	}
	
	public boolean addTestCaseTime(final String testCaseName, final float executionTime) {
		this.dataType = DataType.TESTCASE;
		if (testCaseName == null) {
			return false;
		}
		this.testCases.put(testCaseName, executionTime);
		return true;
	}
	
	public boolean addConfigTestCaseTime(final String configName, final String testCaseName, final float executionTime) {
		// TODO: adjust to SPLs.
		return addTestCaseTime(testCaseName, executionTime);
	}
		
	
	public Collection<String> getConfigNames() {
		return this.configPaths.keySet();
	}

	public DataType getType() {
		return this.dataType;
	}
	
	public void setType(DataType type) {
		this.dataType = type;
	}
	
	/**
	 * Creates the string representation for the paths and their execution times. 
	 * @return String representation of the execution times of paths
	 */
	public String getPathsRep() {
		return getPathsRep(this.paths);
	}
	
	private String getPathsRep(final List<Path> paths) {
		if (paths == null) {
			return "";
		}

		String rep = PATHS_SYMBOL + "\n";
		int counter = 0;
		for (Path p : paths) {
			rep += counter + DATA_DELIM + p.getName() + DATA_DELIM + p.getExecutionTime() + "\n";
			counter++;
		}
		return rep;
	}

	/**
	 * Creates the string representation for the configuration and it's paths and their execution times. 
	 * @param config
	 * @return
	 */
	public String getConfigRep(final String config) {
		if (this.configPaths == null || config == null) {
			return "";
		}
		return getPathsRep(this.configPaths.get(config));
	}
	
	public String getTestCaseRep() {
		if (this.testCases == null) {
			return "";
		}
		String rep = TEST_SYMBOL + "\n";
		int counter = 0;
		for (var testCase : testCases.keySet()) {
			rep += counter + DATA_DELIM + testCase + DATA_DELIM + testCases.get(testCase) + "\n";
			counter++;
		}
		return rep;
	}

	public String getFolderName() {
		return this.folderName;
	}
}
