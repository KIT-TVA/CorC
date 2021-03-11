/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.provider;


import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CbCFormulaItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CbCFormulaItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addPreConditionPropertyDescriptor(object);
			addPostConditionPropertyDescriptor(object);
			addProvenPropertyDescriptor(object);
			addCommentPropertyDescriptor(object);
			addCompositionTechniquePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_name_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Pre Condition feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPreConditionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_preCondition_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_preCondition_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__PRE_CONDITION,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Post Condition feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPostConditionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_postCondition_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_postCondition_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__POST_CONDITION,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Proven feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addProvenPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_proven_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_proven_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__PROVEN,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Comment feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCommentPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_comment_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_comment_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__COMMENT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Composition Technique feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCompositionTechniquePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_CbCFormula_compositionTechnique_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_CbCFormula_compositionTechnique_feature", "_UI_CbCFormula_type"),
				 CbcmodelPackage.Literals.CB_CFORMULA__COMPOSITION_TECHNIQUE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT);
			childrenFeatures.add(CbcmodelPackage.Literals.CB_CFORMULA__PRE_CONDITION);
			childrenFeatures.add(CbcmodelPackage.Literals.CB_CFORMULA__POST_CONDITION);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns CbCFormula.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CbCFormula"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((CbCFormula)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_CbCFormula_type") :
			getString("_UI_CbCFormula_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(CbCFormula.class)) {
			case CbcmodelPackage.CB_CFORMULA__NAME:
			case CbcmodelPackage.CB_CFORMULA__PROVEN:
			case CbcmodelPackage.CB_CFORMULA__COMMENT:
			case CbcmodelPackage.CB_CFORMULA__COMPOSITION_TECHNIQUE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case CbcmodelPackage.CB_CFORMULA__STATEMENT:
			case CbcmodelPackage.CB_CFORMULA__PRE_CONDITION:
			case CbcmodelPackage.CB_CFORMULA__POST_CONDITION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createAbstractStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSkipStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createCompositionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSelectionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createMethodStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createComposition3Statement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createStrengthWeakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__STATEMENT,
				 CbcmodelFactory.eINSTANCE.createOriginalStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__PRE_CONDITION,
				 CbcmodelFactory.eINSTANCE.createCondition()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.CB_CFORMULA__POST_CONDITION,
				 CbcmodelFactory.eINSTANCE.createCondition()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == CbcmodelPackage.Literals.CB_CFORMULA__PRE_CONDITION ||
			childFeature == CbcmodelPackage.Literals.CB_CFORMULA__POST_CONDITION;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return CbcmodelEditPlugin.INSTANCE;
	}

}
