package de.kit.tva.lost.interfaces;

// Generated from LOST.g4 by ANTLR 4.4
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
    void enterGuard(LOSTParser.GuardContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#guard}.
     * 
     * @param ctx the parse tree
     */
    void exitGuard(LOSTParser.GuardContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#program}.
     * 
     * @param ctx the parse tree
     */
    void enterProgram(LOSTParser.ProgramContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#program}.
     * 
     * @param ctx the parse tree
     */
    void exitProgram(LOSTParser.ProgramContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void enterRepetition(LOSTParser.RepetitionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#repetition}.
     * 
     * @param ctx the parse tree
     */
    void exitRepetition(LOSTParser.RepetitionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void enterGlobalConditions(LOSTParser.GlobalConditionsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#globalConditions}.
     * 
     * @param ctx the parse tree
     */
    void exitGlobalConditions(LOSTParser.GlobalConditionsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void enterGuards(LOSTParser.GuardsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#guards}.
     * 
     * @param ctx the parse tree
     */
    void exitGuards(LOSTParser.GuardsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#post}.
     * 
     * @param ctx the parse tree
     */
    void enterPost(LOSTParser.PostContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#post}.
     * 
     * @param ctx the parse tree
     */
    void exitPost(LOSTParser.PostContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void enterComposition(LOSTParser.CompositionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#composition}.
     * 
     * @param ctx the parse tree
     */
    void exitComposition(LOSTParser.CompositionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void enterStatement(LOSTParser.StatementContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#statement}.
     * 
     * @param ctx the parse tree
     */
    void exitStatement(LOSTParser.StatementContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinement(LOSTParser.RefinementContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#refinement}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinement(LOSTParser.RefinementContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void enterReturnS(LOSTParser.ReturnSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#returnS}.
     * 
     * @param ctx the parse tree
     */
    void exitReturnS(LOSTParser.ReturnSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#block}.
     * 
     * @param ctx the parse tree
     */
    void enterBlock(LOSTParser.BlockContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#block}.
     * 
     * @param ctx the parse tree
     */
    void exitBlock(LOSTParser.BlockContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void enterVars(LOSTParser.VarsContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#vars}.
     * 
     * @param ctx the parse tree
     */
    void exitVars(LOSTParser.VarsContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void enterRenaming(LOSTParser.RenamingContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#renaming}.
     * 
     * @param ctx the parse tree
     */
    void exitRenaming(LOSTParser.RenamingContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void enterPre(LOSTParser.PreContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#pre}.
     * 
     * @param ctx the parse tree
     */
    void exitPre(LOSTParser.PreContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void enterJavaReturn(LOSTParser.JavaReturnContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#javaReturn}.
     * 
     * @param ctx the parse tree
     */
    void exitJavaReturn(LOSTParser.JavaReturnContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#var}.
     * 
     * @param ctx the parse tree
     */
    void enterVar(LOSTParser.VarContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#var}.
     * 
     * @param ctx the parse tree
     */
    void exitVar(LOSTParser.VarContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void enterAssigner(LOSTParser.AssignerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#assigner}.
     * 
     * @param ctx the parse tree
     */
    void exitAssigner(LOSTParser.AssignerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void enterRefinementRule(LOSTParser.RefinementRuleContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#refinementRule}.
     * 
     * @param ctx the parse tree
     */
    void exitRefinementRule(LOSTParser.RefinementRuleContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void enterInitializer(LOSTParser.InitializerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#initializer}.
     * 
     * @param ctx the parse tree
     */
    void exitInitializer(LOSTParser.InitializerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void enterRenamer(LOSTParser.RenamerContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#renamer}.
     * 
     * @param ctx the parse tree
     */
    void exitRenamer(LOSTParser.RenamerContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void enterIntm(LOSTParser.IntmContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#intm}.
     * 
     * @param ctx the parse tree
     */
    void exitIntm(LOSTParser.IntmContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void enterInv(LOSTParser.InvContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#inv}.
     * 
     * @param ctx the parse tree
     */
    void exitInv(LOSTParser.InvContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void enterCondition(LOSTParser.ConditionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#condition}.
     * 
     * @param ctx the parse tree
     */
    void exitCondition(LOSTParser.ConditionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void enterSkipS(LOSTParser.SkipSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#skipS}.
     * 
     * @param ctx the parse tree
     */
    void exitSkipS(LOSTParser.SkipSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void enterSelection(LOSTParser.SelectionContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#selection}.
     * 
     * @param ctx the parse tree
     */
    void exitSelection(LOSTParser.SelectionContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void enterMlexpr(LOSTParser.MlexprContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#mlexpr}.
     * 
     * @param ctx the parse tree
     */
    void exitMlexpr(LOSTParser.MlexprContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void enterQuantor(LOSTParser.QuantorContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#quantor}.
     * 
     * @param ctx the parse tree
     */
    void exitQuantor(LOSTParser.QuantorContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void enterVariable(LOSTParser.VariableContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#variable}.
     * 
     * @param ctx the parse tree
     */
    void exitVariable(LOSTParser.VariableContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void enterMethodCallS(LOSTParser.MethodCallSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#methodCallS}.
     * 
     * @param ctx the parse tree
     */
    void exitMethodCallS(LOSTParser.MethodCallSContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#name}.
     * 
     * @param ctx the parse tree
     */
    void enterName(LOSTParser.NameContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#name}.
     * 
     * @param ctx the parse tree
     */
    void exitName(LOSTParser.NameContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void enterFormula(LOSTParser.FormulaContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#formula}.
     * 
     * @param ctx the parse tree
     */
    void exitFormula(LOSTParser.FormulaContext ctx);

    /**
     * Enter a parse tree produced by {@link LOSTParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void enterOriginalS(LOSTParser.OriginalSContext ctx);

    /**
     * Exit a parse tree produced by {@link LOSTParser#originalS}.
     * 
     * @param ctx the parse tree
     */
    void exitOriginalS(LOSTParser.OriginalSContext ctx);
}