/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statistics Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getData <em>Data</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsEntry()
 * @model
 * @generated
 */
public interface StatisticsEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data</em>' containment reference.
	 * @see #setData(StatisticsData)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsEntry_Data()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StatisticsData getData();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getData <em>Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data</em>' containment reference.
	 * @see #getData()
	 * @generated
	 */
	void setData(StatisticsData value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' containment reference.
	 * @see #setMapping(CorcKeyMapping)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsEntry_Mapping()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CorcKeyMapping getMapping();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getMapping <em>Mapping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping</em>' containment reference.
	 * @see #getMapping()
	 * @generated
	 */
	void setMapping(CorcKeyMapping value);

} // StatisticsEntry
