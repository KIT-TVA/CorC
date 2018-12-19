package de.tu_bs.cs.isf.sampling.toolkit.covering;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SatResult;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.RemoveInvalidSlowImpl;

/**
 * Class that executes the Chvatal algorithm
 * @author Tobias
 *
 */
public class Chvatal extends AbstractCovering {

	/**
	 * Constructor
	 * @param featureModel	the input feature model
	 */
	public Chvatal(FeatureModel featureModel) {
		super(featureModel);
		removeInvalid = new RemoveInvalidSlowImpl();
	}
	
	@Override
	public void sampling() {
		Set<List<Integer>> tuples = generateTuple();
		boolean invalidRemoved = false;
		while (!tuples.isEmpty()) {
			solver.assignmentClear(0);
			Set<Integer> configuration = new HashSet<>();
			Set<List<Integer>> tuplesCovered = new HashSet<>();
			for (List<Integer> tuple : tuples) {
				solver.assignmentPush(tuple.get(0));
				solver.assignmentPush(tuple.get(1));
				if (solver.isSatisfiable()  == SatResult.TRUE) {
					configuration.add(tuple.get(0));
					configuration.add(tuple.get(1));
					tuplesCovered.add(tuple);
				} else {
					solver.assignmentPop();
					solver.assignmentPop();
				}
			}
			tuples.removeAll(tuplesCovered);
			if (!tuplesCovered.isEmpty()) {
				int[] resultConfiguration = solver.findModel();
				for (int i : resultConfiguration) {
					configuration.add(i);
				}
				configurations.add(configuration);
			}
			if (!invalidRemoved && Math.log10(tuplesCovered.size()) <= Math.log10(solver.getSatInstance().getNumberOfVariables())) {
				tuples = removeInvalid(tuples);
				invalidRemoved = true;
			}
		}
		for (int i = 1; i <= solver.getSatInstance().getNumberOfVariables(); i++) {
			System.out.println(solver.getSatInstance().getLiteral(i));
		}
		System.out.println(configurations.size());
		System.out.println(configurations);
		for (java.util.Collection<Integer> conf : configurations) {
			solver.assignmentClear(0);
			for (int i : conf) {
				solver.assignmentPush(i);
			}
			System.out.println(solver.isSatisfiable());
		}
	}
}
