package de.kit.tva.lost.models;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.util.GenerateDiagramFromModel;

public class DiagramCreator {
    private Diagram diagram;
    private CbCFormula formula;
    private GlobalConditions conds;
    private Renaming renaming;
    private JavaVariables vars;

    public DiagramCreator() {
    }

    public Diagram getDiagram() {
	return this.diagram;
    }

    public boolean create(Resource diagramRes, CbCFormula formula, GlobalConditions conds, Renaming renaming,
	    JavaVariables vars) throws IOException, CoreException, DiagramResourceModelException {
	this.formula = formula;
	this.conds = conds;
	this.renaming = renaming;
	this.vars = vars;
	GenerateDiagramFromModel generator = new GenerateDiagramFromModel();
	addInitializersTo(diagramRes);
	this.diagram = generator.execute(diagramRes);
	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(1, null);
	return true;
    }

    private void addInitializersTo(final Resource diagramRes) throws IOException, CoreException {
	clearPrevious(diagramRes);
	if (this.conds != null)
	    diagramRes.getContents().add(this.conds);
	if (this.renaming != null)
	    diagramRes.getContents().add(this.renaming);
	if (this.vars != null)
	    diagramRes.getContents().add(this.vars);
	diagramRes.getContents().add(this.formula);
	diagramRes.save(Collections.EMPTY_MAP);
	this.formula.getMethodObj().eResource().save(Collections.EMPTY_MAP);
    }

    private void clearPrevious(Resource diagRes) {
	diagRes.getContents().clear();
    }
}
