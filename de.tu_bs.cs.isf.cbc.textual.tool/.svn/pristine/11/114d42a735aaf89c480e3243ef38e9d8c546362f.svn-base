package de.tu_bs.cs.isf.cbc.textual.tool.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import de.tu_bs.cs.isf.cbc.textual.tool.services.DslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDslParser extends AbstractInternalAntlrParser {
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
	public AbstractInternalAntlrParser[] getDelegates() {
		return new AbstractInternalAntlrParser[] {};
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

	    public InternalDslParser(TokenStream input, DslGrammarAccess grammarAccess) {
	        this(input);
	        this.grammarAccess = grammarAccess;
	        registerRules(grammarAccess.getGrammar());
	    }

	    @Override
	    protected String getFirstRuleName() {
	    	return "CbCProblem";
	   	}

	   	@Override
	   	protected DslGrammarAccess getGrammarAccess() {
	   		return grammarAccess;
	   	}




	// $ANTLR start "entryRuleCbCProblem"
	// InternalDsl.g:64:1: entryRuleCbCProblem returns [EObject current=null] :iv_ruleCbCProblem= ruleCbCProblem EOF ;
	public final EObject entryRuleCbCProblem() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCbCProblem =null;

		try {
			// InternalDsl.g:64:51: (iv_ruleCbCProblem= ruleCbCProblem EOF )
			// InternalDsl.g:65:2: iv_ruleCbCProblem= ruleCbCProblem EOF
			{
			 newCompositeNode(grammarAccess.getCbCProblemRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleCbCProblem=ruleCbCProblem();
			state._fsp--;

			 current =iv_ruleCbCProblem; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleCbCProblem"



	// $ANTLR start "ruleCbCProblem"
	// InternalDsl.g:71:1: ruleCbCProblem returns [EObject current=null] : ( ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) ) ) ;
	public final EObject ruleCbCProblem() throws RecognitionException {
		EObject current = null;


		EObject lv_cbcformula_1_0 =null;
		EObject lv_globalcondition_2_0 =null;
		EObject lv_javaVariable_3_0 =null;
		EObject lv_renaming_4_0 =null;


			enterRule();

		try {
			// InternalDsl.g:77:2: ( ( ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) ) ) )
			// InternalDsl.g:78:2: ( ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) ) )
			{
			// InternalDsl.g:78:2: ( ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) ) )
			// InternalDsl.g:79:3: ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) )
			{
			// InternalDsl.g:79:3: ( ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?) )
			// InternalDsl.g:80:4: ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?)
			{
			 
						  getUnorderedGroupHelper().enter(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
						
			// InternalDsl.g:83:4: ( ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?)
			// InternalDsl.g:84:5: ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+ {...}?
			{
			// InternalDsl.g:84:5: ( ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) ) | ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) ) )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=5;
				int LA1_0 = input.LA(1);
				if ( LA1_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
					alt1=1;
				}
				else if ( LA1_0 == 23 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
					alt1=2;
				}
				else if ( LA1_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
					alt1=3;
				}
				else if ( LA1_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
					alt1=4;
				}

				switch (alt1) {
				case 1 :
					// InternalDsl.g:85:3: ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) )
					{
					// InternalDsl.g:85:3: ({...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) ) )
					// InternalDsl.g:86:4: {...}? => ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0)");
					}
					// InternalDsl.g:86:104: ( ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) ) )
					// InternalDsl.g:87:5: ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 0);
									
					// InternalDsl.g:90:8: ({...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) ) )
					// InternalDsl.g:90:9: {...}? => ( (lv_cbcformula_1_0= ruleCbCFormula ) )
					{
					if ( !((true)) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "true");
					}
					// InternalDsl.g:90:18: ( (lv_cbcformula_1_0= ruleCbCFormula ) )
					// InternalDsl.g:90:19: (lv_cbcformula_1_0= ruleCbCFormula )
					{
					// InternalDsl.g:90:19: (lv_cbcformula_1_0= ruleCbCFormula )
					// InternalDsl.g:91:9: lv_cbcformula_1_0= ruleCbCFormula
					{

														newCompositeNode(grammarAccess.getCbCProblemAccess().getCbcformulaCbCFormulaParserRuleCall_0_0());
													
					pushFollow(FOLLOW_3);
					lv_cbcformula_1_0=ruleCbCFormula();
					state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getCbCProblemRule());
														}
														set(
															current,
															"cbcformula",
															lv_cbcformula_1_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.CbCFormula");
														afterParserOrEnumRuleCall();
													
					}

					}

					}

					 
										getUnorderedGroupHelper().returnFromSelection(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
									
					}

					}

					}
					break;
				case 2 :
					// InternalDsl.g:113:3: ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) )
					{
					// InternalDsl.g:113:3: ({...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) ) )
					// InternalDsl.g:114:4: {...}? => ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1)");
					}
					// InternalDsl.g:114:104: ( ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) ) )
					// InternalDsl.g:115:5: ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 1);
									
					// InternalDsl.g:118:8: ({...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) ) )
					// InternalDsl.g:118:9: {...}? => ( (lv_globalcondition_2_0= ruleGlobalConditions ) )
					{
					if ( !((true)) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "true");
					}
					// InternalDsl.g:118:18: ( (lv_globalcondition_2_0= ruleGlobalConditions ) )
					// InternalDsl.g:118:19: (lv_globalcondition_2_0= ruleGlobalConditions )
					{
					// InternalDsl.g:118:19: (lv_globalcondition_2_0= ruleGlobalConditions )
					// InternalDsl.g:119:9: lv_globalcondition_2_0= ruleGlobalConditions
					{

														newCompositeNode(grammarAccess.getCbCProblemAccess().getGlobalconditionGlobalConditionsParserRuleCall_1_0());
													
					pushFollow(FOLLOW_3);
					lv_globalcondition_2_0=ruleGlobalConditions();
					state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getCbCProblemRule());
														}
														set(
															current,
															"globalcondition",
															lv_globalcondition_2_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.GlobalConditions");
														afterParserOrEnumRuleCall();
													
					}

					}

					}

					 
										getUnorderedGroupHelper().returnFromSelection(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
									
					}

					}

					}
					break;
				case 3 :
					// InternalDsl.g:141:3: ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) )
					{
					// InternalDsl.g:141:3: ({...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) ) )
					// InternalDsl.g:142:4: {...}? => ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2)");
					}
					// InternalDsl.g:142:104: ( ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) ) )
					// InternalDsl.g:143:5: ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 2);
									
					// InternalDsl.g:146:8: ({...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) ) )
					// InternalDsl.g:146:9: {...}? => ( (lv_javaVariable_3_0= ruleJavaVariables ) )
					{
					if ( !((true)) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "true");
					}
					// InternalDsl.g:146:18: ( (lv_javaVariable_3_0= ruleJavaVariables ) )
					// InternalDsl.g:146:19: (lv_javaVariable_3_0= ruleJavaVariables )
					{
					// InternalDsl.g:146:19: (lv_javaVariable_3_0= ruleJavaVariables )
					// InternalDsl.g:147:9: lv_javaVariable_3_0= ruleJavaVariables
					{

														newCompositeNode(grammarAccess.getCbCProblemAccess().getJavaVariableJavaVariablesParserRuleCall_2_0());
													
					pushFollow(FOLLOW_3);
					lv_javaVariable_3_0=ruleJavaVariables();
					state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getCbCProblemRule());
														}
														set(
															current,
															"javaVariable",
															lv_javaVariable_3_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.JavaVariables");
														afterParserOrEnumRuleCall();
													
					}

					}

					}

					 
										getUnorderedGroupHelper().returnFromSelection(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
									
					}

					}

					}
					break;
				case 4 :
					// InternalDsl.g:169:3: ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) )
					{
					// InternalDsl.g:169:3: ({...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) ) )
					// InternalDsl.g:170:4: {...}? => ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) )
					{
					if ( ! getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "getUnorderedGroupHelper().canSelect(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3)");
					}
					// InternalDsl.g:170:104: ( ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) ) )
					// InternalDsl.g:171:5: ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) )
					{

										getUnorderedGroupHelper().select(grammarAccess.getCbCProblemAccess().getUnorderedGroup(), 3);
									
					// InternalDsl.g:174:8: ({...}? => ( (lv_renaming_4_0= ruleRenaming ) ) )
					// InternalDsl.g:174:9: {...}? => ( (lv_renaming_4_0= ruleRenaming ) )
					{
					if ( !((true)) ) {
						throw new FailedPredicateException(input, "ruleCbCProblem", "true");
					}
					// InternalDsl.g:174:18: ( (lv_renaming_4_0= ruleRenaming ) )
					// InternalDsl.g:174:19: (lv_renaming_4_0= ruleRenaming )
					{
					// InternalDsl.g:174:19: (lv_renaming_4_0= ruleRenaming )
					// InternalDsl.g:175:9: lv_renaming_4_0= ruleRenaming
					{

														newCompositeNode(grammarAccess.getCbCProblemAccess().getRenamingRenamingParserRuleCall_3_0());
													
					pushFollow(FOLLOW_3);
					lv_renaming_4_0=ruleRenaming();
					state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getCbCProblemRule());
														}
														set(
															current,
															"renaming",
															lv_renaming_4_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Renaming");
														afterParserOrEnumRuleCall();
													
					}

					}

					}

					 
										getUnorderedGroupHelper().returnFromSelection(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
									
					}

					}

					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			if ( ! getUnorderedGroupHelper().canLeave(grammarAccess.getCbCProblemAccess().getUnorderedGroup()) ) {
				throw new FailedPredicateException(input, "ruleCbCProblem", "getUnorderedGroupHelper().canLeave(grammarAccess.getCbCProblemAccess().getUnorderedGroup())");
			}
			}

			}

			 
						  getUnorderedGroupHelper().leave(grammarAccess.getCbCProblemAccess().getUnorderedGroup());
						
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleCbCProblem"



	// $ANTLR start "entryRuleCbCFormula"
	// InternalDsl.g:208:1: entryRuleCbCFormula returns [EObject current=null] :iv_ruleCbCFormula= ruleCbCFormula EOF ;
	public final EObject entryRuleCbCFormula() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCbCFormula =null;

		try {
			// InternalDsl.g:208:51: (iv_ruleCbCFormula= ruleCbCFormula EOF )
			// InternalDsl.g:209:2: iv_ruleCbCFormula= ruleCbCFormula EOF
			{
			 newCompositeNode(grammarAccess.getCbCFormulaRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleCbCFormula=ruleCbCFormula();
			state._fsp--;

			 current =iv_ruleCbCFormula; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleCbCFormula"



	// $ANTLR start "ruleCbCFormula"
	// InternalDsl.g:215:1: ruleCbCFormula returns [EObject current=null] : (otherlv_0= 'Formula' ( (lv_name_1_0= ruleEString ) ) otherlv_2= 'pre:' otherlv_3= '{' ( (lv_preCondition_4_0= ruleCondition ) ) otherlv_5= '}' otherlv_6= '{' ( (lv_statement_7_0= ruleAbstractStatement ) ) otherlv_8= '}' otherlv_9= 'post:' otherlv_10= '{' ( (lv_postCondition_11_0= ruleCondition ) ) otherlv_12= '}' ) ;
	public final EObject ruleCbCFormula() throws RecognitionException {
		EObject current = null;


		Token otherlv_0=null;
		Token otherlv_2=null;
		Token otherlv_3=null;
		Token otherlv_5=null;
		Token otherlv_6=null;
		Token otherlv_8=null;
		Token otherlv_9=null;
		Token otherlv_10=null;
		Token otherlv_12=null;
		AntlrDatatypeRuleToken lv_name_1_0 =null;
		EObject lv_preCondition_4_0 =null;
		EObject lv_statement_7_0 =null;
		EObject lv_postCondition_11_0 =null;


			enterRule();

		try {
			// InternalDsl.g:221:2: ( (otherlv_0= 'Formula' ( (lv_name_1_0= ruleEString ) ) otherlv_2= 'pre:' otherlv_3= '{' ( (lv_preCondition_4_0= ruleCondition ) ) otherlv_5= '}' otherlv_6= '{' ( (lv_statement_7_0= ruleAbstractStatement ) ) otherlv_8= '}' otherlv_9= 'post:' otherlv_10= '{' ( (lv_postCondition_11_0= ruleCondition ) ) otherlv_12= '}' ) )
			// InternalDsl.g:222:2: (otherlv_0= 'Formula' ( (lv_name_1_0= ruleEString ) ) otherlv_2= 'pre:' otherlv_3= '{' ( (lv_preCondition_4_0= ruleCondition ) ) otherlv_5= '}' otherlv_6= '{' ( (lv_statement_7_0= ruleAbstractStatement ) ) otherlv_8= '}' otherlv_9= 'post:' otherlv_10= '{' ( (lv_postCondition_11_0= ruleCondition ) ) otherlv_12= '}' )
			{
			// InternalDsl.g:222:2: (otherlv_0= 'Formula' ( (lv_name_1_0= ruleEString ) ) otherlv_2= 'pre:' otherlv_3= '{' ( (lv_preCondition_4_0= ruleCondition ) ) otherlv_5= '}' otherlv_6= '{' ( (lv_statement_7_0= ruleAbstractStatement ) ) otherlv_8= '}' otherlv_9= 'post:' otherlv_10= '{' ( (lv_postCondition_11_0= ruleCondition ) ) otherlv_12= '}' )
			// InternalDsl.g:223:3: otherlv_0= 'Formula' ( (lv_name_1_0= ruleEString ) ) otherlv_2= 'pre:' otherlv_3= '{' ( (lv_preCondition_4_0= ruleCondition ) ) otherlv_5= '}' otherlv_6= '{' ( (lv_statement_7_0= ruleAbstractStatement ) ) otherlv_8= '}' otherlv_9= 'post:' otherlv_10= '{' ( (lv_postCondition_11_0= ruleCondition ) ) otherlv_12= '}'
			{
			otherlv_0=(Token)match(input,22,FOLLOW_4); 

						newLeafNode(otherlv_0, grammarAccess.getCbCFormulaAccess().getFormulaKeyword_0());
					
			// InternalDsl.g:227:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:228:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:228:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:229:5: lv_name_1_0= ruleEString
			{

								newCompositeNode(grammarAccess.getCbCFormulaAccess().getNameEStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_5);
			lv_name_1_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCbCFormulaRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_2=(Token)match(input,39,FOLLOW_6); 

						newLeafNode(otherlv_2, grammarAccess.getCbCFormulaAccess().getPreKeyword_2());
					
			otherlv_3=(Token)match(input,46,FOLLOW_4); 

						newLeafNode(otherlv_3, grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_3());
					
			// InternalDsl.g:254:3: ( (lv_preCondition_4_0= ruleCondition ) )
			// InternalDsl.g:255:4: (lv_preCondition_4_0= ruleCondition )
			{
			// InternalDsl.g:255:4: (lv_preCondition_4_0= ruleCondition )
			// InternalDsl.g:256:5: lv_preCondition_4_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getCbCFormulaAccess().getPreConditionConditionParserRuleCall_4_0());
							
			pushFollow(FOLLOW_7);
			lv_preCondition_4_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCbCFormulaRule());
								}
								set(
									current,
									"preCondition",
									lv_preCondition_4_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_5=(Token)match(input,47,FOLLOW_6); 

						newLeafNode(otherlv_5, grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_5());
					
			otherlv_6=(Token)match(input,46,FOLLOW_8); 

						newLeafNode(otherlv_6, grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_6());
					
			// InternalDsl.g:281:3: ( (lv_statement_7_0= ruleAbstractStatement ) )
			// InternalDsl.g:282:4: (lv_statement_7_0= ruleAbstractStatement )
			{
			// InternalDsl.g:282:4: (lv_statement_7_0= ruleAbstractStatement )
			// InternalDsl.g:283:5: lv_statement_7_0= ruleAbstractStatement
			{

								newCompositeNode(grammarAccess.getCbCFormulaAccess().getStatementAbstractStatementParserRuleCall_7_0());
							
			pushFollow(FOLLOW_7);
			lv_statement_7_0=ruleAbstractStatement();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCbCFormulaRule());
								}
								set(
									current,
									"statement",
									lv_statement_7_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_8=(Token)match(input,47,FOLLOW_9); 

						newLeafNode(otherlv_8, grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_8());
					
			otherlv_9=(Token)match(input,38,FOLLOW_6); 

						newLeafNode(otherlv_9, grammarAccess.getCbCFormulaAccess().getPostKeyword_9());
					
			otherlv_10=(Token)match(input,46,FOLLOW_4); 

						newLeafNode(otherlv_10, grammarAccess.getCbCFormulaAccess().getLeftCurlyBracketKeyword_10());
					
			// InternalDsl.g:312:3: ( (lv_postCondition_11_0= ruleCondition ) )
			// InternalDsl.g:313:4: (lv_postCondition_11_0= ruleCondition )
			{
			// InternalDsl.g:313:4: (lv_postCondition_11_0= ruleCondition )
			// InternalDsl.g:314:5: lv_postCondition_11_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getCbCFormulaAccess().getPostConditionConditionParserRuleCall_11_0());
							
			pushFollow(FOLLOW_7);
			lv_postCondition_11_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCbCFormulaRule());
								}
								set(
									current,
									"postCondition",
									lv_postCondition_11_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_12=(Token)match(input,47,FOLLOW_2); 

						newLeafNode(otherlv_12, grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_12());
					
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleCbCFormula"



	// $ANTLR start "entryRuleAbstractStatement"
	// InternalDsl.g:339:1: entryRuleAbstractStatement returns [EObject current=null] :iv_ruleAbstractStatement= ruleAbstractStatement EOF ;
	public final EObject entryRuleAbstractStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleAbstractStatement =null;

		try {
			// InternalDsl.g:339:58: (iv_ruleAbstractStatement= ruleAbstractStatement EOF )
			// InternalDsl.g:340:2: iv_ruleAbstractStatement= ruleAbstractStatement EOF
			{
			 newCompositeNode(grammarAccess.getAbstractStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleAbstractStatement=ruleAbstractStatement();
			state._fsp--;

			 current =iv_ruleAbstractStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleAbstractStatement"



	// $ANTLR start "ruleAbstractStatement"
	// InternalDsl.g:346:1: ruleAbstractStatement returns [EObject current=null] : (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement ) ;
	public final EObject ruleAbstractStatement() throws RecognitionException {
		EObject current = null;


		EObject this_AbstractStatement_Impl_0 =null;
		EObject this_SkipStatement_1 =null;
		EObject this_CompositionStatement_2 =null;
		EObject this_SelectionStatement_3 =null;
		EObject this_SmallRepetitionStatement_4 =null;
		EObject this_MethodStatement_5 =null;


			enterRule();

		try {
			// InternalDsl.g:352:2: ( (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement ) )
			// InternalDsl.g:353:2: (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement )
			{
			// InternalDsl.g:353:2: (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement )
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
					// InternalDsl.g:354:3: this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getAbstractStatement_ImplParserRuleCall_0());
							
					pushFollow(FOLLOW_2);
					this_AbstractStatement_Impl_0=ruleAbstractStatement_Impl();
					state._fsp--;


								current = this_AbstractStatement_Impl_0;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 2 :
					// InternalDsl.g:363:3: this_SkipStatement_1= ruleSkipStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getSkipStatementParserRuleCall_1());
							
					pushFollow(FOLLOW_2);
					this_SkipStatement_1=ruleSkipStatement();
					state._fsp--;


								current = this_SkipStatement_1;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 3 :
					// InternalDsl.g:372:3: this_CompositionStatement_2= ruleCompositionStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getCompositionStatementParserRuleCall_2());
							
					pushFollow(FOLLOW_2);
					this_CompositionStatement_2=ruleCompositionStatement();
					state._fsp--;


								current = this_CompositionStatement_2;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 4 :
					// InternalDsl.g:381:3: this_SelectionStatement_3= ruleSelectionStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getSelectionStatementParserRuleCall_3());
							
					pushFollow(FOLLOW_2);
					this_SelectionStatement_3=ruleSelectionStatement();
					state._fsp--;


								current = this_SelectionStatement_3;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 5 :
					// InternalDsl.g:390:3: this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getSmallRepetitionStatementParserRuleCall_4());
							
					pushFollow(FOLLOW_2);
					this_SmallRepetitionStatement_4=ruleSmallRepetitionStatement();
					state._fsp--;


								current = this_SmallRepetitionStatement_4;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 6 :
					// InternalDsl.g:399:3: this_MethodStatement_5= ruleMethodStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getMethodStatementParserRuleCall_5());
							
					pushFollow(FOLLOW_2);
					this_MethodStatement_5=ruleMethodStatement();
					state._fsp--;


								current = this_MethodStatement_5;
								afterParserOrEnumRuleCall();
							
					}
					break;

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleAbstractStatement"



	// $ANTLR start "entryRuleEString"
	// InternalDsl.g:411:1: entryRuleEString returns [String current=null] :iv_ruleEString= ruleEString EOF ;
	public final String entryRuleEString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleEString =null;

		try {
			// InternalDsl.g:411:47: (iv_ruleEString= ruleEString EOF )
			// InternalDsl.g:412:2: iv_ruleEString= ruleEString EOF
			{
			 newCompositeNode(grammarAccess.getEStringRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleEString=ruleEString();
			state._fsp--;

			 current =iv_ruleEString.getText(); 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleEString"



	// $ANTLR start "ruleEString"
	// InternalDsl.g:418:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID ) ;
	public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token this_STRING_0=null;
		Token this_ID_1=null;


			enterRule();

		try {
			// InternalDsl.g:424:2: ( (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID ) )
			// InternalDsl.g:425:2: (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID )
			{
			// InternalDsl.g:425:2: (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID )
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
					// InternalDsl.g:426:3: this_STRING_0= RULE_STRING
					{
					this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

								current.merge(this_STRING_0);
							

								newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0());
							
					}
					break;
				case 2 :
					// InternalDsl.g:434:3: this_ID_1= RULE_ID
					{
					this_ID_1=(Token)match(input,RULE_ID,FOLLOW_2); 

								current.merge(this_ID_1);
							

								newLeafNode(this_ID_1, grammarAccess.getEStringAccess().getIDTerminalRuleCall_1());
							
					}
					break;

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleEString"



	// $ANTLR start "entryRuleCodeString"
	// InternalDsl.g:445:1: entryRuleCodeString returns [String current=null] :iv_ruleCodeString= ruleCodeString EOF ;
	public final String entryRuleCodeString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleCodeString =null;

		try {
			// InternalDsl.g:445:50: (iv_ruleCodeString= ruleCodeString EOF )
			// InternalDsl.g:446:2: iv_ruleCodeString= ruleCodeString EOF
			{
			 newCompositeNode(grammarAccess.getCodeStringRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleCodeString=ruleCodeString();
			state._fsp--;

			 current =iv_ruleCodeString.getText(); 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleCodeString"



	// $ANTLR start "ruleCodeString"
	// InternalDsl.g:452:1: ruleCodeString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) ) (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )* kw= ';' )+ ;
	public final AntlrDatatypeRuleToken ruleCodeString() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token this_ID_0=null;
		Token kw=null;
		Token this_ID_2=null;
		Token this_INT_3=null;
		Token this_INT_8=null;
		Token this_INT_12=null;
		AntlrDatatypeRuleToken this_VariableString_6 =null;
		AntlrDatatypeRuleToken this_Operation_9 =null;
		AntlrDatatypeRuleToken this_VariableString_10 =null;


			enterRule();

		try {
			// InternalDsl.g:458:2: ( (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) ) (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )* kw= ';' )+ )
			// InternalDsl.g:459:2: (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) ) (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )* kw= ';' )+
			{
			// InternalDsl.g:459:2: (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) ) (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )* kw= ';' )+
			int cnt11=0;
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==RULE_ID) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// InternalDsl.g:460:3: this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) ) (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )* kw= ';'
					{
					this_ID_0=(Token)match(input,RULE_ID,FOLLOW_10); 

								current.merge(this_ID_0);
							

								newLeafNode(this_ID_0, grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_0());
							
					// InternalDsl.g:467:3: (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==26) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// InternalDsl.g:468:4: kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']'
							{
							kw=(Token)match(input,26,FOLLOW_11); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getCodeStringAccess().getLeftSquareBracketKeyword_1_0());
										
							// InternalDsl.g:473:4: (this_ID_2= RULE_ID |this_INT_3= RULE_INT )
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
									// InternalDsl.g:474:5: this_ID_2= RULE_ID
									{
									this_ID_2=(Token)match(input,RULE_ID,FOLLOW_12); 

														current.merge(this_ID_2);
													

														newLeafNode(this_ID_2, grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_1_1_0());
													
									}
									break;
								case 2 :
									// InternalDsl.g:482:5: this_INT_3= RULE_INT
									{
									this_INT_3=(Token)match(input,RULE_INT,FOLLOW_12); 

														current.merge(this_INT_3);
													

														newLeafNode(this_INT_3, grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_1_1_1());
													
									}
									break;

							}

							kw=(Token)match(input,27,FOLLOW_13); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getCodeStringAccess().getRightSquareBracketKeyword_1_2());
										
							}
							break;

					}

					kw=(Token)match(input,21,FOLLOW_14); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getCodeStringAccess().getEqualsSignKeyword_2());
							
					// InternalDsl.g:501:3: (this_VariableString_6= ruleVariableString | ( (kw= '-' )? this_INT_8= RULE_INT ) )
					int alt7=2;
					switch ( input.LA(1) ) {
					case 17:
						{
						int LA7_1 = input.LA(2);
						if ( (LA7_1==RULE_ID) ) {
							alt7=1;
						}
						else if ( (LA7_1==RULE_INT) ) {
							alt7=2;
						}

						else {
							int nvaeMark = input.mark();
							try {
								input.consume();
								NoViableAltException nvae =
									new NoViableAltException("", 7, 1, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

						}
						break;
					case RULE_ID:
						{
						alt7=1;
						}
						break;
					case RULE_INT:
						{
						alt7=2;
						}
						break;
					default:
						NoViableAltException nvae =
							new NoViableAltException("", 7, 0, input);
						throw nvae;
					}
					switch (alt7) {
						case 1 :
							// InternalDsl.g:502:4: this_VariableString_6= ruleVariableString
							{

											newCompositeNode(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_3_0());
										
							pushFollow(FOLLOW_15);
							this_VariableString_6=ruleVariableString();
							state._fsp--;


											current.merge(this_VariableString_6);
										

											afterParserOrEnumRuleCall();
										
							}
							break;
						case 2 :
							// InternalDsl.g:513:4: ( (kw= '-' )? this_INT_8= RULE_INT )
							{
							// InternalDsl.g:513:4: ( (kw= '-' )? this_INT_8= RULE_INT )
							// InternalDsl.g:514:5: (kw= '-' )? this_INT_8= RULE_INT
							{
							// InternalDsl.g:514:5: (kw= '-' )?
							int alt6=2;
							int LA6_0 = input.LA(1);
							if ( (LA6_0==17) ) {
								alt6=1;
							}
							switch (alt6) {
								case 1 :
									// InternalDsl.g:515:6: kw= '-'
									{
									kw=(Token)match(input,17,FOLLOW_16); 

															current.merge(kw);
															newLeafNode(kw, grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_3_1_0());
														
									}
									break;

							}

							this_INT_8=(Token)match(input,RULE_INT,FOLLOW_15); 

												current.merge(this_INT_8);
											

												newLeafNode(this_INT_8, grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_3_1_1());
											
							}

							}
							break;

					}

					// InternalDsl.g:530:3: (this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) ) )*
					loop10:
					while (true) {
						int alt10=2;
						int LA10_0 = input.LA(1);
						if ( (LA10_0==11||(LA10_0 >= 14 && LA10_0 <= 15)||LA10_0==17||LA10_0==19) ) {
							alt10=1;
						}

						switch (alt10) {
						case 1 :
							// InternalDsl.g:531:4: this_Operation_9= ruleOperation (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) )
							{

											newCompositeNode(grammarAccess.getCodeStringAccess().getOperationParserRuleCall_4_0());
										
							pushFollow(FOLLOW_14);
							this_Operation_9=ruleOperation();
							state._fsp--;


											current.merge(this_Operation_9);
										

											afterParserOrEnumRuleCall();
										
							// InternalDsl.g:541:4: (this_VariableString_10= ruleVariableString | ( (kw= '-' )? this_INT_12= RULE_INT ) )
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
									// InternalDsl.g:542:5: this_VariableString_10= ruleVariableString
									{

														newCompositeNode(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_4_1_0());
													
									pushFollow(FOLLOW_15);
									this_VariableString_10=ruleVariableString();
									state._fsp--;


														current.merge(this_VariableString_10);
													

														afterParserOrEnumRuleCall();
													
									}
									break;
								case 2 :
									// InternalDsl.g:553:5: ( (kw= '-' )? this_INT_12= RULE_INT )
									{
									// InternalDsl.g:553:5: ( (kw= '-' )? this_INT_12= RULE_INT )
									// InternalDsl.g:554:6: (kw= '-' )? this_INT_12= RULE_INT
									{
									// InternalDsl.g:554:6: (kw= '-' )?
									int alt8=2;
									int LA8_0 = input.LA(1);
									if ( (LA8_0==17) ) {
										alt8=1;
									}
									switch (alt8) {
										case 1 :
											// InternalDsl.g:555:7: kw= '-'
											{
											kw=(Token)match(input,17,FOLLOW_16); 

																		current.merge(kw);
																		newLeafNode(kw, grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_4_1_1_0());
																	
											}
											break;

									}

									this_INT_12=(Token)match(input,RULE_INT,FOLLOW_15); 

															current.merge(this_INT_12);
														

															newLeafNode(this_INT_12, grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_4_1_1_1());
														
									}

									}
									break;

							}

							}
							break;

						default :
							break loop10;
						}
					}

					kw=(Token)match(input,20,FOLLOW_17); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getCodeStringAccess().getSemicolonKeyword_5());
							
					}
					break;

				default :
					if ( cnt11 >= 1 ) break loop11;
					EarlyExitException eee = new EarlyExitException(11, input);
					throw eee;
				}
				cnt11++;
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleCodeString"



	// $ANTLR start "entryRuleVariableString"
	// InternalDsl.g:580:1: entryRuleVariableString returns [String current=null] :iv_ruleVariableString= ruleVariableString EOF ;
	public final String entryRuleVariableString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleVariableString =null;

		try {
			// InternalDsl.g:580:54: (iv_ruleVariableString= ruleVariableString EOF )
			// InternalDsl.g:581:2: iv_ruleVariableString= ruleVariableString EOF
			{
			 newCompositeNode(grammarAccess.getVariableStringRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleVariableString=ruleVariableString();
			state._fsp--;

			 current =iv_ruleVariableString.getText(); 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleVariableString"



	// $ANTLR start "ruleVariableString"
	// InternalDsl.g:587:1: ruleVariableString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? ) ;
	public final AntlrDatatypeRuleToken ruleVariableString() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token kw=null;
		Token this_ID_1=null;
		Token this_INT_5=null;
		Token this_INT_9=null;
		Token this_INT_16=null;
		AntlrDatatypeRuleToken this_VariableString_3 =null;
		AntlrDatatypeRuleToken this_VariableString_7 =null;
		AntlrDatatypeRuleToken this_VariableString_12 =null;
		AntlrDatatypeRuleToken this_VariableString_14 =null;
		AntlrDatatypeRuleToken this_VariableString_19 =null;
		AntlrDatatypeRuleToken this_VariableString_21 =null;


			enterRule();

		try {
			// InternalDsl.g:593:2: ( ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? ) )
			// InternalDsl.g:594:2: ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? )
			{
			// InternalDsl.g:594:2: ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? )
			// InternalDsl.g:595:3: (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )?
			{
			// InternalDsl.g:595:3: (kw= '-' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==17) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// InternalDsl.g:596:4: kw= '-'
					{
					kw=(Token)match(input,17,FOLLOW_18); 

									current.merge(kw);
									newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_0());
								
					}
					break;

			}

			this_ID_1=(Token)match(input,RULE_ID,FOLLOW_19); 

						current.merge(this_ID_1);
					

						newLeafNode(this_ID_1, grammarAccess.getVariableStringAccess().getIDTerminalRuleCall_1());
					
			// InternalDsl.g:609:3: ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )?
			int alt23=4;
			switch ( input.LA(1) ) {
				case 12:
					{
					alt23=1;
					}
					break;
				case 26:
					{
					alt23=2;
					}
					break;
				case 18:
					{
					alt23=3;
					}
					break;
			}
			switch (alt23) {
				case 1 :
					// InternalDsl.g:610:4: ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? )
					{
					// InternalDsl.g:610:4: ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? )
					// InternalDsl.g:611:5: (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )?
					{
					// InternalDsl.g:611:5: (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' )
					// InternalDsl.g:612:6: kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')'
					{
					kw=(Token)match(input,12,FOLLOW_20); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getLeftParenthesisKeyword_2_0_0_0());
										
					// InternalDsl.g:617:6: ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )?
					int alt18=2;
					int LA18_0 = input.LA(1);
					if ( ((LA18_0 >= RULE_ID && LA18_0 <= RULE_INT)||LA18_0==17) ) {
						alt18=1;
					}
					switch (alt18) {
						case 1 :
							// InternalDsl.g:618:7: (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )*
							{
							// InternalDsl.g:618:7: (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) )
							int alt14=2;
							switch ( input.LA(1) ) {
							case 17:
								{
								int LA14_1 = input.LA(2);
								if ( (LA14_1==RULE_ID) ) {
									alt14=1;
								}
								else if ( (LA14_1==RULE_INT) ) {
									alt14=2;
								}

								else {
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 14, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

								}
								break;
							case RULE_ID:
								{
								alt14=1;
								}
								break;
							case RULE_INT:
								{
								alt14=2;
								}
								break;
							default:
								NoViableAltException nvae =
									new NoViableAltException("", 14, 0, input);
								throw nvae;
							}
							switch (alt14) {
								case 1 :
									// InternalDsl.g:619:8: this_VariableString_3= ruleVariableString
									{

																	newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_0_0());
																
									pushFollow(FOLLOW_21);
									this_VariableString_3=ruleVariableString();
									state._fsp--;


																	current.merge(this_VariableString_3);
																

																	afterParserOrEnumRuleCall();
																
									}
									break;
								case 2 :
									// InternalDsl.g:630:8: ( (kw= '-' )? this_INT_5= RULE_INT )
									{
									// InternalDsl.g:630:8: ( (kw= '-' )? this_INT_5= RULE_INT )
									// InternalDsl.g:631:9: (kw= '-' )? this_INT_5= RULE_INT
									{
									// InternalDsl.g:631:9: (kw= '-' )?
									int alt13=2;
									int LA13_0 = input.LA(1);
									if ( (LA13_0==17) ) {
										alt13=1;
									}
									switch (alt13) {
										case 1 :
											// InternalDsl.g:632:10: kw= '-'
											{
											kw=(Token)match(input,17,FOLLOW_16); 

																					current.merge(kw);
																					newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_0_1_0());
																				
											}
											break;

									}

									this_INT_5=(Token)match(input,RULE_INT,FOLLOW_21); 

																		current.merge(this_INT_5);
																	

																		newLeafNode(this_INT_5, grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_0_1_1());
																	
									}

									}
									break;

							}

							// InternalDsl.g:647:7: (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )*
							loop17:
							while (true) {
								int alt17=2;
								int LA17_0 = input.LA(1);
								if ( (LA17_0==16) ) {
									alt17=1;
								}

								switch (alt17) {
								case 1 :
									// InternalDsl.g:648:8: kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) )
									{
									kw=(Token)match(input,16,FOLLOW_14); 

																	current.merge(kw);
																	newLeafNode(kw, grammarAccess.getVariableStringAccess().getCommaKeyword_2_0_0_1_1_0());
																
									// InternalDsl.g:653:8: (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) )
									int alt16=2;
									switch ( input.LA(1) ) {
									case 17:
										{
										int LA16_1 = input.LA(2);
										if ( (LA16_1==RULE_ID) ) {
											alt16=1;
										}
										else if ( (LA16_1==RULE_INT) ) {
											alt16=2;
										}

										else {
											int nvaeMark = input.mark();
											try {
												input.consume();
												NoViableAltException nvae =
													new NoViableAltException("", 16, 1, input);
												throw nvae;
											} finally {
												input.rewind(nvaeMark);
											}
										}

										}
										break;
									case RULE_ID:
										{
										alt16=1;
										}
										break;
									case RULE_INT:
										{
										alt16=2;
										}
										break;
									default:
										NoViableAltException nvae =
											new NoViableAltException("", 16, 0, input);
										throw nvae;
									}
									switch (alt16) {
										case 1 :
											// InternalDsl.g:654:9: this_VariableString_7= ruleVariableString
											{

																				newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_1_1_0());
																			
											pushFollow(FOLLOW_21);
											this_VariableString_7=ruleVariableString();
											state._fsp--;


																				current.merge(this_VariableString_7);
																			

																				afterParserOrEnumRuleCall();
																			
											}
											break;
										case 2 :
											// InternalDsl.g:665:9: ( (kw= '-' )? this_INT_9= RULE_INT )
											{
											// InternalDsl.g:665:9: ( (kw= '-' )? this_INT_9= RULE_INT )
											// InternalDsl.g:666:10: (kw= '-' )? this_INT_9= RULE_INT
											{
											// InternalDsl.g:666:10: (kw= '-' )?
											int alt15=2;
											int LA15_0 = input.LA(1);
											if ( (LA15_0==17) ) {
												alt15=1;
											}
											switch (alt15) {
												case 1 :
													// InternalDsl.g:667:11: kw= '-'
													{
													kw=(Token)match(input,17,FOLLOW_16); 

																								current.merge(kw);
																								newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_1_1_1_0());
																							
													}
													break;

											}

											this_INT_9=(Token)match(input,RULE_INT,FOLLOW_21); 

																					current.merge(this_INT_9);
																				

																					newLeafNode(this_INT_9, grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_1_1_1_1());
																				
											}

											}
											break;

									}

									}
									break;

								default :
									break loop17;
								}
							}

							}
							break;

					}

					kw=(Token)match(input,13,FOLLOW_22); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getRightParenthesisKeyword_2_0_0_2());
										
					}

					// InternalDsl.g:690:5: (kw= '.' this_VariableString_12= ruleVariableString )?
					int alt19=2;
					int LA19_0 = input.LA(1);
					if ( (LA19_0==18) ) {
						alt19=1;
					}
					switch (alt19) {
						case 1 :
							// InternalDsl.g:691:6: kw= '.' this_VariableString_12= ruleVariableString
							{
							kw=(Token)match(input,18,FOLLOW_23); 

													current.merge(kw);
													newLeafNode(kw, grammarAccess.getVariableStringAccess().getFullStopKeyword_2_0_1_0());
												

													newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_1_1());
												
							pushFollow(FOLLOW_2);
							this_VariableString_12=ruleVariableString();
							state._fsp--;


													current.merge(this_VariableString_12);
												

													afterParserOrEnumRuleCall();
												
							}
							break;

					}

					}

					}
					break;
				case 2 :
					// InternalDsl.g:709:4: ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? )
					{
					// InternalDsl.g:709:4: ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? )
					// InternalDsl.g:710:5: (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )?
					{
					// InternalDsl.g:710:5: (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' )
					// InternalDsl.g:711:6: kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']'
					{
					kw=(Token)match(input,26,FOLLOW_24); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getLeftSquareBracketKeyword_2_1_0_0());
										
					// InternalDsl.g:716:6: (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )?
					int alt21=3;
					switch ( input.LA(1) ) {
						case 17:
							{
							int LA21_1 = input.LA(2);
							if ( (LA21_1==RULE_ID) ) {
								alt21=1;
							}
							else if ( (LA21_1==RULE_INT) ) {
								alt21=2;
							}
							}
							break;
						case RULE_ID:
							{
							alt21=1;
							}
							break;
						case RULE_INT:
							{
							alt21=2;
							}
							break;
					}
					switch (alt21) {
						case 1 :
							// InternalDsl.g:717:7: this_VariableString_14= ruleVariableString
							{

														newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_0_1_0());
													
							pushFollow(FOLLOW_12);
							this_VariableString_14=ruleVariableString();
							state._fsp--;


														current.merge(this_VariableString_14);
													

														afterParserOrEnumRuleCall();
													
							}
							break;
						case 2 :
							// InternalDsl.g:728:7: ( (kw= '-' )? this_INT_16= RULE_INT )
							{
							// InternalDsl.g:728:7: ( (kw= '-' )? this_INT_16= RULE_INT )
							// InternalDsl.g:729:8: (kw= '-' )? this_INT_16= RULE_INT
							{
							// InternalDsl.g:729:8: (kw= '-' )?
							int alt20=2;
							int LA20_0 = input.LA(1);
							if ( (LA20_0==17) ) {
								alt20=1;
							}
							switch (alt20) {
								case 1 :
									// InternalDsl.g:730:9: kw= '-'
									{
									kw=(Token)match(input,17,FOLLOW_16); 

																		current.merge(kw);
																		newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_1_0_1_1_0());
																	
									}
									break;

							}

							this_INT_16=(Token)match(input,RULE_INT,FOLLOW_12); 

															current.merge(this_INT_16);
														

															newLeafNode(this_INT_16, grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_1_0_1_1_1());
														
							}

							}
							break;

					}

					kw=(Token)match(input,27,FOLLOW_22); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getRightSquareBracketKeyword_2_1_0_2());
										
					}

					// InternalDsl.g:751:5: (kw= '.' this_VariableString_19= ruleVariableString )?
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==18) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// InternalDsl.g:752:6: kw= '.' this_VariableString_19= ruleVariableString
							{
							kw=(Token)match(input,18,FOLLOW_23); 

													current.merge(kw);
													newLeafNode(kw, grammarAccess.getVariableStringAccess().getFullStopKeyword_2_1_1_0());
												

													newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_1_1_1());
												
							pushFollow(FOLLOW_2);
							this_VariableString_19=ruleVariableString();
							state._fsp--;


													current.merge(this_VariableString_19);
												

													afterParserOrEnumRuleCall();
												
							}
							break;

					}

					}

					}
					break;
				case 3 :
					// InternalDsl.g:770:4: (kw= '.' this_VariableString_21= ruleVariableString )
					{
					// InternalDsl.g:770:4: (kw= '.' this_VariableString_21= ruleVariableString )
					// InternalDsl.g:771:5: kw= '.' this_VariableString_21= ruleVariableString
					{
					kw=(Token)match(input,18,FOLLOW_23); 

										current.merge(kw);
										newLeafNode(kw, grammarAccess.getVariableStringAccess().getFullStopKeyword_2_2_0());
									

										newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_2_1());
									
					pushFollow(FOLLOW_2);
					this_VariableString_21=ruleVariableString();
					state._fsp--;


										current.merge(this_VariableString_21);
									

										afterParserOrEnumRuleCall();
									
					}

					}
					break;

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleVariableString"



	// $ANTLR start "entryRuleOperation"
	// InternalDsl.g:792:1: entryRuleOperation returns [String current=null] :iv_ruleOperation= ruleOperation EOF ;
	public final String entryRuleOperation() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleOperation =null;

		try {
			// InternalDsl.g:792:49: (iv_ruleOperation= ruleOperation EOF )
			// InternalDsl.g:793:2: iv_ruleOperation= ruleOperation EOF
			{
			 newCompositeNode(grammarAccess.getOperationRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleOperation=ruleOperation();
			state._fsp--;

			 current =iv_ruleOperation.getText(); 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleOperation"



	// $ANTLR start "ruleOperation"
	// InternalDsl.g:799:1: ruleOperation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' ) ;
	public final AntlrDatatypeRuleToken ruleOperation() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token kw=null;


			enterRule();

		try {
			// InternalDsl.g:805:2: ( (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' ) )
			// InternalDsl.g:806:2: (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' )
			{
			// InternalDsl.g:806:2: (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' )
			int alt24=5;
			switch ( input.LA(1) ) {
			case 15:
				{
				alt24=1;
				}
				break;
			case 17:
				{
				alt24=2;
				}
				break;
			case 14:
				{
				alt24=3;
				}
				break;
			case 19:
				{
				alt24=4;
				}
				break;
			case 11:
				{
				alt24=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 24, 0, input);
				throw nvae;
			}
			switch (alt24) {
				case 1 :
					// InternalDsl.g:807:3: kw= '+'
					{
					kw=(Token)match(input,15,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getPlusSignKeyword_0());
							
					}
					break;
				case 2 :
					// InternalDsl.g:813:3: kw= '-'
					{
					kw=(Token)match(input,17,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getHyphenMinusKeyword_1());
							
					}
					break;
				case 3 :
					// InternalDsl.g:819:3: kw= '*'
					{
					kw=(Token)match(input,14,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getAsteriskKeyword_2());
							
					}
					break;
				case 4 :
					// InternalDsl.g:825:3: kw= '/'
					{
					kw=(Token)match(input,19,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getSolidusKeyword_3());
							
					}
					break;
				case 5 :
					// InternalDsl.g:831:3: kw= '%'
					{
					kw=(Token)match(input,11,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getPercentSignKeyword_4());
							
					}
					break;

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleOperation"



	// $ANTLR start "entryRuleAbstractStatement_Impl"
	// InternalDsl.g:840:1: entryRuleAbstractStatement_Impl returns [EObject current=null] :iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF ;
	public final EObject entryRuleAbstractStatement_Impl() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleAbstractStatement_Impl =null;

		try {
			// InternalDsl.g:840:63: (iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF )
			// InternalDsl.g:841:2: iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF
			{
			 newCompositeNode(grammarAccess.getAbstractStatement_ImplRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleAbstractStatement_Impl=ruleAbstractStatement_Impl();
			state._fsp--;

			 current =iv_ruleAbstractStatement_Impl; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleAbstractStatement_Impl"



	// $ANTLR start "ruleAbstractStatement_Impl"
	// InternalDsl.g:847:1: ruleAbstractStatement_Impl returns [EObject current=null] : ( () ( (lv_name_1_0= ruleCodeString ) ) ) ;
	public final EObject ruleAbstractStatement_Impl() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:853:2: ( ( () ( (lv_name_1_0= ruleCodeString ) ) ) )
			// InternalDsl.g:854:2: ( () ( (lv_name_1_0= ruleCodeString ) ) )
			{
			// InternalDsl.g:854:2: ( () ( (lv_name_1_0= ruleCodeString ) ) )
			// InternalDsl.g:855:3: () ( (lv_name_1_0= ruleCodeString ) )
			{
			// InternalDsl.g:855:3: ()
			// InternalDsl.g:856:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getAbstractStatement_ImplAccess().getAbstractStatementAction_0(),
								current);
						
			}

			// InternalDsl.g:862:3: ( (lv_name_1_0= ruleCodeString ) )
			// InternalDsl.g:863:4: (lv_name_1_0= ruleCodeString )
			{
			// InternalDsl.g:863:4: (lv_name_1_0= ruleCodeString )
			// InternalDsl.g:864:5: lv_name_1_0= ruleCodeString
			{

								newCompositeNode(grammarAccess.getAbstractStatement_ImplAccess().getNameCodeStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_2);
			lv_name_1_0=ruleCodeString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getAbstractStatement_ImplRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.CodeString");
								afterParserOrEnumRuleCall();
							
			}

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleAbstractStatement_Impl"



	// $ANTLR start "entryRuleMethodStatement"
	// InternalDsl.g:885:1: entryRuleMethodStatement returns [EObject current=null] :iv_ruleMethodStatement= ruleMethodStatement EOF ;
	public final EObject entryRuleMethodStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleMethodStatement =null;

		try {
			// InternalDsl.g:885:56: (iv_ruleMethodStatement= ruleMethodStatement EOF )
			// InternalDsl.g:886:2: iv_ruleMethodStatement= ruleMethodStatement EOF
			{
			 newCompositeNode(grammarAccess.getMethodStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleMethodStatement=ruleMethodStatement();
			state._fsp--;

			 current =iv_ruleMethodStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleMethodStatement"



	// $ANTLR start "ruleMethodStatement"
	// InternalDsl.g:892:1: ruleMethodStatement returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleMethodStatement() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:898:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:899:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:899:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:900:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:900:3: ()
			// InternalDsl.g:901:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getMethodStatementAccess().getMethodStatementAction_0(),
								current);
						
			}

			// InternalDsl.g:907:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:908:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:908:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:909:5: lv_name_1_0= ruleEString
			{

								newCompositeNode(grammarAccess.getMethodStatementAccess().getNameEStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_2);
			lv_name_1_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getMethodStatementRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleMethodStatement"



	// $ANTLR start "entryRuleCondition"
	// InternalDsl.g:930:1: entryRuleCondition returns [EObject current=null] :iv_ruleCondition= ruleCondition EOF ;
	public final EObject entryRuleCondition() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCondition =null;

		try {
			// InternalDsl.g:930:50: (iv_ruleCondition= ruleCondition EOF )
			// InternalDsl.g:931:2: iv_ruleCondition= ruleCondition EOF
			{
			 newCompositeNode(grammarAccess.getConditionRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleCondition=ruleCondition();
			state._fsp--;

			 current =iv_ruleCondition; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleCondition"



	// $ANTLR start "ruleCondition"
	// InternalDsl.g:937:1: ruleCondition returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleCondition() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:943:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:944:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:944:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:945:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:945:3: ()
			// InternalDsl.g:946:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getConditionAccess().getConditionAction_0(),
								current);
						
			}

			// InternalDsl.g:952:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:953:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:953:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:954:5: lv_name_1_0= ruleEString
			{

								newCompositeNode(grammarAccess.getConditionAccess().getNameEStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_2);
			lv_name_1_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getConditionRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleCondition"



	// $ANTLR start "entryRuleSkipStatement"
	// InternalDsl.g:975:1: entryRuleSkipStatement returns [EObject current=null] :iv_ruleSkipStatement= ruleSkipStatement EOF ;
	public final EObject entryRuleSkipStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSkipStatement =null;

		try {
			// InternalDsl.g:975:54: (iv_ruleSkipStatement= ruleSkipStatement EOF )
			// InternalDsl.g:976:2: iv_ruleSkipStatement= ruleSkipStatement EOF
			{
			 newCompositeNode(grammarAccess.getSkipStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleSkipStatement=ruleSkipStatement();
			state._fsp--;

			 current =iv_ruleSkipStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleSkipStatement"



	// $ANTLR start "ruleSkipStatement"
	// InternalDsl.g:982:1: ruleSkipStatement returns [EObject current=null] : ( (lv_name_0_0= ';' ) ) ;
	public final EObject ruleSkipStatement() throws RecognitionException {
		EObject current = null;


		Token lv_name_0_0=null;


			enterRule();

		try {
			// InternalDsl.g:988:2: ( ( (lv_name_0_0= ';' ) ) )
			// InternalDsl.g:989:2: ( (lv_name_0_0= ';' ) )
			{
			// InternalDsl.g:989:2: ( (lv_name_0_0= ';' ) )
			// InternalDsl.g:990:3: (lv_name_0_0= ';' )
			{
			// InternalDsl.g:990:3: (lv_name_0_0= ';' )
			// InternalDsl.g:991:4: lv_name_0_0= ';'
			{
			lv_name_0_0=(Token)match(input,20,FOLLOW_2); 

							newLeafNode(lv_name_0_0, grammarAccess.getSkipStatementAccess().getNameSemicolonKeyword_0());
						

							if (current==null) {
								current = createModelElement(grammarAccess.getSkipStatementRule());
							}
							setWithLastConsumed(current, "name", lv_name_0_0, ";");
						
			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleSkipStatement"



	// $ANTLR start "entryRuleCompositionStatement"
	// InternalDsl.g:1006:1: entryRuleCompositionStatement returns [EObject current=null] :iv_ruleCompositionStatement= ruleCompositionStatement EOF ;
	public final EObject entryRuleCompositionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCompositionStatement =null;

		try {
			// InternalDsl.g:1006:61: (iv_ruleCompositionStatement= ruleCompositionStatement EOF )
			// InternalDsl.g:1007:2: iv_ruleCompositionStatement= ruleCompositionStatement EOF
			{
			 newCompositeNode(grammarAccess.getCompositionStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleCompositionStatement=ruleCompositionStatement();
			state._fsp--;

			 current =iv_ruleCompositionStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleCompositionStatement"



	// $ANTLR start "ruleCompositionStatement"
	// InternalDsl.g:1013:1: ruleCompositionStatement returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' ) ;
	public final EObject ruleCompositionStatement() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_3=null;
		Token otherlv_4=null;
		Token otherlv_5=null;
		Token otherlv_7=null;
		Token otherlv_8=null;
		Token otherlv_10=null;
		EObject lv_firstStatement_2_0 =null;
		EObject lv_intermediateCondition_6_0 =null;
		EObject lv_secondStatement_9_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1019:2: ( ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' ) )
			// InternalDsl.g:1020:2: ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' )
			{
			// InternalDsl.g:1020:2: ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' )
			// InternalDsl.g:1021:3: () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}'
			{
			// InternalDsl.g:1021:3: ()
			// InternalDsl.g:1022:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getCompositionStatementAccess().getCompositionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,46,FOLLOW_8); 

						newLeafNode(otherlv_1, grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_1());
					
			// InternalDsl.g:1032:3: ( (lv_firstStatement_2_0= ruleAbstractStatement ) )
			// InternalDsl.g:1033:4: (lv_firstStatement_2_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1033:4: (lv_firstStatement_2_0= ruleAbstractStatement )
			// InternalDsl.g:1034:5: lv_firstStatement_2_0= ruleAbstractStatement
			{

								newCompositeNode(grammarAccess.getCompositionStatementAccess().getFirstStatementAbstractStatementParserRuleCall_2_0());
							
			pushFollow(FOLLOW_7);
			lv_firstStatement_2_0=ruleAbstractStatement();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCompositionStatementRule());
								}
								set(
									current,
									"firstStatement",
									lv_firstStatement_2_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_3=(Token)match(input,47,FOLLOW_25); 

						newLeafNode(otherlv_3, grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_3());
					
			otherlv_4=(Token)match(input,34,FOLLOW_26); 

						newLeafNode(otherlv_4, grammarAccess.getCompositionStatementAccess().getIntmKeyword_4());
					
			otherlv_5=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_5, grammarAccess.getCompositionStatementAccess().getLeftSquareBracketKeyword_5());
					
			// InternalDsl.g:1063:3: ( (lv_intermediateCondition_6_0= ruleCondition ) )
			// InternalDsl.g:1064:4: (lv_intermediateCondition_6_0= ruleCondition )
			{
			// InternalDsl.g:1064:4: (lv_intermediateCondition_6_0= ruleCondition )
			// InternalDsl.g:1065:5: lv_intermediateCondition_6_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getCompositionStatementAccess().getIntermediateConditionConditionParserRuleCall_6_0());
							
			pushFollow(FOLLOW_12);
			lv_intermediateCondition_6_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCompositionStatementRule());
								}
								set(
									current,
									"intermediateCondition",
									lv_intermediateCondition_6_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_7=(Token)match(input,27,FOLLOW_6); 

						newLeafNode(otherlv_7, grammarAccess.getCompositionStatementAccess().getRightSquareBracketKeyword_7());
					
			otherlv_8=(Token)match(input,46,FOLLOW_8); 

						newLeafNode(otherlv_8, grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_8());
					
			// InternalDsl.g:1090:3: ( (lv_secondStatement_9_0= ruleAbstractStatement ) )
			// InternalDsl.g:1091:4: (lv_secondStatement_9_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1091:4: (lv_secondStatement_9_0= ruleAbstractStatement )
			// InternalDsl.g:1092:5: lv_secondStatement_9_0= ruleAbstractStatement
			{

								newCompositeNode(grammarAccess.getCompositionStatementAccess().getSecondStatementAbstractStatementParserRuleCall_9_0());
							
			pushFollow(FOLLOW_7);
			lv_secondStatement_9_0=ruleAbstractStatement();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getCompositionStatementRule());
								}
								set(
									current,
									"secondStatement",
									lv_secondStatement_9_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_10=(Token)match(input,47,FOLLOW_2); 

						newLeafNode(otherlv_10, grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_10());
					
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleCompositionStatement"



	// $ANTLR start "entryRuleSelectionStatement"
	// InternalDsl.g:1117:1: entryRuleSelectionStatement returns [EObject current=null] :iv_ruleSelectionStatement= ruleSelectionStatement EOF ;
	public final EObject entryRuleSelectionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSelectionStatement =null;

		try {
			// InternalDsl.g:1117:59: (iv_ruleSelectionStatement= ruleSelectionStatement EOF )
			// InternalDsl.g:1118:2: iv_ruleSelectionStatement= ruleSelectionStatement EOF
			{
			 newCompositeNode(grammarAccess.getSelectionStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleSelectionStatement=ruleSelectionStatement();
			state._fsp--;

			 current =iv_ruleSelectionStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleSelectionStatement"



	// $ANTLR start "ruleSelectionStatement"
	// InternalDsl.g:1124:1: ruleSelectionStatement returns [EObject current=null] : ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' ) ;
	public final EObject ruleSelectionStatement() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_4=null;
		Token otherlv_5=null;
		Token otherlv_6=null;
		Token otherlv_8=null;
		Token otherlv_9=null;
		Token otherlv_10=null;
		Token otherlv_12=null;
		Token otherlv_13=null;
		Token otherlv_14=null;
		Token otherlv_16=null;
		Token otherlv_17=null;
		EObject lv_guards_3_0 =null;
		EObject lv_commands_7_0 =null;
		EObject lv_guards_11_0 =null;
		EObject lv_commands_15_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1130:2: ( ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' ) )
			// InternalDsl.g:1131:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' )
			{
			// InternalDsl.g:1131:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' )
			// InternalDsl.g:1132:3: () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi'
			{
			// InternalDsl.g:1132:3: ()
			// InternalDsl.g:1133:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getSelectionStatementAccess().getSelectionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,33,FOLLOW_27); 

						newLeafNode(otherlv_1, grammarAccess.getSelectionStatementAccess().getIfKeyword_1());
					
			otherlv_2=(Token)match(input,12,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_2());
					
			// InternalDsl.g:1147:3: ( (lv_guards_3_0= ruleCondition ) )
			// InternalDsl.g:1148:4: (lv_guards_3_0= ruleCondition )
			{
			// InternalDsl.g:1148:4: (lv_guards_3_0= ruleCondition )
			// InternalDsl.g:1149:5: lv_guards_3_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_3_0());
							
			pushFollow(FOLLOW_28);
			lv_guards_3_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSelectionStatementRule());
								}
								add(
									current,
									"guards",
									lv_guards_3_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_4=(Token)match(input,13,FOLLOW_29); 

						newLeafNode(otherlv_4, grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_4());
					
			otherlv_5=(Token)match(input,41,FOLLOW_6); 

						newLeafNode(otherlv_5, grammarAccess.getSelectionStatementAccess().getThenKeyword_5());
					
			otherlv_6=(Token)match(input,46,FOLLOW_8); 

						newLeafNode(otherlv_6, grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_6());
					
			// InternalDsl.g:1178:3: ( (lv_commands_7_0= ruleAbstractStatement ) )
			// InternalDsl.g:1179:4: (lv_commands_7_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1179:4: (lv_commands_7_0= ruleAbstractStatement )
			// InternalDsl.g:1180:5: lv_commands_7_0= ruleAbstractStatement
			{

								newCompositeNode(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_7_0());
							
			pushFollow(FOLLOW_7);
			lv_commands_7_0=ruleAbstractStatement();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSelectionStatementRule());
								}
								add(
									current,
									"commands",
									lv_commands_7_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_8=(Token)match(input,47,FOLLOW_30); 

						newLeafNode(otherlv_8, grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_8());
					
			// InternalDsl.g:1201:3: (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )*
			loop25:
			while (true) {
				int alt25=2;
				int LA25_0 = input.LA(1);
				if ( (LA25_0==30) ) {
					alt25=1;
				}

				switch (alt25) {
				case 1 :
					// InternalDsl.g:1202:4: otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}'
					{
					otherlv_9=(Token)match(input,30,FOLLOW_27); 

									newLeafNode(otherlv_9, grammarAccess.getSelectionStatementAccess().getElseifKeyword_9_0());
								
					otherlv_10=(Token)match(input,12,FOLLOW_4); 

									newLeafNode(otherlv_10, grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_9_1());
								
					// InternalDsl.g:1210:4: ( (lv_guards_11_0= ruleCondition ) )
					// InternalDsl.g:1211:5: (lv_guards_11_0= ruleCondition )
					{
					// InternalDsl.g:1211:5: (lv_guards_11_0= ruleCondition )
					// InternalDsl.g:1212:6: lv_guards_11_0= ruleCondition
					{

											newCompositeNode(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_9_2_0());
										
					pushFollow(FOLLOW_28);
					lv_guards_11_0=ruleCondition();
					state._fsp--;


											if (current==null) {
												current = createModelElementForParent(grammarAccess.getSelectionStatementRule());
											}
											add(
												current,
												"guards",
												lv_guards_11_0,
												"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
											afterParserOrEnumRuleCall();
										
					}

					}

					otherlv_12=(Token)match(input,13,FOLLOW_29); 

									newLeafNode(otherlv_12, grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_9_3());
								
					otherlv_13=(Token)match(input,41,FOLLOW_6); 

									newLeafNode(otherlv_13, grammarAccess.getSelectionStatementAccess().getThenKeyword_9_4());
								
					otherlv_14=(Token)match(input,46,FOLLOW_8); 

									newLeafNode(otherlv_14, grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_9_5());
								
					// InternalDsl.g:1241:4: ( (lv_commands_15_0= ruleAbstractStatement ) )
					// InternalDsl.g:1242:5: (lv_commands_15_0= ruleAbstractStatement )
					{
					// InternalDsl.g:1242:5: (lv_commands_15_0= ruleAbstractStatement )
					// InternalDsl.g:1243:6: lv_commands_15_0= ruleAbstractStatement
					{

											newCompositeNode(grammarAccess.getSelectionStatementAccess().getCommandsAbstractStatementParserRuleCall_9_6_0());
										
					pushFollow(FOLLOW_7);
					lv_commands_15_0=ruleAbstractStatement();
					state._fsp--;


											if (current==null) {
												current = createModelElementForParent(grammarAccess.getSelectionStatementRule());
											}
											add(
												current,
												"commands",
												lv_commands_15_0,
												"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
											afterParserOrEnumRuleCall();
										
					}

					}

					otherlv_16=(Token)match(input,47,FOLLOW_30); 

									newLeafNode(otherlv_16, grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_9_7());
								
					}
					break;

				default :
					break loop25;
				}
			}

			otherlv_17=(Token)match(input,31,FOLLOW_2); 

						newLeafNode(otherlv_17, grammarAccess.getSelectionStatementAccess().getFiKeyword_10());
					
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleSelectionStatement"



	// $ANTLR start "entryRuleSmallRepetitionStatement"
	// InternalDsl.g:1273:1: entryRuleSmallRepetitionStatement returns [EObject current=null] :iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF ;
	public final EObject entryRuleSmallRepetitionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSmallRepetitionStatement =null;

		try {
			// InternalDsl.g:1273:65: (iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF )
			// InternalDsl.g:1274:2: iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF
			{
			 newCompositeNode(grammarAccess.getSmallRepetitionStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleSmallRepetitionStatement=ruleSmallRepetitionStatement();
			state._fsp--;

			 current =iv_ruleSmallRepetitionStatement; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleSmallRepetitionStatement"



	// $ANTLR start "ruleSmallRepetitionStatement"
	// InternalDsl.g:1280:1: ruleSmallRepetitionStatement returns [EObject current=null] : ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' ) ;
	public final EObject ruleSmallRepetitionStatement() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_4=null;
		Token otherlv_5=null;
		Token otherlv_6=null;
		Token otherlv_7=null;
		Token otherlv_9=null;
		Token otherlv_10=null;
		Token otherlv_11=null;
		Token otherlv_13=null;
		Token otherlv_14=null;
		Token otherlv_16=null;
		Token otherlv_17=null;
		EObject lv_guard_3_0 =null;
		EObject lv_invariant_8_0 =null;
		EObject lv_variant_12_0 =null;
		EObject lv_loopStatement_15_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1286:2: ( ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' ) )
			// InternalDsl.g:1287:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' )
			{
			// InternalDsl.g:1287:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' )
			// InternalDsl.g:1288:3: () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od'
			{
			// InternalDsl.g:1288:3: ()
			// InternalDsl.g:1289:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getSmallRepetitionStatementAccess().getSmallRepetitionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,45,FOLLOW_27); 

						newLeafNode(otherlv_1, grammarAccess.getSmallRepetitionStatementAccess().getWhileKeyword_1());
					
			otherlv_2=(Token)match(input,12,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getSmallRepetitionStatementAccess().getLeftParenthesisKeyword_2());
					
			// InternalDsl.g:1303:3: ( (lv_guard_3_0= ruleCondition ) )
			// InternalDsl.g:1304:4: (lv_guard_3_0= ruleCondition )
			{
			// InternalDsl.g:1304:4: (lv_guard_3_0= ruleCondition )
			// InternalDsl.g:1305:5: lv_guard_3_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getSmallRepetitionStatementAccess().getGuardConditionParserRuleCall_3_0());
							
			pushFollow(FOLLOW_28);
			lv_guard_3_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSmallRepetitionStatementRule());
								}
								set(
									current,
									"guard",
									lv_guard_3_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_4=(Token)match(input,13,FOLLOW_31); 

						newLeafNode(otherlv_4, grammarAccess.getSmallRepetitionStatementAccess().getRightParenthesisKeyword_4());
					
			otherlv_5=(Token)match(input,29,FOLLOW_32); 

						newLeafNode(otherlv_5, grammarAccess.getSmallRepetitionStatementAccess().getDoKeyword_5());
					
			otherlv_6=(Token)match(input,35,FOLLOW_26); 

						newLeafNode(otherlv_6, grammarAccess.getSmallRepetitionStatementAccess().getInvKeyword_6());
					
			otherlv_7=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_7, grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_7());
					
			// InternalDsl.g:1338:3: ( (lv_invariant_8_0= ruleCondition ) )
			// InternalDsl.g:1339:4: (lv_invariant_8_0= ruleCondition )
			{
			// InternalDsl.g:1339:4: (lv_invariant_8_0= ruleCondition )
			// InternalDsl.g:1340:5: lv_invariant_8_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getSmallRepetitionStatementAccess().getInvariantConditionParserRuleCall_8_0());
							
			pushFollow(FOLLOW_12);
			lv_invariant_8_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSmallRepetitionStatementRule());
								}
								set(
									current,
									"invariant",
									lv_invariant_8_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_9=(Token)match(input,27,FOLLOW_33); 

						newLeafNode(otherlv_9, grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_9());
					
			otherlv_10=(Token)match(input,43,FOLLOW_26); 

						newLeafNode(otherlv_10, grammarAccess.getSmallRepetitionStatementAccess().getVarKeyword_10());
					
			otherlv_11=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_11, grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_11());
					
			// InternalDsl.g:1369:3: ( (lv_variant_12_0= ruleVariant ) )
			// InternalDsl.g:1370:4: (lv_variant_12_0= ruleVariant )
			{
			// InternalDsl.g:1370:4: (lv_variant_12_0= ruleVariant )
			// InternalDsl.g:1371:5: lv_variant_12_0= ruleVariant
			{

								newCompositeNode(grammarAccess.getSmallRepetitionStatementAccess().getVariantVariantParserRuleCall_12_0());
							
			pushFollow(FOLLOW_12);
			lv_variant_12_0=ruleVariant();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSmallRepetitionStatementRule());
								}
								set(
									current,
									"variant",
									lv_variant_12_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Variant");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_13=(Token)match(input,27,FOLLOW_6); 

						newLeafNode(otherlv_13, grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_13());
					
			otherlv_14=(Token)match(input,46,FOLLOW_8); 

						newLeafNode(otherlv_14, grammarAccess.getSmallRepetitionStatementAccess().getLeftCurlyBracketKeyword_14());
					
			// InternalDsl.g:1396:3: ( (lv_loopStatement_15_0= ruleAbstractStatement ) )
			// InternalDsl.g:1397:4: (lv_loopStatement_15_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1397:4: (lv_loopStatement_15_0= ruleAbstractStatement )
			// InternalDsl.g:1398:5: lv_loopStatement_15_0= ruleAbstractStatement
			{

								newCompositeNode(grammarAccess.getSmallRepetitionStatementAccess().getLoopStatementAbstractStatementParserRuleCall_15_0());
							
			pushFollow(FOLLOW_7);
			lv_loopStatement_15_0=ruleAbstractStatement();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getSmallRepetitionStatementRule());
								}
								set(
									current,
									"loopStatement",
									lv_loopStatement_15_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.AbstractStatement");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_16=(Token)match(input,47,FOLLOW_34); 

						newLeafNode(otherlv_16, grammarAccess.getSmallRepetitionStatementAccess().getRightCurlyBracketKeyword_16());
					
			otherlv_17=(Token)match(input,37,FOLLOW_2); 

						newLeafNode(otherlv_17, grammarAccess.getSmallRepetitionStatementAccess().getOdKeyword_17());
					
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleSmallRepetitionStatement"



	// $ANTLR start "entryRuleVariant"
	// InternalDsl.g:1427:1: entryRuleVariant returns [EObject current=null] :iv_ruleVariant= ruleVariant EOF ;
	public final EObject entryRuleVariant() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleVariant =null;

		try {
			// InternalDsl.g:1427:48: (iv_ruleVariant= ruleVariant EOF )
			// InternalDsl.g:1428:2: iv_ruleVariant= ruleVariant EOF
			{
			 newCompositeNode(grammarAccess.getVariantRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleVariant=ruleVariant();
			state._fsp--;

			 current =iv_ruleVariant; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleVariant"



	// $ANTLR start "ruleVariant"
	// InternalDsl.g:1434:1: ruleVariant returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleVariant() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1440:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:1441:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:1441:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:1442:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:1442:3: ()
			// InternalDsl.g:1443:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getVariantAccess().getVariantAction_0(),
								current);
						
			}

			// InternalDsl.g:1449:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:1450:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:1450:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:1451:5: lv_name_1_0= ruleEString
			{

								newCompositeNode(grammarAccess.getVariantAccess().getNameEStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_2);
			lv_name_1_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getVariantRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleVariant"



	// $ANTLR start "entryRuleJavaVariables"
	// InternalDsl.g:1472:1: entryRuleJavaVariables returns [EObject current=null] :iv_ruleJavaVariables= ruleJavaVariables EOF ;
	public final EObject entryRuleJavaVariables() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleJavaVariables =null;

		try {
			// InternalDsl.g:1472:54: (iv_ruleJavaVariables= ruleJavaVariables EOF )
			// InternalDsl.g:1473:2: iv_ruleJavaVariables= ruleJavaVariables EOF
			{
			 newCompositeNode(grammarAccess.getJavaVariablesRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleJavaVariables=ruleJavaVariables();
			state._fsp--;

			 current =iv_ruleJavaVariables; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleJavaVariables"



	// $ANTLR start "ruleJavaVariables"
	// InternalDsl.g:1479:1: ruleJavaVariables returns [EObject current=null] : ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? ) ;
	public final EObject ruleJavaVariables() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_3=null;
		Token otherlv_5=null;
		Token otherlv_7=null;
		EObject lv_variables_4_0 =null;
		EObject lv_variables_6_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1485:2: ( ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? ) )
			// InternalDsl.g:1486:2: ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? )
			{
			// InternalDsl.g:1486:2: ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? )
			// InternalDsl.g:1487:3: () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )?
			{
			// InternalDsl.g:1487:3: ()
			// InternalDsl.g:1488:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getJavaVariablesAccess().getJavaVariablesAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,24,FOLLOW_35); 

						newLeafNode(otherlv_1, grammarAccess.getJavaVariablesAccess().getJavaVariablesKeyword_1());
					
			// InternalDsl.g:1498:3: (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==44) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// InternalDsl.g:1499:4: otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}'
					{
					otherlv_2=(Token)match(input,44,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getJavaVariablesAccess().getVariablesKeyword_2_0());
								
					otherlv_3=(Token)match(input,46,FOLLOW_4); 

									newLeafNode(otherlv_3, grammarAccess.getJavaVariablesAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1507:4: ( (lv_variables_4_0= ruleJavaVariable ) )
					// InternalDsl.g:1508:5: (lv_variables_4_0= ruleJavaVariable )
					{
					// InternalDsl.g:1508:5: (lv_variables_4_0= ruleJavaVariable )
					// InternalDsl.g:1509:6: lv_variables_4_0= ruleJavaVariable
					{

											newCompositeNode(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_36);
					lv_variables_4_0=ruleJavaVariable();
					state._fsp--;


											if (current==null) {
												current = createModelElementForParent(grammarAccess.getJavaVariablesRule());
											}
											add(
												current,
												"variables",
												lv_variables_4_0,
												"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.JavaVariable");
											afterParserOrEnumRuleCall();
										
					}

					}

					// InternalDsl.g:1526:4: (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )*
					loop26:
					while (true) {
						int alt26=2;
						int LA26_0 = input.LA(1);
						if ( (LA26_0==16) ) {
							alt26=1;
						}

						switch (alt26) {
						case 1 :
							// InternalDsl.g:1527:5: otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) )
							{
							otherlv_5=(Token)match(input,16,FOLLOW_4); 

												newLeafNode(otherlv_5, grammarAccess.getJavaVariablesAccess().getCommaKeyword_2_3_0());
											
							// InternalDsl.g:1531:5: ( (lv_variables_6_0= ruleJavaVariable ) )
							// InternalDsl.g:1532:6: (lv_variables_6_0= ruleJavaVariable )
							{
							// InternalDsl.g:1532:6: (lv_variables_6_0= ruleJavaVariable )
							// InternalDsl.g:1533:7: lv_variables_6_0= ruleJavaVariable
							{

														newCompositeNode(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_3_1_0());
													
							pushFollow(FOLLOW_36);
							lv_variables_6_0=ruleJavaVariable();
							state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getJavaVariablesRule());
														}
														add(
															current,
															"variables",
															lv_variables_6_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.JavaVariable");
														afterParserOrEnumRuleCall();
													
							}

							}

							}
							break;

						default :
							break loop26;
						}
					}

					otherlv_7=(Token)match(input,47,FOLLOW_2); 

									newLeafNode(otherlv_7, grammarAccess.getJavaVariablesAccess().getRightCurlyBracketKeyword_2_4());
								
					}
					break;

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleJavaVariables"



	// $ANTLR start "entryRuleJavaVariable"
	// InternalDsl.g:1560:1: entryRuleJavaVariable returns [EObject current=null] :iv_ruleJavaVariable= ruleJavaVariable EOF ;
	public final EObject entryRuleJavaVariable() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleJavaVariable =null;

		try {
			// InternalDsl.g:1560:53: (iv_ruleJavaVariable= ruleJavaVariable EOF )
			// InternalDsl.g:1561:2: iv_ruleJavaVariable= ruleJavaVariable EOF
			{
			 newCompositeNode(grammarAccess.getJavaVariableRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleJavaVariable=ruleJavaVariable();
			state._fsp--;

			 current =iv_ruleJavaVariable; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleJavaVariable"



	// $ANTLR start "ruleJavaVariable"
	// InternalDsl.g:1567:1: ruleJavaVariable returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleJavaVariable() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1573:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:1574:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:1574:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:1575:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:1575:3: ()
			// InternalDsl.g:1576:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getJavaVariableAccess().getJavaVariableAction_0(),
								current);
						
			}

			// InternalDsl.g:1582:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:1583:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:1583:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:1584:5: lv_name_1_0= ruleEString
			{

								newCompositeNode(grammarAccess.getJavaVariableAccess().getNameEStringParserRuleCall_1_0());
							
			pushFollow(FOLLOW_2);
			lv_name_1_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getJavaVariableRule());
								}
								set(
									current,
									"name",
									lv_name_1_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleJavaVariable"



	// $ANTLR start "entryRuleGlobalConditions"
	// InternalDsl.g:1605:1: entryRuleGlobalConditions returns [EObject current=null] :iv_ruleGlobalConditions= ruleGlobalConditions EOF ;
	public final EObject entryRuleGlobalConditions() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleGlobalConditions =null;

		try {
			// InternalDsl.g:1605:57: (iv_ruleGlobalConditions= ruleGlobalConditions EOF )
			// InternalDsl.g:1606:2: iv_ruleGlobalConditions= ruleGlobalConditions EOF
			{
			 newCompositeNode(grammarAccess.getGlobalConditionsRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleGlobalConditions=ruleGlobalConditions();
			state._fsp--;

			 current =iv_ruleGlobalConditions; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleGlobalConditions"



	// $ANTLR start "ruleGlobalConditions"
	// InternalDsl.g:1612:1: ruleGlobalConditions returns [EObject current=null] : ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? ) ;
	public final EObject ruleGlobalConditions() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_3=null;
		Token otherlv_5=null;
		Token otherlv_7=null;
		EObject lv_conditions_4_0 =null;
		EObject lv_conditions_6_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1618:2: ( ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? ) )
			// InternalDsl.g:1619:2: ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? )
			{
			// InternalDsl.g:1619:2: ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? )
			// InternalDsl.g:1620:3: () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )?
			{
			// InternalDsl.g:1620:3: ()
			// InternalDsl.g:1621:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getGlobalConditionsAccess().getGlobalConditionsAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,23,FOLLOW_37); 

						newLeafNode(otherlv_1, grammarAccess.getGlobalConditionsAccess().getGlobalConditionsKeyword_1());
					
			// InternalDsl.g:1631:3: (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( (LA29_0==28) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// InternalDsl.g:1632:4: otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}'
					{
					otherlv_2=(Token)match(input,28,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getGlobalConditionsAccess().getConditionsKeyword_2_0());
								
					otherlv_3=(Token)match(input,46,FOLLOW_4); 

									newLeafNode(otherlv_3, grammarAccess.getGlobalConditionsAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1640:4: ( (lv_conditions_4_0= ruleCondition ) )
					// InternalDsl.g:1641:5: (lv_conditions_4_0= ruleCondition )
					{
					// InternalDsl.g:1641:5: (lv_conditions_4_0= ruleCondition )
					// InternalDsl.g:1642:6: lv_conditions_4_0= ruleCondition
					{

											newCompositeNode(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_36);
					lv_conditions_4_0=ruleCondition();
					state._fsp--;


											if (current==null) {
												current = createModelElementForParent(grammarAccess.getGlobalConditionsRule());
											}
											add(
												current,
												"conditions",
												lv_conditions_4_0,
												"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
											afterParserOrEnumRuleCall();
										
					}

					}

					// InternalDsl.g:1659:4: (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )*
					loop28:
					while (true) {
						int alt28=2;
						int LA28_0 = input.LA(1);
						if ( (LA28_0==16) ) {
							alt28=1;
						}

						switch (alt28) {
						case 1 :
							// InternalDsl.g:1660:5: otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) )
							{
							otherlv_5=(Token)match(input,16,FOLLOW_4); 

												newLeafNode(otherlv_5, grammarAccess.getGlobalConditionsAccess().getCommaKeyword_2_3_0());
											
							// InternalDsl.g:1664:5: ( (lv_conditions_6_0= ruleCondition ) )
							// InternalDsl.g:1665:6: (lv_conditions_6_0= ruleCondition )
							{
							// InternalDsl.g:1665:6: (lv_conditions_6_0= ruleCondition )
							// InternalDsl.g:1666:7: lv_conditions_6_0= ruleCondition
							{

														newCompositeNode(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_3_1_0());
													
							pushFollow(FOLLOW_36);
							lv_conditions_6_0=ruleCondition();
							state._fsp--;


														if (current==null) {
															current = createModelElementForParent(grammarAccess.getGlobalConditionsRule());
														}
														add(
															current,
															"conditions",
															lv_conditions_6_0,
															"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
														afterParserOrEnumRuleCall();
													
							}

							}

							}
							break;

						default :
							break loop28;
						}
					}

					otherlv_7=(Token)match(input,47,FOLLOW_2); 

									newLeafNode(otherlv_7, grammarAccess.getGlobalConditionsAccess().getRightCurlyBracketKeyword_2_4());
								
					}
					break;

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleGlobalConditions"



	// $ANTLR start "entryRuleRenaming"
	// InternalDsl.g:1693:1: entryRuleRenaming returns [EObject current=null] :iv_ruleRenaming= ruleRenaming EOF ;
	public final EObject entryRuleRenaming() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleRenaming =null;

		try {
			// InternalDsl.g:1693:49: (iv_ruleRenaming= ruleRenaming EOF )
			// InternalDsl.g:1694:2: iv_ruleRenaming= ruleRenaming EOF
			{
			 newCompositeNode(grammarAccess.getRenamingRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleRenaming=ruleRenaming();
			state._fsp--;

			 current =iv_ruleRenaming; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleRenaming"



	// $ANTLR start "ruleRenaming"
	// InternalDsl.g:1700:1: ruleRenaming returns [EObject current=null] : ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? ) ;
	public final EObject ruleRenaming() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_3=null;
		Token otherlv_6=null;
		EObject lv_rename_4_0 =null;
		EObject lv_rename_5_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1706:2: ( ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? ) )
			// InternalDsl.g:1707:2: ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? )
			{
			// InternalDsl.g:1707:2: ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? )
			// InternalDsl.g:1708:3: () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )?
			{
			// InternalDsl.g:1708:3: ()
			// InternalDsl.g:1709:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getRenamingAccess().getRenamingAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,25,FOLLOW_38); 

						newLeafNode(otherlv_1, grammarAccess.getRenamingAccess().getRenamingKeyword_1());
					
			// InternalDsl.g:1719:3: (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )?
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==40) ) {
				alt31=1;
			}
			switch (alt31) {
				case 1 :
					// InternalDsl.g:1720:4: otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}'
					{
					otherlv_2=(Token)match(input,40,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getRenamingAccess().getRenamesKeyword_2_0());
								
					otherlv_3=(Token)match(input,46,FOLLOW_6); 

									newLeafNode(otherlv_3, grammarAccess.getRenamingAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1728:4: ( (lv_rename_4_0= ruleRename ) )
					// InternalDsl.g:1729:5: (lv_rename_4_0= ruleRename )
					{
					// InternalDsl.g:1729:5: (lv_rename_4_0= ruleRename )
					// InternalDsl.g:1730:6: lv_rename_4_0= ruleRename
					{

											newCompositeNode(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_39);
					lv_rename_4_0=ruleRename();
					state._fsp--;


											if (current==null) {
												current = createModelElementForParent(grammarAccess.getRenamingRule());
											}
											add(
												current,
												"rename",
												lv_rename_4_0,
												"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Rename");
											afterParserOrEnumRuleCall();
										
					}

					}

					// InternalDsl.g:1747:4: ( (lv_rename_5_0= ruleRename ) )*
					loop30:
					while (true) {
						int alt30=2;
						int LA30_0 = input.LA(1);
						if ( (LA30_0==46) ) {
							alt30=1;
						}

						switch (alt30) {
						case 1 :
							// InternalDsl.g:1748:5: (lv_rename_5_0= ruleRename )
							{
							// InternalDsl.g:1748:5: (lv_rename_5_0= ruleRename )
							// InternalDsl.g:1749:6: lv_rename_5_0= ruleRename
							{

													newCompositeNode(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_3_0());
												
							pushFollow(FOLLOW_39);
							lv_rename_5_0=ruleRename();
							state._fsp--;


													if (current==null) {
														current = createModelElementForParent(grammarAccess.getRenamingRule());
													}
													add(
														current,
														"rename",
														lv_rename_5_0,
														"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Rename");
													afterParserOrEnumRuleCall();
												
							}

							}
							break;

						default :
							break loop30;
						}
					}

					otherlv_6=(Token)match(input,47,FOLLOW_2); 

									newLeafNode(otherlv_6, grammarAccess.getRenamingAccess().getRightCurlyBracketKeyword_2_4());
								
					}
					break;

			}

			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleRenaming"



	// $ANTLR start "entryRuleRename"
	// InternalDsl.g:1775:1: entryRuleRename returns [EObject current=null] :iv_ruleRename= ruleRename EOF ;
	public final EObject entryRuleRename() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleRename =null;

		try {
			// InternalDsl.g:1775:47: (iv_ruleRename= ruleRename EOF )
			// InternalDsl.g:1776:2: iv_ruleRename= ruleRename EOF
			{
			 newCompositeNode(grammarAccess.getRenameRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleRename=ruleRename();
			state._fsp--;

			 current =iv_ruleRename; 
			match(input,EOF,FOLLOW_2); 
			}

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "entryRuleRename"



	// $ANTLR start "ruleRename"
	// InternalDsl.g:1782:1: ruleRename returns [EObject current=null] : ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' ) ;
	public final EObject ruleRename() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_4=null;
		Token otherlv_6=null;
		Token otherlv_8=null;
		AntlrDatatypeRuleToken lv_type_3_0 =null;
		AntlrDatatypeRuleToken lv_function_5_0 =null;
		AntlrDatatypeRuleToken lv_newName_7_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1788:2: ( ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' ) )
			// InternalDsl.g:1789:2: ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' )
			{
			// InternalDsl.g:1789:2: ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' )
			// InternalDsl.g:1790:3: () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}'
			{
			// InternalDsl.g:1790:3: ()
			// InternalDsl.g:1791:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getRenameAccess().getRenameAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,46,FOLLOW_40); 

						newLeafNode(otherlv_1, grammarAccess.getRenameAccess().getLeftCurlyBracketKeyword_1());
					
			otherlv_2=(Token)match(input,42,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getRenameAccess().getTypeKeyword_2());
					
			// InternalDsl.g:1805:3: ( (lv_type_3_0= ruleEString ) )
			// InternalDsl.g:1806:4: (lv_type_3_0= ruleEString )
			{
			// InternalDsl.g:1806:4: (lv_type_3_0= ruleEString )
			// InternalDsl.g:1807:5: lv_type_3_0= ruleEString
			{

								newCompositeNode(grammarAccess.getRenameAccess().getTypeEStringParserRuleCall_3_0());
							
			pushFollow(FOLLOW_41);
			lv_type_3_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getRenameRule());
								}
								set(
									current,
									"type",
									lv_type_3_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_4=(Token)match(input,32,FOLLOW_4); 

						newLeafNode(otherlv_4, grammarAccess.getRenameAccess().getFunctionKeyword_4());
					
			// InternalDsl.g:1828:3: ( (lv_function_5_0= ruleEString ) )
			// InternalDsl.g:1829:4: (lv_function_5_0= ruleEString )
			{
			// InternalDsl.g:1829:4: (lv_function_5_0= ruleEString )
			// InternalDsl.g:1830:5: lv_function_5_0= ruleEString
			{

								newCompositeNode(grammarAccess.getRenameAccess().getFunctionEStringParserRuleCall_5_0());
							
			pushFollow(FOLLOW_42);
			lv_function_5_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getRenameRule());
								}
								set(
									current,
									"function",
									lv_function_5_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_6=(Token)match(input,36,FOLLOW_4); 

						newLeafNode(otherlv_6, grammarAccess.getRenameAccess().getNewNameKeyword_6());
					
			// InternalDsl.g:1851:3: ( (lv_newName_7_0= ruleEString ) )
			// InternalDsl.g:1852:4: (lv_newName_7_0= ruleEString )
			{
			// InternalDsl.g:1852:4: (lv_newName_7_0= ruleEString )
			// InternalDsl.g:1853:5: lv_newName_7_0= ruleEString
			{

								newCompositeNode(grammarAccess.getRenameAccess().getNewNameEStringParserRuleCall_7_0());
							
			pushFollow(FOLLOW_7);
			lv_newName_7_0=ruleEString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getRenameRule());
								}
								set(
									current,
									"newName",
									lv_newName_7_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.EString");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_8=(Token)match(input,47,FOLLOW_2); 

						newLeafNode(otherlv_8, grammarAccess.getRenameAccess().getRightCurlyBracketKeyword_8());
					
			}

			}


				leaveRule();

		}

		    catch (RecognitionException re) {
		        recover(input,re);
		        appendSkippedTokens();
		    }

		finally {
			// do for sure before leaving
		}
		return current;
	}
	// $ANTLR end "ruleRename"

	// Delegated rules



	public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000003C00002L});
	public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000220L});
	public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000400000000000L});
	public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000600200100220L});
	public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000004200000L});
	public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000020060L});
	public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x00000000001AC800L});
	public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000004041002L});
	public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000022060L});
	public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000000012000L});
	public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000020020L});
	public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000008020060L});
	public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000020000000000L});
	public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x00000000C0000000L});
	public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000100000000002L});
	public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000800000010000L});
	public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000010000002L});
	public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000C00000000000L});
	public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000001000000000L});
}
