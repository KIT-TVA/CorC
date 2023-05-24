package de.tu_bs.cs.isf.cbc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;
import de.tu_bs.cs.isf.cbc.parser.data.Field;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod;

class FieldCollector extends VoidVisitorAdapter<Map<String, List<IFieldOrMethod>>> {

	@Override
	public void visit(FieldDeclaration fd, Map<String, List<IFieldOrMethod>> fields) {
		super.visit(fd, fields);

		// Get Class name
		final ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration) fd.getParentNode().get();
		final String className = clazz.getNameAsString();

		// Get Security Level and Mutation Modifier annotations
		final Optional<AnnotationExpr> securityLevelOptional = fd.getAnnotationByClass(SecurityLevel.class);
		final Optional<AnnotationExpr> mutationModifierOptional = fd.getAnnotationByClass(MutationModifier.class);

		if (!securityLevelOptional.isPresent()) {
			System.out.println("Security Level not present, ignoring this field declaration. (Field Declaration: "
					+ fd.toString() + ")\n");
			return;
		}
		// if (!mutationModifierOptional.isPresent()) {
		// System.out.println("Mutation modifier not present, ignoring this field
		// declaration. (Field Declaration: " + fd.toString() + ")\n");
		// return;
		// }

		// Get annotation parameters
		// Check SL
		final SingleMemberAnnotationExpr slExpr = (SingleMemberAnnotationExpr) securityLevelOptional.get();
		final String securityLevel = slExpr.getMemberValue().asStringLiteralExpr().asString();

		// Check MDF
		final String mutationModifier;
		if (mutationModifierOptional.isPresent()) {
			// MDF is defined, using this value
			final SingleMemberAnnotationExpr mdfExpr = (SingleMemberAnnotationExpr) mutationModifierOptional.get();
			mutationModifier = mdfExpr.getMemberValue().toString();
		} else {
			// MDF is not defined, using default value for MDF
			mutationModifier = "MDF.IMMUTABLE";
		}
		
		final NodeList<Modifier> modifiers = fd.getModifiers();
		Boolean staticReference = false;
		for (Modifier modifier : modifiers) {
			System.out.println("Modifier: '" + modifier.toString() + "'");
			if (modifier.toString().contains("static")) {
				staticReference = true;
				break;
			}
		}

		// Add fields to appropriate list per class. Loop because of multiple
		// declarations per line
		for (VariableDeclarator variable : fd.getVariables()) {
			final List<IFieldOrMethod> classMethodsAndFields = fields.get(className) != null ? fields.get(className)
					: new ArrayList<>();
			final Field field = new Field(variable.getNameAsString(),
					securityLevel,
					MDF.forAnnotationExpress(mutationModifier), variable.getTypeAsString(),
					staticReference);
			classMethodsAndFields.add(field);
			fields.put(className, classMethodsAndFields);
		}
	}
}