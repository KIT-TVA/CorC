package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

// Generated from LOST.g4 by ANTLR 4.4

import de.kit.tva.lost.interfaces.LOSTListener;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class LOSTParser extends Parser {
    static {
	RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int T__26 = 1, T__25 = 2, T__24 = 3, T__23 = 4, T__22 = 5, T__21 = 6, T__20 = 7, T__19 = 8,
	    T__18 = 9, T__17 = 10, T__16 = 11, T__15 = 12, T__14 = 13, T__13 = 14, T__12 = 15, T__11 = 16, T__10 = 17,
	    T__9 = 18, T__8 = 19, T__7 = 20, T__6 = 21, T__5 = 22, T__4 = 23, T__3 = 24, T__2 = 25, T__1 = 26,
	    T__0 = 27, WS = 28, NL = 29, OP = 30, ID = 31;
    public static final String[] tokenNames = { "<INVALID>", "'post:'", "'intm:'", "'var:'", "'{'", "';'", "'Renaming'",
	    "'}'", "'()'", "'R:'", "'guard:'", "'C('", "'B('", "'O:'", "'('", "'pre:'", "','", "'Vars'", "'M:'",
	    "'inv:'", "'\t'", "'S('", "'L('", "'skip'", "'name:'", "'F'", "'GlobalConditions'", "')'", "WS", "'\n'",
	    "OP", "ID" };
    public static final int RULE_program = 0, RULE_initializer = 1, RULE_vars = 2, RULE_variable = 3,
	    RULE_globalConditions = 4, RULE_renaming = 5, RULE_renamer = 6, RULE_formula = 7, RULE_pre = 8,
	    RULE_post = 9, RULE_intm = 10, RULE_condition = 11, RULE_quantor = 12, RULE_refinement = 13,
	    RULE_refinementRule = 14, RULE_statement = 15, RULE_javaReturn = 16, RULE_assigner = 17,
	    RULE_composition = 18, RULE_selection = 19, RULE_guards = 20, RULE_repetition = 21, RULE_inv = 22,
	    RULE_guard = 23, RULE_var = 24, RULE_returnS = 25, RULE_originalS = 26, RULE_skipS = 27,
	    RULE_methodCallS = 28, RULE_block = 29, RULE_name = 30, RULE_mlexpr = 31;
    public static final String[] ruleNames = { "program", "initializer", "vars", "variable", "globalConditions",
	    "renaming", "renamer", "formula", "pre", "post", "intm", "condition", "quantor", "refinement",
	    "refinementRule", "statement", "javaReturn", "assigner", "composition", "selection", "guards", "repetition",
	    "inv", "guard", "var", "returnS", "originalS", "skipS", "methodCallS", "block", "name", "mlexpr" };

    @Override
    public String getGrammarFileName() {
	return "LOST.g4";
    }

    @Override
    public String[] getTokenNames() {
	return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
	return ruleNames;
    }

    @Override
    public String getSerializedATN() {
	return _serializedATN;
    }

    @Override
    public ATN getATN() {
	return _ATN;
    }

    public LOSTParser(TokenStream input) {
	super(input);
	_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ProgramContext extends ParserRuleContext {
	public TerminalNode EOF() {
	    return getToken(LOSTParser.EOF, 0);
	}

	public List<InitializerContext> initializer() {
	    return getRuleContexts(InitializerContext.class);
	}

	public InitializerContext initializer(int i) {
	    return getRuleContext(InitializerContext.class, i);
	}

	public ProgramContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_program;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterProgram(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitProgram(this);
	}
    }

    public final ProgramContext program() throws RecognitionException {
	ProgramContext _localctx = new ProgramContext(_ctx, getState());
	enterRule(_localctx, 0, RULE_program);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(65);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(64);
			    initializer();
			}
		    }
		    setState(67);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while ((((_la) & ~0x3f) == 0 && ((1L << _la)
			& ((1L << T__21) | (1L << T__10) | (1L << T__2) | (1L << T__1) | (1L << NL))) != 0));
		setState(69);
		match(EOF);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class InitializerContext extends ParserRuleContext {
	public List<TerminalNode> NL() {
	    return getTokens(LOSTParser.NL);
	}

	public FormulaContext formula() {
	    return getRuleContext(FormulaContext.class, 0);
	}

	public GlobalConditionsContext globalConditions() {
	    return getRuleContext(GlobalConditionsContext.class, 0);
	}

	public TerminalNode NL(int i) {
	    return getToken(LOSTParser.NL, i);
	}

	public RenamingContext renaming() {
	    return getRuleContext(RenamingContext.class, 0);
	}

	public VarsContext vars() {
	    return getRuleContext(VarsContext.class, 0);
	}

	public InitializerContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_initializer;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterInitializer(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitInitializer(this);
	}
    }

    public final InitializerContext initializer() throws RecognitionException {
	InitializerContext _localctx = new InitializerContext(_ctx, getState());
	enterRule(_localctx, 2, RULE_initializer);
	int _la;
	try {
	    int _alt;
	    setState(123);
	    switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(74);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(71);
			    match(NL);
			}
		    }
		    setState(76);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(77);
		formula();
		setState(81);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(78);
				match(NL);
			    }
			}
		    }
		    setState(83);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		}
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(87);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(84);
			    match(NL);
			}
		    }
		    setState(89);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(90);
		vars();
		setState(94);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(91);
				match(NL);
			    }
			}
		    }
		    setState(96);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		}
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(100);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(97);
			    match(NL);
			}
		    }
		    setState(102);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(103);
		globalConditions();
		setState(107);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(104);
				match(NL);
			    }
			}
		    }
		    setState(109);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		}
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(113);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(110);
			    match(NL);
			}
		    }
		    setState(115);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(116);
		renaming();
		setState(120);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(117);
				match(NL);
			    }
			}
		    }
		    setState(122);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
		}
	    }
		break;
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class VarsContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public VariableContext variable(int i) {
	    return getRuleContext(VariableContext.class, i);
	}

	public List<VariableContext> variable() {
	    return getRuleContexts(VariableContext.class);
	}

	public VarsContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_vars;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterVars(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitVars(this);
	}
    }

    public final VarsContext vars() throws RecognitionException {
	VarsContext _localctx = new VarsContext(_ctx, getState());
	enterRule(_localctx, 4, RULE_vars);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(125);
		match(T__10);
		setState(126);
		match(NL);
		setState(128);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(127);
			    variable();
			}
		    }
		    setState(130);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__7);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class VariableContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public List<TerminalNode> ID() {
	    return getTokens(LOSTParser.ID);
	}

	public TerminalNode ID(int i) {
	    return getToken(LOSTParser.ID, i);
	}

	public VariableContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_variable;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterVariable(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitVariable(this);
	}
    }

    public final VariableContext variable() throws RecognitionException {
	VariableContext _localctx = new VariableContext(_ctx, getState());
	enterRule(_localctx, 6, RULE_variable);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(132);
		match(T__7);
		setState(133);
		match(ID);
		setState(134);
		match(ID);
		setState(135);
		match(T__22);
		setState(137);
		switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
		case 1: {
		    setState(136);
		    match(NL);
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class GlobalConditionsContext extends ParserRuleContext {
	public List<TerminalNode> NL() {
	    return getTokens(LOSTParser.NL);
	}

	public ConditionContext condition(int i) {
	    return getRuleContext(ConditionContext.class, i);
	}

	public TerminalNode NL(int i) {
	    return getToken(LOSTParser.NL, i);
	}

	public List<ConditionContext> condition() {
	    return getRuleContexts(ConditionContext.class);
	}

	public GlobalConditionsContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_globalConditions;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterGlobalConditions(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitGlobalConditions(this);
	}
    }

    public final GlobalConditionsContext globalConditions() throws RecognitionException {
	GlobalConditionsContext _localctx = new GlobalConditionsContext(_ctx, getState());
	enterRule(_localctx, 8, RULE_globalConditions);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(139);
		match(T__1);
		setState(140);
		match(NL);
		setState(145);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(141);
			    match(T__7);
			    setState(142);
			    condition();
			    setState(143);
			    match(NL);
			}
		    }
		    setState(147);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__7);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class RenamingContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public List<RenamerContext> renamer() {
	    return getRuleContexts(RenamerContext.class);
	}

	public RenamerContext renamer(int i) {
	    return getRuleContext(RenamerContext.class, i);
	}

	public RenamingContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_renaming;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRenaming(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRenaming(this);
	}
    }

    public final RenamingContext renaming() throws RecognitionException {
	RenamingContext _localctx = new RenamingContext(_ctx, getState());
	enterRule(_localctx, 10, RULE_renaming);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(149);
		match(T__21);
		setState(150);
		match(NL);
		setState(152);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(151);
			    renamer();
			}
		    }
		    setState(154);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__7);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class RenamerContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public TerminalNode OP() {
	    return getToken(LOSTParser.OP, 0);
	}

	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public RenamerContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_renamer;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRenamer(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRenamer(this);
	}
    }

    public final RenamerContext renamer() throws RecognitionException {
	RenamerContext _localctx = new RenamerContext(_ctx, getState());
	enterRule(_localctx, 12, RULE_renamer);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(156);
		match(T__7);
		setState(157);
		match(ID);
		setState(158);
		match(OP);
		setState(159);
		condition();
		setState(161);
		switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
		case 1: {
		    setState(160);
		    match(NL);
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class FormulaContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public PreContext pre() {
	    return getRuleContext(PreContext.class, 0);
	}

	public PostContext post() {
	    return getRuleContext(PostContext.class, 0);
	}

	public RefinementContext refinement() {
	    return getRuleContext(RefinementContext.class, 0);
	}

	public FormulaContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_formula;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterFormula(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitFormula(this);
	}
    }

    public final FormulaContext formula() throws RecognitionException {
	FormulaContext _localctx = new FormulaContext(_ctx, getState());
	enterRule(_localctx, 14, RULE_formula);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(163);
		match(T__2);
		setState(164);
		match(T__13);
		setState(165);
		pre();
		setState(166);
		match(T__11);
		setState(167);
		post();
		setState(168);
		match(T__0);
		setState(169);
		match(NL);
		setState(170);
		refinement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class PreContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public PreContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_pre;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterPre(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitPre(this);
	}
    }

    public final PreContext pre() throws RecognitionException {
	PreContext _localctx = new PreContext(_ctx, getState());
	enterRule(_localctx, 16, RULE_pre);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(172);
		match(T__12);
		setState(173);
		condition();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class PostContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public PostContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_post;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterPost(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitPost(this);
	}
    }

    public final PostContext post() throws RecognitionException {
	PostContext _localctx = new PostContext(_ctx, getState());
	enterRule(_localctx, 18, RULE_post);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(175);
		match(T__26);
		setState(176);
		condition();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class IntmContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public IntmContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_intm;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterIntm(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitIntm(this);
	}
    }

    public final IntmContext intm() throws RecognitionException {
	IntmContext _localctx = new IntmContext(_ctx, getState());
	enterRule(_localctx, 20, RULE_intm);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(178);
		match(T__25);
		setState(179);
		condition();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class ConditionContext extends ParserRuleContext {
	public QuantorContext quantor() {
	    return getRuleContext(QuantorContext.class, 0);
	}

	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public ConditionContext condition(int i) {
	    return getRuleContext(ConditionContext.class, i);
	}

	public TerminalNode OP() {
	    return getToken(LOSTParser.OP, 0);
	}

	public List<ConditionContext> condition() {
	    return getRuleContexts(ConditionContext.class);
	}

	public ConditionContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_condition;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterCondition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitCondition(this);
	}
    }

    public final ConditionContext condition() throws RecognitionException {
	ConditionContext _localctx = new ConditionContext(_ctx, getState());
	enterRule(_localctx, 22, RULE_condition);
	try {
	    setState(197);
	    switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(181);
		match(T__13);
		setState(182);
		condition();
		setState(183);
		match(T__0);
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(185);
		match(T__13);
		setState(186);
		condition();
		setState(187);
		match(T__0);
		setState(188);
		match(OP);
		setState(189);
		condition();
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(191);
		quantor();
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(192);
		match(ID);
	    }
		break;
	    case 5:
		enterOuterAlt(_localctx, 5); {
		setState(193);
		match(ID);
		setState(194);
		condition();
	    }
		break;
	    case 6:
		enterOuterAlt(_localctx, 6); {
		setState(195);
		match(OP);
		setState(196);
		condition();
	    }
		break;
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class QuantorContext extends ParserRuleContext {
	public List<TerminalNode> ID() {
	    return getTokens(LOSTParser.ID);
	}

	public TerminalNode ID(int i) {
	    return getToken(LOSTParser.ID, i);
	}

	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public QuantorContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_quantor;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterQuantor(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitQuantor(this);
	}
    }

    public final QuantorContext quantor() throws RecognitionException {
	QuantorContext _localctx = new QuantorContext(_ctx, getState());
	enterRule(_localctx, 24, RULE_quantor);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(199);
		match(ID);
		setState(200);
		match(T__13);
		setState(201);
		match(ID);
		setState(202);
		match(ID);
		setState(203);
		match(T__22);
		setState(204);
		condition();
		setState(205);
		match(T__0);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class RefinementContext extends ParserRuleContext {
	public RefinementRuleContext refinementRule() {
	    return getRuleContext(RefinementRuleContext.class, 0);
	}

	public RefinementContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_refinement;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRefinement(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRefinement(this);
	}
    }

    public final RefinementContext refinement() throws RecognitionException {
	RefinementContext _localctx = new RefinementContext(_ctx, getState());
	enterRule(_localctx, 26, RULE_refinement);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(208);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(207);
			    match(T__7);
			}
		    }
		    setState(210);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__7);
		setState(212);
		refinementRule();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class RefinementRuleContext extends ParserRuleContext {
	public CompositionContext composition() {
	    return getRuleContext(CompositionContext.class, 0);
	}

	public SelectionContext selection() {
	    return getRuleContext(SelectionContext.class, 0);
	}

	public SkipSContext skipS() {
	    return getRuleContext(SkipSContext.class, 0);
	}

	public OriginalSContext originalS() {
	    return getRuleContext(OriginalSContext.class, 0);
	}

	public MlexprContext mlexpr() {
	    return getRuleContext(MlexprContext.class, 0);
	}

	public MethodCallSContext methodCallS() {
	    return getRuleContext(MethodCallSContext.class, 0);
	}

	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public ReturnSContext returnS() {
	    return getRuleContext(ReturnSContext.class, 0);
	}

	public BlockContext block() {
	    return getRuleContext(BlockContext.class, 0);
	}

	public RepetitionContext repetition() {
	    return getRuleContext(RepetitionContext.class, 0);
	}

	public RefinementRuleContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_refinementRule;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRefinementRule(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRefinementRule(this);
	}
    }

    public final RefinementRuleContext refinementRule() throws RecognitionException {
	RefinementRuleContext _localctx = new RefinementRuleContext(_ctx, getState());
	enterRule(_localctx, 28, RULE_refinementRule);
	try {
	    setState(224);
	    switch (_input.LA(1)) {
	    case T__13:
	    case ID:
		enterOuterAlt(_localctx, 1); {
		setState(214);
		statement();
	    }
		break;
	    case T__16:
		enterOuterAlt(_localctx, 2); {
		setState(215);
		composition();
	    }
		break;
	    case T__6:
		enterOuterAlt(_localctx, 3); {
		setState(216);
		selection();
	    }
		break;
	    case T__5:
		enterOuterAlt(_localctx, 4); {
		setState(217);
		repetition();
	    }
		break;
	    case T__18:
		enterOuterAlt(_localctx, 5); {
		setState(218);
		returnS();
	    }
		break;
	    case T__14:
		enterOuterAlt(_localctx, 6); {
		setState(219);
		originalS();
	    }
		break;
	    case T__4:
		enterOuterAlt(_localctx, 7); {
		setState(220);
		skipS();
	    }
		break;
	    case T__9:
		enterOuterAlt(_localctx, 8); {
		setState(221);
		methodCallS();
	    }
		break;
	    case T__15:
		enterOuterAlt(_localctx, 9); {
		setState(222);
		block();
	    }
		break;
	    case T__23:
		enterOuterAlt(_localctx, 10); {
		setState(223);
		mlexpr();
	    }
		break;
	    default:
		throw new NoViableAltException(this);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class StatementContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public TerminalNode OP() {
	    return getToken(LOSTParser.OP, 0);
	}

	public AssignerContext assigner() {
	    return getRuleContext(AssignerContext.class, 0);
	}

	public JavaReturnContext javaReturn() {
	    return getRuleContext(JavaReturnContext.class, 0);
	}

	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public StatementContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_statement;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterStatement(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitStatement(this);
	}
    }

    public final StatementContext statement() throws RecognitionException {
	StatementContext _localctx = new StatementContext(_ctx, getState());
	enterRule(_localctx, 30, RULE_statement);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(239);
		switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
		case 1: {
		    setState(226);
		    match(ID);
		}
		    break;
		case 2: {
		    setState(227);
		    javaReturn();
		}
		    break;
		case 3: {
		    setState(228);
		    match(T__13);
		    setState(229);
		    statement();
		    setState(230);
		    match(T__0);
		}
		    break;
		case 4: {
		    setState(232);
		    match(ID);
		    setState(233);
		    match(T__19);
		}
		    break;
		case 5: {
		    setState(234);
		    match(ID);
		    setState(235);
		    statement();
		}
		    break;
		case 6: {
		    setState(236);
		    match(ID);
		    setState(237);
		    match(OP);
		    setState(238);
		    assigner();
		}
		    break;
		}
		setState(241);
		match(T__22);
		setState(243);
		switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
		case 1: {
		    setState(242);
		    match(NL);
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class JavaReturnContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public AssignerContext assigner() {
	    return getRuleContext(AssignerContext.class, 0);
	}

	public JavaReturnContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_javaReturn;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterJavaReturn(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitJavaReturn(this);
	}
    }

    public final JavaReturnContext javaReturn() throws RecognitionException {
	JavaReturnContext _localctx = new JavaReturnContext(_ctx, getState());
	enterRule(_localctx, 32, RULE_javaReturn);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(245);
		match(ID);
		setState(246);
		assigner();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class AssignerContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public TerminalNode OP() {
	    return getToken(LOSTParser.OP, 0);
	}

	public AssignerContext assigner() {
	    return getRuleContext(AssignerContext.class, 0);
	}

	public AssignerContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_assigner;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterAssigner(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitAssigner(this);
	}
    }

    public final AssignerContext assigner() throws RecognitionException {
	AssignerContext _localctx = new AssignerContext(_ctx, getState());
	enterRule(_localctx, 34, RULE_assigner);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(260);
		switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
		case 1: {
		    setState(248);
		    match(ID);
		}
		    break;
		case 2: {
		    setState(249);
		    match(T__13);
		    setState(250);
		    assigner();
		    setState(251);
		    match(T__0);
		}
		    break;
		case 3: {
		    setState(253);
		    match(ID);
		    setState(254);
		    match(T__19);
		}
		    break;
		case 4: {
		    setState(255);
		    match(ID);
		    setState(256);
		    assigner();
		}
		    break;
		case 5: {
		    setState(257);
		    match(ID);
		    setState(258);
		    match(OP);
		    setState(259);
		    assigner();
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class CompositionContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public RefinementContext refinement(int i) {
	    return getRuleContext(RefinementContext.class, i);
	}

	public List<RefinementContext> refinement() {
	    return getRuleContexts(RefinementContext.class);
	}

	public IntmContext intm() {
	    return getRuleContext(IntmContext.class, 0);
	}

	public CompositionContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_composition;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterComposition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitComposition(this);
	}
    }

    public final CompositionContext composition() throws RecognitionException {
	CompositionContext _localctx = new CompositionContext(_ctx, getState());
	enterRule(_localctx, 36, RULE_composition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(262);
		match(T__16);
		setState(263);
		intm();
		setState(264);
		match(T__0);
		setState(265);
		match(NL);
		setState(266);
		refinement();
		setState(267);
		refinement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class SelectionContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public GuardsContext guards() {
	    return getRuleContext(GuardsContext.class, 0);
	}

	public RefinementContext refinement(int i) {
	    return getRuleContext(RefinementContext.class, i);
	}

	public List<RefinementContext> refinement() {
	    return getRuleContexts(RefinementContext.class);
	}

	public SelectionContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_selection;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterSelection(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitSelection(this);
	}
    }

    public final SelectionContext selection() throws RecognitionException {
	SelectionContext _localctx = new SelectionContext(_ctx, getState());
	enterRule(_localctx, 38, RULE_selection);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(269);
		match(T__6);
		setState(270);
		guards();
		setState(271);
		match(T__0);
		setState(272);
		match(NL);
		setState(274);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(273);
			    refinement();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(276);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
		} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class GuardsContext extends ParserRuleContext {
	public ConditionContext condition(int i) {
	    return getRuleContext(ConditionContext.class, i);
	}

	public List<ConditionContext> condition() {
	    return getRuleContexts(ConditionContext.class);
	}

	public GuardsContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_guards;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterGuards(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitGuards(this);
	}
    }

    public final GuardsContext guards() throws RecognitionException {
	GuardsContext _localctx = new GuardsContext(_ctx, getState());
	enterRule(_localctx, 40, RULE_guards);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(278);
		match(T__17);
		setState(279);
		condition();
		setState(285);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__11) {
		    {
			{
			    setState(280);
			    match(T__11);
			    setState(281);
			    match(T__17);
			    setState(282);
			    condition();
			}
		    }
		    setState(287);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class RepetitionContext extends ParserRuleContext {
	public InvContext inv() {
	    return getRuleContext(InvContext.class, 0);
	}

	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public GuardContext guard() {
	    return getRuleContext(GuardContext.class, 0);
	}

	public VarContext var() {
	    return getRuleContext(VarContext.class, 0);
	}

	public RefinementContext refinement() {
	    return getRuleContext(RefinementContext.class, 0);
	}

	public RepetitionContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_repetition;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRepetition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRepetition(this);
	}
    }

    public final RepetitionContext repetition() throws RecognitionException {
	RepetitionContext _localctx = new RepetitionContext(_ctx, getState());
	enterRule(_localctx, 42, RULE_repetition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(288);
		match(T__5);
		setState(289);
		inv();
		setState(290);
		match(T__11);
		setState(291);
		guard();
		setState(292);
		match(T__11);
		setState(293);
		var();
		setState(294);
		match(T__0);
		setState(295);
		match(NL);
		setState(296);
		refinement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class InvContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public InvContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_inv;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterInv(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitInv(this);
	}
    }

    public final InvContext inv() throws RecognitionException {
	InvContext _localctx = new InvContext(_ctx, getState());
	enterRule(_localctx, 44, RULE_inv);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(298);
		match(T__8);
		setState(299);
		condition();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class GuardContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public GuardContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_guard;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterGuard(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitGuard(this);
	}
    }

    public final GuardContext guard() throws RecognitionException {
	GuardContext _localctx = new GuardContext(_ctx, getState());
	enterRule(_localctx, 46, RULE_guard);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(301);
		match(T__17);
		setState(302);
		condition();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class VarContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public VarContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_var;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterVar(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitVar(this);
	}
    }

    public final VarContext var() throws RecognitionException {
	VarContext _localctx = new VarContext(_ctx, getState());
	enterRule(_localctx, 48, RULE_var);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(304);
		match(T__24);
		setState(305);
		match(ID);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class ReturnSContext extends ParserRuleContext {
	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public ReturnSContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_returnS;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterReturnS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitReturnS(this);
	}
    }

    public final ReturnSContext returnS() throws RecognitionException {
	ReturnSContext _localctx = new ReturnSContext(_ctx, getState());
	enterRule(_localctx, 50, RULE_returnS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(307);
		match(T__18);
		setState(308);
		statement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class OriginalSContext extends ParserRuleContext {
	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public OriginalSContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_originalS;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterOriginalS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitOriginalS(this);
	}
    }

    public final OriginalSContext originalS() throws RecognitionException {
	OriginalSContext _localctx = new OriginalSContext(_ctx, getState());
	enterRule(_localctx, 52, RULE_originalS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(310);
		match(T__14);
		setState(311);
		statement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class SkipSContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public SkipSContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_skipS;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterSkipS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitSkipS(this);
	}
    }

    public final SkipSContext skipS() throws RecognitionException {
	SkipSContext _localctx = new SkipSContext(_ctx, getState());
	enterRule(_localctx, 54, RULE_skipS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(313);
		match(T__4);
		setState(315);
		switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
		case 1: {
		    setState(314);
		    match(NL);
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class MethodCallSContext extends ParserRuleContext {
	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public MethodCallSContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_methodCallS;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterMethodCallS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitMethodCallS(this);
	}
    }

    public final MethodCallSContext methodCallS() throws RecognitionException {
	MethodCallSContext _localctx = new MethodCallSContext(_ctx, getState());
	enterRule(_localctx, 56, RULE_methodCallS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(317);
		match(T__9);
		setState(318);
		statement();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class BlockContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public PreContext pre() {
	    return getRuleContext(PreContext.class, 0);
	}

	public MlexprContext mlexpr() {
	    return getRuleContext(MlexprContext.class, 0);
	}

	public NameContext name() {
	    return getRuleContext(NameContext.class, 0);
	}

	public PostContext post() {
	    return getRuleContext(PostContext.class, 0);
	}

	public BlockContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_block;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterBlock(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitBlock(this);
	}
    }

    public final BlockContext block() throws RecognitionException {
	BlockContext _localctx = new BlockContext(_ctx, getState());
	enterRule(_localctx, 58, RULE_block);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(320);
		match(T__15);
		setState(321);
		name();
		setState(322);
		match(T__11);
		setState(323);
		pre();
		setState(324);
		match(T__11);
		setState(325);
		post();
		setState(326);
		match(T__0);
		setState(327);
		match(NL);
		setState(328);
		mlexpr();
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class NameContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LOSTParser.ID, 0);
	}

	public NameContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_name;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterName(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitName(this);
	}
    }

    public final NameContext name() throws RecognitionException {
	NameContext _localctx = new NameContext(_ctx, getState());
	enterRule(_localctx, 60, RULE_name);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(330);
		match(T__3);
		setState(331);
		match(ID);
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static class MlexprContext extends ParserRuleContext {
	public List<TerminalNode> NL() {
	    return getTokens(LOSTParser.NL);
	}

	public StatementContext statement(int i) {
	    return getRuleContext(StatementContext.class, i);
	}

	public TerminalNode NL(int i) {
	    return getToken(LOSTParser.NL, i);
	}

	public List<StatementContext> statement() {
	    return getRuleContexts(StatementContext.class);
	}

	public MlexprContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_mlexpr;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterMlexpr(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitMlexpr(this);
	}
    }

    public final MlexprContext mlexpr() throws RecognitionException {
	MlexprContext _localctx = new MlexprContext(_ctx, getState());
	enterRule(_localctx, 62, RULE_mlexpr);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(333);
		match(T__23);
		setState(335);
		_la = _input.LA(1);
		if (_la == NL) {
		    {
			setState(334);
			match(NL);
		    }
		}

		setState(340);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__13 || _la == ID) {
		    {
			{
			    setState(337);
			    statement();
			}
		    }
		    setState(342);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(343);
		match(T__20);
		setState(345);
		switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
		case 1: {
		    setState(344);
		    match(NL);
		}
		    break;
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    exitRule();
	}
	return _localctx;
    }

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3!\u015e\4\2\t\2\4"
	    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
	    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\3\2\6\2D\n\2\r\2\16\2E\3\2\3\2\3\3\7\3K\n\3\f\3\16\3N\13\3\3\3\3\3"
	    + "\7\3R\n\3\f\3\16\3U\13\3\3\3\7\3X\n\3\f\3\16\3[\13\3\3\3\3\3\7\3_\n\3"
	    + "\f\3\16\3b\13\3\3\3\7\3e\n\3\f\3\16\3h\13\3\3\3\3\3\7\3l\n\3\f\3\16\3"
	    + "o\13\3\3\3\7\3r\n\3\f\3\16\3u\13\3\3\3\3\3\7\3y\n\3\f\3\16\3|\13\3\5\3"
	    + "~\n\3\3\4\3\4\3\4\6\4\u0083\n\4\r\4\16\4\u0084\3\5\3\5\3\5\3\5\3\5\5\5"
	    + "\u008c\n\5\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u0094\n\6\r\6\16\6\u0095\3\7\3"
	    + "\7\3\7\6\7\u009b\n\7\r\7\16\7\u009c\3\b\3\b\3\b\3\b\3\b\5\b\u00a4\n\b"
	    + "\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3"
	    + "\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"
	    + "\5\r\u00c8\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\6\17\u00d3"
	    + "\n\17\r\17\16\17\u00d4\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"
	    + "\20\3\20\3\20\5\20\u00e3\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"
	    + "\3\21\3\21\3\21\3\21\3\21\5\21\u00f2\n\21\3\21\3\21\5\21\u00f6\n\21\3"
	    + "\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"
	    + "\23\5\23\u0107\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"
	    + "\3\25\3\25\6\25\u0115\n\25\r\25\16\25\u0116\3\26\3\26\3\26\3\26\3\26\7"
	    + "\26\u011e\n\26\f\26\16\26\u0121\13\26\3\27\3\27\3\27\3\27\3\27\3\27\3"
	    + "\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3"
	    + "\33\3\33\3\34\3\34\3\34\3\35\3\35\5\35\u013e\n\35\3\36\3\36\3\36\3\37"
	    + "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3!\3!\5!\u0152"
	    + "\n!\3!\7!\u0155\n!\f!\16!\u0158\13!\3!\3!\5!\u015c\n!\3!\2\2\"\2\4\6\b"
	    + "\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@\2\2\u016d\2"
	    + "C\3\2\2\2\4}\3\2\2\2\6\177\3\2\2\2\b\u0086\3\2\2\2\n\u008d\3\2\2\2\f\u0097"
	    + "\3\2\2\2\16\u009e\3\2\2\2\20\u00a5\3\2\2\2\22\u00ae\3\2\2\2\24\u00b1\3"
	    + "\2\2\2\26\u00b4\3\2\2\2\30\u00c7\3\2\2\2\32\u00c9\3\2\2\2\34\u00d2\3\2"
	    + "\2\2\36\u00e2\3\2\2\2 \u00f1\3\2\2\2\"\u00f7\3\2\2\2$\u0106\3\2\2\2&\u0108"
	    + "\3\2\2\2(\u010f\3\2\2\2*\u0118\3\2\2\2,\u0122\3\2\2\2.\u012c\3\2\2\2\60"
	    + "\u012f\3\2\2\2\62\u0132\3\2\2\2\64\u0135\3\2\2\2\66\u0138\3\2\2\28\u013b"
	    + "\3\2\2\2:\u013f\3\2\2\2<\u0142\3\2\2\2>\u014c\3\2\2\2@\u014f\3\2\2\2B"
	    + "D\5\4\3\2CB\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2\2\2FG\3\2\2\2GH\7\2\2\3"
	    + "H\3\3\2\2\2IK\7\37\2\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2"
	    + "\2NL\3\2\2\2OS\5\20\t\2PR\7\37\2\2QP\3\2\2\2RU\3\2\2\2SQ\3\2\2\2ST\3\2"
	    + "\2\2T~\3\2\2\2US\3\2\2\2VX\7\37\2\2WV\3\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3"
	    + "\2\2\2Z\\\3\2\2\2[Y\3\2\2\2\\`\5\6\4\2]_\7\37\2\2^]\3\2\2\2_b\3\2\2\2"
	    + "`^\3\2\2\2`a\3\2\2\2a~\3\2\2\2b`\3\2\2\2ce\7\37\2\2dc\3\2\2\2eh\3\2\2"
	    + "\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2im\5\n\6\2jl\7\37\2\2kj\3\2"
	    + "\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2n~\3\2\2\2om\3\2\2\2pr\7\37\2\2qp\3"
	    + "\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2tv\3\2\2\2us\3\2\2\2vz\5\f\7\2wy\7"
	    + "\37\2\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{~\3\2\2\2|z\3\2\2\2}L"
	    + "\3\2\2\2}Y\3\2\2\2}f\3\2\2\2}s\3\2\2\2~\5\3\2\2\2\177\u0080\7\23\2\2\u0080"
	    + "\u0082\7\37\2\2\u0081\u0083\5\b\5\2\u0082\u0081\3\2\2\2\u0083\u0084\3"
	    + "\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\7\3\2\2\2\u0086\u0087"
	    + "\7\26\2\2\u0087\u0088\7!\2\2\u0088\u0089\7!\2\2\u0089\u008b\7\7\2\2\u008a"
	    + "\u008c\7\37\2\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c\t\3\2\2"
	    + "\2\u008d\u008e\7\34\2\2\u008e\u0093\7\37\2\2\u008f\u0090\7\26\2\2\u0090"
	    + "\u0091\5\30\r\2\u0091\u0092\7\37\2\2\u0092\u0094\3\2\2\2\u0093\u008f\3"
	    + "\2\2\2\u0094\u0095\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096"
	    + "\13\3\2\2\2\u0097\u0098\7\b\2\2\u0098\u009a\7\37\2\2\u0099\u009b\5\16"
	    + "\b\2\u009a\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009a\3\2\2\2\u009c"
	    + "\u009d\3\2\2\2\u009d\r\3\2\2\2\u009e\u009f\7\26\2\2\u009f\u00a0\7!\2\2"
	    + "\u00a0\u00a1\7 \2\2\u00a1\u00a3\5\30\r\2\u00a2\u00a4\7\37\2\2\u00a3\u00a2"
	    + "\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\17\3\2\2\2\u00a5\u00a6\7\33\2\2\u00a6"
	    + "\u00a7\7\20\2\2\u00a7\u00a8\5\22\n\2\u00a8\u00a9\7\22\2\2\u00a9\u00aa"
	    + "\5\24\13\2\u00aa\u00ab\7\35\2\2\u00ab\u00ac\7\37\2\2\u00ac\u00ad\5\34"
	    + "\17\2\u00ad\21\3\2\2\2\u00ae\u00af\7\21\2\2\u00af\u00b0\5\30\r\2\u00b0"
	    + "\23\3\2\2\2\u00b1\u00b2\7\3\2\2\u00b2\u00b3\5\30\r\2\u00b3\25\3\2\2\2"
	    + "\u00b4\u00b5\7\4\2\2\u00b5\u00b6\5\30\r\2\u00b6\27\3\2\2\2\u00b7\u00b8"
	    + "\7\20\2\2\u00b8\u00b9\5\30\r\2\u00b9\u00ba\7\35\2\2\u00ba\u00c8\3\2\2"
	    + "\2\u00bb\u00bc\7\20\2\2\u00bc\u00bd\5\30\r\2\u00bd\u00be\7\35\2\2\u00be"
	    + "\u00bf\7 \2\2\u00bf\u00c0\5\30\r\2\u00c0\u00c8\3\2\2\2\u00c1\u00c8\5\32"
	    + "\16\2\u00c2\u00c8\7!\2\2\u00c3\u00c4\7!\2\2\u00c4\u00c8\5\30\r\2\u00c5"
	    + "\u00c6\7 \2\2\u00c6\u00c8\5\30\r\2\u00c7\u00b7\3\2\2\2\u00c7\u00bb\3\2"
	    + "\2\2\u00c7\u00c1\3\2\2\2\u00c7\u00c2\3\2\2\2\u00c7\u00c3\3\2\2\2\u00c7"
	    + "\u00c5\3\2\2\2\u00c8\31\3\2\2\2\u00c9\u00ca\7!\2\2\u00ca\u00cb\7\20\2"
	    + "\2\u00cb\u00cc\7!\2\2\u00cc\u00cd\7!\2\2\u00cd\u00ce\7\7\2\2\u00ce\u00cf"
	    + "\5\30\r\2\u00cf\u00d0\7\35\2\2\u00d0\33\3\2\2\2\u00d1\u00d3\7\26\2\2\u00d2"
	    + "\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2"
	    + "\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\5\36\20\2\u00d7\35\3\2\2\2\u00d8"
	    + "\u00e3\5 \21\2\u00d9\u00e3\5&\24\2\u00da\u00e3\5(\25\2\u00db\u00e3\5,"
	    + "\27\2\u00dc\u00e3\5\64\33\2\u00dd\u00e3\5\66\34\2\u00de\u00e3\58\35\2"
	    + "\u00df\u00e3\5:\36\2\u00e0\u00e3\5<\37\2\u00e1\u00e3\5@!\2\u00e2\u00d8"
	    + "\3\2\2\2\u00e2\u00d9\3\2\2\2\u00e2\u00da\3\2\2\2\u00e2\u00db\3\2\2\2\u00e2"
	    + "\u00dc\3\2\2\2\u00e2\u00dd\3\2\2\2\u00e2\u00de\3\2\2\2\u00e2\u00df\3\2"
	    + "\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e1\3\2\2\2\u00e3\37\3\2\2\2\u00e4\u00f2"
	    + "\7!\2\2\u00e5\u00f2\5\"\22\2\u00e6\u00e7\7\20\2\2\u00e7\u00e8\5 \21\2"
	    + "\u00e8\u00e9\7\35\2\2\u00e9\u00f2\3\2\2\2\u00ea\u00eb\7!\2\2\u00eb\u00f2"
	    + "\7\n\2\2\u00ec\u00ed\7!\2\2\u00ed\u00f2\5 \21\2\u00ee\u00ef\7!\2\2\u00ef"
	    + "\u00f0\7 \2\2\u00f0\u00f2\5$\23\2\u00f1\u00e4\3\2\2\2\u00f1\u00e5\3\2"
	    + "\2\2\u00f1\u00e6\3\2\2\2\u00f1\u00ea\3\2\2\2\u00f1\u00ec\3\2\2\2\u00f1"
	    + "\u00ee\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f5\7\7\2\2\u00f4\u00f6\7\37"
	    + "\2\2\u00f5\u00f4\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6!\3\2\2\2\u00f7\u00f8"
	    + "\7!\2\2\u00f8\u00f9\5$\23\2\u00f9#\3\2\2\2\u00fa\u0107\7!\2\2\u00fb\u00fc"
	    + "\7\20\2\2\u00fc\u00fd\5$\23\2\u00fd\u00fe\7\35\2\2\u00fe\u0107\3\2\2\2"
	    + "\u00ff\u0100\7!\2\2\u0100\u0107\7\n\2\2\u0101\u0102\7!\2\2\u0102\u0107"
	    + "\5$\23\2\u0103\u0104\7!\2\2\u0104\u0105\7 \2\2\u0105\u0107\5$\23\2\u0106"
	    + "\u00fa\3\2\2\2\u0106\u00fb\3\2\2\2\u0106\u00ff\3\2\2\2\u0106\u0101\3\2"
	    + "\2\2\u0106\u0103\3\2\2\2\u0107%\3\2\2\2\u0108\u0109\7\r\2\2\u0109\u010a"
	    + "\5\26\f\2\u010a\u010b\7\35\2\2\u010b\u010c\7\37\2\2\u010c\u010d\5\34\17"
	    + "\2\u010d\u010e\5\34\17\2\u010e\'\3\2\2\2\u010f\u0110\7\27\2\2\u0110\u0111"
	    + "\5*\26\2\u0111\u0112\7\35\2\2\u0112\u0114\7\37\2\2\u0113\u0115\5\34\17"
	    + "\2\u0114\u0113\3\2\2\2\u0115\u0116\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117"
	    + "\3\2\2\2\u0117)\3\2\2\2\u0118\u0119\7\f\2\2\u0119\u011f\5\30\r\2\u011a"
	    + "\u011b\7\22\2\2\u011b\u011c\7\f\2\2\u011c\u011e\5\30\r\2\u011d\u011a\3"
	    + "\2\2\2\u011e\u0121\3\2\2\2\u011f\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120"
	    + "+\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0123\7\30\2\2\u0123\u0124\5.\30\2"
	    + "\u0124\u0125\7\22\2\2\u0125\u0126\5\60\31\2\u0126\u0127\7\22\2\2\u0127"
	    + "\u0128\5\62\32\2\u0128\u0129\7\35\2\2\u0129\u012a\7\37\2\2\u012a\u012b"
	    + "\5\34\17\2\u012b-\3\2\2\2\u012c\u012d\7\25\2\2\u012d\u012e\5\30\r\2\u012e"
	    + "/\3\2\2\2\u012f\u0130\7\f\2\2\u0130\u0131\5\30\r\2\u0131\61\3\2\2\2\u0132"
	    + "\u0133\7\5\2\2\u0133\u0134\7!\2\2\u0134\63\3\2\2\2\u0135\u0136\7\13\2"
	    + "\2\u0136\u0137\5 \21\2\u0137\65\3\2\2\2\u0138\u0139\7\17\2\2\u0139\u013a"
	    + "\5 \21\2\u013a\67\3\2\2\2\u013b\u013d\7\31\2\2\u013c\u013e\7\37\2\2\u013d"
	    + "\u013c\3\2\2\2\u013d\u013e\3\2\2\2\u013e9\3\2\2\2\u013f\u0140\7\24\2\2"
	    + "\u0140\u0141\5 \21\2\u0141;\3\2\2\2\u0142\u0143\7\16\2\2\u0143\u0144\5"
	    + "> \2\u0144\u0145\7\22\2\2\u0145\u0146\5\22\n\2\u0146\u0147\7\22\2\2\u0147"
	    + "\u0148\5\24\13\2\u0148\u0149\7\35\2\2\u0149\u014a\7\37\2\2\u014a\u014b"
	    + "\5@!\2\u014b=\3\2\2\2\u014c\u014d\7\32\2\2\u014d\u014e\7!\2\2\u014e?\3"
	    + "\2\2\2\u014f\u0151\7\6\2\2\u0150\u0152\7\37\2\2\u0151\u0150\3\2\2\2\u0151"
	    + "\u0152\3\2\2\2\u0152\u0156\3\2\2\2\u0153\u0155\5 \21\2\u0154\u0153\3\2"
	    + "\2\2\u0155\u0158\3\2\2\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157"
	    + "\u0159\3\2\2\2\u0158\u0156\3\2\2\2\u0159\u015b\7\t\2\2\u015a\u015c\7\37"
	    + "\2\2\u015b\u015a\3\2\2\2\u015b\u015c\3\2\2\2\u015cA\3\2\2\2\35ELSY`fm"
	    + "sz}\u0084\u008b\u0095\u009c\u00a3\u00c7\u00d4\u00e2\u00f1\u00f5\u0106"
	    + "\u0116\u011f\u013d\u0151\u0156\u015b";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}