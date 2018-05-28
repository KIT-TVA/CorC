package de.tu_bs.cs.isf.sampling.toolkit.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SelectionStrategy;

import com.google.common.collect.Sets;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * Class to start the similary search based algorithm
 * @author Tobias
 *
 */
public class Similarity extends AbstractEvolutionary<List<Set<Integer>>, List<Set<Integer>>> {
	
	/**
	 * Constructor of the algorithm
	 * @param featureModel	The input feature model
	 * @param numberIterations	The maximum number of iterations
	 * @param populationSize	The size of the population
	 */
	public Similarity(FeatureModel featureModel, int numberIterations,
			int populationSize) {
		super(featureModel);
		this.numberIterations = numberIterations;
		this.populationSize = populationSize;
	}

	@Override
	public void sampling() {
		List<Set<Integer>> population = new ArrayList<>();
		createStartPopulation(population);
		
		for (int i = 0; i < numberIterations; i++) {
			double fitness = calculateFitness(population);
			population = globalMaximumDistance(population);
			Set<Integer> worstConfiguration = population.get(population.size()-1);
			
			Set<Integer> newConfiguration = new HashSet<>();
			do {
				solver.shuffleOrder();
				int[] config = solver.findModel();
				for (int j : config) {
					newConfiguration.add(j);
				}
			} while (newConfiguration.equals(worstConfiguration));
			
			population.set(population.size()-1, newConfiguration);
			double newFitness = calculateFitness(population);
			
			if (newFitness <= fitness) {
				population.set(population.size()-1, worstConfiguration);
			}
		}
		
		configurations.addAll(population);
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
	
	/**
	 * Calculates the distances between configurations and sorts them
	 * @param population	The input set of configurations
	 * @return	Sorted list of configurations
	 */
	private List<Set<Integer>> globalMaximumDistance(List<Set<Integer>> population) {
		List<Set<Integer>> newPopulation = new ArrayList<>();
		double maxDistance = 0;
		int conf1 = 0;
		int conf2 = 0;
		for (int i = 1; i < population.size(); i++) {
			for (int j = 0; j < i; j++) {
				if (calculateDistance(population.get(i), population.get(j)) > maxDistance) {
					maxDistance = calculateDistance(population.get(i), population.get(j));
					conf1 = i;
					conf2 = j;
				}
			}
		}
		newPopulation.add(population.get(conf1));
		newPopulation.add(population.get(conf2));
		population.remove(conf1);
		population.remove(conf2);
		
		while (population.size() > 0) {
			maxDistance = 0;
			int conf = 0;
			for (int i = 0; i < population.size(); i++) {
				int tmpDistance = 0;
				for (int j = 0; j < newPopulation.size(); j++) {
					tmpDistance += calculateDistance(population.get(i), newPopulation.get(j));
				}
				if (tmpDistance > maxDistance) {
					maxDistance = tmpDistance;
					conf = i;	
				}
			}
			newPopulation.add(population.get(conf));
			population.remove(conf);
		}
		return newPopulation;
	}

	@Override
	protected void createStartPopulation(List<Set<Integer>> population) {
		Set<Set<Integer>> tmpPopulation = new HashSet<>();
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
		do {
			Set<Integer> configuration = new HashSet<>();
			int[] config = solver.findModel();
			for (int i : config) {
				configuration.add(i);
			}
			tmpPopulation.add(configuration);
		} while (tmpPopulation.size() < populationSize);
		population.addAll(tmpPopulation);
	}

	/**
	 * Calculates the distance between two configurations
	 * @param configuration1	First configuration
	 * @param configuration2	Second configuration
	 * @return	Distance as double
	 */
	private double calculateDistance(Set<Integer> configuration1, Set<Integer> configuration2) {
		double difference = Sets.intersection(configuration1, configuration2).size();
		double union = Sets.union(configuration1, configuration2).size();
		return 1 - (difference / union);
	}
	
	@Override
	protected double calculateFitness(List<Set<Integer>> population) {
		double fitness = 0;
		for (int i = 1; i < population.size(); i++) {
			for (int j = 0; j < i; j++) {
				fitness += calculateDistance(population.get(i), population.get(j));
			}
		}
		return fitness;
	}
}
