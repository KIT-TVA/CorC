/**
 */
package de.tu_bs.cs.isf.cbc.statistics.tests;

import de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry;
import de.tu_bs.cs.isf.cbc.statistics.statisticsFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Statistics Entry</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class StatisticsEntryTest extends TestCase {

	/**
	 * The fixture for this Statistics Entry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsEntry fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(StatisticsEntryTest.class);
	}

	/**
	 * Constructs a new Statistics Entry test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatisticsEntryTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Statistics Entry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(StatisticsEntry fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Statistics Entry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatisticsEntry getFixture() {
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
		setFixture(statisticsFactory.eINSTANCE.createStatisticsEntry());
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

} //StatisticsEntryTest
