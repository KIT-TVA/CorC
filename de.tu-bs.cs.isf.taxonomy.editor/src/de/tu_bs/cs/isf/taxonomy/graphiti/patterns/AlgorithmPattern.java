package de.tu_bs.cs.isf.taxonomy.graphiti.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
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
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;

/**
 * Class that creates the graphical representation of Algorithms
 * @author Tobias
 *
 */
public class AlgorithmPattern extends IdPattern implements IPattern {

	private static final String ID_ALGORITHM_NAME_TEXT = "algorithmNameText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_NAME_SEPARATOR = "nameSeparator";
	private static final String ID_CONDITION_SEPARATOR = "conditionSeparator";
	private static final String ID_METHOD_SEPARATOR = "methodSeparator";
	private static final String ID_PRECONDITION_NAME_TEXT = "preConditionNameText";
	private static final String ID_POSTCONDITION_NAME_TEXT = "postConditionNameText";
	private static final String ID_INVARIANT_NAME_TEXT = "invariantNameText";
	private static final String ID_MAINMETHOD_NAMES_RECTANGLE = "mainMethodNamesRectangle";
	private static final String ID_MAINMETHOD_NAME_TEXT = "mainMethodNameText";
	private static final String ID_DATASTRUCTURE_NAMES_RECTANGLE = "dataStructureNamesRectangle";
//	private static final String ID_DATASTRUCTURE_NAMES_INNER_RECTANGLE = "dataStructureNamesInnerRectangle";
	private static final String ID_DATASTRUCTURE_NAME_TEXT = "dataStructureNameText";
	private static final String ID_DATASTRUCTURE_TYPE = "dataStructureType";
	private static final String ID_METHOD_NAMES_RECTANGLE = "methodNamesRectangle";
//	private static final String ID_METHOD_NAMES_INNER_RECTANGLE = "methodNamesInnerRectangle";
	private static final String ID_METHOD_NAME_TEXT = "methodNameText";
	private static final String ID_MAINMETHOD_TYPE = "mainMethodType";
	private static final String ID_METHOD_TYPE = "methodType";


	/**
	 * Constructor of the class
	 */
	public AlgorithmPattern() {
		super();
	}
	
	@Override
	public String getCreateName() {
		return "Algorithm";
	}
	
