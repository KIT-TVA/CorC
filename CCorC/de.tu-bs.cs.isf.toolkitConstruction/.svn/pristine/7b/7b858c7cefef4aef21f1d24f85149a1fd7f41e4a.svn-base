package de.tu_bs.cs.isf.toolkit.support;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jdt.core.IPackageFragment;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies.Decision;
import de.tu_bs.cs.isf.toolkit.support.output.Console;
import de.tu_bs.cs.isf.toolkit.support.output.ManageProject;
import de.tubs.carsten.robot.monkey.RobotMonkey;
import de.tubs.carsten.robot.util.SimianResult;

/**
 * Class to start and execute the toolkit construction
 * @author Tobias
 *
 */
public class ToolkitConstruction {
	
	/**
	 * list of all algorithm objects of the taxonomy
	 */
	private static List<ClassObject> objects;
	/**
	 * counter for generic types
	 */
	private static int genericCounter;
	/**
	 * initial value for the generic counter: T
	 */
	private static final int START_VALUE_FOR_GENERIC_COUNTER = 84;
	
	private static final boolean PROVE_WITH_KEY = false;

	/**
	 * Start of the toolkit construction
	 * reads the taxonomy and checks all elements
	 * in the end the toolkit is created
	 * @param uri	the uri of the taxonomy
	 * @param monitor	progress monitor
	 */
	public static void runToolkitGeneration(URI uri, IProgressMonitor monitor) {
		Taxonomy taxonomy = readTaxonomy(uri);
		Algorithm parentAlgorithm = null;
		objects = new ArrayList<>();
		genericCounter = START_VALUE_FOR_GENERIC_COUNTER;
		for (Algorithm algo : taxonomy.getAlgorithms()) {
			if (algo.getParentAlgorithms().isEmpty()) {
				parentAlgorithm = algo;
				ClassObject object = new ClassObject(algo.getMethods(), algo.getDataStructures(), null, algo.getName(), algo.isAbstract(), algo.getImports());
				objects.add(object);
			} else {
				ClassObject object = new ClassObject(algo.getMethods(), algo.getDataStructures(), algo.getParentAlgorithms().get(0).getName(), algo.getName(), algo.isAbstract(), algo.getImports());
				objects.add(object);
			}
		}
		Console.clear();
		checkTaxonomy(parentAlgorithm, monitor);
		if (monitor.isCanceled()) {
			return;
		}
		
		IPackageFragment pack = ManageProject.createProject(taxonomy.getName().toLowerCase());
		List<IFile> files = new ArrayList<>();
		for (ClassObject object : objects) {
			String content = object.createContentForClass();
			IFile newJavaFile = ManageProject.createJavaFile(pack, object.getName().replaceAll(" ", ""), content);
			files.add(newJavaFile);
		}
		
		if (PROVE_WITH_KEY) {
			proveWithKey(files);
		}
	}

