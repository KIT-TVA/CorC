package diagnostics;

import java.util.List;

public interface DiagnosticsData {
	public boolean isSplData();
	public List<TimedObject> getConfigData(final String configName);
	public List<TimedObject> getData();
	public List<String> getConfigNames();
	public boolean add(final TimedObject tObj);
	public boolean add(final String configName, final TimedObject tObj);
}
