package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;

import de.kit.tva.lost.interfaces.AbstractModel;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.util.GenerateDiagramFromModel;

/**
 * Translates given LOST-Code into an equivalent CorC Diagram. If the diagram
 * already exists, it's contents are updated.
 */
public class TranslatorModel extends AbstractModel {
    private LostTranslator lostTranslator;

    public TranslatorModel() {
	lostTranslator = new LostTranslator();
    }

    public boolean translate(String lostCode) throws DiagramResourceModelException, IOException, CoreException {
	if (!lostTranslator.translate(lostCode)) {
	    return false;
	}
	GenerateDiagramFromModel generator = new GenerateDiagramFromModel();
	var diagramRes = DiagramResourceModel.getInstance().get(lostTranslator.getFormula().getName());
        addInitializersTo(lostTranslator.getFormula().getName(), diagramRes);
        generator.execute(diagramRes);
	return true;
    }

    private void addInitializersTo(String diagName, final Resource diagramRes) throws IOException, CoreException {
	clearPrevious(diagramRes);
	if (lostTranslator.getGlobalConditions() != null)
	    diagramRes.getContents().add(lostTranslator.getGlobalConditions());
	if (lostTranslator.getRenaming() != null)
	    diagramRes.getContents().add(lostTranslator.getRenaming());
	if (lostTranslator.getVariables() != null)
	    diagramRes.getContents().add(lostTranslator.getVariables());
	lostTranslator.getFormula().setName(diagName);
	diagramRes.getContents().add(lostTranslator.getFormula());
	diagramRes.save(Collections.EMPTY_MAP);
	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    }
    
    private void clearPrevious(Resource diagRes) {
	for (int i = 0; i < diagRes.getContents().size(); i++) {
	    if (diagRes.getContents().get(i) instanceof CbCFormula 
		    || diagRes.getContents().get(i) instanceof JavaVariables 
		    || diagRes.getContents().get(i) instanceof GlobalConditions 
		    || diagRes.getContents().get(i) instanceof Renaming) {
		diagRes.getContents().remove(i);
	    }
	}
    }
}
