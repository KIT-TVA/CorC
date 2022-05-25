/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getConfToVarsMap <em>Conf To Vars Map</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getAtTypesToVarsMap <em>At Types To Vars Map</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getCapsulesUsed <em>Capsules Used</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCondition()
 * @model
 * @generated
 */
public interface Condition extends EObject {
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
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCondition_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Conf To Vars Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.Security},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conf To Vars Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conf To Vars Map</em>' map.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCondition_ConfToVarsMap()
	 * @model mapType="de.tu_bs.cs.isf.cbc.cbcmodel.ConfToVarsMap&lt;org.eclipse.emf.ecore.EString, de.tu_bs.cs.isf.cbc.cbcmodel.Security&gt;"
	 * @generated
	 */
	EMap<String, Security> getConfToVarsMap();

	/**
	 * Returns the value of the '<em><b>At Types To Vars Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type list of {@link de.tu_bs.cs.isf.cbc.cbcmodel.AtType},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>At Types To Vars Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>At Types To Vars Map</em>' map.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCondition_AtTypesToVarsMap()
	 * @model mapType="de.tu_bs.cs.isf.cbc.cbcmodel.AtTypesToVarsMap&lt;org.eclipse.emf.ecore.EString, de.tu_bs.cs.isf.cbc.cbcmodel.AtType&gt;"
	 * @generated
	 */
	EMap<String, EList<AtType>> getAtTypesToVarsMap();

	/**
	 * Returns the value of the '<em><b>Capsules Used</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Capsules Used</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Capsules Used</em>' attribute list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCondition_CapsulesUsed()
	 * @model transient="true"
	 * @generated
	 */
	EList<String> getCapsulesUsed();

} // Condition
