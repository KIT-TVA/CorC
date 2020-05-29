/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Renaming</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenamingImpl#getRenames <em>Renames</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RenamingImpl extends MinimalEObjectImpl.Container implements Renaming {
	/**
	 * The cached value of the '{@link #getRenames() <em>Renames</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRenames()
	 * @generated
	 * @ordered
	 */
	protected EList<Rename> renames;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RenamingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaxonomyPackage.Literals.RENAMING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rename> getRenames() {
		if (renames == null) {
			renames = new EObjectContainmentEList<Rename>(Rename.class, this, TaxonomyPackage.RENAMING__RENAMES);
		}
		return renames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaxonomyPackage.RENAMING__RENAMES:
				return ((InternalEList<?>)getRenames()).basicRemove(otherEnd, msgs);
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
			case TaxonomyPackage.RENAMING__RENAMES:
				return getRenames();
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
			case TaxonomyPackage.RENAMING__RENAMES:
				getRenames().clear();
				getRenames().addAll((Collection<? extends Rename>)newValue);
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
			case TaxonomyPackage.RENAMING__RENAMES:
				getRenames().clear();
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
			case TaxonomyPackage.RENAMING__RENAMES:
				return renames != null && !renames.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RenamingImpl
