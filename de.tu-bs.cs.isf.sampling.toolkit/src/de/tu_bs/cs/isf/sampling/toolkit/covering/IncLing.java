package de.tu_bs.cs.isf.sampling.toolkit.covering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.solver.ISatSolver.SatResult;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.GetCoveredImpl;
import de.tu_bs.cs.isf.sampling.toolkit.covering.helper.RemoveInvalidFastImpl;

/**
 * Class that starts the IncLing algorithm
 * @author Tobias
 *
 */
public class IncLing extends AbstractCovering {
	
	/**
	 * Inner class to handle the appearance of features in tuples
	 * @author Tobias
	 *
	 */
	private static class FeatureIndex implements Comparable<FeatureIndex> {

		/**
		 * The index of a feature
		 */
		private final int index;
		
		/**
		 * The frequency of a feature in all remaining tuples
		 */
		private int frequency;
		
		/**
		 * Number if the feature is more often selected or deselected in the remaining tuples
		 */
		private int signum;

		/**
		 * Constructor
		 * @param index	The index of a feature
		 */
		public FeatureIndex(int index) {
			this.index = index;
		}

		@Override
		public int compareTo(FeatureIndex o) {
			return o.frequency - frequency;
		}

		@Override
		public String toString() {
			return index + "[" + frequency + ", " + signum + "]";
		}

	}
	
	/**
	 * the maximum number of tuples
	 */
	private double totalNumberTuple;
	
	/**
	 * Number of covered tuples
	 */
	private double coverage;
	
	/**
	 * Threshold used in testCombinations
	 */
	private double threshold = 0.5;

	/**
	 * Constructor of the IncLing algorithm
	 * @param featureModel	The input feature model
	 */
	public IncLing(FeatureModel featureModel) {
		super(featureModel);
		removeInvalid = new RemoveInvalidFastImpl();
		getCovered = new GetCoveredImpl();
	}

