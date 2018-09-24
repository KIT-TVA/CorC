/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

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
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory
 * @model kind="package"
 * @generated
 */
public interface CbcmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cbcmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/cbcmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cbcmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CbcmodelPackage eINSTANCE = de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl <em>Cb CFormula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCbCFormula()
	 * @generated
	 */
	int CB_CFORMULA = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__NAME = 0;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__STATEMENT = 1;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__PRE_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__POST_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__PROVEN = 4;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA__COMMENT = 5;

	/**
	 * The number of structural features of the '<em>Cb CFormula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Cb CFormula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CFORMULA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 1;

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
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl <em>Abstract Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getAbstractStatement()
	 * @generated
	 */
	int ABSTRACT_STATEMENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__REFINEMENT = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__PARENT = 2;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__POST_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__PRE_CONDITION = 4;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__PROVEN = 5;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__COMMENT = 6;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT__CONTEXT = 7;

	/**
	 * The number of structural features of the '<em>Abstract Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Abstract Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STATEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl <em>Skip Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSkipStatement()
	 * @generated
	 */
	int SKIP_STATEMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The number of structural features of the '<em>Skip Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Skip Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl <em>Composition Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCompositionStatement()
	 * @generated
	 */
	int COMPOSITION_STATEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The feature id for the '<em><b>First Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__FIRST_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Second Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__SECOND_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION = ABSTRACT_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Composition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Composition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl <em>Selection Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSelectionStatement()
	 * @generated
	 */
	int SELECTION_STATEMENT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The feature id for the '<em><b>Guards</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__GUARDS = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Commands</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__COMMANDS = ABSTRACT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pre Prove</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT__PRE_PROVE = ABSTRACT_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Selection Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Selection Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTION_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl <em>Repetition Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRepetitionStatement()
	 * @generated
	 */
	int REPETITION_STATEMENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__GUARD = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Invariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__INVARIANT = ABSTRACT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Start Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__START_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__VARIANT = ABSTRACT_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Loop Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__LOOP_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>End Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__END_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Variant Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT__VARIANT_PROVEN = ABSTRACT_STATEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Repetition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Repetition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPETITION_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl <em>Method Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getMethodStatement()
	 * @generated
	 */
	int METHOD_STATEMENT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The number of structural features of the '<em>Method Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Method Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.VariantImpl <em>Variant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.VariantImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getVariant()
	 * @generated
	 */
	int VARIANT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Variant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIANT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl <em>Return Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getReturnStatement()
	 * @generated
	 */
	int RETURN_STATEMENT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The number of structural features of the '<em>Return Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Return Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__CODE = 1;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl <em>Composition3 Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getComposition3Statement()
	 * @generated
	 */
	int COMPOSITION3_STATEMENT = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The feature id for the '<em><b>Third Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__THIRD_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Second Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__SECOND_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>First Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__FIRST_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Second Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION = ABSTRACT_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>First Intermediate Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION = ABSTRACT_STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Composition3 Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Composition3 Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITION3_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl <em>Small Repetition Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSmallRepetitionStatement()
	 * @generated
	 */
	int SMALL_REPETITION_STATEMENT = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The feature id for the '<em><b>Loop Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__LOOP_STATEMENT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__VARIANT = ABSTRACT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Invariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__INVARIANT = ABSTRACT_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__GUARD = ABSTRACT_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Variant Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__VARIANT_PROVEN = ABSTRACT_STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Pre Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__PRE_PROVEN = ABSTRACT_STATEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Post Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT__POST_PROVEN = ABSTRACT_STATEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Small Repetition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Small Repetition Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SMALL_REPETITION_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl <em>Java Variables</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getJavaVariables()
	 * @generated
	 */
	int JAVA_VARIABLES = 13;

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
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariableImpl <em>Java Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariableImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getJavaVariable()
	 * @generated
	 */
	int JAVA_VARIABLE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Confidentiality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE__CONFIDENTIALITY = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>Java Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Java Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.GlobalConditionsImpl <em>Global Conditions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.GlobalConditionsImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getGlobalConditions()
	 * @generated
	 */
	int GLOBAL_CONDITIONS = 15;

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
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenamingImpl <em>Renaming</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenamingImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRenaming()
	 * @generated
	 */
	int RENAMING = 16;

	/**
	 * The feature id for the '<em><b>Rename</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RENAMING__RENAME = 0;

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
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenameImpl <em>Rename</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenameImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRename()
	 * @generated
	 */
	int RENAME = 17;

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
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCProblemImpl <em>Cb CProblem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCProblemImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCbCProblem()
	 * @generated
	 */
	int CB_CPROBLEM = 18;

	/**
	 * The feature id for the '<em><b>Method</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM__METHOD = 0;

	/**
	 * The feature id for the '<em><b>Globalcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM__GLOBALCONDITION = 1;

	/**
	 * The feature id for the '<em><b>Cbcformula</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM__CBCFORMULA = 2;

	/**
	 * The feature id for the '<em><b>Java Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM__JAVA_VARIABLE = 3;

	/**
	 * The feature id for the '<em><b>Renaming</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM__RENAMING = 4;

	/**
	 * The number of structural features of the '<em>Cb CProblem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Cb CProblem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CB_CPROBLEM_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl <em>Strength Weak Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getStrengthWeakStatement()
	 * @generated
	 */
	int STRENGTH_WEAK_STATEMENT = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__NAME = ABSTRACT_STATEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refinement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__REFINEMENT = ABSTRACT_STATEMENT__REFINEMENT;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__PARENT = ABSTRACT_STATEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Post Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__POST_CONDITION = ABSTRACT_STATEMENT__POST_CONDITION;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__PRE_CONDITION = ABSTRACT_STATEMENT__PRE_CONDITION;

	/**
	 * The feature id for the '<em><b>Proven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__PROVEN = ABSTRACT_STATEMENT__PROVEN;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__COMMENT = ABSTRACT_STATEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT__CONTEXT = ABSTRACT_STATEMENT__CONTEXT;

	/**
	 * The number of structural features of the '<em>Strength Weak Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT_FEATURE_COUNT = ABSTRACT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Strength Weak Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRENGTH_WEAK_STATEMENT_OPERATION_COUNT = ABSTRACT_STATEMENT_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality <em>Confidentiality</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getConfidentiality()
	 * @generated
	 */
	int CONFIDENTIALITY = 20;


	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula <em>Cb CFormula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cb CFormula</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula
	 * @generated
	 */
	EClass getCbCFormula();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getName()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EAttribute getCbCFormula_Name();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getStatement()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EReference getCbCFormula_Statement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPreCondition()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EReference getCbCFormula_PreCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getPostCondition()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EReference getCbCFormula_PostCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#isProven <em>Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#isProven()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EAttribute getCbCFormula_Proven();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula#getComment()
	 * @see #getCbCFormula()
	 * @generated
	 */
	EAttribute getCbCFormula_Comment();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Condition#getName()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement <em>Abstract Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement
	 * @generated
	 */
	EClass getAbstractStatement();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getName()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EAttribute getAbstractStatement_Name();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement <em>Refinement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Refinement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getRefinement()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EReference getAbstractStatement_Refinement();

	/**
	 * Returns the meta object for the container reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getParent()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EReference getAbstractStatement_Parent();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPostCondition <em>Post Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Post Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPostCondition()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EReference getAbstractStatement_PostCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getPreCondition()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EReference getAbstractStatement_PreCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isProven <em>Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#isProven()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EAttribute getAbstractStatement_Proven();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getComment()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EAttribute getAbstractStatement_Comment();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Context</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement#getContext()
	 * @see #getAbstractStatement()
	 * @generated
	 */
	EAttribute getAbstractStatement_Context();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement <em>Skip Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Skip Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement
	 * @generated
	 */
	EClass getSkipStatement();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement <em>Composition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composition Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement
	 * @generated
	 */
	EClass getCompositionStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getFirstStatement <em>First Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>First Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getFirstStatement()
	 * @see #getCompositionStatement()
	 * @generated
	 */
	EReference getCompositionStatement_FirstStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getSecondStatement <em>Second Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Second Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getSecondStatement()
	 * @see #getCompositionStatement()
	 * @generated
	 */
	EReference getCompositionStatement_SecondStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getIntermediateCondition <em>Intermediate Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Intermediate Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement#getIntermediateCondition()
	 * @see #getCompositionStatement()
	 * @generated
	 */
	EReference getCompositionStatement_IntermediateCondition();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement <em>Selection Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Selection Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement
	 * @generated
	 */
	EClass getSelectionStatement();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getGuards <em>Guards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Guards</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getGuards()
	 * @see #getSelectionStatement()
	 * @generated
	 */
	EReference getSelectionStatement_Guards();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getCommands <em>Commands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Commands</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#getCommands()
	 * @see #getSelectionStatement()
	 * @generated
	 */
	EReference getSelectionStatement_Commands();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#isPreProve <em>Pre Prove</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Prove</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement#isPreProve()
	 * @see #getSelectionStatement()
	 * @generated
	 */
	EAttribute getSelectionStatement_PreProve();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement <em>Repetition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repetition Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement
	 * @generated
	 */
	EClass getRepetitionStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getGuard()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getInvariant <em>Invariant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invariant</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getInvariant()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_Invariant();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getStartStatement <em>Start Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Start Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getStartStatement()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_StartStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getVariant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Variant</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getVariant()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_Variant();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getLoopStatement <em>Loop Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Loop Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getLoopStatement()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_LoopStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getEndStatement <em>End Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>End Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#getEndStatement()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EReference getRepetitionStatement_EndStatement();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#isVariantProven <em>Variant Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variant Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement#isVariantProven()
	 * @see #getRepetitionStatement()
	 * @generated
	 */
	EAttribute getRepetitionStatement_VariantProven();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement <em>Method Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement
	 * @generated
	 */
	EClass getMethodStatement();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Variant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variant</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Variant
	 * @generated
	 */
	EClass getVariant();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Variant#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Variant#getName()
	 * @see #getVariant()
	 * @generated
	 */
	EAttribute getVariant_Name();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement <em>Return Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Return Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement
	 * @generated
	 */
	EClass getReturnStatement();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Method#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Method#getName()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Method#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Method#getCode()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Code();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement <em>Composition3 Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composition3 Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement
	 * @generated
	 */
	EClass getComposition3Statement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getThirdStatement <em>Third Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Third Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getThirdStatement()
	 * @see #getComposition3Statement()
	 * @generated
	 */
	EReference getComposition3Statement_ThirdStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondStatement <em>Second Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Second Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondStatement()
	 * @see #getComposition3Statement()
	 * @generated
	 */
	EReference getComposition3Statement_SecondStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstStatement <em>First Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>First Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstStatement()
	 * @see #getComposition3Statement()
	 * @generated
	 */
	EReference getComposition3Statement_FirstStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondIntermediateCondition <em>Second Intermediate Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Second Intermediate Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getSecondIntermediateCondition()
	 * @see #getComposition3Statement()
	 * @generated
	 */
	EReference getComposition3Statement_SecondIntermediateCondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstIntermediateCondition <em>First Intermediate Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>First Intermediate Condition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement#getFirstIntermediateCondition()
	 * @see #getComposition3Statement()
	 * @generated
	 */
	EReference getComposition3Statement_FirstIntermediateCondition();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement <em>Small Repetition Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Small Repetition Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement
	 * @generated
	 */
	EClass getSmallRepetitionStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getLoopStatement <em>Loop Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Loop Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getLoopStatement()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EReference getSmallRepetitionStatement_LoopStatement();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getVariant <em>Variant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Variant</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getVariant()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EReference getSmallRepetitionStatement_Variant();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getInvariant <em>Invariant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invariant</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getInvariant()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EReference getSmallRepetitionStatement_Invariant();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#getGuard()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EReference getSmallRepetitionStatement_Guard();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isVariantProven <em>Variant Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variant Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isVariantProven()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EAttribute getSmallRepetitionStatement_VariantProven();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPreProven <em>Pre Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPreProven()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EAttribute getSmallRepetitionStatement_PreProven();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPostProven <em>Post Proven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Post Proven</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement#isPostProven()
	 * @see #getSmallRepetitionStatement()
	 * @generated
	 */
	EAttribute getSmallRepetitionStatement_PostProven();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables <em>Java Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Variables</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables
	 * @generated
	 */
	EClass getJavaVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables#getVariables()
	 * @see #getJavaVariables()
	 * @generated
	 */
	EReference getJavaVariables_Variables();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable <em>Java Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Variable</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable
	 * @generated
	 */
	EClass getJavaVariable();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getName()
	 * @see #getJavaVariable()
	 * @generated
	 */
	EAttribute getJavaVariable_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getConfidentiality <em>Confidentiality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Confidentiality</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getConfidentiality()
	 * @see #getJavaVariable()
	 * @generated
	 */
	EAttribute getJavaVariable_Confidentiality();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable#getType()
	 * @see #getJavaVariable()
	 * @generated
	 */
	EAttribute getJavaVariable_Type();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions <em>Global Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Conditions</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions
	 * @generated
	 */
	EClass getGlobalConditions();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions#getConditions()
	 * @see #getGlobalConditions()
	 * @generated
	 */
	EReference getGlobalConditions_Conditions();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Renaming <em>Renaming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Renaming</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Renaming
	 * @generated
	 */
	EClass getRenaming();

	/**
	 * Returns the meta object for the containment reference list '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Renaming#getRename <em>Rename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rename</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Renaming#getRename()
	 * @see #getRenaming()
	 * @generated
	 */
	EReference getRenaming_Rename();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename <em>Rename</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rename</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Rename
	 * @generated
	 */
	EClass getRename();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getType()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_Type();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getFunction()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_Function();

	/**
	 * Returns the meta object for the attribute '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getNewName <em>New Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Name</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Rename#getNewName()
	 * @see #getRename()
	 * @generated
	 */
	EAttribute getRename_NewName();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem <em>Cb CProblem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cb CProblem</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem
	 * @generated
	 */
	EClass getCbCProblem();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Method</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getMethod()
	 * @see #getCbCProblem()
	 * @generated
	 */
	EReference getCbCProblem_Method();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getGlobalcondition <em>Globalcondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Globalcondition</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getGlobalcondition()
	 * @see #getCbCProblem()
	 * @generated
	 */
	EReference getCbCProblem_Globalcondition();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getCbcformula <em>Cbcformula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cbcformula</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getCbcformula()
	 * @see #getCbCProblem()
	 * @generated
	 */
	EReference getCbCProblem_Cbcformula();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getJavaVariable <em>Java Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Variable</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getJavaVariable()
	 * @see #getCbCProblem()
	 * @generated
	 */
	EReference getCbCProblem_JavaVariable();

	/**
	 * Returns the meta object for the containment reference '{@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRenaming <em>Renaming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Renaming</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem#getRenaming()
	 * @see #getCbCProblem()
	 * @generated
	 */
	EReference getCbCProblem_Renaming();

	/**
	 * Returns the meta object for class '{@link de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement <em>Strength Weak Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Strength Weak Statement</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement
	 * @generated
	 */
	EClass getStrengthWeakStatement();

	/**
	 * Returns the meta object for enum '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality <em>Confidentiality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Confidentiality</em>'.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality
	 * @generated
	 */
	EEnum getConfidentiality();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CbcmodelFactory getCbcmodelFactory();

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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl <em>Cb CFormula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCbCFormula()
		 * @generated
		 */
		EClass CB_CFORMULA = eINSTANCE.getCbCFormula();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CB_CFORMULA__NAME = eINSTANCE.getCbCFormula_Name();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CFORMULA__STATEMENT = eINSTANCE.getCbCFormula_Statement();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CFORMULA__PRE_CONDITION = eINSTANCE.getCbCFormula_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CFORMULA__POST_CONDITION = eINSTANCE.getCbCFormula_PostCondition();

		/**
		 * The meta object literal for the '<em><b>Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CB_CFORMULA__PROVEN = eINSTANCE.getCbCFormula_Proven();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CB_CFORMULA__COMMENT = eINSTANCE.getCbCFormula_Comment();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.ConditionImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCondition()
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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl <em>Abstract Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getAbstractStatement()
		 * @generated
		 */
		EClass ABSTRACT_STATEMENT = eINSTANCE.getAbstractStatement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STATEMENT__NAME = eINSTANCE.getAbstractStatement_Name();

		/**
		 * The meta object literal for the '<em><b>Refinement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_STATEMENT__REFINEMENT = eINSTANCE.getAbstractStatement_Refinement();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_STATEMENT__PARENT = eINSTANCE.getAbstractStatement_Parent();

		/**
		 * The meta object literal for the '<em><b>Post Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_STATEMENT__POST_CONDITION = eINSTANCE.getAbstractStatement_PostCondition();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_STATEMENT__PRE_CONDITION = eINSTANCE.getAbstractStatement_PreCondition();

		/**
		 * The meta object literal for the '<em><b>Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STATEMENT__PROVEN = eINSTANCE.getAbstractStatement_Proven();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STATEMENT__COMMENT = eINSTANCE.getAbstractStatement_Comment();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STATEMENT__CONTEXT = eINSTANCE.getAbstractStatement_Context();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl <em>Skip Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SkipStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSkipStatement()
		 * @generated
		 */
		EClass SKIP_STATEMENT = eINSTANCE.getSkipStatement();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl <em>Composition Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CompositionStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCompositionStatement()
		 * @generated
		 */
		EClass COMPOSITION_STATEMENT = eINSTANCE.getCompositionStatement();

		/**
		 * The meta object literal for the '<em><b>First Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION_STATEMENT__FIRST_STATEMENT = eINSTANCE.getCompositionStatement_FirstStatement();

		/**
		 * The meta object literal for the '<em><b>Second Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION_STATEMENT__SECOND_STATEMENT = eINSTANCE.getCompositionStatement_SecondStatement();

		/**
		 * The meta object literal for the '<em><b>Intermediate Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION_STATEMENT__INTERMEDIATE_CONDITION = eINSTANCE.getCompositionStatement_IntermediateCondition();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl <em>Selection Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSelectionStatement()
		 * @generated
		 */
		EClass SELECTION_STATEMENT = eINSTANCE.getSelectionStatement();

		/**
		 * The meta object literal for the '<em><b>Guards</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SELECTION_STATEMENT__GUARDS = eINSTANCE.getSelectionStatement_Guards();

		/**
		 * The meta object literal for the '<em><b>Commands</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SELECTION_STATEMENT__COMMANDS = eINSTANCE.getSelectionStatement_Commands();

		/**
		 * The meta object literal for the '<em><b>Pre Prove</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTION_STATEMENT__PRE_PROVE = eINSTANCE.getSelectionStatement_PreProve();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl <em>Repetition Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRepetitionStatement()
		 * @generated
		 */
		EClass REPETITION_STATEMENT = eINSTANCE.getRepetitionStatement();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__GUARD = eINSTANCE.getRepetitionStatement_Guard();

		/**
		 * The meta object literal for the '<em><b>Invariant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__INVARIANT = eINSTANCE.getRepetitionStatement_Invariant();

		/**
		 * The meta object literal for the '<em><b>Start Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__START_STATEMENT = eINSTANCE.getRepetitionStatement_StartStatement();

		/**
		 * The meta object literal for the '<em><b>Variant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__VARIANT = eINSTANCE.getRepetitionStatement_Variant();

		/**
		 * The meta object literal for the '<em><b>Loop Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__LOOP_STATEMENT = eINSTANCE.getRepetitionStatement_LoopStatement();

		/**
		 * The meta object literal for the '<em><b>End Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPETITION_STATEMENT__END_STATEMENT = eINSTANCE.getRepetitionStatement_EndStatement();

		/**
		 * The meta object literal for the '<em><b>Variant Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPETITION_STATEMENT__VARIANT_PROVEN = eINSTANCE.getRepetitionStatement_VariantProven();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl <em>Method Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getMethodStatement()
		 * @generated
		 */
		EClass METHOD_STATEMENT = eINSTANCE.getMethodStatement();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.VariantImpl <em>Variant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.VariantImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getVariant()
		 * @generated
		 */
		EClass VARIANT = eINSTANCE.getVariant();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIANT__NAME = eINSTANCE.getVariant_Name();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl <em>Return Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.ReturnStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getReturnStatement()
		 * @generated
		 */
		EClass RETURN_STATEMENT = eINSTANCE.getReturnStatement();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getMethod()
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
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD__CODE = eINSTANCE.getMethod_Code();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl <em>Composition3 Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.Composition3StatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getComposition3Statement()
		 * @generated
		 */
		EClass COMPOSITION3_STATEMENT = eINSTANCE.getComposition3Statement();

		/**
		 * The meta object literal for the '<em><b>Third Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION3_STATEMENT__THIRD_STATEMENT = eINSTANCE.getComposition3Statement_ThirdStatement();

		/**
		 * The meta object literal for the '<em><b>Second Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION3_STATEMENT__SECOND_STATEMENT = eINSTANCE.getComposition3Statement_SecondStatement();

		/**
		 * The meta object literal for the '<em><b>First Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION3_STATEMENT__FIRST_STATEMENT = eINSTANCE.getComposition3Statement_FirstStatement();

		/**
		 * The meta object literal for the '<em><b>Second Intermediate Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION = eINSTANCE.getComposition3Statement_SecondIntermediateCondition();

		/**
		 * The meta object literal for the '<em><b>First Intermediate Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION = eINSTANCE.getComposition3Statement_FirstIntermediateCondition();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl <em>Small Repetition Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getSmallRepetitionStatement()
		 * @generated
		 */
		EClass SMALL_REPETITION_STATEMENT = eINSTANCE.getSmallRepetitionStatement();

		/**
		 * The meta object literal for the '<em><b>Loop Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SMALL_REPETITION_STATEMENT__LOOP_STATEMENT = eINSTANCE.getSmallRepetitionStatement_LoopStatement();

		/**
		 * The meta object literal for the '<em><b>Variant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SMALL_REPETITION_STATEMENT__VARIANT = eINSTANCE.getSmallRepetitionStatement_Variant();

		/**
		 * The meta object literal for the '<em><b>Invariant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SMALL_REPETITION_STATEMENT__INVARIANT = eINSTANCE.getSmallRepetitionStatement_Invariant();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SMALL_REPETITION_STATEMENT__GUARD = eINSTANCE.getSmallRepetitionStatement_Guard();

		/**
		 * The meta object literal for the '<em><b>Variant Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMALL_REPETITION_STATEMENT__VARIANT_PROVEN = eINSTANCE.getSmallRepetitionStatement_VariantProven();

		/**
		 * The meta object literal for the '<em><b>Pre Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMALL_REPETITION_STATEMENT__PRE_PROVEN = eINSTANCE.getSmallRepetitionStatement_PreProven();

		/**
		 * The meta object literal for the '<em><b>Post Proven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SMALL_REPETITION_STATEMENT__POST_PROVEN = eINSTANCE.getSmallRepetitionStatement_PostProven();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl <em>Java Variables</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariablesImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getJavaVariables()
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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariableImpl <em>Java Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.JavaVariableImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getJavaVariable()
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
		 * The meta object literal for the '<em><b>Confidentiality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_VARIABLE__CONFIDENTIALITY = eINSTANCE.getJavaVariable_Confidentiality();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_VARIABLE__TYPE = eINSTANCE.getJavaVariable_Type();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.GlobalConditionsImpl <em>Global Conditions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.GlobalConditionsImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getGlobalConditions()
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
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenamingImpl <em>Renaming</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenamingImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRenaming()
		 * @generated
		 */
		EClass RENAMING = eINSTANCE.getRenaming();

		/**
		 * The meta object literal for the '<em><b>Rename</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RENAMING__RENAME = eINSTANCE.getRenaming_Rename();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenameImpl <em>Rename</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.RenameImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getRename()
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

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCProblemImpl <em>Cb CProblem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCProblemImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getCbCProblem()
		 * @generated
		 */
		EClass CB_CPROBLEM = eINSTANCE.getCbCProblem();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CPROBLEM__METHOD = eINSTANCE.getCbCProblem_Method();

		/**
		 * The meta object literal for the '<em><b>Globalcondition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CPROBLEM__GLOBALCONDITION = eINSTANCE.getCbCProblem_Globalcondition();

		/**
		 * The meta object literal for the '<em><b>Cbcformula</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CPROBLEM__CBCFORMULA = eINSTANCE.getCbCProblem_Cbcformula();

		/**
		 * The meta object literal for the '<em><b>Java Variable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CPROBLEM__JAVA_VARIABLE = eINSTANCE.getCbCProblem_JavaVariable();

		/**
		 * The meta object literal for the '<em><b>Renaming</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CB_CPROBLEM__RENAMING = eINSTANCE.getCbCProblem_Renaming();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl <em>Strength Weak Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.StrengthWeakStatementImpl
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getStrengthWeakStatement()
		 * @generated
		 */
		EClass STRENGTH_WEAK_STATEMENT = eINSTANCE.getStrengthWeakStatement();

		/**
		 * The meta object literal for the '{@link de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality <em>Confidentiality</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.Confidentiality
		 * @see de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbcmodelPackageImpl#getConfidentiality()
		 * @generated
		 */
		EEnum CONFIDENTIALITY = eINSTANCE.getConfidentiality();

	}

} //CbcmodelPackage
