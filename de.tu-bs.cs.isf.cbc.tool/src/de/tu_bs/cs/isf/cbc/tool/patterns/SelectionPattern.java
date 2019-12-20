package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.util.List;

import org.eclipse.emf.common.util.EList;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;

/**
 * Class that creates the graphical representation of Conditions
 * 
 * @author Tobias
 *
 */
public class SelectionPattern extends IdPattern implements IPattern {

	private static final String ID_STATEMENT_TEXT = "statementSelNameText";
	private static final String ID_CONDITION_TEXT = "conditionSelText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE_TEXT = "preText";
	private static final String ID_POST_TEXT = "postText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// HEADERS:
	private static final String ID_GUARD_HEADER = "guardHeader";
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_STATEMENT_HEADER = "statementHeader";
	private static final String ID_POST_HEADER = "postHeader";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_HOR3_LINE = "hor3Line";
	private static final String ID_HOR4_LINE = "hor4Line";
	private static final String ID_HOR5_LINE = "hor5Line";
	private static final String ID_HOR6_LINE = "hor6Line";
	private static final String ID_HOR7_LINE = "hor7Line";
	private static final String ID_HOR8_LINE = "hor8Line";
	private static final String ID_VER1_LINE = "ver1Line"; // in guards
	private static final String ID_VER2_LINE = "ver2Line"; // in pre conds
	private static final String ID_VER3_LINE = "ver3Line"; // in statements

