/**
 */
package de.tu_bs.cs.isf.cbc.statistics.tests;

import de.tu_bs.cs.isf.cbc.statistics.StatisticsRegistry;
import de.tu_bs.cs.isf.cbc.statistics.statisticsFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Statistics Registry</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatisticsRegistryTest extends TestCase {

	/**
	 * The fixture for this Statistics Registry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsRegistry fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(StatisticsRegistryTest.class);
	}

	/**
	 * Constructs a new Statistics Registry test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsRegistryTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Statistics Registry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(StatisticsRegistry fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Statistics Registry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsRegistry getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(statisticsFactory.eINSTANCE.createStatisticsRegistry());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //StatisticsRegistryTest
