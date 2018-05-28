package de.tu_bs.cs.isf.toolkit.support;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Helper class to save the resulting algorithm classes
 * @author Tobias
 *
 */
public class ClassObject {

	/**
	 * list of all methods
	 */
	private List<Method> methods;
	/**
	 * list of all data structures
	 */
	private List<DataStructure> data;
	/**
	 * name of the parent algorithm
	 */
	private String parent;
	/**
	 * name of this algorithm
	 */
	private String name;
	/**
	 * abstract value of the algorithm
	 */
	private boolean isAbstract;
	/**
	 * letters for the generic types of the parent
	 */
	private List<String> genericParent;
	/**
	 * letters for the generic types 
	 */
	private List<String> generic;
	/**
	 * string containing all imports
	 */
	private String imports;
	
	/**
	 * Constructor for an ClassObject
	 * @param methods	the methods of the algorithm
	 * @param data		the data structures of the algorithm
	 * @param parent	the name of the parent
	 * @param name		its name
	 * @param isAbstract	value if the algorithm is abstract
	 */
	public ClassObject(List<Method> methods, List<DataStructure> data, String parent, String name, boolean isAbstract, String imports) {
		
		this.methods = new ArrayList<>();
		this.methods.addAll(methods);
		this.data = new ArrayList<>();
		this.data.addAll(data);
		this.parent = parent;
		this.name = name;
		this.isAbstract = isAbstract;
		this.generic = new ArrayList<>();
		this.genericParent = new ArrayList<>();
		this.imports = imports;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public List<DataStructure> getData() {
		return data;
	}

	public void setData(List<DataStructure> data) {
		this.data = data;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public void addGeneric(String generic) {
		this.generic.add(generic);
	}

	public List<String> getGeneric() {
		return this.generic;
	}
	
	public void addGenericParent(String generic) {
		this.genericParent.add(generic);
	}

	public List<String> getGenericParent() {
		return this.genericParent;
	}

	public String getImports() {
		return imports;
	}

	public void setImports(String imports) {
		this.imports = imports;
	}

	/**
	 * creates the string representation of the class
	 * @return	String with the content of the class
	 */
	public String createContentForClass() {
		StringBuffer buffer = new StringBuffer();
		
		if (imports != null && !imports.isEmpty()) {
			buffer.append(imports + "\n\n");
		}
		
		String name = this.getName().replaceAll(" ", "");
		if (this.isAbstract) {
			buffer.append("public abstract class " + name);
		} else {
			buffer.append("public class " + name);
		}
		if(!this.getGeneric().isEmpty() && this.isAbstract) {
			buffer.append("<");
			for (int i = 0; i < this.generic.size(); i++) {
				if (i == this.generic.size() - 1) {
					buffer.append(this.generic.get(i));
				} else {
					buffer.append(this.generic.get(i) + ",");
				}
			}
			buffer.append(">");
		}
		if (this.parent == null) {
			buffer.append(" {\n\n");
		} else {
			name = this.getParent().replaceAll(" ", "");
			if (!this.getGenericParent().isEmpty()) {
				buffer.append(" extends " + name + "<");
				for (int i = 0; i < this.genericParent.size(); i++) {
					if (i == this.genericParent.size() - 1) {
						buffer.append(this.genericParent.get(i));
					} else {
						buffer.append(this.genericParent.get(i) + ",");
					}
				}
				buffer.append("> {\n\n");
			} else {
				buffer.append(" extends " + name + " {\n\n");

			}
		}
		for (DataStructure data : this.getData()) {
			if (data.getInitialValue() != null) {
				buffer.append("protected " + data.getDataType().getName() + " " + data.getName() + " = " + data.getInitialValue() + ";\n");
			} else {
				buffer.append("protected " + data.getDataType().getName() + " " + data.getName() + ";\n");
			}
		}
		buffer.append("\n");	
		for (Method method : this.getMethods()) {
			String methodString = addConditionsToMethod(method);
			buffer.append(methodString + "\n\n");
		}
		buffer.append("}\n");	
		return buffer.toString();
	}
	
	/**
	 * adds pre- and post-condition to the method
	 * @param method	the method
	 * @return	method as String with conditions
	 */
	private static String addConditionsToMethod(Method method) {
		String methodString = method.getPseudoCode();
		String preCondition = method.getPreCondition();
		String postCondition = method.getPostCondition();
		
		if (methodString.contains("abstract")) {
			return methodString;
		}
		int indexOfBody = methodString.indexOf("{");
		String declaration = methodString.substring(0, indexOfBody);
		int indexEnd;
		if (declaration.contains("void")) {
			indexEnd = methodString.lastIndexOf("}");
		} else {
			indexEnd = methodString.lastIndexOf("return");
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodString.substring(0, indexOfBody + 1));
		if (preCondition != null && !preCondition.isEmpty()) {
			buffer.append("\n\tassert " + preCondition + ";\n");
		}
		buffer.append(methodString.substring(indexOfBody + 1, indexEnd));
		if (postCondition != null &&  !postCondition.isEmpty()) {
			buffer.append("\n\tassert " + postCondition + ";\n\t");
		}
		buffer.append(methodString.substring(indexEnd));
		
		return buffer.toString();
	}
	
}
