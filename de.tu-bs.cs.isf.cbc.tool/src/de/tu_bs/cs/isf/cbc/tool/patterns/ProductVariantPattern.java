package de.tu_bs.cs.isf.cbc.tool.patterns;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements;
import de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant;

public class ProductVariantPattern extends IdPattern implements IPattern {
	/**
	 * Constructor of the class
	 */
	public ProductVariantPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "ProductVariant";
	}

	@Override
	public String getCreateDescription() {
		return "Create a product variant.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof ProductVariant;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof MethodRefinements;
	}

	@Override
	public Object[] create(ICreateContext context) {
		MethodRefinements methodRefinement = (MethodRefinements) getBusinessObjectForPictogramElement(
				context.getTargetContainer());
		ProductVariant variant = CbcmodelFactory.eINSTANCE.createProductVariant();
		variant.setRefinementChain("Example.example");
		methodRefinement.getProductvariants().add(variant);
		updatePictogramElement(context.getTargetContainer());
		return new Object[] { variant };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return false;
	}

	@Override
	protected PictogramElement doAdd(IAddContext arg0) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext arg0, String arg1) {
		return false;
	}

	public boolean canUpdate(IdUpdateContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return (bo instanceof ProductVariant);
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
		ProductVariant domainObject = (ProductVariant) getBusinessObjectForPictogramElement(
				context.getPictogramElement());
		nameText.setValue(domainObject.getRefinementChain());
		return true;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
		ProductVariant domainObject = (ProductVariant) getBusinessObjectForPictogramElement(
				context.getPictogramElement());
		if (domainObject.getRefinementChain() == null
				|| !nameText.getValue().equals(domainObject.getRefinementChain())) {
			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getRefinementChain() + "'");
		}
		return Reason.createFalseReason();
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof ProductVariant && ga instanceof Text) {
			return true;
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		ProductVariant variant = (ProductVariant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return variant.getRefinementChain();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variant must not be empty";
		} else if (!value.matches("\\w+(\\.\\w+)?(,\\w+(\\.\\w+)?)*")) {
			return "Refinement chain has to be of the form \"Example.example,Example2.example2,...\"";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		ProductVariant variant = (ProductVariant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		variant.setRefinementChain(value);
		updatePictogramElement(((Shape) context.getPictogramElement()));
	}

	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();

		ProductVariant variant = (ProductVariant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (variant.eContainer() != null && variant.eContainer() instanceof MethodRefinements) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());

			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			super.delete(context);
			layoutPictogramElement(container);
		}
	}

}
