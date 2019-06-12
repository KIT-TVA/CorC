/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy;

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
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getDataTypes <em>Data Types</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPseudoCode <em>Pseudo Code</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getCorCImpl <em>Cor CImpl</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod()
 * @model
 * @generated
 */
public interface Method extends EObject {
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
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Data Types</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Types</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_DataTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<DataType> getDataTypes();

	/**
	 * Returns the value of the '<em><b>Pseudo Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pseudo Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pseudo Code</em>' attribute.
	 * @see #setPseudoCode(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_PseudoCode()
	 * @model
	 * @generated
	 */
	String getPseudoCode();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPseudoCode <em>Pseudo Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pseudo Code</em>' attribute.
	 * @see #getPseudoCode()
	 * @generated
	 */
	void setPseudoCode(String value);

	/**
	 * Returns the value of the '<em><b>Pre Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Condition</em>' attribute.
	 * @see #setPreCondition(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_PreCondition()
	 * @model
	 * @generated
	 */
	String getPreCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPreCondition <em>Pre Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Condition</em>' attribute.
	 * @see #getPreCondition()
	 * @generated
	 */
	void setPreCondition(String value);

	/**
	 * Returns the value of the '<em><b>Post Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Condition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Condition</em>' attribute.
	 * @see #setPostCondition(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_PostCondition()
	 * @model
	 * @generated
	 */
	String getPostCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPostCondition <em>Post Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' attribute.
	 * @see #getPostCondition()
	 * @generated
	 */
	void setPostCondition(String value);

	/**
	 * Returns the value of the '<em><b>Cor CImpl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cor CImpl</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cor CImpl</em>' attribute.
	 * @see #setCorCImpl(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getMethod_CorCImpl()
	 * @model
	 * @generated
	 */
	String getCorCImpl();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getCorCImpl <em>Cor CImpl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cor CImpl</em>' attribute.
	 * @see #getCorCImpl()
	 * @generated
	 */
	void setCorCImpl(String value);

} // Method
