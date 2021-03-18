package de.tu_bs.cs.isf.cbc.tool.properties_view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.AConfigurationGenerator;
import de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.AllConfigurationGenerator;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

/**
 * Class for managing all feature-model based informations. Generates
 * configurations.
 * 
 * @author David
 */
public class FeatureModel {
	private String feature;
	private IFeatureModel featModel;
	private Path path;
	private List<ArrayList<String>> allConfigList = new ArrayList<ArrayList<String>>();
	private IPath projectPath;
	private List<String> originalFeatureNames = new ArrayList<String>();
	private List<String> methodFeatureNames = new ArrayList<String>();

	public FeatureModel(String feature) {
		this.feature = feature;
		loadFeatureModel();
	}

	/**
	 * Loads the Feature-Model
	 */
	private void loadFeatureModel() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();
		IEditorInput input = activeEditor.getEditorInput();
		IResource diagramResource = input.getAdapter(IResource.class);
		IResource projectResource = diagramResource.getParent().getParent().getParent().getParent();
		projectPath = projectResource.getLocation();
		IPath modelPath = projectPath.append("model.xml");
		IFile modelFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(modelPath);
		path = Paths.get(modelFile.getLocationURI());
		this.featModel = FeatureModelManager.load(path);
	}

	/**
	 * Calculates all possible Configurations
	 */
	public void calculateAllConfigs() throws Exception {
		FeatureModelFormula fm = new FeatureModelFormula(featModel);
		CNF cnf = new CNF(fm.getCNF());
		AConfigurationGenerator config = new AllConfigurationGenerator(cnf);
		List<LiteralSet> literalSet = config.analyze(null);
		for (int i = 0; i < literalSet.size(); i++) {
			ArrayList<String> aConfigList = new ArrayList<String>();
			for (int j = 0; j < literalSet.get(i).size(); j++) {
				if (literalSet.get(i).getLiterals()[j] > 0) {
					if (!featModel.getFeature(cnf.getVariables().getName(literalSet.get(i).getLiterals()[j]))
							.getStructure().isAbstract()) {
						aConfigList.add(cnf.getVariables().getName(literalSet.get(i).getLiterals()[j]));
					}
				}
			}
			allConfigList.add(aConfigList);
		}
	}

	public List<ArrayList<String>> getAllConfigs() {
		return allConfigList;
	}

	/**
	 * Method for returning all Configs based on the selection-filter
	 */
	public List<String> getConfigs(List<String> selectedFeatures) {
		List<String> configList = new ArrayList<String>();
		if (allConfigList.isEmpty()) {
			try {
				calculateAllConfigs();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		for (int i = 0; i < allConfigList.size(); i++) {
			if (allConfigList.get(i).containsAll(selectedFeatures)) {
				configList.add(allConfigList.get(i).toString());
			}
		}
		Collections.sort(configList, Comparator.comparing(String::length));
		return configList;
	}

	/**
	 * Method for returning all valid Replacements based on the selection-filter and
	 * based on the replacement type
	 */
	public List<String> getFeatureConfigs(List<String> selectedFeatures, String replacementType) {
		List<String> featureNames = new ArrayList<String>();
		if (replacementType.equals("original")) {
			featureNames = originalFeatureNames;
		} else if (replacementType.equals("method")) {
			featureNames = methodFeatureNames;
		}
		List<String> featureConfigList = new ArrayList<String>();
		if (allConfigList.isEmpty()) {
			try {
				calculateAllConfigs();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		for (int i = 0; i < allConfigList.size(); i++) {
			// List featureNames contains elements included in allConfigList.get(i)
			if (!Collections.disjoint(featureNames, allConfigList.get(i))) {
				if (allConfigList.get(i).containsAll(selectedFeatures) && allConfigList.get(i).contains(feature)) {
					// throw out all irreleavnt features without manipulating allConfigList
					List<String> filteredConfigList = new ArrayList<String>();
					filteredConfigList.addAll(allConfigList.get(i));
					filteredConfigList.retainAll(featureNames);
					// No duplicate configs
					if (!featureConfigList.contains(filteredConfigList.toString())) {
						featureConfigList.add(filteredConfigList.toString());
					}
				}
			}
		}
		Collections.sort(featureConfigList, Comparator.comparing(String::length));
		return featureConfigList;
	}

	public List<String> getAllFeatureNames() {
		return featModel.getFeatureOrderList();
	}

	/**
	 * Method for returning all Features which could be in valid Replacements of
	 * original-call
	 */
	public List<String> getOriginalFeatureNames(String methodName) {
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		methodName += ".diagram";
		for (int i = 0; i < featModel.getFeatureOrderList().size(); i++) {
			if (featModel.getFeatureOrderList().get(i).equals(feature)) {
				break;
			}
			// Add all features, which implement the method
			IPath methodPath = projectPath.append("features");
			IPath methodPath2 = methodPath.append(featModel.getFeatureOrderList().get(i));
			IPath methodPath3 = methodPath2.append("diagram");
			IPath methodPath4 = methodPath3.append(methodName);
			File tmpDir = new File(methodPath4.toString());
			if (tmpDir.exists()) {
				if (!originalFeatureNames.contains(featModel.getFeatureOrderList().get(i))) {
					originalFeatureNames.add(featModel.getFeatureOrderList().get(i));
				}
			}
		}
		return originalFeatureNames;
	}

	/**
	 * Method for returning all Features which could be in valid Replacements of
	 * method-call
	 */
	public List<String> getMethodFeatureNames(String code) {
		methodFeatureNames.clear();
		code = code.replaceAll("\n", "");
		code = code.replaceAll("\r", "");
		String methodCut = code.substring(0, code.indexOf("("));
		String methodName = "";
		for (int i = 0; i < methodCut.length(); i++) {
			if (methodCut.charAt(i) == '=' || methodCut.charAt(i) == ' ') {
				methodName = "";
			} else {
				methodName += methodCut.charAt(i);
			}
		}
		// Manipulate MethodName
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		methodName += ".diagram";

		System.out.println(featModel.getFeatureOrderList());
		// add Features which has the method implemented
		for (int i = 0; i < featModel.getFeatureOrderList().size(); i++) {
			IPath methodPath = projectPath.append("features");
			IPath methodPath2 = methodPath.append(featModel.getFeatureOrderList().get(i));
			IPath methodPath3 = methodPath2.append("diagram");
			IPath methodPath4 = methodPath3.append(methodName);
			File tmpDir = new File(methodPath4.toString());
			if (tmpDir.exists()) {
				if (!methodFeatureNames.contains(featModel.getFeatureOrderList().get(i))) {
					methodFeatureNames.add(featModel.getFeatureOrderList().get(i));
				}
			}
		}
		return methodFeatureNames;
	}
}
