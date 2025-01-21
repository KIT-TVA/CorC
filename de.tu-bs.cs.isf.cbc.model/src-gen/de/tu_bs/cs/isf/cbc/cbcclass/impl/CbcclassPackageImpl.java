/**
 */
package de.tu_bs.cs.isf.cbc.cbcclass.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;

import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CbcclassPackageImpl extends EPackageImpl implements CbcclassPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fieldEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum visibilityEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.tu_bs.cs.isf.cbc.cbcclass.CbcclassPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CbcclassPackageImpl() {
		super(eNS_URI, CbcclassFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link CbcclassPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CbcclassPackage init() {
		if (isInited) return (CbcclassPackage)EPackage.Registry.INSTANCE.getEPackage(CbcclassPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCbcclassPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CbcclassPackageImpl theCbcclassPackage = registeredCbcclassPackage instanceof CbcclassPackageImpl ? (CbcclassPackageImpl)registeredCbcclassPackage : new CbcclassPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CbcmodelPackage.eNS_URI);
		CbcmodelPackageImpl theCbcmodelPackage = (CbcmodelPackageImpl)(registeredPackage instanceof CbcmodelPackageImpl ? registeredPackage : CbcmodelPackage.eINSTANCE);

		// Create package meta-data objects
		theCbcclassPackage.createPackageContents();
		theCbcmodelPackage.createPackageContents();

		// Initialize created meta-data
		theCbcclassPackage.initializePackageContents();
		theCbcmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCbcclassPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CbcclassPackage.eNS_URI, theCbcclassPackage);
		return theCbcclassPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getModelClass() {
		return modelClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelClass_Name() {
		return (EAttribute)modelClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelClass_JavaClassURI() {
		return (EAttribute)modelClassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelClass_Fields() {
		return (EReference)modelClassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelClass_Methods() {
		return (EReference)modelClassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelClass_ClassInvariants() {
		return (EReference)modelClassEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelClass_Package() {
		return (EAttribute)modelClassEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getModelClass_Feature() {
		return (EAttribute)modelClassEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getModelClass_InheritsFrom() {
		return (EReference)modelClassEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getField() {
		return fieldEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_Visibility() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_Type() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_Name() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_IsStatic() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_IsFinal() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getField_DisplayedName() {
		return (EAttribute)fieldEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMethod() {
		return methodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_CbcDiagramURI() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_Name() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_Assignable() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMethod_CbcStartTriple() {
		return (EReference)methodEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMethod_ParentClass() {
		return (EReference)methodEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMethod_Precondition() {
		return (EReference)methodEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMethod_Postcondition() {
		return (EReference)methodEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_ReturnType() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMethod_Parameters() {
		return (EReference)methodEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_Visibility() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_Signature() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethod_IsStatic() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Type() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Name() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVisibility() {
		return visibilityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CbcclassFactory getCbcclassFactory() {
		return (CbcclassFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		modelClassEClass = createEClass(MODEL_CLASS);
		createEAttribute(modelClassEClass, MODEL_CLASS__NAME);
		createEAttribute(modelClassEClass, MODEL_CLASS__JAVA_CLASS_URI);
		createEReference(modelClassEClass, MODEL_CLASS__FIELDS);
		createEReference(modelClassEClass, MODEL_CLASS__METHODS);
		createEReference(modelClassEClass, MODEL_CLASS__CLASS_INVARIANTS);
		createEAttribute(modelClassEClass, MODEL_CLASS__PACKAGE);
		createEAttribute(modelClassEClass, MODEL_CLASS__FEATURE);
		createEReference(modelClassEClass, MODEL_CLASS__INHERITS_FROM);

		fieldEClass = createEClass(FIELD);
		createEAttribute(fieldEClass, FIELD__VISIBILITY);
		createEAttribute(fieldEClass, FIELD__TYPE);
		createEAttribute(fieldEClass, FIELD__NAME);
		createEAttribute(fieldEClass, FIELD__IS_STATIC);
		createEAttribute(fieldEClass, FIELD__IS_FINAL);
		createEAttribute(fieldEClass, FIELD__DISPLAYED_NAME);

		methodEClass = createEClass(METHOD);
		createEAttribute(methodEClass, METHOD__CBC_DIAGRAM_URI);
		createEAttribute(methodEClass, METHOD__NAME);
		createEAttribute(methodEClass, METHOD__ASSIGNABLE);
		createEReference(methodEClass, METHOD__CBC_START_TRIPLE);
		createEReference(methodEClass, METHOD__PARENT_CLASS);
		createEReference(methodEClass, METHOD__PRECONDITION);
		createEReference(methodEClass, METHOD__POSTCONDITION);
		createEAttribute(methodEClass, METHOD__RETURN_TYPE);
		createEReference(methodEClass, METHOD__PARAMETERS);
		createEAttribute(methodEClass, METHOD__VISIBILITY);
		createEAttribute(methodEClass, METHOD__SIGNATURE);
		createEAttribute(methodEClass, METHOD__IS_STATIC);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__TYPE);
		createEAttribute(parameterEClass, PARAMETER__NAME);

		// Create enums
		visibilityEEnum = createEEnum(VISIBILITY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CbcmodelPackage theCbcmodelPackage = (CbcmodelPackage)EPackage.Registry.INSTANCE.getEPackage(CbcmodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(modelClassEClass, ModelClass.class, "ModelClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModelClass_Name(), ecorePackage.getEString(), "name", null, 0, 1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelClass_JavaClassURI(), ecorePackage.getEString(), "javaClassURI", null, 0, 1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelClass_Fields(), this.getField(), null, "fields", null, 0, -1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelClass_Methods(), this.getMethod(), this.getMethod_ParentClass(), "methods", null, 0, -1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelClass_ClassInvariants(), theCbcmodelPackage.getCondition(), null, "classInvariants", null, 0, -1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelClass_Package(), ecorePackage.getEString(), "package", null, 0, 1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelClass_Feature(), ecorePackage.getEString(), "feature", "default", 0, 1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelClass_InheritsFrom(), this.getModelClass(), null, "inheritsFrom", null, 0, 1, ModelClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fieldEClass, Field.class, "Field", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getField_Visibility(), this.getVisibility(), "visibility", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_Type(), ecorePackage.getEString(), "type", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_Name(), ecorePackage.getEString(), "name", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_IsFinal(), ecorePackage.getEBoolean(), "isFinal", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getField_DisplayedName(), ecorePackage.getEString(), "displayedName", null, 0, 1, Field.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(methodEClass, Method.class, "Method", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMethod_CbcDiagramURI(), ecorePackage.getEString(), "cbcDiagramURI", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_Name(), ecorePackage.getEString(), "name", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_Assignable(), ecorePackage.getEString(), "assignable", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMethod_CbcStartTriple(), theCbcmodelPackage.getCbCFormula(), theCbcmodelPackage.getCbCFormula_MethodObj(), "cbcStartTriple", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMethod_ParentClass(), this.getModelClass(), this.getModelClass_Methods(), "parentClass", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMethod_Precondition(), theCbcmodelPackage.getCondition(), null, "precondition", null, 0, 1, Method.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMethod_Postcondition(), theCbcmodelPackage.getCondition(), null, "postcondition", null, 0, 1, Method.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_ReturnType(), ecorePackage.getEString(), "returnType", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMethod_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_Visibility(), this.getVisibility(), "visibility", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_Signature(), ecorePackage.getEString(), "signature", null, 0, 1, Method.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 0, 1, Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Type(), ecorePackage.getEString(), "type", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", "", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(visibilityEEnum, Visibility.class, "Visibility");
		addEEnumLiteral(visibilityEEnum, Visibility.PUBLIC);
		addEEnumLiteral(visibilityEEnum, Visibility.PRIVATE);
		addEEnumLiteral(visibilityEEnum, Visibility.PROTECTED);
		addEEnumLiteral(visibilityEEnum, Visibility.PACKAGE);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/GenModel
		createGenModelAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/GenModel</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createGenModelAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/GenModel";
		addAnnotation
		  (getField_DisplayedName(),
		   source,
		   new String[] {
			   "get", "String staticString = isStatic? \"static \" : \"\"; String finalString = isFinal ? \"final \" : \"\"; return visibility + \" \" + staticString + finalString + type + \" \" + name;"
		   });
		addAnnotation
		  (getMethod_Precondition(),
		   source,
		   new String[] {
			   "get", "return cbcStartTriple.getStatement().getPreCondition();"
		   });
		addAnnotation
		  (getMethod_Postcondition(),
		   source,
		   new String[] {
			   "get", "return cbcStartTriple.getStatement().getPostCondition();"
		   });
		addAnnotation
		  (getMethod_Signature(),
		   source,
		   new String[] {
			   "get", "String staticString = isStatic ? \"static \" : \"\";\nString params = \"\";\nif (getParameters().size() > 0) {\n    for (Parameter param: parameters) {\n        if (!param.getName().equals(\"ret\")) {\n            params += param.getType() + \" \" + param.getName() + \", \";\n        }\n    }\n    if (!params.isEmpty()) {\n        params = params.substring(0, params.length() - 2);\n    }\n}\nreturn visibility.toString().toLowerCase() + \" \" + staticString + returnType + \" \" + name + \"(\" + params + \")\";"
		   });
	}

} //CbcclassPackageImpl
