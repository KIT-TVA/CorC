package de.kit.tva.lost.models.lost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.kit.tva.lost.interfaces.ModifiablesConverter;
import de.kit.tva.lost.models.classes.ClassCreator;
import de.kit.tva.lost.models.diagrams.DiagramCreator;
import de.kit.tva.lost.models.diagrams.DiagramResourceModel;
import de.kit.tva.lost.models.diagrams.DiagramResourceModelException;
import de.kit.tva.lost.models.modifiablesconverters.CompositionModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.FormulaModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.RepetitionModifiablesConverter;
import de.kit.tva.lost.models.modifiablesconverters.SelectionModifiablesConverter;
import de.kit.tva.lost.models.parser.LostParser.CompositionContext;
import de.kit.tva.lost.models.parser.LostParser.ConditionContext;
import de.kit.tva.lost.models.parser.LostParser.DiagramContext;
import de.kit.tva.lost.models.parser.LostParser.DiagramParamContext;
import de.kit.tva.lost.models.parser.LostParser.FormulaContext;
import de.kit.tva.lost.models.parser.LostParser.GlobalConditionsContext;
import de.kit.tva.lost.models.parser.LostParser.GuardContext;
import de.kit.tva.lost.models.parser.LostParser.InitializerContext;
import de.kit.tva.lost.models.parser.LostParser.MethodCallSContext;
import de.kit.tva.lost.models.parser.LostParser.MlexprContext;
import de.kit.tva.lost.models.parser.LostParser.OriginalSContext;
import de.kit.tva.lost.models.parser.LostParser.ProgramContext;
import de.kit.tva.lost.models.parser.LostParser.RenamerContext;
import de.kit.tva.lost.models.parser.LostParser.RenamingContext;
import de.kit.tva.lost.models.parser.LostParser.RepetitionContext;
import de.kit.tva.lost.models.parser.LostParser.ReturnSContext;
import de.kit.tva.lost.models.parser.LostParser.SelectionContext;
import de.kit.tva.lost.models.parser.LostParser.SkipSContext;
import de.kit.tva.lost.models.parser.LostParser.StatementContext;
import de.kit.tva.lost.models.parser.LostParser.VariableContext;
import de.kit.tva.lost.models.parser.LostParser.VarsContext;
import de.kit.tva.lost.models.util.ModelLinker;
import de.kit.tva.lost.models.util.SignatureConstructor;
import de.tu_bs.cs.isf.cbc.cbcclass.CbcclassFactory;
import de.tu_bs.cs.isf.cbc.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.Parameter;
import de.tu_bs.cs.isf.cbc.cbcclass.Visibility;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.exceptions.NotImplementedException;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateModifiableOfConditions;
import de.tu_bs.cs.isf.cbc.util.FeatureUtil;
import de.tu_bs.cs.isf.cbc.util.UpdateConditionsOfChildren;

/**
 * Translates a given LOST-Code into an equivalent CorC diagram model
 * {@link de.tu-bs.cs.isf.cbc.cbcclass.ModelClass}.
 */
public class LostTranslator {
	private Diagram diagram;
	private JavaVariables vars;
	private GlobalConditions conds;
	private Renaming renaming;
	private CbCFormula formula;
	private ParseTreeGenerator parseTreeGenerator;
	private ModelLinker modelLinker;
	private ProgramContext tree;
	private DiagramCreator diagramCreator;
	private List<Field> newFields;
	private List<Parameter> newParams;
	private String methodModifiables;

	public LostTranslator() {
		this.formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		this.parseTreeGenerator = new ParseTreeGenerator();
		this.modelLinker = new ModelLinker();
		this.diagramCreator = new DiagramCreator();
		this.newFields = new ArrayList<Field>();
		this.newParams = new ArrayList<Parameter>();
	}

