/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Variant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant#getRefinementChain <em>Refinement Chain</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getProductVariant()
 * @model
 * @generated
 */
public interface ProductVariant extends EObject {
	/**
	 * Returns the value of the '<em><b>Refinement Chain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refinement Chain</em>' attribute.
	 * @see #setRefinementChain(String)
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getProductVariant_RefinementChain()
	 * @model
	 * @generated
	 */
	String getRefinementChain();

	/**
	 * Sets the value of the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant#getRefinementChain <em>Refinement Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refinement Chain</em>' attribute.
	 * @see #getRefinementChain()
	 * @generated
	 */
	void setRefinementChain(String value);

} // ProductVariant
