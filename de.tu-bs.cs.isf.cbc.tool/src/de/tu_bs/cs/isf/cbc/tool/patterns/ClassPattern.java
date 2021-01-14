package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
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
import org.emftext.language.java.statements.Statement;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassPackage;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.tool.model.CbcClassModelUtil;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;


public class ClassPattern extends IdPattern implements IPattern{

	private static final String ID_NAME_TEXT = "className";
	private static final String ID_CLASS_TEXT = "class";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//line:
	private static final String ID_HOR1_LINE = "hor1Line";
	
	/*
	 * Constructor of the class
	 * */
	public ClassPattern() {
		super();
	}
	
	public String getCreateName() {
		return "Class Method";
	}
	
	public String getCreateDescription() {
		return "Create a class to a method.";
	}
	

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof MethodClass;
	}	
	
	public boolean canCreate(ICreateContext context) {
		MethodClass javaClass = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof MethodClass) {
				javaClass = (MethodClass) obj;
			}
		}
		if (javaClass != null)
			return false;
		return context.getTargetContainer() instanceof Diagram;
	} 
	
	public Object[] create(ICreateContext context) {
		MethodClass javaClass = CbcmodelFactory.eINSTANCE.createMethodClass();
		javaClass.setMethodClass("newMethodClass");
		
		MethodSignature signature = null;
		CbCFormula formula = null;
		JavaVariables vars = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
		    if (obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			} else if(obj instanceof CbCFormula) {
				formula =(CbCFormula) obj;
			}else if(obj instanceof JavaVariables) {
				vars =(JavaVariables) obj;
			}
		}
		
/*		CbcClassModelUtil.createCbcClassModel(getDiagram(), javaClass.getMethodClass(), signature, formula, vars);
		try {
			CbcModelUtil.saveClassToModelFile(javaClass, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}*/

		addGraphicalRepresentation(context, javaClass);
		return new Object[] { javaClass };
	}
	
	public boolean canAdd(IAddContext context) {
		//return false;
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	protected PictogramElement doAdd(IAddContext context) {
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		MethodClass addedClass = (MethodClass) context.getNewObject();
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
		link(outerContainerShape, addedClass);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, true);
		Text classNameText = gaService.createText(nameTextShape, "");
		setId(classNameText, ID_NAME_TEXT);
		classNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		classNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeMethod = peCreateService.createShape(outerContainerShape, false);
		Text nameTextMethod = gaService.createText(textShapeMethod, "Class Method");
		setId(nameTextMethod, ID_CLASS_TEXT);
		nameTextMethod.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameTextMethod.setFont(headerFont);
		
		// line:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		link(outerContainerShape, addedClass);
		link(nameTextShape, addedClass);

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
		} else if (id.equals(ID_CLASS_TEXT)) {
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

	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof MethodClass);
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			MethodClass domainObject = (MethodClass) context.getDomainObject();
			if (domainObject.getMethodClass() == null || !domainObject.getMethodClass().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getMethodClass() + "'");
			}
		} 
		return Reason.createFalseReason();
	}	
	
	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			MethodClass domainObject = (MethodClass) context.getDomainObject();
			nameText.setValue(domainObject.getMethodClass());
			return true;
		}
		return false;
	}
	
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof MethodClass && ga instanceof Text) {
			return true;
		}
		return false;
	}
	
	public String getInitialValue(IDirectEditingContext context) {
		MethodClass signature = (MethodClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return signature.getMethodClass();
	}
	
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "MethodSignature must not be empty";
		}
		return null;
	}
	

	public void setValue(String value, IDirectEditingContext context) {
		MethodClass method = (MethodClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		method.setMethodClass(value);
		updatePictogramElement(context.getPictogramElement());
	}
	
	/*public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();
		
		MethodClass variable = (MethodClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (variable.eContainer() != null && variable.eContainer() instanceof MethodClass) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
			
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			super.delete(context);
			layoutPictogramElement(container);
		}
	}*/
}