	@Override
	public String getCreateDescription() {
		return "Create an Algorithm.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Algorithm;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public Object[] create(ICreateContext context) {
		Algorithm newAlgorithm = TaxonomyFactory.eINSTANCE.createAlgorithm();
		newAlgorithm.setName(createNewName());
		newAlgorithm.setPreCondition("");
		newAlgorithm.setPostCondition("");
		newAlgorithm.setInvariant("");
		Method mainMethod = TaxonomyFactory.eINSTANCE.createMethod();
		mainMethod.setName("mainMethod()");
		mainMethod.setPreCondition("");
		mainMethod.setPostCondition("");
		newAlgorithm.getMethods().add(mainMethod);

		
//		Use the following instead of the above line to store the model
//				data in a seperate file parallel to the diagram file
		try {
			try {
				TaxonomyModelUtil.saveAlgorithmToModelFile(newAlgorithm, getDiagram());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		addGraphicalRepresentation(context, newAlgorithm);
		return new Object[] { newAlgorithm };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		Algorithm addedAlgorithm = (Algorithm) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 200 : context.getWidth();
        int height = context.getHeight() <= 0 ? 150 : context.getHeight();
        
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		gaService.setLocationAndSize(mainRectangle,
	            context.getX(), context.getY(), width, height);

        // create link and wire it
        link(outerContainerShape, addedAlgorithm);

		// Algorithm name
		Shape textShape = peCreateService.createShape(outerContainerShape, false);
		Text algorithmNameText = gaService.createText(textShape, "");
		setId(algorithmNameText, ID_ALGORITHM_NAME_TEXT);
		algorithmNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		algorithmNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		// Separating line
		Shape lineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline = gaService.createPolyline(lineShape);
		setId(polyline, ID_NAME_SEPARATOR);
//		polyline.setForeground(manageColor(IColorConstant.BLACK));
		
		// Precondition
		Shape textShapePre = peCreateService.createShape(outerContainerShape, false);
		Text preConditionText = gaService.createText(textShapePre, "");
		setId(preConditionText, ID_PRECONDITION_NAME_TEXT);
		preConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		preConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				
		// Postcondition
		Shape textShapePost = peCreateService.createShape(outerContainerShape, false);
		Text postConditionText = gaService.createText(textShapePost, "");
		setId(postConditionText, ID_POSTCONDITION_NAME_TEXT);
		postConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		postConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		// Invariant
		Shape textShapeInvariant = peCreateService.createShape(outerContainerShape, false);
		Text invariantText = gaService.createText(textShapeInvariant, "");
		setId(invariantText, ID_INVARIANT_NAME_TEXT);
		invariantText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		invariantText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		
		// Outer container for main method
		ContainerShape mainMethodContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle mainMethodRectangle = gaService.createInvisibleRectangle(mainMethodContainerShape);
		setId(mainMethodRectangle, ID_MAINMETHOD_NAMES_RECTANGLE);
				
		// Main method
		Shape textShapeMainMethod = peCreateService.createShape(mainMethodContainerShape, true);
		Text mainMethodNameText = Graphiti.getGaService().createText(textShapeMainMethod, "mainMethod()");
		setId(mainMethodNameText, ID_MAINMETHOD_NAME_TEXT);
		mainMethodNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		mainMethodNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		
		// Separating line
		Shape lineShape2 = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline2 = gaService.createPolyline(lineShape2);
		setId(polyline2, ID_CONDITION_SEPARATOR);
//		polyline2.setForeground(manageColor(IColorConstant.BLACK));

		// List of data in algorithm
		ContainerShape dataContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle dataRectangle = gaService.createInvisibleRectangle(dataContainerShape);
		setId(dataRectangle, ID_DATASTRUCTURE_NAMES_RECTANGLE);
		
		// Separating line
		Shape lineShape3 = peCreateService.createShape(outerContainerShape, false);
		Polyline polyline3 = gaService.createPolyline(lineShape3);
		setId(polyline3, ID_METHOD_SEPARATOR);
//		polyline3.setForeground(manageColor(IColorConstant.BLACK));
		
		// List of methods in algorithm
		ContainerShape methodContainerShape = peCreateService.createContainerShape(outerContainerShape, false);
		Rectangle methodRectangle = gaService.createInvisibleRectangle(methodContainerShape);
		setId(methodRectangle, ID_METHOD_NAMES_RECTANGLE);

		peCreateService.createChopboxAnchor(outerContainerShape);

		link(outerContainerShape, addedAlgorithm);
		link(textShape, addedAlgorithm);
		link(textShapePre, addedAlgorithm);
		link(textShapePost, addedAlgorithm);
		link(textShapeInvariant, addedAlgorithm);
		link(mainMethodContainerShape, addedAlgorithm);
		link(textShapeMainMethod, addedAlgorithm.getMethods().get(0));
		link(dataContainerShape, addedAlgorithm);
		link(methodContainerShape, addedAlgorithm);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		final int heightAdaption = 110;
		
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		ContainerShape outerContainerShape = (ContainerShape) context.getRootPictogramElement();
		Algorithm algo = (Algorithm) getBusinessObjectForPictogramElement(outerContainerShape);
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		
		if (algo.isAbstract()) {
			Graphiti.getGaService().setRenderingStyle(mainRectangle, PredefinedColoredAreas.getLightGrayAdaptions());
		} else {
			Graphiti.getGaService().setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		}
		
		if (id.equals(ID_ALGORITHM_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 10, mainRectangle.getWidth(), 20);
			changesDone = true;
		} else if (id.equals(ID_NAME_SEPARATOR)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, 30, mainRectangle.getWidth(), 30 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} 
		else if (id.equals(ID_PRECONDITION_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 5, 30, mainRectangle.getWidth() - 10, 20);
			changesDone = true;
		} else if (id.equals(ID_POSTCONDITION_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 5, 50, mainRectangle.getWidth() - 10, 20);
			changesDone = true;
		} else if (id.equals(ID_INVARIANT_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 5, 70, mainRectangle.getWidth() - 10, 20);
			changesDone = true;
		} else if (id.equals(ID_MAINMETHOD_NAMES_RECTANGLE)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 90, mainRectangle.getWidth(), 20);
			changesDone = true;
		} else if (id.equals(ID_MAINMETHOD_NAME_TEXT)) {
			Method method = (Method) getBusinessObjectForPictogramElement(context.getGraphicsAlgorithm().getPictogramElement());
			int width;
			if (method.getDataTypes().isEmpty()) {
				width = (mainRectangle.getWidth() - 5); //(method.getDataTypes().size() + 2) * 2;
			} else {
				width = (mainRectangle.getWidth() - 5) / 2; //(method.getDataTypes().size() + 2) * 2;
			}
			Graphiti.getGaService().setLocationAndSize(ga, 5, 90, width - 5 , 20);
			changesDone = true;
		}
		else if (id.equals(ID_MAINMETHOD_TYPE)) {
			int indexWidth = getIndex(context.getGraphicsAlgorithm());
			DataType type = (DataType) getBusinessObjectForPictogramElement(context.getGraphicsAlgorithm().getPictogramElement());
			Method method = (Method) type.eContainer();
			int width = (mainRectangle.getWidth() - 5) / (method.getDataTypes().size() * 2); //+2
			Graphiti.getGaService().setLocationAndSize(ga, width * (indexWidth + method.getDataTypes().size()), 90, width, 20);
			changesDone = true;
		} else if (id.equals(ID_CONDITION_SEPARATOR)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, 110, mainRectangle.getWidth(), 110 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}
		else if (id.equals(ID_DATASTRUCTURE_NAMES_RECTANGLE)) {
			int height = algo.getDataStructures().size() > 0 ? algo.getDataStructures().size() * 20 : 20;
			Graphiti.getGaService().setLocationAndSize(ga, 0, heightAdaption, mainRectangle.getWidth(),
					height);
			changesDone = true;
		}
//		else if (id.equals(ID_DATASTRUCTURE_NAMES_INNER_RECTANGLE)) {
//			int index = getIndex(context.getGraphicsAlgorithm());
//			Graphiti.getGaService().setLocationAndSize(ga, 0, 30 + 20 * index, mainRectangle.getWidth(), 20);
//			changesDone = true;
//		}
		else if (id.equals(ID_DATASTRUCTURE_NAME_TEXT)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, 5, heightAdaption + 20 * index, (mainRectangle.getWidth()-5) / 3 * 2 - 5, 20);
			//Graphiti.getGaService().setLocationAndSize(ga, 5, 0, mainRectangle.getWidth() / 3 * 2, 20);
			changesDone = true;
		}
		else if (id.equals(ID_DATASTRUCTURE_TYPE)) {
			int index = getIndex(context.getGraphicsAlgorithm());
			Graphiti.getGaService().setLocationAndSize(ga, (mainRectangle.getWidth()-5) / 3 * 2, heightAdaption + 20 * index, (mainRectangle.getWidth()-5) / 3 , 20);
			//Graphiti.getGaService().setLocationAndSize(ga, 5 + mainRectangle.getWidth() / 3 * 2, 0, mainRectangle.getWidth() / 3 - 5, 20);
			changesDone = true;
		} 
		else if (id.equals(ID_METHOD_SEPARATOR)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			int height = algo.getDataStructures().size() > 0 ? algo.getDataStructures().size() * 20 + heightAdaption : heightAdaption + 20;
			List<Point> pointList = Graphiti.getGaService().createPointList(
					new int[] { 0, height, mainRectangle.getWidth(), height });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_METHOD_NAMES_RECTANGLE)) {
			int height = algo.getDataStructures().size() > 0 ? algo.getDataStructures().size() * 20 + heightAdaption : heightAdaption + 20;
			Graphiti.getGaService().setLocationAndSize(ga, 0, height, mainRectangle.getWidth(),
					mainRectangle.getHeight() - height);
			changesDone = true;
		} 
//		else if (id.equals(ID_METHOD_NAMES_INNER_RECTANGLE)) {
//			int index = getIndex(context.getGraphicsAlgorithm());
//			int height = algo.getDatastructures().size() > 0 ? algo.getDatastructures().size() * 20 + 30 : 50;
//			Graphiti.getGaService().setLocationAndSize(ga, 0, height + 20 * index, mainRectangle.getWidth(), 20);
//			changesDone = true;
//		} 
		else if (id.equals(ID_METHOD_NAME_TEXT)) {
			int height = algo.getDataStructures().size() > 0 ? algo.getDataStructures().size() * 20 + heightAdaption : heightAdaption + 20;
			int index = getIndex(context.getGraphicsAlgorithm());
			Method method = (Method) getBusinessObjectForPictogramElement(context.getGraphicsAlgorithm().getPictogramElement());
			int width;
			if (method.getDataTypes().isEmpty()) {
				width = (mainRectangle.getWidth()-5);
			} else {
				width = (mainRectangle.getWidth()-5) / 2; //(method.getDataTypes().size() + 2) * 2;
			}
			Graphiti.getGaService().setLocationAndSize(ga, 5, height + 20 * index, width -5 , 20);
			changesDone = true;
		}
		else if (id.equals(ID_METHOD_TYPE)) {
			int height = algo.getDataStructures().size() > 0 ? algo.getDataStructures().size() * 20 + heightAdaption : heightAdaption + 20;
			int indexWidth = getIndex(context.getGraphicsAlgorithm()) % 100;
			int index = getIndex(context.getGraphicsAlgorithm()) / 100;
			DataType type = (DataType) getBusinessObjectForPictogramElement(context.getGraphicsAlgorithm().getPictogramElement());
			Method method = (Method) type.eContainer();
			int width = (mainRectangle.getWidth()-5) / (method.getDataTypes().size() * 2); //+2
			Graphiti.getGaService().setLocationAndSize(ga, width * (indexWidth + method.getDataTypes().size()), height + 20 * index, width, 20);
			changesDone = true;
		} 

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_ALGORITHM_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getName() == null || !domainObject.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
			}
		} 
		else if (id.equals(ID_PRECONDITION_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getPreCondition() == null ||  (!domainObject.getPreCondition().equals("") && !domainObject.getPreCondition().equals(nameText.getValue()))) {
				return Reason.createTrueReason("PreConditions differs. Expected: '" + domainObject.getPreCondition() + "'");
			}
		}
		else if (id.equals(ID_POSTCONDITION_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getPostCondition() == null || (!domainObject.getPostCondition().equals("") && !domainObject.getPostCondition().equals(nameText.getValue()))) {
				return Reason.createTrueReason("PostConditions differs. Expected: '" + domainObject.getPostCondition() + "'");
			}
		}
		else if (id.equals(ID_INVARIANT_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getInvariant() == null || (!domainObject.getInvariant().equals("") && !domainObject.getInvariant().equals(nameText.getValue()))) {
				return Reason.createTrueReason("Invariant differs. Expected: '" + domainObject.getInvariant() + "'");
			}
		}
		else if (id.equals(ID_DATASTRUCTURE_NAMES_RECTANGLE)) {
			ContainerShape dataContainerShape = (ContainerShape) context.getPictogramElement();
			Algorithm algorithm = (Algorithm) context.getDomainObject();
			if (dataContainerShape.getChildren().size() != algorithm.getDataStructures().size() * 2) {
				return Reason.createTrueReason("Number of DataStructures differ. Expected: " + algorithm.getDataStructures().size() + " "+ dataContainerShape.getChildren().size());
			}
		}
