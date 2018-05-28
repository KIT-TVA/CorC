package de.tu_bs.cs.isf.sampling.toolkit.covering.helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver;
import org.prop4j.solver.ISatSolver.SatResult;

/**
 * Class that implements the RemoveInvalid interface and implements the method in an efficient way
 * @author Tobias
 *
 */
public class RemoveInvalidFastImpl implements RemoveInvalid {

	@Override
	public Set<List<Integer>> removeInvalid(Set<List<Integer>> tuples, ISatSolver solver) {
		Set<List<Integer>> validTuples = new HashSet<>();
		while (!tuples.isEmpty()) {
			solver.assignmentClear(0);
			Iterator<List<Integer>> it = tuples.iterator();
			List<Integer> nextTuple = it.next();
			for (int assignment : nextTuple) {
				solver.assignmentPush(assignment);
			}
			if (solver.isSatisfiable() != SatResult.TRUE) {
				tuples.remove(nextTuple);
			} else {
				validTuples.add(nextTuple);
				tuples.remove(nextTuple);
				int[] conf = solver.findModel();
				Set<Integer> configuration = new HashSet<>();
				for (int i : conf) {
					configuration.add(i);
				}
				Set<List<Integer>> newValidTuples = new HashSet<>();
				for (List<Integer> tuple : tuples) {
					if (configuration.containsAll(tuple)) {
						newValidTuples.add(tuple);
					}
				}
				validTuples.addAll(newValidTuples);
				tuples.removeAll(newValidTuples);
			}
		}
		return validTuples;
	}

}
