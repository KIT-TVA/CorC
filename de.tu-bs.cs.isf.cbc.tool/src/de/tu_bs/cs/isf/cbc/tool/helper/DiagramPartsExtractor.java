package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.impl.AbstractFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.tool.features.MyAbstractAsynchronousCustomFeature;

public class DiagramPartsExtractor {
	private JavaVariables vars;
	private GlobalConditions conds;
	private CbCFormula formula;
	private Renaming renaming;
	
	public DiagramPartsExtractor(Diagram diag) {
		var businessObjects = getAllBusinessObjects(diag);
		
		for (var obj : businessObjects) {
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof GlobalConditions) {
				conds = (GlobalConditions) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			} else if (obj instanceof Renaming) {
				renaming = (Renaming) obj;
			}
		}	
	}

	public JavaVariables getVars() {
		return vars;
	}

	public GlobalConditions getConds() {
		return conds;
	}

	public CbCFormula getFormula() {
		return formula;
	}

	public Renaming getRenaming() {
		return renaming;
	}
	
	private ArrayList<EObject> getAllBusinessObjects(Diagram diag) {
		var bObjs = new ArrayList<EObject>();
		var contents = diag.eContents();
		for (var o : contents) {
			if (o instanceof PictogramElement) {
				var link = ((PictogramElement) o).getLink();
				if (link == null) continue;
				var objs = link.getBusinessObjects();
				bObjs.addAll(objs);
			}
		}
		return bObjs;
	}
}
