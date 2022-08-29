package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPreSelectionStatement extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPreSelectionStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the precondition of selection statement";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the alle guards of the selection statement.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo.getClass().equals(SelectionStatementImpl.class)) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SelectionStatement) {
				SelectionStatement statement = (SelectionStatement) bo;
				AbstractStatement parent = statement.getParent();
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				if (!DataCollector.checkForId(statement)) return;
				boolean proven = false;
				String uriString = getDiagram().eResource().getURI().toPlatformString(true);
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				try {
					if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
				ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString), null);
				if (isVariational) {
					Console.println("--------------- Triggered variational verification ---------------");
					String callingClass = uri.segment(uri.segmentCount()-2) + "";
					String callingFeature = uri.segment(uri.segmentCount()-3) + "";
					String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
					String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false);				
					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
					for (int i = 0; i < featureConfigs.length; i++) {
						prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString), featureConfigs[i]);
						genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
						String configName = "";
						for (String s : featureConfigs[i]) configName += s;
						prove.setConfigName(configName);
						proven = prove.provePreSelWithKey(statement.getGuards(), parent.getPreCondition());
					}
				} else {
					Console.println("--------------- Triggered verification ---------------");
					proven = prove.provePreSelWithKey(statement.getGuards(), parent.getPreCondition());
					}		
				Console.println("--------------- Verification completed --------------- ");
						
				if (proven) {
					statement.setPreProve(true);
				} else {
					statement.setPreProve(false);
				}
				updatePictogramElement(((Shape)pes[0]).getContainer());
			}
		}
		monitor.done();
	}
}