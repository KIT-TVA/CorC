package de.tu_bs.cs.isf.cbc.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;
import de.tu_bs.cs.isf.cbc.parser.data.ConstructorDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;

class ConstructorCollector extends VoidVisitorAdapter<Map<String, ConstructorDefinition>> {

	@Override
	public void visit(ConstructorDeclaration cd, Map<String, ConstructorDefinition> constructors) {
		super.visit(cd, constructors);

		// Get Class name
		final ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration) cd.getParentNode().get();
		final String className = clazz.getNameAsString();
		
		final NodeList<Parameter> parameterDefintion = cd.getParameters();
		final Map<String, ParameterDefinition> constructorParameters = new LinkedHashMap<>(parameterDefintion.size());
		for (Parameter p : parameterDefintion) {
			// Get Security Level and Mutation Modifier annotations
			final Optional<AnnotationExpr> securityLevelOptional = p.getAnnotationByClass(SecurityLevel.class);
			final Optional<AnnotationExpr> mutationModifierOptional = p.getAnnotationByClass(MutationModifier.class);

			if (!securityLevelOptional.isPresent()) {
				System.out.println(
						"Security Level not present for constructor parameter, ignoring this constructor. (Constructor Declaration: "
								+ p.toString() + ")\n");
				break;
			}
//			if (!mutationModifierOptional.isPresent()) {
//				System.out.println(
//						"Mutation modifier not present for constructor parameter, ignoring this constructor. (Constructor Declaration: "
//								+ p.toString() + ")\n");
//				break;
//			}

			// Get annotation parameters
			final SingleMemberAnnotationExpr slExpr = (SingleMemberAnnotationExpr) securityLevelOptional.get();
			final String securityLevel = slExpr.getMemberValue().asStringLiteralExpr().asString();
			final String mutationModifier;
			if (mutationModifierOptional.isPresent()) {
				final SingleMemberAnnotationExpr mdfExpr = (SingleMemberAnnotationExpr) mutationModifierOptional.get();
				mutationModifier = mdfExpr.getMemberValue().toString();
			} else {
				mutationModifier = "MDF.IMMUTABLE";
			}
					
			
			constructorParameters.put(p.getNameAsString(), 
					new ParameterDefinition(
							p.getNameAsString(), 
							securityLevel, 
							MDF.forAnnotationExpress(mutationModifier), 
							p.getTypeAsString()
							));
		}
		
		// Check if all constructor parameters have a SL and MDF definition
		if (constructorParameters.size() != parameterDefintion.size()) {
			System.out.println(
					"Parameter SL and MDF definition was incomplete, not using this constructor. (Constructor Declaration: "
							+ cd.toString() + ")\n");
			return;
		}
		
		// Check if all parameters of the constructor are in correspondence to the field declaration of this class
		for (FieldDeclaration fd : clazz.getFields()) {
			// Check if this is a static reference -> no need for a parameter in the constructor
			final NodeList<Modifier> modifiers = fd.getModifiers();
			Boolean staticReference = false;
			for (Modifier modifier : modifiers) {
				System.out.println("Modifier: '" + modifier.toString() + "'");
				if (modifier.toString().contains("static")) {
					staticReference = true;
					break;
				}
			}
			
			// Static reference? Next field
			if (staticReference) {
				continue;
			}
			
			for (VariableDeclarator variable : fd.getVariables()) {
				if (constructorParameters.get(variable.getNameAsString()) == null) {
					System.out.println(
							"Parameter with name " + variable.getNameAsString() + " is not present in constrcutor definition, ignoring this constrcutor. (Constructor Declaration: "
									+ cd.toString() + ")\n");
					return;
				}
				
				final ParameterDefinition parameterDefinition = constructorParameters.get(variable.getNameAsString());
				if (!variable.getTypeAsString().equals(parameterDefinition.getType())) {
					System.out.println(
							"Parameter with name " + variable.getNameAsString() + " does not have the same type as the field with the same name, ignoring this constructor. (Constructor Declaration: "
									+ cd.toString() + ")\n");
					return;
				}
			}
		}
		
		// all checks were valid, adding this constructor to the class
		
		constructors.put(className, new ConstructorDefinition(new ArrayList<ParameterDefinition>(constructorParameters.values())));
	}
}