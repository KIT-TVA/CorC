package de.tu_bs.cs.isf.cbc.mutation.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.OriginalStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Rename;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SkipStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;

public class CopyDiagram {
	private String name;
	private URI path;
	private Resource diagResource;
	private Diagram copy;
	
	public CopyDiagram(String newDiagramPath, Diagram diagram, String newName) {
		name = newName;
		path = URI.createFileURI(newDiagramPath);
		copyDiagram(diagram);
	}
	
	public Diagram get() {
		return this.copy;
	}

	public void save() {
		GenerateDiagramFromModel gm = new GenerateDiagramFromModel();
		gm.execute(diagResource);
		copy = gm.getDiagram();
	}
	
	private void copyDiagram(Diagram diagram) {
		DiagramPartsExtractor dpeOriginal = new DiagramPartsExtractor(diagram);
		copy = EcoreUtil.copy(diagram);
		ResourceSet resourceSet = new ResourceSetImpl();
		diagResource = resourceSet.createResource(path);
		DiagramPartsExtractor dpe = new DiagramPartsExtractor(copy);
		dpe.setFormula(CbcmodelFactory.eINSTANCE.createCbCFormula());
		dpe.getFormula().setName(name);
		dpe.setConds(CbcmodelFactory.eINSTANCE.createGlobalConditions());
		dpe.setRenaming(CbcmodelFactory.eINSTANCE.createRenaming());
		dpe.setVars(CbcmodelFactory.eINSTANCE.createJavaVariables());
		dpe.getFormula().setStatement(copyRefinements(dpeOriginal.getFormula().getStatement()));
		for (Condition c : dpeOriginal.getConds().getConditions()) {
			dpe.getConds().getConditions().add(copyCondition(c));
		}
		if (dpeOriginal.getRenaming() != null) {
			for (Rename r : dpeOriginal.getRenaming().getRename()) {
				Rename newR = CbcmodelFactory.eINSTANCE.createRename();
				newR.setFunction(r.getFunction());
				newR.setNewName(r.getNewName());
				newR.setType(r.getType());
			}
		}
		for (JavaVariable v : dpeOriginal.getVars().getVariables()) {
			JavaVariable newV = CbcmodelFactory.eINSTANCE.createJavaVariable();
			newV.setKind(v.getKind());
			newV.setName(v.getName());
			dpe.getVars().getVariables().add(newV);
		}
		diagResource.getContents().add(dpe.getFormula());
		//diagResource.getContents().add(dpe.getRenaming());
		diagResource.getContents().add(dpe.getConds());
		diagResource.getContents().add(dpe.getVars());
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
