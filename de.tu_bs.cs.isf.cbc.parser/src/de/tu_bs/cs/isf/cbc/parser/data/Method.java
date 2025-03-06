package de.tu_bs.cs.isf.cbc.parser.data;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;

public class Method implements IFieldOrMethod {

	private String name;
	private String returnType;
	private String securityLevel;
	private MDF mutationModifier;
	private List<ParameterDefinition> parameterList;
	private String receiverSL;
	private MDF receiverMDF;
	private Boolean staticMethod;
	private Boolean voidMethod;
	private String definingClass;

	public Method(String name, String returnType, String securityLevel, MDF mutationModifier,
			List<ParameterDefinition> parameterList, String receiverSL, MDF receiverMDF, Boolean staticMethod,
			Boolean voidMethod, String definingClass) {
		super();
		this.name = name;
		this.returnType = returnType;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
		this.parameterList = parameterList;
		this.receiverSL = receiverSL;
		this.receiverMDF = receiverMDF;
		this.staticMethod = staticMethod;
		this.voidMethod = voidMethod;
		this.definingClass = definingClass;
	}

	public String getName() {
		return name;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public MDF getMutationModifier() {
		return mutationModifier;
	}

	public List<ParameterDefinition> getParameterList() {
		return parameterList;
	}

	public String getReceiverSL() {
		return receiverSL;
	}

	public MDF getReceiverMDF() {
		return receiverMDF;
	}

	public Boolean getStaticMethod() {
		return staticMethod;
	}

	public Boolean getVoidMethod() {
		return voidMethod;
	}

	public String getDefiningClass() {
		return definingClass;
	}

	public void setDefiningClass(String definingClass) {
		this.definingClass = definingClass;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", returnType=" + returnType + ", securityLevel=" + securityLevel
				+ ", mutationModifier=" + mutationModifier + ", parameterList=" + parameterList + ", receiverSL="
				+ receiverSL + ", receiverMDF=" + receiverMDF + ", staticMethod=" + staticMethod + ", voidMethod="
				+ voidMethod + ", definingClass=" + definingClass + "]";
	}

	@Override
	public Type getType() {
		return Type.METHOD;
	}
}
