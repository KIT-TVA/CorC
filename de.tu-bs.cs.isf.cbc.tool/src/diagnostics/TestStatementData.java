package diagnostics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;

/**
 * Generates all needed data regarding statement testing.
 * @author Fynn
 */
public class TestStatementData implements DiagnosticsData {
	private final LinkedHashMap<String, List<TimedObject>> configPaths;
	private final List<TimedObject> paths;
	private final URI diagramUri;
	
	public TestStatementData(final URI diagramUri) {
		this.configPaths = new LinkedHashMap<String, List<TimedObject>>();
		this.paths = new ArrayList<TimedObject>();
		this.diagramUri = diagramUri;
	}

	@Override
	public boolean isSplData() {
		return FileHandler.getInstance().isSPL(diagramUri); 
	}

	@Override
	public List<TimedObject> getConfigData(String configName) {
		return this.configPaths.get(configName);
	}
	@Override
	public List<TimedObject> getData() {
		return this.paths;
	}

	@Override
	public boolean add(TimedObject tObj) {
		if (tObj == null) {
			return false;
		}
		for (var path : paths) {
			if (path.getName().equals(tObj.getName())) {
				return false;
			}
		}
		paths.add(tObj);
		return true;
	}

	@Override
	public boolean add(String configName, TimedObject tObj) {
		if (configName == null || tObj == null) {
			return false;
		}
		for (var config : configPaths.keySet()) {
			if (config.equals(configName)) {
				configPaths.get(config).add(tObj);
				return true;
			}
		}
		configPaths.put(configName, new ArrayList<TimedObject>());
		configPaths.get(configName).add(tObj);
		return true;
	}

	@Override
	public List<String> getConfigNames() {
		return configPaths.keySet().stream().toList(); 
	}
}
