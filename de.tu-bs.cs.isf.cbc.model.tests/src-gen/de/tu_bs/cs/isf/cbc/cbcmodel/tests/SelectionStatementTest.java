/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.tests;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Selection Statement</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SelectionStatementTest extends AbstractStatementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SelectionStatementTest.class);
	}

	/**
	 * Constructs a new Selection Statement test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SelectionStatementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Selection Statement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected SelectionStatement getFixture() {
		return (SelectionStatement)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(CbcmodelFactory.eINSTANCE.createSelectionStatement());
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

} //SelectionStatementTest
