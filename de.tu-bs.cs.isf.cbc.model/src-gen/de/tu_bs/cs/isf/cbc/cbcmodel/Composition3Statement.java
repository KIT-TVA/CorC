/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composition3 Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getThirdStatement <em>Third Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondStatement <em>Second Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstStatement <em>First Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondIntermediateCondition <em>Second Intermediate Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstIntermediateCondition <em>First Intermediate Condition</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement()
 * @model
 * @generated
 */
public interface Composition3Statement extends AbstractStatement {
	/**
	 * Returns the value of the '<em><b>Third Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Third Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Third Statement</em>' containment reference.
	 * @see #setThirdStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement_ThirdStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getThirdStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getThirdStatement <em>Third Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Third Statement</em>' containment reference.
	 * @see #getThirdStatement()
	 * @generated
	 */
	void setThirdStatement(AbstractStatement value);

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
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement_SecondStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getSecondStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondStatement <em>Second Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second Statement</em>' containment reference.
	 * @see #getSecondStatement()
	 * @generated
	 */
	void setSecondStatement(AbstractStatement value);

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
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement_FirstStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getFirstStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstStatement <em>First Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Statement</em>' containment reference.
	 * @see #getFirstStatement()
	 * @generated
	 */
	void setFirstStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Second Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Second Intermediate Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second Intermediate Condition</em>' containment reference.
	 * @see #setSecondIntermediateCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement_SecondIntermediateCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getSecondIntermediateCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondIntermediateCondition <em>Second Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second Intermediate Condition</em>' containment reference.
	 * @see #getSecondIntermediateCondition()
	 * @generated
	 */
	void setSecondIntermediateCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>First Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Intermediate Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Intermediate Condition</em>' containment reference.
	 * @see #setFirstIntermediateCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getComposition3Statement_FirstIntermediateCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getFirstIntermediateCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstIntermediateCondition <em>First Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Intermediate Condition</em>' containment reference.
	 * @see #getFirstIntermediateCondition()
	 * @generated
	 */
	void setFirstIntermediateCondition(Condition value);

} // Composition3Statement
