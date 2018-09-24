package de.tu_bs.cs.isf.cbc.textual.tool.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import de.tu_bs.cs.isf.cbc.textual.tool.services.DslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDslParser extends AbstractInternalContentAssistParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ANY_OTHER", "RULE_ID", "RULE_INT", 
		"RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_STRING", "RULE_WS", "'%'", 
		"'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "';'", "'='", 
		"'Formula'", "'GlobalConditions'", "'JavaVariables'", "'Renaming'", "'['", 
		"']'", "'conditions'", "'do'", "'elseif'", "'fi'", "'function'", "'if'", 
		"'intm:'", "'inv:'", "'newName'", "'od'", "'post:'", "'pre:'", "'renames'", 
		"'then'", "'type'", "'var:'", "'variables'", "'while'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int RULE_ANY_OTHER=4;
	public static final int RULE_ID=5;
	public static final int RULE_INT=6;
	public static final int RULE_ML_COMMENT=7;
	public static final int RULE_SL_COMMENT=8;
	public static final int RULE_STRING=9;
	public static final int RULE_WS=10;

	// delegates
	public AbstractInternalContentAssistParser[] getDelegates() {
		return new AbstractInternalContentAssistParser[] {};
	}

	// delegators


	public InternalDslParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public InternalDslParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return InternalDslParser.tokenNames; }
	@Override public String getGrammarFileName() { return "InternalDsl.g"; }


		private DslGrammarAccess grammarAccess;

		public void setGrammarAccess(DslGrammarAccess grammarAccess) {
			this.grammarAccess = grammarAccess;
		}

		@Override
		protected Grammar getGrammar() {
			return grammarAccess.getGrammar();
		}

		@Override
		protected String getValueForTokenName(String tokenName) {
			return tokenName;
		}



	// $ANTLR start "entryRuleCbCProblem"
	// InternalDsl.g:53:1: entryRuleCbCProblem : ruleCbCProblem EOF ;
	public final void entryRuleCbCProblem() throws RecognitionException {
		try {
			// InternalDsl.g:54:1: ( ruleCbCProblem EOF )
			// InternalDsl.g:55:1: ruleCbCProblem EOF
			{
			 before(grammarAccess.getCbCProblemRule()); 
			pushFollow(FOLLOW_1);
			ruleCbCProblem();
			state._fsp--;

			 after(grammarAccess.getCbCProblemRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleCbCProblem"



	// $ANTLR start "ruleCbCProblem"
	// InternalDsl.g:62:1: ruleCbCProblem : ( ( rule__CbCProblem__UnorderedGroup ) ) ;
	public final void ruleCbCProblem() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:66:2: ( ( ( rule__CbCProblem__UnorderedGroup ) ) )
			// InternalDsl.g:67:2: ( ( rule__CbCProblem__UnorderedGroup ) )
			{
			// InternalDsl.g:67:2: ( ( rule__CbCProblem__UnorderedGroup ) )
			// InternalDsl.g:68:3: ( rule__CbCProblem__UnorderedGroup )
			{
			 before(grammarAccess.getCbCProblemAccess().getUnorderedGroup()); 
			// InternalDsl.g:69:3: ( rule__CbCProblem__UnorderedGroup )
			// InternalDsl.g:69:4: rule__CbCProblem__UnorderedGroup
			{
			pushFollow(FOLLOW_2);
			rule__CbCProblem__UnorderedGroup();
			state._fsp--;

			}

			 after(grammarAccess.getCbCProblemAccess().getUnorderedGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleCbCProblem"



	// $ANTLR start "entryRuleCbCFormula"
	// InternalDsl.g:78:1: entryRuleCbCFormula : ruleCbCFormula EOF ;
	public final void entryRuleCbCFormula() throws RecognitionException {
		try {
			// InternalDsl.g:79:1: ( ruleCbCFormula EOF )
			// InternalDsl.g:80:1: ruleCbCFormula EOF
			{
			 before(grammarAccess.getCbCFormulaRule()); 
			pushFollow(FOLLOW_1);
			ruleCbCFormula();
			state._fsp--;

			 after(grammarAccess.getCbCFormulaRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleCbCFormula"



	// $ANTLR start "ruleCbCFormula"
	// InternalDsl.g:87:1: ruleCbCFormula : ( ( rule__CbCFormula__Group__0 ) ) ;
	public final void ruleCbCFormula() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:91:2: ( ( ( rule__CbCFormula__Group__0 ) ) )
			// InternalDsl.g:92:2: ( ( rule__CbCFormula__Group__0 ) )
			{
			// InternalDsl.g:92:2: ( ( rule__CbCFormula__Group__0 ) )
			// InternalDsl.g:93:3: ( rule__CbCFormula__Group__0 )
			{
			 before(grammarAccess.getCbCFormulaAccess().getGroup()); 
			// InternalDsl.g:94:3: ( rule__CbCFormula__Group__0 )
			// InternalDsl.g:94:4: rule__CbCFormula__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getCbCFormulaAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleCbCFormula"



	// $ANTLR start "entryRuleAbstractStatement"
	// InternalDsl.g:103:1: entryRuleAbstractStatement : ruleAbstractStatement EOF ;
	public final void entryRuleAbstractStatement() throws RecognitionException {
		try {
			// InternalDsl.g:104:1: ( ruleAbstractStatement EOF )
			// InternalDsl.g:105:1: ruleAbstractStatement EOF
			{
			 before(grammarAccess.getAbstractStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getAbstractStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleAbstractStatement"



	// $ANTLR start "ruleAbstractStatement"
	// InternalDsl.g:112:1: ruleAbstractStatement : ( ( rule__AbstractStatement__Alternatives ) ) ;
	public final void ruleAbstractStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:116:2: ( ( ( rule__AbstractStatement__Alternatives ) ) )
			// InternalDsl.g:117:2: ( ( rule__AbstractStatement__Alternatives ) )
			{
			// InternalDsl.g:117:2: ( ( rule__AbstractStatement__Alternatives ) )
			// InternalDsl.g:118:3: ( rule__AbstractStatement__Alternatives )
			{
			 before(grammarAccess.getAbstractStatementAccess().getAlternatives()); 
			// InternalDsl.g:119:3: ( rule__AbstractStatement__Alternatives )
			// InternalDsl.g:119:4: rule__AbstractStatement__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__AbstractStatement__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getAbstractStatementAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleAbstractStatement"



	// $ANTLR start "entryRuleEString"
	// InternalDsl.g:128:1: entryRuleEString : ruleEString EOF ;
	public final void entryRuleEString() throws RecognitionException {
		try {
			// InternalDsl.g:129:1: ( ruleEString EOF )
			// InternalDsl.g:130:1: ruleEString EOF
			{
			 before(grammarAccess.getEStringRule()); 
			pushFollow(FOLLOW_1);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getEStringRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleEString"



	// $ANTLR start "ruleEString"
	// InternalDsl.g:137:1: ruleEString : ( ( rule__EString__Alternatives ) ) ;
	public final void ruleEString() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:141:2: ( ( ( rule__EString__Alternatives ) ) )
			// InternalDsl.g:142:2: ( ( rule__EString__Alternatives ) )
			{
			// InternalDsl.g:142:2: ( ( rule__EString__Alternatives ) )
			// InternalDsl.g:143:3: ( rule__EString__Alternatives )
			{
			 before(grammarAccess.getEStringAccess().getAlternatives()); 
			// InternalDsl.g:144:3: ( rule__EString__Alternatives )
			// InternalDsl.g:144:4: rule__EString__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__EString__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getEStringAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleEString"



	// $ANTLR start "entryRuleCodeString"
	// InternalDsl.g:153:1: entryRuleCodeString : ruleCodeString EOF ;
	public final void entryRuleCodeString() throws RecognitionException {
		try {
			// InternalDsl.g:154:1: ( ruleCodeString EOF )
			// InternalDsl.g:155:1: ruleCodeString EOF
			{
			 before(grammarAccess.getCodeStringRule()); 
			pushFollow(FOLLOW_1);
			ruleCodeString();
			state._fsp--;

			 after(grammarAccess.getCodeStringRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleCodeString"



	// $ANTLR start "ruleCodeString"
	// InternalDsl.g:162:1: ruleCodeString : ( ( ( rule__CodeString__Group__0 ) ) ( ( rule__CodeString__Group__0 )* ) ) ;
	public final void ruleCodeString() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:166:2: ( ( ( ( rule__CodeString__Group__0 ) ) ( ( rule__CodeString__Group__0 )* ) ) )
			// InternalDsl.g:167:2: ( ( ( rule__CodeString__Group__0 ) ) ( ( rule__CodeString__Group__0 )* ) )
			{
			// InternalDsl.g:167:2: ( ( ( rule__CodeString__Group__0 ) ) ( ( rule__CodeString__Group__0 )* ) )
			// InternalDsl.g:168:3: ( ( rule__CodeString__Group__0 ) ) ( ( rule__CodeString__Group__0 )* )
			{
			// InternalDsl.g:168:3: ( ( rule__CodeString__Group__0 ) )
			// InternalDsl.g:169:4: ( rule__CodeString__Group__0 )
			{
			 before(grammarAccess.getCodeStringAccess().getGroup()); 
			// InternalDsl.g:170:4: ( rule__CodeString__Group__0 )
			// InternalDsl.g:170:5: rule__CodeString__Group__0
			{
			pushFollow(FOLLOW_3);
			rule__CodeString__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getCodeStringAccess().getGroup()); 
			}

			// InternalDsl.g:173:3: ( ( rule__CodeString__Group__0 )* )
			// InternalDsl.g:174:4: ( rule__CodeString__Group__0 )*
			{
			 before(grammarAccess.getCodeStringAccess().getGroup()); 
			// InternalDsl.g:175:4: ( rule__CodeString__Group__0 )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==RULE_ID) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// InternalDsl.g:175:5: rule__CodeString__Group__0
					{
					pushFollow(FOLLOW_3);
					rule__CodeString__Group__0();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			 after(grammarAccess.getCodeStringAccess().getGroup()); 
			}

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleCodeString"



	// $ANTLR start "entryRuleVariableString"
	// InternalDsl.g:185:1: entryRuleVariableString : ruleVariableString EOF ;
	public final void entryRuleVariableString() throws RecognitionException {
		try {
			// InternalDsl.g:186:1: ( ruleVariableString EOF )
			// InternalDsl.g:187:1: ruleVariableString EOF
			{
			 before(grammarAccess.getVariableStringRule()); 
			pushFollow(FOLLOW_1);
			ruleVariableString();
			state._fsp--;

			 after(grammarAccess.getVariableStringRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleVariableString"



	// $ANTLR start "ruleVariableString"
	// InternalDsl.g:194:1: ruleVariableString : ( ( rule__VariableString__Group__0 ) ) ;
	public final void ruleVariableString() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:198:2: ( ( ( rule__VariableString__Group__0 ) ) )
			// InternalDsl.g:199:2: ( ( rule__VariableString__Group__0 ) )
			{
			// InternalDsl.g:199:2: ( ( rule__VariableString__Group__0 ) )
			// InternalDsl.g:200:3: ( rule__VariableString__Group__0 )
			{
			 before(grammarAccess.getVariableStringAccess().getGroup()); 
			// InternalDsl.g:201:3: ( rule__VariableString__Group__0 )
			// InternalDsl.g:201:4: rule__VariableString__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleVariableString"



	// $ANTLR start "entryRuleOperation"
	// InternalDsl.g:210:1: entryRuleOperation : ruleOperation EOF ;
	public final void entryRuleOperation() throws RecognitionException {
		try {
			// InternalDsl.g:211:1: ( ruleOperation EOF )
			// InternalDsl.g:212:1: ruleOperation EOF
			{
			 before(grammarAccess.getOperationRule()); 
			pushFollow(FOLLOW_1);
			ruleOperation();
			state._fsp--;

			 after(grammarAccess.getOperationRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleOperation"



	// $ANTLR start "ruleOperation"
	// InternalDsl.g:219:1: ruleOperation : ( ( rule__Operation__Alternatives ) ) ;
	public final void ruleOperation() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:223:2: ( ( ( rule__Operation__Alternatives ) ) )
			// InternalDsl.g:224:2: ( ( rule__Operation__Alternatives ) )
			{
			// InternalDsl.g:224:2: ( ( rule__Operation__Alternatives ) )
			// InternalDsl.g:225:3: ( rule__Operation__Alternatives )
			{
			 before(grammarAccess.getOperationAccess().getAlternatives()); 
			// InternalDsl.g:226:3: ( rule__Operation__Alternatives )
			// InternalDsl.g:226:4: rule__Operation__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__Operation__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getOperationAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleOperation"



	// $ANTLR start "entryRuleAbstractStatement_Impl"
	// InternalDsl.g:235:1: entryRuleAbstractStatement_Impl : ruleAbstractStatement_Impl EOF ;
	public final void entryRuleAbstractStatement_Impl() throws RecognitionException {
		try {
			// InternalDsl.g:236:1: ( ruleAbstractStatement_Impl EOF )
			// InternalDsl.g:237:1: ruleAbstractStatement_Impl EOF
			{
			 before(grammarAccess.getAbstractStatement_ImplRule()); 
			pushFollow(FOLLOW_1);
			ruleAbstractStatement_Impl();
			state._fsp--;

			 after(grammarAccess.getAbstractStatement_ImplRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleAbstractStatement_Impl"



	// $ANTLR start "ruleAbstractStatement_Impl"
	// InternalDsl.g:244:1: ruleAbstractStatement_Impl : ( ( rule__AbstractStatement_Impl__Group__0 ) ) ;
	public final void ruleAbstractStatement_Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:248:2: ( ( ( rule__AbstractStatement_Impl__Group__0 ) ) )
			// InternalDsl.g:249:2: ( ( rule__AbstractStatement_Impl__Group__0 ) )
			{
			// InternalDsl.g:249:2: ( ( rule__AbstractStatement_Impl__Group__0 ) )
			// InternalDsl.g:250:3: ( rule__AbstractStatement_Impl__Group__0 )
			{
			 before(grammarAccess.getAbstractStatement_ImplAccess().getGroup()); 
			// InternalDsl.g:251:3: ( rule__AbstractStatement_Impl__Group__0 )
			// InternalDsl.g:251:4: rule__AbstractStatement_Impl__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__AbstractStatement_Impl__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getAbstractStatement_ImplAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleAbstractStatement_Impl"



	// $ANTLR start "entryRuleMethodStatement"
	// InternalDsl.g:260:1: entryRuleMethodStatement : ruleMethodStatement EOF ;
	public final void entryRuleMethodStatement() throws RecognitionException {
		try {
			// InternalDsl.g:261:1: ( ruleMethodStatement EOF )
			// InternalDsl.g:262:1: ruleMethodStatement EOF
			{
			 before(grammarAccess.getMethodStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleMethodStatement();
			state._fsp--;

			 after(grammarAccess.getMethodStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleMethodStatement"



	// $ANTLR start "ruleMethodStatement"
	// InternalDsl.g:269:1: ruleMethodStatement : ( ( rule__MethodStatement__Group__0 ) ) ;
	public final void ruleMethodStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:273:2: ( ( ( rule__MethodStatement__Group__0 ) ) )
			// InternalDsl.g:274:2: ( ( rule__MethodStatement__Group__0 ) )
			{
			// InternalDsl.g:274:2: ( ( rule__MethodStatement__Group__0 ) )
			// InternalDsl.g:275:3: ( rule__MethodStatement__Group__0 )
			{
			 before(grammarAccess.getMethodStatementAccess().getGroup()); 
			// InternalDsl.g:276:3: ( rule__MethodStatement__Group__0 )
			// InternalDsl.g:276:4: rule__MethodStatement__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__MethodStatement__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getMethodStatementAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleMethodStatement"



	// $ANTLR start "entryRuleCondition"
	// InternalDsl.g:285:1: entryRuleCondition : ruleCondition EOF ;
	public final void entryRuleCondition() throws RecognitionException {
		try {
			// InternalDsl.g:286:1: ( ruleCondition EOF )
			// InternalDsl.g:287:1: ruleCondition EOF
			{
			 before(grammarAccess.getConditionRule()); 
			pushFollow(FOLLOW_1);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getConditionRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleCondition"



	// $ANTLR start "ruleCondition"
	// InternalDsl.g:294:1: ruleCondition : ( ( rule__Condition__Group__0 ) ) ;
	public final void ruleCondition() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:298:2: ( ( ( rule__Condition__Group__0 ) ) )
			// InternalDsl.g:299:2: ( ( rule__Condition__Group__0 ) )
			{
			// InternalDsl.g:299:2: ( ( rule__Condition__Group__0 ) )
			// InternalDsl.g:300:3: ( rule__Condition__Group__0 )
			{
			 before(grammarAccess.getConditionAccess().getGroup()); 
			// InternalDsl.g:301:3: ( rule__Condition__Group__0 )
			// InternalDsl.g:301:4: rule__Condition__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Condition__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getConditionAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleCondition"



	// $ANTLR start "entryRuleSkipStatement"
	// InternalDsl.g:310:1: entryRuleSkipStatement : ruleSkipStatement EOF ;
	public final void entryRuleSkipStatement() throws RecognitionException {
		try {
			// InternalDsl.g:311:1: ( ruleSkipStatement EOF )
			// InternalDsl.g:312:1: ruleSkipStatement EOF
			{
			 before(grammarAccess.getSkipStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleSkipStatement();
			state._fsp--;

			 after(grammarAccess.getSkipStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleSkipStatement"



	// $ANTLR start "ruleSkipStatement"
	// InternalDsl.g:319:1: ruleSkipStatement : ( ( rule__SkipStatement__NameAssignment ) ) ;
	public final void ruleSkipStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:323:2: ( ( ( rule__SkipStatement__NameAssignment ) ) )
			// InternalDsl.g:324:2: ( ( rule__SkipStatement__NameAssignment ) )
			{
			// InternalDsl.g:324:2: ( ( rule__SkipStatement__NameAssignment ) )
			// InternalDsl.g:325:3: ( rule__SkipStatement__NameAssignment )
			{
			 before(grammarAccess.getSkipStatementAccess().getNameAssignment()); 
			// InternalDsl.g:326:3: ( rule__SkipStatement__NameAssignment )
			// InternalDsl.g:326:4: rule__SkipStatement__NameAssignment
			{
			pushFollow(FOLLOW_2);
			rule__SkipStatement__NameAssignment();
			state._fsp--;

			}

			 after(grammarAccess.getSkipStatementAccess().getNameAssignment()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleSkipStatement"



	// $ANTLR start "entryRuleCompositionStatement"
	// InternalDsl.g:335:1: entryRuleCompositionStatement : ruleCompositionStatement EOF ;
	public final void entryRuleCompositionStatement() throws RecognitionException {
		try {
			// InternalDsl.g:336:1: ( ruleCompositionStatement EOF )
			// InternalDsl.g:337:1: ruleCompositionStatement EOF
			{
			 before(grammarAccess.getCompositionStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleCompositionStatement();
			state._fsp--;

			 after(grammarAccess.getCompositionStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleCompositionStatement"



	// $ANTLR start "ruleCompositionStatement"
	// InternalDsl.g:344:1: ruleCompositionStatement : ( ( rule__CompositionStatement__Group__0 ) ) ;
	public final void ruleCompositionStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:348:2: ( ( ( rule__CompositionStatement__Group__0 ) ) )
			// InternalDsl.g:349:2: ( ( rule__CompositionStatement__Group__0 ) )
			{
			// InternalDsl.g:349:2: ( ( rule__CompositionStatement__Group__0 ) )
			// InternalDsl.g:350:3: ( rule__CompositionStatement__Group__0 )
			{
			 before(grammarAccess.getCompositionStatementAccess().getGroup()); 
			// InternalDsl.g:351:3: ( rule__CompositionStatement__Group__0 )
			// InternalDsl.g:351:4: rule__CompositionStatement__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getCompositionStatementAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleCompositionStatement"



	// $ANTLR start "entryRuleSelectionStatement"
	// InternalDsl.g:360:1: entryRuleSelectionStatement : ruleSelectionStatement EOF ;
	public final void entryRuleSelectionStatement() throws RecognitionException {
		try {
			// InternalDsl.g:361:1: ( ruleSelectionStatement EOF )
			// InternalDsl.g:362:1: ruleSelectionStatement EOF
			{
			 before(grammarAccess.getSelectionStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleSelectionStatement();
			state._fsp--;

			 after(grammarAccess.getSelectionStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleSelectionStatement"



	// $ANTLR start "ruleSelectionStatement"
	// InternalDsl.g:369:1: ruleSelectionStatement : ( ( rule__SelectionStatement__Group__0 ) ) ;
	public final void ruleSelectionStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:373:2: ( ( ( rule__SelectionStatement__Group__0 ) ) )
			// InternalDsl.g:374:2: ( ( rule__SelectionStatement__Group__0 ) )
			{
			// InternalDsl.g:374:2: ( ( rule__SelectionStatement__Group__0 ) )
			// InternalDsl.g:375:3: ( rule__SelectionStatement__Group__0 )
			{
			 before(grammarAccess.getSelectionStatementAccess().getGroup()); 
			// InternalDsl.g:376:3: ( rule__SelectionStatement__Group__0 )
			// InternalDsl.g:376:4: rule__SelectionStatement__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getSelectionStatementAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleSelectionStatement"



	// $ANTLR start "entryRuleSmallRepetitionStatement"
	// InternalDsl.g:385:1: entryRuleSmallRepetitionStatement : ruleSmallRepetitionStatement EOF ;
	public final void entryRuleSmallRepetitionStatement() throws RecognitionException {
		try {
			// InternalDsl.g:386:1: ( ruleSmallRepetitionStatement EOF )
			// InternalDsl.g:387:1: ruleSmallRepetitionStatement EOF
			{
			 before(grammarAccess.getSmallRepetitionStatementRule()); 
			pushFollow(FOLLOW_1);
			ruleSmallRepetitionStatement();
			state._fsp--;

			 after(grammarAccess.getSmallRepetitionStatementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleSmallRepetitionStatement"



	// $ANTLR start "ruleSmallRepetitionStatement"
	// InternalDsl.g:394:1: ruleSmallRepetitionStatement : ( ( rule__SmallRepetitionStatement__Group__0 ) ) ;
	public final void ruleSmallRepetitionStatement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:398:2: ( ( ( rule__SmallRepetitionStatement__Group__0 ) ) )
			// InternalDsl.g:399:2: ( ( rule__SmallRepetitionStatement__Group__0 ) )
			{
			// InternalDsl.g:399:2: ( ( rule__SmallRepetitionStatement__Group__0 ) )
			// InternalDsl.g:400:3: ( rule__SmallRepetitionStatement__Group__0 )
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getGroup()); 
			// InternalDsl.g:401:3: ( rule__SmallRepetitionStatement__Group__0 )
			// InternalDsl.g:401:4: rule__SmallRepetitionStatement__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleSmallRepetitionStatement"



	// $ANTLR start "entryRuleVariant"
	// InternalDsl.g:410:1: entryRuleVariant : ruleVariant EOF ;
	public final void entryRuleVariant() throws RecognitionException {
		try {
			// InternalDsl.g:411:1: ( ruleVariant EOF )
			// InternalDsl.g:412:1: ruleVariant EOF
			{
			 before(grammarAccess.getVariantRule()); 
			pushFollow(FOLLOW_1);
			ruleVariant();
			state._fsp--;

			 after(grammarAccess.getVariantRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleVariant"



	// $ANTLR start "ruleVariant"
	// InternalDsl.g:419:1: ruleVariant : ( ( rule__Variant__Group__0 ) ) ;
	public final void ruleVariant() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:423:2: ( ( ( rule__Variant__Group__0 ) ) )
			// InternalDsl.g:424:2: ( ( rule__Variant__Group__0 ) )
			{
			// InternalDsl.g:424:2: ( ( rule__Variant__Group__0 ) )
			// InternalDsl.g:425:3: ( rule__Variant__Group__0 )
			{
			 before(grammarAccess.getVariantAccess().getGroup()); 
			// InternalDsl.g:426:3: ( rule__Variant__Group__0 )
			// InternalDsl.g:426:4: rule__Variant__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Variant__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getVariantAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleVariant"



	// $ANTLR start "entryRuleJavaVariables"
	// InternalDsl.g:435:1: entryRuleJavaVariables : ruleJavaVariables EOF ;
	public final void entryRuleJavaVariables() throws RecognitionException {
		try {
			// InternalDsl.g:436:1: ( ruleJavaVariables EOF )
			// InternalDsl.g:437:1: ruleJavaVariables EOF
			{
			 before(grammarAccess.getJavaVariablesRule()); 
			pushFollow(FOLLOW_1);
			ruleJavaVariables();
			state._fsp--;

			 after(grammarAccess.getJavaVariablesRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleJavaVariables"



	// $ANTLR start "ruleJavaVariables"
	// InternalDsl.g:444:1: ruleJavaVariables : ( ( rule__JavaVariables__Group__0 ) ) ;
	public final void ruleJavaVariables() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:448:2: ( ( ( rule__JavaVariables__Group__0 ) ) )
			// InternalDsl.g:449:2: ( ( rule__JavaVariables__Group__0 ) )
			{
			// InternalDsl.g:449:2: ( ( rule__JavaVariables__Group__0 ) )
			// InternalDsl.g:450:3: ( rule__JavaVariables__Group__0 )
			{
			 before(grammarAccess.getJavaVariablesAccess().getGroup()); 
			// InternalDsl.g:451:3: ( rule__JavaVariables__Group__0 )
			// InternalDsl.g:451:4: rule__JavaVariables__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getJavaVariablesAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleJavaVariables"



	// $ANTLR start "entryRuleJavaVariable"
	// InternalDsl.g:460:1: entryRuleJavaVariable : ruleJavaVariable EOF ;
	public final void entryRuleJavaVariable() throws RecognitionException {
		try {
			// InternalDsl.g:461:1: ( ruleJavaVariable EOF )
			// InternalDsl.g:462:1: ruleJavaVariable EOF
			{
			 before(grammarAccess.getJavaVariableRule()); 
			pushFollow(FOLLOW_1);
			ruleJavaVariable();
			state._fsp--;

			 after(grammarAccess.getJavaVariableRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleJavaVariable"



	// $ANTLR start "ruleJavaVariable"
	// InternalDsl.g:469:1: ruleJavaVariable : ( ( rule__JavaVariable__Group__0 ) ) ;
	public final void ruleJavaVariable() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:473:2: ( ( ( rule__JavaVariable__Group__0 ) ) )
			// InternalDsl.g:474:2: ( ( rule__JavaVariable__Group__0 ) )
			{
			// InternalDsl.g:474:2: ( ( rule__JavaVariable__Group__0 ) )
			// InternalDsl.g:475:3: ( rule__JavaVariable__Group__0 )
			{
			 before(grammarAccess.getJavaVariableAccess().getGroup()); 
			// InternalDsl.g:476:3: ( rule__JavaVariable__Group__0 )
			// InternalDsl.g:476:4: rule__JavaVariable__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariable__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getJavaVariableAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleJavaVariable"



	// $ANTLR start "entryRuleGlobalConditions"
	// InternalDsl.g:485:1: entryRuleGlobalConditions : ruleGlobalConditions EOF ;
	public final void entryRuleGlobalConditions() throws RecognitionException {
		try {
			// InternalDsl.g:486:1: ( ruleGlobalConditions EOF )
			// InternalDsl.g:487:1: ruleGlobalConditions EOF
			{
			 before(grammarAccess.getGlobalConditionsRule()); 
			pushFollow(FOLLOW_1);
			ruleGlobalConditions();
			state._fsp--;

			 after(grammarAccess.getGlobalConditionsRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleGlobalConditions"



	// $ANTLR start "ruleGlobalConditions"
	// InternalDsl.g:494:1: ruleGlobalConditions : ( ( rule__GlobalConditions__Group__0 ) ) ;
	public final void ruleGlobalConditions() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:498:2: ( ( ( rule__GlobalConditions__Group__0 ) ) )
			// InternalDsl.g:499:2: ( ( rule__GlobalConditions__Group__0 ) )
			{
			// InternalDsl.g:499:2: ( ( rule__GlobalConditions__Group__0 ) )
			// InternalDsl.g:500:3: ( rule__GlobalConditions__Group__0 )
			{
			 before(grammarAccess.getGlobalConditionsAccess().getGroup()); 
			// InternalDsl.g:501:3: ( rule__GlobalConditions__Group__0 )
			// InternalDsl.g:501:4: rule__GlobalConditions__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getGlobalConditionsAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleGlobalConditions"



	// $ANTLR start "entryRuleRenaming"
	// InternalDsl.g:510:1: entryRuleRenaming : ruleRenaming EOF ;
	public final void entryRuleRenaming() throws RecognitionException {
		try {
			// InternalDsl.g:511:1: ( ruleRenaming EOF )
			// InternalDsl.g:512:1: ruleRenaming EOF
			{
			 before(grammarAccess.getRenamingRule()); 
			pushFollow(FOLLOW_1);
			ruleRenaming();
			state._fsp--;

			 after(grammarAccess.getRenamingRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleRenaming"



	// $ANTLR start "ruleRenaming"
	// InternalDsl.g:519:1: ruleRenaming : ( ( rule__Renaming__Group__0 ) ) ;
	public final void ruleRenaming() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:523:2: ( ( ( rule__Renaming__Group__0 ) ) )
			// InternalDsl.g:524:2: ( ( rule__Renaming__Group__0 ) )
			{
			// InternalDsl.g:524:2: ( ( rule__Renaming__Group__0 ) )
			// InternalDsl.g:525:3: ( rule__Renaming__Group__0 )
			{
			 before(grammarAccess.getRenamingAccess().getGroup()); 
			// InternalDsl.g:526:3: ( rule__Renaming__Group__0 )
			// InternalDsl.g:526:4: rule__Renaming__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Renaming__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getRenamingAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleRenaming"



	// $ANTLR start "entryRuleRename"
	// InternalDsl.g:535:1: entryRuleRename : ruleRename EOF ;
	public final void entryRuleRename() throws RecognitionException {
		try {
			// InternalDsl.g:536:1: ( ruleRename EOF )
			// InternalDsl.g:537:1: ruleRename EOF
			{
			 before(grammarAccess.getRenameRule()); 
			pushFollow(FOLLOW_1);
			ruleRename();
			state._fsp--;

			 after(grammarAccess.getRenameRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleRename"



	// $ANTLR start "ruleRename"
	// InternalDsl.g:544:1: ruleRename : ( ( rule__Rename__Group__0 ) ) ;
	public final void ruleRename() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:548:2: ( ( ( rule__Rename__Group__0 ) ) )
			// InternalDsl.g:549:2: ( ( rule__Rename__Group__0 ) )
			{
			// InternalDsl.g:549:2: ( ( rule__Rename__Group__0 ) )
			// InternalDsl.g:550:3: ( rule__Rename__Group__0 )
			{
			 before(grammarAccess.getRenameAccess().getGroup()); 
			// InternalDsl.g:551:3: ( rule__Rename__Group__0 )
			// InternalDsl.g:551:4: rule__Rename__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Rename__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getRenameAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleRename"



	// $ANTLR start "rule__AbstractStatement__Alternatives"
	// InternalDsl.g:559:1: rule__AbstractStatement__Alternatives : ( ( ruleAbstractStatement_Impl ) | ( ruleSkipStatement ) | ( ruleCompositionStatement ) | ( ruleSelectionStatement ) | ( ruleSmallRepetitionStatement ) | ( ruleMethodStatement ) );
	public final void rule__AbstractStatement__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:563:2: ( ( ruleAbstractStatement_Impl ) | ( ruleSkipStatement ) | ( ruleCompositionStatement ) | ( ruleSelectionStatement ) | ( ruleSmallRepetitionStatement ) | ( ruleMethodStatement ) )
			int alt2=6;
			switch ( input.LA(1) ) {
			case RULE_ID:
				{
				int LA2_1 = input.LA(2);
				if ( (LA2_1==21||LA2_1==26) ) {
					alt2=1;
				}
				else if ( (LA2_1==EOF||LA2_1==47) ) {
					alt2=6;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 2, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 20:
				{
				alt2=2;
				}
				break;
			case 46:
				{
				alt2=3;
				}
				break;
			case 33:
				{
				alt2=4;
				}
				break;
			case 45:
				{
				alt2=5;
				}
				break;
			case RULE_STRING:
				{
				alt2=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// InternalDsl.g:564:2: ( ruleAbstractStatement_Impl )
					{
					// InternalDsl.g:564:2: ( ruleAbstractStatement_Impl )
					// InternalDsl.g:565:3: ruleAbstractStatement_Impl
					{
					 before(grammarAccess.getAbstractStatementAccess().getAbstractStatement_ImplParserRuleCall_0()); 
					pushFollow(FOLLOW_2);
					ruleAbstractStatement_Impl();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getAbstractStatement_ImplParserRuleCall_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:570:2: ( ruleSkipStatement )
					{
					// InternalDsl.g:570:2: ( ruleSkipStatement )
					// InternalDsl.g:571:3: ruleSkipStatement
					{
					 before(grammarAccess.getAbstractStatementAccess().getSkipStatementParserRuleCall_1()); 
					pushFollow(FOLLOW_2);
					ruleSkipStatement();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getSkipStatementParserRuleCall_1()); 
					}

					}
					break;
				case 3 :
					// InternalDsl.g:576:2: ( ruleCompositionStatement )
					{
					// InternalDsl.g:576:2: ( ruleCompositionStatement )
					// InternalDsl.g:577:3: ruleCompositionStatement
					{
					 before(grammarAccess.getAbstractStatementAccess().getCompositionStatementParserRuleCall_2()); 
					pushFollow(FOLLOW_2);
					ruleCompositionStatement();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getCompositionStatementParserRuleCall_2()); 
					}

					}
					break;
				case 4 :
					// InternalDsl.g:582:2: ( ruleSelectionStatement )
					{
					// InternalDsl.g:582:2: ( ruleSelectionStatement )
					// InternalDsl.g:583:3: ruleSelectionStatement
					{
					 before(grammarAccess.getAbstractStatementAccess().getSelectionStatementParserRuleCall_3()); 
					pushFollow(FOLLOW_2);
					ruleSelectionStatement();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getSelectionStatementParserRuleCall_3()); 
					}

					}
					break;
				case 5 :
					// InternalDsl.g:588:2: ( ruleSmallRepetitionStatement )
					{
					// InternalDsl.g:588:2: ( ruleSmallRepetitionStatement )
					// InternalDsl.g:589:3: ruleSmallRepetitionStatement
					{
					 before(grammarAccess.getAbstractStatementAccess().getSmallRepetitionStatementParserRuleCall_4()); 
					pushFollow(FOLLOW_2);
					ruleSmallRepetitionStatement();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getSmallRepetitionStatementParserRuleCall_4()); 
					}

					}
					break;
				case 6 :
					// InternalDsl.g:594:2: ( ruleMethodStatement )
					{
					// InternalDsl.g:594:2: ( ruleMethodStatement )
					// InternalDsl.g:595:3: ruleMethodStatement
					{
					 before(grammarAccess.getAbstractStatementAccess().getMethodStatementParserRuleCall_5()); 
					pushFollow(FOLLOW_2);
					ruleMethodStatement();
					state._fsp--;

					 after(grammarAccess.getAbstractStatementAccess().getMethodStatementParserRuleCall_5()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement__Alternatives"



	// $ANTLR start "rule__EString__Alternatives"
	// InternalDsl.g:604:1: rule__EString__Alternatives : ( ( RULE_STRING ) | ( RULE_ID ) );
	public final void rule__EString__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:608:2: ( ( RULE_STRING ) | ( RULE_ID ) )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==RULE_STRING) ) {
				alt3=1;
			}
			else if ( (LA3_0==RULE_ID) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// InternalDsl.g:609:2: ( RULE_STRING )
					{
					// InternalDsl.g:609:2: ( RULE_STRING )
					// InternalDsl.g:610:3: RULE_STRING
					{
					 before(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 
					match(input,RULE_STRING,FOLLOW_2); 
					 after(grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:615:2: ( RULE_ID )
					{
					// InternalDsl.g:615:2: ( RULE_ID )
					// InternalDsl.g:616:3: RULE_ID
					{
					 before(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 
					match(input,RULE_ID,FOLLOW_2); 
					 after(grammarAccess.getEStringAccess().getIDTerminalRuleCall_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__EString__Alternatives"



	// $ANTLR start "rule__CodeString__Alternatives_1_1"
	// InternalDsl.g:625:1: rule__CodeString__Alternatives_1_1 : ( ( RULE_ID ) | ( RULE_INT ) );
	public final void rule__CodeString__Alternatives_1_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:629:2: ( ( RULE_ID ) | ( RULE_INT ) )
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==RULE_ID) ) {
				alt4=1;
			}
			else if ( (LA4_0==RULE_INT) ) {
				alt4=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// InternalDsl.g:630:2: ( RULE_ID )
					{
					// InternalDsl.g:630:2: ( RULE_ID )
					// InternalDsl.g:631:3: RULE_ID
					{
					 before(grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_1_1_0()); 
					match(input,RULE_ID,FOLLOW_2); 
					 after(grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_1_1_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:636:2: ( RULE_INT )
					{
					// InternalDsl.g:636:2: ( RULE_INT )
					// InternalDsl.g:637:3: RULE_INT
					{
					 before(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_1_1_1()); 
					match(input,RULE_INT,FOLLOW_2); 
					 after(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_1_1_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Alternatives_1_1"



	// $ANTLR start "rule__CodeString__Alternatives_3"
	// InternalDsl.g:646:1: rule__CodeString__Alternatives_3 : ( ( ruleVariableString ) | ( ( rule__CodeString__Group_3_1__0 ) ) );
	public final void rule__CodeString__Alternatives_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:650:2: ( ( ruleVariableString ) | ( ( rule__CodeString__Group_3_1__0 ) ) )
			int alt5=2;
			switch ( input.LA(1) ) {
			case 17:
				{
				int LA5_1 = input.LA(2);
				if ( (LA5_1==RULE_ID) ) {
					alt5=1;
				}
				else if ( (LA5_1==RULE_INT) ) {
					alt5=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 5, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case RULE_ID:
				{
				alt5=1;
				}
				break;
			case RULE_INT:
				{
				alt5=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}
			switch (alt5) {
				case 1 :
					// InternalDsl.g:651:2: ( ruleVariableString )
					{
					// InternalDsl.g:651:2: ( ruleVariableString )
					// InternalDsl.g:652:3: ruleVariableString
					{
					 before(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_3_0()); 
					pushFollow(FOLLOW_2);
					ruleVariableString();
					state._fsp--;

					 after(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_3_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:657:2: ( ( rule__CodeString__Group_3_1__0 ) )
					{
					// InternalDsl.g:657:2: ( ( rule__CodeString__Group_3_1__0 ) )
					// InternalDsl.g:658:3: ( rule__CodeString__Group_3_1__0 )
					{
					 before(grammarAccess.getCodeStringAccess().getGroup_3_1()); 
					// InternalDsl.g:659:3: ( rule__CodeString__Group_3_1__0 )
					// InternalDsl.g:659:4: rule__CodeString__Group_3_1__0
					{
					pushFollow(FOLLOW_2);
					rule__CodeString__Group_3_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getCodeStringAccess().getGroup_3_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Alternatives_3"



	// $ANTLR start "rule__CodeString__Alternatives_4_1"
	// InternalDsl.g:667:1: rule__CodeString__Alternatives_4_1 : ( ( ruleVariableString ) | ( ( rule__CodeString__Group_4_1_1__0 ) ) );
	public final void rule__CodeString__Alternatives_4_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:671:2: ( ( ruleVariableString ) | ( ( rule__CodeString__Group_4_1_1__0 ) ) )
			int alt6=2;
			switch ( input.LA(1) ) {
			case 17:
				{
				int LA6_1 = input.LA(2);
				if ( (LA6_1==RULE_ID) ) {
					alt6=1;
				}
				else if ( (LA6_1==RULE_INT) ) {
					alt6=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 6, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case RULE_ID:
				{
				alt6=1;
				}
				break;
			case RULE_INT:
				{
				alt6=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// InternalDsl.g:672:2: ( ruleVariableString )
					{
					// InternalDsl.g:672:2: ( ruleVariableString )
					// InternalDsl.g:673:3: ruleVariableString
					{
					 before(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_4_1_0()); 
					pushFollow(FOLLOW_2);
					ruleVariableString();
					state._fsp--;

					 after(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_4_1_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:678:2: ( ( rule__CodeString__Group_4_1_1__0 ) )
					{
					// InternalDsl.g:678:2: ( ( rule__CodeString__Group_4_1_1__0 ) )
					// InternalDsl.g:679:3: ( rule__CodeString__Group_4_1_1__0 )
					{
					 before(grammarAccess.getCodeStringAccess().getGroup_4_1_1()); 
					// InternalDsl.g:680:3: ( rule__CodeString__Group_4_1_1__0 )
					// InternalDsl.g:680:4: rule__CodeString__Group_4_1_1__0
					{
					pushFollow(FOLLOW_2);
					rule__CodeString__Group_4_1_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getCodeStringAccess().getGroup_4_1_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Alternatives_4_1"



	// $ANTLR start "rule__VariableString__Alternatives_2"
	// InternalDsl.g:688:1: rule__VariableString__Alternatives_2 : ( ( ( rule__VariableString__Group_2_0__0 ) ) | ( ( rule__VariableString__Group_2_1__0 ) ) | ( ( rule__VariableString__Group_2_2__0 ) ) );
	public final void rule__VariableString__Alternatives_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:692:2: ( ( ( rule__VariableString__Group_2_0__0 ) ) | ( ( rule__VariableString__Group_2_1__0 ) ) | ( ( rule__VariableString__Group_2_2__0 ) ) )
			int alt7=3;
			switch ( input.LA(1) ) {
			case 12:
				{
				alt7=1;
				}
				break;
			case 26:
				{
				alt7=2;
				}
				break;
			case 18:
				{
				alt7=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}
			switch (alt7) {
				case 1 :
					// InternalDsl.g:693:2: ( ( rule__VariableString__Group_2_0__0 ) )
					{
					// InternalDsl.g:693:2: ( ( rule__VariableString__Group_2_0__0 ) )
					// InternalDsl.g:694:3: ( rule__VariableString__Group_2_0__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_0()); 
					// InternalDsl.g:695:3: ( rule__VariableString__Group_2_0__0 )
					// InternalDsl.g:695:4: rule__VariableString__Group_2_0__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_0__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:699:2: ( ( rule__VariableString__Group_2_1__0 ) )
					{
					// InternalDsl.g:699:2: ( ( rule__VariableString__Group_2_1__0 ) )
					// InternalDsl.g:700:3: ( rule__VariableString__Group_2_1__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_1()); 
					// InternalDsl.g:701:3: ( rule__VariableString__Group_2_1__0 )
					// InternalDsl.g:701:4: rule__VariableString__Group_2_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_1()); 
					}

					}
					break;
				case 3 :
					// InternalDsl.g:705:2: ( ( rule__VariableString__Group_2_2__0 ) )
					{
					// InternalDsl.g:705:2: ( ( rule__VariableString__Group_2_2__0 ) )
					// InternalDsl.g:706:3: ( rule__VariableString__Group_2_2__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_2()); 
					// InternalDsl.g:707:3: ( rule__VariableString__Group_2_2__0 )
					// InternalDsl.g:707:4: rule__VariableString__Group_2_2__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_2__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_2()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Alternatives_2"



	// $ANTLR start "rule__VariableString__Alternatives_2_0_0_1_0"
	// InternalDsl.g:715:1: rule__VariableString__Alternatives_2_0_0_1_0 : ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_0_0_1_0_1__0 ) ) );
	public final void rule__VariableString__Alternatives_2_0_0_1_0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:719:2: ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_0_0_1_0_1__0 ) ) )
			int alt8=2;
			switch ( input.LA(1) ) {
			case 17:
				{
				int LA8_1 = input.LA(2);
				if ( (LA8_1==RULE_ID) ) {
					alt8=1;
				}
				else if ( (LA8_1==RULE_INT) ) {
					alt8=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 8, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case RULE_ID:
				{
				alt8=1;
				}
				break;
			case RULE_INT:
				{
				alt8=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}
			switch (alt8) {
				case 1 :
					// InternalDsl.g:720:2: ( ruleVariableString )
					{
					// InternalDsl.g:720:2: ( ruleVariableString )
					// InternalDsl.g:721:3: ruleVariableString
					{
					 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_0_0()); 
					pushFollow(FOLLOW_2);
					ruleVariableString();
					state._fsp--;

					 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_0_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:726:2: ( ( rule__VariableString__Group_2_0_0_1_0_1__0 ) )
					{
					// InternalDsl.g:726:2: ( ( rule__VariableString__Group_2_0_0_1_0_1__0 ) )
					// InternalDsl.g:727:3: ( rule__VariableString__Group_2_0_0_1_0_1__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_0_1()); 
					// InternalDsl.g:728:3: ( rule__VariableString__Group_2_0_0_1_0_1__0 )
					// InternalDsl.g:728:4: rule__VariableString__Group_2_0_0_1_0_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_0_0_1_0_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_0_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Alternatives_2_0_0_1_0"



	// $ANTLR start "rule__VariableString__Alternatives_2_0_0_1_1_1"
	// InternalDsl.g:736:1: rule__VariableString__Alternatives_2_0_0_1_1_1 : ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_0_0_1_1_1_1__0 ) ) );
	public final void rule__VariableString__Alternatives_2_0_0_1_1_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:740:2: ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_0_0_1_1_1_1__0 ) ) )
			int alt9=2;
			switch ( input.LA(1) ) {
			case 17:
				{
				int LA9_1 = input.LA(2);
				if ( (LA9_1==RULE_ID) ) {
					alt9=1;
				}
				else if ( (LA9_1==RULE_INT) ) {
					alt9=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 9, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case RULE_ID:
				{
				alt9=1;
				}
				break;
			case RULE_INT:
				{
				alt9=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}
			switch (alt9) {
				case 1 :
					// InternalDsl.g:741:2: ( ruleVariableString )
					{
					// InternalDsl.g:741:2: ( ruleVariableString )
					// InternalDsl.g:742:3: ruleVariableString
					{
					 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_1_1_0()); 
					pushFollow(FOLLOW_2);
					ruleVariableString();
					state._fsp--;

					 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_1_1_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:747:2: ( ( rule__VariableString__Group_2_0_0_1_1_1_1__0 ) )
					{
					// InternalDsl.g:747:2: ( ( rule__VariableString__Group_2_0_0_1_1_1_1__0 ) )
					// InternalDsl.g:748:3: ( rule__VariableString__Group_2_0_0_1_1_1_1__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_1_1_1()); 
					// InternalDsl.g:749:3: ( rule__VariableString__Group_2_0_0_1_1_1_1__0 )
					// InternalDsl.g:749:4: rule__VariableString__Group_2_0_0_1_1_1_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_0_0_1_1_1_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_1_1_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Alternatives_2_0_0_1_1_1"



	// $ANTLR start "rule__VariableString__Alternatives_2_1_0_1"
	// InternalDsl.g:757:1: rule__VariableString__Alternatives_2_1_0_1 : ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_1_0_1_1__0 ) ) );
	public final void rule__VariableString__Alternatives_2_1_0_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:761:2: ( ( ruleVariableString ) | ( ( rule__VariableString__Group_2_1_0_1_1__0 ) ) )
			int alt10=2;
			switch ( input.LA(1) ) {
			case 17:
				{
				int LA10_1 = input.LA(2);
				if ( (LA10_1==RULE_ID) ) {
					alt10=1;
				}
				else if ( (LA10_1==RULE_INT) ) {
					alt10=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 10, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case RULE_ID:
				{
				alt10=1;
				}
				break;
			case RULE_INT:
				{
				alt10=2;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 10, 0, input);
				throw nvae;
			}
			switch (alt10) {
				case 1 :
					// InternalDsl.g:762:2: ( ruleVariableString )
					{
					// InternalDsl.g:762:2: ( ruleVariableString )
					// InternalDsl.g:763:3: ruleVariableString
					{
					 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_0_1_0()); 
					pushFollow(FOLLOW_2);
					ruleVariableString();
					state._fsp--;

					 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_0_1_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:768:2: ( ( rule__VariableString__Group_2_1_0_1_1__0 ) )
					{
					// InternalDsl.g:768:2: ( ( rule__VariableString__Group_2_1_0_1_1__0 ) )
					// InternalDsl.g:769:3: ( rule__VariableString__Group_2_1_0_1_1__0 )
					{
					 before(grammarAccess.getVariableStringAccess().getGroup_2_1_0_1_1()); 
					// InternalDsl.g:770:3: ( rule__VariableString__Group_2_1_0_1_1__0 )
					// InternalDsl.g:770:4: rule__VariableString__Group_2_1_0_1_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_1_0_1_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getVariableStringAccess().getGroup_2_1_0_1_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Alternatives_2_1_0_1"



	// $ANTLR start "rule__Operation__Alternatives"
	// InternalDsl.g:778:1: rule__Operation__Alternatives : ( ( '+' ) | ( '-' ) | ( '*' ) | ( '/' ) | ( '%' ) );
	public final void rule__Operation__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:782:2: ( ( '+' ) | ( '-' ) | ( '*' ) | ( '/' ) | ( '%' ) )
			int alt11=5;
			switch ( input.LA(1) ) {
			case 15:
				{
				alt11=1;
				}
				break;
			case 17:
				{
				alt11=2;
				}
				break;
			case 14:
				{
				alt11=3;
				}
				break;
			case 19:
				{
				alt11=4;
				}
				break;
			case 11:
				{
				alt11=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// InternalDsl.g:783:2: ( '+' )
					{
					// InternalDsl.g:783:2: ( '+' )
					// InternalDsl.g:784:3: '+'
					{
					 before(grammarAccess.getOperationAccess().getPlusSignKeyword_0()); 
					match(input,15,FOLLOW_2); 
					 after(grammarAccess.getOperationAccess().getPlusSignKeyword_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:789:2: ( '-' )
					{
					// InternalDsl.g:789:2: ( '-' )
					// InternalDsl.g:790:3: '-'
					{
					 before(grammarAccess.getOperationAccess().getHyphenMinusKeyword_1()); 
					match(input,17,FOLLOW_2); 
					 after(grammarAccess.getOperationAccess().getHyphenMinusKeyword_1()); 
					}

					}
					break;
				case 3 :
					// InternalDsl.g:795:2: ( '*' )
					{
					// InternalDsl.g:795:2: ( '*' )
					// InternalDsl.g:796:3: '*'
					{
					 before(grammarAccess.getOperationAccess().getAsteriskKeyword_2()); 
					match(input,14,FOLLOW_2); 
					 after(grammarAccess.getOperationAccess().getAsteriskKeyword_2()); 
					}

					}
					break;
				case 4 :
					// InternalDsl.g:801:2: ( '/' )
					{
					// InternalDsl.g:801:2: ( '/' )
					// InternalDsl.g:802:3: '/'
					{
					 before(grammarAccess.getOperationAccess().getSolidusKeyword_3()); 
					match(input,19,FOLLOW_2); 
					 after(grammarAccess.getOperationAccess().getSolidusKeyword_3()); 
					}

					}
					break;
				case 5 :
					// InternalDsl.g:807:2: ( '%' )
					{
					// InternalDsl.g:807:2: ( '%' )
					// InternalDsl.g:808:3: '%'
					{
					 before(grammarAccess.getOperationAccess().getPercentSignKeyword_4()); 
					match(input,11,FOLLOW_2); 
					 after(grammarAccess.getOperationAccess().getPercentSignKeyword_4()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Operation__Alternatives"



	// $ANTLR start "rule__CbCFormula__Group__0"
	// InternalDsl.g:817:1: rule__CbCFormula__Group__0 : rule__CbCFormula__Group__0__Impl rule__CbCFormula__Group__1 ;
	public final void rule__CbCFormula__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:821:2: ( rule__CbCFormula__Group__0__Impl rule__CbCFormula__Group__1 )
			// InternalDsl.g:822:2: rule__CbCFormula__Group__0__Impl rule__CbCFormula__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__CbCFormula__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__0"



	// $ANTLR start "rule__CbCFormula__Group__0__Impl"
	// InternalDsl.g:829:1: rule__CbCFormula__Group__0__Impl : ( 'Formula' ) ;
	public final void rule__CbCFormula__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:833:2: ( ( 'Formula' ) )
			// InternalDsl.g:834:2: ( 'Formula' )
			{
			// InternalDsl.g:834:2: ( 'Formula' )
			// InternalDsl.g:835:2: 'Formula'
			{
			 before(grammarAccess.getCbCFormulaAccess().getFormulaKeyword_0()); 
			match(input,22,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getFormulaKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__0__Impl"



	// $ANTLR start "rule__CbCFormula__Group__1"
	// InternalDsl.g:844:1: rule__CbCFormula__Group__1 : rule__CbCFormula__Group__1__Impl rule__CbCFormula__Group__2 ;
	public final void rule__CbCFormula__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:848:2: ( rule__CbCFormula__Group__1__Impl rule__CbCFormula__Group__2 )
			// InternalDsl.g:849:2: rule__CbCFormula__Group__1__Impl rule__CbCFormula__Group__2
			{
			pushFollow(FOLLOW_5);
			rule__CbCFormula__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__1"



	// $ANTLR start "rule__CbCFormula__Group__1__Impl"
	// InternalDsl.g:856:1: rule__CbCFormula__Group__1__Impl : ( ( rule__CbCFormula__NameAssignment_1 ) ) ;
	public final void rule__CbCFormula__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:860:2: ( ( ( rule__CbCFormula__NameAssignment_1 ) ) )
			// InternalDsl.g:861:2: ( ( rule__CbCFormula__NameAssignment_1 ) )
			{
			// InternalDsl.g:861:2: ( ( rule__CbCFormula__NameAssignment_1 ) )
			// InternalDsl.g:862:2: ( rule__CbCFormula__NameAssignment_1 )
			{
			 before(grammarAccess.getCbCFormulaAccess().getNameAssignment_1()); 
			// InternalDsl.g:863:2: ( rule__CbCFormula__NameAssignment_1 )
			// InternalDsl.g:863:3: rule__CbCFormula__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getCbCFormulaAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__1__Impl"



	// $ANTLR start "rule__CbCFormula__Group__2"
	// InternalDsl.g:871:1: rule__CbCFormula__Group__2 : rule__CbCFormula__Group__2__Impl rule__CbCFormula__Group__3 ;
	public final void rule__CbCFormula__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:875:2: ( rule__CbCFormula__Group__2__Impl rule__CbCFormula__Group__3 )
			// InternalDsl.g:876:2: rule__CbCFormula__Group__2__Impl rule__CbCFormula__Group__3
			{
			pushFollow(FOLLOW_6);
			rule__CbCFormula__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__2"



	// $ANTLR start "rule__CbCFormula__Group__2__Impl"
	// InternalDsl.g:883:1: rule__CbCFormula__Group__2__Impl : ( 'pre:' ) ;
	public final void rule__CbCFormula__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:887:2: ( ( 'pre:' ) )
			// InternalDsl.g:888:2: ( 'pre:' )
			{
			// InternalDsl.g:888:2: ( 'pre:' )
			// InternalDsl.g:889:2: 'pre:'
			{
			 before(grammarAccess.getCbCFormulaAccess().getPreKeyword_2()); 
			match(input,39,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getPreKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__2__Impl"



	// $ANTLR start "rule__CbCFormula__Group__3"
	// InternalDsl.g:898:1: rule__CbCFormula__Group__3 : rule__CbCFormula__Group__3__Impl rule__CbCFormula__Group__4 ;
	public final void rule__CbCFormula__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:902:2: ( rule__CbCFormula__Group__3__Impl rule__CbCFormula__Group__4 )
			// InternalDsl.g:903:2: rule__CbCFormula__Group__3__Impl rule__CbCFormula__Group__4
			{
			pushFollow(FOLLOW_4);
			rule__CbCFormula__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__3"



	// $ANTLR start "rule__CbCFormula__Group__3__Impl"
	// InternalDsl.g:910:1: rule__CbCFormula__Group__3__Impl : ( '{' ) ;
	public final void rule__CbCFormula__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:914:2: ( ( '{' ) )
			// InternalDsl.g:915:2: ( '{' )
			{
			// InternalDsl.g:915:2: ( '{' )
			// InternalDsl.g:916:2: '{'
			{
			 before(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_3()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__3__Impl"



	// $ANTLR start "rule__CbCFormula__Group__4"
	// InternalDsl.g:925:1: rule__CbCFormula__Group__4 : rule__CbCFormula__Group__4__Impl rule__CbCFormula__Group__5 ;
	public final void rule__CbCFormula__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:929:2: ( rule__CbCFormula__Group__4__Impl rule__CbCFormula__Group__5 )
			// InternalDsl.g:930:2: rule__CbCFormula__Group__4__Impl rule__CbCFormula__Group__5
			{
			pushFollow(FOLLOW_7);
			rule__CbCFormula__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__4"



	// $ANTLR start "rule__CbCFormula__Group__4__Impl"
	// InternalDsl.g:937:1: rule__CbCFormula__Group__4__Impl : ( ( rule__CbCFormula__PreConditionAssignment_4 ) ) ;
	public final void rule__CbCFormula__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:941:2: ( ( ( rule__CbCFormula__PreConditionAssignment_4 ) ) )
			// InternalDsl.g:942:2: ( ( rule__CbCFormula__PreConditionAssignment_4 ) )
			{
			// InternalDsl.g:942:2: ( ( rule__CbCFormula__PreConditionAssignment_4 ) )
			// InternalDsl.g:943:2: ( rule__CbCFormula__PreConditionAssignment_4 )
			{
			 before(grammarAccess.getCbCFormulaAccess().getPreConditionAssignment_4()); 
			// InternalDsl.g:944:2: ( rule__CbCFormula__PreConditionAssignment_4 )
			// InternalDsl.g:944:3: rule__CbCFormula__PreConditionAssignment_4
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__PreConditionAssignment_4();
			state._fsp--;

			}

			 after(grammarAccess.getCbCFormulaAccess().getPreConditionAssignment_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__4__Impl"



	// $ANTLR start "rule__CbCFormula__Group__5"
	// InternalDsl.g:952:1: rule__CbCFormula__Group__5 : rule__CbCFormula__Group__5__Impl rule__CbCFormula__Group__6 ;
	public final void rule__CbCFormula__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:956:2: ( rule__CbCFormula__Group__5__Impl rule__CbCFormula__Group__6 )
			// InternalDsl.g:957:2: rule__CbCFormula__Group__5__Impl rule__CbCFormula__Group__6
			{
			pushFollow(FOLLOW_6);
			rule__CbCFormula__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__5"



	// $ANTLR start "rule__CbCFormula__Group__5__Impl"
	// InternalDsl.g:964:1: rule__CbCFormula__Group__5__Impl : ( '}' ) ;
	public final void rule__CbCFormula__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:968:2: ( ( '}' ) )
			// InternalDsl.g:969:2: ( '}' )
			{
			// InternalDsl.g:969:2: ( '}' )
			// InternalDsl.g:970:2: '}'
			{
			 before(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_5()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__5__Impl"



	// $ANTLR start "rule__CbCFormula__Group__6"
	// InternalDsl.g:979:1: rule__CbCFormula__Group__6 : rule__CbCFormula__Group__6__Impl rule__CbCFormula__Group__7 ;
	public final void rule__CbCFormula__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:983:2: ( rule__CbCFormula__Group__6__Impl rule__CbCFormula__Group__7 )
			// InternalDsl.g:984:2: rule__CbCFormula__Group__6__Impl rule__CbCFormula__Group__7
			{
			pushFollow(FOLLOW_8);
			rule__CbCFormula__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__6"



	// $ANTLR start "rule__CbCFormula__Group__6__Impl"
	// InternalDsl.g:991:1: rule__CbCFormula__Group__6__Impl : ( '{' ) ;
	public final void rule__CbCFormula__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:995:2: ( ( '{' ) )
			// InternalDsl.g:996:2: ( '{' )
			{
			// InternalDsl.g:996:2: ( '{' )
			// InternalDsl.g:997:2: '{'
			{
			 before(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_6()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__6__Impl"



	// $ANTLR start "rule__CbCFormula__Group__7"
	// InternalDsl.g:1006:1: rule__CbCFormula__Group__7 : rule__CbCFormula__Group__7__Impl rule__CbCFormula__Group__8 ;
	public final void rule__CbCFormula__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1010:2: ( rule__CbCFormula__Group__7__Impl rule__CbCFormula__Group__8 )
			// InternalDsl.g:1011:2: rule__CbCFormula__Group__7__Impl rule__CbCFormula__Group__8
			{
			pushFollow(FOLLOW_7);
			rule__CbCFormula__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__7"



	// $ANTLR start "rule__CbCFormula__Group__7__Impl"
	// InternalDsl.g:1018:1: rule__CbCFormula__Group__7__Impl : ( ( rule__CbCFormula__StatementAssignment_7 ) ) ;
	public final void rule__CbCFormula__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1022:2: ( ( ( rule__CbCFormula__StatementAssignment_7 ) ) )
			// InternalDsl.g:1023:2: ( ( rule__CbCFormula__StatementAssignment_7 ) )
			{
			// InternalDsl.g:1023:2: ( ( rule__CbCFormula__StatementAssignment_7 ) )
			// InternalDsl.g:1024:2: ( rule__CbCFormula__StatementAssignment_7 )
			{
			 before(grammarAccess.getCbCFormulaAccess().getStatementAssignment_7()); 
			// InternalDsl.g:1025:2: ( rule__CbCFormula__StatementAssignment_7 )
			// InternalDsl.g:1025:3: rule__CbCFormula__StatementAssignment_7
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__StatementAssignment_7();
			state._fsp--;

			}

			 after(grammarAccess.getCbCFormulaAccess().getStatementAssignment_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__7__Impl"



	// $ANTLR start "rule__CbCFormula__Group__8"
	// InternalDsl.g:1033:1: rule__CbCFormula__Group__8 : rule__CbCFormula__Group__8__Impl rule__CbCFormula__Group__9 ;
	public final void rule__CbCFormula__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1037:2: ( rule__CbCFormula__Group__8__Impl rule__CbCFormula__Group__9 )
			// InternalDsl.g:1038:2: rule__CbCFormula__Group__8__Impl rule__CbCFormula__Group__9
			{
			pushFollow(FOLLOW_9);
			rule__CbCFormula__Group__8__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__9();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__8"



	// $ANTLR start "rule__CbCFormula__Group__8__Impl"
	// InternalDsl.g:1045:1: rule__CbCFormula__Group__8__Impl : ( '}' ) ;
	public final void rule__CbCFormula__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1049:2: ( ( '}' ) )
			// InternalDsl.g:1050:2: ( '}' )
			{
			// InternalDsl.g:1050:2: ( '}' )
			// InternalDsl.g:1051:2: '}'
			{
			 before(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_8()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__8__Impl"



	// $ANTLR start "rule__CbCFormula__Group__9"
	// InternalDsl.g:1060:1: rule__CbCFormula__Group__9 : rule__CbCFormula__Group__9__Impl rule__CbCFormula__Group__10 ;
	public final void rule__CbCFormula__Group__9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1064:2: ( rule__CbCFormula__Group__9__Impl rule__CbCFormula__Group__10 )
			// InternalDsl.g:1065:2: rule__CbCFormula__Group__9__Impl rule__CbCFormula__Group__10
			{
			pushFollow(FOLLOW_6);
			rule__CbCFormula__Group__9__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__10();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__9"



	// $ANTLR start "rule__CbCFormula__Group__9__Impl"
	// InternalDsl.g:1072:1: rule__CbCFormula__Group__9__Impl : ( 'post:' ) ;
	public final void rule__CbCFormula__Group__9__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1076:2: ( ( 'post:' ) )
			// InternalDsl.g:1077:2: ( 'post:' )
			{
			// InternalDsl.g:1077:2: ( 'post:' )
			// InternalDsl.g:1078:2: 'post:'
			{
			 before(grammarAccess.getCbCFormulaAccess().getPostKeyword_9()); 
			match(input,38,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getPostKeyword_9()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__9__Impl"



	// $ANTLR start "rule__CbCFormula__Group__10"
	// InternalDsl.g:1087:1: rule__CbCFormula__Group__10 : rule__CbCFormula__Group__10__Impl rule__CbCFormula__Group__11 ;
	public final void rule__CbCFormula__Group__10() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1091:2: ( rule__CbCFormula__Group__10__Impl rule__CbCFormula__Group__11 )
			// InternalDsl.g:1092:2: rule__CbCFormula__Group__10__Impl rule__CbCFormula__Group__11
			{
			pushFollow(FOLLOW_4);
			rule__CbCFormula__Group__10__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__11();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__10"



	// $ANTLR start "rule__CbCFormula__Group__10__Impl"
	// InternalDsl.g:1099:1: rule__CbCFormula__Group__10__Impl : ( '{' ) ;
	public final void rule__CbCFormula__Group__10__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1103:2: ( ( '{' ) )
			// InternalDsl.g:1104:2: ( '{' )
			{
			// InternalDsl.g:1104:2: ( '{' )
			// InternalDsl.g:1105:2: '{'
			{
			 before(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_10()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_10()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__10__Impl"



	// $ANTLR start "rule__CbCFormula__Group__11"
	// InternalDsl.g:1114:1: rule__CbCFormula__Group__11 : rule__CbCFormula__Group__11__Impl rule__CbCFormula__Group__12 ;
	public final void rule__CbCFormula__Group__11() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1118:2: ( rule__CbCFormula__Group__11__Impl rule__CbCFormula__Group__12 )
			// InternalDsl.g:1119:2: rule__CbCFormula__Group__11__Impl rule__CbCFormula__Group__12
			{
			pushFollow(FOLLOW_7);
			rule__CbCFormula__Group__11__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__12();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__11"



	// $ANTLR start "rule__CbCFormula__Group__11__Impl"
	// InternalDsl.g:1126:1: rule__CbCFormula__Group__11__Impl : ( ( rule__CbCFormula__PostConditionAssignment_11 ) ) ;
	public final void rule__CbCFormula__Group__11__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1130:2: ( ( ( rule__CbCFormula__PostConditionAssignment_11 ) ) )
			// InternalDsl.g:1131:2: ( ( rule__CbCFormula__PostConditionAssignment_11 ) )
			{
			// InternalDsl.g:1131:2: ( ( rule__CbCFormula__PostConditionAssignment_11 ) )
			// InternalDsl.g:1132:2: ( rule__CbCFormula__PostConditionAssignment_11 )
			{
			 before(grammarAccess.getCbCFormulaAccess().getPostConditionAssignment_11()); 
			// InternalDsl.g:1133:2: ( rule__CbCFormula__PostConditionAssignment_11 )
			// InternalDsl.g:1133:3: rule__CbCFormula__PostConditionAssignment_11
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__PostConditionAssignment_11();
			state._fsp--;

			}

			 after(grammarAccess.getCbCFormulaAccess().getPostConditionAssignment_11()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__11__Impl"



	// $ANTLR start "rule__CbCFormula__Group__12"
	// InternalDsl.g:1141:1: rule__CbCFormula__Group__12 : rule__CbCFormula__Group__12__Impl ;
	public final void rule__CbCFormula__Group__12() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1145:2: ( rule__CbCFormula__Group__12__Impl )
			// InternalDsl.g:1146:2: rule__CbCFormula__Group__12__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CbCFormula__Group__12__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__12"



	// $ANTLR start "rule__CbCFormula__Group__12__Impl"
	// InternalDsl.g:1152:1: rule__CbCFormula__Group__12__Impl : ( '}' ) ;
	public final void rule__CbCFormula__Group__12__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1156:2: ( ( '}' ) )
			// InternalDsl.g:1157:2: ( '}' )
			{
			// InternalDsl.g:1157:2: ( '}' )
			// InternalDsl.g:1158:2: '}'
			{
			 before(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_12()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_12()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__Group__12__Impl"



	// $ANTLR start "rule__CodeString__Group__0"
	// InternalDsl.g:1168:1: rule__CodeString__Group__0 : rule__CodeString__Group__0__Impl rule__CodeString__Group__1 ;
	public final void rule__CodeString__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1172:2: ( rule__CodeString__Group__0__Impl rule__CodeString__Group__1 )
			// InternalDsl.g:1173:2: rule__CodeString__Group__0__Impl rule__CodeString__Group__1
			{
			pushFollow(FOLLOW_10);
			rule__CodeString__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__0"



	// $ANTLR start "rule__CodeString__Group__0__Impl"
	// InternalDsl.g:1180:1: rule__CodeString__Group__0__Impl : ( RULE_ID ) ;
	public final void rule__CodeString__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1184:2: ( ( RULE_ID ) )
			// InternalDsl.g:1185:2: ( RULE_ID )
			{
			// InternalDsl.g:1185:2: ( RULE_ID )
			// InternalDsl.g:1186:2: RULE_ID
			{
			 before(grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_0()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__0__Impl"



	// $ANTLR start "rule__CodeString__Group__1"
	// InternalDsl.g:1195:1: rule__CodeString__Group__1 : rule__CodeString__Group__1__Impl rule__CodeString__Group__2 ;
	public final void rule__CodeString__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1199:2: ( rule__CodeString__Group__1__Impl rule__CodeString__Group__2 )
			// InternalDsl.g:1200:2: rule__CodeString__Group__1__Impl rule__CodeString__Group__2
			{
			pushFollow(FOLLOW_11);
			rule__CodeString__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__1"



	// $ANTLR start "rule__CodeString__Group__1__Impl"
	// InternalDsl.g:1207:1: rule__CodeString__Group__1__Impl : ( ( rule__CodeString__Group_1__0 )? ) ;
	public final void rule__CodeString__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1211:2: ( ( ( rule__CodeString__Group_1__0 )? ) )
			// InternalDsl.g:1212:2: ( ( rule__CodeString__Group_1__0 )? )
			{
			// InternalDsl.g:1212:2: ( ( rule__CodeString__Group_1__0 )? )
			// InternalDsl.g:1213:2: ( rule__CodeString__Group_1__0 )?
			{
			 before(grammarAccess.getCodeStringAccess().getGroup_1()); 
			// InternalDsl.g:1214:2: ( rule__CodeString__Group_1__0 )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==26) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// InternalDsl.g:1214:3: rule__CodeString__Group_1__0
					{
					pushFollow(FOLLOW_2);
					rule__CodeString__Group_1__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getCodeStringAccess().getGroup_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__1__Impl"



	// $ANTLR start "rule__CodeString__Group__2"
	// InternalDsl.g:1222:1: rule__CodeString__Group__2 : rule__CodeString__Group__2__Impl rule__CodeString__Group__3 ;
	public final void rule__CodeString__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1226:2: ( rule__CodeString__Group__2__Impl rule__CodeString__Group__3 )
			// InternalDsl.g:1227:2: rule__CodeString__Group__2__Impl rule__CodeString__Group__3
			{
			pushFollow(FOLLOW_12);
			rule__CodeString__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__2"



	// $ANTLR start "rule__CodeString__Group__2__Impl"
	// InternalDsl.g:1234:1: rule__CodeString__Group__2__Impl : ( '=' ) ;
	public final void rule__CodeString__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1238:2: ( ( '=' ) )
			// InternalDsl.g:1239:2: ( '=' )
			{
			// InternalDsl.g:1239:2: ( '=' )
			// InternalDsl.g:1240:2: '='
			{
			 before(grammarAccess.getCodeStringAccess().getEqualsSignKeyword_2()); 
			match(input,21,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getEqualsSignKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__2__Impl"



	// $ANTLR start "rule__CodeString__Group__3"
	// InternalDsl.g:1249:1: rule__CodeString__Group__3 : rule__CodeString__Group__3__Impl rule__CodeString__Group__4 ;
	public final void rule__CodeString__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1253:2: ( rule__CodeString__Group__3__Impl rule__CodeString__Group__4 )
			// InternalDsl.g:1254:2: rule__CodeString__Group__3__Impl rule__CodeString__Group__4
			{
			pushFollow(FOLLOW_13);
			rule__CodeString__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__3"



	// $ANTLR start "rule__CodeString__Group__3__Impl"
	// InternalDsl.g:1261:1: rule__CodeString__Group__3__Impl : ( ( rule__CodeString__Alternatives_3 ) ) ;
	public final void rule__CodeString__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1265:2: ( ( ( rule__CodeString__Alternatives_3 ) ) )
			// InternalDsl.g:1266:2: ( ( rule__CodeString__Alternatives_3 ) )
			{
			// InternalDsl.g:1266:2: ( ( rule__CodeString__Alternatives_3 ) )
			// InternalDsl.g:1267:2: ( rule__CodeString__Alternatives_3 )
			{
			 before(grammarAccess.getCodeStringAccess().getAlternatives_3()); 
			// InternalDsl.g:1268:2: ( rule__CodeString__Alternatives_3 )
			// InternalDsl.g:1268:3: rule__CodeString__Alternatives_3
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Alternatives_3();
			state._fsp--;

			}

			 after(grammarAccess.getCodeStringAccess().getAlternatives_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__3__Impl"



	// $ANTLR start "rule__CodeString__Group__4"
	// InternalDsl.g:1276:1: rule__CodeString__Group__4 : rule__CodeString__Group__4__Impl rule__CodeString__Group__5 ;
	public final void rule__CodeString__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1280:2: ( rule__CodeString__Group__4__Impl rule__CodeString__Group__5 )
			// InternalDsl.g:1281:2: rule__CodeString__Group__4__Impl rule__CodeString__Group__5
			{
			pushFollow(FOLLOW_14);
			rule__CodeString__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__4"



	// $ANTLR start "rule__CodeString__Group__4__Impl"
	// InternalDsl.g:1288:1: rule__CodeString__Group__4__Impl : ( ( rule__CodeString__Group_4__0 )* ) ;
	public final void rule__CodeString__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1292:2: ( ( ( rule__CodeString__Group_4__0 )* ) )
			// InternalDsl.g:1293:2: ( ( rule__CodeString__Group_4__0 )* )
			{
			// InternalDsl.g:1293:2: ( ( rule__CodeString__Group_4__0 )* )
			// InternalDsl.g:1294:2: ( rule__CodeString__Group_4__0 )*
			{
			 before(grammarAccess.getCodeStringAccess().getGroup_4()); 
			// InternalDsl.g:1295:2: ( rule__CodeString__Group_4__0 )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==11||(LA13_0 >= 14 && LA13_0 <= 15)||LA13_0==17||LA13_0==19) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// InternalDsl.g:1295:3: rule__CodeString__Group_4__0
					{
					pushFollow(FOLLOW_15);
					rule__CodeString__Group_4__0();
					state._fsp--;

					}
					break;

				default :
					break loop13;
				}
			}

			 after(grammarAccess.getCodeStringAccess().getGroup_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__4__Impl"



	// $ANTLR start "rule__CodeString__Group__5"
	// InternalDsl.g:1303:1: rule__CodeString__Group__5 : rule__CodeString__Group__5__Impl ;
	public final void rule__CodeString__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1307:2: ( rule__CodeString__Group__5__Impl )
			// InternalDsl.g:1308:2: rule__CodeString__Group__5__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Group__5__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__5"



	// $ANTLR start "rule__CodeString__Group__5__Impl"
	// InternalDsl.g:1314:1: rule__CodeString__Group__5__Impl : ( ';' ) ;
	public final void rule__CodeString__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1318:2: ( ( ';' ) )
			// InternalDsl.g:1319:2: ( ';' )
			{
			// InternalDsl.g:1319:2: ( ';' )
			// InternalDsl.g:1320:2: ';'
			{
			 before(grammarAccess.getCodeStringAccess().getSemicolonKeyword_5()); 
			match(input,20,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getSemicolonKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group__5__Impl"



	// $ANTLR start "rule__CodeString__Group_1__0"
	// InternalDsl.g:1330:1: rule__CodeString__Group_1__0 : rule__CodeString__Group_1__0__Impl rule__CodeString__Group_1__1 ;
	public final void rule__CodeString__Group_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1334:2: ( rule__CodeString__Group_1__0__Impl rule__CodeString__Group_1__1 )
			// InternalDsl.g:1335:2: rule__CodeString__Group_1__0__Impl rule__CodeString__Group_1__1
			{
			pushFollow(FOLLOW_16);
			rule__CodeString__Group_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__0"



	// $ANTLR start "rule__CodeString__Group_1__0__Impl"
	// InternalDsl.g:1342:1: rule__CodeString__Group_1__0__Impl : ( '[' ) ;
	public final void rule__CodeString__Group_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1346:2: ( ( '[' ) )
			// InternalDsl.g:1347:2: ( '[' )
			{
			// InternalDsl.g:1347:2: ( '[' )
			// InternalDsl.g:1348:2: '['
			{
			 before(grammarAccess.getCodeStringAccess().getLeftSquareBracketKeyword_1_0()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getLeftSquareBracketKeyword_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__0__Impl"



	// $ANTLR start "rule__CodeString__Group_1__1"
	// InternalDsl.g:1357:1: rule__CodeString__Group_1__1 : rule__CodeString__Group_1__1__Impl rule__CodeString__Group_1__2 ;
	public final void rule__CodeString__Group_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1361:2: ( rule__CodeString__Group_1__1__Impl rule__CodeString__Group_1__2 )
			// InternalDsl.g:1362:2: rule__CodeString__Group_1__1__Impl rule__CodeString__Group_1__2
			{
			pushFollow(FOLLOW_17);
			rule__CodeString__Group_1__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group_1__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__1"



	// $ANTLR start "rule__CodeString__Group_1__1__Impl"
	// InternalDsl.g:1369:1: rule__CodeString__Group_1__1__Impl : ( ( rule__CodeString__Alternatives_1_1 ) ) ;
	public final void rule__CodeString__Group_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1373:2: ( ( ( rule__CodeString__Alternatives_1_1 ) ) )
			// InternalDsl.g:1374:2: ( ( rule__CodeString__Alternatives_1_1 ) )
			{
			// InternalDsl.g:1374:2: ( ( rule__CodeString__Alternatives_1_1 ) )
			// InternalDsl.g:1375:2: ( rule__CodeString__Alternatives_1_1 )
			{
			 before(grammarAccess.getCodeStringAccess().getAlternatives_1_1()); 
			// InternalDsl.g:1376:2: ( rule__CodeString__Alternatives_1_1 )
			// InternalDsl.g:1376:3: rule__CodeString__Alternatives_1_1
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Alternatives_1_1();
			state._fsp--;

			}

			 after(grammarAccess.getCodeStringAccess().getAlternatives_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__1__Impl"



	// $ANTLR start "rule__CodeString__Group_1__2"
	// InternalDsl.g:1384:1: rule__CodeString__Group_1__2 : rule__CodeString__Group_1__2__Impl ;
	public final void rule__CodeString__Group_1__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1388:2: ( rule__CodeString__Group_1__2__Impl )
			// InternalDsl.g:1389:2: rule__CodeString__Group_1__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Group_1__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__2"



	// $ANTLR start "rule__CodeString__Group_1__2__Impl"
	// InternalDsl.g:1395:1: rule__CodeString__Group_1__2__Impl : ( ']' ) ;
	public final void rule__CodeString__Group_1__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1399:2: ( ( ']' ) )
			// InternalDsl.g:1400:2: ( ']' )
			{
			// InternalDsl.g:1400:2: ( ']' )
			// InternalDsl.g:1401:2: ']'
			{
			 before(grammarAccess.getCodeStringAccess().getRightSquareBracketKeyword_1_2()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getRightSquareBracketKeyword_1_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_1__2__Impl"



	// $ANTLR start "rule__CodeString__Group_3_1__0"
	// InternalDsl.g:1411:1: rule__CodeString__Group_3_1__0 : rule__CodeString__Group_3_1__0__Impl rule__CodeString__Group_3_1__1 ;
	public final void rule__CodeString__Group_3_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1415:2: ( rule__CodeString__Group_3_1__0__Impl rule__CodeString__Group_3_1__1 )
			// InternalDsl.g:1416:2: rule__CodeString__Group_3_1__0__Impl rule__CodeString__Group_3_1__1
			{
			pushFollow(FOLLOW_18);
			rule__CodeString__Group_3_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group_3_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_3_1__0"



	// $ANTLR start "rule__CodeString__Group_3_1__0__Impl"
	// InternalDsl.g:1423:1: rule__CodeString__Group_3_1__0__Impl : ( ( '-' )? ) ;
	public final void rule__CodeString__Group_3_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1427:2: ( ( ( '-' )? ) )
			// InternalDsl.g:1428:2: ( ( '-' )? )
			{
			// InternalDsl.g:1428:2: ( ( '-' )? )
			// InternalDsl.g:1429:2: ( '-' )?
			{
			 before(grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_3_1_0()); 
			// InternalDsl.g:1430:2: ( '-' )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==17) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// InternalDsl.g:1430:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_3_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_3_1__0__Impl"



	// $ANTLR start "rule__CodeString__Group_3_1__1"
	// InternalDsl.g:1438:1: rule__CodeString__Group_3_1__1 : rule__CodeString__Group_3_1__1__Impl ;
	public final void rule__CodeString__Group_3_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1442:2: ( rule__CodeString__Group_3_1__1__Impl )
			// InternalDsl.g:1443:2: rule__CodeString__Group_3_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Group_3_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_3_1__1"



	// $ANTLR start "rule__CodeString__Group_3_1__1__Impl"
	// InternalDsl.g:1449:1: rule__CodeString__Group_3_1__1__Impl : ( RULE_INT ) ;
	public final void rule__CodeString__Group_3_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1453:2: ( ( RULE_INT ) )
			// InternalDsl.g:1454:2: ( RULE_INT )
			{
			// InternalDsl.g:1454:2: ( RULE_INT )
			// InternalDsl.g:1455:2: RULE_INT
			{
			 before(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_3_1_1()); 
			match(input,RULE_INT,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_3_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_3_1__1__Impl"



	// $ANTLR start "rule__CodeString__Group_4__0"
	// InternalDsl.g:1465:1: rule__CodeString__Group_4__0 : rule__CodeString__Group_4__0__Impl rule__CodeString__Group_4__1 ;
	public final void rule__CodeString__Group_4__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1469:2: ( rule__CodeString__Group_4__0__Impl rule__CodeString__Group_4__1 )
			// InternalDsl.g:1470:2: rule__CodeString__Group_4__0__Impl rule__CodeString__Group_4__1
			{
			pushFollow(FOLLOW_12);
			rule__CodeString__Group_4__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group_4__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4__0"



	// $ANTLR start "rule__CodeString__Group_4__0__Impl"
	// InternalDsl.g:1477:1: rule__CodeString__Group_4__0__Impl : ( ruleOperation ) ;
	public final void rule__CodeString__Group_4__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1481:2: ( ( ruleOperation ) )
			// InternalDsl.g:1482:2: ( ruleOperation )
			{
			// InternalDsl.g:1482:2: ( ruleOperation )
			// InternalDsl.g:1483:2: ruleOperation
			{
			 before(grammarAccess.getCodeStringAccess().getOperationParserRuleCall_4_0()); 
			pushFollow(FOLLOW_2);
			ruleOperation();
			state._fsp--;

			 after(grammarAccess.getCodeStringAccess().getOperationParserRuleCall_4_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4__0__Impl"



	// $ANTLR start "rule__CodeString__Group_4__1"
	// InternalDsl.g:1492:1: rule__CodeString__Group_4__1 : rule__CodeString__Group_4__1__Impl ;
	public final void rule__CodeString__Group_4__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1496:2: ( rule__CodeString__Group_4__1__Impl )
			// InternalDsl.g:1497:2: rule__CodeString__Group_4__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Group_4__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4__1"



	// $ANTLR start "rule__CodeString__Group_4__1__Impl"
	// InternalDsl.g:1503:1: rule__CodeString__Group_4__1__Impl : ( ( rule__CodeString__Alternatives_4_1 ) ) ;
	public final void rule__CodeString__Group_4__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1507:2: ( ( ( rule__CodeString__Alternatives_4_1 ) ) )
			// InternalDsl.g:1508:2: ( ( rule__CodeString__Alternatives_4_1 ) )
			{
			// InternalDsl.g:1508:2: ( ( rule__CodeString__Alternatives_4_1 ) )
			// InternalDsl.g:1509:2: ( rule__CodeString__Alternatives_4_1 )
			{
			 before(grammarAccess.getCodeStringAccess().getAlternatives_4_1()); 
			// InternalDsl.g:1510:2: ( rule__CodeString__Alternatives_4_1 )
			// InternalDsl.g:1510:3: rule__CodeString__Alternatives_4_1
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Alternatives_4_1();
			state._fsp--;

			}

			 after(grammarAccess.getCodeStringAccess().getAlternatives_4_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4__1__Impl"



	// $ANTLR start "rule__CodeString__Group_4_1_1__0"
	// InternalDsl.g:1519:1: rule__CodeString__Group_4_1_1__0 : rule__CodeString__Group_4_1_1__0__Impl rule__CodeString__Group_4_1_1__1 ;
	public final void rule__CodeString__Group_4_1_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1523:2: ( rule__CodeString__Group_4_1_1__0__Impl rule__CodeString__Group_4_1_1__1 )
			// InternalDsl.g:1524:2: rule__CodeString__Group_4_1_1__0__Impl rule__CodeString__Group_4_1_1__1
			{
			pushFollow(FOLLOW_18);
			rule__CodeString__Group_4_1_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CodeString__Group_4_1_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4_1_1__0"



	// $ANTLR start "rule__CodeString__Group_4_1_1__0__Impl"
	// InternalDsl.g:1531:1: rule__CodeString__Group_4_1_1__0__Impl : ( ( '-' )? ) ;
	public final void rule__CodeString__Group_4_1_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1535:2: ( ( ( '-' )? ) )
			// InternalDsl.g:1536:2: ( ( '-' )? )
			{
			// InternalDsl.g:1536:2: ( ( '-' )? )
			// InternalDsl.g:1537:2: ( '-' )?
			{
			 before(grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_4_1_1_0()); 
			// InternalDsl.g:1538:2: ( '-' )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==17) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// InternalDsl.g:1538:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_4_1_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4_1_1__0__Impl"



	// $ANTLR start "rule__CodeString__Group_4_1_1__1"
	// InternalDsl.g:1546:1: rule__CodeString__Group_4_1_1__1 : rule__CodeString__Group_4_1_1__1__Impl ;
	public final void rule__CodeString__Group_4_1_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1550:2: ( rule__CodeString__Group_4_1_1__1__Impl )
			// InternalDsl.g:1551:2: rule__CodeString__Group_4_1_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CodeString__Group_4_1_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4_1_1__1"



	// $ANTLR start "rule__CodeString__Group_4_1_1__1__Impl"
	// InternalDsl.g:1557:1: rule__CodeString__Group_4_1_1__1__Impl : ( RULE_INT ) ;
	public final void rule__CodeString__Group_4_1_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1561:2: ( ( RULE_INT ) )
			// InternalDsl.g:1562:2: ( RULE_INT )
			{
			// InternalDsl.g:1562:2: ( RULE_INT )
			// InternalDsl.g:1563:2: RULE_INT
			{
			 before(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_4_1_1_1()); 
			match(input,RULE_INT,FOLLOW_2); 
			 after(grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_4_1_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CodeString__Group_4_1_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group__0"
	// InternalDsl.g:1573:1: rule__VariableString__Group__0 : rule__VariableString__Group__0__Impl rule__VariableString__Group__1 ;
	public final void rule__VariableString__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1577:2: ( rule__VariableString__Group__0__Impl rule__VariableString__Group__1 )
			// InternalDsl.g:1578:2: rule__VariableString__Group__0__Impl rule__VariableString__Group__1
			{
			pushFollow(FOLLOW_19);
			rule__VariableString__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__0"



	// $ANTLR start "rule__VariableString__Group__0__Impl"
	// InternalDsl.g:1585:1: rule__VariableString__Group__0__Impl : ( ( '-' )? ) ;
	public final void rule__VariableString__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1589:2: ( ( ( '-' )? ) )
			// InternalDsl.g:1590:2: ( ( '-' )? )
			{
			// InternalDsl.g:1590:2: ( ( '-' )? )
			// InternalDsl.g:1591:2: ( '-' )?
			{
			 before(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_0()); 
			// InternalDsl.g:1592:2: ( '-' )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( (LA16_0==17) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// InternalDsl.g:1592:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__0__Impl"



	// $ANTLR start "rule__VariableString__Group__1"
	// InternalDsl.g:1600:1: rule__VariableString__Group__1 : rule__VariableString__Group__1__Impl rule__VariableString__Group__2 ;
	public final void rule__VariableString__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1604:2: ( rule__VariableString__Group__1__Impl rule__VariableString__Group__2 )
			// InternalDsl.g:1605:2: rule__VariableString__Group__1__Impl rule__VariableString__Group__2
			{
			pushFollow(FOLLOW_20);
			rule__VariableString__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__1"



	// $ANTLR start "rule__VariableString__Group__1__Impl"
	// InternalDsl.g:1612:1: rule__VariableString__Group__1__Impl : ( RULE_ID ) ;
	public final void rule__VariableString__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1616:2: ( ( RULE_ID ) )
			// InternalDsl.g:1617:2: ( RULE_ID )
			{
			// InternalDsl.g:1617:2: ( RULE_ID )
			// InternalDsl.g:1618:2: RULE_ID
			{
			 before(grammarAccess.getVariableStringAccess().getIDTerminalRuleCall_1()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getIDTerminalRuleCall_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__1__Impl"



	// $ANTLR start "rule__VariableString__Group__2"
	// InternalDsl.g:1627:1: rule__VariableString__Group__2 : rule__VariableString__Group__2__Impl ;
	public final void rule__VariableString__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1631:2: ( rule__VariableString__Group__2__Impl )
			// InternalDsl.g:1632:2: rule__VariableString__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__2"



	// $ANTLR start "rule__VariableString__Group__2__Impl"
	// InternalDsl.g:1638:1: rule__VariableString__Group__2__Impl : ( ( rule__VariableString__Alternatives_2 )? ) ;
	public final void rule__VariableString__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1642:2: ( ( ( rule__VariableString__Alternatives_2 )? ) )
			// InternalDsl.g:1643:2: ( ( rule__VariableString__Alternatives_2 )? )
			{
			// InternalDsl.g:1643:2: ( ( rule__VariableString__Alternatives_2 )? )
			// InternalDsl.g:1644:2: ( rule__VariableString__Alternatives_2 )?
			{
			 before(grammarAccess.getVariableStringAccess().getAlternatives_2()); 
			// InternalDsl.g:1645:2: ( rule__VariableString__Alternatives_2 )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==12||LA17_0==18||LA17_0==26) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// InternalDsl.g:1645:3: rule__VariableString__Alternatives_2
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Alternatives_2();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getAlternatives_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group__2__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0__0"
	// InternalDsl.g:1654:1: rule__VariableString__Group_2_0__0 : rule__VariableString__Group_2_0__0__Impl rule__VariableString__Group_2_0__1 ;
	public final void rule__VariableString__Group_2_0__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1658:2: ( rule__VariableString__Group_2_0__0__Impl rule__VariableString__Group_2_0__1 )
			// InternalDsl.g:1659:2: rule__VariableString__Group_2_0__0__Impl rule__VariableString__Group_2_0__1
			{
			pushFollow(FOLLOW_21);
			rule__VariableString__Group_2_0__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0__0"



	// $ANTLR start "rule__VariableString__Group_2_0__0__Impl"
	// InternalDsl.g:1666:1: rule__VariableString__Group_2_0__0__Impl : ( ( rule__VariableString__Group_2_0_0__0 ) ) ;
	public final void rule__VariableString__Group_2_0__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1670:2: ( ( ( rule__VariableString__Group_2_0_0__0 ) ) )
			// InternalDsl.g:1671:2: ( ( rule__VariableString__Group_2_0_0__0 ) )
			{
			// InternalDsl.g:1671:2: ( ( rule__VariableString__Group_2_0_0__0 ) )
			// InternalDsl.g:1672:2: ( rule__VariableString__Group_2_0_0__0 )
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_0_0()); 
			// InternalDsl.g:1673:2: ( rule__VariableString__Group_2_0_0__0 )
			// InternalDsl.g:1673:3: rule__VariableString__Group_2_0_0__0
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0__0();
			state._fsp--;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_0_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0__1"
	// InternalDsl.g:1681:1: rule__VariableString__Group_2_0__1 : rule__VariableString__Group_2_0__1__Impl ;
	public final void rule__VariableString__Group_2_0__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1685:2: ( rule__VariableString__Group_2_0__1__Impl )
			// InternalDsl.g:1686:2: rule__VariableString__Group_2_0__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0__1"



	// $ANTLR start "rule__VariableString__Group_2_0__1__Impl"
	// InternalDsl.g:1692:1: rule__VariableString__Group_2_0__1__Impl : ( ( rule__VariableString__Group_2_0_1__0 )? ) ;
	public final void rule__VariableString__Group_2_0__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1696:2: ( ( ( rule__VariableString__Group_2_0_1__0 )? ) )
			// InternalDsl.g:1697:2: ( ( rule__VariableString__Group_2_0_1__0 )? )
			{
			// InternalDsl.g:1697:2: ( ( rule__VariableString__Group_2_0_1__0 )? )
			// InternalDsl.g:1698:2: ( rule__VariableString__Group_2_0_1__0 )?
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_0_1()); 
			// InternalDsl.g:1699:2: ( rule__VariableString__Group_2_0_1__0 )?
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==18) ) {
				alt18=1;
			}
			switch (alt18) {
				case 1 :
					// InternalDsl.g:1699:3: rule__VariableString__Group_2_0_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_0_1__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_0_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0__0"
	// InternalDsl.g:1708:1: rule__VariableString__Group_2_0_0__0 : rule__VariableString__Group_2_0_0__0__Impl rule__VariableString__Group_2_0_0__1 ;
	public final void rule__VariableString__Group_2_0_0__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1712:2: ( rule__VariableString__Group_2_0_0__0__Impl rule__VariableString__Group_2_0_0__1 )
			// InternalDsl.g:1713:2: rule__VariableString__Group_2_0_0__0__Impl rule__VariableString__Group_2_0_0__1
			{
			pushFollow(FOLLOW_22);
			rule__VariableString__Group_2_0_0__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__0"



	// $ANTLR start "rule__VariableString__Group_2_0_0__0__Impl"
	// InternalDsl.g:1720:1: rule__VariableString__Group_2_0_0__0__Impl : ( '(' ) ;
	public final void rule__VariableString__Group_2_0_0__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1724:2: ( ( '(' ) )
			// InternalDsl.g:1725:2: ( '(' )
			{
			// InternalDsl.g:1725:2: ( '(' )
			// InternalDsl.g:1726:2: '('
			{
			 before(grammarAccess.getVariableStringAccess().getLeftParenthesisKeyword_2_0_0_0()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getLeftParenthesisKeyword_2_0_0_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0__1"
	// InternalDsl.g:1735:1: rule__VariableString__Group_2_0_0__1 : rule__VariableString__Group_2_0_0__1__Impl rule__VariableString__Group_2_0_0__2 ;
	public final void rule__VariableString__Group_2_0_0__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1739:2: ( rule__VariableString__Group_2_0_0__1__Impl rule__VariableString__Group_2_0_0__2 )
			// InternalDsl.g:1740:2: rule__VariableString__Group_2_0_0__1__Impl rule__VariableString__Group_2_0_0__2
			{
			pushFollow(FOLLOW_23);
			rule__VariableString__Group_2_0_0__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__1"



	// $ANTLR start "rule__VariableString__Group_2_0_0__1__Impl"
	// InternalDsl.g:1747:1: rule__VariableString__Group_2_0_0__1__Impl : ( ( rule__VariableString__Group_2_0_0_1__0 )? ) ;
	public final void rule__VariableString__Group_2_0_0__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1751:2: ( ( ( rule__VariableString__Group_2_0_0_1__0 )? ) )
			// InternalDsl.g:1752:2: ( ( rule__VariableString__Group_2_0_0_1__0 )? )
			{
			// InternalDsl.g:1752:2: ( ( rule__VariableString__Group_2_0_0_1__0 )? )
			// InternalDsl.g:1753:2: ( rule__VariableString__Group_2_0_0_1__0 )?
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1()); 
			// InternalDsl.g:1754:2: ( rule__VariableString__Group_2_0_0_1__0 )?
			int alt19=2;
			int LA19_0 = input.LA(1);
			if ( ((LA19_0 >= RULE_ID && LA19_0 <= RULE_INT)||LA19_0==17) ) {
				alt19=1;
			}
			switch (alt19) {
				case 1 :
					// InternalDsl.g:1754:3: rule__VariableString__Group_2_0_0_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_0_0_1__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0__2"
	// InternalDsl.g:1762:1: rule__VariableString__Group_2_0_0__2 : rule__VariableString__Group_2_0_0__2__Impl ;
	public final void rule__VariableString__Group_2_0_0__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1766:2: ( rule__VariableString__Group_2_0_0__2__Impl )
			// InternalDsl.g:1767:2: rule__VariableString__Group_2_0_0__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__2"



	// $ANTLR start "rule__VariableString__Group_2_0_0__2__Impl"
	// InternalDsl.g:1773:1: rule__VariableString__Group_2_0_0__2__Impl : ( ')' ) ;
	public final void rule__VariableString__Group_2_0_0__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1777:2: ( ( ')' ) )
			// InternalDsl.g:1778:2: ( ')' )
			{
			// InternalDsl.g:1778:2: ( ')' )
			// InternalDsl.g:1779:2: ')'
			{
			 before(grammarAccess.getVariableStringAccess().getRightParenthesisKeyword_2_0_0_2()); 
			match(input,13,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getRightParenthesisKeyword_2_0_0_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0__2__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1__0"
	// InternalDsl.g:1789:1: rule__VariableString__Group_2_0_0_1__0 : rule__VariableString__Group_2_0_0_1__0__Impl rule__VariableString__Group_2_0_0_1__1 ;
	public final void rule__VariableString__Group_2_0_0_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1793:2: ( rule__VariableString__Group_2_0_0_1__0__Impl rule__VariableString__Group_2_0_0_1__1 )
			// InternalDsl.g:1794:2: rule__VariableString__Group_2_0_0_1__0__Impl rule__VariableString__Group_2_0_0_1__1
			{
			pushFollow(FOLLOW_24);
			rule__VariableString__Group_2_0_0_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1__0"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1__0__Impl"
	// InternalDsl.g:1801:1: rule__VariableString__Group_2_0_0_1__0__Impl : ( ( rule__VariableString__Alternatives_2_0_0_1_0 ) ) ;
	public final void rule__VariableString__Group_2_0_0_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1805:2: ( ( ( rule__VariableString__Alternatives_2_0_0_1_0 ) ) )
			// InternalDsl.g:1806:2: ( ( rule__VariableString__Alternatives_2_0_0_1_0 ) )
			{
			// InternalDsl.g:1806:2: ( ( rule__VariableString__Alternatives_2_0_0_1_0 ) )
			// InternalDsl.g:1807:2: ( rule__VariableString__Alternatives_2_0_0_1_0 )
			{
			 before(grammarAccess.getVariableStringAccess().getAlternatives_2_0_0_1_0()); 
			// InternalDsl.g:1808:2: ( rule__VariableString__Alternatives_2_0_0_1_0 )
			// InternalDsl.g:1808:3: rule__VariableString__Alternatives_2_0_0_1_0
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Alternatives_2_0_0_1_0();
			state._fsp--;

			}

			 after(grammarAccess.getVariableStringAccess().getAlternatives_2_0_0_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1__1"
	// InternalDsl.g:1816:1: rule__VariableString__Group_2_0_0_1__1 : rule__VariableString__Group_2_0_0_1__1__Impl ;
	public final void rule__VariableString__Group_2_0_0_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1820:2: ( rule__VariableString__Group_2_0_0_1__1__Impl )
			// InternalDsl.g:1821:2: rule__VariableString__Group_2_0_0_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1__1"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1__1__Impl"
	// InternalDsl.g:1827:1: rule__VariableString__Group_2_0_0_1__1__Impl : ( ( rule__VariableString__Group_2_0_0_1_1__0 )* ) ;
	public final void rule__VariableString__Group_2_0_0_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1831:2: ( ( ( rule__VariableString__Group_2_0_0_1_1__0 )* ) )
			// InternalDsl.g:1832:2: ( ( rule__VariableString__Group_2_0_0_1_1__0 )* )
			{
			// InternalDsl.g:1832:2: ( ( rule__VariableString__Group_2_0_0_1_1__0 )* )
			// InternalDsl.g:1833:2: ( rule__VariableString__Group_2_0_0_1_1__0 )*
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_1()); 
			// InternalDsl.g:1834:2: ( rule__VariableString__Group_2_0_0_1_1__0 )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( (LA20_0==16) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// InternalDsl.g:1834:3: rule__VariableString__Group_2_0_0_1_1__0
					{
					pushFollow(FOLLOW_25);
					rule__VariableString__Group_2_0_0_1_1__0();
					state._fsp--;

					}
					break;

				default :
					break loop20;
				}
			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_0_0_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_0_1__0"
	// InternalDsl.g:1843:1: rule__VariableString__Group_2_0_0_1_0_1__0 : rule__VariableString__Group_2_0_0_1_0_1__0__Impl rule__VariableString__Group_2_0_0_1_0_1__1 ;
	public final void rule__VariableString__Group_2_0_0_1_0_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1847:2: ( rule__VariableString__Group_2_0_0_1_0_1__0__Impl rule__VariableString__Group_2_0_0_1_0_1__1 )
			// InternalDsl.g:1848:2: rule__VariableString__Group_2_0_0_1_0_1__0__Impl rule__VariableString__Group_2_0_0_1_0_1__1
			{
			pushFollow(FOLLOW_18);
			rule__VariableString__Group_2_0_0_1_0_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_0_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_0_1__0"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_0_1__0__Impl"
	// InternalDsl.g:1855:1: rule__VariableString__Group_2_0_0_1_0_1__0__Impl : ( ( '-' )? ) ;
	public final void rule__VariableString__Group_2_0_0_1_0_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1859:2: ( ( ( '-' )? ) )
			// InternalDsl.g:1860:2: ( ( '-' )? )
			{
			// InternalDsl.g:1860:2: ( ( '-' )? )
			// InternalDsl.g:1861:2: ( '-' )?
			{
			 before(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_0_1_0()); 
			// InternalDsl.g:1862:2: ( '-' )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==17) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// InternalDsl.g:1862:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_0_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_0_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_0_1__1"
	// InternalDsl.g:1870:1: rule__VariableString__Group_2_0_0_1_0_1__1 : rule__VariableString__Group_2_0_0_1_0_1__1__Impl ;
	public final void rule__VariableString__Group_2_0_0_1_0_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1874:2: ( rule__VariableString__Group_2_0_0_1_0_1__1__Impl )
			// InternalDsl.g:1875:2: rule__VariableString__Group_2_0_0_1_0_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_0_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_0_1__1"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_0_1__1__Impl"
	// InternalDsl.g:1881:1: rule__VariableString__Group_2_0_0_1_0_1__1__Impl : ( RULE_INT ) ;
	public final void rule__VariableString__Group_2_0_0_1_0_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1885:2: ( ( RULE_INT ) )
			// InternalDsl.g:1886:2: ( RULE_INT )
			{
			// InternalDsl.g:1886:2: ( RULE_INT )
			// InternalDsl.g:1887:2: RULE_INT
			{
			 before(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_0_1_1()); 
			match(input,RULE_INT,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_0_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_0_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1__0"
	// InternalDsl.g:1897:1: rule__VariableString__Group_2_0_0_1_1__0 : rule__VariableString__Group_2_0_0_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1__1 ;
	public final void rule__VariableString__Group_2_0_0_1_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1901:2: ( rule__VariableString__Group_2_0_0_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1__1 )
			// InternalDsl.g:1902:2: rule__VariableString__Group_2_0_0_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1__1
			{
			pushFollow(FOLLOW_12);
			rule__VariableString__Group_2_0_0_1_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1__0"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1__0__Impl"
	// InternalDsl.g:1909:1: rule__VariableString__Group_2_0_0_1_1__0__Impl : ( ',' ) ;
	public final void rule__VariableString__Group_2_0_0_1_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1913:2: ( ( ',' ) )
			// InternalDsl.g:1914:2: ( ',' )
			{
			// InternalDsl.g:1914:2: ( ',' )
			// InternalDsl.g:1915:2: ','
			{
			 before(grammarAccess.getVariableStringAccess().getCommaKeyword_2_0_0_1_1_0()); 
			match(input,16,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getCommaKeyword_2_0_0_1_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1__1"
	// InternalDsl.g:1924:1: rule__VariableString__Group_2_0_0_1_1__1 : rule__VariableString__Group_2_0_0_1_1__1__Impl ;
	public final void rule__VariableString__Group_2_0_0_1_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1928:2: ( rule__VariableString__Group_2_0_0_1_1__1__Impl )
			// InternalDsl.g:1929:2: rule__VariableString__Group_2_0_0_1_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1__1"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1__1__Impl"
	// InternalDsl.g:1935:1: rule__VariableString__Group_2_0_0_1_1__1__Impl : ( ( rule__VariableString__Alternatives_2_0_0_1_1_1 ) ) ;
	public final void rule__VariableString__Group_2_0_0_1_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1939:2: ( ( ( rule__VariableString__Alternatives_2_0_0_1_1_1 ) ) )
			// InternalDsl.g:1940:2: ( ( rule__VariableString__Alternatives_2_0_0_1_1_1 ) )
			{
			// InternalDsl.g:1940:2: ( ( rule__VariableString__Alternatives_2_0_0_1_1_1 ) )
			// InternalDsl.g:1941:2: ( rule__VariableString__Alternatives_2_0_0_1_1_1 )
			{
			 before(grammarAccess.getVariableStringAccess().getAlternatives_2_0_0_1_1_1()); 
			// InternalDsl.g:1942:2: ( rule__VariableString__Alternatives_2_0_0_1_1_1 )
			// InternalDsl.g:1942:3: rule__VariableString__Alternatives_2_0_0_1_1_1
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Alternatives_2_0_0_1_1_1();
			state._fsp--;

			}

			 after(grammarAccess.getVariableStringAccess().getAlternatives_2_0_0_1_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1_1_1__0"
	// InternalDsl.g:1951:1: rule__VariableString__Group_2_0_0_1_1_1_1__0 : rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1_1_1__1 ;
	public final void rule__VariableString__Group_2_0_0_1_1_1_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1955:2: ( rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1_1_1__1 )
			// InternalDsl.g:1956:2: rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl rule__VariableString__Group_2_0_0_1_1_1_1__1
			{
			pushFollow(FOLLOW_18);
			rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_1_1_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1_1_1__0"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl"
	// InternalDsl.g:1963:1: rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl : ( ( '-' )? ) ;
	public final void rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1967:2: ( ( ( '-' )? ) )
			// InternalDsl.g:1968:2: ( ( '-' )? )
			{
			// InternalDsl.g:1968:2: ( ( '-' )? )
			// InternalDsl.g:1969:2: ( '-' )?
			{
			 before(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_1_1_1_0()); 
			// InternalDsl.g:1970:2: ( '-' )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==17) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// InternalDsl.g:1970:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_1_1_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1_1_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1_1_1__1"
	// InternalDsl.g:1978:1: rule__VariableString__Group_2_0_0_1_1_1_1__1 : rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl ;
	public final void rule__VariableString__Group_2_0_0_1_1_1_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1982:2: ( rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl )
			// InternalDsl.g:1983:2: rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1_1_1__1"



	// $ANTLR start "rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl"
	// InternalDsl.g:1989:1: rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl : ( RULE_INT ) ;
	public final void rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1993:2: ( ( RULE_INT ) )
			// InternalDsl.g:1994:2: ( RULE_INT )
			{
			// InternalDsl.g:1994:2: ( RULE_INT )
			// InternalDsl.g:1995:2: RULE_INT
			{
			 before(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_1_1_1_1()); 
			match(input,RULE_INT,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_1_1_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_0_1_1_1_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_1__0"
	// InternalDsl.g:2005:1: rule__VariableString__Group_2_0_1__0 : rule__VariableString__Group_2_0_1__0__Impl rule__VariableString__Group_2_0_1__1 ;
	public final void rule__VariableString__Group_2_0_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2009:2: ( rule__VariableString__Group_2_0_1__0__Impl rule__VariableString__Group_2_0_1__1 )
			// InternalDsl.g:2010:2: rule__VariableString__Group_2_0_1__0__Impl rule__VariableString__Group_2_0_1__1
			{
			pushFollow(FOLLOW_26);
			rule__VariableString__Group_2_0_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_1__0"



	// $ANTLR start "rule__VariableString__Group_2_0_1__0__Impl"
	// InternalDsl.g:2017:1: rule__VariableString__Group_2_0_1__0__Impl : ( '.' ) ;
	public final void rule__VariableString__Group_2_0_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2021:2: ( ( '.' ) )
			// InternalDsl.g:2022:2: ( '.' )
			{
			// InternalDsl.g:2022:2: ( '.' )
			// InternalDsl.g:2023:2: '.'
			{
			 before(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_0_1_0()); 
			match(input,18,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_0_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_0_1__1"
	// InternalDsl.g:2032:1: rule__VariableString__Group_2_0_1__1 : rule__VariableString__Group_2_0_1__1__Impl ;
	public final void rule__VariableString__Group_2_0_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2036:2: ( rule__VariableString__Group_2_0_1__1__Impl )
			// InternalDsl.g:2037:2: rule__VariableString__Group_2_0_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_0_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_1__1"



	// $ANTLR start "rule__VariableString__Group_2_0_1__1__Impl"
	// InternalDsl.g:2043:1: rule__VariableString__Group_2_0_1__1__Impl : ( ruleVariableString ) ;
	public final void rule__VariableString__Group_2_0_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2047:2: ( ( ruleVariableString ) )
			// InternalDsl.g:2048:2: ( ruleVariableString )
			{
			// InternalDsl.g:2048:2: ( ruleVariableString )
			// InternalDsl.g:2049:2: ruleVariableString
			{
			 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_1_1()); 
			pushFollow(FOLLOW_2);
			ruleVariableString();
			state._fsp--;

			 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_0_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1__0"
	// InternalDsl.g:2059:1: rule__VariableString__Group_2_1__0 : rule__VariableString__Group_2_1__0__Impl rule__VariableString__Group_2_1__1 ;
	public final void rule__VariableString__Group_2_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2063:2: ( rule__VariableString__Group_2_1__0__Impl rule__VariableString__Group_2_1__1 )
			// InternalDsl.g:2064:2: rule__VariableString__Group_2_1__0__Impl rule__VariableString__Group_2_1__1
			{
			pushFollow(FOLLOW_21);
			rule__VariableString__Group_2_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1__0"



	// $ANTLR start "rule__VariableString__Group_2_1__0__Impl"
	// InternalDsl.g:2071:1: rule__VariableString__Group_2_1__0__Impl : ( ( rule__VariableString__Group_2_1_0__0 ) ) ;
	public final void rule__VariableString__Group_2_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2075:2: ( ( ( rule__VariableString__Group_2_1_0__0 ) ) )
			// InternalDsl.g:2076:2: ( ( rule__VariableString__Group_2_1_0__0 ) )
			{
			// InternalDsl.g:2076:2: ( ( rule__VariableString__Group_2_1_0__0 ) )
			// InternalDsl.g:2077:2: ( rule__VariableString__Group_2_1_0__0 )
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_1_0()); 
			// InternalDsl.g:2078:2: ( rule__VariableString__Group_2_1_0__0 )
			// InternalDsl.g:2078:3: rule__VariableString__Group_2_1_0__0
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0__0();
			state._fsp--;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1__1"
	// InternalDsl.g:2086:1: rule__VariableString__Group_2_1__1 : rule__VariableString__Group_2_1__1__Impl ;
	public final void rule__VariableString__Group_2_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2090:2: ( rule__VariableString__Group_2_1__1__Impl )
			// InternalDsl.g:2091:2: rule__VariableString__Group_2_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1__1"



	// $ANTLR start "rule__VariableString__Group_2_1__1__Impl"
	// InternalDsl.g:2097:1: rule__VariableString__Group_2_1__1__Impl : ( ( rule__VariableString__Group_2_1_1__0 )? ) ;
	public final void rule__VariableString__Group_2_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2101:2: ( ( ( rule__VariableString__Group_2_1_1__0 )? ) )
			// InternalDsl.g:2102:2: ( ( rule__VariableString__Group_2_1_1__0 )? )
			{
			// InternalDsl.g:2102:2: ( ( rule__VariableString__Group_2_1_1__0 )? )
			// InternalDsl.g:2103:2: ( rule__VariableString__Group_2_1_1__0 )?
			{
			 before(grammarAccess.getVariableStringAccess().getGroup_2_1_1()); 
			// InternalDsl.g:2104:2: ( rule__VariableString__Group_2_1_1__0 )?
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( (LA23_0==18) ) {
				alt23=1;
			}
			switch (alt23) {
				case 1 :
					// InternalDsl.g:2104:3: rule__VariableString__Group_2_1_1__0
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Group_2_1_1__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getGroup_2_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_0__0"
	// InternalDsl.g:2113:1: rule__VariableString__Group_2_1_0__0 : rule__VariableString__Group_2_1_0__0__Impl rule__VariableString__Group_2_1_0__1 ;
	public final void rule__VariableString__Group_2_1_0__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2117:2: ( rule__VariableString__Group_2_1_0__0__Impl rule__VariableString__Group_2_1_0__1 )
			// InternalDsl.g:2118:2: rule__VariableString__Group_2_1_0__0__Impl rule__VariableString__Group_2_1_0__1
			{
			pushFollow(FOLLOW_27);
			rule__VariableString__Group_2_1_0__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__0"



	// $ANTLR start "rule__VariableString__Group_2_1_0__0__Impl"
	// InternalDsl.g:2125:1: rule__VariableString__Group_2_1_0__0__Impl : ( '[' ) ;
	public final void rule__VariableString__Group_2_1_0__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2129:2: ( ( '[' ) )
			// InternalDsl.g:2130:2: ( '[' )
			{
			// InternalDsl.g:2130:2: ( '[' )
			// InternalDsl.g:2131:2: '['
			{
			 before(grammarAccess.getVariableStringAccess().getLeftSquareBracketKeyword_2_1_0_0()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getLeftSquareBracketKeyword_2_1_0_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_0__1"
	// InternalDsl.g:2140:1: rule__VariableString__Group_2_1_0__1 : rule__VariableString__Group_2_1_0__1__Impl rule__VariableString__Group_2_1_0__2 ;
	public final void rule__VariableString__Group_2_1_0__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2144:2: ( rule__VariableString__Group_2_1_0__1__Impl rule__VariableString__Group_2_1_0__2 )
			// InternalDsl.g:2145:2: rule__VariableString__Group_2_1_0__1__Impl rule__VariableString__Group_2_1_0__2
			{
			pushFollow(FOLLOW_17);
			rule__VariableString__Group_2_1_0__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__1"



	// $ANTLR start "rule__VariableString__Group_2_1_0__1__Impl"
	// InternalDsl.g:2152:1: rule__VariableString__Group_2_1_0__1__Impl : ( ( rule__VariableString__Alternatives_2_1_0_1 )? ) ;
	public final void rule__VariableString__Group_2_1_0__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2156:2: ( ( ( rule__VariableString__Alternatives_2_1_0_1 )? ) )
			// InternalDsl.g:2157:2: ( ( rule__VariableString__Alternatives_2_1_0_1 )? )
			{
			// InternalDsl.g:2157:2: ( ( rule__VariableString__Alternatives_2_1_0_1 )? )
			// InternalDsl.g:2158:2: ( rule__VariableString__Alternatives_2_1_0_1 )?
			{
			 before(grammarAccess.getVariableStringAccess().getAlternatives_2_1_0_1()); 
			// InternalDsl.g:2159:2: ( rule__VariableString__Alternatives_2_1_0_1 )?
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( ((LA24_0 >= RULE_ID && LA24_0 <= RULE_INT)||LA24_0==17) ) {
				alt24=1;
			}
			switch (alt24) {
				case 1 :
					// InternalDsl.g:2159:3: rule__VariableString__Alternatives_2_1_0_1
					{
					pushFollow(FOLLOW_2);
					rule__VariableString__Alternatives_2_1_0_1();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getAlternatives_2_1_0_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_0__2"
	// InternalDsl.g:2167:1: rule__VariableString__Group_2_1_0__2 : rule__VariableString__Group_2_1_0__2__Impl ;
	public final void rule__VariableString__Group_2_1_0__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2171:2: ( rule__VariableString__Group_2_1_0__2__Impl )
			// InternalDsl.g:2172:2: rule__VariableString__Group_2_1_0__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__2"



	// $ANTLR start "rule__VariableString__Group_2_1_0__2__Impl"
	// InternalDsl.g:2178:1: rule__VariableString__Group_2_1_0__2__Impl : ( ']' ) ;
	public final void rule__VariableString__Group_2_1_0__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2182:2: ( ( ']' ) )
			// InternalDsl.g:2183:2: ( ']' )
			{
			// InternalDsl.g:2183:2: ( ']' )
			// InternalDsl.g:2184:2: ']'
			{
			 before(grammarAccess.getVariableStringAccess().getRightSquareBracketKeyword_2_1_0_2()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getRightSquareBracketKeyword_2_1_0_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0__2__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_0_1_1__0"
	// InternalDsl.g:2194:1: rule__VariableString__Group_2_1_0_1_1__0 : rule__VariableString__Group_2_1_0_1_1__0__Impl rule__VariableString__Group_2_1_0_1_1__1 ;
	public final void rule__VariableString__Group_2_1_0_1_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2198:2: ( rule__VariableString__Group_2_1_0_1_1__0__Impl rule__VariableString__Group_2_1_0_1_1__1 )
			// InternalDsl.g:2199:2: rule__VariableString__Group_2_1_0_1_1__0__Impl rule__VariableString__Group_2_1_0_1_1__1
			{
			pushFollow(FOLLOW_18);
			rule__VariableString__Group_2_1_0_1_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0_1_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0_1_1__0"



	// $ANTLR start "rule__VariableString__Group_2_1_0_1_1__0__Impl"
	// InternalDsl.g:2206:1: rule__VariableString__Group_2_1_0_1_1__0__Impl : ( ( '-' )? ) ;
	public final void rule__VariableString__Group_2_1_0_1_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2210:2: ( ( ( '-' )? ) )
			// InternalDsl.g:2211:2: ( ( '-' )? )
			{
			// InternalDsl.g:2211:2: ( ( '-' )? )
			// InternalDsl.g:2212:2: ( '-' )?
			{
			 before(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_1_0_1_1_0()); 
			// InternalDsl.g:2213:2: ( '-' )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==17) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// InternalDsl.g:2213:3: '-'
					{
					match(input,17,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_1_0_1_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0_1_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_0_1_1__1"
	// InternalDsl.g:2221:1: rule__VariableString__Group_2_1_0_1_1__1 : rule__VariableString__Group_2_1_0_1_1__1__Impl ;
	public final void rule__VariableString__Group_2_1_0_1_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2225:2: ( rule__VariableString__Group_2_1_0_1_1__1__Impl )
			// InternalDsl.g:2226:2: rule__VariableString__Group_2_1_0_1_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_0_1_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0_1_1__1"



	// $ANTLR start "rule__VariableString__Group_2_1_0_1_1__1__Impl"
	// InternalDsl.g:2232:1: rule__VariableString__Group_2_1_0_1_1__1__Impl : ( RULE_INT ) ;
	public final void rule__VariableString__Group_2_1_0_1_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2236:2: ( ( RULE_INT ) )
			// InternalDsl.g:2237:2: ( RULE_INT )
			{
			// InternalDsl.g:2237:2: ( RULE_INT )
			// InternalDsl.g:2238:2: RULE_INT
			{
			 before(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_1_0_1_1_1()); 
			match(input,RULE_INT,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_1_0_1_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_0_1_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_1__0"
	// InternalDsl.g:2248:1: rule__VariableString__Group_2_1_1__0 : rule__VariableString__Group_2_1_1__0__Impl rule__VariableString__Group_2_1_1__1 ;
	public final void rule__VariableString__Group_2_1_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2252:2: ( rule__VariableString__Group_2_1_1__0__Impl rule__VariableString__Group_2_1_1__1 )
			// InternalDsl.g:2253:2: rule__VariableString__Group_2_1_1__0__Impl rule__VariableString__Group_2_1_1__1
			{
			pushFollow(FOLLOW_26);
			rule__VariableString__Group_2_1_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_1__0"



	// $ANTLR start "rule__VariableString__Group_2_1_1__0__Impl"
	// InternalDsl.g:2260:1: rule__VariableString__Group_2_1_1__0__Impl : ( '.' ) ;
	public final void rule__VariableString__Group_2_1_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2264:2: ( ( '.' ) )
			// InternalDsl.g:2265:2: ( '.' )
			{
			// InternalDsl.g:2265:2: ( '.' )
			// InternalDsl.g:2266:2: '.'
			{
			 before(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_1_1_0()); 
			match(input,18,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_1_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_1__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_1_1__1"
	// InternalDsl.g:2275:1: rule__VariableString__Group_2_1_1__1 : rule__VariableString__Group_2_1_1__1__Impl ;
	public final void rule__VariableString__Group_2_1_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2279:2: ( rule__VariableString__Group_2_1_1__1__Impl )
			// InternalDsl.g:2280:2: rule__VariableString__Group_2_1_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_1_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_1__1"



	// $ANTLR start "rule__VariableString__Group_2_1_1__1__Impl"
	// InternalDsl.g:2286:1: rule__VariableString__Group_2_1_1__1__Impl : ( ruleVariableString ) ;
	public final void rule__VariableString__Group_2_1_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2290:2: ( ( ruleVariableString ) )
			// InternalDsl.g:2291:2: ( ruleVariableString )
			{
			// InternalDsl.g:2291:2: ( ruleVariableString )
			// InternalDsl.g:2292:2: ruleVariableString
			{
			 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_1_1()); 
			pushFollow(FOLLOW_2);
			ruleVariableString();
			state._fsp--;

			 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_1_1__1__Impl"



	// $ANTLR start "rule__VariableString__Group_2_2__0"
	// InternalDsl.g:2302:1: rule__VariableString__Group_2_2__0 : rule__VariableString__Group_2_2__0__Impl rule__VariableString__Group_2_2__1 ;
	public final void rule__VariableString__Group_2_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2306:2: ( rule__VariableString__Group_2_2__0__Impl rule__VariableString__Group_2_2__1 )
			// InternalDsl.g:2307:2: rule__VariableString__Group_2_2__0__Impl rule__VariableString__Group_2_2__1
			{
			pushFollow(FOLLOW_26);
			rule__VariableString__Group_2_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_2__0"



	// $ANTLR start "rule__VariableString__Group_2_2__0__Impl"
	// InternalDsl.g:2314:1: rule__VariableString__Group_2_2__0__Impl : ( '.' ) ;
	public final void rule__VariableString__Group_2_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2318:2: ( ( '.' ) )
			// InternalDsl.g:2319:2: ( '.' )
			{
			// InternalDsl.g:2319:2: ( '.' )
			// InternalDsl.g:2320:2: '.'
			{
			 before(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_2_0()); 
			match(input,18,FOLLOW_2); 
			 after(grammarAccess.getVariableStringAccess().getFullStopKeyword_2_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_2__0__Impl"



	// $ANTLR start "rule__VariableString__Group_2_2__1"
	// InternalDsl.g:2329:1: rule__VariableString__Group_2_2__1 : rule__VariableString__Group_2_2__1__Impl ;
	public final void rule__VariableString__Group_2_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2333:2: ( rule__VariableString__Group_2_2__1__Impl )
			// InternalDsl.g:2334:2: rule__VariableString__Group_2_2__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__VariableString__Group_2_2__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_2__1"



	// $ANTLR start "rule__VariableString__Group_2_2__1__Impl"
	// InternalDsl.g:2340:1: rule__VariableString__Group_2_2__1__Impl : ( ruleVariableString ) ;
	public final void rule__VariableString__Group_2_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2344:2: ( ( ruleVariableString ) )
			// InternalDsl.g:2345:2: ( ruleVariableString )
			{
			// InternalDsl.g:2345:2: ( ruleVariableString )
			// InternalDsl.g:2346:2: ruleVariableString
			{
			 before(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_2_1()); 
			pushFollow(FOLLOW_2);
			ruleVariableString();
			state._fsp--;

			 after(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__VariableString__Group_2_2__1__Impl"



	// $ANTLR start "rule__AbstractStatement_Impl__Group__0"
	// InternalDsl.g:2356:1: rule__AbstractStatement_Impl__Group__0 : rule__AbstractStatement_Impl__Group__0__Impl rule__AbstractStatement_Impl__Group__1 ;
	public final void rule__AbstractStatement_Impl__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2360:2: ( rule__AbstractStatement_Impl__Group__0__Impl rule__AbstractStatement_Impl__Group__1 )
			// InternalDsl.g:2361:2: rule__AbstractStatement_Impl__Group__0__Impl rule__AbstractStatement_Impl__Group__1
			{
			pushFollow(FOLLOW_19);
			rule__AbstractStatement_Impl__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__AbstractStatement_Impl__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement_Impl__Group__0"



	// $ANTLR start "rule__AbstractStatement_Impl__Group__0__Impl"
	// InternalDsl.g:2368:1: rule__AbstractStatement_Impl__Group__0__Impl : ( () ) ;
	public final void rule__AbstractStatement_Impl__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2372:2: ( ( () ) )
			// InternalDsl.g:2373:2: ( () )
			{
			// InternalDsl.g:2373:2: ( () )
			// InternalDsl.g:2374:2: ()
			{
			 before(grammarAccess.getAbstractStatement_ImplAccess().getAbstractStatementAction_0()); 
			// InternalDsl.g:2375:2: ()
			// InternalDsl.g:2375:3: 
			{
			}

			 after(grammarAccess.getAbstractStatement_ImplAccess().getAbstractStatementAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement_Impl__Group__0__Impl"



	// $ANTLR start "rule__AbstractStatement_Impl__Group__1"
	// InternalDsl.g:2383:1: rule__AbstractStatement_Impl__Group__1 : rule__AbstractStatement_Impl__Group__1__Impl ;
	public final void rule__AbstractStatement_Impl__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2387:2: ( rule__AbstractStatement_Impl__Group__1__Impl )
			// InternalDsl.g:2388:2: rule__AbstractStatement_Impl__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__AbstractStatement_Impl__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement_Impl__Group__1"



	// $ANTLR start "rule__AbstractStatement_Impl__Group__1__Impl"
	// InternalDsl.g:2394:1: rule__AbstractStatement_Impl__Group__1__Impl : ( ( rule__AbstractStatement_Impl__NameAssignment_1 ) ) ;
	public final void rule__AbstractStatement_Impl__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2398:2: ( ( ( rule__AbstractStatement_Impl__NameAssignment_1 ) ) )
			// InternalDsl.g:2399:2: ( ( rule__AbstractStatement_Impl__NameAssignment_1 ) )
			{
			// InternalDsl.g:2399:2: ( ( rule__AbstractStatement_Impl__NameAssignment_1 ) )
			// InternalDsl.g:2400:2: ( rule__AbstractStatement_Impl__NameAssignment_1 )
			{
			 before(grammarAccess.getAbstractStatement_ImplAccess().getNameAssignment_1()); 
			// InternalDsl.g:2401:2: ( rule__AbstractStatement_Impl__NameAssignment_1 )
			// InternalDsl.g:2401:3: rule__AbstractStatement_Impl__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__AbstractStatement_Impl__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getAbstractStatement_ImplAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement_Impl__Group__1__Impl"



	// $ANTLR start "rule__MethodStatement__Group__0"
	// InternalDsl.g:2410:1: rule__MethodStatement__Group__0 : rule__MethodStatement__Group__0__Impl rule__MethodStatement__Group__1 ;
	public final void rule__MethodStatement__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2414:2: ( rule__MethodStatement__Group__0__Impl rule__MethodStatement__Group__1 )
			// InternalDsl.g:2415:2: rule__MethodStatement__Group__0__Impl rule__MethodStatement__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__MethodStatement__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__MethodStatement__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__MethodStatement__Group__0"



	// $ANTLR start "rule__MethodStatement__Group__0__Impl"
	// InternalDsl.g:2422:1: rule__MethodStatement__Group__0__Impl : ( () ) ;
	public final void rule__MethodStatement__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2426:2: ( ( () ) )
			// InternalDsl.g:2427:2: ( () )
			{
			// InternalDsl.g:2427:2: ( () )
			// InternalDsl.g:2428:2: ()
			{
			 before(grammarAccess.getMethodStatementAccess().getMethodStatementAction_0()); 
			// InternalDsl.g:2429:2: ()
			// InternalDsl.g:2429:3: 
			{
			}

			 after(grammarAccess.getMethodStatementAccess().getMethodStatementAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__MethodStatement__Group__0__Impl"



	// $ANTLR start "rule__MethodStatement__Group__1"
	// InternalDsl.g:2437:1: rule__MethodStatement__Group__1 : rule__MethodStatement__Group__1__Impl ;
	public final void rule__MethodStatement__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2441:2: ( rule__MethodStatement__Group__1__Impl )
			// InternalDsl.g:2442:2: rule__MethodStatement__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__MethodStatement__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__MethodStatement__Group__1"



	// $ANTLR start "rule__MethodStatement__Group__1__Impl"
	// InternalDsl.g:2448:1: rule__MethodStatement__Group__1__Impl : ( ( rule__MethodStatement__NameAssignment_1 ) ) ;
	public final void rule__MethodStatement__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2452:2: ( ( ( rule__MethodStatement__NameAssignment_1 ) ) )
			// InternalDsl.g:2453:2: ( ( rule__MethodStatement__NameAssignment_1 ) )
			{
			// InternalDsl.g:2453:2: ( ( rule__MethodStatement__NameAssignment_1 ) )
			// InternalDsl.g:2454:2: ( rule__MethodStatement__NameAssignment_1 )
			{
			 before(grammarAccess.getMethodStatementAccess().getNameAssignment_1()); 
			// InternalDsl.g:2455:2: ( rule__MethodStatement__NameAssignment_1 )
			// InternalDsl.g:2455:3: rule__MethodStatement__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__MethodStatement__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getMethodStatementAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__MethodStatement__Group__1__Impl"



	// $ANTLR start "rule__Condition__Group__0"
	// InternalDsl.g:2464:1: rule__Condition__Group__0 : rule__Condition__Group__0__Impl rule__Condition__Group__1 ;
	public final void rule__Condition__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2468:2: ( rule__Condition__Group__0__Impl rule__Condition__Group__1 )
			// InternalDsl.g:2469:2: rule__Condition__Group__0__Impl rule__Condition__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__Condition__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Condition__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Condition__Group__0"



	// $ANTLR start "rule__Condition__Group__0__Impl"
	// InternalDsl.g:2476:1: rule__Condition__Group__0__Impl : ( () ) ;
	public final void rule__Condition__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2480:2: ( ( () ) )
			// InternalDsl.g:2481:2: ( () )
			{
			// InternalDsl.g:2481:2: ( () )
			// InternalDsl.g:2482:2: ()
			{
			 before(grammarAccess.getConditionAccess().getConditionAction_0()); 
			// InternalDsl.g:2483:2: ()
			// InternalDsl.g:2483:3: 
			{
			}

			 after(grammarAccess.getConditionAccess().getConditionAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Condition__Group__0__Impl"



	// $ANTLR start "rule__Condition__Group__1"
	// InternalDsl.g:2491:1: rule__Condition__Group__1 : rule__Condition__Group__1__Impl ;
	public final void rule__Condition__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2495:2: ( rule__Condition__Group__1__Impl )
			// InternalDsl.g:2496:2: rule__Condition__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Condition__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Condition__Group__1"



	// $ANTLR start "rule__Condition__Group__1__Impl"
	// InternalDsl.g:2502:1: rule__Condition__Group__1__Impl : ( ( rule__Condition__NameAssignment_1 ) ) ;
	public final void rule__Condition__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2506:2: ( ( ( rule__Condition__NameAssignment_1 ) ) )
			// InternalDsl.g:2507:2: ( ( rule__Condition__NameAssignment_1 ) )
			{
			// InternalDsl.g:2507:2: ( ( rule__Condition__NameAssignment_1 ) )
			// InternalDsl.g:2508:2: ( rule__Condition__NameAssignment_1 )
			{
			 before(grammarAccess.getConditionAccess().getNameAssignment_1()); 
			// InternalDsl.g:2509:2: ( rule__Condition__NameAssignment_1 )
			// InternalDsl.g:2509:3: rule__Condition__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__Condition__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getConditionAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Condition__Group__1__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__0"
	// InternalDsl.g:2518:1: rule__CompositionStatement__Group__0 : rule__CompositionStatement__Group__0__Impl rule__CompositionStatement__Group__1 ;
	public final void rule__CompositionStatement__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2522:2: ( rule__CompositionStatement__Group__0__Impl rule__CompositionStatement__Group__1 )
			// InternalDsl.g:2523:2: rule__CompositionStatement__Group__0__Impl rule__CompositionStatement__Group__1
			{
			pushFollow(FOLLOW_6);
			rule__CompositionStatement__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__0"



	// $ANTLR start "rule__CompositionStatement__Group__0__Impl"
	// InternalDsl.g:2530:1: rule__CompositionStatement__Group__0__Impl : ( () ) ;
	public final void rule__CompositionStatement__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2534:2: ( ( () ) )
			// InternalDsl.g:2535:2: ( () )
			{
			// InternalDsl.g:2535:2: ( () )
			// InternalDsl.g:2536:2: ()
			{
			 before(grammarAccess.getCompositionStatementAccess().getCompositionStatementAction_0()); 
			// InternalDsl.g:2537:2: ()
			// InternalDsl.g:2537:3: 
			{
			}

			 after(grammarAccess.getCompositionStatementAccess().getCompositionStatementAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__0__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__1"
	// InternalDsl.g:2545:1: rule__CompositionStatement__Group__1 : rule__CompositionStatement__Group__1__Impl rule__CompositionStatement__Group__2 ;
	public final void rule__CompositionStatement__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2549:2: ( rule__CompositionStatement__Group__1__Impl rule__CompositionStatement__Group__2 )
			// InternalDsl.g:2550:2: rule__CompositionStatement__Group__1__Impl rule__CompositionStatement__Group__2
			{
			pushFollow(FOLLOW_8);
			rule__CompositionStatement__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__1"



	// $ANTLR start "rule__CompositionStatement__Group__1__Impl"
	// InternalDsl.g:2557:1: rule__CompositionStatement__Group__1__Impl : ( '{' ) ;
	public final void rule__CompositionStatement__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2561:2: ( ( '{' ) )
			// InternalDsl.g:2562:2: ( '{' )
			{
			// InternalDsl.g:2562:2: ( '{' )
			// InternalDsl.g:2563:2: '{'
			{
			 before(grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_1()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__1__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__2"
	// InternalDsl.g:2572:1: rule__CompositionStatement__Group__2 : rule__CompositionStatement__Group__2__Impl rule__CompositionStatement__Group__3 ;
	public final void rule__CompositionStatement__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2576:2: ( rule__CompositionStatement__Group__2__Impl rule__CompositionStatement__Group__3 )
			// InternalDsl.g:2577:2: rule__CompositionStatement__Group__2__Impl rule__CompositionStatement__Group__3
			{
			pushFollow(FOLLOW_7);
			rule__CompositionStatement__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__2"



	// $ANTLR start "rule__CompositionStatement__Group__2__Impl"
	// InternalDsl.g:2584:1: rule__CompositionStatement__Group__2__Impl : ( ( rule__CompositionStatement__FirstStatementAssignment_2 ) ) ;
	public final void rule__CompositionStatement__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2588:2: ( ( ( rule__CompositionStatement__FirstStatementAssignment_2 ) ) )
			// InternalDsl.g:2589:2: ( ( rule__CompositionStatement__FirstStatementAssignment_2 ) )
			{
			// InternalDsl.g:2589:2: ( ( rule__CompositionStatement__FirstStatementAssignment_2 ) )
			// InternalDsl.g:2590:2: ( rule__CompositionStatement__FirstStatementAssignment_2 )
			{
			 before(grammarAccess.getCompositionStatementAccess().getFirstStatementAssignment_2()); 
			// InternalDsl.g:2591:2: ( rule__CompositionStatement__FirstStatementAssignment_2 )
			// InternalDsl.g:2591:3: rule__CompositionStatement__FirstStatementAssignment_2
			{
			pushFollow(FOLLOW_2);
			rule__CompositionStatement__FirstStatementAssignment_2();
			state._fsp--;

			}

			 after(grammarAccess.getCompositionStatementAccess().getFirstStatementAssignment_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__2__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__3"
	// InternalDsl.g:2599:1: rule__CompositionStatement__Group__3 : rule__CompositionStatement__Group__3__Impl rule__CompositionStatement__Group__4 ;
	public final void rule__CompositionStatement__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2603:2: ( rule__CompositionStatement__Group__3__Impl rule__CompositionStatement__Group__4 )
			// InternalDsl.g:2604:2: rule__CompositionStatement__Group__3__Impl rule__CompositionStatement__Group__4
			{
			pushFollow(FOLLOW_28);
			rule__CompositionStatement__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__3"



	// $ANTLR start "rule__CompositionStatement__Group__3__Impl"
	// InternalDsl.g:2611:1: rule__CompositionStatement__Group__3__Impl : ( '}' ) ;
	public final void rule__CompositionStatement__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2615:2: ( ( '}' ) )
			// InternalDsl.g:2616:2: ( '}' )
			{
			// InternalDsl.g:2616:2: ( '}' )
			// InternalDsl.g:2617:2: '}'
			{
			 before(grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_3()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__3__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__4"
	// InternalDsl.g:2626:1: rule__CompositionStatement__Group__4 : rule__CompositionStatement__Group__4__Impl rule__CompositionStatement__Group__5 ;
	public final void rule__CompositionStatement__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2630:2: ( rule__CompositionStatement__Group__4__Impl rule__CompositionStatement__Group__5 )
			// InternalDsl.g:2631:2: rule__CompositionStatement__Group__4__Impl rule__CompositionStatement__Group__5
			{
			pushFollow(FOLLOW_29);
			rule__CompositionStatement__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__4"



	// $ANTLR start "rule__CompositionStatement__Group__4__Impl"
	// InternalDsl.g:2638:1: rule__CompositionStatement__Group__4__Impl : ( 'intm:' ) ;
	public final void rule__CompositionStatement__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2642:2: ( ( 'intm:' ) )
			// InternalDsl.g:2643:2: ( 'intm:' )
			{
			// InternalDsl.g:2643:2: ( 'intm:' )
			// InternalDsl.g:2644:2: 'intm:'
			{
			 before(grammarAccess.getCompositionStatementAccess().getIntmKeyword_4()); 
			match(input,34,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getIntmKeyword_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__4__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__5"
	// InternalDsl.g:2653:1: rule__CompositionStatement__Group__5 : rule__CompositionStatement__Group__5__Impl rule__CompositionStatement__Group__6 ;
	public final void rule__CompositionStatement__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2657:2: ( rule__CompositionStatement__Group__5__Impl rule__CompositionStatement__Group__6 )
			// InternalDsl.g:2658:2: rule__CompositionStatement__Group__5__Impl rule__CompositionStatement__Group__6
			{
			pushFollow(FOLLOW_4);
			rule__CompositionStatement__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__5"



	// $ANTLR start "rule__CompositionStatement__Group__5__Impl"
	// InternalDsl.g:2665:1: rule__CompositionStatement__Group__5__Impl : ( '[' ) ;
	public final void rule__CompositionStatement__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2669:2: ( ( '[' ) )
			// InternalDsl.g:2670:2: ( '[' )
			{
			// InternalDsl.g:2670:2: ( '[' )
			// InternalDsl.g:2671:2: '['
			{
			 before(grammarAccess.getCompositionStatementAccess().getLeftSquareBracketKeyword_5()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getLeftSquareBracketKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__5__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__6"
	// InternalDsl.g:2680:1: rule__CompositionStatement__Group__6 : rule__CompositionStatement__Group__6__Impl rule__CompositionStatement__Group__7 ;
	public final void rule__CompositionStatement__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2684:2: ( rule__CompositionStatement__Group__6__Impl rule__CompositionStatement__Group__7 )
			// InternalDsl.g:2685:2: rule__CompositionStatement__Group__6__Impl rule__CompositionStatement__Group__7
			{
			pushFollow(FOLLOW_17);
			rule__CompositionStatement__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__6"



	// $ANTLR start "rule__CompositionStatement__Group__6__Impl"
	// InternalDsl.g:2692:1: rule__CompositionStatement__Group__6__Impl : ( ( rule__CompositionStatement__IntermediateConditionAssignment_6 ) ) ;
	public final void rule__CompositionStatement__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2696:2: ( ( ( rule__CompositionStatement__IntermediateConditionAssignment_6 ) ) )
			// InternalDsl.g:2697:2: ( ( rule__CompositionStatement__IntermediateConditionAssignment_6 ) )
			{
			// InternalDsl.g:2697:2: ( ( rule__CompositionStatement__IntermediateConditionAssignment_6 ) )
			// InternalDsl.g:2698:2: ( rule__CompositionStatement__IntermediateConditionAssignment_6 )
			{
			 before(grammarAccess.getCompositionStatementAccess().getIntermediateConditionAssignment_6()); 
			// InternalDsl.g:2699:2: ( rule__CompositionStatement__IntermediateConditionAssignment_6 )
			// InternalDsl.g:2699:3: rule__CompositionStatement__IntermediateConditionAssignment_6
			{
			pushFollow(FOLLOW_2);
			rule__CompositionStatement__IntermediateConditionAssignment_6();
			state._fsp--;

			}

			 after(grammarAccess.getCompositionStatementAccess().getIntermediateConditionAssignment_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__6__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__7"
	// InternalDsl.g:2707:1: rule__CompositionStatement__Group__7 : rule__CompositionStatement__Group__7__Impl rule__CompositionStatement__Group__8 ;
	public final void rule__CompositionStatement__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2711:2: ( rule__CompositionStatement__Group__7__Impl rule__CompositionStatement__Group__8 )
			// InternalDsl.g:2712:2: rule__CompositionStatement__Group__7__Impl rule__CompositionStatement__Group__8
			{
			pushFollow(FOLLOW_6);
			rule__CompositionStatement__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__7"



	// $ANTLR start "rule__CompositionStatement__Group__7__Impl"
	// InternalDsl.g:2719:1: rule__CompositionStatement__Group__7__Impl : ( ']' ) ;
	public final void rule__CompositionStatement__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2723:2: ( ( ']' ) )
			// InternalDsl.g:2724:2: ( ']' )
			{
			// InternalDsl.g:2724:2: ( ']' )
			// InternalDsl.g:2725:2: ']'
			{
			 before(grammarAccess.getCompositionStatementAccess().getRightSquareBracketKeyword_7()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getRightSquareBracketKeyword_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__7__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__8"
	// InternalDsl.g:2734:1: rule__CompositionStatement__Group__8 : rule__CompositionStatement__Group__8__Impl rule__CompositionStatement__Group__9 ;
	public final void rule__CompositionStatement__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2738:2: ( rule__CompositionStatement__Group__8__Impl rule__CompositionStatement__Group__9 )
			// InternalDsl.g:2739:2: rule__CompositionStatement__Group__8__Impl rule__CompositionStatement__Group__9
			{
			pushFollow(FOLLOW_8);
			rule__CompositionStatement__Group__8__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__9();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__8"



	// $ANTLR start "rule__CompositionStatement__Group__8__Impl"
	// InternalDsl.g:2746:1: rule__CompositionStatement__Group__8__Impl : ( '{' ) ;
	public final void rule__CompositionStatement__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2750:2: ( ( '{' ) )
			// InternalDsl.g:2751:2: ( '{' )
			{
			// InternalDsl.g:2751:2: ( '{' )
			// InternalDsl.g:2752:2: '{'
			{
			 before(grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_8()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__8__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__9"
	// InternalDsl.g:2761:1: rule__CompositionStatement__Group__9 : rule__CompositionStatement__Group__9__Impl rule__CompositionStatement__Group__10 ;
	public final void rule__CompositionStatement__Group__9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2765:2: ( rule__CompositionStatement__Group__9__Impl rule__CompositionStatement__Group__10 )
			// InternalDsl.g:2766:2: rule__CompositionStatement__Group__9__Impl rule__CompositionStatement__Group__10
			{
			pushFollow(FOLLOW_7);
			rule__CompositionStatement__Group__9__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__10();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__9"



	// $ANTLR start "rule__CompositionStatement__Group__9__Impl"
	// InternalDsl.g:2773:1: rule__CompositionStatement__Group__9__Impl : ( ( rule__CompositionStatement__SecondStatementAssignment_9 ) ) ;
	public final void rule__CompositionStatement__Group__9__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2777:2: ( ( ( rule__CompositionStatement__SecondStatementAssignment_9 ) ) )
			// InternalDsl.g:2778:2: ( ( rule__CompositionStatement__SecondStatementAssignment_9 ) )
			{
			// InternalDsl.g:2778:2: ( ( rule__CompositionStatement__SecondStatementAssignment_9 ) )
			// InternalDsl.g:2779:2: ( rule__CompositionStatement__SecondStatementAssignment_9 )
			{
			 before(grammarAccess.getCompositionStatementAccess().getSecondStatementAssignment_9()); 
			// InternalDsl.g:2780:2: ( rule__CompositionStatement__SecondStatementAssignment_9 )
			// InternalDsl.g:2780:3: rule__CompositionStatement__SecondStatementAssignment_9
			{
			pushFollow(FOLLOW_2);
			rule__CompositionStatement__SecondStatementAssignment_9();
			state._fsp--;

			}

			 after(grammarAccess.getCompositionStatementAccess().getSecondStatementAssignment_9()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__9__Impl"



	// $ANTLR start "rule__CompositionStatement__Group__10"
	// InternalDsl.g:2788:1: rule__CompositionStatement__Group__10 : rule__CompositionStatement__Group__10__Impl ;
	public final void rule__CompositionStatement__Group__10() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2792:2: ( rule__CompositionStatement__Group__10__Impl )
			// InternalDsl.g:2793:2: rule__CompositionStatement__Group__10__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CompositionStatement__Group__10__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__10"



	// $ANTLR start "rule__CompositionStatement__Group__10__Impl"
	// InternalDsl.g:2799:1: rule__CompositionStatement__Group__10__Impl : ( '}' ) ;
	public final void rule__CompositionStatement__Group__10__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2803:2: ( ( '}' ) )
			// InternalDsl.g:2804:2: ( '}' )
			{
			// InternalDsl.g:2804:2: ( '}' )
			// InternalDsl.g:2805:2: '}'
			{
			 before(grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_10()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_10()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__Group__10__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__0"
	// InternalDsl.g:2815:1: rule__SelectionStatement__Group__0 : rule__SelectionStatement__Group__0__Impl rule__SelectionStatement__Group__1 ;
	public final void rule__SelectionStatement__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2819:2: ( rule__SelectionStatement__Group__0__Impl rule__SelectionStatement__Group__1 )
			// InternalDsl.g:2820:2: rule__SelectionStatement__Group__0__Impl rule__SelectionStatement__Group__1
			{
			pushFollow(FOLLOW_30);
			rule__SelectionStatement__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__0"



	// $ANTLR start "rule__SelectionStatement__Group__0__Impl"
	// InternalDsl.g:2827:1: rule__SelectionStatement__Group__0__Impl : ( () ) ;
	public final void rule__SelectionStatement__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2831:2: ( ( () ) )
			// InternalDsl.g:2832:2: ( () )
			{
			// InternalDsl.g:2832:2: ( () )
			// InternalDsl.g:2833:2: ()
			{
			 before(grammarAccess.getSelectionStatementAccess().getSelectionStatementAction_0()); 
			// InternalDsl.g:2834:2: ()
			// InternalDsl.g:2834:3: 
			{
			}

			 after(grammarAccess.getSelectionStatementAccess().getSelectionStatementAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__0__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__1"
	// InternalDsl.g:2842:1: rule__SelectionStatement__Group__1 : rule__SelectionStatement__Group__1__Impl rule__SelectionStatement__Group__2 ;
	public final void rule__SelectionStatement__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2846:2: ( rule__SelectionStatement__Group__1__Impl rule__SelectionStatement__Group__2 )
			// InternalDsl.g:2847:2: rule__SelectionStatement__Group__1__Impl rule__SelectionStatement__Group__2
			{
			pushFollow(FOLLOW_31);
			rule__SelectionStatement__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__1"



	// $ANTLR start "rule__SelectionStatement__Group__1__Impl"
	// InternalDsl.g:2854:1: rule__SelectionStatement__Group__1__Impl : ( 'if' ) ;
	public final void rule__SelectionStatement__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2858:2: ( ( 'if' ) )
			// InternalDsl.g:2859:2: ( 'if' )
			{
			// InternalDsl.g:2859:2: ( 'if' )
			// InternalDsl.g:2860:2: 'if'
			{
			 before(grammarAccess.getSelectionStatementAccess().getIfKeyword_1()); 
			match(input,33,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getIfKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__1__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__2"
	// InternalDsl.g:2869:1: rule__SelectionStatement__Group__2 : rule__SelectionStatement__Group__2__Impl rule__SelectionStatement__Group__3 ;
	public final void rule__SelectionStatement__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2873:2: ( rule__SelectionStatement__Group__2__Impl rule__SelectionStatement__Group__3 )
			// InternalDsl.g:2874:2: rule__SelectionStatement__Group__2__Impl rule__SelectionStatement__Group__3
			{
			pushFollow(FOLLOW_4);
			rule__SelectionStatement__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__2"



	// $ANTLR start "rule__SelectionStatement__Group__2__Impl"
	// InternalDsl.g:2881:1: rule__SelectionStatement__Group__2__Impl : ( '(' ) ;
	public final void rule__SelectionStatement__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2885:2: ( ( '(' ) )
			// InternalDsl.g:2886:2: ( '(' )
			{
			// InternalDsl.g:2886:2: ( '(' )
			// InternalDsl.g:2887:2: '('
			{
			 before(grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_2()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__2__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__3"
	// InternalDsl.g:2896:1: rule__SelectionStatement__Group__3 : rule__SelectionStatement__Group__3__Impl rule__SelectionStatement__Group__4 ;
	public final void rule__SelectionStatement__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2900:2: ( rule__SelectionStatement__Group__3__Impl rule__SelectionStatement__Group__4 )
			// InternalDsl.g:2901:2: rule__SelectionStatement__Group__3__Impl rule__SelectionStatement__Group__4
			{
			pushFollow(FOLLOW_23);
			rule__SelectionStatement__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__3"



	// $ANTLR start "rule__SelectionStatement__Group__3__Impl"
	// InternalDsl.g:2908:1: rule__SelectionStatement__Group__3__Impl : ( ( rule__SelectionStatement__GuardsAssignment_3 ) ) ;
	public final void rule__SelectionStatement__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2912:2: ( ( ( rule__SelectionStatement__GuardsAssignment_3 ) ) )
			// InternalDsl.g:2913:2: ( ( rule__SelectionStatement__GuardsAssignment_3 ) )
			{
			// InternalDsl.g:2913:2: ( ( rule__SelectionStatement__GuardsAssignment_3 ) )
			// InternalDsl.g:2914:2: ( rule__SelectionStatement__GuardsAssignment_3 )
			{
			 before(grammarAccess.getSelectionStatementAccess().getGuardsAssignment_3()); 
			// InternalDsl.g:2915:2: ( rule__SelectionStatement__GuardsAssignment_3 )
			// InternalDsl.g:2915:3: rule__SelectionStatement__GuardsAssignment_3
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__GuardsAssignment_3();
			state._fsp--;

			}

			 after(grammarAccess.getSelectionStatementAccess().getGuardsAssignment_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__3__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__4"
	// InternalDsl.g:2923:1: rule__SelectionStatement__Group__4 : rule__SelectionStatement__Group__4__Impl rule__SelectionStatement__Group__5 ;
	public final void rule__SelectionStatement__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2927:2: ( rule__SelectionStatement__Group__4__Impl rule__SelectionStatement__Group__5 )
			// InternalDsl.g:2928:2: rule__SelectionStatement__Group__4__Impl rule__SelectionStatement__Group__5
			{
			pushFollow(FOLLOW_32);
			rule__SelectionStatement__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__4"



	// $ANTLR start "rule__SelectionStatement__Group__4__Impl"
	// InternalDsl.g:2935:1: rule__SelectionStatement__Group__4__Impl : ( ')' ) ;
	public final void rule__SelectionStatement__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2939:2: ( ( ')' ) )
			// InternalDsl.g:2940:2: ( ')' )
			{
			// InternalDsl.g:2940:2: ( ')' )
			// InternalDsl.g:2941:2: ')'
			{
			 before(grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_4()); 
			match(input,13,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__4__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__5"
	// InternalDsl.g:2950:1: rule__SelectionStatement__Group__5 : rule__SelectionStatement__Group__5__Impl rule__SelectionStatement__Group__6 ;
	public final void rule__SelectionStatement__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2954:2: ( rule__SelectionStatement__Group__5__Impl rule__SelectionStatement__Group__6 )
			// InternalDsl.g:2955:2: rule__SelectionStatement__Group__5__Impl rule__SelectionStatement__Group__6
			{
			pushFollow(FOLLOW_6);
			rule__SelectionStatement__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__5"



	// $ANTLR start "rule__SelectionStatement__Group__5__Impl"
	// InternalDsl.g:2962:1: rule__SelectionStatement__Group__5__Impl : ( 'then' ) ;
	public final void rule__SelectionStatement__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2966:2: ( ( 'then' ) )
			// InternalDsl.g:2967:2: ( 'then' )
			{
			// InternalDsl.g:2967:2: ( 'then' )
			// InternalDsl.g:2968:2: 'then'
			{
			 before(grammarAccess.getSelectionStatementAccess().getThenKeyword_5()); 
			match(input,41,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getThenKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__5__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__6"
	// InternalDsl.g:2977:1: rule__SelectionStatement__Group__6 : rule__SelectionStatement__Group__6__Impl rule__SelectionStatement__Group__7 ;
	public final void rule__SelectionStatement__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2981:2: ( rule__SelectionStatement__Group__6__Impl rule__SelectionStatement__Group__7 )
			// InternalDsl.g:2982:2: rule__SelectionStatement__Group__6__Impl rule__SelectionStatement__Group__7
			{
			pushFollow(FOLLOW_8);
			rule__SelectionStatement__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__6"



	// $ANTLR start "rule__SelectionStatement__Group__6__Impl"
	// InternalDsl.g:2989:1: rule__SelectionStatement__Group__6__Impl : ( '{' ) ;
	public final void rule__SelectionStatement__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2993:2: ( ( '{' ) )
			// InternalDsl.g:2994:2: ( '{' )
			{
			// InternalDsl.g:2994:2: ( '{' )
			// InternalDsl.g:2995:2: '{'
			{
			 before(grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_6()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__6__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__7"
	// InternalDsl.g:3004:1: rule__SelectionStatement__Group__7 : rule__SelectionStatement__Group__7__Impl rule__SelectionStatement__Group__8 ;
	public final void rule__SelectionStatement__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3008:2: ( rule__SelectionStatement__Group__7__Impl rule__SelectionStatement__Group__8 )
			// InternalDsl.g:3009:2: rule__SelectionStatement__Group__7__Impl rule__SelectionStatement__Group__8
			{
			pushFollow(FOLLOW_7);
			rule__SelectionStatement__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__7"



	// $ANTLR start "rule__SelectionStatement__Group__7__Impl"
	// InternalDsl.g:3016:1: rule__SelectionStatement__Group__7__Impl : ( ( rule__SelectionStatement__CommandsAssignment_7 ) ) ;
	public final void rule__SelectionStatement__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3020:2: ( ( ( rule__SelectionStatement__CommandsAssignment_7 ) ) )
			// InternalDsl.g:3021:2: ( ( rule__SelectionStatement__CommandsAssignment_7 ) )
			{
			// InternalDsl.g:3021:2: ( ( rule__SelectionStatement__CommandsAssignment_7 ) )
			// InternalDsl.g:3022:2: ( rule__SelectionStatement__CommandsAssignment_7 )
			{
			 before(grammarAccess.getSelectionStatementAccess().getCommandsAssignment_7()); 
			// InternalDsl.g:3023:2: ( rule__SelectionStatement__CommandsAssignment_7 )
			// InternalDsl.g:3023:3: rule__SelectionStatement__CommandsAssignment_7
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__CommandsAssignment_7();
			state._fsp--;

			}

			 after(grammarAccess.getSelectionStatementAccess().getCommandsAssignment_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__7__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__8"
	// InternalDsl.g:3031:1: rule__SelectionStatement__Group__8 : rule__SelectionStatement__Group__8__Impl rule__SelectionStatement__Group__9 ;
	public final void rule__SelectionStatement__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3035:2: ( rule__SelectionStatement__Group__8__Impl rule__SelectionStatement__Group__9 )
			// InternalDsl.g:3036:2: rule__SelectionStatement__Group__8__Impl rule__SelectionStatement__Group__9
			{
			pushFollow(FOLLOW_33);
			rule__SelectionStatement__Group__8__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__9();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__8"



	// $ANTLR start "rule__SelectionStatement__Group__8__Impl"
	// InternalDsl.g:3043:1: rule__SelectionStatement__Group__8__Impl : ( '}' ) ;
	public final void rule__SelectionStatement__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3047:2: ( ( '}' ) )
			// InternalDsl.g:3048:2: ( '}' )
			{
			// InternalDsl.g:3048:2: ( '}' )
			// InternalDsl.g:3049:2: '}'
			{
			 before(grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_8()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__8__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__9"
	// InternalDsl.g:3058:1: rule__SelectionStatement__Group__9 : rule__SelectionStatement__Group__9__Impl rule__SelectionStatement__Group__10 ;
	public final void rule__SelectionStatement__Group__9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3062:2: ( rule__SelectionStatement__Group__9__Impl rule__SelectionStatement__Group__10 )
			// InternalDsl.g:3063:2: rule__SelectionStatement__Group__9__Impl rule__SelectionStatement__Group__10
			{
			pushFollow(FOLLOW_34);
			rule__SelectionStatement__Group__9__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__10();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__9"



	// $ANTLR start "rule__SelectionStatement__Group__9__Impl"
	// InternalDsl.g:3070:1: rule__SelectionStatement__Group__9__Impl : ( ( rule__SelectionStatement__Group_9__0 )* ) ;
	public final void rule__SelectionStatement__Group__9__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3074:2: ( ( ( rule__SelectionStatement__Group_9__0 )* ) )
			// InternalDsl.g:3075:2: ( ( rule__SelectionStatement__Group_9__0 )* )
			{
			// InternalDsl.g:3075:2: ( ( rule__SelectionStatement__Group_9__0 )* )
			// InternalDsl.g:3076:2: ( rule__SelectionStatement__Group_9__0 )*
			{
			 before(grammarAccess.getSelectionStatementAccess().getGroup_9()); 
			// InternalDsl.g:3077:2: ( rule__SelectionStatement__Group_9__0 )*
			loop26:
			while (true) {
				int alt26=2;
				int LA26_0 = input.LA(1);
				if ( (LA26_0==30) ) {
					alt26=1;
				}

				switch (alt26) {
				case 1 :
					// InternalDsl.g:3077:3: rule__SelectionStatement__Group_9__0
					{
					pushFollow(FOLLOW_35);
					rule__SelectionStatement__Group_9__0();
					state._fsp--;

					}
					break;

				default :
					break loop26;
				}
			}

			 after(grammarAccess.getSelectionStatementAccess().getGroup_9()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__9__Impl"



	// $ANTLR start "rule__SelectionStatement__Group__10"
	// InternalDsl.g:3085:1: rule__SelectionStatement__Group__10 : rule__SelectionStatement__Group__10__Impl ;
	public final void rule__SelectionStatement__Group__10() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3089:2: ( rule__SelectionStatement__Group__10__Impl )
			// InternalDsl.g:3090:2: rule__SelectionStatement__Group__10__Impl
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group__10__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__10"



	// $ANTLR start "rule__SelectionStatement__Group__10__Impl"
	// InternalDsl.g:3096:1: rule__SelectionStatement__Group__10__Impl : ( 'fi' ) ;
	public final void rule__SelectionStatement__Group__10__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3100:2: ( ( 'fi' ) )
			// InternalDsl.g:3101:2: ( 'fi' )
			{
			// InternalDsl.g:3101:2: ( 'fi' )
			// InternalDsl.g:3102:2: 'fi'
			{
			 before(grammarAccess.getSelectionStatementAccess().getFiKeyword_10()); 
			match(input,31,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getFiKeyword_10()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group__10__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__0"
	// InternalDsl.g:3112:1: rule__SelectionStatement__Group_9__0 : rule__SelectionStatement__Group_9__0__Impl rule__SelectionStatement__Group_9__1 ;
	public final void rule__SelectionStatement__Group_9__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3116:2: ( rule__SelectionStatement__Group_9__0__Impl rule__SelectionStatement__Group_9__1 )
			// InternalDsl.g:3117:2: rule__SelectionStatement__Group_9__0__Impl rule__SelectionStatement__Group_9__1
			{
			pushFollow(FOLLOW_31);
			rule__SelectionStatement__Group_9__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__0"



	// $ANTLR start "rule__SelectionStatement__Group_9__0__Impl"
	// InternalDsl.g:3124:1: rule__SelectionStatement__Group_9__0__Impl : ( 'elseif' ) ;
	public final void rule__SelectionStatement__Group_9__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3128:2: ( ( 'elseif' ) )
			// InternalDsl.g:3129:2: ( 'elseif' )
			{
			// InternalDsl.g:3129:2: ( 'elseif' )
			// InternalDsl.g:3130:2: 'elseif'
			{
			 before(grammarAccess.getSelectionStatementAccess().getElseifKeyword_9_0()); 
			match(input,30,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getElseifKeyword_9_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__0__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__1"
	// InternalDsl.g:3139:1: rule__SelectionStatement__Group_9__1 : rule__SelectionStatement__Group_9__1__Impl rule__SelectionStatement__Group_9__2 ;
	public final void rule__SelectionStatement__Group_9__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3143:2: ( rule__SelectionStatement__Group_9__1__Impl rule__SelectionStatement__Group_9__2 )
			// InternalDsl.g:3144:2: rule__SelectionStatement__Group_9__1__Impl rule__SelectionStatement__Group_9__2
			{
			pushFollow(FOLLOW_4);
			rule__SelectionStatement__Group_9__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__1"



	// $ANTLR start "rule__SelectionStatement__Group_9__1__Impl"
	// InternalDsl.g:3151:1: rule__SelectionStatement__Group_9__1__Impl : ( '(' ) ;
	public final void rule__SelectionStatement__Group_9__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3155:2: ( ( '(' ) )
			// InternalDsl.g:3156:2: ( '(' )
			{
			// InternalDsl.g:3156:2: ( '(' )
			// InternalDsl.g:3157:2: '('
			{
			 before(grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_9_1()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_9_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__1__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__2"
	// InternalDsl.g:3166:1: rule__SelectionStatement__Group_9__2 : rule__SelectionStatement__Group_9__2__Impl rule__SelectionStatement__Group_9__3 ;
	public final void rule__SelectionStatement__Group_9__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3170:2: ( rule__SelectionStatement__Group_9__2__Impl rule__SelectionStatement__Group_9__3 )
			// InternalDsl.g:3171:2: rule__SelectionStatement__Group_9__2__Impl rule__SelectionStatement__Group_9__3
			{
			pushFollow(FOLLOW_23);
			rule__SelectionStatement__Group_9__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__2"



	// $ANTLR start "rule__SelectionStatement__Group_9__2__Impl"
	// InternalDsl.g:3178:1: rule__SelectionStatement__Group_9__2__Impl : ( ( rule__SelectionStatement__GuardsAssignment_9_2 ) ) ;
	public final void rule__SelectionStatement__Group_9__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3182:2: ( ( ( rule__SelectionStatement__GuardsAssignment_9_2 ) ) )
			// InternalDsl.g:3183:2: ( ( rule__SelectionStatement__GuardsAssignment_9_2 ) )
			{
			// InternalDsl.g:3183:2: ( ( rule__SelectionStatement__GuardsAssignment_9_2 ) )
			// InternalDsl.g:3184:2: ( rule__SelectionStatement__GuardsAssignment_9_2 )
			{
			 before(grammarAccess.getSelectionStatementAccess().getGuardsAssignment_9_2()); 
			// InternalDsl.g:3185:2: ( rule__SelectionStatement__GuardsAssignment_9_2 )
			// InternalDsl.g:3185:3: rule__SelectionStatement__GuardsAssignment_9_2
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__GuardsAssignment_9_2();
			state._fsp--;

			}

			 after(grammarAccess.getSelectionStatementAccess().getGuardsAssignment_9_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__2__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__3"
	// InternalDsl.g:3193:1: rule__SelectionStatement__Group_9__3 : rule__SelectionStatement__Group_9__3__Impl rule__SelectionStatement__Group_9__4 ;
	public final void rule__SelectionStatement__Group_9__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3197:2: ( rule__SelectionStatement__Group_9__3__Impl rule__SelectionStatement__Group_9__4 )
			// InternalDsl.g:3198:2: rule__SelectionStatement__Group_9__3__Impl rule__SelectionStatement__Group_9__4
			{
			pushFollow(FOLLOW_32);
			rule__SelectionStatement__Group_9__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__3"



	// $ANTLR start "rule__SelectionStatement__Group_9__3__Impl"
	// InternalDsl.g:3205:1: rule__SelectionStatement__Group_9__3__Impl : ( ')' ) ;
	public final void rule__SelectionStatement__Group_9__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3209:2: ( ( ')' ) )
			// InternalDsl.g:3210:2: ( ')' )
			{
			// InternalDsl.g:3210:2: ( ')' )
			// InternalDsl.g:3211:2: ')'
			{
			 before(grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_9_3()); 
			match(input,13,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_9_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__3__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__4"
	// InternalDsl.g:3220:1: rule__SelectionStatement__Group_9__4 : rule__SelectionStatement__Group_9__4__Impl rule__SelectionStatement__Group_9__5 ;
	public final void rule__SelectionStatement__Group_9__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3224:2: ( rule__SelectionStatement__Group_9__4__Impl rule__SelectionStatement__Group_9__5 )
			// InternalDsl.g:3225:2: rule__SelectionStatement__Group_9__4__Impl rule__SelectionStatement__Group_9__5
			{
			pushFollow(FOLLOW_6);
			rule__SelectionStatement__Group_9__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__4"



	// $ANTLR start "rule__SelectionStatement__Group_9__4__Impl"
	// InternalDsl.g:3232:1: rule__SelectionStatement__Group_9__4__Impl : ( 'then' ) ;
	public final void rule__SelectionStatement__Group_9__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3236:2: ( ( 'then' ) )
			// InternalDsl.g:3237:2: ( 'then' )
			{
			// InternalDsl.g:3237:2: ( 'then' )
			// InternalDsl.g:3238:2: 'then'
			{
			 before(grammarAccess.getSelectionStatementAccess().getThenKeyword_9_4()); 
			match(input,41,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getThenKeyword_9_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__4__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__5"
	// InternalDsl.g:3247:1: rule__SelectionStatement__Group_9__5 : rule__SelectionStatement__Group_9__5__Impl rule__SelectionStatement__Group_9__6 ;
	public final void rule__SelectionStatement__Group_9__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3251:2: ( rule__SelectionStatement__Group_9__5__Impl rule__SelectionStatement__Group_9__6 )
			// InternalDsl.g:3252:2: rule__SelectionStatement__Group_9__5__Impl rule__SelectionStatement__Group_9__6
			{
			pushFollow(FOLLOW_8);
			rule__SelectionStatement__Group_9__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__5"



	// $ANTLR start "rule__SelectionStatement__Group_9__5__Impl"
	// InternalDsl.g:3259:1: rule__SelectionStatement__Group_9__5__Impl : ( '{' ) ;
	public final void rule__SelectionStatement__Group_9__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3263:2: ( ( '{' ) )
			// InternalDsl.g:3264:2: ( '{' )
			{
			// InternalDsl.g:3264:2: ( '{' )
			// InternalDsl.g:3265:2: '{'
			{
			 before(grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_9_5()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_9_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__5__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__6"
	// InternalDsl.g:3274:1: rule__SelectionStatement__Group_9__6 : rule__SelectionStatement__Group_9__6__Impl rule__SelectionStatement__Group_9__7 ;
	public final void rule__SelectionStatement__Group_9__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3278:2: ( rule__SelectionStatement__Group_9__6__Impl rule__SelectionStatement__Group_9__7 )
			// InternalDsl.g:3279:2: rule__SelectionStatement__Group_9__6__Impl rule__SelectionStatement__Group_9__7
			{
			pushFollow(FOLLOW_7);
			rule__SelectionStatement__Group_9__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__6"



	// $ANTLR start "rule__SelectionStatement__Group_9__6__Impl"
	// InternalDsl.g:3286:1: rule__SelectionStatement__Group_9__6__Impl : ( ( rule__SelectionStatement__CommandsAssignment_9_6 ) ) ;
	public final void rule__SelectionStatement__Group_9__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3290:2: ( ( ( rule__SelectionStatement__CommandsAssignment_9_6 ) ) )
			// InternalDsl.g:3291:2: ( ( rule__SelectionStatement__CommandsAssignment_9_6 ) )
			{
			// InternalDsl.g:3291:2: ( ( rule__SelectionStatement__CommandsAssignment_9_6 ) )
			// InternalDsl.g:3292:2: ( rule__SelectionStatement__CommandsAssignment_9_6 )
			{
			 before(grammarAccess.getSelectionStatementAccess().getCommandsAssignment_9_6()); 
			// InternalDsl.g:3293:2: ( rule__SelectionStatement__CommandsAssignment_9_6 )
			// InternalDsl.g:3293:3: rule__SelectionStatement__CommandsAssignment_9_6
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__CommandsAssignment_9_6();
			state._fsp--;

			}

			 after(grammarAccess.getSelectionStatementAccess().getCommandsAssignment_9_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__6__Impl"



	// $ANTLR start "rule__SelectionStatement__Group_9__7"
	// InternalDsl.g:3301:1: rule__SelectionStatement__Group_9__7 : rule__SelectionStatement__Group_9__7__Impl ;
	public final void rule__SelectionStatement__Group_9__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3305:2: ( rule__SelectionStatement__Group_9__7__Impl )
			// InternalDsl.g:3306:2: rule__SelectionStatement__Group_9__7__Impl
			{
			pushFollow(FOLLOW_2);
			rule__SelectionStatement__Group_9__7__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__7"



	// $ANTLR start "rule__SelectionStatement__Group_9__7__Impl"
	// InternalDsl.g:3312:1: rule__SelectionStatement__Group_9__7__Impl : ( '}' ) ;
	public final void rule__SelectionStatement__Group_9__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3316:2: ( ( '}' ) )
			// InternalDsl.g:3317:2: ( '}' )
			{
			// InternalDsl.g:3317:2: ( '}' )
			// InternalDsl.g:3318:2: '}'
			{
			 before(grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_9_7()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_9_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__Group_9__7__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__0"
	// InternalDsl.g:3328:1: rule__SmallRepetitionStatement__Group__0 : rule__SmallRepetitionStatement__Group__0__Impl rule__SmallRepetitionStatement__Group__1 ;
	public final void rule__SmallRepetitionStatement__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3332:2: ( rule__SmallRepetitionStatement__Group__0__Impl rule__SmallRepetitionStatement__Group__1 )
			// InternalDsl.g:3333:2: rule__SmallRepetitionStatement__Group__0__Impl rule__SmallRepetitionStatement__Group__1
			{
			pushFollow(FOLLOW_36);
			rule__SmallRepetitionStatement__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__0"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__0__Impl"
	// InternalDsl.g:3340:1: rule__SmallRepetitionStatement__Group__0__Impl : ( () ) ;
	public final void rule__SmallRepetitionStatement__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3344:2: ( ( () ) )
			// InternalDsl.g:3345:2: ( () )
			{
			// InternalDsl.g:3345:2: ( () )
			// InternalDsl.g:3346:2: ()
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getSmallRepetitionStatementAction_0()); 
			// InternalDsl.g:3347:2: ()
			// InternalDsl.g:3347:3: 
			{
			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getSmallRepetitionStatementAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__0__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__1"
	// InternalDsl.g:3355:1: rule__SmallRepetitionStatement__Group__1 : rule__SmallRepetitionStatement__Group__1__Impl rule__SmallRepetitionStatement__Group__2 ;
	public final void rule__SmallRepetitionStatement__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3359:2: ( rule__SmallRepetitionStatement__Group__1__Impl rule__SmallRepetitionStatement__Group__2 )
			// InternalDsl.g:3360:2: rule__SmallRepetitionStatement__Group__1__Impl rule__SmallRepetitionStatement__Group__2
			{
			pushFollow(FOLLOW_31);
			rule__SmallRepetitionStatement__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__1"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__1__Impl"
	// InternalDsl.g:3367:1: rule__SmallRepetitionStatement__Group__1__Impl : ( 'while' ) ;
	public final void rule__SmallRepetitionStatement__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3371:2: ( ( 'while' ) )
			// InternalDsl.g:3372:2: ( 'while' )
			{
			// InternalDsl.g:3372:2: ( 'while' )
			// InternalDsl.g:3373:2: 'while'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getWhileKeyword_1()); 
			match(input,45,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getWhileKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__1__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__2"
	// InternalDsl.g:3382:1: rule__SmallRepetitionStatement__Group__2 : rule__SmallRepetitionStatement__Group__2__Impl rule__SmallRepetitionStatement__Group__3 ;
	public final void rule__SmallRepetitionStatement__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3386:2: ( rule__SmallRepetitionStatement__Group__2__Impl rule__SmallRepetitionStatement__Group__3 )
			// InternalDsl.g:3387:2: rule__SmallRepetitionStatement__Group__2__Impl rule__SmallRepetitionStatement__Group__3
			{
			pushFollow(FOLLOW_4);
			rule__SmallRepetitionStatement__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__2"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__2__Impl"
	// InternalDsl.g:3394:1: rule__SmallRepetitionStatement__Group__2__Impl : ( '(' ) ;
	public final void rule__SmallRepetitionStatement__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3398:2: ( ( '(' ) )
			// InternalDsl.g:3399:2: ( '(' )
			{
			// InternalDsl.g:3399:2: ( '(' )
			// InternalDsl.g:3400:2: '('
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLeftParenthesisKeyword_2()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getLeftParenthesisKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__2__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__3"
	// InternalDsl.g:3409:1: rule__SmallRepetitionStatement__Group__3 : rule__SmallRepetitionStatement__Group__3__Impl rule__SmallRepetitionStatement__Group__4 ;
	public final void rule__SmallRepetitionStatement__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3413:2: ( rule__SmallRepetitionStatement__Group__3__Impl rule__SmallRepetitionStatement__Group__4 )
			// InternalDsl.g:3414:2: rule__SmallRepetitionStatement__Group__3__Impl rule__SmallRepetitionStatement__Group__4
			{
			pushFollow(FOLLOW_23);
			rule__SmallRepetitionStatement__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__3"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__3__Impl"
	// InternalDsl.g:3421:1: rule__SmallRepetitionStatement__Group__3__Impl : ( ( rule__SmallRepetitionStatement__GuardAssignment_3 ) ) ;
	public final void rule__SmallRepetitionStatement__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3425:2: ( ( ( rule__SmallRepetitionStatement__GuardAssignment_3 ) ) )
			// InternalDsl.g:3426:2: ( ( rule__SmallRepetitionStatement__GuardAssignment_3 ) )
			{
			// InternalDsl.g:3426:2: ( ( rule__SmallRepetitionStatement__GuardAssignment_3 ) )
			// InternalDsl.g:3427:2: ( rule__SmallRepetitionStatement__GuardAssignment_3 )
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getGuardAssignment_3()); 
			// InternalDsl.g:3428:2: ( rule__SmallRepetitionStatement__GuardAssignment_3 )
			// InternalDsl.g:3428:3: rule__SmallRepetitionStatement__GuardAssignment_3
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__GuardAssignment_3();
			state._fsp--;

			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getGuardAssignment_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__3__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__4"
	// InternalDsl.g:3436:1: rule__SmallRepetitionStatement__Group__4 : rule__SmallRepetitionStatement__Group__4__Impl rule__SmallRepetitionStatement__Group__5 ;
	public final void rule__SmallRepetitionStatement__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3440:2: ( rule__SmallRepetitionStatement__Group__4__Impl rule__SmallRepetitionStatement__Group__5 )
			// InternalDsl.g:3441:2: rule__SmallRepetitionStatement__Group__4__Impl rule__SmallRepetitionStatement__Group__5
			{
			pushFollow(FOLLOW_37);
			rule__SmallRepetitionStatement__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__4"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__4__Impl"
	// InternalDsl.g:3448:1: rule__SmallRepetitionStatement__Group__4__Impl : ( ')' ) ;
	public final void rule__SmallRepetitionStatement__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3452:2: ( ( ')' ) )
			// InternalDsl.g:3453:2: ( ')' )
			{
			// InternalDsl.g:3453:2: ( ')' )
			// InternalDsl.g:3454:2: ')'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getRightParenthesisKeyword_4()); 
			match(input,13,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getRightParenthesisKeyword_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__4__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__5"
	// InternalDsl.g:3463:1: rule__SmallRepetitionStatement__Group__5 : rule__SmallRepetitionStatement__Group__5__Impl rule__SmallRepetitionStatement__Group__6 ;
	public final void rule__SmallRepetitionStatement__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3467:2: ( rule__SmallRepetitionStatement__Group__5__Impl rule__SmallRepetitionStatement__Group__6 )
			// InternalDsl.g:3468:2: rule__SmallRepetitionStatement__Group__5__Impl rule__SmallRepetitionStatement__Group__6
			{
			pushFollow(FOLLOW_38);
			rule__SmallRepetitionStatement__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__5"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__5__Impl"
	// InternalDsl.g:3475:1: rule__SmallRepetitionStatement__Group__5__Impl : ( 'do' ) ;
	public final void rule__SmallRepetitionStatement__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3479:2: ( ( 'do' ) )
			// InternalDsl.g:3480:2: ( 'do' )
			{
			// InternalDsl.g:3480:2: ( 'do' )
			// InternalDsl.g:3481:2: 'do'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getDoKeyword_5()); 
			match(input,29,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getDoKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__5__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__6"
	// InternalDsl.g:3490:1: rule__SmallRepetitionStatement__Group__6 : rule__SmallRepetitionStatement__Group__6__Impl rule__SmallRepetitionStatement__Group__7 ;
	public final void rule__SmallRepetitionStatement__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3494:2: ( rule__SmallRepetitionStatement__Group__6__Impl rule__SmallRepetitionStatement__Group__7 )
			// InternalDsl.g:3495:2: rule__SmallRepetitionStatement__Group__6__Impl rule__SmallRepetitionStatement__Group__7
			{
			pushFollow(FOLLOW_29);
			rule__SmallRepetitionStatement__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__6"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__6__Impl"
	// InternalDsl.g:3502:1: rule__SmallRepetitionStatement__Group__6__Impl : ( 'inv:' ) ;
	public final void rule__SmallRepetitionStatement__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3506:2: ( ( 'inv:' ) )
			// InternalDsl.g:3507:2: ( 'inv:' )
			{
			// InternalDsl.g:3507:2: ( 'inv:' )
			// InternalDsl.g:3508:2: 'inv:'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getInvKeyword_6()); 
			match(input,35,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getInvKeyword_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__6__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__7"
	// InternalDsl.g:3517:1: rule__SmallRepetitionStatement__Group__7 : rule__SmallRepetitionStatement__Group__7__Impl rule__SmallRepetitionStatement__Group__8 ;
	public final void rule__SmallRepetitionStatement__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3521:2: ( rule__SmallRepetitionStatement__Group__7__Impl rule__SmallRepetitionStatement__Group__8 )
			// InternalDsl.g:3522:2: rule__SmallRepetitionStatement__Group__7__Impl rule__SmallRepetitionStatement__Group__8
			{
			pushFollow(FOLLOW_4);
			rule__SmallRepetitionStatement__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__7"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__7__Impl"
	// InternalDsl.g:3529:1: rule__SmallRepetitionStatement__Group__7__Impl : ( '[' ) ;
	public final void rule__SmallRepetitionStatement__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3533:2: ( ( '[' ) )
			// InternalDsl.g:3534:2: ( '[' )
			{
			// InternalDsl.g:3534:2: ( '[' )
			// InternalDsl.g:3535:2: '['
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_7()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__7__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__8"
	// InternalDsl.g:3544:1: rule__SmallRepetitionStatement__Group__8 : rule__SmallRepetitionStatement__Group__8__Impl rule__SmallRepetitionStatement__Group__9 ;
	public final void rule__SmallRepetitionStatement__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3548:2: ( rule__SmallRepetitionStatement__Group__8__Impl rule__SmallRepetitionStatement__Group__9 )
			// InternalDsl.g:3549:2: rule__SmallRepetitionStatement__Group__8__Impl rule__SmallRepetitionStatement__Group__9
			{
			pushFollow(FOLLOW_17);
			rule__SmallRepetitionStatement__Group__8__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__9();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__8"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__8__Impl"
	// InternalDsl.g:3556:1: rule__SmallRepetitionStatement__Group__8__Impl : ( ( rule__SmallRepetitionStatement__InvariantAssignment_8 ) ) ;
	public final void rule__SmallRepetitionStatement__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3560:2: ( ( ( rule__SmallRepetitionStatement__InvariantAssignment_8 ) ) )
			// InternalDsl.g:3561:2: ( ( rule__SmallRepetitionStatement__InvariantAssignment_8 ) )
			{
			// InternalDsl.g:3561:2: ( ( rule__SmallRepetitionStatement__InvariantAssignment_8 ) )
			// InternalDsl.g:3562:2: ( rule__SmallRepetitionStatement__InvariantAssignment_8 )
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getInvariantAssignment_8()); 
			// InternalDsl.g:3563:2: ( rule__SmallRepetitionStatement__InvariantAssignment_8 )
			// InternalDsl.g:3563:3: rule__SmallRepetitionStatement__InvariantAssignment_8
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__InvariantAssignment_8();
			state._fsp--;

			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getInvariantAssignment_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__8__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__9"
	// InternalDsl.g:3571:1: rule__SmallRepetitionStatement__Group__9 : rule__SmallRepetitionStatement__Group__9__Impl rule__SmallRepetitionStatement__Group__10 ;
	public final void rule__SmallRepetitionStatement__Group__9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3575:2: ( rule__SmallRepetitionStatement__Group__9__Impl rule__SmallRepetitionStatement__Group__10 )
			// InternalDsl.g:3576:2: rule__SmallRepetitionStatement__Group__9__Impl rule__SmallRepetitionStatement__Group__10
			{
			pushFollow(FOLLOW_39);
			rule__SmallRepetitionStatement__Group__9__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__10();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__9"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__9__Impl"
	// InternalDsl.g:3583:1: rule__SmallRepetitionStatement__Group__9__Impl : ( ']' ) ;
	public final void rule__SmallRepetitionStatement__Group__9__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3587:2: ( ( ']' ) )
			// InternalDsl.g:3588:2: ( ']' )
			{
			// InternalDsl.g:3588:2: ( ']' )
			// InternalDsl.g:3589:2: ']'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_9()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_9()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__9__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__10"
	// InternalDsl.g:3598:1: rule__SmallRepetitionStatement__Group__10 : rule__SmallRepetitionStatement__Group__10__Impl rule__SmallRepetitionStatement__Group__11 ;
	public final void rule__SmallRepetitionStatement__Group__10() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3602:2: ( rule__SmallRepetitionStatement__Group__10__Impl rule__SmallRepetitionStatement__Group__11 )
			// InternalDsl.g:3603:2: rule__SmallRepetitionStatement__Group__10__Impl rule__SmallRepetitionStatement__Group__11
			{
			pushFollow(FOLLOW_29);
			rule__SmallRepetitionStatement__Group__10__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__11();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__10"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__10__Impl"
	// InternalDsl.g:3610:1: rule__SmallRepetitionStatement__Group__10__Impl : ( 'var:' ) ;
	public final void rule__SmallRepetitionStatement__Group__10__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3614:2: ( ( 'var:' ) )
			// InternalDsl.g:3615:2: ( 'var:' )
			{
			// InternalDsl.g:3615:2: ( 'var:' )
			// InternalDsl.g:3616:2: 'var:'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getVarKeyword_10()); 
			match(input,43,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getVarKeyword_10()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__10__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__11"
	// InternalDsl.g:3625:1: rule__SmallRepetitionStatement__Group__11 : rule__SmallRepetitionStatement__Group__11__Impl rule__SmallRepetitionStatement__Group__12 ;
	public final void rule__SmallRepetitionStatement__Group__11() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3629:2: ( rule__SmallRepetitionStatement__Group__11__Impl rule__SmallRepetitionStatement__Group__12 )
			// InternalDsl.g:3630:2: rule__SmallRepetitionStatement__Group__11__Impl rule__SmallRepetitionStatement__Group__12
			{
			pushFollow(FOLLOW_4);
			rule__SmallRepetitionStatement__Group__11__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__12();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__11"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__11__Impl"
	// InternalDsl.g:3637:1: rule__SmallRepetitionStatement__Group__11__Impl : ( '[' ) ;
	public final void rule__SmallRepetitionStatement__Group__11__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3641:2: ( ( '[' ) )
			// InternalDsl.g:3642:2: ( '[' )
			{
			// InternalDsl.g:3642:2: ( '[' )
			// InternalDsl.g:3643:2: '['
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_11()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_11()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__11__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__12"
	// InternalDsl.g:3652:1: rule__SmallRepetitionStatement__Group__12 : rule__SmallRepetitionStatement__Group__12__Impl rule__SmallRepetitionStatement__Group__13 ;
	public final void rule__SmallRepetitionStatement__Group__12() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3656:2: ( rule__SmallRepetitionStatement__Group__12__Impl rule__SmallRepetitionStatement__Group__13 )
			// InternalDsl.g:3657:2: rule__SmallRepetitionStatement__Group__12__Impl rule__SmallRepetitionStatement__Group__13
			{
			pushFollow(FOLLOW_17);
			rule__SmallRepetitionStatement__Group__12__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__13();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__12"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__12__Impl"
	// InternalDsl.g:3664:1: rule__SmallRepetitionStatement__Group__12__Impl : ( ( rule__SmallRepetitionStatement__VariantAssignment_12 ) ) ;
	public final void rule__SmallRepetitionStatement__Group__12__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3668:2: ( ( ( rule__SmallRepetitionStatement__VariantAssignment_12 ) ) )
			// InternalDsl.g:3669:2: ( ( rule__SmallRepetitionStatement__VariantAssignment_12 ) )
			{
			// InternalDsl.g:3669:2: ( ( rule__SmallRepetitionStatement__VariantAssignment_12 ) )
			// InternalDsl.g:3670:2: ( rule__SmallRepetitionStatement__VariantAssignment_12 )
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getVariantAssignment_12()); 
			// InternalDsl.g:3671:2: ( rule__SmallRepetitionStatement__VariantAssignment_12 )
			// InternalDsl.g:3671:3: rule__SmallRepetitionStatement__VariantAssignment_12
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__VariantAssignment_12();
			state._fsp--;

			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getVariantAssignment_12()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__12__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__13"
	// InternalDsl.g:3679:1: rule__SmallRepetitionStatement__Group__13 : rule__SmallRepetitionStatement__Group__13__Impl rule__SmallRepetitionStatement__Group__14 ;
	public final void rule__SmallRepetitionStatement__Group__13() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3683:2: ( rule__SmallRepetitionStatement__Group__13__Impl rule__SmallRepetitionStatement__Group__14 )
			// InternalDsl.g:3684:2: rule__SmallRepetitionStatement__Group__13__Impl rule__SmallRepetitionStatement__Group__14
			{
			pushFollow(FOLLOW_6);
			rule__SmallRepetitionStatement__Group__13__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__14();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__13"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__13__Impl"
	// InternalDsl.g:3691:1: rule__SmallRepetitionStatement__Group__13__Impl : ( ']' ) ;
	public final void rule__SmallRepetitionStatement__Group__13__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3695:2: ( ( ']' ) )
			// InternalDsl.g:3696:2: ( ']' )
			{
			// InternalDsl.g:3696:2: ( ']' )
			// InternalDsl.g:3697:2: ']'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_13()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_13()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__13__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__14"
	// InternalDsl.g:3706:1: rule__SmallRepetitionStatement__Group__14 : rule__SmallRepetitionStatement__Group__14__Impl rule__SmallRepetitionStatement__Group__15 ;
	public final void rule__SmallRepetitionStatement__Group__14() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3710:2: ( rule__SmallRepetitionStatement__Group__14__Impl rule__SmallRepetitionStatement__Group__15 )
			// InternalDsl.g:3711:2: rule__SmallRepetitionStatement__Group__14__Impl rule__SmallRepetitionStatement__Group__15
			{
			pushFollow(FOLLOW_8);
			rule__SmallRepetitionStatement__Group__14__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__15();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__14"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__14__Impl"
	// InternalDsl.g:3718:1: rule__SmallRepetitionStatement__Group__14__Impl : ( '{' ) ;
	public final void rule__SmallRepetitionStatement__Group__14__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3722:2: ( ( '{' ) )
			// InternalDsl.g:3723:2: ( '{' )
			{
			// InternalDsl.g:3723:2: ( '{' )
			// InternalDsl.g:3724:2: '{'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLeftCurlyBracketKeyword_14()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getLeftCurlyBracketKeyword_14()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__14__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__15"
	// InternalDsl.g:3733:1: rule__SmallRepetitionStatement__Group__15 : rule__SmallRepetitionStatement__Group__15__Impl rule__SmallRepetitionStatement__Group__16 ;
	public final void rule__SmallRepetitionStatement__Group__15() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3737:2: ( rule__SmallRepetitionStatement__Group__15__Impl rule__SmallRepetitionStatement__Group__16 )
			// InternalDsl.g:3738:2: rule__SmallRepetitionStatement__Group__15__Impl rule__SmallRepetitionStatement__Group__16
			{
			pushFollow(FOLLOW_7);
			rule__SmallRepetitionStatement__Group__15__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__16();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__15"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__15__Impl"
	// InternalDsl.g:3745:1: rule__SmallRepetitionStatement__Group__15__Impl : ( ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 ) ) ;
	public final void rule__SmallRepetitionStatement__Group__15__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3749:2: ( ( ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 ) ) )
			// InternalDsl.g:3750:2: ( ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 ) )
			{
			// InternalDsl.g:3750:2: ( ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 ) )
			// InternalDsl.g:3751:2: ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 )
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLoopStatementAssignment_15()); 
			// InternalDsl.g:3752:2: ( rule__SmallRepetitionStatement__LoopStatementAssignment_15 )
			// InternalDsl.g:3752:3: rule__SmallRepetitionStatement__LoopStatementAssignment_15
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__LoopStatementAssignment_15();
			state._fsp--;

			}

			 after(grammarAccess.getSmallRepetitionStatementAccess().getLoopStatementAssignment_15()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__15__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__16"
	// InternalDsl.g:3760:1: rule__SmallRepetitionStatement__Group__16 : rule__SmallRepetitionStatement__Group__16__Impl rule__SmallRepetitionStatement__Group__17 ;
	public final void rule__SmallRepetitionStatement__Group__16() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3764:2: ( rule__SmallRepetitionStatement__Group__16__Impl rule__SmallRepetitionStatement__Group__17 )
			// InternalDsl.g:3765:2: rule__SmallRepetitionStatement__Group__16__Impl rule__SmallRepetitionStatement__Group__17
			{
			pushFollow(FOLLOW_40);
			rule__SmallRepetitionStatement__Group__16__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__17();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__16"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__16__Impl"
	// InternalDsl.g:3772:1: rule__SmallRepetitionStatement__Group__16__Impl : ( '}' ) ;
	public final void rule__SmallRepetitionStatement__Group__16__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3776:2: ( ( '}' ) )
			// InternalDsl.g:3777:2: ( '}' )
			{
			// InternalDsl.g:3777:2: ( '}' )
			// InternalDsl.g:3778:2: '}'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getRightCurlyBracketKeyword_16()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getRightCurlyBracketKeyword_16()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__16__Impl"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__17"
	// InternalDsl.g:3787:1: rule__SmallRepetitionStatement__Group__17 : rule__SmallRepetitionStatement__Group__17__Impl ;
	public final void rule__SmallRepetitionStatement__Group__17() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3791:2: ( rule__SmallRepetitionStatement__Group__17__Impl )
			// InternalDsl.g:3792:2: rule__SmallRepetitionStatement__Group__17__Impl
			{
			pushFollow(FOLLOW_2);
			rule__SmallRepetitionStatement__Group__17__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__17"



	// $ANTLR start "rule__SmallRepetitionStatement__Group__17__Impl"
	// InternalDsl.g:3798:1: rule__SmallRepetitionStatement__Group__17__Impl : ( 'od' ) ;
	public final void rule__SmallRepetitionStatement__Group__17__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3802:2: ( ( 'od' ) )
			// InternalDsl.g:3803:2: ( 'od' )
			{
			// InternalDsl.g:3803:2: ( 'od' )
			// InternalDsl.g:3804:2: 'od'
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getOdKeyword_17()); 
			match(input,37,FOLLOW_2); 
			 after(grammarAccess.getSmallRepetitionStatementAccess().getOdKeyword_17()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__Group__17__Impl"



	// $ANTLR start "rule__Variant__Group__0"
	// InternalDsl.g:3814:1: rule__Variant__Group__0 : rule__Variant__Group__0__Impl rule__Variant__Group__1 ;
	public final void rule__Variant__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3818:2: ( rule__Variant__Group__0__Impl rule__Variant__Group__1 )
			// InternalDsl.g:3819:2: rule__Variant__Group__0__Impl rule__Variant__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__Variant__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Variant__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Variant__Group__0"



	// $ANTLR start "rule__Variant__Group__0__Impl"
	// InternalDsl.g:3826:1: rule__Variant__Group__0__Impl : ( () ) ;
	public final void rule__Variant__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3830:2: ( ( () ) )
			// InternalDsl.g:3831:2: ( () )
			{
			// InternalDsl.g:3831:2: ( () )
			// InternalDsl.g:3832:2: ()
			{
			 before(grammarAccess.getVariantAccess().getVariantAction_0()); 
			// InternalDsl.g:3833:2: ()
			// InternalDsl.g:3833:3: 
			{
			}

			 after(grammarAccess.getVariantAccess().getVariantAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Variant__Group__0__Impl"



	// $ANTLR start "rule__Variant__Group__1"
	// InternalDsl.g:3841:1: rule__Variant__Group__1 : rule__Variant__Group__1__Impl ;
	public final void rule__Variant__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3845:2: ( rule__Variant__Group__1__Impl )
			// InternalDsl.g:3846:2: rule__Variant__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Variant__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Variant__Group__1"



	// $ANTLR start "rule__Variant__Group__1__Impl"
	// InternalDsl.g:3852:1: rule__Variant__Group__1__Impl : ( ( rule__Variant__NameAssignment_1 ) ) ;
	public final void rule__Variant__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3856:2: ( ( ( rule__Variant__NameAssignment_1 ) ) )
			// InternalDsl.g:3857:2: ( ( rule__Variant__NameAssignment_1 ) )
			{
			// InternalDsl.g:3857:2: ( ( rule__Variant__NameAssignment_1 ) )
			// InternalDsl.g:3858:2: ( rule__Variant__NameAssignment_1 )
			{
			 before(grammarAccess.getVariantAccess().getNameAssignment_1()); 
			// InternalDsl.g:3859:2: ( rule__Variant__NameAssignment_1 )
			// InternalDsl.g:3859:3: rule__Variant__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__Variant__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getVariantAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Variant__Group__1__Impl"



	// $ANTLR start "rule__JavaVariables__Group__0"
	// InternalDsl.g:3868:1: rule__JavaVariables__Group__0 : rule__JavaVariables__Group__0__Impl rule__JavaVariables__Group__1 ;
	public final void rule__JavaVariables__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3872:2: ( rule__JavaVariables__Group__0__Impl rule__JavaVariables__Group__1 )
			// InternalDsl.g:3873:2: rule__JavaVariables__Group__0__Impl rule__JavaVariables__Group__1
			{
			pushFollow(FOLLOW_41);
			rule__JavaVariables__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__0"



	// $ANTLR start "rule__JavaVariables__Group__0__Impl"
	// InternalDsl.g:3880:1: rule__JavaVariables__Group__0__Impl : ( () ) ;
	public final void rule__JavaVariables__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3884:2: ( ( () ) )
			// InternalDsl.g:3885:2: ( () )
			{
			// InternalDsl.g:3885:2: ( () )
			// InternalDsl.g:3886:2: ()
			{
			 before(grammarAccess.getJavaVariablesAccess().getJavaVariablesAction_0()); 
			// InternalDsl.g:3887:2: ()
			// InternalDsl.g:3887:3: 
			{
			}

			 after(grammarAccess.getJavaVariablesAccess().getJavaVariablesAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__0__Impl"



	// $ANTLR start "rule__JavaVariables__Group__1"
	// InternalDsl.g:3895:1: rule__JavaVariables__Group__1 : rule__JavaVariables__Group__1__Impl rule__JavaVariables__Group__2 ;
	public final void rule__JavaVariables__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3899:2: ( rule__JavaVariables__Group__1__Impl rule__JavaVariables__Group__2 )
			// InternalDsl.g:3900:2: rule__JavaVariables__Group__1__Impl rule__JavaVariables__Group__2
			{
			pushFollow(FOLLOW_42);
			rule__JavaVariables__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__1"



	// $ANTLR start "rule__JavaVariables__Group__1__Impl"
	// InternalDsl.g:3907:1: rule__JavaVariables__Group__1__Impl : ( 'JavaVariables' ) ;
	public final void rule__JavaVariables__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3911:2: ( ( 'JavaVariables' ) )
			// InternalDsl.g:3912:2: ( 'JavaVariables' )
			{
			// InternalDsl.g:3912:2: ( 'JavaVariables' )
			// InternalDsl.g:3913:2: 'JavaVariables'
			{
			 before(grammarAccess.getJavaVariablesAccess().getJavaVariablesKeyword_1()); 
			match(input,24,FOLLOW_2); 
			 after(grammarAccess.getJavaVariablesAccess().getJavaVariablesKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__1__Impl"



	// $ANTLR start "rule__JavaVariables__Group__2"
	// InternalDsl.g:3922:1: rule__JavaVariables__Group__2 : rule__JavaVariables__Group__2__Impl ;
	public final void rule__JavaVariables__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3926:2: ( rule__JavaVariables__Group__2__Impl )
			// InternalDsl.g:3927:2: rule__JavaVariables__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__2"



	// $ANTLR start "rule__JavaVariables__Group__2__Impl"
	// InternalDsl.g:3933:1: rule__JavaVariables__Group__2__Impl : ( ( rule__JavaVariables__Group_2__0 )? ) ;
	public final void rule__JavaVariables__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3937:2: ( ( ( rule__JavaVariables__Group_2__0 )? ) )
			// InternalDsl.g:3938:2: ( ( rule__JavaVariables__Group_2__0 )? )
			{
			// InternalDsl.g:3938:2: ( ( rule__JavaVariables__Group_2__0 )? )
			// InternalDsl.g:3939:2: ( rule__JavaVariables__Group_2__0 )?
			{
			 before(grammarAccess.getJavaVariablesAccess().getGroup_2()); 
			// InternalDsl.g:3940:2: ( rule__JavaVariables__Group_2__0 )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==44) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// InternalDsl.g:3940:3: rule__JavaVariables__Group_2__0
					{
					pushFollow(FOLLOW_2);
					rule__JavaVariables__Group_2__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getJavaVariablesAccess().getGroup_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group__2__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2__0"
	// InternalDsl.g:3949:1: rule__JavaVariables__Group_2__0 : rule__JavaVariables__Group_2__0__Impl rule__JavaVariables__Group_2__1 ;
	public final void rule__JavaVariables__Group_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3953:2: ( rule__JavaVariables__Group_2__0__Impl rule__JavaVariables__Group_2__1 )
			// InternalDsl.g:3954:2: rule__JavaVariables__Group_2__0__Impl rule__JavaVariables__Group_2__1
			{
			pushFollow(FOLLOW_6);
			rule__JavaVariables__Group_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__0"



	// $ANTLR start "rule__JavaVariables__Group_2__0__Impl"
	// InternalDsl.g:3961:1: rule__JavaVariables__Group_2__0__Impl : ( 'variables' ) ;
	public final void rule__JavaVariables__Group_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3965:2: ( ( 'variables' ) )
			// InternalDsl.g:3966:2: ( 'variables' )
			{
			// InternalDsl.g:3966:2: ( 'variables' )
			// InternalDsl.g:3967:2: 'variables'
			{
			 before(grammarAccess.getJavaVariablesAccess().getVariablesKeyword_2_0()); 
			match(input,44,FOLLOW_2); 
			 after(grammarAccess.getJavaVariablesAccess().getVariablesKeyword_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__0__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2__1"
	// InternalDsl.g:3976:1: rule__JavaVariables__Group_2__1 : rule__JavaVariables__Group_2__1__Impl rule__JavaVariables__Group_2__2 ;
	public final void rule__JavaVariables__Group_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3980:2: ( rule__JavaVariables__Group_2__1__Impl rule__JavaVariables__Group_2__2 )
			// InternalDsl.g:3981:2: rule__JavaVariables__Group_2__1__Impl rule__JavaVariables__Group_2__2
			{
			pushFollow(FOLLOW_4);
			rule__JavaVariables__Group_2__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__1"



	// $ANTLR start "rule__JavaVariables__Group_2__1__Impl"
	// InternalDsl.g:3988:1: rule__JavaVariables__Group_2__1__Impl : ( '{' ) ;
	public final void rule__JavaVariables__Group_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3992:2: ( ( '{' ) )
			// InternalDsl.g:3993:2: ( '{' )
			{
			// InternalDsl.g:3993:2: ( '{' )
			// InternalDsl.g:3994:2: '{'
			{
			 before(grammarAccess.getJavaVariablesAccess().getLeftCurlyBracketKeyword_2_1()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getJavaVariablesAccess().getLeftCurlyBracketKeyword_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__1__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2__2"
	// InternalDsl.g:4003:1: rule__JavaVariables__Group_2__2 : rule__JavaVariables__Group_2__2__Impl rule__JavaVariables__Group_2__3 ;
	public final void rule__JavaVariables__Group_2__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4007:2: ( rule__JavaVariables__Group_2__2__Impl rule__JavaVariables__Group_2__3 )
			// InternalDsl.g:4008:2: rule__JavaVariables__Group_2__2__Impl rule__JavaVariables__Group_2__3
			{
			pushFollow(FOLLOW_43);
			rule__JavaVariables__Group_2__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__2"



	// $ANTLR start "rule__JavaVariables__Group_2__2__Impl"
	// InternalDsl.g:4015:1: rule__JavaVariables__Group_2__2__Impl : ( ( rule__JavaVariables__VariablesAssignment_2_2 ) ) ;
	public final void rule__JavaVariables__Group_2__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4019:2: ( ( ( rule__JavaVariables__VariablesAssignment_2_2 ) ) )
			// InternalDsl.g:4020:2: ( ( rule__JavaVariables__VariablesAssignment_2_2 ) )
			{
			// InternalDsl.g:4020:2: ( ( rule__JavaVariables__VariablesAssignment_2_2 ) )
			// InternalDsl.g:4021:2: ( rule__JavaVariables__VariablesAssignment_2_2 )
			{
			 before(grammarAccess.getJavaVariablesAccess().getVariablesAssignment_2_2()); 
			// InternalDsl.g:4022:2: ( rule__JavaVariables__VariablesAssignment_2_2 )
			// InternalDsl.g:4022:3: rule__JavaVariables__VariablesAssignment_2_2
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__VariablesAssignment_2_2();
			state._fsp--;

			}

			 after(grammarAccess.getJavaVariablesAccess().getVariablesAssignment_2_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__2__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2__3"
	// InternalDsl.g:4030:1: rule__JavaVariables__Group_2__3 : rule__JavaVariables__Group_2__3__Impl rule__JavaVariables__Group_2__4 ;
	public final void rule__JavaVariables__Group_2__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4034:2: ( rule__JavaVariables__Group_2__3__Impl rule__JavaVariables__Group_2__4 )
			// InternalDsl.g:4035:2: rule__JavaVariables__Group_2__3__Impl rule__JavaVariables__Group_2__4
			{
			pushFollow(FOLLOW_7);
			rule__JavaVariables__Group_2__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__3"



	// $ANTLR start "rule__JavaVariables__Group_2__3__Impl"
	// InternalDsl.g:4042:1: rule__JavaVariables__Group_2__3__Impl : ( ( rule__JavaVariables__Group_2_3__0 )* ) ;
	public final void rule__JavaVariables__Group_2__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4046:2: ( ( ( rule__JavaVariables__Group_2_3__0 )* ) )
			// InternalDsl.g:4047:2: ( ( rule__JavaVariables__Group_2_3__0 )* )
			{
			// InternalDsl.g:4047:2: ( ( rule__JavaVariables__Group_2_3__0 )* )
			// InternalDsl.g:4048:2: ( rule__JavaVariables__Group_2_3__0 )*
			{
			 before(grammarAccess.getJavaVariablesAccess().getGroup_2_3()); 
			// InternalDsl.g:4049:2: ( rule__JavaVariables__Group_2_3__0 )*
			loop28:
			while (true) {
				int alt28=2;
				int LA28_0 = input.LA(1);
				if ( (LA28_0==16) ) {
					alt28=1;
				}

				switch (alt28) {
				case 1 :
					// InternalDsl.g:4049:3: rule__JavaVariables__Group_2_3__0
					{
					pushFollow(FOLLOW_25);
					rule__JavaVariables__Group_2_3__0();
					state._fsp--;

					}
					break;

				default :
					break loop28;
				}
			}

			 after(grammarAccess.getJavaVariablesAccess().getGroup_2_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__3__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2__4"
	// InternalDsl.g:4057:1: rule__JavaVariables__Group_2__4 : rule__JavaVariables__Group_2__4__Impl ;
	public final void rule__JavaVariables__Group_2__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4061:2: ( rule__JavaVariables__Group_2__4__Impl )
			// InternalDsl.g:4062:2: rule__JavaVariables__Group_2__4__Impl
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2__4__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__4"



	// $ANTLR start "rule__JavaVariables__Group_2__4__Impl"
	// InternalDsl.g:4068:1: rule__JavaVariables__Group_2__4__Impl : ( '}' ) ;
	public final void rule__JavaVariables__Group_2__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4072:2: ( ( '}' ) )
			// InternalDsl.g:4073:2: ( '}' )
			{
			// InternalDsl.g:4073:2: ( '}' )
			// InternalDsl.g:4074:2: '}'
			{
			 before(grammarAccess.getJavaVariablesAccess().getRightCurlyBracketKeyword_2_4()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getJavaVariablesAccess().getRightCurlyBracketKeyword_2_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2__4__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2_3__0"
	// InternalDsl.g:4084:1: rule__JavaVariables__Group_2_3__0 : rule__JavaVariables__Group_2_3__0__Impl rule__JavaVariables__Group_2_3__1 ;
	public final void rule__JavaVariables__Group_2_3__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4088:2: ( rule__JavaVariables__Group_2_3__0__Impl rule__JavaVariables__Group_2_3__1 )
			// InternalDsl.g:4089:2: rule__JavaVariables__Group_2_3__0__Impl rule__JavaVariables__Group_2_3__1
			{
			pushFollow(FOLLOW_4);
			rule__JavaVariables__Group_2_3__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2_3__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2_3__0"



	// $ANTLR start "rule__JavaVariables__Group_2_3__0__Impl"
	// InternalDsl.g:4096:1: rule__JavaVariables__Group_2_3__0__Impl : ( ',' ) ;
	public final void rule__JavaVariables__Group_2_3__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4100:2: ( ( ',' ) )
			// InternalDsl.g:4101:2: ( ',' )
			{
			// InternalDsl.g:4101:2: ( ',' )
			// InternalDsl.g:4102:2: ','
			{
			 before(grammarAccess.getJavaVariablesAccess().getCommaKeyword_2_3_0()); 
			match(input,16,FOLLOW_2); 
			 after(grammarAccess.getJavaVariablesAccess().getCommaKeyword_2_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2_3__0__Impl"



	// $ANTLR start "rule__JavaVariables__Group_2_3__1"
	// InternalDsl.g:4111:1: rule__JavaVariables__Group_2_3__1 : rule__JavaVariables__Group_2_3__1__Impl ;
	public final void rule__JavaVariables__Group_2_3__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4115:2: ( rule__JavaVariables__Group_2_3__1__Impl )
			// InternalDsl.g:4116:2: rule__JavaVariables__Group_2_3__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__Group_2_3__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2_3__1"



	// $ANTLR start "rule__JavaVariables__Group_2_3__1__Impl"
	// InternalDsl.g:4122:1: rule__JavaVariables__Group_2_3__1__Impl : ( ( rule__JavaVariables__VariablesAssignment_2_3_1 ) ) ;
	public final void rule__JavaVariables__Group_2_3__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4126:2: ( ( ( rule__JavaVariables__VariablesAssignment_2_3_1 ) ) )
			// InternalDsl.g:4127:2: ( ( rule__JavaVariables__VariablesAssignment_2_3_1 ) )
			{
			// InternalDsl.g:4127:2: ( ( rule__JavaVariables__VariablesAssignment_2_3_1 ) )
			// InternalDsl.g:4128:2: ( rule__JavaVariables__VariablesAssignment_2_3_1 )
			{
			 before(grammarAccess.getJavaVariablesAccess().getVariablesAssignment_2_3_1()); 
			// InternalDsl.g:4129:2: ( rule__JavaVariables__VariablesAssignment_2_3_1 )
			// InternalDsl.g:4129:3: rule__JavaVariables__VariablesAssignment_2_3_1
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariables__VariablesAssignment_2_3_1();
			state._fsp--;

			}

			 after(grammarAccess.getJavaVariablesAccess().getVariablesAssignment_2_3_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__Group_2_3__1__Impl"



	// $ANTLR start "rule__JavaVariable__Group__0"
	// InternalDsl.g:4138:1: rule__JavaVariable__Group__0 : rule__JavaVariable__Group__0__Impl rule__JavaVariable__Group__1 ;
	public final void rule__JavaVariable__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4142:2: ( rule__JavaVariable__Group__0__Impl rule__JavaVariable__Group__1 )
			// InternalDsl.g:4143:2: rule__JavaVariable__Group__0__Impl rule__JavaVariable__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__JavaVariable__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__JavaVariable__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariable__Group__0"



	// $ANTLR start "rule__JavaVariable__Group__0__Impl"
	// InternalDsl.g:4150:1: rule__JavaVariable__Group__0__Impl : ( () ) ;
	public final void rule__JavaVariable__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4154:2: ( ( () ) )
			// InternalDsl.g:4155:2: ( () )
			{
			// InternalDsl.g:4155:2: ( () )
			// InternalDsl.g:4156:2: ()
			{
			 before(grammarAccess.getJavaVariableAccess().getJavaVariableAction_0()); 
			// InternalDsl.g:4157:2: ()
			// InternalDsl.g:4157:3: 
			{
			}

			 after(grammarAccess.getJavaVariableAccess().getJavaVariableAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariable__Group__0__Impl"



	// $ANTLR start "rule__JavaVariable__Group__1"
	// InternalDsl.g:4165:1: rule__JavaVariable__Group__1 : rule__JavaVariable__Group__1__Impl ;
	public final void rule__JavaVariable__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4169:2: ( rule__JavaVariable__Group__1__Impl )
			// InternalDsl.g:4170:2: rule__JavaVariable__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariable__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariable__Group__1"



	// $ANTLR start "rule__JavaVariable__Group__1__Impl"
	// InternalDsl.g:4176:1: rule__JavaVariable__Group__1__Impl : ( ( rule__JavaVariable__NameAssignment_1 ) ) ;
	public final void rule__JavaVariable__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4180:2: ( ( ( rule__JavaVariable__NameAssignment_1 ) ) )
			// InternalDsl.g:4181:2: ( ( rule__JavaVariable__NameAssignment_1 ) )
			{
			// InternalDsl.g:4181:2: ( ( rule__JavaVariable__NameAssignment_1 ) )
			// InternalDsl.g:4182:2: ( rule__JavaVariable__NameAssignment_1 )
			{
			 before(grammarAccess.getJavaVariableAccess().getNameAssignment_1()); 
			// InternalDsl.g:4183:2: ( rule__JavaVariable__NameAssignment_1 )
			// InternalDsl.g:4183:3: rule__JavaVariable__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__JavaVariable__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getJavaVariableAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariable__Group__1__Impl"



	// $ANTLR start "rule__GlobalConditions__Group__0"
	// InternalDsl.g:4192:1: rule__GlobalConditions__Group__0 : rule__GlobalConditions__Group__0__Impl rule__GlobalConditions__Group__1 ;
	public final void rule__GlobalConditions__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4196:2: ( rule__GlobalConditions__Group__0__Impl rule__GlobalConditions__Group__1 )
			// InternalDsl.g:4197:2: rule__GlobalConditions__Group__0__Impl rule__GlobalConditions__Group__1
			{
			pushFollow(FOLLOW_44);
			rule__GlobalConditions__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__0"



	// $ANTLR start "rule__GlobalConditions__Group__0__Impl"
	// InternalDsl.g:4204:1: rule__GlobalConditions__Group__0__Impl : ( () ) ;
	public final void rule__GlobalConditions__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4208:2: ( ( () ) )
			// InternalDsl.g:4209:2: ( () )
			{
			// InternalDsl.g:4209:2: ( () )
			// InternalDsl.g:4210:2: ()
			{
			 before(grammarAccess.getGlobalConditionsAccess().getGlobalConditionsAction_0()); 
			// InternalDsl.g:4211:2: ()
			// InternalDsl.g:4211:3: 
			{
			}

			 after(grammarAccess.getGlobalConditionsAccess().getGlobalConditionsAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__0__Impl"



	// $ANTLR start "rule__GlobalConditions__Group__1"
	// InternalDsl.g:4219:1: rule__GlobalConditions__Group__1 : rule__GlobalConditions__Group__1__Impl rule__GlobalConditions__Group__2 ;
	public final void rule__GlobalConditions__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4223:2: ( rule__GlobalConditions__Group__1__Impl rule__GlobalConditions__Group__2 )
			// InternalDsl.g:4224:2: rule__GlobalConditions__Group__1__Impl rule__GlobalConditions__Group__2
			{
			pushFollow(FOLLOW_45);
			rule__GlobalConditions__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__1"



	// $ANTLR start "rule__GlobalConditions__Group__1__Impl"
	// InternalDsl.g:4231:1: rule__GlobalConditions__Group__1__Impl : ( 'GlobalConditions' ) ;
	public final void rule__GlobalConditions__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4235:2: ( ( 'GlobalConditions' ) )
			// InternalDsl.g:4236:2: ( 'GlobalConditions' )
			{
			// InternalDsl.g:4236:2: ( 'GlobalConditions' )
			// InternalDsl.g:4237:2: 'GlobalConditions'
			{
			 before(grammarAccess.getGlobalConditionsAccess().getGlobalConditionsKeyword_1()); 
			match(input,23,FOLLOW_2); 
			 after(grammarAccess.getGlobalConditionsAccess().getGlobalConditionsKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__1__Impl"



	// $ANTLR start "rule__GlobalConditions__Group__2"
	// InternalDsl.g:4246:1: rule__GlobalConditions__Group__2 : rule__GlobalConditions__Group__2__Impl ;
	public final void rule__GlobalConditions__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4250:2: ( rule__GlobalConditions__Group__2__Impl )
			// InternalDsl.g:4251:2: rule__GlobalConditions__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__2"



	// $ANTLR start "rule__GlobalConditions__Group__2__Impl"
	// InternalDsl.g:4257:1: rule__GlobalConditions__Group__2__Impl : ( ( rule__GlobalConditions__Group_2__0 )? ) ;
	public final void rule__GlobalConditions__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4261:2: ( ( ( rule__GlobalConditions__Group_2__0 )? ) )
			// InternalDsl.g:4262:2: ( ( rule__GlobalConditions__Group_2__0 )? )
			{
			// InternalDsl.g:4262:2: ( ( rule__GlobalConditions__Group_2__0 )? )
			// InternalDsl.g:4263:2: ( rule__GlobalConditions__Group_2__0 )?
			{
			 before(grammarAccess.getGlobalConditionsAccess().getGroup_2()); 
			// InternalDsl.g:4264:2: ( rule__GlobalConditions__Group_2__0 )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==28) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// InternalDsl.g:4264:3: rule__GlobalConditions__Group_2__0
					{
					pushFollow(FOLLOW_2);
					rule__GlobalConditions__Group_2__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getGlobalConditionsAccess().getGroup_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group__2__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2__0"
	// InternalDsl.g:4273:1: rule__GlobalConditions__Group_2__0 : rule__GlobalConditions__Group_2__0__Impl rule__GlobalConditions__Group_2__1 ;
	public final void rule__GlobalConditions__Group_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4277:2: ( rule__GlobalConditions__Group_2__0__Impl rule__GlobalConditions__Group_2__1 )
			// InternalDsl.g:4278:2: rule__GlobalConditions__Group_2__0__Impl rule__GlobalConditions__Group_2__1
			{
			pushFollow(FOLLOW_6);
			rule__GlobalConditions__Group_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__0"



	// $ANTLR start "rule__GlobalConditions__Group_2__0__Impl"
	// InternalDsl.g:4285:1: rule__GlobalConditions__Group_2__0__Impl : ( 'conditions' ) ;
	public final void rule__GlobalConditions__Group_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4289:2: ( ( 'conditions' ) )
			// InternalDsl.g:4290:2: ( 'conditions' )
			{
			// InternalDsl.g:4290:2: ( 'conditions' )
			// InternalDsl.g:4291:2: 'conditions'
			{
			 before(grammarAccess.getGlobalConditionsAccess().getConditionsKeyword_2_0()); 
			match(input,28,FOLLOW_2); 
			 after(grammarAccess.getGlobalConditionsAccess().getConditionsKeyword_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__0__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2__1"
	// InternalDsl.g:4300:1: rule__GlobalConditions__Group_2__1 : rule__GlobalConditions__Group_2__1__Impl rule__GlobalConditions__Group_2__2 ;
	public final void rule__GlobalConditions__Group_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4304:2: ( rule__GlobalConditions__Group_2__1__Impl rule__GlobalConditions__Group_2__2 )
			// InternalDsl.g:4305:2: rule__GlobalConditions__Group_2__1__Impl rule__GlobalConditions__Group_2__2
			{
			pushFollow(FOLLOW_4);
			rule__GlobalConditions__Group_2__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__1"



	// $ANTLR start "rule__GlobalConditions__Group_2__1__Impl"
	// InternalDsl.g:4312:1: rule__GlobalConditions__Group_2__1__Impl : ( '{' ) ;
	public final void rule__GlobalConditions__Group_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4316:2: ( ( '{' ) )
			// InternalDsl.g:4317:2: ( '{' )
			{
			// InternalDsl.g:4317:2: ( '{' )
			// InternalDsl.g:4318:2: '{'
			{
			 before(grammarAccess.getGlobalConditionsAccess().getLeftCurlyBracketKeyword_2_1()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getGlobalConditionsAccess().getLeftCurlyBracketKeyword_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__1__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2__2"
	// InternalDsl.g:4327:1: rule__GlobalConditions__Group_2__2 : rule__GlobalConditions__Group_2__2__Impl rule__GlobalConditions__Group_2__3 ;
	public final void rule__GlobalConditions__Group_2__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4331:2: ( rule__GlobalConditions__Group_2__2__Impl rule__GlobalConditions__Group_2__3 )
			// InternalDsl.g:4332:2: rule__GlobalConditions__Group_2__2__Impl rule__GlobalConditions__Group_2__3
			{
			pushFollow(FOLLOW_43);
			rule__GlobalConditions__Group_2__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__2"



	// $ANTLR start "rule__GlobalConditions__Group_2__2__Impl"
	// InternalDsl.g:4339:1: rule__GlobalConditions__Group_2__2__Impl : ( ( rule__GlobalConditions__ConditionsAssignment_2_2 ) ) ;
	public final void rule__GlobalConditions__Group_2__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4343:2: ( ( ( rule__GlobalConditions__ConditionsAssignment_2_2 ) ) )
			// InternalDsl.g:4344:2: ( ( rule__GlobalConditions__ConditionsAssignment_2_2 ) )
			{
			// InternalDsl.g:4344:2: ( ( rule__GlobalConditions__ConditionsAssignment_2_2 ) )
			// InternalDsl.g:4345:2: ( rule__GlobalConditions__ConditionsAssignment_2_2 )
			{
			 before(grammarAccess.getGlobalConditionsAccess().getConditionsAssignment_2_2()); 
			// InternalDsl.g:4346:2: ( rule__GlobalConditions__ConditionsAssignment_2_2 )
			// InternalDsl.g:4346:3: rule__GlobalConditions__ConditionsAssignment_2_2
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__ConditionsAssignment_2_2();
			state._fsp--;

			}

			 after(grammarAccess.getGlobalConditionsAccess().getConditionsAssignment_2_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__2__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2__3"
	// InternalDsl.g:4354:1: rule__GlobalConditions__Group_2__3 : rule__GlobalConditions__Group_2__3__Impl rule__GlobalConditions__Group_2__4 ;
	public final void rule__GlobalConditions__Group_2__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4358:2: ( rule__GlobalConditions__Group_2__3__Impl rule__GlobalConditions__Group_2__4 )
			// InternalDsl.g:4359:2: rule__GlobalConditions__Group_2__3__Impl rule__GlobalConditions__Group_2__4
			{
			pushFollow(FOLLOW_7);
			rule__GlobalConditions__Group_2__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__3"



	// $ANTLR start "rule__GlobalConditions__Group_2__3__Impl"
	// InternalDsl.g:4366:1: rule__GlobalConditions__Group_2__3__Impl : ( ( rule__GlobalConditions__Group_2_3__0 )* ) ;
	public final void rule__GlobalConditions__Group_2__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4370:2: ( ( ( rule__GlobalConditions__Group_2_3__0 )* ) )
			// InternalDsl.g:4371:2: ( ( rule__GlobalConditions__Group_2_3__0 )* )
			{
			// InternalDsl.g:4371:2: ( ( rule__GlobalConditions__Group_2_3__0 )* )
			// InternalDsl.g:4372:2: ( rule__GlobalConditions__Group_2_3__0 )*
			{
			 before(grammarAccess.getGlobalConditionsAccess().getGroup_2_3()); 
			// InternalDsl.g:4373:2: ( rule__GlobalConditions__Group_2_3__0 )*
			loop30:
			while (true) {
				int alt30=2;
				int LA30_0 = input.LA(1);
				if ( (LA30_0==16) ) {
					alt30=1;
				}

				switch (alt30) {
				case 1 :
					// InternalDsl.g:4373:3: rule__GlobalConditions__Group_2_3__0
					{
					pushFollow(FOLLOW_25);
					rule__GlobalConditions__Group_2_3__0();
					state._fsp--;

					}
					break;

				default :
					break loop30;
				}
			}

			 after(grammarAccess.getGlobalConditionsAccess().getGroup_2_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__3__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2__4"
	// InternalDsl.g:4381:1: rule__GlobalConditions__Group_2__4 : rule__GlobalConditions__Group_2__4__Impl ;
	public final void rule__GlobalConditions__Group_2__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4385:2: ( rule__GlobalConditions__Group_2__4__Impl )
			// InternalDsl.g:4386:2: rule__GlobalConditions__Group_2__4__Impl
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2__4__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__4"



	// $ANTLR start "rule__GlobalConditions__Group_2__4__Impl"
	// InternalDsl.g:4392:1: rule__GlobalConditions__Group_2__4__Impl : ( '}' ) ;
	public final void rule__GlobalConditions__Group_2__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4396:2: ( ( '}' ) )
			// InternalDsl.g:4397:2: ( '}' )
			{
			// InternalDsl.g:4397:2: ( '}' )
			// InternalDsl.g:4398:2: '}'
			{
			 before(grammarAccess.getGlobalConditionsAccess().getRightCurlyBracketKeyword_2_4()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getGlobalConditionsAccess().getRightCurlyBracketKeyword_2_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2__4__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2_3__0"
	// InternalDsl.g:4408:1: rule__GlobalConditions__Group_2_3__0 : rule__GlobalConditions__Group_2_3__0__Impl rule__GlobalConditions__Group_2_3__1 ;
	public final void rule__GlobalConditions__Group_2_3__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4412:2: ( rule__GlobalConditions__Group_2_3__0__Impl rule__GlobalConditions__Group_2_3__1 )
			// InternalDsl.g:4413:2: rule__GlobalConditions__Group_2_3__0__Impl rule__GlobalConditions__Group_2_3__1
			{
			pushFollow(FOLLOW_4);
			rule__GlobalConditions__Group_2_3__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2_3__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2_3__0"



	// $ANTLR start "rule__GlobalConditions__Group_2_3__0__Impl"
	// InternalDsl.g:4420:1: rule__GlobalConditions__Group_2_3__0__Impl : ( ',' ) ;
	public final void rule__GlobalConditions__Group_2_3__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4424:2: ( ( ',' ) )
			// InternalDsl.g:4425:2: ( ',' )
			{
			// InternalDsl.g:4425:2: ( ',' )
			// InternalDsl.g:4426:2: ','
			{
			 before(grammarAccess.getGlobalConditionsAccess().getCommaKeyword_2_3_0()); 
			match(input,16,FOLLOW_2); 
			 after(grammarAccess.getGlobalConditionsAccess().getCommaKeyword_2_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2_3__0__Impl"



	// $ANTLR start "rule__GlobalConditions__Group_2_3__1"
	// InternalDsl.g:4435:1: rule__GlobalConditions__Group_2_3__1 : rule__GlobalConditions__Group_2_3__1__Impl ;
	public final void rule__GlobalConditions__Group_2_3__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4439:2: ( rule__GlobalConditions__Group_2_3__1__Impl )
			// InternalDsl.g:4440:2: rule__GlobalConditions__Group_2_3__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__Group_2_3__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2_3__1"



	// $ANTLR start "rule__GlobalConditions__Group_2_3__1__Impl"
	// InternalDsl.g:4446:1: rule__GlobalConditions__Group_2_3__1__Impl : ( ( rule__GlobalConditions__ConditionsAssignment_2_3_1 ) ) ;
	public final void rule__GlobalConditions__Group_2_3__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4450:2: ( ( ( rule__GlobalConditions__ConditionsAssignment_2_3_1 ) ) )
			// InternalDsl.g:4451:2: ( ( rule__GlobalConditions__ConditionsAssignment_2_3_1 ) )
			{
			// InternalDsl.g:4451:2: ( ( rule__GlobalConditions__ConditionsAssignment_2_3_1 ) )
			// InternalDsl.g:4452:2: ( rule__GlobalConditions__ConditionsAssignment_2_3_1 )
			{
			 before(grammarAccess.getGlobalConditionsAccess().getConditionsAssignment_2_3_1()); 
			// InternalDsl.g:4453:2: ( rule__GlobalConditions__ConditionsAssignment_2_3_1 )
			// InternalDsl.g:4453:3: rule__GlobalConditions__ConditionsAssignment_2_3_1
			{
			pushFollow(FOLLOW_2);
			rule__GlobalConditions__ConditionsAssignment_2_3_1();
			state._fsp--;

			}

			 after(grammarAccess.getGlobalConditionsAccess().getConditionsAssignment_2_3_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__Group_2_3__1__Impl"



	// $ANTLR start "rule__Renaming__Group__0"
	// InternalDsl.g:4462:1: rule__Renaming__Group__0 : rule__Renaming__Group__0__Impl rule__Renaming__Group__1 ;
	public final void rule__Renaming__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4466:2: ( rule__Renaming__Group__0__Impl rule__Renaming__Group__1 )
			// InternalDsl.g:4467:2: rule__Renaming__Group__0__Impl rule__Renaming__Group__1
			{
			pushFollow(FOLLOW_46);
			rule__Renaming__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__0"



	// $ANTLR start "rule__Renaming__Group__0__Impl"
	// InternalDsl.g:4474:1: rule__Renaming__Group__0__Impl : ( () ) ;
	public final void rule__Renaming__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4478:2: ( ( () ) )
			// InternalDsl.g:4479:2: ( () )
			{
			// InternalDsl.g:4479:2: ( () )
			// InternalDsl.g:4480:2: ()
			{
			 before(grammarAccess.getRenamingAccess().getRenamingAction_0()); 
			// InternalDsl.g:4481:2: ()
			// InternalDsl.g:4481:3: 
			{
			}

			 after(grammarAccess.getRenamingAccess().getRenamingAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__0__Impl"



	// $ANTLR start "rule__Renaming__Group__1"
	// InternalDsl.g:4489:1: rule__Renaming__Group__1 : rule__Renaming__Group__1__Impl rule__Renaming__Group__2 ;
	public final void rule__Renaming__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4493:2: ( rule__Renaming__Group__1__Impl rule__Renaming__Group__2 )
			// InternalDsl.g:4494:2: rule__Renaming__Group__1__Impl rule__Renaming__Group__2
			{
			pushFollow(FOLLOW_47);
			rule__Renaming__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__1"



	// $ANTLR start "rule__Renaming__Group__1__Impl"
	// InternalDsl.g:4501:1: rule__Renaming__Group__1__Impl : ( 'Renaming' ) ;
	public final void rule__Renaming__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4505:2: ( ( 'Renaming' ) )
			// InternalDsl.g:4506:2: ( 'Renaming' )
			{
			// InternalDsl.g:4506:2: ( 'Renaming' )
			// InternalDsl.g:4507:2: 'Renaming'
			{
			 before(grammarAccess.getRenamingAccess().getRenamingKeyword_1()); 
			match(input,25,FOLLOW_2); 
			 after(grammarAccess.getRenamingAccess().getRenamingKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__1__Impl"



	// $ANTLR start "rule__Renaming__Group__2"
	// InternalDsl.g:4516:1: rule__Renaming__Group__2 : rule__Renaming__Group__2__Impl ;
	public final void rule__Renaming__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4520:2: ( rule__Renaming__Group__2__Impl )
			// InternalDsl.g:4521:2: rule__Renaming__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Renaming__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__2"



	// $ANTLR start "rule__Renaming__Group__2__Impl"
	// InternalDsl.g:4527:1: rule__Renaming__Group__2__Impl : ( ( rule__Renaming__Group_2__0 )? ) ;
	public final void rule__Renaming__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4531:2: ( ( ( rule__Renaming__Group_2__0 )? ) )
			// InternalDsl.g:4532:2: ( ( rule__Renaming__Group_2__0 )? )
			{
			// InternalDsl.g:4532:2: ( ( rule__Renaming__Group_2__0 )? )
			// InternalDsl.g:4533:2: ( rule__Renaming__Group_2__0 )?
			{
			 before(grammarAccess.getRenamingAccess().getGroup_2()); 
			// InternalDsl.g:4534:2: ( rule__Renaming__Group_2__0 )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==40) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// InternalDsl.g:4534:3: rule__Renaming__Group_2__0
					{
					pushFollow(FOLLOW_2);
					rule__Renaming__Group_2__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getRenamingAccess().getGroup_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group__2__Impl"



	// $ANTLR start "rule__Renaming__Group_2__0"
	// InternalDsl.g:4543:1: rule__Renaming__Group_2__0 : rule__Renaming__Group_2__0__Impl rule__Renaming__Group_2__1 ;
	public final void rule__Renaming__Group_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4547:2: ( rule__Renaming__Group_2__0__Impl rule__Renaming__Group_2__1 )
			// InternalDsl.g:4548:2: rule__Renaming__Group_2__0__Impl rule__Renaming__Group_2__1
			{
			pushFollow(FOLLOW_6);
			rule__Renaming__Group_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__0"



	// $ANTLR start "rule__Renaming__Group_2__0__Impl"
	// InternalDsl.g:4555:1: rule__Renaming__Group_2__0__Impl : ( 'renames' ) ;
	public final void rule__Renaming__Group_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4559:2: ( ( 'renames' ) )
			// InternalDsl.g:4560:2: ( 'renames' )
			{
			// InternalDsl.g:4560:2: ( 'renames' )
			// InternalDsl.g:4561:2: 'renames'
			{
			 before(grammarAccess.getRenamingAccess().getRenamesKeyword_2_0()); 
			match(input,40,FOLLOW_2); 
			 after(grammarAccess.getRenamingAccess().getRenamesKeyword_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__0__Impl"



	// $ANTLR start "rule__Renaming__Group_2__1"
	// InternalDsl.g:4570:1: rule__Renaming__Group_2__1 : rule__Renaming__Group_2__1__Impl rule__Renaming__Group_2__2 ;
	public final void rule__Renaming__Group_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4574:2: ( rule__Renaming__Group_2__1__Impl rule__Renaming__Group_2__2 )
			// InternalDsl.g:4575:2: rule__Renaming__Group_2__1__Impl rule__Renaming__Group_2__2
			{
			pushFollow(FOLLOW_6);
			rule__Renaming__Group_2__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group_2__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__1"



	// $ANTLR start "rule__Renaming__Group_2__1__Impl"
	// InternalDsl.g:4582:1: rule__Renaming__Group_2__1__Impl : ( '{' ) ;
	public final void rule__Renaming__Group_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4586:2: ( ( '{' ) )
			// InternalDsl.g:4587:2: ( '{' )
			{
			// InternalDsl.g:4587:2: ( '{' )
			// InternalDsl.g:4588:2: '{'
			{
			 before(grammarAccess.getRenamingAccess().getLeftCurlyBracketKeyword_2_1()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getRenamingAccess().getLeftCurlyBracketKeyword_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__1__Impl"



	// $ANTLR start "rule__Renaming__Group_2__2"
	// InternalDsl.g:4597:1: rule__Renaming__Group_2__2 : rule__Renaming__Group_2__2__Impl rule__Renaming__Group_2__3 ;
	public final void rule__Renaming__Group_2__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4601:2: ( rule__Renaming__Group_2__2__Impl rule__Renaming__Group_2__3 )
			// InternalDsl.g:4602:2: rule__Renaming__Group_2__2__Impl rule__Renaming__Group_2__3
			{
			pushFollow(FOLLOW_48);
			rule__Renaming__Group_2__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group_2__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__2"



	// $ANTLR start "rule__Renaming__Group_2__2__Impl"
	// InternalDsl.g:4609:1: rule__Renaming__Group_2__2__Impl : ( ( rule__Renaming__RenameAssignment_2_2 ) ) ;
	public final void rule__Renaming__Group_2__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4613:2: ( ( ( rule__Renaming__RenameAssignment_2_2 ) ) )
			// InternalDsl.g:4614:2: ( ( rule__Renaming__RenameAssignment_2_2 ) )
			{
			// InternalDsl.g:4614:2: ( ( rule__Renaming__RenameAssignment_2_2 ) )
			// InternalDsl.g:4615:2: ( rule__Renaming__RenameAssignment_2_2 )
			{
			 before(grammarAccess.getRenamingAccess().getRenameAssignment_2_2()); 
			// InternalDsl.g:4616:2: ( rule__Renaming__RenameAssignment_2_2 )
			// InternalDsl.g:4616:3: rule__Renaming__RenameAssignment_2_2
			{
			pushFollow(FOLLOW_2);
			rule__Renaming__RenameAssignment_2_2();
			state._fsp--;

			}

			 after(grammarAccess.getRenamingAccess().getRenameAssignment_2_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__2__Impl"



	// $ANTLR start "rule__Renaming__Group_2__3"
	// InternalDsl.g:4624:1: rule__Renaming__Group_2__3 : rule__Renaming__Group_2__3__Impl rule__Renaming__Group_2__4 ;
	public final void rule__Renaming__Group_2__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4628:2: ( rule__Renaming__Group_2__3__Impl rule__Renaming__Group_2__4 )
			// InternalDsl.g:4629:2: rule__Renaming__Group_2__3__Impl rule__Renaming__Group_2__4
			{
			pushFollow(FOLLOW_7);
			rule__Renaming__Group_2__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Renaming__Group_2__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__3"



	// $ANTLR start "rule__Renaming__Group_2__3__Impl"
	// InternalDsl.g:4636:1: rule__Renaming__Group_2__3__Impl : ( ( rule__Renaming__RenameAssignment_2_3 )* ) ;
	public final void rule__Renaming__Group_2__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4640:2: ( ( ( rule__Renaming__RenameAssignment_2_3 )* ) )
			// InternalDsl.g:4641:2: ( ( rule__Renaming__RenameAssignment_2_3 )* )
			{
			// InternalDsl.g:4641:2: ( ( rule__Renaming__RenameAssignment_2_3 )* )
			// InternalDsl.g:4642:2: ( rule__Renaming__RenameAssignment_2_3 )*
			{
			 before(grammarAccess.getRenamingAccess().getRenameAssignment_2_3()); 
			// InternalDsl.g:4643:2: ( rule__Renaming__RenameAssignment_2_3 )*
			loop32:
			while (true) {
				int alt32=2;
				int LA32_0 = input.LA(1);
				if ( (LA32_0==46) ) {
					alt32=1;
				}

				switch (alt32) {
				case 1 :
					// InternalDsl.g:4643:3: rule__Renaming__RenameAssignment_2_3
					{
					pushFollow(FOLLOW_49);
					rule__Renaming__RenameAssignment_2_3();
					state._fsp--;

					}
					break;

				default :
					break loop32;
				}
			}

			 after(grammarAccess.getRenamingAccess().getRenameAssignment_2_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__3__Impl"



	// $ANTLR start "rule__Renaming__Group_2__4"
	// InternalDsl.g:4651:1: rule__Renaming__Group_2__4 : rule__Renaming__Group_2__4__Impl ;
	public final void rule__Renaming__Group_2__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4655:2: ( rule__Renaming__Group_2__4__Impl )
			// InternalDsl.g:4656:2: rule__Renaming__Group_2__4__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Renaming__Group_2__4__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__4"



	// $ANTLR start "rule__Renaming__Group_2__4__Impl"
	// InternalDsl.g:4662:1: rule__Renaming__Group_2__4__Impl : ( '}' ) ;
	public final void rule__Renaming__Group_2__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4666:2: ( ( '}' ) )
			// InternalDsl.g:4667:2: ( '}' )
			{
			// InternalDsl.g:4667:2: ( '}' )
			// InternalDsl.g:4668:2: '}'
			{
			 before(grammarAccess.getRenamingAccess().getRightCurlyBracketKeyword_2_4()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getRenamingAccess().getRightCurlyBracketKeyword_2_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__Group_2__4__Impl"



	// $ANTLR start "rule__Rename__Group__0"
	// InternalDsl.g:4678:1: rule__Rename__Group__0 : rule__Rename__Group__0__Impl rule__Rename__Group__1 ;
	public final void rule__Rename__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4682:2: ( rule__Rename__Group__0__Impl rule__Rename__Group__1 )
			// InternalDsl.g:4683:2: rule__Rename__Group__0__Impl rule__Rename__Group__1
			{
			pushFollow(FOLLOW_6);
			rule__Rename__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__0"



	// $ANTLR start "rule__Rename__Group__0__Impl"
	// InternalDsl.g:4690:1: rule__Rename__Group__0__Impl : ( () ) ;
	public final void rule__Rename__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4694:2: ( ( () ) )
			// InternalDsl.g:4695:2: ( () )
			{
			// InternalDsl.g:4695:2: ( () )
			// InternalDsl.g:4696:2: ()
			{
			 before(grammarAccess.getRenameAccess().getRenameAction_0()); 
			// InternalDsl.g:4697:2: ()
			// InternalDsl.g:4697:3: 
			{
			}

			 after(grammarAccess.getRenameAccess().getRenameAction_0()); 
			}

			}

		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__0__Impl"



	// $ANTLR start "rule__Rename__Group__1"
	// InternalDsl.g:4705:1: rule__Rename__Group__1 : rule__Rename__Group__1__Impl rule__Rename__Group__2 ;
	public final void rule__Rename__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4709:2: ( rule__Rename__Group__1__Impl rule__Rename__Group__2 )
			// InternalDsl.g:4710:2: rule__Rename__Group__1__Impl rule__Rename__Group__2
			{
			pushFollow(FOLLOW_50);
			rule__Rename__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__1"



	// $ANTLR start "rule__Rename__Group__1__Impl"
	// InternalDsl.g:4717:1: rule__Rename__Group__1__Impl : ( '{' ) ;
	public final void rule__Rename__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4721:2: ( ( '{' ) )
			// InternalDsl.g:4722:2: ( '{' )
			{
			// InternalDsl.g:4722:2: ( '{' )
			// InternalDsl.g:4723:2: '{'
			{
			 before(grammarAccess.getRenameAccess().getLeftCurlyBracketKeyword_1()); 
			match(input,46,FOLLOW_2); 
			 after(grammarAccess.getRenameAccess().getLeftCurlyBracketKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__1__Impl"



	// $ANTLR start "rule__Rename__Group__2"
	// InternalDsl.g:4732:1: rule__Rename__Group__2 : rule__Rename__Group__2__Impl rule__Rename__Group__3 ;
	public final void rule__Rename__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4736:2: ( rule__Rename__Group__2__Impl rule__Rename__Group__3 )
			// InternalDsl.g:4737:2: rule__Rename__Group__2__Impl rule__Rename__Group__3
			{
			pushFollow(FOLLOW_4);
			rule__Rename__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__2"



	// $ANTLR start "rule__Rename__Group__2__Impl"
	// InternalDsl.g:4744:1: rule__Rename__Group__2__Impl : ( 'type' ) ;
	public final void rule__Rename__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4748:2: ( ( 'type' ) )
			// InternalDsl.g:4749:2: ( 'type' )
			{
			// InternalDsl.g:4749:2: ( 'type' )
			// InternalDsl.g:4750:2: 'type'
			{
			 before(grammarAccess.getRenameAccess().getTypeKeyword_2()); 
			match(input,42,FOLLOW_2); 
			 after(grammarAccess.getRenameAccess().getTypeKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__2__Impl"



	// $ANTLR start "rule__Rename__Group__3"
	// InternalDsl.g:4759:1: rule__Rename__Group__3 : rule__Rename__Group__3__Impl rule__Rename__Group__4 ;
	public final void rule__Rename__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4763:2: ( rule__Rename__Group__3__Impl rule__Rename__Group__4 )
			// InternalDsl.g:4764:2: rule__Rename__Group__3__Impl rule__Rename__Group__4
			{
			pushFollow(FOLLOW_51);
			rule__Rename__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__3"



	// $ANTLR start "rule__Rename__Group__3__Impl"
	// InternalDsl.g:4771:1: rule__Rename__Group__3__Impl : ( ( rule__Rename__TypeAssignment_3 ) ) ;
	public final void rule__Rename__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4775:2: ( ( ( rule__Rename__TypeAssignment_3 ) ) )
			// InternalDsl.g:4776:2: ( ( rule__Rename__TypeAssignment_3 ) )
			{
			// InternalDsl.g:4776:2: ( ( rule__Rename__TypeAssignment_3 ) )
			// InternalDsl.g:4777:2: ( rule__Rename__TypeAssignment_3 )
			{
			 before(grammarAccess.getRenameAccess().getTypeAssignment_3()); 
			// InternalDsl.g:4778:2: ( rule__Rename__TypeAssignment_3 )
			// InternalDsl.g:4778:3: rule__Rename__TypeAssignment_3
			{
			pushFollow(FOLLOW_2);
			rule__Rename__TypeAssignment_3();
			state._fsp--;

			}

			 after(grammarAccess.getRenameAccess().getTypeAssignment_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__3__Impl"



	// $ANTLR start "rule__Rename__Group__4"
	// InternalDsl.g:4786:1: rule__Rename__Group__4 : rule__Rename__Group__4__Impl rule__Rename__Group__5 ;
	public final void rule__Rename__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4790:2: ( rule__Rename__Group__4__Impl rule__Rename__Group__5 )
			// InternalDsl.g:4791:2: rule__Rename__Group__4__Impl rule__Rename__Group__5
			{
			pushFollow(FOLLOW_4);
			rule__Rename__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__4"



	// $ANTLR start "rule__Rename__Group__4__Impl"
	// InternalDsl.g:4798:1: rule__Rename__Group__4__Impl : ( 'function' ) ;
	public final void rule__Rename__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4802:2: ( ( 'function' ) )
			// InternalDsl.g:4803:2: ( 'function' )
			{
			// InternalDsl.g:4803:2: ( 'function' )
			// InternalDsl.g:4804:2: 'function'
			{
			 before(grammarAccess.getRenameAccess().getFunctionKeyword_4()); 
			match(input,32,FOLLOW_2); 
			 after(grammarAccess.getRenameAccess().getFunctionKeyword_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__4__Impl"



	// $ANTLR start "rule__Rename__Group__5"
	// InternalDsl.g:4813:1: rule__Rename__Group__5 : rule__Rename__Group__5__Impl rule__Rename__Group__6 ;
	public final void rule__Rename__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4817:2: ( rule__Rename__Group__5__Impl rule__Rename__Group__6 )
			// InternalDsl.g:4818:2: rule__Rename__Group__5__Impl rule__Rename__Group__6
			{
			pushFollow(FOLLOW_52);
			rule__Rename__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__5"



	// $ANTLR start "rule__Rename__Group__5__Impl"
	// InternalDsl.g:4825:1: rule__Rename__Group__5__Impl : ( ( rule__Rename__FunctionAssignment_5 ) ) ;
	public final void rule__Rename__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4829:2: ( ( ( rule__Rename__FunctionAssignment_5 ) ) )
			// InternalDsl.g:4830:2: ( ( rule__Rename__FunctionAssignment_5 ) )
			{
			// InternalDsl.g:4830:2: ( ( rule__Rename__FunctionAssignment_5 ) )
			// InternalDsl.g:4831:2: ( rule__Rename__FunctionAssignment_5 )
			{
			 before(grammarAccess.getRenameAccess().getFunctionAssignment_5()); 
			// InternalDsl.g:4832:2: ( rule__Rename__FunctionAssignment_5 )
			// InternalDsl.g:4832:3: rule__Rename__FunctionAssignment_5
			{
			pushFollow(FOLLOW_2);
			rule__Rename__FunctionAssignment_5();
			state._fsp--;

			}

			 after(grammarAccess.getRenameAccess().getFunctionAssignment_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__5__Impl"



	// $ANTLR start "rule__Rename__Group__6"
	// InternalDsl.g:4840:1: rule__Rename__Group__6 : rule__Rename__Group__6__Impl rule__Rename__Group__7 ;
	public final void rule__Rename__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4844:2: ( rule__Rename__Group__6__Impl rule__Rename__Group__7 )
			// InternalDsl.g:4845:2: rule__Rename__Group__6__Impl rule__Rename__Group__7
			{
			pushFollow(FOLLOW_4);
			rule__Rename__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__6"



	// $ANTLR start "rule__Rename__Group__6__Impl"
	// InternalDsl.g:4852:1: rule__Rename__Group__6__Impl : ( 'newName' ) ;
	public final void rule__Rename__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4856:2: ( ( 'newName' ) )
			// InternalDsl.g:4857:2: ( 'newName' )
			{
			// InternalDsl.g:4857:2: ( 'newName' )
			// InternalDsl.g:4858:2: 'newName'
			{
			 before(grammarAccess.getRenameAccess().getNewNameKeyword_6()); 
			match(input,36,FOLLOW_2); 
			 after(grammarAccess.getRenameAccess().getNewNameKeyword_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__6__Impl"



	// $ANTLR start "rule__Rename__Group__7"
	// InternalDsl.g:4867:1: rule__Rename__Group__7 : rule__Rename__Group__7__Impl rule__Rename__Group__8 ;
	public final void rule__Rename__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4871:2: ( rule__Rename__Group__7__Impl rule__Rename__Group__8 )
			// InternalDsl.g:4872:2: rule__Rename__Group__7__Impl rule__Rename__Group__8
			{
			pushFollow(FOLLOW_7);
			rule__Rename__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Rename__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__7"



	// $ANTLR start "rule__Rename__Group__7__Impl"
	// InternalDsl.g:4879:1: rule__Rename__Group__7__Impl : ( ( rule__Rename__NewNameAssignment_7 ) ) ;
	public final void rule__Rename__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4883:2: ( ( ( rule__Rename__NewNameAssignment_7 ) ) )
			// InternalDsl.g:4884:2: ( ( rule__Rename__NewNameAssignment_7 ) )
			{
			// InternalDsl.g:4884:2: ( ( rule__Rename__NewNameAssignment_7 ) )
			// InternalDsl.g:4885:2: ( rule__Rename__NewNameAssignment_7 )
			{
			 before(grammarAccess.getRenameAccess().getNewNameAssignment_7()); 
			// InternalDsl.g:4886:2: ( rule__Rename__NewNameAssignment_7 )
			// InternalDsl.g:4886:3: rule__Rename__NewNameAssignment_7
			{
			pushFollow(FOLLOW_2);
			rule__Rename__NewNameAssignment_7();
			state._fsp--;

			}

			 after(grammarAccess.getRenameAccess().getNewNameAssignment_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__7__Impl"



	// $ANTLR start "rule__Rename__Group__8"
	// InternalDsl.g:4894:1: rule__Rename__Group__8 : rule__Rename__Group__8__Impl ;
	public final void rule__Rename__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4898:2: ( rule__Rename__Group__8__Impl )
			// InternalDsl.g:4899:2: rule__Rename__Group__8__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Rename__Group__8__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__8"



	// $ANTLR start "rule__Rename__Group__8__Impl"
	// InternalDsl.g:4905:1: rule__Rename__Group__8__Impl : ( '}' ) ;
	public final void rule__Rename__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:4909:2: ( ( '}' ) )
			// InternalDsl.g:4910:2: ( '}' )
			{
			// InternalDsl.g:4910:2: ( '}' )
			// InternalDsl.g:4911:2: '}'
			{
			 before(grammarAccess.getRenameAccess().getRightCurlyBracketKeyword_8()); 
			match(input,47,FOLLOW_2); 
			 after(grammarAccess.getRenameAccess().getRightCurlyBracketKeyword_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__Group__8__Impl"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup"
	// InternalDsl.g:4921:1: rule__CbCProblem__UnorderedGroup : rule__CbCProblem__UnorderedGroup__0 {...}?;
	public final void rule__CbCProblem__UnorderedGroup() throws RecognitionException {

				int stackSize = keepStackSize();
				getUnorderedGroupHelper().enter(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
			
		try {
			// InternalDsl.g:4926:2: ( rule__CbCProblem__UnorderedGroup__0 {...}?)
			// InternalDsl.g:4927:2: rule__CbCProblem__UnorderedGroup__0 {...}?
			{
			pushFollow(FOLLOW_2);
			rule__CbCProblem__UnorderedGroup__0();
			state._fsp--;

			if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getCbCProblemAccess().getUnorderedGroup()) ) {
				throw new FailedPredicateException(input, "rule__CbCProblem__UnorderedGroup", "getUnorderedGroupHelper().canLeave(grammarAccess.getCbCProblemAccess().getUnorderedGroup())");
			}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				getUnorderedGroupHelper().leave(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup__Impl"
	// InternalDsl.g:4935:1: rule__CbCProblem__UnorderedGroup__Impl : ( ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) ) ) ;
	public final void rule__CbCProblem__UnorderedGroup__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
				boolean selected = false;
			
		try {
			// InternalDsl.g:4940:3: ( ( ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) ) ) )
			// InternalDsl.g:4941:3: ( ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) ) )
			{
			// InternalDsl.g:4941:3: ( ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) ) | ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) ) )
			int alt33=4;
			int LA33_0 = input.LA(1);
			if ( LA33_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
				alt33=1;
			}
			else if ( LA33_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
				alt33=2;
			}
			else if ( LA33_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
				alt33=3;
			}
			else if ( LA33_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
				alt33=4;
			}

			switch (alt33) {
				case 1 :
					// InternalDsl.g:4942:3: ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) )
					{
					// InternalDsl.g:4942:3: ({...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) ) )
					// InternalDsl.g:4943:4: {...}? => ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
						throw new FailedPredicateException(input, "rule__CbCProblem__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0)");
					}
					// InternalDsl.g:4943:104: ( ( ( rule__CbCProblem__CbcformulaAssignment_0 ) ) )
					// InternalDsl.g:4944:5: ( ( rule__CbCProblem__CbcformulaAssignment_0 ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0);
									

										selected = true;
									
					// InternalDsl.g:4950:5: ( ( rule__CbCProblem__CbcformulaAssignment_0 ) )
					// InternalDsl.g:4951:6: ( rule__CbCProblem__CbcformulaAssignment_0 )
					{
					 before(grammarAccess.getCbCProblemAccess().getCbcformulaAssignment_0()); 
					// InternalDsl.g:4952:6: ( rule__CbCProblem__CbcformulaAssignment_0 )
					// InternalDsl.g:4952:7: rule__CbCProblem__CbcformulaAssignment_0
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__CbcformulaAssignment_0();
					state._fsp--;

					}

					 after(grammarAccess.getCbCProblemAccess().getCbcformulaAssignment_0()); 
					}

					}

					}

					}
					break;
				case 2 :
					// InternalDsl.g:4957:3: ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) )
					{
					// InternalDsl.g:4957:3: ({...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) ) )
					// InternalDsl.g:4958:4: {...}? => ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
						throw new FailedPredicateException(input, "rule__CbCProblem__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1)");
					}
					// InternalDsl.g:4958:104: ( ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) ) )
					// InternalDsl.g:4959:5: ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1);
									

										selected = true;
									
					// InternalDsl.g:4965:5: ( ( rule__CbCProblem__GlobalconditionAssignment_1 ) )
					// InternalDsl.g:4966:6: ( rule__CbCProblem__GlobalconditionAssignment_1 )
					{
					 before(grammarAccess.getCbCProblemAccess().getGlobalconditionAssignment_1()); 
					// InternalDsl.g:4967:6: ( rule__CbCProblem__GlobalconditionAssignment_1 )
					// InternalDsl.g:4967:7: rule__CbCProblem__GlobalconditionAssignment_1
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__GlobalconditionAssignment_1();
					state._fsp--;

					}

					 after(grammarAccess.getCbCProblemAccess().getGlobalconditionAssignment_1()); 
					}

					}

					}

					}
					break;
				case 3 :
					// InternalDsl.g:4972:3: ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) )
					{
					// InternalDsl.g:4972:3: ({...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) ) )
					// InternalDsl.g:4973:4: {...}? => ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
						throw new FailedPredicateException(input, "rule__CbCProblem__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2)");
					}
					// InternalDsl.g:4973:104: ( ( ( rule__CbCProblem__JavaVariableAssignment_2 ) ) )
					// InternalDsl.g:4974:5: ( ( rule__CbCProblem__JavaVariableAssignment_2 ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2);
									

										selected = true;
									
					// InternalDsl.g:4980:5: ( ( rule__CbCProblem__JavaVariableAssignment_2 ) )
					// InternalDsl.g:4981:6: ( rule__CbCProblem__JavaVariableAssignment_2 )
					{
					 before(grammarAccess.getCbCProblemAccess().getJavaVariableAssignment_2()); 
					// InternalDsl.g:4982:6: ( rule__CbCProblem__JavaVariableAssignment_2 )
					// InternalDsl.g:4982:7: rule__CbCProblem__JavaVariableAssignment_2
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__JavaVariableAssignment_2();
					state._fsp--;

					}

					 after(grammarAccess.getCbCProblemAccess().getJavaVariableAssignment_2()); 
					}

					}

					}

					}
					break;
				case 4 :
					// InternalDsl.g:4987:3: ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) )
					{
					// InternalDsl.g:4987:3: ({...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) ) )
					// InternalDsl.g:4988:4: {...}? => ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
						throw new FailedPredicateException(input, "rule__CbCProblem__UnorderedGroup__Impl", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3)");
					}
					// InternalDsl.g:4988:104: ( ( ( rule__CbCProblem__RenamingAssignment_3 ) ) )
					// InternalDsl.g:4989:5: ( ( rule__CbCProblem__RenamingAssignment_3 ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3);
									

										selected = true;
									
					// InternalDsl.g:4995:5: ( ( rule__CbCProblem__RenamingAssignment_3 ) )
					// InternalDsl.g:4996:6: ( rule__CbCProblem__RenamingAssignment_3 )
					{
					 before(grammarAccess.getCbCProblemAccess().getRenamingAssignment_3()); 
					// InternalDsl.g:4997:6: ( rule__CbCProblem__RenamingAssignment_3 )
					// InternalDsl.g:4997:7: rule__CbCProblem__RenamingAssignment_3
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__RenamingAssignment_3();
					state._fsp--;

					}

					 after(grammarAccess.getCbCProblemAccess().getRenamingAssignment_3()); 
					}

					}

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				if (selected)
					getUnorderedGroupHelper().returnFromSelection(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup__Impl"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup__0"
	// InternalDsl.g:5010:1: rule__CbCProblem__UnorderedGroup__0 : rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__1 )? ;
	public final void rule__CbCProblem__UnorderedGroup__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5014:2: ( rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__1 )? )
			// InternalDsl.g:5015:2: rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__1 )?
			{
			pushFollow(FOLLOW_53);
			rule__CbCProblem__UnorderedGroup__Impl();
			state._fsp--;

			// InternalDsl.g:5016:2: ( rule__CbCProblem__UnorderedGroup__1 )?
			int alt34=2;
			int LA34_0 = input.LA(1);
			if ( LA34_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
				alt34=1;
			}
			else if ( LA34_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
				alt34=1;
			}
			else if ( LA34_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
				alt34=1;
			}
			else if ( LA34_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
				alt34=1;
			}
			switch (alt34) {
				case 1 :
					// InternalDsl.g:5016:2: rule__CbCProblem__UnorderedGroup__1
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__UnorderedGroup__1();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup__0"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup__1"
	// InternalDsl.g:5022:1: rule__CbCProblem__UnorderedGroup__1 : rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__2 )? ;
	public final void rule__CbCProblem__UnorderedGroup__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5026:2: ( rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__2 )? )
			// InternalDsl.g:5027:2: rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__2 )?
			{
			pushFollow(FOLLOW_53);
			rule__CbCProblem__UnorderedGroup__Impl();
			state._fsp--;

			// InternalDsl.g:5028:2: ( rule__CbCProblem__UnorderedGroup__2 )?
			int alt35=2;
			int LA35_0 = input.LA(1);
			if ( LA35_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
				alt35=1;
			}
			else if ( LA35_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
				alt35=1;
			}
			else if ( LA35_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
				alt35=1;
			}
			else if ( LA35_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
				alt35=1;
			}
			switch (alt35) {
				case 1 :
					// InternalDsl.g:5028:2: rule__CbCProblem__UnorderedGroup__2
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__UnorderedGroup__2();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup__1"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup__2"
	// InternalDsl.g:5034:1: rule__CbCProblem__UnorderedGroup__2 : rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__3 )? ;
	public final void rule__CbCProblem__UnorderedGroup__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5038:2: ( rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__3 )? )
			// InternalDsl.g:5039:2: rule__CbCProblem__UnorderedGroup__Impl ( rule__CbCProblem__UnorderedGroup__3 )?
			{
			pushFollow(FOLLOW_53);
			rule__CbCProblem__UnorderedGroup__Impl();
			state._fsp--;

			// InternalDsl.g:5040:2: ( rule__CbCProblem__UnorderedGroup__3 )?
			int alt36=2;
			int LA36_0 = input.LA(1);
			if ( LA36_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
				alt36=1;
			}
			else if ( LA36_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
				alt36=1;
			}
			else if ( LA36_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
				alt36=1;
			}
			else if ( LA36_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
				alt36=1;
			}
			switch (alt36) {
				case 1 :
					// InternalDsl.g:5040:2: rule__CbCProblem__UnorderedGroup__3
					{
					pushFollow(FOLLOW_2);
					rule__CbCProblem__UnorderedGroup__3();
					state._fsp--;

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup__2"



	// $ANTLR start "rule__CbCProblem__UnorderedGroup__3"
	// InternalDsl.g:5046:1: rule__CbCProblem__UnorderedGroup__3 : rule__CbCProblem__UnorderedGroup__Impl ;
	public final void rule__CbCProblem__UnorderedGroup__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5050:2: ( rule__CbCProblem__UnorderedGroup__Impl )
			// InternalDsl.g:5051:2: rule__CbCProblem__UnorderedGroup__Impl
			{
			pushFollow(FOLLOW_2);
			rule__CbCProblem__UnorderedGroup__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__UnorderedGroup__3"



	// $ANTLR start "rule__CbCProblem__CbcformulaAssignment_0"
	// InternalDsl.g:5058:1: rule__CbCProblem__CbcformulaAssignment_0 : ( ruleCbCFormula ) ;
	public final void rule__CbCProblem__CbcformulaAssignment_0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5062:2: ( ( ruleCbCFormula ) )
			// InternalDsl.g:5063:2: ( ruleCbCFormula )
			{
			// InternalDsl.g:5063:2: ( ruleCbCFormula )
			// InternalDsl.g:5064:3: ruleCbCFormula
			{
			 before(grammarAccess.getCbCProblemAccess().getCbcformulaCbCFormulaParserRuleCall_0_0()); 
			pushFollow(FOLLOW_2);
			ruleCbCFormula();
			state._fsp--;

			 after(grammarAccess.getCbCProblemAccess().getCbcformulaCbCFormulaParserRuleCall_0_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__CbcformulaAssignment_0"



	// $ANTLR start "rule__CbCProblem__GlobalconditionAssignment_1"
	// InternalDsl.g:5073:1: rule__CbCProblem__GlobalconditionAssignment_1 : ( ruleGlobalConditions ) ;
	public final void rule__CbCProblem__GlobalconditionAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5077:2: ( ( ruleGlobalConditions ) )
			// InternalDsl.g:5078:2: ( ruleGlobalConditions )
			{
			// InternalDsl.g:5078:2: ( ruleGlobalConditions )
			// InternalDsl.g:5079:3: ruleGlobalConditions
			{
			 before(grammarAccess.getCbCProblemAccess().getGlobalconditionGlobalConditionsParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleGlobalConditions();
			state._fsp--;

			 after(grammarAccess.getCbCProblemAccess().getGlobalconditionGlobalConditionsParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__GlobalconditionAssignment_1"



	// $ANTLR start "rule__CbCProblem__JavaVariableAssignment_2"
	// InternalDsl.g:5088:1: rule__CbCProblem__JavaVariableAssignment_2 : ( ruleJavaVariables ) ;
	public final void rule__CbCProblem__JavaVariableAssignment_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5092:2: ( ( ruleJavaVariables ) )
			// InternalDsl.g:5093:2: ( ruleJavaVariables )
			{
			// InternalDsl.g:5093:2: ( ruleJavaVariables )
			// InternalDsl.g:5094:3: ruleJavaVariables
			{
			 before(grammarAccess.getCbCProblemAccess().getJavaVariableJavaVariablesParserRuleCall_2_0()); 
			pushFollow(FOLLOW_2);
			ruleJavaVariables();
			state._fsp--;

			 after(grammarAccess.getCbCProblemAccess().getJavaVariableJavaVariablesParserRuleCall_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__JavaVariableAssignment_2"



	// $ANTLR start "rule__CbCProblem__RenamingAssignment_3"
	// InternalDsl.g:5103:1: rule__CbCProblem__RenamingAssignment_3 : ( ruleRenaming ) ;
	public final void rule__CbCProblem__RenamingAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5107:2: ( ( ruleRenaming ) )
			// InternalDsl.g:5108:2: ( ruleRenaming )
			{
			// InternalDsl.g:5108:2: ( ruleRenaming )
			// InternalDsl.g:5109:3: ruleRenaming
			{
			 before(grammarAccess.getCbCProblemAccess().getRenamingRenamingParserRuleCall_3_0()); 
			pushFollow(FOLLOW_2);
			ruleRenaming();
			state._fsp--;

			 after(grammarAccess.getCbCProblemAccess().getRenamingRenamingParserRuleCall_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCProblem__RenamingAssignment_3"



	// $ANTLR start "rule__CbCFormula__NameAssignment_1"
	// InternalDsl.g:5118:1: rule__CbCFormula__NameAssignment_1 : ( ruleEString ) ;
	public final void rule__CbCFormula__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5122:2: ( ( ruleEString ) )
			// InternalDsl.g:5123:2: ( ruleEString )
			{
			// InternalDsl.g:5123:2: ( ruleEString )
			// InternalDsl.g:5124:3: ruleEString
			{
			 before(grammarAccess.getCbCFormulaAccess().getNameEStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getCbCFormulaAccess().getNameEStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__NameAssignment_1"



	// $ANTLR start "rule__CbCFormula__PreConditionAssignment_4"
	// InternalDsl.g:5133:1: rule__CbCFormula__PreConditionAssignment_4 : ( ruleCondition ) ;
	public final void rule__CbCFormula__PreConditionAssignment_4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5137:2: ( ( ruleCondition ) )
			// InternalDsl.g:5138:2: ( ruleCondition )
			{
			// InternalDsl.g:5138:2: ( ruleCondition )
			// InternalDsl.g:5139:3: ruleCondition
			{
			 before(grammarAccess.getCbCFormulaAccess().getPreConditionConditionParserRuleCall_4_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getCbCFormulaAccess().getPreConditionConditionParserRuleCall_4_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__PreConditionAssignment_4"



	// $ANTLR start "rule__CbCFormula__StatementAssignment_7"
	// InternalDsl.g:5148:1: rule__CbCFormula__StatementAssignment_7 : ( ruleAbstractStatement ) ;
	public final void rule__CbCFormula__StatementAssignment_7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5152:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5153:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5153:2: ( ruleAbstractStatement )
			// InternalDsl.g:5154:3: ruleAbstractStatement
			{
			 before(grammarAccess.getCbCFormulaAccess().getStatementAbstractStatementParserRuleCall_7_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getCbCFormulaAccess().getStatementAbstractStatementParserRuleCall_7_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__StatementAssignment_7"



	// $ANTLR start "rule__CbCFormula__PostConditionAssignment_11"
	// InternalDsl.g:5163:1: rule__CbCFormula__PostConditionAssignment_11 : ( ruleCondition ) ;
	public final void rule__CbCFormula__PostConditionAssignment_11() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5167:2: ( ( ruleCondition ) )
			// InternalDsl.g:5168:2: ( ruleCondition )
			{
			// InternalDsl.g:5168:2: ( ruleCondition )
			// InternalDsl.g:5169:3: ruleCondition
			{
			 before(grammarAccess.getCbCFormulaAccess().getPostConditionConditionParserRuleCall_11_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getCbCFormulaAccess().getPostConditionConditionParserRuleCall_11_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CbCFormula__PostConditionAssignment_11"



	// $ANTLR start "rule__AbstractStatement_Impl__NameAssignment_1"
	// InternalDsl.g:5178:1: rule__AbstractStatement_Impl__NameAssignment_1 : ( ruleCodeString ) ;
	public final void rule__AbstractStatement_Impl__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5182:2: ( ( ruleCodeString ) )
			// InternalDsl.g:5183:2: ( ruleCodeString )
			{
			// InternalDsl.g:5183:2: ( ruleCodeString )
			// InternalDsl.g:5184:3: ruleCodeString
			{
			 before(grammarAccess.getAbstractStatement_ImplAccess().getNameCodeStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleCodeString();
			state._fsp--;

			 after(grammarAccess.getAbstractStatement_ImplAccess().getNameCodeStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractStatement_Impl__NameAssignment_1"



	// $ANTLR start "rule__MethodStatement__NameAssignment_1"
	// InternalDsl.g:5193:1: rule__MethodStatement__NameAssignment_1 : ( ruleEString ) ;
	public final void rule__MethodStatement__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5197:2: ( ( ruleEString ) )
			// InternalDsl.g:5198:2: ( ruleEString )
			{
			// InternalDsl.g:5198:2: ( ruleEString )
			// InternalDsl.g:5199:3: ruleEString
			{
			 before(grammarAccess.getMethodStatementAccess().getNameEStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getMethodStatementAccess().getNameEStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__MethodStatement__NameAssignment_1"



	// $ANTLR start "rule__Condition__NameAssignment_1"
	// InternalDsl.g:5208:1: rule__Condition__NameAssignment_1 : ( ruleEString ) ;
	public final void rule__Condition__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5212:2: ( ( ruleEString ) )
			// InternalDsl.g:5213:2: ( ruleEString )
			{
			// InternalDsl.g:5213:2: ( ruleEString )
			// InternalDsl.g:5214:3: ruleEString
			{
			 before(grammarAccess.getConditionAccess().getNameEStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getConditionAccess().getNameEStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Condition__NameAssignment_1"



	// $ANTLR start "rule__SkipStatement__NameAssignment"
	// InternalDsl.g:5223:1: rule__SkipStatement__NameAssignment : ( ( ';' ) ) ;
	public final void rule__SkipStatement__NameAssignment() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5227:2: ( ( ( ';' ) ) )
			// InternalDsl.g:5228:2: ( ( ';' ) )
			{
			// InternalDsl.g:5228:2: ( ( ';' ) )
			// InternalDsl.g:5229:3: ( ';' )
			{
			 before(grammarAccess.getSkipStatementAccess().getNameSemicolonKeyword_0()); 
			// InternalDsl.g:5230:3: ( ';' )
			// InternalDsl.g:5231:4: ';'
			{
			 before(grammarAccess.getSkipStatementAccess().getNameSemicolonKeyword_0()); 
			match(input,20,FOLLOW_2); 
			 after(grammarAccess.getSkipStatementAccess().getNameSemicolonKeyword_0()); 
			}

			 after(grammarAccess.getSkipStatementAccess().getNameSemicolonKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SkipStatement__NameAssignment"



	// $ANTLR start "rule__CompositionStatement__FirstStatementAssignment_2"
	// InternalDsl.g:5242:1: rule__CompositionStatement__FirstStatementAssignment_2 : ( ruleAbstractStatement ) ;
	public final void rule__CompositionStatement__FirstStatementAssignment_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5246:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5247:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5247:2: ( ruleAbstractStatement )
			// InternalDsl.g:5248:3: ruleAbstractStatement
			{
			 before(grammarAccess.getCompositionStatementAccess().getFirstStatementAbstractStatementParserRuleCall_2_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getCompositionStatementAccess().getFirstStatementAbstractStatementParserRuleCall_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__FirstStatementAssignment_2"



	// $ANTLR start "rule__CompositionStatement__IntermediateConditionAssignment_6"
	// InternalDsl.g:5257:1: rule__CompositionStatement__IntermediateConditionAssignment_6 : ( ruleCondition ) ;
	public final void rule__CompositionStatement__IntermediateConditionAssignment_6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5261:2: ( ( ruleCondition ) )
			// InternalDsl.g:5262:2: ( ruleCondition )
			{
			// InternalDsl.g:5262:2: ( ruleCondition )
			// InternalDsl.g:5263:3: ruleCondition
			{
			 before(grammarAccess.getCompositionStatementAccess().getIntermediateConditionConditionParserRuleCall_6_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getCompositionStatementAccess().getIntermediateConditionConditionParserRuleCall_6_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__IntermediateConditionAssignment_6"



	// $ANTLR start "rule__CompositionStatement__SecondStatementAssignment_9"
	// InternalDsl.g:5272:1: rule__CompositionStatement__SecondStatementAssignment_9 : ( ruleAbstractStatement ) ;
	public final void rule__CompositionStatement__SecondStatementAssignment_9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5276:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5277:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5277:2: ( ruleAbstractStatement )
			// InternalDsl.g:5278:3: ruleAbstractStatement
			{
			 before(grammarAccess.getCompositionStatementAccess().getSecondStatementAbstractStatementParserRuleCall_9_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getCompositionStatementAccess().getSecondStatementAbstractStatementParserRuleCall_9_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__CompositionStatement__SecondStatementAssignment_9"



	// $ANTLR start "rule__SelectionStatement__GuardsAssignment_3"
	// InternalDsl.g:5287:1: rule__SelectionStatement__GuardsAssignment_3 : ( ruleCondition ) ;
	public final void rule__SelectionStatement__GuardsAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5291:2: ( ( ruleCondition ) )
			// InternalDsl.g:5292:2: ( ruleCondition )
			{
			// InternalDsl.g:5292:2: ( ruleCondition )
			// InternalDsl.g:5293:3: ruleCondition
			{
			 before(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_3_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__GuardsAssignment_3"



	// $ANTLR start "rule__SelectionStatement__CommandsAssignment_7"
	// InternalDsl.g:5302:1: rule__SelectionStatement__CommandsAssignment_7 : ( ruleAbstractStatement ) ;
	public final void rule__SelectionStatement__CommandsAssignment_7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5306:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5307:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5307:2: ( ruleAbstractStatement )
			// InternalDsl.g:5308:3: ruleAbstractStatement
			{
			 before(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_7_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_7_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__CommandsAssignment_7"



	// $ANTLR start "rule__SelectionStatement__GuardsAssignment_9_2"
	// InternalDsl.g:5317:1: rule__SelectionStatement__GuardsAssignment_9_2 : ( ruleCondition ) ;
	public final void rule__SelectionStatement__GuardsAssignment_9_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5321:2: ( ( ruleCondition ) )
			// InternalDsl.g:5322:2: ( ruleCondition )
			{
			// InternalDsl.g:5322:2: ( ruleCondition )
			// InternalDsl.g:5323:3: ruleCondition
			{
			 before(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_9_2_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_9_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__GuardsAssignment_9_2"



	// $ANTLR start "rule__SelectionStatement__CommandsAssignment_9_6"
	// InternalDsl.g:5332:1: rule__SelectionStatement__CommandsAssignment_9_6 : ( ruleAbstractStatement ) ;
	public final void rule__SelectionStatement__CommandsAssignment_9_6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5336:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5337:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5337:2: ( ruleAbstractStatement )
			// InternalDsl.g:5338:3: ruleAbstractStatement
			{
			 before(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_9_6_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_9_6_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SelectionStatement__CommandsAssignment_9_6"



	// $ANTLR start "rule__SmallRepetitionStatement__GuardAssignment_3"
	// InternalDsl.g:5347:1: rule__SmallRepetitionStatement__GuardAssignment_3 : ( ruleCondition ) ;
	public final void rule__SmallRepetitionStatement__GuardAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5351:2: ( ( ruleCondition ) )
			// InternalDsl.g:5352:2: ( ruleCondition )
			{
			// InternalDsl.g:5352:2: ( ruleCondition )
			// InternalDsl.g:5353:3: ruleCondition
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getGuardConditionParserRuleCall_3_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getSmallRepetitionStatementAccess().getGuardConditionParserRuleCall_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__GuardAssignment_3"



	// $ANTLR start "rule__SmallRepetitionStatement__InvariantAssignment_8"
	// InternalDsl.g:5362:1: rule__SmallRepetitionStatement__InvariantAssignment_8 : ( ruleCondition ) ;
	public final void rule__SmallRepetitionStatement__InvariantAssignment_8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5366:2: ( ( ruleCondition ) )
			// InternalDsl.g:5367:2: ( ruleCondition )
			{
			// InternalDsl.g:5367:2: ( ruleCondition )
			// InternalDsl.g:5368:3: ruleCondition
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getInvariantConditionParserRuleCall_8_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getSmallRepetitionStatementAccess().getInvariantConditionParserRuleCall_8_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__InvariantAssignment_8"



	// $ANTLR start "rule__SmallRepetitionStatement__VariantAssignment_12"
	// InternalDsl.g:5377:1: rule__SmallRepetitionStatement__VariantAssignment_12 : ( ruleVariant ) ;
	public final void rule__SmallRepetitionStatement__VariantAssignment_12() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5381:2: ( ( ruleVariant ) )
			// InternalDsl.g:5382:2: ( ruleVariant )
			{
			// InternalDsl.g:5382:2: ( ruleVariant )
			// InternalDsl.g:5383:3: ruleVariant
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getVariantVariantParserRuleCall_12_0()); 
			pushFollow(FOLLOW_2);
			ruleVariant();
			state._fsp--;

			 after(grammarAccess.getSmallRepetitionStatementAccess().getVariantVariantParserRuleCall_12_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__VariantAssignment_12"



	// $ANTLR start "rule__SmallRepetitionStatement__LoopStatementAssignment_15"
	// InternalDsl.g:5392:1: rule__SmallRepetitionStatement__LoopStatementAssignment_15 : ( ruleAbstractStatement ) ;
	public final void rule__SmallRepetitionStatement__LoopStatementAssignment_15() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5396:2: ( ( ruleAbstractStatement ) )
			// InternalDsl.g:5397:2: ( ruleAbstractStatement )
			{
			// InternalDsl.g:5397:2: ( ruleAbstractStatement )
			// InternalDsl.g:5398:3: ruleAbstractStatement
			{
			 before(grammarAccess.getSmallRepetitionStatementAccess().getLoopStatementAbstractStatementParserRuleCall_15_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractStatement();
			state._fsp--;

			 after(grammarAccess.getSmallRepetitionStatementAccess().getLoopStatementAbstractStatementParserRuleCall_15_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__SmallRepetitionStatement__LoopStatementAssignment_15"



	// $ANTLR start "rule__Variant__NameAssignment_1"
	// InternalDsl.g:5407:1: rule__Variant__NameAssignment_1 : ( ruleEString ) ;
	public final void rule__Variant__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5411:2: ( ( ruleEString ) )
			// InternalDsl.g:5412:2: ( ruleEString )
			{
			// InternalDsl.g:5412:2: ( ruleEString )
			// InternalDsl.g:5413:3: ruleEString
			{
			 before(grammarAccess.getVariantAccess().getNameEStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getVariantAccess().getNameEStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Variant__NameAssignment_1"



	// $ANTLR start "rule__JavaVariables__VariablesAssignment_2_2"
	// InternalDsl.g:5422:1: rule__JavaVariables__VariablesAssignment_2_2 : ( ruleJavaVariable ) ;
	public final void rule__JavaVariables__VariablesAssignment_2_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5426:2: ( ( ruleJavaVariable ) )
			// InternalDsl.g:5427:2: ( ruleJavaVariable )
			{
			// InternalDsl.g:5427:2: ( ruleJavaVariable )
			// InternalDsl.g:5428:3: ruleJavaVariable
			{
			 before(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_2_0()); 
			pushFollow(FOLLOW_2);
			ruleJavaVariable();
			state._fsp--;

			 after(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__VariablesAssignment_2_2"



	// $ANTLR start "rule__JavaVariables__VariablesAssignment_2_3_1"
	// InternalDsl.g:5437:1: rule__JavaVariables__VariablesAssignment_2_3_1 : ( ruleJavaVariable ) ;
	public final void rule__JavaVariables__VariablesAssignment_2_3_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5441:2: ( ( ruleJavaVariable ) )
			// InternalDsl.g:5442:2: ( ruleJavaVariable )
			{
			// InternalDsl.g:5442:2: ( ruleJavaVariable )
			// InternalDsl.g:5443:3: ruleJavaVariable
			{
			 before(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_3_1_0()); 
			pushFollow(FOLLOW_2);
			ruleJavaVariable();
			state._fsp--;

			 after(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_3_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariables__VariablesAssignment_2_3_1"



	// $ANTLR start "rule__JavaVariable__NameAssignment_1"
	// InternalDsl.g:5452:1: rule__JavaVariable__NameAssignment_1 : ( ruleEString ) ;
	public final void rule__JavaVariable__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5456:2: ( ( ruleEString ) )
			// InternalDsl.g:5457:2: ( ruleEString )
			{
			// InternalDsl.g:5457:2: ( ruleEString )
			// InternalDsl.g:5458:3: ruleEString
			{
			 before(grammarAccess.getJavaVariableAccess().getNameEStringParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getJavaVariableAccess().getNameEStringParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__JavaVariable__NameAssignment_1"



	// $ANTLR start "rule__GlobalConditions__ConditionsAssignment_2_2"
	// InternalDsl.g:5467:1: rule__GlobalConditions__ConditionsAssignment_2_2 : ( ruleCondition ) ;
	public final void rule__GlobalConditions__ConditionsAssignment_2_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5471:2: ( ( ruleCondition ) )
			// InternalDsl.g:5472:2: ( ruleCondition )
			{
			// InternalDsl.g:5472:2: ( ruleCondition )
			// InternalDsl.g:5473:3: ruleCondition
			{
			 before(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_2_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__ConditionsAssignment_2_2"



	// $ANTLR start "rule__GlobalConditions__ConditionsAssignment_2_3_1"
	// InternalDsl.g:5482:1: rule__GlobalConditions__ConditionsAssignment_2_3_1 : ( ruleCondition ) ;
	public final void rule__GlobalConditions__ConditionsAssignment_2_3_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5486:2: ( ( ruleCondition ) )
			// InternalDsl.g:5487:2: ( ruleCondition )
			{
			// InternalDsl.g:5487:2: ( ruleCondition )
			// InternalDsl.g:5488:3: ruleCondition
			{
			 before(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_3_1_0()); 
			pushFollow(FOLLOW_2);
			ruleCondition();
			state._fsp--;

			 after(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_3_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__GlobalConditions__ConditionsAssignment_2_3_1"



	// $ANTLR start "rule__Renaming__RenameAssignment_2_2"
	// InternalDsl.g:5497:1: rule__Renaming__RenameAssignment_2_2 : ( ruleRename ) ;
	public final void rule__Renaming__RenameAssignment_2_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5501:2: ( ( ruleRename ) )
			// InternalDsl.g:5502:2: ( ruleRename )
			{
			// InternalDsl.g:5502:2: ( ruleRename )
			// InternalDsl.g:5503:3: ruleRename
			{
			 before(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_2_0()); 
			pushFollow(FOLLOW_2);
			ruleRename();
			state._fsp--;

			 after(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__RenameAssignment_2_2"



	// $ANTLR start "rule__Renaming__RenameAssignment_2_3"
	// InternalDsl.g:5512:1: rule__Renaming__RenameAssignment_2_3 : ( ruleRename ) ;
	public final void rule__Renaming__RenameAssignment_2_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5516:2: ( ( ruleRename ) )
			// InternalDsl.g:5517:2: ( ruleRename )
			{
			// InternalDsl.g:5517:2: ( ruleRename )
			// InternalDsl.g:5518:3: ruleRename
			{
			 before(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_3_0()); 
			pushFollow(FOLLOW_2);
			ruleRename();
			state._fsp--;

			 after(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Renaming__RenameAssignment_2_3"



	// $ANTLR start "rule__Rename__TypeAssignment_3"
	// InternalDsl.g:5527:1: rule__Rename__TypeAssignment_3 : ( ruleEString ) ;
	public final void rule__Rename__TypeAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5531:2: ( ( ruleEString ) )
			// InternalDsl.g:5532:2: ( ruleEString )
			{
			// InternalDsl.g:5532:2: ( ruleEString )
			// InternalDsl.g:5533:3: ruleEString
			{
			 before(grammarAccess.getRenameAccess().getTypeEStringParserRuleCall_3_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getRenameAccess().getTypeEStringParserRuleCall_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__TypeAssignment_3"



	// $ANTLR start "rule__Rename__FunctionAssignment_5"
	// InternalDsl.g:5542:1: rule__Rename__FunctionAssignment_5 : ( ruleEString ) ;
	public final void rule__Rename__FunctionAssignment_5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5546:2: ( ( ruleEString ) )
			// InternalDsl.g:5547:2: ( ruleEString )
			{
			// InternalDsl.g:5547:2: ( ruleEString )
			// InternalDsl.g:5548:3: ruleEString
			{
			 before(grammarAccess.getRenameAccess().getFunctionEStringParserRuleCall_5_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getRenameAccess().getFunctionEStringParserRuleCall_5_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__FunctionAssignment_5"



	// $ANTLR start "rule__Rename__NewNameAssignment_7"
	// InternalDsl.g:5557:1: rule__Rename__NewNameAssignment_7 : ( ruleEString ) ;
	public final void rule__Rename__NewNameAssignment_7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:5561:2: ( ( ruleEString ) )
			// InternalDsl.g:5562:2: ( ruleEString )
			{
			// InternalDsl.g:5562:2: ( ruleEString )
			// InternalDsl.g:5563:3: ruleEString
			{
			 before(grammarAccess.getRenameAccess().getNewNameEStringParserRuleCall_7_0()); 
			pushFollow(FOLLOW_2);
			ruleEString();
			state._fsp--;

			 after(grammarAccess.getRenameAccess().getNewNameEStringParserRuleCall_7_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Rename__NewNameAssignment_7"

	// Delegated rules



	public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000220L});
	public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000600200100220L});
	public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000004200000L});
	public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000020060L});
	public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x00000000001AC800L});
	public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00000000000AC802L});
	public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000004041000L});
	public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000022060L});
	public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000020020L});
	public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000008020060L});
	public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x00000000C0000000L});
	public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000040000002L});
	public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000800000010000L});
	public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000C00000000000L});
	public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000400000000002L});
	public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000001000000000L});
	public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000003C00002L});
}
