package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.mutation.feature.Mutator;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class CopyDiagram {
	private String name;
	private URI originalPath;
	private URI path;
	private Resource diagResource;
	private CbCFormula newFormula;
	//private JavaVariables vars;
	
	public CopyDiagram(String newDiagramPath, Diagram diagram, String newName) throws IOException {
		name = newName;
		originalPath = diagram.eResource().getURI();
		path = URI.createFileURI(newDiagramPath + ".cbcmodel");
		copyDiagram(diagram);
	}
	
	public Resource getResource() {
		return diagResource;
	}
	
	/*
	public JavaVariables getVars() {
		return this.vars;
	}*/

	public void save() {
		GenerateDiagramFromModel gm = new GenerateDiagramFromModel();
		gm.execute(diagResource);
	}
	
	private void copyDiagram(Diagram diagram) throws IOException {
		DiagramPartsExtractor dpeOriginal = new DiagramPartsExtractor(diagram);
		ResourceSet newRs = new ResourceSetImpl();
		diagResource = newRs.createResource(this.path);
		copyFormula(dpeOriginal.getFormula());
		copyConditions(dpeOriginal.getConds());
		copyVars(dpeOriginal.getVars());
		copyRenaming(dpeOriginal.getRenaming());
		diagResource.save(Collections.EMPTY_MAP);
	}
	
	private void copyFormula(CbCFormula formula) {
		newFormula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		newFormula.setClassName(this.originalPath.segment(this.originalPath.segmentCount()-2));
		newFormula.setName(name);
		newFormula.setStatement(copyRefinements(formula.getStatement()));
		diagResource.getContents().add(newFormula);
	}

	private void copyConditions(GlobalConditions conditions) {
		if (conditions == null) {
			return;
		}
		GlobalConditions newGc = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		for (Condition c : conditions.getConditions()) {
			newGc.getConditions().add(copyCondition(c));
		}
		diagResource.getContents().add(newGc);
	}
	
	private void copyVars(JavaVariables vars) {
		if (vars == null) {
			return;
		}
		JavaVariables newJv = CbcmodelFactory.eINSTANCE.createJavaVariables();
		for (JavaVariable v : vars.getVariables()) {
			JavaVariable newV = CbcmodelFactory.eINSTANCE.createJavaVariable();
			newV.setKind(v.getKind());
			newV.setName(v.getName());
			newJv.getVariables().add(newV);
		}
		newJv.getFields().addAll(vars.getFields());
		newJv.getParams().addAll(vars.getParams());
		diagResource.getContents().add(newJv);
	}
	
	private void copyRenaming(Renaming renaming) {
		if (renaming == null) {
			return;
		}
		Renaming newRn = CbcmodelFactory.eINSTANCE.createRenaming();
		for (Rename r : renaming.getRename()) {
			Rename newR = CbcmodelFactory.eINSTANCE.createRename();
			newR.setFunction(r.getFunction());
			newR.setNewName(r.getNewName());
			newR.setType(r.getType());
		}
		diagResource.getContents().add(newRn);
	}
	
	private AbstractStatement copyStatement(AbstractStatement statement) {
		if (statement == null) {
			return null;
		}
		AbstractStatement newS = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		newS.setCodeRepresentation(statement.getCodeRepresentation());
		newS.setComment(statement.getComment());
		newS.generateID();
		newS.setName(statement.getName());
		newS.setPostCondition(copyCondition(statement.getPostCondition()));
		newS.setPreCondition(copyCondition(statement.getPreCondition()));
		newS.setProven(statement.isProven());
		newS.setRefinement(copyRefinements(statement.getRefinement()));
		newS.setTested(statement.isTested());
		return newS;
	}
	
	private Condition copyCondition(Condition condition) {
		if (condition == null) {
			return null;
		}
		Condition newC = CbcmodelFactory.eINSTANCE.createCondition();
		newC.setCodeRepresentation(condition.getCodeRepresentation());
		newC.setName(condition.getName());
		return newC;
	}
	
	private Variant copyVariant(Variant variant) {
		Variant newV = CbcmodelFactory.eINSTANCE.createVariant();
		newV.setName(variant.getName());
		return newV;
	}
	
	private AbstractStatement copyRefinements(EObject curSource) {
			if (curSource == null) {
				return null;
			}
			if (curSource instanceof CompositionStatement) {
				CompositionStatement originalCs = (CompositionStatement)curSource;
				CompositionStatement cs = CbcmodelFactory.eINSTANCE.createCompositionStatement();
				cs.setCodeRepresentation(originalCs.getCodeRepresentation());
				cs.setComment(originalCs.getComment());
				cs.setFirstStatement(copyStatement(originalCs.getFirstStatement()));
				cs.generateID();
				cs.setIntermediateCondition(copyCondition(originalCs.getIntermediateCondition()));
				cs.setName(originalCs.getName());
				cs.setPostCondition(copyCondition(originalCs.getPostCondition()));
				cs.setPreCondition(copyCondition(originalCs.getPreCondition()));
				cs.setProven(originalCs.isProven());
				cs.setTested(originalCs.isTested());
				cs.setRefinement(copyStatement(originalCs.getRefinement()));
				cs.setSecondStatement(copyStatement(originalCs.getSecondStatement()));
				return cs;
			} else if (curSource instanceof MethodStatement) {
				MethodStatement newM = (MethodStatement)copyStatement((AbstractStatement)curSource);
				return newM;
			} else if (curSource instanceof ReturnStatement) {
				ReturnStatement newR = (ReturnStatement)copyStatement((AbstractStatement)curSource);
				return newR;
			} else if (curSource instanceof SelectionStatement) {
				SelectionStatement newS = (SelectionStatement)copyStatement((AbstractStatement)curSource);
				return newS;
			} else if (curSource instanceof SkipStatement) {
				SkipStatement newS = (SkipStatement)copyStatement((AbstractStatement)curSource);
				return newS;
			} else if (curSource instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement originalRs = (SmallRepetitionStatement)curSource;
				SmallRepetitionStatement newRs = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
				newRs.setCodeRepresentation(originalRs.getCodeRepresentation());
				newRs.setComment(originalRs.getComment());
				newRs.generateID();
				newRs.setName(originalRs.getName());
				newRs.setPostCondition(copyCondition(originalRs.getPostCondition()));
				newRs.setPreCondition(copyCondition(originalRs.getPreCondition()));
				newRs.setProven(originalRs.isProven());
				newRs.setRefinement(copyStatement(originalRs.getRefinement()));
				newRs.setTested(originalRs.isTested());
				newRs.setGuard(copyCondition(originalRs.getGuard()));
				newRs.setInvariant(copyCondition(originalRs.getInvariant()));
				newRs.setLoopStatement(copyStatement(originalRs.getLoopStatement()));
				newRs.setPostProven(originalRs.isPostProven());
				newRs.setPreProven(originalRs.isPreProven());
				newRs.setVariant(copyVariant(originalRs.getVariant()));
				newRs.setVariantProven(originalRs.isVariantProven());
				return newRs;
			} else if (curSource instanceof StrengthWeakStatement) {
				StrengthWeakStatement newR = (StrengthWeakStatement)copyStatement((AbstractStatement)curSource);
				return newR;
			} else if (curSource instanceof OriginalStatement) {
				OriginalStatement newR = (OriginalStatement)copyStatement((AbstractStatement)curSource);
				return newR;
			} else if (curSource instanceof AbstractStatement) {
				AbstractStatement newR = (AbstractStatement)copyStatement((AbstractStatement)curSource);
				return newR;
			}
			return null;
	}
}
