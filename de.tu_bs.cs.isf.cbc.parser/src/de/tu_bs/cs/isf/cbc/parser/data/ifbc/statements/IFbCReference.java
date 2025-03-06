package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;

public class IFbCReference implements IFbCStatement {

	final IFbCReferenceEntity entity;

	public IFbCReference(IFbCReferenceEntity entity) {
		this.entity = entity;
	}

	@Override
	public TypeableResult isTypeable(final Lattice lattice, final String projectName,
			final Map<String, List<IFbCReferenceEntity>> elevatedEntities, final Map<String, String> changedTypes,
			final List<String> usedCapsules, final String optionalGuardSL, final Method constructingMethod)
			throws IFbCException {
		return new TypeableResult(true, "Reference Entity is always typeable", elevatedEntities, changedTypes,
				usedCapsules);
	}

	@Override
	public StatementType getType() {
		return StatementType.Reference;
	}

	public String getSecurityLevel() {
		return entity.getSecurityLevel();
	}

	public MDF getTypeModifier() {
		return entity.getMutationModifier();
	}

	@Override
	public String getStatement() {
		return entity.getName();
	}

}
