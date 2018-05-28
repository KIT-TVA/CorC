package de.tu_bs.cs.isf.sampling.toolkit.search;

import java.util.Collection;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.AbstractSampling;

/**
 * Abstract class for all search based algorithms
 * @author Tobias
 *
 * @param <T>	Type of the population or the chromosome for the fitness calculation (Collection)
 * @param <E>	SubType in the collection of T
 * @param <V>	Type of the population
 */
public abstract class AbstractEvolutionary<T extends Collection<?>, V> extends AbstractSampling {

	/**
	 * The number of iterations that should be performed
	 */
	protected int numberIterations;
	
	/**
	 * The start population size
	 */
	protected int populationSize;
	
	/**
	 * Constructor of search based algorithms
	 * @param featureModel	The input feature model
	 */
	public AbstractEvolutionary(FeatureModel featureModel) {
		super(featureModel);
	}
	
	/**
	 * Method to create a start population
	 * @param population	An empty input population
	 */
	protected abstract void createStartPopulation(V population);
	
	/**
	 * Method to calculate the fitness of the input
	 * @param population	The input population
	 * @return	A double that represents the fitness
	 */
	protected abstract double calculateFitness(T population);

}
