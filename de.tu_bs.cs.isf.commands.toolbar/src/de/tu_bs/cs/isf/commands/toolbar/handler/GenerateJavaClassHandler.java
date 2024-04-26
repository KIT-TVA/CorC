package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.ConstructCodeBlock;

public class GenerateJavaClassHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			if (strucSelection.toList().size() != 1) {
				throw new ExecutionException("Select only one File.");
			}
			
			List<IFile> iFiles= new ArrayList<IFile>();
			IFolder iFolder = null;
			
			
			if (strucSelection.toList().get(0) instanceof PackageFragment) {
				PackageFragment pF = ((PackageFragment) strucSelection.toList().get(0));
				IPath tempPath = pF.getPath();
				iFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(pF.getPath());
				
			} else if (strucSelection.toList().get(0) instanceof IFolder) {
				List<IFolder> fileList = strucSelection.toList();
				iFolder = fileList.get(0);
			} else if (strucSelection.toList().get(0) instanceof IFile){
				iFiles.add((IFile) strucSelection.toList().get(0));
			}
			
			if (iFolder != null) {
				try {
					IResource[] resources = iFolder.members();
					for(IResource resource : resources) {
						if(resource instanceof IFile) {
							IFile file = (IFile) resource;
							if (file.getFileExtension().equals("cbcmodel")) {
								iFiles.add(file);
							}
						}
					} 
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (iFiles.size() < 1) {
				throw new ExecutionException("Select a Folder that contains cbcmodel files.");
			}
			
			for (IFile iFile : iFiles) {
				Resource res = null;
				ResourceSet rs = new ResourceSetImpl();
				URI uri = null;
				
				System.out.println(iFile.getLocation().toPortableString());
				
				if (iFile.getFileExtension().equals("diagram")) {
					uri = rs.getURIConverter().normalize(URI.createFileURI(iFile.getLocation().toPortableString().replace(".diagram", ".cbcmodel")));
				} else {
					uri = URI.createFileURI(iFile.getLocation().toPortableString());
				}
				res = rs.getResource(uri, true);
				CbCFormula formula = (CbCFormula) res.getContents().get(0);
				JavaVariables vars = (JavaVariables) res.getContents().get(1);
						
				String signatureString = formula.getMethodObj() != null ? formula.getMethodObj().getSignature() : ("public void " + formula.getName().toLowerCase() + " ()");
			
				JavaVariable returnVariable = null;
				int counter = 0;
				LinkedList<String> localVariables = new LinkedList<String>();
				for(int i = 0; i < vars.getVariables().size(); i++) {
					JavaVariable currentVariable = vars.getVariables().get(i);
					if (currentVariable.getKind() == VariableKind.RETURN) {
						counter++;
						if (!signatureString.substring(0, signatureString.indexOf('(')).contains(currentVariable.getName().replace("non-null", "").trim().split(" ")[0])) {
							Console.println("Method return type and variable type does not match.");
							return null;
						}
						if (counter > 1) {
							Console.println("Too much variables of kind RETURN.");
							return null;
						}
						returnVariable = currentVariable;
					} else if	(currentVariable.getKind() == VariableKind.LOCAL) {
						localVariables.add(currentVariable.getName().replace("non-null", ""));
					}
				}
				String globalVariables ="";
				for (Field field : vars.getFields()) {
					globalVariables += ("\t" + field.getVisibility().getName().toLowerCase() + " /*@spec_public@*/ " + field.getType() + " " + field.getName().replace("non-null ", "") + ";\n");
				}
			
				String location = uri.toString().replace("file:", "");//!
				
				if (location.contains("/features/")) 
					location = location.substring(0, location.indexOf("/features/"));
				else 
					location = location.substring(0, location.indexOf("/src/"));
				location += "/code-gen" + File.separator + (formula.getClassName().equals("") ? ("Class" + formula.getName()) : formula.getClassName()) + ".java";
				String code = ConstructCodeBlock.constructCodeBlockForExport(formula, null, null, localVariables, returnVariable, signatureString, new String[0]);
				writeFile(location, code, formula.getMethodObj() != null ? formula.getMethodObj().getParentClass().getPackage() : "", formula.getClassName().equals("") ? ("Class" + formula.getName()) : formula.getClassName(), signatureString, globalVariables);
			}
		}
		return null;
	}
	
	private void writeFile(String location, String code, String packageName, String className,  String signature, String globalVariables) {
		StringBuffer newCode = new StringBuffer();
		newCode.setLength(0);
		File javaFile = new File(location);
		try {
			if (!javaFile.exists()) {
				javaFile.getParentFile().mkdirs();
				javaFile.createNewFile();
			} else {
				newCode = ConstructCodeBlock.editCodeBlockForExport(code, javaFile, signature, globalVariables);
			}
			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			if(newCode.length() == 0) {
				if (packageName != null && !packageName.isEmpty()) {
					bw.write("package " + packageName + "\n\n");
				}
				if(globalVariables.isEmpty())
					bw.write("public class " + className + " {\n\n" + code + "\n}"); 
				else {
					bw.write("public class " + className + " {\n\n" + globalVariables + "\n" + code + "\n}");
				}
			}
			else {
				newCode.append("\n}");
				bw.write(newCode.toString());
			}
			
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(location);
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			System.out.println(ifile.getFullPath().toOSString());
			ifile.refreshLocal(0, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}	
}
