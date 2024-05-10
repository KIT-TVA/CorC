package de.kit.tva.lost.models;

// Generated from Lost.g4 by ANTLR 4.4
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import de.kit.tva.lost.interfaces.LostListener;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LostParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__27=1, T__26=2, T__25=3, T__24=4, T__23=5, T__22=6, T__21=7, T__20=8, 
		T__19=9, T__18=10, T__17=11, T__16=12, T__15=13, T__14=14, T__13=15, T__12=16, 
		T__11=17, T__10=18, T__9=19, T__8=20, T__7=21, T__6=22, T__5=23, T__4=24, 
		T__3=25, T__2=26, T__1=27, T__0=28, WS=29, NL=30, KIND=31, TYPE=32, OP=33, 
		ID=34;
	public static final String[] tokenNames = {
		"<INVALID>", "'post:'", "'intm:'", "'var:'", "'{'", "';'", "'Renaming'", 
		"'}'", "'D('", "'()'", "'R:'", "'guard:'", "'C('", "'B('", "'O:'", "'('", 
		"'pre:'", "','", "'Vars'", "'M:'", "'inv:'", "'\t'", "'S('", "'L('", "'skip'", 
		"'name:'", "'F'", "'GlobalConditions'", "')'", "WS", "'\n'", "KIND", "TYPE", 
		"OP", "ID"
	};
	public static final int
		RULE_program = 0, RULE_root = 1, RULE_diagram = 2, RULE_initializer = 3, 
		RULE_vars = 4, RULE_variable = 5, RULE_globalConditions = 6, RULE_renaming = 7, 
		RULE_renamer = 8, RULE_formula = 9, RULE_pre = 10, RULE_post = 11, RULE_intm = 12, 
		RULE_condition = 13, RULE_quantor = 14, RULE_refinement = 15, RULE_refinementRule = 16, 
		RULE_statement = 17, RULE_javaReturn = 18, RULE_assigner = 19, RULE_composition = 20, 
		RULE_selection = 21, RULE_guards = 22, RULE_repetition = 23, RULE_inv = 24, 
		RULE_guard = 25, RULE_var = 26, RULE_returnS = 27, RULE_originalS = 28, 
		RULE_skipS = 29, RULE_methodCallS = 30, RULE_block = 31, RULE_name = 32, 
		RULE_mlexpr = 33;
	public static final String[] ruleNames = {
		"program", "root", "diagram", "initializer", "vars", "variable", "globalConditions", 
		"renaming", "renamer", "formula", "pre", "post", "intm", "condition", 
		"quantor", "refinement", "refinementRule", "statement", "javaReturn", 
		"assigner", "composition", "selection", "guards", "repetition", "inv", 
		"guard", "var", "returnS", "originalS", "skipS", "methodCallS", "block", 
		"name", "mlexpr"
	};

	@Override
	public String getGrammarFileName() { return "Lost.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LostParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(LostParser.EOF, 0); }
		public RootContext root() {
			return getRuleContext(RootContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); root();
			setState(69); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RootContext extends ParserRuleContext {
		public DiagramContext diagram() {
			return getRuleContext(DiagramContext.class,0);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRoot(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71); diagram();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DiagramContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<InitializerContext> initializer() {
			return getRuleContexts(InitializerContext.class);
		}
		public InitializerContext initializer(int i) {
			return getRuleContext(InitializerContext.class,i);
		}
		public DiagramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diagram; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterDiagram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitDiagram(this);
		}
	}

	public final DiagramContext diagram() throws RecognitionException {
		DiagramContext _localctx = new DiagramContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_diagram);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73); match(T__20);
			setState(74); name();
			setState(75); match(T__0);
			setState(76); match(NL);
			setState(78); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(77); initializer();
				}
				}
				setState(80); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 || _la==NL );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(LostParser.NL); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public GlobalConditionsContext globalConditions() {
			return getRuleContext(GlobalConditionsContext.class,0);
		}
		public TerminalNode NL(int i) {
			return getToken(LostParser.NL, i);
		}
		public RenamingContext renaming() {
			return getRuleContext(RenamingContext.class,0);
		}
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitInitializer(this);
		}
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_initializer);
		int _la;
		try {
			int _alt;
			setState(134);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(82); match(NL);
					}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(88); formula();
				setState(92);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(89); match(NL);
						}
						} 
					}
					setState(94);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(95); match(NL);
					}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(101); vars();
				setState(105);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(102); match(NL);
						}
						} 
					}
					setState(107);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(108); match(NL);
					}
					}
					setState(113);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(114); globalConditions();
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(115); match(NL);
						}
						} 
					}
					setState(120);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NL) {
					{
					{
					setState(121); match(NL);
					}
					}
					setState(126);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(127); renaming();
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(128); match(NL);
						}
						} 
					}
					setState(133);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarsContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterVars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitVars(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_vars);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(136); match(T__7);
			setState(137); match(T__10);
			setState(138); match(NL);
			setState(140); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(139); variable();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(142); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public TerminalNode TYPE() { return getToken(LostParser.TYPE, 0); }
		public TerminalNode KIND() { return getToken(LostParser.KIND, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(144); match(T__7);
				}
				}
				setState(147); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 );
			setState(149); match(KIND);
			setState(150); match(TYPE);
			setState(151); match(ID);
			setState(152); match(T__23);
			setState(154);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(153); match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GlobalConditionsContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(LostParser.NL); }
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
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
		@Override public int getRuleIndex() { return RULE_globalConditions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterGlobalConditions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitGlobalConditions(this);
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
			setState(156); match(T__7);
			setState(157); match(T__1);
			setState(158); match(NL);
			setState(167); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(160); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(159); match(T__7);
						}
						}
						setState(162); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==T__7 );
					setState(164); condition();
					setState(165); match(NL);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(169); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenamingContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public List<RenamerContext> renamer() {
			return getRuleContexts(RenamerContext.class);
		}
		public RenamerContext renamer(int i) {
			return getRuleContext(RenamerContext.class,i);
		}
		public RenamingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renaming; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRenaming(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRenaming(this);
		}
	}

	public final RenamingContext renaming() throws RecognitionException {
		RenamingContext _localctx = new RenamingContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_renaming);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(171); match(T__7);
			setState(172); match(T__22);
			setState(173); match(NL);
			setState(175); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(174); renamer();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(177); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RenamerContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public TerminalNode OP() { return getToken(LostParser.OP, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public RenamerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_renamer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRenamer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRenamer(this);
		}
	}

	public final RenamerContext renamer() throws RecognitionException {
		RenamerContext _localctx = new RenamerContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_renamer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(179); match(T__7);
				}
				}
				setState(182); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 );
			setState(184); match(ID);
			setState(185); match(OP);
			setState(186); condition();
			setState(188);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(187); match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormulaContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public PreContext pre() {
			return getRuleContext(PreContext.class,0);
		}
		public PostContext post() {
			return getRuleContext(PostContext.class,0);
		}
		public RefinementContext refinement() {
			return getRuleContext(RefinementContext.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190); match(T__7);
			setState(191); match(T__2);
			setState(192); match(T__13);
			setState(193); pre();
			setState(194); match(T__11);
			setState(195); post();
			setState(196); match(T__0);
			setState(197); match(NL);
			setState(198); refinement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PreContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public PreContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pre; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterPre(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitPre(this);
		}
	}

	public final PreContext pre() throws RecognitionException {
		PreContext _localctx = new PreContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pre);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200); match(T__12);
			setState(201); condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public PostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_post; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterPost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitPost(this);
		}
	}

	public final PostContext post() throws RecognitionException {
		PostContext _localctx = new PostContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_post);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203); match(T__27);
			setState(204); condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntmContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public IntmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterIntm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitIntm(this);
		}
	}

	public final IntmContext intm() throws RecognitionException {
		IntmContext _localctx = new IntmContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_intm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206); match(T__26);
			setState(207); condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public QuantorContext quantor() {
			return getRuleContext(QuantorContext.class,0);
		}
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode OP() { return getToken(LostParser.OP, 0); }
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_condition);
		try {
			setState(225);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(209); match(T__13);
				setState(210); condition();
				setState(211); match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(213); match(T__13);
				setState(214); condition();
				setState(215); match(T__0);
				setState(216); match(OP);
				setState(217); condition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(219); quantor();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(220); match(ID);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(221); match(ID);
				setState(222); condition();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(223); match(OP);
				setState(224); condition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantorContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(LostParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LostParser.ID, i);
		}
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public QuantorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterQuantor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitQuantor(this);
		}
	}

	public final QuantorContext quantor() throws RecognitionException {
		QuantorContext _localctx = new QuantorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_quantor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227); match(ID);
			setState(228); match(T__13);
			setState(229); match(ID);
			setState(230); match(ID);
			setState(231); match(T__23);
			setState(232); condition();
			setState(233); match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RefinementContext extends ParserRuleContext {
		public RefinementRuleContext refinementRule() {
			return getRuleContext(RefinementRuleContext.class,0);
		}
		public RefinementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_refinement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRefinement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRefinement(this);
		}
	}

	public final RefinementContext refinement() throws RecognitionException {
		RefinementContext _localctx = new RefinementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_refinement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(235); match(T__7);
				}
				}
				setState(238); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__7 );
			setState(240); refinementRule();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RefinementRuleContext extends ParserRuleContext {
		public CompositionContext composition() {
			return getRuleContext(CompositionContext.class,0);
		}
		public SelectionContext selection() {
			return getRuleContext(SelectionContext.class,0);
		}
		public SkipSContext skipS() {
			return getRuleContext(SkipSContext.class,0);
		}
		public OriginalSContext originalS() {
			return getRuleContext(OriginalSContext.class,0);
		}
		public MlexprContext mlexpr() {
			return getRuleContext(MlexprContext.class,0);
		}
		public MethodCallSContext methodCallS() {
			return getRuleContext(MethodCallSContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ReturnSContext returnS() {
			return getRuleContext(ReturnSContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public RepetitionContext repetition() {
			return getRuleContext(RepetitionContext.class,0);
		}
		public RefinementRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_refinementRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRefinementRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRefinementRule(this);
		}
	}

	public final RefinementRuleContext refinementRule() throws RecognitionException {
		RefinementRuleContext _localctx = new RefinementRuleContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_refinementRule);
		try {
			setState(252);
			switch (_input.LA(1)) {
			case T__13:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(242); statement();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 2);
				{
				setState(243); composition();
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 3);
				{
				setState(244); selection();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(245); repetition();
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 5);
				{
				setState(246); returnS();
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 6);
				{
				setState(247); originalS();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 7);
				{
				setState(248); skipS();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 8);
				{
				setState(249); methodCallS();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 9);
				{
				setState(250); block();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 10);
				{
				setState(251); mlexpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public TerminalNode OP() { return getToken(LostParser.OP, 0); }
		public AssignerContext assigner() {
			return getRuleContext(AssignerContext.class,0);
		}
		public JavaReturnContext javaReturn() {
			return getRuleContext(JavaReturnContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(254); match(ID);
				}
				break;
			case 2:
				{
				setState(255); javaReturn();
				}
				break;
			case 3:
				{
				setState(256); match(T__13);
				setState(257); statement();
				setState(258); match(T__0);
				}
				break;
			case 4:
				{
				setState(260); match(ID);
				setState(261); match(T__19);
				}
				break;
			case 5:
				{
				setState(262); match(ID);
				setState(263); statement();
				}
				break;
			case 6:
				{
				setState(264); match(ID);
				setState(265); match(OP);
				setState(266); assigner();
				}
				break;
			}
			setState(269); match(T__23);
			setState(271);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(270); match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JavaReturnContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public AssignerContext assigner() {
			return getRuleContext(AssignerContext.class,0);
		}
		public JavaReturnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_javaReturn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterJavaReturn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitJavaReturn(this);
		}
	}

	public final JavaReturnContext javaReturn() throws RecognitionException {
		JavaReturnContext _localctx = new JavaReturnContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_javaReturn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273); match(ID);
			setState(274); assigner();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignerContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public TerminalNode OP() { return getToken(LostParser.OP, 0); }
		public AssignerContext assigner() {
			return getRuleContext(AssignerContext.class,0);
		}
		public AssignerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assigner; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterAssigner(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitAssigner(this);
		}
	}

	public final AssignerContext assigner() throws RecognitionException {
		AssignerContext _localctx = new AssignerContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_assigner);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(276); match(ID);
				}
				break;
			case 2:
				{
				setState(277); match(T__13);
				setState(278); assigner();
				setState(279); match(T__0);
				}
				break;
			case 3:
				{
				setState(281); match(ID);
				setState(282); match(T__19);
				}
				break;
			case 4:
				{
				setState(283); match(ID);
				setState(284); assigner();
				}
				break;
			case 5:
				{
				setState(285); match(ID);
				setState(286); match(OP);
				setState(287); assigner();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompositionContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public RefinementContext refinement(int i) {
			return getRuleContext(RefinementContext.class,i);
		}
		public List<RefinementContext> refinement() {
			return getRuleContexts(RefinementContext.class);
		}
		public IntmContext intm() {
			return getRuleContext(IntmContext.class,0);
		}
		public CompositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_composition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterComposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitComposition(this);
		}
	}

	public final CompositionContext composition() throws RecognitionException {
		CompositionContext _localctx = new CompositionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_composition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290); match(T__16);
			setState(291); intm();
			setState(292); match(T__0);
			setState(293); match(NL);
			setState(294); refinement();
			setState(295); refinement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public GuardsContext guards() {
			return getRuleContext(GuardsContext.class,0);
		}
		public RefinementContext refinement(int i) {
			return getRuleContext(RefinementContext.class,i);
		}
		public List<RefinementContext> refinement() {
			return getRuleContexts(RefinementContext.class);
		}
		public SelectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterSelection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitSelection(this);
		}
	}

	public final SelectionContext selection() throws RecognitionException {
		SelectionContext _localctx = new SelectionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_selection);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(297); match(T__6);
			setState(298); guards();
			setState(299); match(T__0);
			setState(300); match(NL);
			setState(302); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(301); refinement();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(304); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GuardsContext extends ParserRuleContext {
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public GuardsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guards; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterGuards(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitGuards(this);
		}
	}

	public final GuardsContext guards() throws RecognitionException {
		GuardsContext _localctx = new GuardsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_guards);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306); match(T__17);
			setState(307); condition();
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(308); match(T__11);
				setState(309); match(T__17);
				setState(310); condition();
				}
				}
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RepetitionContext extends ParserRuleContext {
		public InvContext inv() {
			return getRuleContext(InvContext.class,0);
		}
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public GuardContext guard() {
			return getRuleContext(GuardContext.class,0);
		}
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public RefinementContext refinement() {
			return getRuleContext(RefinementContext.class,0);
		}
		public RepetitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_repetition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterRepetition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitRepetition(this);
		}
	}

	public final RepetitionContext repetition() throws RecognitionException {
		RepetitionContext _localctx = new RepetitionContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_repetition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316); match(T__5);
			setState(317); inv();
			setState(318); match(T__11);
			setState(319); guard();
			setState(320); match(T__11);
			setState(321); var();
			setState(322); match(T__0);
			setState(323); match(NL);
			setState(324); refinement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InvContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public InvContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterInv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitInv(this);
		}
	}

	public final InvContext inv() throws RecognitionException {
		InvContext _localctx = new InvContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_inv);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326); match(T__8);
			setState(327); condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GuardContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public GuardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterGuard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitGuard(this);
		}
	}

	public final GuardContext guard() throws RecognitionException {
		GuardContext _localctx = new GuardContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_guard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329); match(T__17);
			setState(330); condition();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitVar(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332); match(T__25);
			setState(333); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnSContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ReturnSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterReturnS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitReturnS(this);
		}
	}

	public final ReturnSContext returnS() throws RecognitionException {
		ReturnSContext _localctx = new ReturnSContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_returnS);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335); match(T__18);
			setState(336); statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OriginalSContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public OriginalSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_originalS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterOriginalS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitOriginalS(this);
		}
	}

	public final OriginalSContext originalS() throws RecognitionException {
		OriginalSContext _localctx = new OriginalSContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_originalS);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338); match(T__14);
			setState(339); statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SkipSContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public SkipSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skipS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterSkipS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitSkipS(this);
		}
	}

	public final SkipSContext skipS() throws RecognitionException {
		SkipSContext _localctx = new SkipSContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_skipS);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341); match(T__4);
			setState(343);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				setState(342); match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallSContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public MethodCallSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCallS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterMethodCallS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitMethodCallS(this);
		}
	}

	public final MethodCallSContext methodCallS() throws RecognitionException {
		MethodCallSContext _localctx = new MethodCallSContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_methodCallS);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(345); match(T__9);
			setState(346); statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode NL() { return getToken(LostParser.NL, 0); }
		public PreContext pre() {
			return getRuleContext(PreContext.class,0);
		}
		public MlexprContext mlexpr() {
			return getRuleContext(MlexprContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public PostContext post() {
			return getRuleContext(PostContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348); match(T__15);
			setState(349); name();
			setState(350); match(T__11);
			setState(351); pre();
			setState(352); match(T__11);
			setState(353); post();
			setState(354); match(T__0);
			setState(355); match(NL);
			setState(356); mlexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LostParser.ID, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358); match(T__3);
			setState(359); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MlexprContext extends ParserRuleContext {
		public List<TerminalNode> NL() { return getTokens(LostParser.NL); }
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
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
		@Override public int getRuleIndex() { return RULE_mlexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).enterMlexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LostListener ) ((LostListener)listener).exitMlexpr(this);
		}
	}

	public final MlexprContext mlexpr() throws RecognitionException {
		MlexprContext _localctx = new MlexprContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_mlexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361); match(T__24);
			setState(363);
			_la = _input.LA(1);
			if (_la==NL) {
				{
				setState(362); match(NL);
				}
			}

			setState(368);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13 || _la==ID) {
				{
				{
				setState(365); statement();
				}
				}
				setState(370);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(371); match(T__21);
			setState(373);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				setState(372); match(NL);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\u017a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\6\4Q\n\4\r\4"+
		"\16\4R\3\5\7\5V\n\5\f\5\16\5Y\13\5\3\5\3\5\7\5]\n\5\f\5\16\5`\13\5\3\5"+
		"\7\5c\n\5\f\5\16\5f\13\5\3\5\3\5\7\5j\n\5\f\5\16\5m\13\5\3\5\7\5p\n\5"+
		"\f\5\16\5s\13\5\3\5\3\5\7\5w\n\5\f\5\16\5z\13\5\3\5\7\5}\n\5\f\5\16\5"+
		"\u0080\13\5\3\5\3\5\7\5\u0084\n\5\f\5\16\5\u0087\13\5\5\5\u0089\n\5\3"+
		"\6\3\6\3\6\3\6\6\6\u008f\n\6\r\6\16\6\u0090\3\7\6\7\u0094\n\7\r\7\16\7"+
		"\u0095\3\7\3\7\3\7\3\7\3\7\5\7\u009d\n\7\3\b\3\b\3\b\3\b\6\b\u00a3\n\b"+
		"\r\b\16\b\u00a4\3\b\3\b\3\b\6\b\u00aa\n\b\r\b\16\b\u00ab\3\t\3\t\3\t\3"+
		"\t\6\t\u00b2\n\t\r\t\16\t\u00b3\3\n\6\n\u00b7\n\n\r\n\16\n\u00b8\3\n\3"+
		"\n\3\n\3\n\5\n\u00bf\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00e4\n\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\6\21\u00ef\n\21\r\21\16"+
		"\21\u00f0\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u00ff\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\5\23\u010e\n\23\3\23\3\23\5\23\u0112\n\23\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0123"+
		"\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\6\27"+
		"\u0131\n\27\r\27\16\27\u0132\3\30\3\30\3\30\3\30\3\30\7\30\u013a\n\30"+
		"\f\30\16\30\u013d\13\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3"+
		"\36\3\36\3\37\3\37\5\37\u015a\n\37\3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3\"\3\"\3\"\3#\3#\5#\u016e\n#\3#\7#\u0171\n#\f#\16#\u0174\13#\3#"+
		"\3#\5#\u0178\n#\3#\2\2$\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",.\60\62\64\668:<>@BD\2\2\u018a\2F\3\2\2\2\4I\3\2\2\2\6K\3\2\2\2\b\u0088"+
		"\3\2\2\2\n\u008a\3\2\2\2\f\u0093\3\2\2\2\16\u009e\3\2\2\2\20\u00ad\3\2"+
		"\2\2\22\u00b6\3\2\2\2\24\u00c0\3\2\2\2\26\u00ca\3\2\2\2\30\u00cd\3\2\2"+
		"\2\32\u00d0\3\2\2\2\34\u00e3\3\2\2\2\36\u00e5\3\2\2\2 \u00ee\3\2\2\2\""+
		"\u00fe\3\2\2\2$\u010d\3\2\2\2&\u0113\3\2\2\2(\u0122\3\2\2\2*\u0124\3\2"+
		"\2\2,\u012b\3\2\2\2.\u0134\3\2\2\2\60\u013e\3\2\2\2\62\u0148\3\2\2\2\64"+
		"\u014b\3\2\2\2\66\u014e\3\2\2\28\u0151\3\2\2\2:\u0154\3\2\2\2<\u0157\3"+
		"\2\2\2>\u015b\3\2\2\2@\u015e\3\2\2\2B\u0168\3\2\2\2D\u016b\3\2\2\2FG\5"+
		"\4\3\2GH\7\2\2\3H\3\3\2\2\2IJ\5\6\4\2J\5\3\2\2\2KL\7\n\2\2LM\5B\"\2MN"+
		"\7\36\2\2NP\7 \2\2OQ\5\b\5\2PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2S"+
		"\7\3\2\2\2TV\7 \2\2UT\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2"+
		"YW\3\2\2\2Z^\5\24\13\2[]\7 \2\2\\[\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2"+
		"\2\2_\u0089\3\2\2\2`^\3\2\2\2ac\7 \2\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2d"+
		"e\3\2\2\2eg\3\2\2\2fd\3\2\2\2gk\5\n\6\2hj\7 \2\2ih\3\2\2\2jm\3\2\2\2k"+
		"i\3\2\2\2kl\3\2\2\2l\u0089\3\2\2\2mk\3\2\2\2np\7 \2\2on\3\2\2\2ps\3\2"+
		"\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2\2\2sq\3\2\2\2tx\5\16\b\2uw\7 \2\2vu\3\2"+
		"\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\u0089\3\2\2\2zx\3\2\2\2{}\7 \2\2|"+
		"{\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0081\3\2\2\2\u0080"+
		"~\3\2\2\2\u0081\u0085\5\20\t\2\u0082\u0084\7 \2\2\u0083\u0082\3\2\2\2"+
		"\u0084\u0087\3\2\2\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0089"+
		"\3\2\2\2\u0087\u0085\3\2\2\2\u0088W\3\2\2\2\u0088d\3\2\2\2\u0088q\3\2"+
		"\2\2\u0088~\3\2\2\2\u0089\t\3\2\2\2\u008a\u008b\7\27\2\2\u008b\u008c\7"+
		"\24\2\2\u008c\u008e\7 \2\2\u008d\u008f\5\f\7\2\u008e\u008d\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\13\3\2\2"+
		"\2\u0092\u0094\7\27\2\2\u0093\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095"+
		"\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0098\7!"+
		"\2\2\u0098\u0099\7\"\2\2\u0099\u009a\7$\2\2\u009a\u009c\7\7\2\2\u009b"+
		"\u009d\7 \2\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\r\3\2\2\2"+
		"\u009e\u009f\7\27\2\2\u009f\u00a0\7\35\2\2\u00a0\u00a9\7 \2\2\u00a1\u00a3"+
		"\7\27\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a2\3\2\2\2"+
		"\u00a4\u00a5\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\5\34\17\2\u00a7\u00a8"+
		"\7 \2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a2\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\17\3\2\2\2\u00ad\u00ae\7\27\2"+
		"\2\u00ae\u00af\7\b\2\2\u00af\u00b1\7 \2\2\u00b0\u00b2\5\22\n\2\u00b1\u00b0"+
		"\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\21\3\2\2\2\u00b5\u00b7\7\27\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2"+
		"\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb"+
		"\7$\2\2\u00bb\u00bc\7#\2\2\u00bc\u00be\5\34\17\2\u00bd\u00bf\7 \2\2\u00be"+
		"\u00bd\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\23\3\2\2\2\u00c0\u00c1\7\27\2"+
		"\2\u00c1\u00c2\7\34\2\2\u00c2\u00c3\7\21\2\2\u00c3\u00c4\5\26\f\2\u00c4"+
		"\u00c5\7\23\2\2\u00c5\u00c6\5\30\r\2\u00c6\u00c7\7\36\2\2\u00c7\u00c8"+
		"\7 \2\2\u00c8\u00c9\5 \21\2\u00c9\25\3\2\2\2\u00ca\u00cb\7\22\2\2\u00cb"+
		"\u00cc\5\34\17\2\u00cc\27\3\2\2\2\u00cd\u00ce\7\3\2\2\u00ce\u00cf\5\34"+
		"\17\2\u00cf\31\3\2\2\2\u00d0\u00d1\7\4\2\2\u00d1\u00d2\5\34\17\2\u00d2"+
		"\33\3\2\2\2\u00d3\u00d4\7\21\2\2\u00d4\u00d5\5\34\17\2\u00d5\u00d6\7\36"+
		"\2\2\u00d6\u00e4\3\2\2\2\u00d7\u00d8\7\21\2\2\u00d8\u00d9\5\34\17\2\u00d9"+
		"\u00da\7\36\2\2\u00da\u00db\7#\2\2\u00db\u00dc\5\34\17\2\u00dc\u00e4\3"+
		"\2\2\2\u00dd\u00e4\5\36\20\2\u00de\u00e4\7$\2\2\u00df\u00e0\7$\2\2\u00e0"+
		"\u00e4\5\34\17\2\u00e1\u00e2\7#\2\2\u00e2\u00e4\5\34\17\2\u00e3\u00d3"+
		"\3\2\2\2\u00e3\u00d7\3\2\2\2\u00e3\u00dd\3\2\2\2\u00e3\u00de\3\2\2\2\u00e3"+
		"\u00df\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\35\3\2\2\2\u00e5\u00e6\7$\2\2"+
		"\u00e6\u00e7\7\21\2\2\u00e7\u00e8\7$\2\2\u00e8\u00e9\7$\2\2\u00e9\u00ea"+
		"\7\7\2\2\u00ea\u00eb\5\34\17\2\u00eb\u00ec\7\36\2\2\u00ec\37\3\2\2\2\u00ed"+
		"\u00ef\7\27\2\2\u00ee\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00ee\3"+
		"\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f3\5\"\22\2\u00f3"+
		"!\3\2\2\2\u00f4\u00ff\5$\23\2\u00f5\u00ff\5*\26\2\u00f6\u00ff\5,\27\2"+
		"\u00f7\u00ff\5\60\31\2\u00f8\u00ff\58\35\2\u00f9\u00ff\5:\36\2\u00fa\u00ff"+
		"\5<\37\2\u00fb\u00ff\5> \2\u00fc\u00ff\5@!\2\u00fd\u00ff\5D#\2\u00fe\u00f4"+
		"\3\2\2\2\u00fe\u00f5\3\2\2\2\u00fe\u00f6\3\2\2\2\u00fe\u00f7\3\2\2\2\u00fe"+
		"\u00f8\3\2\2\2\u00fe\u00f9\3\2\2\2\u00fe\u00fa\3\2\2\2\u00fe\u00fb\3\2"+
		"\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff#\3\2\2\2\u0100\u010e"+
		"\7$\2\2\u0101\u010e\5&\24\2\u0102\u0103\7\21\2\2\u0103\u0104\5$\23\2\u0104"+
		"\u0105\7\36\2\2\u0105\u010e\3\2\2\2\u0106\u0107\7$\2\2\u0107\u010e\7\13"+
		"\2\2\u0108\u0109\7$\2\2\u0109\u010e\5$\23\2\u010a\u010b\7$\2\2\u010b\u010c"+
		"\7#\2\2\u010c\u010e\5(\25\2\u010d\u0100\3\2\2\2\u010d\u0101\3\2\2\2\u010d"+
		"\u0102\3\2\2\2\u010d\u0106\3\2\2\2\u010d\u0108\3\2\2\2\u010d\u010a\3\2"+
		"\2\2\u010e\u010f\3\2\2\2\u010f\u0111\7\7\2\2\u0110\u0112\7 \2\2\u0111"+
		"\u0110\3\2\2\2\u0111\u0112\3\2\2\2\u0112%\3\2\2\2\u0113\u0114\7$\2\2\u0114"+
		"\u0115\5(\25\2\u0115\'\3\2\2\2\u0116\u0123\7$\2\2\u0117\u0118\7\21\2\2"+
		"\u0118\u0119\5(\25\2\u0119\u011a\7\36\2\2\u011a\u0123\3\2\2\2\u011b\u011c"+
		"\7$\2\2\u011c\u0123\7\13\2\2\u011d\u011e\7$\2\2\u011e\u0123\5(\25\2\u011f"+
		"\u0120\7$\2\2\u0120\u0121\7#\2\2\u0121\u0123\5(\25\2\u0122\u0116\3\2\2"+
		"\2\u0122\u0117\3\2\2\2\u0122\u011b\3\2\2\2\u0122\u011d\3\2\2\2\u0122\u011f"+
		"\3\2\2\2\u0123)\3\2\2\2\u0124\u0125\7\16\2\2\u0125\u0126\5\32\16\2\u0126"+
		"\u0127\7\36\2\2\u0127\u0128\7 \2\2\u0128\u0129\5 \21\2\u0129\u012a\5 "+
		"\21\2\u012a+\3\2\2\2\u012b\u012c\7\30\2\2\u012c\u012d\5.\30\2\u012d\u012e"+
		"\7\36\2\2\u012e\u0130\7 \2\2\u012f\u0131\5 \21\2\u0130\u012f\3\2\2\2\u0131"+
		"\u0132\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133-\3\2\2\2"+
		"\u0134\u0135\7\r\2\2\u0135\u013b\5\34\17\2\u0136\u0137\7\23\2\2\u0137"+
		"\u0138\7\r\2\2\u0138\u013a\5\34\17\2\u0139\u0136\3\2\2\2\u013a\u013d\3"+
		"\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c/\3\2\2\2\u013d\u013b"+
		"\3\2\2\2\u013e\u013f\7\31\2\2\u013f\u0140\5\62\32\2\u0140\u0141\7\23\2"+
		"\2\u0141\u0142\5\64\33\2\u0142\u0143\7\23\2\2\u0143\u0144\5\66\34\2\u0144"+
		"\u0145\7\36\2\2\u0145\u0146\7 \2\2\u0146\u0147\5 \21\2\u0147\61\3\2\2"+
		"\2\u0148\u0149\7\26\2\2\u0149\u014a\5\34\17\2\u014a\63\3\2\2\2\u014b\u014c"+
		"\7\r\2\2\u014c\u014d\5\34\17\2\u014d\65\3\2\2\2\u014e\u014f\7\5\2\2\u014f"+
		"\u0150\7$\2\2\u0150\67\3\2\2\2\u0151\u0152\7\f\2\2\u0152\u0153\5$\23\2"+
		"\u01539\3\2\2\2\u0154\u0155\7\20\2\2\u0155\u0156\5$\23\2\u0156;\3\2\2"+
		"\2\u0157\u0159\7\32\2\2\u0158\u015a\7 \2\2\u0159\u0158\3\2\2\2\u0159\u015a"+
		"\3\2\2\2\u015a=\3\2\2\2\u015b\u015c\7\25\2\2\u015c\u015d\5$\23\2\u015d"+
		"?\3\2\2\2\u015e\u015f\7\17\2\2\u015f\u0160\5B\"\2\u0160\u0161\7\23\2\2"+
		"\u0161\u0162\5\26\f\2\u0162\u0163\7\23\2\2\u0163\u0164\5\30\r\2\u0164"+
		"\u0165\7\36\2\2\u0165\u0166\7 \2\2\u0166\u0167\5D#\2\u0167A\3\2\2\2\u0168"+
		"\u0169\7\33\2\2\u0169\u016a\7$\2\2\u016aC\3\2\2\2\u016b\u016d\7\6\2\2"+
		"\u016c\u016e\7 \2\2\u016d\u016c\3\2\2\2\u016d\u016e\3\2\2\2\u016e\u0172"+
		"\3\2\2\2\u016f\u0171\5$\23\2\u0170\u016f\3\2\2\2\u0171\u0174\3\2\2\2\u0172"+
		"\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0175\3\2\2\2\u0174\u0172\3\2"+
		"\2\2\u0175\u0177\7\t\2\2\u0176\u0178\7 \2\2\u0177\u0176\3\2\2\2\u0177"+
		"\u0178\3\2\2\2\u0178E\3\2\2\2 RW^dkqx~\u0085\u0088\u0090\u0095\u009c\u00a4"+
		"\u00ab\u00b3\u00b8\u00be\u00e3\u00f0\u00fe\u010d\u0111\u0122\u0132\u013b"+
		"\u0159\u016d\u0172\u0177";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}