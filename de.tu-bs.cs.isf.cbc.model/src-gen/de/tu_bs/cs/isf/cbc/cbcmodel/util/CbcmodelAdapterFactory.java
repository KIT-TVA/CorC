/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.util;

import de.tu_bs.cs.isf.cbc.cbcmodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage
 * @generated
 */
public class CbcmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CbcmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbcmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CbcmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CbcmodelSwitch<Adapter> modelSwitch =
		new CbcmodelSwitch<Adapter>() {
			@Override
			public Adapter caseCbCFormula(CbCFormula object) {
				return createCbCFormulaAdapter();
			}
			@Override
			public Adapter caseCondition(Condition object) {
				return createConditionAdapter();
			}
			@Override
			public Adapter caseAbstractStatement(AbstractStatement object) {
				return createAbstractStatementAdapter();
			}
			@Override
			public Adapter caseSkipStatement(SkipStatement object) {
				return createSkipStatementAdapter();
			}
			@Override
			public Adapter caseCompositionStatement(CompositionStatement object) {
				return createCompositionStatementAdapter();
			}
			@Override
			public Adapter caseSelectionStatement(SelectionStatement object) {
				return createSelectionStatementAdapter();
			}
			@Override
			public Adapter caseRepetitionStatement(RepetitionStatement object) {
				return createRepetitionStatementAdapter();
			}
			@Override
			public Adapter caseMethodStatement(MethodStatement object) {
				return createMethodStatementAdapter();
			}
			@Override
			public Adapter caseVariant(Variant object) {
				return createVariantAdapter();
			}
			@Override
			public Adapter caseReturnStatement(ReturnStatement object) {
				return createReturnStatementAdapter();
			}
			@Override
			public Adapter caseMethod(Method object) {
				return createMethodAdapter();
			}
			@Override
			public Adapter caseComposition3Statement(Composition3Statement object) {
				return createComposition3StatementAdapter();
			}
			@Override
			public Adapter caseSmallRepetitionStatement(SmallRepetitionStatement object) {
				return createSmallRepetitionStatementAdapter();
			}
			@Override
			public Adapter caseJavaVariables(JavaVariables object) {
				return createJavaVariablesAdapter();
			}
			@Override
			public Adapter caseJavaVariable(JavaVariable object) {
				return createJavaVariableAdapter();
			}
			@Override
			public Adapter caseGlobalConditions(GlobalConditions object) {
				return createGlobalConditionsAdapter();
			}
			@Override
			public Adapter caseRenaming(Renaming object) {
				return createRenamingAdapter();
			}
			@Override
			public Adapter caseRename(Rename object) {
				return createRenameAdapter();
			}
			@Override
			public Adapter caseCbCProblem(CbCProblem object) {
				return createCbCProblemAdapter();
			}
			@Override
			public Adapter caseStrengthWeakStatement(StrengthWeakStatement object) {
				return createStrengthWeakStatementAdapter();
			}
			@Override
			public Adapter caseMethodRefinements(MethodRefinements object) {
				return createMethodRefinementsAdapter();
			}
			@Override
			public Adapter caseProductVariant(ProductVariant object) {
				return createProductVariantAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula <em>Cb CFormula</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula
	 * @generated
	 */
	public Adapter createCbCFormulaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Condition
	 * @generated
	 */
	public Adapter createConditionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement <em>Abstract Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement
	 * @generated
	 */
	public Adapter createAbstractStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement <em>Skip Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement
	 * @generated
	 */
	public Adapter createSkipStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement <em>Composition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement
	 * @generated
	 */
	public Adapter createCompositionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement <em>Selection Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement
	 * @generated
	 */
	public Adapter createSelectionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement <em>Repetition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement
	 * @generated
	 */
	public Adapter createRepetitionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement <em>Method Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement
	 * @generated
	 */
	public Adapter createMethodStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Variant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Variant
	 * @generated
	 */
	public Adapter createVariantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement <em>Return Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement
	 * @generated
	 */
	public Adapter createReturnStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Method
	 * @generated
	 */
	public Adapter createMethodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement <em>Composition3 Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement
	 * @generated
	 */
	public Adapter createComposition3StatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement <em>Small Repetition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement
	 * @generated
	 */
	public Adapter createSmallRepetitionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables <em>Java Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables
	 * @generated
	 */
	public Adapter createJavaVariablesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable <em>Java Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable
	 * @generated
	 */
	public Adapter createJavaVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions <em>Global Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions
	 * @generated
	 */
	public Adapter createGlobalConditionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Renaming <em>Renaming</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Renaming
	 * @generated
	 */
	public Adapter createRenamingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename <em>Rename</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Rename
	 * @generated
	 */
	public Adapter createRenameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem <em>Cb CProblem</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem
	 * @generated
	 */
	public Adapter createCbCProblemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement <em>Strength Weak Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement
	 * @generated
	 */
	public Adapter createStrengthWeakStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements <em>Method Refinements</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements
	 * @generated
	 */
	public Adapter createMethodRefinementsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant <em>Product Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant
	 * @generated
	 */
	public Adapter createProductVariantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CbcmodelAdapterFactory
