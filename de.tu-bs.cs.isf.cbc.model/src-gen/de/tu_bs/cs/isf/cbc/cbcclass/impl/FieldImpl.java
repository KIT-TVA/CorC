/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#getType <em>Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#isIsFinal <em>Is Final</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.FieldImpl#getDisplayedName <em>Displayed Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FieldImpl extends MinimalEObjectImpl.Container implements Field {
	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final Visibility VISIBILITY_EDEFAULT = Visibility.PUBLIC;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected Visibility visibility = VISIBILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_STATIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean isStatic = IS_STATIC_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsFinal() <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsFinal() <em>Is Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean isFinal = IS_FINAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayedName() <em>Displayed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayedName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAYED_NAME_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcclassPackage.Literals.FIELD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisibility(Visibility newVisibility) {
		Visibility oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.FIELD__VISIBILITY, oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.FIELD__TYPE, oldType, type));
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
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.FIELD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsStatic() {
		return isStatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsStatic(boolean newIsStatic) {
		boolean oldIsStatic = isStatic;
		isStatic = newIsStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.FIELD__IS_STATIC, oldIsStatic, isStatic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsFinal() {
		return isFinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsFinal(boolean newIsFinal) {
		boolean oldIsFinal = isFinal;
		isFinal = newIsFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.FIELD__IS_FINAL, oldIsFinal, isFinal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDisplayedName() {
		String staticString = isStatic? "static " : ""; String finalString = isFinal ? "final " : ""; return visibility + " " + staticString + finalString + type + " " + name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcclassPackage.FIELD__VISIBILITY:
				return getVisibility();
			case CbcclassPackage.FIELD__TYPE:
				return getType();
			case CbcclassPackage.FIELD__NAME:
				return getName();
			case CbcclassPackage.FIELD__IS_STATIC:
				return isIsStatic();
			case CbcclassPackage.FIELD__IS_FINAL:
				return isIsFinal();
			case CbcclassPackage.FIELD__DISPLAYED_NAME:
				return getDisplayedName();
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
			case CbcclassPackage.FIELD__VISIBILITY:
				setVisibility((Visibility)newValue);
				return;
			case CbcclassPackage.FIELD__TYPE:
				setType((String)newValue);
				return;
			case CbcclassPackage.FIELD__NAME:
				setName((String)newValue);
				return;
			case CbcclassPackage.FIELD__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case CbcclassPackage.FIELD__IS_FINAL:
				setIsFinal((Boolean)newValue);
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
			case CbcclassPackage.FIELD__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case CbcclassPackage.FIELD__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CbcclassPackage.FIELD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CbcclassPackage.FIELD__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case CbcclassPackage.FIELD__IS_FINAL:
				setIsFinal(IS_FINAL_EDEFAULT);
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
			case CbcclassPackage.FIELD__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case CbcclassPackage.FIELD__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case CbcclassPackage.FIELD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CbcclassPackage.FIELD__IS_STATIC:
				return isStatic != IS_STATIC_EDEFAULT;
			case CbcclassPackage.FIELD__IS_FINAL:
				return isFinal != IS_FINAL_EDEFAULT;
			case CbcclassPackage.FIELD__DISPLAYED_NAME:
				return DISPLAYED_NAME_EDEFAULT == null ? getDisplayedName() != null : !DISPLAYED_NAME_EDEFAULT.equals(getDisplayedName());
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
		result.append(" (visibility: ");
		result.append(visibility);
		result.append(", type: ");
		result.append(type);
		result.append(", name: ");
		result.append(name);
		result.append(", isStatic: ");
		result.append(isStatic);
		result.append(", isFinal: ");
		result.append(isFinal);
		result.append(')');
		return result.toString();
	}

} //FieldImpl
