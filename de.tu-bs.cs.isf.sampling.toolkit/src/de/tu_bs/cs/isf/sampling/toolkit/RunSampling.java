package de.tu_bs.cs.isf.sampling.toolkit;

import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.tu_bs.cs.isf.sampling.toolkit.covering.Chvatal;
import de.tu_bs.cs.isf.sampling.toolkit.covering.ICPL;
import de.tu_bs.cs.isf.sampling.toolkit.covering.IncLing;
import de.tu_bs.cs.isf.sampling.toolkit.io.FeatureModelIO;
import de.tu_bs.cs.isf.sampling.toolkit.search.Evolutionary;
import de.tu_bs.cs.isf.sampling.toolkit.search.MultiObjective;
import de.tu_bs.cs.isf.sampling.toolkit.search.Similarity;

/**
 * Main class to start the sampling
 * @author Tobias
 *
 */
public class RunSampling {

	public static void main(String[] args) {
		FeatureModel featureModel = FeatureModelIO.readFeatureModel("DB2.xml");
//		AbstractSampling sampling = new MultiObjective(featureModel, 10, 10, 13, 5);
//		AbstractSampling sampling = new Similarity(featureModel, 10, 10);
		AbstractSampling sampling = new Evolutionary(featureModel, 10, 10);
//		AbstractSampling sampling = new IncLing(featureModel);
//		AbstractSampling sampling = new Chvatal(featureModel);
//		AbstractSampling sampling = new ICPL(featureModel);
		sampling.sampling();
		System.out.println(sampling.counterTuple());
	}
}
