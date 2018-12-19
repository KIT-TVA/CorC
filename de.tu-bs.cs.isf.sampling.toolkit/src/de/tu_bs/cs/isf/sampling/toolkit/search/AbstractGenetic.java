package de.tu_bs.cs.isf.sampling.toolkit.search;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * Abstract class for all evolutionary algorithms
 * @author Tobias
 *
 * @param <T>	Type of the chromosome for the fitness calculation (Collection)
 * @param <E>	SubType in the collection of T
 * @param <V>	Type of the population (Map)
 */
public abstract class AbstractGenetic<T extends Collection<?>, V extends Map<T, Double>> extends AbstractEvolutionary<T, V> {

	/**
	 * variable to call the random generator
	 */
	protected Random random = new Random();
	
	/**
	 * Constructor for evolutionary algorithms
	 * @param featureModel	The input feature model
	 */
	public AbstractGenetic(FeatureModel featureModel) {
		super(featureModel);
	}
	
	/**
	 * Method to select an individual in the population
	 * @param population	A population containing many individuals
	 * @return	One individual in the population
	 */
	protected abstract T selectIndividual(V population);
	
	/**
	 * Method to mutate one chromosome
	 * @param chromosome	The input chromosome
	 */
	protected abstract void mutate(T chromosome);
	
	/**
	 * Method to generate the crossover product of two chromosomes
	 * @param chromosome1	The first input chromosome
	 * @param chromosome2	The second input chromosome
	 * @param childChromosome1	The first output chromosome
	 * @param childChromosome2	The second output chromosome
	 */
	protected abstract void crossover(T chromosome1,
			T chromosome2, T childChromosome1,
			T childChromosome2);
}
