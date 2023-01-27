package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;

import de.tu_bs.cs.isf.cbc.tool.helper.Util;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

/**
 * TODO!
 * Tests all possible configurations of a SPL.
 * @author Fynn
 */
public class TestAllStatementsSPL extends TestAllStatements {
	public TestAllStatementsSPL(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Test all statements of the software product line.";
	}

	@Override
	public String getDescription() {
		return "Generates statement tests for every diagram in this software product line.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {	
		try {
			final IProject project = FileUtil.getProjectFromFileInProject(getUri(getDiagram()));
			Console.clear();
			if (project.getNature("de.ovgu.featureide.core.featureProjectNature") == null) {
				Console.println("This diagram is not part of a SPL.");
				return;
			}
			Console.println("[SPL]", blue);
			Console.println("Start testing...\n");
			final long startTime = System.nanoTime();
			var uri = super.getUri(getDiagram());
			String callingClass = uri.segment(uri.segmentCount()-2) + "";
			String callingFeature = uri.segment(uri.segmentCount()-3) + "";
			String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
			String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false, null);
			String configName = "";
			
			for (int i = 0; i < featureConfigs.length; i++) {
				configName = "";
				for (int j = 0; j < featureConfigs[i].length; j++) configName += featureConfigs[i][j];
				
			}
			
			//super.testDiagram(getDiagram());
			
			long endTime = System.nanoTime();
			long duration = (endTime - startTime) / 1000000;
			Console.println("Testing finished.");
			Console.println("Time needed: " + duration + "ms");	
			
			
			throw new NotImplementedException("TestAllStatementsSPL");
		} catch (CoreException e1) {
			e1.printStackTrace();
		} catch(NotImplementedException e2) {
			e2.printStackTrace();
			Console.println(e2.getMessage());
			return;
		}
	}
	
	private void prepareTest(final String projectPath, final String configName) {
		Util.writeToFile(projectPath, configName, "");
	}	
}
