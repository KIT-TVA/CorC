package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.awt.Frame;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.impl.TextImpl;
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
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbcclass.tool.diagram.CbCClassImageProvider;
import helper.ClassUtil;
import model.CbcClassUtil;

public class ModelClassPattern extends IdPattern implements IPattern {

	@Override
	public String getCreateName() {
		return "Model Class";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Model Class";
	}
	
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_CLASS_FIELDS_TEXT = "fieldsNameText";
	private static final String ID_CLASS_INVARIANTS_TEXT = "classInvariantsText";
	
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// Header:
	private static final String ID_CLASS_INVARIANTS_HEADER = "ivHeader";
	private static final String ID_CLASS_FIELDS_HEADER = "fHeader";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_HOR3_LINE = "hor3Line";
	private static final String ID_HOR4_LINE = "hor4Line";	
	
	private int width;
	private int height;	
	
	public static ModelClass instance;	
	private IProject project;
	
	public ModelClassPattern() {
		super();
	}
	
	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}
	
	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof ModelClass;
	}
	
	public boolean canCreate(ICreateContext context) {
		ModelClass modelClass = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof ModelClass) {
				modelClass = (ModelClass) obj;
			}
		}
	
		if (modelClass != null) return false;
		return context.getTargetContainer() instanceof Diagram;
	} 
	
	public Object[] create (ICreateContext context) {
		Frame jf=new JFrame();
		jf.setAlwaysOnTop(true);
		
		ModelClass modelClass = CbcclassFactory.eINSTANCE.createModelClass();
		modelClass.setName(getDiagram().getName());
		
		String inheritance = "";
		String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI());
		Resource inheritanceResource = null;
		while (inheritance.equals("")) {
			inheritance = (String) JOptionPane.showInputDialog(null, "Should this class inherit? Type a class name or press cancel.");
			if (inheritance != null) inheritanceResource = ClassUtil.getClassModelResource(path, inheritance.trim());
			if (inheritanceResource == null && inheritance != null && !inheritance.equals(modelClass.getName())) {
				inheritance = "";
			} else if (inheritance == null) {
				inheritance = "none";
			}
		}
		
		if (!inheritance.equals("none")) {
			modelClass.setInheritsFrom((ModelClass) inheritanceResource.getContents().get(0));
		}
		
		URI uri = getDiagram().eResource().getURI();
		project = FileUtil.getProjectFromFileInProject(uri);
		try {
			if(project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
				String[] segments = uri.segments();
				modelClass.setFeature(segments[segments.length-3]);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		try {
			CbcClassUtil.saveModelClass(modelClass, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}		
		
		addGraphicalRepresentation(context, modelClass);
		return new Object[] { modelClass };
	}
	
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}
	
	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if ((domainObject instanceof ModelClass) && ga instanceof RoundedRectangle) {
			return true;
		}
		return false;
	}
	
	@Override
	protected PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		ModelClass addedModelClass = (ModelClass) context.getNewObject();
		
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		width = context.getWidth() <= 0 ? 350 : context.getWidth();
		height = context.getHeight() <= 0 ? 250 : context.getHeight();

		Font headerFont = gaService.manageFont(getDiagram(), "Arial", 9, false, true);
		
		// Main contents area
		ContainerShape outerContainerShape = peCreateService.createContainerShape(targetDiagram, true);
		RoundedRectangle mainRectangle = gaService.createRoundedRectangle(outerContainerShape, 20, 20);
		mainRectangle.setFilled(true);
		gaService.setRenderingStyle(mainRectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		setId(mainRectangle, ID_MAIN_RECTANGLE);
		gaService.setLocationAndSize(mainRectangle, context.getX(), context.getY(), width, height);

		// create link and wire it
		link(outerContainerShape, addedModelClass);	
		
		// ModelClass
		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCClassImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);
		
		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, addedModelClass.getName());
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);
		
		// Invariants
		Shape hor1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Polyline = gaService.createPolyline(hor1LineShape);
		setId(hor1Polyline, ID_HOR1_LINE);
		
		Shape invariantsHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text invariantsHeader = gaService.createText(invariantsHeaderShape, "invariants");
		setId(invariantsHeader, ID_CLASS_INVARIANTS_HEADER);
		invariantsHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantsHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		invariantsHeader.setFont(headerFont);
		
		Shape hor2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Polyline = gaService.createPolyline(hor2LineShape);
		setId(hor2Polyline, ID_HOR2_LINE);
		
		// Fields
		Shape hor3LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor3Polyline = gaService.createPolyline(hor3LineShape);
		setId(hor3Polyline, ID_HOR3_LINE);
		
		Shape fieldsHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text fieldsHeader = gaService.createText(fieldsHeaderShape, "fields");
		setId(fieldsHeader, ID_CLASS_FIELDS_HEADER);
		fieldsHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldsHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		fieldsHeader.setFont(headerFont);
		
		Shape hor4LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor4Polyline = gaService.createPolyline(hor4LineShape);
		setId(hor4Polyline, ID_HOR4_LINE);
		
		peCreateService.createChopboxAnchor(outerContainerShape);
		peCreateService.createChopboxAnchor(textShapeName);
		
		link(outerContainerShape, addedModelClass);
		link(textShapeName, addedModelClass);
		link(proveShape, addedModelClass);
		
		return outerContainerShape;	
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;
		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		width = mainRectangle.getWidth();
		height = mainRectangle.getHeight();

		int xMiddle = width / 3;
		int heightName = 30; // height of name of modelclass
		int heightHeader = 20; // height of the headers of fields, invariants
		int positionHeader = 40; // position where the Header is
		int sizeText = 30;
		int positionText = positionHeader + heightHeader; // position from the blocks (pre, statement, post)
		
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getRootPictogramElement());
		int sizeInvariants = modelClass.getClassInvariants().size();
		int sizeFields = modelClass.getFields().size();

		if (modelClass.getInheritsFrom() != null) {
			sizeInvariants += modelClass.getInheritsFrom().getClassInvariants().size();
			sizeFields += modelClass.getInheritsFrom().getFields().size();
		}
		
		sizeText = 30;
		
		// resize modelclass
		int totalSize = sizeInvariants + sizeFields;
		mainRectangle.setHeight((totalSize * sizeText + 1) + 170);		

		int beginYFieldHeader = positionText + (sizeText * sizeInvariants) + 40;
		
		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 5, mainRectangle.getWidth(), heightName);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionText + sizeText * getIndex(ga), mainRectangle.getWidth(), sizeText);
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, beginYFieldHeader + sizeText * (sizeInvariants == 0 ? getIndex(ga) + 1 : getIndex(ga) - sizeInvariants + 1) - 7, mainRectangle.getWidth(), sizeText);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		} else if (id.equals(ID_CLASS_INVARIANTS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, xMiddle, positionHeader, xMiddle, heightHeader);
			changesDone = true;
		} else if (id.equals(ID_CLASS_FIELDS_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, xMiddle, beginYFieldHeader, xMiddle, heightHeader);
			changesDone = true;
		} else if (id.equals(ID_HOR1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { 0, positionHeader, mainRectangle.getWidth(), positionHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, positionHeader + heightHeader,
					mainRectangle.getWidth(), positionHeader + heightHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR3_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYFieldHeader,
					mainRectangle.getWidth(), beginYFieldHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_HOR4_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, beginYFieldHeader + 20,
					mainRectangle.getWidth(), beginYFieldHeader + 20 });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} 
		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {		
		if (id.equals(ID_MAIN_RECTANGLE)) {
			ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
			ModelClass mc = (ModelClass) context.getDomainObject();
			List<Condition> invs = mc.getClassInvariants();
			List<Field> fields = mc.getFields();
			List<Condition> inheritedInvs = new ArrayList<Condition>();
			List<Field> inheritedFields = new ArrayList<Field>();
			if (mc.getInheritsFrom() != null) {
				inheritedInvs = mc.getInheritsFrom().getClassInvariants();
				inheritedFields = mc.getInheritsFrom().getFields();
			}
			
			int size = invs.size() + fields.size() + inheritedInvs.size() + inheritedFields.size();
			if (containerShape.getChildren().size() - 8 != size) {
				return Reason.createTrueReason("Number of Elements differ. Expected: " + size + " Actual: " + (containerShape.getChildren().size() - 8));
			}
			
			List<Integer> found = new ArrayList<Integer>();
			EList<Shape> shapes = containerShape.getChildren();
			for (int i = 8; i < shapes.size(); i++) {
				Shape shape = shapes.get(i);
				EList<EObject> objects = shape.eContents();
				for (int j = 0; j < objects.size(); j++) {
					EObject obj = objects.get(j);
					if (obj instanceof Text) {
						int index = i - 8;
						// check own fields
						for (int k = 0; k < fields.size(); k++) {
							Field f = fields.get(k);
							if (f.getDisplayedName().equalsIgnoreCase(((Text) obj).getValue()) && !found.contains(index)) {
								found.add(index);
								break;
							}
						} 
						// check inherited fields
						for (int k = 0; k < inheritedFields.size(); k++) {
							Field f = inheritedFields.get(k);
							if (f.getDisplayedName().equalsIgnoreCase(((Text) obj).getValue().replace(" inherited", "")) && !found.contains(index)) {
								found.add(index);
								break;
							}
						}
						// check own invariants
						for (int k = 0; k < invs.size(); k++) {
							Condition c = invs.get(k);
							if (("{" + c.getName() + "}").equalsIgnoreCase(((Text) obj).getValue()) && !found.contains(index)) {
								found.add(index);
								break;
							}
						}
						// check inherited invariants
						for (int k = 0; k < inheritedInvs.size(); k++) {
							Condition c = inheritedInvs.get(k);
							if (("{" + c.getName() + "}").equalsIgnoreCase(((Text) obj).getValue().replace(" inherited", "")) && !found.contains(index)) {
								found.add(index);
								break;
							}
						}
						break;
					}
				}
			}
			if (found.size() != size) {
				return Reason.createTrueReason("ModelClass differs.");
			}	
		}

		return Reason.createFalseReason();	

	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_NAME_TEXT)) {
			MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
			ModelClass domainObject = (ModelClass) context.getDomainObject();
			if (domainObject.getInheritsFrom() != null) {
				nameText.setValue(domainObject.getName() + " extends " + domainObject.getInheritsFrom().getName());
			} else {
				nameText.setValue(domainObject.getName());
			}
			return true;
		} else if (id.equals(ID_MAIN_RECTANGLE)) {
			ModelClass modelClass = (ModelClass) context.getDomainObject();

			for (int i = 0; i < modelClass.getFields().size();) {
				if (modelClass.getFields().get(i).getType() == null) {
					modelClass.getFields().remove(i);
				} else i++;
			}
			
			
			List<Condition> inheritedInvs = new ArrayList<Condition>();
			List<Field> inheritedFields = new ArrayList<Field>();
			if (modelClass.getInheritsFrom() != null) {
				for (int i = 0; i < modelClass.getInheritsFrom().getFields().size(); i++) {
					if (modelClass.getInheritsFrom().getFields().get(i).getName() == null) {
						modelClass.getInheritsFrom().getFields().remove(i);
						i--;
					}
				}
				inheritedInvs = modelClass.getInheritsFrom().getClassInvariants();
				inheritedFields = modelClass.getInheritsFrom().getFields();
			}

			List<Integer> checkedShapes = new ArrayList<>();
			checkedShapes.add(0); //prove shape
			checkedShapes.add(1); //modelclass name
			checkedShapes.add(2); //line above inv
			checkedShapes.add(3); //inv name
			checkedShapes.add(4); //line below inv	
			checkedShapes.add(5); //line above fields
			checkedShapes.add(6); //fields name
			checkedShapes.add(7); //line below fields
			List<Condition> invs = modelClass.getClassInvariants();
			List<Field> fields = modelClass.getFields();			
			
			// invariants
			for (int i = 0; i < invs.size() + inheritedInvs.size(); i++) {
				Condition inv = i < inheritedInvs.size() ? inheritedInvs.get(i) : invs.get(i - inheritedInvs.size());
				EList<Shape> shapes = ((ContainerShape) context.getPictogramElement()).getChildren();
				boolean check = false;
				for (int j = 8; j < shapes.size(); j++) {
					Shape shape = shapes.get(j);
					if (shape.getGraphicsAlgorithm() instanceof TextImpl) {
						Text text = (Text) shape.getGraphicsAlgorithm();
						if (inv.getName().equals(text.getValue().replace(" inherited", "").replace("{", "").replace("}", ""))) {
							check = true;
							checkedShapes.add(j);
							break;
						}
					} else {
						if (!checkedShapes.contains(j)) checkedShapes.add(j);
					}
				}
				if (!check) {
					int newIndex = 0;
					ContainerShape container = (ContainerShape) context.getPictogramElement();
					for (Shape childShape : container.getChildren()) {
						if (getIndex(childShape.getGraphicsAlgorithm()) >= newIndex) {
							setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) + 1);
						}
					}

					Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
					Text variableNameText = Graphiti.getGaService().createText(shapeText, "{" + inv.getName() + "}");
					if (!((ModelClass) inv.eContainer()).getName().equals(modelClass.getName())) {
						variableNameText.setValue(variableNameText.getValue() + " inherited");
					}
					setId(variableNameText, ID_CLASS_INVARIANTS_TEXT);
					setIndex(variableNameText, newIndex);
					variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
					variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeText, inv);

					checkedShapes.add(shapes.size() - 1);
				}
			}

			// fields
			for (int i = 0; i < fields.size() + inheritedFields.size(); i++) {
				Field field = i < inheritedFields.size() ? inheritedFields.get(i) : fields.get(i - inheritedFields.size());
				EList<Shape> shapes = ((ContainerShape) context.getPictogramElement()).getChildren();
				boolean check = false;
				for (int j = 8; j < shapes.size(); j++) {
					Shape shape = shapes.get(j);
					if (shape.getGraphicsAlgorithm() instanceof TextImpl) {
						Text text = (Text) shape.getGraphicsAlgorithm();
						if (field.getDisplayedName().equals(text.getValue().replace(" inherited", ""))) {
							check = true;
							checkedShapes.add(j);
							break;
						}
					} else {
						if (!checkedShapes.contains(j)) checkedShapes.add(j);
					}
				}
				if (!check) {
					int newIndex = invs.size() + inheritedInvs.size() + i;
					ContainerShape container = (ContainerShape) context.getPictogramElement();
					for (Shape childShape : container.getChildren()) {
						if (getIndex(childShape.getGraphicsAlgorithm()) >= newIndex) {
							setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) + 1);
						}
					}

					Shape shapeText = Graphiti.getPeCreateService().createShape((ContainerShape) context.getPictogramElement(), true);
					Text variableNameText = Graphiti.getGaService().createText(shapeText, field.getDisplayedName());
					if (!((ModelClass) field.eContainer()).getName().equals(modelClass.getName())) {
						variableNameText.setValue(variableNameText.getValue() + " inherited");
					}
					setId(variableNameText, ID_CLASS_FIELDS_TEXT);
					setIndex(variableNameText, newIndex);
					variableNameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
					variableNameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
					link(shapeText, field);
					checkedShapes.add(shapes.size() - 1);
				}
			}

			EList<Shape> shapes = ((ContainerShape) context.getPictogramElement()).getChildren();
			int counter = 0;
			for (int i = 8; i < shapes.size(); i++) {
				if (!checkedShapes.contains(i)) {
					Shape shape = shapes.get(i - counter);
					int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
					for (Shape childShape : shapes) {
						if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
							setIndex(childShape.getGraphicsAlgorithm(),
									getIndex(childShape.getGraphicsAlgorithm()) - 1);
						}
					}
					EcoreUtil.delete(shape.getLink());
					EcoreUtil.delete(shape);
					counter++;
				}
			}
			return true;
		}
		return false;
	}
	
	public void setValue(String value, IDirectEditingContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		String[] fragments = value.trim().split(" ");
		//is new value valid?
		if ((fragments.length != 3 && fragments.length != 1) || !fragments[0].equals(modelClass.getName())) {
			return;
		}
		Resource inheritanceResource = null;
		ModelClass parentClass = null;
		if (fragments.length == 1) {
			modelClass.setInheritsFrom(parentClass);
		} else if (fragments.length == 3) {
			if (fragments[1].equals("extends")) {
				String path = FileUtil.getProjectLocation(getDiagram().eResource().getURI());
				inheritanceResource = ClassUtil.getClassModelResource(path, fragments[2]);
				if (inheritanceResource == null) {
					return;
				}
				parentClass = (ModelClass) inheritanceResource.getContents().get(0);
				modelClass.setInheritsFrom(parentClass);
			}
		}
		updatePictogramElement(context.getPictogramElement());
	}
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		ModelClass mc = (ModelClass) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (mc.getInheritsFrom() != null) {
			return mc.getName() + " extends " + mc.getInheritsFrom().getName();
		} else {
			return mc.getName();
		}
	}
}