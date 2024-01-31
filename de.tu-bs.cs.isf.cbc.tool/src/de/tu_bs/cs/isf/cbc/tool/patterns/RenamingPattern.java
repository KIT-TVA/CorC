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
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;

/**
 * Class that creates the graphical representation for renaming
 * @author Tobias
 *
 */
public class RenamingPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "RenamingName";
	private static final String ID_RENAME_FUNCTION = "functionName";
	private static final String ID_RENAME_TYPE = "functionType";
	private static final String ID_RENAME_NEW = "newFunctionName";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	//Header:
	private static final String ID_TYPE_HEADER = "typeHeader";
	private static final String ID_DESTINATION_HEADER = "destinationHeader";
	private static final String ID_RENAMED_HEADER = "renamedHeader"; //the name that is used in the diagram
	//lines:
	private static final String ID_HOR1_LINE = "hor1Header";
	private static final String ID_HOR2_LINE = "hor2Header";
	private static final String ID_VER1_LINE = "ver1Header";
	private static final String ID_VER2_LINE = "ver2Header";

	/**
	 * Constructor of the class
	 */
	public RenamingPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Renaming";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a list of function renaming.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Renaming;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		Renaming renaming = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			}
		}
		if (renaming != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		Renaming renaming = CbcmodelFactory.eINSTANCE.createRenaming();
		Rename rename = CbcmodelFactory.eINSTANCE.createRename();
		rename.setType("pred");
		rename.setFunction("Example.example");
		rename.setNewName("exp");
		renaming.getRename().add(rename);
		
		try {
			CbcModelUtil.saveRenamingToModelFile(renaming, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, renaming);
		return new Object[] { renaming };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		Renaming addedRenaming = (Renaming) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 400 : context.getWidth();
        int height = context.getHeight() <= 0 ? 150 : context.getHeight();
        
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
        link(outerContainerShape, addedRenaming);

		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text renamingNameText = gaService.createText(nameTextShape, "Renaming");
		setId(renamingNameText, ID_NAME_TEXT);
		renamingNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		renamingNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		renamingNameText.setFont(headerFont);
		
		//header:
		Shape typeHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text typeHeader = gaService.createText(typeHeaderShape, "data type");
		setId(typeHeader, ID_TYPE_HEADER);
		typeHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		typeHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		typeHeader.setFont(headerFont);
		
		Shape destinationHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text destinationHeader = gaService.createText(destinationHeaderShape, "original name");
		setId(destinationHeader, ID_DESTINATION_HEADER);
		destinationHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		destinationHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		destinationHeader.setFont(headerFont);
		
		Shape remanedHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text remanedHeader = gaService.createText(remanedHeaderShape, "renamed");
		setId(remanedHeader, ID_RENAMED_HEADER);
		remanedHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		remanedHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);	
		remanedHeader.setFont(headerFont);
		
		//lines:	
		Shape hor1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1line = gaService.createPolyline(hor1Shape);
		setId(hor1line, ID_HOR1_LINE);
		
		Shape hor2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2line = gaService.createPolyline(hor2Shape);
		setId(hor2line, ID_HOR2_LINE);
		
		Shape ver1Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1line = Graphiti.getGaService().createPolyline(ver1Shape);
		setId(ver1line, ID_VER1_LINE);
		
		Shape ver2Shape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2line = Graphiti.getGaService().createPolyline(ver2Shape);
		setId(ver2line, ID_VER2_LINE);
		
		link(outerContainerShape, addedRenaming);
		link(nameTextShape, addedRenaming);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		Renaming renaming = (Renaming) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (renaming.getRename().size() >= 1) {
			height = height / (renaming.getRename().size() + 2);
		}
		int width = mainRectangle.getWidth() / 3;
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_FUNCTION)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_TYPE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_NEW)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * 2, height * (index + 2), width, height);
			changesDone = true;
		} else if (id.equals(ID_TYPE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_DESTINATION_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, width, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_RENAMED_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, width * 2, height, width, height);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height, mainRectangle.getWidth(), height});
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height * 2, mainRectangle.getWidth(), height * 2 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;	
		} else if (id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { width, height, width, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { width * 2, height, width * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			Renaming renaming = (Renaming) context.getDomainObject();
			if (containerShape.getChildren().size() - 8 != renaming.getRename().size() * 3) {
				return Reason.createTrueReason("Number of function renaming differ. Expected: " + renaming.getRename().size() 
						+ " " + (containerShape.getChildren().size() - 8));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<Rename> renames = ((Renaming) context.getDomainObject()).getRename();
			while ((((ContainerShape) context.getPictogramElement()).getChildren().size() - 8) / 3 < renames.size()) {
				int newIndex = (((ContainerShape) context.getPictogramElement()).getChildren().size() - 8) / 3;
				Rename rename = renames.get(newIndex);
				Shape shapeFunctionText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text functionNameText = Graphiti.getGaService().createText(shapeFunctionText, rename.getFunction());
				setId(functionNameText, ID_RENAME_FUNCTION);
				setIndex(functionNameText, newIndex);
				functionNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				functionNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeFunctionText, rename);
				
				Shape shapeTypeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text functionTypeText = Graphiti.getGaService().createText(shapeTypeText, rename.getType());
				setId(functionTypeText, ID_RENAME_TYPE);
				setIndex(functionTypeText, newIndex);
				functionTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				functionTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeTypeText, rename);
				
				Shape shapeNewNameText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text functionNewNameText = Graphiti.getGaService().createText(shapeNewNameText, rename.getNewName());
				setId(functionNewNameText, ID_RENAME_NEW);
				setIndex(functionNewNameText, newIndex);
				functionNewNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				functionNewNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeNewNameText, rename);
				
			}
			return true;
		}
		return false;
	}
}

