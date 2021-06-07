/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composition Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getFirstStatement <em>First Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getSecondStatement <em>Second Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getIntermediateCondition <em>Intermediate Condition</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCompositionStatement()
 * @model
 * @generated
 */
public interface CompositionStatement extends AbstractStatement {
	/**
	 * Returns the value of the '<em><b>First Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Statement</em>' containment reference.
	 * @see #setFirstStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCompositionStatement_FirstStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getFirstStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getFirstStatement <em>First Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Statement</em>' containment reference.
	 * @see #getFirstStatement()
	 * @generated
	 */
	void setFirstStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Second Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second Statement</em>' containment reference.
	 * @see #setSecondStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCompositionStatement_SecondStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getSecondStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getSecondStatement <em>Second Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second Statement</em>' containment reference.
	 * @see #getSecondStatement()
	 * @generated
	 */
	void setSecondStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intermediate Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intermediate Condition</em>' containment reference.
	 * @see #setIntermediateCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCompositionStatement_IntermediateCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getIntermediateCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getIntermediateCondition <em>Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intermediate Condition</em>' containment reference.
	 * @see #getIntermediateCondition()
	 * @generated
	 */
	void setIntermediateCondition(Condition value);

} // CompositionStatement
