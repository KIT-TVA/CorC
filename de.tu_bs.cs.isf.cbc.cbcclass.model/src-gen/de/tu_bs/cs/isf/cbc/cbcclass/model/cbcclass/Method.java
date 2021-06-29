/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcDiagramURI <em>Cbc Diagram URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getSignature <em>Signature</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getAssignable <em>Assignable</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcStartTriple <em>Cbc Start Triple</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass <em>Parent Class</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPostcondition <em>Postcondition</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod()
 * @model
 * @generated
 */
public interface Method extends EObject {
	/**
	 * Returns the value of the '<em><b>Cbc Diagram URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cbc Diagram URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cbc Diagram URI</em>' attribute.
	 * @see #setCbcDiagramURI(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_CbcDiagramURI()
	 * @model
	 * @generated
	 */
	String getCbcDiagramURI();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcDiagramURI <em>Cbc Diagram URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cbc Diagram URI</em>' attribute.
	 * @see #getCbcDiagramURI()
	 * @generated
	 */
	void setCbcDiagramURI(String value);

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' attribute.
	 * @see #setSignature(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_Signature()
	 * @model
	 * @generated
	 */
	String getSignature();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getSignature <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' attribute.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(String value);

	/**
	 * Returns the value of the '<em><b>Assignable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignable</em>' attribute.
	 * @see #setAssignable(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_Assignable()
	 * @model
	 * @generated
	 */
	String getAssignable();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getAssignable <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assignable</em>' attribute.
	 * @see #getAssignable()
	 * @generated
	 */
	void setAssignable(String value);

	/**
	 * Returns the value of the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Precondition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precondition</em>' reference.
	 * @see #setPrecondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_Precondition()
	 * @model transient="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return cbcStartTriple.getPreCondition();'"
	 * @generated
	 */
	Condition getPrecondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPrecondition <em>Precondition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition</em>' reference.
	 * @see #getPrecondition()
	 * @generated
	 */
	void setPrecondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Postcondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Postcondition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Postcondition</em>' reference.
	 * @see #setPostcondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_Postcondition()
	 * @model transient="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return cbcStartTriple.getPostCondition();'"
	 * @generated
	 */
	Condition getPostcondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPostcondition <em>Postcondition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postcondition</em>' reference.
	 * @see #getPostcondition()
	 * @generated
	 */
	void setPostcondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Cbc Start Triple</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodObj <em>Method Obj</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cbc Start Triple</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cbc Start Triple</em>' reference.
	 * @see #setCbcStartTriple(CbCFormula)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_CbcStartTriple()
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodObj
	 * @model opposite="methodObj"
	 * @generated
	 */
	CbCFormula getCbcStartTriple();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcStartTriple <em>Cbc Start Triple</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cbc Start Triple</em>' reference.
	 * @see #getCbcStartTriple()
	 * @generated
	 */
	void setCbcStartTriple(CbCFormula value);

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Class</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' container reference.
	 * @see #setParentClass(ModelClass)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getMethod_ParentClass()
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getMethods
	 * @model opposite="methods" transient="false"
	 * @generated
	 */
	ModelClass getParentClass();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass <em>Parent Class</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' container reference.
	 * @see #getParentClass()
	 * @generated
	 */
	void setParentClass(ModelClass value);

} // Method
