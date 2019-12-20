/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Composition Technique</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getCompositionTechnique()
 * @model
 * @generated
 */
public enum CompositionTechnique implements Enumerator {
	/**
	 * The '<em><b>CONTRACT OVERRIDING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTRACT_OVERRIDING_VALUE
	 * @generated
	 * @ordered
	 */
	CONTRACT_OVERRIDING(0, "CONTRACT_OVERRIDING", "CONTRACT_OVERRIDING"),

	/**
	 * The '<em><b>EXPLICIT CONTRACTING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPLICIT_CONTRACTING_VALUE
	 * @generated
	 * @ordered
	 */
	EXPLICIT_CONTRACTING(1, "EXPLICIT_CONTRACTING", "EXPLICIT_CONTRACTING"),

	/**
	 * The '<em><b>CONJUNCTIVE CONTRACTING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONJUNCTIVE_CONTRACTING_VALUE
	 * @generated
	 * @ordered
	 */
	CONJUNCTIVE_CONTRACTING(2, "CONJUNCTIVE_CONTRACTING", "CONJUNCTIVE_CONTRACTING");

	/**
	 * The '<em><b>CONTRACT OVERRIDING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTRACT_OVERRIDING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONTRACT_OVERRIDING_VALUE = 0;

	/**
	 * The '<em><b>EXPLICIT CONTRACTING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPLICIT_CONTRACTING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXPLICIT_CONTRACTING_VALUE = 1;

	/**
	 * The '<em><b>CONJUNCTIVE CONTRACTING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONJUNCTIVE_CONTRACTING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONJUNCTIVE_CONTRACTING_VALUE = 2;

	/**
	 * An array of all the '<em><b>Composition Technique</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CompositionTechnique[] VALUES_ARRAY =
		new CompositionTechnique[] {
			CONTRACT_OVERRIDING,
			EXPLICIT_CONTRACTING,
			CONJUNCTIVE_CONTRACTING,
		};

	/**
	 * A public read-only list of all the '<em><b>Composition Technique</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CompositionTechnique> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Composition Technique</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CompositionTechnique get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CompositionTechnique result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Composition Technique</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CompositionTechnique getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CompositionTechnique result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Composition Technique</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CompositionTechnique get(int value) {
		switch (value) {
			case CONTRACT_OVERRIDING_VALUE: return CONTRACT_OVERRIDING;
			case EXPLICIT_CONTRACTING_VALUE: return EXPLICIT_CONTRACTING;
			case CONJUNCTIVE_CONTRACTING_VALUE: return CONJUNCTIVE_CONTRACTING;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private CompositionTechnique(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //CompositionTechnique
