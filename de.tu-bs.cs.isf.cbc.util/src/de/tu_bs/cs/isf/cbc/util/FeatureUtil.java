package de.tu_bs.cs.isf.cbc.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.exceptions.FeatureCallerException;

public final class FeatureUtil {
	public static String FEATURE_FOLDER = "features";
	
	private static FeatureUtil instance = new FeatureUtil();
	
	public static FeatureUtil getInstance() {
		return instance;
	}

	public String getCallingClass(URI uri)	{
		// Assumption: There are three cases: SPL, OO or non-class
		String className = getClassIfSPL(uri); 
		return className.isEmpty() ? getClassIfOO(uri) : className;
	}
	
	public String getCallingClass(String path) {
		return getCallingClass(URI.createPlatformResourceURI(path));
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
		if (potentialClassName.indexOf("/") == -1) {
			return "";
		}
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
	
	public String getCallingFeature(String path) {
		path = path.replaceAll("\\\\", "/");
		var segments = path.split("/");
		for (int i = 0; i < segments.length; i++) {
			if (segments[i].equals(FEATURE_FOLDER)) {
				return segments[i+1];
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
