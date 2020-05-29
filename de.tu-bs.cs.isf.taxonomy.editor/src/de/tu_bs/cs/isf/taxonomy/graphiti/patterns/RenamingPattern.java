package de.tu_bs.cs.isf.taxonomy.graphiti.patterns;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
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

import de.tu_bs.cs.isf.taxonomy.graphiti.model.TaxonomyModelUtil;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Rename;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

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
	private static final String ID_RENAME_ARROW = "arrow";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";


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
		Renaming renaming = TaxonomyFactory.eINSTANCE.createRenaming();
		Rename rename = TaxonomyFactory.eINSTANCE.createRename();
		rename.setType("boolean");
		rename.setFunction("Example.example");
		rename.setNewName("exp");
		renaming.getRenames().add(rename);
		
		try {
			TaxonomyModelUtil.saveRenamingToModelFile(renaming, getDiagram());
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

		int width = context.getWidth() <= 0 ? 300 : context.getWidth();
        int height = context.getHeight() <= 0 ? 100 : context.getHeight();
        
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
		if (renaming.getRenames().size() >= 1) {
			height = height / (renaming.getRenames().size() + 1);
		}
		int width = mainRectangle.getWidth() / 13;
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_FUNCTION)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * 3, height * (index + 1), width * 6, height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_TYPE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 1), width * 3, height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_NEW)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * 10, height * (index + 1), width * 3, height);
			changesDone = true;
		} else if (id.equals(ID_RENAME_ARROW)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, width * 9, height * (index + 1), width, height);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			Renaming renaming = (Renaming) context.getDomainObject();
			if (containerShape.getChildren().size() - 1 != renaming.getRenames().size() * 4) {
				return Reason.createTrueReason("Number of function renaming differ. Expected: " + renaming.getRenames().size() 
						+ " " + (containerShape.getChildren().size() - 1));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<Rename> renames = ((Renaming) context.getDomainObject()).getRenames();
			int newIndex = renames.size() - 1;
			Rename rename = renames.get(newIndex);
			Shape shapeFunctionText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
			Text functionNameText = Graphiti.getGaService().createText(shapeFunctionText, rename.getFunction());
			setId(functionNameText, ID_RENAME_FUNCTION);
			setIndex(functionNameText, newIndex);
			functionNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			functionNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeFunctionText, rename);
			
			Shape shapeTypeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), false);
			Text functionTypeText = Graphiti.getGaService().createText(shapeTypeText, rename.getType());
			setId(functionTypeText, ID_RENAME_TYPE);
			setIndex(functionTypeText, newIndex);
			functionTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			functionTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeTypeText, rename);
			
			Shape shapeNewNameText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), false);
			Text functionNewNameText = Graphiti.getGaService().createText(shapeNewNameText, rename.getNewName());
			setId(functionNewNameText, ID_RENAME_NEW);
			setIndex(functionNewNameText, newIndex);
			functionNewNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			functionNewNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeNewNameText, rename);
			
			Shape shapeArrow = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), false);
			Text arrowText = Graphiti.getGaService().createText(shapeArrow, "->");
			setId(arrowText, ID_RENAME_ARROW);
			setIndex(arrowText, newIndex);
			arrowText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			arrowText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeArrow, rename);
			
			return true;
		}
		return false;
	}
}

