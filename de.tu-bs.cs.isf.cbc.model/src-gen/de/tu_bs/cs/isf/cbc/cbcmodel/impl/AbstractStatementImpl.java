/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getId <em>Id</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#isTested <em>Tested</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getContext <em>Context</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getTypeableResult <em>Typeable Result</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getTypeableText <em>Typeable Text</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl#getCodeRepresentation <em>Code Representation</em>}</li>
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
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isTested() <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTested()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TESTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTested() <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTested()
	 * @generated
	 * @ordered
	 */
	protected boolean tested = TESTED_EDEFAULT;

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
	 * The default value of the '{@link #getTypeableText() <em>Typeable Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeableText()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPEABLE_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypeableText() <em>Typeable Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeableText()
	 * @generated
	 * @ordered
	 */
	protected String typeableText = TYPEABLE_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCodeRepresentation() <em>Code Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeRepresentation()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_REPRESENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCodeRepresentation() <em>Code Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeRepresentation()
	 * @generated
	 * @ordered
	 */
	protected String codeRepresentation = CODE_REPRESENTATION_EDEFAULT;

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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public boolean isProven() {
		return proven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTested() {
		return tested;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTested(boolean newTested) {
		boolean oldTested = tested;
		tested = newTested;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__TESTED, oldTested, tested));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public String getTypeableResult() {
		return typeableResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
	@Override
	public String getTypeableText() {
		return typeableText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeableText(String newTypeableText) {
		String oldTypeableText = typeableText;
		typeableText = newTypeableText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_TEXT, oldTypeableText, typeableText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCodeRepresentation() {
		return codeRepresentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCodeRepresentation(String newCodeRepresentation) {
		String oldCodeRepresentation = codeRepresentation;
		codeRepresentation = newCodeRepresentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.ABSTRACT_STATEMENT__CODE_REPRESENTATION, oldCodeRepresentation, codeRepresentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void generateID() {
		if(id==null) id = java.util.UUID.randomUUID().toString();
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__ID:
				return getId();
			case CbcmodelPackage.ABSTRACT_STATEMENT__TESTED:
				return isTested();
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				return getContext();
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				return getTypeableResult();
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_TEXT:
				return getTypeableText();
			case CbcmodelPackage.ABSTRACT_STATEMENT__CODE_REPRESENTATION:
				return getCodeRepresentation();
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__ID:
				setId((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TESTED:
				setTested((Boolean)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				setContext((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				setTypeableResult((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_TEXT:
				setTypeableText((String)newValue);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CODE_REPRESENTATION:
				setCodeRepresentation((String)newValue);
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TESTED:
				setTested(TESTED_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				setContext(CONTEXT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				setTypeableResult(TYPEABLE_RESULT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_TEXT:
				setTypeableText(TYPEABLE_TEXT_EDEFAULT);
				return;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CODE_REPRESENTATION:
				setCodeRepresentation(CODE_REPRESENTATION_EDEFAULT);
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
			case CbcmodelPackage.ABSTRACT_STATEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case CbcmodelPackage.ABSTRACT_STATEMENT__TESTED:
				return tested != TESTED_EDEFAULT;
			case CbcmodelPackage.ABSTRACT_STATEMENT__CONTEXT:
				return CONTEXT_EDEFAULT == null ? context != null : !CONTEXT_EDEFAULT.equals(context);
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_RESULT:
				return TYPEABLE_RESULT_EDEFAULT == null ? typeableResult != null : !TYPEABLE_RESULT_EDEFAULT.equals(typeableResult);
			case CbcmodelPackage.ABSTRACT_STATEMENT__TYPEABLE_TEXT:
				return TYPEABLE_TEXT_EDEFAULT == null ? typeableText != null : !TYPEABLE_TEXT_EDEFAULT.equals(typeableText);
			case CbcmodelPackage.ABSTRACT_STATEMENT__CODE_REPRESENTATION:
				return CODE_REPRESENTATION_EDEFAULT == null ? codeRepresentation != null : !CODE_REPRESENTATION_EDEFAULT.equals(codeRepresentation);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case CbcmodelPackage.ABSTRACT_STATEMENT___GENERATE_ID:
				generateID();
				return null;
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", proven: ");
		result.append(proven);
		result.append(", comment: ");
		result.append(comment);
		result.append(", id: ");
		result.append(id);
		result.append(", tested: ");
		result.append(tested);
		result.append(", context: ");
		result.append(context);
		result.append(", typeableResult: ");
		result.append(typeableResult);
		result.append(", typeableText: ");
		result.append(typeableText);
		result.append(", codeRepresentation: ");
		result.append(codeRepresentation);
		result.append(')');
		return result.toString();
	}

} //AbstractStatementImpl
