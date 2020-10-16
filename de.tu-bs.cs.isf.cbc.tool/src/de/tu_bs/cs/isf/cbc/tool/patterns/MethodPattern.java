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
import de.tu_bs.cs.isf.cbc.cbcmodel.Method;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation of Methods
 * @author Tobias
 *
 */
public class MethodPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "methodName";
	private static final String ID_METHOD_TEXT = "methodNameMethod";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//line:
	private static final String ID_HOR1_LINE = "hor1Line";


	/**
	 * Constructor of the class
	 */
	public MethodPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Method";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a Method.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Method;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		Method method = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof Method) {
				method = (Method) obj;
			}
		}
		if (method != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		Method method = CbcmodelFactory.eINSTANCE.createMethod();
		method.setName("newMethod()");
		
		try {
			CbcModelUtil.saveMethodToModelFile(method, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, method);
		return new Object[] { method };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		Method addedMethod = (Method) context.getNewObject();
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
        link(outerContainerShape, addedMethod);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text methodNameText = gaService.createText(nameTextShape, "");
		setId(methodNameText, ID_NAME_TEXT);
		methodNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		methodNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeMethod = peCreateService.createShape(outerContainerShape, false);
		Text nameTextMethod = gaService.createText(textShapeMethod, "Methoda");
		setId(nameTextMethod, ID_METHOD_TEXT);
		nameTextMethod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setFont(headerFont);
		
		//line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		link(outerContainerShape, addedMethod);
		link(nameTextShape, addedMethod);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int thirdHeight = mainRectangle.getHeight() / 3; 
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, thirdHeight, mainRectangle.getWidth(), thirdHeight * 2);
			changesDone = true;
		} else if (id.equals(ID_METHOD_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), thirdHeight);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, thirdHeight, mainRectangle.getWidth(), thirdHeight});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		} 
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method domainObject = (Method) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		}
		return false;
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Method && ga instanceof Text) {
			return true;
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return method.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Method must not be empty";
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		method.setName(value);
		updatePictogramElement(context.getPictogramElement());
	}
}

