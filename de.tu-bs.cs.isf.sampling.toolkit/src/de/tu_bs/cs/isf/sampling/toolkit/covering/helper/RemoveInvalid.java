package de.tu_bs.cs.isf.sampling.toolkit.covering.helper;

import java.util.List;
import java.util.Set;
import org.prop4j.solver.ISatSolver;

/**
 * Interface for all removeInvalid methods
 * @author Tobias
 *
 */
public interface RemoveInvalid {

	/**
	 * Method to remove all invalid tuples in the set of tuples
	 * @param tuples	The input tuples
	 * @param solver	Instance of the sat solver
	 * @return	Returns only the valid tuples
	 */
	public Set<List<Integer>> removeInvalid(Set<List<Integer>> tuples, ISatSolver solver);
}
