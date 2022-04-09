/**
 */
package de.tu_bs.cs.isf.cbc.statistics.impl;

import de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsData;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry;
import de.tu_bs.cs.isf.cbc.statistics.statisticsFactory;
import de.tu_bs.cs.isf.cbc.statistics.statisticsPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class statisticsPackageImpl extends EPackageImpl implements statisticsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statisticsRegistryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statisticsEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statisticsDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass corcKeyMappingEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tu_bs.cs.isf.cbc.statistics.statisticsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private statisticsPackageImpl() {
		super(eNS_URI, statisticsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link statisticsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static statisticsPackage init() {
		if (isInited) return (statisticsPackage)EPackage.Registry.INSTANCE.getEPackage(statisticsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredstatisticsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		statisticsPackageImpl thestatisticsPackage = registeredstatisticsPackage instanceof statisticsPackageImpl ? (statisticsPackageImpl)registeredstatisticsPackage : new statisticsPackageImpl();

		isInited = true;

		// Create package meta-data objects
		thestatisticsPackage.createPackageContents();

		// Initialize created meta-data
		thestatisticsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thestatisticsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(statisticsPackage.eNS_URI, thestatisticsPackage);
		return thestatisticsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatisticsRegistry() {
		return statisticsRegistryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatisticsRegistry_Entries() {
		return (EReference)statisticsRegistryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatisticsEntry() {
		return statisticsEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatisticsEntry_Data() {
		return (EReference)statisticsEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatisticsEntry_Mapping() {
		return (EReference)statisticsEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatisticsData() {
		return statisticsDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_NumberOfNodes() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_AutoModeTimeInMillis() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_TimeInMillis() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_TimePerStepInMillis() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_NumberOfBranches() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_TotalRuleApps() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_Timestamp() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatisticsData_IsProven() {
		return (EAttribute)statisticsDataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorcKeyMapping() {
		return corcKeyMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorcKeyMapping_KeyFilePath() {
		return (EAttribute)corcKeyMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorcKeyMapping_KeyProofProblemHashValue() {
		return (EAttribute)corcKeyMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorcKeyMapping_CorcElementFormula() {
		return (EReference)corcKeyMappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorcKeyMapping_CorcElementStatement() {
		return (EReference)corcKeyMappingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorcKeyMapping_CorcDiagramName() {
		return (EAttribute)corcKeyMappingEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorcKeyMapping_CorcDiagramPath() {
		return (EAttribute)corcKeyMappingEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCorcKeyMapping_CorcElementId() {
		return (EAttribute)corcKeyMappingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public statisticsFactory getstatisticsFactory() {
		return (statisticsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		statisticsRegistryEClass = createEClass(STATISTICS_REGISTRY);
		createEReference(statisticsRegistryEClass, STATISTICS_REGISTRY__ENTRIES);

		statisticsEntryEClass = createEClass(STATISTICS_ENTRY);
		createEReference(statisticsEntryEClass, STATISTICS_ENTRY__DATA);
		createEReference(statisticsEntryEClass, STATISTICS_ENTRY__MAPPING);

		statisticsDataEClass = createEClass(STATISTICS_DATA);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__NUMBER_OF_BRANCHES);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__NUMBER_OF_NODES);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__AUTO_MODE_TIME_IN_MILLIS);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__TOTAL_RULE_APPS);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__TIME_IN_MILLIS);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__TIME_PER_STEP_IN_MILLIS);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__TIMESTAMP);
		createEAttribute(statisticsDataEClass, STATISTICS_DATA__IS_PROVEN);

		corcKeyMappingEClass = createEClass(CORC_KEY_MAPPING);
		createEAttribute(corcKeyMappingEClass, CORC_KEY_MAPPING__KEY_FILE_PATH);
		createEAttribute(corcKeyMappingEClass, CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE);
		createEReference(corcKeyMappingEClass, CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA);
		createEReference(corcKeyMappingEClass, CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT);
		createEAttribute(corcKeyMappingEClass, CORC_KEY_MAPPING__CORC_ELEMENT_ID);
		createEAttribute(corcKeyMappingEClass, CORC_KEY_MAPPING__CORC_DIAGRAM_NAME);
		createEAttribute(corcKeyMappingEClass, CORC_KEY_MAPPING__CORC_DIAGRAM_PATH);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(statisticsRegistryEClass, StatisticsRegistry.class, "StatisticsRegistry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatisticsRegistry_Entries(), this.getStatisticsEntry(), null, "entries", null, 0, -1, StatisticsRegistry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statisticsEntryEClass, StatisticsEntry.class, "StatisticsEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatisticsEntry_Data(), this.getStatisticsData(), null, "data", null, 1, 1, StatisticsEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStatisticsEntry_Mapping(), this.getCorcKeyMapping(), null, "mapping", null, 1, 1, StatisticsEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statisticsDataEClass, StatisticsData.class, "StatisticsData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStatisticsData_NumberOfBranches(), ecorePackage.getEInt(), "numberOfBranches", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_NumberOfNodes(), ecorePackage.getEInt(), "numberOfNodes", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_AutoModeTimeInMillis(), ecorePackage.getELong(), "autoModeTimeInMillis", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_TotalRuleApps(), ecorePackage.getEInt(), "totalRuleApps", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_TimeInMillis(), ecorePackage.getELong(), "timeInMillis", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_TimePerStepInMillis(), ecorePackage.getEFloat(), "timePerStepInMillis", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_Timestamp(), ecorePackage.getEDate(), "timestamp", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatisticsData_IsProven(), ecorePackage.getEBoolean(), "isProven", null, 0, 1, StatisticsData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(corcKeyMappingEClass, CorcKeyMapping.class, "CorcKeyMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCorcKeyMapping_KeyFilePath(), ecorePackage.getEString(), "keyFilePath", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorcKeyMapping_KeyProofProblemHashValue(), ecorePackage.getEString(), "keyProofProblemHashValue", null, 0, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorcKeyMapping_CorcElementFormula(), ecorePackage.getEObject(), null, "corcElementFormula", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorcKeyMapping_CorcElementStatement(), ecorePackage.getEObject(), null, "corcElementStatement", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorcKeyMapping_CorcElementId(), ecorePackage.getEString(), "corcElementId", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorcKeyMapping_CorcDiagramName(), ecorePackage.getEString(), "corcDiagramName", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCorcKeyMapping_CorcDiagramPath(), ecorePackage.getEString(), "corcDiagramPath", null, 1, 1, CorcKeyMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //statisticsPackageImpl
