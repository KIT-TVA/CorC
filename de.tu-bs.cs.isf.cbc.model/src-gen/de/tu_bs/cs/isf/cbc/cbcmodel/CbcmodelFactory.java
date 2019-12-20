/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage
 * @generated
 */
public interface CbcmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CbcmodelFactory eINSTANCE = de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Cb CFormula</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cb CFormula</em>'.
	 * @generated
	 */
	CbCFormula createCbCFormula();

	/**
	 * Returns a new object of class '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Condition</em>'.
	 * @generated
	 */
	Condition createCondition();

	/**
	 * Returns a new object of class '<em>Abstract Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Abstract Statement</em>'.
	 * @generated
	 */
	AbstractStatement createAbstractStatement();

	/**
	 * Returns a new object of class '<em>Skip Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Skip Statement</em>'.
	 * @generated
	 */
	SkipStatement createSkipStatement();

	/**
	 * Returns a new object of class '<em>Composition Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composition Statement</em>'.
	 * @generated
	 */
	CompositionStatement createCompositionStatement();

	/**
	 * Returns a new object of class '<em>Selection Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Selection Statement</em>'.
	 * @generated
	 */
	SelectionStatement createSelectionStatement();

	/**
	 * Returns a new object of class '<em>Repetition Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repetition Statement</em>'.
	 * @generated
	 */
	RepetitionStatement createRepetitionStatement();

	/**
	 * Returns a new object of class '<em>Method Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Method Statement</em>'.
	 * @generated
	 */
	MethodStatement createMethodStatement();

	/**
	 * Returns a new object of class '<em>Variant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variant</em>'.
	 * @generated
	 */
	Variant createVariant();

	/**
	 * Returns a new object of class '<em>Return Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Return Statement</em>'.
	 * @generated
	 */
	ReturnStatement createReturnStatement();

	/**
	 * Returns a new object of class '<em>Method</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Method</em>'.
	 * @generated
	 */
	Method createMethod();

	/**
	 * Returns a new object of class '<em>Composition3 Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composition3 Statement</em>'.
	 * @generated
	 */
	Composition3Statement createComposition3Statement();

	/**
	 * Returns a new object of class '<em>Small Repetition Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Small Repetition Statement</em>'.
	 * @generated
	 */
	SmallRepetitionStatement createSmallRepetitionStatement();

	/**
	 * Returns a new object of class '<em>Java Variables</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Variables</em>'.
	 * @generated
	 */
	JavaVariables createJavaVariables();

	/**
	 * Returns a new object of class '<em>Java Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Variable</em>'.
	 * @generated
	 */
	JavaVariable createJavaVariable();

	/**
	 * Returns a new object of class '<em>Global Conditions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Conditions</em>'.
	 * @generated
	 */
	GlobalConditions createGlobalConditions();

	/**
	 * Returns a new object of class '<em>Renaming</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Renaming</em>'.
	 * @generated
	 */
	Renaming createRenaming();

	/**
	 * Returns a new object of class '<em>Rename</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rename</em>'.
	 * @generated
	 */
	Rename createRename();

	/**
	 * Returns a new object of class '<em>Cb CProblem</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cb CProblem</em>'.
	 * @generated
	 */
	CbCProblem createCbCProblem();

	/**
	 * Returns a new object of class '<em>Strength Weak Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Strength Weak Statement</em>'.
	 * @generated
	 */
	StrengthWeakStatement createStrengthWeakStatement();

	/**
	 * Returns a new object of class '<em>Method Refinements</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Method Refinements</em>'.
	 * @generated
	 */
	MethodRefinements createMethodRefinements();

	/**
	 * Returns a new object of class '<em>Product Variant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Product Variant</em>'.
	 * @generated
	 */
	ProductVariant createProductVariant();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CbcmodelPackage getCbcmodelPackage();

} //CbcmodelFactory
