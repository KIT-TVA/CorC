package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.util.List;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;

public interface IFileUtil {

	public File getSrcFile(String className);
	
	public File getClassFile(String className);
	
	public File getSrcFile(String className);

    public List<String> readFileInList(String path);

    public File writeFile(String proofType, String problem, String helper, String location, boolean override,
	    AbstractStatement statement, String subProofName, boolean proofExists, String configName,
	    List<String> originalPrePost);

    public String getProjectLocation(String uri);

    public String generateComposedClass(String project, String composedClassName, String className, String content,
	    String contentOriginal);

    public String getLocationString(String uri);

    public List<Predicate> readPredicates(String filePath);
}
