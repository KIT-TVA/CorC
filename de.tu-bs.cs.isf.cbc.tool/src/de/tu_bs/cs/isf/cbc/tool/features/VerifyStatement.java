package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateCodeForVariationalVerification;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.KeYInteraction;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyStatement extends MyAbstractAsynchronousCustomFeature {
	private String proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
	
	public void setProofType(String proofType) {
		this.proofType = proofType;
	}

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
		Console.clear();
		monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				boolean returnStatement = bo instanceof ReturnStatement;
				AbstractStatement statement = (AbstractStatement) bo;
				
				boolean proven = false;
				URI uri = getDiagram().eResource().getURI();
				// delete 'tests' folder if it exists because it will cause reference errors
				// since key doesn't use TestNG.
				FileHandler.instance.deleteFolder(uri, "tests");

				IProject project = FileUtil.getProjectFromFileInProject(uri);
				boolean isVariational = false;
				if (FileHandler.instance.isSPL(uri)) {
					isVariational = true;
				}

				if (isVariational) {
					proven = executeVariationalVerification(project, statement, getDiagram(), returnStatement, monitor);
				} else {
					proven = executeNormalVerification(statement, getDiagram(), returnStatement, monitor);
				}
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		// reset proof type since partial proofs also call this method.
		proofType = KeYInteraction.ABSTRACT_PROOF_FULL;
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("\nVerification done."); 
		Console.println("Time needed: " + duration + "ms");
		monitor.done();
	}

	private boolean executeNormalVerification(AbstractStatement statement, Diagram diagram, boolean returnStatement, IProgressMonitor monitor) {
		StatDataCollector.checkForId(statement);
		boolean proven = false;
		Console.println("Starting verification...\n");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
			URI uri = getDiagram().eResource().getURI();
			String platformUri = uri.toPlatformString(true);
			String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
			ProveWithKey prove = new ProveWithKey(statement, getDiagram(), monitor, new FileUtil(platformUri), null, 0, proofType);
			proven = prove.proveStatementWithKey(returnStatement, false, callingClass, true);
		} else {
			Console.println("Statement is not in correct format.");
		}
		return proven;
	}

	private boolean executeVariationalVerification(IProject project, AbstractStatement statement, Diagram diagram, boolean returnStatement, IProgressMonitor monitor) {
		StatDataCollector.checkForId(statement);
		boolean proven = false;
		DiagramPartsExtractor extractor = new DiagramPartsExtractor(diagram);
    	JavaVariables vars = extractor.getVars();
		GlobalConditions conds = extractor.getConds();
		Renaming renaming = extractor.getRenaming();
		CbCFormula formula = extractor.getFormula();		
		URI uri = diagram.eResource().getURI();
		String callingFeature = FeatureUtil.getInstance().getCallingFeature(uri);
		String callingClass = FeatureUtil.getInstance().getCallingClass(uri);
		String callingMethod = FeatureUtil.getInstance().getCallingMethod(uri);
		String[][] featureConfigs = VerifyFeatures.verifyConfig(uri, callingMethod, true, callingClass, false, null);
		String[][] featureConfigsRelevant = VerifyFeatures.verifyConfig(uri, callingMethod, true, callingClass, true, null);
		
		Console.println("Starting variational verification...\n");

		// do for found configurations
		GenerateCodeForVariationalVerification genCode = new GenerateCodeForVariationalVerification(super.getFeatureProvider());
		if (featureConfigs != null) {
			String[] variants = generateVariantsStringFromFeatureConfigs(featureConfigsRelevant, callingFeature, callingClass);
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
					genCode.setProofTypeInfo(i, proofType);
					if(!genCode.generate(project.getLocation(), callingFeature, callingClass, callingMethod, featureConfigs[i])) continue;
					ProveWithKey prove = new ProveWithKey(statement, diagram, monitor, new FileUtil(uri.toPlatformString(true)), featureConfigs[i], i, proofType);
					List<CbCFormula> refinements = generateCbCFormulasForRefinements(variants[i], callingMethod);
					List<JavaVariables> refinementsVars = generateJavaVariablesForRefinements(variants[i], callingMethod);
					proven = prove.proveStatementWithKey(null, refinements, refinementsVars, returnStatement, false, callingMethod, "", callingClass, true);
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
				String diagramFeature = FeatureUtil.getInstance().getCallingFeature(diagramUri);
				String diagramClass = FeatureUtil.getInstance().getCallingClass(diagramUri);
				String diagramMethod = FeatureUtil.getInstance().getCallingMethod(diagramUri);
				if (diagramFeature.equalsIgnoreCase(featureName)
						&& diagramClass.equals(className)
						&& diagramMethod.equals(methodName)
						&& diagramMethod.matches("[a-z][a-zA-Z]*")) {
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
				String diagramFeature = FeatureUtil.getInstance().getCallingFeature(diagramUri);
				String diagramClass = FeatureUtil.getInstance().getCallingClass(diagramUri);
				String diagramMethod = FeatureUtil.getInstance().getCallingMethod(diagramUri);
				if (diagramFeature.equalsIgnoreCase(featureName)
						&& diagramClass.equals(className)
						&& diagramMethod.equals(methodName)
						&& diagramMethod.matches("[a-z][a-zA-Z]*")) {
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
