package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
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
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.FeatureCaller;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;

public class CopyDiagram {
	private String name;
	private URI originalPath;
	private URI path;
	private Resource diagResource;
	private CbCFormula newFormula;
	private Diagram newDiagram;
	
	public CopyDiagram(String newDiagramPath, Diagram diagram, String newName) throws IOException {
		newDiagram = null;
		name = newName;
		originalPath = diagram.eResource().getURI();
		path = URI.createFileURI(newDiagramPath + ".cbcmodel");
		copyDiagram(diagram);
	}
	
	public Resource getResource() {
		return diagResource;
	}
	
	public Diagram getDiagram() {
		return newDiagram;
	}
	
	public void save() {
		GenerateDiagramFromModel gm = new GenerateDiagramFromModel();
		newDiagram = gm.execute(diagResource);
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
		newFormula.setClassName(FeatureCaller.getInstance().getCallingClass(this.originalPath));
		newFormula.setName(formula.getName()); // Set this to value of variable 'name' if mutant name should be used
		if (formula.getMethodObj() != null) {
			newFormula.setMethodName(formula.getMethodObj().getSignature()); // TODO: Find better way of passing the signature to the new method obj created in MutatedClass.
		}
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
	
	/*	
		for (Field f : vars.getFields()) {
			Field newF = CbcclassFactory.eINSTANCE.createField();
			newF.setIsFinal(f.isIsFinal());
			newF.setIsStatic(f.isIsStatic());
			newF.setName(f.getName());
			newF.setType(f.getType());
			newF.setVisibility(f.getVisibility());
			newJv.getFields().add(newF);
		}*/
		//newJv.getFields().addAll(vars.getFields());
/*
		for (Parameter p : vars.getParams()) {
			Parameter newP = CbcclassFactory.eINSTANCE.createParameter();
			newP.setName(p.getName());
			newP.setType(p.getType());
			newJv.getParams().add(newP);
		}*/
		//newJv.getParams().addAll(vars.getParams());
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
	
	private <T> T copyStatement(T statement) {
		if (statement == null) {
			return null;
		}
		T newS;
		if (statement instanceof SkipStatement) {
			newS = (T)CbcmodelFactory.eINSTANCE.createSkipStatement();
		} else if (statement instanceof ReturnStatement) {
			newS = (T)CbcmodelFactory.eINSTANCE.createReturnStatement();
		} else if (statement instanceof MethodStatement) {
			newS = (T)CbcmodelFactory.eINSTANCE.createMethodStatement();
		} else if (statement instanceof StrengthWeakStatement) {
			newS = (T)CbcmodelFactory.eINSTANCE.createStrengthWeakStatement();
		} else if (statement instanceof OriginalStatement) {
			newS = (T)CbcmodelFactory.eINSTANCE.createOriginalStatement();
		} else {
			newS = (T)CbcmodelFactory.eINSTANCE.createAbstractStatement();
		}
		((AbstractStatement)newS).setCodeRepresentation(((AbstractStatement)statement).getCodeRepresentation());
		((AbstractStatement)newS).setComment(((AbstractStatement)statement).getComment());
		((AbstractStatement)newS).setId(((AbstractStatement)statement).getId());
		((AbstractStatement)newS).setName(((AbstractStatement)statement).getName());
		((AbstractStatement)newS).setPostCondition(copyCondition(((AbstractStatement)statement).getPostCondition()));
		((AbstractStatement)newS).setPreCondition(copyCondition(((AbstractStatement)statement).getPreCondition()));
		((AbstractStatement)newS).setProven(((AbstractStatement)statement).isProven());
		((AbstractStatement)newS).setRefinement(copyRefinements(((AbstractStatement)statement).getRefinement()));
		((AbstractStatement)newS).setTested(((AbstractStatement)statement).isTested());
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
				cs.setId(originalCs.getId());
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
				MethodStatement newM = (MethodStatement)copyStatement((MethodStatement)curSource);
				return newM;
			} else if (curSource instanceof ReturnStatement) {
				ReturnStatement newR = (ReturnStatement)copyStatement((ReturnStatement)curSource);
				return newR;
			} else if (curSource instanceof SelectionStatement) {
				SelectionStatement originalS = (SelectionStatement)curSource;
				SelectionStatement newS = CbcmodelFactory.eINSTANCE.createSelectionStatement();
				newS.setCodeRepresentation(originalS.getCodeRepresentation());
				newS.setComment(originalS.getComment());
				newS.setId(originalS.getId());
				newS.setName(originalS.getName());
				newS.setPostCondition(copyCondition(originalS.getPostCondition()));
				newS.setPreCondition(copyCondition(originalS.getPreCondition()));
				newS.setProven(originalS.isProven());
				newS.setRefinement(copyStatement(originalS.getRefinement()));
				newS.setTested(originalS.isTested());
				newS.getGuards().addAll(copyGuards(originalS.getGuards()));
				newS.getCommands().addAll(copyCommands(originalS.getCommands()));
				return newS;
			} else if (curSource instanceof SkipStatement) {
				SkipStatement newS = (SkipStatement)copyStatement((SkipStatement)curSource);
				return newS;
			} else if (curSource instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement originalRs = (SmallRepetitionStatement)curSource;
				SmallRepetitionStatement newRs = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
				newRs.setCodeRepresentation(originalRs.getCodeRepresentation());
				newRs.setComment(originalRs.getComment());
				newRs.setId(originalRs.getId());
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
				StrengthWeakStatement newR = (StrengthWeakStatement)copyStatement((StrengthWeakStatement)curSource);
				return newR;
			} else if (curSource instanceof OriginalStatement) {
				OriginalStatement newR = (OriginalStatement)copyStatement((OriginalStatement)curSource);
				return newR;
			} else if (curSource instanceof AbstractStatement) {
				AbstractStatement newR = (AbstractStatement)copyStatement((AbstractStatement)curSource);
				return newR;
			}
			return null;
	}
	
	private List<Condition> copyGuards(List<Condition> conditions) {
		ArrayList<Condition> newCons = new ArrayList<Condition>();
		conditions.stream().forEach(c -> newCons.add(copyCondition(c)));
		return newCons;
	}
	
	private List<AbstractStatement> copyCommands(List<AbstractStatement> statements) {
		ArrayList<AbstractStatement> newStatements = new ArrayList<AbstractStatement>();
		statements.stream().forEach(s -> newStatements.add(copyStatement(s)));
		return newStatements;
	}
}
