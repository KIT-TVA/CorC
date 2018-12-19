package de.tu_bs.cs.isf.sampling.toolkit.covering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.AbstractSampling;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.GetCovered;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.RemoveInvalid;

/**
 * Abstract class for covering array algorithms, like Inc-Ling, ICPL and Chvatal.
 * @author Tobias
 *
 */
public abstract class AbstractCovering extends AbstractSampling {

	/**
	 * Variable that saves the selected getCovered method
	 */
	protected GetCovered getCovered; 
	
	/**
	 * Variable that saves the selected removeInvalid method
	 */
    protected RemoveInvalid removeInvalid; 
    
    /**
     * Constructor
     * @param featureModel	the input feature model
     */
	public AbstractCovering(FeatureModel featureModel) {
		super(featureModel);
	}
	
	/**
	 * Sets the getCovered method
	 * @param getCovered
	 */
	public void setGetCovered(GetCovered getCovered) {
		this.getCovered = getCovered;
	}

	/**
	 * Sets the removeInvalid method
	 * @param removeInvalid
	 */
	public void setRemoveInvalid(RemoveInvalid removeInvalid) {
		this.removeInvalid = removeInvalid;
	}

	/**
	 * Method to generate all possible tuple (valid and invalid)
	 * @return	A set of all tuple
	 */
	protected Set<List<Integer>> generateTuple() {
		Set<List<Integer>> tuples = new HashSet<>();
		int numberFeatures = this.solver.getSatInstance().getNumberOfVariables();
		for (int i = 1; i <= numberFeatures; i++) {
			for (int j = i+1; j <= numberFeatures; j++) {
				tuples.add(new ArrayList<Integer>(Arrays.asList(i, j)));
				tuples.add(new ArrayList<Integer>(Arrays.asList(i, -j)));
				tuples.add(new ArrayList<Integer>(Arrays.asList(-i, j)));
				tuples.add(new ArrayList<Integer>(Arrays.asList(-i, -j)));
			}
		}
		return tuples;
	}
	
	/**
	 * Calls the saved removeInvalid method
	 * @param tuples	The input tuples
	 * @return	Returns only the valid tuples
	 */
	protected Set<List<Integer>> removeInvalid(Set<List<Integer>> tuples) {
		return removeInvalid.removeInvalid(tuples, solver);
	}
	
	/**
	 * Calls the saved getCovered method
	 * @param tuples	the input tuples
	 * @param configuration	the input configurations
	 * @return	Returns all tuples of the input tuples that are covered by the configuration
	 */
	protected Set<List<Integer>> getCovered(Set<List<Integer>> tuples, Set<Integer> configuration) {
		return getCovered.getCovered(tuples, configuration);
	}
}
