package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SelectionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPreSelectionStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPreSelectionStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the precondition of selection statement as proof graph (begin)";
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
		long startTime = System.nanoTime();

		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SelectionStatement) {

				SelectionStatement statement = (SelectionStatement) bo;
				AbstractStatement parent = statement.getParent();
				
				if (statement.getPreCondition().toString().contains("original")) {
					VerifyOriginalCallStatementProofGraphBegin original = new VerifyOriginalCallStatementProofGraphBegin(getFeatureProvider());
					original.execute(context);
				} else {
					boolean proven = false;
					String uriString = getDiagram().eResource().getURI().toPlatformString(true);
					URI uri = getDiagram().eResource().getURI();
					IProject project = FileUtil.getProjectFromFileInProject(uri);

					Console.println("Starting variational verification...\n");
					String callingClass = uri.segment(uri.segmentCount()-2) + "";
					String callingFeature = uri.segment(uri.segmentCount()-3) + "";
					String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";

					String[] featureConfig = VerifyFeatures.findValidProduct(List.of(callingFeature), project);
					String[][] variantWrapper = {featureConfig};

					GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
					VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());
					
					String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature, callingClass);
					for (int i = 0; i < variants.length; i++) {
						ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString), featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_BEGIN);
						genCode.setProofTypeInfo(i, KeYInteraction.ABSTRACT_PROOF_BEGIN);
						if(!genCode.generate(FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(), callingFeature, callingClass, callingMethod, variantWrapper[i])) continue;
						prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString), variantWrapper[i], i, KeYInteraction.ABSTRACT_PROOF_BEGIN);
						List<CbCFormula> refinements = verifyStmt.generateCbCFormulasForRefinements(variants[i], callingMethod);
						String configName = "";
						for (String s : variantWrapper[i]) configName += s;
						prove.setConfigName(configName);
						proven = prove.provePreSelWithKey(refinements, statement.getGuards(), parent.getPreCondition());
					}

					if (proven) {
						statement.setPreProve(true);
					} else {
						statement.setPreProve(false);
					}
					updatePictogramElement(((Shape)pes[0]).getContainer());				
				}
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
		monitor.done();	
	}
}
