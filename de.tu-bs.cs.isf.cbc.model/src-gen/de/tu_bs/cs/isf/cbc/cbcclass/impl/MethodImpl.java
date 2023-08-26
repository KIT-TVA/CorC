/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getCbcDiagramURI <em>Cbc Diagram URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getAssignable <em>Assignable</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getCbcStartTriple <em>Cbc Start Triple</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getParentClass <em>Parent Class</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getPostcondition <em>Postcondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl#isIsStatic <em>Is Static</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MethodImpl extends MinimalEObjectImpl.Container implements Method
{
	/**
	 * The default value of the '{@link #getCbcDiagramURI() <em>Cbc Diagram URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCbcDiagramURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CBC_DIAGRAM_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCbcDiagramURI() <em>Cbc Diagram URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCbcDiagramURI()
	 * @generated
	 * @ordered
	 */
	protected String cbcDiagramURI = CBC_DIAGRAM_URI_EDEFAULT;

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
	 * The default value of the '{@link #getAssignable() <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignable()
	 * @generated
	 * @ordered
	 */
	protected static final String ASSIGNABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAssignable() <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignable()
	 * @generated
	 * @ordered
	 */
	protected String assignable = ASSIGNABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCbcStartTriple() <em>Cbc Start Triple</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCbcStartTriple()
	 * @generated
	 * @ordered
	 */
	protected CbCFormula cbcStartTriple;

	/**
	 * The default value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected static final String RETURN_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected String returnType = RETURN_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final Visibility VISIBILITY_EDEFAULT = Visibility.PUBLIC;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected Visibility visibility = VISIBILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNATURE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_STATIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean isStatic = IS_STATIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return CbcclassPackage.Literals.METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCbcDiagramURI()
	{
		return cbcDiagramURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCbcDiagramURI(String newCbcDiagramURI)
	{
		String oldCbcDiagramURI = cbcDiagramURI;
		cbcDiagramURI = newCbcDiagramURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__CBC_DIAGRAM_URI, oldCbcDiagramURI, cbcDiagramURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAssignable()
	{
		return assignable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAssignable(String newAssignable)
	{
		String oldAssignable = assignable;
		assignable = newAssignable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__ASSIGNABLE, oldAssignable, assignable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CbCFormula getCbcStartTriple()
	{
		if (cbcStartTriple != null && cbcStartTriple.eIsProxy())
		{
			InternalEObject oldCbcStartTriple = (InternalEObject)cbcStartTriple;
			cbcStartTriple = (CbCFormula)eResolveProxy(oldCbcStartTriple);
			if (cbcStartTriple != oldCbcStartTriple)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CbcclassPackage.METHOD__CBC_START_TRIPLE, oldCbcStartTriple, cbcStartTriple));
			}
		}
		return cbcStartTriple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbCFormula basicGetCbcStartTriple()
	{
		return cbcStartTriple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCbcStartTriple(CbCFormula newCbcStartTriple, NotificationChain msgs)
	{
		CbCFormula oldCbcStartTriple = cbcStartTriple;
		cbcStartTriple = newCbcStartTriple;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__CBC_START_TRIPLE, oldCbcStartTriple, newCbcStartTriple);
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
	public void setCbcStartTriple(CbCFormula newCbcStartTriple)
	{
		if (newCbcStartTriple != cbcStartTriple)
		{
			NotificationChain msgs = null;
			if (cbcStartTriple != null)
				msgs = ((InternalEObject)cbcStartTriple).eInverseRemove(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, CbCFormula.class, msgs);
			if (newCbcStartTriple != null)
				msgs = ((InternalEObject)newCbcStartTriple).eInverseAdd(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, CbCFormula.class, msgs);
			msgs = basicSetCbcStartTriple(newCbcStartTriple, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__CBC_START_TRIPLE, newCbcStartTriple, newCbcStartTriple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModelClass getParentClass()
	{
		if (eContainerFeatureID() != CbcclassPackage.METHOD__PARENT_CLASS) return null;
		return (ModelClass)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentClass(ModelClass newParentClass, NotificationChain msgs)
	{
		msgs = eBasicSetContainer((InternalEObject)newParentClass, CbcclassPackage.METHOD__PARENT_CLASS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParentClass(ModelClass newParentClass)
	{
		if (newParentClass != eInternalContainer() || (eContainerFeatureID() != CbcclassPackage.METHOD__PARENT_CLASS && newParentClass != null))
		{
			if (EcoreUtil.isAncestor(this, newParentClass))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentClass != null)
				msgs = ((InternalEObject)newParentClass).eInverseAdd(this, CbcclassPackage.MODEL_CLASS__METHODS, ModelClass.class, msgs);
			msgs = basicSetParentClass(newParentClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__PARENT_CLASS, newParentClass, newParentClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getPrecondition()
	{
		Condition precondition = basicGetPrecondition();
		return precondition != null && precondition.eIsProxy() ? (Condition)eResolveProxy((InternalEObject)precondition) : precondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition basicGetPrecondition()
	{
		return cbcStartTriple.getStatement().getPreCondition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrecondition(Condition newPrecondition)
	{
		// TODO: implement this method to set the 'Precondition' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Condition getPostcondition()
	{
		Condition postcondition = basicGetPostcondition();
		return postcondition != null && postcondition.eIsProxy() ? (Condition)eResolveProxy((InternalEObject)postcondition) : postcondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition basicGetPostcondition()
	{
		return cbcStartTriple.getStatement().getPostCondition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPostcondition(Condition newPostcondition)
	{
		// TODO: implement this method to set the 'Postcondition' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReturnType()
	{
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReturnType(String newReturnType)
	{
		String oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters()
	{
		if (parameters == null)
		{
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, CbcclassPackage.METHOD__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Visibility getVisibility()
	{
		return visibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVisibility(Visibility newVisibility)
	{
		Visibility oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__VISIBILITY, oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSignature()
	{
		String staticString = isStatic ? "static " : "";		String params = "";		if (getParameters().size() > 0) {			for (Parameter param : parameters) {				if (!param.getName().equals("ret")) {					params += param.getType() + " " + param.getName() + ", ";				}			}			params = params.substring(0, params.length() - 2);		}		return visibility.toString().toLowerCase() + " " + staticString + returnType + " " + name + "(" + params + ")";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignature(String newSignature)
	{
		// TODO: implement this method to set the 'Signature' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsStatic()
	{
		return isStatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsStatic(boolean newIsStatic)
	{
		boolean oldIsStatic = isStatic;
		isStatic = newIsStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__IS_STATIC, oldIsStatic, isStatic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				if (cbcStartTriple != null)
					msgs = ((InternalEObject)cbcStartTriple).eInverseRemove(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ, CbCFormula.class, msgs);
				return basicSetCbcStartTriple((CbCFormula)otherEnd, msgs);
			case CbcclassPackage.METHOD__PARENT_CLASS:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentClass((ModelClass)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				return basicSetCbcStartTriple(null, msgs);
			case CbcclassPackage.METHOD__PARENT_CLASS:
				return basicSetParentClass(null, msgs);
			case CbcclassPackage.METHOD__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs)
	{
		switch (eContainerFeatureID())
		{
			case CbcclassPackage.METHOD__PARENT_CLASS:
				return eInternalContainer().eInverseRemove(this, CbcclassPackage.MODEL_CLASS__METHODS, ModelClass.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
				return getCbcDiagramURI();
			case CbcclassPackage.METHOD__NAME:
				return getName();
			case CbcclassPackage.METHOD__ASSIGNABLE:
				return getAssignable();
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				if (resolve) return getCbcStartTriple();
				return basicGetCbcStartTriple();
			case CbcclassPackage.METHOD__PARENT_CLASS:
				return getParentClass();
			case CbcclassPackage.METHOD__PRECONDITION:
				if (resolve) return getPrecondition();
				return basicGetPrecondition();
			case CbcclassPackage.METHOD__POSTCONDITION:
				if (resolve) return getPostcondition();
				return basicGetPostcondition();
			case CbcclassPackage.METHOD__RETURN_TYPE:
				return getReturnType();
			case CbcclassPackage.METHOD__PARAMETERS:
				return getParameters();
			case CbcclassPackage.METHOD__VISIBILITY:
				return getVisibility();
			case CbcclassPackage.METHOD__SIGNATURE:
				return getSignature();
			case CbcclassPackage.METHOD__IS_STATIC:
				return isIsStatic();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
				setCbcDiagramURI((String)newValue);
				return;
			case CbcclassPackage.METHOD__NAME:
				setName((String)newValue);
				return;
			case CbcclassPackage.METHOD__ASSIGNABLE:
				setAssignable((String)newValue);
				return;
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				setCbcStartTriple((CbCFormula)newValue);
				return;
			case CbcclassPackage.METHOD__PARENT_CLASS:
				setParentClass((ModelClass)newValue);
				return;
			case CbcclassPackage.METHOD__PRECONDITION:
				setPrecondition((Condition)newValue);
				return;
			case CbcclassPackage.METHOD__POSTCONDITION:
				setPostcondition((Condition)newValue);
				return;
			case CbcclassPackage.METHOD__RETURN_TYPE:
				setReturnType((String)newValue);
				return;
			case CbcclassPackage.METHOD__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case CbcclassPackage.METHOD__VISIBILITY:
				setVisibility((Visibility)newValue);
				return;
			case CbcclassPackage.METHOD__SIGNATURE:
				setSignature((String)newValue);
				return;
			case CbcclassPackage.METHOD__IS_STATIC:
				setIsStatic((Boolean)newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
				setCbcDiagramURI(CBC_DIAGRAM_URI_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__ASSIGNABLE:
				setAssignable(ASSIGNABLE_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				setCbcStartTriple((CbCFormula)null);
				return;
			case CbcclassPackage.METHOD__PARENT_CLASS:
				setParentClass((ModelClass)null);
				return;
			case CbcclassPackage.METHOD__PRECONDITION:
				setPrecondition((Condition)null);
				return;
			case CbcclassPackage.METHOD__POSTCONDITION:
				setPostcondition((Condition)null);
				return;
			case CbcclassPackage.METHOD__RETURN_TYPE:
				setReturnType(RETURN_TYPE_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__PARAMETERS:
				getParameters().clear();
				return;
			case CbcclassPackage.METHOD__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__SIGNATURE:
				setSignature(SIGNATURE_EDEFAULT);
				return;
			case CbcclassPackage.METHOD__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
				return CBC_DIAGRAM_URI_EDEFAULT == null ? cbcDiagramURI != null : !CBC_DIAGRAM_URI_EDEFAULT.equals(cbcDiagramURI);
			case CbcclassPackage.METHOD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CbcclassPackage.METHOD__ASSIGNABLE:
				return ASSIGNABLE_EDEFAULT == null ? assignable != null : !ASSIGNABLE_EDEFAULT.equals(assignable);
			case CbcclassPackage.METHOD__CBC_START_TRIPLE:
				return cbcStartTriple != null;
			case CbcclassPackage.METHOD__PARENT_CLASS:
				return getParentClass() != null;
			case CbcclassPackage.METHOD__PRECONDITION:
				return basicGetPrecondition() != null;
			case CbcclassPackage.METHOD__POSTCONDITION:
				return basicGetPostcondition() != null;
			case CbcclassPackage.METHOD__RETURN_TYPE:
				return RETURN_TYPE_EDEFAULT == null ? returnType != null : !RETURN_TYPE_EDEFAULT.equals(returnType);
			case CbcclassPackage.METHOD__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case CbcclassPackage.METHOD__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case CbcclassPackage.METHOD__SIGNATURE:
				return SIGNATURE_EDEFAULT == null ? getSignature() != null : !SIGNATURE_EDEFAULT.equals(getSignature());
			case CbcclassPackage.METHOD__IS_STATIC:
				return isStatic != IS_STATIC_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (cbcDiagramURI: ");
		result.append(cbcDiagramURI);
		result.append(", name: ");
		result.append(name);
		result.append(", assignable: ");
		result.append(assignable);
		result.append(", returnType: ");
		result.append(returnType);
		result.append(", visibility: ");
		result.append(visibility);
		result.append(", isStatic: ");
		result.append(isStatic);
		result.append(')');
		return result.toString();
	}

} //MethodImpl
