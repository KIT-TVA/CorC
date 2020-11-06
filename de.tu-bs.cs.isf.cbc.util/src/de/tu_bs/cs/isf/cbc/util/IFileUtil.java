package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.util.List;

public interface IFileUtil {

	public File getClassFile(String className);

	public List<String> readFileInList(String path);

	public File writeFile(String problem, String location, int numberFile, boolean override);

	public String getProjectLocation(String uri);

	public String generateComposedClass(String project, String composedClassName, String className, String content, String contentOriginal);

	public String getLastSegment(String uri);
}
