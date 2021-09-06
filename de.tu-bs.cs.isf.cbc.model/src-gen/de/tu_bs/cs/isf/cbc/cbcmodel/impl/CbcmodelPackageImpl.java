/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.impl;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.CbcclassPackageImpl;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

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
public class CbcmodelPackageImpl extends EPackageImpl implements CbcmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cbCFormulaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass skipStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass selectionStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass returnStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass smallRepetitionStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaVariablesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalConditionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass renamingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass renameEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cbCProblemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass strengthWeakStatementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodSignatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variableKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum compositionTechniqueEEnum = null;

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
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CbcmodelPackageImpl() {
		super(eNS_URI, CbcmodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CbcmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CbcmodelPackage init() {
		if (isInited) return (CbcmodelPackage)EPackage.Registry.INSTANCE.getEPackage(CbcmodelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCbcmodelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CbcmodelPackageImpl theCbcmodelPackage = registeredCbcmodelPackage instanceof CbcmodelPackageImpl ? (CbcmodelPackageImpl)registeredCbcmodelPackage : new CbcmodelPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CbcclassPackage.eNS_URI);
		CbcclassPackageImpl theCbcclassPackage = (CbcclassPackageImpl)(registeredPackage instanceof CbcclassPackageImpl ? registeredPackage : CbcclassPackage.eINSTANCE);

		// Create package meta-data objects
		theCbcmodelPackage.createPackageContents();
		theCbcclassPackage.createPackageContents();

		// Initialize created meta-data
		theCbcmodelPackage.initializePackageContents();
		theCbcclassPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCbcmodelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CbcmodelPackage.eNS_URI, theCbcmodelPackage);
		return theCbcmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCbCFormula() {
		return cbCFormulaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_Name() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCFormula_Statement() {
		return (EReference)cbCFormulaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCFormula_PreCondition() {
		return (EReference)cbCFormulaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCFormula_PostCondition() {
		return (EReference)cbCFormulaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_Proven() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_Comment() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_CompositionTechnique() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_ClassName() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCbCFormula_MethodName() {
		return (EAttribute)cbCFormulaEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCFormula_MethodObj() {
		return (EReference)cbCFormulaEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCondition() {
		return conditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCondition_Name() {
		return (EAttribute)conditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractStatement() {
		return abstractStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractStatement_Name() {
		return (EAttribute)abstractStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractStatement_Refinement() {
		return (EReference)abstractStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractStatement_Parent() {
		return (EReference)abstractStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractStatement_PostCondition() {
		return (EReference)abstractStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractStatement_PreCondition() {
		return (EReference)abstractStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractStatement_Proven() {
		return (EAttribute)abstractStatementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractStatement_Comment() {
		return (EAttribute)abstractStatementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSkipStatement() {
		return skipStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompositionStatement() {
		return compositionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositionStatement_FirstStatement() {
		return (EReference)compositionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositionStatement_SecondStatement() {
		return (EReference)compositionStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositionStatement_IntermediateCondition() {
		return (EReference)compositionStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSelectionStatement() {
		return selectionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSelectionStatement_Guards() {
		return (EReference)selectionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSelectionStatement_Commands() {
		return (EReference)selectionStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSelectionStatement_PreProve() {
		return (EAttribute)selectionStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariant() {
		return variantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariant_Name() {
		return (EAttribute)variantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReturnStatement() {
		return returnStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSmallRepetitionStatement() {
		return smallRepetitionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSmallRepetitionStatement_LoopStatement() {
		return (EReference)smallRepetitionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSmallRepetitionStatement_Variant() {
		return (EReference)smallRepetitionStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSmallRepetitionStatement_Invariant() {
		return (EReference)smallRepetitionStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSmallRepetitionStatement_Guard() {
		return (EReference)smallRepetitionStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSmallRepetitionStatement_VariantProven() {
		return (EAttribute)smallRepetitionStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSmallRepetitionStatement_PreProven() {
		return (EAttribute)smallRepetitionStatementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSmallRepetitionStatement_PostProven() {
		return (EAttribute)smallRepetitionStatementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJavaVariables() {
		return javaVariablesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJavaVariables_Variables() {
		return (EReference)javaVariablesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJavaVariables_Fields() {
		return (EReference)javaVariablesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJavaVariable() {
		return javaVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJavaVariable_Name() {
		return (EAttribute)javaVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJavaVariable_Kind() {
		return (EAttribute)javaVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getJavaVariable_DisplayedName() {
		return (EAttribute)javaVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGlobalConditions() {
		return globalConditionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGlobalConditions_Conditions() {
		return (EReference)globalConditionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRenaming() {
		return renamingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRenaming_Rename() {
		return (EReference)renamingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRename() {
		return renameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRename_Type() {
		return (EAttribute)renameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRename_Function() {
		return (EAttribute)renameEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRename_NewName() {
		return (EAttribute)renameEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCbCProblem() {
		return cbCProblemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_Globalcondition() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_Cbcformula() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_JavaVariable() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_Renaming() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_Methodsignature() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCbCProblem_MethodClass() {
		return (EReference)cbCProblemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStrengthWeakStatement() {
		return strengthWeakStatementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMethodSignature() {
		return methodSignatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethodSignature_MethodSignature() {
		return (EAttribute)methodSignatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMethodClass() {
		return methodClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMethodClass_MethodClass() {
		return (EAttribute)methodClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVariableKind() {
		return variableKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getCompositionTechnique() {
		return compositionTechniqueEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CbcmodelFactory getCbcmodelFactory() {
		return (CbcmodelFactory)getEFactoryInstance();
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
		cbCFormulaEClass = createEClass(CB_CFORMULA);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__NAME);
		createEReference(cbCFormulaEClass, CB_CFORMULA__STATEMENT);
		createEReference(cbCFormulaEClass, CB_CFORMULA__PRE_CONDITION);
		createEReference(cbCFormulaEClass, CB_CFORMULA__POST_CONDITION);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__PROVEN);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__COMMENT);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__COMPOSITION_TECHNIQUE);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__CLASS_NAME);
		createEAttribute(cbCFormulaEClass, CB_CFORMULA__METHOD_NAME);
		createEReference(cbCFormulaEClass, CB_CFORMULA__METHOD_OBJ);

		conditionEClass = createEClass(CONDITION);
		createEAttribute(conditionEClass, CONDITION__NAME);

		abstractStatementEClass = createEClass(ABSTRACT_STATEMENT);
		createEAttribute(abstractStatementEClass, ABSTRACT_STATEMENT__NAME);
		createEReference(abstractStatementEClass, ABSTRACT_STATEMENT__REFINEMENT);
		createEReference(abstractStatementEClass, ABSTRACT_STATEMENT__PARENT);
		createEReference(abstractStatementEClass, ABSTRACT_STATEMENT__POST_CONDITION);
		createEReference(abstractStatementEClass, ABSTRACT_STATEMENT__PRE_CONDITION);
		createEAttribute(abstractStatementEClass, ABSTRACT_STATEMENT__PROVEN);
		createEAttribute(abstractStatementEClass, ABSTRACT_STATEMENT__COMMENT);

		skipStatementEClass = createEClass(SKIP_STATEMENT);

		compositionStatementEClass = createEClass(COMPOSITION_STATEMENT);
		createEReference(compositionStatementEClass, COMPOSITION_STATEMENT__FIRST_STATEMENT);
		createEReference(compositionStatementEClass, COMPOSITION_STATEMENT__SECOND_STATEMENT);
		createEReference(compositionStatementEClass, COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION);

		selectionStatementEClass = createEClass(SELECTION_STATEMENT);
		createEReference(selectionStatementEClass, SELECTION_STATEMENT__GUARDS);
		createEReference(selectionStatementEClass, SELECTION_STATEMENT__COMMANDS);
		createEAttribute(selectionStatementEClass, SELECTION_STATEMENT__PRE_PROVE);

		variantEClass = createEClass(VARIANT);
		createEAttribute(variantEClass, VARIANT__NAME);

		returnStatementEClass = createEClass(RETURN_STATEMENT);

		smallRepetitionStatementEClass = createEClass(SMALL_REPETITION_STATEMENT);
		createEReference(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__LOOP_STATEMENT);
		createEReference(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__VARIANT);
		createEReference(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__INVARIANT);
		createEReference(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__GUARD);
		createEAttribute(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__VARIANT_PROVEN);
		createEAttribute(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__PRE_PROVEN);
		createEAttribute(smallRepetitionStatementEClass, SMALL_REPETITION_STATEMENT__POST_PROVEN);

		javaVariablesEClass = createEClass(JAVA_VARIABLES);
		createEReference(javaVariablesEClass, JAVA_VARIABLES__VARIABLES);
		createEReference(javaVariablesEClass, JAVA_VARIABLES__FIELDS);

		javaVariableEClass = createEClass(JAVA_VARIABLE);
		createEAttribute(javaVariableEClass, JAVA_VARIABLE__NAME);
		createEAttribute(javaVariableEClass, JAVA_VARIABLE__KIND);
		createEAttribute(javaVariableEClass, JAVA_VARIABLE__DISPLAYED_NAME);

		globalConditionsEClass = createEClass(GLOBAL_CONDITIONS);
		createEReference(globalConditionsEClass, GLOBAL_CONDITIONS__CONDITIONS);

		renamingEClass = createEClass(RENAMING);
		createEReference(renamingEClass, RENAMING__RENAME);

		renameEClass = createEClass(RENAME);
		createEAttribute(renameEClass, RENAME__TYPE);
		createEAttribute(renameEClass, RENAME__FUNCTION);
		createEAttribute(renameEClass, RENAME__NEW_NAME);

		cbCProblemEClass = createEClass(CB_CPROBLEM);
		createEReference(cbCProblemEClass, CB_CPROBLEM__GLOBALCONDITION);
		createEReference(cbCProblemEClass, CB_CPROBLEM__CBCFORMULA);
		createEReference(cbCProblemEClass, CB_CPROBLEM__JAVA_VARIABLE);
		createEReference(cbCProblemEClass, CB_CPROBLEM__RENAMING);
		createEReference(cbCProblemEClass, CB_CPROBLEM__METHODSIGNATURE);
		createEReference(cbCProblemEClass, CB_CPROBLEM__METHOD_CLASS);

		strengthWeakStatementEClass = createEClass(STRENGTH_WEAK_STATEMENT);

		methodSignatureEClass = createEClass(METHOD_SIGNATURE);
		createEAttribute(methodSignatureEClass, METHOD_SIGNATURE__METHOD_SIGNATURE);

		methodClassEClass = createEClass(METHOD_CLASS);
		createEAttribute(methodClassEClass, METHOD_CLASS__METHOD_CLASS);

		// Create enums
		variableKindEEnum = createEEnum(VARIABLE_KIND);
		compositionTechniqueEEnum = createEEnum(COMPOSITION_TECHNIQUE);
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
		CbcclassPackage theCbcclassPackage = (CbcclassPackage)EPackage.Registry.INSTANCE.getEPackage(CbcclassPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		skipStatementEClass.getESuperTypes().add(this.getAbstractStatement());
		compositionStatementEClass.getESuperTypes().add(this.getAbstractStatement());
		selectionStatementEClass.getESuperTypes().add(this.getAbstractStatement());
		returnStatementEClass.getESuperTypes().add(this.getAbstractStatement());
		smallRepetitionStatementEClass.getESuperTypes().add(this.getAbstractStatement());
		strengthWeakStatementEClass.getESuperTypes().add(this.getAbstractStatement());

		// Initialize classes, features, and operations; add parameters
		initEClass(cbCFormulaEClass, CbCFormula.class, "CbCFormula", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCbCFormula_Name(), ecorePackage.getEString(), "name", null, 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCFormula_Statement(), this.getAbstractStatement(), null, "statement", null, 1, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCFormula_PreCondition(), this.getCondition(), null, "preCondition", null, 1, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCFormula_PostCondition(), this.getCondition(), null, "postCondition", null, 1, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCbCFormula_Proven(), ecorePackage.getEBoolean(), "proven", null, 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCbCFormula_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCbCFormula_CompositionTechnique(), this.getCompositionTechnique(), "compositionTechnique", "CONTRACT_OVERRIDING", 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCbCFormula_ClassName(), ecorePackage.getEString(), "className", "", 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCbCFormula_MethodName(), ecorePackage.getEString(), "methodName", "", 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCFormula_MethodObj(), theCbcclassPackage.getMethod(), theCbcclassPackage.getMethod_CbcStartTriple(), "methodObj", null, 0, 1, CbCFormula.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionEClass, Condition.class, "Condition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCondition_Name(), ecorePackage.getEString(), "name", null, 0, 1, Condition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractStatementEClass, AbstractStatement.class, "AbstractStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractStatement_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractStatement_Refinement(), this.getAbstractStatement(), this.getAbstractStatement_Parent(), "refinement", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractStatement_Parent(), this.getAbstractStatement(), this.getAbstractStatement_Refinement(), "parent", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractStatement_PostCondition(), this.getCondition(), null, "postCondition", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAbstractStatement_PreCondition(), this.getCondition(), null, "preCondition", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractStatement_Proven(), ecorePackage.getEBoolean(), "proven", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractStatement_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, AbstractStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(skipStatementEClass, SkipStatement.class, "SkipStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(compositionStatementEClass, CompositionStatement.class, "CompositionStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionStatement_FirstStatement(), this.getAbstractStatement(), null, "firstStatement", null, 1, 1, CompositionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionStatement_SecondStatement(), this.getAbstractStatement(), null, "secondStatement", null, 1, 1, CompositionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionStatement_IntermediateCondition(), this.getCondition(), null, "intermediateCondition", null, 1, 1, CompositionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(selectionStatementEClass, SelectionStatement.class, "SelectionStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSelectionStatement_Guards(), this.getCondition(), null, "guards", null, 0, -1, SelectionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSelectionStatement_Commands(), this.getAbstractStatement(), null, "commands", null, 0, -1, SelectionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSelectionStatement_PreProve(), ecorePackage.getEBoolean(), "preProve", null, 0, 1, SelectionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variantEClass, Variant.class, "Variant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariant_Name(), ecorePackage.getEString(), "name", null, 0, 1, Variant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(returnStatementEClass, ReturnStatement.class, "ReturnStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(smallRepetitionStatementEClass, SmallRepetitionStatement.class, "SmallRepetitionStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSmallRepetitionStatement_LoopStatement(), this.getAbstractStatement(), null, "loopStatement", null, 1, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSmallRepetitionStatement_Variant(), this.getVariant(), null, "variant", null, 1, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSmallRepetitionStatement_Invariant(), this.getCondition(), null, "invariant", null, 1, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSmallRepetitionStatement_Guard(), this.getCondition(), null, "guard", null, 1, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSmallRepetitionStatement_VariantProven(), ecorePackage.getEBoolean(), "variantProven", null, 0, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSmallRepetitionStatement_PreProven(), ecorePackage.getEBoolean(), "preProven", null, 0, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSmallRepetitionStatement_PostProven(), ecorePackage.getEBoolean(), "postProven", null, 0, 1, SmallRepetitionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaVariablesEClass, JavaVariables.class, "JavaVariables", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaVariables_Variables(), this.getJavaVariable(), null, "variables", null, 0, -1, JavaVariables.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getJavaVariables_Fields(), theCbcclassPackage.getField(), null, "fields", null, 0, -1, JavaVariables.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaVariableEClass, JavaVariable.class, "JavaVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaVariable_Name(), ecorePackage.getEString(), "name", "int a", 0, 1, JavaVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaVariable_Kind(), this.getVariableKind(), "kind", "LOCAL", 0, 1, JavaVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getJavaVariable_DisplayedName(), ecorePackage.getEString(), "displayedName", "kind + \" \" + name", 0, 1, JavaVariable.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(globalConditionsEClass, GlobalConditions.class, "GlobalConditions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalConditions_Conditions(), this.getCondition(), null, "conditions", null, 0, -1, GlobalConditions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(renamingEClass, Renaming.class, "Renaming", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRenaming_Rename(), this.getRename(), null, "rename", null, 0, -1, Renaming.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(renameEClass, Rename.class, "Rename", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRename_Type(), ecorePackage.getEString(), "type", null, 0, 1, Rename.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRename_Function(), ecorePackage.getEString(), "function", null, 0, 1, Rename.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRename_NewName(), ecorePackage.getEString(), "newName", null, 0, 1, Rename.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cbCProblemEClass, CbCProblem.class, "CbCProblem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCbCProblem_Globalcondition(), this.getGlobalConditions(), null, "globalcondition", null, 0, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCProblem_Cbcformula(), this.getCbCFormula(), null, "cbcformula", null, 1, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCProblem_JavaVariable(), this.getJavaVariables(), null, "javaVariable", null, 0, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCProblem_Renaming(), this.getRenaming(), null, "renaming", null, 0, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCProblem_Methodsignature(), this.getMethodSignature(), null, "methodsignature", null, 0, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCbCProblem_MethodClass(), this.getMethodClass(), null, "methodClass", null, 0, 1, CbCProblem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(strengthWeakStatementEClass, StrengthWeakStatement.class, "StrengthWeakStatement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(methodSignatureEClass, MethodSignature.class, "MethodSignature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMethodSignature_MethodSignature(), ecorePackage.getEString(), "methodSignature", null, 0, 1, MethodSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(methodClassEClass, MethodClass.class, "MethodClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMethodClass_MethodClass(), ecorePackage.getEString(), "methodClass", null, 0, 1, MethodClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(variableKindEEnum, VariableKind.class, "VariableKind");
		addEEnumLiteral(variableKindEEnum, VariableKind.LOCAL);
		addEEnumLiteral(variableKindEEnum, VariableKind.PARAM);
		addEEnumLiteral(variableKindEEnum, VariableKind.RETURN);
		addEEnumLiteral(variableKindEEnum, VariableKind.GLOBAL);
		addEEnumLiteral(variableKindEEnum, VariableKind.GLOBAL_PARAM);
		addEEnumLiteral(variableKindEEnum, VariableKind.RETURNPARAM);

		initEEnum(compositionTechniqueEEnum, CompositionTechnique.class, "CompositionTechnique");
		addEEnumLiteral(compositionTechniqueEEnum, CompositionTechnique.CONTRACT_OVERRIDING);
		addEEnumLiteral(compositionTechniqueEEnum, CompositionTechnique.EXPLICIT_CONTRACTING);
		addEEnumLiteral(compositionTechniqueEEnum, CompositionTechnique.CONJUNCTIVE_CONTRACTING);

		// Create resource
		createResource(eNS_URI);
	}

} //CbcmodelPackageImpl
