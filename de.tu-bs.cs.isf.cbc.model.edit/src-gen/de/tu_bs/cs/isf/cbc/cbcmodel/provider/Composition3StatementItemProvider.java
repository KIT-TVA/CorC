/**
 */
package de.tu_bs.cs.isf.cbc.cbcmodel.provider;


import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class Composition3StatementItemProvider extends AbstractStatementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Composition3StatementItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT);
			childrenFeatures.add(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT);
			childrenFeatures.add(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT);
			childrenFeatures.add(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION);
			childrenFeatures.add(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION);
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
	 * This returns Composition3Statement.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Composition3Statement"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Composition3Statement)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_Composition3Statement_type") :
			getString("_UI_Composition3Statement_type") + " " + label;
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

		switch (notification.getFeatureID(Composition3Statement.class)) {
			case CbcmodelPackage.COMPOSITION3_STATEMENT__THIRD_STATEMENT:
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_STATEMENT:
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_STATEMENT:
			case CbcmodelPackage.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION:
			case CbcmodelPackage.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION:
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
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createAbstractStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSkipStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createCompositionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSelectionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createMethodStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createComposition3Statement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createStrengthWeakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createOriginalStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createAbstractStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSkipStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createCompositionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSelectionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createMethodStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createComposition3Statement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createStrengthWeakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createOriginalStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createAbstractStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSkipStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createCompositionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSelectionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createMethodStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createReturnStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createComposition3Statement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createStrengthWeakStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT,
				 CbcmodelFactory.eINSTANCE.createOriginalStatement()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION,
				 CbcmodelFactory.eINSTANCE.createCondition()));

		newChildDescriptors.add
			(createChildParameter
				(CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION,
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
			childFeature == CbcmodelPackage.Literals.ABSTRACT_STATEMENT__REFINEMENT ||
			childFeature == CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__THIRD_STATEMENT ||
			childFeature == CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_STATEMENT ||
			childFeature == CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_STATEMENT ||
			childFeature == CbcmodelPackage.Literals.ABSTRACT_STATEMENT__POST_CONDITION ||
			childFeature == CbcmodelPackage.Literals.ABSTRACT_STATEMENT__PRE_CONDITION ||
			childFeature == CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__SECOND_INTERMEDIATE_CONDITION ||
			childFeature == CbcmodelPackage.Literals.COMPOSITION3_STATEMENT__FIRST_INTERMEDIATE_CONDITION;

		if (qualify) {
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
