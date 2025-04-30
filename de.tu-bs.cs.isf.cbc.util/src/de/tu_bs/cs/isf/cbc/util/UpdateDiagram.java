package de.tu_bs.cs.isf.cbc.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;

public class UpdateDiagram {
	private static UpdateDiagram instance;

	public static UpdateDiagram getInstance() {
		if (instance == null) {
			instance = new UpdateDiagram();
		}
		return instance;
	}

	public boolean run(Diagram diagram) {
		Console.println("Updating Diagram '" + diagram.getName() + "'...");
		long startTime = System.nanoTime();
		var featureProviderId = GraphitiUi.getExtensionManager().getDiagramTypeProviderId(diagram.getDiagramTypeId());
		var typeProvider = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram, featureProviderId);
		var featureProvider = typeProvider.getFeatureProvider();
		var peServ = Graphiti.getPeService();
		var pes = peServ.getAllContainedPictogramElements(diagram);
		for (var pe : pes) {
			UpdateContext uContext = new UpdateContext(pe);
			featureProvider.updateIfPossible(uContext);
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("Updating done.");
		// Console.println("Time needed: " + duration + "ms");
		return true;
	}

	public IFeatureProvider getFeatureProvider(Diagram diagram) {
		IDiagramTypeProvider diagramTypeProvider = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
				"de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
		if (diagramTypeProvider != null) {
			return diagramTypeProvider.getFeatureProvider();
		}
		return null;
	}

	public void updateDiagram(Diagram diagram) throws IOException {
		UpdateContext updateContext = new UpdateContext(
				diagram.getChildren().get(0).getGraphicsAlgorithm().getPictogramElement());
		IUpdateFeature updateFeature = getFeatureProvider(diagram).getUpdateFeature(updateContext);
		updateFeature.update(updateContext);
		UpdateDiagram.getInstance().run(diagram);
		diagram.eResource().save(Collections.EMPTY_MAP);
	}

}
