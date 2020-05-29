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
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariable;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Class that creates the graphical representation of Methods
 * @author Tobias
 *
 */
public class VariablesPattern extends IdPattern implements IPattern {

	private static final String ID_NAME_TEXT = "variablesName";
	private static final String ID_VARIABLE_TEXT = "variable";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";


	/**
	 * Constructor of the class
	 */
	public VariablesPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Variables";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create a list of variables.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof JavaVariables;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		JavaVariables vars = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			}
		}
		if (vars != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		JavaVariables variables = TaxonomyFactory.eINSTANCE.createJavaVariables();
		JavaVariable variable = TaxonomyFactory.eINSTANCE.createJavaVariable();
		variable.setName("int a");
		variables.getVariables().add(variable);
		
		try {
			TaxonomyModelUtil.saveVariablesToModelFile(variables, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, variables);
		return new Object[] { variables };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		JavaVariables addedVariables = (JavaVariables) context.getNewObject();
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
        link(outerContainerShape, addedVariables);

		// method name
		Shape nameTextShape = peCreateService.createShape(outerContainerShape, false);
		Text variablesNameText = gaService.createText(nameTextShape, "Variables");
		setId(variablesNameText, ID_NAME_TEXT);
		variablesNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		variablesNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		link(outerContainerShape, addedVariables);
		link(nameTextShape, addedVariables);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		JavaVariables variables = (JavaVariables) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int height = mainRectangle.getHeight();
		if (variables.getVariables().size() >= 1) {
			height = height / (variables.getVariables().size() + 1);
		}
		
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 0, mainRectangle.getWidth(), height);
			changesDone = true;
		} else if (id.equals(ID_VARIABLE_TEXT)) {
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
			JavaVariables variables = (JavaVariables) context.getDomainObject();
			if (containerShape.getChildren().size() - 1 != variables.getVariables().size()) {
				return Reason.createTrueReason("Number of Variables differ. Expected: " + variables.getVariables().size() 
						+ " " + (containerShape.getChildren().size() - 1));
			}
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			EList<JavaVariable> variables = ((JavaVariables) context.getDomainObject()).getVariables();
			int newIndex = variables.size() - 1;
			JavaVariable variable = variables.get(newIndex);
			Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
			Text variableNameText = Graphiti.getGaService().createText(shapeText, variable.getName());
			setId(variableNameText, ID_VARIABLE_TEXT);
			setIndex(variableNameText, newIndex);
			variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			link(shapeText, variable);
			
			return true;
		}
		return false;
	}
}

