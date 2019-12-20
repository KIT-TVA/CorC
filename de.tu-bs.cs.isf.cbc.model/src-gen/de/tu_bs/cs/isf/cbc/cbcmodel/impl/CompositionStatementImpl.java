/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl#getFirstStatement <em>First Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl#getSecondStatement <em>Second Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl#getIntermediateCondition <em>Intermediate Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositionStatementImpl extends AbstractStatementImpl implements CompositionStatement {
	/**
	 * The cached value of the '{@link #getFirstStatement() <em>First Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement firstStatement;

	/**
	 * The cached value of the '{@link #getSecondStatement() <em>Second Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement secondStatement;

	/**
	 * The cached value of the '{@link #getIntermediateCondition() <em>Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntermediateCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition intermediateCondition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositionStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.COMPOSITION_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getFirstStatement() {
		return firstStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFirstStatement(AbstractStatement newFirstStatement, NotificationChain msgs) {
		AbstractStatement oldFirstStatement = firstStatement;
		firstStatement = newFirstStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT, oldFirstStatement, newFirstStatement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFirstStatement(AbstractStatement newFirstStatement) {
		if (newFirstStatement != firstStatement) {
			NotificationChain msgs = null;
			if (firstStatement != null)
				msgs = ((InternalEObject)firstStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT, null, msgs);
			if (newFirstStatement != null)
				msgs = ((InternalEObject)newFirstStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT, null, msgs);
			msgs = basicSetFirstStatement(newFirstStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT, newFirstStatement, newFirstStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getSecondStatement() {
		return secondStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondStatement(AbstractStatement newSecondStatement, NotificationChain msgs) {
		AbstractStatement oldSecondStatement = secondStatement;
		secondStatement = newSecondStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT, oldSecondStatement, newSecondStatement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondStatement(AbstractStatement newSecondStatement) {
		if (newSecondStatement != secondStatement) {
			NotificationChain msgs = null;
			if (secondStatement != null)
				msgs = ((InternalEObject)secondStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT, null, msgs);
			if (newSecondStatement != null)
				msgs = ((InternalEObject)newSecondStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT, null, msgs);
			msgs = basicSetSecondStatement(newSecondStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT, newSecondStatement, newSecondStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getIntermediateCondition() {
		return intermediateCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntermediateCondition(Condition newIntermediateCondition, NotificationChain msgs) {
		Condition oldIntermediateCondition = intermediateCondition;
		intermediateCondition = newIntermediateCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION, oldIntermediateCondition, newIntermediateCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIntermediateCondition(Condition newIntermediateCondition) {
		if (newIntermediateCondition != intermediateCondition) {
			NotificationChain msgs = null;
			if (intermediateCondition != null)
				msgs = ((InternalEObject)intermediateCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION, null, msgs);
			if (newIntermediateCondition != null)
				msgs = ((InternalEObject)newIntermediateCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION, null, msgs);
			msgs = basicSetIntermediateCondition(newIntermediateCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION, newIntermediateCondition, newIntermediateCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT:
				return basicSetFirstStatement(null, msgs);
			case CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT:
				return basicSetSecondStatement(null, msgs);
			case CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION:
				return basicSetIntermediateCondition(null, msgs);
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
			case CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT:
				return getFirstStatement();
			case CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT:
				return getSecondStatement();
			case CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION:
				return getIntermediateCondition();
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
			case CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT:
				setFirstStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT:
				setSecondStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION:
				setIntermediateCondition((Condition)newValue);
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
			case CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT:
				setFirstStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT:
				setSecondStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION:
				setIntermediateCondition((Condition)null);
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
			case CbcmodelPackage.COMPOSITION_STATEMENT__FIRST_STATEMENT:
				return firstStatement != null;
			case CbcmodelPackage.COMPOSITION_STATEMENT__SECOND_STATEMENT:
				return secondStatement != null;
			case CbcmodelPackage.COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION:
				return intermediateCondition != null;
		}
		return super.eIsSet(featureID);
	}

} //CompositionStatementImpl
