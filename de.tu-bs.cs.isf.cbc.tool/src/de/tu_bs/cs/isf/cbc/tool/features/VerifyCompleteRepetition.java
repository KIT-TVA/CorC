package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.impl.RepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyCompleteRepetition extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyCompleteRepetition(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify all three properties of the repetition.";
	}

	@Override
	public String getDescription() {
		return "Verifies all three properties of the repetition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(RepetitionStatementImpl.class) || bo.getClass().equals(SmallRepetitionStatementImpl.class))) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify pre post and variant", IProgressMonitor.UNKNOWN);
		ICustomFeature custom = new VerifyPreRepetitionStatement(getFeatureProvider());
		custom.execute(context);
		custom = new VerifyPostRepetitionStatement(getFeatureProvider());
		custom.execute(context);
		custom = new VerifyVariant3(getFeatureProvider());
		custom.execute(context);
		
		monitor.done();
	}
}