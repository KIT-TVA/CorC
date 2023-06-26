package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

public class IdAdder {
	private CbCFormula formula;
	
	public IdAdder(CbCFormula formula) {
		this.formula = formula;
		distributeIds(formula);
		save();
	}
	
	private void save() {
		try {
			formula.eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean distributeIds(EObject root) {
		for (int i = 0; i < root.eContents().size(); i++) {
			EObject content = root.eContents().get(i);
			if (content instanceof AbstractStatement) {
				((AbstractStatement) content).generateID();
			}
			if (content.eContents().size() > 0) {
				if (distributeIds(content)) {
					return true;
				}
			}
		}
		return false;
	}
}
