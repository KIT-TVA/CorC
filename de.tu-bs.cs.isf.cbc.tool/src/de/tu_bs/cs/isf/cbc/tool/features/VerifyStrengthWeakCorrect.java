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
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
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
public class VerifyStrengthWeakCorrect extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyStrengthWeakCorrect(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify strengthening and weakening are correct.";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the precondition and post implies post.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && bo instanceof StrengthWeakStatement) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify strengtening and weakening", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof StrengthWeakStatement) {
				StrengthWeakStatement statement = (StrengthWeakStatement) bo;
				if (statement.getParent() != null) {
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
					boolean proven1 = false;
					boolean proven2 = false;
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
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, formula, new FileUtil(uriString));
					if (isVariational) {
						Console.println("--------------- Triggered variational verification ---------------");
						String callingClass = uri.segment(uri.segmentCount()-2) + "";
						String callingFeature = uri.segment(uri.segmentCount()-3) + "";
						String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
						String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false);				
						VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());		
						for (int i = 0; i < featureConfigs.length; i++) {
							verifyStmt.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
							proven1 = prove.proveCImpliesCWithKey(parent.getPreCondition(), statement.getPreCondition(), i);
							proven2 = prove.proveCImpliesCWithKey(statement.getPostCondition(), parent.getPostCondition(), i);
						}
					} else {
						Console.println("--------------- Triggered verification ---------------");
						proven1 = prove.proveCImpliesCWithKey(parent.getPreCondition(), statement.getPreCondition(), 0);
						proven2 = prove.proveCImpliesCWithKey(statement.getPostCondition(), parent.getPostCondition(), 0);
					}		
					Console.println("--------------- Verification completed --------------- ");
					
					if (proven1 && proven2) {
						statement.setProven(true);
					} else {
						statement.setProven(false);
					}
					updatePictogramElement(((Shape)pes[0]).getContainer());
				}
			}
		}
		monitor.done();
	}
}