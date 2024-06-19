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
    public static final int T__32 = 1, T__31 = 2, T__30 = 3, T__29 = 4, T__28 = 5, T__27 = 6, T__26 = 7, T__25 = 8,
	    T__24 = 9, T__23 = 10, T__22 = 11, T__21 = 12, T__20 = 13, T__19 = 14, T__18 = 15, T__17 = 16, T__16 = 17,
	    T__15 = 18, T__14 = 19, T__13 = 20, T__12 = 21, T__11 = 22, T__10 = 23, T__9 = 24, T__8 = 25, T__7 = 26,
	    T__6 = 27, T__5 = 28, T__4 = 29, T__3 = 30, T__2 = 31, T__1 = 32, T__0 = 33, WS = 34, NL = 35, KIND = 36,
	    VISIBILITY = 37, TYPE = 38, BRACKETS = 39, EMPTY_BRACKETS = 40, NEW = 41, OP = 42, QT = 43, ID = 44;
    public static final String[] tokenNames = { "<INVALID>", "'return'", "'post:'", "'intm:'", "'var:'", "'{'",
	    "'void'", "'\\'", "';'", "'Renaming'", "'}'", "'C'", "'R:'", "'guard:'", "'O:'", "'('", "'sig:'", "'pre:'",
	    "','", "'Vars'", "'M:'", "'inv:'", "'S'", "'\t'", "'['", "'skip'", "']'", "'name:'", "'B'", "'D'", "'F'",
	    "'GlobalConditions'", "')'", "'L'", "WS", "'\n'", "KIND", "VISIBILITY", "TYPE", "BRACKETS", "'()'",
	    "'new '", "OP", "QT", "ID" };
    public static final int RULE_program = 0, RULE_root = 1, RULE_diagram = 2, RULE_diagramParam = 3,
	    RULE_initializer = 4, RULE_vars = 5, RULE_variable = 6, RULE_globalConditions = 7, RULE_renaming = 8,
	    RULE_renamer = 9, RULE_formula = 10, RULE_pre = 11, RULE_post = 12, RULE_intm = 13, RULE_condition = 14,
	    RULE_quantor = 15, RULE_keyword = 16, RULE_identifier = 17, RULE_refinement = 18, RULE_refinementRule = 19,
	    RULE_statement = 20, RULE_javaReturn = 21, RULE_assigner = 22, RULE_composition = 23, RULE_selection = 24,
	    RULE_guards = 25, RULE_repetition = 26, RULE_inv = 27, RULE_guard = 28, RULE_var = 29, RULE_returnS = 30,
	    RULE_originalS = 31, RULE_skipS = 32, RULE_methodCallS = 33, RULE_block = 34, RULE_name = 35,
	    RULE_signature = 36, RULE_methodParameter = 37, RULE_mlexpr = 38;
    public static final String[] ruleNames = { "program", "root", "diagram", "diagramParam", "initializer", "vars",
	    "variable", "globalConditions", "renaming", "renamer", "formula", "pre", "post", "intm", "condition",
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
		setState(78);
		root();
		setState(79);
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
		setState(81);
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
		setState(83);
		match(T__4);
		setState(84);
		match(T__18);
		setState(85);
		diagramParam();
		setState(86);
		match(T__1);
		setState(87);
		match(NL);
		setState(89);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(88);
			    initializer();
			}
		    }
		    setState(91);
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
		setState(95);
		switch (_input.LA(1)) {
		case T__6: {
		    setState(93);
		    name();
		}
		    break;
		case T__17: {
		    setState(94);
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
	    setState(153);
	    switch (getInterpreter().adaptivePredict(_input, 10, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
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
		match(T__10);
		setState(104);
		formula();
		setState(108);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(105);
				match(NL);
			    }
			}
		    }
		    setState(110);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
		}
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(114);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(111);
			    match(NL);
			}
		    }
		    setState(116);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(117);
		match(T__10);
		setState(118);
		vars();
		setState(122);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(119);
				match(NL);
			    }
			}
		    }
		    setState(124);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 5, _ctx);
		}
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(128);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(125);
			    match(NL);
			}
		    }
		    setState(130);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(131);
		match(T__10);
		setState(132);
		globalConditions();
		setState(136);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(133);
				match(NL);
			    }
			}
		    }
		    setState(138);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
		}
	    }
		break;
	    case 4:
		enterOuterAlt(_localctx, 4); {
		setState(142);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == NL) {
		    {
			{
			    setState(139);
			    match(NL);
			}
		    }
		    setState(144);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		}
		setState(145);
		match(T__10);
		setState(146);
		renaming();
		setState(150);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 9, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(147);
				match(NL);
			    }
			}
		    }
		    setState(152);
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
		setState(155);
		match(T__14);
		setState(156);
		match(NL);
		setState(158);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(157);
			    variable();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(160);
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
		setState(163);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(162);
			    match(T__10);
			}
		    }
		    setState(165);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(167);
		match(KIND);
		setState(168);
		match(TYPE);
		setState(170);
		_la = _input.LA(1);
		if (_la == BRACKETS) {
		    {
			setState(169);
			match(BRACKETS);
		    }
		}

		setState(172);
		match(ID);
		setState(174);
		switch (getInterpreter().adaptivePredict(_input, 14, _ctx)) {
		case 1: {
		    setState(173);
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
		setState(176);
		match(T__2);
		setState(177);
		match(NL);
		setState(186);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(179);
			    _errHandler.sync(this);
			    _la = _input.LA(1);
			    do {
				{
				    {
					setState(178);
					match(T__10);
				    }
				}
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
			    } while (_la == T__10);
			    setState(183);
			    condition(0);
			    setState(184);
			    match(NL);
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(188);
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
		setState(190);
		match(T__24);
		setState(191);
		match(NL);
		setState(193);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(192);
			    renamer();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(195);
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
		setState(198);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(197);
			    match(T__10);
			}
		    }
		    setState(200);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(202);
		match(ID);
		setState(203);
		match(OP);
		setState(204);
		condition(0);
		setState(206);
		switch (getInterpreter().adaptivePredict(_input, 19, _ctx)) {
		case 1: {
		    setState(205);
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
	enterRule(_localctx, 20, RULE_formula);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(208);
		match(T__3);
		setState(209);
		match(T__18);
		setState(210);
		pre();
		setState(211);
		match(T__15);
		setState(212);
		post();
		setState(213);
		match(T__1);
		setState(214);
		match(NL);
		setState(215);
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
		setState(217);
		match(T__16);
		setState(218);
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
		setState(220);
		match(T__31);
		setState(221);
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
	enterRule(_localctx, 26, RULE_intm);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(223);
		match(T__30);
		setState(224);
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
	int _startState = 28;
	enterRecursionRule(_localctx, 28, RULE_condition, _p);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(244);
		switch (getInterpreter().adaptivePredict(_input, 20, _ctx)) {
		case 1: {
		    setState(227);
		    identifier();
		    setState(228);
		    condition(3);
		}
		    break;
		case 2: {
		    setState(230);
		    match(OP);
		    setState(231);
		    condition(2);
		}
		    break;
		case 3: {
		    setState(232);
		    match(T__18);
		    setState(233);
		    condition(0);
		    setState(234);
		    match(T__1);
		}
		    break;
		case 4: {
		    setState(236);
		    match(T__18);
		    setState(237);
		    condition(0);
		    setState(238);
		    match(T__1);
		    setState(239);
		    match(OP);
		    setState(240);
		    condition(0);
		}
		    break;
		case 5: {
		    setState(242);
		    quantor();
		}
		    break;
		case 6: {
		    setState(243);
		    identifier();
		}
		    break;
		}
		_ctx.stop = _input.LT(-1);
		setState(251);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			if (_parseListeners != null)
			    triggerExitRuleEvent();
			_prevctx = _localctx;
			{
			    {
				_localctx = new ConditionContext(_parentctx, _parentState);
				pushNewRecursionContext(_localctx, _startState, RULE_condition);
				setState(246);
				if (!(precpred(_ctx, 1)))
				    throw new FailedPredicateException(this, "precpred(_ctx, 1)");
				setState(247);
				match(T__15);
				setState(248);
				condition(2);
			    }
			}
		    }
		    setState(253);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 21, _ctx);
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
	enterRule(_localctx, 30, RULE_quantor);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(254);
		match(QT);
		setState(255);
		match(ID);
		setState(256);
		match(T__25);
		setState(257);
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
	enterRule(_localctx, 32, RULE_keyword);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(259);
		match(T__26);
		setState(260);
		match(ID);
		setState(261);
		match(T__18);
		setState(262);
		identifier();
		setState(263);
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
	enterRule(_localctx, 34, RULE_identifier);
	try {
	    setState(276);
	    switch (getInterpreter().adaptivePredict(_input, 23, _ctx)) {
	    case 1:
		enterOuterAlt(_localctx, 1); {
		setState(265);
		match(ID);
	    }
		break;
	    case 2:
		enterOuterAlt(_localctx, 2); {
		setState(266);
		keyword();
	    }
		break;
	    case 3:
		enterOuterAlt(_localctx, 3); {
		setState(270);
		switch (_input.LA(1)) {
		case TYPE: {
		    setState(267);
		    match(TYPE);
		}
		    break;
		case ID: {
		    setState(268);
		    match(ID);
		}
		    break;
		case T__26: {
		    setState(269);
		    keyword();
		}
		    break;
		default:
		    throw new NoViableAltException(this);
		}
		setState(272);
		match(T__9);
		setState(273);
		condition(0);
		setState(274);
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
	enterRule(_localctx, 36, RULE_refinement);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(279);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(278);
			    match(T__10);
			}
		    }
		    setState(281);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(283);
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
	enterRule(_localctx, 38, RULE_refinementRule);
	try {
	    setState(295);
	    switch (_input.LA(1)) {
	    case T__32:
	    case T__26:
	    case T__25:
	    case TYPE:
	    case ID:
		enterOuterAlt(_localctx, 1); {
		setState(285);
		statement();
	    }
		break;
	    case T__22:
		enterOuterAlt(_localctx, 2); {
		setState(286);
		composition();
	    }
		break;
	    case T__11:
		enterOuterAlt(_localctx, 3); {
		setState(287);
		selection();
	    }
		break;
	    case T__0:
		enterOuterAlt(_localctx, 4); {
		setState(288);
		repetition();
	    }
		break;
	    case T__21:
		enterOuterAlt(_localctx, 5); {
		setState(289);
		returnS();
	    }
		break;
	    case T__19:
		enterOuterAlt(_localctx, 6); {
		setState(290);
		originalS();
	    }
		break;
	    case T__8:
		enterOuterAlt(_localctx, 7); {
		setState(291);
		skipS();
	    }
		break;
	    case T__13:
		enterOuterAlt(_localctx, 8); {
		setState(292);
		methodCallS();
	    }
		break;
	    case T__5:
		enterOuterAlt(_localctx, 9); {
		setState(293);
		block();
	    }
		break;
	    case T__28:
		enterOuterAlt(_localctx, 10); {
		setState(294);
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
	enterRule(_localctx, 40, RULE_statement);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(317);
		switch (getInterpreter().adaptivePredict(_input, 26, _ctx)) {
		case 1: {
		    setState(297);
		    identifier();
		}
		    break;
		case 2: {
		    setState(298);
		    javaReturn();
		}
		    break;
		case 3: {
		    setState(299);
		    identifier();
		    setState(300);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case 4: {
		    setState(302);
		    identifier();
		    setState(303);
		    match(T__18);
		    setState(304);
		    condition(0);
		    setState(305);
		    match(T__1);
		}
		    break;
		case 5: {
		    setState(307);
		    identifier();
		    setState(308);
		    statement();
		}
		    break;
		case 6: {
		    setState(310);
		    identifier();
		    setState(311);
		    match(OP);
		    setState(312);
		    assigner();
		}
		    break;
		case 7: {
		    setState(314);
		    identifier();
		    setState(315);
		    match(OP);
		}
		    break;
		}
		setState(319);
		match(T__25);
		setState(321);
		switch (getInterpreter().adaptivePredict(_input, 27, _ctx)) {
		case 1: {
		    setState(320);
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
	enterRule(_localctx, 42, RULE_javaReturn);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(323);
		match(T__32);
		setState(324);
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
	enterRule(_localctx, 44, RULE_assigner);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(343);
		switch (getInterpreter().adaptivePredict(_input, 28, _ctx)) {
		case 1: {
		    setState(326);
		    identifier();
		}
		    break;
		case 2: {
		    setState(327);
		    match(T__18);
		    setState(328);
		    assigner();
		    setState(329);
		    match(T__1);
		}
		    break;
		case 3: {
		    setState(331);
		    identifier();
		    setState(332);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case 4: {
		    setState(334);
		    identifier();
		    setState(335);
		    assigner();
		}
		    break;
		case 5: {
		    setState(337);
		    match(NEW);
		    setState(338);
		    identifier();
		}
		    break;
		case 6: {
		    setState(339);
		    identifier();
		    setState(340);
		    match(OP);
		    setState(341);
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
	enterRule(_localctx, 46, RULE_composition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(345);
		match(T__22);
		setState(346);
		match(T__18);
		setState(347);
		intm();
		setState(348);
		match(T__1);
		setState(349);
		match(NL);
		setState(350);
		refinement();
		setState(351);
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
	enterRule(_localctx, 48, RULE_selection);
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(353);
		match(T__11);
		setState(354);
		match(T__18);
		setState(355);
		guards();
		setState(356);
		match(T__1);
		setState(357);
		match(NL);
		setState(359);
		_errHandler.sync(this);
		_alt = 1;
		do {
		    switch (_alt) {
		    case 1: {
			{
			    setState(358);
			    refinement();
			}
		    }
			break;
		    default:
			throw new NoViableAltException(this);
		    }
		    setState(361);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 29, _ctx);
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
	enterRule(_localctx, 50, RULE_guards);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(363);
		guard();
		setState(368);
		_errHandler.sync(this);
		_la = _input.LA(1);
		while (_la == T__15) {
		    {
			{
			    setState(364);
			    match(T__15);
			    setState(365);
			    guard();
			}
		    }
		    setState(370);
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
	enterRule(_localctx, 52, RULE_repetition);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(371);
		match(T__0);
		setState(372);
		match(T__18);
		setState(373);
		inv();
		setState(374);
		match(T__15);
		setState(375);
		guard();
		setState(376);
		match(T__15);
		setState(377);
		var();
		setState(378);
		match(T__1);
		setState(379);
		match(NL);
		setState(380);
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
	enterRule(_localctx, 54, RULE_inv);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(382);
		match(T__12);
		setState(383);
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
	enterRule(_localctx, 56, RULE_guard);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(385);
		match(T__20);
		setState(386);
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
	enterRule(_localctx, 58, RULE_var);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(388);
		match(T__29);
		setState(389);
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
	enterRule(_localctx, 60, RULE_returnS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(391);
		match(T__21);
		setState(392);
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
	enterRule(_localctx, 62, RULE_originalS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(394);
		match(T__19);
		setState(395);
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
	enterRule(_localctx, 64, RULE_skipS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(397);
		match(T__8);
		setState(399);
		switch (getInterpreter().adaptivePredict(_input, 31, _ctx)) {
		case 1: {
		    setState(398);
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
	enterRule(_localctx, 66, RULE_methodCallS);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(401);
		match(T__13);
		setState(402);
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
	enterRule(_localctx, 68, RULE_block);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(404);
		match(T__5);
		setState(405);
		match(T__18);
		setState(406);
		name();
		setState(407);
		match(T__15);
		setState(408);
		pre();
		setState(409);
		match(T__15);
		setState(410);
		post();
		setState(411);
		match(T__1);
		setState(412);
		match(NL);
		setState(413);
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
	enterRule(_localctx, 70, RULE_name);
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(415);
		match(T__6);
		setState(416);
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
	enterRule(_localctx, 72, RULE_signature);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(418);
		match(T__17);
		setState(419);
		match(VISIBILITY);
		setState(425);
		switch (_input.LA(1)) {
		case T__27: {
		    setState(420);
		    match(T__27);
		}
		    break;
		case TYPE: {
		    setState(421);
		    match(TYPE);
		    setState(423);
		    _la = _input.LA(1);
		    if (_la == BRACKETS) {
			{
			    setState(422);
			    match(BRACKETS);
			}
		    }

		}
		    break;
		default:
		    throw new NoViableAltException(this);
		}
		setState(427);
		match(ID);
		setState(440);
		switch (_input.LA(1)) {
		case EMPTY_BRACKETS: {
		    setState(428);
		    match(EMPTY_BRACKETS);
		}
		    break;
		case T__18: {
		    setState(429);
		    match(T__18);
		    setState(430);
		    methodParameter();
		    setState(435);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		    while (_la == T__15) {
			{
			    {
				setState(431);
				match(T__15);
				setState(432);
				methodParameter();
			    }
			}
			setState(437);
			_errHandler.sync(this);
			_la = _input.LA(1);
		    }
		    setState(438);
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
	enterRule(_localctx, 74, RULE_methodParameter);
	int _la;
	try {
	    enterOuterAlt(_localctx, 1);
	    {
		setState(442);
		match(TYPE);
		setState(444);
		_la = _input.LA(1);
		if (_la == BRACKETS) {
		    {
			setState(443);
			match(BRACKETS);
		    }
		}

		setState(446);
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
	enterRule(_localctx, 76, RULE_mlexpr);
	int _la;
	try {
	    int _alt;
	    enterOuterAlt(_localctx, 1);
	    {
		setState(448);
		match(T__28);
		setState(450);
		_la = _input.LA(1);
		if (_la == NL) {
		    {
			setState(449);
			match(NL);
		    }
		}

		setState(460);
		_errHandler.sync(this);
		_alt = getInterpreter().adaptivePredict(_input, 39, _ctx);
		while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
		    if (_alt == 1) {
			{
			    {
				setState(453);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
				    {
					{
					    setState(452);
					    match(T__10);
					}
				    }
				    setState(455);
				    _errHandler.sync(this);
				    _la = _input.LA(1);
				} while (_la == T__10);
				setState(457);
				statement();
			    }
			}
		    }
		    setState(462);
		    _errHandler.sync(this);
		    _alt = getInterpreter().adaptivePredict(_input, 39, _ctx);
		}
		setState(464);
		_errHandler.sync(this);
		_la = _input.LA(1);
		do {
		    {
			{
			    setState(463);
			    match(T__10);
			}
		    }
		    setState(466);
		    _errHandler.sync(this);
		    _la = _input.LA(1);
		} while (_la == T__10);
		setState(468);
		match(T__23);
		setState(470);
		switch (getInterpreter().adaptivePredict(_input, 41, _ctx)) {
		case 1: {
		    setState(469);
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
	case 14:
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

    public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3.\u01db\4\2\t\2\4"
	    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
	    + "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
	    + "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
	    + "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
	    + "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\3\3\3\3"
	    + "\3\4\3\4\3\4\3\4\3\4\3\4\6\4\\\n\4\r\4\16\4]\3\5\3\5\5\5b\n\5\3\6\7\6"
	    + "e\n\6\f\6\16\6h\13\6\3\6\3\6\3\6\7\6m\n\6\f\6\16\6p\13\6\3\6\7\6s\n\6"
	    + "\f\6\16\6v\13\6\3\6\3\6\3\6\7\6{\n\6\f\6\16\6~\13\6\3\6\7\6\u0081\n\6"
	    + "\f\6\16\6\u0084\13\6\3\6\3\6\3\6\7\6\u0089\n\6\f\6\16\6\u008c\13\6\3\6"
	    + "\7\6\u008f\n\6\f\6\16\6\u0092\13\6\3\6\3\6\3\6\7\6\u0097\n\6\f\6\16\6"
	    + "\u009a\13\6\5\6\u009c\n\6\3\7\3\7\3\7\6\7\u00a1\n\7\r\7\16\7\u00a2\3\b"
	    + "\6\b\u00a6\n\b\r\b\16\b\u00a7\3\b\3\b\3\b\5\b\u00ad\n\b\3\b\3\b\5\b\u00b1"
	    + "\n\b\3\t\3\t\3\t\6\t\u00b6\n\t\r\t\16\t\u00b7\3\t\3\t\3\t\6\t\u00bd\n"
	    + "\t\r\t\16\t\u00be\3\n\3\n\3\n\6\n\u00c4\n\n\r\n\16\n\u00c5\3\13\6\13\u00c9"
	    + "\n\13\r\13\16\13\u00ca\3\13\3\13\3\13\3\13\5\13\u00d1\n\13\3\f\3\f\3\f"
	    + "\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20"
	    + "\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"
	    + "\3\20\3\20\3\20\5\20\u00f7\n\20\3\20\3\20\3\20\7\20\u00fc\n\20\f\20\16"
	    + "\20\u00ff\13\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22"
	    + "\3\23\3\23\3\23\3\23\3\23\5\23\u0111\n\23\3\23\3\23\3\23\3\23\5\23\u0117"
	    + "\n\23\3\24\6\24\u011a\n\24\r\24\16\24\u011b\3\24\3\24\3\25\3\25\3\25\3"
	    + "\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u012a\n\25\3\26\3\26\3\26\3\26"
	    + "\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"
	    + "\3\26\3\26\5\26\u0140\n\26\3\26\3\26\5\26\u0144\n\26\3\27\3\27\3\27\3"
	    + "\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"
	    + "\30\3\30\3\30\5\30\u015a\n\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"
	    + "\3\32\3\32\3\32\3\32\3\32\3\32\6\32\u016a\n\32\r\32\16\32\u016b\3\33\3"
	    + "\33\3\33\7\33\u0171\n\33\f\33\16\33\u0174\13\33\3\34\3\34\3\34\3\34\3"
	    + "\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3"
	    + "\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\5\"\u0192\n\"\3#\3#\3#\3$\3$\3$\3$"
	    + "\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3&\3&\3&\3&\3&\5&\u01aa\n&\5&\u01ac\n&"
	    + "\3&\3&\3&\3&\3&\3&\7&\u01b4\n&\f&\16&\u01b7\13&\3&\3&\5&\u01bb\n&\3\'"
	    + "\3\'\5\'\u01bf\n\'\3\'\3\'\3(\3(\5(\u01c5\n(\3(\6(\u01c8\n(\r(\16(\u01c9"
	    + "\3(\7(\u01cd\n(\f(\16(\u01d0\13(\3(\6(\u01d3\n(\r(\16(\u01d4\3(\3(\5("
	    + "\u01d9\n(\3(\2\3\36)\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60"
	    + "\62\64\668:<>@BDFHJLN\2\2\u01f7\2P\3\2\2\2\4S\3\2\2\2\6U\3\2\2\2\ba\3"
	    + "\2\2\2\n\u009b\3\2\2\2\f\u009d\3\2\2\2\16\u00a5\3\2\2\2\20\u00b2\3\2\2"
	    + "\2\22\u00c0\3\2\2\2\24\u00c8\3\2\2\2\26\u00d2\3\2\2\2\30\u00db\3\2\2\2"
	    + "\32\u00de\3\2\2\2\34\u00e1\3\2\2\2\36\u00f6\3\2\2\2 \u0100\3\2\2\2\"\u0105"
	    + "\3\2\2\2$\u0116\3\2\2\2&\u0119\3\2\2\2(\u0129\3\2\2\2*\u013f\3\2\2\2,"
	    + "\u0145\3\2\2\2.\u0159\3\2\2\2\60\u015b\3\2\2\2\62\u0163\3\2\2\2\64\u016d"
	    + "\3\2\2\2\66\u0175\3\2\2\28\u0180\3\2\2\2:\u0183\3\2\2\2<\u0186\3\2\2\2"
	    + ">\u0189\3\2\2\2@\u018c\3\2\2\2B\u018f\3\2\2\2D\u0193\3\2\2\2F\u0196\3"
	    + "\2\2\2H\u01a1\3\2\2\2J\u01a4\3\2\2\2L\u01bc\3\2\2\2N\u01c2\3\2\2\2PQ\5"
	    + "\4\3\2QR\7\2\2\3R\3\3\2\2\2ST\5\6\4\2T\5\3\2\2\2UV\7\37\2\2VW\7\21\2\2"
	    + "WX\5\b\5\2XY\7\"\2\2Y[\7%\2\2Z\\\5\n\6\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2"
	    + "\2]^\3\2\2\2^\7\3\2\2\2_b\5H%\2`b\5J&\2a_\3\2\2\2a`\3\2\2\2b\t\3\2\2\2"
	    + "ce\7%\2\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gi\3\2\2\2hf\3\2\2\2"
	    + "ij\7\31\2\2jn\5\26\f\2km\7%\2\2lk\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2"
	    + "\2o\u009c\3\2\2\2pn\3\2\2\2qs\7%\2\2rq\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3"
	    + "\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7\31\2\2x|\5\f\7\2y{\7%\2\2zy\3\2\2\2{~\3"
	    + "\2\2\2|z\3\2\2\2|}\3\2\2\2}\u009c\3\2\2\2~|\3\2\2\2\177\u0081\7%\2\2\u0080"
	    + "\177\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2"
	    + "\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086\7\31\2\2\u0086"
	    + "\u008a\5\20\t\2\u0087\u0089\7%\2\2\u0088\u0087\3\2\2\2\u0089\u008c\3\2"
	    + "\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u009c\3\2\2\2\u008c"
	    + "\u008a\3\2\2\2\u008d\u008f\7%\2\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2"
	    + "\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0093\3\2\2\2\u0092"
	    + "\u0090\3\2\2\2\u0093\u0094\7\31\2\2\u0094\u0098\5\22\n\2\u0095\u0097\7"
	    + "%\2\2\u0096\u0095\3\2\2\2\u0097\u009a\3\2\2\2\u0098\u0096\3\2\2\2\u0098"
	    + "\u0099\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009bf\3\2\2\2"
	    + "\u009bt\3\2\2\2\u009b\u0082\3\2\2\2\u009b\u0090\3\2\2\2\u009c\13\3\2\2"
	    + "\2\u009d\u009e\7\25\2\2\u009e\u00a0\7%\2\2\u009f\u00a1\5\16\b\2\u00a0"
	    + "\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2"
	    + "\2\2\u00a3\r\3\2\2\2\u00a4\u00a6\7\31\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a7"
	    + "\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9"
	    + "\u00aa\7&\2\2\u00aa\u00ac\7(\2\2\u00ab\u00ad\7)\2\2\u00ac\u00ab\3\2\2"
	    + "\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\7.\2\2\u00af\u00b1"
	    + "\7%\2\2\u00b0\u00af\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\17\3\2\2\2\u00b2"
	    + "\u00b3\7!\2\2\u00b3\u00bc\7%\2\2\u00b4\u00b6\7\31\2\2\u00b5\u00b4\3\2"
	    + "\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"
	    + "\u00b9\3\2\2\2\u00b9\u00ba\5\36\20\2\u00ba\u00bb\7%\2\2\u00bb\u00bd\3"
	    + "\2\2\2\u00bc\u00b5\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bc\3\2\2\2\u00be"
	    + "\u00bf\3\2\2\2\u00bf\21\3\2\2\2\u00c0\u00c1\7\13\2\2\u00c1\u00c3\7%\2"
	    + "\2\u00c2\u00c4\5\24\13\2\u00c3\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"
	    + "\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\23\3\2\2\2\u00c7\u00c9\7\31\2"
	    + "\2\u00c8\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb"
	    + "\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\7.\2\2\u00cd\u00ce\7,\2\2\u00ce"
	    + "\u00d0\5\36\20\2\u00cf\u00d1\7%\2\2\u00d0\u00cf\3\2\2\2\u00d0\u00d1\3"
	    + "\2\2\2\u00d1\25\3\2\2\2\u00d2\u00d3\7 \2\2\u00d3\u00d4\7\21\2\2\u00d4"
	    + "\u00d5\5\30\r\2\u00d5\u00d6\7\24\2\2\u00d6\u00d7\5\32\16\2\u00d7\u00d8"
	    + "\7\"\2\2\u00d8\u00d9\7%\2\2\u00d9\u00da\5&\24\2\u00da\27\3\2\2\2\u00db"
	    + "\u00dc\7\23\2\2\u00dc\u00dd\5\36\20\2\u00dd\31\3\2\2\2\u00de\u00df\7\4"
	    + "\2\2\u00df\u00e0\5\36\20\2\u00e0\33\3\2\2\2\u00e1\u00e2\7\5\2\2\u00e2"
	    + "\u00e3\5\36\20\2\u00e3\35\3\2\2\2\u00e4\u00e5\b\20\1\2\u00e5\u00e6\5$"
	    + "\23\2\u00e6\u00e7\5\36\20\5\u00e7\u00f7\3\2\2\2\u00e8\u00e9\7,\2\2\u00e9"
	    + "\u00f7\5\36\20\4\u00ea\u00eb\7\21\2\2\u00eb\u00ec\5\36\20\2\u00ec\u00ed"
	    + "\7\"\2\2\u00ed\u00f7\3\2\2\2\u00ee\u00ef\7\21\2\2\u00ef\u00f0\5\36\20"
	    + "\2\u00f0\u00f1\7\"\2\2\u00f1\u00f2\7,\2\2\u00f2\u00f3\5\36\20\2\u00f3"
	    + "\u00f7\3\2\2\2\u00f4\u00f7\5 \21\2\u00f5\u00f7\5$\23\2\u00f6\u00e4\3\2"
	    + "\2\2\u00f6\u00e8\3\2\2\2\u00f6\u00ea\3\2\2\2\u00f6\u00ee\3\2\2\2\u00f6"
	    + "\u00f4\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00fd\3\2\2\2\u00f8\u00f9\f\3"
	    + "\2\2\u00f9\u00fa\7\24\2\2\u00fa\u00fc\5\36\20\4\u00fb\u00f8\3\2\2\2\u00fc"
	    + "\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\37\3\2\2"
	    + "\2\u00ff\u00fd\3\2\2\2\u0100\u0101\7-\2\2\u0101\u0102\7.\2\2\u0102\u0103"
	    + "\7\n\2\2\u0103\u0104\5\36\20\2\u0104!\3\2\2\2\u0105\u0106\7\t\2\2\u0106"
	    + "\u0107\7.\2\2\u0107\u0108\7\21\2\2\u0108\u0109\5$\23\2\u0109\u010a\7\""
	    + "\2\2\u010a#\3\2\2\2\u010b\u0117\7.\2\2\u010c\u0117\5\"\22\2\u010d\u0111"
	    + "\7(\2\2\u010e\u0111\7.\2\2\u010f\u0111\5\"\22\2\u0110\u010d\3\2\2\2\u0110"
	    + "\u010e\3\2\2\2\u0110\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0113\7\32"
	    + "\2\2\u0113\u0114\5\36\20\2\u0114\u0115\7\34\2\2\u0115\u0117\3\2\2\2\u0116"
	    + "\u010b\3\2\2\2\u0116\u010c\3\2\2\2\u0116\u0110\3\2\2\2\u0117%\3\2\2\2"
	    + "\u0118\u011a\7\31\2\2\u0119\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u0119"
	    + "\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e\5(\25\2\u011e"
	    + "\'\3\2\2\2\u011f\u012a\5*\26\2\u0120\u012a\5\60\31\2\u0121\u012a\5\62"
	    + "\32\2\u0122\u012a\5\66\34\2\u0123\u012a\5> \2\u0124\u012a\5@!\2\u0125"
	    + "\u012a\5B\"\2\u0126\u012a\5D#\2\u0127\u012a\5F$\2\u0128\u012a\5N(\2\u0129"
	    + "\u011f\3\2\2\2\u0129\u0120\3\2\2\2\u0129\u0121\3\2\2\2\u0129\u0122\3\2"
	    + "\2\2\u0129\u0123\3\2\2\2\u0129\u0124\3\2\2\2\u0129\u0125\3\2\2\2\u0129"
	    + "\u0126\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u0128\3\2\2\2\u012a)\3\2\2\2"
	    + "\u012b\u0140\5$\23\2\u012c\u0140\5,\27\2\u012d\u012e\5$\23\2\u012e\u012f"
	    + "\7*\2\2\u012f\u0140\3\2\2\2\u0130\u0131\5$\23\2\u0131\u0132\7\21\2\2\u0132"
	    + "\u0133\5\36\20\2\u0133\u0134\7\"\2\2\u0134\u0140\3\2\2\2\u0135\u0136\5"
	    + "$\23\2\u0136\u0137\5*\26\2\u0137\u0140\3\2\2\2\u0138\u0139\5$\23\2\u0139"
	    + "\u013a\7,\2\2\u013a\u013b\5.\30\2\u013b\u0140\3\2\2\2\u013c\u013d\5$\23"
	    + "\2\u013d\u013e\7,\2\2\u013e\u0140\3\2\2\2\u013f\u012b\3\2\2\2\u013f\u012c"
	    + "\3\2\2\2\u013f\u012d\3\2\2\2\u013f\u0130\3\2\2\2\u013f\u0135\3\2\2\2\u013f"
	    + "\u0138\3\2\2\2\u013f\u013c\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0141\3\2"
	    + "\2\2\u0141\u0143\7\n\2\2\u0142\u0144\7%\2\2\u0143\u0142\3\2\2\2\u0143"
	    + "\u0144\3\2\2\2\u0144+\3\2\2\2\u0145\u0146\7\3\2\2\u0146\u0147\5.\30\2"
	    + "\u0147-\3\2\2\2\u0148\u015a\5$\23\2\u0149\u014a\7\21\2\2\u014a\u014b\5"
	    + ".\30\2\u014b\u014c\7\"\2\2\u014c\u015a\3\2\2\2\u014d\u014e\5$\23\2\u014e"
	    + "\u014f\7*\2\2\u014f\u015a\3\2\2\2\u0150\u0151\5$\23\2\u0151\u0152\5.\30"
	    + "\2\u0152\u015a\3\2\2\2\u0153\u0154\7+\2\2\u0154\u015a\5$\23\2\u0155\u0156"
	    + "\5$\23\2\u0156\u0157\7,\2\2\u0157\u0158\5.\30\2\u0158\u015a\3\2\2\2\u0159"
	    + "\u0148\3\2\2\2\u0159\u0149\3\2\2\2\u0159\u014d\3\2\2\2\u0159\u0150\3\2"
	    + "\2\2\u0159\u0153\3\2\2\2\u0159\u0155\3\2\2\2\u015a/\3\2\2\2\u015b\u015c"
	    + "\7\r\2\2\u015c\u015d\7\21\2\2\u015d\u015e\5\34\17\2\u015e\u015f\7\"\2"
	    + "\2\u015f\u0160\7%\2\2\u0160\u0161\5&\24\2\u0161\u0162\5&\24\2\u0162\61"
	    + "\3\2\2\2\u0163\u0164\7\30\2\2\u0164\u0165\7\21\2\2\u0165\u0166\5\64\33"
	    + "\2\u0166\u0167\7\"\2\2\u0167\u0169\7%\2\2\u0168\u016a\5&\24\2\u0169\u0168"
	    + "\3\2\2\2\u016a\u016b\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c"
	    + "\63\3\2\2\2\u016d\u0172\5:\36\2\u016e\u016f\7\24\2\2\u016f\u0171\5:\36"
	    + "\2\u0170\u016e\3\2\2\2\u0171\u0174\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0173"
	    + "\3\2\2\2\u0173\65\3\2\2\2\u0174\u0172\3\2\2\2\u0175\u0176\7#\2\2\u0176"
	    + "\u0177\7\21\2\2\u0177\u0178\58\35\2\u0178\u0179\7\24\2\2\u0179\u017a\5"
	    + ":\36\2\u017a\u017b\7\24\2\2\u017b\u017c\5<\37\2\u017c\u017d\7\"\2\2\u017d"
	    + "\u017e\7%\2\2\u017e\u017f\5&\24\2\u017f\67\3\2\2\2\u0180\u0181\7\27\2"
	    + "\2\u0181\u0182\5\36\20\2\u01829\3\2\2\2\u0183\u0184\7\17\2\2\u0184\u0185"
	    + "\5\36\20\2\u0185;\3\2\2\2\u0186\u0187\7\6\2\2\u0187\u0188\5\36\20\2\u0188"
	    + "=\3\2\2\2\u0189\u018a\7\16\2\2\u018a\u018b\5*\26\2\u018b?\3\2\2\2\u018c"
	    + "\u018d\7\20\2\2\u018d\u018e\5*\26\2\u018eA\3\2\2\2\u018f\u0191\7\33\2"
	    + "\2\u0190\u0192\7%\2\2\u0191\u0190\3\2\2\2\u0191\u0192\3\2\2\2\u0192C\3"
	    + "\2\2\2\u0193\u0194\7\26\2\2\u0194\u0195\5*\26\2\u0195E\3\2\2\2\u0196\u0197"
	    + "\7\36\2\2\u0197\u0198\7\21\2\2\u0198\u0199\5H%\2\u0199\u019a\7\24\2\2"
	    + "\u019a\u019b\5\30\r\2\u019b\u019c\7\24\2\2\u019c\u019d\5\32\16\2\u019d"
	    + "\u019e\7\"\2\2\u019e\u019f\7%\2\2\u019f\u01a0\5N(\2\u01a0G\3\2\2\2\u01a1"
	    + "\u01a2\7\35\2\2\u01a2\u01a3\7.\2\2\u01a3I\3\2\2\2\u01a4\u01a5\7\22\2\2"
	    + "\u01a5\u01ab\7\'\2\2\u01a6\u01ac\7\b\2\2\u01a7\u01a9\7(\2\2\u01a8\u01aa"
	    + "\7)\2\2\u01a9\u01a8\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01ac\3\2\2\2\u01ab"
	    + "\u01a6\3\2\2\2\u01ab\u01a7\3\2\2\2\u01ac\u01ad\3\2\2\2\u01ad\u01ba\7."
	    + "\2\2\u01ae\u01bb\7*\2\2\u01af\u01b0\7\21\2\2\u01b0\u01b5\5L\'\2\u01b1"
	    + "\u01b2\7\24\2\2\u01b2\u01b4\5L\'\2\u01b3\u01b1\3\2\2\2\u01b4\u01b7\3\2"
	    + "\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6\u01b8\3\2\2\2\u01b7"
	    + "\u01b5\3\2\2\2\u01b8\u01b9\7\"\2\2\u01b9\u01bb\3\2\2\2\u01ba\u01ae\3\2"
	    + "\2\2\u01ba\u01af\3\2\2\2\u01bbK\3\2\2\2\u01bc\u01be\7(\2\2\u01bd\u01bf"
	    + "\7)\2\2\u01be\u01bd\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0"
	    + "\u01c1\7.\2\2\u01c1M\3\2\2\2\u01c2\u01c4\7\7\2\2\u01c3\u01c5\7%\2\2\u01c4"
	    + "\u01c3\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5\u01ce\3\2\2\2\u01c6\u01c8\7\31"
	    + "\2\2\u01c7\u01c6\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01c7\3\2\2\2\u01c9"
	    + "\u01ca\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cd\5*\26\2\u01cc\u01c7\3\2"
	    + "\2\2\u01cd\u01d0\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf"
	    + "\u01d2\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d1\u01d3\7\31\2\2\u01d2\u01d1\3"
	    + "\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d2\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5"
	    + "\u01d6\3\2\2\2\u01d6\u01d8\7\f\2\2\u01d7\u01d9\7%\2\2\u01d8\u01d7\3\2"
	    + "\2\2\u01d8\u01d9\3\2\2\2\u01d9O\3\2\2\2,]afnt|\u0082\u008a\u0090\u0098"
	    + "\u009b\u00a2\u00a7\u00ac\u00b0\u00b7\u00be\u00c5\u00ca\u00d0\u00f6\u00fd"
	    + "\u0110\u0116\u011b\u0129\u013f\u0143\u0159\u016b\u0172\u0191\u01a9\u01ab"
	    + "\u01b5\u01ba\u01be\u01c4\u01c9\u01ce\u01d4\u01d8";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
	_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
	    _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
	}
    }
}