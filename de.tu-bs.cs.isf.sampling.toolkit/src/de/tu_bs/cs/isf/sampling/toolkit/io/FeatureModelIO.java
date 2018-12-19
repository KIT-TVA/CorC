package de.tu_bs.cs.isf.sampling.toolkit.io;

import java.nio.file.Path;
import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

/**
 * Class to import a feature model
 * @author Tobias
 *
 */
public class FeatureModelIO {

	/**
	 * Reads a feature model from a file
	 * @param stringPath	The path to the feature model file
	 * @return	A feature model object
	 */
	public static FeatureModel readFeatureModel(String stringPath) {
		DefaultFeatureModelFactory factory = DefaultFeatureModelFactory.getInstance();
		FeatureModel fm = factory.createFeatureModel();
		Path path = Paths.get(stringPath);
		fm = (FeatureModel) FeatureModelManager.readFromFile(path);
		return fm;
	}
}