	public boolean translate(String lostCode, boolean genDiagram) throws IOException, CoreException,
			DiagramResourceModelException, SettingsException, NotImplementedException {
		if (!this.parseTreeGenerator.generateParseTree(lostCode)) {
			return false;
		}
		reset();
		this.tree = this.parseTreeGenerator.get();
		formula.setName(extractName(tree.root().diagram().diagramParam()));
		for (int i = 0; i < tree.root().diagram().getChildCount(); ++i) {
			addInitializers(tree.root().diagram().initializer(i));
		}
		if (!genDiagram)
			return true;
		var diagramRes = DiagramResourceModel.getInstance().get(this.formula.getName());
		if (diagramRes != null && !FeatureUtil.getInstance().getCallingClass(diagramRes.getURI()).isEmpty()) {
			createClass(diagramRes.getURI(), tree.root().diagram());
		}
		this.diagramCreator.create(diagramRes, this.formula, this.conds, this.renaming, this.vars);
		this.diagram = this.diagramCreator.getDiagram();
		return true;
	}

	public boolean translate(String lostCode) throws DiagramResourceModelException, IOException, CoreException,
			SettingsException, NotImplementedException {
		return translate(lostCode, true);
	}

	public Diagram getDiagram() {
		return this.diagram;
	}

	public ModelLinker getModelLinker() {
		return this.modelLinker;
	}

	public CbCFormula getFormula() {
		return this.formula;
	}

	public GlobalConditions getGlobalConditions() {
		return this.conds;
	}

	public Renaming getRenaming() {
		return this.renaming;
	}

	public JavaVariables getVariables() {
		return this.vars;
	}

	private void reset() {
		this.modelLinker.reset();
		this.newFields.clear();
		this.newParams.clear();
	}

	private String extractName(DiagramParamContext paramCtx) {
		if (paramCtx.name() != null) {
			return paramCtx.name().ID().getText();
		}
		return paramCtx.signature().ID().getText();
	}

	private void createClass(URI diagramUri, DiagramContext diagramCtx) throws IOException, SettingsException {
		var diagPath = diagramUri.trimFileExtension();
		var name = diagPath.segment(diagPath.segmentCount() - 1);
		ClassCreator classCreator = new ClassCreator(diagramUri);
		classCreator.setFields(newFields);
		classCreator.setParams(classCreator.getMethod(name), newParams);
		this.vars.getFields().addAll(classCreator.getFields());
		var method = classCreator.getMethod(name);
		method.setCbcStartTriple(this.formula);
		if (diagramCtx.diagramParam().signature() != null) {
			method.setSignature(SignatureConstructor.getInstance().run(diagramCtx.diagramParam().signature()));
		}
		var params = copyParams(method);
		this.vars.getParams().addAll(params);
		this.formula.setMethodObj(classCreator.getMethod(name));
		this.formula.setClassName(classCreator.get().getName());
	}

	private List<Parameter> copyParams(Method m) {
		/*
		 * var copyParams = new ArrayList<Parameter>(); for (var param :
		 * m.getParameters()) { copyParams.add(EcoreUtil.copy(param)); } return
		 * copyParams;
		 */
		return m.getParameters();
	}

	private void addInitializers(InitializerContext partTree) throws NotImplementedException {
		if (partTree == null) {
			return;
		}
		if (partTree.vars() != null) {
			addVariables(partTree.vars());
		} else if (partTree.globalConditions() != null) {
			addGlobalConds(partTree.globalConditions());
		} else if (partTree.renaming() != null) {
			addRenamers(partTree.renaming());
		} else if (partTree.formula() != null) {
			addRefinements(partTree.formula());
		}
	}

	private void addVariables(VarsContext varsTree) {
		vars = CbcmodelFactory.eINSTANCE.createJavaVariables();
		for (int i = 0; i < varsTree.getRuleContexts(VariableContext.class).size(); i++) {
			var treeVariable = varsTree.variable(i);
			String arrayBrackets = treeVariable.BRACKETS() != null ? treeVariable.BRACKETS().getText() : "";
			var kind = treeVariable.KIND().getText();
			if (kind.equals("PARAM")) {
				// Since params and fields are not handled in diagram but in their class,
				// they need to be added to the class.
				var param = CbcclassFactory.eINSTANCE.createParameter();
				param.setType(treeVariable.TYPE().getText() + arrayBrackets);
				param.setName(treeVariable.ID().getText());
				newParams.add(param);
			} else if (kind.equals("PUBLIC")) {
				var field = CbcclassFactory.eINSTANCE.createField();
				field.setIsFinal(false);
				field.setIsStatic(false);
				field.setVisibility(Visibility.PUBLIC);
				field.setType(treeVariable.TYPE().getText() + arrayBrackets);
				field.setName(treeVariable.ID().getText());
				newFields.add(field);
			} else {
				var variable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				variable.setKind(VariableKind.getByName(treeVariable.KIND().getText()));
				variable.setName(treeVariable.TYPE().getText() + arrayBrackets + " " + treeVariable.ID().getText());
				vars.getVariables().add(variable);
			}
		}
	}

