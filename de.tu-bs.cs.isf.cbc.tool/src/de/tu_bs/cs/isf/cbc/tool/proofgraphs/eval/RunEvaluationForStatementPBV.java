package de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval;

import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyMethodCallStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyOriginalCallStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPreSelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.MyAbstractAsynchronousCustomFeature;

public class RunEvaluationForStatementPBV extends MyAbstractAsynchronousCustomFeature {

	public RunEvaluationForStatementPBV(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Run Evaluation for Statement (PBV)";
	}

	@Override
	public String getDescription() {
		return "Runs the Eval for the statement";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		return pes != null && pes.length == 1;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		RunEvaluationForStatementPP.clear();
		PictogramElement[] pes = context.getPictogramElements();
		IJobManager manager = Job.getJobManager();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);

			VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
			VerifyOriginalCallStatement verifyOriginalCall = new VerifyOriginalCallStatement(getFeatureProvider());
			VerifyMethodCallStatement verifyMethodCall = new VerifyMethodCallStatement(getFeatureProvider());
			VerifyPreSelectionStatement pgSelection = new VerifyPreSelectionStatement(getFeatureProvider());
			MyJobChangeListener listener = new MyJobChangeListener(10, bo, context);
			if (bo instanceof SelectionStatement) {
				Console.println("==========ProofStart==========");
				manager.addJobChangeListener(listener);
				pgSelection.execute(context);
			} else if (bo instanceof MethodStatement) {
				Console.println("==========ProofStart==========");
				manager.addJobChangeListener(listener);
				verifyMethodCall.execute(context);

			} else if (bo instanceof OriginalStatement) {

				Console.println("==========ProofStart==========");
				manager.addJobChangeListener(listener);
				verifyOriginalCall.execute(context);
			} else {

				Console.println("==========ProofStart==========");
				manager.addJobChangeListener(listener);
				verifyStatement.execute(context);

			}
		}

	}

	private class MyJobChangeListener implements IJobChangeListener {

		private final int rounds;
		private final Object bo;
		private final ICustomContext context;
		public MyJobChangeListener(int rounds, Object bo, ICustomContext context) {
			this.rounds = rounds;
			this.bo = bo;
			this.context = context;
		}

		@Override
		public void aboutToRun(IJobChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void awake(IJobChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void done(IJobChangeEvent event) {
			VerifyStatement verifyStatement = new VerifyStatement(getFeatureProvider());
			VerifyOriginalCallStatement verifyOriginalCall = new VerifyOriginalCallStatement(getFeatureProvider());
			VerifyMethodCallStatement verifyMethodCall = new VerifyMethodCallStatement(getFeatureProvider());
			VerifyPreSelectionStatement pgSelection = new VerifyPreSelectionStatement(getFeatureProvider());
			if (event.getJob().getName().equals(verifyStatement.getName())
					|| event.getJob().getName().equals(verifyOriginalCall.getName())
					|| event.getJob().getName().equals(verifyMethodCall.getName())
					|| event.getJob().getName().equals(pgSelection.getName())) {
				if (RunEvaluationForStatementPP.current < rounds) {

					Console.println("===================== ROUND " + RunEvaluationForStatementPP.current
							+ "===================== ");
					RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE
							.add(RunEvaluationForStatementPP.currentProofTimes + "");
					RunEvaluationForStatementPP.NODES_COMPLETE.add(RunEvaluationForStatementPP.currentProofNodes + "");
					RunEvaluationForStatementPP.currentProofTimes = 0;
					RunEvaluationForStatementPP.currentProofNodes = 0;
					if (bo instanceof SelectionStatement) {
						Console.println("==========ProofCompletion==========");
						pgSelection.execute(context);
					} else if (bo instanceof MethodStatement) {
						Console.println("==========ProofCompletion==========");
						verifyMethodCall.execute(context);

					} else if (bo instanceof OriginalStatement) {
						Console.println("==========ProofCompletion==========");
						verifyOriginalCall.execute(context);
					} else {

						Console.println("==========ProofCompletion==========");
						verifyStatement.execute(context);
					}

				} else {
					RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE
							.add(RunEvaluationForStatementPP.currentProofTimes + "");
					RunEvaluationForStatementPP.NODES_COMPLETE.add(RunEvaluationForStatementPP.currentProofNodes + "");

					Console.println(Arrays.toString(RunEvaluationForStatementPP.WHOLE_RUNTIME_START.toArray())
							.replace("[", "").replace("]", ""));
					Console.println(Arrays.toString(RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.toArray())
							.replace("[", "").replace("]", ""));
					Console.println(Arrays.toString(RunEvaluationForStatementPP.PROOF_RUNTIME_START.toArray())
							.replace("[", "").replace("]", ""));
					Console.println(Arrays.toString(RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE.toArray())
							.replace("[", "").replace("]", ""));
					Console.println(Arrays.toString(RunEvaluationForStatementPP.NODES_START.toArray()).replace("[", "")
							.replace("]", ""));
					Console.println(Arrays.toString(RunEvaluationForStatementPP.NODES_COMPLETE.toArray())
							.replace("[", "").replace("]", ""));
					IJobManager manager = Job.getJobManager();
					manager.removeJobChangeListener(this);
				}
				RunEvaluationForStatementPP.current++;
			}

		}

		@Override
		public void running(IJobChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void scheduled(IJobChangeEvent event) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sleeping(IJobChangeEvent event) {
			// TODO Auto-generated method stub

		}

	}
}