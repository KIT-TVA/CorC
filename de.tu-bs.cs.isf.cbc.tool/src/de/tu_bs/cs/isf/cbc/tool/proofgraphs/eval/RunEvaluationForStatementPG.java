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

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyMethodCallStatement;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyMethodCallStatementProofGraphBegin;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyMethodCallStatementProofGraphComplete;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyOriginalCallStatementProofGraphBegin;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyOriginalCallStatementProofGraphComplete;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyPreSelectionStatementProofGraphBegin;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyPreSelectionStatementProofGraphComplete;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyStatementProofGraphBegin;
import de.tu_bs.cs.isf.cbc.tool.proofgraphs.VerifyStatementProofGraphComplete;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.Parser;

public class RunEvaluationForStatementPG extends MyAbstractAsynchronousCustomFeature {
	
	public RunEvaluationForStatementPG(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Run Evaluation for Statement (PG)";
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
				VerifyOriginalCallStatementProofGraphBegin pgOriginalBegin = new VerifyOriginalCallStatementProofGraphBegin(getFeatureProvider());
				VerifyStatementProofGraphBegin pgBegin = new VerifyStatementProofGraphBegin(getFeatureProvider());
				VerifyMethodCallStatementProofGraphBegin pgMethodBegin = new VerifyMethodCallStatementProofGraphBegin(getFeatureProvider());
				VerifyPreSelectionStatementProofGraphBegin pgSelection = new VerifyPreSelectionStatementProofGraphBegin(getFeatureProvider());
				MyJobChangeListener listener = new MyJobChangeListener(20, bo, context);

				if (bo instanceof SelectionStatement) {
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					pgSelection.execute(context);
				} else {
				String preFormula = Parser.getConditionFromCondition(((AbstractStatement) bo).getPreCondition().getName());
				String postFormula = Parser.getConditionFromCondition(((AbstractStatement) bo).getPostCondition().getName());
					if (bo instanceof VerifyMethodCallStatement) {
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					pgMethodBegin.execute(context);
				} else if (bo instanceof OriginalStatement || preFormula.contains("original") || postFormula.contains("original")) {
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					pgOriginalBegin.execute(context);
				} else {

					
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					pgBegin.execute(context);

				}

				}
						}
		
	}



private class MyJobChangeListener implements IJobChangeListener  {

	private final int rounds;
	private final Object bo;
	private final ICustomContext context;
	boolean proofStart = false;
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
		VerifyStatementProofGraphBegin pgBegin = new VerifyStatementProofGraphBegin(getFeatureProvider());
		VerifyStatementProofGraphComplete pgComplete = new VerifyStatementProofGraphComplete(getFeatureProvider());
		VerifyOriginalCallStatementProofGraphBegin pgOriginalBegin = new VerifyOriginalCallStatementProofGraphBegin(getFeatureProvider());
		VerifyOriginalCallStatementProofGraphComplete pgOriginalComplete = new VerifyOriginalCallStatementProofGraphComplete(getFeatureProvider());
		VerifyMethodCallStatementProofGraphBegin pgMethodBegin = new VerifyMethodCallStatementProofGraphBegin(getFeatureProvider());
		VerifyMethodCallStatementProofGraphComplete pgMethodComplete = new VerifyMethodCallStatementProofGraphComplete(getFeatureProvider());
		VerifyPreSelectionStatementProofGraphBegin pgSelectionBegin = new VerifyPreSelectionStatementProofGraphBegin(getFeatureProvider());
		VerifyPreSelectionStatementProofGraphComplete pgSelectionComplete = new VerifyPreSelectionStatementProofGraphComplete(getFeatureProvider());

		if (
					 event.getJob().getName().equals(pgBegin.getName())
				|| event.getJob().getName().equals(pgComplete.getName())
				|| event.getJob().getName().equals(pgOriginalBegin.getName())
				|| event.getJob().getName().equals(pgOriginalComplete.getName())
				|| event.getJob().getName().equals(pgMethodBegin.getName())
				|| event.getJob().getName().equals(pgMethodComplete.getName())
				|| event.getJob().getName().equals(pgSelectionBegin.getName())
				|| event.getJob().getName().equals(pgSelectionComplete.getName())
				) {
		if (RunEvaluationForStatementPP.current < rounds) {

			if (proofStart && RunEvaluationForStatementPP.current != 1) {
				Console.println("===================== ROUND " + RunEvaluationForStatementPP.current/2 + "===================== ");
				RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE.add(RunEvaluationForStatementPP.currentProofTimes + "");
				RunEvaluationForStatementPP.NODES_COMPLETE.add(RunEvaluationForStatementPP.currentProofNodes + "");
				RunEvaluationForStatementPP.currentProofTimes = 0;
				RunEvaluationForStatementPP.currentProofNodes = 0;
			} else {
				RunEvaluationForStatementPP.PROOF_RUNTIME_START.add(RunEvaluationForStatementPP.currentProofTimes + "");
				RunEvaluationForStatementPP.NODES_START.add(RunEvaluationForStatementPP.currentProofNodes + "");
				RunEvaluationForStatementPP.currentProofTimes = 0;
				RunEvaluationForStatementPP.currentProofNodes = 0;
			}
				if (bo instanceof SelectionStatement) {
					if (proofStart) {
						Console.println("==========ProofStart==========");
						pgSelectionBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						pgSelectionComplete.execute(context);
					}	
				} else {
					String preFormula = Parser.getConditionFromCondition(((AbstractStatement) bo).getPreCondition().getName());
			String postFormula = Parser.getConditionFromCondition(((AbstractStatement) bo).getPostCondition().getName());

			 if (bo instanceof MethodStatement) {
					if (proofStart) {
						Console.println("==========ProofStart==========");
						pgMethodBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						pgMethodComplete.execute(context);
					}	
			} else if (bo instanceof OriginalStatement || preFormula.contains("original") || postFormula.contains("original")) {
				if (proofStart) {
						Console.println("==========ProofStart==========");
						pgOriginalBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						pgOriginalComplete.execute(context);
					}
				} else {
				
					if (proofStart) {
						Console.println("==========ProofStart==========");
						pgBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						pgComplete.execute(context);
					}
				}			
				}

			proofStart = !proofStart;

		} else {
			RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE.add(RunEvaluationForStatementPP.currentProofTimes + "");
			RunEvaluationForStatementPP.NODES_COMPLETE.add(RunEvaluationForStatementPP.currentProofNodes + "");

			Console.println(Arrays.toString(RunEvaluationForStatementPP.WHOLE_RUNTIME_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(RunEvaluationForStatementPP.PROOF_RUNTIME_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(RunEvaluationForStatementPP.NODES_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(RunEvaluationForStatementPP.NODES_COMPLETE.toArray()).replace("[", "").replace("]", ""));
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