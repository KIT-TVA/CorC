package de.tu_bs.cs.isf.cbc.tool.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
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
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;

/**
 * Class that changes the abstract value of algorithms
 * 
 * @author Tobias
 *
 */
public class PrintFormulaFeature extends MyAbstractAsynchronousCustomFeature {

	/**
	 * Constructor of the class
	 * 
	 * @param fp
	 *            The FeatureProvider
	 */
	public PrintFormulaFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Extract Code";
	}

	@Override
	public String getDescription() {
		return "Generates the code of the created algorithm.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		MethodClass javaClass = null;
		MethodSignature signature = null;
		JavaVariables vars = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
			if(obj instanceof MethodClass) {
				javaClass = (MethodClass) obj;
			} else if(obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			} else if(obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			}
		}
		if(javaClass == null || signature == null || vars == null)
			return false;
		return true;
	}
	
	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) { 
		
		JavaVariables vars = null; 
		Renaming renaming = null;
		MethodClass javaClass = null;
		MethodSignature signature = null;
		CbCFormula formula = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if(obj instanceof MethodClass) {
				javaClass = (MethodClass) obj;
			} else if(obj instanceof MethodSignature) {
				signature = (MethodSignature) obj;
			}
		}

		String parameters = signature.getMethodSignature().substring(signature.getMethodSignature().indexOf('(') + 1, signature.getMethodSignature().indexOf(')'));
			
		Set<String> hs1 = new HashSet<String>();
		if(!parameters.isEmpty()) {
			String[] splitParameters = parameters.split(",", 0);
			for(int i = 0; i < splitParameters.length; i++) {
				hs1.add(splitParameters[i].trim());
			}
		}
		
		Set<String> hs2 = new HashSet<String>();
		LinkedList<String> localVariables = new LinkedList<String>();
		JavaVariable returnVariable = null;
		int counter = 0;
		for(int i = 0; i < vars.getVariables().size(); i++) {
			JavaVariable currentVariable = vars.getVariables().get(i);
			if(currentVariable.getKind() == VariableKind.PARAM) {
				hs2.add(currentVariable.getName());	
			} else if(currentVariable.getKind() == VariableKind.LOCAL) {
				localVariables.add(currentVariable.getName());
			} else if(currentVariable.getKind() == VariableKind.GLOBAL_PARAM) {
				hs2.add(currentVariable.getName());
			} else if(currentVariable.getKind() == VariableKind.RETURN) {
				counter++;
				if(!signature.getMethodSignature().substring(0, signature.getMethodSignature().indexOf('(')).contains(currentVariable.getName().trim().split(" ")[0])) {
					Console.println("Method return type and variable type does not match.");
					return;
				}
				if(counter > 1) {
					Console.println("Too much variables of kind RETURN.");
					return;
				}
				returnVariable = currentVariable;
			}
		}
		
		if(counter == 0 && !signature.getMethodSignature().contains("void")) {//void must have no return variables
			Console.println("Variable of kind RETURN is missing.");
			return;
		}
		
	    if(!hs1.equals(hs2)) {
	    	Console.println("Method parameters are not known");
			return;
	    }
	    
		Display.getDefault().asyncExec(() -> { // oder: getDiagramBehavior().getDiagramContainer().doSave(monitor);			
			/*try {				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			*/
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart editor = page.getActiveEditor();
			Console.println("start saving");
			page.saveEditor(editor, true);	
			Console.println("saved");
		});
		
		LinkedList<String> globalVariablesList = getVariables(javaClass.getMethodClass());
	    
		if(returnVariable != null) {//check if return variable is also as local, param or global implemented
			JavaVariable j =  returnVariable;
			if(!globalVariablesList.contains(returnVariable.getName()) && !globalVariablesList.contains("static " + returnVariable.getName())
					&& vars.getVariables().stream().filter(e -> e.getName().equals(j.getName()) || e.getName().equals("static " + j.getName())).count() < 2) {
				localVariables.add(j.getName());
			}
		}
		
		StringBuffer globalVariables = new StringBuffer();
		globalVariablesList.forEach(e -> {globalVariables.append("\tprivate " + e + ";\n");});

		String code = ConstructCodeBlock.constructCodeBlockForExport(formula, renaming, localVariables, returnVariable, signature);
		
		URI uri = getDiagram().eResource().getURI(); 
		String location = FileUtil.getProject(uri).getLocation() + "/src/" + javaClass.getMethodClass() + ".java";
		writeFile(location, code, signature, javaClass.getMethodClass(), globalVariables.toString());
	}
	
	protected Collection<Diagram> getDiagrams() {
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
	
	public LinkedList<String> getVariables(String javaClass){		
		boolean addVars = false;
		LinkedList<String> vars = new LinkedList<String>();
		final Collection<Diagram> allDiagrams = getDiagrams();
		for (final Diagram d : allDiagrams) {
			final Diagram currentDiagram = getDiagram();
			if (!currentDiagram.getName().equals(d.getName())) {//!EcoreUtil.equals(currentDiagram, d) doesnt work here!
				for (Shape shape : d.getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if(obj instanceof MethodClass) {
						if(((MethodClass) obj).getMethodClass().equals(javaClass)) {
							addVars = true;
						} 
					}
				}
//			}
//			if (!currentDiagram.getName().equals(d.getName())) {//!EcoreUtil.equals(currentDiagram, d) doesnt work here!
				for (Shape shape : d.getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof JavaVariables && addVars) {
						Iterator<JavaVariable> itr = ((JavaVariables) obj).getVariables().iterator();
						while(itr.hasNext()) {
							JavaVariable var = itr.next();
							boolean b = false;
							if(var.getKind() == VariableKind.GLOBAL || var.getKind() == VariableKind.GLOBAL_PARAM) {
								for(int i = 0; i < vars.size() ; i++) {
									if(var.getName().substring(var.getName().trim().lastIndexOf(' ')).equals(vars.get(i).substring(vars.get(i).trim().lastIndexOf(' ')))) /*|| ("static " + var.getName()).equals(vars.get(i)))*/ {
										b = true;
									}							
								}
							} else
								b = true;
							if(!b)
								vars.add(var.getName());
							b = false;
						}
					}
				}
				addVars = false;
			}
		}
		
		JavaVariables jv = null;
		for (Shape shape : getDiagram().getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape); 
			if (obj instanceof JavaVariables) {
				jv = (JavaVariables) obj;
			} 
		}
		
		Iterator<JavaVariable> itr = jv.getVariables().iterator();
		while(itr.hasNext()) {
			JavaVariable variable = itr.next();
			boolean b = false;
			if(variable.getKind() == VariableKind.GLOBAL || variable.getKind() == VariableKind.GLOBAL_PARAM) {
				for(int i = 0; i < vars.size() ; i++) {
					if(variable.getName().substring(variable.getName().trim().lastIndexOf(' ')).equals(vars.get(i).substring(vars.get(i).trim().lastIndexOf(' ')))) {// || ("static " + variable.getName()).equals(vars.get(i))) {
						b = true;										
					}							
				}
			} else
				b = true;
			if(!b)
				vars.add(variable.getName());
			b = false;
		}
		
		//StringBuffer globalVariables = new StringBuffer();
		//vars.forEach(e -> {globalVariables.append("\tprivate " + e + ";\n");});
		//return globalVariables.toString();
		return vars;
	}
	
	private void writeFile(String location, String code, MethodSignature signature, String className, String globalVariables) {
		
		//StringBuffer globalVariables = new StringBuffer();
		//globalVariables.forEach(e -> {globalVariables.append("\tprivate " + e + ";\n");});
		// globalVariables.toString();
		StringBuffer newCode = new StringBuffer();
		newCode.setLength(0);
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.createNewFile();
			} else {
				newCode = ConstructCodeBlock.editCodeBlockForExport(code, javaFile, signature, globalVariables);
			}
			
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			if(newCode.length() == 0) {
				if(globalVariables.isEmpty())
					bw.write("public class " + className + "{\n\n" + code + "\n}"); 
				else {
					bw.write("public class " + className + "{\n\n" + globalVariables + "\n" + code + "\n}");
				}
			}
			else {
				newCode.append("\n}");
				bw.write(newCode.toString());
			}
			
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

	// private void printFormula(CbCFormula formula) {
	// StringBuffer buffer = new StringBuffer();
	// String tabs = "\t";
	// buffer.append("Formula: " + formula.getName() + "\n");
	// buffer.append("PreCondition: " +
	// formula.getStatement().getPreCondition().getName() + "\n");
	// if (formula.getStatement().getRefinement() != null) {
	// chooseStatement(buffer, tabs, formula.getStatement());
	// } else {
	// buffer.append("Statement: " + formula.getStatement().getName() + "\n");
	// }
	//
	// buffer.append("PostCondition: " +
	// formula.getStatement().getPostCondition().getName() + "\n");
	// System.out.println(buffer.toString());
	// }
	//
	// private void printAbstractStatement(StringBuffer buffer, String tabs,
	// AbstractStatement statement) {
	// if (statement.getRefinement() != null) {
	// chooseStatement(buffer, tabs + "\t", statement);
	// } else {
	// buffer.append(statement.getName() + "\n");
	// }
	// }
	//
	// private void printRepetitionStatement(StringBuffer buffer, String tabs,
	// RepetitionStatement statement) {
	// buffer.append("RepetitionStatement" + "\n");
	// buffer.append(tabs + "Invariant: " + statement.getInvariant().getName() +
	// "\n");
	// buffer.append(tabs + "Variant: " + statement.getVariant().getName() + "\n");
	// if (statement.getStartStatement().getRefinement() != null) {
	// buffer.append(tabs + "StartStatement: ");
	// chooseStatement(buffer, tabs + "\t", statement.getStartStatement());
	// } else {
	// buffer.append(tabs + "StartStatement: " +
	// statement.getStartStatement().getName() + "\n");
	// }
	// buffer.append(tabs + "Guard: " + statement.getGuard().getName() + "\n");
	// if (statement.getLoopStatement().getRefinement() != null) {
	// buffer.append(tabs + "LoopStatement: ");
	// chooseStatement(buffer, tabs + "\t", statement.getLoopStatement());
	// } else {
	// buffer.append(tabs + "LoopStatement: " +
	// statement.getLoopStatement().getName() + "\n");
	// }
	// if (statement.getEndStatement().getRefinement() != null) {
	// buffer.append(tabs + "EndStatement: ");
	// chooseStatement(buffer, tabs + "\t", statement.getEndStatement());
	// } else {
	// buffer.append(tabs + "EndStatement: " + statement.getEndStatement().getName()
	// + "\n");
	// }
	//
	// }
	//
	// private void printCompositionStatement(StringBuffer buffer, String tabs,
	// CompositionStatement statement) {
	// buffer.append("CompositionStatement" + "\n");
	// if (statement.getFirstStatement().getRefinement() != null) {
	// buffer.append(tabs + "FirstStatement: ");
	// chooseStatement(buffer, tabs + "\t", statement.getFirstStatement());
	// } else {
	// buffer.append(tabs + "FirstStatement: " +
	// statement.getFirstStatement().getName() + "\n");
	// }
	// buffer.append(tabs + "IntermediateCondition: " +
	// statement.getIntermediateCondition().getName() + "\n");
	// if (statement.getSecondStatement().getRefinement() != null) {
	// buffer.append(tabs + "SecondStatement: ");
	// chooseStatement(buffer, tabs + "\t", statement.getSecondStatement());
	// } else {
	// buffer.append(tabs + "SecondStatement: " +
	// statement.getSecondStatement().getName() + "\n");
	// }
	// }
	//
	// private void printSelectionStatement(StringBuffer buffer, String tabs,
	// SelectionStatement statement) {
	// buffer.append("SelectionStatement" + "\n");
	// for (int i = 0; i < statement.getGuards().size(); i++) {
	// buffer.append(tabs + "Guard " + i + ": " +
	// statement.getGuards().get(i).getName() + "\n");
	// if (statement.getCommands().get(i).getRefinement() != null) {
	// buffer.append(tabs + "SelectionStatement " + i + ": ");
	// chooseStatement(buffer, tabs + "\t", statement.getCommands().get(i));
	// } else {
	// buffer.append(tabs + "SelectionStatement " + i + ": " +
	// statement.getCommands().get(i).getName() + "\n");
	// }
	// }
	// }
	//
	// private void printSkipStatement(StringBuffer buffer, String tabs,
	// SkipStatement statement) {
	// buffer.append("Skip" + "\n");
	// }
	//
	//
	// private void chooseStatement(StringBuffer buffer, String tabs,
	// AbstractStatement statement) {
	// statement = statement.getRefinement();
	// if (statement.getClass().equals(AbstractStatementImpl.class)) {
	// printAbstractStatement(buffer, tabs, statement);
	// } else if (statement.getClass().equals(SkipStatementImpl.class)) {
	// printSkipStatement(buffer, tabs, (SkipStatement) statement);
	// } else if (statement.getClass().equals(ReturnStatementImpl.class)) {
	// printReturnStatement(buffer, tabs, (ReturnStatement) statement);
	// } else if (statement.getClass().equals(MethodStatementImpl.class)) {
	// printMethodStatement(buffer, tabs, (MethodStatement) statement);
	// } else if (statement.getClass().equals(SelectionStatementImpl.class)) {
	// printSelectionStatement(buffer, tabs, (SelectionStatement) statement);
	// } else if (statement.getClass().equals(CompositionStatementImpl.class)) {
	// printCompositionStatement(buffer, tabs, (CompositionStatement) statement);
	// } else if (statement.getClass().equals(RepetitionStatementImpl.class)) {
	// printRepetitionStatement(buffer, tabs, (RepetitionStatement) statement);
	// }
	//
	// }
	//
	// private void printMethodStatement(StringBuffer buffer, String tabs,
	// MethodStatement statement) {
	// buffer.append(statement.getName() + ";\n");
	// }
	//
	// private void printReturnStatement(StringBuffer buffer, String tabs,
	// ReturnStatement statement) {
	// buffer.append(statement.getName() + "\n");
	// }
}