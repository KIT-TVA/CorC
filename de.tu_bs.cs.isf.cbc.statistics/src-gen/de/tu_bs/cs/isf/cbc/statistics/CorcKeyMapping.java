/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Corc Key Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyFilePath <em>Key File Path</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyProofProblemHashValue <em>Key Proof Problem Hash Value</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementFormula <em>Corc Element Formula</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementStatement <em>Corc Element Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementId <em>Corc Element Id</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramName <em>Corc Diagram Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramPath <em>Corc Diagram Path</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping()
 * @model
 * @generated
 */
public interface CorcKeyMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Key File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key File Path</em>' attribute.
	 * @see #setKeyFilePath(String)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_KeyFilePath()
	 * @model required="true"
	 * @generated
	 */
	String getKeyFilePath();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyFilePath <em>Key File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key File Path</em>' attribute.
	 * @see #getKeyFilePath()
	 * @generated
	 */
	void setKeyFilePath(String value);

	/**
	 * Returns the value of the '<em><b>Key Proof Problem Hash Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Proof Problem Hash Value</em>' attribute.
	 * @see #setKeyProofProblemHashValue(String)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_KeyProofProblemHashValue()
	 * @model
	 * @generated
	 */
	String getKeyProofProblemHashValue();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyProofProblemHashValue <em>Key Proof Problem Hash Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Proof Problem Hash Value</em>' attribute.
	 * @see #getKeyProofProblemHashValue()
	 * @generated
	 */
	void setKeyProofProblemHashValue(String value);

	/**
	 * Returns the value of the '<em><b>Corc Element Formula</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corc Element Formula</em>' reference.
	 * @see #setCorcElementFormula(EObject)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_CorcElementFormula()
	 * @model required="true"
	 * @generated
	 */
	EObject getCorcElementFormula();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementFormula <em>Corc Element Formula</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corc Element Formula</em>' reference.
	 * @see #getCorcElementFormula()
	 * @generated
	 */
	void setCorcElementFormula(EObject value);

	/**
	 * Returns the value of the '<em><b>Corc Element Statement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corc Element Statement</em>' reference.
	 * @see #setCorcElementStatement(EObject)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_CorcElementStatement()
	 * @model required="true"
	 * @generated
	 */
	EObject getCorcElementStatement();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementStatement <em>Corc Element Statement</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corc Element Statement</em>' reference.
	 * @see #getCorcElementStatement()
	 * @generated
	 */
	void setCorcElementStatement(EObject value);

	/**
	 * Returns the value of the '<em><b>Corc Diagram Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corc Diagram Name</em>' attribute.
	 * @see #setCorcDiagramName(String)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_CorcDiagramName()
	 * @model required="true"
	 * @generated
	 */
	String getCorcDiagramName();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramName <em>Corc Diagram Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corc Diagram Name</em>' attribute.
	 * @see #getCorcDiagramName()
	 * @generated
	 */
	void setCorcDiagramName(String value);

	/**
	 * Returns the value of the '<em><b>Corc Diagram Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corc Diagram Path</em>' attribute.
	 * @see #setCorcDiagramPath(String)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_CorcDiagramPath()
	 * @model required="true"
	 * @generated
	 */
	String getCorcDiagramPath();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramPath <em>Corc Diagram Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corc Diagram Path</em>' attribute.
	 * @see #getCorcDiagramPath()
	 * @generated
	 */
	void setCorcDiagramPath(String value);

	/**
	 * Returns the value of the '<em><b>Corc Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corc Element Id</em>' attribute.
	 * @see #setCorcElementId(String)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getCorcKeyMapping_CorcElementId()
	 * @model unique="false" required="true"
	 * @generated
	 */
	String getCorcElementId();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementId <em>Corc Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corc Element Id</em>' attribute.
	 * @see #getCorcElementId()
	 * @generated
	 */
	void setCorcElementId(String value);

} // CorcKeyMapping
