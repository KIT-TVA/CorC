package de.tu_bs.cs.isf.cbc.parser.javaparser.code;

import java.util.regex.Pattern;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

public class IntegerLiteralModifier extends ModifierVisitor<Void> {

	private static final Pattern LOOK_AHEAD_THREE = Pattern.compile("(\\d)(?=(\\d{3})+$)");

	@Override
	public Visitable visit(FieldDeclaration fd, Void arg) {
		super.visit(fd, arg);

		fd.getVariables().forEach(v -> v.getInitializer().ifPresent(i -> {
			if (i instanceof IntegerLiteralExpr) {
				v.setInitializer(formatWithUnderscores(((IntegerLiteralExpr) i).getValue()));
			}
		}));

		return fd;
	}

	static String formatWithUnderscores(String value) {
		String withoutUnderscores = value.replace("_", "");
		return LOOK_AHEAD_THREE.matcher(withoutUnderscores).replaceAll("$1_");
	}
}
