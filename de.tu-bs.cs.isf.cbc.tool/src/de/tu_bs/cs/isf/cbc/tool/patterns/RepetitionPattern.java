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
import de.tu_bs.cs.isf.cbc.cbcmodel.RepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class RepetitionPattern extends IdPattern implements IPattern {

	private static final String ID_STATEMENT1_TEXT = "statement1RepNameText";
	private static final String ID_STATEMENT2_TEXT = "statement2RepNameText";
	private static final String ID_STATEMENT3_TEXT = "statement3RepNameText";
	private static final String ID_CONDITION_TEXT = "conditionRepText";
	private static final String ID_VARIANT_TEXT = "variantRepText";
	private static final String ID_INVARIANT_TEXT = "invariantRepText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE1_TEXT = "pre1NameText";
	private static final String ID_POST1_TEXT = "post1NameText";
	private static final String ID_PRE2_TEXT = "pre2NameText";
	private static final String ID_POST2_TEXT = "post2NameText";
	private static final String ID_PRE3_TEXT = "pre3NameText";
	private static final String ID_POST3_TEXT = "post3NameText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	//Headers:
	private static final String ID_INVARIANT_HEADER = "invariantHeader";
	private static final String ID_GUARD_HEADER = "guardHeader";
	private static final String ID_VARIANT_HEADER = "variantHeader";
	private static final String ID_PRE_1_HEADER = "pre1Header";
	private static final String ID_PRE_2_HEADER = "pre2Header";
	private static final String ID_PRE_3_HEADER = "pre3Header";
	private static final String ID_START_HEADER = "startHeader";
	private static final String ID_LOOP_HEADER = "loopHeader";
	private static final String ID_END_HEADER = "endHeader";
	private static final String ID_POST_1_HEADER = "post1Header";
	private static final String ID_POST_2_HEADER = "post2Header";
	private static final String ID_POST_3_HEADER = "post3Header";
	//lines:
	private static final String ID_HOR1_SEP = "hor1SEP"; //HOR = horizontal, 1 is most up line, 8 is most bottom line
	private static final String ID_HOR2_SEP = "hor2SEP";
	private static final String ID_HOR3_SEP = "hor3SEP";
	private static final String ID_HOR4_SEP = "hor4SEP";
	private static final String ID_HOR5_SEP = "hor5SEP";
	private static final String ID_HOR6_SEP = "hor6SEP";
	private static final String ID_HOR7_SEP = "hor7SEP";
	private static final String ID_HOR8_SEP = "hor8SEP";
	private static final String ID_VER1_SEP = "ver1SEP"; //ver = vertical
	private static final String ID_VER2_SEP = "ver2SEP";

	/**
	 * Constructor of the class
	 */
	public RepetitionPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "RepetitionStatement (big)";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a RepetitionStatement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof RepetitionStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		RepetitionStatement repetitionStatement = CbcmodelFactory.eINSTANCE.createRepetitionStatement();
		repetitionStatement.setName("repetitionStatement");
		AbstractStatement statement1 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement1.setName("start");
		repetitionStatement.setStartStatement(statement1);
		AbstractStatement statement2 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement2.setName("loop");
		repetitionStatement.setLoopStatement(statement2);
		AbstractStatement statement3 = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement3.setName("end");
		repetitionStatement.setEndStatement(statement3);
		Condition condition = CbcmodelFactory.eINSTANCE.createCondition();
		condition.setName("guard");
		repetitionStatement.setGuard(condition);
		Condition invariant = CbcmodelFactory.eINSTANCE.createCondition();
		invariant.setName("invariant");
		repetitionStatement.setInvariant(invariant);
		Variant variant = CbcmodelFactory.eINSTANCE.createVariant();
		variant.setName("variant");
		repetitionStatement.setVariant(variant);
		
		Condition pre1 = CbcmodelFactory.eINSTANCE.createCondition();
		pre1.setName("{}");
		statement1.setPreCondition(pre1);
		Condition post1 = CbcmodelFactory.eINSTANCE.createCondition();
		post1.setName("{}");
		statement1.setPostCondition(post1);
		Condition pre2 = CbcmodelFactory.eINSTANCE.createCondition();
		pre2.setName("{}");
		statement2.setPreCondition(pre2);
		Condition post2 = CbcmodelFactory.eINSTANCE.createCondition();
		post2.setName("{}");
		statement2.setPostCondition(post2);
		Condition pre3 = CbcmodelFactory.eINSTANCE.createCondition();
		pre3.setName("{}");
		statement3.setPreCondition(pre3);
		Condition post3 = CbcmodelFactory.eINSTANCE.createCondition();
		post3.setName("{}");
		statement3.setPostCondition(post3);
		
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
		RepetitionStatement addedStatement = (RepetitionStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 300 : context.getWidth();
        int height = context.getHeight() <= 0 ? 300 : context.getHeight();
        //font
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
		setId(statement1Text, ID_STATEMENT1_TEXT);
		statement1Text.setValue(addedStatement.getStartStatement().getName());
		statement1Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement1Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeStatement2 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement2Text = gaService.createMultiText(textShapeStatement2, "");
		setId(statement2Text, ID_STATEMENT2_TEXT);
		statement2Text.setValue(addedStatement.getLoopStatement().getName());
		statement2Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement2Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape textShapeStatement3 = peCreateService.createShape(outerContainerShape, true);
		MultiText statement3Text = gaService.createMultiText(textShapeStatement3, "");
		setId(statement3Text, ID_STATEMENT3_TEXT);
		statement3Text.setValue(addedStatement.getEndStatement().getName());
		statement3Text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statement3Text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
        
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
		MultiText pre1NameText = gaService.createMultiText(pre1Shape, addedStatement.getStartStatement().getPreCondition().getName());
		setId(pre1NameText, ID_PRE1_TEXT);
		pre1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post1Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post1NameText = gaService.createMultiText(post1Shape, addedStatement.getStartStatement().getPostCondition().getName());
		setId(post1NameText, ID_POST1_TEXT);
		post1NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post1NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape pre2Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText pre2NameText = gaService.createMultiText(pre2Shape, addedStatement.getLoopStatement().getPreCondition().getName());
		setId(pre2NameText, ID_PRE2_TEXT);
		pre2NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre2NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post2Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post2NameText = gaService.createMultiText(post2Shape, addedStatement.getLoopStatement().getPostCondition().getName());
		setId(post2NameText, ID_POST2_TEXT);
		post2NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post2NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape pre3Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText pre3NameText = gaService.createMultiText(pre3Shape, addedStatement.getEndStatement().getPreCondition().getName());
		setId(pre3NameText, ID_PRE3_TEXT);
		pre3NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre3NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape post3Shape = peCreateService.createShape(outerContainerShape, false);
		MultiText post3NameText = gaService.createMultiText(post3Shape, addedStatement.getEndStatement().getPostCondition().getName());
		setId(post3NameText, ID_POST3_TEXT);
		post3NameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post3NameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);
		
		//HEADER:
		Shape headerShapeInvariant = peCreateService.createShape(outerContainerShape, false);
		Text invariantHeader = gaService.createText(headerShapeInvariant, "invariant");
		setId(invariantHeader, ID_INVARIANT_HEADER);
		invariantHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantHeader.setFont(headerFont);
		
		Shape headerShapeGuard = peCreateService.createShape(outerContainerShape, false);
		Text guardHeader = gaService.createText(headerShapeGuard, "guard");
		setId(guardHeader, ID_GUARD_HEADER);
		guardHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		guardHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		guardHeader.setFont(headerFont);
		
		Shape headerShapeVariant = peCreateService.createShape(outerContainerShape, false);
		Text VariantHeader = gaService.createText(headerShapeVariant, "variant");
		setId(VariantHeader, ID_VARIANT_HEADER);
		VariantHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		VariantHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		VariantHeader.setFont(headerFont);
		
		Shape headerShapePre1 = peCreateService.createShape(outerContainerShape, false);
		Text pre1Header = gaService.createText(headerShapePre1, "precondition 1");
		setId(pre1Header, ID_PRE_1_HEADER);
		pre1Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		pre1Header.setFont(headerFont);
		
		Shape headerShapePre2 = peCreateService.createShape(outerContainerShape, false);
		Text pre2Header = gaService.createText(headerShapePre2, "precondition 2");
		setId(pre2Header, ID_PRE_2_HEADER);
		pre2Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre2Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		pre2Header.setFont(headerFont);
		
		Shape headerShapePre3 = peCreateService.createShape(outerContainerShape, false);
		Text pre3Header = gaService.createText(headerShapePre3, "precondondition 3");
		setId(pre3Header, ID_PRE_3_HEADER);
		pre3Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		pre3Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		pre3Header.setFont(headerFont);
		
		Shape headerShapeStart = peCreateService.createShape(outerContainerShape, false);
		Text startHeader = gaService.createText(headerShapeStart, "start statement");
		setId(startHeader, ID_START_HEADER);
		startHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		startHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		startHeader.setFont(headerFont);
		
		Shape headerShapeLoop = peCreateService.createShape(outerContainerShape, false);
		Text loopHeader = gaService.createText(headerShapeLoop, "loop statement");
		setId(loopHeader, ID_LOOP_HEADER);
		loopHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		loopHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		loopHeader.setFont(headerFont);
		
		Shape headerShapeEnd = peCreateService.createShape(outerContainerShape, false);
		Text endHeader = gaService.createText(headerShapeEnd, "end statement");
		setId(endHeader, ID_END_HEADER);
		endHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		endHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		endHeader.setFont(headerFont);
		
		Shape headerShapePost1 = peCreateService.createShape(outerContainerShape, false);
		Text post1Header = gaService.createText(headerShapePost1, "postcondition 1");
		setId(post1Header, ID_POST_1_HEADER);
		post1Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post1Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		post1Header.setFont(headerFont);
		
		Shape headerShapePost2 = peCreateService.createShape(outerContainerShape, false);
		Text post2Header = gaService.createText(headerShapePost2, "postcondition 2");
		setId(post2Header, ID_POST_2_HEADER);
		post2Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post2Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		post2Header.setFont(headerFont);
		
		Shape headerShapePost3 = peCreateService.createShape(outerContainerShape, false);
		Text post3Header = gaService.createText(headerShapePost3, "precondition 3");
		setId(post3Header, ID_POST_3_HEADER);
		post3Header.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		post3Header.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		post3Header.setFont(headerFont);
		//lines:
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Line = gaService.createPolyline(hor1Shape);
		setId(hor1Line, ID_HOR1_SEP);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Line = gaService.createPolyline(hor2Shape);
		setId(hor2Line, ID_HOR2_SEP);
		
		Shape hor3Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3Line = gaService.createPolyline(hor3Shape);
		setId(hor3Line, ID_HOR3_SEP);
		
		Shape hor4Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4Line = gaService.createPolyline(hor4Shape);
		setId(hor4Line, ID_HOR4_SEP);
		
		Shape hor5Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor5Line = gaService.createPolyline(hor5Shape);
		setId(hor5Line, ID_HOR5_SEP);
		
		Shape hor6Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor6Line = gaService.createPolyline(hor6Shape);
		setId(hor6Line, ID_HOR6_SEP);
		
		Shape hor7Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor7Line = gaService.createPolyline(hor7Shape);
		setId(hor7Line, ID_HOR7_SEP);
		
		Shape hor8Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor8Line = gaService.createPolyline(hor8Shape);
		setId(hor8Line, ID_HOR8_SEP);
		
		Shape ver1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1Line = gaService.createPolyline(ver1Shape);
		setId(ver1Line, ID_VER1_SEP);
		
		Shape ver2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2Line = gaService.createPolyline(ver2Shape);
		setId(ver2Line, ID_VER2_SEP);
		
		peCreateService.createChopboxAnchor(textShapeStatement1);
		peCreateService.createChopboxAnchor(textShapeStatement2);
		peCreateService.createChopboxAnchor(textShapeStatement3);
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		link(textShapeCondition, addedStatement.getGuard());
		link(textShapeStatement1, addedStatement.getStartStatement());
		link(textShapeStatement2, addedStatement.getLoopStatement());
		link(textShapeStatement3, addedStatement.getEndStatement());
		link(textShapeVariant, addedStatement.getVariant());
		link(textShapeInvariant, addedStatement.getInvariant());
		link(pre1Shape, addedStatement.getStartStatement().getPreCondition());
		link(post1Shape, addedStatement.getStartStatement().getPostCondition());
		link(pre2Shape, addedStatement.getLoopStatement().getPreCondition());
		link(post2Shape, addedStatement.getLoopStatement().getPostCondition());
		link(pre3Shape, addedStatement.getEndStatement().getPreCondition());
		link(post3Shape, addedStatement.getEndStatement().getPostCondition());
		link(proveShape, addedStatement);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int thirdWidth = mainRectangle.getWidth() / 3;
		int sizeHeader = 20;
		int sizeName = 60; //size taken for the statement name (repetitionstatement do..od)
		int sizeBlock = (mainRectangle.getHeight() - sizeName) / 4 - sizeHeader; // of one block
		int positionLine1 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) /4 * 4 + 20; //invariant,, guard, variant; + 20 for some space between header and Name
		int positionLine2 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) / 4 * 3 + 20; //pre1, pre2, pre3
		int positionLine3 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) / 4 * 2 + 20; //start, loop, end
		int positionLine4 = mainRectangle.getHeight() - (mainRectangle.getHeight() - sizeName ) / 4 * 1 + 20; //post1, post2, post3
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_INVARIANT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine1, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_VARIANT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine1, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine3, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine3, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT3_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine3, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_PRE1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_POST1_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine4, thirdWidth, sizeBlock);
			changesDone = true;
		}  else if (id.equals(ID_PRE2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_POST2_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine4, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_PRE3_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_POST3_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine4, thirdWidth, sizeBlock);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		//HEADERS:
		} else if (id.equals(ID_INVARIANT_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine1 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_GUARD_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine1 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_VARIANT_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine1 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_PRE_1_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine2 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_PRE_2_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine2 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_PRE_3_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine2 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_START_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine3 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_LOOP_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine3 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_END_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine3 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_1_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionLine4 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_2_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth, positionLine4 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_3_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, thirdWidth * 2, positionLine4 - sizeHeader, thirdWidth, sizeHeader);
			changesDone = true;
		//LINES:
		} else if(id.equals(ID_HOR1_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1 - sizeHeader, mainRectangle.getWidth(), positionLine1 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR2_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine1, mainRectangle.getWidth(), positionLine1 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR3_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2 - sizeHeader, mainRectangle.getWidth(), positionLine2 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR4_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine2, mainRectangle.getWidth(), positionLine2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR5_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine3 - sizeHeader, mainRectangle.getWidth(), positionLine3 - sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR6_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine3, mainRectangle.getWidth(), positionLine3 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR7_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine4 - sizeHeader, mainRectangle.getWidth(), positionLine4- sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_HOR8_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionLine4, mainRectangle.getWidth(), positionLine4 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER1_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth, positionLine1 - sizeHeader, thirdWidth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if(id.equals(ID_VER2_SEP)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { thirdWidth * 2, positionLine1 - sizeHeader, thirdWidth * 2, mainRectangle.getHeight() });
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
			RepetitionStatement domainObject = (RepetitionStatement) context.getDomainObject();
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
			RepetitionStatement domainObject = (RepetitionStatement) context.getDomainObject();
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
			RepetitionStatement domainObject = (RepetitionStatement) context.getDomainObject();
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
			RepetitionStatement domainObject = (RepetitionStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (checkIsProven(domainObject)) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			} 
		}
		return false;
	}
	
	private boolean checkIsProven(RepetitionStatement statement) {
		AbstractStatement firstStatement = statement.getStartStatement();
		AbstractStatement secondStatement = statement.getLoopStatement();
		AbstractStatement thirdStatement = statement.getEndStatement();
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
		if (firstStatementToCheck.isProven() && secondStatementToCheck.isProven() && thirdStatementToCheck.isProven() && statement.isVariantProven()) {
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

