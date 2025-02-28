/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement <em>Refinement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent <em>Parent</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isProven <em>Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getComment <em>Comment</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getId <em>Id</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isTested <em>Tested</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getContext <em>Context</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getTypeableResult <em>Typeable Result</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getTypeableText <em>Typeable Text</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getCodeRepresentation <em>Code Representation</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement()
 * @model
 * @generated
 */
public interface AbstractStatement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Refinement</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refinement</em>' containment reference.
	 * @see #setRefinement(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Refinement()
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	AbstractStatement getRefinement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement <em>Refinement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refinement</em>' containment reference.
	 * @see #getRefinement()
	 * @generated
	 */
	void setRefinement(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement <em>Refinement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(AbstractStatement)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Parent()
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement
	 * @model opposite="refinement" transient="false"
	 * @generated
	 */
	AbstractStatement getParent();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(AbstractStatement value);

	/**
	 * Returns the value of the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Condition</em>' containment reference.
	 * @see #setPostCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_PostCondition()
	 * @model containment="true"
	 * @generated
	 */
	Condition getPostCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPostCondition <em>Post Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' containment reference.
	 * @see #getPostCondition()
	 * @generated
	 */
	void setPostCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Condition</em>' containment reference.
	 * @see #setPreCondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_PreCondition()
	 * @model containment="true"
	 * @generated
	 */
	Condition getPreCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPreCondition <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' containment reference.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proven</em>' attribute.
	 * @see #setProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Proven()
	 * @model
	 * @generated
	 */
	boolean isProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isProven <em>Proven</em>}' attribute.
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested</em>' attribute.
	 * @see #setTested(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Tested()
	 * @model
	 * @generated
	 */
	boolean isTested();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isTested <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tested</em>' attribute.
	 * @see #isTested()
	 * @generated
	 */
	void setTested(boolean value);

	/**
	 * Returns the value of the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' attribute.
	 * @see #setContext(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_Context()
	 * @model
	 * @generated
	 */
	String getContext();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getContext <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' attribute.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(String value);

	/**
	 * Returns the value of the '<em><b>Typeable Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typeable Result</em>' attribute.
	 * @see #setTypeableResult(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_TypeableResult()
	 * @model
	 * @generated
	 */
	String getTypeableResult();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getTypeableResult <em>Typeable Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typeable Result</em>' attribute.
	 * @see #getTypeableResult()
	 * @generated
	 */
	void setTypeableResult(String value);

	/**
	 * Returns the value of the '<em><b>Typeable Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typeable Text</em>' attribute.
	 * @see #setTypeableText(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_TypeableText()
	 * @model
	 * @generated
	 */
	String getTypeableText();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getTypeableText <em>Typeable Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typeable Text</em>' attribute.
	 * @see #getTypeableText()
	 * @generated
	 */
	void setTypeableText(String value);

	/**
	 * Returns the value of the '<em><b>Code Representation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code Representation</em>' attribute.
	 * @see #setCodeRepresentation(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getAbstractStatement_CodeRepresentation()
	 * @model
	 * @generated
	 */
	String getCodeRepresentation();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getCodeRepresentation <em>Code Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code Representation</em>' attribute.
	 * @see #getCodeRepresentation()
	 * @generated
	 */
	void setCodeRepresentation(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void generateID();

} // AbstractStatement
