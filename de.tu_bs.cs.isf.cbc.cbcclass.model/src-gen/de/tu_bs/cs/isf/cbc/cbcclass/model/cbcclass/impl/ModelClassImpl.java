/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getJavaClassURI <em>Java Class URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getFields <em>Fields</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getMethods <em>Methods</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getClassInvariants <em>Class Invariants</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelClassImpl extends MinimalEObjectImpl.Container implements ModelClass {
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
	 * The default value of the '{@link #getJavaClassURI() <em>Java Class URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaClassURI()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_CLASS_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJavaClassURI() <em>Java Class URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaClassURI()
	 * @generated
	 * @ordered
	 */
	protected String javaClassURI = JAVA_CLASS_URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFields() <em>Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFields()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> fields;

	/**
	 * The cached value of the '{@link #getMethods() <em>Methods</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethods()
	 * @generated
	 * @ordered
	 */
	protected EList<Method> methods;

	/**
	 * The cached value of the '{@link #getClassInvariants() <em>Class Invariants</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassInvariants()
	 * @generated
	 * @ordered
	 */
	protected EList<Condition> classInvariants;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcclassPackage.Literals.MODEL_CLASS;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.MODEL_CLASS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getJavaClassURI() {
		return javaClassURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setJavaClassURI(String newJavaClassURI) {
		String oldJavaClassURI = javaClassURI;
		javaClassURI = newJavaClassURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.MODEL_CLASS__JAVA_CLASS_URI,
					oldJavaClassURI, javaClassURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Field> getFields() {
		if (fields == null) {
			fields = new EObjectContainmentEList<Field>(Field.class, this, CbcclassPackage.MODEL_CLASS__FIELDS);
		}
		return fields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Condition> getClassInvariants() {
		if (classInvariants == null) {
			classInvariants = new EObjectContainmentEList<Condition>(Condition.class, this,
					CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS);
		}
		return classInvariants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.MODEL_CLASS__PACKAGE, oldPackage,
					package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Method> getMethods() {
		if (methods == null) {
			methods = new EObjectContainmentWithInverseEList<Method>(Method.class, this,
					CbcclassPackage.MODEL_CLASS__METHODS, CbcclassPackage.METHOD__PARENT_CLASS);
		}
		return methods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CbcclassPackage.MODEL_CLASS__METHODS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getMethods()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CbcclassPackage.MODEL_CLASS__FIELDS:
			return ((InternalEList<?>) getFields()).basicRemove(otherEnd, msgs);
		case CbcclassPackage.MODEL_CLASS__METHODS:
			return ((InternalEList<?>) getMethods()).basicRemove(otherEnd, msgs);
		case CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS:
			return ((InternalEList<?>) getClassInvariants()).basicRemove(otherEnd, msgs);
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
		case CbcclassPackage.MODEL_CLASS__NAME:
			return getName();
		case CbcclassPackage.MODEL_CLASS__JAVA_CLASS_URI:
			return getJavaClassURI();
		case CbcclassPackage.MODEL_CLASS__FIELDS:
			return getFields();
		case CbcclassPackage.MODEL_CLASS__METHODS:
			return getMethods();
		case CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS:
			return getClassInvariants();
		case CbcclassPackage.MODEL_CLASS__PACKAGE:
			return getPackage();
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
		case CbcclassPackage.MODEL_CLASS__NAME:
			setName((String) newValue);
			return;
		case CbcclassPackage.MODEL_CLASS__JAVA_CLASS_URI:
			setJavaClassURI((String) newValue);
			return;
		case CbcclassPackage.MODEL_CLASS__FIELDS:
			getFields().clear();
			getFields().addAll((Collection<? extends Field>) newValue);
			return;
		case CbcclassPackage.MODEL_CLASS__METHODS:
			getMethods().clear();
			getMethods().addAll((Collection<? extends Method>) newValue);
			return;
		case CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS:
			getClassInvariants().clear();
			getClassInvariants().addAll((Collection<? extends Condition>) newValue);
			return;
		case CbcclassPackage.MODEL_CLASS__PACKAGE:
			setPackage((String) newValue);
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
		case CbcclassPackage.MODEL_CLASS__NAME:
			setName(NAME_EDEFAULT);
			return;
		case CbcclassPackage.MODEL_CLASS__JAVA_CLASS_URI:
			setJavaClassURI(JAVA_CLASS_URI_EDEFAULT);
			return;
		case CbcclassPackage.MODEL_CLASS__FIELDS:
			getFields().clear();
			return;
		case CbcclassPackage.MODEL_CLASS__METHODS:
			getMethods().clear();
			return;
		case CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS:
			getClassInvariants().clear();
			return;
		case CbcclassPackage.MODEL_CLASS__PACKAGE:
			setPackage(PACKAGE_EDEFAULT);
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
		case CbcclassPackage.MODEL_CLASS__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case CbcclassPackage.MODEL_CLASS__JAVA_CLASS_URI:
			return JAVA_CLASS_URI_EDEFAULT == null ? javaClassURI != null
					: !JAVA_CLASS_URI_EDEFAULT.equals(javaClassURI);
		case CbcclassPackage.MODEL_CLASS__FIELDS:
			return fields != null && !fields.isEmpty();
		case CbcclassPackage.MODEL_CLASS__METHODS:
			return methods != null && !methods.isEmpty();
		case CbcclassPackage.MODEL_CLASS__CLASS_INVARIANTS:
			return classInvariants != null && !classInvariants.isEmpty();
		case CbcclassPackage.MODEL_CLASS__PACKAGE:
			return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", javaClassURI: ");
		result.append(javaClassURI);
		result.append(", package: ");
		result.append(package_);
		result.append(')');
		return result.toString();
	}

} //ModelClassImpl
