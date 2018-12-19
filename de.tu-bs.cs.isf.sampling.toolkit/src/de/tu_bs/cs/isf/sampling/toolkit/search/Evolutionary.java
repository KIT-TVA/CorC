package de.tu_bs.cs.isf.sampling.toolkit.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SatResult;
import org.prop4j.solver.ISatSolver.SelectionStrategy;

import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;

/**
 * Class that starts the evolutionary search based algorithm
 * @author Tobias
 *
 */
public class Evolutionary extends AbstractGenetic<List<Integer>, Map<List<Integer>, Double>> {

	/**
	 * the maximum fitness value of the population
	 */
	private double maxFitnessValue;
	
	/**
	 * Constructor for the algorithm
	 * @param featureModel	The input feature model
	 * @param numberIterations	The number of iterations
	 * @param populationSize	The maximum population size
	 */
	public Evolutionary(FeatureModel featureModel, int numberIterations,
			int populationSize) {
		super(featureModel);
		this.numberIterations = numberIterations;
		this.populationSize = populationSize;
		int features = this.solver.getSatInstance().getNumberOfVariables();
		int constraints = this.featureModel.getConstraintCount();
		this.maxFitnessValue = Math.hypot(features, constraints);
	}

	@Override
	public void sampling() {
		Map<List<Integer>, Double> population = new HashMap<>();
		createStartPopulation(population);
		
		for (int i = 0; i < numberIterations; i++) {
			List<List<Integer>> removeConfigurations = new ArrayList<>();
			for (List<Integer> configuration : population.keySet()) {
				if (random.nextDouble() < population.get(configuration)) {
					removeConfigurations.add(configuration);
				}
			}
			for (List<Integer> removeConfiguration : removeConfigurations) {
				population.remove(removeConfiguration);
			}
			
			while (population.size() < populationSize) {
				int size = population.size();
				//add conf if empty
				if (size == 0) {
					solver.assignmentClear(0);
					int[] config = solver.findModel();
					List<Integer> configuration = new ArrayList<>();
					for (int j : config) {
						configuration.add(j);
					}
					double fitness = calculateFitness(configuration);
					population.put(configuration, fitness);
				}

				List<Integer> configuration1 = selectIndividual(population);
				List<Integer> configuration2 = selectIndividual(population);
				List<Integer> configuration3 = selectIndividual(population);
				List<Integer> configuration4 = selectIndividual(population);
				List<Integer> childConfiguration1 = new ArrayList<>();
				List<Integer> childConfiguration2 = new ArrayList<>();
				List<Integer> childConfiguration3 = new ArrayList<>();
				List<Integer> childConfiguration4 = new ArrayList<>();
				crossover(configuration1, configuration2, childConfiguration1, childConfiguration2);
				childConfiguration3.addAll(configuration3);
				childConfiguration4.addAll(configuration4);
				mutate(childConfiguration3);
				mutate(childConfiguration4);
				
				solver.assignmentClear(0);
				for (int j : childConfiguration1) {
					solver.assignmentPush(j);
				}
				if (solver.isSatisfiable() == SatResult.TRUE) {
					double fitness1 = calculateFitness(childConfiguration1);
					population.put(childConfiguration1, fitness1);
				}
				
				solver.assignmentClear(0);
				for (int j : childConfiguration2) {
					solver.assignmentPush(j);
				}
				if (solver.isSatisfiable() == SatResult.TRUE) {
					double fitness2 = calculateFitness(childConfiguration2);
					population.put(childConfiguration2, fitness2);
				}
				
				solver.assignmentClear(0);
				for (int j : childConfiguration3) {
					solver.assignmentPush(j);
				}
				if (solver.isSatisfiable() == SatResult.TRUE) {
					double fitness3 = calculateFitness(childConfiguration3);
					population.put(childConfiguration3, fitness3);
				}
				
				solver.assignmentClear(0);
				for (int j : childConfiguration4) {
					solver.assignmentPush(j);
				}
				if (solver.isSatisfiable() == SatResult.TRUE) {
					double fitness4 = calculateFitness(childConfiguration4);
					population.put(childConfiguration4, fitness4);
				}
				//add new random configuration if stuck
				if (size == population.size()) {
					solver.assignmentClear(0);
					int[] config = solver.findModel();
					List<Integer> configuration = new ArrayList<>();
					for (int j : config) {
						configuration.add(j);
					}
					double fitness = calculateFitness(configuration);
					population.put(configuration, fitness);
				}
			}
			while (population.size() > populationSize) {
				List<Integer> removeConfiguration = null;
				double biggest = 0;
				for (List<Integer> nextConf : population.keySet()) {
					if (population.get(nextConf) > biggest) {
						biggest = population.get(nextConf);
						removeConfiguration = nextConf;
					}
				}
				population.remove(removeConfiguration);
			}
		}
		
		configurations.addAll(population.keySet());
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
	protected void mutate(List<Integer> childConfiguration) {
		int index = random.nextInt(childConfiguration.size());
		childConfiguration.set(index, -childConfiguration.get(index));
	}

	@Override
	protected void crossover(List<Integer> configuration1,
			List<Integer> configuration2, List<Integer> childConfiguration1,
			List<Integer> childConfiguration2) {
		int number = random.nextInt(Math.min(configuration1.size(), configuration2.size()));
		for (int i = 0; i < configuration1.size(); i++) {
			if (i <= number) {
				childConfiguration1.add(configuration1.get(i));
				childConfiguration2.add(configuration2.get(i));
			} else {
				childConfiguration1.add(configuration2.get(i));
				childConfiguration2.add(configuration1.get(i));
			}
		}
	}

	@Override
	protected void createStartPopulation(Map<List<Integer>, Double> population) {
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
		do {
			List<Integer> configuration = new ArrayList<>();
			int[] config = solver.findModel();
			for (int i : config) {
				configuration.add(i);
			}
			double fitness = calculateFitness(configuration);
			population.put(configuration, fitness);
		} while (population.size() < populationSize);
	}

	@Override
	protected double calculateFitness(List<Integer> configuration) {
		double variabilityCoverage = 0;
		double cyclomaticComplexity = 0;
		
		for (Integer featureAsInt : configuration) {
			if (featureAsInt > 0) {
				variabilityCoverage++;
			}
		}
		
		NextConstraint: for (IConstraint constraint : this.featureModel.getConstraints()) {
			for (IFeature feature : constraint.getContainedFeatures()) {
				for (Integer featureAsInt : configuration) {
					String stringOfFeatureAsInt = this.solver.getSatInstance().getLiteral(Math.abs(featureAsInt)).toString();
					if (feature.getName().equals(stringOfFeatureAsInt)) {
						cyclomaticComplexity++;
						continue NextConstraint;
					}
				}
			}
		}
		return Math.hypot(variabilityCoverage, cyclomaticComplexity);
	}
	
	@Override
	protected List<Integer> selectIndividual(Map<List<Integer>, Double> population) {
		Set<List<Integer>> keySet = population.keySet();
		List<List<Integer>> keyList = new ArrayList<>(keySet);
		boolean notaccepted = true;
		int index = 0;
		while (notaccepted){
			index = random.nextInt(keyList.size());
			if (random.nextDouble() * maxFitnessValue > population.get(keyList.get(index))) {
				notaccepted = false;
			}
		}
		return keyList.get(index);
	}
}
