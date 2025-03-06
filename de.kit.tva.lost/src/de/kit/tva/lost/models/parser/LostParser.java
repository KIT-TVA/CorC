package de.kit.tva.lost.models.parser;

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
    public static final int T__34 = 1, T__33 = 2, T__32 = 3, T__31 = 4, T__30 = 5, T__29 = 6, T__28 = 7, T__27 = 8,
	    T__26 = 9, T__25 = 10, T__24 = 11, T__23 = 12, T__22 = 13, T__21 = 14, T__20 = 15, T__19 = 16, T__18 = 17,
	    T__17 = 18, T__16 = 19, T__15 = 20, T__14 = 21, T__13 = 22, T__12 = 23, T__11 = 24, T__10 = 25, T__9 = 26,
	    T__8 = 27, T__7 = 28, T__6 = 29, T__5 = 30, T__4 = 31, T__3 = 32, T__2 = 33, T__1 = 34, T__0 = 35, WS = 36,
	    NL = 37, INDICATOR_DELIM = 38, KIND = 39, VISIBILITY = 40, TYPE = 41, BRACKETS = 42, EMPTY_BRACKETS = 43,
	    NEW = 44, OP = 45, QT = 46, ID = 47;
    public static final String[] tokenNames = { "<INVALID>", "'return'", "'post:'", "'intm:'", "'var:'", "'{'",
	    "'void'", "'\\'", "'Renaming'", "'}'", "'C'", "'R:'", "'guard:'", "'mod:'", "'O:'", "'('", "'sig:'",
	    "'pre:'", "','", "'Vars'", "'M:'", "'inv:'", "'S'", "'\t'", "'['", "'skip'", "']'", "'bool'", "'[*]'",
	    "'name:'", "'B'", "'D'", "'F'", "'GlobalConditions'", "')'", "'L'", "WS", "'\n'", "';'", "KIND",
	    "VISIBILITY", "TYPE", "BRACKETS", "'()'", "'new '", "OP", "QT", "ID" };
    public static final int RULE_program = 0, RULE_root = 1, RULE_diagram = 2, RULE_diagramParam = 3,
	    RULE_initializer = 4, RULE_vars = 5, RULE_variable = 6, RULE_globalConditions = 7, RULE_renaming = 8,
	    RULE_renamer = 9, RULE_formula = 10, RULE_pre = 11, RULE_post = 12, RULE_mod = 13, RULE_intm = 14,
	    RULE_condition = 15, RULE_quantor = 16, RULE_keyword = 17, RULE_identifier = 18, RULE_refinement = 19,
	    RULE_refinementRule = 20, RULE_statement = 21, RULE_javaReturn = 22, RULE_assigner = 23,
	    RULE_composition = 24, RULE_selection = 25, RULE_guards = 26, RULE_repetition = 27, RULE_inv = 28,
	    RULE_guard = 29, RULE_var = 30, RULE_returnS = 31, RULE_originalS = 32, RULE_skipS = 33,
	    RULE_methodCallS = 34, RULE_block = 35, RULE_name = 36, RULE_signature = 37, RULE_methodParameter = 38,
	    RULE_mlexpr = 39;
    public static final String[] ruleNames = { "program", "root", "diagram", "diagramParam", "initializer", "vars",
	    "variable", "globalConditions", "renaming", "renamer", "formula", "pre", "post", "mod", "intm", "condition",
	    "quantor", "keyword", "identifier", "refinement", "refinementRule", "statement", "javaReturn", "assigner",
	    "composition", "selection", "guards", "repetition", "inv", "guard", "var", "returnS", "originalS", "skipS",
	    "methodCallS", "block", "name", "signature", "methodParameter", "mlexpr" };

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
		setState(80);
		root();
		setState(81);
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
		setState(83);
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

	public DiagramParamContext diagramParam() {
	    return getRuleContext(DiagramParamContext.class, 0);
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
		setState(85);
		match(T__4);
		setState(86);
		match(T__20);
		setState(87);
		diagramParam();
		setState(88);
		match(T__1);
		setState(89);
		match(NL);
		setState(91);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(90);
			    initializer();
			}
		    }
		    setState(93);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__12 || _la == NL);
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

    public static class DiagramParamContext extends ParserRuleContext {
	public SignatureContext signature() {
	    return getRuleContext(SignatureContext.class, 0);
	}

	public NameContext name() {
	    return getRuleContext(NameContext.class, 0);
	}

	public DiagramParamContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_diagramParam;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterDiagramParam(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitDiagramParam(this);
	}
    }

    public final DiagramParamContext diagramParam() throws RecognitionException {
	DiagramParamContext _localctx = new DiagramParamContext(_ctx, getState());
	enterRule(_localctx, 6, RULE_diagramParam);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(97);
		switch (_input.LA(1)) {
		case T__6: {
		    setState(95);
		    name();
		}
		    break;
		case T__19: {
		    setState(96);
		    signature();
		}
		    break;
		default:
		    throw new NoViableAltException(this);
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
	enterRule(_localctx, 8, RULE_initializer);
	int _la;
	try {
	    int _alt;
	    setState(155);
	    switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(102);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(99);
			    match(NL);
			}
		    }
		    setState(104);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(105);
		match(T__12);
		setState(106);
		formula();
		setState(110);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(107);
				match(NL);
			    }
			}
		    }
		    setState(112);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
		}
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(116);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(113);
			    match(NL);
			}
		    }
		    setState(118);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(119);
		match(T__12);
		setState(120);
		vars();
		setState(124);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(121);
				match(NL);
			    }
			}
		    }
		    setState(126);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
		}
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(130);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(127);
			    match(NL);
			}
		    }
		    setState(132);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(133);
		match(T__12);
		setState(134);
		globalConditions();
		setState(138);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(135);
				match(NL);
			    }
			}
		    }
		    setState(140);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
		}
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(144);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(141);
			    match(NL);
			}
		    }
		    setState(146);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(147);
		match(T__12);
		setState(148);
		renaming();
		setState(152);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(149);
				match(NL);
			    }
			}
		    }
		    setState(154);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
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
	enterRule(_localctx, 10, RULE_vars);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(157);
		match(T__16);
		setState(158);
		match(NL);
		setState(160);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(159);
			    variable();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(162);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 11, _ctx);
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

	public TerminalNode BRACKETS() {
	    return getToken(LostParser.BRACKETS, 0);
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
	enterRule(_localctx, 12, RULE_variable);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(165);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(164);
			    match(T__12);
			}
		    }
		    setState(167);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__12);
		setState(169);
		match(KIND);
		setState(170);
		match(TYPE);
		setState(172);
		_la = _input.LA(1);
		if (_la == BRACKETS) {
		    {
			setState(171);
			match(BRACKETS);
		    }
		}

		setState(174);
		match(ID);
		setState(176);
		switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
		case 1: {
		    setState(175);
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
	enterRule(_localctx, 14, RULE_globalConditions);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(178);
		match(T__2);
		setState(179);
		match(NL);
		setState(188);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(181);
			    _errHandler.sync(this);
			    _la = _input.LA(1);
			    do {
				{
				    {
					setState(180);
					match(T__12);
				    }
				}
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
			    } while (_la == T__12);
			    setState(185);
			    condition(0);
			    setState(186);
			    match(NL);
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(190);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
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
	enterRule(_localctx, 16, RULE_renaming);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(192);
		match(T__27);
		setState(193);
		match(NL);
		setState(195);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(194);
			    renamer();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(197);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 17, _ctx);
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

	public TerminalNode TYPE() {
	    return getToken(LostParser.TYPE, 0);
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
	enterRule(_localctx, 18, RULE_renamer);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(200);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(199);
			    match(T__12);
			}
		    }
		    setState(202);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__12);
		setState(204);
		_la = _input.LA(1);
		if (!(_la == T__8 || _la == TYPE)) {
		    _errHandler.recoverInline(this);
		}
		consume();
		setState(205);
		match(ID);
		setState(206);
		match(OP);
		setState(207);
		condition(0);
		setState(209);
		switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
		case 1: {
		    setState(208);
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

	public ModContext mod() {
	    return getRuleContext(ModContext.class, 0);
	}

	public TerminalNode INDICATOR_DELIM(int i) {
	    return getToken(LostParser.INDICATOR_DELIM, i);
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

	public List<TerminalNode> INDICATOR_DELIM() {
	    return getTokens(LostParser.INDICATOR_DELIM);
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
	enterRule(_localctx, 20, RULE_formula);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(211);
		match(T__3);
		setState(212);
		match(T__20);
		setState(216);
		_la = _input.LA(1);
		if (_la == T__22) {
		    {
			setState(213);
			mod();
			setState(214);
			match(INDICATOR_DELIM);
		    }
		}

		setState(218);
		pre();
		setState(219);
		match(INDICATOR_DELIM);
		setState(220);
		post();
		setState(221);
		match(T__1);
		setState(222);
		match(NL);
		setState(223);
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
	enterRule(_localctx, 22, RULE_pre);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(225);
		match(T__18);
		setState(226);
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
	enterRule(_localctx, 24, RULE_post);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(228);
		match(T__33);
		setState(229);
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

    public static class ModContext extends ParserRuleContext {
	public ConditionContext condition() {
	    return getRuleContext(ConditionContext.class, 0);
	}

	public ModContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_mod;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterMod(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitMod(this);
	}
    }

    public final ModContext mod() throws RecognitionException {
	ModContext _localctx = new ModContext(_ctx, getState());
	enterRule(_localctx, 26, RULE_mod);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(231);
		match(T__22);
		setState(232);
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
	enterRule(_localctx, 28, RULE_intm);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(234);
		match(T__32);
		setState(235);
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
	int _startState = 30;
	enterRecursionRule(_localctx, 30, RULE_condition, _p);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(255);
		switch (getInterpreter().adaptivePredict(_input, 21, _ctx)) {
		case 1: {
		    setState(238);
		    identifier();
		    setState(239);
		    condition(3);
		}
		    break;
		case 2: {
		    setState(241);
		    match(OP);
		    setState(242);
		    condition(2);
		}
		    break;
		case 3: {
		    setState(243);
		    match(T__20);
		    setState(244);
		    condition(0);
		    setState(245);
		    match(T__1);
		}
		    break;
		case 4: {
		    setState(247);
		    match(T__20);
		    setState(248);
		    condition(0);
		    setState(249);
		    match(T__1);
		    setState(250);
		    match(OP);
		    setState(251);
		    condition(0);
		}
		    break;
		case 5: {
		    setState(253);
		    quantor();
		}
		    break;
		case 6: {
		    setState(254);
		    identifier();
		}
		    break;
		}
		_ctx.stop = _input.LT(-1);
		setState(262);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			if (_parseListeners != null)
			    triggerExitRuleEvent();
			_prevctx = _localctx;
			{
			    {
				_localctx = new ConditionContext(_parentctx, _parentState);
				pushNewRecursionContext(_localctx, _startState, RULE_condition);
				setState(257);
				if (!(precpred(_ctx, 1)))
				    throw new FailedPredicateException(this, "precpred(_ctx, 1)");
				setState(258);
				match(T__17);
				setState(259);
				condition(2);
			    }
			}
		    }
		    setState(264);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
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
	enterRule(_localctx, 32, RULE_quantor);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(265);
		match(QT);
		setState(266);
		match(ID);
		setState(267);
		match(INDICATOR_DELIM);
		setState(268);
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
	enterRule(_localctx, 34, RULE_keyword);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(270);
		match(T__28);
		setState(271);
		match(ID);
		setState(272);
		match(T__20);
		setState(273);
		identifier();
		setState(274);
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

	public TerminalNode TYPE() {
	    return getToken(LostParser.TYPE, 0);
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
	enterRule(_localctx, 36, RULE_identifier);
	try {
	    setState(290);
	    switch (getInterpreter().adaptivePredict(_input, 25, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(276);
		match(ID);
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(277);
		keyword();
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(281);
		switch (_input.LA(1)) {
		case TYPE: {
		    setState(278);
		    match(TYPE);
		}
		    break;
		case ID: {
		    setState(279);
		    match(ID);
		}
		    break;
		case T__28: {
		    setState(280);
		    keyword();
		}
		    break;
		default:
		    throw new NoViableAltException(this);
		}
		setState(288);
		switch (_input.LA(1)) {
		case T__11: {
		    setState(283);
		    match(T__11);
		    setState(284);
		    condition(0);
		    setState(285);
		    match(T__9);
		}
		    break;
		case T__7: {
		    setState(287);
		    match(T__7);
		}
		    break;
		default:
		    throw new NoViableAltException(this);
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
	enterRule(_localctx, 38, RULE_refinement);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(293);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(292);
			    match(T__12);
			}
		    }
		    setState(295);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__12);
		setState(297);
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
	enterRule(_localctx, 40, RULE_refinementRule);
	try {
	    setState(309);
	    switch (_input.LA(1)) {
	    case T__34:
	    case T__28:
	    case INDICATOR_DELIM:
	    case TYPE:
	    case ID:
		enterOuterAlt(_localctx, 1); {
		setState(299);
		statement();
	    }
		break;
	    case T__25:
		enterOuterAlt(_localctx, 2); {
		setState(300);
		composition();
	    }
		break;
	    case T__13:
		enterOuterAlt(_localctx, 3); {
		setState(301);
		selection();
	    }
		break;
	    case T__0:
		enterOuterAlt(_localctx, 4); {
		setState(302);
		repetition();
	    }
		break;
	    case T__24:
		enterOuterAlt(_localctx, 5); {
		setState(303);
		returnS();
	    }
		break;
	    case T__21:
		enterOuterAlt(_localctx, 6); {
		setState(304);
		originalS();
	    }
		break;
	    case T__10:
		enterOuterAlt(_localctx, 7); {
		setState(305);
		skipS();
	    }
		break;
	    case T__15:
		enterOuterAlt(_localctx, 8); {
		setState(306);
		methodCallS();
	    }
		break;
	    case T__5:
		enterOuterAlt(_localctx, 9); {
		setState(307);
		block();
	    }
		break;
	    case T__30:
		enterOuterAlt(_localctx, 10); {
		setState(308);
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
	enterRule(_localctx, 42, RULE_statement);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(331);
		switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
		case 1: {
		    setState(311);
		    identifier();
		}
		    break;
		case 2: {
		    setState(312);
		    javaReturn();
		}
		    break;
		case 3: {
		    setState(313);
		    identifier();
		    setState(314);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case 4: {
		    setState(316);
		    identifier();
		    setState(317);
		    match(T__20);
		    setState(318);
		    condition(0);
		    setState(319);
		    match(T__1);
		}
		    break;
		case 5: {
		    setState(321);
		    identifier();
		    setState(322);
		    statement();
		}
		    break;
		case 6: {
		    setState(324);
		    identifier();
		    setState(325);
		    match(OP);
		    setState(326);
		    assigner();
		}
		    break;
		case 7: {
		    setState(328);
		    identifier();
		    setState(329);
		    match(OP);
		}
		    break;
		}
		setState(333);
		match(INDICATOR_DELIM);
		setState(335);
		switch (getInterpreter().adaptivePredict(_input, 29, _ctx)) {
		case 1: {
		    setState(334);
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
	enterRule(_localctx, 44, RULE_javaReturn);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(337);
		match(T__34);
		setState(338);
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
	public TerminalNode EMPTY_BRACKETS() {
	    return getToken(LostParser.EMPTY_BRACKETS, 0);
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

	public TerminalNode NEW() {
	    return getToken(LostParser.NEW, 0);
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
	enterRule(_localctx, 46, RULE_assigner);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(357);
		switch (getInterpreter().adaptivePredict(_input, 30, _ctx)) {
		case 1: {
		    setState(340);
		    identifier();
		}
		    break;
		case 2: {
		    setState(341);
		    match(T__20);
		    setState(342);
		    assigner();
		    setState(343);
		    match(T__1);
		}
		    break;
		case 3: {
		    setState(345);
		    identifier();
		    setState(346);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case 4: {
		    setState(348);
		    identifier();
		    setState(349);
		    assigner();
		}
		    break;
		case 5: {
		    setState(351);
		    match(NEW);
		    setState(352);
		    identifier();
		}
		    break;
		case 6: {
		    setState(353);
		    identifier();
		    setState(354);
		    match(OP);
		    setState(355);
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

	public ModContext mod() {
	    return getRuleContext(ModContext.class, 0);
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

	public TerminalNode INDICATOR_DELIM() {
	    return getToken(LostParser.INDICATOR_DELIM, 0);
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
	enterRule(_localctx, 48, RULE_composition);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(359);
		match(T__25);
		setState(360);
		match(T__20);
		setState(364);
		_la = _input.LA(1);
		if (_la == T__22) {
		    {
			setState(361);
			mod();
			setState(362);
			match(INDICATOR_DELIM);
		    }
		}

		setState(366);
		intm();
		setState(367);
		match(T__1);
		setState(368);
		match(NL);
		setState(369);
		refinement();
		setState(370);
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

	public ModContext mod() {
	    return getRuleContext(ModContext.class, 0);
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

	public TerminalNode INDICATOR_DELIM() {
	    return getToken(LostParser.INDICATOR_DELIM, 0);
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
	enterRule(_localctx, 50, RULE_selection);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(372);
		match(T__13);
		setState(373);
		match(T__20);
		setState(377);
		_la = _input.LA(1);
		if (_la == T__22) {
		    {
			setState(374);
			mod();
			setState(375);
			match(INDICATOR_DELIM);
		    }
		}

		setState(379);
		guards();
		setState(380);
		match(T__1);
		setState(381);
		match(NL);
		setState(383);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(382);
			    refinement();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(385);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 33, _ctx);
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

	public TerminalNode INDICATOR_DELIM(int i) {
	    return getToken(LostParser.INDICATOR_DELIM, i);
	}

	public GuardContext guard(int i) {
	    return getRuleContext(GuardContext.class, i);
	}

	public List<TerminalNode> INDICATOR_DELIM() {
	    return getTokens(LostParser.INDICATOR_DELIM);
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
	enterRule(_localctx, 52, RULE_guards);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(387);
		guard();
		setState(392);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == INDICATOR_DELIM) {
		    {
			{
			    setState(388);
			    match(INDICATOR_DELIM);
			    setState(389);
			    guard();
			}
		    }
		    setState(394);
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

	public ModContext mod() {
	    return getRuleContext(ModContext.class, 0);
	}

	public GuardContext guard() {
	    return getRuleContext(GuardContext.class, 0);
	}

	public TerminalNode INDICATOR_DELIM(int i) {
	    return getToken(LostParser.INDICATOR_DELIM, i);
	}

	public VarContext var() {
	    return getRuleContext(VarContext.class, 0);
	}

	public RefinementContext refinement() {
	    return getRuleContext(RefinementContext.class, 0);
	}

	public List<TerminalNode> INDICATOR_DELIM() {
	    return getTokens(LostParser.INDICATOR_DELIM);
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
	enterRule(_localctx, 54, RULE_repetition);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(395);
		match(T__0);
		setState(396);
		match(T__20);
		setState(400);
		_la = _input.LA(1);
		if (_la == T__22) {
		    {
			setState(397);
			mod();
			setState(398);
			match(INDICATOR_DELIM);
		    }
		}

		setState(402);
		inv();
		setState(403);
		match(INDICATOR_DELIM);
		setState(404);
		guard();
		setState(405);
		match(INDICATOR_DELIM);
		setState(406);
		var();
		setState(407);
		match(T__1);
		setState(408);
		match(NL);
		setState(409);
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
	enterRule(_localctx, 56, RULE_inv);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(411);
		match(T__14);
		setState(412);
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
	enterRule(_localctx, 58, RULE_guard);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(414);
		match(T__23);
		setState(415);
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
	enterRule(_localctx, 60, RULE_var);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(417);
		match(T__31);
		setState(418);
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
	enterRule(_localctx, 62, RULE_returnS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(420);
		match(T__24);
		setState(421);
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
	enterRule(_localctx, 64, RULE_originalS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(423);
		match(T__21);
		setState(424);
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
	enterRule(_localctx, 66, RULE_skipS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(426);
		match(T__10);
		setState(428);
		switch (getInterpreter().adaptivePredict(_input, 36, _ctx)) {
		case 1: {
		    setState(427);
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
	enterRule(_localctx, 68, RULE_methodCallS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(430);
		match(T__15);
		setState(431);
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

	public ModContext mod() {
	    return getRuleContext(ModContext.class, 0);
	}

	public TerminalNode INDICATOR_DELIM(int i) {
	    return getToken(LostParser.INDICATOR_DELIM, i);
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

	public List<TerminalNode> INDICATOR_DELIM() {
	    return getTokens(LostParser.INDICATOR_DELIM);
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
	enterRule(_localctx, 70, RULE_block);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(433);
		match(T__5);
		setState(434);
		match(T__20);
		setState(438);
		_la = _input.LA(1);
		if (_la == T__22) {
		    {
			setState(435);
			mod();
			setState(436);
			match(INDICATOR_DELIM);
		    }
		}

		setState(440);
		name();
		setState(441);
		match(INDICATOR_DELIM);
		setState(442);
		pre();
		setState(443);
		match(INDICATOR_DELIM);
		setState(444);
		post();
		setState(445);
		match(T__1);
		setState(446);
		match(NL);
		setState(447);
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
	enterRule(_localctx, 72, RULE_name);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(449);
		match(T__6);
		setState(450);
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

    public static class SignatureContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public MethodParameterContext methodParameter(int i) {
	    return getRuleContext(MethodParameterContext.class, i);
	}

	public TerminalNode EMPTY_BRACKETS() {
	    return getToken(LostParser.EMPTY_BRACKETS, 0);
	}

	public List<MethodParameterContext> methodParameter() {
	    return getRuleContexts(MethodParameterContext.class);
	}

	public TerminalNode VISIBILITY() {
	    return getToken(LostParser.VISIBILITY, 0);
	}

	public TerminalNode TYPE() {
	    return getToken(LostParser.TYPE, 0);
	}

	public TerminalNode BRACKETS() {
	    return getToken(LostParser.BRACKETS, 0);
	}

	public SignatureContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_signature;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterSignature(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitSignature(this);
	}
    }

    public final SignatureContext signature() throws RecognitionException {
	SignatureContext _localctx = new SignatureContext(_ctx, getState());
	enterRule(_localctx, 74, RULE_signature);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(452);
		match(T__19);
		setState(453);
		match(VISIBILITY);
		setState(459);
		switch (_input.LA(1)) {
		case T__29: {
		    setState(454);
		    match(T__29);
		}
		    break;
		case TYPE: {
		    setState(455);
		    match(TYPE);
		    setState(457);
		    _la = _input.LA(1);
		    if (_la == BRACKETS) {
			{
			    setState(456);
			    match(BRACKETS);
			}
		    }

		}
		    break;
		default:
		    throw new NoViableAltException(this);
		}
		setState(461);
		match(ID);
		setState(474);
		switch (_input.LA(1)) {
		case EMPTY_BRACKETS: {
		    setState(462);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case T__20: {
		    setState(463);
		    match(T__20);
		    setState(464);
		    methodParameter();
		    setState(469);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		    while (_la == T__17) {
			{
			    {
				setState(465);
				match(T__17);
				setState(466);
				methodParameter();
			    }
			}
			setState(471);
			_errHandler.sync(this);
			_la = _input.LA(1);
		    }
		    setState(472);
		    match(T__1);
		}
		    break;
		default:
		    throw new NoViableAltException(this);
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

    public static class MethodParameterContext extends ParserRuleContext {
	public TerminalNode ID() {
	    return getToken(LostParser.ID, 0);
	}

	public TerminalNode TYPE() {
	    return getToken(LostParser.TYPE, 0);
	}

	public TerminalNode BRACKETS() {
	    return getToken(LostParser.BRACKETS, 0);
	}

	public MethodParameterContext(ParserRuleContext parent, int invokingState) {
	    super(parent, invokingState);
	}

	@Override
	public int getRuleIndex() {
	    return RULE_methodParameter;
	}

	@Override
	public void enterRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).enterMethodParameter(this);
	}

	@Override
	public void exitRule(ParseTreeListener listener) {
	    if (listener instanceof LostListener)
		((LostListener) listener).exitMethodParameter(this);
	}
    }

    public final MethodParameterContext methodParameter() throws RecognitionException {
	MethodParameterContext _localctx = new MethodParameterContext(_ctx, getState());
	enterRule(_localctx, 76, RULE_methodParameter);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(476);
		match(TYPE);
		setState(478);
		_la = _input.LA(1);
		if (_la == BRACKETS) {
		    {
			setState(477);
			match(BRACKETS);
		    }
		}

		setState(480);
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
	enterRule(_localctx, 78, RULE_mlexpr);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(482);
		match(T__30);
		setState(484);
		_la = _input.LA(1);
		if (_la == NL) {
		    {
			setState(483);
			match(NL);
		    }
		}

		setState(494);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 45, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(487);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
				    {
					{
					    setState(486);
					    match(T__12);
					}
				    }
				    setState(489);
				    _errHandler.sync(this);
				    _la = _input.LA(1);
				} while (_la == T__12);
				setState(491);
				statement();
			    }
			}
		    }
		    setState(496);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 45, _ctx);
		}
		setState(498);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(497);
			    match(T__12);
			}
		    }
		    setState(500);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__12);
		setState(502);
		match(T__26);
		setState(504);
		switch (getInterpreter().adaptivePredict(_input, 47, _ctx)) {
		case 1: {
		    setState(503);
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
	case 15:
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

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\61\u01fd\4\2\t\2"
	    + "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
	    + "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\2\3"
	    + "\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\6\4^\n\4\r\4\16\4_\3\5\3\5\5\5d\n\5\3\6"
	    + "\7\6g\n\6\f\6\16\6j\13\6\3\6\3\6\3\6\7\6o\n\6\f\6\16\6r\13\6\3\6\7\6u"
	    + "\n\6\f\6\16\6x\13\6\3\6\3\6\3\6\7\6}\n\6\f\6\16\6\u0080\13\6\3\6\7\6\u0083"
	    + "\n\6\f\6\16\6\u0086\13\6\3\6\3\6\3\6\7\6\u008b\n\6\f\6\16\6\u008e\13\6"
	    + "\3\6\7\6\u0091\n\6\f\6\16\6\u0094\13\6\3\6\3\6\3\6\7\6\u0099\n\6\f\6\16"
	    + "\6\u009c\13\6\5\6\u009e\n\6\3\7\3\7\3\7\6\7\u00a3\n\7\r\7\16\7\u00a4\3"
	    + "\b\6\b\u00a8\n\b\r\b\16\b\u00a9\3\b\3\b\3\b\5\b\u00af\n\b\3\b\3\b\5\b"
	    + "\u00b3\n\b\3\t\3\t\3\t\6\t\u00b8\n\t\r\t\16\t\u00b9\3\t\3\t\3\t\6\t\u00bf"
	    + "\n\t\r\t\16\t\u00c0\3\n\3\n\3\n\6\n\u00c6\n\n\r\n\16\n\u00c7\3\13\6\13"
	    + "\u00cb\n\13\r\13\16\13\u00cc\3\13\3\13\3\13\3\13\3\13\5\13\u00d4\n\13"
	    + "\3\f\3\f\3\f\3\f\3\f\5\f\u00db\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r"
	    + "\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21"
	    + "\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"
	    + "\5\21\u0102\n\21\3\21\3\21\3\21\7\21\u0107\n\21\f\21\16\21\u010a\13\21"
	    + "\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"
	    + "\3\24\3\24\5\24\u011c\n\24\3\24\3\24\3\24\3\24\3\24\5\24\u0123\n\24\5"
	    + "\24\u0125\n\24\3\25\6\25\u0128\n\25\r\25\16\25\u0129\3\25\3\25\3\26\3"
	    + "\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0138\n\26\3\27\3\27"
	    + "\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"
	    + "\3\27\3\27\3\27\3\27\5\27\u014e\n\27\3\27\3\27\5\27\u0152\n\27\3\30\3"
	    + "\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"
	    + "\31\3\31\3\31\3\31\3\31\5\31\u0168\n\31\3\32\3\32\3\32\3\32\3\32\5\32"
	    + "\u016f\n\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\5\33"
	    + "\u017c\n\33\3\33\3\33\3\33\3\33\6\33\u0182\n\33\r\33\16\33\u0183\3\34"
	    + "\3\34\3\34\7\34\u0189\n\34\f\34\16\34\u018c\13\34\3\35\3\35\3\35\3\35"
	    + "\3\35\5\35\u0193\n\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36"
	    + "\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\5#\u01af"
	    + "\n#\3$\3$\3$\3%\3%\3%\3%\3%\5%\u01b9\n%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3&"
	    + "\3&\3&\3\'\3\'\3\'\3\'\3\'\5\'\u01cc\n\'\5\'\u01ce\n\'\3\'\3\'\3\'\3\'"
	    + "\3\'\3\'\7\'\u01d6\n\'\f\'\16\'\u01d9\13\'\3\'\3\'\5\'\u01dd\n\'\3(\3"
	    + "(\5(\u01e1\n(\3(\3(\3)\3)\5)\u01e7\n)\3)\6)\u01ea\n)\r)\16)\u01eb\3)\7"
	    + ")\u01ef\n)\f)\16)\u01f2\13)\3)\6)\u01f5\n)\r)\16)\u01f6\3)\3)\5)\u01fb"
	    + "\n)\3)\2\3 *\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64"
	    + "\668:<>@BDFHJLNP\2\3\4\2\35\35++\u021e\2R\3\2\2\2\4U\3\2\2\2\6W\3\2\2"
	    + "\2\bc\3\2\2\2\n\u009d\3\2\2\2\f\u009f\3\2\2\2\16\u00a7\3\2\2\2\20\u00b4"
	    + "\3\2\2\2\22\u00c2\3\2\2\2\24\u00ca\3\2\2\2\26\u00d5\3\2\2\2\30\u00e3\3"
	    + "\2\2\2\32\u00e6\3\2\2\2\34\u00e9\3\2\2\2\36\u00ec\3\2\2\2 \u0101\3\2\2"
	    + "\2\"\u010b\3\2\2\2$\u0110\3\2\2\2&\u0124\3\2\2\2(\u0127\3\2\2\2*\u0137"
	    + "\3\2\2\2,\u014d\3\2\2\2.\u0153\3\2\2\2\60\u0167\3\2\2\2\62\u0169\3\2\2"
	    + "\2\64\u0176\3\2\2\2\66\u0185\3\2\2\28\u018d\3\2\2\2:\u019d\3\2\2\2<\u01a0"
	    + "\3\2\2\2>\u01a3\3\2\2\2@\u01a6\3\2\2\2B\u01a9\3\2\2\2D\u01ac\3\2\2\2F"
	    + "\u01b0\3\2\2\2H\u01b3\3\2\2\2J\u01c3\3\2\2\2L\u01c6\3\2\2\2N\u01de\3\2"
	    + "\2\2P\u01e4\3\2\2\2RS\5\4\3\2ST\7\2\2\3T\3\3\2\2\2UV\5\6\4\2V\5\3\2\2"
	    + "\2WX\7!\2\2XY\7\21\2\2YZ\5\b\5\2Z[\7$\2\2[]\7\'\2\2\\^\5\n\6\2]\\\3\2"
	    + "\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\7\3\2\2\2ad\5J&\2bd\5L\'\2ca\3\2\2"
	    + "\2cb\3\2\2\2d\t\3\2\2\2eg\7\'\2\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2"
	    + "\2\2ik\3\2\2\2jh\3\2\2\2kl\7\31\2\2lp\5\26\f\2mo\7\'\2\2nm\3\2\2\2or\3"
	    + "\2\2\2pn\3\2\2\2pq\3\2\2\2q\u009e\3\2\2\2rp\3\2\2\2su\7\'\2\2ts\3\2\2"
	    + "\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\7\31\2\2z~\5\f"
	    + "\7\2{}\7\'\2\2|{\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u009e"
	    + "\3\2\2\2\u0080~\3\2\2\2\u0081\u0083\7\'\2\2\u0082\u0081\3\2\2\2\u0083"
	    + "\u0086\3\2\2\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0087\3\2"
	    + "\2\2\u0086\u0084\3\2\2\2\u0087\u0088\7\31\2\2\u0088\u008c\5\20\t\2\u0089"
	    + "\u008b\7\'\2\2\u008a\u0089\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2"
	    + "\2\2\u008c\u008d\3\2\2\2\u008d\u009e\3\2\2\2\u008e\u008c\3\2\2\2\u008f"
	    + "\u0091\7\'\2\2\u0090\u008f\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2"
	    + "\2\2\u0092\u0093\3\2\2\2\u0093\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095"
	    + "\u0096\7\31\2\2\u0096\u009a\5\22\n\2\u0097\u0099\7\'\2\2\u0098\u0097\3"
	    + "\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b"
	    + "\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009dh\3\2\2\2\u009dv\3\2\2\2\u009d"
	    + "\u0084\3\2\2\2\u009d\u0092\3\2\2\2\u009e\13\3\2\2\2\u009f\u00a0\7\25\2"
	    + "\2\u00a0\u00a2\7\'\2\2\u00a1\u00a3\5\16\b\2\u00a2\u00a1\3\2\2\2\u00a3"
	    + "\u00a4\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\r\3\2\2\2"
	    + "\u00a6\u00a8\7\31\2\2\u00a7\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00a7"
	    + "\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\7)\2\2\u00ac"
	    + "\u00ae\7+\2\2\u00ad\u00af\7,\2\2\u00ae\u00ad\3\2\2\2\u00ae\u00af\3\2\2"
	    + "\2\u00af\u00b0\3\2\2\2\u00b0\u00b2\7\61\2\2\u00b1\u00b3\7\'\2\2\u00b2"
	    + "\u00b1\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\17\3\2\2\2\u00b4\u00b5\7#\2\2"
	    + "\u00b5\u00be\7\'\2\2\u00b6\u00b8\7\31\2\2\u00b7\u00b6\3\2\2\2\u00b8\u00b9"
	    + "\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb"
	    + "\u00bc\5 \21\2\u00bc\u00bd\7\'\2\2\u00bd\u00bf\3\2\2\2\u00be\u00b7\3\2"
	    + "\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1"
	    + "\21\3\2\2\2\u00c2\u00c3\7\n\2\2\u00c3\u00c5\7\'\2\2\u00c4\u00c6\5\24\13"
	    + "\2\u00c5\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8"
	    + "\3\2\2\2\u00c8\23\3\2\2\2\u00c9\u00cb\7\31\2\2\u00ca\u00c9\3\2\2\2\u00cb"
	    + "\u00cc\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00ce\3\2"
	    + "\2\2\u00ce\u00cf\t\2\2\2\u00cf\u00d0\7\61\2\2\u00d0\u00d1\7/\2\2\u00d1"
	    + "\u00d3\5 \21\2\u00d2\u00d4\7\'\2\2\u00d3\u00d2\3\2\2\2\u00d3\u00d4\3\2"
	    + "\2\2\u00d4\25\3\2\2\2\u00d5\u00d6\7\"\2\2\u00d6\u00da\7\21\2\2\u00d7\u00d8"
	    + "\5\34\17\2\u00d8\u00d9\7(\2\2\u00d9\u00db\3\2\2\2\u00da\u00d7\3\2\2\2"
	    + "\u00da\u00db\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\5\30\r\2\u00dd\u00de"
	    + "\7(\2\2\u00de\u00df\5\32\16\2\u00df\u00e0\7$\2\2\u00e0\u00e1\7\'\2\2\u00e1"
	    + "\u00e2\5(\25\2\u00e2\27\3\2\2\2\u00e3\u00e4\7\23\2\2\u00e4\u00e5\5 \21"
	    + "\2\u00e5\31\3\2\2\2\u00e6\u00e7\7\4\2\2\u00e7\u00e8\5 \21\2\u00e8\33\3"
	    + "\2\2\2\u00e9\u00ea\7\17\2\2\u00ea\u00eb\5 \21\2\u00eb\35\3\2\2\2\u00ec"
	    + "\u00ed\7\5\2\2\u00ed\u00ee\5 \21\2\u00ee\37\3\2\2\2\u00ef\u00f0\b\21\1"
	    + "\2\u00f0\u00f1\5&\24\2\u00f1\u00f2\5 \21\5\u00f2\u0102\3\2\2\2\u00f3\u00f4"
	    + "\7/\2\2\u00f4\u0102\5 \21\4\u00f5\u00f6\7\21\2\2\u00f6\u00f7\5 \21\2\u00f7"
	    + "\u00f8\7$\2\2\u00f8\u0102\3\2\2\2\u00f9\u00fa\7\21\2\2\u00fa\u00fb\5 "
	    + "\21\2\u00fb\u00fc\7$\2\2\u00fc\u00fd\7/\2\2\u00fd\u00fe\5 \21\2\u00fe"
	    + "\u0102\3\2\2\2\u00ff\u0102\5\"\22\2\u0100\u0102\5&\24\2\u0101\u00ef\3"
	    + "\2\2\2\u0101\u00f3\3\2\2\2\u0101\u00f5\3\2\2\2\u0101\u00f9\3\2\2\2\u0101"
	    + "\u00ff\3\2\2\2\u0101\u0100\3\2\2\2\u0102\u0108\3\2\2\2\u0103\u0104\f\3"
	    + "\2\2\u0104\u0105\7\24\2\2\u0105\u0107\5 \21\4\u0106\u0103\3\2\2\2\u0107"
	    + "\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0109\3\2\2\2\u0109!\3\2\2\2"
	    + "\u010a\u0108\3\2\2\2\u010b\u010c\7\60\2\2\u010c\u010d\7\61\2\2\u010d\u010e"
	    + "\7(\2\2\u010e\u010f\5 \21\2\u010f#\3\2\2\2\u0110\u0111\7\t\2\2\u0111\u0112"
	    + "\7\61\2\2\u0112\u0113\7\21\2\2\u0113\u0114\5&\24\2\u0114\u0115\7$\2\2"
	    + "\u0115%\3\2\2\2\u0116\u0125\7\61\2\2\u0117\u0125\5$\23\2\u0118\u011c\7"
	    + "+\2\2\u0119\u011c\7\61\2\2\u011a\u011c\5$\23\2\u011b\u0118\3\2\2\2\u011b"
	    + "\u0119\3\2\2\2\u011b\u011a\3\2\2\2\u011c\u0122\3\2\2\2\u011d\u011e\7\32"
	    + "\2\2\u011e\u011f\5 \21\2\u011f\u0120\7\34\2\2\u0120\u0123\3\2\2\2\u0121"
	    + "\u0123\7\36\2\2\u0122\u011d\3\2\2\2\u0122\u0121\3\2\2\2\u0123\u0125\3"
	    + "\2\2\2\u0124\u0116\3\2\2\2\u0124\u0117\3\2\2\2\u0124\u011b\3\2\2\2\u0125"
	    + "\'\3\2\2\2\u0126\u0128\7\31\2\2\u0127\u0126\3\2\2\2\u0128\u0129\3\2\2"
	    + "\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b\u012c"
	    + "\5*\26\2\u012c)\3\2\2\2\u012d\u0138\5,\27\2\u012e\u0138\5\62\32\2\u012f"
	    + "\u0138\5\64\33\2\u0130\u0138\58\35\2\u0131\u0138\5@!\2\u0132\u0138\5B"
	    + "\"\2\u0133\u0138\5D#\2\u0134\u0138\5F$\2\u0135\u0138\5H%\2\u0136\u0138"
	    + "\5P)\2\u0137\u012d\3\2\2\2\u0137\u012e\3\2\2\2\u0137\u012f\3\2\2\2\u0137"
	    + "\u0130\3\2\2\2\u0137\u0131\3\2\2\2\u0137\u0132\3\2\2\2\u0137\u0133\3\2"
	    + "\2\2\u0137\u0134\3\2\2\2\u0137\u0135\3\2\2\2\u0137\u0136\3\2\2\2\u0138"
	    + "+\3\2\2\2\u0139\u014e\5&\24\2\u013a\u014e\5.\30\2\u013b\u013c\5&\24\2"
	    + "\u013c\u013d\7-\2\2\u013d\u014e\3\2\2\2\u013e\u013f\5&\24\2\u013f\u0140"
	    + "\7\21\2\2\u0140\u0141\5 \21\2\u0141\u0142\7$\2\2\u0142\u014e\3\2\2\2\u0143"
	    + "\u0144\5&\24\2\u0144\u0145\5,\27\2\u0145\u014e\3\2\2\2\u0146\u0147\5&"
	    + "\24\2\u0147\u0148\7/\2\2\u0148\u0149\5\60\31\2\u0149\u014e\3\2\2\2\u014a"
	    + "\u014b\5&\24\2\u014b\u014c\7/\2\2\u014c\u014e\3\2\2\2\u014d\u0139\3\2"
	    + "\2\2\u014d\u013a\3\2\2\2\u014d\u013b\3\2\2\2\u014d\u013e\3\2\2\2\u014d"
	    + "\u0143\3\2\2\2\u014d\u0146\3\2\2\2\u014d\u014a\3\2\2\2\u014d\u014e\3\2"
	    + "\2\2\u014e\u014f\3\2\2\2\u014f\u0151\7(\2\2\u0150\u0152\7\'\2\2\u0151"
	    + "\u0150\3\2\2\2\u0151\u0152\3\2\2\2\u0152-\3\2\2\2\u0153\u0154\7\3\2\2"
	    + "\u0154\u0155\5\60\31\2\u0155/\3\2\2\2\u0156\u0168\5&\24\2\u0157\u0158"
	    + "\7\21\2\2\u0158\u0159\5\60\31\2\u0159\u015a\7$\2\2\u015a\u0168\3\2\2\2"
	    + "\u015b\u015c\5&\24\2\u015c\u015d\7-\2\2\u015d\u0168\3\2\2\2\u015e\u015f"
	    + "\5&\24\2\u015f\u0160\5\60\31\2\u0160\u0168\3\2\2\2\u0161\u0162\7.\2\2"
	    + "\u0162\u0168\5&\24\2\u0163\u0164\5&\24\2\u0164\u0165\7/\2\2\u0165\u0166"
	    + "\5\60\31\2\u0166\u0168\3\2\2\2\u0167\u0156\3\2\2\2\u0167\u0157\3\2\2\2"
	    + "\u0167\u015b\3\2\2\2\u0167\u015e\3\2\2\2\u0167\u0161\3\2\2\2\u0167\u0163"
	    + "\3\2\2\2\u0168\61\3\2\2\2\u0169\u016a\7\f\2\2\u016a\u016e\7\21\2\2\u016b"
	    + "\u016c\5\34\17\2\u016c\u016d\7(\2\2\u016d\u016f\3\2\2\2\u016e\u016b\3"
	    + "\2\2\2\u016e\u016f\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0171\5\36\20\2\u0171"
	    + "\u0172\7$\2\2\u0172\u0173\7\'\2\2\u0173\u0174\5(\25\2\u0174\u0175\5(\25"
	    + "\2\u0175\63\3\2\2\2\u0176\u0177\7\30\2\2\u0177\u017b\7\21\2\2\u0178\u0179"
	    + "\5\34\17\2\u0179\u017a\7(\2\2\u017a\u017c\3\2\2\2\u017b\u0178\3\2\2\2"
	    + "\u017b\u017c\3\2\2\2\u017c\u017d\3\2\2\2\u017d\u017e\5\66\34\2\u017e\u017f"
	    + "\7$\2\2\u017f\u0181\7\'\2\2\u0180\u0182\5(\25\2\u0181\u0180\3\2\2\2\u0182"
	    + "\u0183\3\2\2\2\u0183\u0181\3\2\2\2\u0183\u0184\3\2\2\2\u0184\65\3\2\2"
	    + "\2\u0185\u018a\5<\37\2\u0186\u0187\7(\2\2\u0187\u0189\5<\37\2\u0188\u0186"
	    + "\3\2\2\2\u0189\u018c\3\2\2\2\u018a\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018b"
	    + "\67\3\2\2\2\u018c\u018a\3\2\2\2\u018d\u018e\7%\2\2\u018e\u0192\7\21\2"
	    + "\2\u018f\u0190\5\34\17\2\u0190\u0191\7(\2\2\u0191\u0193\3\2\2\2\u0192"
	    + "\u018f\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0195\5:"
	    + "\36\2\u0195\u0196\7(\2\2\u0196\u0197\5<\37\2\u0197\u0198\7(\2\2\u0198"
	    + "\u0199\5> \2\u0199\u019a\7$\2\2\u019a\u019b\7\'\2\2\u019b\u019c\5(\25"
	    + "\2\u019c9\3\2\2\2\u019d\u019e\7\27\2\2\u019e\u019f\5 \21\2\u019f;\3\2"
	    + "\2\2\u01a0\u01a1\7\16\2\2\u01a1\u01a2\5 \21\2\u01a2=\3\2\2\2\u01a3\u01a4"
	    + "\7\6\2\2\u01a4\u01a5\5 \21\2\u01a5?\3\2\2\2\u01a6\u01a7\7\r\2\2\u01a7"
	    + "\u01a8\5,\27\2\u01a8A\3\2\2\2\u01a9\u01aa\7\20\2\2\u01aa\u01ab\5,\27\2"
	    + "\u01abC\3\2\2\2\u01ac\u01ae\7\33\2\2\u01ad\u01af\7\'\2\2\u01ae\u01ad\3"
	    + "\2\2\2\u01ae\u01af\3\2\2\2\u01afE\3\2\2\2\u01b0\u01b1\7\26\2\2\u01b1\u01b2"
	    + "\5,\27\2\u01b2G\3\2\2\2\u01b3\u01b4\7 \2\2\u01b4\u01b8\7\21\2\2\u01b5"
	    + "\u01b6\5\34\17\2\u01b6\u01b7\7(\2\2\u01b7\u01b9\3\2\2\2\u01b8\u01b5\3"
	    + "\2\2\2\u01b8\u01b9\3\2\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb\5J&\2\u01bb"
	    + "\u01bc\7(\2\2\u01bc\u01bd\5\30\r\2\u01bd\u01be\7(\2\2\u01be\u01bf\5\32"
	    + "\16\2\u01bf\u01c0\7$\2\2\u01c0\u01c1\7\'\2\2\u01c1\u01c2\5P)\2\u01c2I"
	    + "\3\2\2\2\u01c3\u01c4\7\37\2\2\u01c4\u01c5\7\61\2\2\u01c5K\3\2\2\2\u01c6"
	    + "\u01c7\7\22\2\2\u01c7\u01cd\7*\2\2\u01c8\u01ce\7\b\2\2\u01c9\u01cb\7+"
	    + "\2\2\u01ca\u01cc\7,\2\2\u01cb\u01ca\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc"
	    + "\u01ce\3\2\2\2\u01cd\u01c8\3\2\2\2\u01cd\u01c9\3\2\2\2\u01ce\u01cf\3\2"
	    + "\2\2\u01cf\u01dc\7\61\2\2\u01d0\u01dd\7-\2\2\u01d1\u01d2\7\21\2\2\u01d2"
	    + "\u01d7\5N(\2\u01d3\u01d4\7\24\2\2\u01d4\u01d6\5N(\2\u01d5\u01d3\3\2\2"
	    + "\2\u01d6\u01d9\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d7\u01d8\3\2\2\2\u01d8\u01da"
	    + "\3\2\2\2\u01d9\u01d7\3\2\2\2\u01da\u01db\7$\2\2\u01db\u01dd\3\2\2\2\u01dc"
	    + "\u01d0\3\2\2\2\u01dc\u01d1\3\2\2\2\u01ddM\3\2\2\2\u01de\u01e0\7+\2\2\u01df"
	    + "\u01e1\7,\2\2\u01e0\u01df\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e2\3\2"
	    + "\2\2\u01e2\u01e3\7\61\2\2\u01e3O\3\2\2\2\u01e4\u01e6\7\7\2\2\u01e5\u01e7"
	    + "\7\'\2\2\u01e6\u01e5\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7\u01f0\3\2\2\2\u01e8"
	    + "\u01ea\7\31\2\2\u01e9\u01e8\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb\u01e9\3"
	    + "\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ed\3\2\2\2\u01ed\u01ef\5,\27\2\u01ee"
	    + "\u01e9\3\2\2\2\u01ef\u01f2\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2"
	    + "\2\2\u01f1\u01f4\3\2\2\2\u01f2\u01f0\3\2\2\2\u01f3\u01f5\7\31\2\2\u01f4"
	    + "\u01f3\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f4\3\2\2\2\u01f6\u01f7\3\2"
	    + "\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01fa\7\13\2\2\u01f9\u01fb\7\'\2\2\u01fa"
	    + "\u01f9\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fbQ\3\2\2\2\62_chpv~\u0084\u008c"
	    + "\u0092\u009a\u009d\u00a4\u00a9\u00ae\u00b2\u00b9\u00c0\u00c7\u00cc\u00d3"
	    + "\u00da\u0101\u0108\u011b\u0122\u0124\u0129\u0137\u014d\u0151\u0167\u016e"
	    + "\u017b\u0183\u018a\u0192\u01ae\u01b8\u01cb\u01cd\u01d7\u01dc\u01e0\u01e6" + "\u01eb\u01f0\u01f6\u01fa";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}