package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.isf.taxonomy.graphiti.helper.GetDiagramUtil;

/**
 * Drilldown Feature that opens associated corc implementations for a method in tax
 * @author Domenik Eichhorn
 */
public class DrillDownFeature extends AbstractDrillDownFeature {
	
	private Method selectedMethod;
	
	public DrillDownFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Open associated diagram to this method";
	}
	
	@Override
    public String getDescription() {
        return "Open the associated CorC file";
    }
	
	@Override
    public boolean canExecute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof Method) {
            	this.selectedMethod = (Method) bo;
            	return super.canExecute(context);
            }
        }
        return false;
    }
	
	/**
	 * searches for diagrams only in the same project from the currently selected diagram
	 * note: searching for all diagrams in the project workspace got a really bad performance in bigger workspaces
	 */
	@Override
	protected Collection<Diagram> getDiagrams() {
		List<Diagram> result = new ArrayList<Diagram>();
		//gets the current project of the diagram:
		URI projectURI = getDiagram().eResource().getURI().trimFragment();
		if (projectURI.isPlatformResource()) {
			String platformString = projectURI.toPlatformString(true);
			IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (resource != null) {
				IProject currentProject = resource.getProject();
				//gets all diagrams in the current project
				result.addAll(GetDiagramUtil.getDiagrams(currentProject));
				//removes all diagram that are not of the right type: (right type is "cbc")
				for (int i = 0; i < result.size(); i++) {
					if (!result.get(i).getDiagramTypeId().equals("cbc")) {
						result.remove(i);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * overrides getLinkedDiagrams from AbstractDrilldownfeature
	 * searches in workspace for formulas that are linked to the taxmethod in tax (compares names)
	 */
	@Override
    protected Collection<Diagram> getLinkedDiagrams(PictogramElement pe) {
		final Collection<Diagram> ret = new HashSet<Diagram>();
		final Collection<Diagram> allDiagrams = getDiagrams();
		for (final Diagram d : allDiagrams) {
			final Diagram currentDiagram = getDiagram();
			if (!EcoreUtil.equals(currentDiagram, d)) {
				final Object[] businessObjectsForDiagram = getAllBusinessObjectsForPictogramElement(d);
				for (Object businessObjectForDiagram : businessObjectsForDiagram) {
					if (businessObjectForDiagram instanceof CbCFormula) {
						final CbCFormula formula = (CbCFormula) businessObjectForDiagram;
						//checks if TaxMethod from formula is associated with CorCImpl from Method
						if (formula != null && formula.getTaxMethod() != null && formula.getTaxMethod().equals(selectedMethod.getCorCImpl())) {
							ret.add(d);
						}
					}
				}
			}
		}
		return ret;
	}
}
