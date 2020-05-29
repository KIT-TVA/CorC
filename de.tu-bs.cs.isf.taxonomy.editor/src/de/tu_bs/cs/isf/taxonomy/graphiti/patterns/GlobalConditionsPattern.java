package de.tu_bs.cs.isf.taxonomy.graphiti.patterns;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
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
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Condition;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Class that creates the graphical representation of global conditions
 * @author Tobias
 *
 */
public class GlobalConditionsPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "globalConditionsName";
	private static final String ID_CONDITION_TEXT = "globalcondition";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";


	/**
	 * Constructor of the class
	 */
	public GlobalConditionsPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Global Conditions";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a list of global conditions.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof GlobalConditions;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		GlobalConditions conds = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof GlobalConditions) {
				conds = (GlobalConditions) obj;
			}
		}
		if (conds != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		GlobalConditions conditions = TaxonomyFactory.eINSTANCE.createGlobalConditions();
		Condition condition = TaxonomyFactory.eINSTANCE.createCondition();
		condition.setName("{}");
		conditions.getConditions().add(condition);
		
		try {
			TaxonomyModelUtil.saveGlobalConditionsToModelFile(conditions, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, conditions);
		return new Object[] { conditions };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		GlobalConditions addedGlobalConditions = (GlobalConditions) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 200 : context.getWidth();
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
        link(outerContainerShape, addedGlobalConditions);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text conditionsNameText = gaService.createText(nameTextShape, "Global Conditions");
		setId(conditionsNameText, ID_NAME_TEXT);
		conditionsNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		conditionsNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		link(outerContainerShape, addedGlobalConditions);
		link(nameTextShape, addedGlobalConditions);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GlobalConditions conditions = (GlobalConditions) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (conditions.getConditions().size() >= 1) {
			height = height / (conditions.getConditions().size() + 1);
		}
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 0, height * (index + 1), mainRectangle.getWidth(), height);
			changesDone = true;
		}

		return changesDone;
	}
	
	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			GlobalConditions conditions = (GlobalConditions) context.getDomainObject();
			if (containerShape.getChildren().size() - 1 != conditions.getConditions().size()) {
				return Reason.createTrueReason("Number of Conditions differ. Expected: " + conditions.getConditions().size() 
						+ " " + (containerShape.getChildren().size() - 1));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<Condition> conditions = ((GlobalConditions) context.getDomainObject()).getConditions();
			int newIndex = conditions.size() - 1;
			Condition condition = conditions.get(newIndex);
			Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
			MultiText conditionNameText = Graphiti.getGaService().createMultiText(shapeText, condition.getName());
			setId(conditionNameText, ID_CONDITION_TEXT);
			setIndex(conditionNameText, newIndex);
			conditionNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			conditionNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeText, condition);
			
			return true;
		}
		return false;
	}
}

