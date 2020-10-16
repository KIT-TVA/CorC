package de.tu_bs.cs.isf.cbc.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCImageProvider;
import de.tu_bs.cs.isf.cbc.tool.model.CbcModelUtil;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;

/**
 * Class that creates the graphical representation of Conditions
 * 
 * @author Tobias
 *
 */
public class FormulaPattern extends IdPattern implements IPattern {

	private static final String ID_STATEMENT_TEXT = "statementNameText";
	private static final String ID_NAME_TEXT = "nameText";
	private static final String ID_PRE_TEXT = "preConditionText";
	private static final String ID_POST_TEXT = "postConditionText";
	private static final String ID_MAIN_RECTANGLE = "mainRectangle";
	private static final String ID_IMAGE_PROVEN = "imageproven";
	// Header:
	private static final String ID_PRE_HEADER = "preHeader";
	private static final String ID_ST_HEADER = "stHeader";
	private static final String ID_POST_HEADER = "postHeader";
	// lines:
	private static final String ID_HOR1_LINE = "hor1Line";
	private static final String ID_HOR2_LINE = "hor2Line";
	private static final String ID_VER1_LINE = "ver1Line";
	private static final String ID_VER2_LINE = "ver2Line";

	/**
	 * Constructor of the class
	 */
	public FormulaPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Formula";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Formula.";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof CbCFormula;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		CbCFormula formula = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			}
		}
		if (formula != null)
			return false;
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		formula.setName(getDiagram().getName());
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		formula.setStatement(statement);
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName("pre");
		statement.setPreCondition(preCondition);
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName("post");
		statement.setPostCondition(postCondition);




		
		MethodSignature signature = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
		    if (obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			} 
		}
		if(signature != null) {
			String value = signature.getMethodSignature();
			value = value.replace("public", "");
		    value = value.replace("static", "");
		    value = value.trim().split(" ", 2)[1];
		    if(!value.contains("()")) {
		    	String s2 = value.substring(value.indexOf('(') + 1, value.lastIndexOf(')'));
		    	String[] variableTypes = s2.split(",");
		    	s2 = variableTypes[0].trim().split(" ")[0];
		    	for(int i = 1; i < variableTypes.length; i++) {
		    		if(variableTypes[i].trim().split(" ")[0].equals("float"))
		    			s2 = s2 + ",double";
		    		else
		    			s2 = s2 + "," + variableTypes[i].trim().split(" ")[0];
		    	}
		    	value = value.substring(0, value.indexOf('(') + 1);
		    	value = value + s2 + ")";
		    }
			formula.setMethodName(value);
		}
		
		// Use the following instead of the above line to store the model
		// data in a seperate file parallel to the diagram file
		try {
			CbcModelUtil.saveFormulaToModelFile(formula, getDiagram());
		} catch (CoreException | IOException e) {
			e.printStackTrace();
		}
		addGraphicalRepresentation(context, formula);
		return new Object[] { formula };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		manageColor(IColorConstant.DARK_GREEN);
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		CbCFormula addedFormula = (CbCFormula) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		int width = context.getWidth() <= 0 ? 300 : context.getWidth();
		int height = context.getHeight() <= 0 ? 150 : context.getHeight();
		// header font:
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
		link(outerContainerShape, addedFormula);

		// Statement name
		Shape textShapePreCondition = peCreateService.createShape(outerContainerShape, true);
		MultiText preConditionText = gaService.createMultiText(textShapePreCondition, "");
		setId(preConditionText, ID_PRE_TEXT);
		preConditionText.setValue("{" + addedFormula.getStatement().getPreCondition().getName() + "}");
		preConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapeStatement = peCreateService.createShape(outerContainerShape, true);
		MultiText statementText = gaService.createMultiText(textShapeStatement, "");
		setId(statementText, ID_STATEMENT_TEXT);
		statementText.setValue(addedFormula.getStatement().getName());
		statementText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		statementText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapePostCondition = peCreateService.createShape(outerContainerShape, true);
		MultiText postConditionText = gaService.createMultiText(textShapePostCondition, "");
		postConditionText.setValue("{" + addedFormula.getStatement().getPostCondition().getName() + "}");
		setId(postConditionText, ID_POST_TEXT);
		postConditionText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postConditionText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);

		Shape textShapeName = peCreateService.createShape(outerContainerShape, false);
		MultiText nameText = gaService.createMultiText(textShapeName, "Formula");
		setId(nameText, ID_NAME_TEXT);
		nameText.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		nameText.setFont(headerFont);

		Shape proveShape = peCreateService.createShape(outerContainerShape, false);
		Image image = gaService.createImage(proveShape, CbCImageProvider.IMG_UNPROVEN);
		setId(image, ID_IMAGE_PROVEN);

		// Header:
		Shape preHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text preHeader = gaService.createText(preHeaderShape, "precondition");
		setId(preHeader, ID_PRE_HEADER);
		preHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		preHeader.setFont(headerFont);

		Shape postHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text postHeader = gaService.createText(postHeaderShape, "postcondition");
		setId(postHeader, ID_POST_HEADER);
		postHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		postHeader.setFont(headerFont);

		Shape stHeaderShape = peCreateService.createShape(outerContainerShape, false);
		Text stHeader = gaService.createText(stHeaderShape, "statement");
		setId(stHeader, ID_ST_HEADER);
		stHeader.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		stHeader.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
		stHeader.setFont(headerFont);
		// lines:
		Shape hor1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor1Polyline = gaService.createPolyline(hor1LineShape);
		setId(hor1Polyline, ID_HOR1_LINE);

		Shape hor2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline hor2Polyline = gaService.createPolyline(hor2LineShape);
		setId(hor2Polyline, ID_HOR2_LINE);

		Shape ver1LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver1Polyline = gaService.createPolyline(ver1LineShape);
		setId(ver1Polyline, ID_VER1_LINE);

		Shape ver2LineShape = peCreateService.createShape(outerContainerShape, false);
		Polyline ver2Polyline = gaService.createPolyline(ver2LineShape);
		setId(ver2Polyline, ID_VER2_LINE);

		peCreateService.createChopboxAnchor(textShapeStatement);

		link(outerContainerShape, addedFormula);
		link(getDiagram(), addedFormula);
		link(textShapePreCondition, addedFormula.getStatement().getPreCondition());
		link(textShapeStatement, addedFormula.getStatement());
		link(textShapePostCondition, addedFormula.getStatement().getPostCondition());
		link(proveShape, addedFormula);

		return outerContainerShape;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		boolean changesDone = false;

		GraphicsAlgorithm mainRectangle = context.getRootPictogramElement().getGraphicsAlgorithm();
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		int third = mainRectangle.getWidth() / 3;
		// stable sizes from Name and Header save space when the diagram gets big!
		int sizeName = 30; // size from Formular block
		int sizeHeader = 20; // size from the Header
		int positionHeader = 40; // position where the Header is
		int sizeText = mainRectangle.getHeight() - positionHeader - sizeHeader; // size from the blocks (pre, statement,
																				// post)
		int positionText = positionHeader + sizeHeader; // position from the blocks (pre, statement, post)

		if (id.equals(ID_NAME_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, 5, mainRectangle.getWidth(), sizeName);
			changesDone = true;
		} else if (id.equals(ID_PRE_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionText, third, sizeText);
			changesDone = true;
		} else if (id.equals(ID_STATEMENT_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, positionText, third, sizeText);
			changesDone = true;
		} else if (id.equals(ID_POST_TEXT)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, positionText, third, sizeText);
			changesDone = true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			Graphiti.getGaService().setLocationAndSize(ga, mainRectangle.getWidth() - 20, 10, 10, 10);
			changesDone = true;
		} else if (id.equals(ID_PRE_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, 0, positionHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_ST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third, positionHeader, third, sizeHeader);
			changesDone = true;
		} else if (id.equals(ID_POST_HEADER)) {
			Graphiti.getGaService().setLocationAndSize(ga, third * 2, positionHeader, third, sizeHeader);
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
			List<Point> pointList = Graphiti.getGaService().createPointList(new int[] { 0, positionHeader + sizeHeader,
					mainRectangle.getWidth(), positionHeader + sizeHeader });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER1_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { third, positionHeader, third, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		} else if (id.equals(ID_VER2_LINE)) {
			Polyline polyline = (Polyline) ga;
			polyline.getPoints().clear();
			List<Point> pointList = Graphiti.getGaService()
					.createPointList(new int[] { third * 2, positionHeader, third * 2, mainRectangle.getHeight() });
			polyline.getPoints().addAll(pointList);
			changesDone = true;
		}

		return changesDone;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			CbCFormula domainObject = (CbCFormula) context.getDomainObject();
			AbstractStatement statement = domainObject.getStatement();
			AbstractStatement statementToCheck = null;
			if (statement.getRefinement() != null) {
				statementToCheck = statement.getRefinement();
			} else {
				statementToCheck = statement;
			}
			if (statementToCheck.isProven() && ((rectangle.getForeground() != null
					&& !rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN)))
					|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!statementToCheck.isProven() && ((rectangle.getForeground() != null
					&& rectangle.getForeground().equals(manageColor(IColorConstant.DARK_GREEN)))
					|| rectangle.getForeground() == null)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			CbCFormula domainObject = (CbCFormula) context.getDomainObject();
			AbstractStatement statement = domainObject.getStatement();
			AbstractStatement statementToCheck = null;
			if (statement.getRefinement() != null) {
				statementToCheck = statement.getRefinement();
			} else {
				statementToCheck = statement;
			}
			Image image = (Image) context.getGraphicsAlgorithm();
			if (statementToCheck.isProven() && image.getId().equals(CbCImageProvider.IMG_UNPROVEN)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!statementToCheck.isProven() && image.getId().equals(CbCImageProvider.IMG_PROVEN)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		}
		// if (id.equals(ID_PRE_TEXT)) {
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// Condition domainObject = (Condition) context.getDomainObject();
		//// if (domainObject.getName() == null ||
		// !domainObject.getName().equals(nameText.getValue())) {
		//// return Reason.createTrueReason("Name differs. Expected: '" +
		// domainObject.getName() + "'");
		//// }
		// } else if (id.equals(ID_STATEMENT_TEXT)) {
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// AbstractStatement domainObject = (AbstractStatement)
		// context.getDomainObject();
		//// if (domainObject.getName() == null ||
		// !domainObject.getName().equals(nameText.getValue())) {
		//// return Reason.createTrueReason("Name differs. Expected: '" +
		// domainObject.getName() + "'");
		//// }
		// } else if (id.equals(ID_POST_TEXT)) {
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// Condition domainObject = (Condition) context.getDomainObject();
		//// if (domainObject.getName() == null ||
		// !domainObject.getName().equals(nameText.getValue())) {
		//// return Reason.createTrueReason("Name differs. Expected: '" +
		// domainObject.getName() + "'");
		//// }
		// }

		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (id.equals(ID_MAIN_RECTANGLE)) {
			RoundedRectangle rectangle = (RoundedRectangle) context.getGraphicsAlgorithm();
			CbCFormula domainObject = (CbCFormula) context.getDomainObject();
			AbstractStatement statement = domainObject.getStatement();
			AbstractStatement statementToCheck = null;
			if (statement.getRefinement() != null) {
				statementToCheck = statement.getRefinement();
			} else {
				statementToCheck = statement;
			}
			if (statementToCheck.isProven()) {
				domainObject.setProven(true);
				rectangle.setForeground(manageColor(IColorConstant.DARK_GREEN));
				// updateParent(domainObject, true); MethodStatement of other diagram
			} else {
				domainObject.setProven(false);
				// updateParent(domainObject, false);
				rectangle.setForeground(manageColor(IColorConstant.RED));
			}
			return true;
		} else if (id.equals(ID_IMAGE_PROVEN)) {
			CbCFormula domainObject = (CbCFormula) context.getDomainObject();
			Image image = (Image) context.getGraphicsAlgorithm();
			AbstractStatement statement = domainObject.getStatement();
			AbstractStatement statementToCheck = null;
			if (statement.getRefinement() != null) {
				statementToCheck = statement.getRefinement();
			} else {
				statementToCheck = statement;
			}
			if (statementToCheck.isProven()) {
				image.setId(CbCImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCImageProvider.IMG_UNPROVEN);
			}
		}
		// if (id.equals(ID_PRE_TEXT)) {
		// updatePictogramElement(context.getPictogramElement());
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// Condition domainObject = (Condition) context.getDomainObject();
		//// nameText.setValue(domainObject.getName());
		// return true;
		// } else if (id.equals(ID_STATEMENT_TEXT)) {
		// updatePictogramElement(context.getPictogramElement());
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// AbstractStatement domainObject = (AbstractStatement)
		// context.getDomainObject();
		//// nameText.setValue(domainObject.getName());
		// return true;
		// } else if (id.equals(ID_POST_TEXT)) {
		// updatePictogramElement(context.getPictogramElement());
		//// MultiText nameText = (MultiText) context.getGraphicsAlgorithm();
		//// Condition domainObject = (Condition) context.getDomainObject();
		//// nameText.setValue(domainObject.getName());
		// return true;
		// }
		return false;
	}

	// private void updateParent(CbCFormula formula, boolean proven) {
	// final Collection<Diagram> allDiagrams = getDiagrams();
	// for (final Diagram d : allDiagrams) {
	// final Diagram currentDiagram = getDiagram();
	// if (!EcoreUtil.equals(currentDiagram, d)) { // always filter out the
	// // current
	// // diagram
	// final Collection<MethodStatement> statements = new
	// HashSet<MethodStatement>();
	// final Object businessObjectForDiagram =
	// getBusinessObjectForPictogramElement(d);
	// if (businessObjectForDiagram instanceof CbCFormula) {
	// final CbCFormula formula2 = (CbCFormula) businessObjectForDiagram;
	// if (formula2 != null) {
	// TreeIterator<EObject> iterator = formula2.eAllContents();
	// while (iterator.hasNext()) {
	// EObject object = iterator.next();
	// if (object instanceof MethodStatement) {
	// MethodStatement statement = (MethodStatement) object;
	// if (formula.getName().equals(statement.getName())) {
	// statements.add(statement);
	// }
	// }
	// }
	// }
	// }
	// IPeService pe = Graphiti.getPeService();
	// for (MethodStatement statement : statements) {
	// statement.setProven(proven);
	// EObject[] objArray = {statement};
	// Object[] obj = pe.getLinkedPictogramElements(objArray, d);
	// if (obj.length > 0) {
	// Shape pElement = (Shape) obj[0];
	// if (pElement != null) updatePictogramElement(pElement);
	// }
	// try {
	// URI uri = d.eResource().getURI();
	// uri = uri.trimFragment();
	// uri = uri.trimFileExtension();
	// uri = uri.appendFileExtension("cbcmodel");
	// ResourceSet rSet = d.eResource().getResourceSet();
	// Resource modelResource = rSet.getResource(uri, false);
	// modelResource.save(Collections.EMPTY_MAP);
	// modelResource.setTrackingModification(true);
	// d.eResource().save(Collections.EMPTY_MAP);
	// d.eResource().setTrackingModification(true);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// }
	//
	// private Collection<Diagram> getDiagrams() {
	// Collection<Diagram> result = Collections.emptyList();
	// Resource resource = getDiagram().eResource();
	// URI uri = resource.getURI();
	// URI uriTrimmed = uri.trimFragment();
	// if (uriTrimmed.isPlatformResource()){
	// String platformString = uriTrimmed.toPlatformString(true);
	// IResource fileResource = ResourcesPlugin.getWorkspace()
	// .getRoot().findMember(platformString);
	// if (fileResource != null){
	// IProject project = fileResource.getProject();
	// result = GetDiagramUtil.getDiagrams(project);
	// }
	// }
	// return result;
	// }
}
