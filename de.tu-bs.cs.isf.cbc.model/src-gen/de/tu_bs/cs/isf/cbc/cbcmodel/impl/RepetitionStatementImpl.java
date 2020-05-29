/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repetition Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getStartStatement <em>Start Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getLoopStatement <em>Loop Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#getEndStatement <em>End Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl#isVariantProven <em>Variant Proven</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepetitionStatementImpl extends AbstractStatementImpl implements RepetitionStatement {
	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Condition guard;

	/**
	 * The cached value of the '{@link #getInvariant() <em>Invariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariant()
	 * @generated
	 * @ordered
	 */
	protected Condition invariant;

	/**
	 * The cached value of the '{@link #getStartStatement() <em>Start Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement startStatement;

	/**
	 * The cached value of the '{@link #getVariant() <em>Variant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected Variant variant;

	/**
	 * The cached value of the '{@link #getLoopStatement() <em>Loop Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement loopStatement;

	/**
	 * The cached value of the '{@link #getEndStatement() <em>End Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement endStatement;

	/**
	 * The default value of the '{@link #isVariantProven() <em>Variant Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVariantProven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VARIANT_PROVEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVariantProven() <em>Variant Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVariantProven()
	 * @generated
	 * @ordered
	 */
	protected boolean variantProven = VARIANT_PROVEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepetitionStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.REPETITION_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGuard(Condition newGuard, NotificationChain msgs) {
		Condition oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__GUARD, oldGuard, newGuard);
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
	public void setGuard(Condition newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__GUARD, newGuard, newGuard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getInvariant() {
		return invariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvariant(Condition newInvariant, NotificationChain msgs) {
		Condition oldInvariant = invariant;
		invariant = newInvariant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__INVARIANT, oldInvariant, newInvariant);
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
	public void setInvariant(Condition newInvariant) {
		if (newInvariant != invariant) {
			NotificationChain msgs = null;
			if (invariant != null)
				msgs = ((InternalEObject)invariant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__INVARIANT, null, msgs);
			if (newInvariant != null)
				msgs = ((InternalEObject)newInvariant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__INVARIANT, null, msgs);
			msgs = basicSetInvariant(newInvariant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__INVARIANT, newInvariant, newInvariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getStartStatement() {
		return startStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartStatement(AbstractStatement newStartStatement, NotificationChain msgs) {
		AbstractStatement oldStartStatement = startStatement;
		startStatement = newStartStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT, oldStartStatement, newStartStatement);
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
	public void setStartStatement(AbstractStatement newStartStatement) {
		if (newStartStatement != startStatement) {
			NotificationChain msgs = null;
			if (startStatement != null)
				msgs = ((InternalEObject)startStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT, null, msgs);
			if (newStartStatement != null)
				msgs = ((InternalEObject)newStartStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT, null, msgs);
			msgs = basicSetStartStatement(newStartStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT, newStartStatement, newStartStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Variant getVariant() {
		return variant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariant(Variant newVariant, NotificationChain msgs) {
		Variant oldVariant = variant;
		variant = newVariant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__VARIANT, oldVariant, newVariant);
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
	public void setVariant(Variant newVariant) {
		if (newVariant != variant) {
			NotificationChain msgs = null;
			if (variant != null)
				msgs = ((InternalEObject)variant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__VARIANT, null, msgs);
			if (newVariant != null)
				msgs = ((InternalEObject)newVariant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__VARIANT, null, msgs);
			msgs = basicSetVariant(newVariant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__VARIANT, newVariant, newVariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getLoopStatement() {
		return loopStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoopStatement(AbstractStatement newLoopStatement, NotificationChain msgs) {
		AbstractStatement oldLoopStatement = loopStatement;
		loopStatement = newLoopStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT, oldLoopStatement, newLoopStatement);
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
	public void setLoopStatement(AbstractStatement newLoopStatement) {
		if (newLoopStatement != loopStatement) {
			NotificationChain msgs = null;
			if (loopStatement != null)
				msgs = ((InternalEObject)loopStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT, null, msgs);
			if (newLoopStatement != null)
				msgs = ((InternalEObject)newLoopStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT, null, msgs);
			msgs = basicSetLoopStatement(newLoopStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT, newLoopStatement, newLoopStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getEndStatement() {
		return endStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEndStatement(AbstractStatement newEndStatement, NotificationChain msgs) {
		AbstractStatement oldEndStatement = endStatement;
		endStatement = newEndStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT, oldEndStatement, newEndStatement);
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
	public void setEndStatement(AbstractStatement newEndStatement) {
		if (newEndStatement != endStatement) {
			NotificationChain msgs = null;
			if (endStatement != null)
				msgs = ((InternalEObject)endStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT, null, msgs);
			if (newEndStatement != null)
				msgs = ((InternalEObject)newEndStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT, null, msgs);
			msgs = basicSetEndStatement(newEndStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT, newEndStatement, newEndStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isVariantProven() {
		return variantProven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariantProven(boolean newVariantProven) {
		boolean oldVariantProven = variantProven;
		variantProven = newVariantProven;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.REPETITION_STATEMENT__VARIANT_PROVEN, oldVariantProven, variantProven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.REPETITION_STATEMENT__GUARD:
				return basicSetGuard(null, msgs);
			case CbcmodelPackage.REPETITION_STATEMENT__INVARIANT:
				return basicSetInvariant(null, msgs);
			case CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT:
				return basicSetStartStatement(null, msgs);
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT:
				return basicSetVariant(null, msgs);
			case CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT:
				return basicSetLoopStatement(null, msgs);
			case CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT:
				return basicSetEndStatement(null, msgs);
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
			case CbcmodelPackage.REPETITION_STATEMENT__GUARD:
				return getGuard();
			case CbcmodelPackage.REPETITION_STATEMENT__INVARIANT:
				return getInvariant();
			case CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT:
				return getStartStatement();
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT:
				return getVariant();
			case CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT:
				return getLoopStatement();
			case CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT:
				return getEndStatement();
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT_PROVEN:
				return isVariantProven();
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
			case CbcmodelPackage.REPETITION_STATEMENT__GUARD:
				setGuard((Condition)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__INVARIANT:
				setInvariant((Condition)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT:
				setStartStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT:
				setVariant((Variant)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT:
				setLoopStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT:
				setEndStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT_PROVEN:
				setVariantProven((Boolean)newValue);
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
			case CbcmodelPackage.REPETITION_STATEMENT__GUARD:
				setGuard((Condition)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__INVARIANT:
				setInvariant((Condition)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT:
				setStartStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT:
				setVariant((Variant)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT:
				setLoopStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT:
				setEndStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT_PROVEN:
				setVariantProven(VARIANT_PROVEN_EDEFAULT);
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
			case CbcmodelPackage.REPETITION_STATEMENT__GUARD:
				return guard != null;
			case CbcmodelPackage.REPETITION_STATEMENT__INVARIANT:
				return invariant != null;
			case CbcmodelPackage.REPETITION_STATEMENT__START_STATEMENT:
				return startStatement != null;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT:
				return variant != null;
			case CbcmodelPackage.REPETITION_STATEMENT__LOOP_STATEMENT:
				return loopStatement != null;
			case CbcmodelPackage.REPETITION_STATEMENT__END_STATEMENT:
				return endStatement != null;
			case CbcmodelPackage.REPETITION_STATEMENT__VARIANT_PROVEN:
				return variantProven != VARIANT_PROVEN_EDEFAULT;
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
		result.append(" (variantProven: ");
		result.append(variantProven);
		result.append(')');
		return result.toString();
	}

} //RepetitionStatementImpl
