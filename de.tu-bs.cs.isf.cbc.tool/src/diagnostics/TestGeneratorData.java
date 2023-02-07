package diagnostics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

/**
 * Generates all needed data regarding test cases generated through the test generator.
 * @author Fynn
 */
public class TestGeneratorData implements DiagnosticsData {
	private final LinkedHashMap<String, List<TimedObject>> configTestCases;
	private final List<TimedObject> testCases;
	private final URI diagramUri;
	
	public TestGeneratorData(final URI diagramUri) {
		this.configTestCases = new LinkedHashMap<String, List<TimedObject>>();
		this.testCases = new ArrayList<TimedObject>();
		this.diagramUri = diagramUri;
	}

	@Override
	public boolean isSplData() {
		return FileHandler.isSPL(diagramUri); 
	}

	@Override
	public List<TimedObject> getConfigData(String configName) {
		return this.configTestCases.get(configName);
	}
	@Override
	public List<TimedObject> getData() {
		return this.testCases;
	}

	@Override
	public boolean add(TimedObject tObj) {
		if (tObj == null) {
			return false;
		}
		for (var testCase : testCases) {
			if (testCase.getName().equals(tObj.getName())) {
				return false;
			}
		}
		testCases.add(tObj);
		return true;
	}

	@Override
	public boolean add(String testCaseName, TimedObject tObj) {
		// TODO: adjust to SPLs.
		return add(tObj);
	}

	@Override
	public List<String> getConfigNames() {
		return configTestCases.keySet().stream().toList();
	}
}
