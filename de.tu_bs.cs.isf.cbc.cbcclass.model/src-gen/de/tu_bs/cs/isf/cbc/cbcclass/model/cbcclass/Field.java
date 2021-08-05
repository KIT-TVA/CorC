/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getType <em>Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getDisplayedName <em>Displayed Name</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField()
 * @model
 * @generated
 */
public interface Field extends EObject {
	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute.
	 * The literals are from the enumeration {@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility
	 * @see #setVisibility(Visibility)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_Visibility()
	 * @model
	 * @generated
	 */
	Visibility getVisibility();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(Visibility value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static</em>' attribute.
	 * @see #setIsStatic(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_IsStatic()
	 * @model
	 * @generated
	 */
	boolean isIsStatic();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
	void setIsStatic(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Final</em>' attribute.
	 * @see #setIsFinal(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_IsFinal()
	 * @model
	 * @generated
	 */
	boolean isIsFinal();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsFinal <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Final</em>' attribute.
	 * @see #isIsFinal()
	 * @generated
	 */
	void setIsFinal(boolean value);

	/**
	 * Returns the value of the '<em><b>Displayed Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Displayed Name</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getField_DisplayedName()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getDisplayedName();

} // Field
