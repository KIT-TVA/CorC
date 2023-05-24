package de.tu_bs.cs.isf.cbc.parser.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.ast.expr.LiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;

import de.tu_bs.cs.isf.cbc.parser.AbstractIFbCParser;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod.Type;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;

public class JavaClass {
	
	private String className;
	private String sourceFile;
	private Long parseTime;
	private List<IFieldOrMethod> fieldsAndMethods;
	private ClassSubtypes subtypes;
	private ConstructorDefinition constructor;
	
	public JavaClass(String className, 
	                 String sourceFile, 
	                 Long parseTime, 
	                 List<IFieldOrMethod> v, 
	                 ClassSubtypes subtypes,
	                 ConstructorDefinition constructor) {
		super();
		this.className = className;
		this.sourceFile = sourceFile;
		this.parseTime = parseTime;
		this.fieldsAndMethods = v;
		this.subtypes = subtypes;
		this.constructor = constructor;
	}

	public String getClassName() {
		return className;
	}
	
	public String getSourceFile() {
		return sourceFile;
	}
	
	public Long getParseTime() {
		return parseTime;
	}

	public List<IFieldOrMethod> getFieldsAndMethods() {
		return fieldsAndMethods;
	}

	public ClassSubtypes getSubtypes() {
		return subtypes;
	}

	public ConstructorDefinition getConstructor() {
		return constructor;
	}

	public void setConstructor(ConstructorDefinition constructor) {
		this.constructor = constructor;
	}

	@Override
	public String toString() {
		return "JavaClass [className=" + className + ",\n sourceFile=" + sourceFile + ",\n parseTime=" + parseTime
				+ ",\n fieldsAndMethods=" + fieldsAndMethods + ",\n subtypes=" + subtypes + ",\n constructor=" + constructor
				+ "]";
	}

	/**
	 * Returns the methods for which the method name and the size of parameters/arguments
	 * are the same in this class
	 * 
	 * @param methodCallExpr
	 * @param diagramVariables 
	 * @return
	 */
	public Method getMethodForMethodCallExpr(final MethodCallExpr methodCallExpr, Map<String, IFbCReferenceEntity> diagramVariables) {
		final List<Method> methods = getFieldsAndMethods()
				.stream()
				.filter(fm -> fm.getType() == Type.METHOD)
				.map(fm -> (Method) fm)
				.filter(m ->  m.getName().equals(methodCallExpr.getNameAsString()))
				.filter(m -> m.getParameterList().size() == methodCallExpr.getArguments().size())
				.collect(Collectors.toList());
		
	
		for (Method m : methods) {
			// Parameter types from the method signature in the source file
			final Set<String> parameterTypesFromMethod = m.getParameterList().stream().map(ParameterDefinition::getType).collect(Collectors.toSet());
			//Parameters from the used variables in the statement
			final Set<String> parameterTypesFromStatement = new HashSet<>();
			methodCallExpr.getArguments().forEach(e -> {
				System.out.println("Checking expression: " + e.toString());
				if (e.isNameExpr()) {
					final NameExpr nameExpr = e.asNameExpr();
					final IFbCReferenceEntity IFbCEntity = diagramVariables.get(nameExpr.getNameAsString());
					if (IFbCEntity == null) {
						return;
					}
					parameterTypesFromStatement.add(IFbCEntity.getType());
				} else if (e.isLiteralExpr()) {
					final LiteralExpr literalExpr = e.asLiteralExpr();
					final String literalType = AbstractIFbCParser.getLiteralType(literalExpr);
					parameterTypesFromStatement.add(literalType);
				}  else {
					System.out.println("Expression " + e.toString() + " is currently not supported as parameter of method.");
				}
			});
			
			// check if we have a match with this method
			System.out.println("Checking parameter types for method" + m.toString());
			System.out.println("Comparing " + parameterTypesFromMethod.toString() + " with " + parameterTypesFromStatement.toString());
			if (parameterTypesFromMethod.equals(parameterTypesFromStatement)) {
				return m;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the methods for which the method name and the size of parameters/arguments
	 * are the same in this class
	 * 
	 * @param methodCallExpr
	 * @param diagramVariables 
	 * @return
	 */
	public Method getMethodByNameAndParameterSize(final String methodName, List<ParameterDefinition> parameters) { //, Map<String, IFbCReferenceEntity> diagramVariables) {
		System.out.println("Searching method with name '" + methodName + "' and the following parameters " + parameters + " in class " + this.getClassName());
		final List<Method> methods = getFieldsAndMethods()
				.stream()
				.filter(fm -> fm.getType() == Type.METHOD)
				.map(fm -> (Method) fm)
				.filter(m ->  m.getName().equals(methodName))
				.filter(m -> m.getParameterList().size() == parameters.size())
				.collect(Collectors.toList());		
	
		for (Method m : methods) {
			// Parameter types from the method signature in the source file
			final Set<String> parameterTypesFromMethod = m.getParameterList().stream().map(ParameterDefinition::getType).collect(Collectors.toSet());
			//Parameters from the used variables in the statement
			final Set<String> parameterTypesFromStatement = new HashSet<>();
			parameters.forEach(e -> {
				parameterTypesFromStatement.add(e.getType());
			});
			
			// check if we have a match with this method
			if (parameterTypesFromMethod.equals(parameterTypesFromStatement)) {
				return m;
			}
		}
		
		return null;
	}
	
	public Optional<Field> getFieldForFieldName(String fieldName) {
		return getFieldsAndMethods()
				.stream()
				.filter(fm -> fm.getType() == Type.FIELD)
				.map(fm -> (Field) fm)
				.filter(f ->  f.getName().equals(fieldName)).findFirst();
	}
}
