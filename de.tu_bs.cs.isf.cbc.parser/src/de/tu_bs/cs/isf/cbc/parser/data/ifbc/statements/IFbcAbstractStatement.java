package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.ConstructorDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCEntity.EntityType;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCFieldAccessEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCNewEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public abstract class IFbcAbstractStatement implements IFbCStatement {

	@Override
	public abstract TypeableResult isTypeable(Lattice lattice,
	                                          String projectName,
	                                          Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                          Map<String, String> changedTypes,
	                                          List<String> usedCapsules,
	                                          String optionalGuardSL,
	                                          Method constructingMethod)
			throws IFbCException;

	@Override
	public abstract StatementType getType();

	@Override
	public abstract String getStatement();

	
	/**
	 * Check if a provided parsed MDF (from a diagram) is a subtype of a provided defined subtype (from a code file)
	 *  
	 * @param elevatedEntities Map of elevated entities, in case a entity needs to be elevated
	 * @param changedTypes Map of changed entities, in case a entity needs to be changed
	 * @param usedCapsules List of used capsules, in case a capsule reference is used
	 * @param parsedParameter the parsed parameter for logging info
	 * @param parsedParamMDF the parsed parameters MDF
	 * @param definedParamMDF the defined parameters MDF
	 * 
	 * @return Returns true if the parsed parameter is a subtype of the defined parameter, false otherwise.
	 */
	protected TypeableResult checkMDFSubtype(final Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                         final Map<String, String> changedTypes,
	                                         final List<String> usedCapsules,
	                                         final ParameterDefinition parsedParameter,
	                                         final MDF parsedParamMDF,
	                                         final MDF definedParamMDF) {
		if (definedParamMDF.equals(MDF.CAPSULE) && !parsedParamMDF.equals(MDF.CAPSULE)) {
			return new TypeableResult(false,
					"Defined paramters MDF is CAPSULE, runtime parameter can only be CAPSULE as well, is " + parsedParamMDF
							+ ". (Parameter: " + parsedParameter.toString() + ")",
					elevatedEntities, changedTypes, usedCapsules);
		}

		if (definedParamMDF.equals(MDF.MUTABLE)
				&& (!parsedParamMDF.equals(MDF.MUTABLE) && !parsedParamMDF.equals(MDF.CAPSULE))) {
			return new TypeableResult(false,
					"Defined paramters MDF is MUTABLE, runtime parameter can only be in (MUTABLE | CAPSULE), is "
							+ parsedParamMDF + ". (Parameter: " + parsedParameter.toString() + ")",
					elevatedEntities, changedTypes, usedCapsules);
		}

		if (definedParamMDF.equals(MDF.IMMUTABLE)
				&& (!parsedParamMDF.equals(MDF.IMMUTABLE) && !parsedParamMDF.equals(MDF.CAPSULE))) {
			return new TypeableResult(false,
					"Defined paramters MDF is IMMUTABLE, runtime parameter can only be in (IMMUTABLE | CAPSULE), is "
							+ parsedParamMDF + ". (Parameter: " + parsedParameter.toString() + ")",
					elevatedEntities, changedTypes, usedCapsules);
		}

		return new TypeableResult(true, "MDF subtype check was valid.", elevatedEntities, changedTypes, usedCapsules);
	}
	
	protected TypeableResult checkParameterDefintion(Lattice lattice,
	                                          String projectName,
	                                          Map<String, List<IFbCReferenceEntity>> elevatedEntities,
	                                          Map<String, String> changedTypes,
	                                          List<String> usedCapsules,
	                                          Map<String, JavaClass> javaClassesForProject,
	                                          ParameterDefinition parameter) throws IFbCException {
		final String parsedParameterSL;
		final MDF parsedParamMDF;
		if (parameter.getOptionalEntity() != null) {
			final IFbCReferenceEntity optionalEntity = parameter.getOptionalEntity();
			if (optionalEntity.getEntityType().equals(EntityType.NEW)) {
				System.out.println("Analyzing SL/MDF for New entity.");
				final IFbCNewEntity newEntityParam = (IFbCNewEntity) optionalEntity;
				final TypeableResult checkNewEntityResult = checkNewEntity(newEntityParam, javaClassesForProject, newEntityParam.getType(), projectName, elevatedEntities, changedTypes, usedCapsules, lattice);
				if (!checkNewEntityResult.isTypeable()) {
					return checkNewEntityResult;
				} 
				
				parsedParameterSL = checkNewEntityResult.getSecurityLevel();
				parsedParamMDF = checkNewEntityResult.getMutationModifier();
			} else if (optionalEntity.getEntityType().equals(EntityType.FIELD_ACCESS)) {
				System.out.println("Analyzing SL/MDF for Field Access entity.");
				final IFbCFieldAccessEntity fieldAccess = (IFbCFieldAccessEntity) optionalEntity;	
				
				final List<Node> scopeSLs = fieldAccess.getScopeSecurityLevels(lattice);				
				parsedParameterSL = LeastUpperBound.leastUpperBound(scopeSLs, lattice).getName();
				parsedParamMDF = fieldAccess.getCombinedMDF();
			} else if (optionalEntity.getEntityType().equals(EntityType.METHOD)) {
				System.out.println("Analyzing SL/MDF for Method entity.");
				final IFbCMethodEntity methodEntityParam = (IFbCMethodEntity) optionalEntity;
				IFbCMethod method = new IFbCMethod(methodEntityParam.getName(), methodEntityParam);
				final TypeableResult checkMethodEntityResult = method.isTypeable(lattice, projectName, elevatedEntities, changedTypes, usedCapsules, null, methodEntityParam.getDefinedMethod());
				if (!checkMethodEntityResult.isTypeable()) {
					return checkMethodEntityResult;
				} 
				
				parsedParameterSL = method.getStatementSL();
				parsedParamMDF = method.getStatementMDF();
			} else {
				return new TypeableResult(
						false, "EntityType " + optionalEntity.getEntityType() + " not implemented for parsed parameter of constructor or method.",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
		} else {
			parsedParameterSL = parameter.getSecurityLevel();
			parsedParamMDF = parameter.getMutationModifier();
		}
		System.out.println("Determined SL '" + parsedParameterSL + "' for parameter '" + parameter.getName() + "'.");
		return new TypeableResult(true, "Parameter check valid", elevatedEntities, changedTypes, usedCapsules, parsedParameterSL, parsedParamMDF);		
	}
	
	protected TypeableResult checkNewEntity(IFbCNewEntity newEntity, Map<String, JavaClass> javaClassesForProject, String constructorName, String projectName, Map<String, List<IFbCReferenceEntity>> elevatedEntities, Map<String, String> changedTypes, List<String> usedCapsules, Lattice lattice) throws IFbCException {
		System.out.println("Checking new entity: " + newEntity + " with constructorName :" + constructorName);
		
		String valueSL;
		MDF valueMDF;
		
		final List<ParameterDefinition> parameters = newEntity.getParameters();
		final JavaClass javaClass =  JavaClasses.getClassIgnoringGeneric(javaClassesForProject, constructorName);
		if (javaClass == null) {
			throw new IFbCException("Java Class for name " + constructorName + " is not defined.");
		}
		final ConstructorDefinition constructor = javaClass.getConstructor();
		if (constructor == null) {
			throw new IFbCException("Constructor for class " + constructorName + " is not defined.");
		}

		System.out.println("Parsed parameters: " + parameters.toString());
		System.out.println("Defined parameters: " + constructor.getParameters());

		if (parameters.size() != constructor.getParameters().size()) {
			throw new IFbCException(
					"Defined number of parameters and parsed number of parameters differ in size. (defined: "
							+ constructor.getParameters().size() + ", parsed: " + parameters.size() + ")");
		}

		// check if all parsed parameters are subtypes of the defined ones
		final List<Node> breakingMutAndReadSLs = new ArrayList<>();
		final ListIterator<ParameterDefinition> parsedIt = parameters.listIterator();
		final ListIterator<ParameterDefinition> definedIt = constructor.getParameters().listIterator();
		for (int i = 0; i < parameters.size(); i++) {
			final ParameterDefinition parsedParameter = parsedIt.next();
			final ParameterDefinition definedParameter = definedIt.next();
			
			// Workaround for null literals - null pointer are irrelevant for information flow
			if (parsedParameter.getName().equals("null")) {
				continue;
			}

			// Check if runtime parameters type is subtype of defined parameters type
			if (!JavaClasses.isRuntimeParameterMatchingSignature(parsedParameter.getType(),
					definedParameter.getType(), projectName)) {
				return new TypeableResult(
						false, "Runtime parameter " + parsedParameter.getType()
								+ " is no subtype of defined parameter " + definedParameter.getType() + ".",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}

			final MDF definedParamMDF = definedParameter.getMutationModifier();
			
			// Check if the parsed parameter needs to be typechecked itself
			final  TypeableResult checkParameterSL = checkParameterDefintion(lattice, projectName, elevatedEntities, changedTypes, usedCapsules, javaClassesForProject, parsedParameter);
			if (!checkParameterSL.isTypeable()) {
				return checkParameterSL;
			}
			
			final String parsedParameterSL = checkParameterSL.getSecurityLevel();
			final MDF parsedParamMDF = checkParameterSL.getMutationModifier();

			final Node parsedParamSLNode = lattice.getNodePerName(parsedParameterSL);
			final Node definedParamSLNode = lattice.getNodePerName(definedParameter.getSecurityLevel());

			// Check if runtime parameters SL is a subtype of defined parameter SL
			if (parsedParamSLNode == null || definedParamSLNode == null) {
				throw new IFbCException("SL for defined or parsed parameter could not be found. (defined: "
						+ definedParameter.toString() + ", parsed: " + parsedParameter.toString());
			}
			
			if (LeastUpperBound.secondHigherThanFirst(parsedParamSLNode, definedParamSLNode)
					&& !definedParamSLNode.getName().equals(parsedParamSLNode.getName())) {
				if (!isSLPromotable(parsedParamSLNode, definedParamSLNode)) {
					return new TypeableResult(
							false, "Defined parameter security level (" + parsedParamSLNode.getName() + ") is not reachable from  parsed parameter security level (" + parsedParamSLNode.getName() +"). Cannot locate parameter locally.", 
							elevatedEntities, changedTypes, usedCapsules);
				}
				if (parsedParamMDF.equals(MDF.MUTABLE) || parsedParamMDF.equals(MDF.READ)) {
					// Calculate lub() of defined and parsed SL and add it to the list of SLs to be
					// used to determine the valueSL
					final Node lub = LeastUpperBound
							.leastUpperBound(Arrays.asList(parsedParamSLNode, definedParamSLNode), lattice);
					breakingMutAndReadSLs.add(lub);
					System.out.println("Adding lub(" + parsedParamSLNode.getName() + ", "
							+ definedParamSLNode.getName() + ") to breakingMutAndReadSLs. (Parameters: (defined: "
							+ definedParameter.toString() + ", parsed: " + parsedParameter.toString() + ")");
				} else {
					// parsed MDF is IMMUTABLE or CAPSULE, can be locally promoted and nothing needs
					// to be done here
					if (parsedParamMDF.equals(MDF.CAPSULE)) {
						if (usedCapsules.contains(parsedParameter.getName())) {
							return new TypeableResult(
									false, "Runtime parameter " + parsedParameter.getName()
											+ " is has the type modifier CAPSULE and was already used before. Cannot promote locally.",
									elevatedEntities, changedTypes, usedCapsules);
						} else {
							usedCapsules.add(parsedParameter.getName());
						}
					}
					System.out.println(
							"parsed MDF is IMMUTABLE or CAPSULE, can be locally promoted and nothing needs to be done here.");
					// return new TypeableResult(false, "Runtime parameters SL " +
					// parsedParamSLNode.getName() + " is no subtype of defined parameter SL" +
					// definedParamSLNode.getName() + ".", elevatedEntites);
				}
			}

			// Check if runtime parameters MDF is a subtype of defined parameter MDF
			final TypeableResult checkMDFSubtype = checkMDFSubtype(elevatedEntities, changedTypes, usedCapsules, parsedParameter, parsedParamMDF,
					definedParamMDF);
			if (!checkMDFSubtype.isTypeable()) {
				return checkMDFSubtype;
			}
		}

		// Determine SL of expression
		if (breakingMutAndReadSLs.isEmpty()) {
			// All parameters are okay and no SL needs to be determined, using bottom of
			// lattice.
			System.out
					.println("All parameters are okay and no SL needs to be determined, using bottom of lattice.");
			valueSL = lattice.getBottom().getName();
		} else {
			System.out.println("Need to caculate lub() of all breaking SLs: " + breakingMutAndReadSLs.toString());
			final Node lub = LeastUpperBound.leastUpperBound(breakingMutAndReadSLs, lattice);
			if (lub == null) {
				return new TypeableResult(false,
						"No lub() for the following SLs found: " + breakingMutAndReadSLs.toString(),
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
			System.out.println("lub() is: " + lub.getName());

			valueSL = lub.getName();
		}

		// Determine MDF of expression. If only one of the parameters is not IMMUTABLE,
		// the value is MUTABLE.
		valueMDF = MDF.MUTABLE;
		if (!parameters.isEmpty() && parameters.stream().allMatch(p -> p.getMutationModifier().equals(MDF.IMMUTABLE))) {
			valueMDF = MDF.IMMUTABLE;
		}
		
		return new TypeableResult(true, "NewEntity is typeable.", elevatedEntities, changedTypes, usedCapsules, valueSL, valueMDF);
	}
	
	/**
	 * Check if a security level is promotable to another security level, i.e. if there reachable going upwards from the starting security level.
	 * 
	 * @return
	 */
	protected Boolean isSLPromotable(final Node startingSL, final Node targetSL) {
		return LeastUpperBound.secondHigherThanFirst(startingSL, targetSL);
	}
}
