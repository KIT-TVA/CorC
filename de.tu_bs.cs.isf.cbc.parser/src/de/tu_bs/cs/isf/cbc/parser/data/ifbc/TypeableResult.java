package de.tu_bs.cs.isf.cbc.parser.data.ifbc;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;

public class TypeableResult {
	private final boolean typeable;
	private final String message;
	private final Map<String, List<IFbCReferenceEntity>> elevatedEntities;
	private final Map<String, String> changedTypes;
	private final List<String> usedCapsules;
	private final String securityLevel;
	private final MDF mutationModifier;

	public boolean isTypeable() {
		return typeable;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, List<IFbCReferenceEntity>> getElevatedEntities() {
		return elevatedEntities;
	}

	public Map<String, String> getChangedTypes() {
		return changedTypes;
	}

	public List<String> getUsedCapsules() {
		return usedCapsules;
	}

	public String getSecurityLevel() {
		return securityLevel;
	}

	public MDF getMutationModifier() {
		return mutationModifier;
	}

	public TypeableResult(boolean typeable, String message, Map<String, List<IFbCReferenceEntity>> elevatedEntities,
			Map<String, String> changedTypes, List<String> usedCapsules) {
		super();
		this.typeable = typeable;
		this.message = message;
		this.elevatedEntities = elevatedEntities;
		this.changedTypes = changedTypes;
		this.usedCapsules = usedCapsules;
		this.securityLevel = null;
		this.mutationModifier = null;
	}

	public TypeableResult(boolean typeable, String message, Map<String, List<IFbCReferenceEntity>> elevatedEntities,
			Map<String, String> changedTypes, List<String> usedCapsules, String securityLevel, MDF mutationModifier) {
		super();
		this.typeable = typeable;
		this.message = message;
		this.elevatedEntities = elevatedEntities;
		this.changedTypes = changedTypes;
		this.usedCapsules = usedCapsules;
		this.securityLevel = securityLevel;
		this.mutationModifier = mutationModifier;
	}

	@Override
	public String toString() {
		return "TypeableResult [typeable=" + typeable + ", message=" + message + ", elevatedEntities="
				+ elevatedEntities + ", changedTypes=" + changedTypes + ", usedCapsules=" + usedCapsules
				+ ", securityLevel=" + securityLevel + ", mutationModifier=" + mutationModifier + "]";
	}
}
