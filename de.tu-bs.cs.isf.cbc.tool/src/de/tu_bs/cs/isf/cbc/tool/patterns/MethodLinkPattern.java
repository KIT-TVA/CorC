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
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodLink;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation of a method link
 * @author Patrick
 *
 */
public class MethodLinkPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "methodLinkName";
	private static final String ID_METHODLINK_CLASS_TEXT = "methodlink_class";
	private static final String ID_METHODLINK_SIGNATURE_TEXT = "methodlink_signature";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//line:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";


	/**
	 * Constructor of the class
	 */
	public MethodLinkPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Method Link";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a link to method defined in source code";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof MethodLink;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		MethodLink conds = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof MethodLink) {
				conds = (MethodLink) obj;
			}
		}
		if (conds != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		MethodLink methodLink = CbcmodelFactory.eINSTANCE.createMethodLink();
		methodLink.setClassName("className");
		methodLink.setMethodSignature("Data exampleSignature(Data)");
		
		try {
			CbcModelUtil.saveMethodLinkToModelFile(methodLink, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, methodLink);
		return new Object[] { methodLink };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof MethodLink);
    }
    
    @Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof MethodLink && ga instanceof Text 
				&& (getId(ga).equals(ID_METHODLINK_CLASS_TEXT) || getId(ga).equals(ID_METHODLINK_SIGNATURE_TEXT))) {
			return true;
		}
		return false;
	}
	

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		MethodLink methodLink = (MethodLink) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_METHODLINK_CLASS_TEXT)) {
			return methodLink.getClassName();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_METHODLINK_SIGNATURE_TEXT)) {
			return methodLink.getMethodSignature();
		}
		return "Something went wrong";
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		MethodLink methodLink = (MethodLink) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_METHODLINK_CLASS_TEXT)) {
			methodLink.setClassName(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_METHODLINK_SIGNATURE_TEXT)) {
			methodLink.setMethodSignature(value);
		}
		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		MethodLink addedMethodLink = (MethodLink) context.getNewObject();
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
        link(outerContainerShape, addedMethodLink);

		// Header
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text conditionsNameText = gaService.createText(nameTextShape, "Method Link");
		setId(conditionsNameText, ID_NAME_TEXT);
		conditionsNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionsNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionsNameText.setFont(headerFont);
		
		// className
		Shape classNameTextShape = peCreateService.createShape(outerContainerShape, true);
		Text classNameText = gaService.createText(classNameTextShape, addedMethodLink.getClassName());
		setId(classNameText, ID_METHODLINK_CLASS_TEXT);
		classNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		classNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		// classNameText.setFont(headerFont);
		
		// signature
		Shape signatureTextShape = peCreateService.createShape(outerContainerShape, true);
		Text signatureText = gaService.createText(signatureTextShape, addedMethodLink.getMethodSignature());
		setId(signatureText, ID_METHODLINK_SIGNATURE_TEXT);
		signatureText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		signatureText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		// conditionsNameText.setFont(headerFont);
		
		//line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2line = gaService.createPolyline(hor2Shape);
		setId(hor2line, ID_HOR2_LINE);
		
		link(outerContainerShape, addedMethodLink);
		link(classNameTextShape, addedMethodLink);
		link(signatureTextShape, addedMethodLink);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		MethodLink methodLink = (MethodLink) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight() / 3;
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_METHODLINK_CLASS_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * 1, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_METHODLINK_SIGNATURE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * 2, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height, mainRectangle.getWidth(), height});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			polyline.setLineStyle(LineStyle.DOT);
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height * 2, mainRectangle.getWidth(), height * 2});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_METHODLINK_CLASS_TEXT)) {
			Text classNameText = (Text) context.getGraphicsAlgorithm();
			MethodLink domainObject = (MethodLink) context.getDomainObject();
			if (domainObject.getClassName() == null || !domainObject.getClassName().equals(classNameText.getValue())) {
				return Reason.createTrueReason("Class Name differs. Expected: '" + domainObject.getClassName() + "'");
			}
		} else if (id.equals(ID_METHODLINK_SIGNATURE_TEXT)) {
			Text signatureText = (Text) context.getGraphicsAlgorithm();
			MethodLink domainObject = (MethodLink) context.getDomainObject();
			if (domainObject.getMethodSignature() == null || !domainObject.getMethodSignature().equals(signatureText.getValue())) {
				return Reason.createTrueReason("Method signature differs. Expected: '" + domainObject.getMethodSignature() + "'");
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_METHODLINK_CLASS_TEXT)) {
			//updatePictogramElement(context.getPictogramElement());
			Text classNameText = (Text) context.getGraphicsAlgorithm();
			MethodLink domainObject = (MethodLink) context.getDomainObject();
			classNameText.setValue(domainObject.getClassName());
		} else if (id.equals(ID_METHODLINK_SIGNATURE_TEXT)) {
			//updatePictogramElement(context.getPictogramElement());
			Text signatureText = (Text) context.getGraphicsAlgorithm();
			MethodLink domainObject = (MethodLink) context.getDomainObject();
			signatureText.setValue(domainObject.getMethodSignature());
		}
		System.out.println("MethodLinkPattern update()");
		return true;
	}
}


