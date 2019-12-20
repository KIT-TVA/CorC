/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repetition Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getGuard <em>Guard</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getStartStatement <em>Start Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getLoopStatement <em>Loop Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getEndStatement <em>End Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#isVariantProven <em>Variant Proven</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement()
 * @model
 * @generated
 */
public interface RepetitionStatement extends AbstractStatement {
	/**
	 * Returns the value of the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard</em>' containment reference.
	 * @see #setGuard(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_Guard()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getGuard();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getGuard <em>Guard</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Condition value);

	/**
	 * Returns the value of the '<em><b>Invariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invariant</em>' containment reference.
	 * @see #setInvariant(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_Invariant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getInvariant();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getInvariant <em>Invariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invariant</em>' containment reference.
	 * @see #getInvariant()
	 * @generated
	 */
	void setInvariant(Condition value);

	/**
	 * Returns the value of the '<em><b>Start Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Statement</em>' containment reference.
	 * @see #setStartStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_StartStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getStartStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getStartStatement <em>Start Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Statement</em>' containment reference.
	 * @see #getStartStatement()
	 * @generated
	 */
	void setStartStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Variant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant</em>' containment reference.
	 * @see #setVariant(Variant)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_Variant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variant getVariant();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getVariant <em>Variant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variant</em>' containment reference.
	 * @see #getVariant()
	 * @generated
	 */
	void setVariant(Variant value);

	/**
	 * Returns the value of the '<em><b>Loop Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loop Statement</em>' containment reference.
	 * @see #setLoopStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_LoopStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getLoopStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getLoopStatement <em>Loop Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loop Statement</em>' containment reference.
	 * @see #getLoopStatement()
	 * @generated
	 */
	void setLoopStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>End Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Statement</em>' containment reference.
	 * @see #setEndStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_EndStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getEndStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getEndStatement <em>End Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Statement</em>' containment reference.
	 * @see #getEndStatement()
	 * @generated
	 */
	void setEndStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Variant Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant Proven</em>' attribute.
	 * @see #setVariantProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getRepetitionStatement_VariantProven()
	 * @model
	 * @generated
	 */
	boolean isVariantProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#isVariantProven <em>Variant Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variant Proven</em>' attribute.
	 * @see #isVariantProven()
	 * @generated
	 */
	void setVariantProven(boolean value);

} // RepetitionStatement
