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
import de.tu_bs.cs.isf.cbc.cbcmodel.Composition3Statement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class Composition3Pattern extends IdPattern implements IPattern {

	private static final String ID_ST1_TEXT = "statement1NameText";
	private static final String ID_INTER1_TEXT = "intermediate1NameText";
	private static final String ID_INTER2_TEXT = "intermediate2NameText";
	private static final String ID_ST2_TEXT = "statement2NameText";
	private static final String ID_ST3_TEXT = "statement3NameText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE1_TEXT = "pre1NameText";
	private static final String ID_POST3_TEXT = "post3NameText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	//headers:
	private static final String ID_ST1_HEADER = "statement1Header";
	private static final String ID_ST2_HEADER = "statement2Header";
	private static final String ID_ST3_HEADER = "statement3Header";
	private static final String ID_IMC1_HEADER = "intermediateCond1Header";
	private static final String ID_IMC2_HEADER = "intermediateCond2Header";
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_POST_HEADER = "postHeader";
	//lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_HOR3_LINE = "hor3Line";
	private static final String ID_HOR4_LINE = "hor4Line";
	private static final String ID_HOR5_LINE = "hor5Line";
	private static final String ID_HOR6_LINE = "hor6Line";
	private static final String ID_VER1_LINE = "ver1Line";
	private static final String ID_VER2_LINE = "ver2Line";
	private static final String ID_VER3_LINE = "ver3Line";
	private static final String ID_VER4_LINE = "ver4Line";


	/**
	 * Constructor of the class
	 */
	public Composition3Pattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Composition3Statement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a CompositionStatement for three statements.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Composition3Statement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		Composition3Statement compoStatement = CbcmodelFactory.eINSTANCE.createComposition3Statement();
		compoStatement.setName("compositionStatement");
		AbstractStatement statement1 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement1.setName("statement1");
		compoStatement.setFirstStatement(statement1);
		Condition pre1 = CbcmodelFactory.eINSTANCE.createCondition();
		pre1.setName("{}");
		statement1.setPreCondition(pre1);
		Condition post1 = CbcmodelFactory.eINSTANCE.createCondition();
		post1.setName("{}");
		statement1.setPostCondition(post1);
		
		Condition condition1 = CbcmodelFactory.eINSTANCE.createCondition();
		condition1.setName("intermediateCond");
		compoStatement.setFirstIntermediateCondition(condition1);
		AbstractStatement statement2 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement2.setName("statement2");
		compoStatement.setSecondStatement(statement2);
		Condition pre2 = CbcmodelFactory.eINSTANCE.createCondition();
		pre2.setName("{}");
		statement2.setPreCondition(pre2);
		Condition post2 = CbcmodelFactory.eINSTANCE.createCondition();
		post2.setName("{}");
		statement2.setPostCondition(post2);
		
		Condition condition2 = CbcmodelFactory.eINSTANCE.createCondition();
		condition2.setName("intermediateCond");
		compoStatement.setSecondIntermediateCondition(condition2);
		AbstractStatement statement3 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement3.setName("statement3");
		compoStatement.setThirdStatement(statement3);
		Condition pre3 = CbcmodelFactory.eINSTANCE.createCondition();
		pre3.setName("{}");
		statement3.setPreCondition(pre3);
		Condition post3 = CbcmodelFactory.eINSTANCE.createCondition();
		post3.setName("{}");
		statement3.setPostCondition(post3);
		
		
		
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
		Composition3Statement addedStatement = (Composition3Statement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 400 : context.getWidth();
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
		MultiText conditionText = gaService.createMultiText(textShapeCondition, "{" + addedStatement.getFirstIntermediateCondition().getName() + "}");
		setId(conditionText, ID_INTER1_TEXT);
		conditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeStatement2 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement2Text = gaService.createMultiText(textShapeStatement2, "");
		setId(statement2Text, ID_ST2_TEXT);
		statement2Text.setValue(addedStatement.getSecondStatement().getName());
		statement2Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement2Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeCondition2 = peCreateService.createShape(outerContainerShape, true);
		MultiText condition2Text = gaService.createMultiText(textShapeCondition2, "{" + addedStatement.getFirstIntermediateCondition().getName() + "}");
		setId(condition2Text, ID_INTER2_TEXT);
		condition2Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		condition2Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeStatement3 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement3Text = gaService.createMultiText(textShapeStatement3, "");
		setId(statement3Text, ID_ST3_TEXT);
		statement3Text.setValue(addedStatement.getThirdStatement().getName());
		statement3Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement3Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "Composition");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);
		
		Shape pre1Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText pre1NameText = gaService.createMultiText(pre1Shape, addedStatement.getFirstStatement().getPreCondition().getName());
		setId(pre1NameText, ID_PRE1_TEXT);
		pre1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post3Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post3NameText = gaService.createMultiText(post3Shape, addedStatement.getSecondStatement().getPostCondition().getName());
		setId(post3NameText, ID_POST3_TEXT);
		post3NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post3NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		//Header:
		Shape shapePreHeader = peCreateService.createShape(outerContainerShape, false);
		Text preHeader = gaService.createText(shapePreHeader, "precondition");
		setId(preHeader, ID_PRE_HEADER);
		preHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setFont(headerFont);
		
		Shape shapePostHeader = peCreateService.createShape(outerContainerShape, false);
		Text postHeader = gaService.createText(shapePostHeader, "postcondition");
		setId(postHeader, ID_POST_HEADER);
		postHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setFont(headerFont);
		
		Shape shapeStatement1Header = peCreateService.createShape(outerContainerShape, false);
		Text statement1Header = gaService.createText(shapeStatement1Header, "statement 1");
		setId(statement1Header, ID_ST1_HEADER);
		statement1Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement1Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statement1Header.setFont(headerFont);
		
		Shape shapeStatement2Header = peCreateService.createShape(outerContainerShape, false);
		Text statement2Header = gaService.createText(shapeStatement2Header, "statement 2");
		setId(statement2Header, ID_ST2_HEADER);
		statement2Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement2Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statement2Header.setFont(headerFont);
		
		Shape shapeStatement3Header = peCreateService.createShape(outerContainerShape, false);
		Text statement3Header = gaService.createText(shapeStatement3Header, "statement 3");
		setId(statement3Header, ID_ST3_HEADER);
		statement3Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement3Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statement3Header.setFont(headerFont);
		
		Shape shapeIMC1Header = peCreateService.createShape(outerContainerShape, false);
		Text IMC1Header = gaService.createText(shapeIMC1Header, "intermediate condition 1");
		setId(IMC1Header, ID_IMC1_HEADER);
		IMC1Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		IMC1Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		IMC1Header.setFont(headerFont);
		
		Shape shapeIMC2Header = peCreateService.createShape(outerContainerShape, false);
		Text IMC2Header = gaService.createText(shapeIMC2Header, "intermediate condition 2");
		setId(IMC2Header, ID_IMC2_HEADER);
		IMC2Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		IMC2Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		IMC2Header.setFont(headerFont);
		//lines:
		Shape ver1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1line = gaService.createPolyline(ver1Shape);
		setId(ver1line, ID_VER1_LINE);
		
		Shape ver2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2line = gaService.createPolyline(ver2Shape);
		setId(ver2line, ID_VER2_LINE);
		
		Shape ver3Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver3line = gaService.createPolyline(ver3Shape);
		setId(ver3line, ID_VER3_LINE);
		
		Shape ver4Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver4line = gaService.createPolyline(ver4Shape);
		setId(ver4line, ID_VER4_LINE);

		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2line = gaService.createPolyline(hor2Shape);
		setId(hor2line, ID_HOR2_LINE);
		
		Shape hor3Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3line = gaService.createPolyline(hor3Shape);
		setId(hor3line, ID_HOR3_LINE);
		
		Shape hor4Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4line = gaService.createPolyline(hor4Shape);
		setId(hor4line, ID_HOR4_LINE);
		
		Shape hor5Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor5line = gaService.createPolyline(hor5Shape);
		setId(hor5line, ID_HOR5_LINE);
		
		Shape hor6Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor6line = gaService.createPolyline(hor6Shape);
		setId(hor6line, ID_HOR6_LINE);
				
		peCreateService.createChopboxAnchor(textShapeStatement1);
		peCreateService.createChopboxAnchor(textShapeStatement2);
		peCreateService.createChopboxAnchor(textShapeStatement3);
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		link(textShapeCondition, addedStatement.getFirstIntermediateCondition());
		link(textShapeCondition2, addedStatement.getSecondIntermediateCondition());
		link(textShapeStatement1, addedStatement.getFirstStatement());
		link(textShapeStatement2, addedStatement.getSecondStatement());
		link(textShapeStatement3, addedStatement.getThirdStatement());
		link(pre1Shape, addedStatement.getFirstStatement().getPreCondition());
		link(post3Shape, addedStatement.getThirdStatement().getPostCondition());
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
		int sizeName = 60;
		int sizeHeader = 20;
		int sizeBlock = (mainRectangle.getHeight() - sizeName) / 3 - sizeHeader;
		int positionLine1 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) /3 * 3 + 20;
		int positionLine2 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) /3 * 2 + 20;
		int positionLine3 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) /3 * 1 + 20;
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_ST1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_INTER1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine3, halfWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_ST2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_INTER2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth,  positionLine3, halfWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_ST3_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_PRE1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1, halfWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_POST3_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionLine1, halfWidth	, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		}
		//HEADER:
		 else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1 - sizeHeader, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionLine1 - sizeHeader, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST1_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST2_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST3_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2  - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_IMC1_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine3 - sizeHeader, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_IMC2_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionLine3 - sizeHeader, halfWidth, sizeHeader);
			changesDone = true;
		//lines:
		} else if(id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1 - sizeHeader, mainRectangle.getWidth(), positionLine1 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1, mainRectangle.getWidth(), positionLine1 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2 - sizeHeader, mainRectangle.getWidth(), positionLine2 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2, mainRectangle.getWidth(), positionLine2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR5_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine3 - sizeHeader, mainRectangle.getWidth(), positionLine3 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR6_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine3, mainRectangle.getWidth(), positionLine3 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { halfWidth, positionLine1 - sizeHeader, halfWidth, positionLine2 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth, positionLine2 - sizeHeader, thirdWidth, positionLine3 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth * 2, positionLine2 - sizeHeader, thirdWidth * 2, positionLine3 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { halfWidth, positionLine3 - sizeHeader, halfWidth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			Composition3Statement domainObject = (Composition3Statement) context.getDomainObject();
			if (checkIsProven(domainObject) &&
					((rectangle.getForeground() != null && !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!checkIsProven(domainObject) && 
					((rectangle.getForeground() != null && rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}  
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Composition3Statement domainObject = (Composition3Statement) context.getDomainObject();
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
			Composition3Statement domainObject = (Composition3Statement) context.getDomainObject();
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
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Composition3Statement domainObject = (Composition3Statement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject)) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			} 
		}
		return false;
	}
	
	private boolean checkIsProven(Composition3Statement statement) {
		AbstractStatement firstStatement = statement.getFirstStatement();
		AbstractStatement secondStatement = statement.getSecondStatement();
		AbstractStatement thirdStatement = statement.getThirdStatement();
		AbstractStatement firstStatementToCheck = null;
		AbstractStatement secondStatementToCheck = null;
		AbstractStatement thirdStatementToCheck = null;
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
		if (thirdStatement.getRefinement() != null) {
			thirdStatementToCheck = thirdStatement.getRefinement();
		} else {
			thirdStatementToCheck = thirdStatement;
		}
		if (firstStatementToCheck.isProven() && secondStatementToCheck.isProven() && thirdStatementToCheck.isProven()) {
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
			Shape pElement = (Shape) obj[0];
			if (pElement.getContainer() != null) updatePictogramElement(pElement.getContainer());
		}
	}
}

