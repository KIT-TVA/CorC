package de.kit.tva.lost.interfaces;

// Generated from Lost.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import de.kit.tva.lost.models.parser.LostParser;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LostParser}.
 */
public interface LostListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link LostParser#mod}.
     * 
     * @param ctx the parse tree
     */
    void enterMod(@NotNull LostParser.ModContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#mod}.
     * 
     * @param ctx the parse tree
     */
    void exitMod(@NotNull LostParser.ModContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#signature}.
     * 
     * @param ctx the parse tree
     */
    void enterSignature(@NotNull LostParser.SignatureContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#signature}.
     * 
     * @param ctx the parse tree
     */
    void exitSignature(@NotNull LostParser.SignatureContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#guard}.
     * 
     * @param ctx the parse tree
     */
    void enterGuard(@NotNull LostParser.GuardContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#guard}.
     * 
     * @param ctx the parse tree
     */
    void exitGuard(@NotNull LostParser.GuardContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#methodParameter}.
     * 
     * @param ctx the parse tree
     */
    void enterMethodParameter(@NotNull LostParser.MethodParameterContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#methodParameter}.
     * 
     * @param ctx the parse tree
     */
    void exitMethodParameter(@NotNull LostParser.MethodParameterContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#program}.
     * 
     * @param ctx the parse tree
     */
    void enterProgram(@NotNull LostParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#program}.
     * 
     * @param ctx the parse tree
     */
    void exitProgram(@NotNull LostParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void enterRepetition(@NotNull LostParser.RepetitionContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void exitRepetition(@NotNull LostParser.RepetitionContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void enterGlobalConditions(@NotNull LostParser.GlobalConditionsContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void exitGlobalConditions(@NotNull LostParser.GlobalConditionsContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void enterGuards(@NotNull LostParser.GuardsContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void exitGuards(@NotNull LostParser.GuardsContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#post}.
     * 
     * @param ctx the parse tree
     */
    void enterPost(@NotNull LostParser.PostContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#post}.
     * 
     * @param ctx the parse tree
     */
    void exitPost(@NotNull LostParser.PostContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void enterComposition(@NotNull LostParser.CompositionContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void exitComposition(@NotNull LostParser.CompositionContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#root}.
     * 
     * @param ctx the parse tree
     */
    void enterRoot(@NotNull LostParser.RootContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#root}.
     * 
     * @param ctx the parse tree
     */
    void exitRoot(@NotNull LostParser.RootContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void enterStatement(@NotNull LostParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void exitStatement(@NotNull LostParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinement(@NotNull LostParser.RefinementContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinement(@NotNull LostParser.RefinementContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void enterReturnS(@NotNull LostParser.ReturnSContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void exitReturnS(@NotNull LostParser.ReturnSContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#block}.
     * 
     * @param ctx the parse tree
     */
    void enterBlock(@NotNull LostParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#block}.
     * 
     * @param ctx the parse tree
     */
    void exitBlock(@NotNull LostParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void enterVars(@NotNull LostParser.VarsContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void exitVars(@NotNull LostParser.VarsContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#diagramParam}.
     * 
     * @param ctx the parse tree
     */
    void enterDiagramParam(@NotNull LostParser.DiagramParamContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#diagramParam}.
     * 
     * @param ctx the parse tree
     */
    void exitDiagramParam(@NotNull LostParser.DiagramParamContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#keyword}.
     * 
     * @param ctx the parse tree
     */
    void enterKeyword(@NotNull LostParser.KeywordContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#keyword}.
     * 
     * @param ctx the parse tree
     */
    void exitKeyword(@NotNull LostParser.KeywordContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void enterRenaming(@NotNull LostParser.RenamingContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void exitRenaming(@NotNull LostParser.RenamingContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#identifier}.
     * 
     * @param ctx the parse tree
     */
    void enterIdentifier(@NotNull LostParser.IdentifierContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#identifier}.
     * 
     * @param ctx the parse tree
     */
    void exitIdentifier(@NotNull LostParser.IdentifierContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void enterPre(@NotNull LostParser.PreContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void exitPre(@NotNull LostParser.PreContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void enterJavaReturn(@NotNull LostParser.JavaReturnContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void exitJavaReturn(@NotNull LostParser.JavaReturnContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#var}.
     * 
     * @param ctx the parse tree
     */
    void enterVar(@NotNull LostParser.VarContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#var}.
     * 
     * @param ctx the parse tree
     */
    void exitVar(@NotNull LostParser.VarContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void enterAssigner(@NotNull LostParser.AssignerContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void exitAssigner(@NotNull LostParser.AssignerContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinementRule(@NotNull LostParser.RefinementRuleContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinementRule(@NotNull LostParser.RefinementRuleContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void enterInitializer(@NotNull LostParser.InitializerContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void exitInitializer(@NotNull LostParser.InitializerContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void enterRenamer(@NotNull LostParser.RenamerContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void exitRenamer(@NotNull LostParser.RenamerContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void enterIntm(@NotNull LostParser.IntmContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void exitIntm(@NotNull LostParser.IntmContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#diagram}.
     * 
     * @param ctx the parse tree
     */
    void enterDiagram(@NotNull LostParser.DiagramContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#diagram}.
     * 
     * @param ctx the parse tree
     */
    void exitDiagram(@NotNull LostParser.DiagramContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void enterInv(@NotNull LostParser.InvContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void exitInv(@NotNull LostParser.InvContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void enterCondition(@NotNull LostParser.ConditionContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void exitCondition(@NotNull LostParser.ConditionContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void enterSkipS(@NotNull LostParser.SkipSContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void exitSkipS(@NotNull LostParser.SkipSContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void enterSelection(@NotNull LostParser.SelectionContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void exitSelection(@NotNull LostParser.SelectionContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void enterMlexpr(@NotNull LostParser.MlexprContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void exitMlexpr(@NotNull LostParser.MlexprContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void enterQuantor(@NotNull LostParser.QuantorContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void exitQuantor(@NotNull LostParser.QuantorContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void enterVariable(@NotNull LostParser.VariableContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void exitVariable(@NotNull LostParser.VariableContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void enterMethodCallS(@NotNull LostParser.MethodCallSContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void exitMethodCallS(@NotNull LostParser.MethodCallSContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#name}.
     * 
     * @param ctx the parse tree
     */
    void enterName(@NotNull LostParser.NameContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#name}.
     * 
     * @param ctx the parse tree
     */
    void exitName(@NotNull LostParser.NameContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void enterFormula(@NotNull LostParser.FormulaContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void exitFormula(@NotNull LostParser.FormulaContext ctx);

    /**
     * Enter a parse tree produced by {@link LostParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void enterOriginalS(@NotNull LostParser.OriginalSContext ctx);

    /**
     * Exit a parse tree produced by {@link LostParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void exitOriginalS(@NotNull LostParser.OriginalSContext ctx);
}