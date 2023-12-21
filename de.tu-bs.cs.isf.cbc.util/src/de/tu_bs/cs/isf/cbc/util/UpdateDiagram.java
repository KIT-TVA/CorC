package de.tu_bs.cs.isf.cbc.util;

import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;


public class UpdateDiagram {
	public static boolean run(Diagram diagram) {
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
		//Console.println("Time needed: " + duration + "ms");
		return true;
	}
}
