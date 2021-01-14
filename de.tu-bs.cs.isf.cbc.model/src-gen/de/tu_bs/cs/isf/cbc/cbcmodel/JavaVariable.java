/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getKind <em>Kind</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getDisplayedName <em>Displayed Name</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariable()
 * @model
 * @generated
 */
public interface JavaVariable extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>"int a"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariable_Name()
	 * @model default="int a"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The default value is <code>"LOCAL"</code>.
	 * The literals are from the enumeration {@link de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind
	 * @see #setKind(VariableKind)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariable_Kind()
	 * @model default="LOCAL" unique="false"
	 * @generated
	 */
	VariableKind getKind();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(VariableKind value);

	/**
	 * Returns the value of the '<em><b>Displayed Name</b></em>' attribute.
	 * The default value is <code>"name"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Displayed Name</em>' attribute.
	 * @see #isSetDisplayedName()
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariable_DisplayedName()
	 * @model default="name" unsettable="true" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return getKind() + \" \" + getName();'"
	 * @generated
	 */
	String getDisplayedName();

	/**
	 * Returns whether the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getDisplayedName <em>Displayed Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Displayed Name</em>' attribute is set.
	 * @see #getDisplayedName()
	 * @generated
	 */
	boolean isSetDisplayedName();

} // JavaVariable
