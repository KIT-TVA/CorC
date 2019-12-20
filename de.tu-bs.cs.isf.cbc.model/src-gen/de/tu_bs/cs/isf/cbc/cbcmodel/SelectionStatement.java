/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Selection Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getGuards <em>Guards</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getCommands <em>Commands</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#isPreProve <em>Pre Prove</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSelectionStatement()
 * @model
 * @generated
 */
public interface SelectionStatement extends AbstractStatement {
	/**
	 * Returns the value of the '<em><b>Guards</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guards</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSelectionStatement_Guards()
	 * @model containment="true"
	 * @generated
	 */
	EList<Condition> getGuards();

	/**
	 * Returns the value of the '<em><b>Commands</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commands</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSelectionStatement_Commands()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractStatement> getCommands();

	/**
	 * Returns the value of the '<em><b>Pre Prove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Prove</em>' attribute.
	 * @see #setPreProve(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSelectionStatement_PreProve()
	 * @model
	 * @generated
	 */
	boolean isPreProve();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#isPreProve <em>Pre Prove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Prove</em>' attribute.
	 * @see #isPreProve()
	 * @generated
	 */
	void setPreProve(boolean value);

} // SelectionStatement
