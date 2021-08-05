/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory
 * @model kind="package"
 * @generated
 */
public interface CbcclassPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cbcclass";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/cbcclass";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cbcclass";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CbcclassPackage eINSTANCE = de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl <em>Model Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getModelClass()
	 * @generated
	 */
	int MODEL_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__NAME = 0;

	/**
	 * The feature id for the '<em><b>Java Class URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__JAVA_CLASS_URI = 1;

	/**
	 * The feature id for the '<em><b>Fields</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__FIELDS = 2;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__METHODS = 3;

	/**
	 * The feature id for the '<em><b>Class Invariants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__CLASS_INVARIANTS = 4;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS__PACKAGE = 5;

	/**
	 * The number of structural features of the '<em>Model Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Model Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_CLASS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.FieldImpl <em>Field</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.FieldImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getField()
	 * @generated
	 */
	int FIELD = 1;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__VISIBILITY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__NAME = 2;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__IS_STATIC = 3;

	/**
	 * The feature id for the '<em><b>Is Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__IS_FINAL = 4;

	/**
	 * The feature id for the '<em><b>Displayed Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD__DISPLAYED_NAME = 5;

	/**
	 * The number of structural features of the '<em>Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Field</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 2;

	/**
	 * The feature id for the '<em><b>Cbc Diagram URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__CBC_DIAGRAM_URI = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = 1;

	/**
	 * The feature id for the '<em><b>Assignable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__ASSIGNABLE = 2;

	/**
	 * The feature id for the '<em><b>Cbc Start Triple</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__CBC_START_TRIPLE = 3;

	/**
	 * The feature id for the '<em><b>Parent Class</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PARENT_CLASS = 4;

	/**
	 * The feature id for the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PRECONDITION = 5;

	/**
	 * The feature id for the '<em><b>Postcondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__POSTCONDITION = 6;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__RETURN_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__PARAMETERS = 8;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__VISIBILITY = 9;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__SIGNATURE = 10;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__IS_STATIC = 11;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ParameterImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility <em>Visibility</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getVisibility()
	 * @generated
	 */
	int VISIBILITY = 4;

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass <em>Model Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Class</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass
	 * @generated
	 */
	EClass getModelClass();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getName()
	 * @see #getModelClass()
	 * @generated
	 */
	EAttribute getModelClass_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getJavaClassURI <em>Java Class URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Class URI</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getJavaClassURI()
	 * @see #getModelClass()
	 * @generated
	 */
	EAttribute getModelClass_JavaClassURI();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getFields <em>Fields</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fields</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getFields()
	 * @see #getModelClass()
	 * @generated
	 */
	EReference getModelClass_Fields();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getMethods()
	 * @see #getModelClass()
	 * @generated
	 */
	EReference getModelClass_Methods();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getClassInvariants <em>Class Invariants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Invariants</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getClassInvariants()
	 * @see #getModelClass()
	 * @generated
	 */
	EReference getModelClass_ClassInvariants();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass#getPackage()
	 * @see #getModelClass()
	 * @generated
	 */
	EAttribute getModelClass_Package();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field <em>Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field
	 * @generated
	 */
	EClass getField();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getVisibility()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Visibility();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getType()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getName()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsStatic()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_IsStatic();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsFinal <em>Is Final</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Final</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#isIsFinal()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_IsFinal();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getDisplayedName <em>Displayed Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Displayed Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field#getDisplayedName()
	 * @see #getField()
	 * @generated
	 */
	EAttribute getField_DisplayedName();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcDiagramURI <em>Cbc Diagram URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cbc Diagram URI</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcDiagramURI()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_CbcDiagramURI();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getName()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getAssignable <em>Assignable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assignable</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getAssignable()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Assignable();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcStartTriple <em>Cbc Start Triple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cbc Start Triple</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getCbcStartTriple()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_CbcStartTriple();

	/**
	 * Returns the meta object for the container reference '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass <em>Parent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Class</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParentClass()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_ParentClass();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPrecondition <em>Precondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Precondition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPrecondition()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Precondition();

	/**
	 * Returns the meta object for the reference '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPostcondition <em>Postcondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Postcondition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getPostcondition()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Postcondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Type</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getReturnType()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_ReturnType();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getParameters()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getVisibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getVisibility()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Visibility();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signature</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#getSignature()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Signature();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method#isIsStatic()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_IsStatic();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for enum '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility <em>Visibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Visibility</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility
	 * @generated
	 */
	EEnum getVisibility();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CbcclassFactory getCbcclassFactory();

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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl <em>Model Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ModelClassImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getModelClass()
		 * @generated
		 */
		EClass MODEL_CLASS = eINSTANCE.getModelClass();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_CLASS__NAME = eINSTANCE.getModelClass_Name();

		/**
		 * The meta object literal for the '<em><b>Java Class URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_CLASS__JAVA_CLASS_URI = eINSTANCE.getModelClass_JavaClassURI();

		/**
		 * The meta object literal for the '<em><b>Fields</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_CLASS__FIELDS = eINSTANCE.getModelClass_Fields();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_CLASS__METHODS = eINSTANCE.getModelClass_Methods();

		/**
		 * The meta object literal for the '<em><b>Class Invariants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_CLASS__CLASS_INVARIANTS = eINSTANCE.getModelClass_ClassInvariants();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_CLASS__PACKAGE = eINSTANCE.getModelClass_Package();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.FieldImpl <em>Field</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.FieldImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getField()
		 * @generated
		 */
		EClass FIELD = eINSTANCE.getField();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__VISIBILITY = eINSTANCE.getField_Visibility();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__TYPE = eINSTANCE.getField_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__NAME = eINSTANCE.getField_Name();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__IS_STATIC = eINSTANCE.getField_IsStatic();

		/**
		 * The meta object literal for the '<em><b>Is Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__IS_FINAL = eINSTANCE.getField_IsFinal();

		/**
		 * The meta object literal for the '<em><b>Displayed Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD__DISPLAYED_NAME = eINSTANCE.getField_DisplayedName();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '<em><b>Cbc Diagram URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__CBC_DIAGRAM_URI = eINSTANCE.getMethod_CbcDiagramURI();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__NAME = eINSTANCE.getMethod_Name();

		/**
		 * The meta object literal for the '<em><b>Assignable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__ASSIGNABLE = eINSTANCE.getMethod_Assignable();

		/**
		 * The meta object literal for the '<em><b>Cbc Start Triple</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__CBC_START_TRIPLE = eINSTANCE.getMethod_CbcStartTriple();

		/**
		 * The meta object literal for the '<em><b>Parent Class</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__PARENT_CLASS = eINSTANCE.getMethod_ParentClass();

		/**
		 * The meta object literal for the '<em><b>Precondition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__PRECONDITION = eINSTANCE.getMethod_Precondition();

		/**
		 * The meta object literal for the '<em><b>Postcondition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__POSTCONDITION = eINSTANCE.getMethod_Postcondition();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__RETURN_TYPE = eINSTANCE.getMethod_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METHOD__PARAMETERS = eINSTANCE.getMethod_Parameters();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__VISIBILITY = eINSTANCE.getMethod_Visibility();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__SIGNATURE = eINSTANCE.getMethod_Signature();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__IS_STATIC = eINSTANCE.getMethod_IsStatic();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.ParameterImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__TYPE = eINSTANCE.getParameter_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility <em>Visibility</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Visibility
		 * @see de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl#getVisibility()
		 * @generated
		 */
		EEnum VISIBILITY = eINSTANCE.getVisibility();

	}

} //CbcclassPackage
