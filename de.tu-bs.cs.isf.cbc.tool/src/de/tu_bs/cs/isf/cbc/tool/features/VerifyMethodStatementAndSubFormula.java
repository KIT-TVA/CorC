package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.Parser;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MyAbstractAsynchronousCustomFeature;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class VerifyMethodStatementAndSubFormula extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public VerifyMethodStatementAndSubFormula(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Verify the method statement and sub formula";
	}

	@Override
	public String getDescription() {
		return "Verifies that the precondition implies the precondition and post implies post.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(MethodStatementImpl.class))) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		monitor.beginTask("Verify", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof MethodStatement || bo instanceof AbstractStatement) {
				MethodStatement statement = (MethodStatement) bo;
				Collection<Diagram> diagrams = getLinkedDiagrams(statement);
				if (diagrams.size() == 1) {
					JavaVariables varsMethod = null;
					GlobalConditions condsMethod = null;
					Renaming renamingMethod = null;
					for (Shape shape : getDiagram().getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							varsMethod = (JavaVariables) obj;
						} else if (obj instanceof GlobalConditions) {
							condsMethod = (GlobalConditions) obj;
						} else if (obj instanceof Renaming) {
							renamingMethod = (Renaming) obj;
						}
					}
					
					CbCFormula formula = null;
					JavaVariables varsFormula = null;
					GlobalConditions condsFormula = null;
					Renaming renamingFormula = null;
					MethodClass javaClass = null;
					for (Shape shape : diagrams.iterator().next().getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof JavaVariables) {
							varsFormula = (JavaVariables) obj;
						} else if (obj instanceof GlobalConditions) {
							condsFormula = (GlobalConditions) obj;
						} else if (obj instanceof Renaming) {
							renamingFormula = (Renaming) obj;
						} else if (obj instanceof CbCFormula) {
							formula = (CbCFormula) obj;
						} else if (obj instanceof MethodClass) {
							javaClass = (MethodClass) obj;
						}
					}
					
					boolean prove = false;
					List<JavaVariable> vars = mergeJavaVariables(varsMethod, varsFormula);
					List<Condition> conds = mergeGlobalConditions(condsMethod, condsFormula);
					List<Rename> renaming = mergeRenaming(renamingMethod, renamingFormula);
