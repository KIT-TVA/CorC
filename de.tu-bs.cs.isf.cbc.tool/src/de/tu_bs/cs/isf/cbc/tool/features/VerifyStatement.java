package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodRefinements;
import de.tu_bs.cs.isf.cbc.cbcmodel.ProductVariant;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.cbc.util.VerifyFeatures;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;
import de.tu_bs.cs.isf.toolkit.support.compare.CompareMethodBodies;


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
				AbstractStatement statement = (AbstractStatement) bo;
				JavaVariables vars = null;
				GlobalConditions conds = null;
				Renaming renaming = null;
				String[] variants = new String[0];
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
					}
				}
				
			
				boolean prove = false;				
				String[][] featureConfigs = null;
				URI uri = getDiagram().eResource().getURI();
				String callingFeature = uri.segment(uri.segmentCount()-3) + "";
				IProject project = FileUtil.getProject(uri); 
				String projectString = project.getLocation().toOSString();
				String projectPath[] = projectString.split("\\\\");
				String projectName = projectPath[projectPath.length-1];
				String callingMethod = uri.segment(uri.segmentCount()-1) + "";
				String callingMethodParts[] = callingMethod.split("\\.");
				
				//handle calling statement
				boolean original = false;
				boolean variableM = false;
				String varM = "";
				if (statement.getName().contains("original(")) {
					original = true;
					featureConfigs = VerifyFeatures.verifyConfig(uri, uri.segment(uri.segmentCount()-1), original);
				} else if (statement.getName().contains("(")) {
					original = false;
					String stmt = statement.getName();
					char stmtChar[] = stmt.toCharArray();
					boolean name = false;
					for (int i = stmtChar.length -1; i >= 0; i--) {
						if (!name && stmtChar[i] == '(') {
							name = true;
						} else if (name && Character.isLetter(stmtChar[i])) {
							varM = stmtChar[i] + varM;
							variableM = true;
						} else if (name && stmtChar[i] == '.') {
							variableM = false;
							break;
						} else {
							name = false;
						}
					}
					if (variableM) {
						varM = varM.substring(0, 1).toUpperCase() + varM.substring(1) + ".diagram";
						featureConfigs = VerifyFeatures.verifyConfig(uri, varM, !variableM);
						//Console.println(varM);
					}
					
				} 
				Console.println("--------------- Triggered verification---------------");
				String location = FileUtil.getProject(uri).getLocation() + "/src_gen/Generated_" + projectName + ".java";
				writeFile(location, "");
				//do for found configurations
				String varMParts[] = varM.split("\\.");
				if (featureConfigs != null) {
					variants = new String[featureConfigs.length];
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
					JavaVariables variables = null;
					Renaming renamings = null;
					CbCFormula formula = null;
					
					
					String code = "public class Generated_" + projectName + " {";
					Collection<Diagram> diagramme = Collections.emptyList();
					diagramme = getDiagrams();
					if (variableM) {
						for (Diagram dia : diagramme) {
							if (dia.getName().equalsIgnoreCase(varMParts[0])) {
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
								code += "\n\n" + ConstructCodeBlock.constructMethodStubsForExport(formula, renamings, variables, featureName, projectName);
							}
						}
					} else {
						for (Diagram dia : diagramme) {
							if (dia.getName().equals(callingMethodParts[0])) {
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
								code += "\n\n" + ConstructCodeBlock.constructMethodStubsForExport(formula, renamings, variables, featureName, projectName);
							}
						}
					}
					
					code += "\n}";
					writeFile(location, code);
					
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
						for (int i = 0; i < variants.length; i++) {
							if (variants[i] != null) {
								String output = "Configuration: ";
								for (int j = 0; j < featureConfigs[i].length; j++) {
									if (j == featureConfigs[i].length-1) {
										output += featureConfigs[i][j];
									} else {
										output += featureConfigs[i][j] + ", ";
									}
								}
								Console.println(output);
								if (variableM) {
									prove = ProveWithKey.proveStatementWithKey(statement, vars, conds, renaming, variants[i], uri, i , monitor, varMParts[0], varMParts[0]);
								} else {
									prove = ProveWithKey.proveStatementWithKey(statement, vars, conds, renaming, variants[i], uri, i , monitor, callingMethodParts[0], varMParts[0]);
								}
								//writeFile(location, "");
								if (!prove) {
									//break;
								}
							}
						}					
					} else {
						Console.println("  Statement is not in correct format.");
					}
				} else { //do for normal statement
					if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
                        prove = ProveWithKey.proveStatementWithKey(statement, vars, conds, renaming, null,
                                        getDiagram().eResource().getURI(), 0, monitor, callingMethodParts[0], "");
					} else {
                        Console.println("Statement is not in correct format.");
                }
				}					
				
				if (prove) {
					statement.setProven(true);
				} else {
					statement.setProven(false);
				}
				updatePictogramElement(((Shape) pes[0]).getContainer());
			}
		}
		Console.println("--------------- Verification completed ---------------");
		monitor.done();
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