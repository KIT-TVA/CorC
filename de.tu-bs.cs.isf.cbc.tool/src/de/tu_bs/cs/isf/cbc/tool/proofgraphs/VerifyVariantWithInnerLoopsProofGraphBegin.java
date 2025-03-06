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
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.SmallRepetitionStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public class VerifyVariantWithInnerLoopsProofGraphBegin extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyVariantWithInnerLoopsProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the variant (splitted) as proof graph (begin)";
	}

	@Override
	public String getDescription() {
		return "Verifies the variant of a repetition statement as partial proof (begin).";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(SmallRepetitionStatementImpl.class))) {
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
			if (bo instanceof SmallRepetitionStatement) {

				SmallRepetitionStatement statement = (SmallRepetitionStatement) bo;
				AbstractStatement parent = statement.getParent();

				boolean proven = false;
				String uriString = getDiagram().eResource().getURI().toPlatformString(true);
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);

				Console.println("Starting variational verification...\n");
				String callingClass = uri.segment(uri.segmentCount() - 2) + "";
				String callingFeature = uri.segment(uri.segmentCount() - 3) + "";
				String callingMethod = uri.trimFileExtension().segment(uri.segmentCount() - 1) + "";

				String[] featureConfig = VerifyFeatures.findValidProduct(List.of(callingFeature), project);
				String[][] variantWrapper = {featureConfig};

				GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(
						super.getFeatureProvider());
				VerifyStatement verifyStmt = new VerifyStatement(super.getFeatureProvider());

				String[] variants = verifyStmt.generateVariantsStringFromFeatureConfigs(variantWrapper, callingFeature,
						callingClass);
				for (int i = 0; i < variants.length; i++) {
					ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(uriString),
							featureConfig, 0, KeYInteraction.ABSTRACT_PROOF_FULL);
					genCode.setProofTypeInfo(i, KeYInteraction.ABSTRACT_PROOF_BEGIN);
					if (!genCode.generate(
							FileUtil.getProjectFromFileInProject(getDiagram().eResource().getURI()).getLocation(),
							callingFeature, callingClass, callingMethod, variantWrapper[i]))
						continue;
					String configName = "";
					for (String s : variantWrapper[i])
						configName += s;
					prove.setConfigName(configName);
					proven = prove.proveVariantWithKey(ConstructCodeBlock.constructCodeBlockAndVerify(statement, true),
							statement.getInvariant(), statement.getGuard(), statement.getVariant());
				}

				statement.setPostProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		RunEvaluationForStatementPP.WHOLE_RUNTIME_START.add(duration + ""); // PG DEBUG
		Console.println("\nVerification done.");
		Console.println("Time needed: " + duration + "ms");
		monitor.done();
	}
}