/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodSignatureImpl#getMethodSignature <em>Method Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MethodSignatureImpl extends MinimalEObjectImpl.Container implements MethodSignature {
	/**
	 * The default value of the '{@link #getMethodSignature() <em>Method Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodSignature()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_SIGNATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodSignature() <em>Method Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodSignature()
	 * @generated
	 * @ordered
	 */
	protected String methodSignature = METHOD_SIGNATURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodSignatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.METHOD_SIGNATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethodSignature() {
		return methodSignature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethodSignature(String newMethodSignature) {
		String oldMethodSignature = methodSignature;
		methodSignature = newMethodSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.METHOD_SIGNATURE__METHOD_SIGNATURE, oldMethodSignature, methodSignature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcmodelPackage.METHOD_SIGNATURE__METHOD_SIGNATURE:
				return getMethodSignature();
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
			case CbcmodelPackage.METHOD_SIGNATURE__METHOD_SIGNATURE:
				setMethodSignature((String)newValue);
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
			case CbcmodelPackage.METHOD_SIGNATURE__METHOD_SIGNATURE:
				setMethodSignature(METHOD_SIGNATURE_EDEFAULT);
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
			case CbcmodelPackage.METHOD_SIGNATURE__METHOD_SIGNATURE:
				return METHOD_SIGNATURE_EDEFAULT == null ? methodSignature != null : !METHOD_SIGNATURE_EDEFAULT.equals(methodSignature);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (methodSignature: ");
		result.append(methodSignature);
		result.append(')');
		return result.toString();
	}

} //MethodSignatureImpl