	/**
	 * Reads the taxonomy from an uri
	 * @param uri	the uri of the taxonomy
	 * @return	the taxonomy object
	 */
	private static Taxonomy readTaxonomy(URI uri) {
		TaxonomyPackage.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("taxonomy", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.getResource(uri, true);
		Taxonomy taxonomy = (Taxonomy) r.getContents().get(0);
		return taxonomy;
	}

	/**
	 * traverses the taxonomy and compares the elements recursively
	 * @param parentAlgorithm	the parent algorithm
	 * @param monitor	the progress monitor
	 */
	private static void checkTaxonomy(Algorithm parentAlgorithm, IProgressMonitor monitor) {
		for (Algorithm childAlgorithm : parentAlgorithm.getChildAlgorithms()) {
			checkTaxonomy(childAlgorithm, monitor);
			
			if (monitor.isCanceled()) {
				return;
			}
			compareAlgorithms(parentAlgorithm, childAlgorithm);
		}
		for (Algorithm childAlgorithm : parentAlgorithm.getChildAlgorithms()) {
			for (Algorithm childAlgorithm2 : parentAlgorithm.getChildAlgorithms()) {
				if (!childAlgorithm.equals(childAlgorithm2)) {
					if (monitor.isCanceled()) {
						return;
					}
					compareChildAlgorithms(childAlgorithm, childAlgorithm2);
				}
			}
		}
	}

	/**
	 * Compares a parent with its child algorithm
	 * Compares all methods and alters the methods if necessary (e.g. inheritance,...)
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void compareAlgorithms(Algorithm parentAlgorithm, Algorithm childAlgorithm) {
//		boolean inheritance = true;
		for (DataStructure data : parentAlgorithm.getDataStructures()) {
//			boolean notFound = true;
			for (DataStructure data2 : childAlgorithm.getDataStructures()) {
				if (data.getName().equals(data2.getName()) && data.getDataType().getName().equals(data2.getDataType().getName())) {
//					notFound = false;
					ManageProject.addToInfoFileContent("Same data structure found: " + data.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
					ClassObject object = getClassObjectFromList(childAlgorithm.getName());
					object.getData().remove(data2);
				}
			}
//			if (notFound) {
//				inheritance = false;
//			}
		}
		
		for (Method method : parentAlgorithm.getMethods()) {
			String methodName = method.getName();
			methodName = methodName.substring(0, methodName.indexOf("("));
			for (Method method2 : childAlgorithm.getMethods()) {
				String methodName2 = method2.getName();
				methodName2 = methodName2.substring(0, methodName2.indexOf("("));
				if (methodName.equals(methodName2)) {
					boolean sameTypes = true;
					boolean overloading = false;
					boolean sameNumberOfTypes = false;
					if (method.getDataTypes().size() != method2.getDataTypes().size()) {
						Set<String> parentDataTypes = new HashSet<>();
						Set<String> childDataTypes = new HashSet<>();
						for (DataType type : method.getDataTypes()) {
							parentDataTypes.add(type.getName());
						}
						for (DataType type : method2.getDataTypes()) {
							childDataTypes.add(type.getName());
						}
						if (parentDataTypes.containsAll(childDataTypes)) {
							overloading = true;
						}
						sameTypes = false;
					} else if (method.getDataTypes().size() == method2.getDataTypes().size()) {
						sameNumberOfTypes = true;
						for (int i = 0; i < method.getDataTypes().size(); i++) {
							if (!method.getDataTypes().get(i).getName().equals(method2.getDataTypes().get(i).getName())) {
								sameTypes = false;
							}
						}
					}
					Decision implementation = null;
					if (method.getPseudoCode() != null && method2.getPseudoCode() != null && !method.getPseudoCode().isEmpty() && !method2.getPseudoCode().isEmpty()) {
						implementation = CompareMethodBodies.compareMethodBodies(method.getPseudoCode(), method2.getPseudoCode());
					}
					
					decideForAlgorithms(parentAlgorithm, childAlgorithm, method, method2, sameTypes, overloading,
							sameNumberOfTypes, implementation);
				}
			}
		}
	}

	/**
	 * Decides for the algorithms how they are handled
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 * @param parentMethod		the parent method
	 * @param childMethod		the child method
	 * @param sameTypes			boolean if the types are equal
	 * @param overloading		boolean if overloading is possible
	 * @param sameNumberOfTypes	boolean if the number of types is equal
	 * @param decision			decision for the method bodies
	 */
	private static void decideForAlgorithms(Algorithm parentAlgorithm, Algorithm childAlgorithm, Method parentMethod,
			Method childMethod, boolean sameTypes, boolean overloading, boolean sameNumberOfTypes, Decision decision) {
		if (decision == Decision.INHERIT && sameTypes) {
			ManageProject.addToInfoFileContent("Same method should be inherit: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
			ClassObject object = getClassObjectFromList(childAlgorithm.getName());
			object.getMethods().remove(childMethod);
		} else if (decision == Decision.EXTEND && sameTypes) {
			if (parentMethod.getPseudoCode() != null && !parentMethod.getPseudoCode().contains("abstract")) {
				modifyToImplementExtendWithConditionals(parentMethod, childMethod, parentAlgorithm, childAlgorithm);
			}
			ManageProject.addToInfoFileContent("Parent method can be extended with conditionals: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
		} else if (decision == Decision.EXTENDSUPER && sameTypes) {
			modifyToImplementSuperMethod(parentMethod, childMethod, parentAlgorithm, childAlgorithm);
			ManageProject.addToInfoFileContent("Super method can be called in the child method: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
		} else if (decision == Decision.OVERRIDE && sameTypes) {
			String code = childMethod.getPseudoCode();
			code = "@Override\n" + code;
			childMethod.setPseudoCode(code);
			ManageProject.addToInfoFileContent("Method of parent should be overridden: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
		} else if (decision == Decision.OVERRIDECOND && sameTypes) {
			if (parentMethod.getPseudoCode() != null && !parentMethod.getPseudoCode().contains("abstract")) {
				modifyToImplementExtendWithConditionals2(parentMethod, childMethod, parentAlgorithm, childAlgorithm);
			}
			ManageProject.addToInfoFileContent("Parent method can be extended with conditionals: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
		} else if (overloading) {
			String statement = modifyToImplementOverloadMethod(parentMethod, childMethod, parentAlgorithm, childAlgorithm);
			ManageProject.addToInfoFileContent("Method should be overloaded with default values: " + parentMethod.getName() + "/" + childMethod.getName() + " in " + parentAlgorithm.getName() + "/" + childAlgorithm.getName() + ".\nMaybe call in childMethod: " + statement);
		} else if (!sameTypes && sameNumberOfTypes && parentAlgorithm.isAbstract() && parentMethod.getPseudoCode() != null && parentMethod.getPseudoCode().contains("abstract")) {
			modifyToImplementGenericMethod(parentMethod, childMethod, parentAlgorithm, childAlgorithm);
			ManageProject.addToInfoFileContent("Method should be implemented with generics: " + parentMethod.getName() + " in " + parentAlgorithm.getName() + " and " + childAlgorithm.getName());
		}
	}
	
	/**
	 * Modifies the child method to call the super method
	 * @param parentMethod	the parent/super method
	 * @param childMethod	the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void modifyToImplementSuperMethod(Method parentMethod, Method childMethod, Algorithm parentAlgorithm,
			Algorithm childAlgorithm) {
		List<String> parentMethodTokens = new ArrayList<>();
		List<String> childMethodTokens = new ArrayList<>();
		
		CompareMethodBodies.readBodies(parentMethodTokens, childMethodTokens, parentMethod.getPseudoCode(), childMethod.getPseudoCode(), true);
		String firstStatementOfParent = parentMethodTokens.get(0);
		String lastStatementOfParent = parentMethodTokens.get(parentMethodTokens.size() - 1);
		int indexFirstInChild = childMethod.getPseudoCode().indexOf(firstStatementOfParent);
		int indexLastInChild = childMethod.getPseudoCode().lastIndexOf(lastStatementOfParent);
		String before = childMethod.getPseudoCode().substring(0, indexFirstInChild - 1);
		String after = childMethod.getPseudoCode().substring(indexLastInChild - 1, childMethod.getPseudoCode().length());
		after = after.substring(after.indexOf("\n") + 1, after.length()); //delete first line
		String superCall = "\tsuper." + parentMethod.getName() + ";\n";
		
		childMethod.setPseudoCode(before + superCall + after);
		
	}
	
	/**
	 * Modifies the parent method and adds conditionals
	 * The child method is included into the parent method
	 * Different parts are separated with conditionals (if)
	 * @param parentMethod	the parent method
	 * @param childMethod	the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void modifyToImplementExtendWithConditionals(Method parentMethod, Method childMethod, Algorithm parentAlgorithm,
			Algorithm childAlgorithm) {
		List<String> parentMethodTokens = new ArrayList<>();
		List<String> childMethodTokens = new ArrayList<>();
		List<String> blocks = new ArrayList<>();
		CompareMethodBodies.readBodies(parentMethodTokens, childMethodTokens, parentMethod.getPseudoCode(), childMethod.getPseudoCode(), false);

		boolean contains;
		String nextBlock;
		if (parentMethodTokens.get(0).equals(childMethodTokens.get(0))) {
			contains = true;
			nextBlock = childMethodTokens.get(0);
		} else {
			contains = false;
			nextBlock = "\tif (flag) {\n" + childMethodTokens.get(0);
		}
		for (int i = 1; i < childMethodTokens.size(); i++) {
			if (contains) {
				if (parentMethodTokens.contains(childMethodTokens.get(i))) {
					nextBlock += "\n" + childMethodTokens.get(i);
				} else {
					blocks.add(nextBlock);
					nextBlock = "\tif (flag) {\n" + childMethodTokens.get(i);
					contains = false;
				}
			} else {
				if (!parentMethodTokens.contains(childMethodTokens.get(i))) {
					nextBlock += "\n" + childMethodTokens.get(i);
				} else {
					nextBlock += "\n\t}\n";
					blocks.add(nextBlock);
					nextBlock = childMethodTokens.get(i);
					contains = true;
				}
			}
		}
		blocks.add(nextBlock);
		String parentMethodString = parentMethod.getPseudoCode();
		int begin = parentMethodString.indexOf(")");
		String newParentMethodString = parentMethodString.substring(0, begin);
		if (parentMethod.getDataTypes().isEmpty()) {
			newParentMethodString += "boolean flag) {";
		} else {
			newParentMethodString += ", boolean flag) {";
		}
		for (String blockContent : blocks) {
			newParentMethodString += blockContent;
		}
		newParentMethodString += "\n}";
		
		String childMethodString = childMethod.getPseudoCode();
		begin = childMethodString.indexOf("{");
		String newChildMethodString = "\n\n/*Other possibility: Conditionals for " + childAlgorithm.getName() + "\n" + childMethodString.substring(0, begin);
		newChildMethodString += "{\n\tsuper." + childMethod.getName();
		newChildMethodString = newChildMethodString.substring(0, newChildMethodString.length() - 1);
		if (parentMethod.getDataTypes().isEmpty()) {
			newChildMethodString += "true);\n}\n*/";
		} else {
			newChildMethodString += ", true);\n}\n*/";
		}
		
		parentMethod.setPseudoCode(parentMethod.getPseudoCode() + "\n\n/*Other possibility: Conditionals for " + childAlgorithm.getName() + "\n" + newParentMethodString +"\n*/");
		childMethod.setPseudoCode(childMethod.getPseudoCode() + newChildMethodString);
	}
	
	/**
	 * Modifies the parent method and adds conditionals
	 * The child method is included into the parent method
	 * Different parts are separated with conditionals (if-else)
	 * @param parentMethod	the parent method
	 * @param childMethod	the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void modifyToImplementExtendWithConditionals2(Method parentMethod, Method childMethod, Algorithm parentAlgorithm,
			Algorithm childAlgorithm) {
		List<String> parentMethodTokens = new ArrayList<>();
		List<String> childMethodTokens = new ArrayList<>();
		List<String> blocks = new ArrayList<>();
		CompareMethodBodies.readBodies(parentMethodTokens, childMethodTokens, parentMethod.getPseudoCode(), childMethod.getPseudoCode(), false);

		boolean isComment = false;
		for (Iterator<String> it = parentMethodTokens.iterator(); it.hasNext();) {
			String next = it.next();
			if (next.contains("/*")) {
				it.remove();
				isComment = true;
			} else if (isComment) {
				it.remove();
			} else if (next.contains("*/")) {
				it.remove();
				isComment = false;
			}
			
		}
		
		isComment = false;
		for (Iterator<String> it = childMethodTokens.iterator(); it.hasNext();) {
			String next = it.next();
			if (next.contains("/*")) {
				it.remove();
				isComment = true;
			} else if (isComment) {
				it.remove();
			} else if (next.contains("*/")) {
				it.remove();
				isComment = false;
			}
			
		}
		boolean contains;
		boolean isFromChild;
		String nextBlock;
		int child = 0;
		int parent = 0;
		if (parentMethodTokens.get(parent).equals(childMethodTokens.get(child))) {
			contains = true;
			isFromChild = false;
			nextBlock = childMethodTokens.get(0);
			child++;
			parent++;
		} else if (!parentMethodTokens.contains(childMethodTokens.get(child))) {
			contains = false;
			isFromChild = true;
			nextBlock = "\tif (flag) {\n\t" + childMethodTokens.get(0);
			child++;
		} else {
			contains = false;
			isFromChild = false;
			nextBlock = "\tif (flag) {\n\t} else {\n\t" + parentMethodTokens.get(0);
			parent++;
		}
		while (child < childMethodTokens.size() && parent < parentMethodTokens.size()) {
			if (contains) {
				if (parentMethodTokens.get(parent).equals(childMethodTokens.get(child))) {
					nextBlock += "\n" + childMethodTokens.get(child);
					child++;
					parent++;
				} else if (!parentMethodTokens.contains(childMethodTokens.get(child))) {
					contains = false;
					isFromChild = true;
					blocks.add(nextBlock);
					nextBlock = "\n\tif (flag) {\n\t" + childMethodTokens.get(child);
					child++;
				} else {
					contains = false;
					isFromChild = false;
					blocks.add(nextBlock);
					nextBlock = "\n\tif (flag) {\n\t} else {\n\t" + parentMethodTokens.get(parent);
					parent++;
				}
			} else {
				if (!parentMethodTokens.get(parent).equals(childMethodTokens.get(child))) {
					if (isFromChild) {
						if (!parentMethodTokens.contains(childMethodTokens.get(child))) {
							nextBlock += "\n\t" + childMethodTokens.get(child);
							child++;
						} else {
							nextBlock += "\n\t} else {\n\t";
							blocks.add(nextBlock);
							nextBlock = parentMethodTokens.get(parent);
							isFromChild = false;
							parent++;
						}
					} else {
						nextBlock += "\n\t" + parentMethodTokens.get(parent);
						parent++;
					}
				} else {
					nextBlock += "\n\t}\n";
					blocks.add(nextBlock);
					nextBlock = childMethodTokens.get(child);
					contains = true;
					child++;
					parent++;
				}
			}
		}
		blocks.add(nextBlock);
		if (child < childMethodTokens.size()) {
			nextBlock = "\tif (flag) {";
			while (child < childMethodTokens.size()) {
				nextBlock += "\n\t" + childMethodTokens.get(child);
				child++;
			}
			nextBlock += "\n\t}\n";
			blocks.add(nextBlock);
		}
		if (parent < parentMethodTokens.size()) {
			nextBlock = "\tif (flag) {\n\t} else {";
			while (parent < parentMethodTokens.size()) {
				nextBlock += "\n\t" + parentMethodTokens.get(parent);
				parent++;
			}
			nextBlock += "\n\t}\n";
			blocks.add(nextBlock);
		}
		
		String parentMethodString = parentMethod.getPseudoCode();
		int begin = parentMethodString.indexOf(")");
		String newParentMethodString = parentMethodString.substring(0, begin);
		if (parentMethod.getDataTypes().isEmpty()) {
			newParentMethodString += "boolean flag) {";
		} else {
			newParentMethodString += ", boolean flag) {";
		}
		for (String blockContent : blocks) {
			newParentMethodString += blockContent;
		}
		newParentMethodString += "\n}";
		
		String childMethodString = childMethod.getPseudoCode();
		begin = childMethodString.indexOf("{");
		String newChildMethodString = "\n\n/*Other possibility: Conditionals for " + childAlgorithm.getName() + "\n" + childMethodString.substring(0, begin);
		newChildMethodString += "{\n\tsuper." + childMethod.getName();
		newChildMethodString = newChildMethodString.substring(0, newChildMethodString.length() - 1);
		if (parentMethod.getDataTypes().isEmpty()) {
			newChildMethodString += "true);\n}\n*/";
		} else {
			newChildMethodString += ", true);\n}\n*/";
		}
		
		parentMethod.setPseudoCode(parentMethod.getPseudoCode() + "\n\n/*Other possibility: Conditionals for " + childAlgorithm.getName() + "\n" + newParentMethodString +"\n*/");
		childMethod.setPseudoCode(childMethod.getPseudoCode() + newChildMethodString);
	}
	
	/**
	 * Generates the suggestions to call the super method in the case of overloading
	 * the default parameter are collected
	 * @param parentMethod	the parent method
	 * @param childMethod	the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 * @return
	 */
	private static String modifyToImplementOverloadMethod(Method parentMethod, Method childMethod, Algorithm parentAlgorithm,
			Algorithm childAlgorithm) {
		String pseudoCodeString = parentMethod.getPseudoCode();
		pseudoCodeString = pseudoCodeString.substring(pseudoCodeString.indexOf("(") + 1, pseudoCodeString.indexOf(")"));
		StringTokenizer tokenizer = new StringTokenizer(pseudoCodeString, ",");
		
		List<String> parentParameter = new ArrayList<>();
		while (tokenizer.hasMoreElements()) {
			parentParameter.add(tokenizer.nextToken().trim());
		}
		
		pseudoCodeString = childMethod.getPseudoCode();
		pseudoCodeString = pseudoCodeString.substring(pseudoCodeString.indexOf("(") + 1, pseudoCodeString.indexOf(")"));
		tokenizer = new StringTokenizer(pseudoCodeString, ",");
		
		List<String> childParameter = new ArrayList<>();
		while (tokenizer.hasMoreElements()) {
			childParameter.add(tokenizer.nextToken().trim());
		}
		
		List<String> defaultParameters = new ArrayList<>();
		for (String parameter : parentParameter) {
			if (!childParameter.contains(parameter)) {
				defaultParameters.add(parameter);
			}
			
		}
		
		String methodCall = "super." + parentMethod.getName();
		
		for (String defaultParameter : defaultParameters) {
			if (childMethod.getPseudoCode().contains(defaultParameter)) {
				String statement = childMethod.getPseudoCode().substring(childMethod.getPseudoCode().indexOf(defaultParameter));
				statement = statement.substring(statement.indexOf("=") + 1, statement.indexOf(";")).trim();
				tokenizer = new StringTokenizer(defaultParameter, " ");
				tokenizer.nextToken();
				String token = tokenizer.nextToken();
				methodCall = methodCall.replace(token, statement);
			}
		}
		return methodCall;
		
	}
	
	/**
	 * Searches for parameter that could be replaced with generics
	 * @param parentMethod	the parent method
	 * @param childMethod	the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void modifyToImplementGenericMethod(Method parentMethod, Method childMethod, Algorithm parentAlgorithm, Algorithm childAlgorithm) {
		String stringTypeChild = "";
		String stringTypeParent = "";
		List<String> pairs = new ArrayList<>();
		
		for (int i = 0; i < parentMethod.getDataTypes().size(); i++) {
			if (!parentMethod.getDataTypes().get(i).getName().equals(childMethod.getDataTypes().get(i).getName())) {
					stringTypeChild = childMethod.getDataTypes().get(i).getName();
					stringTypeParent = parentMethod.getDataTypes().get(i).getName();
					if (!pairs.contains(stringTypeParent + " " + stringTypeChild)) {
						pairs.add(stringTypeParent + " " + stringTypeChild);
					}
			}
		}
		for (String element : pairs) {
			StringTokenizer tokenizer = new StringTokenizer(element, " ");
			modifyToImplementGenericMethodSubRoutine(tokenizer.nextToken(), tokenizer.nextToken(), parentMethod, childMethod, parentAlgorithm, childAlgorithm);
		}
	}
	
	/**
	 * Does the generic replacement for every selected parameter
	 * The parent gets a generic type and the child its replacement
	 * @param stringTypeParent	the type of the parent parameter
	 * @param stringTypeChild	the type of the child parameter
	 * @param parentMethod		the parent method
	 * @param childMethod		the child method
	 * @param parentAlgorithm	the parent algorithm
	 * @param childAlgorithm	the child algorithm
	 */
	private static void modifyToImplementGenericMethodSubRoutine(String stringTypeParent, String stringTypeChild, Method parentMethod, Method childMethod, Algorithm parentAlgorithm, Algorithm childAlgorithm) {
		List<Integer> parameterOccurance = new ArrayList<>();
		
		for (int i = 0; i < parentMethod.getDataTypes().size(); i++) {
			if (parentMethod.getDataTypes().get(i).getName().equals(stringTypeParent)) {
				if (childMethod.getDataTypes().get(i).getName().equals(stringTypeChild)) {
					parameterOccurance.add(i);
				}
			}
		}
		char genericLetter = (char) genericCounter;
		boolean used = false;
		boolean first = true;
		for (Integer i : parameterOccurance) {
			if (stringTypeParent.length() != 1) {
				used = true;
				parentMethod.getDataTypes().get(i).setName("" + genericLetter);
				String pseudoCodeString = parentMethod.getPseudoCode();
				pseudoCodeString = pseudoCodeString.substring(pseudoCodeString.indexOf("(") + 1, pseudoCodeString.indexOf(")"));
				StringTokenizer tokenizer = new StringTokenizer(pseudoCodeString, ",");
				List<String> parameter = new ArrayList<>();
				while (tokenizer.hasMoreElements()) {
					parameter.add(tokenizer.nextToken());
				}
				String thisParameter = parameter.get(i);
				String thisNewParameter = thisParameter.replace(stringTypeParent, "" + genericLetter);
				parentMethod.setPseudoCode(parentMethod.getPseudoCode().replace(thisParameter, thisNewParameter));
				if (first) {
					ClassObject object = getClassObjectFromList(parentAlgorithm.getName());
					object.addGeneric("" + genericLetter);
					ClassObject childObject = getClassObjectFromList(childAlgorithm.getName());
					childObject.addGenericParent(stringTypeChild);
				}
			} else {
				if (first) {
					ClassObject childObject = getClassObjectFromList(childAlgorithm.getName());
					childObject.addGenericParent(stringTypeChild);
				}
			}
			first = false;
		}
		
		if (used) {
			genericCounter++;
		}
	}
	
	/**
	 * Compares two child algorithms and searches for similarities
	 * Suggestions are generated
	 * @param childAlgorithm	the first child algorithm
	 * @param childAlgorithm2	the second child algorithm
	 */
	private static void compareChildAlgorithms(Algorithm childAlgorithm, Algorithm childAlgorithm2) {
		for (DataStructure data : childAlgorithm.getDataStructures()) {
			for (DataStructure data2 : childAlgorithm2.getDataStructures()) {
				if (data.getName().equals(data2.getName()) && data.getDataType().getName().equals(data2.getDataType().getName())) {
					ManageProject.addToInfoFileContent("Same data structure found: " + data.getName() + " in " + childAlgorithm.getName() + " and " + childAlgorithm2.getName());
				}
			}
		}
		
		for (Method method : childAlgorithm.getMethods()) {
			String methodName = method.getName();
			methodName = methodName.substring(0, methodName.indexOf("("));
			for (Method method2 : childAlgorithm2.getMethods()) {
				String methodName2 = method2.getName();
				methodName2 = methodName2.substring(0, methodName2.indexOf("("));
				if (methodName.equals(methodName2)) {
					boolean sameTypes = true;
					boolean overloading = false;
					boolean sameNumberOfTypes = false;
					if (method.getDataTypes().size() > method2.getDataTypes().size()) {
						Set<String> parentDataTypes = new HashSet<>();
						Set<String> childDataTypes = new HashSet<>();
						for (DataType type : method.getDataTypes()) {
							parentDataTypes.add(type.getName());
						}
						for (DataType type : method2.getDataTypes()) {
							childDataTypes.add(type.getName());
						}
						if (parentDataTypes.containsAll(childDataTypes)) {
							overloading = true;
						}
						sameTypes = false;
					} else if (method.getDataTypes().size() == method2.getDataTypes().size()) {
						sameNumberOfTypes = true;
						for (int i = 0; i < method.getDataTypes().size(); i++) {
							if (!method.getDataTypes().get(i).getName().equals(method2.getDataTypes().get(i).getName())) {
								sameTypes = false;
							}
						}
					}
					
					Decision implementation = null;
					if (method.getPseudoCode() != null && method2.getPseudoCode() != null && !method.getPseudoCode().isEmpty() && !method2.getPseudoCode().isEmpty()) {
						implementation = CompareMethodBodies.compareMethodBodies(method.getPseudoCode(), method2.getPseudoCode());
					}
					if (implementation == Decision.INHERIT && sameTypes) {
						ManageProject.addToInfoFileContent("Same method: " + method.getName() + " in " + childAlgorithm.getName() + " and " + childAlgorithm2.getName());
					} else if (implementation == Decision.EXTEND && sameTypes) {
						ManageProject.addToInfoFileContent("Method is extended: " + method.getName() + " from " + childAlgorithm.getName() + " is extended in " + childAlgorithm2.getName());
					} else if (overloading) {
						ManageProject.addToInfoFileContent("Method names are equal, but methods have different number of types: " + method.getName() + "/" + method2.getName() + " in " + childAlgorithm.getName() + "/" + childAlgorithm2.getName());
					} else if (!sameTypes && sameNumberOfTypes) {
						ManageProject.addToInfoFileContent("Method names are equal, but methods have different types. Maybe use generics: " + method.getName() + " in " + childAlgorithm.getName() + " and " + childAlgorithm2.getName());
					}
				}
			}
		}
	}
	
	/**
	 * Helper method to get the corresponding ClassObject of an algorithm
	 * @param name	the name of the algorithm
	 * @return	the corresponding ClassObject
	 */
	private static ClassObject getClassObjectFromList(String name) {
		for (ClassObject object : objects) {
			if (object.getName().equals(name)) {
				return object;
			}
		}
		return null;
	}
	
	/**
	 * Reads all classes of the taxonomy and checks the correctness with KeY
	 * @param files	the input classes of the taxonomy
	 */
	private static boolean proveWithKey(List<IFile> files) {
		RobotMonkey monkey = new RobotMonkey("Test", true);
		String content = "";
		for (IFile iFile : files) {
			File file = iFile.getLocation().toFile();
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader =  new BufferedReader(fileReader);
				String line;
				while((line = bufferedReader.readLine()) != null) {
					if (!line.contains("package")) {
						content += line + "\n";
					}
	            }
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			
		}
		try {
			InputStream program = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
			SimianResult res =  monkey.test(program, "Test");
			if (res.isClosed()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}