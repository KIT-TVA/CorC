package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Font;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.IFbCSpecification;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodLink;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation of a method link
 * @author Patrick
 *
 */
public class IFbCSpecPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "ifbcSpecName";
	private static final String ID_IFBCSPEC_TEXT = "ifbcSpec_class";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//line:
	private static final String ID_HOR1_LINE = "hor1Line";


	/**
	 * Constructor of the class
	 */
	public IFbCSpecPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "IFbC Specification";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a element to define the IFbC specification";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof IFbCSpecification;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		IFbCSpecification conds = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof IFbCSpecification) {
				conds = (IFbCSpecification) obj;
			}
		}
		if (conds != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		IFbCSpecification spec = CbcmodelFactory.eINSTANCE.createIFbCSpecification();
		spec.setSpecification("");
		
		try {
			CbcModelUtil.saveIFbCSpecificationToModelFile(spec, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, spec);
		return new Object[] { spec };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof IFbCSpecification);
    }
    
    @Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof IFbCSpecification && ga instanceof Text 
				&& getId(ga).equals(ID_IFBCSPEC_TEXT)) {
			return true;
		}
		return false;
	}
	

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		IFbCSpecification spec = (IFbCSpecification) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_IFBCSPEC_TEXT)) {
			return spec.getSpecification();
		}
		return "Something went wrong";
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		IFbCSpecification spec = (IFbCSpecification) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_IFBCSPEC_TEXT)) {
			spec.setSpecification(value);
		}
		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IFbCSpecification addedIFbCSpec = (IFbCSpecification) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 200 : context.getWidth();
        int height = context.getHeight() <= 0 ? 100 : context.getHeight();
        
        Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);
        
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle,
	            context.getX(), context.getY(), width, height);

        // create link and wire it
        link(outerContainerShape, addedIFbCSpec);

		// Header
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text conditionsNameText = gaService.createText(nameTextShape, "IFbC Specification");
		setId(conditionsNameText, ID_NAME_TEXT);
		conditionsNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionsNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionsNameText.setFont(headerFont);
		
		// Specification
		Shape ifbcSpecTextShape = peCreateService.createShape(outerContainerShape, true);
		Text ifbcSpecText = gaService.createText(ifbcSpecTextShape, addedIFbCSpec.getSpecification());
		setId(ifbcSpecText, ID_IFBCSPEC_TEXT);
		ifbcSpecText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		ifbcSpecText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		// classNameText.setFont(headerFont);
		
		//line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);
		
		link(outerContainerShape, addedIFbCSpec);
		link(ifbcSpecTextShape, addedIFbCSpec);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		IFbCSpecification spec = (IFbCSpecification) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight() / 2;
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_IFBCSPEC_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * 1, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height, mainRectangle.getWidth(), height});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} 

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_IFBCSPEC_TEXT)) {
			Text ifbcSpecText = (Text) context.getGraphicsAlgorithm();
			IFbCSpecification domainObject = (IFbCSpecification) context.getDomainObject();
			if (domainObject.getSpecification() == null || !domainObject.getSpecification().equals(ifbcSpecText.getValue())) {
				return Reason.createTrueReason("Specification differs. Expected: '" + domainObject.getSpecification() + "'");
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_IFBCSPEC_TEXT)) {
			//updatePictogramElement(context.getPictogramElement());
			Text ifbcSpecText = (Text) context.getGraphicsAlgorithm();
			IFbCSpecification domainObject = (IFbCSpecification) context.getDomainObject();
			ifbcSpecText.setValue(domainObject.getSpecification());
		}
		System.out.println("IFbCSpecificationPattern update()");
		return true;
	}
}


