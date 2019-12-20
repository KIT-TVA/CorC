/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Small Repetition Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#getLoopStatement <em>Loop Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#getVariant <em>Variant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#isVariantProven <em>Variant Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#isPreProven <em>Pre Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl#isPostProven <em>Post Proven</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SmallRepetitionStatementImpl extends AbstractStatementImpl implements SmallRepetitionStatement {
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
	 * The cached value of the '{@link #getVariant() <em>Variant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariant()
	 * @generated
	 * @ordered
	 */
	protected Variant variant;

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
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Condition guard;

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
	 * The default value of the '{@link #isPreProven() <em>Pre Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreProven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRE_PROVEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPreProven() <em>Pre Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreProven()
	 * @generated
	 * @ordered
	 */
	protected boolean preProven = PRE_PROVEN_EDEFAULT;

	/**
	 * The default value of the '{@link #isPostProven() <em>Post Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPostProven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POST_PROVEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPostProven() <em>Post Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPostProven()
	 * @generated
	 * @ordered
	 */
	protected boolean postProven = POST_PROVEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SmallRepetitionStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.SMALL_REPETITION_STATEMENT;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT, oldLoopStatement, newLoopStatement);
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
				msgs = ((InternalEObject)loopStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT, null, msgs);
			if (newLoopStatement != null)
				msgs = ((InternalEObject)newLoopStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT, null, msgs);
			msgs = basicSetLoopStatement(newLoopStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT, newLoopStatement, newLoopStatement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT, oldVariant, newVariant);
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
				msgs = ((InternalEObject)variant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT, null, msgs);
			if (newVariant != null)
				msgs = ((InternalEObject)newVariant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT, null, msgs);
			msgs = basicSetVariant(newVariant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT, newVariant, newVariant));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT, oldInvariant, newInvariant);
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
				msgs = ((InternalEObject)invariant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT, null, msgs);
			if (newInvariant != null)
				msgs = ((InternalEObject)newInvariant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT, null, msgs);
			msgs = basicSetInvariant(newInvariant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT, newInvariant, newInvariant));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD, oldGuard, newGuard);
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
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD, newGuard, newGuard));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT_PROVEN, oldVariantProven, variantProven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPreProven() {
		return preProven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPreProven(boolean newPreProven) {
		boolean oldPreProven = preProven;
		preProven = newPreProven;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__PRE_PROVEN, oldPreProven, preProven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPostProven() {
		return postProven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPostProven(boolean newPostProven) {
		boolean oldPostProven = postProven;
		postProven = newPostProven;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.SMALL_REPETITION_STATEMENT__POST_PROVEN, oldPostProven, postProven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT:
				return basicSetLoopStatement(null, msgs);
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT:
				return basicSetVariant(null, msgs);
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT:
				return basicSetInvariant(null, msgs);
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD:
				return basicSetGuard(null, msgs);
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
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT:
				return getLoopStatement();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT:
				return getVariant();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT:
				return getInvariant();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD:
				return getGuard();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT_PROVEN:
				return isVariantProven();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__PRE_PROVEN:
				return isPreProven();
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__POST_PROVEN:
				return isPostProven();
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
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT:
				setLoopStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT:
				setVariant((Variant)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT:
				setInvariant((Condition)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD:
				setGuard((Condition)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT_PROVEN:
				setVariantProven((Boolean)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__PRE_PROVEN:
				setPreProven((Boolean)newValue);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__POST_PROVEN:
				setPostProven((Boolean)newValue);
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
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT:
				setLoopStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT:
				setVariant((Variant)null);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT:
				setInvariant((Condition)null);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD:
				setGuard((Condition)null);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT_PROVEN:
				setVariantProven(VARIANT_PROVEN_EDEFAULT);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__PRE_PROVEN:
				setPreProven(PRE_PROVEN_EDEFAULT);
				return;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__POST_PROVEN:
				setPostProven(POST_PROVEN_EDEFAULT);
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
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__LOOP_STATEMENT:
				return loopStatement != null;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT:
				return variant != null;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__INVARIANT:
				return invariant != null;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__GUARD:
				return guard != null;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__VARIANT_PROVEN:
				return variantProven != VARIANT_PROVEN_EDEFAULT;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__PRE_PROVEN:
				return preProven != PRE_PROVEN_EDEFAULT;
			case CbcmodelPackage.SMALL_REPETITION_STATEMENT__POST_PROVEN:
				return postProven != POST_PROVEN_EDEFAULT;
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
		result.append(", preProven: ");
		result.append(preProven);
		result.append(", postProven: ");
		result.append(postProven);
		result.append(')');
		return result.toString();
	}

} //SmallRepetitionStatementImpl
