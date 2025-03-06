package de.tu_bs.cs.isf.cbc.parser;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.tu_bs.cs.isf.cbc.parser.data.ClassSubtypes;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;

public class JavaClasses {

	private static SimpleDateFormat df = new SimpleDateFormat();

	private static Map<String, Map<String, JavaClass>> javaClassesPerProject = new ConcurrentHashMap<String, Map<String, JavaClass>>();

	private static final PropertyChangeSupport pcs = new PropertyChangeSupport(javaClassesPerProject);

	public static void refreshJavaClassesForProject(final String projectName, Map<String, JavaClass> javaClasses) {
		final int oldSize = javaClassesPerProject.size();
		if (javaClassesPerProject.get(projectName) != null) {
			final Map<String, JavaClass> currentMap = javaClassesPerProject.get(projectName);
			currentMap.putAll(javaClasses);
		} else {
			javaClassesPerProject.put(projectName, javaClasses);
		}

		pcs.firePropertyChange("classMapChanged", false, true);

		// printForDebug();
	}

	public static void printForDebug() {
		javaClassesPerProject.forEach((projectName, classesForProject) -> {
			System.out.println("Information for project " + projectName);
			classesForProject.forEach((className, classInfo) -> {
				System.out.println("\t* Information for class " + className);

				System.out.println("\tClass name: " + classInfo.getClassName());
				System.out.println("\tSource file: " + classInfo.getSourceFile());
				System.out.println("\tParse time: " + df.format(classInfo.getParseTime()) + " (Milliseconds: "
						+ classInfo.getParseTime() + ")");
				System.out.println();
				final ClassSubtypes subtypes = classInfo.getSubtypes();
				if (!subtypes.getExtendedTypes().isEmpty() || !subtypes.getImplementedTypes().isEmpty()) {
					System.out.println("\tSubtypes:");
					subtypes.getImplementedTypes().forEach(i -> System.out.println("\t\t- implements Interface: " + i));
					subtypes.getExtendedTypes().forEach(e -> System.out.println("\t\t- extends Class: " + e));
					System.out.println();
				}
				final List<IFieldOrMethod> fields = classInfo.getFieldsAndMethods();
				if (!fields.isEmpty()) {
					System.out.println("\tFields and Methods: ");
					fields.forEach(f -> System.out
							.println("\t - " + (f.getType().equals(IFieldOrMethod.Type.FIELD) ? "Field" : "Method")
									+ " declaration: " + f.toString()));
					System.out.println();
				}
			});
		});
	}

	public static Map<String, JavaClass> getJavaClassesForProject(final String projectName) {
		return javaClassesPerProject.get(projectName);
	}

	public static Map<String, Map<String, JavaClass>> getJavaClasses() {
		return javaClassesPerProject;
	}

	public static void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public static void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	/**
	 * Determines if type at runtime is a subtype of the signature definition type
	 * 
	 * @param runtimeParameterType
	 *            Name of type at runtime
	 * @param signatureParameterType
	 *            Name of type at parameter definition
	 * 
	 * @return True if the runtime parameter is matching the signature parameter,
	 *         i.e. the runtime parameter is a decendant of the signature definition
	 *         type
	 */
	public static Boolean isRuntimeParameterMatchingSignature(String runtimeParameterType,
			String signatureParameterType, final String projectName) {
		final Map<String, JavaClass> javaClassesForProject = getJavaClassesForProject(projectName);
		final JavaClass javaClass = getClassIgnoringGeneric(javaClassesForProject, signatureParameterType);

		if (javaClass == null) {
			// Workaround for literal expressions
			if (!signatureParameterType.matches("boolean|char|double|int|long|Object|String")) {
				System.out.println("Java class " + signatureParameterType + " is not present.");
				return false;
			}
		}

		if (runtimeParameterType.equals(signatureParameterType)) {
			return true;
		}

		final List<String> subtypes = new ArrayList<>();
		subtypes.addAll(javaClass.getSubtypes().getImplementedTypes());
		subtypes.addAll(javaClass.getSubtypes().getExtendedTypes());

		System.out.println(
				"Checking if " + runtimeParameterType + " is a subtype of the following types: " + subtypes.toString());

		if (subtypes.contains(runtimeParameterType)) {
			return true;
		} else {
			System.out.println("Need to dive in deeper.");
			for (final String subtype : subtypes) {
				final Boolean runtimeParameterMatchingSignature = isRuntimeParameterMatchingSignature(
						runtimeParameterType, subtype, projectName);
				if (runtimeParameterMatchingSignature) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Get a java class by name ignoring an optional generic modifier
	 * 
	 * @param javaClassesForProject
	 *            a map of java classes for a project
	 * @param clazzName
	 *            the desired class name (containing an optional generic)
	 * 
	 * @return the desired java class
	 */
	public static JavaClass getClassIgnoringGeneric(Map<String, JavaClass> javaClassesForProject, String clazzName) {
		return javaClassesForProject.get(clazzName.contains("<") // remove optional generic here
				? clazzName.substring(0, clazzName.indexOf('<'))
				: clazzName);
	}
}
