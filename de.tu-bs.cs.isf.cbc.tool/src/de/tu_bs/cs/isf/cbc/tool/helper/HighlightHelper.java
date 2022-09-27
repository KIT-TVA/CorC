package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class HighlightHelper {
	
	public static final HighlightHelper instance = new HighlightHelper();
	
	private HighlightHelper() {}
	
	
	
	private List<CbCDiagramTypeProvider> editors = new LinkedList<>();
	private String elementToHighlightID;
	private int editorsToUpdate;
	private boolean needsInitialHighlight = false;
	
	
	
	public void registerElementToHighlight(String highlightID) {
		elementToHighlightID = highlightID;
		needsInitialHighlight = true;
		editorsToUpdate = editors.size();
		
		for(CbCDiagramTypeProvider editor : editors) {
			editor.triggerHighlight();
		}
	}

	public boolean needsInitialHighlightUpdate(IdUpdateContext context) {
		if(!needsInitialHighlight)
			return false;
		
		return isElementToHighlight(context);
	}

	private boolean isElementToHighlight(IdUpdateContext context) {
		if(elementToHighlightID == null)
			return false;
		
		if(context.getDomainObject() instanceof AbstractStatement) {
			String contextElementID = ((AbstractStatement) context.getDomainObject()).getId();
			if(contextElementID.equals(elementToHighlightID)) {
				return true;
			}
		}
		return false;
	}
	
	public void notifyHighlighted() {
		needsInitialHighlight = false;
	}
	
	

	public boolean registerHighlistListener(CbCDiagramTypeProvider listener) {
		return editors.add(listener);
	}

	public boolean deleteHighlistListener(CbCDiagramTypeProvider listener) {
		return editors.remove(listener);
	}
	
	

	public void handleHighlightDrawing(IdUpdateContext context, RoundedRectangle rectangle) {
		if(isElementToHighlight(context)) {
			Graphiti.getGaService().setRenderingStyle(rectangle, PredefinedColoredAreas.getCopperWhiteGlossAdaptions());
			notifyHighlighted();
		}
		else {
			Graphiti.getGaService().setRenderingStyle(rectangle, PredefinedColoredAreas.getBlueWhiteAdaptions());
		}
	}

	public void highlightUpdateFinished() {
		editorsToUpdate--;
		if(editorsToUpdate == 0)
			elementToHighlightID = null;
	}
	
}
