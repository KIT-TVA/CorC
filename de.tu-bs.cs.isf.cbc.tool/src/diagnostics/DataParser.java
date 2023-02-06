package diagnostics;

import org.eclipse.emf.common.util.URI;

import de.tu_bs.cs.isf.cbc.tool.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.tool.helper.Features;
import de.tu_bs.cs.isf.cbc.tool.helper.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.tool.exceptions.DiagnosticsException;

public class DataParser {
	private final Features features;

	public DataParser(final URI projectPath, final String diagnosticsFolderName) throws DiagnosticsException {
		final var project = FileUtil.getProject(projectPath);
		final var diagnosticsFolder = project.getFolder(diagnosticsFolderName);
		if (!diagnosticsFolder.exists()) {
			throw new DiagnosticsException(ExceptionMessages.diagnosticsFolderNotFound(diagnosticsFolder));
		}
		if (FileHandler.isSPL(projectPath)) {
			features = new Features(projectPath);
		} else {
			features = null;
		}
		//readDiagnosticsData(diagnosticsFolder);
	}
}