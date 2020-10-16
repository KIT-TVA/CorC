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

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

public class MethodSignaturePattern extends IdPattern implements IPattern{

	private static final String ID_NAME_TEXT = "signatureName";
	private static final String ID_SIGNATURE_TEXT = "signature";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//line:
	private static final String ID_HOR1_LINE = "hor1Line";
	
	/*
	 * Constructor of the class
	 **/
	public MethodSignaturePattern() {
		super();
	}
	
	public String getCreateName() {
		return "Method Signature";
	}
	
	public String getCreateDescription() {
		return "Create method signature.";
	}
	
	public boolean canCreate(ICreateContext context) {
		MethodSignature signature = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			}
		}
		if (signature != null)
			return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	public Object[] create(ICreateContext context) {
		MethodSignature signature = CbcmodelFactory.eINSTANCE.createMethodSignature();
		signature.setMethodSignature("void newMethod(int a)");

		try {
			CbcModelUtil.saveSignatureToModelFile(signature, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}

		addGraphicalRepresentation(context, signature);
		
		CbCFormula formula = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
		    if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} 
		}
		if(formula != null) {
			formula.setMethodName("newMethod(int)");
		}
		return new Object[] { signature };
	}
	
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	protected PictogramElement doAdd(IAddContext context) {
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		MethodSignature addedSignature = (MethodSignature) context.getNewObject();
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
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedSignature);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, true);
		Text signatureNameText = gaService.createText(nameTextShape, "");
		setId(signatureNameText, ID_NAME_TEXT);
		signatureNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		signatureNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeMethod = peCreateService.createShape(outerContainerShape, false);
		Text nameTextMethod = gaService.createText(textShapeMethod, "Method Signature");
		setId(nameTextMethod, ID_SIGNATURE_TEXT);
		nameTextMethod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setFont(headerFont);
		
		// line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		link(outerContainerShape, addedSignature);
		link(nameTextShape, addedSignature);

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
		} else if (id.equals(ID_SIGNATURE_TEXT)) {
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
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			MethodSignature domainObject = (MethodSignature) context.getDomainObject();
			nameText.setValue(domainObject.getMethodSignature());
			return true;
		}
		return false;
	}

	public int getEditingType() {
		return TYPE_TEXT;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			MethodSignature domainObject = (MethodSignature) context.getDomainObject();
			if (domainObject.getMethodSignature() == null || !domainObject.getMethodSignature().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getMethodSignature() + "'");
			}
		} 
		return Reason.createFalseReason();
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof MethodSignature;
	}
	
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof MethodSignature && ga instanceof Text) {
			return true;
		}
		return false;
	}
	
	public String getInitialValue(IDirectEditingContext context) {
		MethodSignature signature = (MethodSignature) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return signature.getMethodSignature();
	}
	
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "MethodSignature must not be empty";
		} else if(value.length() > 0 && !value.matches(
				"(static\\s)?"
				+ "(int|char|float|long|boolean|byte|short|double|([A-Z]\\w*)|void)"
				+ "(\\[\\])?(\\s)"
				+ "(\\w+)"
				+ "([\\(])"
				+ "("//([\\,s])?"
				+ "(int|char|float|long|boolean|byte|short|double|([A-Z]\\w*))"
				+ "(\\[\\])?"
				+ "(\\s)"
				+ "[a-zA-Z]\\w*(\\,\\s)?)*"
				+ "([\\)])")
				) {
			return "Value is not valid";
		}
		return null;
	}
	
	public void setValue(String value, IDirectEditingContext context) {
		MethodSignature method = (MethodSignature) getBusinessObjectForPictogramElement(context.getPictogramElement());
		method.setMethodSignature(value);

		
		CbCFormula formula = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
		    if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} 
		}

	    value = value.replace("public", "");
	    value = value.replace("static", "");
	    value = value.trim().split(" ", 2)[1];
	    if(!value.contains("()")) {
	    	String s2 = value.substring(value.indexOf('(') + 1, value.lastIndexOf(')'));
	    	String[] variableTypes = s2.split(",");
	    	s2 = variableTypes[0].trim().split(" ")[0];
	    	for(int i = 1; i < variableTypes.length; i++) {
	    		if(variableTypes[i].trim().split(" ")[0].equals("float"))
	    			s2 = s2 + ",double";
	    		else
	    			s2 = s2 + "," + variableTypes[i].trim().split(" ")[0];
	    	}
	    	value = value.substring(0, value.indexOf('(') + 1);
	    	value = value + s2 + ")";
	    }
		if(formula != null) {
			formula.setMethodName(value);
		}
		
		updatePictogramElement(context.getPictogramElement());
	}
}
