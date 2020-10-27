/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Variant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ProductVariantImpl#getRefinementChain <em>Refinement Chain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductVariantImpl extends MinimalEObjectImpl.Container implements ProductVariant {
	/**
	 * The default value of the '{@link #getRefinementChain() <em>Refinement Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinementChain()
	 * @generated
	 * @ordered
	 */
	protected static final String REFINEMENT_CHAIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRefinementChain() <em>Refinement Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinementChain()
	 * @generated
	 * @ordered
	 */
	protected String refinementChain = REFINEMENT_CHAIN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductVariantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.PRODUCT_VARIANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRefinementChain() {
		return refinementChain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefinementChain(String newRefinementChain) {
		String oldRefinementChain = refinementChain;
		refinementChain = newRefinementChain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.PRODUCT_VARIANT__REFINEMENT_CHAIN, oldRefinementChain, refinementChain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcmodelPackage.PRODUCT_VARIANT__REFINEMENT_CHAIN:
				return getRefinementChain();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CbcmodelPackage.PRODUCT_VARIANT__REFINEMENT_CHAIN:
				setRefinementChain((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CbcmodelPackage.PRODUCT_VARIANT__REFINEMENT_CHAIN:
				setRefinementChain(REFINEMENT_CHAIN_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CbcmodelPackage.PRODUCT_VARIANT__REFINEMENT_CHAIN:
				return REFINEMENT_CHAIN_EDEFAULT == null ? refinementChain != null : !REFINEMENT_CHAIN_EDEFAULT.equals(refinementChain);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (refinementChain: ");
		result.append(refinementChain);
		result.append(')');
		return result.toString();
	}

} //ProductVariantImpl
