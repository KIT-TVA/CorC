/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TaxonomyFactoryImpl extends EFactoryImpl implements TaxonomyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TaxonomyFactory init() {
		try {
			TaxonomyFactory theTaxonomyFactory = (TaxonomyFactory)EPackage.Registry.INSTANCE.getEFactory(TaxonomyPackage.eNS_URI);
			if (theTaxonomyFactory != null) {
				return theTaxonomyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TaxonomyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaxonomyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TaxonomyPackage.TAXONOMY: return createTaxonomy();
			case TaxonomyPackage.ALGORITHM: return createAlgorithm();
			case TaxonomyPackage.DATA_STRUCTURE: return createDataStructure();
			case TaxonomyPackage.METHOD: return createMethod();
			case TaxonomyPackage.DATA_TYPE: return createDataType();
			case TaxonomyPackage.GLOBAL_CONDITIONS: return createGlobalConditions();
			case TaxonomyPackage.CONDITION: return createCondition();
			case TaxonomyPackage.JAVA_VARIABLES: return createJavaVariables();
			case TaxonomyPackage.JAVA_VARIABLE: return createJavaVariable();
			case TaxonomyPackage.RENAMING: return createRenaming();
			case TaxonomyPackage.RENAME: return createRename();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Taxonomy createTaxonomy() {
		TaxonomyImpl taxonomy = new TaxonomyImpl();
		return taxonomy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Algorithm createAlgorithm() {
		AlgorithmImpl algorithm = new AlgorithmImpl();
		return algorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataStructure createDataStructure() {
		DataStructureImpl dataStructure = new DataStructureImpl();
		return dataStructure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Method createMethod() {
		MethodImpl method = new MethodImpl();
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataType() {
		DataTypeImpl dataType = new DataTypeImpl();
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalConditions createGlobalConditions() {
		GlobalConditionsImpl globalConditions = new GlobalConditionsImpl();
		return globalConditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition createCondition() {
		ConditionImpl condition = new ConditionImpl();
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaVariables createJavaVariables() {
		JavaVariablesImpl javaVariables = new JavaVariablesImpl();
		return javaVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaVariable createJavaVariable() {
		JavaVariableImpl javaVariable = new JavaVariableImpl();
		return javaVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Renaming createRenaming() {
		RenamingImpl renaming = new RenamingImpl();
		return renaming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rename createRename() {
		RenameImpl rename = new RenameImpl();
		return rename;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaxonomyPackage getTaxonomyPackage() {
		return (TaxonomyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TaxonomyPackage getPackage() {
		return TaxonomyPackage.eINSTANCE;
	}

} //TaxonomyFactoryImpl
