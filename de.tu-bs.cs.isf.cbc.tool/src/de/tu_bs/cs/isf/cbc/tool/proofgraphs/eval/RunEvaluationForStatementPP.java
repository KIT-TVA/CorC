package de.tu_bs.cs.isf.cbc.tool.proofgraphs.eval;

import java.util.ArrayList;
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
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyMethodCallStatementPartialProofBegin;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyMethodCallStatementPartialProofComplete;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyOriginalCallStatementPartialProofBegin;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyOriginalCallStatementPartialProofComplete;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyStatementPartialProofBegin;
import de.tu_bs.cs.isf.cbc.tool.partialproof.VerifyStatementPartialProofComplete;
import de.tu_bs.cs.isf.cbc.util.Console;

public class RunEvaluationForStatementPP extends MyAbstractAsynchronousCustomFeature {
	
	public static ArrayList<String> WHOLE_RUNTIME_START = new ArrayList<String>();
	public static ArrayList<String> WHOLE_RUNTIME_COMPLETE = new ArrayList<String>();
	public static ArrayList<String> PROOF_RUNTIME_START = new ArrayList<String>();
	public static ArrayList<String> PROOF_RUNTIME_COMPLETE = new ArrayList<String>();
	public static ArrayList<String> NODES_START = new ArrayList<String>();
	public static ArrayList<String> NODES_COMPLETE = new ArrayList<String>();
	public static int currentProofTimes = 0;
	public static int currentProofNodes = 0;
	public static int current = 1;

	private Object monitor = new Object();

	public RunEvaluationForStatementPP(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Run Evaluation for Statement (PP)";
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
		clear();
		PictogramElement[] pes = context.getPictogramElements();
		IJobManager manager = Job.getJobManager();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);

				MyJobChangeListener listener = new MyJobChangeListener(20, bo, context);
				VerifyOriginalCallStatementPartialProofBegin ppOriginalBegin = new VerifyOriginalCallStatementPartialProofBegin(getFeatureProvider());
				VerifyStatementPartialProofBegin ppBegin = new VerifyStatementPartialProofBegin(getFeatureProvider());
				VerifyMethodCallStatementPartialProofBegin ppMethodCallStatement = new VerifyMethodCallStatementPartialProofBegin(getFeatureProvider());
				if (bo instanceof MethodStatement) {
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					ppMethodCallStatement.execute(context);

				} else if (bo instanceof OriginalStatement) {

					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					ppOriginalBegin.execute(context);
				} else {
					
					Console.println("==========ProofStart==========");
					manager.addJobChangeListener(listener);
					ppBegin.execute(context);

				}
		}
		
	}
	
	
	public static void clear() {
			RunEvaluationForStatementPP.WHOLE_RUNTIME_START.clear();
			RunEvaluationForStatementPP.WHOLE_RUNTIME_COMPLETE.clear();
			RunEvaluationForStatementPP.PROOF_RUNTIME_START.clear();
			RunEvaluationForStatementPP.PROOF_RUNTIME_COMPLETE.clear();
			RunEvaluationForStatementPP.NODES_START.clear();
			RunEvaluationForStatementPP.NODES_COMPLETE.clear();
			RunEvaluationForStatementPP.currentProofNodes = 0;
			RunEvaluationForStatementPP.currentProofTimes = 0;
			RunEvaluationForStatementPP.current = 1;
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
		VerifyStatementPartialProofBegin ppStatementBegin = new VerifyStatementPartialProofBegin(getFeatureProvider());
		VerifyStatementPartialProofComplete ppStatementComplete = new VerifyStatementPartialProofComplete(getFeatureProvider());
		VerifyOriginalCallStatementPartialProofBegin ppOriginalBegin = new VerifyOriginalCallStatementPartialProofBegin(getFeatureProvider());
		VerifyOriginalCallStatementPartialProofComplete ppOriginalComplete = new VerifyOriginalCallStatementPartialProofComplete(getFeatureProvider());
		VerifyMethodCallStatementPartialProofBegin ppMethodCallStatementBegin = new VerifyMethodCallStatementPartialProofBegin(getFeatureProvider());
		VerifyMethodCallStatementPartialProofComplete ppMethodCallStatementEnd = new VerifyMethodCallStatementPartialProofComplete(getFeatureProvider());
		if (
					 event.getJob().getName().equals(ppStatementBegin.getName())
					|| event.getJob().getName().equals(ppStatementComplete.getName())
					|| event.getJob().getName().equals(ppOriginalBegin.getName())
					|| event.getJob().getName().equals(ppOriginalComplete.getName())
					|| event.getJob().getName().equals(ppMethodCallStatementBegin.getName())
					|| event.getJob().getName().equals(ppMethodCallStatementEnd.getName())
				) {
		if (current < rounds) {

			if (proofStart && current != 1) {
				Console.println("===================== ROUND " + current/2 + "===================== ");
				PROOF_RUNTIME_COMPLETE.add(currentProofTimes + "");
				NODES_COMPLETE.add(currentProofNodes + "");
				currentProofTimes = 0;
				currentProofNodes = 0;
			} else {
				PROOF_RUNTIME_START.add(currentProofTimes + "");
				NODES_START.add(currentProofNodes+ "");
				currentProofTimes = 0;
				currentProofNodes = 0;
			}
			
				if (bo instanceof MethodStatement) {
					if (proofStart) {
						Console.println("==========ProofStart==========");
						ppMethodCallStatementBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						ppMethodCallStatementEnd.execute(context);
					}
				
				} else if (bo instanceof OriginalStatement) {
					if (proofStart) {
						Console.println("==========ProofStart==========");
						ppOriginalBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						ppOriginalComplete.execute(context);
					}
				} else {
					
					if (proofStart) {
						Console.println("==========ProofStart==========");
						ppStatementBegin.execute(context);
					} else {
						Console.println("==========ProofCompletion==========");
						ppStatementComplete.execute(context);
					}

			}
			proofStart = !proofStart;
		} else {
			PROOF_RUNTIME_COMPLETE.add(currentProofTimes + "");
			NODES_COMPLETE.add(currentProofNodes + "");

			Console.println(Arrays.toString(WHOLE_RUNTIME_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(WHOLE_RUNTIME_COMPLETE.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(PROOF_RUNTIME_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(PROOF_RUNTIME_COMPLETE.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(NODES_START.toArray()).replace("[", "").replace("]", ""));
			Console.println(Arrays.toString(NODES_COMPLETE.toArray()).replace("[", "").replace("]", ""));
			IJobManager manager = Job.getJobManager();
			manager.removeJobChangeListener(this);
		}
		current++;
			
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