package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements;
import de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

public class MethodRefinementsPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "MethodRefinementsName";
	private static final String ID_FOPREFINEMENT = "refinementName";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Header";

	private static final String HEADER_FOPREFINEMENT_NAME = "MethodRefinements";

	/**
	 * Constructor of the class
	 */
	public MethodRefinementsPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "MethodRefinements";
	}

	@Override
	public String getCreateDescription() {
		return "Create a list of method refinements.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof MethodRefinements;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		MethodRefinements methodRefinement = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof MethodRefinements) {
				methodRefinement = (MethodRefinements) obj;
			}
		}
		if (methodRefinement != null)
			return false;
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		MethodRefinements methodRefinement = CbcmodelFactory.eINSTANCE.createMethodRefinements();
		ProductVariant variant = CbcmodelFactory.eINSTANCE.createProductVariant();
		variant.setRefinementChain("Example.example");
		methodRefinement.getProductvariants().add(variant);

		try {
			CbcModelUtil.saveMethodRefinementsToModelFile(methodRefinement, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}

		addGraphicalRepresentation(context, methodRefinement);
		return new Object[] { methodRefinement };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		MethodRefinements addedMethodRefinements = (MethodRefinements) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 400 : context.getWidth();
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
		link(outerContainerShape, addedMethodRefinements);

		// header
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text methodRefinementsNameText = gaService.createText(nameTextShape, HEADER_FOPREFINEMENT_NAME);
		setId(methodRefinementsNameText, ID_NAME_TEXT);
		methodRefinementsNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		methodRefinementsNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		methodRefinementsNameText.setFont(headerFont);

		// horizontal line
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);

		// create links and wire it
		link(outerContainerShape, addedMethodRefinements);
		link(nameTextShape, addedMethodRefinements);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		MethodRefinements methodRefinement = (MethodRefinements) getBusinessObjectForPictogramElement(
				context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (methodRefinement.getProductvariants().size() >= 1) {
			height = height / (methodRefinement.getProductvariants().size() + 1);
		}

		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_FOPREFINEMENT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 1), mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, height, mainRectangle.getWidth(), height });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			MethodRefinements methodRefinement = (MethodRefinements) context.getDomainObject();
			if (containerShape.getChildren().size() - 2 != methodRefinement.getProductvariants().size()) {
				return Reason.createTrueReason(
						"Number of Variables differ. Expected: " + methodRefinement.getProductvariants().size() + " "
								+ (containerShape.getChildren().size() - 2));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<ProductVariant> variants = ((MethodRefinements) context.getDomainObject()).getProductvariants();
			while (((ContainerShape) context.getPictogramElement()).getChildren().size() - 2 < variants.size()) {
				int newIndex = ((ContainerShape) context.getPictogramElement()).getChildren().size() - 2;
				ProductVariant variant = variants.get(newIndex);
				Shape shapeText = Graphiti.getPeCreateService()
						.createShape((ContainerShape) context.getPictogramElement(), true);
				Text variantNameText = Graphiti.getGaService().createText(shapeText, variant.getRefinementChain());
				setId(variantNameText, ID_FOPREFINEMENT);
				setIndex(variantNameText, newIndex);
				variantNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				variantNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeText, variant);
			}
			return true;
		}
		return false;
	}
}
