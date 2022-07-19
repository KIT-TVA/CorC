package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.statistics.DataCollector;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyStatement extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp The FeatureProvider
	 */
	public VerifyStatement(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify a statement";
	}

	@Override
	public String getDescription() {
		return "Verifies a statement using the pre and post condition.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof SkipStatement
					|| bo instanceof ReturnStatement)) {
				AbstractStatement statement = (AbstractStatement) bo;
				if (statement.getRefinement() == null) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		verifyStatement(context, monitor, false);
	}

	void verifyStatement(ICustomContext context, IProgressMonitor monitor, boolean inlining) {
		long startTime = System.nanoTime();
		monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				boolean returnStatement = bo instanceof ReturnStatement;
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				CbCFormula formula = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof CbCFormula) {
						formula = (CbCFormula) obj;
					}
				}
				
				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				try {
					if (project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}

				if (isVariational) {
					proven = executeVariationalVerification(project, uri, statement, vars, conds, renaming, formula, returnStatement, monitor);
				} else {
					proven = executeNormalVerification(statement, vars, conds, renaming, formula, returnStatement, monitor);
				}
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("--------------- Verification completed --------------- " + duration + "ms");
		monitor.done();
	}

	private boolean executeNormalVerification(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		if (!DataCollector.checkForId(statement)) return false;
		boolean proven = false;
		Console.println("--------------- Triggered verification ---------------");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			URI uri = getDiagram().eResource().getURI();
			String platformUri = uri.toPlatformString(true);
			String callingClass = uri.segment(uri.segmentCount() - 2) + "";
			ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, platformUri, formula, new FileUtil(platformUri), "");
			proven = prove.proveStatementWithKey(returnStatement, false, callingClass, true);
		} else {
			Console.println("Statement is not in correct format.");
		}
		return proven;
	}

	private boolean executeVariationalVerification(IProject project, URI uri, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, CbCFormula formula, boolean returnStatement, IProgressMonitor monitor) {
		if (!DataCollector.checkForId(statement)) return false;
		boolean proven = false;
		String callingFeature = uri.segment(uri.segmentCount() - 3) + "";
		String callingClass = uri.segment(uri.segmentCount() - 2) + "";
		String callingMethod = uri.trimFileExtension().segment(uri.segmentCount()-1) + "";
		String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.trimFileExtension().segmentCount() - 1), true, callingClass, false);
		String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, uri.trimFileExtension().segment(uri.segmentCount() - 1), true, callingClass, true);
		
		Console.println("--------------- Triggered variational verification ---------------");

		// do for found configurations
		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
		if (featureConfigs != null) {
			String[] variants = generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					genCode.generate(project.getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i]);
					String configName = "";
					for (String s : featureConfigs[i]) configName += s;
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uri.toPlatformString(true), formula, new FileUtil(uri.toPlatformString(true)), configName);
					List<CbCFormula> refinements = generateCbCFormulasForRefinements(variants[i], callingMethod);
					List<JavaVariables> refinementsVars = generateJavaVariablesForRefinements(variants[i], callingMethod);
					proven = prove.proveStatementWithKey(refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, true);
				i=100;
				}
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}

	public List<CbCFormula> generateCbCFormulasForRefinements(String refs, String methodName) {
		List<CbCFormula> formulas = new ArrayList<CbCFormula>();
		if (refs == null) return formulas;
		String[] variants = refs.split(",");
		Collection<Diagram> diagrams = getDiagrams();
		for (String variant : variants) {
			boolean variantFound = false;
			String className = variant.split("\\.")[0];
			String featureName = variant.split("\\.")[1];
			for (Diagram dia : diagrams) {
				if (variantFound) break;
				URI diagramUri = dia.eResource().getURI();
				if (diagramUri.segment(diagramUri.segmentCount() - 3).equalsIgnoreCase(featureName)
						&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
						&& diagramUri.trimFileExtension().lastSegment().equals(methodName)
						&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z]*")) {
					for (Shape shape : dia.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof CbCFormula) {
							formulas.add((CbCFormula) obj);
							variantFound = true;
							break;
						}
					}
				}
			}
		}
		return formulas;
	}
	
	public List<JavaVariables> generateJavaVariablesForRefinements(String refs, String methodName) {
		List<JavaVariables> variables = new ArrayList<JavaVariables>();
		if (refs == null) return variables;
		String[] variants = refs.split(",");
		Collection<Diagram> diagrams = getDiagrams();
		for (String variant : variants) {
			boolean variantFound = false;
			String className = variant.split("\\.")[0];
			String featureName = variant.split("\\.")[1];
			for (Diagram dia : diagrams) {
				if (variantFound) break;
				URI diagramUri = dia.eResource().getURI();
				if (diagramUri.segment(diagramUri.segmentCount() - 3).equalsIgnoreCase(featureName)
						&& diagramUri.segment(diagramUri.segmentCount() - 2).equals(className)
						&& diagramUri.trimFileExtension().lastSegment().equals(methodName)
						&& diagramUri.trimFileExtension().lastSegment().matches("[a-z][a-zA-Z]*")) {
					for (Shape shape : dia.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							variables.add((JavaVariables) obj);
							variantFound = true;
							break;
						}
					}
				}
			}
		}
		return variables;
	}

	public String[] generateVariantsStringFromFeatureConfigs(String[][] featureConfigs, String callingFeature,	String className) {
		String[] variants = new String[featureConfigs.length];
		for (int i = 0; i < featureConfigs.length; i++) {
			for (int j = featureConfigs[i].length - 1; j >= 0; j--) {
				if (!featureConfigs[i][j].equals(callingFeature)) {
					if (variants[i] == null) {
						variants[i] = className + "." + featureConfigs[i][j].toLowerCase();
					} else {
						variants[i] += "," + className + "." + featureConfigs[i][j].toLowerCase();
					}
				}
			}
		}
		return variants;
	}

	private Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = GetDiagramUtil.getDiagrams(project);
			}
		}
		return result;
	}
}