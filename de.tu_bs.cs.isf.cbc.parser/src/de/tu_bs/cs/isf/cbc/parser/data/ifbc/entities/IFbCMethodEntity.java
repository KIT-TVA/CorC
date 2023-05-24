package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCMethod;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCStatement;

public class IFbCMethodEntity extends IFbCReferenceEntity {

	private String definingClassName;
	private List<IFbCReferenceEntity> scopes;
	private String methodSecurityLevel;
	private MDF methodMDF;
	private final List<ParameterDefinition> parameters;
	private Method definedMethod;
	private final String statement;

	public IFbCMethodEntity(String name, String securityLevel, MDF mutationModifier, String type,
			String definingClassName, List<IFbCReferenceEntity> scopes, String methodSecurityLevel, MDF methodMDF,
			List<ParameterDefinition> parameters, Method definedMethod, String statement) {
		super(name, securityLevel, mutationModifier, type);
		this.definingClassName = definingClassName;
		this.scopes = scopes;
		this.methodSecurityLevel = methodSecurityLevel;
		this.methodMDF = methodMDF;
		this.parameters = parameters;
		this.definedMethod = definedMethod;
		this.statement = statement;
	}

	public String getDefiningClassName() {
		return definingClassName;
	}

	public void setDefiningClassName(String definingClassName) {
		this.definingClassName = definingClassName;
	}

	public List<IFbCReferenceEntity> getScopes() {
		return scopes;
	}

	public String getMethodSecurityLevel() {
		return methodSecurityLevel;
	}

	public MDF getMethodMDF() {
		return methodMDF;
	}

	public List<ParameterDefinition> getParameters() {
		return parameters;
	}

	public Method getDefinedMethod() {
		return definedMethod;
	}

	public void setDefinedMethod(Method definedMethod) {
		this.definedMethod = definedMethod;
	}

	public void setMethodSecurityLevel(String methodSecurityLevel) {
		this.methodSecurityLevel = methodSecurityLevel;
	}

	public void setMethodMDF(MDF methodMDF) {
		this.methodMDF = methodMDF;
	}

	public String getStatement() {
		return statement;
	}

	@Override
	public String toString() {
		return "IFbCMethodEntity [definingClassName=" + definingClassName + ", scopes=" + scopes
				+ ", methodSecurityLevel=" + methodSecurityLevel + ", methodMDF=" + methodMDF + ", parameters="
				+ parameters + ", definedMethod=" + definedMethod + ", statement=" + statement + ", getSecurityLevel()="
				+ getSecurityLevel() + ", getMutationModifier()=" + getMutationModifier() + ", getType()=" + getType()
				+ "]";
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.METHOD;
	}
}
