package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

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
	protected Collection<Diagram> getDiagrams() {
		List<Diagram> result = new ArrayList<Diagram>();
		//gets all projects in workspace
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		//gets diagrams for every project
		for (IProject p : projects) {
			List<Diagram> diagrams = (List<Diagram>) GetDiagramUtil.getDiagrams(p);
			for (int i = 0; i < diagrams.size(); i++) {
				result.add(diagrams.get(i));
			}
		}
		return result;
	}
	
	@Override
    protected Collection<Diagram> getLinkedDiagrams(PictogramElement pe) {
		final Collection<Diagram> ret = new HashSet<Diagram>();
		final Collection<Diagram> allDiagrams = getDiagrams();
		for (final Diagram d : allDiagrams) {
			final Diagram currentDiagram = getDiagram();
			if (!EcoreUtil.equals(currentDiagram, d)) {
				final Object[] businessObjectsForDiagram = getAllBusinessObjectsForPictogramElement(d);
				for (Object businessObjectForDiagram : businessObjectsForDiagram) {
					if (businessObjectForDiagram instanceof Method) {
						final Method method = (Method) businessObjectForDiagram;
						if (method != null && method.getCorCImpl() != null && method.getCorCImpl().equals(selectedFormula.getTaxMethod())) {
							ret.add(d);
						}
					}
				}
			}
		}
		return ret;
	}

}
