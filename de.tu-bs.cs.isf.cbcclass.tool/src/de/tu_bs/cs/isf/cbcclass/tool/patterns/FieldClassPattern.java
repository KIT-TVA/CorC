package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.impl.TextImpl;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.util.ClassUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class FieldClassPattern extends IdPattern implements IPattern {

	private static final String ID_FIELD_TEXT = "fieldText";

	public FieldClassPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Class Field";
	}

	@Override
	public String getCreateDescription() {
		return "Create a class field: \n[modifier] [static] [final] type name \n[] means optional.\nExample: private final int[] number";
	}

	@Override
	protected PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof ModelClass;
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Field;
	}

	public Object[] create(ICreateContext context) {
		ModelClass modelClass = (ModelClass) getBusinessObjectForPictogramElement(context.getTargetContainer());
		Field field = CbcclassFactory.eINSTANCE.createField();
		field.setVisibility(Visibility.PUBLIC);
		Random random = new Random();
		field.setName("field" + random.nextInt(100000));
		field.setType("int[]");
		field.setIsFinal(false);
		field.setIsStatic(false);
		modelClass.getFields().add(field);
		updatePictogramElement(context.getTargetContainer());
		return new Object[]{field};
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		String split[] = value.trim().split(" ");
		int pointer = 1;

		// check for visibility modifier
		switch (split[0].toLowerCase()) {
			case "private" :
				field.setVisibility(Visibility.PRIVATE);
				break;
			case "public" :
				field.setVisibility(Visibility.PUBLIC);
				break;
			case "protected" :
				field.setVisibility(Visibility.PROTECTED);
				break;
			case "package" :
				field.setVisibility(Visibility.PACKAGE);
				break;
			default :
				field.setVisibility(Visibility.PUBLIC);
				pointer = 0;
				break;
		}
		// check for final and static modifier
		if (Arrays.stream(split).anyMatch("static"::equalsIgnoreCase)) {
			field.setIsStatic(true);
			pointer++;
		} else
			field.setIsStatic(false);
		if (Arrays.stream(split).anyMatch("final"::equalsIgnoreCase)) {
			field.setIsFinal(true);
			pointer++;
		} else
			field.setIsFinal(false);

		// set type and name of field
		field.setType(split[pointer++]);
		field.setName(split[pointer]);

		ShapeImpl shape = (ShapeImpl) context.getPictogramElement();
		TextImpl text = (TextImpl) shape.getGraphicsAlgorithm();
		text.setValue(field.getDisplayedName());

		// refresh project
		URI uri = getDiagram().eResource().getURI();
		ClassUtil.refreshProject(FileUtil.getProjectLocation(uri));
		String path = FileUtil.getProjectLocation(uri).replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path)
					|| (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		updatePictogramElement(context.getPictogramElement());
	}

	@Override
	public int getEditingType() {
		return TYPE_TEXT;
	}

	public boolean canAdd(IAddContext context) {
		return false;
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value == null || value.length() == 0) {
			return "Variable must not be empty";
		} else if (value.length() > 0 && !value.toLowerCase()
				.matches("(public\\s|private\\s|protected\\s|)" + "(static\\s|final\\s)?" + "(final\\s|static\\s)?"
						+ "(int|char|float|long|boolean|byte|short|double|([A-Za-z]\\w*))(\\[\\])?\\s[a-zA-Z]\\w*")) {
			return "Field must contain a type and a name";
		}

		if (hasKeywordsAsTypeOrName(value)) {
			return "Keywords aren't allowed as a data type or field name";
		}
		return null;
	}

	private boolean hasKeywordsAsTypeOrName(String value) {
		ArrayList<String> keywords = new ArrayList<>(
				Arrays.asList("public", "private", "protected", "static", "final"));
		String[] tokens = value.split(" ");
		if (tokens.length > 1) {
			String lastValue = tokens[tokens.length - 1].trim();
			String penultimateValue = tokens[tokens.length - 2].trim();
			if (keywords.contains(lastValue) || keywords.contains(penultimateValue)) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}

	public boolean canUpdate(IdUpdateContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return (bo instanceof Field);
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext context, String id) {
		Text nameText = (Text) context.getPictogramElement().getGraphicsAlgorithm();
		Field domainObject = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (domainObject.getName() == null
				|| !nameText.getValue().replace(" inherited", "").equals(domainObject.getDisplayedName())) {
			return Reason.createTrueReason("Name differs. Expected: '" + domainObject.getName() + "'");
		}
		return Reason.createFalseReason();
	}

	@Override
	protected boolean update(IdUpdateContext context, String id) {
		if (!id.equals(ID_FIELD_TEXT)) {
			Text nameText = (Text) context.getGraphicsAlgorithm();
			Field domainObject = (Field) context.getDomainObject();
			nameText.setValue(domainObject.getDisplayedName());
			return true;
		}
		return false;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		Object domainObject = getBusinessObjectForPictogramElement(context.getPictogramElement());
		GraphicsAlgorithm ga = context.getGraphicsAlgorithm();
		if ((domainObject instanceof Field || domainObject instanceof JavaVariable) && ga instanceof Text) {
			Field f = (Field) domainObject;
			ModelClass mc = (ModelClass) f.eContainer();
			if (mc.getName().equals(getDiagram().getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return field.getDisplayedName();
	}

	@Override
	public boolean canRemove(IRemoveContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		Text text = (Text) shape.getGraphicsAlgorithm();
		if (text.getValue().trim().endsWith("inherited") || text.getValue().trim().startsWith("PUBLIC")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean canDelete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		Text text = (Text) shape.getGraphicsAlgorithm();
		Field f = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (text.getValue().trim().endsWith("inherited")) {
			return false;
		}
		if (!(f.eContainer() instanceof ModelClass) && text.getValue().trim().startsWith("PUBLIC")) {
			return false;
		}
		return true;
	}

	@Override
	public void delete(IDeleteContext context) {
		Shape shape = (Shape) context.getPictogramElement();
		ContainerShape container = shape.getContainer();

		Field field = (Field) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (field.eContainer() != null && field.eContainer() instanceof ModelClass) {
			int indexToDelete = getIndex(shape.getGraphicsAlgorithm());
			for (Shape childShape : container.getChildren()) {
				if (getIndex(childShape.getGraphicsAlgorithm()) > indexToDelete) {
					setIndex(childShape.getGraphicsAlgorithm(), getIndex(childShape.getGraphicsAlgorithm()) - 1);
				}
			}
			super.delete(context);
			layoutPictogramElement(container);
		}
	}
}
