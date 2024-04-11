/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Variables</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl#getFields <em>Fields</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl#getParams <em>Params</em>}</li>
 * </ul>
 *
 * @generated
 */
public class JavaVariablesImpl extends MinimalEObjectImpl.Container implements JavaVariables {
	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaVariable> variables;

	/**
	 * The cached value of the '{@link #getFields() <em>Fields</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFields()
	 * @generated
	 * @ordered
	 */
	protected EList<Field> fields;

	/**
	 * The cached value of the '{@link #getParams() <em>Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParams()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> params;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaVariablesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CbcmodelPackage.Literals.JAVA_VARIABLES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<JavaVariable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<JavaVariable>(JavaVariable.class, this, CbcmodelPackage.JAVA_VARIABLES__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<Field> getFields() {
		if (eDirectResource() != null) {
			if (eDirectResource().getResourceSet() != null) {
				if (eDirectResource().getResourceSet().getResources() != null) {
					EList<Resource> resources = eDirectResource().getResourceSet().getResources();
					URI uri = eDirectResource().getURI();
					uri = uri.trimSegments(1);
					uri = uri.appendSegment(uri.lastSegment() + ".cbcclass");
					for (Resource res : resources) {
						if (res.getURI().equals(uri)) {
							EList<EObject> content = res.getContents();
							for (EObject o : content) {
								if (o instanceof ModelClass) {
									return ((ModelClass) o).getFields();
								}
							}
						}
					}
				}
			}
		}	
		if (fields == null) {
			fields = new EObjectResolvingEList<Field>(Field.class, this, CbcmodelPackage.JAVA_VARIABLES__FIELDS);
		}
		return fields;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<Parameter> getParams() {
		if (eDirectResource() != null) {
			String methodName = eDirectResource().getURI().trimFileExtension().lastSegment();
			if (eDirectResource().getResourceSet() != null) {
				if (eDirectResource().getResourceSet().getResources() != null) {
					EList<Resource> resources = eDirectResource().getResourceSet().getResources();
					URI uri = eDirectResource().getURI();
					uri = uri.trimSegments(1);
					uri = uri.appendSegment(uri.lastSegment() + ".cbcclass");
					for (Resource res : resources) {
						if (res.getURI().equals(uri) || res.getURI().toString().endsWith(uri.toString().replace("platform:/resource",""))) {
							EList<EObject> content = res.getContents();
							for (EObject o : content) {
								if (o instanceof ModelClass) {
									ModelClass mc = (ModelClass) o;
									for (Method method : mc.getMethods()) {
										if (method.getName().equals(methodName)) {
											return method.getParameters();
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (params == null) {
			params = new EObjectResolvingEList<Parameter>(Parameter.class, this, CbcmodelPackage.JAVA_VARIABLES__PARAMS);
		}
		return params;	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CbcmodelPackage.JAVA_VARIABLES__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case CbcmodelPackage.JAVA_VARIABLES__PARAMS:
				return ((InternalEList<?>)getParams()).basicRemove(otherEnd, msgs);
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
			case CbcmodelPackage.JAVA_VARIABLES__VARIABLES:
				return getVariables();
			case CbcmodelPackage.JAVA_VARIABLES__FIELDS:
				return getFields();
			case CbcmodelPackage.JAVA_VARIABLES__PARAMS:
				return getParams();
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
			case CbcmodelPackage.JAVA_VARIABLES__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends JavaVariable>)newValue);
				return;
			case CbcmodelPackage.JAVA_VARIABLES__FIELDS:
				getFields().clear();
				getFields().addAll((Collection<? extends Field>)newValue);
				return;
			case CbcmodelPackage.JAVA_VARIABLES__PARAMS:
				getParams().clear();
				getParams().addAll((Collection<? extends Parameter>)newValue);
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
			case CbcmodelPackage.JAVA_VARIABLES__VARIABLES:
				getVariables().clear();
				return;
			case CbcmodelPackage.JAVA_VARIABLES__FIELDS:
				getFields().clear();
				return;
			case CbcmodelPackage.JAVA_VARIABLES__PARAMS:
				getParams().clear();
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
			case CbcmodelPackage.JAVA_VARIABLES__VARIABLES:
				return variables != null && !variables.isEmpty();
			case CbcmodelPackage.JAVA_VARIABLES__FIELDS:
				return fields != null && !fields.isEmpty();
			case CbcmodelPackage.JAVA_VARIABLES__PARAMS:
				return params != null && !params.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //JavaVariablesImpl
