package diagnostics;

import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.exceptions.DiagnosticsException;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.tool.helper.Features;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

/**
 * Parses diagnostic data given a diagram path, if it exits.
 * @author Fynn
 */
public class DataParser {
	private final Features features;
	private final DataCollector dataCollector;

	public DataParser(final URI diagramPath) throws DiagnosticsException {
		if (diagramPath == null) {
			throw new DiagnosticsException(ExceptionMessages.URI_NULL);
		}
		final var project = FileUtil.getProject(diagramPath);
		final var diagnosticsFolderPath = getDiagnosticsFolderPath(diagramPath);
		final var diagnosticsFolder = project.getFolder(diagnosticsFolderPath);
		dataCollector = new DataCollector(diagramPath, extractDiagramName(diagnosticsFolderPath));
		if (!diagnosticsFolder.exists()) {
			throw new DiagnosticsException(ExceptionMessages.diagnosticsFolderNotFound(diagnosticsFolder));
		}
		if (FileHandler.getInstance().isSPL(diagramPath)) {
			features = new Features(diagramPath);
		} else {
			features = null;
		}
		readDiagnosticsData(diagnosticsFolder);
	}
	
	private String getDiagnosticsFolderPath(final URI diagramPath) {
		var segments = diagramPath.segmentsList();
		if (segments == null) {
			return "";
		}
		String diagnosticsFolderPath = "";
		for (int i = 2; i < segments.size() - 1; i++) {
			diagnosticsFolderPath += segments.get(i) + "/";
		}
		diagnosticsFolderPath += segments.get(segments.size() - 1).split("\\.")[0] + DataCollector.FOLDER_APPENDIX;
		return diagnosticsFolderPath;
	}
	
	public DataCollector getDataCollector() {
		return this.dataCollector;
	}
	
	private void readDiagnosticsData(final IFolder diagnosticsFolder) {
		try {
			var files = FileUtil.getFiles(diagnosticsFolder, "");
			for (var file : files) {
				final var configName = file.getFullPath().segment(file.getFullPath().segmentCount()-2);
				var fileContent = new String(file.getContents(true).readAllBytes());				
				parseContent(configName, fileContent);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiagnosticsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseContent(final String configName, final String content) throws DiagnosticsException {
		final var lines = content.split(DataCollector.EXPR_DELIM);
		final var symbol = lines[0];
		if (symbol.equals(DataCollector.PATHS_SYMBOL)) {
			dataCollector.setType(DataType.PATH);
		} else if (symbol.equals(DataCollector.TEST_SYMBOL)) {
			dataCollector.setType(DataType.TESTCASE);
		} else {
			throw new DiagnosticsException(ExceptionMessages.invalidDiagnosticsSymbol(symbol));  
		}
		for (int i = 1; i < lines.length; i++) {
			var data = lines[i].split(DataCollector.DATA_DELIM);
			if (data.length != DataCollector.EXPR_SIZE) {
				throw new DiagnosticsException(ExceptionMessages.WRONG_DIAGNOSTICS_SYNTAX);
			}
			if (features == null) {
				dataCollector.addData(data[1], Float.parseFloat(data[2]));
			} else {
				dataCollector.addData(configName, data[1], Float.parseFloat(data[2]));
			}
		}
	}
	
	private String extractDiagramName(final String folderPath) {
		final var segments = folderPath.split("\\/");
		return segments[segments.length-1];
	}
}