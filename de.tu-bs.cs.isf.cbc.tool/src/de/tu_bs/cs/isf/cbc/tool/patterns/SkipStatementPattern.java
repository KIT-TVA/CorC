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
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;
import de.tu_bs.cs.isf.cbc.tool.helper.HighlightHelper;

/**
 * Class that creates the graphical representation of Conditions
 * @author Tobias
 *
 */
public class SkipStatementPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "statementNameText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_PRE_TEXT = "preNameText";
	private static final String ID_POST_TEXT = "postNameText";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	//Headers:
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_POST_HEADER = "postHeader";
	//lines:
	private static final String ID_SEP_LEFT = "sepLeft";
	private static final String ID_SEP_RIGHT = "sepRight";
	private static final String ID_SEP_HEADER1 = "sepHeader1";
	private static final String ID_SEP_HEADER2 = "sepHeader";


	/**
	 * Constructor of the class
	 */
	public SkipStatementPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "SkipStatement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a SkipStatement.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof SkipStatement;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		SkipStatement statement = CbcmodelFactory.eINSTANCE.createSkipStatement();
		statement.setName(";");
		Condition pre = CbcmodelFactory.eINSTANCE.createCondition();
		pre.setName("");
		statement.setPreCondition(pre);
		Condition post = CbcmodelFactory.eINSTANCE.createCondition();
		post.setName("");
		statement.setPostCondition(post);
		
		addGraphicalRepresentation(context, statement);
		return new Object[] { statement };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		SkipStatement addedStatement = (SkipStatement) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 300 : context.getWidth();
        int height = context.getHeight() <= 0 ? 150 : context.getHeight();
        
        //Font for the Header:
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
		Shape textShape = peCreateService.createShape(outerContainerShape, false);
		MultiText statementNameText = gaService.createMultiText(textShape, "");
		setId(statementNameText, ID_NAME_TEXT);
		statementNameText.setValue("Skip");
		statementNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statementNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		statementNameText.setFont(headerFont);
		
		Shape preShape = peCreateService.createShape(outerContainerShape, false);
		MultiText preNameText = gaService.createMultiText(preShape, "{" + addedStatement.getPreCondition().getName() + "}");
		setId(preNameText, ID_PRE_TEXT);
		preNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		Shape postShape = peCreateService.createShape(outerContainerShape, false);
		MultiText postNameText = gaService.createMultiText(postShape, "{" + addedStatement.getPostCondition().getName() + "}");
		setId(postNameText, ID_POST_TEXT);
		postNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);
		
		//Header: 
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
		Shape leftSepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polylineLeft = gaService.createPolyline(leftSepShape);
		setId(polylineLeft, ID_SEP_LEFT);
		
		Shape rightSepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polylineRight = gaService.createPolyline(rightSepShape);
		setId(polylineRight, ID_SEP_RIGHT);
		
		Shape header1SepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline header1line = gaService.createPolyline(header1SepShape);
		setId(header1line, ID_SEP_HEADER1);
		
		Shape header2SepShape = peCreateService.createShape(outerContainerShape, false);
		Polyline header2line = gaService.createPolyline(header2SepShape);
		setId(header2line, ID_SEP_HEADER2);
				
		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedStatement);
		link(textShape, addedStatement);
		link(preShape, addedStatement.getPreCondition());
		link(postShape, addedStatement.getPostCondition());
		link(proveShape, addedStatement);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int halfWidth = mainRectangle.getWidth() / 2;
		//stable sizes from Name and Header save space when the diagram gets big!
		int sizeName = 30; //size from Name block
		int sizeHeader = 20; //size from the Header
		int positionHeader = 40; //position where the Header is
		int sizeText = mainRectangle.getHeight() - positionHeader - sizeName;  //size from the blocks (pre, statement, post)
		int positionText = positionHeader + sizeName; //position from the blocks (pre, statement, post)
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_PRE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionText, halfWidth, sizeText);
			changesDone = true;
		} else if (id.equals(ID_POST_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionText, halfWidth, sizeText);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionHeader, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, halfWidth, positionHeader, halfWidth, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_SEP_HEADER1)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionHeader, mainRectangle.getWidth(), positionHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_SEP_HEADER2)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, positionHeader + sizeHeader, mainRectangle.getWidth(), positionHeader + sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_SEP_LEFT)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { halfWidth, positionHeader, halfWidth, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_SEP_RIGHT)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { halfWidth * 2, positionHeader, halfWidth * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			if (domainObject.isProven() && 
					((rectangle.getForeground() != null && !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!domainObject.isProven() && 
					((rectangle.getForeground() != null && rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN))) 
							|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			} 
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (domainObject.isProven() && image.getId().equals(CbCImageProvider.IMG_UNPROVEN)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!domainObject.isProven() && image.getId().equals(CbCImageProvider.IMG_PROVEN)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			} 
		}
		if(HighlightHelper.instance.needsInitialHighlightUpdate(context)) {
			return Reason.createTrueReason("Element needs to be highlighted.");
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			if (domainObject.isProven()) {
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				if(domainObject.getParent()!= null) {
					IPeService pe = Graphiti.getPeService();
					EObject[] objArray = {domainObject.getParent()};
					Object[] obj =  pe.getLinkedPictogramElements(objArray, getDiagram());
					Shape pElement = (Shape) obj[0];
					if (pElement.getContainer() != null) updatePictogramElement(pElement.getContainer());
				}
			} else {
				rectangle.setForeground(manageColor(IColorConstant.RED));
				if(domainObject.getParent()!= null) {
					IPeService pe = Graphiti.getPeService();
					EObject[] objArray = {domainObject.getParent()};
					Object[] obj =  pe.getLinkedPictogramElements(objArray, getDiagram());
					Shape pElement = (Shape) obj[0];
					if (pElement.getContainer() != null) updatePictogramElement(pElement.getContainer());
				}
			}
			HighlightHelper.instance.handleHighlightDrawing(context, rectangle);
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			AbstractStatement domainObject = (AbstractStatement) context.getDomainObject();
			 Image image = (Image) context.getGraphicsAlgorithm();
			if (domainObject.isProven()) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			} 
		}
		return false;
	}
}
