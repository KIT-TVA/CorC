package de.tu_bs.cs.isf.taxonomy.graphiti.features;


import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.isf.taxonomy.graphiti.helper.CustomInputDialog;

/**
 * this feature lets you link a corc diagram to a method, so the drilldown feature can open it
 * @author Domenik Eichhorn
 */
public class LinkCorCDiagramFeature extends AbstractCustomFeature {
	
	private Method selectedMethod;
	
	public LinkCorCDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Link method to CorC diagram";
	}
	
	@Override
	public String getDescription() {
		return "links this method to a corc diagram, so its associated for the drilldown feature";
	}
	
	@Override
    public boolean canExecute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof Method) {
            	this.selectedMethod = (Method) bo;
                return true;
            }
        }
        return false;
    }
	
	/**
	 * lets user select a corc .diagram file that he wants to link with the method
	 * links the formula from the .diagram file with the method
	 */
	@Override
	public void execute(ICustomContext context) {

		
		FileDialog dialog = new FileDialog(new Shell(), SWT.OPEN);
		dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString());
		dialog.setFilterExtensions(new String[] {"*.diagram"});
		
		String selectedInput = dialog.open();
		if (selectedInput != null) {
			//transform InputString, so that createPlatformResourceURI can use it
			selectedInput = selectedInput.replaceAll("\\\\", "/"); //replaces all \ with / in the path
			selectedInput = selectedInput.replaceAll(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/", "");
			String selectedInputModel = selectedInput.replace(".diagram", ".cbcmodel");
		
			
			//from the Resource, get the CbcFormula
			ResourceSet resourceSet = new ResourceSetImpl();
			URI modelUri = URI.createPlatformResourceURI(selectedInputModel, true);
			URI diagramUri = URI.createPlatformResourceURI(selectedInput, true);
					
			Resource modelResource = resourceSet.getResource(modelUri, true);
			Resource diagramResource = resourceSet.getResource(diagramUri, true);
			
			for (EObject object : diagramResource.getContents()) {
				if (object instanceof Diagram) {
					Diagram diagram = (Diagram) object;
					for (Shape shape : diagram.getChildren()) {
						Object obj = getBusinessObjectForPictogramElement(shape);
						if (obj instanceof CbCFormula) {
							CbCFormula formula = (CbCFormula) obj;
							//Diagram diagram = (Diagram) object;
							if (selectedMethod.getCorCImpl() != null) {
								//if method already got a tax method set, just link formula
								formula.setTaxMethod(selectedMethod.getCorCImpl());
							} else {
								//if method does not got a method set already, user has to select one using the custom dialog
								String message = "please select a identifier so that the corc diagram and method can be linked";
								String title = "linking to corc";
								CustomInputDialog implDialog = new CustomInputDialog(new Shell(), message, title);
								implDialog.open();
								formula.setTaxMethod(implDialog.getInput());
								selectedMethod.setCorCImpl(implDialog.getInput());
							}
							
							try {
								modelResource.save(Collections.emptyMap());
								diagramResource.save(Collections.emptyMap());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
		}
	}
}
