/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition3 Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl#getThirdStatement <em>Third Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl#getSecondStatement <em>Second Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl#getFirstStatement <em>First Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl#getSecondIntermediateCondition <em>Second Intermediate Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl#getFirstIntermediateCondition <em>First Intermediate Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Composition3StatementImpl extends AbstractStatementImpl implements Composition3Statement {
	/**
	 * The cached value of the '{@link #getThirdStatement() <em>Third Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThirdStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement thirdStatement;

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
	 * The cached value of the '{@link #getFirstStatement() <em>First Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement firstStatement;

	/**
	 * The cached value of the '{@link #getSecondIntermediateCondition() <em>Second Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondIntermediateCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition secondIntermediateCondition;

	/**
	 * The cached value of the '{@link #getFirstIntermediateCondition() <em>First Intermediate Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstIntermediateCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition firstIntermediateCondition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Composition3StatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.COMPOSITION3_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getThirdStatement() {
		return thirdStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThirdStatement(AbstractStatement newThirdStatement, NotificationChain msgs) {
		AbstractStatement oldThirdStatement = thirdStatement;
		thirdStatement = newThirdStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT, oldThirdStatement, newThirdStatement);
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
	public void setThirdStatement(AbstractStatement newThirdStatement) {
		if (newThirdStatement != thirdStatement) {
			NotificationChain msgs = null;
			if (thirdStatement != null)
				msgs = ((InternalEObject)thirdStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT, null, msgs);
			if (newThirdStatement != null)
				msgs = ((InternalEObject)newThirdStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT, null, msgs);
			msgs = basicSetThirdStatement(newThirdStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT, newThirdStatement, newThirdStatement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT, oldSecondStatement, newSecondStatement);
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
				msgs = ((InternalEObject)secondStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT, null, msgs);
			if (newSecondStatement != null)
				msgs = ((InternalEObject)newSecondStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT, null, msgs);
			msgs = basicSetSecondStatement(newSecondStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT, newSecondStatement, newSecondStatement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT, oldFirstStatement, newFirstStatement);
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
				msgs = ((InternalEObject)firstStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT, null, msgs);
			if (newFirstStatement != null)
				msgs = ((InternalEObject)newFirstStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT, null, msgs);
			msgs = basicSetFirstStatement(newFirstStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT, newFirstStatement, newFirstStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getSecondIntermediateCondition() {
		return secondIntermediateCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondIntermediateCondition(Condition newSecondIntermediateCondition, NotificationChain msgs) {
		Condition oldSecondIntermediateCondition = secondIntermediateCondition;
		secondIntermediateCondition = newSecondIntermediateCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION, oldSecondIntermediateCondition, newSecondIntermediateCondition);
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
	public void setSecondIntermediateCondition(Condition newSecondIntermediateCondition) {
		if (newSecondIntermediateCondition != secondIntermediateCondition) {
			NotificationChain msgs = null;
			if (secondIntermediateCondition != null)
				msgs = ((InternalEObject)secondIntermediateCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION, null, msgs);
			if (newSecondIntermediateCondition != null)
				msgs = ((InternalEObject)newSecondIntermediateCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION, null, msgs);
			msgs = basicSetSecondIntermediateCondition(newSecondIntermediateCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION, newSecondIntermediateCondition, newSecondIntermediateCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getFirstIntermediateCondition() {
		return firstIntermediateCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFirstIntermediateCondition(Condition newFirstIntermediateCondition, NotificationChain msgs) {
		Condition oldFirstIntermediateCondition = firstIntermediateCondition;
		firstIntermediateCondition = newFirstIntermediateCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION, oldFirstIntermediateCondition, newFirstIntermediateCondition);
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
	public void setFirstIntermediateCondition(Condition newFirstIntermediateCondition) {
		if (newFirstIntermediateCondition != firstIntermediateCondition) {
			NotificationChain msgs = null;
			if (firstIntermediateCondition != null)
				msgs = ((InternalEObject)firstIntermediateCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION, null, msgs);
			if (newFirstIntermediateCondition != null)
				msgs = ((InternalEObject)newFirstIntermediateCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION, null, msgs);
			msgs = basicSetFirstIntermediateCondition(newFirstIntermediateCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION, newFirstIntermediateCondition, newFirstIntermediateCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
				return basicSetThirdStatement(null, msgs);
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
				return basicSetSecondStatement(null, msgs);
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
				return basicSetFirstStatement(null, msgs);
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
				return basicSetSecondIntermediateCondition(null, msgs);
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
				return basicSetFirstIntermediateCondition(null, msgs);
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
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
				return getThirdStatement();
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
				return getSecondStatement();
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
				return getFirstStatement();
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
				return getSecondIntermediateCondition();
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
				return getFirstIntermediateCondition();
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
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
				setThirdStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
				setSecondStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
				setFirstStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
				setSecondIntermediateCondition((Condition)newValue);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
				setFirstIntermediateCondition((Condition)newValue);
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
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
				setThirdStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
				setSecondStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
				setFirstStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
				setSecondIntermediateCondition((Condition)null);
				return;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
				setFirstIntermediateCondition((Condition)null);
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
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
				return thirdStatement != null;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
				return secondStatement != null;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
				return firstStatement != null;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
				return secondIntermediateCondition != null;
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
				return firstIntermediateCondition != null;
		}
		return super.eIsSet(featureID);
	}

} //Composition3StatementImpl