	private void addGlobalConds(GlobalConditionsContext condsTree) {
		conds = CbcmodelFactory.eINSTANCE.createGlobalConditions();
		for (int i = 0; i < condsTree.getRuleContexts(ConditionContext.class).size(); i++) {
			var condition = CbcmodelFactory.eINSTANCE.createCondition();
			condition.setName(condsTree.condition(i).getText());
			conds.getConditions().add(condition);
		}
	}

	private void addRenamers(RenamingContext renamingTree) {
		renaming = CbcmodelFactory.eINSTANCE.createRenaming();
		for (int i = 0; i < renamingTree.getRuleContexts(RenamerContext.class).size(); i++) {
			var renamer = CbcmodelFactory.eINSTANCE.createRename();
			if (renamingTree.renamer(i).TYPE() == null) {
				renamer.setType("bool");
			} else {
				renamer.setType(renamingTree.renamer(i).TYPE().getText());
			}
			renamer.setFunction(renamingTree.renamer(i).ID().getText());
			renamer.setNewName(renamingTree.renamer(i).condition().getText());
			renaming.getRename().add(renamer);
		}
	}

	private void addRefinements(FormulaContext formulaTree) throws NotImplementedException {
		var statement = getStatementDummy();
		initFormula();
		setModifiables(statement, formulaTree);
		statement.getPreCondition().setName(formulaTree.pre().condition().getText());
		statement.getPostCondition().setName(formulaTree.post().condition().getText());
		formula.getPreCondition().setName(formulaTree.pre().condition().getText());
		formula.getPostCondition().setName(formulaTree.post().condition().getText());
		formula.setStatement(statement);
		walkRefinement(formula.getStatement(), formulaTree.refinement().refinementRule().getChild(0));
		propagateConditionsThroughRefinements();
	}

	private void setModifiables(final AbstractStatement statement, final ParseTree ruleCtx)
			throws NotImplementedException {
		ModifiablesConverter modConverter = null;
		if (ruleCtx instanceof FormulaContext) {
			modConverter = new FormulaModifiablesConverter();
		} else if (ruleCtx instanceof CompositionContext) {
			modConverter = new CompositionModifiablesConverter();
		} else if (ruleCtx instanceof SelectionContext) {
			modConverter = new SelectionModifiablesConverter();
		} else if (ruleCtx instanceof RepetitionContext) {
			modConverter = new RepetitionModifiablesConverter();
		} else {
			throw new NotImplementedException("Modifiables is not supported for '" + ruleCtx.getClass() + "'.");
		}
		modConverter.setModifiables(statement, ruleCtx);
		if (modConverter instanceof FormulaModifiablesConverter) {
			this.methodModifiables = modConverter.getMethodModifiables();
		}
	}

	private AbstractStatement getStatementDummy() {
		var statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		var preCon = CbcmodelFactory.eINSTANCE.createCondition();
		var postCon = CbcmodelFactory.eINSTANCE.createCondition();
		statement.setName("statement");
		statement.setPreCondition(preCon);
		statement.setPostCondition(postCon);
		return statement;
	}

	private void initFormula() {
		var preCon2 = CbcmodelFactory.eINSTANCE.createCondition();
		var postCon2 = CbcmodelFactory.eINSTANCE.createCondition();
		formula.setPreCondition(preCon2);
		formula.setPostCondition(postCon2);
	}

	private void addEmptyPrePost(final AbstractStatement s) {
		var preCon = CbcmodelFactory.eINSTANCE.createCondition();
		var postCon = CbcmodelFactory.eINSTANCE.createCondition();
		preCon.setName("");
		postCon.setName("");
		s.setPreCondition(preCon);
		s.setPostCondition(postCon);
	}

