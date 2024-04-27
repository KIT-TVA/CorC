package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.kit.tva.lost.interfaces.AbstractModel;
import de.tu_bs.cs.isf.cbc.util.GenerateDiagramFromModel;

/**
 * Translates given LOST-Code into an equivalent CorC Diagram. If the diagram
 * already exists, it's contents are updated.
 */
public class TranslatorModel extends AbstractModel {
    private LOSTTranslator lostTranslator;
    private Diagram diagram;

    public TranslatorModel() {
	lostTranslator = new LOSTTranslator();
    }

    public boolean translate(String lostCode) throws DiagramResourceModelException, IOException, CoreException {
	if (!lostTranslator.translate(lostCode)) {
	    return false;
	}
	var diagramRes = DiagramResourceModel.getInstance().get(lostTranslator.formula.getName());
	if (DiagramResourceModel.getInstance().wasCreated()) {
	    addInitializersTo("test", diagramRes);
	    GenerateDiagramFromModel generator = new GenerateDiagramFromModel();
	    diagram = generator.execute(diagramRes);
	} else {
	    // TODO: Update the existing diagram
	}
	return true;
    }

    public Diagram getDiagram() {
	return this.diagram;
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
