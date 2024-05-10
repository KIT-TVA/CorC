/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getCbcDiagramURI <em>Cbc Diagram URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getAssignable <em>Assignable</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getCbcStartTriple <em>Cbc Start Triple</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getParentClass <em>Parent Class</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getPostcondition <em>Postcondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getSignature <em>Signature</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#isIsStatic <em>Is Static</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod()
 * @model
 * @generated
 */
public interface Method extends EObject {
	/**
	 * Returns the value of the '<em><b>Cbc Diagram URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cbc Diagram URI</em>' attribute.
	 * @see #setCbcDiagramURI(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_CbcDiagramURI()
	 * @model
	 * @generated
	 */
	String getCbcDiagramURI();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getCbcDiagramURI <em>Cbc Diagram URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cbc Diagram URI</em>' attribute.
	 * @see #getCbcDiagramURI()
	 * @generated
	 */
	void setCbcDiagramURI(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Assignable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignable</em>' attribute.
	 * @see #setAssignable(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Assignable()
	 * @model
	 * @generated
	 */
	String getAssignable();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getAssignable <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assignable</em>' attribute.
	 * @see #getAssignable()
	 * @generated
	 */
	void setAssignable(String value);

	/**
	 * Returns the value of the '<em><b>Cbc Start Triple</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodObj <em>Method Obj</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cbc Start Triple</em>' reference.
	 * @see #setCbcStartTriple(CbCFormula)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_CbcStartTriple()
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getMethodObj
	 * @model opposite="methodObj"
	 * @generated
	 */
	CbCFormula getCbcStartTriple();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getCbcStartTriple <em>Cbc Start Triple</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cbc Start Triple</em>' reference.
	 * @see #getCbcStartTriple()
	 * @generated
	 */
	void setCbcStartTriple(CbCFormula value);

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcclass.ModelClass#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' container reference.
	 * @see #setParentClass(ModelClass)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_ParentClass()
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.ModelClass#getMethods
	 * @model opposite="methods" transient="false"
	 * @generated
	 */
	ModelClass getParentClass();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getParentClass <em>Parent Class</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' container reference.
	 * @see #getParentClass()
	 * @generated
	 */
	void setParentClass(ModelClass value);

	/**
	 * Returns the value of the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precondition</em>' reference.
	 * @see #setPrecondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Precondition()
	 * @model transient="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return cbcStartTriple.getStatement().getPreCondition();'"
	 * @generated
	 */
	Condition getPrecondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getPrecondition <em>Precondition</em>}' reference.
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Postcondition</em>' reference.
	 * @see #setPostcondition(Condition)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Postcondition()
	 * @model transient="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return cbcStartTriple.getStatement().getPostCondition();'"
	 * @generated
	 */
	Condition getPostcondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getPostcondition <em>Postcondition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postcondition</em>' reference.
	 * @see #getPostcondition()
	 * @generated
	 */
	void setPostcondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' attribute.
	 * @see #setReturnType(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_ReturnType()
	 * @model
	 * @generated
	 */
	String getReturnType();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getReturnType <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' attribute.
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcclass.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute.
	 * The literals are from the enumeration {@link de.tu_bs.cs.isf.cbc.cbcclass.Visibility}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.Visibility
	 * @see #setVisibility(Visibility)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Visibility()
	 * @model
	 * @generated
	 */
	Visibility getVisibility();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visibility</em>' attribute.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.Visibility
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(Visibility value);

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' attribute.
	 * @see #setSignature(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_Signature()
	 * @model transient="true" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='String staticString = isStatic ? \"static \" : \"\";\t\tString params = \"\";\t\tif (getParameters().size() &gt; 0) {\t\t\tfor (Parameter param : parameters) {\t\t\t\tif (!param.getName().equals(\"ret\")) {\t\t\t\t\tparams += param.getType() + \" \" + param.getName() + \", \";\t\t\t\t}\t\t\t}\t\t\tparams = params.substring(0, params.length() - 2);\t\t}\t\treturn visibility.toString().toLowerCase() + \" \" + staticString + returnType + \" \" + name + \"(\" + params + \")\";'"
	 * @generated
	 */
	String getSignature();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#getSignature <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' attribute.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(String value);

	/**
	 * Returns the value of the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static</em>' attribute.
	 * @see #setIsStatic(boolean)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#getMethod_IsStatic()
	 * @model
	 * @generated
	 */
	boolean isIsStatic();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.Method#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
	void setIsStatic(boolean value);

} // Method
