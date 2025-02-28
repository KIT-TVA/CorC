/**
 */
package de.tu_bs.cs.isf.cbc.statistics.impl;

import de.tu_bs.cs.isf.cbc.statistics.CorcKeyMapping;
import de.tu_bs.cs.isf.cbc.statistics.statisticsPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Corc Key Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getKeyFilePath <em>Key File Path</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getKeyProofProblemHashValue <em>Key Proof Problem Hash Value</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getCorcElementFormula <em>Corc Element Formula</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getCorcElementStatement <em>Corc Element Statement</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getCorcElementId <em>Corc Element Id</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getCorcDiagramName <em>Corc Diagram Name</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.statistics.impl.CorcKeyMappingImpl#getCorcDiagramPath <em>Corc Diagram Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CorcKeyMappingImpl extends MinimalEObjectImpl.Container implements CorcKeyMapping {
	/**
	 * The default value of the '{@link #getKeyFilePath() <em>Key File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeyFilePath() <em>Key File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyFilePath()
	 * @generated
	 * @ordered
	 */
	protected String keyFilePath = KEY_FILE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeyProofProblemHashValue() <em>Key Proof Problem Hash Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyProofProblemHashValue()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_PROOF_PROBLEM_HASH_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeyProofProblemHashValue() <em>Key Proof Problem Hash Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeyProofProblemHashValue()
	 * @generated
	 * @ordered
	 */
	protected String keyProofProblemHashValue = KEY_PROOF_PROBLEM_HASH_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCorcElementFormula() <em>Corc Element Formula</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcElementFormula()
	 * @generated
	 * @ordered
	 */
	protected EObject corcElementFormula;

	/**
	 * The cached value of the '{@link #getCorcElementStatement() <em>Corc Element Statement</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcElementStatement()
	 * @generated
	 * @ordered
	 */
	protected EObject corcElementStatement;

	/**
	 * The default value of the '{@link #getCorcElementId() <em>Corc Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcElementId()
	 * @generated
	 * @ordered
	 */
	protected static final String CORC_ELEMENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorcElementId() <em>Corc Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcElementId()
	 * @generated
	 * @ordered
	 */
	protected String corcElementId = CORC_ELEMENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getCorcDiagramName() <em>Corc Diagram Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcDiagramName()
	 * @generated
	 * @ordered
	 */
	protected static final String CORC_DIAGRAM_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorcDiagramName() <em>Corc Diagram Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcDiagramName()
	 * @generated
	 * @ordered
	 */
	protected String corcDiagramName = CORC_DIAGRAM_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCorcDiagramPath() <em>Corc Diagram Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcDiagramPath()
	 * @generated
	 * @ordered
	 */
	protected static final String CORC_DIAGRAM_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCorcDiagramPath() <em>Corc Diagram Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorcDiagramPath()
	 * @generated
	 * @ordered
	 */
	protected String corcDiagramPath = CORC_DIAGRAM_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorcKeyMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return statisticsPackage.Literals.CORC_KEY_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKeyFilePath() {
		return keyFilePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyFilePath(String newKeyFilePath) {
		String oldKeyFilePath = keyFilePath;
		keyFilePath = newKeyFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__KEY_FILE_PATH, oldKeyFilePath, keyFilePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKeyProofProblemHashValue() {
		return keyProofProblemHashValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeyProofProblemHashValue(String newKeyProofProblemHashValue) {
		String oldKeyProofProblemHashValue = keyProofProblemHashValue;
		keyProofProblemHashValue = newKeyProofProblemHashValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE, oldKeyProofProblemHashValue, keyProofProblemHashValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getCorcElementFormula() {
		if (corcElementFormula != null && corcElementFormula.eIsProxy()) {
			InternalEObject oldCorcElementFormula = (InternalEObject)corcElementFormula;
			corcElementFormula = eResolveProxy(oldCorcElementFormula);
			if (corcElementFormula != oldCorcElementFormula) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA, oldCorcElementFormula, corcElementFormula));
			}
		}
		return corcElementFormula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetCorcElementFormula() {
		return corcElementFormula;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorcElementFormula(EObject newCorcElementFormula) {
		EObject oldCorcElementFormula = corcElementFormula;
		corcElementFormula = newCorcElementFormula;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA, oldCorcElementFormula, corcElementFormula));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getCorcElementStatement() {
		if (corcElementStatement != null && corcElementStatement.eIsProxy()) {
			InternalEObject oldCorcElementStatement = (InternalEObject)corcElementStatement;
			corcElementStatement = eResolveProxy(oldCorcElementStatement);
			if (corcElementStatement != oldCorcElementStatement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT, oldCorcElementStatement, corcElementStatement));
			}
		}
		return corcElementStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetCorcElementStatement() {
		return corcElementStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorcElementStatement(EObject newCorcElementStatement) {
		EObject oldCorcElementStatement = corcElementStatement;
		corcElementStatement = newCorcElementStatement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT, oldCorcElementStatement, corcElementStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCorcDiagramName() {
		return corcDiagramName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorcDiagramName(String newCorcDiagramName) {
		String oldCorcDiagramName = corcDiagramName;
		corcDiagramName = newCorcDiagramName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_NAME, oldCorcDiagramName, corcDiagramName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCorcDiagramPath() {
		return corcDiagramPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorcDiagramPath(String newCorcDiagramPath) {
		String oldCorcDiagramPath = corcDiagramPath;
		corcDiagramPath = newCorcDiagramPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_PATH, oldCorcDiagramPath, corcDiagramPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCorcElementId() {
		return corcElementId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorcElementId(String newCorcElementId) {
		String oldCorcElementId = corcElementId;
		corcElementId = newCorcElementId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_ID, oldCorcElementId, corcElementId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case statisticsPackage.CORC_KEY_MAPPING__KEY_FILE_PATH:
				return getKeyFilePath();
			case statisticsPackage.CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE:
				return getKeyProofProblemHashValue();
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA:
				if (resolve) return getCorcElementFormula();
				return basicGetCorcElementFormula();
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT:
				if (resolve) return getCorcElementStatement();
				return basicGetCorcElementStatement();
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_ID:
				return getCorcElementId();
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_NAME:
				return getCorcDiagramName();
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_PATH:
				return getCorcDiagramPath();
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
			case statisticsPackage.CORC_KEY_MAPPING__KEY_FILE_PATH:
				setKeyFilePath((String)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE:
				setKeyProofProblemHashValue((String)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA:
				setCorcElementFormula((EObject)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT:
				setCorcElementStatement((EObject)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_ID:
				setCorcElementId((String)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_NAME:
				setCorcDiagramName((String)newValue);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_PATH:
				setCorcDiagramPath((String)newValue);
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
			case statisticsPackage.CORC_KEY_MAPPING__KEY_FILE_PATH:
				setKeyFilePath(KEY_FILE_PATH_EDEFAULT);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE:
				setKeyProofProblemHashValue(KEY_PROOF_PROBLEM_HASH_VALUE_EDEFAULT);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA:
				setCorcElementFormula((EObject)null);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT:
				setCorcElementStatement((EObject)null);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_ID:
				setCorcElementId(CORC_ELEMENT_ID_EDEFAULT);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_NAME:
				setCorcDiagramName(CORC_DIAGRAM_NAME_EDEFAULT);
				return;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_PATH:
				setCorcDiagramPath(CORC_DIAGRAM_PATH_EDEFAULT);
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
			case statisticsPackage.CORC_KEY_MAPPING__KEY_FILE_PATH:
				return KEY_FILE_PATH_EDEFAULT == null ? keyFilePath != null : !KEY_FILE_PATH_EDEFAULT.equals(keyFilePath);
			case statisticsPackage.CORC_KEY_MAPPING__KEY_PROOF_PROBLEM_HASH_VALUE:
				return KEY_PROOF_PROBLEM_HASH_VALUE_EDEFAULT == null ? keyProofProblemHashValue != null : !KEY_PROOF_PROBLEM_HASH_VALUE_EDEFAULT.equals(keyProofProblemHashValue);
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_FORMULA:
				return corcElementFormula != null;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_STATEMENT:
				return corcElementStatement != null;
			case statisticsPackage.CORC_KEY_MAPPING__CORC_ELEMENT_ID:
				return CORC_ELEMENT_ID_EDEFAULT == null ? corcElementId != null : !CORC_ELEMENT_ID_EDEFAULT.equals(corcElementId);
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_NAME:
				return CORC_DIAGRAM_NAME_EDEFAULT == null ? corcDiagramName != null : !CORC_DIAGRAM_NAME_EDEFAULT.equals(corcDiagramName);
			case statisticsPackage.CORC_KEY_MAPPING__CORC_DIAGRAM_PATH:
				return CORC_DIAGRAM_PATH_EDEFAULT == null ? corcDiagramPath != null : !CORC_DIAGRAM_PATH_EDEFAULT.equals(corcDiagramPath);
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
		result.append(" (keyFilePath: ");
		result.append(keyFilePath);
		result.append(", keyProofProblemHashValue: ");
		result.append(keyProofProblemHashValue);
		result.append(", corcElementId: ");
		result.append(corcElementId);
		result.append(", corcDiagramName: ");
		result.append(corcDiagramName);
		result.append(", corcDiagramPath: ");
		result.append(corcDiagramPath);
		result.append(')');
		return result.toString();
	}

} //CorcKeyMappingImpl
