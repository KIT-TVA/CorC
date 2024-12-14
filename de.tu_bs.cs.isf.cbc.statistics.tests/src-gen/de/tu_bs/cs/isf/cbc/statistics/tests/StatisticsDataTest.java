/**
 */
package de.tu_bs.cs.isf.cbc.statistics.tests;

import de.tu_bs.cs.isf.cbc.statistics.StatisticsData;
import de.tu_bs.cs.isf.cbc.statistics.statisticsFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Statistics Data</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatisticsDataTest extends TestCase {

	/**
	 * The fixture for this Statistics Data test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsData fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(StatisticsDataTest.class);
	}

	/**
	 * Constructs a new Statistics Data test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsDataTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Statistics Data test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(StatisticsData fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Statistics Data test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsData getFixture() {
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
		setFixture(statisticsFactory.eINSTANCE.createStatisticsData());
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

} //StatisticsDataTest