//					String javaClass = statement.getName().substring(0, statement.getName().indexOf('.'));
					
					prove = ProveWithKey.proveMethodFormulaWithKey(formula.getStatement().getPreCondition(), statement.getPreCondition(), javaClass.getMethodClass(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
					if (prove) {
						prove = ProveWithKey.proveMethodFormulaWithKey(statement.getPostCondition(), formula.getStatement().getPostCondition(), javaClass.getMethodClass(), vars, conds, renaming, getDiagram().eResource().getURI(), monitor);
					}
					if (prove) {
						statement.setProven(true);
					} else {
						statement.setProven(false);
					}
					updatePictogramElement(((Shape)pes[0]).getContainer());
				}
			}
		}
		monitor.done();
	}
	
	private List<JavaVariable> mergeJavaVariables(JavaVariables vars1, JavaVariables vars2) {
		if (vars1 == null && vars2 == null) {
			return null;
		} else if (vars1 != null && vars2 == null) {
			return vars1.getVariables();
		} else if (vars1 == null && vars2 != null) {
			return vars2.getVariables();
		}
		List<JavaVariable> newVars = new ArrayList<JavaVariable>();
		newVars.addAll(vars2.getVariables());
		for (JavaVariable var1 : vars1.getVariables()) {
			boolean isNew = true;
			for (JavaVariable var2 : vars2.getVariables()) {
				if (var1.getName().equals(var2.getName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newVars.add(var1);
			}
			isNew = true;
		}
		return newVars;
	}
	
	private List<Condition> mergeGlobalConditions(GlobalConditions conds1, GlobalConditions conds2) {
		if (conds1 == null && conds2 == null) {
			return null;
		} else if (conds1 != null && conds2 == null) {
			return conds1.getConditions();
		} else if (conds1 == null && conds2 != null) {
			return conds2.getConditions();
		}
		List<Condition> newConds = new ArrayList<Condition>();
		newConds.addAll(conds2.getConditions());
		for (Condition cond1 : conds1.getConditions()) {
			boolean isNew = true;
			for (Condition cond2 : conds2.getConditions()) {
				if (cond1.getName().equals(cond2.getName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newConds.add(cond1);
			}
			isNew = true;
		}
		return newConds;
	}
	
	private List<Rename> mergeRenaming(Renaming renaming1, Renaming renaming2) {
		if (renaming1 == null && renaming2 == null) {
			return null;
		} else if (renaming1 != null && renaming2 == null) {
			return renaming1.getRename();
		} else if (renaming1 == null && renaming2 != null) {
			return renaming2.getRename();
		}
		List<Rename> newRenaming = new ArrayList<Rename>();
		newRenaming.addAll(renaming2.getRename());
		for (Rename rename1 : renaming1.getRename()) {
			boolean isNew = true;
			for (Rename rename2 : renaming2.getRename()) {
				if (rename1.getFunction().equals(rename2.getFunction())
						&& rename1.getType().equals(rename2.getType())
						&& rename1.getNewName().equals(rename2.getNewName())) {
					isNew = false;
				}
			}
			if (isNew) {
				newRenaming.add(rename1);
			}
			isNew = true;
		}
		return newRenaming;
	}
	
	private Collection<Diagram> getLinkedDiagrams(MethodStatement statement) {
		final Collection<Diagram> ret = new HashSet<Diagram>();
		JavaVariables vars = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} 
		}
		String s = getSignature(statement.getName(), vars);
		final Collection<Diagram> allDiagrams = getDiagrams();
		for (final Diagram d : allDiagrams) {
			final Diagram currentDiagram = getDiagram();
			if (!EcoreUtil.equals(currentDiagram, d)) { // always filter out the
														// current
				// diagram
				final Object[] businessObjectsForDiagram = getAllBusinessObjectsForPictogramElement(d);
				for (Object businessObjectForDiagram : businessObjectsForDiagram) {
					if (businessObjectForDiagram instanceof CbCFormula) {
						final CbCFormula formula = (CbCFormula) businessObjectForDiagram;
						if (formula != null && formula.getMethodName() != null) {
							if(formula.getMethodName().equals(s))
								ret.add(d);
						}
					}
				}
			}
		}

		return ret;
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
	
	private String getSignature(String methodName, JavaVariables vars) {
		if(methodName.matches("(\\(.+\\))")) {//first indexof ( = indexof)+1
			String[] methodVariables = methodName.substring(methodName.indexOf('(') + 1, methodName.lastIndexOf(')')).split(",");
			for(int j = 0; j < methodVariables.length; j++) {
				if(methodVariables[j].contains("(")) {//Cast
					methodVariables[j] = methodVariables[j].substring(methodVariables[j].indexOf('(') + 1, methodVariables[j].indexOf(')'));
				} else if(methodVariables[j].trim().startsWith("\"")) {
					methodVariables[j] = "String";
				} else if(methodVariables[j].trim().startsWith("\'")) {
					methodVariables[j] = "char";
				} else if(methodVariables[j].trim().matches("(\\-)?(\\d+)")) {
					try {
			             int i = Integer.parseInt(methodVariables[j].trim());
			             methodVariables[j] = "int";
			        }
			        catch (NumberFormatException e)
			        {
			             long l = Long.valueOf(methodVariables[j]);
			             methodVariables[j] = "long";
			        }
				} else if(methodVariables[j].trim().matches("(\\-)?(\\d+\\.\\d+)")) {
					methodVariables[j] = "double";
				} else if(methodVariables[j].trim().equals("true") || methodVariables[j].trim().equals("false") ) {
					methodVariables[j] = "boolean";
				} else {
					methodVariables[j] = Parser.getTypeOfVariable(methodVariables[j], vars);							
				} 
			}
			methodName = methodName.substring(0, methodName.indexOf('(') + 1);
			if(methodVariables.length == 1) {
				methodName  = methodName + methodVariables[0];
			} else {
				for(int j = 0; j < methodVariables.length - 1; j++) {
					methodName = methodName + methodVariables[j] + ",";
				}
				methodName = methodName + methodVariables[methodVariables.length - 1];
			}
		    methodName = methodName + ")";
		} 
	    methodName = methodName.replaceFirst("\\w+.", "");	
	    methodName = methodName.replaceFirst(";", "");
	    methodName = methodName.replaceAll("\\r\\n*|\\n+", "");
		return methodName;
	}
}