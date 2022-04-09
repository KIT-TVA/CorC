/**
 */
package de.tu_bs.cs.isf.cbc.statistics;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsFactory
 * @model kind="package"
 * @generated
 */
public interface statisticsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "statistics";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://www.tu-braunschweig.de/isf/statistics";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "statistics";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	statisticsPackage eINSTANCE = de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsRegistryImpl <em>Statistics Registry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsRegistryImpl
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsRegistry()
	 * @generated
	 */
	int STATISTICS_REGISTRY = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_REGISTRY__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Statistics Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_REGISTRY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Statistics Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_REGISTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsEntryImpl <em>Statistics Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsEntryImpl
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsEntry()
	 * @generated
	 */
	int STATISTICS_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_ENTRY__DATA = 0;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_ENTRY__MAPPING = 1;

	/**
	 * The number of structural features of the '<em>Statistics Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Statistics Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsDataImpl <em>Statistics Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsDataImpl
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsData()
	 * @generated
	 */
	int STATISTICS_DATA = 2;

	/**
	 * The feature id for the '<em><b>Number Of Branches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__NUMBER_OF_BRANCHES = 0;

	/**
	 * The feature id for the '<em><b>Number Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__NUMBER_OF_NODES = 1;

	/**
	 * The feature id for the '<em><b>Auto Mode Time In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__AUTO_MODE_TIME_IN_MILLIS = 2;

	/**
	 * The feature id for the '<em><b>Total Rule Apps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__TOTAL_RULE_APPS = 3;

	/**
	 * The feature id for the '<em><b>Time In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__TIME_IN_MILLIS = 4;

	/**
	 * The feature id for the '<em><b>Time Per Step In Millis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__TIME_PER_STEP_IN_MILLIS = 5;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__TIMESTAMP = 6;

	/**
	 * The feature id for the '<em><b>Is Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA__IS_PROVEN = 7;

	/**
	 * The number of structural features of the '<em>Statistics Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Statistics Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATISTICS_DATA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl <em>Corc Key Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl
	 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getCorcKeyMapping()
	 * @generated
	 */
	int CORC_KEY_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Key File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__KEY_FILE_PATH = 0;

	/**
	 * The feature id for the '<em><b>Key Proof Problem Hash Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Corc Element Formula</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA = 2;

	/**
	 * The feature id for the '<em><b>Corc Element Statement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT = 3;

	/**
	 * The feature id for the '<em><b>Corc Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__CORC_ELEMENT_ID = 4;

	/**
	 * The feature id for the '<em><b>Corc Diagram Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__CORC_DIAGRAM_NAME = 5;

	/**
	 * The feature id for the '<em><b>Corc Diagram Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING__CORC_DIAGRAM_PATH = 6;

	/**
	 * The number of structural features of the '<em>Corc Key Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Corc Key Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORC_KEY_MAPPING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry <em>Statistics Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statistics Registry</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry
	 * @generated
	 */
	EClass getStatisticsRegistry();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry#getEntries()
	 * @see #getStatisticsRegistry()
	 * @generated
	 */
	EReference getStatisticsRegistry_Entries();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry <em>Statistics Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statistics Entry</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry
	 * @generated
	 */
	EClass getStatisticsEntry();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getData <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getData()
	 * @see #getStatisticsEntry()
	 * @generated
	 */
	EReference getStatisticsEntry_Data();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mapping</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry#getMapping()
	 * @see #getStatisticsEntry()
	 * @generated
	 */
	EReference getStatisticsEntry_Mapping();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData <em>Statistics Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statistics Data</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData
	 * @generated
	 */
	EClass getStatisticsData();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfNodes <em>Number Of Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Nodes</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfNodes()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_NumberOfNodes();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getAutoModeTimeInMillis <em>Auto Mode Time In Millis</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auto Mode Time In Millis</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getAutoModeTimeInMillis()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_AutoModeTimeInMillis();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimeInMillis <em>Time In Millis</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time In Millis</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimeInMillis()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_TimeInMillis();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimePerStepInMillis <em>Time Per Step In Millis</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Per Step In Millis</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimePerStepInMillis()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_TimePerStepInMillis();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfBranches <em>Number Of Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Branches</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getNumberOfBranches()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_NumberOfBranches();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTotalRuleApps <em>Total Rule Apps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Rule Apps</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTotalRuleApps()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_TotalRuleApps();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#getTimestamp()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.StatisticsData#isIsProven <em>Is Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.StatisticsData#isIsProven()
	 * @see #getStatisticsData()
	 * @generated
	 */
	EAttribute getStatisticsData_IsProven();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping <em>Corc Key Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Corc Key Mapping</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping
	 * @generated
	 */
	EClass getCorcKeyMapping();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyFilePath <em>Key File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key File Path</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyFilePath()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EAttribute getCorcKeyMapping_KeyFilePath();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyProofProblemHashValue <em>Key Proof Problem Hash Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key Proof Problem Hash Value</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getKeyProofProblemHashValue()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EAttribute getCorcKeyMapping_KeyProofProblemHashValue();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementFormula <em>Corc Element Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Corc Element Formula</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementFormula()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EReference getCorcKeyMapping_CorcElementFormula();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementStatement <em>Corc Element Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Corc Element Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementStatement()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EReference getCorcKeyMapping_CorcElementStatement();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramName <em>Corc Diagram Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Corc Diagram Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramName()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EAttribute getCorcKeyMapping_CorcDiagramName();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramPath <em>Corc Diagram Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Corc Diagram Path</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcDiagramPath()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EAttribute getCorcKeyMapping_CorcDiagramPath();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementId <em>Corc Element Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Corc Element Id</em>'.
	 * @see de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping#getCorcElementId()
	 * @see #getCorcKeyMapping()
	 * @generated
	 */
	EAttribute getCorcKeyMapping_CorcElementId();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	statisticsFactory getstatisticsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsRegistryImpl <em>Statistics Registry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsRegistryImpl
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsRegistry()
		 * @generated
		 */
		EClass STATISTICS_REGISTRY = eINSTANCE.getStatisticsRegistry();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATISTICS_REGISTRY__ENTRIES = eINSTANCE.getStatisticsRegistry_Entries();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsEntryImpl <em>Statistics Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsEntryImpl
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsEntry()
		 * @generated
		 */
		EClass STATISTICS_ENTRY = eINSTANCE.getStatisticsEntry();

		/**
		 * The meta object literal for the '<em><b>Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATISTICS_ENTRY__DATA = eINSTANCE.getStatisticsEntry_Data();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATISTICS_ENTRY__MAPPING = eINSTANCE.getStatisticsEntry_Mapping();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsDataImpl <em>Statistics Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.StatisticsDataImpl
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getStatisticsData()
		 * @generated
		 */
		EClass STATISTICS_DATA = eINSTANCE.getStatisticsData();

		/**
		 * The meta object literal for the '<em><b>Number Of Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__NUMBER_OF_NODES = eINSTANCE.getStatisticsData_NumberOfNodes();

		/**
		 * The meta object literal for the '<em><b>Auto Mode Time In Millis</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__AUTO_MODE_TIME_IN_MILLIS = eINSTANCE.getStatisticsData_AutoModeTimeInMillis();

		/**
		 * The meta object literal for the '<em><b>Time In Millis</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__TIME_IN_MILLIS = eINSTANCE.getStatisticsData_TimeInMillis();

		/**
		 * The meta object literal for the '<em><b>Time Per Step In Millis</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__TIME_PER_STEP_IN_MILLIS = eINSTANCE.getStatisticsData_TimePerStepInMillis();

		/**
		 * The meta object literal for the '<em><b>Number Of Branches</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__NUMBER_OF_BRANCHES = eINSTANCE.getStatisticsData_NumberOfBranches();

		/**
		 * The meta object literal for the '<em><b>Total Rule Apps</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__TOTAL_RULE_APPS = eINSTANCE.getStatisticsData_TotalRuleApps();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__TIMESTAMP = eINSTANCE.getStatisticsData_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Is Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATISTICS_DATA__IS_PROVEN = eINSTANCE.getStatisticsData_IsProven();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl <em>Corc Key Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl
		 * @see de.tu_bs.cs.isf.cbc.statistics.impl.statisticsPackageImpl#getCorcKeyMapping()
		 * @generated
		 */
		EClass CORC_KEY_MAPPING = eINSTANCE.getCorcKeyMapping();

		/**
		 * The meta object literal for the '<em><b>Key File Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORC_KEY_MAPPING__KEY_FILE_PATH = eINSTANCE.getCorcKeyMapping_KeyFilePath();

		/**
		 * The meta object literal for the '<em><b>Key Proof Problem Hash Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE = eINSTANCE.getCorcKeyMapping_KeyProofProblemHashValue();

		/**
		 * The meta object literal for the '<em><b>Corc Element Formula</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA = eINSTANCE.getCorcKeyMapping_CorcElementFormula();

		/**
		 * The meta object literal for the '<em><b>Corc Element Statement</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT = eINSTANCE.getCorcKeyMapping_CorcElementStatement();

		/**
		 * The meta object literal for the '<em><b>Corc Diagram Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORC_KEY_MAPPING__CORC_DIAGRAM_NAME = eINSTANCE.getCorcKeyMapping_CorcDiagramName();

		/**
		 * The meta object literal for the '<em><b>Corc Diagram Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORC_KEY_MAPPING__CORC_DIAGRAM_PATH = eINSTANCE.getCorcKeyMapping_CorcDiagramPath();

		/**
		 * The meta object literal for the '<em><b>Corc Element Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORC_KEY_MAPPING__CORC_ELEMENT_ID = eINSTANCE.getCorcKeyMapping_CorcElementId();

	}

} //statisticsPackage
