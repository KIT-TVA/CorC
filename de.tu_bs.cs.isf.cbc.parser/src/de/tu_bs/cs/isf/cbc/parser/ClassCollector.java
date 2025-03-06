package de.tu_bs.cs.isf.cbc.parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.tu_bs.cs.isf.cbc.parser.data.ClassSubtypes;

class ClassCollector extends VoidVisitorAdapter<Map<String, ClassSubtypes>> {

	@Override
	public void visit(ClassOrInterfaceDeclaration cid, Map<String, ClassSubtypes> subtypes) {
		super.visit(cid, subtypes);

		// Get Class name
		final String className = cid.getNameAsString();

		// Get implements and extends
		final List<String> implementedTypes = cid.getImplementedTypes().stream().map(c -> c.getNameAsString())
				.collect(Collectors.toList());
		final List<String> extendedTypes = cid.getExtendedTypes().stream().map(c -> c.getNameAsString())
				.collect(Collectors.toList());

		final ClassSubtypes subtype = new ClassSubtypes(implementedTypes, extendedTypes);

		subtypes.put(className, subtype);
	}
}