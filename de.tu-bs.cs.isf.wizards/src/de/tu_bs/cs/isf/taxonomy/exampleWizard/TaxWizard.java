package de.tu_bs.cs.isf.taxonomy.exampleWizard;

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
 * wizard that creates examples from the taxonomy tool
 */
public class TaxWizard extends Wizard implements INewWizard {
	
	//page that lets user choose the project path:
	private TaxProjectNamePage namePage = new TaxProjectNamePage();
	private String folderpath;
	private String pluginPath;
	
	@Override
	public void addPages() {
		addPage(namePage);
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("TAXONOMY tool");
		
	}

	@Override
	public boolean performFinish() {
		if (namePage.name != null) {
			//creates a new project in the eclipse workspace
			String name = namePage.name.getText();
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	        IProject project = root.getProject(name);
	        IFolder srcFolder = project.getFolder("src");
	        IFolder diagramFolder = project.getFolder("src/diagrams");
	        //CREATES PROJECT:
	        try {
	        	//if at least one example is selected, folder structure is created
	        	if (namePage.DFAMin || namePage.madfa || namePage.sampling || namePage.twoDMadfa) {
			        if (!project.exists()) {
							project.create(null);
							project.open(null);
			        }   
			        if(!srcFolder.exists()) {
						srcFolder.create(false, true, null);
			        }			        
			        if (!diagramFolder.exists()) {
			        	diagramFolder.create(false, true, null);
			        }
	        	}
		        //the folder on the workspace where the examples should be pasted:
	        	folderpath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/" + name + "/src/diagrams";
		        //builds the path where the plugin is installed to get the defined examples from the plugin
	        	Bundle wizardBundle = Platform.getBundle("de.tu-bs.cs.isf.wizards");
	        	Path sourcePath = new Path("/examples");
	        	URL sourceURL = FileLocator.find(wizardBundle, sourcePath, null);
	        	try {
					pluginPath = (FileLocator.toFileURL(sourceURL).getPath()); //contains path where the plugin (exampleWizard) is installed
					System.out.println(pluginPath);
		        	if (namePage.madfa) {   
		    			pasteDiagram("MadfaTax");    			     	
		        	}
		        	if (namePage.DFAMin) {   
		    			pasteDiagram("DFAMin");    			     	
		        	}
		        	if (namePage.sampling) {   
		    			pasteDiagram("SamplingTax");    			     	
		        	}
		        	if (namePage.twoDMadfa) {   
		    			pasteDiagram("2dMadfa");    			     	
		        	}
	        	} catch (IOException e1) {
					e1.printStackTrace();
				}
        		//refreshes the folder so you dont have to press f5 after the examples where added
	        	if (srcFolder.exists() && diagramFolder.exists()) {
		        	srcFolder.refreshLocal(1, null);
					diagramFolder.refreshLocal(1, null);
	        	}
	        } catch (CoreException e) {
				e.printStackTrace();
			}
        	return true; //everything worked
		}
		return false;
	}
	private void pasteDiagram (String diagramName) {
		File sourceDiagram = new File (pluginPath + "/" + diagramName +".diagram");
		File sourceCbcmodel = new File (pluginPath + "/" + diagramName + ".taxonomy");
		File destinationDiagram = new File(folderpath + "/" + diagramName + ".diagram");
		File destinationCbcmodel = new File(folderpath +"/" + diagramName + ".taxonomy");
		try {
			Files.copy(sourceDiagram, destinationDiagram);
			Files.copy(sourceCbcmodel, destinationCbcmodel);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