	private AbstractStatement dummyStatement(int num) {
		var statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		addEmptyPrePost(statement);
		statement.setName("statement" + num);
		return statement;
	}

	private void walkRefinement(AbstractStatement parent, ParseTree subtree) throws NotImplementedException {
		if (subtree instanceof CompositionContext) {
			addComposition(parent, (CompositionContext) subtree);
		} else if (subtree instanceof StatementContext) {
			addStatement(parent, (StatementContext) subtree);
		} else if (subtree instanceof SelectionContext) {
			addSelection(parent, (SelectionContext) subtree);
		} else if (subtree instanceof RepetitionContext) {
			addRepetition(parent, (RepetitionContext) subtree);
		} else if (subtree instanceof ReturnSContext) {
			addReturnStatement(parent, (ReturnSContext) subtree);
		} else if (subtree instanceof OriginalSContext) {
			addOriginalStatement(parent, (OriginalSContext) subtree);
		} else if (subtree instanceof SkipSContext) {
			addSkip(parent, (SkipSContext) subtree);
		} else if (subtree instanceof MethodCallSContext) {
			addMethodStatement(parent, (MethodCallSContext) subtree);
		} else if (subtree instanceof MlexprContext) {
			addMlStatement(parent, (MlexprContext) subtree);
		}
	}

	private void addComposition(AbstractStatement parent, CompositionContext csCtx) throws NotImplementedException {
		CompositionStatement cs = CbcmodelFactory.eINSTANCE.createCompositionStatement();
		cs.setFirstStatement(dummyStatement(1));
		cs.setSecondStatement(dummyStatement(2));
		var intm = CbcmodelFactory.eINSTANCE.createCondition();
		cs.setIntermediateCondition(intm);
		cs.getIntermediateCondition().setName(csCtx.intm().condition().getText());
		walkRefinement(cs.getFirstStatement(), csCtx.refinement(0).refinementRule().getChild(0));
		walkRefinement(cs.getSecondStatement(), csCtx.refinement(1).refinementRule().getChild(0));
		setRefinement(parent, cs);
		setModifiables(cs, csCtx);
	}

	private void addStatement(AbstractStatement parent, StatementContext sCtx) {
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		addEmptyPrePost(statement);
		statement.setName(sCtx.getText());
		modelLinker.link(statement, sCtx);
		setRefinement(parent, statement);
		UpdateModifiableOfConditions.updateAssignmentStatement(this.newFields, statement, this.methodModifiables);
	}

	private void addSelection(AbstractStatement parent, SelectionContext selCtx) throws NotImplementedException {
		SelectionStatement selection = CbcmodelFactory.eINSTANCE.createSelectionStatement();
		addEmptyPrePost(selection);
		List<ParseTree> guards = selCtx.guards().children.stream().filter(c -> c instanceof GuardContext).toList();
		for (int i = 0; i < guards.size(); ++i) {
			var g = CbcmodelFactory.eINSTANCE.createCondition();
			g.setName(selCtx.guards().guard(i).condition().getText());
			selection.getGuards().add(g);
			selection.getCommands().add(dummyStatement(i));
			walkRefinement(selection.getCommands().get(i), selCtx.refinement(i).refinementRule().getChild(0));
		}
		setRefinement(parent, selection);
		setModifiables(selection, selCtx);
	}

	private void addRepetition(AbstractStatement parent, RepetitionContext repCtx) throws NotImplementedException {
		var repetition = CbcmodelFactory.eINSTANCE.createSmallRepetitionStatement();
		addEmptyPrePost(repetition);
		var invariant = CbcmodelFactory.eINSTANCE.createCondition();
		var guard = CbcmodelFactory.eINSTANCE.createCondition();
		var variant = CbcmodelFactory.eINSTANCE.createVariant();
		invariant.setName(repCtx.inv().condition().getText());
		guard.setName(repCtx.guard().condition().getText());
		variant.setName(repCtx.var().condition().getText());
		repetition.setInvariant(invariant);
		repetition.setGuard(guard);
		repetition.setVariant(variant);
		repetition.setLoopStatement(dummyStatement(1));
		walkRefinement(repetition.getLoopStatement(), repCtx.refinement().refinementRule().getChild(0));
		setRefinement(parent, repetition);
		setModifiables(repetition, repCtx);
	}

