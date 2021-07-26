/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodClassImpl#getMethodClass <em>Method Class</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MethodClassImpl extends MinimalEObjectImpl.Container implements MethodClass {
	/**
	 * The default value of the '{@link #getMethodClass() <em>Method Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClass()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethodClass() <em>Method Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodClass()
	 * @generated
	 * @ordered
	 */
	protected String methodClass = METHOD_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.METHOD_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMethodClass() {
		return methodClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMethodClass(String newMethodClass) {
		String oldMethodClass = methodClass;
		methodClass = newMethodClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.METHOD_CLASS__METHOD_CLASS, oldMethodClass, methodClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcmodelPackage.METHOD_CLASS__METHOD_CLASS:
				return getMethodClass();
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
			case CbcmodelPackage.METHOD_CLASS__METHOD_CLASS:
				setMethodClass((String)newValue);
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
			case CbcmodelPackage.METHOD_CLASS__METHOD_CLASS:
				setMethodClass(METHOD_CLASS_EDEFAULT);
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
			case CbcmodelPackage.METHOD_CLASS__METHOD_CLASS:
				return METHOD_CLASS_EDEFAULT == null ? methodClass != null : !METHOD_CLASS_EDEFAULT.equals(methodClass);
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
		result.append(" (methodClass: ");
		result.append(methodClass);
		result.append(')');
		return result.toString();
	}

} //MethodClassImpl
