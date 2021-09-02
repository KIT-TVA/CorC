package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
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
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbcclass.tool.diagram.CbCClassImageProvider;
import helper.ModelClassHelper;
import model.CbcClassUtil;


public class MethodClassPattern extends IdPattern implements IPattern {
	
	private static final String ID_NAME_TEXT = "classMethodNameText";
	private static final String ID_PRE_TEXT = "preConditionText";
	private static final String ID_POST_TEXT = "postConditionText";
	private static final String ID_STATEMENT_TEXT = "statementText";
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
	
		
	
	public MethodClassPattern() {
		super();
	}
	
	
	@Override
	public int getEditingType() {
	    return TYPE_TEXT;
	}
	
	@Override
	public String getCreateName() {
		return "Class Method";
	}

	@Override
	public String getCreateDescription() {
		return "Create a Class Method";
	}
	
	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		// TODO Auto-generated method stub
		//return mainBusinessObject instanceof MethodClass;
		return mainBusinessObject instanceof Method;
		
	}
	
	
	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof ModelClass;
	}
	
	
	// TODO: CHECK!
	@Override
	public Object[] create(ICreateContext context) {
		
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getTargetContainer());
		
		
		Method method = CbcclassFactory.eINSTANCE.createMethod();
		
		MethodClass methodClass = CbcmodelFactory.eINSTANCE.createMethodClass();
		methodClass.setMethodClass(getDiagram().getName());
		
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		formula.setName(getDiagram().getName());
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("{statement}");
		formula.setStatement(statement);
		
		// create and assign conditions
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName("{pre}");
		statement.setPreCondition(preCondition);
		
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName("{post}");
		statement.setPostCondition(postCondition);
		
		//TODO: ???
		//statement.setPreCondition(new );
		//classMethod.setPrecondition(pre);
		
		//MethodSignature signature = null;
		MethodSignature signature = CbcmodelFactory.eINSTANCE.createMethodSignature();
		signature.setMethodSignature("public int methodname");
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
		    if (obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			} 
		}
		/* if(signature != null) {
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
		
		*/
		
		method.setParentClass(modelClass);
		method.setSignature(signature.getMethodSignature());
		method.setCbcStartTriple(formula);
		method.setPrecondition(preCondition);
		method.setPostcondition(postCondition);
		
		modelClass.getMethods().add(method);
		ModelClassHelper.setObject(method);
		updatePictogramElement(context.getTargetContainer());
		


		/*
		try {
			CbcClassUtil.saveMethod(method, getDiagram());
			CbcClassUtil.saveCondition(preCondition, getDiagram());
			CbcClassUtil.saveCondition(postCondition, getDiagram());
			CbcClassUtil.saveMethodSignature(signature, getDiagram());
			CbcClassUtil.saveFormulaToModelFile(formula, getDiagram());
			
			
		}	catch (CoreException | IOException e) {
				e.printStackTrace();
		}
		
		*/

		
		
		return new Object[] { methodClass };
	}
	
	@Override
	public boolean canAdd(IAddContext context) {
		return super.canAdd(context) && context.getTargetContainer() instanceof Diagram;
		
	}
	
	
	// TODO: change behavior
		@Override
		public boolean canDirectEdit(IDirectEditingContext context) {
			Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
			GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
			if ((domainObject instanceof Method) && ga instanceof Text) {
				return true;
			}
			return false;

		}

	@Override
	protected PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		/*if (id.equals(ID_MAIN_RECTANGLE)) {
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
			if (statementToCheck.isProven() && image.getId().equals(CbCClassImageProvider.IMG_UNPROVEN)) {
				return Reason.createTrueReason("Statement is proven. Expected green color.");
			} else if (!statementToCheck.isProven() && image.getId().equals(CbCClassImageProvider.IMG_PROVEN)) {
				return Reason.createTrueReason("Statement is not proven. Expected red color.");
			}
		}
		*/
		return Reason.createFalseReason();
	}
	
	
	public boolean canUpdate(IdUpdateContext context) {
        Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
        return (bo instanceof Method);
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
				image.setId(CbCClassImageProvider.IMG_PROVEN);
			} else {
				image.setId(CbCClassImageProvider.IMG_UNPROVEN);
			}
		}
		return false;
	}
	
	
	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());


		method.setSignature(value.trim());

		ShapeImpl shape = (ShapeImpl)context.getPictogramElement();



		TextImpl text = (TextImpl)shape.getGraphicsAlgorithm();
		text.setValue(method.getSignature());
			
		updatePictogramElement(context.getPictogramElement());
	
	}
	
	
	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getPictogramElement());
		
		
		//ModelClass modelClass = method.getParentClass();
		
		
		ShapeImpl shape = (ShapeImpl)context.getPictogramElement();
		System.out.println(shape.getLink());

		
		return method.getSignature();
	}
}
