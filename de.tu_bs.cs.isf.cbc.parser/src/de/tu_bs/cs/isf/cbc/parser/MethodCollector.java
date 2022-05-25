package de.tu_bs.cs.isf.cbc.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Pair;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;
import de.tu_bs.cs.isf.cbc.parser.data.IFieldOrMethod;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;

class MethodCollector extends VoidVisitorAdapter<Map<String, List<IFieldOrMethod>>> {
	
	@Override
	public void visit(MethodDeclaration md, Map<String, List<IFieldOrMethod>> methods) {
		super.visit(md, methods);
		
		// Get Class name
		final ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration) md.getParentNode().get();
		final String className = clazz.getNameAsString();
		
		// Get Security Level and Mutation Modifier annotations
		final Optional<AnnotationExpr> securityLevelOptional = md.getAnnotationByClass(SecurityLevel.class);
		final Optional<AnnotationExpr> mutationModifierOptional = md.getAnnotationByClass(MutationModifier.class);
		final Optional<AnnotationExpr> methodReceiverOptional = md.getAnnotationByClass(MethodReceiver.class);
		
		final Boolean voidMethod = md.getTypeAsString().equals("void");
		System.out.println("Return type: " + voidMethod); 
		
		final NodeList<Modifier> modifiers = md.getModifiers();
		Boolean staticMethod = false;
		for (Modifier modifier : modifiers) {
			System.out.println("Modifier: '" + modifier.toString() + "'");
			if (modifier.toString().contains("static")) {
				staticMethod = true;
				break;
			}
		}
		System.out.println("Static method: " + staticMethod);
		
		if (!securityLevelOptional.isPresent() && !voidMethod) {
			System.out.println("Security Level not present for return type, ignoring this method declaration. (Method Declaration: \n" + md.toString() + ")\n");
			return;
		}
//		if (!mutationModifierOptional.isPresent() && !voidMethod) {
//			System.out.println("Mutation modifier not present for return type, ignoring this method declaration. (Method Declaration: \n" + md.toString() + ")\n");
//			return;
//		}
		if (!methodReceiverOptional.isPresent() && !staticMethod) {
			System.out.println("Method receiver not present, ignoring this method declaration. (Method Declaration: \n" + md.toString() + ")\n");
			return;
		}
		
		// Get annotation parameters
		final String securityLevel;
		final String mutationModifier;
		
		// Check SL
		if (voidMethod) {
			securityLevel = "";
		} else {
			final SingleMemberAnnotationExpr slExpr = (SingleMemberAnnotationExpr) securityLevelOptional.get();
			securityLevel = slExpr.getMemberValue().asStringLiteralExpr().asString();
		}
		
		// Check MDF
		if (voidMethod) {
			mutationModifier = "";
		} else {
			if (mutationModifierOptional.isPresent()) {
				// MDF is defined, using this value
				final SingleMemberAnnotationExpr mdfExpr = (SingleMemberAnnotationExpr) mutationModifierOptional.get();
				mutationModifier = mdfExpr.getMemberValue().toString();
			} else {
				// MDF is not defined, using default value for MDF
				mutationModifier = "MDF.IMMUTABLE";
			}
		}
		
		final NormalAnnotationExpr methodReceiver = staticMethod ? null : (NormalAnnotationExpr) methodReceiverOptional.get();
		String receiverSL = "";
		MDF receiverMDF = MDF.READ;
		if (!staticMethod) {
			for (MemberValuePair p : methodReceiver.getPairs()) {
				if (p.getNameAsString().equals("SL")) {
					receiverSL = p.getValue().asStringLiteralExpr().asString();
				} else if (p.getNameAsString().equals("MDF")) {
					receiverMDF = MDF.forAnnotationExpress(p.getValue().toString());
				}
			}
		}
		
		
		// Collect parameters
		final List<ParameterDefinition> parameters = new ArrayList<>();
		md.getParameters().forEach(n -> {
			final Optional<AnnotationExpr> slOptional = n.getAnnotationByClass(SecurityLevel.class);
			final Optional<AnnotationExpr> mdfOptional = n.getAnnotationByClass(MutationModifier.class);
			
			if (!slOptional.isPresent()) {
				System.out.println("Security Level not present for parameter " + n.getName() + ". (Parameter Declaration: " + n.toString() + ")");
				return;
			}
//			if (!mdfOptinal.isPresent()) {
//				System.out.println("Mutation modifier not present for parameter " + n.getName() + ". (Parameter Declaration: " + n.toString() + ")");
//				return;
//			}
			// Get annotation parameters
			final SingleMemberAnnotationExpr slExpr = (SingleMemberAnnotationExpr) slOptional.get();
			final String sl = slExpr.getMemberValue().asStringLiteralExpr().asString();
			final String mdf;
			if (mdfOptional.isPresent()) {
				final SingleMemberAnnotationExpr mdfExpr = (SingleMemberAnnotationExpr) mdfOptional.get();
				mdf = mdfExpr.getMemberValue().toString();
			} else {
				mdf = "MDF.IMMUTABLE";
			}
			
			parameters.add(new ParameterDefinition(n.getName().asString(), 
					sl, 
					MDF.forAnnotationExpress(mdf),
					n.getTypeAsString()));
		});
		
		if (parameters.size() != md.getParameters().size()) {
			System.out.println("Ignoring the method " + md.getNameAsString() + " because parameter declaration is invalid.\n");
			return;
		}
		
		// Add Method to list or create a new one
		final List<IFieldOrMethod> classMethodsOrFields = methods.get(className) != null ? methods.get(className) : new ArrayList<>();
		final Method method = new Method(
				md.getName().asString(), 
				md.getTypeAsString(),
				voidMethod ? null : securityLevel, 
				voidMethod ? null : MDF.forAnnotationExpress(mutationModifier), 
				parameters,
				staticMethod ? null : receiverSL,
				staticMethod ? null : receiverMDF,
				staticMethod,
				voidMethod,
				className);
		classMethodsOrFields.add(method);
		methods.put(className, classMethodsOrFields);
	}
}