package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class UpdateDiagramFeature extends AbstractCustomFeature {

	public UpdateDiagramFeature(IFeatureProvider fp) {
		super(fp);
	}
 
    @Override
    public boolean canExecute(ICustomContext context) {
        return true;
    }
 
    @Override
    public boolean isAvailable(IContext context) {
        return false;
    }

	@Override
	public void execute(ICustomContext context) {
		updateRecursively(getDiagram());
	}
	
	private void updateRecursively(PictogramElement element) {
		updatePictogramElement(element);
		if(element instanceof ContainerShape) {
			for(Shape child : ((ContainerShape) element).getChildren()) {
				updateRecursively(child);
			}
		}
	}
}
