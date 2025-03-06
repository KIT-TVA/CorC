package de.tu_bs.cs.isf.cbc.util;

import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.ast.body.MethodDeclaration;

public class Method {
	private MethodDeclaration methodDeclaration;
	private String methodName;
	private String className;
	private boolean isAbstract;
	private List<String> preCondition;
	private List<String> postCondition;
	private String feature;
	private List<String> parameters;
	private String returnType;

	public Method(MethodDeclaration methodDeclaration, String methodName, String className, boolean isAbstract,
			List<String> preCondition, List<String> postCondition, String feature, List<String> parameter,
			String returnType) {
		this.methodDeclaration = methodDeclaration;
		this.methodName = methodName;
		this.className = className;
		this.isAbstract = isAbstract;
		this.preCondition = preCondition;
		this.postCondition = postCondition;
		this.feature = feature;
		this.parameters = parameter;
		this.returnType = returnType;
	}

	public MethodDeclaration getMethodDeclaration() {
		return methodDeclaration;
	}

	public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
		this.methodDeclaration = methodDeclaration;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public List<String> getPreCondition() {
		return preCondition;
	}

	public void setPreCondition(List<String> preCondition) {
		this.preCondition = preCondition;
	}

	public List<String> getPostCondition() {
		return postCondition;
	}

	public void setPostCondition(List<String> postCondition) {
		this.postCondition = postCondition;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameter) {
		this.parameters = parameter;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String createSignature() {
		return "public " + getReturnType() + " " + getMethodName() + "("
				+ getParameters().stream().collect(Collectors.joining(", ")) + ")";
	}

}
