package de.tu_bs.cs.isf.cbc.tool.features;

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
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;

/**
 * feature that lets the user open a associated taxonomy file from to a cbc formula
 */
public class OpenTaxFileFeature extends AbstractDrillDownFeature{
	
	private CbCFormula selectedFormula;
	
	public OpenTaxFileFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
		return "Open associated Taxonomy";
	}
	
	@Override
    public String getDescription() {
        return "Open the associated Taxonomy file";
    }
	
	@Override
    public boolean canExecute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof CbCFormula) {
            	this.selectedFormula = (CbCFormula) bo;
            	return true;
            }
        }
        return false;
    }
	
	@Override
	/**
	 * searches for diagrams only in the same project from the currently selected diagram
	 * note: searching for all diagrams in the project workspace got a really bad performance in bigger workspaces
	 */
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
				//removes all diagram that are not of the right type (right type is "taxonomy"):
				for (int i = 0; i < result.size(); i++) {
					if (!result.get(i).getDiagramTypeId().equals("taxonomy")) {
						result.remove(i);
					}
				}
			}
		}
		return result;
	}
	
	@Override
	//TODO: search through every algorithm in a tax diagram
    protected Collection<Diagram> getLinkedDiagrams(PictogramElement pe) {
		final Collection<Diagram> ret = new HashSet<Diagram>();
		final Collection<Diagram> allDiagrams = getDiagrams();
		final Diagram currentDiagram = getDiagram();
		for (final Diagram d : allDiagrams) {
			//the current diagram should not equal linked diagram:
			if (!EcoreUtil.equals(currentDiagram, d)) {
//				System.out.println("diagram: " +  d);
				for (Shape shape : d.getChildren()) {
					Object obj = getBusinessObjectForPictogramElement(shape);
					if (obj instanceof Algorithm) {
						System.out.println(obj);
						Algorithm algorithm = (Algorithm) obj;
						algorithm.getMethods();
					}
//				final Object[] businessObjectsForDiagram = getAllBusinessObjectsForPictogramElement(d);
//				//getting the bo for the diagram does not work
//				
//				
//				
//				for (Object businessObjectForDiagram : businessObjectsForDiagram) {
//					if (businessObjectForDiagram instanceof Method) {
//						final Method method = (Method) businessObjectForDiagram;
//						System.out.println(method);
//						if (method != null && method.getCorCImpl() != null && method.getCorCImpl().equals(selectedFormula.getTaxMethod())) {
//							ret.add(d);
//						}
//					}
				}
			}
		}
		return ret;
	}

}
