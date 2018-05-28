package de.tu_bs.cs.isf.sampling.toolkit.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SelectionStrategy;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * Class to start the multi objective algorithm
 * @author Tobias
 *
 */
public class MultiObjective extends AbstractGenetic<Set<Set<Integer>>, Map<Set<Set<Integer>>, Double>> {

	/**
	 * The maximum size of an individual
	 */
	private int maximumIndividualSize;
	
	/**
	 * The number of individuals that are selected for the next iteration
	 */
	private int elitismSize;
	
	/**
	 * Maximum number of tuples
	 */
	private int maxCoverage;
	
	 /**
	  * Maximum number of costs
	  */
	private int maxTestingCost;
	
	/**
	 * threshold for covering
	 */
	private double wCoverage = 0.9;
	
	/**
	 * threshold for number of products
	 */
	private double wProducts = 0.05;
	
	/**
	 * threshold for number of testing costs
	 */
	private double wTestingCost = 0.05;
	
	/**
	 * Constructor for the multi objective algorithm
	 * @param featureModel	the feature model
	 * @param numberIterations	the number of iterations
	 * @param populationSize	the maximum size of the population
	 * @param maximumIndividualSize	the maximum size of an individual
	 * @param elitismSize	the number how many individuals survive the selection
	 */
	public MultiObjective(FeatureModel featureModel, int numberIterations,
			int populationSize, int maximumIndividualSize, int elitismSize) {
		super(featureModel);
		this.numberIterations = numberIterations;
		this.populationSize = populationSize;
		this.maximumIndividualSize = maximumIndividualSize;
		this.elitismSize = elitismSize;
		int features = this.solver.getSatInstance().getNumberOfVariables();
		this.maxCoverage = ((features * (features - 1)) / 2) * 4;
		this.maxTestingCost = features * maximumIndividualSize;
	}

