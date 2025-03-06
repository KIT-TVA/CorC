package de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;

public class IFbCFieldEntity extends IFbCReferenceEntity {

	public final List<IFbCReferenceEntity> scopes;

	public IFbCFieldEntity(String name, String securityLevel, MDF mutationModifier, String type,
			List<IFbCReferenceEntity> scopes) {
		super(name, securityLevel, mutationModifier, type);
		this.scopes = scopes;
	}

	public List<IFbCReferenceEntity> getScopes() {
		return scopes;
	}

	public List<Node> getScopeSecurityLevels(final Lattice lattice) throws IFbCException {
		final List<Node> scopeLatticesNodes = new ArrayList<>(scopes.size());
		String securityLevel = this.getSecurityLevel();
		Node scopeSL = lattice.getNodePerName(securityLevel);
		scopeLatticesNodes.add(scopeSL);
		for (IFbCReferenceEntity scope : scopes) {
			securityLevel = scope.getSecurityLevel();
			scopeSL = lattice.getNodePerName(securityLevel);

			if (scopeSL == null) {
				throw new IFbCException("Lattice node for security level " + scopeSL + " of field " + scope.getName()
						+ " cannot be found.");
			}

			scopeLatticesNodes.add(scopeSL);
		}
		return scopeLatticesNodes;
	}

	public List<MDF> getScopeMDFs() {
		return scopes.stream().map(s -> s.getMutationModifier()).collect(Collectors.toList());
	}

	public MDF getCombinedMDF() {
		final List<MDF> scopeMDFs = getScopeMDFs();
		System.out.println("Scope MDFs: " + scopeMDFs);
		MDF combinedMDF = this.getMutationModifier();
		for (MDF m : scopeMDFs) {
			System.out.println("Current MDF: " + m);
			if (m.equals(MDF.CAPSULE) || m.equals(MDF.MUTABLE)) {
				combinedMDF = combinedMDF; // do nothing
			}
			if (m.equals(MDF.IMMUTABLE)) {
				combinedMDF = MDF.IMMUTABLE;
			}
			if (m.equals(MDF.READ)) {
				combinedMDF = MDF.READ;
			}
			System.out.println("Combined MDF: " + combinedMDF);
		}
		return combinedMDF;
	}
}