//		else if (id.equals(ID_DATASTRUCTURE_NAMES_INNER_RECTANGLE)) {
//			ContainerShape dataContainerShape = (ContainerShape) context.getPictogramElement();
//			if (dataContainerShape.getChildren().size() != 2) {
//				return Reason.createTrueReason("Number of DataStructures differ. Expected: ");
//			}
//		} 
		else if (id.equals(ID_DATASTRUCTURE_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			DataStructure data = (DataStructure) context.getDomainObject();
			if (data.getName() == null || !data.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + data.getName() + "'");
			}
		}
		else if (id.equals(ID_DATASTRUCTURE_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			if (dataType.getName()== null || !dataType.getName().equals(typeText.getValue())) {
				return Reason.createTrueReason("Type differs. Expected: '" + dataType.getName() + "'");
			}
		}
		else if (id.equals(ID_METHOD_NAMES_RECTANGLE)) {
			ContainerShape methodContainerShape = (ContainerShape) context.getPictogramElement();
			Algorithm algorithm = (Algorithm) context.getDomainObject();
			int methodSize = 0;
			for (int i = 1; i < algorithm.getMethods().size(); i++) {
				Method method = algorithm.getMethods().get(i);
				methodSize += method.getDataTypes().size() + 1;
			}
			if (methodContainerShape.getChildren().size() != methodSize) {
				return Reason.createTrueReason("Number of Methods and Types differ.");
			}
		}
		else if (id.equals(ID_MAINMETHOD_NAMES_RECTANGLE)) {
			ContainerShape methodContainerShape = (ContainerShape) context.getPictogramElement();
			Algorithm algorithm = (Algorithm) context.getDomainObject();
			int methodSize = 0;
			Method mainMethod = algorithm.getMethods().get(0);
			methodSize += mainMethod.getDataTypes().size() + 1;
			if (methodContainerShape.getChildren().size() != methodSize) {
				return Reason.createTrueReason("Number of Types differ in main method.");
			}
		}
//		else if (id.equals(ID_METHOD_NAMES_INNER_RECTANGLE)) {
//			ContainerShape methodContainerShape = (ContainerShape) context.getPictogramElement();
//			Method method = (Method) context.getDomainObject();
//			if (methodContainerShape.getChildren().size() != method.getDataTypes().size() + 1) {
//				return Reason.createTrueReason("Number of Methods differ. Expected: " + method.getDataTypes().size() + 1);
//			}
//		}
		else if (id.equals(ID_MAINMETHOD_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method method = (Method) context.getDomainObject();
			if (method.getName() == null || !method.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + method.getName() + "'");
			}
		}
		else if (id.equals(ID_METHOD_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method method = (Method) context.getDomainObject();
			if (method.getName() == null || !method.getName().equals(nameText.getValue())) {
				return Reason.createTrueReason("Name differs. Expected: '" + method.getName() + "'");
			}
		}
		else if (id.equals(ID_MAINMETHOD_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			if (dataType.getName()== null || !dataType.getName().equals(typeText.getValue())) {
				return Reason.createTrueReason("Type differs. Expected: '" + dataType.getName() + "'");
			}
		}
		else if (id.equals(ID_METHOD_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			if (dataType.getName()== null || !dataType.getName().equals(typeText.getValue())) {
				return Reason.createTrueReason("Type differs. Expected: '" + dataType.getName() + "'");
			}
		}

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_ALGORITHM_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			nameText.setValue(domainObject.getName());
			return true;
		} 
		else if (id.equals(ID_PRECONDITION_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getPreCondition().equals("")) {
				nameText.setValue("pre condition");
			} else {
				nameText.setValue(domainObject.getPreCondition());
			}
			return true;
		}
		else if (id.equals(ID_POSTCONDITION_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getPostCondition().equals("")) {
				nameText.setValue("post condition");
			} else {
				nameText.setValue(domainObject.getPostCondition());
			}
			return true;
		}
		else if (id.equals(ID_INVARIANT_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Algorithm domainObject = (Algorithm) context.getDomainObject();
			if (domainObject.getInvariant().equals("")) {
				nameText.setValue("invariant");
			} else {
				nameText.setValue(domainObject.getInvariant());
			}
			return true;
		}
		else if (id.equals(ID_DATASTRUCTURE_NAMES_RECTANGLE)) {
			EList<Shape> children = ((ContainerShape) context.getPictogramElement()).getChildren();
			Shape[] toDelete = children.toArray(new Shape[children.size()]);
			for (Shape shape : toDelete) {
				EcoreUtil.delete(shape, true);
			}
			EList<DataStructure> datas = ((Algorithm) context.getDomainObject()).getDataStructures();
			int index = 0;
			for (DataStructure data : datas) {
//				ContainerShape containerShape = Graphiti.getPeCreateService().createContainerShape((ContainerShape) context.getPictogramElement(), true);
//				Rectangle dataRectangle = Graphiti.getGaService().createInvisibleRectangle(containerShape);
//				setId(dataRectangle, ID_DATASTRUCTURE_NAMES_INNER_RECTANGLE);
//				setIndex(dataRectangle, index);
//				link(containerShape, data);
				
				ContainerShape shapeText = Graphiti.getPeCreateService().createContainerShape((ContainerShape) context.getPictogramElement(), true);
//				
//				Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text dataNameText = Graphiti.getGaService().createText(shapeText, data.getName());
				setId(dataNameText, ID_DATASTRUCTURE_NAME_TEXT);
				setIndex(dataNameText, index);
				dataNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				dataNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeText, data);
				
				Shape shapeType = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text dataTypeText = Graphiti.getGaService().createText(shapeType, data.getDataType().getName());
				setId(dataTypeText, ID_DATASTRUCTURE_TYPE);
				setIndex(dataTypeText, index);
				dataTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				dataTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeType, data.getDataType());
				index++;
			}
			return true;
		} else if (id.equals(ID_DATASTRUCTURE_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			DataStructure data = (DataStructure) context.getDomainObject();
			nameText.setValue(data.getName());
			return true;
		} 
		else if (id.equals(ID_DATASTRUCTURE_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			typeText.setValue(dataType.getName());
			return true;
		}
		else if (id.equals(ID_METHOD_NAMES_RECTANGLE)) {
			EList<Shape> children = ((ContainerShape) context.getPictogramElement()).getChildren();
			Shape[] toDelete = children.toArray(new Shape[children.size()]);
			for (Shape shape : toDelete) {
				EcoreUtil.delete(shape, true);
			}
			EList<Method> methods = ((Algorithm) context.getDomainObject()).getMethods();
			int index = 0;
			for (int i = 1; i < methods.size(); i++) {
				Method method = methods.get(i);
//				ContainerShape containerShape = Graphiti.getPeCreateService().createContainerShape((ContainerShape) context.getPictogramElement(), true);
//				Rectangle methodRectangle = Graphiti.getGaService().createInvisibleRectangle(containerShape);
//				setId(methodRectangle, ID_METHOD_NAMES_INNER_RECTANGLE);
//				setIndex(methodRectangle, index);
//				link(containerShape, method);
				
				ContainerShape shapeText = Graphiti.getPeCreateService().createContainerShape((ContainerShape) context.getPictogramElement(), true);
//				Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text methodNameText = Graphiti.getGaService().createText(shapeText, method.getName());
				setId(methodNameText, ID_METHOD_NAME_TEXT);
				setIndex(methodNameText, index);
				methodNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				methodNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				int typeIndex = 0;
				for (DataType type : method.getDataTypes()) {
					Shape shapeType = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
					Text methodTypeText = Graphiti.getGaService().createText(shapeType, type.getName());
					setId(methodTypeText, ID_METHOD_TYPE);
					setIndex(methodTypeText, index * 100 + typeIndex);
					methodTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
					methodTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeType, type);
					typeIndex++;
				}
				index++;
				link(shapeText, method);
			}
			return true;
		}
		else if (id.equals(ID_MAINMETHOD_NAMES_RECTANGLE)) {
			EList<Shape> children = ((ContainerShape) context.getPictogramElement()).getChildren();
			Shape[] toDelete = children.toArray(new Shape[children.size()]);
			for (Shape shape : toDelete) {
				EcoreUtil.delete(shape, true);
			}
			Method method = ((Algorithm) context.getDomainObject()).getMethods().get(0);
			ContainerShape shapeText = Graphiti.getPeCreateService().createContainerShape((ContainerShape) context.getPictogramElement(), true);
			Text methodNameText = Graphiti.getGaService().createText(shapeText, method.getName());
			setId(methodNameText, ID_MAINMETHOD_NAME_TEXT);
			methodNameText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
			methodNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			int typeIndex = 0;
			for (DataType type : method.getDataTypes()) {
				Shape shapeType = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
				Text methodTypeText = Graphiti.getGaService().createText(shapeType, type.getName());
				setId(methodTypeText, ID_MAINMETHOD_TYPE);
				setIndex(methodTypeText,  typeIndex);
				methodTypeText.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				methodTypeText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
				link(shapeType, type);
				typeIndex++;
			}
			link(shapeText, method);
			return true;
		} 
		else if (id.equals(ID_MAINMETHOD_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method method = (Method) context.getDomainObject();
			nameText.setValue(method.getName());
			return true;
		}
		else if (id.equals(ID_METHOD_NAME_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Method method = (Method) context.getDomainObject();
			nameText.setValue(method.getName());
			return true;
		}
		else if (id.equals(ID_MAINMETHOD_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			typeText.setValue(dataType.getName());
			return true;
		}
		else if (id.equals(ID_METHOD_TYPE)) {
			Text typeText = (Text) context.getGraphicsAlgorithm();
			DataType dataType = (DataType) context.getDomainObject();
			typeText.setValue(dataType.getName());
			return true;
		}
		return false;
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if (domainObject instanceof Algorithm && ga instanceof Text) {
			return true;
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Algorithm algo = (Algorithm) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_ALGORITHM_NAME_TEXT)) {
			return algo.getName();
		}
		else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_PRECONDITION_NAME_TEXT)) {
			return algo.getPreCondition();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_POSTCONDITION_NAME_TEXT)) {
			return algo.getPostCondition();
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_INVARIANT_NAME_TEXT)) {
			return algo.getInvariant();
		}
		return "Something went wrong.";
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Text field must not be empty";
		}

		Algorithm algo = (Algorithm) getBusinessObjectForPictogramElement(context.getPictogramElement());
		EList<Shape> children = getDiagram().getChildren();
		for (Shape child : children) {
			Object domainObject = getBusinessObjectForPictogramElement(child);
			if (domainObject instanceof Algorithm) {
				if (!domainObject.equals(algo) && value.equals(((Algorithm) domainObject).getName())) {
					return "An Algorithm with name '" + ((Algorithm) domainObject).getName() + "' already exists.";
				}
			}
		}
		if (!getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_ALGORITHM_NAME_TEXT)) {
			if (!CompareMethodBodies.readAndTestAssertWithJaMoPP(value.replaceAll("->", "&"))) {
				return "Value is not in correct format: " + value ;
			}
		}
		return null;
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Algorithm algo = (Algorithm) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_ALGORITHM_NAME_TEXT)) {
			algo.setName(value);
		} 
		else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_PRECONDITION_NAME_TEXT)) {
			algo.setPreCondition(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_POSTCONDITION_NAME_TEXT)) {
			algo.setPostCondition(value);
		} else if (getId(context.getPictogramElement().getGraphicsAlgorithm()).equals(ID_INVARIANT_NAME_TEXT)) {
			algo.setInvariant(value);
		}
		updatePictogramElement(context.getPictogramElement());
	}
	
	@Override
	public void moveShape(IMoveShapeContext context) {
		ContainerShape shape = (ContainerShape) context.getPictogramElement();
		super.moveShape(context);
		for (Anchor anchor : shape.getAnchors()) {
			for (Connection connection : anchor.getIncomingConnections()) {
				layoutPictogramElement(connection);
			}
			for (Connection connection : anchor.getOutgoingConnections()) {
				layoutPictogramElement(connection);
			}
		}
	}

	@Override
	public void resizeShape(IResizeShapeContext context) {
		ContainerShape shape = (ContainerShape) context.getPictogramElement();
		super.resizeShape(context);
		for (Anchor anchor : shape.getAnchors()) {
			for (Connection connection : anchor.getIncomingConnections()) {
				layoutPictogramElement(connection);
			}
			for (Connection connection : anchor.getOutgoingConnections()) {
				layoutPictogramElement(connection);
			}
		}
	}

	/**
	 * Creates a new name that is unique
	 * @return	An unique name
	 */
	private String createNewName() {
		String initialName = "NewAlgorithm";
		String name = initialName;
		int number = 0;
		while (findAlgorithm(name) != null) {
			number++;
			name = initialName + number;
		}
		return name;
	}

	/**
	 * Method to search for Algorithms with same name
	 * @param name	The input name that should be searched
	 * @return	An Algorithm with the same name or null
	 */
	private Algorithm findAlgorithm(String name) {
		try {
			Taxonomy taxonomy = TaxonomyModelUtil.getTaxonomy(getDiagram());
			EList<Algorithm> contents = taxonomy.getAlgorithms();
			for (Algorithm algo : contents) {
				if (name.equals(algo.getName())) {
					return algo;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
