package de.tu_bs.cs.isf.taxonomy.graphiti.features;


import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
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
		System.out.println("Input: " + selectedInput);
		if (selectedInput != null) {
			
			//MAGIC:
			CbcmodelPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("cbcmodel", new XMIResourceFactoryImpl());
			//END MAGIC
			
			//from Resource to CbcFormula
			ResourceSet resourceSet = new ResourceSetImpl();
			System.out.println(URI.createPlatformResourceURI("Userstudy/src/diagrams/BankAccountUpdate.diagram",false));
			Resource selectedResource = resourceSet.getResource(URI.createPlatformResourceURI("Userstudy/src/diagrams/BankAccountUpdate.diagram",true), true); //returns null, needs to be fixed
			System.out.println("Resource: " + selectedResource);
			for (EObject object : selectedResource.getContents()) {
				System.out.println(selectedResource.getContents());
				if (object instanceof CbCFormula) {
					CbCFormula formula = (CbCFormula) object;
					if (selectedMethod.getCorCImpl() != null) {
						//if method already got a tax method set, just link formula
						formula.setTaxMethod(selectedMethod.getCorCImpl());
					} else {
						//if method does not got a method set already, user has to select one using the custom dialog
						String message = "please select a identifier so that the corc diagram and method can be linked";
						String title = "implementing in corc";
						CustomInputDialog implDialog = new CustomInputDialog(new Shell(), message, title);
						dialog.open();
						formula.setTaxMethod(implDialog.getMessage());
						selectedMethod.setCorCImpl(implDialog.getMessage());
					}
				}
			}
		}
	}
}
