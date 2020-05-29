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
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class SmallRepetitionPattern extends IdPattern implements IPattern {

	private static final String ID_STATEMENT_TEXT = "statementRepNameText";
	private static final String ID_CONDITION_TEXT = "conditionRepText";
	private static final String ID_VARIANT_TEXT = "variantRepText";
	private static final String ID_INVARIANT_TEXT = "invariantRepText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE_TEXT = "preNameText";
	private static final String ID_POST_TEXT = "postNameText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	//Headers:
	private static final String ID_VARIANT_HEADER = "variantHeader";
	private static final String ID_INVARIANT_HEADER = "invariantHeader";
	private static final String ID_GUARD_HEADER = "guardHeader";
	private static final String ID_LOOP_HEADER = "loopHeader";
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_POST_HEADER = "postHeader";
	//lines:
	private static final String ID_HOR1_SEP = "hor1SEP"; // HOR = horizontal
	private static final String ID_HOR2_SEP = "hor2SEP";
	private static final String ID_HOR3_SEP = "hor3SEP";
	private static final String ID_HOR4_SEP = "hor4SEP";
	private static final String ID_VER1_SEP = "ver1SEP"; //VER = vertical
	private static final String ID_VER2_SEP = "ver2SEP";


	/**
	 * Constructor of the class
	 */
	public SmallRepetitionPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "SmallRepetitionStatement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a RepetitionStatement with only the loop statement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof SmallRepetitionStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		SmallRepetitionStatement repetitionStatement = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
		repetitionStatement.setName("repetitionStatement");
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("loop");
		repetitionStatement.setLoopStatement(statement);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("guard");
		repetitionStatement.setGuard(condition);
		Condition invariant = CbcmodelFactory.eINSTANCE.createCondition();
		invariant.setName("invariant");
		repetitionStatement.setInvariant(invariant);
		Variant variant = CbcmodelFactory.eINSTANCE.createVariant();
		variant.setName("variant");
		repetitionStatement.setVariant(variant);
		
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);
		
		Condition preRep = CbcmodelFactory.eINSTANCE.createCondition();
		preRep.setName("");
		repetitionStatement.setPreCondition(preRep);
		Condition postRep = CbcmodelFactory.eINSTANCE.createCondition();
		postRep.setName("");
		repetitionStatement.setPostCondition(postRep);
		
		addGraphicalRepresentation(context, repetitionStatement);
		return new Object[] { repetitionStatement };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {

manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		SmallRepetitionStatement addedStatement = (SmallRepetitionStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 300 : context.getWidth();
        int height = context.getHeight() <= 0 ? 300 : context.getHeight();
        //Font:
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
		setId(statement1Text, ID_STATEMENT_TEXT);
		statement1Text.setValue(addedStatement.getLoopStatement().getName());
		statement1Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement1Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeCondition = peCreateService.createShape(outerContainerShape, true);
		MultiText conditionText = gaService.createMultiText(textShapeCondition, "");
		setId(conditionText, ID_CONDITION_TEXT);
		conditionText.setValue(addedStatement.getGuard().getName());
		conditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeInvariant = peCreateService.createShape(outerContainerShape, true);
		MultiText invariantText = gaService.createMultiText(textShapeInvariant, "");
		setId(invariantText, ID_INVARIANT_TEXT);
		invariantText.setValue(addedStatement.getInvariant().getName());
		invariantText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeVariant = peCreateService.createShape(outerContainerShape, true);
		MultiText variantText = gaService.createMultiText(textShapeVariant, "");
		setId(variantText, ID_VARIANT_TEXT);
		variantText.setValue(addedStatement.getVariant().getName());
		variantText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		variantText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "RepetitionStatement DO...OD");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);
		
		Shape pre1Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText pre1NameText = gaService.createMultiText(pre1Shape, "{" + addedStatement.getLoopStatement().getPreCondition().getName() + "}");
		setId(pre1NameText, ID_PRE_TEXT);
		pre1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post1Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post1NameText = gaService.createMultiText(post1Shape, "{" + addedStatement.getLoopStatement().getPostCondition().getName() + "}");
		setId(post1NameText, ID_POST_TEXT);
		post1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);
		//HEADER:
		Shape variantHeader = peCreateService.createShape(outerContainerShape, false);
		Text variantNameHeader = gaService.createText(variantHeader, "variant");
		setId(variantNameHeader, ID_VARIANT_HEADER);
		variantNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		variantNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		variantNameHeader.setFont(headerFont);
		
		Shape invariantHeader = peCreateService.createShape(outerContainerShape, false);
		Text invariantNameHeader = gaService.createText(invariantHeader, "invariant");
		setId(invariantNameHeader, ID_INVARIANT_HEADER);
		invariantNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantNameHeader.setFont(headerFont);
		
		Shape guardHeader = peCreateService.createShape(outerContainerShape, false);
		Text guardNameHeader = gaService.createText(guardHeader, "guard");
		setId(guardNameHeader, ID_GUARD_HEADER);
		guardNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		guardNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		guardNameHeader.setFont(headerFont);
		
		Shape loopHeader = peCreateService.createShape(outerContainerShape, false);
		Text loopNameHeader = gaService.createText(loopHeader, "loop statement");
		setId(loopNameHeader, ID_LOOP_HEADER);
		loopNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		loopNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		loopNameHeader.setFont(headerFont);
		
		Shape postHeader = peCreateService.createShape(outerContainerShape, false);
		Text postNameHeader = gaService.createText(postHeader, "postcondition");
		setId(postNameHeader, ID_POST_HEADER);
		postNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameHeader.setFont(headerFont);
		
		Shape preHeader = peCreateService.createShape(outerContainerShape, false);
		Text preNameHeader = gaService.createText(preHeader, "precondition");
		setId(preNameHeader, ID_PRE_HEADER);
		preNameHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameHeader.setFont(headerFont);
		//lines:
		Shape ver1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1line = gaService.createPolyline(ver1Shape);
		setId(ver1line, ID_VER1_SEP);
		
		Shape ver2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2line = gaService.createPolyline(ver2Shape);
		setId(ver2line, ID_VER2_SEP);
		
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_SEP);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2line = gaService.createPolyline(hor2Shape);
		setId(hor2line, ID_HOR2_SEP);
		
		Shape hor3Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3line = gaService.createPolyline(hor3Shape);
		setId(hor3line, ID_HOR3_SEP);
		
		Shape hor4Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4line = gaService.createPolyline(hor4Shape);
		setId(hor4line, ID_HOR4_SEP);
		
		peCreateService.createChopboxAnchor(textShapeStatement1);
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		link(textShapeCondition, addedStatement.getGuard());
		link(textShapeStatement1, addedStatement.getLoopStatement());
		link(textShapeVariant, addedStatement.getVariant());
		link(textShapeInvariant, addedStatement.getInvariant());
		link(pre1Shape, addedStatement.getLoopStatement().getPreCondition());
		link(post1Shape, addedStatement.getLoopStatement().getPostCondition());
		link(proveShape, addedStatement);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int thirdWidth = mainRectangle.getWidth() / 3;
		int sizeName = 60;
		int sizeHeader = 20;
		int sizeBlock = (mainRectangle.getHeight() - sizeName) / 2 - sizeHeader;
		int positionFirstLine = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) + 20; // +20 for some space between Name and Header
		int positionSecondLine = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) / 2 + 20;
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_INVARIANT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionFirstLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionFirstLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_VARIANT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionFirstLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionSecondLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_PRE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionSecondLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_POST_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionSecondLine, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		//HEADERS:
		} else if (id.equals(ID_VARIANT_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionFirstLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_INVARIANT_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionFirstLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_GUARD_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionFirstLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_LOOP_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionSecondLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionSecondLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionSecondLine - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		//lines:
		} else if (id.equals(ID_HOR1_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionFirstLine, mainRectangle.getWidth(), positionFirstLine });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionFirstLine -sizeHeader, mainRectangle.getWidth(), positionFirstLine - sizeHeader});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR3_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionSecondLine - sizeHeader, mainRectangle.getWidth(), positionSecondLine - sizeHeader});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR4_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionSecondLine, mainRectangle.getWidth(), positionSecondLine });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER1_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth, positionFirstLine - sizeHeader, thirdWidth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth * 2, positionFirstLine - sizeHeader, thirdWidth * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} 

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_VARIANT_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Variant domainObject = (Variant) context.getDomainObject();
			if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		} else if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			SmallRepetitionStatement domainObject = (SmallRepetitionStatement) context.getDomainObject();
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
			SmallRepetitionStatement domainObject = (SmallRepetitionStatement) context.getDomainObject();
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
		if (id.equals(ID_VARIANT_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			Variant domainObject = (Variant) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		} else if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			SmallRepetitionStatement domainObject = (SmallRepetitionStatement) context.getDomainObject();
			if (checkIsProven(domainObject)) {
				domainObject.setProven(true);
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				updateParent(domainObject);
			} else {
				domainObject.setProven(false);
				domainObject.setVariantProven(false);
				rectangle.setForeground(manageColor(IColorConstant.RED));
				updateParent(domainObject);
			}
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			SmallRepetitionStatement domainObject = (SmallRepetitionStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject)) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			} 
		}
		return false;
	}
	
	private boolean checkIsProven(SmallRepetitionStatement statement) {
		AbstractStatement loopStatement = statement.getLoopStatement();
		AbstractStatement firstStatementToCheck = null;
		if (loopStatement.getRefinement() != null) {
			firstStatementToCheck = loopStatement.getRefinement();
		} else {
			firstStatementToCheck = loopStatement;
		}
		if (firstStatementToCheck.isProven() && statement.isPreProven() && statement.isPostProven() && statement.isVariantProven()) {
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

