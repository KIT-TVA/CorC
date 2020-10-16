/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Renaming</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Renaming#getRename <em>Rename</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRenaming()
 * @model
 * @generated
 */
public interface Renaming extends EObject {
	/**
	 * Returns the value of the '<em><b>Rename</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rename</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rename</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRenaming_Rename()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rename> getRename();

} // Renaming
