package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.platform.IDiagramBehavior;

import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

/**
 * Class that allows to add and edit name of statements
 * 
 * @author Tobias
 *
 */
public class RenameConditionFeature extends AbstractCustomFeature {

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public RenameConditionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Edit Name of a Condition";
	}

	@Override
	public String getDescription() {
		return "Edit Name of a Condition";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Condition) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Condition) {
				IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
				directEditingInfo.setMainPictogramElement(pes[0]);
				directEditingInfo.setPictogramElement(pes[0]);
				directEditingInfo.setGraphicsAlgorithm(pes[0].getGraphicsAlgorithm());
				directEditingInfo.setActive(true);

				IFeatureProvider prov = getFeatureProvider();
				IDiagramTypeProvider diagtyp = prov.getDiagramTypeProvider();
				IDiagramBehavior diagrbeh = diagtyp.getDiagramBehavior();
				diagrbeh.refresh();
				getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}
}