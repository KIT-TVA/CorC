/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Algorithm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getDataStructures <em>Data Structures</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getMethods <em>Methods</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getChildAlgorithms <em>Child Algorithms</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getParentAlgorithms <em>Parent Algorithms</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getImports <em>Imports</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm()
 * @model
 * @generated
 */
public interface Algorithm extends EObject {
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
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_Abstract()
	 * @model
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

	/**
	 * Returns the value of the '<em><b>Data Structures</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Structures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Structures</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_DataStructures()
	 * @model containment="true"
	 * @generated
	 */
	EList<DataStructure> getDataStructures();

	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_Methods()
	 * @model containment="true"
	 * @generated
	 */
	EList<Method> getMethods();

	/**
	 * Returns the value of the '<em><b>Child Algorithms</b></em>' reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm}.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getParentAlgorithms <em>Parent Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Algorithms</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Algorithms</em>' reference list.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_ChildAlgorithms()
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getParentAlgorithms
	 * @model opposite="parentAlgorithms"
	 * @generated
	 */
	EList<Algorithm> getChildAlgorithms();

	/**
	 * Returns the value of the '<em><b>Parent Algorithms</b></em>' reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm}.
	 * It is bidirectional and its opposite is '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getChildAlgorithms <em>Child Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Algorithms</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Algorithms</em>' reference list.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_ParentAlgorithms()
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getChildAlgorithms
	 * @model opposite="childAlgorithms"
	 * @generated
	 */
	EList<Algorithm> getParentAlgorithms();

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
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_PreCondition()
	 * @model
	 * @generated
	 */
	String getPreCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPreCondition <em>Pre Condition</em>}' attribute.
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
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_PostCondition()
	 * @model
	 * @generated
	 */
	String getPostCondition();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPostCondition <em>Post Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Condition</em>' attribute.
	 * @see #getPostCondition()
	 * @generated
	 */
	void setPostCondition(String value);

	/**
	 * Returns the value of the '<em><b>Invariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invariant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invariant</em>' attribute.
	 * @see #setInvariant(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_Invariant()
	 * @model
	 * @generated
	 */
	String getInvariant();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getInvariant <em>Invariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invariant</em>' attribute.
	 * @see #getInvariant()
	 * @generated
	 */
	void setInvariant(String value);

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' attribute.
	 * @see #setImports(String)
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage#getAlgorithm_Imports()
	 * @model
	 * @generated
	 */
	String getImports();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getImports <em>Imports</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Imports</em>' attribute.
	 * @see #getImports()
	 * @generated
	 */
	void setImports(String value);

} // Algorithm
