package de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCMethodEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class IFbCMethod extends IFbcAbstractStatement {

	private final String name;
	private final IFbCMethodEntity entity;
	private String statementSL;
	private MDF statementMDF;
	
	public IFbCMethod(final String name,
	                  final IFbCMethodEntity entity) {
		this.name = name;
		this.entity = entity;
	}
	
	@Override
	public TypeableResult isTypeable(final Lattice lattice, 
	                                 final String projectName, 
	                                 final Map<String, List<IFbCReferenceEntity>> elevatedEntities, 
	                                 final Map<String, String> changedTypes, 
	                                 final List<String> usedCapsules,
	                                 final String optionalGuardSL,
	                                 final Method constructingMethod) throws IFbCException {
		final Map<String, JavaClass> javaClassesForProject = JavaClasses.getJavaClassesForProject(projectName);
		final String returnSL = entity.getSecurityLevel();
		final MDF returnMDF = entity.getMutationModifier();
		final String runtimeReceiverSL = entity.getMethodSecurityLevel();
		final MDF runtimeReceiverMDF = entity.getMethodMDF();
		final String definedReceiverSL = entity.getDefinedMethod().getReceiverSL();
		final MDF definedReceiverMDF = entity.getDefinedMethod().getReceiverMDF();
		
		final List<ParameterDefinition> parsedParameters = entity.getParameters();
		final String definingClassName = changedTypes.get(entity.getName()) != null ? changedTypes.get(entity.getName()) : entity.getDefiningClassName();
		final JavaClass javaClass = javaClassesForProject.get(definingClassName);
		if (javaClass == null) {
			throw new IFbCException("Java Class for name " + definingClassName + " is not defined.");
		}
		final Method method = javaClass.getMethodByNameAndParameterSize(entity.getName(), parsedParameters);
		if (method == null) {
			throw new IFbCException("Method for name " + entity.getName() + " with parameters "
					+ parsedParameters.toString() + " is not defined.");
		}

		System.out.println("Defined Method receiver: " + definedReceiverSL);
		System.out.println("Runtime Method receiver: " + runtimeReceiverSL);
		System.out.println("Parsed parameters: " + parsedParameters.toString());
		System.out.println("Defined parameters: " + method.getParameterList());

		// check if all parsed parameters are subtypes of the defined ones
		final List<Node> breakingMutAndReadSLs = new ArrayList<>();
		final ListIterator<ParameterDefinition> parsedIt = parsedParameters.listIterator();
		final ListIterator<ParameterDefinition> definedIt = method.getParameterList().listIterator();
		for (int i = 0; i < parsedParameters.size(); i++) {
			final ParameterDefinition parsedParameter = parsedIt.next();
			final ParameterDefinition definedParameter = definedIt.next();

			// Check if runtime parameters type is subtype of defined parameters type
			if (!JavaClasses.isRuntimeParameterMatchingSignature(parsedParameter.getType(),
					definedParameter.getType(), projectName)) {
				return new TypeableResult(
						false, "Runtime parameter " + parsedParameter.getType()
								+ " is no subtype of defined parameter " + definedParameter.getType() + ".",
						elevatedEntities, changedTypes, usedCapsules);
			}
			
			// Check if the parsed parameter needs to be typechecked itself
			final  TypeableResult checkParameterSL = checkParameterDefintion(lattice, projectName, elevatedEntities, changedTypes, usedCapsules, javaClassesForProject, parsedParameter);
			if (!checkParameterSL.isTypeable()) {
				return checkParameterSL;
			}
			
			final String parsedParameterSL = checkParameterSL.getSecurityLevel();
			final MDF parsedParamMDF = checkParameterSL.getMutationModifier();
			
			final MDF definedParamMDF = definedParameter.getMutationModifier();

			final Node parsedParamSLNode = lattice.getNodePerName(parsedParameterSL);
			final Node definedParamSLNode = lattice.getNodePerName(definedParameter.getSecurityLevel());

			// Check if runtime parameters SL is a subtype of defined parameter SL
			if (parsedParamSLNode == null || definedParamSLNode == null) {
				throw new IFbCException("SL for defined or parsed parameter could not be found. (defined: "
						+ definedParameter.toString() + ", parsed: " + parsedParameter.toString());
			}
			
			System.out.println("Checking SL for parameters. parsed param SL: " + parsedParamSLNode.getName() + ", defined param SL: " + definedParamSLNode.getName());
			if (LeastUpperBound.secondHigherThanFirst(definedParamSLNode, parsedParamSLNode)
					&& !definedParamSLNode.getName().equals(parsedParamSLNode.getName())) {
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
		
		if (!method.getStaticMethod()) {
			// check if defined and runtime receiver are matching, i.e. runtime is >= defined
			final Node definedReceiverSLNode = lattice.getNodePerName(definedReceiverSL);
			final Node runtimeReceiverSLNode = lattice.getNodePerName(runtimeReceiverSL);

			if (definedReceiverSLNode == null || runtimeReceiverSLNode == null) {
				throw new IFbCException("SL for defined or runtime security level could not be found. (defined: "
						+ definedReceiverSL + ", runtime: " + runtimeReceiverSL);
			}
			if (!definedReceiverSL.equals(runtimeReceiverSL) && LeastUpperBound.secondHigherThanFirst(runtimeReceiverSLNode, definedReceiverSLNode)) {
				return new TypeableResult(false,
						"Defined receiver SL (" + definedReceiverSL + ") for method " + entity.getName() + " is higher than runtime SL for receiver (" + runtimeReceiverSL + ").",
						elevatedEntities,
						changedTypes,
						usedCapsules);
			}
			
			if (!method.getVoidMethod()) {
				// check if runtime receiver sl >= defined return type sl
				final Node returnSLNode = lattice.getNodePerName(returnSL);
				System.out.println("Lattice: " + lattice.getNodes().toString());
				if (returnSLNode == null) {
					throw new IFbCException(
							"SL for return type security level (" + runtimeReceiverSL + ") could not be found.");
				}
				System.out.println("Comparing " + returnSL + " to " + runtimeReceiverSL);
				if (!returnMDF.equals(MDF.IMMUTABLE) && !returnMDF.equals(MDF.CAPSULE) && !returnSL.equals(runtimeReceiverSL)
						&& LeastUpperBound.secondHigherThanFirst(returnSLNode, runtimeReceiverSLNode)) {
					return new TypeableResult(false,
							"Defined return type SL (" + returnSL + ") for method " + entity.getName()
									+ " is lower than runtime SL for receiver (" + runtimeReceiverSL + ").",
							elevatedEntities, changedTypes, usedCapsules);
				} 
			}
		}
		
		// Determine SL of expression
		if (breakingMutAndReadSLs.isEmpty()) {
			// All parameters are okay and no SL needs to be determined, using defined one.
			System.out
					.println("All parameters are okay and no SL needs to be determined, using defined return type SL.");
			this.statementSL = method.getVoidMethod() ? lattice.getBottom().getName() : method.getSecurityLevel();
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

			this.statementSL = lub.getName();
		}
		
		if (!method.getStaticMethod()) {
			// Check if receiver SL is higher than expression SL -> not typeable
			final Node statementSLNode = lattice.getNodePerName(this.statementSL);
			final Node runtimeReceiverSLNode = lattice.getNodePerName(runtimeReceiverSL);
			if (statementSLNode == null) {
				throw new IFbCException("SL for statement could not be found: " + this.statementSL);
			}
			if (runtimeReceiverSLNode == null) {
				throw new IFbCException("SL " + runtimeReceiverSL + " for runtime receiver SL could not be found: " + runtimeReceiverSL);
			}
			
			if (!runtimeReceiverSL.equals(this.statementSL) && LeastUpperBound.secondHigherThanFirst(statementSLNode, runtimeReceiverSLNode)) {
				if (parsedParameters == null || parsedParameters.isEmpty()) {
					return new TypeableResult(false,
							"Runtime receiver SL (" + runtimeReceiverSL + ") is higher than expression SL (" + this.statementSL  +"). Need to use parameters with a higher SL. No automatic promotion possible without parameters.",
							elevatedEntities,
							changedTypes,
							usedCapsules);
				}
				
				for (ParameterDefinition parsedParameter : parsedParameters) {
					// Check if SL of receiver is reachable from the parameter SL
					final TypeableResult checkParameterDefintion = checkParameterDefintion(lattice, projectName, elevatedEntities, changedTypes, usedCapsules, javaClassesForProject, parsedParameter);
					final String parameterSL = checkParameterDefintion.getSecurityLevel();
					final Node parameterSLNode = lattice.getNodePerName(parameterSL);
					if (!isSLPromotable(parameterSLNode, runtimeReceiverSLNode)) {
						return new TypeableResult(
								false, "Runtime receiver security level (" + runtimeReceiverSLNode.getName() + ") is not reachable from parsed parameter security level (" + parameterSLNode.getName() +"). Cannot locate parameter locally.", 
								elevatedEntities, changedTypes, usedCapsules);
					}
					
					// Check if promotion is possible
					final MDF parameterMDF = parsedParameter.getMutationModifier();
					if (!parameterMDF.equals(MDF.IMMUTABLE) && !parameterMDF.equals(MDF.CAPSULE)) {
						return new TypeableResult(false,
								"Runtime receiver SL (" + runtimeReceiverSL + ") is higher than expression SL (" + this.statementSL  +"). "
										+ "Need to use parameters with a higher SL. "
										+ "\n\tNo automatic promotion of parameters possible since MDF of parameter '" + parsedParameter.getName() + "' is not IMM|CAPS.",
								elevatedEntities,
								changedTypes,
								usedCapsules);
					}
				}			
				
				System.out.println("Local promotion of all parameters of method call " + method.getName() + " was possible to achieve receiver SL " + runtimeReceiverSL + ".");
			}
		}		

		// Determine MDF of expression. If only one of the parameters is not IMMUTABLE,
		// the value is MUTABLE.
		if (method.getVoidMethod()) {
			if (parsedParameters.stream().allMatch(p -> p.getMutationModifier().equals(MDF.IMMUTABLE))) {
				this.statementMDF = MDF.IMMUTABLE;
			} else {
				this.statementMDF = MDF.MUTABLE;
			}
		} else {
			this.statementMDF = returnMDF;
		}
		
		return new TypeableResult(true,
				"Method  " + entity.getStatement() + " is typeable.",
				elevatedEntities,
				changedTypes,
				usedCapsules);
	}

	public String getStatementSL() {
		return statementSL;
	}

	public void setStatementSL(String statementSL) {
		this.statementSL = statementSL;
	}

	public MDF getStatementMDF() {
		return statementMDF;
	}

	public void setStatementMDF(MDF statementMDF) {
		this.statementMDF = statementMDF;
	}

	@Override
	public StatementType getType() {
		return StatementType.MethodCall;
	}

	@Override
	public String getStatement() {
		return entity.getStatement();
	}

	@Override
	public String toString() {
		return "IFbCMethod [name=" + name + ", entity=" + entity + ", statementSL=" + statementSL + ", statementMDF="
				+ statementMDF + "]";
	}

}
