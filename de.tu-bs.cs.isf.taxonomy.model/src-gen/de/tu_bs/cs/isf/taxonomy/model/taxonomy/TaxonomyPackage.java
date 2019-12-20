/**
 */
package de.tu_bs.cs.isf.taxonomy.model.taxonomy;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory
 * @model kind="package"
 * @generated
 */
public interface TaxonomyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "taxonomy";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/taxonomy";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "taxonomy";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TaxonomyPackage eINSTANCE = de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyImpl <em>Taxonomy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getTaxonomy()
	 * @generated
	 */
	int TAXONOMY = 0;

	/**
	 * The feature id for the '<em><b>Algorithms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXONOMY__ALGORITHMS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXONOMY__NAME = 1;

	/**
	 * The number of structural features of the '<em>Taxonomy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXONOMY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Taxonomy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAXONOMY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl <em>Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getAlgorithm()
	 * @generated
	 */
	int ALGORITHM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__ABSTRACT = 1;

	/**
	 * The feature id for the '<em><b>Data Structures</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__DATA_STRUCTURES = 2;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__METHODS = 3;

	/**
	 * The feature id for the '<em><b>Child Algorithms</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__CHILD_ALGORITHMS = 4;

	/**
	 * The feature id for the '<em><b>Parent Algorithms</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__PARENT_ALGORITHMS = 5;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__PRE_CONDITION = 6;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__POST_CONDITION = 7;

	/**
	 * The feature id for the '<em><b>Invariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__INVARIANT = 8;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__IMPORTS = 9;

	/**
	 * The number of structural features of the '<em>Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataStructureImpl <em>Data Structure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataStructureImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getDataStructure()
	 * @generated
	 */
	int DATA_STRUCTURE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STRUCTURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STRUCTURE__DATA_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STRUCTURE__INITIAL_VALUE = 2;

	/**
	 * The number of structural features of the '<em>Data Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STRUCTURE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Data Structure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_STRUCTURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.MethodImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Data Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__DATA_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Pseudo Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PSEUDO_CODE = 2;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PRE_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__POST_CONDITION = 4;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataTypeImpl <em>Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataTypeImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.GlobalConditionsImpl <em>Global Conditions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.GlobalConditionsImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getGlobalConditions()
	 * @generated
	 */
	int GLOBAL_CONDITIONS = 5;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONDITIONS__CONDITIONS = 0;

	/**
	 * The number of structural features of the '<em>Global Conditions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONDITIONS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Global Conditions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONDITIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.ConditionImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__NAME = 0;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariablesImpl <em>Java Variables</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariablesImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getJavaVariables()
	 * @generated
	 */
	int JAVA_VARIABLES = 7;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLES__VARIABLES = 0;

	/**
	 * The number of structural features of the '<em>Java Variables</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Java Variables</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariableImpl <em>Java Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariableImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getJavaVariable()
	 * @generated
	 */
	int JAVA_VARIABLE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Java Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Java Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenamingImpl <em>Renaming</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenamingImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getRenaming()
	 * @generated
	 */
	int RENAMING = 9;

	/**
	 * The feature id for the '<em><b>Renames</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAMING__RENAMES = 0;

	/**
	 * The number of structural features of the '<em>Renaming</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAMING_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Renaming</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAMING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenameImpl <em>Rename</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenameImpl
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getRename()
	 * @generated
	 */
	int RENAME = 10;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAME__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAME__FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>New Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAME__NEW_NAME = 2;

	/**
	 * The number of structural features of the '<em>Rename</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAME_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Rename</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAME_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy <em>Taxonomy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Taxonomy</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy
	 * @generated
	 */
	EClass getTaxonomy();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy#getAlgorithms <em>Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Algorithms</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy#getAlgorithms()
	 * @see #getTaxonomy()
	 * @generated
	 */
	EReference getTaxonomy_Algorithms();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy#getName()
	 * @see #getTaxonomy()
	 * @generated
	 */
	EAttribute getTaxonomy_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Algorithm</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm
	 * @generated
	 */
	EClass getAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getName()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#isAbstract()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_Abstract();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getDataStructures <em>Data Structures</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Structures</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getDataStructures()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EReference getAlgorithm_DataStructures();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getMethods()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EReference getAlgorithm_Methods();

	/**
	 * Returns the meta object for the reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getChildAlgorithms <em>Child Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Algorithms</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getChildAlgorithms()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EReference getAlgorithm_ChildAlgorithms();

	/**
	 * Returns the meta object for the reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getParentAlgorithms <em>Parent Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parent Algorithms</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getParentAlgorithms()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EReference getAlgorithm_ParentAlgorithms();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Condition</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPreCondition()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_PreCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Post Condition</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getPostCondition()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_PostCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getInvariant <em>Invariant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Invariant</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getInvariant()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_Invariant();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Imports</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm#getImports()
	 * @see #getAlgorithm()
	 * @generated
	 */
	EAttribute getAlgorithm_Imports();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure <em>Data Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Structure</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure
	 * @generated
	 */
	EClass getDataStructure();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getName()
	 * @see #getDataStructure()
	 * @generated
	 */
	EAttribute getDataStructure_Name();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Data Type</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getDataType()
	 * @see #getDataStructure()
	 * @generated
	 */
	EReference getDataStructure_DataType();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure#getInitialValue()
	 * @see #getDataStructure()
	 * @generated
	 */
	EAttribute getDataStructure_InitialValue();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getName()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getDataTypes <em>Data Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Types</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getDataTypes()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_DataTypes();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPseudoCode <em>Pseudo Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pseudo Code</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPseudoCode()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_PseudoCode();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Condition</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPreCondition()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_PreCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Post Condition</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method#getPostCondition()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_PostCondition();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Type</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType#getName()
	 * @see #getDataType()
	 * @generated
	 */
	EAttribute getDataType_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions <em>Global Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Conditions</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions
	 * @generated
	 */
	EClass getGlobalConditions();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions#getConditions()
	 * @see #getGlobalConditions()
	 * @generated
	 */
	EReference getGlobalConditions_Conditions();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition#getName()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables <em>Java Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Variables</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables
	 * @generated
	 */
	EClass getJavaVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables#getVariables()
	 * @see #getJavaVariables()
	 * @generated
	 */
	EReference getJavaVariables_Variables();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable <em>Java Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Variable</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable
	 * @generated
	 */
	EClass getJavaVariable();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable#getName()
	 * @see #getJavaVariable()
	 * @generated
	 */
	EAttribute getJavaVariable_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming <em>Renaming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Renaming</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming
	 * @generated
	 */
	EClass getRenaming();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming#getRenames <em>Renames</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Renames</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming#getRenames()
	 * @see #getRenaming()
	 * @generated
	 */
	EReference getRenaming_Renames();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename <em>Rename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rename</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename
	 * @generated
	 */
	EClass getRename();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getType()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getFunction()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_Function();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getNewName <em>New Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Name</em>'.
	 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename#getNewName()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_NewName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TaxonomyFactory getTaxonomyFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyImpl <em>Taxonomy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getTaxonomy()
		 * @generated
		 */
		EClass TAXONOMY = eINSTANCE.getTaxonomy();

		/**
		 * The meta object literal for the '<em><b>Algorithms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAXONOMY__ALGORITHMS = eINSTANCE.getTaxonomy_Algorithms();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAXONOMY__NAME = eINSTANCE.getTaxonomy_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl <em>Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.AlgorithmImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getAlgorithm()
		 * @generated
		 */
		EClass ALGORITHM = eINSTANCE.getAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__NAME = eINSTANCE.getAlgorithm_Name();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__ABSTRACT = eINSTANCE.getAlgorithm_Abstract();

		/**
		 * The meta object literal for the '<em><b>Data Structures</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGORITHM__DATA_STRUCTURES = eINSTANCE.getAlgorithm_DataStructures();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGORITHM__METHODS = eINSTANCE.getAlgorithm_Methods();

		/**
		 * The meta object literal for the '<em><b>Child Algorithms</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGORITHM__CHILD_ALGORITHMS = eINSTANCE.getAlgorithm_ChildAlgorithms();

		/**
		 * The meta object literal for the '<em><b>Parent Algorithms</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGORITHM__PARENT_ALGORITHMS = eINSTANCE.getAlgorithm_ParentAlgorithms();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__PRE_CONDITION = eINSTANCE.getAlgorithm_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__POST_CONDITION = eINSTANCE.getAlgorithm_PostCondition();

		/**
		 * The meta object literal for the '<em><b>Invariant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__INVARIANT = eINSTANCE.getAlgorithm_Invariant();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGORITHM__IMPORTS = eINSTANCE.getAlgorithm_Imports();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataStructureImpl <em>Data Structure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataStructureImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getDataStructure()
		 * @generated
		 */
		EClass DATA_STRUCTURE = eINSTANCE.getDataStructure();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_STRUCTURE__NAME = eINSTANCE.getDataStructure_Name();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_STRUCTURE__DATA_TYPE = eINSTANCE.getDataStructure_DataType();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_STRUCTURE__INITIAL_VALUE = eINSTANCE.getDataStructure_InitialValue();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.MethodImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__NAME = eINSTANCE.getMethod_Name();

		/**
		 * The meta object literal for the '<em><b>Data Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__DATA_TYPES = eINSTANCE.getMethod_DataTypes();

		/**
		 * The meta object literal for the '<em><b>Pseudo Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__PSEUDO_CODE = eINSTANCE.getMethod_PseudoCode();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__PRE_CONDITION = eINSTANCE.getMethod_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__POST_CONDITION = eINSTANCE.getMethod_PostCondition();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataTypeImpl <em>Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.DataTypeImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_TYPE__NAME = eINSTANCE.getDataType_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.GlobalConditionsImpl <em>Global Conditions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.GlobalConditionsImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getGlobalConditions()
		 * @generated
		 */
		EClass GLOBAL_CONDITIONS = eINSTANCE.getGlobalConditions();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONDITIONS__CONDITIONS = eINSTANCE.getGlobalConditions_Conditions();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.ConditionImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__NAME = eINSTANCE.getCondition_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariablesImpl <em>Java Variables</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariablesImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getJavaVariables()
		 * @generated
		 */
		EClass JAVA_VARIABLES = eINSTANCE.getJavaVariables();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_VARIABLES__VARIABLES = eINSTANCE.getJavaVariables_Variables();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariableImpl <em>Java Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.JavaVariableImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getJavaVariable()
		 * @generated
		 */
		EClass JAVA_VARIABLE = eINSTANCE.getJavaVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_VARIABLE__NAME = eINSTANCE.getJavaVariable_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenamingImpl <em>Renaming</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenamingImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getRenaming()
		 * @generated
		 */
		EClass RENAMING = eINSTANCE.getRenaming();

		/**
		 * The meta object literal for the '<em><b>Renames</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RENAMING__RENAMES = eINSTANCE.getRenaming_Renames();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenameImpl <em>Rename</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.RenameImpl
		 * @see de.tu_bs.cs.isf.taxonomy.model.taxonomy.impl.TaxonomyPackageImpl#getRename()
		 * @generated
		 */
		EClass RENAME = eINSTANCE.getRename();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENAME__TYPE = eINSTANCE.getRename_Type();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENAME__FUNCTION = eINSTANCE.getRename_Function();

		/**
		 * The meta object literal for the '<em><b>New Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RENAME__NEW_NAME = eINSTANCE.getRename_NewName();

	}

} //TaxonomyPackage