	private void addOriginalStatement(AbstractStatement parent, OriginalSContext origCtx) {
		var originalS = CbcmodelFactory.eINSTANCE.createOriginalStatement();
		addEmptyPrePost(originalS);
		originalS.setName(origCtx.statement().getText());
		modelLinker.link(originalS, origCtx);
		setRefinement(parent, originalS);
		UpdateModifiableOfConditions.updateAssignmentStatement(this.newFields, originalS, this.methodModifiables);
	}

	private void addMethodStatement(AbstractStatement parent, MethodCallSContext methodCCtx) {
		var methodS = CbcmodelFactory.eINSTANCE.createMethodStatement();
		addEmptyPrePost(methodS);
		methodS.setName(methodCCtx.statement().getText());
		modelLinker.link(methodS, methodCCtx);
		setRefinement(parent, methodS);
		UpdateModifiableOfConditions.updateAssignmentStatement(this.newFields, methodS, this.methodModifiables);
	}

	private void addSkip(AbstractStatement parent, SkipSContext sCtx) {
		var skipS = CbcmodelFactory.eINSTANCE.createSkipStatement();
		addEmptyPrePost(skipS);
		modelLinker.link(skipS, sCtx);
		setRefinement(parent, skipS);
	}

	private void addReturnStatement(AbstractStatement parent, ReturnSContext retCtx) {
		var returnS = CbcmodelFactory.eINSTANCE.createReturnStatement();
		addEmptyPrePost(returnS);
		returnS.setName(retCtx.statement().getText());
		modelLinker.link(returnS, retCtx);
		setRefinement(parent, returnS);
		UpdateModifiableOfConditions.updateAssignmentStatement(this.newFields, returnS, this.methodModifiables);
	}

	private void addMlStatement(AbstractStatement parent, MlexprContext mlexprCtx) {
		var mlexprS = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		addEmptyPrePost(mlexprS);
		String mlStatement = "";
		for (int i = 0; i < mlexprCtx.getRuleContexts(StatementContext.class).size(); i++) {
			mlStatement += mlexprCtx.statement(i).getText();
		}
		mlexprS.setName(mlStatement);
		modelLinker.link(mlexprS, mlexprCtx);
		setRefinement(parent, mlexprS);
		UpdateModifiableOfConditions.updateAssignmentStatement(this.newFields, mlexprS, this.methodModifiables);
	}

	private void setRefinement(AbstractStatement parent, AbstractStatement refinement) {
		if (parent instanceof CompositionStatement) {
			handleComposition((CompositionStatement) parent, refinement);
		} else if (parent instanceof SmallRepetitionStatement) {
			handleRepetition((SmallRepetitionStatement) parent, refinement);
		} else if (parent instanceof SelectionStatement) {
			handleSelection((SelectionStatement) parent, refinement);
		} else if (parent instanceof AbstractStatement) {
			handleStatement(parent, refinement);
		}
	}

	private void handleComposition(CompositionStatement parent, AbstractStatement refinement) {
		if (parent.getFirstStatement().getRefinement() == null) {
			parent.getFirstStatement().setRefinement(refinement);
		} else {
			parent.getSecondStatement().setRefinement(refinement);
		}
	}

	private void handleRepetition(SmallRepetitionStatement parent, AbstractStatement refinement) {
		parent.getLoopStatement().setRefinement(refinement);
	}

	private void handleSelection(SelectionStatement parent, AbstractStatement refinement) {
		for (var command : parent.getCommands()) {
			if (command.getRefinement() == null) {
				command.setRefinement(refinement);
			}
		}
	}

	private void handleStatement(AbstractStatement parent, AbstractStatement refinement) {
		parent.setRefinement(refinement);
	}

	private void propagateConditionsThroughRefinements() {
		UpdateConditionsOfChildren.updateConditionsofChildren(formula.getStatement().getPreCondition(), true);
		UpdateConditionsOfChildren.updateConditionsofChildren(formula.getStatement().getPostCondition(), true);
	}

}
