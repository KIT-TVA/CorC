/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AtType;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Security;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl#getConfToVarsMap <em>Conf To Vars Map</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl#getAtTypesToVarsMap <em>At Types To Vars Map</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl#getCapsulesUsed <em>Capsules Used</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionImpl extends MinimalEObjectImpl.Container implements Condition {
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
	 * The cached value of the '{@link #getConfToVarsMap() <em>Conf To Vars Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfToVarsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Security> confToVarsMap;

	/**
	 * The cached value of the '{@link #getAtTypesToVarsMap() <em>At Types To Vars Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAtTypesToVarsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, EList<AtType>> atTypesToVarsMap;

	/**
	 * The cached value of the '{@link #getCapsulesUsed() <em>Capsules Used</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCapsulesUsed()
	 * @generated
	 * @ordered
	 */
	protected EList<String> capsulesUsed;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CONDITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Security> getConfToVarsMap() {
		if (confToVarsMap == null) {
			confToVarsMap = new EcoreEMap<String,Security>(CbcmodelPackage.Literals.CONF_TO_VARS_MAP, ConfToVarsMapImpl.class, this, CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP);
		}
		return confToVarsMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, EList<AtType>> getAtTypesToVarsMap() {
		if (atTypesToVarsMap == null) {
			atTypesToVarsMap = new EcoreEMap<String,EList<AtType>>(CbcmodelPackage.Literals.AT_TYPES_TO_VARS_MAP, AtTypesToVarsMapImpl.class, this, CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP);
		}
		return atTypesToVarsMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getCapsulesUsed() {
		if (capsulesUsed == null) {
			capsulesUsed = new EDataTypeUniqueEList<String>(String.class, this, CbcmodelPackage.CONDITION__CAPSULES_USED);
		}
		return capsulesUsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP:
				return ((InternalEList<?>)getConfToVarsMap()).basicRemove(otherEnd, msgs);
			case CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP:
				return ((InternalEList<?>)getAtTypesToVarsMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcmodelPackage.CONDITION__NAME:
				return getName();
			case CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP:
				if (coreType) return getConfToVarsMap();
				else return getConfToVarsMap().map();
			case CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP:
				if (coreType) return getAtTypesToVarsMap();
				else return getAtTypesToVarsMap().map();
			case CbcmodelPackage.CONDITION__CAPSULES_USED:
				return getCapsulesUsed();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CbcmodelPackage.CONDITION__NAME:
				setName((String)newValue);
				return;
			case CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP:
				((EStructuralFeature.Setting)getConfToVarsMap()).set(newValue);
				return;
			case CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP:
				((EStructuralFeature.Setting)getAtTypesToVarsMap()).set(newValue);
				return;
			case CbcmodelPackage.CONDITION__CAPSULES_USED:
				getCapsulesUsed().clear();
				getCapsulesUsed().addAll((Collection<? extends String>)newValue);
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
			case CbcmodelPackage.CONDITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP:
				getConfToVarsMap().clear();
				return;
			case CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP:
				getAtTypesToVarsMap().clear();
				return;
			case CbcmodelPackage.CONDITION__CAPSULES_USED:
				getCapsulesUsed().clear();
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
			case CbcmodelPackage.CONDITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CbcmodelPackage.CONDITION__CONF_TO_VARS_MAP:
				return confToVarsMap != null && !confToVarsMap.isEmpty();
			case CbcmodelPackage.CONDITION__AT_TYPES_TO_VARS_MAP:
				return atTypesToVarsMap != null && !atTypesToVarsMap.isEmpty();
			case CbcmodelPackage.CONDITION__CAPSULES_USED:
				return capsulesUsed != null && !capsulesUsed.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", capsulesUsed: ");
		result.append(capsulesUsed);
		result.append(')');
		return result.toString();
	}

} //ConditionImpl
