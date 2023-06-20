package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.AtType;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Security;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.JavaConditionParser;
import de.tu_bs.cs.isf.cbc.parser.JavaStatementParser;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;
import de.tu_bs.cs.isf.cbc.parser.data.Method;
import de.tu_bs.cs.isf.cbc.parser.data.ParameterDefinition;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.TypeableResult;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.entities.IFbCReferenceEntity;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCAssignment;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCStatement;
import de.tu_bs.cs.isf.cbc.parser.data.ifbc.statements.IFbCStatement.StatementType;
import de.tu_bs.cs.isf.cbc.parser.exceptions.IFbCException;
import de.tu_bs.cs.isf.lattice.Lattice;
import de.tu_bs.cs.isf.lattice.Node;
import de.tu_bs.cs.isf.lattice.calculation.LeastUpperBound;

public class UpdateInformationFlow {

	/**
	 * Check a diagram regarding conformity to type system and refinement rules.
	 * Starting with the CbCFormula the entire diagram needs to be checked and
	 * optionally updated regarding security levels, capsule usages and type
	 * changes.
	 * 
	 * @param projectName
	 *            Name of the project containing the diagram
	 * @param statement
	 *            The statement the action was initiated from
	 * @param latticeBottom
	 *            The lattice bottom security level
	 * @param lattice
	 *            The lattice of the project
	 * 
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	public static void updateInformationFlow(	final String projectName,
												final AbstractStatement statement,
												final Lattice lattice)
			throws IFbCException {
		final Map<String, IFbCReferenceEntity> declaredVariables = getDeclaredVariables(statement);
		final Method constructingMethod = getLinkedMethod(statement, projectName);
		final CbCFormula cbCFormula = getCbCFormula(statement);
		if (cbCFormula == null) return;
		final AbstractStatement firstStatement = cbCFormula.getStatement().getRefinement();

		// Update the rest of the diagram
		final boolean typeableResult = updateDiagramBlock(firstStatement, projectName, lattice, declaredVariables,
				constructingMethod);

		// Update the information of the CbCFormula to be shown in the properties view
		System.out.println("Diagram is typeable: " + typeableResult);
		cbCFormula.getStatement().setTypeableResult(Boolean.toString(typeableResult));
		if (firstStatement != null) {
			copyConfToVars(firstStatement.getPostCondition(), cbCFormula.getStatement().getPostCondition());
			copyAtTypeToVars(firstStatement.getPostCondition(), cbCFormula.getStatement().getPostCondition());
			copyCapsulesUsed(firstStatement.getPostCondition(), cbCFormula.getStatement().getPostCondition());
		}
	}

	/**
	 * Delegates the current AbstractStatement to the correct method to check the
	 * implementation.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateDiagramBlock(	final AbstractStatement actualStatement,
												final String projectName,
												final Lattice lattice,
												final Map<String, IFbCReferenceEntity> declaredVariables,
												final Method constructingMethod)
			throws IFbCException {
		if (actualStatement instanceof CompositionStatement) {
			return updateCompositionStatement(projectName, actualStatement, lattice, declaredVariables,
					constructingMethod);
		} else if (actualStatement instanceof SmallRepetitionStatement) {
			return updateSmallRepetitionStatement(projectName, actualStatement, lattice, declaredVariables,
					constructingMethod);
		} else if (actualStatement instanceof SelectionStatement) {
			return updateSelectionStatement(projectName, actualStatement, lattice, declaredVariables,
					constructingMethod);
		} else if (actualStatement instanceof AbstractStatement) {
			return updateAbstractStatement(projectName, actualStatement, lattice, declaredVariables, constructingMethod);
		} else {
			if (actualStatement != null) {
				System.out.println("updateDiagramBlock() not implemented for instance of AbstractStatement: "
						+ actualStatement.getClass().getCanonicalName());
			}
			return false;
		}

	}

	/**
	 * Delegates the current AbstractStatement to the correct method to check the
	 * implementation. This method has a security context from a guard residing in
	 * guardSL.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateDiagramBlock(	final AbstractStatement actualStatement,
												final String projectName,
												final Lattice lattice,
												final Map<String, IFbCReferenceEntity> declaredVariables,
												final Method constructingMethod,
												final String guardSL)
			throws IFbCException {
		if (actualStatement instanceof CompositionStatement) {
			return updateCompositionStatement(projectName, actualStatement, lattice, declaredVariables,
					constructingMethod, guardSL);
		} else if (actualStatement instanceof SmallRepetitionStatement) {
			if (guardSL != null) {
				return updateSmallRepetitionStatement(projectName, actualStatement, lattice, declaredVariables,
						constructingMethod, guardSL);
			} else {
				return updateSmallRepetitionStatement(projectName, actualStatement, lattice, declaredVariables,
						constructingMethod);
			}
		} else if (actualStatement instanceof SelectionStatement) {
			if (guardSL != null) {
				return updateSelectionStatement(projectName, actualStatement, lattice, declaredVariables,
						constructingMethod, guardSL);
			} else {
				return updateSelectionStatement(projectName, actualStatement, lattice, declaredVariables,
						constructingMethod);
			}
		} else if (actualStatement instanceof AbstractStatement) {
			return updateAbstractStatement(projectName, actualStatement, lattice, declaredVariables, constructingMethod,
					guardSL);
		} else {
			System.out.println("updateDiagramBlock() not implemented for instance of AbstractStatement: "
					+ actualStatement.getClass().getCanonicalName());
			return false;
		}

	}

	/**
	 * Wrapper for updating a SmallRepetitionStatement without a security context.
	 * Used for compatibility reasons since the security context was introduced at a
	 * later stage of development and is optional.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateSmallRepetitionStatement(	final String projectName,
															final AbstractStatement statement,
															final Lattice lattice,
															final Map<String, IFbCReferenceEntity> declaredVariables,
															final Method constructingMethod)
			throws IFbCException {
		return updateSmallRepetitionStatement(projectName, statement, lattice, declaredVariables, constructingMethod);
	}

	/**
	 * Updating a SmallRepetitionStatement. This involves updating pre and post
	 * conditions before and after checking the actual statement encapsulated in
	 * this repetition.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateSmallRepetitionStatement(	final String projectName,
															final AbstractStatement statement,
															final Lattice lattice,
															final Map<String, IFbCReferenceEntity> declaredVariables,
															final Method constructingMethod,
															final String guardSL)
			throws IFbCException {
		SmallRepetitionStatement repetition = (SmallRepetitionStatement) statement;
		final Condition guard = repetition.getGuard();
		final AbstractStatement command = repetition.getLoopStatement().getRefinement();

		// set context
		setStatementContext(guardSL, repetition);

		// Get changed types from preVars
		final Map<String, String> changedTypes = new ConcurrentHashMap<>();
		repetition.getPreCondition().getAtTypesToVarsMap().forEach(entry -> {
			final String type = entry.getKey();
			final EList<AtType> list = entry.getValue();
			list.forEach(reference -> {
				changedTypes.put(reference.getName(), type);
			});
		});

		final Condition selectionPostCondition = repetition.getPostCondition();
		selectionPostCondition.getConfToVarsMap().clear();
		selectionPostCondition.getAtTypesToVarsMap().clear();
		selectionPostCondition.getCapsulesUsed().clear();

		if (guard == null || command == null) {
			throw new IFbCException("Cannot type selection since guard or statement are null");
		}

		System.out.println("Got guard " + guard.getName() + " with statement " + command.getName() + ".");

		final String repetitionGuardSL = JavaConditionParser.parseJavaCondition(projectName, guard.getName(),
				declaredVariables, changedTypes, constructingMethod);
		if (repetitionGuardSL == null) {
			throw new IFbCException("Guard of selection is empty");
		}
		System.out.println("SL for Condition " + guard.getName() + " is: " + repetitionGuardSL);

		// Update statement conditions before updating
		copyConfToVars(repetition.getPreCondition(), command.getPreCondition());
		copyAtTypeToVars(repetition.getPreCondition(), command.getPreCondition());
		copyCapsulesUsed(repetition.getPreCondition(), command.getPreCondition());

		// updateAbstractStatement(projectName, command, lattice, declaredVariables,
		// guardSL);
		final boolean typeableResult = updateDiagramBlock(command, projectName, lattice, declaredVariables,
				constructingMethod, repetitionGuardSL);

		// Update composition post condition
		final Condition postCondition = command.getPostCondition();
		copyConfToVars(postCondition, selectionPostCondition);
		copyAtTypeToVars(postCondition, selectionPostCondition);
		copyCapsulesUsed(postCondition, selectionPostCondition);

		return typeableResult;
	}

	/**
	 * Wrapper for updating a SelectionStatement without a security context. Used
	 * for compatibility reasons since the security context was introduced at a
	 * later stage of development and is optional.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateSelectionStatement(final String projectName,
													final AbstractStatement statement,
													final Lattice lattice,
													final Map<String, IFbCReferenceEntity> declaredVariables,
													final Method constructingMethod)
			throws IFbCException {
		return updateSelectionStatement(projectName, statement, lattice, declaredVariables, constructingMethod, null);
	}

	/**
	 * Updating a SelectionStatement. This involves updating pre and post conditions
	 * before and after checking the actual statements encapsulated in this
	 * selection.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateSelectionStatement(final String projectName,
													final AbstractStatement statement,
													final Lattice lattice,
													final Map<String, IFbCReferenceEntity> declaredVariables,
													final Method constructingMethod,
													final String guardSL)
			throws IFbCException {
		SelectionStatement selection = (SelectionStatement) statement;
		final EList<Condition> guards = selection.getGuards();
		final EList<AbstractStatement> commands = selection.getCommands();

		// set context
		setStatementContext(guardSL, selection);

		// Get changed types from preVars
		final Map<String, String> changedTypes = new ConcurrentHashMap<>();
		selection.getPreCondition().getAtTypesToVarsMap().forEach(entry -> {
			final String type = entry.getKey();
			final EList<AtType> list = entry.getValue();
			list.forEach(reference -> {
				changedTypes.put(reference.getName(), type);
			});
		});

		final Condition selectionPostCondition = selection.getPostCondition();
		selectionPostCondition.getConfToVarsMap().clear();
		selectionPostCondition.getAtTypesToVarsMap().clear();
		selectionPostCondition.getCapsulesUsed().clear();

		final Map<String, String> confToVarsMap = new ConcurrentHashMap<>();
		final Map<String, List<String>> atTypesToVarsMap = new ConcurrentHashMap<>();
		final List<String> capsulesUsed = new ArrayList<>();
		boolean typeableResult = true;
		for (int i = 0; i < guards.size(); i++) {
			final Condition condition = guards.get(i);
			final AbstractStatement abstractStatement = commands.get(i).getRefinement();

			if (condition == null || abstractStatement == null) {
				System.out.println("Cannot type selection since guard or statement are null for index " + i);
				continue;
			}

			System.out.println(
					"Got guard " + condition.getName() + " with statement " + abstractStatement.getName() + ".");

			final String selectionGuardSL = JavaConditionParser.parseJavaCondition(projectName, condition.getName(),
					declaredVariables, changedTypes, constructingMethod);
			if (selectionGuardSL == null) {
				throw new IFbCException("Guard of selection is empty");
			}
			System.out.println("SL for Condition " + condition.getName() + " is: " + selectionGuardSL);

			// Update statement conditions before updating
			copyConfToVars(selection.getPreCondition(), abstractStatement.getPreCondition());
			copyAtTypeToVars(selection.getPreCondition(), abstractStatement.getPreCondition());
			copyCapsulesUsed(selection.getPreCondition(), abstractStatement.getPreCondition());

			// updateAbstractStatement(projectName, abstractStatement, lattice,
			// declaredVariables, guardSL);
			typeableResult = updateDiagramBlock(abstractStatement, projectName, lattice, declaredVariables,
					constructingMethod, selectionGuardSL);

			// Collecting updates Vars, Types and Capsules after statement for selection
			// post condition
			final Condition postCondition = abstractStatement.getPostCondition();

			// Collection elevated entities
			for (Entry<String, Security> entry : postCondition.getConfToVarsMap()) {
				final String elevatedReference = entry.getKey();
				final Security elevatedSL = entry.getValue();
				final Node elevatedSLNode = lattice.getNodePerName(elevatedSL.getLevel());
				if (elevatedSLNode == null) {
					throw new IFbCException("Node for security level " + elevatedSLNode + " not found in lattice.");
				}
				// Check if there is already an elevation from a previous selection statement
				if (confToVarsMap.containsKey(elevatedReference)) {
					final Node preSLNode = lattice.getNodePerName(confToVarsMap.get(elevatedReference));
					// Check if the previous elevation is higher than the current
					if (LeastUpperBound.secondHigherThanFirst(preSLNode, elevatedSLNode)) {
						confToVarsMap.put(elevatedReference, elevatedSLNode.getName());
					} else {
						System.out.println("Won't put SL " + elevatedSL + " for " + elevatedReference
								+ " since we already have a higher level (" + preSLNode.getName()
								+ ") from a previous selection statement.");
					}
				} else {
					// No previous elevation
					confToVarsMap.put(elevatedReference, elevatedSLNode.getName());
				}
			}

			// Collecting changed types (later guards corresponding statements will override
			// previous statements for now regarding type changes)
			for (Entry<String, EList<AtType>> entry : postCondition.getAtTypesToVarsMap()) {
				final String changedReference = entry.getKey();
				final EList<AtType> changedReferenceTypes = entry.getValue();
				List<String> atTypes = changedReferenceTypes.stream().map(AtType::getName).collect(Collectors.toList());
				atTypesToVarsMap.put(changedReference, atTypes);
			}

			// Collecting capsules used
			capsulesUsed.addAll(postCondition.getCapsulesUsed());
		}

		// Add collected elevated entities, changed types and capsule usages in this
		// selection to it's post condition

		// Elevated SLs
		confToVarsMap.forEach((reference, level) -> {
			final Security sec = CbcmodelFactory.eINSTANCE.createSecurity();
			sec.setLevel(level);
			sec.setMutationModifier("IMMUTABLE");
			final CbCFormula cbCFormula = getCbCFormula(statement);
			cbCFormula.getSecurity().add(sec);
			System.out.println("Putting Security " + sec.toString() + " into confToVarsMap.");
			selectionPostCondition.getConfToVarsMap().put(reference, sec);
		});

		// Changed types
		atTypesToVarsMap.forEach((type, references) -> {
			// One type can have multiple references that are changed to this type
			references.forEach(reference -> {
				final AtType atType = CbcmodelFactory.eINSTANCE.createAtType();
				atType.setName(reference);
				final CbCFormula cbCFormula = getCbCFormula(statement);
				cbCFormula.getAtType().add(atType);
				System.out.println("Putting Changed type for " + reference + " to " + type + " into AtTypesToVarsMap.");
				// Is this type already present or should it be created?
				if (selectionPostCondition.getAtTypesToVarsMap().get(type) == null) {
					final EList<AtType> newList = new BasicEList<AtType>();
					newList.add(atType);
					selectionPostCondition.getAtTypesToVarsMap().put(type, newList);
				} else {
					final EList<AtType> eList = selectionPostCondition.getAtTypesToVarsMap().get(type);
					eList.add(atType);
				}
			});
		});

		// Capsule usage
		selectionPostCondition.getCapsulesUsed().addAll(capsulesUsed);

		return typeableResult;
	}

	/**
	 * Wrapper for updating a CompositionStatement without a security context. Used
	 * for compatibility reasons since the security context was introduced at a
	 * later stage of development and is optional.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateCompositionStatement(	final String projectName,
														final AbstractStatement statement,
														final Lattice lattice,
														final Map<String, IFbCReferenceEntity> declaredVariables,
														final Method constructingMethod)
			throws IFbCException {
		return updateCompositionStatement(projectName, statement, lattice, declaredVariables, constructingMethod, null);
	}

	/**
	 * Updating a CompositionStatement. This involves updating both statements, the
	 * pre and post conditions and using the intermediate condition between the two
	 * statement updates.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateCompositionStatement(	final String projectName,
														final AbstractStatement statement,
														final Lattice lattice,
														final Map<String, IFbCReferenceEntity> declaredVariables,
														final Method constructingMethod,
														final String guardSL)
			throws IFbCException {
		System.out.println("Updating Composition");
		final CompositionStatement comp = (CompositionStatement) statement;
		final AbstractStatement first = comp.getFirstStatement().getRefinement();
		final AbstractStatement second = comp.getSecondStatement().getRefinement();
		boolean typeableResult = true;

		// set context
		setStatementContext(guardSL, comp);

		// Update first statement conditions before updating
		if (first != null) {
			copyConfToVars(comp.getPreCondition(), first.getPreCondition());
			copyAtTypeToVars(comp.getPreCondition(), first.getPreCondition());
			copyCapsulesUsed(comp.getPreCondition(), first.getPreCondition());
		}

		// Check first statement
		if (guardSL == null) {
			typeableResult = updateDiagramBlock(first, projectName, lattice, declaredVariables, constructingMethod);
		} else {
			typeableResult = updateDiagramBlock(first, projectName, lattice, declaredVariables, constructingMethod,
					guardSL);
		}

		// Update second statement conditions before updating
		if (first != null && second != null) {
			copyConfToVars(first.getPostCondition(), second.getPreCondition());
			copyAtTypeToVars(first.getPostCondition(), second.getPreCondition());
			copyCapsulesUsed(first.getPostCondition(), second.getPreCondition());
		}

		// check second statement
		if (guardSL == null) {
			typeableResult = updateDiagramBlock(second, projectName, lattice, declaredVariables, constructingMethod);
		} else {
			typeableResult = updateDiagramBlock(second, projectName, lattice, declaredVariables, constructingMethod,
					guardSL);
		}

		// Update composition post condition
		if (second != null) {
			copyConfToVars(second.getPostCondition(), comp.getPostCondition());
			copyAtTypeToVars(second.getPostCondition(), comp.getPostCondition());
			copyCapsulesUsed(second.getPostCondition(), comp.getPostCondition());
		}
		System.out.println("");

		return typeableResult;
	}

	/**
	 * Wrapper for updating a AbstractStatement without a security context. Used for
	 * compatibility reasons since the security context was introduced at a later
	 * stage of development and is optional.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateAbstractStatement(	final String projectName,
													final AbstractStatement actualStatement,
													final Lattice lattice,
													final Map<String, IFbCReferenceEntity> declaredVariables,
													final Method constructingMethod)
			throws IFbCException {
		return updateAbstractStatement(projectName, actualStatement, lattice, declaredVariables, constructingMethod,
				null);
	}

	/**
	 * Updating a AbstractStatement. This includes parsing of the java statements in
	 * the textfield of the statement and calling the appropriate implementation of
	 * the interface IFbCStatement.
	 * 
	 * @param actualStatement
	 *            The statement to be updated.
	 * @param projectName
	 *            The name of the current project.
	 * @param lattice
	 *            The lattice of the current project.
	 * @param declaredVariables
	 *            Variables declared in the current diagram.
	 * @param constructingMethod
	 *            The optional method the diagram is constructing.
	 * @param guardSL
	 *            The security context for any subsiding statements
	 * @param specification
	 *            The optional IFbC specification limiting possible security level
	 *            increments
	 * @return
	 * @throws IFbCException
	 *             The called methods can throw an exception with information in a
	 *             number of different cases. For now the exception should be
	 *             delegated to the calling method.
	 */
	private static boolean updateAbstractStatement(	final String projectName,
													final AbstractStatement actualStatement,
													final Lattice lattice,
													final Map<String, IFbCReferenceEntity> declaredVariables,
													final Method constructingMethod,
													final String guardSL)
			throws IFbCException {
		final String refinementName = actualStatement.getName();
		System.out.println("Updating Abstract statement regarding information flow: " + refinementName);

		// set context
		setStatementContext(guardSL, actualStatement);

		// Get elevated entities from preVars
		final Map<String, List<IFbCReferenceEntity>> elevatedEntities = new ConcurrentHashMap<>();
		actualStatement.getPreCondition().getConfToVarsMap().forEach(entry -> {
			// set new security level for this entity
			final IFbCReferenceEntity iFbCReferenceEntity = declaredVariables.get(entry.getKey());
			iFbCReferenceEntity.setSecurityLevel(entry.getValue().getLevel());
			if (elevatedEntities.get(entry.getValue().getLevel()) == null) {
				final List<IFbCReferenceEntity> slList = new ArrayList<>();
				slList.add(iFbCReferenceEntity);
				elevatedEntities.put(entry.getValue().getLevel(), slList);
			} else {
				final List<IFbCReferenceEntity> slList = elevatedEntities.get(entry.getValue().getLevel());
				slList.add(iFbCReferenceEntity);
			}
		});

		// Get changed types from preVars
		final Map<String, String> changedTypes = new ConcurrentHashMap<>();
		actualStatement.getPreCondition().getAtTypesToVarsMap().forEach(entry -> {
			final String type = entry.getKey();
			final EList<AtType> list = entry.getValue();
			list.forEach(reference -> {
				changedTypes.put(reference.getName(), type);
			});
		});

		// Get used capsule references
		final List<String> usedCapsules = new ArrayList<>();
		actualStatement.getPreCondition().getCapsulesUsed().forEach(c -> usedCapsules.add(c));

		List<IFbCStatement> javaStatements = null;
		try {
			javaStatements = JavaStatementParser.parseJavaStatement(projectName, refinementName, declaredVariables,
					changedTypes, constructingMethod);
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			return processStatementFailure(actualStatement);
		}

		// Check if number of parsed statements and number of statements in
		// AbstractStatement are equal
		final int numberOfStatements = (refinementName.length() - refinementName.replace(";", "").length());
		System.out.println("Comparing number of parsed statements (" + javaStatements.size()
				+ ") to actual statements (" + numberOfStatements + ") in AbstracStatement.");
		if (javaStatements.size() != numberOfStatements) {
			return processStatementFailure(actualStatement);
		}

		// Check statements
		Boolean globalTypeable = Boolean.TRUE;
		final StringBuilder sb = new StringBuilder();
		for (IFbCStatement s : javaStatements) {
			if (s.getType().equals(StatementType.Assignment) || s.getType().equals(StatementType.MethodCall)
					|| s.getType().equals(StatementType.Return)) {
				// Debug Information for assignment
				if (s.getType().equals(StatementType.Assignment)) {
					final IFbCAssignment assignment = (IFbCAssignment) s;
					System.out.println("Target information: " + assignment.getTarget().toString());
					System.out.println("Value information: " + assignment.getValue().toString());
				}

				try {
					final TypeableResult typeable = s.isTypeable(lattice, projectName, elevatedEntities, changedTypes,
							usedCapsules, guardSL, constructingMethod);
					System.out.println("Typeable: " + typeable);
					if (!typeable.isTypeable()) {
						globalTypeable = Boolean.FALSE;
					}
					sb.append(s.getStatement() + ": " + typeable.getMessage() + "\n");
				} catch (IFbCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Unknown type: " + s.getType());
			}
		}

		// Set fields for property view
		actualStatement.setTypeableResult(globalTypeable.toString());
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		actualStatement.setTypeableText(sb.toString());

		// Add elevated to Post Variables
		actualStatement.getPostCondition().getConfToVarsMap().clear();
		elevatedEntities.forEach((key, value) -> {
			System.out.println("Key: " + key + ", value: " + value.toString());
			value.forEach(entity -> {
				System.out.println("Entity: " + entity.toString());
				final Security sec = CbcmodelFactory.eINSTANCE.createSecurity();
				sec.setLevel(key);
				sec.setMutationModifier(entity.getMutationModifier().toString());
				final CbCFormula cbCFormula = getCbCFormula(actualStatement);
				cbCFormula.getSecurity().add(sec);
				System.out.println("Putting Security " + sec.toString() + " into confToVarsMap.");
				actualStatement.getPostCondition().getConfToVarsMap().put(entity.getName(), sec);
			});
		});

		// Add changed Types to AtPost
		actualStatement.getPostCondition().getAtTypesToVarsMap().clear();
		changedTypes.forEach((targetScopeName, valueReturnType) -> {
			System.out.println("Key: " + targetScopeName + ", value: " + valueReturnType.toString());
			final AtType atType = CbcmodelFactory.eINSTANCE.createAtType();
			atType.setName(targetScopeName);
			final CbCFormula cbCFormula = getCbCFormula(actualStatement);
			cbCFormula.getAtType().add(atType);
			System.out.println("Putting Changed type for " + targetScopeName + " to " + valueReturnType
					+ " into AtTypesToVarsMap.");
			if (actualStatement.getPostCondition().getAtTypesToVarsMap().get(valueReturnType) == null) {
				final EList<AtType> newList = new BasicEList<AtType>();
				newList.add(atType);
				actualStatement.getPostCondition().getAtTypesToVarsMap().put(valueReturnType, newList);
			} else {
				final EList<AtType> eList = actualStatement.getPostCondition().getAtTypesToVarsMap()
						.get(valueReturnType);
				eList.add(atType);
			}
			// });
		});

		// Add used capsules to condition
		actualStatement.getPostCondition().getCapsulesUsed().clear();
		usedCapsules.forEach(c -> {
			actualStatement.getPostCondition().getCapsulesUsed().add(c);
		});
		System.out.println("");
		return globalTypeable;
	}

	/**
	 * Retrieve the variables declared in a diagram.
	 * 
	 * @param statement
	 *            An statement of the diagram for which the variables are to be
	 *            retrieved.
	 * @return
	 */
	private static Map<String, IFbCReferenceEntity> getDeclaredVariables(AbstractStatement statement) {
		JavaVariables variables = null;
		Map<String, IFbCReferenceEntity> variablesAsMap = new HashMap<String, IFbCReferenceEntity>();
		if (statement.eResource() != null) {
			for (EObject object : statement.eResource().getContents()) {
				if (object instanceof JavaVariables) {
					variables = (JavaVariables) object;
				}
			}
			if (variables != null) {
				for (JavaVariable variable : variables.getVariables()) {
					MDF mutationModifier = MDF.READ;

					if (variable.getModifier() == null) {
						mutationModifier = MDF.IMMUTABLE;
					} else if (variable.getModifier().equals("imm")) {
						mutationModifier = MDF.IMMUTABLE;
					} else if (variable.getModifier().equals("capsule")) {
						mutationModifier = MDF.CAPSULE;
					} else if (variable.getModifier().equals("mut")) {
						mutationModifier = MDF.MUTABLE;
					}

					// CbCFormula formula = getCbCFormula(statement);
					// formula.getSecurity().add(security);
					variablesAsMap.put(getNameOfVar(variable), new IFbCReferenceEntity(getNameOfVar(variable),
							variable.getConfidentiality(), mutationModifier, getTypeOfVar(variable)));
				}
			}
		}
		return variablesAsMap;
	}
	
	private static String getTypeOfVar(JavaVariable var) {
		String varName = var.getName();
		String varType = varName.substring(0, varName.lastIndexOf(" "));
		varType = varType.replace("static", "").replace("non-null", "");
		return varType.trim();
	}
	
	private static String getNameOfVar(JavaVariable var) {
		String varName = var.getName();
		String varRet = varName.substring(varName.lastIndexOf(" ") + 1, varName.length());
		varRet = varRet.replace("static", "").replace("non-null", "");
		return varRet.trim();
	}

	/**
	 * Handles the fail of a statement check. Sets the model to represent the
	 * failure and passes the conditions from the pre condition to the post
	 * condition.
	 * 
	 * @param statement
	 *            The actual statement that failed.
	 * 
	 * @return
	 */
	private static boolean processStatementFailure(final AbstractStatement statement) {
		statement.setTypeableResult("false");
		statement.setTypeableText("Could not parse statement(s) in this diagram.");
		// Just copy pre vars to post vars
		copyConfToVars(statement.getPreCondition(), statement.getPostCondition());
		copyAtTypeToVars(statement.getPreCondition(), statement.getPostCondition());
		copyCapsulesUsed(statement.getPreCondition(), statement.getPostCondition());
		return false;
	}

	/**
	 * Returned the method that is constructed in a diagram defined through a
	 * MethodLink object inside a diagram.
	 * 
	 * @param statement
	 *            An statement of the diagram.
	 * @param projectName
	 *            The name of the current project.
	 * @return
	 */
	private static Method getLinkedMethod(AbstractStatement statement, String projectName) {
		CbCFormula formula = null;
		if (statement.eResource() != null) {
			for (EObject object : statement.eResource().getContents()) {
				if (object instanceof CbCFormula) {
					formula = (CbCFormula) object;
				}
			}
			if (formula != null) {
				// get Name and Method from formula
				final String className = formula.getClassName();	
				if (className == null) {
					System.out.println("No class name set in CbCFormula");
					return null;
				}
				
				final String methodSignature = formula.getMethodName();
				if (methodSignature == null || methodSignature.isEmpty()) {
					System.out.println("No method signature set in CbCFormula");
					return null;
				}
			

				final String returnType = methodSignature.substring(0, methodSignature.indexOf(' '));
				final String methodName = methodSignature.substring(methodSignature.indexOf(' ') + 1,
						methodSignature.indexOf('('));
				final String parameterString = methodSignature.substring(methodSignature.indexOf('(') + 1,
						methodSignature.indexOf(')'));

				final String[] split = parameterString.trim().split(",");
				final List<ParameterDefinition> parameters = new ArrayList<>(split.length);
				for (String parameter : split) {
					if (!parameter.isEmpty()) {
						final ParameterDefinition parameterDefinition = new ParameterDefinition(null, null, null,
								parameter.trim());
						parameters.add(parameterDefinition);
					}
				}

				final Map<String, JavaClass> javaClassesForProject = JavaClasses.getJavaClassesForProject(projectName);
				if (javaClassesForProject == null) {
					System.out.println("No Java Classes found for project name: " + projectName);
					return null;
				}

				final JavaClass javaClass = javaClassesForProject.get(className);
				if (javaClass == null) {
					System.out.println("No Java Class found for class name: " + className);
					return null;
				}

				final Method method = javaClass.getMethodByNameAndParameterSize(methodName, parameters);
				if (method == null) {
					System.out.println("Could not find Method in class " + className + " with name " + methodName
							+ " and parameters " + parameters.toString());
					return null;
				}

				System.out.println("Found linked method: " + method.toString());
				return method;
			} else {
				System.out.println("No CbCFormula in diagram, could not determine constructing method.");
				return null;
			}
		} else {
			System.out.println("No resource in statement, could not determine constructing method.");
			return null;
		}
	}

	/**
	 * The the CbCFormula for a statement
	 * 
	 * @param statement
	 *            The statement for which the CbCFormula is to be retrieved.
	 * 
	 * @return The CbCFormula from the diagram to which the statement belongs.
	 */
	private static CbCFormula getCbCFormula(AbstractStatement statement) {
		EObject parent = getParentOfStatement(statement);
		if (parent != null) {
			if (parent instanceof CbCFormula) {
				return (CbCFormula) parent;
			} else {
				return getCbCFormula((AbstractStatement) parent);
			}
		}
		return null;
	}

	/**
	 * Retrieve the parent of a statement.
	 * 
	 * @param statement
	 *            The statement for which a parent should be retrieved.
	 * 
	 * @return The parent of the statement.
	 */
	private static EObject getParentOfStatement(AbstractStatement statement) {
		EObject parent = null;
		if (statement.getParent() != null) {
			parent = statement.getParent().eContainer();
		} else if (statement.eContainer() != null) {
			parent = statement.eContainer();
		}
		return parent;
	}

	/**
	 * Copy confToVars (for elevated security levels) from one condition to another.
	 * 
	 * @param from
	 *            Source condition
	 * @param to
	 *            Destination condition
	 */
	private static void copyConfToVars(final Condition from, final Condition to) {
		// System.out.println("From Vars: " + from.toString());
		// System.out.println("Conf To Vars before clear: " + to.toString());
		to.getConfToVarsMap().clear();
		from.getConfToVarsMap().forEach(entry -> {
			to.getConfToVarsMap().put(entry.getKey(), entry.getValue());
		});

		// System.out.println("Conf To Vars after copying: " + to.toString());
	}

	/**
	 * Copy atTypeToVars (for changed class types) from one condition to another.
	 * 
	 * @param from
	 *            Source condition
	 * @param to
	 *            Destination condition
	 */
	private static void copyAtTypeToVars(final Condition from, final Condition to) {
		// System.out.println("From Vars: " + from.toString());
		// System.out.println("Conf To Vars before clear: " + to.toString());
		to.getAtTypesToVarsMap().clear();
		from.getAtTypesToVarsMap().forEach(entry -> {
			to.getAtTypesToVarsMap().put(entry.getKey(), entry.getValue());
		});

		// System.out.println("Conf To Vars after copying: " + to.toString());
	}

	/**
	 * Copy used capsules references from one condition to another.
	 * 
	 * @param from
	 *            Source condition
	 * @param to
	 *            Destination condition
	 */
	private static void copyCapsulesUsed(final Condition from, final Condition to) {
		to.getCapsulesUsed().clear();
		from.getCapsulesUsed().forEach(c -> {
			to.getCapsulesUsed().add(c);
		});
	}

	/**
	 * Set the context of a statement.
	 * 
	 * @param guardSL
	 *            The security context.
	 * @param statement
	 *            The statement for which the security context should be set
	 */
	private static void setStatementContext(final String guardSL, AbstractStatement statement) {
		if (guardSL != null) {
			statement.setContext(guardSL);
		} else {
			statement.setContext("none");
		}
	}

}
