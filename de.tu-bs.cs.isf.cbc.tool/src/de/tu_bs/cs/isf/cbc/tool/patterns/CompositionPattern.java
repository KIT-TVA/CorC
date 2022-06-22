package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
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
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class CompositionPattern extends IdPattern implements IPattern {

	private static final String ID_ST1_TEXT = "statement1NameText";
	private static final String ID_CONDITION_TEXT = "conditionText";
	private static final String ID_ST2_TEXT = "statement2NameText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE1_TEXT = "pre1NameText";
	private static final String ID_POST2_TEXT = "post2NameText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	//Header:
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_POST_HEADER = "postHeader";
	private static final String ID_ST1_HEADER = "statement1Header";
	private static final String ID_ST2_HEADER = "statement2Header";
	private static final String ID_IMC_HEADER = "intermediateCondHeader";
	//lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_HOR3_LINE = "hor3Line";
	private static final String ID_HOR4_LINE = "hor4Line";
	private static final String ID_VER1_LINE = "ver1Line";
	private static final String ID_VER2_LINE = "ver2Line";
	private static final String ID_VER3_LINE = "ver3Line";


	/**
	 * Constructor of the class
	 */
	public CompositionPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "CompositionStatement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a CompositionStatement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof CompositionStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		CompositionStatement compoStatement = CbcmodelFactory.eINSTANCE.createCompositionStatement();
		compoStatement.setName("compositionStatement");
		AbstractStatement statement1 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement1.setName("statement1");
		compoStatement.setFirstStatement(statement1);
		Condition pre1 = CbcmodelFactory.eINSTANCE.createCondition();
		pre1.setName("");
		statement1.setPreCondition(pre1);
		Condition post1 = CbcmodelFactory.eINSTANCE.createCondition();
		post1.setName("");
		statement1.setPostCondition(post1);
		
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("intermediateCond");
		compoStatement.setIntermediateCondition(condition);
		AbstractStatement statement2 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement2.setName("statement2");
		compoStatement.setSecondStatement(statement2);
		Condition pre2 = CbcmodelFactory.eINSTANCE.createCondition();
		pre2.setName("");
		statement2.setPreCondition(pre2);
		Condition post2 = CbcmodelFactory.eINSTANCE.createCondition();
		post2.setName("");
		statement2.setPostCondition(post2);
		
		addGraphicalRepresentation(context, compoStatement);
		return new Object[] { compoStatement };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		CompositionStatement addedStatement = (CompositionStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();
		int width = context.getWidth() <= 0 ? 500 : context.getWidth();
        int height = context.getHeight() <= 0 ? 300 : context.getHeight();
        //font:
        Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);
        
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		mainRectangle.setForeground(manageColor(IColorConstant.RED));
		mainRectangle.setLineWidth(2);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle,
	            context.getX(), context.getY(), width, height);

        // create link and wire it
        link(outerContainerShape, addedStatement);

		// Statement name
        Shape textShapeStatement1 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement1Text = gaService.createMultiText(textShapeStatement1, "");
		setId(statement1Text, ID_ST1_TEXT);
		statement1Text.setValue(addedStatement.getFirstStatement().getName());
		statement1Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement1Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeCondition = peCreateService.createShape(outerContainerShape, true);
		MultiText conditionText = gaService.createMultiText(textShapeCondition, "{" + addedStatement.getIntermediateCondition().getName() + "}");
		setId(conditionText, ID_CONDITION_TEXT);
		conditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeStatement2 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement2Text = gaService.createMultiText(textShapeStatement2, "");
		setId(statement2Text, ID_ST2_TEXT);
		statement2Text.setValue(addedStatement.getSecondStatement().getName());
		statement2Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement2Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "Composition");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);
		
		Shape pre1Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText pre1NameText = gaService.createMultiText(pre1Shape, "{" + addedStatement.getFirstStatement().getPreCondition().getName()+ "}");
		setId(pre1NameText, ID_PRE1_TEXT);
		pre1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post2Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post2NameText = gaService.createMultiText(post2Shape, "{" + addedStatement.getSecondStatement().getPostCondition().getName()+ "}");
		setId(post2NameText, ID_POST2_TEXT);
		post2NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post2NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		//Header:
		Shape preHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text preHeader = gaService.createText(preHeaderShape, "precondition");
		setId(preHeader, ID_PRE_HEADER);
		preHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setFont(headerFont);
		
		Shape postHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text postHeader = gaService.createText(postHeaderShape, "postcondition");
		setId(postHeader, ID_POST_HEADER);
		postHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setFont(headerFont);
		
		Shape st1HeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text st1Header = gaService.createText(st1HeaderShape, "statement 1");
		setId(st1Header, ID_ST1_HEADER);
		st1Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		st1Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		st1Header.setFont(headerFont);
		
		Shape st2HeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text st2Header = gaService.createText(st2HeaderShape, "statement 2");
		setId(st2Header, ID_ST2_HEADER);
		st2Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		st2Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		st2Header.setFont(headerFont);
		
		Shape imcHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text imcHeader = gaService.createText(imcHeaderShape, "intermediate condition");
		setId(imcHeader, ID_IMC_HEADER);
		imcHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		imcHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		imcHeader.setFont(headerFont);
		//lines:
		Shape hor1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Line = gaService.createPolyline(hor1LineShape);
		setId(hor1Line, ID_HOR1_LINE);
		
		Shape hor2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Line = gaService.createPolyline(hor2LineShape);
		setId(hor2Line, ID_HOR2_LINE);
		
		Shape hor3LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3Line = gaService.createPolyline(hor3LineShape);
		setId(hor3Line, ID_HOR3_LINE);
		
		Shape hor4LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4Line = gaService.createPolyline(hor4LineShape);
		setId(hor4Line, ID_HOR4_LINE);
		
		Shape ver1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1Line = gaService.createPolyline(ver1LineShape);
		setId(ver1Line, ID_VER1_LINE);
		
		Shape ver2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2Line = gaService.createPolyline(ver2LineShape);
		setId(ver2Line, ID_VER2_LINE);
		
		Shape ver3LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver3Line = gaService.createPolyline(ver3LineShape);
		setId(ver3Line, ID_VER3_LINE);
				
		peCreateService.createChopboxAnchor(textShapeStatement1);
		peCreateService.createChopboxAnchor(textShapeStatement2);
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		link(textShapeCondition, addedStatement.getIntermediateCondition());
		link(textShapeStatement1, addedStatement.getFirstStatement());
		link(textShapeStatement2, addedStatement.getSecondStatement());
		link(pre1Shape, addedStatement.getFirstStatement().getPreCondition());
		link(post2Shape, addedStatement.getSecondStatement().getPostCondition());
		link(proveShape, addedStatement);
		
		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int thirdWidth = mainRectangle.getWidth() / 3;
		int halfWidth = mainRectangle.getWidth() / 2;
		int sizeName = 60; //size of the name from the Statement (Composition)
		int HeightWithoutName = mainRectangle.getHeight() - sizeName; //size from the statement without the Name block, -20 for some space
		int sizeHeader = 20;
		int blockSize = HeightWithoutName / 2 - sizeHeader; //size from one block (statement, pre, ..)
		int positionLine1 = mainRectangle.getHeight() - HeightWithoutName;
		int positionLine2 = mainRectangle.getHeight() - HeightWithoutName / 2;
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_ST1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2 + sizeHeader, thirdWidth, blockSize);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2 + sizeHeader, thirdWidth, blockSize);
			changesDone = true;
		} else if (id.equals(ID_ST2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2 + sizeHeader, thirdWidth, blockSize);
			changesDone = true;
		} else if (id.equals(ID_PRE1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1 + sizeHeader, halfWidth, blockSize);
			changesDone = true;
		}  else if (id.equals(ID_POST2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionLine1 + sizeHeader, halfWidth, blockSize);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
			
		//HEADER:
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga,  halfWidth, positionLine1, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST1_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST2_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_IMC_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2, thirdWidth, sizeHeader);
			changesDone = true;
		//lines:	
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1, mainRectangle.getWidth(), positionLine1});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1 + sizeHeader, mainRectangle.getWidth(), positionLine1 + sizeHeader});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2 , mainRectangle.getWidth(), positionLine2});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2 + sizeHeader, mainRectangle.getWidth(), positionLine2 + sizeHeader});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { halfWidth, positionLine1, halfWidth, positionLine2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth, positionLine2, thirdWidth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth * 2, positionLine2, thirdWidth * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			CompositionStatement domainObject = (CompositionStatement) context.getDomainObject();
			if (checkIsProven(domainObject) &&
					((rectangle.getForeground() != null && !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!checkIsProven(domainObject) && 
					((rectangle.getForeground() != null && rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}  
		} 
		else if (id.equals(ID_IMAGE_PROVEN)) {
			CompositionStatement domainObject = (CompositionStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject) && image.getId().equals(CbCImageProvider.IMG_UNPROVEN)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!checkIsProven(domainObject) && image.getId().equals(CbCImageProvider.IMG_PROVEN)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			} 
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			CompositionStatement domainObject = (CompositionStatement) context.getDomainObject();
			if (checkIsProven(domainObject)) {
				domainObject.setProven(true);
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				updateParent(domainObject);
			} else {
				domainObject.setProven(false);
				rectangle.setForeground(manageColor(IColorConstant.RED));
				updateParent(domainObject);
			}
			return true;
		} 
		else if (id.equals(ID_IMAGE_PROVEN)) {
			CompositionStatement domainObject = (CompositionStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject)) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			} 
		}
		return false;
	}
	
	private boolean checkIsProven(CompositionStatement statement) {
		AbstractStatement firstStatement = statement.getFirstStatement();
		AbstractStatement secondStatement = statement.getSecondStatement();
		AbstractStatement firstStatementToCheck = null;
		AbstractStatement secondStatementToCheck = null;
		if (firstStatement.getRefinement() != null) {
			firstStatementToCheck = firstStatement.getRefinement();
		} else {
			firstStatementToCheck = firstStatement;
		}
		if (secondStatement.getRefinement() != null) {
			secondStatementToCheck = secondStatement.getRefinement();
		} else {
			secondStatementToCheck = secondStatement;
		}
		if (firstStatementToCheck.isProven() && secondStatementToCheck.isProven()) {
			return true;
		} else {
			return false;
		}
	}
	
	private void updateParent(AbstractStatement statement) {
		if(statement.getParent()!= null) {
			IPeService pe = Graphiti.getPeService();
			EObject[] objArray = {statement.getParent()};
			Object[] obj =  pe.getLinkedPictogramElements(objArray, getDiagram());
			if (obj.length > 0) {
				Shape pElement = (Shape) obj[0];
				if (pElement.getContainer() != null) updatePictogramElement(pElement.getContainer());
			}
		}
	}
}