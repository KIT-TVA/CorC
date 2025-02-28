/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage
 * @generated
 */
public interface statisticsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	statisticsFactory eINSTANCE = de.tu_bs.cs.isf.cbc.statistics.impl.statisticsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Statistics Registry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Statistics Registry</em>'.
	 * @generated
	 */
	StatisticsRegistry createStatisticsRegistry();

	/**
	 * Returns a new object of class '<em>Statistics Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Statistics Entry</em>'.
	 * @generated
	 */
	StatisticsEntry createStatisticsEntry();

	/**
	 * Returns a new object of class '<em>Statistics Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Statistics Data</em>'.
	 * @generated
	 */
	StatisticsData createStatisticsData();

	/**
	 * Returns a new object of class '<em>Corc Key Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Corc Key Mapping</em>'.
	 * @generated
	 */
	CorcKeyMapping createCorcKeyMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	statisticsPackage getstatisticsPackage();

} //statisticsFactory
