/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>cbcmodel</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class CbcmodelTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new CbcmodelTests("cbcmodel Tests");
		suite.addTestSuite(AbstractStatementTest.class);
		suite.addTestSuite(SkipStatementTest.class);
		suite.addTestSuite(CompositionStatementTest.class);
		suite.addTestSuite(SelectionStatementTest.class);
		suite.addTestSuite(MethodStatementTest.class);
		suite.addTestSuite(ReturnStatementTest.class);
		suite.addTestSuite(SmallRepetitionStatementTest.class);
		suite.addTestSuite(JavaVariableTest.class);
		suite.addTestSuite(StrengthWeakStatementTest.class);
		suite.addTestSuite(OriginalStatementTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbcmodelTests(String name) {
		super(name);
	}

} //CbcmodelTests
