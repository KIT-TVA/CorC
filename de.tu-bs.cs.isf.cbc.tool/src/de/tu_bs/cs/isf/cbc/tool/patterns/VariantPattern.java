package de.tu_bs.cs.isf.cbc.tool.patterns;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;

/**
 * Class that creates the graphical representation of Variants
 * @author Tobias
 *
 */
public class VariantPattern extends IdPattern implements IPattern {


	/**
	 * Constructor of the class
	 */
	public VariantPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Variant";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a Variant.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Variant;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return false;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		return null;
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return false;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
//		if(context.getGraphicsAlgorithm() instanceof MultiText) {
//			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
//			Condition domainObject = (Condition) context.getDomainObject();
//			if (domainObject.getName() == null || !(domainObject.getName().equals(nameText.getValue()) || nameText.getValue().equals("{" + domainObject.getName() + "}")) ) {
//				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
//			}
//		}
		

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
//		if(context.getGraphicsAlgorithm() instanceof MultiText) {
//			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
//			Condition domainObject = (Condition) context.getDomainObject();
//			if (domainObject.eContainer().getClass().equals(AbstractStatementImpl.class) 
//					|| domainObject.eContainer().getClass().equals(SkipStatementImpl.class)
//					|| domainObject.eContainer().getClass().equals(CompositionStatementImpl.class)
//					|| domainObject.eContainer().getClass().equals(Composition3StatementImpl.class)) {
//				nameText.setValue("{" + domainObject.getName() + "}");
//			} else {
//				nameText.setValue(domainObject.getName());
//			}
//			return true;
//		}
		return false;
	}

	@Override
	public int getEditingType() {
	    return TYPE_MULTILINETEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Variant && ga instanceof MultiText) {
			return true;
		}
		return false;
	}
	

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Variant variant = (Variant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return variant.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variant must not be empty";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Variant variant = (Variant) getBusinessObjectForPictogramElement(context.getPictogramElement());
		variant.setName(value);
		updatePictogramElement(((Shape) context.getPictogramElement()).getContainer());
	}
}

