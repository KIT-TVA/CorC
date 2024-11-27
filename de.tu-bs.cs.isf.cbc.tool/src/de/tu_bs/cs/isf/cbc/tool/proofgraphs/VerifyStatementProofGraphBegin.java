package de.tu_bs.cs.isf.cbc.tool.proofgraphs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.ovgu.featureide.fm.core.io.velvet.VelvetParser.instanceImports_return;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval.RunEvaluationForStatementPP;
import de.tu_bs.cs.isf.cbc.tool.proofs.IKeYProof;
import de.tu_bs.cs.isf.cbc.tool.proofs.KeYProofProver;
import de.tu_bs.cs.isf.cbc.tool.proofs.strategies.ProofGraphStatementBeginStrategy;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.Parser;

public class VerifyStatementProofGraphBegin extends MyAbstractAsynchronousCustomFeature {
	
	public VerifyStatementProofGraphBegin(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement as proof node (begin)";
	}

	@Override
	public String getDescription() {
		return "Creates the partial proof for all feature nodes in this method";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
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
			boolean isReturnStatement = bo instanceof ReturnStatement;
			if (bo instanceof AbstractStatement statement) {

				URI uri = this.getDiagram().eResource().getURI();
				String preFormula = Parser.getConditionFromCondition(statement.getPreCondition().getName());
				String postFormula = Parser.getConditionFromCondition(statement.getPostCondition().getName());

				if (preFormula.contains("original") || postFormula.contains("original")) {
					VerifyOriginalCallStatementProofGraphBegin verify = new VerifyOriginalCallStatementProofGraphBegin(getFeatureProvider());
					verify.execute(context);
				} else {
					Console.println("No Variation within statement. Instantly starting verification!");
					FileHandler.instance.deleteFolder(uri, "tests");
					
					IKeYProof keyProof = new IKeYProof();
					keyProof.setDiagram(getDiagram());
					keyProof.setFeatureProvider(getFeatureProvider());
					keyProof.setProgressMonitor(monitor);
					keyProof.setStatement(statement);
					keyProof.setJavaVariables(generateJavaVariables());
					keyProof.setCbCFormulas(generateCbCFormulas());
					keyProof.setReturnStatement(isReturnStatement);
					
					KeYProofProver prover = new KeYProofProver(keyProof, new ProofGraphStatementBeginStrategy());
					statement.setProven(prover.prove());
				}

				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1_000_000;
				startTime = System.nanoTime();

				Console.println("\nVerification done."); 
				Console.println("Time needed: " + duration + "ms");
			}
		}
	}

	public List<JavaVariables> generateJavaVariables() {
		List<JavaVariables> variables = new ArrayList<JavaVariables>();

		for (Shape shape : this.getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				variables.add((JavaVariables) obj);
				break;
			}
		}

		return variables;
	}

	public List<CbCFormula> generateCbCFormulas() {
		List<CbCFormula> cbcFormulas = new ArrayList<CbCFormula>();

		for (Shape shape : this.getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof CbCFormula) {
				cbcFormulas.add((CbCFormula) obj);
				break;
			}
		}

		return cbcFormulas;
	}
}
	