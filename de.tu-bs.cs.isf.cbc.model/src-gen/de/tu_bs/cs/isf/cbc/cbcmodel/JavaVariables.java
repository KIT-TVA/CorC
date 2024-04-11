/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Variables</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables#getVariables <em>Variables</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables#getFields <em>Fields</em>}</li>
 *   <li>{@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables#getParams <em>Params</em>}</li>
 * </ul>
 *
 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariables()
 * @model
 * @generated
 */
public interface JavaVariables extends EObject {
	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariables_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<JavaVariable> getVariables();

	/**
	 * Returns the value of the '<em><b>Fields</b></em>' reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcclass.Field}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fields</em>' reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariables_Fields()
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel get='if (eDirectResource() != null) { \t\t\tif (eDirectResource().getResourceSet() != null) { \t\t\t\tif (eDirectResource().getResourceSet().getResources() != null) { \t\t\t\t\tEList&lt;Resource&gt; resources = eDirectResource().getResourceSet().getResources(); \t\t\t\t\tURI uri = eDirectResource().getURI(); \t\t\t\t\turi = uri.trimSegments(1); \t\t\t\t\turi = uri.appendSegment(uri.lastSegment() + \".cbcclass\"); \t\t\t\t\tfor (Resource res : resources) { \t\t\t\t\t\tif (res.getURI().equals(uri)) { \t\t\t\t\t\t\tEList&lt;EObject&gt; content = res.getContents(); \t\t\t\t\t\t\tfor (EObject o : content) { \t\t\t\t\t\t\t\tif (o instanceof ModelClass) { \t\t\t\t\t\t\t\t\treturn ((ModelClass) o).getFields(); \t\t\t\t\t\t\t\t} \t\t\t\t\t\t\t} \t\t\t\t\t\t} \t\t\t\t\t} \t\t\t\t} \t\t\t} \t\t}\t \t\tif (fields == null) { \t\t\tfields = new EObjectResolvingEList&lt;Field&gt;(Field.class, this, CbcmodelPackage.JAVA_VARIABLES__FIELDS); \t\t} \t\treturn fields;'"
	 * @generated
	 */
	EList<Field> getFields();

	/**
	 * Returns the value of the '<em><b>Params</b></em>' containment reference list.
	 * The list contents are of type {@link de.tu_bs.cs.isf.cbc.cbcclass.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Params</em>' containment reference list.
	 * @see de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage#getJavaVariables_Params()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='null'"
	 * @generated
	 */
	EList<Parameter> getParams();

} // JavaVariables
