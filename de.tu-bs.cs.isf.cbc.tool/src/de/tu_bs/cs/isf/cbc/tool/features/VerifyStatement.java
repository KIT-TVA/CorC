package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
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
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
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
	 * @param fp
	 *            The FeatureProvider
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
		monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				
				//get content from diagram
				boolean returnStatement = bo instanceof ReturnStatement;
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				MethodClass javaClass = null;
				for (Shape shape : getDiagram().getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables) {
						vars = (JavaVariables) obj;
					} else if (obj instanceof GlobalConditions) {
						conds = (GlobalConditions) obj;
					} else if (obj instanceof Renaming) {
						renaming = (Renaming) obj;
					} else if (obj instanceof MethodRefinements) {
						Console.println("Manually specified refinements are ignored in this version of VarCorC!");
					} else if(obj instanceof MethodClass) {
						javaClass = (MethodClass) obj;
					}
				}
				
				//project infos
				boolean proven = false;				
				String callingMethod = getDiagram().getName();
				URI uri = getDiagram().eResource().getURI();
				IProject project = FileUtil.getProject(uri); 
				boolean isVariational = false;
				try {
					if(project.getNature("de.ovgu.featureide.core.featureProjectNature") != null) {
						isVariational = true;
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(isVariational) {
					proven = executeVariationalVerification(project, uri, statement, vars, conds, renaming, javaClass,returnStatement, callingMethod, monitor);
				} else {
					proven = executeNormalVerification(statement, vars, conds, renaming, javaClass, returnStatement, monitor, callingMethod);
				}					
				
				statement.setProven(proven);
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		Console.println("--------------- Verification completed ---------------");
		monitor.done();
	}
	
	private boolean executeVariationalVerification(IProject project, URI uri, AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, MethodClass javaClass, boolean returnStatement, String callingMethod, IProgressMonitor monitor) {
		boolean proven = false;
		String projectName = project.getName();
		String[][] featureConfigs = null;
		String callingFeature = uri.segment(uri.segmentCount()-3) + "";
		String stmt = statement.getName();
		boolean isInSameClass = true;
		String varM = "";
		if (!stmt.contains("original(") && stmt.matches(".*\\w*\\(\\w*\\).*")) { //contains method call and not original
			varM = Parser.extractMethodNameFromStatemtent(stmt);
			isInSameClass= stmt.matches(".*\\w*\\.\\w*\\(.*"); //(x=)Class.method(...)
			featureConfigs = VerifyFeatures.calculateFeatureConfigs(uri, varM, !isInSameClass);
		} else { //original or normal statement
			featureConfigs = VerifyFeatures.calculateFeatureConfigs(uri, uri.segment(uri.segmentCount()-1), true);
		}
		Console.println("--------------- Triggered variational verification ---------------");
		String location = project.getLocation() + "/src_gen/Generated_" + projectName + ".java";
		writeFile(location, "");
		//do for found configurations
		String varMParts[] = varM.split("\\.");
		if (featureConfigs != null) {
			String[] variants = generateVariantsStringFromFeatureConfigs(featureConfigs, callingFeature, projectName);
			String methodName = isInSameClass ? callingMethod : varMParts[0];
			generateMethodStubs(projectName, methodName, location);
			
			if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
				for (int i = 0; i < variants.length; i++) {
						Console.println(printConfiguration(featureConfigs, i));
						if (isInSameClass) {
							proven = ProveWithKey.proveStatementWithKey(statement, vars, conds, returnStatement, false, renaming, javaClass, variants[i], uri, i , monitor, varMParts[0], varMParts[0]);
						} else {
							proven = ProveWithKey.proveStatementWithKey(statement, vars, conds, returnStatement, false, renaming, javaClass, variants[i], uri, i , monitor, callingMethod, varMParts[0]);
						}
				}					
			} else {
				Console.println("  Statement is not in correct format.");
			}
		}
		return proven;
	}

	private Object printConfiguration(String[][] featureConfigs, int i) {
		String output = "Configuration: ";
		for (int j = 0; j < featureConfigs[i].length; j++) {
			if (j == featureConfigs[i].length-1) {
				output += featureConfigs[i][j];
			} else {
				output += featureConfigs[i][j] + ", ";
			}
		}
		return output;
	}

	private String[] generateVariantsStringFromFeatureConfigs(String[][] featureConfigs, String callingFeature, String projectName) {
		String[] variants = new String[featureConfigs.length];
		for (int i = 0; i < featureConfigs.length; i++) {
			//für jede Konfiguration führe aus
			for (int j = featureConfigs[i].length - 1; j >= 0; j--) {
				if (!featureConfigs[i][j].equals(callingFeature)) {
					if (variants[i] == null) {
						variants[i] = projectName + "." + featureConfigs[i][j].toLowerCase();
					} else {
						variants[i] += "," + projectName + "." + featureConfigs[i][j].toLowerCase();
					}
				}						
			}
		}
		return variants;
		
		/*
		for (int i = 0; i < featureConfigs.length; i++) {
			for (int j = 0; j < featureConfigs[i].length; j++) {
				Console.println(featureConfigs[i][j]);
			}
		}
		for (int i = 0; i < variants.length; i++) {
				Console.println(variants[i] + "");
			
		}
		*/
	}

	private void generateMethodStubs(String className, String methodName, String location) {
		JavaVariables variables = null;
		Renaming renamings = null;
		CbCFormula formula = null;
		
		
		String code = "public class Generated_" + className + " {";
		Collection<Diagram> diagrams = Collections.emptyList();
		diagrams = getDiagrams();			
			
			for (Diagram dia : diagrams) {
				if (dia.getName().equalsIgnoreCase(methodName)) {
					for (Shape shape : dia.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							variables = (JavaVariables) obj;
						} else if (obj instanceof Renaming) {
							renamings = (Renaming) obj;
						} else if (obj instanceof CbCFormula) {
							formula = (CbCFormula) obj;
						}
					}
					URI methodUri = formula.eResource().getURI();
					String featureName = methodUri.segment(methodUri.segmentCount()-3) + "";
					code += "\n\n" + ConstructCodeBlock.constructMethodStubsForExport(formula, renamings, variables, featureName, className);
				}
			}
		
		code += "\n}";
		writeFile(location, code);	
	}

	private boolean executeNormalVerification(AbstractStatement statement, JavaVariables vars, GlobalConditions conds, Renaming renaming, MethodClass javaClass, boolean returnStatement, IProgressMonitor monitor, String callingMethod) {
		boolean proven = false;
		Console.println("--------------- Triggered verification ---------------");
		if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
            proven = ProveWithKey.proveStatementWithKey(statement, vars, conds, returnStatement, false, renaming, javaClass,
                            "", getDiagram().eResource().getURI(), 0, monitor, callingMethod, "");
		} else {
            Console.println("Statement is not in correct format.");
		}
		return proven;
	}

	private void writeFile(String location, String code) {
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			}
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(code);
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(javaFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	private Collection<Diagram> getDiagrams() {
	       Collection<Diagram> result = Collections.emptyList();
	       Resource resource = getDiagram().eResource();
	       URI uri = resource.getURI();
	       URI uriTrimmed = uri.trimFragment();
	       if (uriTrimmed.isPlatformResource()){
	           String platformString = uriTrimmed.toPlatformString(true);
	           IResource fileResource = ResourcesPlugin.getWorkspace()
	             .getRoot().findMember(platformString);
	           if (fileResource != null){
	               IProject project = fileResource.getProject();
	               result = GetDiagramUtil.getDiagrams(project);
	           }
	       }
	       return result;
	    }
}