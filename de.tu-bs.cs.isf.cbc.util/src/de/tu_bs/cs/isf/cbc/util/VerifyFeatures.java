package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.IVariables;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.AllConfigurationGenerator;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.IConfigurationGenerator;
import de.ovgu.featureide.fm.core.analysis.cnf.solver.AdvancedSatSolver;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager; //Notices changes of model.xml
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.core.job.LongRunningWrapper;

public class VerifyFeatures {
	static {
		FMCoreLibrary.getInstance().install();
	}

	private static String[][] configurations;
	private static String callingFeature;
	private static String[] configLn = new String[0];
	private static List<String> featureOrder;

	private static IProject thisProject;
	private static URI uri;
	private static Path path;

	private static FeatureModelFormula featureModel;
	private static IFeatureModel featModel;
	private static Configuration configuration;

	private static ConfigurationAnalyzer configurationAnalyzer;

	// calculates feature-configurations
	public static String[][] verifyConfig(URI uri_new, String method, boolean original, String callingClass, boolean cleanFromIrrelevant) {
		uri = uri_new;
		thisProject = FileUtil.getProject(uri);
		path = Paths.get(thisProject.getLocation() + "/model.xml");
		try {
			createEmptyConfiguration(path);
			featModel = FeatureModelManager.load(path);
			featureOrder = featModel.getFeatureOrderList();
			callingFeature = uri.segment(uri.segmentCount() - 3) + "";
			configurations = new String[0][];

			IFeatureModel featureModel = FeatureModelManager.load(path);
			Configuration configuration = new Configuration(new FeatureModelFormula(featureModel));
			configuration.setManual(callingFeature, Selection.SELECTED);

			List<Configuration> configList = getCompleteConfigurations(configuration);
			for (Configuration config : configList) {
				List<IFeature> selected = config.getSelectedFeatures();
				configLn = new String[selected.size()];
				for (int i = 0; i < selected.size(); i++) {
					configLn[i] = selected.get(i) + "";
				}
				writeConfig();
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		checkConfigs(method, original, callingClass, cleanFromIrrelevant);
		return configurations;
	}

	// removes features not containing method-refinement from list
	public static String[] removeIrrelevant(String[] features, String methodName, String className) {
		File checkFile;
		for (int i = 0; i < features.length; i++) {
			checkFile = new File(thisProject + "/features/" + features[i] + "/" + className + "/" + methodName + ".cbcmodel");
			if (!checkFile.exists() && !(features[i]).equalsIgnoreCase(callingFeature)) {
				String[] temp = new String[features.length];
				boolean foundToDelete = false;
				for (int j = 0; i < temp.length; j++) {
					if (j != i) {
						temp[j] = features[j + (foundToDelete ? 1 : 0)];
					} else {
						foundToDelete = true;
					}					
				}
			}
		}
		return features;
	}

	// writes actual configurations to configurations-array
	public static void writeConfig() {
		String[][] tmp = new String[configurations.length][];
		for (int i = 0; i < configurations.length; i++) {
			tmp[i] = configurations[i];
		}
		configurations = new String[tmp.length + 1][];
		for (int i = 0; i < tmp.length; i++) {
			configurations[i] = tmp[i];
		}
		configurations[configurations.length - 1] = configLn;
		return;
	}

	// calculates all configurations possible
	public static List<Configuration> getCompleteConfigurations(Configuration partialConfig) {
		FeatureModelFormula formula = partialConfig.getFeatureModelFormula();
		CNF cnf = formula.getCNF();
		IVariables vars = cnf.getVariables();

		// Set assumptions for the SAT solver
		AdvancedSatSolver solver = new AdvancedSatSolver(cnf);
		for (SelectableFeature feature : partialConfig.getFeatures()) {
			if (feature.getSelection() != Selection.UNDEFINED) {
				solver.assignmentPush(
						vars.getVariable(feature.getFeature().getName(), feature.getSelection() == Selection.SELECTED));
			}
		}

		// Compute complete configurations
		IConfigurationGenerator generator = new AllConfigurationGenerator(solver, Integer.MAX_VALUE);
		List<LiteralSet> completeConfigList = LongRunningWrapper.runMethod(generator);

		// Convert to configuration objects
		ArrayList<Configuration> configList = new ArrayList<>();
		for (LiteralSet completeConfiguration : completeConfigList) {
			Configuration config = new Configuration(partialConfig, formula);
			for (int literal : completeConfiguration.getLiterals()) {
				SelectableFeature feature = config.getSelectableFeature(vars.getName(literal));
				if (feature.getSelection() == Selection.UNDEFINED) {
					config.setManual(feature, literal > 0 ? Selection.SELECTED : Selection.UNSELECTED);
				}
			}
			configList.add(config);
		}
		return configList;
	}

	// removes irrelevant features and configurations from configurations-array
	public static void checkConfigs(String method, boolean original, String className, boolean cleanFromIrrelevant) {
		String varMClass = method.split("\\.")[0];
		method = method.contains(".") ? method.split("\\.")[1] : method;
		// bring features to featuremodel-order
		for (int i = 0; i < configurations.length; i++) {
			configLn = new String[configurations[i].length];
			int cursor = 0;
			for (String chosenFeature : featureOrder) {
				for (int j = 0; j < configurations[i].length; j++) {
					if (chosenFeature.equals(configurations[i][j])) {
						configLn[cursor] = chosenFeature;
						cursor++;
					}
				}
			}
			for (int j = 0; j < configLn.length; j++) {
				if (configLn[j] == null) {
					configLn[j] = "empty";
				}
			}
			configurations[i] = configLn;
		}

		if (cleanFromIrrelevant) {
			if (original) {
			// removes all features with order-place behind calling feature
			for (int i = 0; i < configurations.length; i++) {
				for (int j = 0; j < configurations[i].length; j++) {
					if (configurations[i][j].equals(callingFeature)) {
						for (int k = j + 1; k < configurations[i].length; k++) {
							configurations[i][k] = "empty";
						}
					}
				}
			}
			}
			// remove all features from configurations which are not part of
			// refinement-chain
			File checkFile;
			for (int i = 0; i < configurations.length; i++) {
				for (int j = 0; j < configurations[i].length; j++) {
					checkFile = new File(thisProject.getLocation() + "/features/" + configurations[i][j] + "/" + varMClass + "/"
							+ method.toLowerCase() + ".cbcmodel");
					if (!checkFile.exists() && !configurations[i][j].equals(callingFeature)) {
						checkFile = new File(thisProject.getLocation() + "/features/" + configurations[i][j] + "/"
								+ className + "/" + method.toLowerCase() + ".cbcmodel");
						if (!checkFile.exists() && !configurations[i][j].equals(callingFeature)) {
							configurations[i][j] = "empty";
						}
					}
				}
			}
		}

		// remove all duplicate configurations

		String conjConfigs[] = new String[configurations.length];
		if (!cleanFromIrrelevant) {
			for (int i = 0; i < configurations.length; i++) {
				for (int j = 0; j < configurations[i].length; j++) {
					if (conjConfigs[i] == null) {
						conjConfigs[i] = configurations[i][j];
					} else {
						conjConfigs[i] += configurations[i][j];
					}
				}
			}
			for (int i = 0; i < conjConfigs.length; i++) {
				for (int j = i; j < conjConfigs.length; j++) {
					if (i != j && conjConfigs[i].equals(conjConfigs[j])) {
						for (int k = 0; k < configurations[j].length; k++) {
							configurations[j][k] = null;
						}
					}
				}
			}
		}

		// clean configurations from empty features
		int counter;
		for (int i = 0; i < configurations.length; i++) {
			counter = 0;
			for (int j = 0; j < configurations[i].length; j++) {
				if (configurations[i][j] != "empty" && configurations[i][j] != null) {
					counter++;
				}
			}
			configLn = new String[counter];
			counter = 0;
			for (int j = 0; j < configurations[i].length; j++) {
				if (configurations[i][j] != "empty" && configurations[i][0] != null) {
					configLn[counter] = configurations[i][j];
					counter++;
				}
			}
			if (!configLn.equals("")) {
				configurations[i] = configLn;
			}
		}
		counter = 0;
		for (int i = 0; i < configurations.length; i++) {
			if (configurations[i].length != 0) {
				counter++;
			}
		}
		if (!cleanFromIrrelevant) {

			String temp[][] = new String[counter][];
			counter = 0;
			for (int i = 0; i < configurations.length; i++)
				if (configurations[i].length != 0) {
					String configLine[] = new String[configurations[i].length];
					for (int j = 0; j < configurations[i].length; j++) {
						configLine[j] = configurations[i][j];
					}
					temp[counter] = configLine;
					counter++;
				}
			configurations = temp;

			// remove empty configurations
			conjConfigs = new String[configurations.length];
			for (int i = 0; i < configurations.length; i++) {
				for (int j = 0; j < configurations[i].length; j++) {
					if (conjConfigs[i] == null) {
						conjConfigs[i] = configurations[i][j];
					} else {
						conjConfigs[i] += configurations[i][j];
					}
				}
			}
			for (int i = 0; i < conjConfigs.length; i++) {
				for (int j = i; j < conjConfigs.length; j++) {
					if (i != j && conjConfigs[i].equals(conjConfigs[j])) {
						for (int k = 0; k < configurations[j].length; k++) {
							configurations[j][k] = null;
						}
					}
				}
			}
		}
		counter = 0;
		for (int i = 0; i < configurations.length; i++) {
			if (configurations[i][0] != null) {
				counter++;
			}
		}
		String temporary[][] = new String[counter][];
		counter = 0;
		for (int i = 0; i < configurations.length; i++) {
			if (configurations[i][0] != null) {
				String tmp[] = new String[configurations[i].length];
				for (int j = 0; j < configurations[i].length; j++) {
					tmp[j] = configurations[i][j];
				}
				temporary[counter] = tmp;
				counter++;
			}
		}
		configurations = temporary;
		return;
	}

	private static void createEmptyConfiguration(final Path path) throws IOException {
		final FileHandler<IFeatureModel> fh = FeatureModelManager.getFileHandler(path);
		if (!fh.getLastProblems().containsError()) {
			featureModel = new FeatureModelFormula(fh.getObject());
			configuration = new Configuration(featureModel);
			new ConfigurationAnalyzer(featureModel, configuration);
		} else {
			throw new IOException("Feature model could not be loaded");
		}
	}
}