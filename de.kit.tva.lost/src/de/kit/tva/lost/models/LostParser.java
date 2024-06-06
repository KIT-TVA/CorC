package de.kit.tva.lost.models;

import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
// Generated from Lost.g4 by ANTLR 4.4
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.kit.tva.lost.interfaces.LostListener;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class LostParser extends Parser {
    static {
	RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    public static final int T__31 = 1, T__30 = 2, T__29 = 3, T__28 = 4, T__27 = 5, T__26 = 6, T__25 = 7, T__24 = 8,
	    T__23 = 9, T__22 = 10, T__21 = 11, T__20 = 12, T__19 = 13, T__18 = 14, T__17 = 15, T__16 = 16, T__15 = 17,
	    T__14 = 18, T__13 = 19, T__12 = 20, T__11 = 21, T__10 = 22, T__9 = 23, T__8 = 24, T__7 = 25, T__6 = 26,
	    T__5 = 27, T__4 = 28, T__3 = 29, T__2 = 30, T__1 = 31, T__0 = 32, WS = 33, NL = 34, KIND = 35, TYPE = 36,
	    OP = 37, QT = 38, ID = 39;
    public static final String[] tokenNames = { "<INVALID>", "'return'", "'post:'", "'intm:'", "'var:'", "'{'", "'\\'",
	    "';'", "'Renaming'", "'}'", "'()'", "'C'", "'R:'", "'guard:'", "'O:'", "'('", "'pre:'", "','", "'Vars'",
	    "'M:'", "'inv:'", "'S'", "'\t'", "'['", "'skip'", "']'", "'name:'", "'B'", "'D'", "'F'",
	    "'GlobalConditions'", "')'", "'L'", "WS", "'\n'", "KIND", "TYPE", "OP", "QT", "ID" };
    public static final int RULE_program = 0, RULE_root = 1, RULE_diagram = 2, RULE_initializer = 3, RULE_vars = 4,
	    RULE_variable = 5, RULE_globalConditions = 6, RULE_renaming = 7, RULE_renamer = 8, RULE_formula = 9,
	    RULE_pre = 10, RULE_post = 11, RULE_intm = 12, RULE_condition = 13, RULE_quantor = 14, RULE_keyword = 15,
	    RULE_identifier = 16, RULE_refinement = 17, RULE_refinementRule = 18, RULE_statement = 19,
	    RULE_javaReturn = 20, RULE_assigner = 21, RULE_composition = 22, RULE_selection = 23, RULE_guards = 24,
	    RULE_repetition = 25, RULE_inv = 26, RULE_guard = 27, RULE_var = 28, RULE_returnS = 29, RULE_originalS = 30,
	    RULE_skipS = 31, RULE_methodCallS = 32, RULE_block = 33, RULE_name = 34, RULE_mlexpr = 35;
    public static final String[] ruleNames = { "program", "root", "diagram", "initializer", "vars", "variable",
	    "globalConditions", "renaming", "renamer", "formula", "pre", "post", "intm", "condition", "quantor",
	    "keyword", "identifier", "refinement", "refinementRule", "statement", "javaReturn", "assigner",
	    "composition", "selection", "guards", "repetition", "inv", "guard", "var", "returnS", "originalS", "skipS",
	    "methodCallS", "block", "name", "mlexpr" };

    @Override
    public String getGrammarFileName() {
	return "Lost.g4";
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

    public LostParser(TokenStream input) {
	super(input);
	_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ProgramContext extends ParserRuleContext {
	public TerminalNode EOF() {
	    return getToken(LostParser.EOF, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterProgram(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitProgram(this);
	}
    }

    public final ProgramContext program() throws RecognitionException {
	ProgramContext _localctx = new ProgramContext(_ctx, getState());
	enterRule(_localctx, 0, RULE_program);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(72);
		root();
		setState(73);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRoot(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRoot(this);
	}
    }

    public final RootContext root() throws RecognitionException {
	RootContext _localctx = new RootContext(_ctx, getState());
	enterRule(_localctx, 2, RULE_root);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(75);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterDiagram(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitDiagram(this);
	}
    }

    public final DiagramContext diagram() throws RecognitionException {
	DiagramContext _localctx = new DiagramContext(_ctx, getState());
	enterRule(_localctx, 4, RULE_diagram);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(77);
		match(T__4);
		setState(78);
		match(T__17);
		setState(79);
		name();
		setState(80);
		match(T__1);
		setState(81);
		match(NL);
		setState(83);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(82);
			    initializer();
			}
		    }
		    setState(85);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10 || _la == NL);
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
	    return getTokens(LostParser.NL);
	}

	public FormulaContext formula() {
	    return getRuleContext(FormulaContext.class, 0);
	}

	public GlobalConditionsContext globalConditions() {
	    return getRuleContext(GlobalConditionsContext.class, 0);
	}

	public TerminalNode NL(int i) {
	    return getToken(LostParser.NL, i);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterInitializer(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitInitializer(this);
	}
    }

    public final InitializerContext initializer() throws RecognitionException {
	InitializerContext _localctx = new InitializerContext(_ctx, getState());
	enterRule(_localctx, 6, RULE_initializer);
	int _la;
	try {
	    int _alt;
	    setState(143);
	    switch (getInterpreter().adaptivePredict(_input, 9, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(90);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(87);
			    match(NL);
			}
		    }
		    setState(92);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(93);
		match(T__10);
		setState(94);
		formula();
		setState(98);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(95);
				match(NL);
			    }
			}
		    }
		    setState(100);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
		}
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(104);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(101);
			    match(NL);
			}
		    }
		    setState(106);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(107);
		match(T__10);
		setState(108);
		vars();
		setState(112);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(109);
				match(NL);
			    }
			}
		    }
		    setState(114);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
		}
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(118);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(115);
			    match(NL);
			}
		    }
		    setState(120);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(121);
		match(T__10);
		setState(122);
		globalConditions();
		setState(126);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(123);
				match(NL);
			    }
			}
		    }
		    setState(128);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
		}
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(132);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(129);
			    match(NL);
			}
		    }
		    setState(134);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(135);
		match(T__10);
		setState(136);
		renaming();
		setState(140);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(137);
				match(NL);
			    }
			}
		    }
		    setState(142);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterVars(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitVars(this);
	}
    }

    public final VarsContext vars() throws RecognitionException {
	VarsContext _localctx = new VarsContext(_ctx, getState());
	enterRule(_localctx, 8, RULE_vars);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(145);
		match(T__14);
		setState(146);
		match(NL);
		setState(148);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(147);
			    variable();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(150);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 10, _ctx);
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

    public static class VariableContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LostParser.NL, 0);
	}

	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public TerminalNode TYPE() {
	    return getToken(LostParser.TYPE, 0);
	}

	public TerminalNode KIND() {
	    return getToken(LostParser.KIND, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterVariable(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitVariable(this);
	}
    }

    public final VariableContext variable() throws RecognitionException {
	VariableContext _localctx = new VariableContext(_ctx, getState());
	enterRule(_localctx, 10, RULE_variable);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(153);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(152);
			    match(T__10);
			}
		    }
		    setState(155);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(157);
		match(KIND);
		setState(158);
		match(TYPE);
		setState(159);
		match(ID);
		setState(161);
		switch (getInterpreter().adaptivePredict(_input, 12, _ctx)) {
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

    public static class GlobalConditionsContext extends ParserRuleContext {
	public List<TerminalNode> NL() {
	    return getTokens(LostParser.NL);
	}

	public ConditionContext condition(int i) {
	    return getRuleContext(ConditionContext.class, i);
	}

	public TerminalNode NL(int i) {
	    return getToken(LostParser.NL, i);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterGlobalConditions(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitGlobalConditions(this);
	}
    }

    public final GlobalConditionsContext globalConditions() throws RecognitionException {
	GlobalConditionsContext _localctx = new GlobalConditionsContext(_ctx, getState());
	enterRule(_localctx, 12, RULE_globalConditions);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(163);
		match(T__2);
		setState(164);
		match(NL);
		setState(173);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(166);
			    _errHandler.sync(this);
			    _la = _input.LA(1);
			    do {
				{
				    {
					setState(165);
					match(T__10);
				    }
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			    } while (_la == T__10);
			    setState(170);
			    condition(0);
			    setState(171);
			    match(NL);
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(175);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 14, _ctx);
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

    public static class RenamingContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRenaming(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRenaming(this);
	}
    }

    public final RenamingContext renaming() throws RecognitionException {
	RenamingContext _localctx = new RenamingContext(_ctx, getState());
	enterRule(_localctx, 14, RULE_renaming);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(177);
		match(T__24);
		setState(178);
		match(NL);
		setState(180);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(179);
			    renamer();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(182);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 15, _ctx);
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

    public static class RenamerContext extends ParserRuleContext {
	public TerminalNode NL() {
	    return getToken(LostParser.NL, 0);
	}

	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public TerminalNode OP() {
	    return getToken(LostParser.OP, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRenamer(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRenamer(this);
	}
    }

    public final RenamerContext renamer() throws RecognitionException {
	RenamerContext _localctx = new RenamerContext(_ctx, getState());
	enterRule(_localctx, 16, RULE_renamer);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(185);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(184);
			    match(T__10);
			}
		    }
		    setState(187);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(189);
		match(ID);
		setState(190);
		match(OP);
		setState(191);
		condition(0);
		setState(193);
		switch (getInterpreter().adaptivePredict(_input, 17, _ctx)) {
		case 1: {
		    setState(192);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterFormula(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitFormula(this);
	}
    }

    public final FormulaContext formula() throws RecognitionException {
	FormulaContext _localctx = new FormulaContext(_ctx, getState());
	enterRule(_localctx, 18, RULE_formula);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(195);
		match(T__3);
		setState(196);
		match(T__17);
		setState(197);
		pre();
		setState(198);
		match(T__15);
		setState(199);
		post();
		setState(200);
		match(T__1);
		setState(201);
		match(NL);
		setState(202);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterPre(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitPre(this);
	}
    }

    public final PreContext pre() throws RecognitionException {
	PreContext _localctx = new PreContext(_ctx, getState());
	enterRule(_localctx, 20, RULE_pre);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(204);
		match(T__16);
		setState(205);
		condition(0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterPost(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitPost(this);
	}
    }

    public final PostContext post() throws RecognitionException {
	PostContext _localctx = new PostContext(_ctx, getState());
	enterRule(_localctx, 22, RULE_post);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(207);
		match(T__30);
		setState(208);
		condition(0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterIntm(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitIntm(this);
	}
    }

    public final IntmContext intm() throws RecognitionException {
	IntmContext _localctx = new IntmContext(_ctx, getState());
	enterRule(_localctx, 24, RULE_intm);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(210);
		match(T__29);
		setState(211);
		condition(0);
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

	public ConditionContext condition(int i) {
	    return getRuleContext(ConditionContext.class, i);
	}

	public TerminalNode OP() {
	    return getToken(LostParser.OP, 0);
	}

	public IdentifierContext identifier() {
	    return getRuleContext(IdentifierContext.class, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterCondition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitCondition(this);
	}
    }

    public final ConditionContext condition() throws RecognitionException {
	return condition(0);
    }

    private ConditionContext condition(int _p) throws RecognitionException {
	ParserRuleContext _parentctx = _ctx;
	int _parentState = getState();
	ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
	ConditionContext _prevctx = _localctx;
	int _startState = 26;
	enterRecursionRule(_localctx, 26, RULE_condition, _p);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(231);
		switch (getInterpreter().adaptivePredict(_input, 18, _ctx)) {
		case 1: {
		    setState(214);
		    identifier();
		    setState(215);
		    condition(3);
		}
		    break;
		case 2: {
		    setState(217);
		    match(OP);
		    setState(218);
		    condition(2);
		}
		    break;
		case 3: {
		    setState(219);
		    match(T__17);
		    setState(220);
		    condition(0);
		    setState(221);
		    match(T__1);
		}
		    break;
		case 4: {
		    setState(223);
		    match(T__17);
		    setState(224);
		    condition(0);
		    setState(225);
		    match(T__1);
		    setState(226);
		    match(OP);
		    setState(227);
		    condition(0);
		}
		    break;
		case 5: {
		    setState(229);
		    quantor();
		}
		    break;
		case 6: {
		    setState(230);
		    identifier();
		}
		    break;
		}
		_ctx.stop = _input.LT(-1);
		setState(238);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			if (_parseListeners != null)
			    triggerExitRuleEvent();
			_prevctx = _localctx;
			{
			    {
				_localctx = new ConditionContext(_parentctx, _parentState);
				pushNewRecursionContext(_localctx, _startState, RULE_condition);
				setState(233);
				if (!(precpred(_ctx, 1)))
				    throw new FailedPredicateException(this, "precpred(_ctx, 1)");
				setState(234);
				match(T__15);
				setState(235);
				condition(2);
			    }
			}
		    }
		    setState(240);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 19, _ctx);
		}
	    }
	} catch (RecognitionException re) {
	    _localctx.exception = re;
	    _errHandler.reportError(this, re);
	    _errHandler.recover(this, re);
	} finally {
	    unrollRecursionContexts(_parentctx);
	}
	return _localctx;
    }

    public static class QuantorContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public TerminalNode QT() {
	    return getToken(LostParser.QT, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterQuantor(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitQuantor(this);
	}
    }

    public final QuantorContext quantor() throws RecognitionException {
	QuantorContext _localctx = new QuantorContext(_ctx, getState());
	enterRule(_localctx, 28, RULE_quantor);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(241);
		match(QT);
		setState(242);
		match(ID);
		setState(243);
		match(T__25);
		setState(244);
		condition(0);
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

    public static class KeywordContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public IdentifierContext identifier() {
	    return getRuleContext(IdentifierContext.class, 0);
	}

	public KeywordContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_keyword;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterKeyword(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitKeyword(this);
	}
    }

    public final KeywordContext keyword() throws RecognitionException {
	KeywordContext _localctx = new KeywordContext(_ctx, getState());
	enterRule(_localctx, 30, RULE_keyword);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(246);
		match(T__26);
		setState(247);
		match(ID);
		setState(248);
		match(T__17);
		setState(249);
		identifier();
		setState(250);
		match(T__1);
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

    public static class IdentifierContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public KeywordContext keyword() {
	    return getRuleContext(KeywordContext.class, 0);
	}

	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public IdentifierContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_identifier;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterIdentifier(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitIdentifier(this);
	}
    }

    public final IdentifierContext identifier() throws RecognitionException {
	IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
	enterRule(_localctx, 32, RULE_identifier);
	try {
	    setState(262);
	    switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(252);
		match(ID);
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(253);
		keyword();
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(256);
		switch (_input.LA(1)) {
		case ID: {
		    setState(254);
		    match(ID);
		}
		    break;
		case T__26: {
		    setState(255);
		    keyword();
		}
		    break;
		default:
		    throw new NoViableAltException(this);
		}
		setState(258);
		match(T__9);
		setState(259);
		condition(0);
		setState(260);
		match(T__7);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRefinement(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRefinement(this);
	}
    }

    public final RefinementContext refinement() throws RecognitionException {
	RefinementContext _localctx = new RefinementContext(_ctx, getState());
	enterRule(_localctx, 34, RULE_refinement);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(265);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(264);
			    match(T__10);
			}
		    }
		    setState(267);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(269);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRefinementRule(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRefinementRule(this);
	}
    }

    public final RefinementRuleContext refinementRule() throws RecognitionException {
	RefinementRuleContext _localctx = new RefinementRuleContext(_ctx, getState());
	enterRule(_localctx, 36, RULE_refinementRule);
	try {
	    setState(281);
	    switch (_input.LA(1)) {
	    case T__31:
	    case T__26:
	    case ID:
		enterOuterAlt(_localctx, 1); {
		setState(271);
		statement();
	    }
		break;
	    case T__21:
		enterOuterAlt(_localctx, 2); {
		setState(272);
		composition();
	    }
		break;
	    case T__11:
		enterOuterAlt(_localctx, 3); {
		setState(273);
		selection();
	    }
		break;
	    case T__0:
		enterOuterAlt(_localctx, 4); {
		setState(274);
		repetition();
	    }
		break;
	    case T__20:
		enterOuterAlt(_localctx, 5); {
		setState(275);
		returnS();
	    }
		break;
	    case T__18:
		enterOuterAlt(_localctx, 6); {
		setState(276);
		originalS();
	    }
		break;
	    case T__8:
		enterOuterAlt(_localctx, 7); {
		setState(277);
		skipS();
	    }
		break;
	    case T__13:
		enterOuterAlt(_localctx, 8); {
		setState(278);
		methodCallS();
	    }
		break;
	    case T__5:
		enterOuterAlt(_localctx, 9); {
		setState(279);
		block();
	    }
		break;
	    case T__27:
		enterOuterAlt(_localctx, 10); {
		setState(280);
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
	    return getToken(LostParser.NL, 0);
	}

	public TerminalNode OP() {
	    return getToken(LostParser.OP, 0);
	}

	public AssignerContext assigner() {
	    return getRuleContext(AssignerContext.class, 0);
	}

	public IdentifierContext identifier() {
	    return getRuleContext(IdentifierContext.class, 0);
	}

	public JavaReturnContext javaReturn() {
	    return getRuleContext(JavaReturnContext.class, 0);
	}

	public StatementContext statement() {
	    return getRuleContext(StatementContext.class, 0);
	}

	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterStatement(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitStatement(this);
	}
    }

    public final StatementContext statement() throws RecognitionException {
	StatementContext _localctx = new StatementContext(_ctx, getState());
	enterRule(_localctx, 38, RULE_statement);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(300);
		switch (getInterpreter().adaptivePredict(_input, 24, _ctx)) {
		case 1: {
		    setState(283);
		    identifier();
		}
		    break;
		case 2: {
		    setState(284);
		    javaReturn();
		}
		    break;
		case 3: {
		    setState(285);
		    identifier();
		    setState(286);
		    match(T__22);
		}
		    break;
		case 4: {
		    setState(288);
		    identifier();
		    setState(289);
		    match(T__17);
		    setState(290);
		    condition(0);
		    setState(291);
		    match(T__1);
		}
		    break;
		case 5: {
		    setState(293);
		    identifier();
		    setState(294);
		    statement();
		}
		    break;
		case 6: {
		    setState(296);
		    identifier();
		    setState(297);
		    match(OP);
		    setState(298);
		    assigner();
		}
		    break;
		}
		setState(302);
		match(T__25);
		setState(304);
		switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
		case 1: {
		    setState(303);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterJavaReturn(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitJavaReturn(this);
	}
    }

    public final JavaReturnContext javaReturn() throws RecognitionException {
	JavaReturnContext _localctx = new JavaReturnContext(_ctx, getState());
	enterRule(_localctx, 40, RULE_javaReturn);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(306);
		match(T__31);
		setState(307);
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
	public TerminalNode OP() {
	    return getToken(LostParser.OP, 0);
	}

	public AssignerContext assigner() {
	    return getRuleContext(AssignerContext.class, 0);
	}

	public IdentifierContext identifier() {
	    return getRuleContext(IdentifierContext.class, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterAssigner(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitAssigner(this);
	}
    }

    public final AssignerContext assigner() throws RecognitionException {
	AssignerContext _localctx = new AssignerContext(_ctx, getState());
	enterRule(_localctx, 42, RULE_assigner);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(324);
		switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
		case 1: {
		    setState(309);
		    identifier();
		}
		    break;
		case 2: {
		    setState(310);
		    match(T__17);
		    setState(311);
		    assigner();
		    setState(312);
		    match(T__1);
		}
		    break;
		case 3: {
		    setState(314);
		    identifier();
		    setState(315);
		    match(T__22);
		}
		    break;
		case 4: {
		    setState(317);
		    identifier();
		    setState(318);
		    assigner();
		}
		    break;
		case 5: {
		    setState(320);
		    identifier();
		    setState(321);
		    match(OP);
		    setState(322);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterComposition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitComposition(this);
	}
    }

    public final CompositionContext composition() throws RecognitionException {
	CompositionContext _localctx = new CompositionContext(_ctx, getState());
	enterRule(_localctx, 44, RULE_composition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(326);
		match(T__21);
		setState(327);
		match(T__17);
		setState(328);
		intm();
		setState(329);
		match(T__1);
		setState(330);
		match(NL);
		setState(331);
		refinement();
		setState(332);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterSelection(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitSelection(this);
	}
    }

    public final SelectionContext selection() throws RecognitionException {
	SelectionContext _localctx = new SelectionContext(_ctx, getState());
	enterRule(_localctx, 46, RULE_selection);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(334);
		match(T__11);
		setState(335);
		match(T__17);
		setState(336);
		guards();
		setState(337);
		match(T__1);
		setState(338);
		match(NL);
		setState(340);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(339);
			    refinement();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(342);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
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
	public List<GuardContext> guard() {
	    return getRuleContexts(GuardContext.class);
	}

	public GuardContext guard(int i) {
	    return getRuleContext(GuardContext.class, i);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterGuards(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitGuards(this);
	}
    }

    public final GuardsContext guards() throws RecognitionException {
	GuardsContext _localctx = new GuardsContext(_ctx, getState());
	enterRule(_localctx, 48, RULE_guards);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(344);
		guard();
		setState(349);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__15) {
		    {
			{
			    setState(345);
			    match(T__15);
			    setState(346);
			    guard();
			}
		    }
		    setState(351);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterRepetition(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitRepetition(this);
	}
    }

    public final RepetitionContext repetition() throws RecognitionException {
	RepetitionContext _localctx = new RepetitionContext(_ctx, getState());
	enterRule(_localctx, 50, RULE_repetition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(352);
		match(T__0);
		setState(353);
		match(T__17);
		setState(354);
		inv();
		setState(355);
		match(T__15);
		setState(356);
		guard();
		setState(357);
		match(T__15);
		setState(358);
		var();
		setState(359);
		match(T__1);
		setState(360);
		match(NL);
		setState(361);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterInv(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitInv(this);
	}
    }

    public final InvContext inv() throws RecognitionException {
	InvContext _localctx = new InvContext(_ctx, getState());
	enterRule(_localctx, 52, RULE_inv);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(363);
		match(T__12);
		setState(364);
		condition(0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterGuard(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitGuard(this);
	}
    }

    public final GuardContext guard() throws RecognitionException {
	GuardContext _localctx = new GuardContext(_ctx, getState());
	enterRule(_localctx, 54, RULE_guard);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(366);
		match(T__19);
		setState(367);
		condition(0);
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
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterVar(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitVar(this);
	}
    }

    public final VarContext var() throws RecognitionException {
	VarContext _localctx = new VarContext(_ctx, getState());
	enterRule(_localctx, 56, RULE_var);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(369);
		match(T__28);
		setState(370);
		condition(0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterReturnS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitReturnS(this);
	}
    }

    public final ReturnSContext returnS() throws RecognitionException {
	ReturnSContext _localctx = new ReturnSContext(_ctx, getState());
	enterRule(_localctx, 58, RULE_returnS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(372);
		match(T__20);
		setState(373);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterOriginalS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitOriginalS(this);
	}
    }

    public final OriginalSContext originalS() throws RecognitionException {
	OriginalSContext _localctx = new OriginalSContext(_ctx, getState());
	enterRule(_localctx, 60, RULE_originalS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(375);
		match(T__18);
		setState(376);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterSkipS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitSkipS(this);
	}
    }

    public final SkipSContext skipS() throws RecognitionException {
	SkipSContext _localctx = new SkipSContext(_ctx, getState());
	enterRule(_localctx, 62, RULE_skipS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(378);
		match(T__8);
		setState(380);
		switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
		case 1: {
		    setState(379);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterMethodCallS(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitMethodCallS(this);
	}
    }

    public final MethodCallSContext methodCallS() throws RecognitionException {
	MethodCallSContext _localctx = new MethodCallSContext(_ctx, getState());
	enterRule(_localctx, 64, RULE_methodCallS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(382);
		match(T__13);
		setState(383);
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
	    return getToken(LostParser.NL, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterBlock(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitBlock(this);
	}
    }

    public final BlockContext block() throws RecognitionException {
	BlockContext _localctx = new BlockContext(_ctx, getState());
	enterRule(_localctx, 66, RULE_block);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(385);
		match(T__5);
		setState(386);
		match(T__17);
		setState(387);
		name();
		setState(388);
		match(T__15);
		setState(389);
		pre();
		setState(390);
		match(T__15);
		setState(391);
		post();
		setState(392);
		match(T__1);
		setState(393);
		match(NL);
		setState(394);
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
	    return getToken(LostParser.ID, 0);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterName(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitName(this);
	}
    }

    public final NameContext name() throws RecognitionException {
	NameContext _localctx = new NameContext(_ctx, getState());
	enterRule(_localctx, 68, RULE_name);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(396);
		match(T__6);
		setState(397);
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
	    return getTokens(LostParser.NL);
	}

	public StatementContext statement(int i) {
	    return getRuleContext(StatementContext.class, i);
	}

	public TerminalNode NL(int i) {
	    return getToken(LostParser.NL, i);
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
	    if (listener instanceof LostListener)
		((LostListener) listener).enterMlexpr(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitMlexpr(this);
	}
    }

    public final MlexprContext mlexpr() throws RecognitionException {
	MlexprContext _localctx = new MlexprContext(_ctx, getState());
	enterRule(_localctx, 70, RULE_mlexpr);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(399);
		match(T__27);
		setState(401);
		_la = _input.LA(1);
		if (_la == NL) {
		    {
			setState(400);
			match(NL);
		    }
		}

		setState(411);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 32, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(404);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
				    {
					{
					    setState(403);
					    match(T__10);
					}
				    }
				    setState(406);
				    _errHandler.sync(this);
				    _la = _input.LA(1);
				} while (_la == T__10);
				setState(408);
				statement();
			    }
			}
		    }
		    setState(413);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 32, _ctx);
		}
		setState(415);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(414);
			    match(T__10);
			}
		    }
		    setState(417);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(419);
		match(T__23);
		setState(421);
		switch (getInterpreter().adaptivePredict(_input, 34, _ctx)) {
		case 1: {
		    setState(420);
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

    public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
	switch (ruleIndex) {
	case 13:
	    return condition_sempred((ConditionContext) _localctx, predIndex);
	}
	return true;
    }

    private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
	switch (predIndex) {
	case 0:
	    return precpred(_ctx, 1);
	}
	return true;
    }

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3)\u01aa\4\2\t\2\4"
	    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
	    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4"
	    + "\3\4\6\4V\n\4\r\4\16\4W\3\5\7\5[\n\5\f\5\16\5^\13\5\3\5\3\5\3\5\7\5c\n"
	    + "\5\f\5\16\5f\13\5\3\5\7\5i\n\5\f\5\16\5l\13\5\3\5\3\5\3\5\7\5q\n\5\f\5"
	    + "\16\5t\13\5\3\5\7\5w\n\5\f\5\16\5z\13\5\3\5\3\5\3\5\7\5\177\n\5\f\5\16"
	    + "\5\u0082\13\5\3\5\7\5\u0085\n\5\f\5\16\5\u0088\13\5\3\5\3\5\3\5\7\5\u008d"
	    + "\n\5\f\5\16\5\u0090\13\5\5\5\u0092\n\5\3\6\3\6\3\6\6\6\u0097\n\6\r\6\16"
	    + "\6\u0098\3\7\6\7\u009c\n\7\r\7\16\7\u009d\3\7\3\7\3\7\3\7\5\7\u00a4\n"
	    + "\7\3\b\3\b\3\b\6\b\u00a9\n\b\r\b\16\b\u00aa\3\b\3\b\3\b\6\b\u00b0\n\b"
	    + "\r\b\16\b\u00b1\3\t\3\t\3\t\6\t\u00b7\n\t\r\t\16\t\u00b8\3\n\6\n\u00bc"
	    + "\n\n\r\n\16\n\u00bd\3\n\3\n\3\n\3\n\5\n\u00c4\n\n\3\13\3\13\3\13\3\13"
	    + "\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17"
	    + "\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"
	    + "\3\17\3\17\3\17\5\17\u00ea\n\17\3\17\3\17\3\17\7\17\u00ef\n\17\f\17\16"
	    + "\17\u00f2\13\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"
	    + "\3\22\3\22\3\22\3\22\5\22\u0103\n\22\3\22\3\22\3\22\3\22\5\22\u0109\n"
	    + "\22\3\23\6\23\u010c\n\23\r\23\16\23\u010d\3\23\3\23\3\24\3\24\3\24\3\24"
	    + "\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u011c\n\24\3\25\3\25\3\25\3\25\3\25"
	    + "\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u012f"
	    + "\n\25\3\25\3\25\5\25\u0133\n\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27"
	    + "\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0147\n\27\3\30"
	    + "\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\6\31"
	    + "\u0157\n\31\r\31\16\31\u0158\3\32\3\32\3\32\7\32\u015e\n\32\f\32\16\32"
	    + "\u0161\13\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3"
	    + "\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3"
	    + "!\3!\5!\u017f\n!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3"
	    + "$\3%\3%\5%\u0194\n%\3%\6%\u0197\n%\r%\16%\u0198\3%\7%\u019c\n%\f%\16%"
	    + "\u019f\13%\3%\6%\u01a2\n%\r%\16%\u01a3\3%\3%\5%\u01a8\n%\3%\2\3\34&\2"
	    + "\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFH\2"
	    + "\2\u01be\2J\3\2\2\2\4M\3\2\2\2\6O\3\2\2\2\b\u0091\3\2\2\2\n\u0093\3\2"
	    + "\2\2\f\u009b\3\2\2\2\16\u00a5\3\2\2\2\20\u00b3\3\2\2\2\22\u00bb\3\2\2"
	    + "\2\24\u00c5\3\2\2\2\26\u00ce\3\2\2\2\30\u00d1\3\2\2\2\32\u00d4\3\2\2\2"
	    + "\34\u00e9\3\2\2\2\36\u00f3\3\2\2\2 \u00f8\3\2\2\2\"\u0108\3\2\2\2$\u010b"
	    + "\3\2\2\2&\u011b\3\2\2\2(\u012e\3\2\2\2*\u0134\3\2\2\2,\u0146\3\2\2\2."
	    + "\u0148\3\2\2\2\60\u0150\3\2\2\2\62\u015a\3\2\2\2\64\u0162\3\2\2\2\66\u016d"
	    + "\3\2\2\28\u0170\3\2\2\2:\u0173\3\2\2\2<\u0176\3\2\2\2>\u0179\3\2\2\2@"
	    + "\u017c\3\2\2\2B\u0180\3\2\2\2D\u0183\3\2\2\2F\u018e\3\2\2\2H\u0191\3\2"
	    + "\2\2JK\5\4\3\2KL\7\2\2\3L\3\3\2\2\2MN\5\6\4\2N\5\3\2\2\2OP\7\36\2\2PQ"
	    + "\7\21\2\2QR\5F$\2RS\7!\2\2SU\7$\2\2TV\5\b\5\2UT\3\2\2\2VW\3\2\2\2WU\3"
	    + "\2\2\2WX\3\2\2\2X\7\3\2\2\2Y[\7$\2\2ZY\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\"
	    + "]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\7\30\2\2`d\5\24\13\2ac\7$\2\2ba\3\2\2"
	    + "\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2e\u0092\3\2\2\2fd\3\2\2\2gi\7$\2\2hg\3"
	    + "\2\2\2il\3\2\2\2jh\3\2\2\2jk\3\2\2\2km\3\2\2\2lj\3\2\2\2mn\7\30\2\2nr"
	    + "\5\n\6\2oq\7$\2\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\u0092\3\2\2"
	    + "\2tr\3\2\2\2uw\7$\2\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2"
	    + "\2zx\3\2\2\2{|\7\30\2\2|\u0080\5\16\b\2}\177\7$\2\2~}\3\2\2\2\177\u0082"
	    + "\3\2\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0092\3\2\2\2\u0082"
	    + "\u0080\3\2\2\2\u0083\u0085\7$\2\2\u0084\u0083\3\2\2\2\u0085\u0088\3\2"
	    + "\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0089\3\2\2\2\u0088"
	    + "\u0086\3\2\2\2\u0089\u008a\7\30\2\2\u008a\u008e\5\20\t\2\u008b\u008d\7"
	    + "$\2\2\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e"
	    + "\u008f\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0091\\\3\2\2\2"
	    + "\u0091j\3\2\2\2\u0091x\3\2\2\2\u0091\u0086\3\2\2\2\u0092\t\3\2\2\2\u0093"
	    + "\u0094\7\24\2\2\u0094\u0096\7$\2\2\u0095\u0097\5\f\7\2\u0096\u0095\3\2"
	    + "\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099"
	    + "\13\3\2\2\2\u009a\u009c\7\30\2\2\u009b\u009a\3\2\2\2\u009c\u009d\3\2\2"
	    + "\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0"
	    + "\7%\2\2\u00a0\u00a1\7&\2\2\u00a1\u00a3\7)\2\2\u00a2\u00a4\7$\2\2\u00a3"
	    + "\u00a2\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\r\3\2\2\2\u00a5\u00a6\7 \2\2"
	    + "\u00a6\u00af\7$\2\2\u00a7\u00a9\7\30\2\2\u00a8\u00a7\3\2\2\2\u00a9\u00aa"
	    + "\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac"
	    + "\u00ad\5\34\17\2\u00ad\u00ae\7$\2\2\u00ae\u00b0\3\2\2\2\u00af\u00a8\3"
	    + "\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"
	    + "\17\3\2\2\2\u00b3\u00b4\7\n\2\2\u00b4\u00b6\7$\2\2\u00b5\u00b7\5\22\n"
	    + "\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9"
	    + "\3\2\2\2\u00b9\21\3\2\2\2\u00ba\u00bc\7\30\2\2\u00bb\u00ba\3\2\2\2\u00bc"
	    + "\u00bd\3\2\2\2\u00bd\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2"
	    + "\2\2\u00bf\u00c0\7)\2\2\u00c0\u00c1\7\'\2\2\u00c1\u00c3\5\34\17\2\u00c2"
	    + "\u00c4\7$\2\2\u00c3\u00c2\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\23\3\2\2\2"
	    + "\u00c5\u00c6\7\37\2\2\u00c6\u00c7\7\21\2\2\u00c7\u00c8\5\26\f\2\u00c8"
	    + "\u00c9\7\23\2\2\u00c9\u00ca\5\30\r\2\u00ca\u00cb\7!\2\2\u00cb\u00cc\7"
	    + "$\2\2\u00cc\u00cd\5$\23\2\u00cd\25\3\2\2\2\u00ce\u00cf\7\22\2\2\u00cf"
	    + "\u00d0\5\34\17\2\u00d0\27\3\2\2\2\u00d1\u00d2\7\4\2\2\u00d2\u00d3\5\34"
	    + "\17\2\u00d3\31\3\2\2\2\u00d4\u00d5\7\5\2\2\u00d5\u00d6\5\34\17\2\u00d6"
	    + "\33\3\2\2\2\u00d7\u00d8\b\17\1\2\u00d8\u00d9\5\"\22\2\u00d9\u00da\5\34"
	    + "\17\5\u00da\u00ea\3\2\2\2\u00db\u00dc\7\'\2\2\u00dc\u00ea\5\34\17\4\u00dd"
	    + "\u00de\7\21\2\2\u00de\u00df\5\34\17\2\u00df\u00e0\7!\2\2\u00e0\u00ea\3"
	    + "\2\2\2\u00e1\u00e2\7\21\2\2\u00e2\u00e3\5\34\17\2\u00e3\u00e4\7!\2\2\u00e4"
	    + "\u00e5\7\'\2\2\u00e5\u00e6\5\34\17\2\u00e6\u00ea\3\2\2\2\u00e7\u00ea\5"
	    + "\36\20\2\u00e8\u00ea\5\"\22\2\u00e9\u00d7\3\2\2\2\u00e9\u00db\3\2\2\2"
	    + "\u00e9\u00dd\3\2\2\2\u00e9\u00e1\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00e8"
	    + "\3\2\2\2\u00ea\u00f0\3\2\2\2\u00eb\u00ec\f\3\2\2\u00ec\u00ed\7\23\2\2"
	    + "\u00ed\u00ef\5\34\17\4\u00ee\u00eb\3\2\2\2\u00ef\u00f2\3\2\2\2\u00f0\u00ee"
	    + "\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\35\3\2\2\2\u00f2\u00f0\3\2\2\2\u00f3"
	    + "\u00f4\7(\2\2\u00f4\u00f5\7)\2\2\u00f5\u00f6\7\t\2\2\u00f6\u00f7\5\34"
	    + "\17\2\u00f7\37\3\2\2\2\u00f8\u00f9\7\b\2\2\u00f9\u00fa\7)\2\2\u00fa\u00fb"
	    + "\7\21\2\2\u00fb\u00fc\5\"\22\2\u00fc\u00fd\7!\2\2\u00fd!\3\2\2\2\u00fe"
	    + "\u0109\7)\2\2\u00ff\u0109\5 \21\2\u0100\u0103\7)\2\2\u0101\u0103\5 \21"
	    + "\2\u0102\u0100\3\2\2\2\u0102\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0105"
	    + "\7\31\2\2\u0105\u0106\5\34\17\2\u0106\u0107\7\33\2\2\u0107\u0109\3\2\2"
	    + "\2\u0108\u00fe\3\2\2\2\u0108\u00ff\3\2\2\2\u0108\u0102\3\2\2\2\u0109#"
	    + "\3\2\2\2\u010a\u010c\7\30\2\2\u010b\u010a\3\2\2\2\u010c\u010d\3\2\2\2"
	    + "\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110"
	    + "\5&\24\2\u0110%\3\2\2\2\u0111\u011c\5(\25\2\u0112\u011c\5.\30\2\u0113"
	    + "\u011c\5\60\31\2\u0114\u011c\5\64\33\2\u0115\u011c\5<\37\2\u0116\u011c"
	    + "\5> \2\u0117\u011c\5@!\2\u0118\u011c\5B\"\2\u0119\u011c\5D#\2\u011a\u011c"
	    + "\5H%\2\u011b\u0111\3\2\2\2\u011b\u0112\3\2\2\2\u011b\u0113\3\2\2\2\u011b"
	    + "\u0114\3\2\2\2\u011b\u0115\3\2\2\2\u011b\u0116\3\2\2\2\u011b\u0117\3\2"
	    + "\2\2\u011b\u0118\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011a\3\2\2\2\u011c"
	    + "\'\3\2\2\2\u011d\u012f\5\"\22\2\u011e\u012f\5*\26\2\u011f\u0120\5\"\22"
	    + "\2\u0120\u0121\7\f\2\2\u0121\u012f\3\2\2\2\u0122\u0123\5\"\22\2\u0123"
	    + "\u0124\7\21\2\2\u0124\u0125\5\34\17\2\u0125\u0126\7!\2\2\u0126\u012f\3"
	    + "\2\2\2\u0127\u0128\5\"\22\2\u0128\u0129\5(\25\2\u0129\u012f\3\2\2\2\u012a"
	    + "\u012b\5\"\22\2\u012b\u012c\7\'\2\2\u012c\u012d\5,\27\2\u012d\u012f\3"
	    + "\2\2\2\u012e\u011d\3\2\2\2\u012e\u011e\3\2\2\2\u012e\u011f\3\2\2\2\u012e"
	    + "\u0122\3\2\2\2\u012e\u0127\3\2\2\2\u012e\u012a\3\2\2\2\u012f\u0130\3\2"
	    + "\2\2\u0130\u0132\7\t\2\2\u0131\u0133\7$\2\2\u0132\u0131\3\2\2\2\u0132"
	    + "\u0133\3\2\2\2\u0133)\3\2\2\2\u0134\u0135\7\3\2\2\u0135\u0136\5,\27\2"
	    + "\u0136+\3\2\2\2\u0137\u0147\5\"\22\2\u0138\u0139\7\21\2\2\u0139\u013a"
	    + "\5,\27\2\u013a\u013b\7!\2\2\u013b\u0147\3\2\2\2\u013c\u013d\5\"\22\2\u013d"
	    + "\u013e\7\f\2\2\u013e\u0147\3\2\2\2\u013f\u0140\5\"\22\2\u0140\u0141\5"
	    + ",\27\2\u0141\u0147\3\2\2\2\u0142\u0143\5\"\22\2\u0143\u0144\7\'\2\2\u0144"
	    + "\u0145\5,\27\2\u0145\u0147\3\2\2\2\u0146\u0137\3\2\2\2\u0146\u0138\3\2"
	    + "\2\2\u0146\u013c\3\2\2\2\u0146\u013f\3\2\2\2\u0146\u0142\3\2\2\2\u0147"
	    + "-\3\2\2\2\u0148\u0149\7\r\2\2\u0149\u014a\7\21\2\2\u014a\u014b\5\32\16"
	    + "\2\u014b\u014c\7!\2\2\u014c\u014d\7$\2\2\u014d\u014e\5$\23\2\u014e\u014f"
	    + "\5$\23\2\u014f/\3\2\2\2\u0150\u0151\7\27\2\2\u0151\u0152\7\21\2\2\u0152"
	    + "\u0153\5\62\32\2\u0153\u0154\7!\2\2\u0154\u0156\7$\2\2\u0155\u0157\5$"
	    + "\23\2\u0156\u0155\3\2\2\2\u0157\u0158\3\2\2\2\u0158\u0156\3\2\2\2\u0158"
	    + "\u0159\3\2\2\2\u0159\61\3\2\2\2\u015a\u015f\58\35\2\u015b\u015c\7\23\2"
	    + "\2\u015c\u015e\58\35\2\u015d\u015b\3\2\2\2\u015e\u0161\3\2\2\2\u015f\u015d"
	    + "\3\2\2\2\u015f\u0160\3\2\2\2\u0160\63\3\2\2\2\u0161\u015f\3\2\2\2\u0162"
	    + "\u0163\7\"\2\2\u0163\u0164\7\21\2\2\u0164\u0165\5\66\34\2\u0165\u0166"
	    + "\7\23\2\2\u0166\u0167\58\35\2\u0167\u0168\7\23\2\2\u0168\u0169\5:\36\2"
	    + "\u0169\u016a\7!\2\2\u016a\u016b\7$\2\2\u016b\u016c\5$\23\2\u016c\65\3"
	    + "\2\2\2\u016d\u016e\7\26\2\2\u016e\u016f\5\34\17\2\u016f\67\3\2\2\2\u0170"
	    + "\u0171\7\17\2\2\u0171\u0172\5\34\17\2\u01729\3\2\2\2\u0173\u0174\7\6\2"
	    + "\2\u0174\u0175\5\34\17\2\u0175;\3\2\2\2\u0176\u0177\7\16\2\2\u0177\u0178"
	    + "\5(\25\2\u0178=\3\2\2\2\u0179\u017a\7\20\2\2\u017a\u017b\5(\25\2\u017b"
	    + "?\3\2\2\2\u017c\u017e\7\32\2\2\u017d\u017f\7$\2\2\u017e\u017d\3\2\2\2"
	    + "\u017e\u017f\3\2\2\2\u017fA\3\2\2\2\u0180\u0181\7\25\2\2\u0181\u0182\5"
	    + "(\25\2\u0182C\3\2\2\2\u0183\u0184\7\35\2\2\u0184\u0185\7\21\2\2\u0185"
	    + "\u0186\5F$\2\u0186\u0187\7\23\2\2\u0187\u0188\5\26\f\2\u0188\u0189\7\23"
	    + "\2\2\u0189\u018a\5\30\r\2\u018a\u018b\7!\2\2\u018b\u018c\7$\2\2\u018c"
	    + "\u018d\5H%\2\u018dE\3\2\2\2\u018e\u018f\7\34\2\2\u018f\u0190\7)\2\2\u0190"
	    + "G\3\2\2\2\u0191\u0193\7\7\2\2\u0192\u0194\7$\2\2\u0193\u0192\3\2\2\2\u0193"
	    + "\u0194\3\2\2\2\u0194\u019d\3\2\2\2\u0195\u0197\7\30\2\2\u0196\u0195\3"
	    + "\2\2\2\u0197\u0198\3\2\2\2\u0198\u0196\3\2\2\2\u0198\u0199\3\2\2\2\u0199"
	    + "\u019a\3\2\2\2\u019a\u019c\5(\25\2\u019b\u0196\3\2\2\2\u019c\u019f\3\2"
	    + "\2\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019e\u01a1\3\2\2\2\u019f"
	    + "\u019d\3\2\2\2\u01a0\u01a2\7\30\2\2\u01a1\u01a0\3\2\2\2\u01a2\u01a3\3"
	    + "\2\2\2\u01a3\u01a1\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5"
	    + "\u01a7\7\13\2\2\u01a6\u01a8\7$\2\2\u01a7\u01a6\3\2\2\2\u01a7\u01a8\3\2"
	    + "\2\2\u01a8I\3\2\2\2%W\\djrx\u0080\u0086\u008e\u0091\u0098\u009d\u00a3"
	    + "\u00aa\u00b1\u00b8\u00bd\u00c3\u00e9\u00f0\u0102\u0108\u010d\u011b\u012e"
	    + "\u0132\u0146\u0158\u015f\u017e\u0193\u0198\u019d\u01a3\u01a7";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}