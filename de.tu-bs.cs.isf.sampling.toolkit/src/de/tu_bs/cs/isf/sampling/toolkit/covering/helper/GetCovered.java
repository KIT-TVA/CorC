package de.tu_bs.cs.isf.sampling.toolkit.covering.helper;

import java.util.List;
import java.util.Set;

/**
 * Interface for all getCoverd method
 * @author Tobias
 *
 */
public interface GetCovered {

	/**
	 * Method to collect all tuples that are covered by the configuration
	 * @param tuples	the input tuples
	 * @param configuration	the input configurations
	 * @return	Returns all tuples of the input tuples that are covered by the configuration
	 */
	public Set<List<Integer>> getCovered(Set<List<Integer>> tuples, Set<Integer> configuration);
}
