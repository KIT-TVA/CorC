/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selection Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl#getGuards <em>Guards</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl#getCommands <em>Commands</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl#isPreProve <em>Pre Prove</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionStatementImpl extends AbstractStatementImpl implements SelectionStatement {
	/**
	 * The cached value of the '{@link #getGuards() <em>Guards</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuards()
	 * @generated
	 * @ordered
	 */
	protected EList<Condition> guards;

	/**
	 * The cached value of the '{@link #getCommands() <em>Commands</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommands()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractStatement> commands;

	/**
	 * The default value of the '{@link #isPreProve() <em>Pre Prove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreProve()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRE_PROVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPreProve() <em>Pre Prove</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreProve()
	 * @generated
	 * @ordered
	 */
	protected boolean preProve = PRE_PROVE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SelectionStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.SELECTION_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Condition> getGuards() {
		if (guards == null) {
			guards = new EObjectContainmentEList<Condition>(Condition.class, this, CbcmodelPackage.SELECTION_STATEMENT__GUARDS);
		}
		return guards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractStatement> getCommands() {
		if (commands == null) {
			commands = new EObjectContainmentEList<AbstractStatement>(AbstractStatement.class, this, CbcmodelPackage.SELECTION_STATEMENT__COMMANDS);
		}
		return commands;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPreProve() {
		return preProve;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPreProve(boolean newPreProve) {
		boolean oldPreProve = preProve;
		preProve = newPreProve;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SELECTION_STATEMENT__PRE_PROVE, oldPreProve, preProve));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.SELECTION_STATEMENT__GUARDS:
				return ((InternalEList<?>)getGuards()).basicRemove(otherEnd, msgs);
			case CbcmodelPackage.SELECTION_STATEMENT__COMMANDS:
				return ((InternalEList<?>)getCommands()).basicRemove(otherEnd, msgs);
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
			case CbcmodelPackage.SELECTION_STATEMENT__GUARDS:
				return getGuards();
			case CbcmodelPackage.SELECTION_STATEMENT__COMMANDS:
				return getCommands();
			case CbcmodelPackage.SELECTION_STATEMENT__PRE_PROVE:
				return isPreProve();
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
			case CbcmodelPackage.SELECTION_STATEMENT__GUARDS:
				getGuards().clear();
				getGuards().addAll((Collection<? extends Condition>)newValue);
				return;
			case CbcmodelPackage.SELECTION_STATEMENT__COMMANDS:
				getCommands().clear();
				getCommands().addAll((Collection<? extends AbstractStatement>)newValue);
				return;
			case CbcmodelPackage.SELECTION_STATEMENT__PRE_PROVE:
				setPreProve((Boolean)newValue);
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
			case CbcmodelPackage.SELECTION_STATEMENT__GUARDS:
				getGuards().clear();
				return;
			case CbcmodelPackage.SELECTION_STATEMENT__COMMANDS:
				getCommands().clear();
				return;
			case CbcmodelPackage.SELECTION_STATEMENT__PRE_PROVE:
				setPreProve(PRE_PROVE_EDEFAULT);
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
			case CbcmodelPackage.SELECTION_STATEMENT__GUARDS:
				return guards != null && !guards.isEmpty();
			case CbcmodelPackage.SELECTION_STATEMENT__COMMANDS:
				return commands != null && !commands.isEmpty();
			case CbcmodelPackage.SELECTION_STATEMENT__PRE_PROVE:
				return preProve != PRE_PROVE_EDEFAULT;
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
		result.append(" (preProve: ");
		result.append(preProve);
		result.append(')');
		return result.toString();
	}

} //SelectionStatementImpl
