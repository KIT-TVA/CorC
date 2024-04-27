package de.kit.tva.lost.interfaces;

// Generated from LOST.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

import de.kit.tva.lost.models.LOSTParser;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LOSTParser}.
 */
public interface LOSTListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link LOSTParser#guard}.
     * 
     * @param ctx the parse tree
     */
    void enterGuard(@NotNull LOSTParser.GuardContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#guard}.
     * 
     * @param ctx the parse tree
     */
    void exitGuard(@NotNull LOSTParser.GuardContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#program}.
     * 
     * @param ctx the parse tree
     */
    void enterProgram(@NotNull LOSTParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#program}.
     * 
     * @param ctx the parse tree
     */
    void exitProgram(@NotNull LOSTParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void enterRepetition(@NotNull LOSTParser.RepetitionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void exitRepetition(@NotNull LOSTParser.RepetitionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void enterGlobalConditions(@NotNull LOSTParser.GlobalConditionsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void exitGlobalConditions(@NotNull LOSTParser.GlobalConditionsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void enterGuards(@NotNull LOSTParser.GuardsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void exitGuards(@NotNull LOSTParser.GuardsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#post}.
     * 
     * @param ctx the parse tree
     */
    void enterPost(@NotNull LOSTParser.PostContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#post}.
     * 
     * @param ctx the parse tree
     */
    void exitPost(@NotNull LOSTParser.PostContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void enterComposition(@NotNull LOSTParser.CompositionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void exitComposition(@NotNull LOSTParser.CompositionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#root}.
     * 
     * @param ctx the parse tree
     */
    void enterRoot(@NotNull LOSTParser.RootContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#root}.
     * 
     * @param ctx the parse tree
     */
    void exitRoot(@NotNull LOSTParser.RootContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void enterStatement(@NotNull LOSTParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void exitStatement(@NotNull LOSTParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinement(@NotNull LOSTParser.RefinementContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinement(@NotNull LOSTParser.RefinementContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void enterReturnS(@NotNull LOSTParser.ReturnSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void exitReturnS(@NotNull LOSTParser.ReturnSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#block}.
     * 
     * @param ctx the parse tree
     */
    void enterBlock(@NotNull LOSTParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#block}.
     * 
     * @param ctx the parse tree
     */
    void exitBlock(@NotNull LOSTParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void enterVars(@NotNull LOSTParser.VarsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void exitVars(@NotNull LOSTParser.VarsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void enterRenaming(@NotNull LOSTParser.RenamingContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void exitRenaming(@NotNull LOSTParser.RenamingContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void enterPre(@NotNull LOSTParser.PreContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void exitPre(@NotNull LOSTParser.PreContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void enterJavaReturn(@NotNull LOSTParser.JavaReturnContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void exitJavaReturn(@NotNull LOSTParser.JavaReturnContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#var}.
     * 
     * @param ctx the parse tree
     */
    void enterVar(@NotNull LOSTParser.VarContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#var}.
     * 
     * @param ctx the parse tree
     */
    void exitVar(@NotNull LOSTParser.VarContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void enterAssigner(@NotNull LOSTParser.AssignerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void exitAssigner(@NotNull LOSTParser.AssignerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinementRule(@NotNull LOSTParser.RefinementRuleContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinementRule(@NotNull LOSTParser.RefinementRuleContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void enterInitializer(@NotNull LOSTParser.InitializerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void exitInitializer(@NotNull LOSTParser.InitializerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void enterRenamer(@NotNull LOSTParser.RenamerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void exitRenamer(@NotNull LOSTParser.RenamerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void enterIntm(@NotNull LOSTParser.IntmContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void exitIntm(@NotNull LOSTParser.IntmContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#diagram}.
     * 
     * @param ctx the parse tree
     */
    void enterDiagram(@NotNull LOSTParser.DiagramContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#diagram}.
     * 
     * @param ctx the parse tree
     */
    void exitDiagram(@NotNull LOSTParser.DiagramContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void enterInv(@NotNull LOSTParser.InvContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void exitInv(@NotNull LOSTParser.InvContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void enterCondition(@NotNull LOSTParser.ConditionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void exitCondition(@NotNull LOSTParser.ConditionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void enterSkipS(@NotNull LOSTParser.SkipSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void exitSkipS(@NotNull LOSTParser.SkipSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void enterSelection(@NotNull LOSTParser.SelectionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void exitSelection(@NotNull LOSTParser.SelectionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void enterMlexpr(@NotNull LOSTParser.MlexprContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void exitMlexpr(@NotNull LOSTParser.MlexprContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void enterQuantor(@NotNull LOSTParser.QuantorContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void exitQuantor(@NotNull LOSTParser.QuantorContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void enterVariable(@NotNull LOSTParser.VariableContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void exitVariable(@NotNull LOSTParser.VariableContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void enterMethodCallS(@NotNull LOSTParser.MethodCallSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void exitMethodCallS(@NotNull LOSTParser.MethodCallSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#name}.
     * 
     * @param ctx the parse tree
     */
    void enterName(@NotNull LOSTParser.NameContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#name}.
     * 
     * @param ctx the parse tree
     */
    void exitName(@NotNull LOSTParser.NameContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void enterFormula(@NotNull LOSTParser.FormulaContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void exitFormula(@NotNull LOSTParser.FormulaContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void enterOriginalS(@NotNull LOSTParser.OriginalSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void exitOriginalS(@NotNull LOSTParser.OriginalSContext ctx);
}