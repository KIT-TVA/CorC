/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getRefinement <em>Refinement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#isProven <em>Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getContext <em>Context</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getTypeableResult <em>Typeable Result</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getTyleableText <em>Tyleable Text</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbstractStatementImpl extends MinimalEObjectImpl.Container implements AbstractStatement {
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
	 * The cached value of the '{@link #getRefinement() <em>Refinement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefinement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement refinement;

	/**
	 * The cached value of the '{@link #getPostCondition() <em>Post Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition postCondition;

	/**
	 * The cached value of the '{@link #getPreCondition() <em>Pre Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition preCondition;

	/**
	 * The default value of the '{@link #isProven() <em>Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isProven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROVEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isProven() <em>Proven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isProven()
	 * @generated
	 * @ordered
	 */
	protected boolean proven = PROVEN_EDEFAULT;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected String context = CONTEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypeableResult() <em>Typeable Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeableResult()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPEABLE_RESULT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeableResult() <em>Typeable Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeableResult()
	 * @generated
	 * @ordered
	 */
	protected String typeableResult = TYPEABLE_RESULT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTyleableText() <em>Tyleable Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTyleableText()
	 * @generated
	 * @ordered
	 */
	protected static final String TYLEABLE_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTyleableText() <em>Tyleable Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTyleableText()
	 * @generated
	 * @ordered
	 */
	protected String tyleableText = TYLEABLE_TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.ABSTRACT_STATEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractStatement getRefinement() {
		return refinement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefinement(AbstractStatement newRefinement, NotificationChain msgs) {
		AbstractStatement oldRefinement = refinement;
		refinement = newRefinement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT, oldRefinement, newRefinement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefinement(AbstractStatement newRefinement) {
		if (newRefinement != refinement) {
			NotificationChain msgs = null;
			if (refinement != null)
				msgs = ((InternalEObject)refinement).eInverseRemove(this, CbcmodelPackage.ABSTRACT_STATEMENT__PARENT, AbstractStatement.class, msgs);
			if (newRefinement != null)
				msgs = ((InternalEObject)newRefinement).eInverseAdd(this, CbcmodelPackage.ABSTRACT_STATEMENT__PARENT, AbstractStatement.class, msgs);
			msgs = basicSetRefinement(newRefinement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT, newRefinement, newRefinement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractStatement getParent() {
		if (eContainerFeatureID() != CbcmodelPackage.ABSTRACT_STATEMENT__PARENT) return null;
		return (AbstractStatement)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(AbstractStatement newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, CbcmodelPackage.ABSTRACT_STATEMENT__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(AbstractStatement newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != CbcmodelPackage.ABSTRACT_STATEMENT__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT, AbstractStatement.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getPostCondition() {
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostCondition(Condition newPostCondition, NotificationChain msgs) {
		Condition oldPostCondition = postCondition;
		postCondition = newPostCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION, oldPostCondition, newPostCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPostCondition(Condition newPostCondition) {
		if (newPostCondition != postCondition) {
			NotificationChain msgs = null;
			if (postCondition != null)
				msgs = ((InternalEObject)postCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION, null, msgs);
			if (newPostCondition != null)
				msgs = ((InternalEObject)newPostCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION, null, msgs);
			msgs = basicSetPostCondition(newPostCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION, newPostCondition, newPostCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getPreCondition() {
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreCondition(Condition newPreCondition, NotificationChain msgs) {
		Condition oldPreCondition = preCondition;
		preCondition = newPreCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION, oldPreCondition, newPreCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreCondition(Condition newPreCondition) {
		if (newPreCondition != preCondition) {
			NotificationChain msgs = null;
			if (preCondition != null)
				msgs = ((InternalEObject)preCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION, null, msgs);
			if (newPreCondition != null)
				msgs = ((InternalEObject)newPreCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION, null, msgs);
			msgs = basicSetPreCondition(newPreCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION, newPreCondition, newPreCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isProven() {
		return proven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProven(boolean newProven) {
		boolean oldProven = proven;
		proven = newProven;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__PROVEN, oldProven, proven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(String newContext) {
		String oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeableResult() {
		return typeableResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeableResult(String newTypeableResult) {
		String oldTypeableResult = typeableResult;
		typeableResult = newTypeableResult;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT, oldTypeableResult, typeableResult));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTyleableText() {
		return tyleableText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTyleableText(String newTyleableText) {
		String oldTyleableText = tyleableText;
		tyleableText = newTyleableText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__TYLEABLE_TEXT, oldTyleableText, tyleableText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				if (refinement != null)
					msgs = ((InternalEObject)refinement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT, null, msgs);
				return basicSetRefinement((AbstractStatement)otherEnd, msgs);
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((AbstractStatement)otherEnd, msgs);
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				return basicSetRefinement(null, msgs);
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				return basicSetParent(null, msgs);
			case CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION:
				return basicSetPostCondition(null, msgs);
			case CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION:
				return basicSetPreCondition(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				return eInternalContainer().eInverseRemove(this, CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT, AbstractStatement.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CbcmodelPackage.ABSTRACT_STATEMENT__NAME:
				return getName();
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				return getRefinement();
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				return getParent();
			case CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION:
				return getPostCondition();
			case CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION:
				return getPreCondition();
			case CbcmodelPackage.ABSTRACT_STATEMENT__PROVEN:
				return isProven();
			case CbcmodelPackage.ABSTRACT_STATEMENT__COMMENT:
				return getComment();
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				return getContext();
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				return getTypeableResult();
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYLEABLE_TEXT:
				return getTyleableText();
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__NAME:
				setName((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				setRefinement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				setParent((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION:
				setPostCondition((Condition)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION:
				setPreCondition((Condition)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PROVEN:
				setProven((Boolean)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__COMMENT:
				setComment((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				setContext((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				setTypeableResult((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYLEABLE_TEXT:
				setTyleableText((String)newValue);
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				setRefinement((AbstractStatement)null);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				setParent((AbstractStatement)null);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION:
				setPostCondition((Condition)null);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION:
				setPreCondition((Condition)null);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PROVEN:
				setProven(PROVEN_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				setTypeableResult(TYPEABLE_RESULT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYLEABLE_TEXT:
				setTyleableText(TYLEABLE_TEXT_EDEFAULT);
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CbcmodelPackage.ABSTRACT_STATEMENT__REFINEMENT:
				return refinement != null;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PARENT:
				return getParent() != null;
			case CbcmodelPackage.ABSTRACT_STATEMENT__POST_CONDITION:
				return postCondition != null;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PRE_CONDITION:
				return preCondition != null;
			case CbcmodelPackage.ABSTRACT_STATEMENT__PROVEN:
				return proven != PROVEN_EDEFAULT;
			case CbcmodelPackage.ABSTRACT_STATEMENT__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				return TYPEABLE_RESULT_EDEFAULT == null ? typeableResult != null : !TYPEABLE_RESULT_EDEFAULT.equals(typeableResult);
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYLEABLE_TEXT:
				return TYLEABLE_TEXT_EDEFAULT == null ? tyleableText != null : !TYLEABLE_TEXT_EDEFAULT.equals(tyleableText);
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
		result.append(", proven: ");
		result.append(proven);
		result.append(", comment: ");
		result.append(comment);
		result.append(", context: ");
		result.append(context);
		result.append(", typeableResult: ");
		result.append(typeableResult);
		result.append(", tyleableText: ");
		result.append(tyleableText);
		result.append(')');
		return result.toString();
	}

} //AbstractStatementImpl
