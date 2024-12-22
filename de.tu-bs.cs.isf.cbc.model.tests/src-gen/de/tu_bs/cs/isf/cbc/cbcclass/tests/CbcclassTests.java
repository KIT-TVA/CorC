/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>cbcclass</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class CbcclassTests extends TestSuite {

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
		TestSuite suite = new CbcclassTests("cbcclass Tests");
		suite.addTestSuite(FieldTest.class);
		suite.addTestSuite(MethodTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbcclassTests(String name) {
		super(name);
	}

} //CbcclassTests
