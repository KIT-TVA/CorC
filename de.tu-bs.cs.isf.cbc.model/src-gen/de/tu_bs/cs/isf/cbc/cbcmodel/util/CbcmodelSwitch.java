/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.util;

import de.tu_bs.cs.isf.cbc.cbcmodel.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage
 * @generated
 */
public class CbcmodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CbcmodelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbcmodelSwitch() {
		if (modelPackage == null) {
			modelPackage = CbcmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CbcmodelPackage.CB_CFORMULA: {
				CbCFormula cbCFormula = (CbCFormula)theEObject;
				T result = caseCbCFormula(cbCFormula);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.CONDITION: {
				Condition condition = (Condition)theEObject;
				T result = caseCondition(condition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.ABSTRACT_STATEMENT: {
				AbstractStatement abstractStatement = (AbstractStatement)theEObject;
				T result = caseAbstractStatement(abstractStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.SKIP_STATEMENT: {
				SkipStatement skipStatement = (SkipStatement)theEObject;
				T result = caseSkipStatement(skipStatement);
				if (result == null) result = caseAbstractStatement(skipStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.COMPOSITION_STATEMENT: {
				CompositionStatement compositionStatement = (CompositionStatement)theEObject;
				T result = caseCompositionStatement(compositionStatement);
				if (result == null) result = caseAbstractStatement(compositionStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.SELECTION_STATEMENT: {
				SelectionStatement selectionStatement = (SelectionStatement)theEObject;
				T result = caseSelectionStatement(selectionStatement);
				if (result == null) result = caseAbstractStatement(selectionStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.METHOD_STATEMENT: {
				MethodStatement methodStatement = (MethodStatement)theEObject;
				T result = caseMethodStatement(methodStatement);
				if (result == null) result = caseAbstractStatement(methodStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.VARIANT: {
				Variant variant = (Variant)theEObject;
				T result = caseVariant(variant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.RETURN_STATEMENT: {
				ReturnStatement returnStatement = (ReturnStatement)theEObject;
				T result = caseReturnStatement(returnStatement);
				if (result == null) result = caseAbstractStatement(returnStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT: {
				SmallRepetitionStatement smallRepetitionStatement = (SmallRepetitionStatement)theEObject;
				T result = caseSmallRepetitionStatement(smallRepetitionStatement);
				if (result == null) result = caseAbstractStatement(smallRepetitionStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.JAVA_VARIABLES: {
				JavaVariables javaVariables = (JavaVariables)theEObject;
				T result = caseJavaVariables(javaVariables);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.JAVA_VARIABLE: {
				JavaVariable javaVariable = (JavaVariable)theEObject;
				T result = caseJavaVariable(javaVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.GLOBAL_CONDITIONS: {
				GlobalConditions globalConditions = (GlobalConditions)theEObject;
				T result = caseGlobalConditions(globalConditions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.RENAMING: {
				Renaming renaming = (Renaming)theEObject;
				T result = caseRenaming(renaming);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.RENAME: {
				Rename rename = (Rename)theEObject;
				T result = caseRename(rename);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.CB_CPROBLEM: {
				CbCProblem cbCProblem = (CbCProblem)theEObject;
				T result = caseCbCProblem(cbCProblem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.STRENGTH_WEAK_STATEMENT: {
				StrengthWeakStatement strengthWeakStatement = (StrengthWeakStatement)theEObject;
				T result = caseStrengthWeakStatement(strengthWeakStatement);
				if (result == null) result = caseAbstractStatement(strengthWeakStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CbcmodelPackage.ORIGINAL_STATEMENT: {
				OriginalStatement originalStatement = (OriginalStatement)theEObject;
				T result = caseOriginalStatement(originalStatement);
				if (result == null) result = caseAbstractStatement(originalStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cb CFormula</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cb CFormula</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCbCFormula(CbCFormula object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractStatement(AbstractStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Skip Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Skip Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSkipStatement(SkipStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionStatement(CompositionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Selection Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Selection Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSelectionStatement(SelectionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Method Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Method Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMethodStatement(MethodStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariant(Variant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Return Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Return Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReturnStatement(ReturnStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Small Repetition Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Small Repetition Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSmallRepetitionStatement(SmallRepetitionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Variables</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Variables</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaVariables(JavaVariables object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaVariable(JavaVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Conditions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Conditions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalConditions(GlobalConditions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Renaming</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Renaming</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRenaming(Renaming object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rename</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rename</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRename(Rename object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cb CProblem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cb CProblem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCbCProblem(CbCProblem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Strength Weak Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Strength Weak Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStrengthWeakStatement(StrengthWeakStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Original Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Original Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOriginalStatement(OriginalStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CbcmodelSwitch
