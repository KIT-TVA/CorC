/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getJavaClassURI <em>Java Class URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getFields <em>Fields</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getMethods <em>Methods</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getClassInvariants <em>Class Invariants</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass()
 * @model
 * @generated
 */
public interface ModelClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Java Class URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Class URI</em>' attribute.
	 * @see #setJavaClassURI(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_JavaClassURI()
	 * @model
	 * @generated
	 */
	String getJavaClassURI();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getJavaClassURI <em>Java Class URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Class URI</em>' attribute.
	 * @see #getJavaClassURI()
	 * @generated
	 */
	void setJavaClassURI(String value);

	/**
	 * Returns the value of the '<em><b>Fields</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fields</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_Fields()
	 * @model containment="true"
	 * @generated
	 */
	EList<Field> getFields();

	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method}.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_Methods()
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass
	 * @model opposite="parentClass" containment="true"
	 * @generated
	 */
	EList<Method> getMethods();

	/**
	 * Returns the value of the '<em><b>Class Invariants</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Invariants</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_ClassInvariants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Condition> getClassInvariants();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage#getModelClass_Package()
	 * @model
	 * @generated
	 */
	String getPackage();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

} // ModelClass
