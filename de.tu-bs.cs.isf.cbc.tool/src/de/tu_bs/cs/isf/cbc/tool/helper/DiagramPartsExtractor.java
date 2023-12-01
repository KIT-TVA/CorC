package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
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
	private Diagram diag;
	private ArrayList<EObject> businessObjects;
	private JavaVariables vars = null;
	private GlobalConditions conds = null;
	private CbCFormula formula = null;
	private Renaming renaming = null;
	
	public DiagramPartsExtractor(Diagram diag) {
		if (diag == null) {
			return;
		}
		this.diag = diag;
		businessObjects = getAllBusinessObjects();
		
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

	public void setVars(JavaVariables vars) {
		setBusinessObj(vars);
	}

	public void setConds(GlobalConditions conds) {
		setBusinessObj(conds);
	}

	public void setFormula(CbCFormula formula) {
		setBusinessObj(formula);
	}
	

	public void setRenaming(Renaming renaming) {
		setBusinessObj(renaming);
	}

	private void setBusinessObj(Object obj) {
		var contents = this.diag.eContents();
		for (int i = 0; i < contents.size(); i++) {
			if (diag.eContents().get(i) instanceof PictogramElement) {
				var link = ((PictogramElement)diag.eContents().get(i)).getLink();
				if (link == null) continue;
				EList<EObject> objs = link.getBusinessObjects();
				for (int j = 0; j < objs.size(); j++) {
					if (obj instanceof CbCFormula && objs.get(j) instanceof CbCFormula) {
						objs.set(j, (CbCFormula)obj);
						this.formula = (CbCFormula)obj;
						return;
					} else if (obj instanceof Renaming && objs.get(j) instanceof Renaming) {
						objs.set(j, (Renaming)obj);
						this.renaming = (Renaming)obj;
						return;
					} else if (obj instanceof JavaVariables && objs.get(j) instanceof JavaVariables) {
						objs.set(j, (JavaVariables)obj);
						this.vars = (JavaVariables)obj;
						return;
					} else if (obj instanceof GlobalConditions && objs.get(j) instanceof GlobalConditions) {
						objs.set(j, (GlobalConditions)obj);
						this.conds = (GlobalConditions)obj;
						return;
					}
				}
			}
		}
	}
	
	private ArrayList<EObject> getAllBusinessObjects() {
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
