/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Statistics Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfBranches <em>Number Of Branches</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfNodes <em>Number Of Nodes</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getAutoModeTimeInMillis <em>Auto Mode Time In Millis</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTotalRuleApps <em>Total Rule Apps</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimeInMillis <em>Time In Millis</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimePerStepInMillis <em>Time Per Step In Millis</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#isIsProven <em>Is Proven</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData()
 * @model
 * @generated
 */
public interface StatisticsData extends EObject {
	/**
	 * Returns the value of the '<em><b>Number Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Nodes</em>' attribute.
	 * @see #setNumberOfNodes(int)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_NumberOfNodes()
	 * @model
	 * @generated
	 */
	int getNumberOfNodes();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfNodes <em>Number Of Nodes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Nodes</em>' attribute.
	 * @see #getNumberOfNodes()
	 * @generated
	 */
	void setNumberOfNodes(int value);

	/**
	 * Returns the value of the '<em><b>Auto Mode Time In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Mode Time In Millis</em>' attribute.
	 * @see #setAutoModeTimeInMillis(long)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_AutoModeTimeInMillis()
	 * @model
	 * @generated
	 */
	long getAutoModeTimeInMillis();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getAutoModeTimeInMillis <em>Auto Mode Time In Millis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Mode Time In Millis</em>' attribute.
	 * @see #getAutoModeTimeInMillis()
	 * @generated
	 */
	void setAutoModeTimeInMillis(long value);

	/**
	 * Returns the value of the '<em><b>Time In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time In Millis</em>' attribute.
	 * @see #setTimeInMillis(long)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_TimeInMillis()
	 * @model
	 * @generated
	 */
	long getTimeInMillis();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimeInMillis <em>Time In Millis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time In Millis</em>' attribute.
	 * @see #getTimeInMillis()
	 * @generated
	 */
	void setTimeInMillis(long value);

	/**
	 * Returns the value of the '<em><b>Time Per Step In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Per Step In Millis</em>' attribute.
	 * @see #setTimePerStepInMillis(float)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_TimePerStepInMillis()
	 * @model
	 * @generated
	 */
	float getTimePerStepInMillis();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimePerStepInMillis <em>Time Per Step In Millis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Per Step In Millis</em>' attribute.
	 * @see #getTimePerStepInMillis()
	 * @generated
	 */
	void setTimePerStepInMillis(float value);

	/**
	 * Returns the value of the '<em><b>Number Of Branches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Branches</em>' attribute.
	 * @see #setNumberOfBranches(int)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_NumberOfBranches()
	 * @model
	 * @generated
	 */
	int getNumberOfBranches();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfBranches <em>Number Of Branches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Branches</em>' attribute.
	 * @see #getNumberOfBranches()
	 * @generated
	 */
	void setNumberOfBranches(int value);

	/**
	 * Returns the value of the '<em><b>Total Rule Apps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Rule Apps</em>' attribute.
	 * @see #setTotalRuleApps(int)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_TotalRuleApps()
	 * @model
	 * @generated
	 */
	int getTotalRuleApps();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTotalRuleApps <em>Total Rule Apps</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Rule Apps</em>' attribute.
	 * @see #getTotalRuleApps()
	 * @generated
	 */
	void setTotalRuleApps(int value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Date)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_Timestamp()
	 * @model
	 * @generated
	 */
	Date getTimestamp();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Date value);

	/**
	 * Returns the value of the '<em><b>Is Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Proven</em>' attribute.
	 * @see #setIsProven(boolean)
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#getStatisticsData_IsProven()
	 * @model
	 * @generated
	 */
	boolean isIsProven();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#isIsProven <em>Is Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Proven</em>' attribute.
	 * @see #isIsProven()
	 * @generated
	 */
	void setIsProven(boolean value);

} // StatisticsData
