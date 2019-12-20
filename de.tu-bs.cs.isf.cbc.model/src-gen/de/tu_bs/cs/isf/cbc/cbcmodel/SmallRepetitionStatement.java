/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Small Repetition Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getLoopStatement <em>Loop Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getGuard <em>Guard</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isVariantProven <em>Variant Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPreProven <em>Pre Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPostProven <em>Post Proven</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement()
 * @model
 * @generated
 */
public interface SmallRepetitionStatement extends AbstractStatement {
	/**
	 * Returns the value of the '<em><b>Loop Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Loop Statement</em>' containment reference.
	 * @see #setLoopStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_LoopStatement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getLoopStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getLoopStatement <em>Loop Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Loop Statement</em>' containment reference.
	 * @see #getLoopStatement()
	 * @generated
	 */
	void setLoopStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Variant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant</em>' containment reference.
	 * @see #setVariant(Variant)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_Variant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variant getVariant();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getVariant <em>Variant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variant</em>' containment reference.
	 * @see #getVariant()
	 * @generated
	 */
	void setVariant(Variant value);

	/**
	 * Returns the value of the '<em><b>Invariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invariant</em>' containment reference.
	 * @see #setInvariant(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_Invariant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getInvariant();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getInvariant <em>Invariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invariant</em>' containment reference.
	 * @see #getInvariant()
	 * @generated
	 */
	void setInvariant(Condition value);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard</em>' containment reference.
	 * @see #setGuard(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_Guard()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getGuard();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getGuard <em>Guard</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Condition value);

	/**
	 * Returns the value of the '<em><b>Variant Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variant Proven</em>' attribute.
	 * @see #setVariantProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_VariantProven()
	 * @model
	 * @generated
	 */
	boolean isVariantProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isVariantProven <em>Variant Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variant Proven</em>' attribute.
	 * @see #isVariantProven()
	 * @generated
	 */
	void setVariantProven(boolean value);

	/**
	 * Returns the value of the '<em><b>Pre Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Proven</em>' attribute.
	 * @see #setPreProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_PreProven()
	 * @model
	 * @generated
	 */
	boolean isPreProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPreProven <em>Pre Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Proven</em>' attribute.
	 * @see #isPreProven()
	 * @generated
	 */
	void setPreProven(boolean value);

	/**
	 * Returns the value of the '<em><b>Post Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Proven</em>' attribute.
	 * @see #setPostProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getSmallRepetitionStatement_PostProven()
	 * @model
	 * @generated
	 */
	boolean isPostProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPostProven <em>Post Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Proven</em>' attribute.
	 * @see #isPostProven()
	 * @generated
	 */
	void setPostProven(boolean value);

} // SmallRepetitionStatement
