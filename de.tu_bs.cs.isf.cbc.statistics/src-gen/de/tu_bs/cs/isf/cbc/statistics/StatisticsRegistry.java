/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statistics Registry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsRegistry()
 * @model
 * @generated
 */
public interface StatisticsRegistry extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsRegistry_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<StatisticsEntry> getEntries();

} // StatisticsRegistry
