package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.corc.newFileWizard.NewCorCFileWizard;
import de.tu_bs.cs.isf.corc.newFileWizard.chooseDiagramTypePage;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Feature that lets you implement a new corc diagram to a selected method
 * @author Domenik Eichhorn
 */
public class ImplementMethodinCorcFeature extends AbstractCustomFeature {
	 
	private Method selectedMethod;
    public ImplementMethodinCorcFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Implement Method in CorC";
    }
 
    @Override
    public String getDescription() {
        return "this will create a CorC diagram to the selected method";
    }
    
    /**
     * can execute when a method (in taxonomy editor) is selected
     */
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
     * opens the wizard for a new corc diagram
     * when corc diagram type is selected in the wizard, it adds a new formula (with the information from the selcted method)
     */
    @Override
    public void execute(ICustomContext context) {
    	Shell shell = new Shell(Display.getCurrent());
		NewCorCFileWizard wizard = new NewCorCFileWizard();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		chooseDiagramTypePage page = (chooseDiagramTypePage) wizard.getFirstPage();
		
		dialog.create();
		
		//set name and path in the dialog:
		page.name.setText(selectedMethod.getName());
		String path = selectedMethod.eResource().getURI().trimSegments(1).toString();
		path = path.replace("platform:/resource", "");
		path = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + path;
		page.path.setText(path);
		page.error.setText("");
		page.setPageComplete(true);
		dialog.open();
		
		Diagram diagram = wizard.getCreatedDiagram();
		Resource modelResource = wizard.getCreatedModelResource();
		Resource diagramResource = wizard.getCreatedDiagramResource();
		if (diagram != null && modelResource != null) {
			CbCFormula formula = createFormula(diagram, selectedMethod); //create formula
			modelResource.getContents().add(formula); //add formula to resource
			
			AddContext addContext = new AddContext();
			addContext.setLocation(550, 150);
			addContext.setTargetContainer(diagram);
			addContext.setNewObject(formula);
	        
	        IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
					"de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
			IFeatureProvider featureProvider = dtp.getFeatureProvider();
	        IAddFeature addFeature = featureProvider.getAddFeature(addContext);
			if (addFeature.canAdd(addContext)) {
				addFeature.add(addContext);
			}
	        
			try {
				modelResource.save(Collections.emptyMap());
				diagramResource.save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
    }
    
    /**
	 * creates the formula in the new diagram
	 */
	private CbCFormula createFormula(Diagram diagram, Method method) {
		//creates formula and sets names etc.
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		formula.setName(diagram.getName());
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		formula.setStatement(statement);
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName(method.getPreCondition());
		statement.setPreCondition(preCondition);
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName(method.getPostCondition());
		statement.setPostCondition(postCondition);
		return formula;
		
	}
	
}