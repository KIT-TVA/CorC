package de.tu_bs.cs.isf.cbc.tool.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class UpdateDiagram {
	public static boolean run(Diagram diagram) {
		var featureProviderId = GraphitiUi.getExtensionManager().getDiagramTypeProviderId(diagram.getDiagramTypeId());
		var typeProvider = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram, featureProviderId);
		//typeProvider.getDiagramBehavior().refresh();
		var featureProvider = typeProvider.getFeatureProvider();
		
		
		var peServ = Graphiti.getPeService();
		var pes = peServ.getAllContainedPictogramElements(diagram);
		for (var pe : pes) {
			UpdateContext uContext = new UpdateContext(pe);
			featureProvider.updateIfPossible(uContext);
		}
	/*	
		CbCFormula formula = null;
		JavaVariables vars = null;

		for (Shape shape : diagram.getChildren()) {
			Object obj = featureProvider.getBusinessObjectForPictogramElement(shape);
			if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof JavaVariables) {
				vars = (JavaVariables)obj;
			}
		}	
		if (formula == null) {
			return false;
		}

		UpdateModifiableOfConditions.setVars(vars);
		FileUtil.setApplicationUri(diagram.eResource().getURI());
		updateStatements(featureProvider, diagram, formula.getStatement());*/
		return true;
	}

	/*
	private static void updateStatements(IFeatureProvider featureProvider, Diagram diagram, EObject cur) {
		if (cur instanceof CompositionStatement) {
			var jskdlfjsd = 2;
		}
		for (PictogramElement pe : GraphitiUi.getLinkService().getPictogramElements(diagram, cur)) {
			UpdateContext uContext = new UpdateContext(pe);
			featureProvider.updateIfPossible(uContext);
		}
		
		for (var c : cur.eContents()) {
			updateStatements(featureProvider, diagram, c);
		}
	}*/
}
