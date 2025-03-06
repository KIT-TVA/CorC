package de.tu_bs.cs.isf.cbc.parser.jobs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.JavaFileParser;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;

public class JavaFileParseJob extends Job {

	private final IFile javaFile;
	private String projectName;

	public JavaFileParseJob(final IFile javaFile, final String projectName) {
		super("Parsing Java file " + javaFile.getName());
		this.javaFile = javaFile;
		this.projectName = projectName;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final JavaFileParser parser = new JavaFileParser();
		try {
			final Map<String, JavaClass> classInformation = parser
					.parseFile(new File(javaFile.getLocation().toOSString()));
			JavaClasses.refreshJavaClassesForProject(this.projectName, classInformation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return Status.OK_STATUS;
	}

}
