package diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.exceptions.DiagnosticsException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

public class DataCollector {
	public static final String EXPR_DELIM = "\n";
	public static final String TEST_SYMBOL = "[TESTCASE]";
	public static final String PATHS_SYMBOL  = "[PATHS]";
	public static final String DATA_DELIM = "#";
	public static final int EXPR_SIZE = 3;
	public static final String FOLDER_APPENDIX = "_diagnostics";
	private final URI projectPath;
	private final String folderName;
	private DiagnosticsData data;
	

	public DataCollector(final URI projectPath, final String diagramName) {
		this.projectPath = projectPath;
		this.folderName = diagramName + "_diagnostics";
		this.data = null;
	}
	
	public DataCollector(final URI projectPath, final DataType type, final String diagramName) throws DiagnosticsException {
		this.projectPath = projectPath;
		this.folderName = diagramName + "_diagnostics";
		this.setType(type);
	}
	
	public void finish() {
		FileHandler.saveDiagnosticData(projectPath, this);
	}
	
	private void initData(DataType type) {
		if (type == DataType.PATH) {
			data = new TestStatementData(this.projectPath);
		} else if (type == DataType.TESTCASE) {
			data = new TestGeneratorData(this.projectPath);
		} else {
			//throw new DiagnosticsException(ExceptionMessages.UNKNOWN_DATA_TYPE);
		}
	}
	
	public boolean addData(final String pathName, final float executionTime) {
		return addData("", pathName, executionTime);
	}

	public boolean addData(final String configName, final String pathName, final float executionTime) {
		if (data == null) {
			return false;
		}
		if (configName.isEmpty()) {
			return addData(this.getType(), null, pathName, executionTime);
		} else {
			return addData(this.getType(), configName, pathName, executionTime);
		}
	}
	
	public boolean addData(final DataType type, final String pathName, final float executionTime) {
		return addData(type, null, pathName, executionTime);
	}
	
	public boolean addData(final DataType type, final String configName, final String pathName, final float executionTime) {
		if (pathName == null) {
			return false;
		}
		if (data == null) {
			initData(type);
		}
		if (configName == null) {
			data.add(new TimedObject(pathName, executionTime));
		} else {
			data.add(configName, new TimedObject(pathName, executionTime));
		}
		return true;
	}
	
	public Collection<String> getConfigNames() {
		return this.data.getConfigNames();
	}

	public DataType getType() {
		if (data instanceof TestStatementData) {
			return DataType.PATH;
		} else if (data instanceof TestGeneratorData) {
			return DataType.TESTCASE;
		} else {
			return DataType.NONE;
		}
	}
	
	public void setType(DataType type) throws DiagnosticsException {
		if (data == null) {
			initData(type);
		}
	}
	
	/**
	 * Creates the string representation for the paths and their execution times. 
	 * @return String representation of the execution times of paths
	 */
	public String getPathsRep() {
		return getPathsRep(data.getData());
	}
	
	private String getPathsRep(final List<TimedObject> timedObjects) {
		if (timedObjects == null) {
			return "";
		}

		String rep = PATHS_SYMBOL + "\n";
		int counter = 0;
		for (TimedObject p : timedObjects) {
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
		if (config == null) {
			return "";
		}
		return getPathsRep(data.getConfigData(config));
	}
	
	public String getTestCaseRep() {
		String rep = TEST_SYMBOL + "\n";
		int counter = 0;
		for (var testCase : data.getData()) {
			rep += counter + DATA_DELIM + testCase.getName() + DATA_DELIM + testCase.getExecutionTime() + "\n";
			counter++;
		}
		return rep;
	}

	public String getFolderName() {
		return this.folderName;
	}
}
