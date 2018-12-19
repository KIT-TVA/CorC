package de.tu_bs.cs.isf.sampling.toolkit.covering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SatResult;
import org.prop4j.solver.ISatSolver.SelectionStrategy;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.GetCoveredImpl;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.RemoveInvalidFastImpl;

/**
 * Class that starts the ICPL algorithm
 * @author Tobias
 *
 */
public class ICPL extends AbstractCovering {

	/**
	 * Constructor of the ICPL algorithm
	 * @param featureModel	The input feature model
	 */
	public ICPL(FeatureModel featureModel) {
		super(featureModel);
		removeInvalid = new RemoveInvalidFastImpl();
		getCovered = new GetCoveredImpl();
		
	}
	
	/**
	 * Method to generate all 1-wise tuple
	 * @return	All 1-wise tuple (valid and invalid)
	 */
	private Set<List<Integer>> generate1Tuples() {
		Set<List<Integer>> tuples = new HashSet<>();
		int numberFeatures = this.solver.getSatInstance().getNumberOfVariables();
		for (int i = 1; i <= numberFeatures; i++) {
			for (int j = i+1; j <= numberFeatures; j++) {
				tuples.add(new ArrayList<Integer>(Arrays.asList(i)));
				tuples.add(new ArrayList<Integer>(Arrays.asList(-i)));
			}
		}
		return tuples;
	}
	
