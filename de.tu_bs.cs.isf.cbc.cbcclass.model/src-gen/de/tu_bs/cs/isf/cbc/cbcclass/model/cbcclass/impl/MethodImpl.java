/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

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
 * An implementation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getCbcDiagramURI <em>Cbc Diagram URI</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getAssignable <em>Assignable</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getCbcStartTriple <em>Cbc Start Triple</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getParentClass <em>Parent Class</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl#getPostcondition <em>Postcondition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MethodImpl extends MinimalEObjectImpl.Container implements Method {
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
	 * The default value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected String signature = SIGNATURE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MethodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcclassPackage.Literals.METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCbcDiagramURI() {
		return cbcDiagramURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCbcDiagramURI(String newCbcDiagramURI) {
		String oldCbcDiagramURI = cbcDiagramURI;
		cbcDiagramURI = newCbcDiagramURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__CBC_DIAGRAM_URI,
					oldCbcDiagramURI, cbcDiagramURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(String newSignature) {
		String oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__SIGNATURE, oldSignature,
					signature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAssignable() {
		return assignable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignable(String newAssignable) {
		String oldAssignable = assignable;
		assignable = newAssignable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__ASSIGNABLE, oldAssignable,
					assignable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getPrecondition() {
		Condition precondition = basicGetPrecondition();
		return precondition != null && precondition.eIsProxy()
				? (Condition) eResolveProxy((InternalEObject) precondition)
				: precondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition basicGetPrecondition() {
		return cbcStartTriple.getPreCondition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPrecondition(Condition newPrecondition) {

		cbcStartTriple.setPreCondition(newPrecondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getPostcondition() {
		Condition postcondition = basicGetPostcondition();
		return postcondition != null && postcondition.eIsProxy()
				? (Condition) eResolveProxy((InternalEObject) postcondition)
				: postcondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition basicGetPostcondition() {
		return cbcStartTriple.getPostCondition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setPostcondition(Condition newPostcondition) {

		cbcStartTriple.setPostCondition(newPostcondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbCFormula getCbcStartTriple() {
		if (cbcStartTriple != null && cbcStartTriple.eIsProxy()) {
			InternalEObject oldCbcStartTriple = (InternalEObject) cbcStartTriple;
			cbcStartTriple = (CbCFormula) eResolveProxy(oldCbcStartTriple);
			if (cbcStartTriple != oldCbcStartTriple) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CbcclassPackage.METHOD__CBC_START_TRIPLE,
							oldCbcStartTriple, cbcStartTriple));
			}
		}
		return cbcStartTriple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbCFormula basicGetCbcStartTriple() {
		return cbcStartTriple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCbcStartTriple(CbCFormula newCbcStartTriple, NotificationChain msgs) {
		CbCFormula oldCbcStartTriple = cbcStartTriple;
		cbcStartTriple = newCbcStartTriple;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CbcclassPackage.METHOD__CBC_START_TRIPLE, oldCbcStartTriple, newCbcStartTriple);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCbcStartTriple(CbCFormula newCbcStartTriple) {
		if (newCbcStartTriple != cbcStartTriple) {
			NotificationChain msgs = null;
			if (cbcStartTriple != null)
				msgs = ((InternalEObject) cbcStartTriple).eInverseRemove(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ,
						CbCFormula.class, msgs);
			if (newCbcStartTriple != null)
				msgs = ((InternalEObject) newCbcStartTriple).eInverseAdd(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ,
						CbCFormula.class, msgs);
			msgs = basicSetCbcStartTriple(newCbcStartTriple, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__CBC_START_TRIPLE,
					newCbcStartTriple, newCbcStartTriple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelClass getParentClass() {
		if (eContainerFeatureID() != CbcclassPackage.METHOD__PARENT_CLASS)
			return null;
		return (ModelClass) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentClass(ModelClass newParentClass, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newParentClass, CbcclassPackage.METHOD__PARENT_CLASS, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentClass(ModelClass newParentClass) {
		if (newParentClass != eInternalContainer()
				|| (eContainerFeatureID() != CbcclassPackage.METHOD__PARENT_CLASS && newParentClass != null)) {
			if (EcoreUtil.isAncestor(this, newParentClass))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentClass != null)
				msgs = ((InternalEObject) newParentClass).eInverseAdd(this, CbcclassPackage.MODEL_CLASS__METHODS,
						ModelClass.class, msgs);
			msgs = basicSetParentClass(newParentClass, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CbcclassPackage.METHOD__PARENT_CLASS, newParentClass,
					newParentClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CbcclassPackage.METHOD__CBC_START_TRIPLE:
			if (cbcStartTriple != null)
				msgs = ((InternalEObject) cbcStartTriple).eInverseRemove(this, CbcmodelPackage.CB_CFORMULA__METHOD_OBJ,
						CbCFormula.class, msgs);
			return basicSetCbcStartTriple((CbCFormula) otherEnd, msgs);
		case CbcclassPackage.METHOD__PARENT_CLASS:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetParentClass((ModelClass) otherEnd, msgs);
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
		case CbcclassPackage.METHOD__CBC_START_TRIPLE:
			return basicSetCbcStartTriple(null, msgs);
		case CbcclassPackage.METHOD__PARENT_CLASS:
			return basicSetParentClass(null, msgs);
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
		case CbcclassPackage.METHOD__PARENT_CLASS:
			return eInternalContainer().eInverseRemove(this, CbcclassPackage.MODEL_CLASS__METHODS, ModelClass.class,
					msgs);
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
		case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
			return getCbcDiagramURI();
		case CbcclassPackage.METHOD__SIGNATURE:
			return getSignature();
		case CbcclassPackage.METHOD__ASSIGNABLE:
			return getAssignable();
		case CbcclassPackage.METHOD__CBC_START_TRIPLE:
			if (resolve)
				return getCbcStartTriple();
			return basicGetCbcStartTriple();
		case CbcclassPackage.METHOD__PARENT_CLASS:
			return getParentClass();
		case CbcclassPackage.METHOD__PRECONDITION:
			if (resolve)
				return getPrecondition();
			return basicGetPrecondition();
		case CbcclassPackage.METHOD__POSTCONDITION:
			if (resolve)
				return getPostcondition();
			return basicGetPostcondition();
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
		case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
			setCbcDiagramURI((String) newValue);
			return;
		case CbcclassPackage.METHOD__SIGNATURE:
			setSignature((String) newValue);
			return;
		case CbcclassPackage.METHOD__ASSIGNABLE:
			setAssignable((String) newValue);
			return;
		case CbcclassPackage.METHOD__CBC_START_TRIPLE:
			setCbcStartTriple((CbCFormula) newValue);
			return;
		case CbcclassPackage.METHOD__PARENT_CLASS:
			setParentClass((ModelClass) newValue);
			return;
		case CbcclassPackage.METHOD__PRECONDITION:
			setPrecondition((Condition) newValue);
			return;
		case CbcclassPackage.METHOD__POSTCONDITION:
			setPostcondition((Condition) newValue);
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
		case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
			setCbcDiagramURI(CBC_DIAGRAM_URI_EDEFAULT);
			return;
		case CbcclassPackage.METHOD__SIGNATURE:
			setSignature(SIGNATURE_EDEFAULT);
			return;
		case CbcclassPackage.METHOD__ASSIGNABLE:
			setAssignable(ASSIGNABLE_EDEFAULT);
			return;
		case CbcclassPackage.METHOD__CBC_START_TRIPLE:
			setCbcStartTriple((CbCFormula) null);
			return;
		case CbcclassPackage.METHOD__PARENT_CLASS:
			setParentClass((ModelClass) null);
			return;
		case CbcclassPackage.METHOD__PRECONDITION:
			setPrecondition((Condition) null);
			return;
		case CbcclassPackage.METHOD__POSTCONDITION:
			setPostcondition((Condition) null);
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
		case CbcclassPackage.METHOD__CBC_DIAGRAM_URI:
			return CBC_DIAGRAM_URI_EDEFAULT == null ? cbcDiagramURI != null
					: !CBC_DIAGRAM_URI_EDEFAULT.equals(cbcDiagramURI);
		case CbcclassPackage.METHOD__SIGNATURE:
			return SIGNATURE_EDEFAULT == null ? signature != null : !SIGNATURE_EDEFAULT.equals(signature);
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (cbcDiagramURI: ");
		result.append(cbcDiagramURI);
		result.append(", signature: ");
		result.append(signature);
		result.append(", assignable: ");
		result.append(assignable);
		result.append(')');
		return result.toString();
	}

} //MethodImpl
