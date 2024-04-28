package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;

import de.kit.tva.lost.interfaces.AbstractModel;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
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
	var diagramRes = DiagramResourceModel.getInstance().get(lostTranslator.formula.getName());
	if (DiagramResourceModel.getInstance().wasCreated()) {
	    addInitializersTo(lostTranslator.formula.getName(), diagramRes);
	    return true;
	}
	if (!replaceFormula(diagramRes)) {
	    return false;
	}
	generator.execute(diagramRes);
	return true;
    }

    private boolean replaceFormula(Resource diagramResource) throws IOException, CoreException {
	for (int i = 0; i < diagramResource.getContents().size(); ++i) {
	    if (diagramResource.getContents().get(i) instanceof CbCFormula) {
		diagramResource.getContents().remove(i);
		diagramResource.getContents().add(lostTranslator.formula);
		break;
	    }
	    if (i == diagramResource.getContents().size() - 1) {
		return false;
	    }
	}
	diagramResource.save(Collections.EMPTY_MAP);
	return true;
    }

    private void addInitializersTo(String diagName, final Resource diagramRes) throws IOException, CoreException {
	if (lostTranslator.conds != null)
	    diagramRes.getContents().add(lostTranslator.conds);
	if (lostTranslator.renaming != null)
	    diagramRes.getContents().add(lostTranslator.renaming);
	if (lostTranslator.vars != null)
	    diagramRes.getContents().add(lostTranslator.vars);
	lostTranslator.formula.setName(diagName);
	diagramRes.getContents().add(lostTranslator.formula);
	diagramRes.save(Collections.EMPTY_MAP);
	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
    }
}
