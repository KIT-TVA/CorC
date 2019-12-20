/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Algorithm</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getDataStructures <em>Data Structures</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getMethods <em>Methods</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getChildAlgorithms <em>Child Algorithms</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getParentAlgorithms <em>Parent Algorithms</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getPreCondition <em>Pre Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getPostCondition <em>Post Condition</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getInvariant <em>Invariant</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl#getImports <em>Imports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AlgorithmImpl extends MinimalEObjectImpl.Container implements Algorithm {
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
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDataStructures() <em>Data Structures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataStructures()
	 * @generated
	 * @ordered
	 */
	protected EList<DataStructure> dataStructures;

	/**
	 * The cached value of the '{@link #getMethods() <em>Methods</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethods()
	 * @generated
	 * @ordered
	 */
	protected EList<Method> methods;

	/**
	 * The cached value of the '{@link #getChildAlgorithms() <em>Child Algorithms</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildAlgorithms()
	 * @generated
	 * @ordered
	 */
	protected EList<Algorithm> childAlgorithms;

	/**
	 * The cached value of the '{@link #getParentAlgorithms() <em>Parent Algorithms</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentAlgorithms()
	 * @generated
	 * @ordered
	 */
	protected EList<Algorithm> parentAlgorithms;

	/**
	 * The default value of the '{@link #getPreCondition() <em>Pre Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String PRE_CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreCondition() <em>Pre Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreCondition()
	 * @generated
	 * @ordered
	 */
	protected String preCondition = PRE_CONDITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getPostCondition() <em>Post Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String POST_CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPostCondition() <em>Post Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostCondition()
	 * @generated
	 * @ordered
	 */
	protected String postCondition = POST_CONDITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getInvariant() <em>Invariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariant()
	 * @generated
	 * @ordered
	 */
	protected static final String INVARIANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInvariant() <em>Invariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariant()
	 * @generated
	 * @ordered
	 */
	protected String invariant = INVARIANT_EDEFAULT;

	/**
	 * The default value of the '{@link #getImports() <em>Imports</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPORTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected String imports = IMPORTS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlgorithmImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TaxonomyPackage.Literals.ALGORITHM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataStructure> getDataStructures() {
		if (dataStructures == null) {
			dataStructures = new EObjectContainmentEList<DataStructure>(DataStructure.class, this, TaxonomyPackage.ALGORITHM__DATA_STRUCTURES);
		}
		return dataStructures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Method> getMethods() {
		if (methods == null) {
			methods = new EObjectContainmentEList<Method>(Method.class, this, TaxonomyPackage.ALGORITHM__METHODS);
		}
		return methods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Algorithm> getChildAlgorithms() {
		if (childAlgorithms == null) {
			childAlgorithms = new EObjectWithInverseResolvingEList.ManyInverse<Algorithm>(Algorithm.class, this, TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS, TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS);
		}
		return childAlgorithms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Algorithm> getParentAlgorithms() {
		if (parentAlgorithms == null) {
			parentAlgorithms = new EObjectWithInverseResolvingEList.ManyInverse<Algorithm>(Algorithm.class, this, TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS, TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS);
		}
		return parentAlgorithms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreCondition() {
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreCondition(String newPreCondition) {
		String oldPreCondition = preCondition;
		preCondition = newPreCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__PRE_CONDITION, oldPreCondition, preCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPostCondition() {
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPostCondition(String newPostCondition) {
		String oldPostCondition = postCondition;
		postCondition = newPostCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__POST_CONDITION, oldPostCondition, postCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInvariant() {
		return invariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvariant(String newInvariant) {
		String oldInvariant = invariant;
		invariant = newInvariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__INVARIANT, oldInvariant, invariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImports() {
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImports(String newImports) {
		String oldImports = imports;
		imports = newImports;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TaxonomyPackage.ALGORITHM__IMPORTS, oldImports, imports));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildAlgorithms()).basicAdd(otherEnd, msgs);
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParentAlgorithms()).basicAdd(otherEnd, msgs);
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
			case TaxonomyPackage.ALGORITHM__DATA_STRUCTURES:
				return ((InternalEList<?>)getDataStructures()).basicRemove(otherEnd, msgs);
			case TaxonomyPackage.ALGORITHM__METHODS:
				return ((InternalEList<?>)getMethods()).basicRemove(otherEnd, msgs);
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				return ((InternalEList<?>)getChildAlgorithms()).basicRemove(otherEnd, msgs);
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				return ((InternalEList<?>)getParentAlgorithms()).basicRemove(otherEnd, msgs);
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
			case TaxonomyPackage.ALGORITHM__NAME:
				return getName();
			case TaxonomyPackage.ALGORITHM__ABSTRACT:
				return isAbstract();
			case TaxonomyPackage.ALGORITHM__DATA_STRUCTURES:
				return getDataStructures();
			case TaxonomyPackage.ALGORITHM__METHODS:
				return getMethods();
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				return getChildAlgorithms();
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				return getParentAlgorithms();
			case TaxonomyPackage.ALGORITHM__PRE_CONDITION:
				return getPreCondition();
			case TaxonomyPackage.ALGORITHM__POST_CONDITION:
				return getPostCondition();
			case TaxonomyPackage.ALGORITHM__INVARIANT:
				return getInvariant();
			case TaxonomyPackage.ALGORITHM__IMPORTS:
				return getImports();
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
			case TaxonomyPackage.ALGORITHM__NAME:
				setName((String)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__ABSTRACT:
				setAbstract((Boolean)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__DATA_STRUCTURES:
				getDataStructures().clear();
				getDataStructures().addAll((Collection<? extends DataStructure>)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__METHODS:
				getMethods().clear();
				getMethods().addAll((Collection<? extends Method>)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				getChildAlgorithms().clear();
				getChildAlgorithms().addAll((Collection<? extends Algorithm>)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				getParentAlgorithms().clear();
				getParentAlgorithms().addAll((Collection<? extends Algorithm>)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__PRE_CONDITION:
				setPreCondition((String)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__POST_CONDITION:
				setPostCondition((String)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__INVARIANT:
				setInvariant((String)newValue);
				return;
			case TaxonomyPackage.ALGORITHM__IMPORTS:
				setImports((String)newValue);
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
			case TaxonomyPackage.ALGORITHM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TaxonomyPackage.ALGORITHM__ABSTRACT:
				setAbstract(ABSTRACT_EDEFAULT);
				return;
			case TaxonomyPackage.ALGORITHM__DATA_STRUCTURES:
				getDataStructures().clear();
				return;
			case TaxonomyPackage.ALGORITHM__METHODS:
				getMethods().clear();
				return;
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				getChildAlgorithms().clear();
				return;
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				getParentAlgorithms().clear();
				return;
			case TaxonomyPackage.ALGORITHM__PRE_CONDITION:
				setPreCondition(PRE_CONDITION_EDEFAULT);
				return;
			case TaxonomyPackage.ALGORITHM__POST_CONDITION:
				setPostCondition(POST_CONDITION_EDEFAULT);
				return;
			case TaxonomyPackage.ALGORITHM__INVARIANT:
				setInvariant(INVARIANT_EDEFAULT);
				return;
			case TaxonomyPackage.ALGORITHM__IMPORTS:
				setImports(IMPORTS_EDEFAULT);
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
			case TaxonomyPackage.ALGORITHM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TaxonomyPackage.ALGORITHM__ABSTRACT:
				return abstract_ != ABSTRACT_EDEFAULT;
			case TaxonomyPackage.ALGORITHM__DATA_STRUCTURES:
				return dataStructures != null && !dataStructures.isEmpty();
			case TaxonomyPackage.ALGORITHM__METHODS:
				return methods != null && !methods.isEmpty();
			case TaxonomyPackage.ALGORITHM__CHILD_ALGORITHMS:
				return childAlgorithms != null && !childAlgorithms.isEmpty();
			case TaxonomyPackage.ALGORITHM__PARENT_ALGORITHMS:
				return parentAlgorithms != null && !parentAlgorithms.isEmpty();
			case TaxonomyPackage.ALGORITHM__PRE_CONDITION:
				return PRE_CONDITION_EDEFAULT == null ? preCondition != null : !PRE_CONDITION_EDEFAULT.equals(preCondition);
			case TaxonomyPackage.ALGORITHM__POST_CONDITION:
				return POST_CONDITION_EDEFAULT == null ? postCondition != null : !POST_CONDITION_EDEFAULT.equals(postCondition);
			case TaxonomyPackage.ALGORITHM__INVARIANT:
				return INVARIANT_EDEFAULT == null ? invariant != null : !INVARIANT_EDEFAULT.equals(invariant);
			case TaxonomyPackage.ALGORITHM__IMPORTS:
				return IMPORTS_EDEFAULT == null ? imports != null : !IMPORTS_EDEFAULT.equals(imports);
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
		result.append(", abstract: ");
		result.append(abstract_);
		result.append(", preCondition: ");
		result.append(preCondition);
		result.append(", postCondition: ");
		result.append(postCondition);
		result.append(", invariant: ");
		result.append(invariant);
		result.append(", imports: ");
		result.append(imports);
		result.append(')');
		return result.toString();
	}

} //AlgorithmImpl
