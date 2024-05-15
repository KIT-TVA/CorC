/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.tests;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Strength Weak Statement</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class StrengthWeakStatementTest extends AbstractStatementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(StrengthWeakStatementTest.class);
	}

	/**
	 * Constructs a new Strength Weak Statement test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StrengthWeakStatementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Strength Weak Statement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected StrengthWeakStatement getFixture() {
		return (StrengthWeakStatement)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CbcmodelFactory.eINSTANCE.createStrengthWeakStatement());
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

} //StrengthWeakStatementTest
