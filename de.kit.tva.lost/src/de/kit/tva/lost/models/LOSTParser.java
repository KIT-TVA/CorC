package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
// Generated from LOST.g4 by ANTLR 4.4
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.kit.tva.lost.interfaces.LOSTListener;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class LOSTParser extends Parser {
    static {
	RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int T__27 = 1, T__26 = 2, T__25 = 3, T__24 = 4, T__23 = 5, T__22 = 6, T__21 = 7, T__20 = 8,
	    T__19 = 9, T__18 = 10, T__17 = 11, T__16 = 12, T__15 = 13, T__14 = 14, T__13 = 15, T__12 = 16, T__11 = 17,
	    T__10 = 18, T__9 = 19, T__8 = 20, T__7 = 21, T__6 = 22, T__5 = 23, T__4 = 24, T__3 = 25, T__2 = 26,
	    T__1 = 27, T__0 = 28, WS = 29, NL = 30, OP = 31, ID = 32;
    public static final String[] tokenNames = { "<INVALID>", "'post:'", "'intm:'", "'var:'", "'{'", "';'", "'Renaming'",
	    "'}'", "'D('", "'()'", "'R:'", "'guard:'", "'C('", "'B('", "'O:'", "'('", "'pre:'", "','", "'Vars'", "'M:'",
	    "'inv:'", "'\t'", "'S('", "'L('", "'skip'", "'name:'", "'F'", "'GlobalConditions'", "')'", "WS", "'\n'",
	    "OP", "ID" };
    public static final int RULE_program = 0, RULE_root = 1, RULE_diagram = 2, RULE_initializer = 3, RULE_vars = 4,
	    RULE_variable = 5, RULE_globalConditions = 6, RULE_renaming = 7, RULE_renamer = 8, RULE_formula = 9,
	    RULE_pre = 10, RULE_post = 11, RULE_intm = 12, RULE_condition = 13, RULE_quantor = 14, RULE_refinement = 15,
	    RULE_refinementRule = 16, RULE_statement = 17, RULE_javaReturn = 18, RULE_assigner = 19,
	    RULE_composition = 20, RULE_selection = 21, RULE_guards = 22, RULE_repetition = 23, RULE_inv = 24,
	    RULE_guard = 25, RULE_var = 26, RULE_returnS = 27, RULE_originalS = 28, RULE_skipS = 29,
	    RULE_methodCallS = 30, RULE_block = 31, RULE_name = 32, RULE_mlexpr = 33;
    public static final String[] ruleNames = { "program", "root", "diagram", "initializer", "vars", "variable",
	    "globalConditions", "renaming", "renamer", "formula", "pre", "post", "intm", "condition", "quantor",
	    "refinement", "refinementRule", "statement", "javaReturn", "assigner", "composition", "selection", "guards",
	    "repetition", "inv", "guard", "var", "returnS", "originalS", "skipS", "methodCallS", "block", "name",
	    "mlexpr" };

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

	public RootContext root() {
	    return getRuleContext(RootContext.class, 0);
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
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(68);
		root();
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

    public static class RootContext extends ParserRuleContext {
	public DiagramContext diagram() {
	    return getRuleContext(DiagramContext.class, 0);
	}

	public RootContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_root;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterRoot(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitRoot(this);
	}
    }

    public final RootContext root() throws RecognitionException {
	RootContext _localctx = new RootContext(_ctx, getState());
	enterRule(_localctx, 2, RULE_root);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(71);
		diagram();
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

    public static class DiagramContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LOSTParser.NL, 0);
	}

	public NameContext name() {
	    return getRuleContext(NameContext.class, 0);
	}

	public List<InitializerContext> initializer() {
	    return getRuleContexts(InitializerContext.class);
	}

	public InitializerContext initializer(int i) {
	    return getRuleContext(InitializerContext.class, i);
	}

	public DiagramContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_diagram;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).enterDiagram(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LOSTListener)
		((LOSTListener) listener).exitDiagram(this);
	}
    }

    public final DiagramContext diagram() throws RecognitionException {
	DiagramContext _localctx = new DiagramContext(_ctx, getState());
	enterRule(_localctx, 4, RULE_diagram);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(73);
		match(T__20);
		setState(74);
		name();
		setState(75);
		match(T__0);
		setState(76);
		match(NL);
		setState(77);
		match(T__7);
		setState(79);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(78);
			    initializer();
			}
		    }
		    setState(81);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while ((((_la) & ~0x3f) == 0 && ((1L << _la)
			& ((1L << T__22) | (1L << T__10) | (1L << T__2) | (1L << T__1) | (1L << NL))) != 0));
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
	enterRule(_localctx, 6, RULE_initializer);
	int _la;
	try {
	    int _alt;
	    setState(135);
	    switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(86);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(83);
			    match(NL);
			}
		    }
		    setState(88);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(89);
		formula();
		setState(93);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(90);
				match(NL);
			    }
			}
		    }
		    setState(95);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		}
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(99);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(96);
			    match(NL);
			}
		    }
		    setState(101);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(102);
		vars();
		setState(106);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(103);
				match(NL);
			    }
			}
		    }
		    setState(108);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		}
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(112);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(109);
			    match(NL);
			}
		    }
		    setState(114);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(115);
		globalConditions();
		setState(119);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(116);
				match(NL);
			    }
			}
		    }
		    setState(121);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		}
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(125);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(122);
			    match(NL);
			}
		    }
		    setState(127);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(128);
		renaming();
		setState(132);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(129);
				match(NL);
			    }
			}
		    }
		    setState(134);
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
	enterRule(_localctx, 8, RULE_vars);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(137);
		match(T__10);
		setState(138);
		match(NL);
		setState(140);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(139);
			    variable();
			}
		    }
		    setState(142);
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
	enterRule(_localctx, 10, RULE_variable);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(144);
		match(T__7);
		setState(145);
		match(ID);
		setState(146);
		match(ID);
		setState(147);
		match(T__23);
		setState(149);
		switch (getInterpreter().adaptivePredict(_input, 11, _ctx)) {
		case 1: {
		    setState(148);
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
	enterRule(_localctx, 12, RULE_globalConditions);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(151);
		match(T__1);
		setState(152);
		match(NL);
		setState(157);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(153);
			    match(T__7);
			    setState(154);
			    condition();
			    setState(155);
			    match(NL);
			}
		    }
		    setState(159);
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
	enterRule(_localctx, 14, RULE_renaming);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(161);
		match(T__22);
		setState(162);
		match(NL);
		setState(164);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(163);
			    renamer();
			}
		    }
		    setState(166);
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
	enterRule(_localctx, 16, RULE_renamer);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(168);
		match(T__7);
		setState(169);
		match(ID);
		setState(170);
		match(OP);
		setState(171);
		condition();
		setState(173);
		switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
		case 1: {
		    setState(172);
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
	enterRule(_localctx, 18, RULE_formula);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(175);
		match(T__2);
		setState(176);
		match(T__13);
		setState(177);
		pre();
		setState(178);
		match(T__11);
		setState(179);
		post();
		setState(180);
		match(T__0);
		setState(181);
		match(NL);
		setState(182);
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
	enterRule(_localctx, 20, RULE_pre);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(184);
		match(T__12);
		setState(185);
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
	enterRule(_localctx, 22, RULE_post);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(187);
		match(T__27);
		setState(188);
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
	enterRule(_localctx, 24, RULE_intm);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(190);
		match(T__26);
		setState(191);
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
	enterRule(_localctx, 26, RULE_condition);
	try {
	    setState(209);
	    switch (getInterpreter().adaptivePredict(_input, 15, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(193);
		match(T__13);
		setState(194);
		condition();
		setState(195);
		match(T__0);
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(197);
		match(T__13);
		setState(198);
		condition();
		setState(199);
		match(T__0);
		setState(200);
		match(OP);
		setState(201);
		condition();
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(203);
		quantor();
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(204);
		match(ID);
	    }
		break;
	    case 5:
		enterOuterAlt(_localctx, 5); {
		setState(205);
		match(ID);
		setState(206);
		condition();
	    }
		break;
	    case 6:
		enterOuterAlt(_localctx, 6); {
		setState(207);
		match(OP);
		setState(208);
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
	enterRule(_localctx, 28, RULE_quantor);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(211);
		match(ID);
		setState(212);
		match(T__13);
		setState(213);
		match(ID);
		setState(214);
		match(ID);
		setState(215);
		match(T__23);
		setState(216);
		condition();
		setState(217);
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
	enterRule(_localctx, 30, RULE_refinement);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(220);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(219);
			    match(T__7);
			}
		    }
		    setState(222);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__7);
		setState(224);
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
	enterRule(_localctx, 32, RULE_refinementRule);
	try {
	    setState(236);
	    switch (_input.LA(1)) {
	    case T__13:
	    case ID:
		enterOuterAlt(_localctx, 1); {
		setState(226);
		statement();
	    }
		break;
	    case T__16:
		enterOuterAlt(_localctx, 2); {
		setState(227);
		composition();
	    }
		break;
	    case T__6:
		enterOuterAlt(_localctx, 3); {
		setState(228);
		selection();
	    }
		break;
	    case T__5:
		enterOuterAlt(_localctx, 4); {
		setState(229);
		repetition();
	    }
		break;
	    case T__18:
		enterOuterAlt(_localctx, 5); {
		setState(230);
		returnS();
	    }
		break;
	    case T__14:
		enterOuterAlt(_localctx, 6); {
		setState(231);
		originalS();
	    }
		break;
	    case T__4:
		enterOuterAlt(_localctx, 7); {
		setState(232);
		skipS();
	    }
		break;
	    case T__9:
		enterOuterAlt(_localctx, 8); {
		setState(233);
		methodCallS();
	    }
		break;
	    case T__15:
		enterOuterAlt(_localctx, 9); {
		setState(234);
		block();
	    }
		break;
	    case T__24:
		enterOuterAlt(_localctx, 10); {
		setState(235);
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
	enterRule(_localctx, 34, RULE_statement);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(251);
		switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
		case 1: {
		    setState(238);
		    match(ID);
		}
		    break;
		case 2: {
		    setState(239);
		    javaReturn();
		}
		    break;
		case 3: {
		    setState(240);
		    match(T__13);
		    setState(241);
		    statement();
		    setState(242);
		    match(T__0);
		}
		    break;
		case 4: {
		    setState(244);
		    match(ID);
		    setState(245);
		    match(T__19);
		}
		    break;
		case 5: {
		    setState(246);
		    match(ID);
		    setState(247);
		    statement();
		}
		    break;
		case 6: {
		    setState(248);
		    match(ID);
		    setState(249);
		    match(OP);
		    setState(250);
		    assigner();
		}
		    break;
		}
		setState(253);
		match(T__23);
		setState(255);
		switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
		case 1: {
		    setState(254);
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
	enterRule(_localctx, 36, RULE_javaReturn);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(257);
		match(ID);
		setState(258);
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
	enterRule(_localctx, 38, RULE_assigner);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(272);
		switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
		case 1: {
		    setState(260);
		    match(ID);
		}
		    break;
		case 2: {
		    setState(261);
		    match(T__13);
		    setState(262);
		    assigner();
		    setState(263);
		    match(T__0);
		}
		    break;
		case 3: {
		    setState(265);
		    match(ID);
		    setState(266);
		    match(T__19);
		}
		    break;
		case 4: {
		    setState(267);
		    match(ID);
		    setState(268);
		    assigner();
		}
		    break;
		case 5: {
		    setState(269);
		    match(ID);
		    setState(270);
		    match(OP);
		    setState(271);
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
	enterRule(_localctx, 40, RULE_composition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(274);
		match(T__16);
		setState(275);
		intm();
		setState(276);
		match(T__0);
		setState(277);
		match(NL);
		setState(278);
		refinement();
		setState(279);
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
	enterRule(_localctx, 42, RULE_selection);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(281);
		match(T__6);
		setState(282);
		guards();
		setState(283);
		match(T__0);
		setState(284);
		match(NL);
		setState(286);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(285);
			    refinement();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(288);
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
	enterRule(_localctx, 44, RULE_guards);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(290);
		match(T__17);
		setState(291);
		condition();
		setState(297);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__11) {
		    {
			{
			    setState(292);
			    match(T__11);
			    setState(293);
			    match(T__17);
			    setState(294);
			    condition();
			}
		    }
		    setState(299);
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
	enterRule(_localctx, 46, RULE_repetition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(300);
		match(T__5);
		setState(301);
		inv();
		setState(302);
		match(T__11);
		setState(303);
		guard();
		setState(304);
		match(T__11);
		setState(305);
		var();
		setState(306);
		match(T__0);
		setState(307);
		match(NL);
		setState(308);
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
	enterRule(_localctx, 48, RULE_inv);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(310);
		match(T__8);
		setState(311);
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
	enterRule(_localctx, 50, RULE_guard);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(313);
		match(T__17);
		setState(314);
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
	enterRule(_localctx, 52, RULE_var);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(316);
		match(T__25);
		setState(317);
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
	enterRule(_localctx, 54, RULE_returnS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(319);
		match(T__18);
		setState(320);
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
	enterRule(_localctx, 56, RULE_originalS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(322);
		match(T__14);
		setState(323);
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
	enterRule(_localctx, 58, RULE_skipS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(325);
		match(T__4);
		setState(327);
		switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
		case 1: {
		    setState(326);
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
	enterRule(_localctx, 60, RULE_methodCallS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(329);
		match(T__9);
		setState(330);
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
	enterRule(_localctx, 62, RULE_block);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(332);
		match(T__15);
		setState(333);
		name();
		setState(334);
		match(T__11);
		setState(335);
		pre();
		setState(336);
		match(T__11);
		setState(337);
		post();
		setState(338);
		match(T__0);
		setState(339);
		match(NL);
		setState(340);
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
	enterRule(_localctx, 64, RULE_name);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(342);
		match(T__3);
		setState(343);
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
	enterRule(_localctx, 66, RULE_mlexpr);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(345);
		match(T__24);
		setState(347);
		_la = _input.LA(1);
		if (_la == NL) {
		    {
			setState(346);
			match(NL);
		    }
		}

		setState(352);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__13 || _la == ID) {
		    {
			{
			    setState(349);
			    statement();
			}
		    }
		    setState(354);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(355);
		match(T__21);
		setState(357);
		switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
		case 1: {
		    setState(356);
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

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\"\u016a\4\2\t\2\4"
	    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
	    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\6\4R\n\4"
	    + "\r\4\16\4S\3\5\7\5W\n\5\f\5\16\5Z\13\5\3\5\3\5\7\5^\n\5\f\5\16\5a\13\5"
	    + "\3\5\7\5d\n\5\f\5\16\5g\13\5\3\5\3\5\7\5k\n\5\f\5\16\5n\13\5\3\5\7\5q"
	    + "\n\5\f\5\16\5t\13\5\3\5\3\5\7\5x\n\5\f\5\16\5{\13\5\3\5\7\5~\n\5\f\5\16"
	    + "\5\u0081\13\5\3\5\3\5\7\5\u0085\n\5\f\5\16\5\u0088\13\5\5\5\u008a\n\5"
	    + "\3\6\3\6\3\6\6\6\u008f\n\6\r\6\16\6\u0090\3\7\3\7\3\7\3\7\3\7\5\7\u0098"
	    + "\n\7\3\b\3\b\3\b\3\b\3\b\3\b\6\b\u00a0\n\b\r\b\16\b\u00a1\3\t\3\t\3\t"
	    + "\6\t\u00a7\n\t\r\t\16\t\u00a8\3\n\3\n\3\n\3\n\3\n\5\n\u00b0\n\n\3\13\3"
	    + "\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3"
	    + "\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"
	    + "\17\3\17\3\17\3\17\5\17\u00d4\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20"
	    + "\3\20\3\21\6\21\u00df\n\21\r\21\16\21\u00e0\3\21\3\21\3\22\3\22\3\22\3"
	    + "\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00ef\n\22\3\23\3\23\3\23\3\23"
	    + "\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00fe\n\23\3\23\3\23"
	    + "\5\23\u0102\n\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"
	    + "\3\25\3\25\3\25\3\25\5\25\u0113\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26"
	    + "\3\27\3\27\3\27\3\27\3\27\6\27\u0121\n\27\r\27\16\27\u0122\3\30\3\30\3"
	    + "\30\3\30\3\30\7\30\u012a\n\30\f\30\16\30\u012d\13\30\3\31\3\31\3\31\3"
	    + "\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3"
	    + "\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\5\37\u014a\n\37\3 \3"
	    + " \3 \3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3#\3#\5#\u015e\n#\3#\7"
	    + "#\u0161\n#\f#\16#\u0164\13#\3#\3#\5#\u0168\n#\3#\2\2$\2\4\6\b\n\f\16\20"
	    + "\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BD\2\2\u0177\2F\3\2\2\2"
	    + "\4I\3\2\2\2\6K\3\2\2\2\b\u0089\3\2\2\2\n\u008b\3\2\2\2\f\u0092\3\2\2\2"
	    + "\16\u0099\3\2\2\2\20\u00a3\3\2\2\2\22\u00aa\3\2\2\2\24\u00b1\3\2\2\2\26"
	    + "\u00ba\3\2\2\2\30\u00bd\3\2\2\2\32\u00c0\3\2\2\2\34\u00d3\3\2\2\2\36\u00d5"
	    + "\3\2\2\2 \u00de\3\2\2\2\"\u00ee\3\2\2\2$\u00fd\3\2\2\2&\u0103\3\2\2\2"
	    + "(\u0112\3\2\2\2*\u0114\3\2\2\2,\u011b\3\2\2\2.\u0124\3\2\2\2\60\u012e"
	    + "\3\2\2\2\62\u0138\3\2\2\2\64\u013b\3\2\2\2\66\u013e\3\2\2\28\u0141\3\2"
	    + "\2\2:\u0144\3\2\2\2<\u0147\3\2\2\2>\u014b\3\2\2\2@\u014e\3\2\2\2B\u0158"
	    + "\3\2\2\2D\u015b\3\2\2\2FG\5\4\3\2GH\7\2\2\3H\3\3\2\2\2IJ\5\6\4\2J\5\3"
	    + "\2\2\2KL\7\n\2\2LM\5B\"\2MN\7\36\2\2NO\7 \2\2OQ\7\27\2\2PR\5\b\5\2QP\3"
	    + "\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2T\7\3\2\2\2UW\7 \2\2VU\3\2\2\2WZ\3"
	    + "\2\2\2XV\3\2\2\2XY\3\2\2\2Y[\3\2\2\2ZX\3\2\2\2[_\5\24\13\2\\^\7 \2\2]"
	    + "\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\u008a\3\2\2\2a_\3\2\2\2bd\7"
	    + " \2\2cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2hl\5"
	    + "\n\6\2ik\7 \2\2ji\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2m\u008a\3\2\2\2"
	    + "nl\3\2\2\2oq\7 \2\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2su\3\2\2\2"
	    + "tr\3\2\2\2uy\5\16\b\2vx\7 \2\2wv\3\2\2\2x{\3\2\2\2yw\3\2\2\2yz\3\2\2\2"
	    + "z\u008a\3\2\2\2{y\3\2\2\2|~\7 \2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2"
	    + "\2\177\u0080\3\2\2\2\u0080\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u0086"
	    + "\5\20\t\2\u0083\u0085\7 \2\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086"
	    + "\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2"
	    + "\2\2\u0089X\3\2\2\2\u0089e\3\2\2\2\u0089r\3\2\2\2\u0089\177\3\2\2\2\u008a"
	    + "\t\3\2\2\2\u008b\u008c\7\24\2\2\u008c\u008e\7 \2\2\u008d\u008f\5\f\7\2"
	    + "\u008e\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091"
	    + "\3\2\2\2\u0091\13\3\2\2\2\u0092\u0093\7\27\2\2\u0093\u0094\7\"\2\2\u0094"
	    + "\u0095\7\"\2\2\u0095\u0097\7\7\2\2\u0096\u0098\7 \2\2\u0097\u0096\3\2"
	    + "\2\2\u0097\u0098\3\2\2\2\u0098\r\3\2\2\2\u0099\u009a\7\35\2\2\u009a\u009f"
	    + "\7 \2\2\u009b\u009c\7\27\2\2\u009c\u009d\5\34\17\2\u009d\u009e\7 \2\2"
	    + "\u009e\u00a0\3\2\2\2\u009f\u009b\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u009f"
	    + "\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\17\3\2\2\2\u00a3\u00a4\7\b\2\2\u00a4"
	    + "\u00a6\7 \2\2\u00a5\u00a7\5\22\n\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2"
	    + "\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\21\3\2\2\2\u00aa\u00ab"
	    + "\7\27\2\2\u00ab\u00ac\7\"\2\2\u00ac\u00ad\7!\2\2\u00ad\u00af\5\34\17\2"
	    + "\u00ae\u00b0\7 \2\2\u00af\u00ae\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0\23\3"
	    + "\2\2\2\u00b1\u00b2\7\34\2\2\u00b2\u00b3\7\21\2\2\u00b3\u00b4\5\26\f\2"
	    + "\u00b4\u00b5\7\23\2\2\u00b5\u00b6\5\30\r\2\u00b6\u00b7\7\36\2\2\u00b7"
	    + "\u00b8\7 \2\2\u00b8\u00b9\5 \21\2\u00b9\25\3\2\2\2\u00ba\u00bb\7\22\2"
	    + "\2\u00bb\u00bc\5\34\17\2\u00bc\27\3\2\2\2\u00bd\u00be\7\3\2\2\u00be\u00bf"
	    + "\5\34\17\2\u00bf\31\3\2\2\2\u00c0\u00c1\7\4\2\2\u00c1\u00c2\5\34\17\2"
	    + "\u00c2\33\3\2\2\2\u00c3\u00c4\7\21\2\2\u00c4\u00c5\5\34\17\2\u00c5\u00c6"
	    + "\7\36\2\2\u00c6\u00d4\3\2\2\2\u00c7\u00c8\7\21\2\2\u00c8\u00c9\5\34\17"
	    + "\2\u00c9\u00ca\7\36\2\2\u00ca\u00cb\7!\2\2\u00cb\u00cc\5\34\17\2\u00cc"
	    + "\u00d4\3\2\2\2\u00cd\u00d4\5\36\20\2\u00ce\u00d4\7\"\2\2\u00cf\u00d0\7"
	    + "\"\2\2\u00d0\u00d4\5\34\17\2\u00d1\u00d2\7!\2\2\u00d2\u00d4\5\34\17\2"
	    + "\u00d3\u00c3\3\2\2\2\u00d3\u00c7\3\2\2\2\u00d3\u00cd\3\2\2\2\u00d3\u00ce"
	    + "\3\2\2\2\u00d3\u00cf\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4\35\3\2\2\2\u00d5"
	    + "\u00d6\7\"\2\2\u00d6\u00d7\7\21\2\2\u00d7\u00d8\7\"\2\2\u00d8\u00d9\7"
	    + "\"\2\2\u00d9\u00da\7\7\2\2\u00da\u00db\5\34\17\2\u00db\u00dc\7\36\2\2"
	    + "\u00dc\37\3\2\2\2\u00dd\u00df\7\27\2\2\u00de\u00dd\3\2\2\2\u00df\u00e0"
	    + "\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2"
	    + "\u00e3\5\"\22\2\u00e3!\3\2\2\2\u00e4\u00ef\5$\23\2\u00e5\u00ef\5*\26\2"
	    + "\u00e6\u00ef\5,\27\2\u00e7\u00ef\5\60\31\2\u00e8\u00ef\58\35\2\u00e9\u00ef"
	    + "\5:\36\2\u00ea\u00ef\5<\37\2\u00eb\u00ef\5> \2\u00ec\u00ef\5@!\2\u00ed"
	    + "\u00ef\5D#\2\u00ee\u00e4\3\2\2\2\u00ee\u00e5\3\2\2\2\u00ee\u00e6\3\2\2"
	    + "\2\u00ee\u00e7\3\2\2\2\u00ee\u00e8\3\2\2\2\u00ee\u00e9\3\2\2\2\u00ee\u00ea"
	    + "\3\2\2\2\u00ee\u00eb\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ed\3\2\2\2\u00ef"
	    + "#\3\2\2\2\u00f0\u00fe\7\"\2\2\u00f1\u00fe\5&\24\2\u00f2\u00f3\7\21\2\2"
	    + "\u00f3\u00f4\5$\23\2\u00f4\u00f5\7\36\2\2\u00f5\u00fe\3\2\2\2\u00f6\u00f7"
	    + "\7\"\2\2\u00f7\u00fe\7\13\2\2\u00f8\u00f9\7\"\2\2\u00f9\u00fe\5$\23\2"
	    + "\u00fa\u00fb\7\"\2\2\u00fb\u00fc\7!\2\2\u00fc\u00fe\5(\25\2\u00fd\u00f0"
	    + "\3\2\2\2\u00fd\u00f1\3\2\2\2\u00fd\u00f2\3\2\2\2\u00fd\u00f6\3\2\2\2\u00fd"
	    + "\u00f8\3\2\2\2\u00fd\u00fa\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\7\7"
	    + "\2\2\u0100\u0102\7 \2\2\u0101\u0100\3\2\2\2\u0101\u0102\3\2\2\2\u0102"
	    + "%\3\2\2\2\u0103\u0104\7\"\2\2\u0104\u0105\5(\25\2\u0105\'\3\2\2\2\u0106"
	    + "\u0113\7\"\2\2\u0107\u0108\7\21\2\2\u0108\u0109\5(\25\2\u0109\u010a\7"
	    + "\36\2\2\u010a\u0113\3\2\2\2\u010b\u010c\7\"\2\2\u010c\u0113\7\13\2\2\u010d"
	    + "\u010e\7\"\2\2\u010e\u0113\5(\25\2\u010f\u0110\7\"\2\2\u0110\u0111\7!"
	    + "\2\2\u0111\u0113\5(\25\2\u0112\u0106\3\2\2\2\u0112\u0107\3\2\2\2\u0112"
	    + "\u010b\3\2\2\2\u0112\u010d\3\2\2\2\u0112\u010f\3\2\2\2\u0113)\3\2\2\2"
	    + "\u0114\u0115\7\16\2\2\u0115\u0116\5\32\16\2\u0116\u0117\7\36\2\2\u0117"
	    + "\u0118\7 \2\2\u0118\u0119\5 \21\2\u0119\u011a\5 \21\2\u011a+\3\2\2\2\u011b"
	    + "\u011c\7\30\2\2\u011c\u011d\5.\30\2\u011d\u011e\7\36\2\2\u011e\u0120\7"
	    + " \2\2\u011f\u0121\5 \21\2\u0120\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122"
	    + "\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123-\3\2\2\2\u0124\u0125\7\r\2\2"
	    + "\u0125\u012b\5\34\17\2\u0126\u0127\7\23\2\2\u0127\u0128\7\r\2\2\u0128"
	    + "\u012a\5\34\17\2\u0129\u0126\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3"
	    + "\2\2\2\u012b\u012c\3\2\2\2\u012c/\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u012f"
	    + "\7\31\2\2\u012f\u0130\5\62\32\2\u0130\u0131\7\23\2\2\u0131\u0132\5\64"
	    + "\33\2\u0132\u0133\7\23\2\2\u0133\u0134\5\66\34\2\u0134\u0135\7\36\2\2"
	    + "\u0135\u0136\7 \2\2\u0136\u0137\5 \21\2\u0137\61\3\2\2\2\u0138\u0139\7"
	    + "\26\2\2\u0139\u013a\5\34\17\2\u013a\63\3\2\2\2\u013b\u013c\7\r\2\2\u013c"
	    + "\u013d\5\34\17\2\u013d\65\3\2\2\2\u013e\u013f\7\5\2\2\u013f\u0140\7\""
	    + "\2\2\u0140\67\3\2\2\2\u0141\u0142\7\f\2\2\u0142\u0143\5$\23\2\u01439\3"
	    + "\2\2\2\u0144\u0145\7\20\2\2\u0145\u0146\5$\23\2\u0146;\3\2\2\2\u0147\u0149"
	    + "\7\32\2\2\u0148\u014a\7 \2\2\u0149\u0148\3\2\2\2\u0149\u014a\3\2\2\2\u014a"
	    + "=\3\2\2\2\u014b\u014c\7\25\2\2\u014c\u014d\5$\23\2\u014d?\3\2\2\2\u014e"
	    + "\u014f\7\17\2\2\u014f\u0150\5B\"\2\u0150\u0151\7\23\2\2\u0151\u0152\5"
	    + "\26\f\2\u0152\u0153\7\23\2\2\u0153\u0154\5\30\r\2\u0154\u0155\7\36\2\2"
	    + "\u0155\u0156\7 \2\2\u0156\u0157\5D#\2\u0157A\3\2\2\2\u0158\u0159\7\33"
	    + "\2\2\u0159\u015a\7\"\2\2\u015aC\3\2\2\2\u015b\u015d\7\6\2\2\u015c\u015e"
	    + "\7 \2\2\u015d\u015c\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u0162\3\2\2\2\u015f"
	    + "\u0161\5$\23\2\u0160\u015f\3\2\2\2\u0161\u0164\3\2\2\2\u0162\u0160\3\2"
	    + "\2\2\u0162\u0163\3\2\2\2\u0163\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165"
	    + "\u0167\7\t\2\2\u0166\u0168\7 \2\2\u0167\u0166\3\2\2\2\u0167\u0168\3\2"
	    + "\2\2\u0168E\3\2\2\2\35SX_elry\177\u0086\u0089\u0090\u0097\u00a1\u00a8"
	    + "\u00af\u00d3\u00e0\u00ee\u00fd\u0101\u0112\u0122\u012b\u0149\u015d\u0162" + "\u0167";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}