	@Override
	public void sampling() {
		Map<Set<Set<Integer>>, Double> population = new HashMap<>();
		createStartPopulation(population);
		
		for (int i = 0; i < numberIterations; i++) {
			Map<Set<Set<Integer>>, Double> newPopulation = new HashMap<>();
			for (Set<Set<Integer>> individual : population.keySet()) {
				newPopulation.put(individual, population.get(individual));
			}
			while (newPopulation.size() > elitismSize) {
				Set<Set<Integer>> removeIndividual = null;
				double biggest = 0;
				for (Set<Set<Integer>> individual : newPopulation.keySet()) {
					if (newPopulation.get(individual) > biggest) {
						biggest = newPopulation.get(individual);
						removeIndividual = individual;
					}
				}
				newPopulation.remove(removeIndividual);
			}
		 
			int s = random.nextInt(maximumIndividualSize) + 1;
			Set<Set<Integer>> individual = new HashSet<>();
			
			while (individual.size() < s) {
				int[] config = solver.findModel();
				Set<Integer> configuration = new HashSet<>();
				for (int j : config) {
					configuration.add(j);
				}
				individual.add(configuration);
			}
			double fitness = calculateFitness(individual);
			newPopulation.put(individual, fitness);
			
			while (newPopulation.size() < populationSize) {
				Set<Set<Integer>> individual1 = selectIndividual(population);
				Set<Set<Integer>> individual2 = selectIndividual(population);
				Set<Set<Integer>> childIndividual1 = new HashSet<>();
				Set<Set<Integer>> childIndividual2 = new HashSet<>();
				crossover(individual1, individual2, childIndividual1, childIndividual2);
				if (random.nextDouble() < 0.5) {
					mutate(childIndividual1);
				}
				if (random.nextDouble() < 0.5) {
					mutate(childIndividual2);
				}
				double fitness1 = calculateFitness(childIndividual1);
				newPopulation.put(childIndividual1, fitness1);
				double fitness2 = calculateFitness(childIndividual2);
				newPopulation.put(childIndividual2, fitness2);
			}
			while (newPopulation.size() > populationSize) {
				Set<Set<Integer>> removeIndividual = null;
				double biggest = 0;
				for (Set<Set<Integer>> nextIndividual : newPopulation.keySet()) {
					if (newPopulation.get(nextIndividual) > biggest) {
						biggest = newPopulation.get(nextIndividual);
						removeIndividual = nextIndividual;
					}
				}
				newPopulation.remove(removeIndividual);
			}
			population = newPopulation;
		}
		
		Set<Set<Integer>> bestIndividual = null;
		double smallest = 1;
		for (Set<Set<Integer>> individual : population.keySet()) {
			if (population.get(individual) < smallest) {
				smallest = population.get(individual);
				bestIndividual = individual;
			}
		}
		System.out.println(population.get(bestIndividual));
		configurations.addAll(bestIndividual);
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

	@Override
	protected void mutate(Set<Set<Integer>> childIndividual) {
		int[] config = solver.findModel();
		Set<Integer> configuration = new HashSet<>();
		for (int i : config) {
			configuration.add(i);
		}
		Iterator<Set<Integer>> it = childIndividual.iterator();
		Set<Integer> remove = it.next();
		childIndividual.remove(remove);
		childIndividual.add(configuration);
	}

	@Override
	protected void crossover(Set<Set<Integer>> individual1,
			Set<Set<Integer>> individual2, Set<Set<Integer>> childIndividual1,
			Set<Set<Integer>> childIndividual2) {
		int number = random.nextInt(Math.min(individual1.size(), individual2.size()));
		Set<Set<Integer>> individual1Stay = new HashSet<>();
		Set<Set<Integer>> individual2Stay = new HashSet<>();
		Set<Set<Integer>> individual1Remove = new HashSet<>();
		Set<Set<Integer>> individual2Remove = new HashSet<>();
		Iterator<Set<Integer>> it1 = individual1.iterator();
		int counter = 0;
		while (it1.hasNext()) {
			if (counter < number) {
				individual1Remove.add(it1.next());
			} else {
				individual1Stay.add(it1.next());
			}
			counter++;
		}
		counter = 0;
		Iterator<Set<Integer>> it2 = individual2.iterator();
		while (it2.hasNext()) {
			if (counter < number) {
				individual2Remove.add(it2.next());
			} else {
				individual2Stay.add(it2.next());
			}
			counter++;
		}
		childIndividual1.addAll(individual1Stay);
		childIndividual1.addAll(individual2Remove);
		childIndividual2.addAll(individual2Stay);
		childIndividual2.addAll(individual1Remove);
	}

	@Override
	protected Set<Set<Integer>> selectIndividual(Map<Set<Set<Integer>>, Double> population) {
		Set<Set<Set<Integer>>> keySet = population.keySet();
		List<Set<Set<Integer>>> keyList = new ArrayList<>(keySet);
		boolean notaccepted = true;
		int index = 0;
		while (notaccepted){
			index = random.nextInt(keyList.size());
			if (random.nextDouble() > population.get(keyList.get(index))) {
				notaccepted = false;
			}
		}
		return keyList.get(index);
	}

	@Override
	protected void createStartPopulation(Map<Set<Set<Integer>>, Double> population) {
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
		do {
			int s = random.nextInt(maximumIndividualSize) + 1;
			Set<Set<Integer>> individual = new HashSet<>();
			
			while (individual.size() < s) {
				int[] config = solver.findModel();
				Set<Integer> configuration = new HashSet<>();
				for (int i : config) {
					configuration.add(i);
				}
				individual.add(configuration);
			}
			double fitness = calculateFitness(individual);
			population.put(individual, fitness);
		} while (population.size() < populationSize);
	}

	@Override
	protected double calculateFitness(Set<Set<Integer>> individual) {
		double coverage = 0;
		double products = individual.size();
		double testingCost = 0;
		Set<Set<Integer>> tuples = new HashSet<>();
		for (Set<Integer> configuration : individual) {
			for (int i : configuration) {
				if (i > 0) {
					testingCost++;
				}
				for (int j : configuration) {
					if (i != j) {
						tuples.add(new HashSet<Integer>(Arrays.asList(i, j)));
					}
				}
			}
		}
		coverage = tuples.size();
		double fitness = (wCoverage * (1 - coverage/maxCoverage)) + wProducts * (products/maximumIndividualSize) + wTestingCost * (testingCost/maxTestingCost);
		
		return fitness;
	}

}
