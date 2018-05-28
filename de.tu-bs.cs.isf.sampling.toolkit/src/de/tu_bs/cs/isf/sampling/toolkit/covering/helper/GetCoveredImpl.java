package de.tu_bs.cs.isf.sampling.toolkit.covering.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that implements the getCoverd Interface and implements the getCovered method
 * @author Tobias
 *
 */
public class GetCoveredImpl implements GetCovered {

	@Override
	public Set<List<Integer>> getCovered(Set<List<Integer>> tuples,
			Set<Integer> configuration) {
		Set<List<Integer>> tuplesCovered = new HashSet<>();
		for (List<Integer> tuple : tuples) {
			if (configuration.containsAll(tuple)) {
				tuplesCovered.add(tuple);
			}
		}
		return tuplesCovered;
	}

}