	/**
	 * Constructor of the class
	 */
	public SelectionPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "SelectionStatement";
	}

	@Override
	public String getCreateDescription() {
		return "Create a SelectionStatement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof SelectionStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		SelectionStatement selectionStatement = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		selectionStatement.setName("selectionStatement");
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		selectionStatement.getCommands().add(statement);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("guard");
		selectionStatement.getGuards().add(condition);
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);

		addGraphicalRepresentation(context, selectionStatement);
		return new Object[] { selectionStatement };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		SelectionStatement addedStatement = (SelectionStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 200 : context.getWidth();
		int height = context.getHeight() <= 0 ? 300 : context.getHeight();
		// font:
		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);

		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		mainRectangle.setForeground(manageColor(IColorConstant.RED));
		mainRectangle.setLineWidth(2);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedStatement);

		// Statement name
		// Shape textShapeStatement = peCreateService.createShape(outerContainerShape,
		// true);
		// MultiText statementText = gaService.createMultiText(textShapeStatement, "");
		// setId(statementText, ID_STATEMENT_TEXT);
		// setIndex(statementText, 0);
		// statementText.setValue(addedStatement.getCommands().get(0).getName());
		// statementText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		// statementText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		//
		// Shape textShapeCondition = peCreateService.createShape(outerContainerShape,
		// false);
		// MultiText conditionText = gaService.createMultiText(textShapeCondition, "");
		// setId(conditionText, ID_CONDITION_TEXT);
		// setIndex(conditionText, 0);
		// conditionText.setValue(addedStatement.getGuards().get(0).getName());
		// conditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		// conditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "SelectionStatement IF..FI");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Shape preShape = peCreateService.createShape(outerContainerShape, false);
		// MultiText preNameText = gaService.createMultiText(preShape,
		// addedStatement.getCommands().get(0).getPreCondition().getName());
		// setId(preNameText, ID_PRE_TEXT);
		// setIndex(preNameText, 0);
		// preNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		// preNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		//
		// Shape postShape = peCreateService.createShape(outerContainerShape, false);
		// MultiText postNameText = gaService.createMultiText(postShape,
		// addedStatement.getCommands().get(0).getPostCondition().getName());
		// setId(postNameText, ID_POST_TEXT);
		// setIndex(postNameText, 0);
		// postNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		// postNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		// HEADERS:

		Shape guardHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text guardHeader = gaService.createText(guardHeaderShape, "guards");
		setId(guardHeader, ID_GUARD_HEADER);
		guardHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		guardHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		guardHeader.setFont(headerFont);

		Shape preHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text preHeader = Graphiti.getGaService().createText(preHeaderShape, "precondition");
		setId(preHeader, ID_PRE_HEADER);
		preHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setFont(headerFont);

		Shape statementHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text statementHeader = Graphiti.getGaService().createText(statementHeaderShape, "statements");
		setId(statementHeader, ID_STATEMENT_HEADER);
		statementHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statementHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statementHeader.setFont(headerFont);

		Shape postHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text postHeader = Graphiti.getGaService().createText(postHeaderShape, "postcondition");
		setId(postHeader, ID_POST_HEADER);
		postHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setFont(headerFont);

		// LINES:

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

		Shape hor7Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor7line = gaService.createPolyline(hor7Shape);
		setId(hor7line, ID_HOR7_LINE);

		Shape hor8Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor8line = gaService.createPolyline(hor8Shape);
		setId(hor8line, ID_HOR8_LINE);

		// peCreateService.createChopboxAnchor(textShapeStatement);
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		// link(textShapeCondition, addedStatement.getGuards().get(0));
		// link(textShapeStatement, addedStatement.getCommands().get(0));
		link(proveShape, addedStatement);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		SelectionStatement statement = (SelectionStatement) getBusinessObjectForPictogramElement(
				context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int width = mainRectangle.getWidth();
		// int fifthHeight = mainRectangle.getHeight() / 5;
		int sizeHeader = 20;
		int sizeName = 40; // name = SelectionStatement IF..FI
		int sizeBlock = (mainRectangle.getHeight() - sizeName) / 4 - sizeHeader; // Height from one Block
		int positionLine1 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName) / 4 * 4 + 20; // guard
		int positionLine2 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName) / 4 * 3 + 20; // pre
		int positionLine3 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName) / 4 * 2 + 20; // statement
		int positionLine4 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName) / 4 * 1 + 20; // post
		if (statement.getCommands().size() > 1) {
			width = width / statement.getCommands().size();
		}

		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * index, positionLine1, width, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * index, positionLine3, width, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_PRE_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * index, positionLine2, width, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
			// Header:
		} else if (id.equals(ID_POST_TEXT) && getIndex(context.getGraphicsAlgorithm()) == 0) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine4, mainRectangle.getWidth(), sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_GUARD_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1 - sizeHeader, mainRectangle.getWidth(),
					sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2 - sizeHeader, mainRectangle.getWidth(),
					sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine3 - sizeHeader, mainRectangle.getWidth(),
					sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine4 - sizeHeader, mainRectangle.getWidth(),
					sizeHeader);
			changesDone = true;
			// lines:
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1 - sizeHeader, mainRectangle.getWidth(), positionLine1 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionLine1, mainRectangle.getWidth(), positionLine1 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2 - sizeHeader, mainRectangle.getWidth(), positionLine2 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionLine2, mainRectangle.getWidth(), positionLine2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR5_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine3 - sizeHeader, mainRectangle.getWidth(), positionLine3 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR6_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionLine3, mainRectangle.getWidth(), positionLine3 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR7_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine4 - sizeHeader, mainRectangle.getWidth(), positionLine4 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR8_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionLine4, mainRectangle.getWidth(), positionLine4 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER1_LINE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			if (index > 0) {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
				List<Point> pointList = Graphiti.getGaService().createPointList(
						new int[] { width * index, positionLine1, width * index, positionLine2 - sizeHeader });
				polyline.getPoints().addAll(pointList);
			} else {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
			}
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			if (index > 0) {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
				List<Point> pointList = Graphiti.getGaService().createPointList(
						new int[] { width * index, positionLine2, width * index, positionLine3 - sizeHeader });
				polyline.getPoints().addAll(pointList);
			} else {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
			}
			changesDone = true;
		} else if (id.equals(ID_VER3_LINE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			if (index > 0) {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
				List<Point> pointList = Graphiti.getGaService().createPointList(
						new int[] { width * index, positionLine3, width * index, positionLine4 - sizeHeader });
				polyline.getPoints().addAll(pointList);
			} else {
				Polyline polyline = (Polyline) ga;
				polyline.getPoints().clear();
			}
		}
		changesDone = true;

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			SelectionStatement statement = (SelectionStatement) context.getDomainObject();
			if (containerShape.getChildren().size() - 14 != statement.getCommands().size() * 7) {
				return Reason.createTrueReason("Number of Commands and Guards differ. Expected: "
						+ statement.getCommands().size() + " " + containerShape.getChildren().size());
			}
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			if (checkIsProven(statement) && ((rectangle.getForeground() != null
					&& !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN)))
					|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!checkIsProven(statement) && ((rectangle.getForeground() != null
					&& rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN)))
					|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			SelectionStatement domainObject = (SelectionStatement) context.getDomainObject();
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
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			SelectionStatement statement = (SelectionStatement) context.getDomainObject();
			// 7 = number of graphics elements in update (vertical lines + blocks)
			// 13 = number of graphics in doAdd (horizontal lines + header + name)
			while (containerShape.getChildren().size() - 14 != statement.getCommands().size() * 7) {
				// if (containerShape.getChildren().size() - 1 != statement.getCommands().size()
				// * 4) {
				EList<AbstractStatement> childStatements = ((SelectionStatement) context.getDomainObject())
						.getCommands();
				int newIndex = (containerShape.getChildren().size() - 14) / 7;
				// int newIndex = childStatements.size() - 1;
				AbstractStatement childStatement = childStatements.get(newIndex);
				Shape shapeText = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), true);
				MultiText statementNameText = Graphiti.getGaService().createMultiText(shapeText,
						childStatement.getName());
				setId(statementNameText, ID_STATEMENT_TEXT);
				setIndex(statementNameText, newIndex);
				statementNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				statementNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				IPeCreateService peCreateService = Graphiti.getPeCreateService();
				peCreateService.createChopboxAnchor(shapeText);
				link(shapeText, childStatement);

				Shape shapeTextPre = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), false);
				MultiText preNameText = Graphiti.getGaService().createMultiText(shapeTextPre,
						"{" + childStatement.getPreCondition().getName() + "}");
				setId(preNameText, ID_PRE_TEXT);
				setIndex(preNameText, newIndex);
				preNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				preNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeTextPre, childStatement.getPreCondition());

				Shape shapeTextPost = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), false);
				MultiText postNameText = Graphiti.getGaService().createMultiText(shapeTextPost,
						"{" + childStatement.getPostCondition().getName() + "}");
				setId(postNameText, ID_POST_TEXT);
				setIndex(postNameText, newIndex);
				postNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				postNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeTextPost, childStatement.getPostCondition());

				// vertical lines, that need to be added, when ExtraSelection is used:
				Shape ver1Shape = peCreateService.createShape((ContainerShape) context.getPictogramElement(), false);
				Polyline ver1line = Graphiti.getGaService().createPolyline(ver1Shape);
				setId(ver1line, ID_VER1_LINE);
				setIndex(ver1line, newIndex);

				Shape ver2Shape = peCreateService.createShape((ContainerShape) context.getPictogramElement(), false);
				Polyline ver2line = Graphiti.getGaService().createPolyline(ver2Shape);
				setId(ver2line, ID_VER2_LINE);
				setIndex(ver2line, newIndex);

				Shape ver3Shape = peCreateService.createShape((ContainerShape) context.getPictogramElement(), false);
				Polyline ver3line = Graphiti.getGaService().createPolyline(ver3Shape);
				setId(ver3line, ID_VER3_LINE);
				setIndex(ver3line, newIndex);

				EList<Condition> childConditions = ((SelectionStatement) context.getDomainObject()).getGuards();
				Condition childCondition = childConditions.get(newIndex);

				Shape shapeTextCond = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), true);
				MultiText conditionNameText = Graphiti.getGaService().createMultiText(shapeTextCond,
						childCondition.getName());
				setId(conditionNameText, ID_CONDITION_TEXT);
				setIndex(conditionNameText, newIndex);
				conditionNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				conditionNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeTextCond, childCondition);
			}
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			if (checkIsProven(statement)) {
				statement.setProven(true);
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				updateParent(statement);
			} else {
				statement.setProven(false);
				rectangle.setForeground(manageColor(IColorConstant.RED));
				updateParent(statement);
			}
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			SelectionStatement domainObject = (SelectionStatement) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject)) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			}
		}
		return false;
	}

	private boolean checkIsProven(SelectionStatement statement) {
		boolean isProven = statement.isPreProve();
		for (AbstractStatement childStatement : statement.getCommands()) {
			AbstractStatement statementToCheck = null;
			if (childStatement.getRefinement() != null) {
				statementToCheck = childStatement.getRefinement();
			} else {
				statementToCheck = childStatement;
			}
			if (!statementToCheck.isProven()) {
				isProven = false;
			}
		}
		return isProven;

	}

	private void updateParent(AbstractStatement statement) {
		if (statement.getParent() != null) {
			IPeService pe = Graphiti.getPeService();
			EObject[] objArray = { statement.getParent() };
			Object[] obj = pe.getLinkedPictogramElements(objArray, getDiagram());
			if (obj.length > 0) {
				Shape pElement = (Shape) obj[0];
				if (pElement.getContainer() != null)
					updatePictogramElement(pElement.getContainer());
			}
		}
	}
}
