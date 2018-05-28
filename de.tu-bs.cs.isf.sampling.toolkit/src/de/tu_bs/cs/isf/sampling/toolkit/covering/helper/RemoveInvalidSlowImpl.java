package de.tu_bs.cs.isf.sampling.toolkit.covering.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver;
import org.prop4j.solver.ISatSolver.SatResult;

/**
 * Class that implements the RemoveInvalid interface and implements the method basically
 * @author Tobias
 *
 */
public class RemoveInvalidSlowImpl implements RemoveInvalid {

	@Override
	public Set<List<Integer>> removeInvalid(Set<List<Integer>> tuples, ISatSolver solver) {
		Set<List<Integer>> tuplesRemove = new HashSet<>();
		for (List<Integer> tuple : tuples) {
			solver.assignmentClear(0);
			solver.assignmentPush(tuple.get(0));
			solver.assignmentPush(tuple.get(1));
			if (solver.isSatisfiable()  != SatResult.TRUE) {
				tuplesRemove.add(tuple);
			}
		}
		tuples.removeAll(tuplesRemove);
		return tuples;
	}

}
