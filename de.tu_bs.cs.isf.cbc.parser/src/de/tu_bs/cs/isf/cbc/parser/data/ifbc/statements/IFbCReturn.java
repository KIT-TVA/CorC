package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class IFbCReturn implements IFbCStatement {

	private final String name;
	private final IFbCStatement returnStatement;

	public IFbCReturn(final String name, final IFbCStatement returnStatement) {
		this.name = name;
		this.returnStatement = returnStatement;
	}

	@Override
	public TypeableResult isTypeable(final Lattice lattice, final String projectName,
			final Map<String, List<IFbCReferenceEntity>> elevatedEntities, final Map<String, String> changedTypes,
			final List<String> usedCapsules, final String optionalGuardSL, final Method constructingMethod)
			throws IFbCException {
		System.out.println("IFbCReturn: " + this.toString());

		final String definedMethodSL = constructingMethod.getSecurityLevel();
		final MDF definedMethodMDF = constructingMethod.getMutationModifier();
		String statementSL;
		final MDF statementMDF;

		switch (returnStatement.getType()) {
			case Assignment :
				return new TypeableResult(false, "Return statement should not contain assignment. Not typeable",
						elevatedEntities, changedTypes, usedCapsules);
			case MethodCall :
				final IFbCMethod methodStmt = (IFbCMethod) returnStatement;
				final TypeableResult typeableResult = methodStmt.isTypeable(lattice, projectName, elevatedEntities,
						changedTypes, usedCapsules, optionalGuardSL, constructingMethod);
				if (!typeableResult.isTypeable()) {
					return new TypeableResult(false, "Method in return statement was not typeable. (Reason: "
							+ typeableResult.getMessage() + ")", elevatedEntities, changedTypes, usedCapsules);
				}

				System.out.println("Embedded method in return is typeable.");
				System.out.println("Method after typeable call: " + methodStmt);

				statementSL = methodStmt.getStatementSL();
				statementMDF = methodStmt.getStatementMDF();

				break;
			case Return :
				return new TypeableResult(false, "Return statement should not contain return-statement. Not typeable",
						elevatedEntities, changedTypes, usedCapsules);
			case Reference :
				final IFbCReference reference = (IFbCReference) returnStatement;
				final String referenceName = reference.getStatement();

				statementMDF = reference.getTypeModifier();
				statementSL = reference.getSecurityLevel();
				for (Entry<String, List<IFbCReferenceEntity>> entry : elevatedEntities.entrySet()) {
					final String level = entry.getKey();
					boolean changed = false;
					for (IFbCReferenceEntity entity : entry.getValue()) {
						if (entity.getName().equals(referenceName)) {
							statementSL = level;
							changed = true;
							break;
						}
						if (changed) {
							break;
						}
					}
				}
				break;
			default :
				return new TypeableResult(false, "ERROR: Unsupported type in return statement.", elevatedEntities,
						changedTypes, usedCapsules);

		}

		// Check MDF of defined method and returned statement
		if (!MDF.isSubtypeOf(statementMDF, definedMethodMDF)) {
			return new TypeableResult(
					false, "Returned MDF " + statementMDF + " is not equal to defined MDF " + definedMethodMDF
							+ " of method " + constructingMethod.getName() + ".",
					elevatedEntities, changedTypes, usedCapsules);
		}

		// defined SL of method about to be constructed equal to the SL of the returned
		// object?
		if (statementSL.equals(definedMethodSL)) {
			return new TypeableResult(true,
					"Return is typeable since defined method return type SL (" + definedMethodSL + ") for method "
							+ constructingMethod.getName() + "() and SL of returned object  (" + statementSL
							+ ") is equal.",
					elevatedEntities, changedTypes, usedCapsules);
		}

		final Node statementSLNode = lattice.getNodePerName(statementSL);
		final Node definedMethodSLNode = lattice.getNodePerName(definedMethodSL);

		if (statementSLNode == null || definedMethodSLNode == null) {
			return new TypeableResult(false,
					"SL for either " + statementSL + " or " + definedMethodSL + " is not defined in projects lattice.",
					elevatedEntities, changedTypes, usedCapsules);
		}

		// Promotion only for returned object
		if (LeastUpperBound.secondHigherThanFirst(statementSLNode, definedMethodSLNode)) {
			// promotion of returned object possible?
			if (statementMDF.equals(MDF.IMMUTABLE) | statementMDF.equals(MDF.CAPSULE)) {
				return new TypeableResult(true, "Return is typeable because SEC-PROM of returned object (SL: "
						+ statementSL + ", MDF: " + statementMDF + ")  is possible. ", elevatedEntities, changedTypes,
						usedCapsules);
			}

			return new TypeableResult(false, "Return is not typeable because SEC-PROM of returned object (SL: "
					+ statementSL + ", MDF: " + statementMDF + ")  is not possible. ", elevatedEntities, changedTypes,
					usedCapsules);
		} else {
			return new TypeableResult(false,
					"Defined method SL is lower than the SL of the returned object. Breaking method specification.",
					elevatedEntities, changedTypes, usedCapsules);
		}
	}

	@Override
	public StatementType getType() {
		return StatementType.Return;
	}

	@Override
	public String getStatement() {
		return returnStatement.getStatement();
	}

	@Override
	public String toString() {
		return "IFbCReturn [name=" + name + ", returnStatement=" + returnStatement + "]";
	}

}