	@Override
	public void sampling() {
		int numberFeatures = this.solver.getSatInstance().getNumberOfVariables();
		List<FeatureIndex> featureList = new ArrayList<>();
		for (int i = 1; i <= numberFeatures; i++) {
			featureList.add(new FeatureIndex(i));
		}
		
		Set<List<Integer>> tuples = generateTuple();
		tuples = removeInvalid(tuples);
		totalNumberTuple = tuples.size();
		coverage = 0;
		
		while (!tuples.isEmpty()) {
			updateFrequency(featureList, tuples);
			Collections.sort(featureList);
			
			Set<Integer> configuration = new HashSet<>();
			solver.assignmentClear(0);
			for (int i = 1; i < numberFeatures; i++) {
				for (int j = 0; j < i; j++) {
					List<List<Integer>> testTuples = getTuples(tuples, featureList.get(i), featureList.get(j));
					testTuples(testTuples, configuration);
				}
			}
			int[] resultConfiguration = solver.findModel();
//			try {
//				solver.getInternalSolver().addBlockingClause(new VecInt(SatInstance.negateModel(resultConfiguration)));
//			} catch (ContradictionException e) {
//				e.printStackTrace();
//			}
			for (int i : resultConfiguration) {
				configuration.add(i);
			}
			configurations.add(configuration);
			Set<List<Integer>> tuplesCovered = getCovered(tuples, configuration);
			tuples.removeAll(tuplesCovered);
			coverage = totalNumberTuple - tuples.size();
		}
		
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
	 * Tries to add a tuple to the configuration
	 * @param testTuples	The input tuples that are tested in order
	 * @param configuration	The input configuration that should be extended
	 */
	private void testTuples(List<List<Integer>> testTuples,
			Set<Integer> configuration) {
		for (List<Integer> tuple : testTuples) {
			int feature1 = tuple.get(0);
			int feature2 = tuple.get(1);
			boolean wasUndefined1 = !configuration.contains(feature1);
			boolean wasUndefined2 = !configuration.contains(feature2);
			if (configuration.contains(feature1) || configuration.contains(-feature1) || configuration.contains(feature2) || configuration.contains(-feature2)) {
				if ((configuration.contains(-feature1) || configuration.contains(-feature2)) 
						|| (configuration.contains(feature1) && configuration.contains(feature2))
						|| (coverage / totalNumberTuple) < threshold) {
					continue;
				}
			}
			if (!configuration.contains(feature2)) {
				solver.assignmentPush(feature2);
				if (solver.isSatisfiable()  == SatResult.TRUE) {
					configuration.add(feature2);
				} else {
					solver.assignmentPop();
					solver.assignmentPush(-feature2);
					configuration.add(-feature2);
					return;
				}
			}
			if (!configuration.contains(feature1)) {
				solver.assignmentPush(feature1);
				configuration.add(feature1);
			}
			if (solver.isSatisfiable() == SatResult.TRUE) {
				return;
			} else {
				if (wasUndefined2) {
					if (wasUndefined1) {
						configuration.remove(feature1);
						solver.assignmentPop();
					}
					configuration.remove(feature2);
					solver.assignmentPop();
				} else {
					configuration.remove(feature1);
					configuration.add(-feature1);
					solver.assignmentPop();
					solver.assignmentPush(-feature1);
					return;
				}
			}
		}
	}

	/**
	 * Collects tuples, that are in the set of all tuples, which contains both input feature
	 * @param tuples	The set of all tuples
	 * @param featureIndex	the first feature that should be in the tuple
	 * @param featureIndex2	the second feature that should be in the tuple
	 * @return	A sorted list of tuples that contains both features
	 */
	private List<List<Integer>> getTuples(Set<List<Integer>> tuples,
			FeatureIndex featureIndex, FeatureIndex featureIndex2) {
		List<List<Integer>> testTuple = new ArrayList<>();
		for (List<Integer> tuple : tuples) {
			if ((tuple.contains(featureIndex.index) || tuple.contains(-featureIndex.index)) && (tuple.contains(featureIndex2.index) || tuple.contains(-featureIndex2.index))) {
				testTuple.add(tuple);
			}
		}
		
		List<List<Integer>> sortedTuple = new ArrayList<>();
		List<Integer> negNeg;
		List<Integer> negPos;
		List<Integer> posNeg;
		List<Integer> posPos;
		if (Math.abs(featureIndex.index) < Math.abs(featureIndex2.index)) {
			negNeg = new ArrayList<>(Arrays.asList(-featureIndex.index, -featureIndex2.index));
			negPos = new ArrayList<>(Arrays.asList(-featureIndex.index, featureIndex2.index));
			posNeg = new ArrayList<>(Arrays.asList(featureIndex.index, -featureIndex2.index));
			posPos = new ArrayList<>(Arrays.asList(featureIndex.index, featureIndex2.index));
		} else {
			negNeg = new ArrayList<>(Arrays.asList(-featureIndex2.index, -featureIndex.index));
			negPos = new ArrayList<>(Arrays.asList(-featureIndex2.index, featureIndex.index));
			posNeg = new ArrayList<>(Arrays.asList(featureIndex2.index, -featureIndex.index));
			posPos = new ArrayList<>(Arrays.asList(featureIndex2.index, featureIndex.index));
		}
		
		if (featureIndex.signum < 0) {
			if (featureIndex2.signum < 0) {
				if (testTuple.contains(negNeg)) {
					sortedTuple.add(negNeg);
				}
				if (testTuple.contains(posNeg)) {
					sortedTuple.add(posNeg);
				}
				if (testTuple.contains(negPos)) {
					sortedTuple.add(negPos);
				}
				if (testTuple.contains(posPos)) {
					sortedTuple.add(posPos);
				}
			} else {
				if (testTuple.contains(negPos)) {
					sortedTuple.add(negPos);
				}
				if (testTuple.contains(posPos)) {
					sortedTuple.add(posPos);
				}
				if (testTuple.contains(negNeg)) {
					sortedTuple.add(negNeg);
				}
				if (testTuple.contains(posNeg)) {
					sortedTuple.add(posNeg);
				}
			}
		} else {
			if (featureIndex2.signum < 0) {
				if (testTuple.contains(posNeg)) {
					sortedTuple.add(posNeg);
				}
				if (testTuple.contains(negNeg)) {
					sortedTuple.add(negNeg);
				}
				if (testTuple.contains(posPos)) {
					sortedTuple.add(posPos);
				}
				if (testTuple.contains(negPos)) {
					sortedTuple.add(negPos);
				}
			} else {
				if (testTuple.contains(posPos)) {
					sortedTuple.add(posPos);
				}
				if (testTuple.contains(negPos)) {
					sortedTuple.add(negPos);
				}
				if (testTuple.contains(posNeg)) {
					sortedTuple.add(posNeg);
				}
				if (testTuple.contains(negNeg)) {
					sortedTuple.add(negNeg);
				}
			}
		}
		return sortedTuple;
	}
	
	/**
	 * Updates the feature list (frequency and signum)
	 * @param featureList	The list of all features
	 * @param tuples	All tuples
	 */
	private void updateFrequency(List<FeatureIndex> featureList, Set<List<Integer>> tuples) {
		for (FeatureIndex feature : featureList) {
			feature.frequency = 0;
			feature.signum = 0;
		}
		for (List<Integer> tuple : tuples) {
			for (FeatureIndex feature : featureList) {
				if (Math.abs(tuple.get(0)) == feature.index) {
					feature.frequency++;
					if (tuple.get(0) > 0 ) {
						feature.signum++;
					} else {
						feature.signum--;
					}
				}
				if (Math.abs(tuple.get(1)) == feature.index) {
					feature.frequency++;
					if (tuple.get(1) > 0 ) {
						feature.signum++;
					} else {
						feature.signum--;
					}
				}
			}
		}
	}
}
