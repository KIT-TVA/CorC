package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;

public class DrillUpFeature extends AbstractDrillDownFeature {
	 
    public DrillUpFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Open associated diagram";
    }
 
    @Override
    public String getDescription() {
        return "Open the diagram associated with this Formula";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        // first check, if one EClass is selected
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo instanceof CbCFormula) {
                // then forward to super-implementation, which checks if
                // this EClass is associated with other diagrams
                return super.canExecute(context);
            }
        }
        return false;
    }
 
    @Override
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
    
    protected Collection<Diagram> getLinkedDiagrams(PictogramElement pe) {
		final Collection<Diagram> ret = new HashSet<Diagram>();

		final Object businessObjectsForPictogramElement = getBusinessObjectForPictogramElement(pe);
		final CbCFormula formula = (CbCFormula) businessObjectsForPictogramElement;
		final Collection<Diagram> allDiagrams = getDiagrams();
		for (final Diagram d : allDiagrams) {
			final Diagram currentDiagram = getDiagram();
			if (!EcoreUtil.equals(currentDiagram, d)) { // always filter out the
														// current
				// diagram
				final Object[] businessObjectsForDiagram = getAllBusinessObjectsForPictogramElement(d);
				for (Object businessObjectForDiagram : businessObjectsForDiagram) {
					if (businessObjectForDiagram instanceof CbCFormula) {
						final CbCFormula formula2 = (CbCFormula) businessObjectForDiagram;
						if (formula2 != null) {
							TreeIterator<EObject> iterator = formula2.eAllContents();
							while (iterator.hasNext()) {
								EObject object = iterator.next();
								if (object instanceof MethodStatement) {
									MethodStatement statement = (MethodStatement) object;
									if (formula.getName().equals(statement.getName())) {
										ret.add(d);
									}
								}
							}
						}
					}
				}
			}
		}

		return ret;
	}
}
