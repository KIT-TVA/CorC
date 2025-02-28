/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.AtType;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Security;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cb CFormula</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getStatement <em>Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#isProven <em>Proven</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getCompositionTechnique <em>Composition Technique</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getMethodObj <em>Method Obj</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#isTested <em>Tested</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getAtType <em>At Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl#getSecurity <em>Security</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CbCFormulaImpl extends MinimalEObjectImpl.Container implements CbCFormula {
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
	 * The cached value of the '{@link #getStatement() <em>Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatement()
	 * @generated
	 * @ordered
	 */
	protected AbstractStatement statement;

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
	 * The cached value of the '{@link #getPostCondition() <em>Post Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition postCondition;

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
	 * The default value of the '{@link #getCompositionTechnique() <em>Composition Technique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositionTechnique()
	 * @generated
	 * @ordered
	 */
	protected static final CompositionTechnique COMPOSITION_TECHNIQUE_EDEFAULT = CompositionTechnique.CONTRACT_OVERRIDING;

	/**
	 * The cached value of the '{@link #getCompositionTechnique() <em>Composition Technique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositionTechnique()
	 * @generated
	 * @ordered
	 */
	protected CompositionTechnique compositionTechnique = COMPOSITION_TECHNIQUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_NAME_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getMethodName() <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodName()
	 * @generated
	 * @ordered
	 */
	protected String methodName = METHOD_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMethodObj() <em>Method Obj</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethodObj()
	 * @generated
	 * @ordered
	 */
	protected Method methodObj;

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
	 * The cached value of the '{@link #getAtType() <em>At Type</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAtType()
	 * @generated
	 * @ordered
	 */
	protected EList<AtType> atType;

	/**
	 * The cached value of the '{@link #getSecurity() <em>Security</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurity()
	 * @generated
	 * @ordered
	 */
	protected EList<Security> security;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CbCFormulaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.CB_CFORMULA;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractStatement getStatement() {
		return statement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatement(AbstractStatement newStatement, NotificationChain msgs) {
		AbstractStatement oldStatement = statement;
		statement = newStatement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__STATEMENT, oldStatement, newStatement);
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
	public void setStatement(AbstractStatement newStatement) {
		if (newStatement != statement) {
			NotificationChain msgs = null;
			if (statement != null)
				msgs = ((InternalEObject)statement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__STATEMENT, null, msgs);
			if (newStatement != null)
				msgs = ((InternalEObject)newStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__STATEMENT, null, msgs);
			msgs = basicSetStatement(newStatement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__STATEMENT, newStatement, newStatement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__PRE_CONDITION, oldPreCondition, newPreCondition);
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
				msgs = ((InternalEObject)preCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__PRE_CONDITION, null, msgs);
			if (newPreCondition != null)
				msgs = ((InternalEObject)newPreCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__PRE_CONDITION, null, msgs);
			msgs = basicSetPreCondition(newPreCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__PRE_CONDITION, newPreCondition, newPreCondition));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__POST_CONDITION, oldPostCondition, newPostCondition);
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
				msgs = ((InternalEObject)postCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__POST_CONDITION, null, msgs);
			if (newPostCondition != null)
				msgs = ((InternalEObject)newPostCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CbcmodelPackage.CB_CFORMULA__POST_CONDITION, null, msgs);
			msgs = basicSetPostCondition(newPostCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__POST_CONDITION, newPostCondition, newPostCondition));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__PROVEN, oldProven, proven));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositionTechnique getCompositionTechnique() {
		return compositionTechnique;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompositionTechnique(CompositionTechnique newCompositionTechnique) {
		CompositionTechnique oldCompositionTechnique = compositionTechnique;
		compositionTechnique = newCompositionTechnique == null ? COMPOSITION_TECHNIQUE_EDEFAULT : newCompositionTechnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE, oldCompositionTechnique, compositionTechnique));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMethodName() {
		return methodName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMethodName(String newMethodName) {
		String oldMethodName = methodName;
		methodName = newMethodName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__METHOD_NAME, oldMethodName, methodName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Method getMethodObj() {
		if (methodObj != null && methodObj.eIsProxy()) {
			InternalEObject oldMethodObj = (InternalEObject)methodObj;
			methodObj = (Method)eResolveProxy(oldMethodObj);
			if (methodObj != oldMethodObj) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, oldMethodObj, methodObj));
			}
		}
		return methodObj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Method basicGetMethodObj() {
		return methodObj;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMethodObj(Method newMethodObj, NotificationChain msgs) {
		Method oldMethodObj = methodObj;
		methodObj = newMethodObj;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, oldMethodObj, newMethodObj);
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
	public void setMethodObj(Method newMethodObj) {
		if (newMethodObj != methodObj) {
			NotificationChain msgs = null;
			if (methodObj != null)
				msgs = ((InternalEObject)methodObj).eInverseRemove(this, CbcclassPackage.METHOD__CBC_START_TRIPLE, Method.class, msgs);
			if (newMethodObj != null)
				msgs = ((InternalEObject)newMethodObj).eInverseAdd(this, CbcclassPackage.METHOD__CBC_START_TRIPLE, Method.class, msgs);
			msgs = basicSetMethodObj(newMethodObj, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, newMethodObj, newMethodObj));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CbcmodelPackage.CB_CFORMULA__TESTED, oldTested, tested));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AtType> getAtType() {
		if (atType == null) {
			atType = new EObjectContainmentEList<AtType>(AtType.class, this, CbcmodelPackage.CB_CFORMULA__AT_TYPE);
		}
		return atType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Security> getSecurity() {
		if (security == null) {
			security = new EObjectContainmentEList<Security>(Security.class, this, CbcmodelPackage.CB_CFORMULA__SECURITY);
		}
		return security;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				if (methodObj != null)
					msgs = ((InternalEObject)methodObj).eInverseRemove(this, CbcclassPackage.METHOD__CBC_START_TRIPLE, Method.class, msgs);
				return basicSetMethodObj((Method)otherEnd, msgs);
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
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
				return basicSetStatement(null, msgs);
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
				return basicSetPreCondition(null, msgs);
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				return basicSetPostCondition(null, msgs);
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				return basicSetMethodObj(null, msgs);
			case CbcmodelPackage.CB_CFORMULA__AT_TYPE:
				return ((InternalEList<?>)getAtType()).basicRemove(otherEnd, msgs);
			case CbcmodelPackage.CB_CFORMULA__SECURITY:
				return ((InternalEList<?>)getSecurity()).basicRemove(otherEnd, msgs);
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
			case CbcmodelPackage.CB_CFORMULA__NAME:
				return getName();
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
				return getStatement();
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
				return getPreCondition();
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				return getPostCondition();
			case CbcmodelPackage.CB_CFORMULA__PROVEN:
				return isProven();
			case CbcmodelPackage.CB_CFORMULA__COMMENT:
				return getComment();
			case CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE:
				return getCompositionTechnique();
			case CbcmodelPackage.CB_CFORMULA__CLASS_NAME:
				return getClassName();
			case CbcmodelPackage.CB_CFORMULA__METHOD_NAME:
				return getMethodName();
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				if (resolve) return getMethodObj();
				return basicGetMethodObj();
			case CbcmodelPackage.CB_CFORMULA__TESTED:
				return isTested();
			case CbcmodelPackage.CB_CFORMULA__AT_TYPE:
				return getAtType();
			case CbcmodelPackage.CB_CFORMULA__SECURITY:
				return getSecurity();
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
			case CbcmodelPackage.CB_CFORMULA__NAME:
				setName((String)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
				setStatement((AbstractStatement)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
				setPreCondition((Condition)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				setPostCondition((Condition)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__PROVEN:
				setProven((Boolean)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__COMMENT:
				setComment((String)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE:
				setCompositionTechnique((CompositionTechnique)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__METHOD_NAME:
				setMethodName((String)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				setMethodObj((Method)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__TESTED:
				setTested((Boolean)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__AT_TYPE:
				getAtType().clear();
				getAtType().addAll((Collection<? extends AtType>)newValue);
				return;
			case CbcmodelPackage.CB_CFORMULA__SECURITY:
				getSecurity().clear();
				getSecurity().addAll((Collection<? extends Security>)newValue);
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
			case CbcmodelPackage.CB_CFORMULA__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
				setStatement((AbstractStatement)null);
				return;
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
				setPreCondition((Condition)null);
				return;
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				setPostCondition((Condition)null);
				return;
			case CbcmodelPackage.CB_CFORMULA__PROVEN:
				setProven(PROVEN_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE:
				setCompositionTechnique(COMPOSITION_TECHNIQUE_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__METHOD_NAME:
				setMethodName(METHOD_NAME_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				setMethodObj((Method)null);
				return;
			case CbcmodelPackage.CB_CFORMULA__TESTED:
				setTested(TESTED_EDEFAULT);
				return;
			case CbcmodelPackage.CB_CFORMULA__AT_TYPE:
				getAtType().clear();
				return;
			case CbcmodelPackage.CB_CFORMULA__SECURITY:
				getSecurity().clear();
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
			case CbcmodelPackage.CB_CFORMULA__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
				return statement != null;
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
				return preCondition != null;
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				return postCondition != null;
			case CbcmodelPackage.CB_CFORMULA__PROVEN:
				return proven != PROVEN_EDEFAULT;
			case CbcmodelPackage.CB_CFORMULA__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
			case CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE:
				return compositionTechnique != COMPOSITION_TECHNIQUE_EDEFAULT;
			case CbcmodelPackage.CB_CFORMULA__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case CbcmodelPackage.CB_CFORMULA__METHOD_NAME:
				return METHOD_NAME_EDEFAULT == null ? methodName != null : !METHOD_NAME_EDEFAULT.equals(methodName);
			case CbcmodelPackage.CB_CFORMULA__METHOD_OBJ:
				return methodObj != null;
			case CbcmodelPackage.CB_CFORMULA__TESTED:
				return tested != TESTED_EDEFAULT;
			case CbcmodelPackage.CB_CFORMULA__AT_TYPE:
				return atType != null && !atType.isEmpty();
			case CbcmodelPackage.CB_CFORMULA__SECURITY:
				return security != null && !security.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", proven: ");
		result.append(proven);
		result.append(", comment: ");
		result.append(comment);
		result.append(", compositionTechnique: ");
		result.append(compositionTechnique);
		result.append(", className: ");
		result.append(className);
		result.append(", methodName: ");
		result.append(methodName);
		result.append(", tested: ");
		result.append(tested);
		result.append(')');
		return result.toString();
	}

} //CbCFormulaImpl
