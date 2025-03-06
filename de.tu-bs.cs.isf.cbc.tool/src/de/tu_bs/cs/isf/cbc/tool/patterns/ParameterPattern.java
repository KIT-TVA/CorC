package de.tu_bs.cs.isf.cbc.tool.patterns;

import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.id.IdLayoutContext;
import org.eclipse.graphiti.pattern.id.IdPattern;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;

import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;

/**
 * Class that creates the graphical representation of Methods
 * 
 * @author Tobias
 *
 */
public class ParameterPattern extends IdPattern implements IPattern {
	public static final String VARIABLE_KIND_PARAMETER = "param";
	public static final String VARIABLE_KIND_RETURN = "return";
	public static final String VARIABLE_KIND_GLOBAL = "global";
	public static final String VARIABLE_KIND_GLOBAL_PARAM = "global param";
	public static final String VARIABLE_KIND_RETURNPARAM = "returnparam";

	public static final String VARIABLE_KIND_LOCAL = "local";

	/**
	 * Constructor of the class
	 */
	public ParameterPattern() {
		super();
	}

	@Override
	public String getCreateName() {
		return "Parameter";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Parameter;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return false;
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return false;
	}

	@Override
	public PictogramElement doAdd(IAddContext context) {
		return null;
	}

	@Override
	protected boolean layout(IdLayoutContext context, String id) {
		return false;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		return false;
	}

	@Override
	public boolean canDelete(IDeleteContext context) {
		return false;
	}

	@Override
	protected boolean update(IdUpdateContext arg0, String arg1) {
		return false;
	}

	@Override
	protected IReason updateNeeded(IdUpdateContext arg0, String arg1) {
		return Reason.createFalseReason();
	}
}