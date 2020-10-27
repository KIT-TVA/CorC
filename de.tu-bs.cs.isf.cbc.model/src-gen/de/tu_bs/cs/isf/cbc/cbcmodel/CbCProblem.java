/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cb CProblem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getGlobalcondition <em>Globalcondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getCbcformula <em>Cbcformula</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getJavaVariable <em>Java Variable</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRenaming <em>Renaming</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRefinements <em>Refinements</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethodsignature <em>Methodsignature</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethodClass <em>Method Class</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem()
 * @model
 * @generated
 */
public interface CbCProblem extends EObject {
	/**
	 * Returns the value of the '<em><b>Globalcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Globalcondition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Globalcondition</em>' containment reference.
	 * @see #setGlobalcondition(GlobalConditions)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_Globalcondition()
	 * @model containment="true"
	 * @generated
	 */
	GlobalConditions getGlobalcondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getGlobalcondition <em>Globalcondition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Globalcondition</em>' containment reference.
	 * @see #getGlobalcondition()
	 * @generated
	 */
	void setGlobalcondition(GlobalConditions value);

	/**
	 * Returns the value of the '<em><b>Cbcformula</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cbcformula</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cbcformula</em>' containment reference.
	 * @see #setCbcformula(CbCFormula)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_Cbcformula()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CbCFormula getCbcformula();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getCbcformula <em>Cbcformula</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cbcformula</em>' containment reference.
	 * @see #getCbcformula()
	 * @generated
	 */
	void setCbcformula(CbCFormula value);

	/**
	 * Returns the value of the '<em><b>Java Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Variable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Variable</em>' containment reference.
	 * @see #setJavaVariable(JavaVariables)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_JavaVariable()
	 * @model containment="true"
	 * @generated
	 */
	JavaVariables getJavaVariable();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getJavaVariable <em>Java Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Variable</em>' containment reference.
	 * @see #getJavaVariable()
	 * @generated
	 */
	void setJavaVariable(JavaVariables value);

	/**
	 * Returns the value of the '<em><b>Renaming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Renaming</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Renaming</em>' containment reference.
	 * @see #setRenaming(Renaming)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_Renaming()
	 * @model containment="true"
	 * @generated
	 */
	Renaming getRenaming();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRenaming <em>Renaming</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Renaming</em>' containment reference.
	 * @see #getRenaming()
	 * @generated
	 */
	void setRenaming(Renaming value);

	/**
	 * Returns the value of the '<em><b>Refinements</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refinements</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refinements</em>' containment reference.
	 * @see #setRefinements(MethodRefinements)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_Refinements()
	 * @model containment="true"
	 * @generated
	 */
	MethodRefinements getRefinements();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRefinements <em>Refinements</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refinements</em>' containment reference.
	 * @see #getRefinements()
	 * @generated
	 */
	void setRefinements(MethodRefinements value);

	/**
	 * Returns the value of the '<em><b>Methodsignature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methodsignature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methodsignature</em>' containment reference.
	 * @see #setMethodsignature(MethodSignature)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_Methodsignature()
	 * @model containment="true"
	 * @generated
	 */
	MethodSignature getMethodsignature();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethodsignature <em>Methodsignature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Methodsignature</em>' containment reference.
	 * @see #getMethodsignature()
	 * @generated
	 */
	void setMethodsignature(MethodSignature value);

	/**
	 * Returns the value of the '<em><b>Method Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Class</em>' containment reference.
	 * @see #setMethodClass(MethodClass)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCProblem_MethodClass()
	 * @model containment="true"
	 * @generated
	 */
	MethodClass getMethodClass();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethodClass <em>Method Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Class</em>' containment reference.
	 * @see #getMethodClass()
	 * @generated
	 */
	void setMethodClass(MethodClass value);

} // CbCProblem
