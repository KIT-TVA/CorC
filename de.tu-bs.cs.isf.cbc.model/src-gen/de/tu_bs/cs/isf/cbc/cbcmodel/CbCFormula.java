/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cb CFormula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getStatement <em>Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#isProven <em>Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getComment <em>Comment</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getCompositionTechnique <em>Composition Technique</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getTaxMethod <em>Tax Method</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getClassName <em>Class Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodName <em>Method Name</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula()
 * @model
 * @generated
 */
public interface CbCFormula extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statement</em>' containment reference.
	 * @see #setStatement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_Statement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractStatement getStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getStatement <em>Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Statement</em>' containment reference.
	 * @see #getStatement()
	 * @generated
	 */
	void setStatement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Condition</em>' containment reference.
	 * @see #setPreCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_PreCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getPreCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPreCondition <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' containment reference.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Condition</em>' containment reference.
	 * @see #setPostCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_PostCondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Condition getPostCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPostCondition <em>Post Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' containment reference.
	 * @see #getPostCondition()
	 * @generated
	 */
	void setPostCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proven</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proven</em>' attribute.
	 * @see #setProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_Proven()
	 * @model
	 * @generated
	 */
	boolean isProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#isProven <em>Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Proven</em>' attribute.
	 * @see #isProven()
	 * @generated
	 */
	void setProven(boolean value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Composition Technique</b></em>' attribute.
	 * The default value is <code>"CONTRACT_OVERRIDING"</code>.
	 * The literals are from the enumeration {@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composition Technique</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composition Technique</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique
	 * @see #setCompositionTechnique(CompositionTechnique)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_CompositionTechnique()
	 * @model default="CONTRACT_OVERRIDING"
	 * @generated
	 */
	CompositionTechnique getCompositionTechnique();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getCompositionTechnique <em>Composition Technique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composition Technique</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique
	 * @see #getCompositionTechnique()
	 * @generated
	 */
	void setCompositionTechnique(CompositionTechnique value);

	/**
	 * Returns the value of the '<em><b>Tax Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tax Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tax Method</em>' attribute.
	 * @see #setTaxMethod(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_TaxMethod()
	 * @model unique="false"
	 * @generated
	 */
	String getTaxMethod();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getTaxMethod <em>Tax Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tax Method</em>' attribute.
	 * @see #getTaxMethod()
	 * @generated
	 */
	void setTaxMethod(String value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_ClassName()
	 * @model default=""
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Method Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Name</em>' attribute.
	 * @see #setMethodName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCbCFormula_MethodName()
	 * @model default=""
	 * @generated
	 */
	String getMethodName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodName <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Name</em>' attribute.
	 * @see #getMethodName()
	 * @generated
	 */
	void setMethodName(String value);

} // CbCFormula