	@Override
	public void sampling() {
		sampling(2);
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
	 * Helper method of sampling. The sampling is done for t-wise tuple
	 * @param t	the number of features in a tuple
	 * @return	A list of invalid tuple (Only used for t == 1)
	 */
	private Set<List<Integer>> sampling(int t) {
		Set<List<Integer>> tuples;
		Set<List<Integer>> invalid1Tuples;
		boolean invalidRemoved = false;
		if (t == 1) {
			invalid1Tuples = genCompleteI();
			tuples = generate1Tuples();
			tuples.removeAll(invalid1Tuples);
			invalidRemoved = true;
		} else {
			invalid1Tuples = sampling(1);
			tuples = generateTuple();
			tuples = reduceTuples(tuples, invalid1Tuples);
		}
		
		while (!tuples.isEmpty()) {
			Set<Integer> configuration;
			configuration = genConfiguration(tuples);
			configurations.add(configuration);
			Set<List<Integer>> tuplesCovered = getCovered(tuples, configuration);
			tuples.removeAll(tuplesCovered);
			
			if (!invalidRemoved && Math.log10(tuplesCovered.size()) <= Math.log10(solver.getSatInstance().getNumberOfVariables())) {
				tuples = removeInvalid(tuples);
				invalidRemoved = true;
			}
		}
		return invalid1Tuples;
	}

	/**
	 * Generates all 1-wise invalid tuples
	 * @return	A set of all 1-wise invalid tuples
	 */
	private Set<List<Integer>> genCompleteI() {
		Set<List<Integer>> tuples = generate1Tuples();
		Set<List<Integer>> tempTuples = new HashSet<>();
		Set<List<Integer>> invalidTuples = new HashSet<>();
		Set<Integer> configuration = new HashSet<>();
		Set<Integer> notCore = new HashSet<>();
		Set<Integer> notDead = new HashSet<>();
		int[] config = solver.findModel();
		for (int i : config) {
			configuration.add(i);
			if (i < 0) {
				notCore.add(i);
			}
		}
		configurations.add(configuration);
		for (List<Integer> tuple : tuples) {
			if (tuple.get(0) < 0 && !notCore.contains(tuple.get(0))) {
				tempTuples.add(tuple);
			}
		}
		invalidTuples.addAll(getInvalidAssignments(tempTuples));
		
		solver.setSelectionStrategy(SelectionStrategy.POSITIVE);
		config = solver.findModel();
		configuration = new HashSet<>();
		tempTuples = new HashSet<>();
		for (int i : config) {
			configuration.add(i);
			if (i > 0) {
				notDead.add(i);
			}
		}
		configurations.add(configuration);
		for (List<Integer> tuple : tuples) {
			if (tuple.get(0) > 0 && !notCore.contains(tuple.get(0))) {
				tempTuples.add(tuple);
			}
		}
		invalidTuples.addAll(getInvalidAssignments(tempTuples));
		
		return invalidTuples;
	}
	
	/**
	 * Tests every 1-wise tuple of the input if it is not satisfiable
	 * @param tuples	The input 1-wise tuple that should be tested
	 * @return	A set containing all invalid tuples
	 */
	private Set<List<Integer>> getInvalidAssignments(Set<List<Integer>> tuples) {
		Set<List<Integer>> invalidTuples = new HashSet<>();
		for (List<Integer> tuple : tuples) {
			solver.assignmentPush(tuple.get(0));
			if (solver.isSatisfiable() != SatResult.TRUE) {
				invalidTuples.add(tuple);
			}
			solver.assignmentPop();
		}
		return invalidTuples;
	}
	
	/**
	 * Method to reduce the set of tuples that have to be covered
	 * @param tuples	The input tuples
	 * @param invalid1Tuples	The invalid 1-wise tuples
	 * @return	A set of tuples that have to be covered
	 */
	private Set<List<Integer>> reduceTuples(Set<List<Integer>> tuples,
			Set<List<Integer>> invalid1Tuples) {
		Set<List<Integer>> newTuples = new HashSet<>();
		
		Loop: for (List<Integer> tuple : tuples) {
			for (Collection<Integer> configuration : configurations) {
				if (configuration.contains(tuple.get(0)) && configuration.contains(tuple.get(1))) {
					continue Loop;
				}
			}
			for (List<Integer> invalidTuple : invalid1Tuples) {
				if (tuple.contains(invalidTuple.get(0))) {
					continue Loop;
				}
			}
			newTuples.add(tuple);
		}
		return newTuples;
	}
	
	/**
	 * Generates a new configuration
	 * @param tuples	The input tuples
	 * @return	A configuration that covers some tuples
	 */
	private Set<Integer> genConfiguration(Set<List<Integer>> tuples) {
		Set<Integer> configuration = new HashSet<>();
		Set<Integer> cf = new HashSet<>();
		Set<Integer> pf = new HashSet<>();
		for (List<Integer> tuple : tuples) {
			for (int assignment : tuple) {
				pf.add(Math.abs(assignment));
			}
		}
		solver.assignmentClear(0);
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
		Loop: for (List<Integer> tuple : tuples) {
			Set<Integer> features = new HashSet<>();
			for (int assignment : tuple) {
				features.add(Math.abs(assignment));
			}
			if (cf.containsAll(features)) {
				continue;
			}
			for (int assignment : tuple) {
				solver.assignmentPush(assignment);
			}
			if (solver.isSatisfiable()  == SatResult.TRUE) {
				configuration.addAll(tuple);
				cf.addAll(features);
			} else {
				for (int i = 0; i < tuple.size(); i++) {
					solver.assignmentPop();
				}
				for (int assignment : tuple) {
					List<Integer> tempTuple = new ArrayList<>();
					tempTuple.addAll(tuple);
					tempTuple.remove((Integer) assignment);
					if ((tempTuple.isEmpty() || configuration.containsAll(tempTuple)) && !configuration.contains(assignment)) {
						int invertedAssignment = -assignment;
						solver.assignmentPush(invertedAssignment);
						configuration.add(invertedAssignment);
						cf.add(Math.abs(invertedAssignment));
					}
				}
			}
			if (cf.size() == pf.size()) {
				break Loop;
			}
		}
		int[] resultConfiguration = solver.findModel();
		for (int i : resultConfiguration) {
			configuration.add(i);
		}
		return configuration;
	}
}
