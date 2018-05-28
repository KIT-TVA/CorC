package de.tu_bs.cs.isf.sampling.toolkit;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.prop4j.Node;
import org.prop4j.analyses.PairWiseConfigurationGenerator;
import org.prop4j.solver.BasicSolver;
import org.prop4j.solver.ISatSolver;
import org.prop4j.solver.ISatSolver.SelectionStrategy;
import org.prop4j.solver.SatInstance;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FactoryWorkspace;
import de.ovgu.featureide.fm.core.base.impl.Feature;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator;
import de.ovgu.featureide.fm.core.editing.AdvancedNodeCreator.CNFType;
import de.ovgu.featureide.fm.core.filter.AbstractFeatureFilter;
import de.ovgu.featureide.fm.core.functional.Functional;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.job.LongRunningWrapper;
import de.ovgu.featureide.fm.core.job.monitor.IMonitor;

/**
 * test class during the implementation
 * @author Tobias
 *
 */
public class TestClass {
	
	private void callConfigurationGenerator(IFeatureModel fm, int solutionCount) {
		final AdvancedNodeCreator advancedNodeCreator = new AdvancedNodeCreator(fm, new AbstractFeatureFilter());
		advancedNodeCreator.setCnfType(CNFType.Regular);
		advancedNodeCreator.setIncludeBooleanValues(false);

		final Node createNodes = advancedNodeCreator.createNodes();
		final SatInstance satInstance = new SatInstance(createNodes, Functional.toList(FeatureUtils.getConcreteFeatureNames(fm)));
		final PairWiseConfigurationGenerator gen = getGenerator(satInstance, solutionCount);
//		gen.analyze(monitor);
	}

	protected PairWiseConfigurationGenerator getGenerator(SatInstance solver, int solutionCount) {
		return new PairWiseConfigurationGenerator(solver, solutionCount);
	}
	
	private static void generateConfiguration(Configuration configuration, List<String> solution) {
		configuration.resetValues();
		for (final String selection : solution) {
			configuration.setManual(selection, Selection.SELECTED);
		}
}

	public static void main(String[] args) {
		DefaultFeatureModelFactory factory = DefaultFeatureModelFactory.getInstance();
		FeatureModel fm = factory.createFeatureModel();
		Path path = Paths.get("model.xml");
		fm = (FeatureModel) FeatureModelManager.readFromFile(path);
		System.out.println(fm);
		System.out.println(fm.getFeatureTable());
		System.out.println(fm.getFeatureTable());
		System.out.println(fm.getFeatureOrderList());
		System.out.println(fm.getFeatures());
		Feature f = (Feature) fm.getFeature("World");
		Feature f2 = (Feature) fm.getFeature("Feature");
		System.out.println(f.getStructure().isConcrete());
		System.out.println(f2.getStructure().isAbstract());
		Configuration conf = new Configuration(fm);
		conf.setManual("World", Selection.SELECTED);
		System.out.println(conf.isValid());
		System.out.println(conf.toString());
		int solutionCount = 0;
		final AdvancedNodeCreator advancedNodeCreator = new AdvancedNodeCreator(fm, new AbstractFeatureFilter());
		advancedNodeCreator.setCnfType(CNFType.Regular);
		advancedNodeCreator.setIncludeBooleanValues(false);

		final Node createNodes = advancedNodeCreator.createNodes();
		final SatInstance satInstance = new SatInstance(createNodes, Functional.toList(FeatureUtils.getConcreteFeatureNames(fm)));
		final PairWiseConfigurationGenerator gen = new PairWiseConfigurationGenerator(satInstance, solutionCount);
		ISatSolver solver = null;
		try {
			solver = new BasicSolver(satInstance);
		} catch (ContradictionException e) {
		}
		solver.initSolutionList(5);
		solver.setSelectionStrategy(SelectionStrategy.RANDOM);
//		solver.assignmentPush(2);
//		solver.isSatisfiable();
//		solver.assignmentClear(numberOfFixedFeatures);
//		System.out.println(solver.getNumberOfSolutions());
		Set<int[]> configs = new HashSet<>();
		int count = 0;
		while (configs.size() < 4) {
			count++;
			int[] newModel = solver.findModel();
			boolean newSet = true;
			for (int[] b : configs) {
				boolean equal = true;
				for(int c = 0; c < 4; c++) {
					equal = Arrays.equals(b, newModel);
				}
				if (equal == true) {
					newSet = false;
				}
			}
			if (newSet == true || configs.isEmpty()) {
				configs.add(newModel);
			}
		}
		System.out.println(satInstance.getLiteral(1));
		System.out.println(satInstance.getLiteral(2));
		System.out.println(satInstance.getLiteral(3));
		System.out.println(satInstance.getLiteral(4));
//		for (int[]a: configs){
//			String s = "";
//			for (int i : a) {
//				s += i + " ";
//			}
//			System.out.println(s);
//			Configuration configuration = new Configuration(fm);
//			System.out.println("test");
//			System.out.println(satInstance.convertToString(a, true, true));
//			generateConfiguration(configuration, satInstance.convertToString(a));
//			System.out.println("test");
//			System.out.println(configuration);
//			System.out.println(count);
//		}
		
		
		//solver.assignmentClear(0);
//		int[] b = new int[10];
//		
//		solver.setSelectionStrategy(SelectionStrategy.NEGATIVE);
//		a = solver.findModel();
//		System.out.println("test");
//		System.out.println(satInstance.convertToString(a, true, true));
//		generateConfiguration(configuration, satInstance.convertToString(a));
//		System.out.println("test");
//		System.out.println(configuration);
		
	}
}
