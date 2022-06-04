package de.tu_bs.cs.isf.corc.exampleWizard;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;

import com.google.common.io.Files;

/**
 * wizard that creates examples from the corc tool
 */
public class CorCWizard extends Wizard implements INewWizard {
	
	//page that lets user choose the project path:
	private CorCProjectNamePage namePage = new CorCProjectNamePage();
	private String folderpath;
	private String pluginPath;
	
	@Override
	public void addPages() {
		addPage(namePage); //only one page here
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("CorC example wizard");
		
	}

	@Override
	public boolean performFinish() {
		//creates a new project in the eclipse workspace	
	    //folder paths:
		String name = namePage.name.getText();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	    IProject project = root.getProject(name);
	    folderpath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/" + name + "/src/diagrams";
		Bundle wizardBundle = Platform.getBundle("de.tu-bs.cs.isf.wizards");
	  	Path sourcePath = new Path("examples");
	    URL sourceURL = FileLocator.find(wizardBundle, sourcePath, null);
	    try {
			pluginPath = (FileLocator.toFileURL(sourceURL).getPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		} //contains path where the plugin (exampleWizard) is installed
	    //CREATES PROJECT:
	    IFolder srcFolder = project.getFolder("src");
		IFolder diagramFolder = project.getFolder("src/diagrams");
		try {
	        //creates the folder structure if at least one example is selected
	        if (namePage.maxElement || namePage.logarithm || namePage.binarySearch || namePage.dutchFlag || 
	       			namePage.exponentation || namePage.factorialGraphical || namePage.linearSearch) {
	       		if (!project.exists()) {
					project.create(null);
					project.open(null);
			    }
			    if(!srcFolder.exists()) {
					srcFolder.create(false, true, null);
					//and copy/pastes helper.java:
					//do it at this point, so it can't be override when examples are created again
					String srcFolderPath = srcFolder.getLocation().toString();
					File sourceHelper = new File (pluginPath + "/Helper.java");
			   		File destinationHelper = new File(srcFolderPath + "/Helper.java");	
			   		try {
						Files.copy(sourceHelper, destinationHelper);
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }  
			    if (!diagramFolder.exists()) {
			       	diagramFolder.create(false, true, null);
		        }   
	        }
			if (namePage.linearSearch) { 
				pasteDiagram("LinearSearch", project);   			     	
			}
			/*
			if (namePage.binarySearch) {
				pasteDiagram("BinarySearch", project);
			}
			*/
			if (namePage.dutchFlag) {
				pasteDiagram("DutchFlag", project);
			}
			if (namePage.exponentation) {
				pasteDiagram("Exponentation", project);
			}
			if (namePage.factorialGraphical) {
				pasteDiagram("FactorialGraphical", project);
			}
			if (namePage.logarithm) {
				pasteDiagram("Logarithm", project);
			}
			if (namePage.maxElement) {
				pasteDiagram("maxElement", project);
			}
       		//refreshes the folder so you don't have to press f5 after the examples where added
			if (srcFolder.exists() && diagramFolder.exists()) {
				srcFolder.refreshLocal(1, null);
				diagramFolder.refreshLocal(1, null);
			}
			return true; //everything worked
	    } catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * copy/pastes the given diagram with its helper.key and prove folder
	 * @param diagramName (name from the data)
	 * helper to make the code shorter
	 */
	private void pasteDiagram (String diagramName, IProject project) {
		//copy/paste diagram:
		File sourceDiagram = new File (pluginPath + "/" + diagramName +".diagram");
		File sourceCbcmodel = new File (pluginPath + "/" + diagramName + ".cbcmodel");
		File destinationDiagram = new File(folderpath + "/" + diagramName + ".diagram");
		File destinationCbcmodel = new File(folderpath +"/" + diagramName + ".cbcmodel");
		//copy/paste helper.key:
		String name = namePage.name.getText();
		String keyDataPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/" + name + "/src/diagrams/prove" + diagramName;
   		File sourceHelper = new File (pluginPath + "/" + "helper" + diagramName + ".key");
   		File destinationHelper = new File(keyDataPath + "/" + "helper.key");
		//create prove folder for the diagram:
		IFolder proveFolder = project.getFolder("src/diagrams/prove" + diagramName);
		if (!proveFolder.exists()) {
			try {
				proveFolder.create(false, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		//the actual copy/paste:
		try {
			Files.copy(sourceDiagram, destinationDiagram);
			Files.copy(sourceCbcmodel, destinationCbcmodel);
			Files.copy(sourceHelper, destinationHelper);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//refresh so you don't have to press f5 after new diagrams are added
		try {
			proveFolder.refreshLocal(1, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
	

