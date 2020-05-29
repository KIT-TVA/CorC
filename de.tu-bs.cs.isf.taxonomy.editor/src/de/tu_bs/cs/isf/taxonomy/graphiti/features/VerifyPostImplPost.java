package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming;
import de.tu_bs.cs.isf.toolkit.support.output.Console;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyPostImplPost extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyPostImplPost(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the postconditions";
	}

	@Override
	public String getDescription() {
		return "Verifies that the postcondition of the child implies the postcondition of the parent.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 2) {
			Object first = getBusinessObjectForPictogramElement(pes[0]);
			Object second = getBusinessObjectForPictogramElement(pes[1]);
			if (first != null && first instanceof Method && second != null && second instanceof Method) {
				ret = true;
			} else if (first != null && first instanceof Algorithm && second != null && second instanceof Algorithm) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify post->post", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 2) {
			Object first = getBusinessObjectForPictogramElement(pes[0]);
			Object second = getBusinessObjectForPictogramElement(pes[1]);
			JavaVariables vars = null;
			GlobalConditions conds = null;
			Renaming renaming = null;
			for (Shape shape : getDiagram().getChildren()) {
				Object obj = getBusinessObjectForPictogramElement(shape);
				if (obj instanceof JavaVariables) {
					vars = (JavaVariables) obj;
				} else if (obj instanceof GlobalConditions) {
					conds = (GlobalConditions) obj;
				} else if (obj instanceof Renaming) {
					renaming = (Renaming) obj;
				}
			}
			if (first instanceof Method) {
				Method firstMethod = (Method) first;
				Method secondMethod = (Method) second;
				Algorithm firstAlgo = (Algorithm) firstMethod.eContainer();
				Algorithm secondAlgo = (Algorithm) secondMethod.eContainer();
				Method parentMethod;
				Method childMethod;
				if (checkParent(firstAlgo, secondAlgo)) {
					parentMethod = secondMethod;
					childMethod = firstMethod;
				} else {
					parentMethod = firstMethod;
					childMethod = secondMethod;
				}
				ProveWithKey.provePostImplPostWithKey(parentMethod.getPostCondition(), childMethod.getPostCondition(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
			} else if (first instanceof Algorithm) {
				Algorithm firstAlgo = (Algorithm) first;
				Algorithm secondAlgo = (Algorithm) second;
				Algorithm parentAlgo;
				Algorithm childAlgo;
				if (checkParent(firstAlgo, secondAlgo)) {
					parentAlgo = secondAlgo;
					childAlgo = firstAlgo;
				} else if (checkParent(secondAlgo, firstAlgo)){
					parentAlgo = firstAlgo;
					childAlgo = secondAlgo;
				} else {
					Console.println("Algorithms are not in a parent child relation.");
					monitor.done();
					return;
				}
				ProveWithKey.provePostImplPostWithKey(parentAlgo.getPostCondition(), childAlgo.getPostCondition(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
			}
		}
		monitor.done();
	}
	
	private boolean checkParent(Algorithm sourceAlgo, Algorithm targetAlgo) {
		boolean isParent = false;
		if (targetAlgo.getChildAlgorithms().contains(sourceAlgo)) {
			return true;
		}
		for (Algorithm parentOfSource : sourceAlgo.getParentAlgorithms()) {
			isParent = checkParent(parentOfSource, targetAlgo);
			if (isParent) {
				return true;
			}
		}
		return false;
	}
}