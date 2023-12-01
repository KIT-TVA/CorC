package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.exceptions.FeatureCallerException;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public final class FeatureCaller {
	public static String FEATURE_FOLDER = "features";
	
	private static FeatureCaller instance = new FeatureCaller();
	
	public static FeatureCaller getInstance() {
		return instance;
	}

	public String getCallingClass(URI uri)	{
		// Assumption: There are three cases: SPL, OO or non-class
		String className = getClassIfSPL(uri); 
		return className.isEmpty() ? getClassIfOO(uri) : className;
	}
	
	private String getClassIfSPL(URI uri) {
		for (int i = 0; i < uri.segmentCount(); i++) {
			if (uri.segment(i).equals(FEATURE_FOLDER)) {
				return uri.segment(i+2);
			}
		}
		return "";
	}
	
	private String getClassIfOO(URI uri) {
		String path = uri.toPlatformString(false);
		if (path == null) {
			return "";
		}
		path = path.replaceAll("\\\\", "/");
		String potentialClassName = path.substring(path.indexOf("src") + 4, path.length());
		potentialClassName = potentialClassName.substring(0, potentialClassName.indexOf("/"));
		IProject project = FileUtil.getProject(uri);
		for (Resource r : FileUtil.getCbCClasses(project)) {
			ModelClass clazz = (ModelClass)r.getContents().get(0);
			if (clazz.getName().equals(potentialClassName)) {
				return potentialClassName;
			}
		}
		return "";
	}
	
	public String getCallingFeature(URI uri) {
		for (int i = 0; i < uri.segmentCount(); i++) {
			if (uri.segment(i).equals(FEATURE_FOLDER)) {
				return uri.segment(i+1);
			}
		}
		return "";
	}
	
	public String getCallingMethod(URI uri) {
		String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		if (callingMethod.contains("Mutant")) {
			callingMethod = callingMethod.substring(0, callingMethod.lastIndexOf("Mutant"));
		}
		return callingMethod;
	}
}
