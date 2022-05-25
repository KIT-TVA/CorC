package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ConstructorDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCDeclassifyEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAccessEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAssignEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCNewEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCEntity.EntityType;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class IFbCAssignment extends IFbcAbstractStatement {

	private final IFbCReferenceEntity target;
	private final IFbCReferenceEntity value;
	private final String statement;

	public IFbCAssignment(final IFbCReferenceEntity targetEntity, final IFbCReferenceEntity valueEntity,
			final String statement) {
		this.target = targetEntity;
		this.value = valueEntity;
		this.statement = statement;
	}

	@Override
	public StatementType getType() {
		return StatementType.Assignment;
	}

	@Override
	public TypeableResult isTypeable(final Lattice lattice, 
	                                 final String projectName, 
	                                 final Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                 final Map<String, String> changedTypes,
	                                 final List<String> usedCapsules,
	                                 final String optionalGuardSL,
	                                 final Method constructingMethod,
	                                 final Map<String, String> specification) throws IFbCException {
		final Map<String, JavaClass> javaClassesForProject = JavaClasses.getJavaClassesForProject(projectName);

		if (javaClassesForProject == null || javaClassesForProject.isEmpty()) {
			throw new IFbCException("No java classes for project " + projectName + " present or no classes defined.");
		}
		
		// DEBUG: elevatedEntities
		elevatedEntities.forEach((level, list) -> {
			System.out.println("Elevated entities for level " + level + " are: " + list.toString());
		});
		
		// DEBUG: usedCapsules
		usedCapsules.forEach(c -> System.out.println("Used capsule reference " + c));

		String targetSL = target.getSecurityLevel();
		String valueSL = value.getSecurityLevel();

		final MDF targetMDF = target.getMutationModifier();
		MDF valueMDF = value.getMutationModifier();
		
		String targetTypeScope = target.getType();
		String valueReturnType = value.getType();	
		
		String valueType = value.getType();
		
		/**
		 * Check optional guard security level from a potential selection containing this statement
		 * 
		 * If optionalGuardSL is != null, then this method was called in the context of a selection.
		 * 
		 * 1) TODO: If guardSL is == bottom of the lattice (meaning the lowest SL), both branches have to have the 
		 *	same SL. Possibly return the SL of the branch here in the TypeableResult?
		 *
		 * 2) If the guard SL is > bottom of the lattice, we restrict mutation (=assignment) in two ways:
		 * 		a) if the target is a non-local variable (meaning defined in a class, not in the diagram), an assignment
		 * 		to mutable targets is only possible if the security level of the target is >= guardSL
		 * 
		 * 		b) if the target is a local variable (meaning defined in the diagram), an assignment is only possible
		 * 		if the target SL is >= guardSL, disregarding the type modifier of the target
		 */
		if (optionalGuardSL != null) {
			final Node guardSLNode = lattice.getNodePerName(optionalGuardSL);
			if (guardSLNode == null) {
				return new TypeableResult(
						false, "Cannot find SL node in lattice for guard: " + optionalGuardSL, elevatedEntities, changedTypes, usedCapsules);	
			}
			if (optionalGuardSL.equals(lattice.getBottom().getName())) {
				// Case 1, possibly just set a boolean to return the complete SL of the assigment later on
				// TODO: implement
			} else {
				// Case 2
				if (target.getEntityType().equals(EntityType.FIELD_ASSIGN)) {
					// Case 2a
					// TODO: implement
					
				} else {
					// Case 2b
					final Node targetSLNode = lattice.getNodePerName(targetSL);
					if (targetSLNode == null) {
						return new TypeableResult(
								false,  "Cannot find SL node in lattice for guard: " + targetSL,
								elevatedEntities, changedTypes, usedCapsules);	
					}
					
					if (!targetSL.equals(optionalGuardSL) && !LeastUpperBound.secondHigherThanFirst(guardSLNode, targetSLNode)) {
						// try sec-prom
						if (targetMDF.equals(MDF.CAPSULE) || targetMDF.equals(MDF.IMMUTABLE)) {
							// Elevation
							if (targetMDF.equals(MDF.CAPSULE)) {
								if (usedCapsules.contains(target.getName())) {
									return new TypeableResult(
											false, "Target " + target.getName()
													+ " has the type modifier CAPSULE and was already used before. Cannot promote locally.",
											elevatedEntities, changedTypes, usedCapsules);
								}
//								} else {
//									usedCapsules.add(target.getName());
//								}
							}
							
							if (elevatedEntities.get(optionalGuardSL) == null) {
								elevatedEntities.put(optionalGuardSL, new ArrayList<>());
							}
							final List<IFbCReferenceEntity> elevatedForSLList = elevatedEntities.get(optionalGuardSL);
							elevatedForSLList.add(target);
							elevatedEntities.put(optionalGuardSL, elevatedForSLList);
							targetSL = optionalGuardSL;
						} else {
							return new TypeableResult(
									false, "Targets " + target.getName()
									+ " security level cannot be promoted, modifier " + targetMDF.toString() + " is not in IMM|CAPS.",
							elevatedEntities, changedTypes, usedCapsules);
						}
					}
				}
			}			
		}
		
		/**
		 * Assignment value checks regarding type system and determining real SL and MDF
		 * 
		 * values include:
		 * 
		 * - literals, object references, i.e. "e = f" - field access, i.e. "e = f.a" - field access
		 * through method return type, i.e "e = f.getFieldObject().text" - method return
		 * type, i.e. "e = f.getFieldObject()" - constructor, i.e. "e = new C()" -
		 * keyword declassify, i.e. "e = declassify(f)"
		 * combination with the above?
		 */

		// Check of value is of type IFbCMethodStatement and needs to be checked
		if (value.getEntityType().equals(EntityType.METHOD)) {
			final IFbCMethodEntity entity = (IFbCMethodEntity) value;
			
			if (entity.getType().equals("void")) {
				return new TypeableResult(
						false, "Method return type cannot be 'void' in assignment.",
						elevatedEntities, changedTypes, usedCapsules);				
			}
			
			IFbCMethod methodStatement = new IFbCMethod(entity.getName(), entity);
			final TypeableResult typeableResult = methodStatement.isTypeable(lattice, projectName, elevatedEntities, changedTypes, usedCapsules, optionalGuardSL, constructingMethod, specification);
			
			if (!typeableResult.isTypeable()) {
				return typeableResult;
			}
			
			valueSL = methodStatement.getStatementSL();
			valueMDF = methodStatement.getStatementMDF();
			valueReturnType = entity.getType();
		}
		if (value.getEntityType().equals(EntityType.FIELD_ACCESS)) {
			// Field access changes: value type, value SL and value MDF
			final IFbCFieldAccessEntity fieldAccess = (IFbCFieldAccessEntity) value;
			// variable/reference
			valueType = fieldAccess.getType();
			// f.a.b.c
			final List<Node> scopeSLs = fieldAccess.getScopeSecurityLevels(lattice);
			valueSL = LeastUpperBound.leastUpperBound(scopeSLs, lattice).getName();
			valueMDF = fieldAccess.getCombinedMDF();
			
			valueReturnType = valueType;
		}
		if (value.getEntityType().equals(EntityType.DECLASSIFY)) {
			final IFbCDeclassifyEntity declassify = (IFbCDeclassifyEntity) value;
			System.out.println("Lattice: " + lattice + ", lattice.getBottom(): " + lattice.getBottom());
			if (declassify.getParameterMDF().equals(MDF.READ) || declassify.getParameterMDF().equals(MDF.MUTABLE)) {
				return new TypeableResult(false,
						"Cannot declassify value because MDF is not IMMUTABLE or CAPSULE, is "
								+ declassify.getParameterMDF() + ".",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
			valueSL = targetSL;
			valueMDF = MDF.CAPSULE;			
			valueReturnType = "String";
		}
		if (value.getEntityType().equals(EntityType.NEW)) {
			final IFbCNewEntity newEntity = (IFbCNewEntity) value;
			final String constructorName = newEntity.getType();			
			valueReturnType = constructorName;
			final TypeableResult checkNewEntityResult = checkNewEntity(newEntity, javaClassesForProject, constructorName, projectName, elevatedEntities, changedTypes, usedCapsules, lattice);
			if (!checkNewEntityResult.isTypeable()) {
				return checkNewEntityResult;
			}
			
			System.out.println("New entity checked typeable with SL " + checkNewEntityResult.getSecurityLevel() + " and MDF " + checkNewEntityResult.getMutationModifier() + ".");
			
			valueSL = checkNewEntityResult.getSecurityLevel();
			valueMDF = checkNewEntityResult.getMutationModifier();
		}

		// Get nodes from lattice
		final Node targetSLNode = lattice.getNodePerName(targetSL);
		final Node valueSLNode = lattice.getNodePerName(valueSL);
		if (targetSLNode == null || valueSLNode == null) {
			return new TypeableResult(false,
					"Lattice nodes not present for either " + targetSL + " or " + valueSL + ".", 
					elevatedEntities,
					changedTypes,
					usedCapsules);
		}

		/**
		 * Assignment target checks regarding type system
		 * 
		 * targets include:
		 * 
		 * - object references, i.e. "f = e" - field assign, i.e. "f.a = e" - field
		 * assign through method return type, i.e. "f.getObjectWithField().text = e"
		 */
		if (target.getEntityType().equals(EntityType.FIELD_ASSIGN)) {
			final IFbCFieldAssignEntity entity = (IFbCFieldAssignEntity) target;
			final Node fieldSLNode = lattice.getNodePerName(entity.getSecurityLevel());
			final Node faValueSLNode = lattice.getNodePerName(value.getSecurityLevel());
			final List<IFbCReferenceEntity> fieldScopes = entity.getScopes();

			// No nodes found in lattice
			if (fieldSLNode == null) {
				throw new IFbCException("Lattice node for security level " + entity.getSecurityLevel() + "of scope "
						+ entity.getName() + " cannot be found.");
			}
			if (faValueSLNode == null) {
				throw new IFbCException("Lattice node for security level " + value.getSecurityLevel() + " of value "
						+ value.getName() + " cannot be found.");
			}

			// Check mutability of all scopes
			for (IFbCReferenceEntity scope : entity.getScopes()) {
				if (!scope.getMutationModifier().equals(MDF.MUTABLE)) {
					// Scope is not mutable
					return new TypeableResult(
							false, 
							"Scope  " + scope.getName() + " is not mutable.", 
							elevatedEntities,
							changedTypes,
							usedCapsules);
				}
			}

			// Check if value SL is equal to Least Upper Bound of all the scope SLs and the
			// field SL, therefore adding the fieldSL to the list of scopeSLs
			final List<Node> scopeLatticesNodes = entity.getScopeSecurityLevels(lattice);
			scopeLatticesNodes.add(fieldSLNode);
			final Node lubScopeField = LeastUpperBound.leastUpperBound(scopeLatticesNodes, lattice);
			if (!lubScopeField.getName().equals(valueSL)) {
				if (!valueMDF.equals(MDF.IMMUTABLE) && !valueMDF.equals(MDF.CAPSULE)) {
					// lub() of scopes and field is not equal to SL of value
					return new TypeableResult(false,
							"lub() of scopes and field (" + lubScopeField.getName() + ") is not equal to SL of value.",
							elevatedEntities,
							changedTypes,
							usedCapsules);
				}
			}
			targetSL = lubScopeField.getName();
			targetTypeScope = entity.getType();
		}		
		

		// SLs are equal
		if (targetSL.equals(valueSL)) {
			checkChangedTargetType(targetTypeScope, valueReturnType, changedTypes);
			final TypeableResult checkCapsuleUsage = checkCapsuleUsage(elevatedEntities, changedTypes, usedCapsules);
			if (checkCapsuleUsage != null) {
				return checkCapsuleUsage;
			}
			return new TypeableResult(true, "Security levels are equal.", elevatedEntities, changedTypes, usedCapsules);
		}

		// SL of target must be higher than SL of value
		// hier muss value nur lokal angehoben werden über promotion
		if (LeastUpperBound.secondHigherThanFirst(valueSLNode, targetSLNode)) {
			return tryLocalValuePromotion(lattice, elevatedEntities, changedTypes, usedCapsules, targetSL,
					targetTypeScope, valueReturnType, targetSLNode, valueSL, valueMDF);
			
		} else {
			// secondHigherThanFirst implies, that the SL of target node is below the SL of
			// value node, we need to elevate it globally
			if (target.getMutationModifier().equals(MDF.IMMUTABLE)
					|| target.getMutationModifier().equals(MDF.CAPSULE)) {
				// Check of this promotion would break the specification				
				final TypeableResult checkPromotionRegardingSpecification = checkPromotionRegardingSpecification(lattice, elevatedEntities, changedTypes, usedCapsules,
						specification, valueSL, valueSLNode);
				if (checkPromotionRegardingSpecification != null) {
					return checkPromotionRegardingSpecification;
				}
				
				// Capsule usage? 
				if (target.getMutationModifier().equals(MDF.CAPSULE)) {
					if (usedCapsules.contains(target.getName())) {
						return new TypeableResult(
								false, "Target " + target.getName()
										+ " has the type modifier CAPSULE and was already used before. Cannot promote locally.",
								elevatedEntities, changedTypes, usedCapsules);
					} 
				}
				
				// return new TypeableResult(false, "Runtime parameters SL " +
				// parsedParamSLNode.getName() + " is no subtype of defined parameter SL" +
				// definedParamSLNode.getName() + ".", elevatedEntites);
				if (elevatedEntities.get(valueSL) == null) {
					elevatedEntities.put(valueSL, new ArrayList<>());
				}
				final List<IFbCReferenceEntity> elevatedForSLList = elevatedEntities.get(valueSL);
				elevatedForSLList.add(target);
				elevatedEntities.put(valueSL, elevatedForSLList);
				checkChangedTargetType(targetTypeScope, valueReturnType, changedTypes);
				final TypeableResult checkCapsuleUsage = checkCapsuleUsage(elevatedEntities, changedTypes, usedCapsules);
				if (checkCapsuleUsage != null) {
					return checkCapsuleUsage;
				}
				return new TypeableResult(true, "target SL is lower than value SL. (using SEC-PROM)", elevatedEntities, changedTypes, usedCapsules);
			}

			return new TypeableResult(false,
					"target SL is lower than value SL. Promotion using SEC-PROM not possible because target MDF ("
							+ targetMDF + ") is not (imm|caps)",
					elevatedEntities, changedTypes, usedCapsules);
		}
	}

	private TypeableResult checkCapsuleUsage(final Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                         final Map<String, String> changedTypes,
	                                         final List<String> usedCapsules) {
		// capsule used as value?
		if (value.getMutationModifier().equals(MDF.CAPSULE)) {
			if (usedCapsules.contains(value.getName())) {
				return new TypeableResult(
						false, "Value " + value.getName()
								+ " has the type modifier CAPSULE and was already used before.",
						elevatedEntities, changedTypes, usedCapsules);
			} else {
				usedCapsules.add(value.getName());
			}
		}
		return null;
	}

	private TypeableResult checkPromotionRegardingSpecification(final Lattice lattice,
	                                                            final Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                                            final Map<String, String> changedTypes,
	                                                            final List<String> usedCapsules,
	                                                            final Map<String, String> specification,
	                                                            final String valueSL,
	                                                            final Node valueSLNode) {
		if (specification.containsKey(target.getName())) {
			final String specificationMaxLevel = specification.get(target.getName());
			final Node specificationMaxLevelNode = lattice.getNodePerName(specificationMaxLevel);
			if (specificationMaxLevelNode == null) {
				return new TypeableResult(
						false, "Specification level '" + specificationMaxLevel + "' for reference '" + target.getName() + "' does not exist in Lattice." ,
						elevatedEntities, changedTypes, usedCapsules);
			}
			
			if (LeastUpperBound.secondHigherThanFirst(specificationMaxLevelNode, valueSLNode)) {
				return new TypeableResult(
						false, "Using SEC-PROM to elevate target '" + target.getName() + "' to level '" + valueSL + "' is breaking specification of the reference.",
						elevatedEntities, changedTypes, usedCapsules);
			}
		}
		
		return null;
	}

	/**
	 * The value SL of the assignment is not conforming to the target SL, trying local promotion for the different entity types
	 *  
	 * @param lattice The used lattice for this type check
	 * @param elevatedEntities map of elevated entities
	 * @param changedTypes map of changed types
	 * @param usedCapsules list of capsule usage
	 * @param targetSL the target SL
	 * @param targetTypeScope the target type scope
	 * @param valueReturnType  the value return type
	 * @param targetSLNode target SL node from lattice
	 * @param valueMDF 
	 * @param valueSL 
	 * @return Returns an object of TypeableResult containing the result of the type check
	 * 
	 * @throws IFbCException
	 */
	private TypeableResult tryLocalValuePromotion(	final Lattice lattice,
													final Map<String, List<IFbCReferenceEntity>> elevatedEntities,
													final Map<String, String> changedTypes,
													final List<String> usedCapsules,
													final String targetSL,
													final String targetTypeScope,
													final String valueReturnType,
													final Node targetSLNode,
													final String valueSL, 
													final MDF valueMDF)
			throws IFbCException {
		// value is lower and must be elevated locally
		switch(value.getEntityType()) {
		case ENTITY: // just try to promote locally based on parsed modifier
			if (value.getMutationModifier().equals(MDF.IMMUTABLE) || value.getMutationModifier().equals(MDF.CAPSULE)) {
				// Capsule usage?
				if (value.getMutationModifier().equals(MDF.CAPSULE)) {
					if (usedCapsules.contains(value.getName())) {
						return new TypeableResult(
								false, "Value " + value.getName()
										+ " has the type modifier CAPSULE and was already used before. Cannot promote locally.",
								elevatedEntities, changedTypes, usedCapsules);
					} else {
						usedCapsules.add(value.getName());
					}
				}
				
				checkChangedTargetType(targetTypeScope, valueReturnType, changedTypes);
				return new TypeableResult(true, "target SL is higher than value SL. (using local promotion)",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			} else {				
				return new TypeableResult(false,
						"target SL is higher than value SL, local promotion not possible because value MDF is not (imm|caps).",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
		case FIELD_ACCESS:
			final IFbCFieldAccessEntity fieldAccess = (IFbCFieldAccessEntity) value;
			final List<MDF> scopeMDFs = fieldAccess.getScopeMDFs();
			final List<Node> scopeSecurityLevels = fieldAccess.getScopeSecurityLevels(lattice);
			
			// Check if the field access expression as a whole has MDF == caps || imm
			if (fieldAccess.getCombinedMDF().equals(MDF.IMMUTABLE) || fieldAccess.getCombinedMDF().equals(MDF.CAPSULE)) {
				return new TypeableResult(true,
						"Local promotion of field access to target SL " + targetSL + " was possible because combined MDF for expression is " + fieldAccess.getCombinedMDF() + ".",
						elevatedEntities,
						changedTypes,
						usedCapsules);	
			}
			
			// Check if one of the scopes can be elevated
			
			// Scope MDF and SL size consistend?
			if (scopeMDFs.size() != scopeSecurityLevels.size()) {
				return new TypeableResult(false,
						"Cannot promote field access scopes locally since size of scope MDFs differ from size of scope SLs.",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
			
			// Check scopes for elevation
			for (int i = 0; i < scopeSecurityLevels.size(); i++) {
				final Node currentSLNode = scopeSecurityLevels.get(i);
				final MDF currentMDF = scopeMDFs.get(i);
				
				if (targetSLNode.equals(currentSLNode)) {
					continue; // scope SL is equal to target SL -> all good
				} 
				if (LeastUpperBound.secondHigherThanFirst(targetSLNode, currentSLNode)) {
					continue; // scope SL is higher than target SL -> all good 
				}
				if (currentMDF.equals(MDF.IMMUTABLE) || currentMDF.equals(MDF.CAPSULE)) {
					continue; // scope SL is lower than target SL but local promotion is possible -> all good
				}
				// Cannot promote locally to target SL -> the field access will not reach the 
				return new TypeableResult(false,
						"Cannot use local promotion for scope " + fieldAccess.getScopes().get(i) + " of field access value.",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
			
			// all checks in the for-loop where successful -> we can elevate all scopes 
			return new TypeableResult(true,
					"Local promotion of field access to target SL " + targetSL + " was possible.",
					elevatedEntities,
					changedTypes,
					usedCapsules);	
		case METHOD:
			// Elevation possible or is only the defined SL for the return type possible? depending on defined MDF?
			if (valueMDF.equals(MDF.IMMUTABLE) || (valueMDF.equals(MDF.CAPSULE))) {
				return new TypeableResult(true,
						"Local promotion of method return object to target SL " + targetSL + " was possible because method MDF for expression is " + valueMDF + ".",
						elevatedEntities,
						changedTypes,
						usedCapsules);	
			} else {
				return new TypeableResult(false,
						"Local promotion of method return object to target SL " + targetSL + " was not possible because method MDF for expression is " + valueMDF + ".",
						elevatedEntities,
						changedTypes,
						usedCapsules);	
			}
		case NEW:
			// Shoudn't this be done by the developer only through using corresponding parameters for the constructor?
			return new TypeableResult(false,
					"Unsupported local promotion. (NEW)",
					elevatedEntities,
					changedTypes,
					usedCapsules);	
		default:
			return new TypeableResult(false,
					"Unsupported local promotion. (" + value.getEntityType() + ")",
					elevatedEntities,
					changedTypes,
					usedCapsules);			
		}
	}

	private void checkChangedTargetType(String targetTypeScope, String valueReturnType, final Map<String, String> changedTypes) {
		System.out.println("Checking if target type " +targetTypeScope + " differs from value type " + valueReturnType + ".");
		
		// remove generic type
		if (valueReturnType.contains("<")) {
			valueReturnType = valueReturnType.substring(0, valueReturnType.indexOf("<"));
			System.out.println("valueReturnType contains generic, removing it: " + valueReturnType);
		}
		if (!targetTypeScope.equals(valueReturnType) && !value.getLiteral()) {
			changedTypes.put(target.getName(), valueReturnType);
		}
	}

	public IFbCReferenceEntity getTarget() {
		return target;
	}

	public IFbCReferenceEntity getValue() {
		return value;
	}

	public String getStatement() {
		return statement;
	}
}
