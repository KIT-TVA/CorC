package de.tu_bs.cs.isf.sampling.toolkit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.prop4j.Node;
import org.prop4j.solver.BasicSolver;
import org.prop4j.solver.ISatSolver;
import org.prop4j.solver.ISatSolver.SelectionStrategy;
import org.prop4j.solver.SatInstance;
import org.sat4j.specs.ContradictionException;

import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator.CNFType;
import de.ovgu.featureide.fm.core.filter.AbstractFeatureFilter;
import de.ovgu.featureide.fm.core.functional.Functional;
/**
 * Abstract class for subset selection algorithms.
 * It starts the sampling
 * @author Tobias
 *
 */
public abstract class AbstractSampling {

	/**
	 * Set of configurations
	 */
	protected Set<Collection<Integer>> configurations;
	
	/**
	 * The input feature model
	 */
	protected FeatureModel featureModel;
	
	/**
	 * The sat solver
	 */
	protected ISatSolver solver;
	
	/**
	 * Get-method for the configurations
	 * @return the configurations
	 */
	public Set<Collection<Integer>> getConfigurations() {
		return configurations;
	}

	/**
	 * Constructor that creates a sampling object and initializes a sat solver
	 * @param featureModel	the input feature model
	 */
	public AbstractSampling(FeatureModel featureModel) {
		final AdvancedNodeCreator advancedNodeCreator = new AdvancedNodeCreator(featureModel, new AbstractFeatureFilter());
		advancedNodeCreator.setCnfType(CNFType.Regular);
		advancedNodeCreator.setIncludeBooleanValues(false);

		final Node createNodes = advancedNodeCreator.createNodes();
		final SatInstance satInstance = new SatInstance(createNodes, Functional.toList(FeatureUtils.getConcreteFeatureNames(featureModel)));
		try {
			solver = new BasicSolver(satInstance);
		} catch (ContradictionException e) {
		}
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
		solver.initSolutionList(Math.min(solver.getSatInstance().getNumberOfVariables(), ISatSolver.MAX_SOLUTION_BUFFER));
		this.configurations = new HashSet<>();
		this.featureModel = featureModel;
	}
	
	/**
	 * Method to start the sampling
	 */
	public abstract void sampling();
	
	/**
	 * Counts all tuple that are covered by the configurations
	 * @return	The number of covered tuple
	 */
	public int counterTuple() {
		Set<Set<Integer>> tuples = new HashSet<>();
		for (Collection<Integer> configuration : configurations) {
			for (int i : configuration) {
				for (int j : configuration) {
					if (i != j) {
						tuples.add(new HashSet<Integer>(Arrays.asList(i, j)));
					}
				}
			}
		}
		return tuples.size();
	}
}
