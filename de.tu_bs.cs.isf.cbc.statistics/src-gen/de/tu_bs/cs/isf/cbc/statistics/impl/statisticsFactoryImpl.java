/**
 */
package de.tu_bs.cs.isf.cbc.statistics.impl;

import de.tu_bs.cs.isf.cbc.statistics.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class statisticsFactoryImpl extends EFactoryImpl implements statisticsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static statisticsFactory init() {
		try {
			statisticsFactory thestatisticsFactory = (statisticsFactory)EPackage.Registry.INSTANCE.getEFactory(statisticsPackage.eNS_URI);
			if (thestatisticsFactory != null) {
				return thestatisticsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new statisticsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public statisticsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case statisticsPackage.STATISTICS_REGISTRY: return createStatisticsRegistry();
			case statisticsPackage.STATISTICS_ENTRY: return createStatisticsEntry();
			case statisticsPackage.STATISTICS_DATA: return createStatisticsData();
			case statisticsPackage.CORC_KEY_MAPPING: return createCorcKeyMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsRegistry createStatisticsRegistry() {
		StatisticsRegistryImpl statisticsRegistry = new StatisticsRegistryImpl();
		return statisticsRegistry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsEntry createStatisticsEntry() {
		StatisticsEntryImpl statisticsEntry = new StatisticsEntryImpl();
		return statisticsEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsData createStatisticsData() {
		StatisticsDataImpl statisticsData = new StatisticsDataImpl();
		return statisticsData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorcKeyMapping createCorcKeyMapping() {
		CorcKeyMappingImpl corcKeyMapping = new CorcKeyMappingImpl();
		return corcKeyMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public statisticsPackage getstatisticsPackage() {
		return (statisticsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static statisticsPackage getPackage() {
		return statisticsPackage.eINSTANCE;
	}

} //statisticsFactoryImpl
