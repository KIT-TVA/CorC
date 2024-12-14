package de.tu_bs.cs.isf.cbc.tool.proofs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;

public class IKeYProof {


	private String callingFeature;
	private String callingMethod;
	private String callingClass;
	private Diagram diagram;
	private boolean isReturnStatement;
	private List<JavaVariables> javaVariables;
	private List<CbCFormula> cbcFormulas;
	private AbstractStatement statement;
	private IFeatureProvider featureProvider;
	private IProgressMonitor progressMonitor;
	
	
	public boolean isReturnStatement() {
		return isReturnStatement;
	}

	public void setReturnStatement(boolean isReturnStatement) {
		this.isReturnStatement = isReturnStatement;
	}

	public String getCallingFeature() {
		return callingFeature;
	}

	public String getCallingMethod() {
		return callingMethod;
	}

	public String getCallingClass() {
		return callingClass;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public List<JavaVariables> getJavaVariables() {
		return javaVariables;
	}

	public List<CbCFormula> getCbcFormulas() {
		return cbcFormulas;
	}

	public AbstractStatement getStatement() {
		return statement;
	}

	public IFeatureProvider getFeatureProvider() {
		return featureProvider;
	}

	public IProgressMonitor getProgressMonitor() {
		return progressMonitor;
	}

	public void setCallingFeature(String callingFeature) {
		this.callingFeature = callingFeature;
	}
	
	public void setCallingClass(String callingClass) {
		this.callingClass = callingClass;
	}
	
	public void setCallingMethod(String callingMethod) {
		this.callingMethod = callingMethod;
	}
	
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
		URI uri = this.getDiagram().eResource().getURI();
		this.callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
		this.callingClass = FeatureUtil.getInstance().getCallingClass(uri);
		this.callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
	}
	
	public void setJavaVariables(List<JavaVariables> javaVariables) {
		this.javaVariables = javaVariables;
	}
	
	public void setCbCFormulas(List<CbCFormula> cbcFormulas) {
		this.cbcFormulas = cbcFormulas;
	}
	
	public void setStatement(AbstractStatement statement) {
		this.statement = statement;
	}
	
	public void setFeatureProvider(IFeatureProvider featureProvider) {
		this.featureProvider = featureProvider;
	}

	public void setProgressMonitor(IProgressMonitor progressMonitor) {
		this.progressMonitor = progressMonitor;
	}
	
}
