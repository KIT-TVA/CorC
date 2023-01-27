package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.tool.features.TestAndAssertionGenerator;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

public class Features {
	private final URI uri;
	private final String callingClass;
	private final String callingFeature;
	private final String callingMethod;
	private final String[][] featureConfigs;
	private int curConfig = -1;
	private int configs = -1;
	
	public Features(final URI uri) {
		this.uri = uri;
		callingClass = uri.segment(uri.segmentCount()-2) + "";
		callingFeature = uri.segment(uri.segmentCount()-3) + "";
		callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), true, callingClass, false, null);
		configs = featureConfigs.length;
	}
	
	public String getCallingClass() {
		return this.callingClass;
	}
	
	public String getCallingFeature() {
		return this.callingFeature;
	}
	
	public String getCallingMethod() {
		return this.callingMethod;
	}
	
	public URI getUri() {
		return this.uri;
	}
	
	public String[] getNextConfig() {
		if (++curConfig != configs) {
			return featureConfigs[curConfig];
		} else {
			return null;
		}
	}
	
	public String getConfigName(final String[] config) {
		String configName = "";
		for (int j = 0; j < config.length; j++) 
			configName += config[j];
		return configName;
	}

	public int getFeatureIndex(final String[] config, final String callingFeature) {
		for (int i = 0; i < config.length; i++) {
			if (config[i].equals(callingFeature)) {
				return i;
			}
		}
		return -1;
	}
	
	public CbCFormula loadFormulaFromFeature(final IFeatureProvider fp, final String featureName, final String className, final String methodSignature) {
		final String methodName = TestAndAssertionGenerator.getMethodNameFromSig(methodSignature);
		
		if (featureName.isBlank() || methodSignature.isBlank()) {
			return null;
		}
		final ResourceSet rSet = new ResourceSetImpl();
		final IFolder folder = FileUtil.getProject(uri).getFolder("features").getFolder(featureName).getFolder(className);
		if (folder == null) {
			return null;
		}
		final IFile file = folder.getFile(methodName + ".diagram");
		if (file == null) {
			return null;
		}
		final Diagram diag = GetDiagramUtil.getDiagramFromFile(file, rSet);
		
		CbCFormula formula = null;
		for (Shape shape : diag.getChildren()) {
			Object obj = fp.getBusinessObjectForPictogramElement(shape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
				break;
			}
		}	
		return formula;
	}
}
