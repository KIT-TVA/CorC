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
		"'return'", "'then'", "'type'", "'var:'", "'variables'", "'while'", "'{'", 
		"'}'"
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
	public static final int T__48=48;
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
					
			otherlv_3=(Token)match(input,47,FOLLOW_4); 

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

			otherlv_5=(Token)match(input,48,FOLLOW_6); 

						newLeafNode(otherlv_5, grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_5());
					
			otherlv_6=(Token)match(input,47,FOLLOW_8); 

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

			otherlv_8=(Token)match(input,48,FOLLOW_9); 

						newLeafNode(otherlv_8, grammarAccess.getCbCFormulaAccess().getRightCurlyBracketKeyword_8());
					
			otherlv_9=(Token)match(input,38,FOLLOW_6); 

						newLeafNode(otherlv_9, grammarAccess.getCbCFormulaAccess().getPostKeyword_9());
					
			otherlv_10=(Token)match(input,47,FOLLOW_4); 

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

			otherlv_12=(Token)match(input,48,FOLLOW_2); 

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
	// InternalDsl.g:346:1: ruleAbstractStatement returns [EObject current=null] : (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement |this_ReturnStatement_6= ruleReturnStatement |this_StrengthWeakStatement_7= ruleStrengthWeakStatement ) ;
	public final EObject ruleAbstractStatement() throws RecognitionException {
		EObject current = null;


		EObject this_AbstractStatement_Impl_0 =null;
		EObject this_SkipStatement_1 =null;
		EObject this_CompositionStatement_2 =null;
		EObject this_SelectionStatement_3 =null;
		EObject this_SmallRepetitionStatement_4 =null;
		EObject this_MethodStatement_5 =null;
		EObject this_ReturnStatement_6 =null;
		EObject this_StrengthWeakStatement_7 =null;


			enterRule();

		try {
			// InternalDsl.g:352:2: ( (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement |this_ReturnStatement_6= ruleReturnStatement |this_StrengthWeakStatement_7= ruleStrengthWeakStatement ) )
			// InternalDsl.g:353:2: (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement |this_ReturnStatement_6= ruleReturnStatement |this_StrengthWeakStatement_7= ruleStrengthWeakStatement )
			{
			// InternalDsl.g:353:2: (this_AbstractStatement_Impl_0= ruleAbstractStatement_Impl |this_SkipStatement_1= ruleSkipStatement |this_CompositionStatement_2= ruleCompositionStatement |this_SelectionStatement_3= ruleSelectionStatement |this_SmallRepetitionStatement_4= ruleSmallRepetitionStatement |this_MethodStatement_5= ruleMethodStatement |this_ReturnStatement_6= ruleReturnStatement |this_StrengthWeakStatement_7= ruleStrengthWeakStatement )
			int alt2=8;
			switch ( input.LA(1) ) {
			case RULE_ID:
				{
				int LA2_1 = input.LA(2);
				if ( (LA2_1==21||LA2_1==26) ) {
					alt2=1;
				}
				else if ( (LA2_1==EOF||LA2_1==48) ) {
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
			case 47:
				{
				alt2=3;
				}
				break;
			case 33:
				{
				alt2=4;
				}
				break;
			case 46:
				{
				alt2=5;
				}
				break;
			case RULE_STRING:
				{
				alt2=6;
				}
				break;
			case 41:
				{
				alt2=7;
				}
				break;
			case 39:
				{
				alt2=8;
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
				case 7 :
					// InternalDsl.g:408:3: this_ReturnStatement_6= ruleReturnStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getReturnStatementParserRuleCall_6());
							
					pushFollow(FOLLOW_2);
					this_ReturnStatement_6=ruleReturnStatement();
					state._fsp--;


								current = this_ReturnStatement_6;
								afterParserOrEnumRuleCall();
							
					}
					break;
				case 8 :
					// InternalDsl.g:417:3: this_StrengthWeakStatement_7= ruleStrengthWeakStatement
					{

								newCompositeNode(grammarAccess.getAbstractStatementAccess().getStrengthWeakStatementParserRuleCall_7());
							
					pushFollow(FOLLOW_2);
					this_StrengthWeakStatement_7=ruleStrengthWeakStatement();
					state._fsp--;


								current = this_StrengthWeakStatement_7;
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
	// InternalDsl.g:429:1: entryRuleEString returns [String current=null] :iv_ruleEString= ruleEString EOF ;
	public final String entryRuleEString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleEString =null;

		try {
			// InternalDsl.g:429:47: (iv_ruleEString= ruleEString EOF )
			// InternalDsl.g:430:2: iv_ruleEString= ruleEString EOF
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
	// InternalDsl.g:436:1: ruleEString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID ) ;
	public final AntlrDatatypeRuleToken ruleEString() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token this_STRING_0=null;
		Token this_ID_1=null;


			enterRule();

		try {
			// InternalDsl.g:442:2: ( (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID ) )
			// InternalDsl.g:443:2: (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID )
			{
			// InternalDsl.g:443:2: (this_STRING_0= RULE_STRING |this_ID_1= RULE_ID )
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
					// InternalDsl.g:444:3: this_STRING_0= RULE_STRING
					{
					this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

								current.merge(this_STRING_0);
							

								newLeafNode(this_STRING_0, grammarAccess.getEStringAccess().getSTRINGTerminalRuleCall_0());
							
					}
					break;
				case 2 :
					// InternalDsl.g:452:3: this_ID_1= RULE_ID
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
	// InternalDsl.g:463:1: entryRuleCodeString returns [String current=null] :iv_ruleCodeString= ruleCodeString EOF ;
	public final String entryRuleCodeString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleCodeString =null;

		try {
			// InternalDsl.g:463:50: (iv_ruleCodeString= ruleCodeString EOF )
			// InternalDsl.g:464:2: iv_ruleCodeString= ruleCodeString EOF
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
	// InternalDsl.g:470:1: ruleCodeString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (kw= '(' )? (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )* kw= ';' )+ ;
	public final AntlrDatatypeRuleToken ruleCodeString() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token this_ID_0=null;
		Token kw=null;
		Token this_ID_2=null;
		Token this_INT_3=null;
		Token this_INT_9=null;
		Token this_INT_14=null;
		AntlrDatatypeRuleToken this_VariableString_7 =null;
		AntlrDatatypeRuleToken this_Operation_10 =null;
		AntlrDatatypeRuleToken this_VariableString_12 =null;


			enterRule();

		try {
			// InternalDsl.g:476:2: ( (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (kw= '(' )? (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )* kw= ';' )+ )
			// InternalDsl.g:477:2: (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (kw= '(' )? (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )* kw= ';' )+
			{
			// InternalDsl.g:477:2: (this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (kw= '(' )? (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )* kw= ';' )+
			int cnt14=0;
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==RULE_ID) ) {
					alt14=1;
				}

				switch (alt14) {
				case 1 :
					// InternalDsl.g:478:3: this_ID_0= RULE_ID (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )? kw= '=' (kw= '(' )? (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )* kw= ';'
					{
					this_ID_0=(Token)match(input,RULE_ID,FOLLOW_10); 

								current.merge(this_ID_0);
							

								newLeafNode(this_ID_0, grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_0());
							
					// InternalDsl.g:485:3: (kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']' )?
					int alt5=2;
					int LA5_0 = input.LA(1);
					if ( (LA5_0==26) ) {
						alt5=1;
					}
					switch (alt5) {
						case 1 :
							// InternalDsl.g:486:4: kw= '[' (this_ID_2= RULE_ID |this_INT_3= RULE_INT ) kw= ']'
							{
							kw=(Token)match(input,26,FOLLOW_11); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getCodeStringAccess().getLeftSquareBracketKeyword_1_0());
										
							// InternalDsl.g:491:4: (this_ID_2= RULE_ID |this_INT_3= RULE_INT )
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
									// InternalDsl.g:492:5: this_ID_2= RULE_ID
									{
									this_ID_2=(Token)match(input,RULE_ID,FOLLOW_12); 

														current.merge(this_ID_2);
													

														newLeafNode(this_ID_2, grammarAccess.getCodeStringAccess().getIDTerminalRuleCall_1_1_0());
													
									}
									break;
								case 2 :
									// InternalDsl.g:500:5: this_INT_3= RULE_INT
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
							
					// InternalDsl.g:519:3: (kw= '(' )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==12) ) {
						alt6=1;
					}
					switch (alt6) {
						case 1 :
							// InternalDsl.g:520:4: kw= '('
							{
							kw=(Token)match(input,12,FOLLOW_15); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getCodeStringAccess().getLeftParenthesisKeyword_3());
										
							}
							break;

					}

					// InternalDsl.g:526:3: (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) )
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
							// InternalDsl.g:527:4: this_VariableString_7= ruleVariableString
							{

											newCompositeNode(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_4_0());
										
							pushFollow(FOLLOW_16);
							this_VariableString_7=ruleVariableString();
							state._fsp--;


											current.merge(this_VariableString_7);
										

											afterParserOrEnumRuleCall();
										
							}
							break;
						case 2 :
							// InternalDsl.g:538:4: ( (kw= '-' )? this_INT_9= RULE_INT )
							{
							// InternalDsl.g:538:4: ( (kw= '-' )? this_INT_9= RULE_INT )
							// InternalDsl.g:539:5: (kw= '-' )? this_INT_9= RULE_INT
							{
							// InternalDsl.g:539:5: (kw= '-' )?
							int alt7=2;
							int LA7_0 = input.LA(1);
							if ( (LA7_0==17) ) {
								alt7=1;
							}
							switch (alt7) {
								case 1 :
									// InternalDsl.g:540:6: kw= '-'
									{
									kw=(Token)match(input,17,FOLLOW_17); 

															current.merge(kw);
															newLeafNode(kw, grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_4_1_0());
														
									}
									break;

							}

							this_INT_9=(Token)match(input,RULE_INT,FOLLOW_16); 

												current.merge(this_INT_9);
											

												newLeafNode(this_INT_9, grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_4_1_1());
											
							}

							}
							break;

					}

					// InternalDsl.g:555:3: (this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )* )*
					loop13:
					while (true) {
						int alt13=2;
						int LA13_0 = input.LA(1);
						if ( (LA13_0==11||(LA13_0 >= 14 && LA13_0 <= 15)||LA13_0==17||LA13_0==19) ) {
							alt13=1;
						}

						switch (alt13) {
						case 1 :
							// InternalDsl.g:556:4: this_Operation_10= ruleOperation (kw= '(' )* (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) ) (kw= ')' )*
							{

											newCompositeNode(grammarAccess.getCodeStringAccess().getOperationParserRuleCall_5_0());
										
							pushFollow(FOLLOW_14);
							this_Operation_10=ruleOperation();
							state._fsp--;


											current.merge(this_Operation_10);
										

											afterParserOrEnumRuleCall();
										
							// InternalDsl.g:566:4: (kw= '(' )*
							loop9:
							while (true) {
								int alt9=2;
								int LA9_0 = input.LA(1);
								if ( (LA9_0==12) ) {
									alt9=1;
								}

								switch (alt9) {
								case 1 :
									// InternalDsl.g:567:5: kw= '('
									{
									kw=(Token)match(input,12,FOLLOW_14); 

														current.merge(kw);
														newLeafNode(kw, grammarAccess.getCodeStringAccess().getLeftParenthesisKeyword_5_1());
													
									}
									break;

								default :
									break loop9;
								}
							}

							// InternalDsl.g:573:4: (this_VariableString_12= ruleVariableString | ( (kw= '-' )? this_INT_14= RULE_INT ) )
							int alt11=2;
							switch ( input.LA(1) ) {
							case 17:
								{
								int LA11_1 = input.LA(2);
								if ( (LA11_1==RULE_ID) ) {
									alt11=1;
								}
								else if ( (LA11_1==RULE_INT) ) {
									alt11=2;
								}

								else {
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 11, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

								}
								break;
							case RULE_ID:
								{
								alt11=1;
								}
								break;
							case RULE_INT:
								{
								alt11=2;
								}
								break;
							default:
								NoViableAltException nvae =
									new NoViableAltException("", 11, 0, input);
								throw nvae;
							}
							switch (alt11) {
								case 1 :
									// InternalDsl.g:574:5: this_VariableString_12= ruleVariableString
									{

														newCompositeNode(grammarAccess.getCodeStringAccess().getVariableStringParserRuleCall_5_2_0());
													
									pushFollow(FOLLOW_18);
									this_VariableString_12=ruleVariableString();
									state._fsp--;


														current.merge(this_VariableString_12);
													

														afterParserOrEnumRuleCall();
													
									}
									break;
								case 2 :
									// InternalDsl.g:585:5: ( (kw= '-' )? this_INT_14= RULE_INT )
									{
									// InternalDsl.g:585:5: ( (kw= '-' )? this_INT_14= RULE_INT )
									// InternalDsl.g:586:6: (kw= '-' )? this_INT_14= RULE_INT
									{
									// InternalDsl.g:586:6: (kw= '-' )?
									int alt10=2;
									int LA10_0 = input.LA(1);
									if ( (LA10_0==17) ) {
										alt10=1;
									}
									switch (alt10) {
										case 1 :
											// InternalDsl.g:587:7: kw= '-'
											{
											kw=(Token)match(input,17,FOLLOW_17); 

																		current.merge(kw);
																		newLeafNode(kw, grammarAccess.getCodeStringAccess().getHyphenMinusKeyword_5_2_1_0());
																	
											}
											break;

									}

									this_INT_14=(Token)match(input,RULE_INT,FOLLOW_18); 

															current.merge(this_INT_14);
														

															newLeafNode(this_INT_14, grammarAccess.getCodeStringAccess().getINTTerminalRuleCall_5_2_1_1());
														
									}

									}
									break;

							}

							// InternalDsl.g:602:4: (kw= ')' )*
							loop12:
							while (true) {
								int alt12=2;
								int LA12_0 = input.LA(1);
								if ( (LA12_0==13) ) {
									alt12=1;
								}

								switch (alt12) {
								case 1 :
									// InternalDsl.g:603:5: kw= ')'
									{
									kw=(Token)match(input,13,FOLLOW_18); 

														current.merge(kw);
														newLeafNode(kw, grammarAccess.getCodeStringAccess().getRightParenthesisKeyword_5_3());
													
									}
									break;

								default :
									break loop12;
								}
							}

							}
							break;

						default :
							break loop13;
						}
					}

					kw=(Token)match(input,20,FOLLOW_19); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getCodeStringAccess().getSemicolonKeyword_6());
							
					}
					break;

				default :
					if ( cnt14 >= 1 ) break loop14;
					EarlyExitException eee = new EarlyExitException(14, input);
					throw eee;
				}
				cnt14++;
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
	// InternalDsl.g:619:1: entryRuleVariableString returns [String current=null] :iv_ruleVariableString= ruleVariableString EOF ;
	public final String entryRuleVariableString() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleVariableString =null;

		try {
			// InternalDsl.g:619:54: (iv_ruleVariableString= ruleVariableString EOF )
			// InternalDsl.g:620:2: iv_ruleVariableString= ruleVariableString EOF
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
	// InternalDsl.g:626:1: ruleVariableString returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? ) ;
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
			// InternalDsl.g:632:2: ( ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? ) )
			// InternalDsl.g:633:2: ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? )
			{
			// InternalDsl.g:633:2: ( (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )? )
			// InternalDsl.g:634:3: (kw= '-' )? this_ID_1= RULE_ID ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )?
			{
			// InternalDsl.g:634:3: (kw= '-' )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==17) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// InternalDsl.g:635:4: kw= '-'
					{
					kw=(Token)match(input,17,FOLLOW_20); 

									current.merge(kw);
									newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_0());
								
					}
					break;

			}

			this_ID_1=(Token)match(input,RULE_ID,FOLLOW_21); 

						current.merge(this_ID_1);
					

						newLeafNode(this_ID_1, grammarAccess.getVariableStringAccess().getIDTerminalRuleCall_1());
					
			// InternalDsl.g:648:3: ( ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? ) | ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? ) | (kw= '.' this_VariableString_21= ruleVariableString ) )?
			int alt26=4;
			switch ( input.LA(1) ) {
				case 12:
					{
					alt26=1;
					}
					break;
				case 26:
					{
					alt26=2;
					}
					break;
				case 18:
					{
					alt26=3;
					}
					break;
			}
			switch (alt26) {
				case 1 :
					// InternalDsl.g:649:4: ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? )
					{
					// InternalDsl.g:649:4: ( (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )? )
					// InternalDsl.g:650:5: (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' ) (kw= '.' this_VariableString_12= ruleVariableString )?
					{
					// InternalDsl.g:650:5: (kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')' )
					// InternalDsl.g:651:6: kw= '(' ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )? kw= ')'
					{
					kw=(Token)match(input,12,FOLLOW_22); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getLeftParenthesisKeyword_2_0_0_0());
										
					// InternalDsl.g:656:6: ( (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )* )?
					int alt21=2;
					int LA21_0 = input.LA(1);
					if ( ((LA21_0 >= RULE_ID && LA21_0 <= RULE_INT)||LA21_0==17) ) {
						alt21=1;
					}
					switch (alt21) {
						case 1 :
							// InternalDsl.g:657:7: (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) ) (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )*
							{
							// InternalDsl.g:657:7: (this_VariableString_3= ruleVariableString | ( (kw= '-' )? this_INT_5= RULE_INT ) )
							int alt17=2;
							switch ( input.LA(1) ) {
							case 17:
								{
								int LA17_1 = input.LA(2);
								if ( (LA17_1==RULE_ID) ) {
									alt17=1;
								}
								else if ( (LA17_1==RULE_INT) ) {
									alt17=2;
								}

								else {
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 17, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}

								}
								break;
							case RULE_ID:
								{
								alt17=1;
								}
								break;
							case RULE_INT:
								{
								alt17=2;
								}
								break;
							default:
								NoViableAltException nvae =
									new NoViableAltException("", 17, 0, input);
								throw nvae;
							}
							switch (alt17) {
								case 1 :
									// InternalDsl.g:658:8: this_VariableString_3= ruleVariableString
									{

																	newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_0_0());
																
									pushFollow(FOLLOW_23);
									this_VariableString_3=ruleVariableString();
									state._fsp--;


																	current.merge(this_VariableString_3);
																

																	afterParserOrEnumRuleCall();
																
									}
									break;
								case 2 :
									// InternalDsl.g:669:8: ( (kw= '-' )? this_INT_5= RULE_INT )
									{
									// InternalDsl.g:669:8: ( (kw= '-' )? this_INT_5= RULE_INT )
									// InternalDsl.g:670:9: (kw= '-' )? this_INT_5= RULE_INT
									{
									// InternalDsl.g:670:9: (kw= '-' )?
									int alt16=2;
									int LA16_0 = input.LA(1);
									if ( (LA16_0==17) ) {
										alt16=1;
									}
									switch (alt16) {
										case 1 :
											// InternalDsl.g:671:10: kw= '-'
											{
											kw=(Token)match(input,17,FOLLOW_17); 

																					current.merge(kw);
																					newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_0_1_0());
																				
											}
											break;

									}

									this_INT_5=(Token)match(input,RULE_INT,FOLLOW_23); 

																		current.merge(this_INT_5);
																	

																		newLeafNode(this_INT_5, grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_0_1_1());
																	
									}

									}
									break;

							}

							// InternalDsl.g:686:7: (kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) ) )*
							loop20:
							while (true) {
								int alt20=2;
								int LA20_0 = input.LA(1);
								if ( (LA20_0==16) ) {
									alt20=1;
								}

								switch (alt20) {
								case 1 :
									// InternalDsl.g:687:8: kw= ',' (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) )
									{
									kw=(Token)match(input,16,FOLLOW_15); 

																	current.merge(kw);
																	newLeafNode(kw, grammarAccess.getVariableStringAccess().getCommaKeyword_2_0_0_1_1_0());
																
									// InternalDsl.g:692:8: (this_VariableString_7= ruleVariableString | ( (kw= '-' )? this_INT_9= RULE_INT ) )
									int alt19=2;
									switch ( input.LA(1) ) {
									case 17:
										{
										int LA19_1 = input.LA(2);
										if ( (LA19_1==RULE_ID) ) {
											alt19=1;
										}
										else if ( (LA19_1==RULE_INT) ) {
											alt19=2;
										}

										else {
											int nvaeMark = input.mark();
											try {
												input.consume();
												NoViableAltException nvae =
													new NoViableAltException("", 19, 1, input);
												throw nvae;
											} finally {
												input.rewind(nvaeMark);
											}
										}

										}
										break;
									case RULE_ID:
										{
										alt19=1;
										}
										break;
									case RULE_INT:
										{
										alt19=2;
										}
										break;
									default:
										NoViableAltException nvae =
											new NoViableAltException("", 19, 0, input);
										throw nvae;
									}
									switch (alt19) {
										case 1 :
											// InternalDsl.g:693:9: this_VariableString_7= ruleVariableString
											{

																				newCompositeNode(grammarAccess.getVariableStringAccess().getVariableStringParserRuleCall_2_0_0_1_1_1_0());
																			
											pushFollow(FOLLOW_23);
											this_VariableString_7=ruleVariableString();
											state._fsp--;


																				current.merge(this_VariableString_7);
																			

																				afterParserOrEnumRuleCall();
																			
											}
											break;
										case 2 :
											// InternalDsl.g:704:9: ( (kw= '-' )? this_INT_9= RULE_INT )
											{
											// InternalDsl.g:704:9: ( (kw= '-' )? this_INT_9= RULE_INT )
											// InternalDsl.g:705:10: (kw= '-' )? this_INT_9= RULE_INT
											{
											// InternalDsl.g:705:10: (kw= '-' )?
											int alt18=2;
											int LA18_0 = input.LA(1);
											if ( (LA18_0==17) ) {
												alt18=1;
											}
											switch (alt18) {
												case 1 :
													// InternalDsl.g:706:11: kw= '-'
													{
													kw=(Token)match(input,17,FOLLOW_17); 

																								current.merge(kw);
																								newLeafNode(kw, grammarAccess.getVariableStringAccess().getHyphenMinusKeyword_2_0_0_1_1_1_1_0());
																							
													}
													break;

											}

											this_INT_9=(Token)match(input,RULE_INT,FOLLOW_23); 

																					current.merge(this_INT_9);
																				

																					newLeafNode(this_INT_9, grammarAccess.getVariableStringAccess().getINTTerminalRuleCall_2_0_0_1_1_1_1_1());
																				
											}

											}
											break;

									}

									}
									break;

								default :
									break loop20;
								}
							}

							}
							break;

					}

					kw=(Token)match(input,13,FOLLOW_24); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getRightParenthesisKeyword_2_0_0_2());
										
					}

					// InternalDsl.g:729:5: (kw= '.' this_VariableString_12= ruleVariableString )?
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==18) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// InternalDsl.g:730:6: kw= '.' this_VariableString_12= ruleVariableString
							{
							kw=(Token)match(input,18,FOLLOW_25); 

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
					// InternalDsl.g:748:4: ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? )
					{
					// InternalDsl.g:748:4: ( (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )? )
					// InternalDsl.g:749:5: (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' ) (kw= '.' this_VariableString_19= ruleVariableString )?
					{
					// InternalDsl.g:749:5: (kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']' )
					// InternalDsl.g:750:6: kw= '[' (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )? kw= ']'
					{
					kw=(Token)match(input,26,FOLLOW_26); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getLeftSquareBracketKeyword_2_1_0_0());
										
					// InternalDsl.g:755:6: (this_VariableString_14= ruleVariableString | ( (kw= '-' )? this_INT_16= RULE_INT ) )?
					int alt24=3;
					switch ( input.LA(1) ) {
						case 17:
							{
							int LA24_1 = input.LA(2);
							if ( (LA24_1==RULE_ID) ) {
								alt24=1;
							}
							else if ( (LA24_1==RULE_INT) ) {
								alt24=2;
							}
							}
							break;
						case RULE_ID:
							{
							alt24=1;
							}
							break;
						case RULE_INT:
							{
							alt24=2;
							}
							break;
					}
					switch (alt24) {
						case 1 :
							// InternalDsl.g:756:7: this_VariableString_14= ruleVariableString
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
							// InternalDsl.g:767:7: ( (kw= '-' )? this_INT_16= RULE_INT )
							{
							// InternalDsl.g:767:7: ( (kw= '-' )? this_INT_16= RULE_INT )
							// InternalDsl.g:768:8: (kw= '-' )? this_INT_16= RULE_INT
							{
							// InternalDsl.g:768:8: (kw= '-' )?
							int alt23=2;
							int LA23_0 = input.LA(1);
							if ( (LA23_0==17) ) {
								alt23=1;
							}
							switch (alt23) {
								case 1 :
									// InternalDsl.g:769:9: kw= '-'
									{
									kw=(Token)match(input,17,FOLLOW_17); 

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

					kw=(Token)match(input,27,FOLLOW_24); 

											current.merge(kw);
											newLeafNode(kw, grammarAccess.getVariableStringAccess().getRightSquareBracketKeyword_2_1_0_2());
										
					}

					// InternalDsl.g:790:5: (kw= '.' this_VariableString_19= ruleVariableString )?
					int alt25=2;
					int LA25_0 = input.LA(1);
					if ( (LA25_0==18) ) {
						alt25=1;
					}
					switch (alt25) {
						case 1 :
							// InternalDsl.g:791:6: kw= '.' this_VariableString_19= ruleVariableString
							{
							kw=(Token)match(input,18,FOLLOW_25); 

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
					// InternalDsl.g:809:4: (kw= '.' this_VariableString_21= ruleVariableString )
					{
					// InternalDsl.g:809:4: (kw= '.' this_VariableString_21= ruleVariableString )
					// InternalDsl.g:810:5: kw= '.' this_VariableString_21= ruleVariableString
					{
					kw=(Token)match(input,18,FOLLOW_25); 

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
	// InternalDsl.g:831:1: entryRuleOperation returns [String current=null] :iv_ruleOperation= ruleOperation EOF ;
	public final String entryRuleOperation() throws RecognitionException {
		String current = null;


		AntlrDatatypeRuleToken iv_ruleOperation =null;

		try {
			// InternalDsl.g:831:49: (iv_ruleOperation= ruleOperation EOF )
			// InternalDsl.g:832:2: iv_ruleOperation= ruleOperation EOF
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
	// InternalDsl.g:838:1: ruleOperation returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' ) ;
	public final AntlrDatatypeRuleToken ruleOperation() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();


		Token kw=null;


			enterRule();

		try {
			// InternalDsl.g:844:2: ( (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' ) )
			// InternalDsl.g:845:2: (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' )
			{
			// InternalDsl.g:845:2: (kw= '+' |kw= '-' |kw= '*' |kw= '/' |kw= '%' )
			int alt27=5;
			switch ( input.LA(1) ) {
			case 15:
				{
				alt27=1;
				}
				break;
			case 17:
				{
				alt27=2;
				}
				break;
			case 14:
				{
				alt27=3;
				}
				break;
			case 19:
				{
				alt27=4;
				}
				break;
			case 11:
				{
				alt27=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 27, 0, input);
				throw nvae;
			}
			switch (alt27) {
				case 1 :
					// InternalDsl.g:846:3: kw= '+'
					{
					kw=(Token)match(input,15,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getPlusSignKeyword_0());
							
					}
					break;
				case 2 :
					// InternalDsl.g:852:3: kw= '-'
					{
					kw=(Token)match(input,17,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getHyphenMinusKeyword_1());
							
					}
					break;
				case 3 :
					// InternalDsl.g:858:3: kw= '*'
					{
					kw=(Token)match(input,14,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getAsteriskKeyword_2());
							
					}
					break;
				case 4 :
					// InternalDsl.g:864:3: kw= '/'
					{
					kw=(Token)match(input,19,FOLLOW_2); 

								current.merge(kw);
								newLeafNode(kw, grammarAccess.getOperationAccess().getSolidusKeyword_3());
							
					}
					break;
				case 5 :
					// InternalDsl.g:870:3: kw= '%'
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
	// InternalDsl.g:879:1: entryRuleAbstractStatement_Impl returns [EObject current=null] :iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF ;
	public final EObject entryRuleAbstractStatement_Impl() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleAbstractStatement_Impl =null;

		try {
			// InternalDsl.g:879:63: (iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF )
			// InternalDsl.g:880:2: iv_ruleAbstractStatement_Impl= ruleAbstractStatement_Impl EOF
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
	// InternalDsl.g:886:1: ruleAbstractStatement_Impl returns [EObject current=null] : ( () ( (lv_name_1_0= ruleCodeString ) ) ) ;
	public final EObject ruleAbstractStatement_Impl() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:892:2: ( ( () ( (lv_name_1_0= ruleCodeString ) ) ) )
			// InternalDsl.g:893:2: ( () ( (lv_name_1_0= ruleCodeString ) ) )
			{
			// InternalDsl.g:893:2: ( () ( (lv_name_1_0= ruleCodeString ) ) )
			// InternalDsl.g:894:3: () ( (lv_name_1_0= ruleCodeString ) )
			{
			// InternalDsl.g:894:3: ()
			// InternalDsl.g:895:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getAbstractStatement_ImplAccess().getAbstractStatementAction_0(),
								current);
						
			}

			// InternalDsl.g:901:3: ( (lv_name_1_0= ruleCodeString ) )
			// InternalDsl.g:902:4: (lv_name_1_0= ruleCodeString )
			{
			// InternalDsl.g:902:4: (lv_name_1_0= ruleCodeString )
			// InternalDsl.g:903:5: lv_name_1_0= ruleCodeString
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
	// InternalDsl.g:924:1: entryRuleMethodStatement returns [EObject current=null] :iv_ruleMethodStatement= ruleMethodStatement EOF ;
	public final EObject entryRuleMethodStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleMethodStatement =null;

		try {
			// InternalDsl.g:924:56: (iv_ruleMethodStatement= ruleMethodStatement EOF )
			// InternalDsl.g:925:2: iv_ruleMethodStatement= ruleMethodStatement EOF
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
	// InternalDsl.g:931:1: ruleMethodStatement returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleMethodStatement() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:937:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:938:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:938:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:939:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:939:3: ()
			// InternalDsl.g:940:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getMethodStatementAccess().getMethodStatementAction_0(),
								current);
						
			}

			// InternalDsl.g:946:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:947:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:947:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:948:5: lv_name_1_0= ruleEString
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



	// $ANTLR start "entryRuleReturnStatement"
	// InternalDsl.g:969:1: entryRuleReturnStatement returns [EObject current=null] :iv_ruleReturnStatement= ruleReturnStatement EOF ;
	public final EObject entryRuleReturnStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleReturnStatement =null;

		try {
			// InternalDsl.g:969:56: (iv_ruleReturnStatement= ruleReturnStatement EOF )
			// InternalDsl.g:970:2: iv_ruleReturnStatement= ruleReturnStatement EOF
			{
			 newCompositeNode(grammarAccess.getReturnStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleReturnStatement=ruleReturnStatement();
			state._fsp--;

			 current =iv_ruleReturnStatement; 
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
	// $ANTLR end "entryRuleReturnStatement"



	// $ANTLR start "ruleReturnStatement"
	// InternalDsl.g:976:1: ruleReturnStatement returns [EObject current=null] : ( () otherlv_1= 'return' ( (lv_name_2_0= ruleCodeString ) ) ) ;
	public final EObject ruleReturnStatement() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		AntlrDatatypeRuleToken lv_name_2_0 =null;


			enterRule();

		try {
			// InternalDsl.g:982:2: ( ( () otherlv_1= 'return' ( (lv_name_2_0= ruleCodeString ) ) ) )
			// InternalDsl.g:983:2: ( () otherlv_1= 'return' ( (lv_name_2_0= ruleCodeString ) ) )
			{
			// InternalDsl.g:983:2: ( () otherlv_1= 'return' ( (lv_name_2_0= ruleCodeString ) ) )
			// InternalDsl.g:984:3: () otherlv_1= 'return' ( (lv_name_2_0= ruleCodeString ) )
			{
			// InternalDsl.g:984:3: ()
			// InternalDsl.g:985:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getReturnStatementAccess().getReturnStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,41,FOLLOW_20); 

						newLeafNode(otherlv_1, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
					
			// InternalDsl.g:995:3: ( (lv_name_2_0= ruleCodeString ) )
			// InternalDsl.g:996:4: (lv_name_2_0= ruleCodeString )
			{
			// InternalDsl.g:996:4: (lv_name_2_0= ruleCodeString )
			// InternalDsl.g:997:5: lv_name_2_0= ruleCodeString
			{

								newCompositeNode(grammarAccess.getReturnStatementAccess().getNameCodeStringParserRuleCall_2_0());
							
			pushFollow(FOLLOW_2);
			lv_name_2_0=ruleCodeString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getReturnStatementRule());
								}
								set(
									current,
									"name",
									lv_name_2_0,
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
	// $ANTLR end "ruleReturnStatement"



	// $ANTLR start "entryRuleStrengthWeakStatement"
	// InternalDsl.g:1018:1: entryRuleStrengthWeakStatement returns [EObject current=null] :iv_ruleStrengthWeakStatement= ruleStrengthWeakStatement EOF ;
	public final EObject entryRuleStrengthWeakStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleStrengthWeakStatement =null;

		try {
			// InternalDsl.g:1018:62: (iv_ruleStrengthWeakStatement= ruleStrengthWeakStatement EOF )
			// InternalDsl.g:1019:2: iv_ruleStrengthWeakStatement= ruleStrengthWeakStatement EOF
			{
			 newCompositeNode(grammarAccess.getStrengthWeakStatementRule()); 
			pushFollow(FOLLOW_1);
			iv_ruleStrengthWeakStatement=ruleStrengthWeakStatement();
			state._fsp--;

			 current =iv_ruleStrengthWeakStatement; 
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
	// $ANTLR end "entryRuleStrengthWeakStatement"



	// $ANTLR start "ruleStrengthWeakStatement"
	// InternalDsl.g:1025:1: ruleStrengthWeakStatement returns [EObject current=null] : ( () otherlv_1= 'pre:' otherlv_2= '{' ( (lv_preCondition_3_0= ruleCondition ) ) otherlv_4= '}' otherlv_5= '{' ( (lv_name_6_0= ruleCodeString ) ) otherlv_7= '}' otherlv_8= 'post:' otherlv_9= '{' ( (lv_postCondition_10_0= ruleCondition ) ) otherlv_11= '}' ) ;
	public final EObject ruleStrengthWeakStatement() throws RecognitionException {
		EObject current = null;


		Token otherlv_1=null;
		Token otherlv_2=null;
		Token otherlv_4=null;
		Token otherlv_5=null;
		Token otherlv_7=null;
		Token otherlv_8=null;
		Token otherlv_9=null;
		Token otherlv_11=null;
		EObject lv_preCondition_3_0 =null;
		AntlrDatatypeRuleToken lv_name_6_0 =null;
		EObject lv_postCondition_10_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1031:2: ( ( () otherlv_1= 'pre:' otherlv_2= '{' ( (lv_preCondition_3_0= ruleCondition ) ) otherlv_4= '}' otherlv_5= '{' ( (lv_name_6_0= ruleCodeString ) ) otherlv_7= '}' otherlv_8= 'post:' otherlv_9= '{' ( (lv_postCondition_10_0= ruleCondition ) ) otherlv_11= '}' ) )
			// InternalDsl.g:1032:2: ( () otherlv_1= 'pre:' otherlv_2= '{' ( (lv_preCondition_3_0= ruleCondition ) ) otherlv_4= '}' otherlv_5= '{' ( (lv_name_6_0= ruleCodeString ) ) otherlv_7= '}' otherlv_8= 'post:' otherlv_9= '{' ( (lv_postCondition_10_0= ruleCondition ) ) otherlv_11= '}' )
			{
			// InternalDsl.g:1032:2: ( () otherlv_1= 'pre:' otherlv_2= '{' ( (lv_preCondition_3_0= ruleCondition ) ) otherlv_4= '}' otherlv_5= '{' ( (lv_name_6_0= ruleCodeString ) ) otherlv_7= '}' otherlv_8= 'post:' otherlv_9= '{' ( (lv_postCondition_10_0= ruleCondition ) ) otherlv_11= '}' )
			// InternalDsl.g:1033:3: () otherlv_1= 'pre:' otherlv_2= '{' ( (lv_preCondition_3_0= ruleCondition ) ) otherlv_4= '}' otherlv_5= '{' ( (lv_name_6_0= ruleCodeString ) ) otherlv_7= '}' otherlv_8= 'post:' otherlv_9= '{' ( (lv_postCondition_10_0= ruleCondition ) ) otherlv_11= '}'
			{
			// InternalDsl.g:1033:3: ()
			// InternalDsl.g:1034:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getStrengthWeakStatementAccess().getStrengthWeakStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,39,FOLLOW_6); 

						newLeafNode(otherlv_1, grammarAccess.getStrengthWeakStatementAccess().getPreKeyword_1());
					
			otherlv_2=(Token)match(input,47,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getStrengthWeakStatementAccess().getLeftCurlyBracketKeyword_2());
					
			// InternalDsl.g:1048:3: ( (lv_preCondition_3_0= ruleCondition ) )
			// InternalDsl.g:1049:4: (lv_preCondition_3_0= ruleCondition )
			{
			// InternalDsl.g:1049:4: (lv_preCondition_3_0= ruleCondition )
			// InternalDsl.g:1050:5: lv_preCondition_3_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getStrengthWeakStatementAccess().getPreConditionConditionParserRuleCall_3_0());
							
			pushFollow(FOLLOW_7);
			lv_preCondition_3_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getStrengthWeakStatementRule());
								}
								set(
									current,
									"preCondition",
									lv_preCondition_3_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_4=(Token)match(input,48,FOLLOW_6); 

						newLeafNode(otherlv_4, grammarAccess.getStrengthWeakStatementAccess().getRightCurlyBracketKeyword_4());
					
			otherlv_5=(Token)match(input,47,FOLLOW_20); 

						newLeafNode(otherlv_5, grammarAccess.getStrengthWeakStatementAccess().getLeftCurlyBracketKeyword_5());
					
			// InternalDsl.g:1075:3: ( (lv_name_6_0= ruleCodeString ) )
			// InternalDsl.g:1076:4: (lv_name_6_0= ruleCodeString )
			{
			// InternalDsl.g:1076:4: (lv_name_6_0= ruleCodeString )
			// InternalDsl.g:1077:5: lv_name_6_0= ruleCodeString
			{

								newCompositeNode(grammarAccess.getStrengthWeakStatementAccess().getNameCodeStringParserRuleCall_6_0());
							
			pushFollow(FOLLOW_7);
			lv_name_6_0=ruleCodeString();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getStrengthWeakStatementRule());
								}
								set(
									current,
									"name",
									lv_name_6_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.CodeString");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_7=(Token)match(input,48,FOLLOW_9); 

						newLeafNode(otherlv_7, grammarAccess.getStrengthWeakStatementAccess().getRightCurlyBracketKeyword_7());
					
			otherlv_8=(Token)match(input,38,FOLLOW_6); 

						newLeafNode(otherlv_8, grammarAccess.getStrengthWeakStatementAccess().getPostKeyword_8());
					
			otherlv_9=(Token)match(input,47,FOLLOW_4); 

						newLeafNode(otherlv_9, grammarAccess.getStrengthWeakStatementAccess().getLeftCurlyBracketKeyword_9());
					
			// InternalDsl.g:1106:3: ( (lv_postCondition_10_0= ruleCondition ) )
			// InternalDsl.g:1107:4: (lv_postCondition_10_0= ruleCondition )
			{
			// InternalDsl.g:1107:4: (lv_postCondition_10_0= ruleCondition )
			// InternalDsl.g:1108:5: lv_postCondition_10_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getStrengthWeakStatementAccess().getPostConditionConditionParserRuleCall_10_0());
							
			pushFollow(FOLLOW_7);
			lv_postCondition_10_0=ruleCondition();
			state._fsp--;


								if (current==null) {
									current = createModelElementForParent(grammarAccess.getStrengthWeakStatementRule());
								}
								set(
									current,
									"postCondition",
									lv_postCondition_10_0,
									"de.tu_bs.cs.isf.cbc.textual.tool.Dsl.Condition");
								afterParserOrEnumRuleCall();
							
			}

			}

			otherlv_11=(Token)match(input,48,FOLLOW_2); 

						newLeafNode(otherlv_11, grammarAccess.getStrengthWeakStatementAccess().getRightCurlyBracketKeyword_11());
					
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
	// $ANTLR end "ruleStrengthWeakStatement"



	// $ANTLR start "entryRuleCondition"
	// InternalDsl.g:1133:1: entryRuleCondition returns [EObject current=null] :iv_ruleCondition= ruleCondition EOF ;
	public final EObject entryRuleCondition() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCondition =null;

		try {
			// InternalDsl.g:1133:50: (iv_ruleCondition= ruleCondition EOF )
			// InternalDsl.g:1134:2: iv_ruleCondition= ruleCondition EOF
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
	// InternalDsl.g:1140:1: ruleCondition returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleCondition() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1146:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:1147:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:1147:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:1148:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:1148:3: ()
			// InternalDsl.g:1149:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getConditionAccess().getConditionAction_0(),
								current);
						
			}

			// InternalDsl.g:1155:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:1156:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:1156:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:1157:5: lv_name_1_0= ruleEString
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
	// InternalDsl.g:1178:1: entryRuleSkipStatement returns [EObject current=null] :iv_ruleSkipStatement= ruleSkipStatement EOF ;
	public final EObject entryRuleSkipStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSkipStatement =null;

		try {
			// InternalDsl.g:1178:54: (iv_ruleSkipStatement= ruleSkipStatement EOF )
			// InternalDsl.g:1179:2: iv_ruleSkipStatement= ruleSkipStatement EOF
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
	// InternalDsl.g:1185:1: ruleSkipStatement returns [EObject current=null] : ( (lv_name_0_0= ';' ) ) ;
	public final EObject ruleSkipStatement() throws RecognitionException {
		EObject current = null;


		Token lv_name_0_0=null;


			enterRule();

		try {
			// InternalDsl.g:1191:2: ( ( (lv_name_0_0= ';' ) ) )
			// InternalDsl.g:1192:2: ( (lv_name_0_0= ';' ) )
			{
			// InternalDsl.g:1192:2: ( (lv_name_0_0= ';' ) )
			// InternalDsl.g:1193:3: (lv_name_0_0= ';' )
			{
			// InternalDsl.g:1193:3: (lv_name_0_0= ';' )
			// InternalDsl.g:1194:4: lv_name_0_0= ';'
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
	// InternalDsl.g:1209:1: entryRuleCompositionStatement returns [EObject current=null] :iv_ruleCompositionStatement= ruleCompositionStatement EOF ;
	public final EObject entryRuleCompositionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleCompositionStatement =null;

		try {
			// InternalDsl.g:1209:61: (iv_ruleCompositionStatement= ruleCompositionStatement EOF )
			// InternalDsl.g:1210:2: iv_ruleCompositionStatement= ruleCompositionStatement EOF
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
	// InternalDsl.g:1216:1: ruleCompositionStatement returns [EObject current=null] : ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' ) ;
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
			// InternalDsl.g:1222:2: ( ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' ) )
			// InternalDsl.g:1223:2: ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' )
			{
			// InternalDsl.g:1223:2: ( () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}' )
			// InternalDsl.g:1224:3: () otherlv_1= '{' ( (lv_firstStatement_2_0= ruleAbstractStatement ) ) otherlv_3= '}' otherlv_4= 'intm:' otherlv_5= '[' ( (lv_intermediateCondition_6_0= ruleCondition ) ) otherlv_7= ']' otherlv_8= '{' ( (lv_secondStatement_9_0= ruleAbstractStatement ) ) otherlv_10= '}'
			{
			// InternalDsl.g:1224:3: ()
			// InternalDsl.g:1225:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getCompositionStatementAccess().getCompositionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,47,FOLLOW_8); 

						newLeafNode(otherlv_1, grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_1());
					
			// InternalDsl.g:1235:3: ( (lv_firstStatement_2_0= ruleAbstractStatement ) )
			// InternalDsl.g:1236:4: (lv_firstStatement_2_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1236:4: (lv_firstStatement_2_0= ruleAbstractStatement )
			// InternalDsl.g:1237:5: lv_firstStatement_2_0= ruleAbstractStatement
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

			otherlv_3=(Token)match(input,48,FOLLOW_27); 

						newLeafNode(otherlv_3, grammarAccess.getCompositionStatementAccess().getRightCurlyBracketKeyword_3());
					
			otherlv_4=(Token)match(input,34,FOLLOW_28); 

						newLeafNode(otherlv_4, grammarAccess.getCompositionStatementAccess().getIntmKeyword_4());
					
			otherlv_5=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_5, grammarAccess.getCompositionStatementAccess().getLeftSquareBracketKeyword_5());
					
			// InternalDsl.g:1266:3: ( (lv_intermediateCondition_6_0= ruleCondition ) )
			// InternalDsl.g:1267:4: (lv_intermediateCondition_6_0= ruleCondition )
			{
			// InternalDsl.g:1267:4: (lv_intermediateCondition_6_0= ruleCondition )
			// InternalDsl.g:1268:5: lv_intermediateCondition_6_0= ruleCondition
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
					
			otherlv_8=(Token)match(input,47,FOLLOW_8); 

						newLeafNode(otherlv_8, grammarAccess.getCompositionStatementAccess().getLeftCurlyBracketKeyword_8());
					
			// InternalDsl.g:1293:3: ( (lv_secondStatement_9_0= ruleAbstractStatement ) )
			// InternalDsl.g:1294:4: (lv_secondStatement_9_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1294:4: (lv_secondStatement_9_0= ruleAbstractStatement )
			// InternalDsl.g:1295:5: lv_secondStatement_9_0= ruleAbstractStatement
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

			otherlv_10=(Token)match(input,48,FOLLOW_2); 

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
	// InternalDsl.g:1320:1: entryRuleSelectionStatement returns [EObject current=null] :iv_ruleSelectionStatement= ruleSelectionStatement EOF ;
	public final EObject entryRuleSelectionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSelectionStatement =null;

		try {
			// InternalDsl.g:1320:59: (iv_ruleSelectionStatement= ruleSelectionStatement EOF )
			// InternalDsl.g:1321:2: iv_ruleSelectionStatement= ruleSelectionStatement EOF
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
	// InternalDsl.g:1327:1: ruleSelectionStatement returns [EObject current=null] : ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' ) ;
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
			// InternalDsl.g:1333:2: ( ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' ) )
			// InternalDsl.g:1334:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' )
			{
			// InternalDsl.g:1334:2: ( () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi' )
			// InternalDsl.g:1335:3: () otherlv_1= 'if' otherlv_2= '(' ( (lv_guards_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'then' otherlv_6= '{' ( (lv_commands_7_0= ruleAbstractStatement ) ) otherlv_8= '}' (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )* otherlv_17= 'fi'
			{
			// InternalDsl.g:1335:3: ()
			// InternalDsl.g:1336:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getSelectionStatementAccess().getSelectionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,33,FOLLOW_29); 

						newLeafNode(otherlv_1, grammarAccess.getSelectionStatementAccess().getIfKeyword_1());
					
			otherlv_2=(Token)match(input,12,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_2());
					
			// InternalDsl.g:1350:3: ( (lv_guards_3_0= ruleCondition ) )
			// InternalDsl.g:1351:4: (lv_guards_3_0= ruleCondition )
			{
			// InternalDsl.g:1351:4: (lv_guards_3_0= ruleCondition )
			// InternalDsl.g:1352:5: lv_guards_3_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_3_0());
							
			pushFollow(FOLLOW_30);
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

			otherlv_4=(Token)match(input,13,FOLLOW_31); 

						newLeafNode(otherlv_4, grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_4());
					
			otherlv_5=(Token)match(input,42,FOLLOW_6); 

						newLeafNode(otherlv_5, grammarAccess.getSelectionStatementAccess().getThenKeyword_5());
					
			otherlv_6=(Token)match(input,47,FOLLOW_8); 

						newLeafNode(otherlv_6, grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_6());
					
			// InternalDsl.g:1381:3: ( (lv_commands_7_0= ruleAbstractStatement ) )
			// InternalDsl.g:1382:4: (lv_commands_7_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1382:4: (lv_commands_7_0= ruleAbstractStatement )
			// InternalDsl.g:1383:5: lv_commands_7_0= ruleAbstractStatement
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

			otherlv_8=(Token)match(input,48,FOLLOW_32); 

						newLeafNode(otherlv_8, grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_8());
					
			// InternalDsl.g:1404:3: (otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}' )*
			loop28:
			while (true) {
				int alt28=2;
				int LA28_0 = input.LA(1);
				if ( (LA28_0==30) ) {
					alt28=1;
				}

				switch (alt28) {
				case 1 :
					// InternalDsl.g:1405:4: otherlv_9= 'elseif' otherlv_10= '(' ( (lv_guards_11_0= ruleCondition ) ) otherlv_12= ')' otherlv_13= 'then' otherlv_14= '{' ( (lv_commands_15_0= ruleAbstractStatement ) ) otherlv_16= '}'
					{
					otherlv_9=(Token)match(input,30,FOLLOW_29); 

									newLeafNode(otherlv_9, grammarAccess.getSelectionStatementAccess().getElseifKeyword_9_0());
								
					otherlv_10=(Token)match(input,12,FOLLOW_4); 

									newLeafNode(otherlv_10, grammarAccess.getSelectionStatementAccess().getLeftParenthesisKeyword_9_1());
								
					// InternalDsl.g:1413:4: ( (lv_guards_11_0= ruleCondition ) )
					// InternalDsl.g:1414:5: (lv_guards_11_0= ruleCondition )
					{
					// InternalDsl.g:1414:5: (lv_guards_11_0= ruleCondition )
					// InternalDsl.g:1415:6: lv_guards_11_0= ruleCondition
					{

											newCompositeNode(grammarAccess.getSelectionStatementAccess().getGuardsConditionParserRuleCall_9_2_0());
										
					pushFollow(FOLLOW_30);
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

					otherlv_12=(Token)match(input,13,FOLLOW_31); 

									newLeafNode(otherlv_12, grammarAccess.getSelectionStatementAccess().getRightParenthesisKeyword_9_3());
								
					otherlv_13=(Token)match(input,42,FOLLOW_6); 

									newLeafNode(otherlv_13, grammarAccess.getSelectionStatementAccess().getThenKeyword_9_4());
								
					otherlv_14=(Token)match(input,47,FOLLOW_8); 

									newLeafNode(otherlv_14, grammarAccess.getSelectionStatementAccess().getLeftCurlyBracketKeyword_9_5());
								
					// InternalDsl.g:1444:4: ( (lv_commands_15_0= ruleAbstractStatement ) )
					// InternalDsl.g:1445:5: (lv_commands_15_0= ruleAbstractStatement )
					{
					// InternalDsl.g:1445:5: (lv_commands_15_0= ruleAbstractStatement )
					// InternalDsl.g:1446:6: lv_commands_15_0= ruleAbstractStatement
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

					otherlv_16=(Token)match(input,48,FOLLOW_32); 

									newLeafNode(otherlv_16, grammarAccess.getSelectionStatementAccess().getRightCurlyBracketKeyword_9_7());
								
					}
					break;

				default :
					break loop28;
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
	// InternalDsl.g:1476:1: entryRuleSmallRepetitionStatement returns [EObject current=null] :iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF ;
	public final EObject entryRuleSmallRepetitionStatement() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleSmallRepetitionStatement =null;

		try {
			// InternalDsl.g:1476:65: (iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF )
			// InternalDsl.g:1477:2: iv_ruleSmallRepetitionStatement= ruleSmallRepetitionStatement EOF
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
	// InternalDsl.g:1483:1: ruleSmallRepetitionStatement returns [EObject current=null] : ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' ) ;
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
			// InternalDsl.g:1489:2: ( ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' ) )
			// InternalDsl.g:1490:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' )
			{
			// InternalDsl.g:1490:2: ( () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od' )
			// InternalDsl.g:1491:3: () otherlv_1= 'while' otherlv_2= '(' ( (lv_guard_3_0= ruleCondition ) ) otherlv_4= ')' otherlv_5= 'do' otherlv_6= 'inv:' otherlv_7= '[' ( (lv_invariant_8_0= ruleCondition ) ) otherlv_9= ']' otherlv_10= 'var:' otherlv_11= '[' ( (lv_variant_12_0= ruleVariant ) ) otherlv_13= ']' otherlv_14= '{' ( (lv_loopStatement_15_0= ruleAbstractStatement ) ) otherlv_16= '}' otherlv_17= 'od'
			{
			// InternalDsl.g:1491:3: ()
			// InternalDsl.g:1492:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getSmallRepetitionStatementAccess().getSmallRepetitionStatementAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,46,FOLLOW_29); 

						newLeafNode(otherlv_1, grammarAccess.getSmallRepetitionStatementAccess().getWhileKeyword_1());
					
			otherlv_2=(Token)match(input,12,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getSmallRepetitionStatementAccess().getLeftParenthesisKeyword_2());
					
			// InternalDsl.g:1506:3: ( (lv_guard_3_0= ruleCondition ) )
			// InternalDsl.g:1507:4: (lv_guard_3_0= ruleCondition )
			{
			// InternalDsl.g:1507:4: (lv_guard_3_0= ruleCondition )
			// InternalDsl.g:1508:5: lv_guard_3_0= ruleCondition
			{

								newCompositeNode(grammarAccess.getSmallRepetitionStatementAccess().getGuardConditionParserRuleCall_3_0());
							
			pushFollow(FOLLOW_30);
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

			otherlv_4=(Token)match(input,13,FOLLOW_33); 

						newLeafNode(otherlv_4, grammarAccess.getSmallRepetitionStatementAccess().getRightParenthesisKeyword_4());
					
			otherlv_5=(Token)match(input,29,FOLLOW_34); 

						newLeafNode(otherlv_5, grammarAccess.getSmallRepetitionStatementAccess().getDoKeyword_5());
					
			otherlv_6=(Token)match(input,35,FOLLOW_28); 

						newLeafNode(otherlv_6, grammarAccess.getSmallRepetitionStatementAccess().getInvKeyword_6());
					
			otherlv_7=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_7, grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_7());
					
			// InternalDsl.g:1541:3: ( (lv_invariant_8_0= ruleCondition ) )
			// InternalDsl.g:1542:4: (lv_invariant_8_0= ruleCondition )
			{
			// InternalDsl.g:1542:4: (lv_invariant_8_0= ruleCondition )
			// InternalDsl.g:1543:5: lv_invariant_8_0= ruleCondition
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

			otherlv_9=(Token)match(input,27,FOLLOW_35); 

						newLeafNode(otherlv_9, grammarAccess.getSmallRepetitionStatementAccess().getRightSquareBracketKeyword_9());
					
			otherlv_10=(Token)match(input,44,FOLLOW_28); 

						newLeafNode(otherlv_10, grammarAccess.getSmallRepetitionStatementAccess().getVarKeyword_10());
					
			otherlv_11=(Token)match(input,26,FOLLOW_4); 

						newLeafNode(otherlv_11, grammarAccess.getSmallRepetitionStatementAccess().getLeftSquareBracketKeyword_11());
					
			// InternalDsl.g:1572:3: ( (lv_variant_12_0= ruleVariant ) )
			// InternalDsl.g:1573:4: (lv_variant_12_0= ruleVariant )
			{
			// InternalDsl.g:1573:4: (lv_variant_12_0= ruleVariant )
			// InternalDsl.g:1574:5: lv_variant_12_0= ruleVariant
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
					
			otherlv_14=(Token)match(input,47,FOLLOW_8); 

						newLeafNode(otherlv_14, grammarAccess.getSmallRepetitionStatementAccess().getLeftCurlyBracketKeyword_14());
					
			// InternalDsl.g:1599:3: ( (lv_loopStatement_15_0= ruleAbstractStatement ) )
			// InternalDsl.g:1600:4: (lv_loopStatement_15_0= ruleAbstractStatement )
			{
			// InternalDsl.g:1600:4: (lv_loopStatement_15_0= ruleAbstractStatement )
			// InternalDsl.g:1601:5: lv_loopStatement_15_0= ruleAbstractStatement
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

			otherlv_16=(Token)match(input,48,FOLLOW_36); 

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
	// InternalDsl.g:1630:1: entryRuleVariant returns [EObject current=null] :iv_ruleVariant= ruleVariant EOF ;
	public final EObject entryRuleVariant() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleVariant =null;

		try {
			// InternalDsl.g:1630:48: (iv_ruleVariant= ruleVariant EOF )
			// InternalDsl.g:1631:2: iv_ruleVariant= ruleVariant EOF
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
	// InternalDsl.g:1637:1: ruleVariant returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleVariant() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1643:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:1644:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:1644:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:1645:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:1645:3: ()
			// InternalDsl.g:1646:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getVariantAccess().getVariantAction_0(),
								current);
						
			}

			// InternalDsl.g:1652:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:1653:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:1653:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:1654:5: lv_name_1_0= ruleEString
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
	// InternalDsl.g:1675:1: entryRuleJavaVariables returns [EObject current=null] :iv_ruleJavaVariables= ruleJavaVariables EOF ;
	public final EObject entryRuleJavaVariables() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleJavaVariables =null;

		try {
			// InternalDsl.g:1675:54: (iv_ruleJavaVariables= ruleJavaVariables EOF )
			// InternalDsl.g:1676:2: iv_ruleJavaVariables= ruleJavaVariables EOF
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
	// InternalDsl.g:1682:1: ruleJavaVariables returns [EObject current=null] : ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? ) ;
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
			// InternalDsl.g:1688:2: ( ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? ) )
			// InternalDsl.g:1689:2: ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? )
			{
			// InternalDsl.g:1689:2: ( () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )? )
			// InternalDsl.g:1690:3: () otherlv_1= 'JavaVariables' (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )?
			{
			// InternalDsl.g:1690:3: ()
			// InternalDsl.g:1691:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getJavaVariablesAccess().getJavaVariablesAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,24,FOLLOW_37); 

						newLeafNode(otherlv_1, grammarAccess.getJavaVariablesAccess().getJavaVariablesKeyword_1());
					
			// InternalDsl.g:1701:3: (otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}' )?
			int alt30=2;
			int LA30_0 = input.LA(1);
			if ( (LA30_0==45) ) {
				alt30=1;
			}
			switch (alt30) {
				case 1 :
					// InternalDsl.g:1702:4: otherlv_2= 'variables' otherlv_3= '{' ( (lv_variables_4_0= ruleJavaVariable ) ) (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )* otherlv_7= '}'
					{
					otherlv_2=(Token)match(input,45,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getJavaVariablesAccess().getVariablesKeyword_2_0());
								
					otherlv_3=(Token)match(input,47,FOLLOW_4); 

									newLeafNode(otherlv_3, grammarAccess.getJavaVariablesAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1710:4: ( (lv_variables_4_0= ruleJavaVariable ) )
					// InternalDsl.g:1711:5: (lv_variables_4_0= ruleJavaVariable )
					{
					// InternalDsl.g:1711:5: (lv_variables_4_0= ruleJavaVariable )
					// InternalDsl.g:1712:6: lv_variables_4_0= ruleJavaVariable
					{

											newCompositeNode(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_38);
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

					// InternalDsl.g:1729:4: (otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) ) )*
					loop29:
					while (true) {
						int alt29=2;
						int LA29_0 = input.LA(1);
						if ( (LA29_0==16) ) {
							alt29=1;
						}

						switch (alt29) {
						case 1 :
							// InternalDsl.g:1730:5: otherlv_5= ',' ( (lv_variables_6_0= ruleJavaVariable ) )
							{
							otherlv_5=(Token)match(input,16,FOLLOW_4); 

												newLeafNode(otherlv_5, grammarAccess.getJavaVariablesAccess().getCommaKeyword_2_3_0());
											
							// InternalDsl.g:1734:5: ( (lv_variables_6_0= ruleJavaVariable ) )
							// InternalDsl.g:1735:6: (lv_variables_6_0= ruleJavaVariable )
							{
							// InternalDsl.g:1735:6: (lv_variables_6_0= ruleJavaVariable )
							// InternalDsl.g:1736:7: lv_variables_6_0= ruleJavaVariable
							{

														newCompositeNode(grammarAccess.getJavaVariablesAccess().getVariablesJavaVariableParserRuleCall_2_3_1_0());
													
							pushFollow(FOLLOW_38);
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
							break loop29;
						}
					}

					otherlv_7=(Token)match(input,48,FOLLOW_2); 

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
	// InternalDsl.g:1763:1: entryRuleJavaVariable returns [EObject current=null] :iv_ruleJavaVariable= ruleJavaVariable EOF ;
	public final EObject entryRuleJavaVariable() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleJavaVariable =null;

		try {
			// InternalDsl.g:1763:53: (iv_ruleJavaVariable= ruleJavaVariable EOF )
			// InternalDsl.g:1764:2: iv_ruleJavaVariable= ruleJavaVariable EOF
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
	// InternalDsl.g:1770:1: ruleJavaVariable returns [EObject current=null] : ( () ( (lv_name_1_0= ruleEString ) ) ) ;
	public final EObject ruleJavaVariable() throws RecognitionException {
		EObject current = null;


		AntlrDatatypeRuleToken lv_name_1_0 =null;


			enterRule();

		try {
			// InternalDsl.g:1776:2: ( ( () ( (lv_name_1_0= ruleEString ) ) ) )
			// InternalDsl.g:1777:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			{
			// InternalDsl.g:1777:2: ( () ( (lv_name_1_0= ruleEString ) ) )
			// InternalDsl.g:1778:3: () ( (lv_name_1_0= ruleEString ) )
			{
			// InternalDsl.g:1778:3: ()
			// InternalDsl.g:1779:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getJavaVariableAccess().getJavaVariableAction_0(),
								current);
						
			}

			// InternalDsl.g:1785:3: ( (lv_name_1_0= ruleEString ) )
			// InternalDsl.g:1786:4: (lv_name_1_0= ruleEString )
			{
			// InternalDsl.g:1786:4: (lv_name_1_0= ruleEString )
			// InternalDsl.g:1787:5: lv_name_1_0= ruleEString
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
	// InternalDsl.g:1808:1: entryRuleGlobalConditions returns [EObject current=null] :iv_ruleGlobalConditions= ruleGlobalConditions EOF ;
	public final EObject entryRuleGlobalConditions() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleGlobalConditions =null;

		try {
			// InternalDsl.g:1808:57: (iv_ruleGlobalConditions= ruleGlobalConditions EOF )
			// InternalDsl.g:1809:2: iv_ruleGlobalConditions= ruleGlobalConditions EOF
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
	// InternalDsl.g:1815:1: ruleGlobalConditions returns [EObject current=null] : ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? ) ;
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
			// InternalDsl.g:1821:2: ( ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? ) )
			// InternalDsl.g:1822:2: ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? )
			{
			// InternalDsl.g:1822:2: ( () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )? )
			// InternalDsl.g:1823:3: () otherlv_1= 'GlobalConditions' (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )?
			{
			// InternalDsl.g:1823:3: ()
			// InternalDsl.g:1824:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getGlobalConditionsAccess().getGlobalConditionsAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,23,FOLLOW_39); 

						newLeafNode(otherlv_1, grammarAccess.getGlobalConditionsAccess().getGlobalConditionsKeyword_1());
					
			// InternalDsl.g:1834:3: (otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}' )?
			int alt32=2;
			int LA32_0 = input.LA(1);
			if ( (LA32_0==28) ) {
				alt32=1;
			}
			switch (alt32) {
				case 1 :
					// InternalDsl.g:1835:4: otherlv_2= 'conditions' otherlv_3= '{' ( (lv_conditions_4_0= ruleCondition ) ) (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )* otherlv_7= '}'
					{
					otherlv_2=(Token)match(input,28,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getGlobalConditionsAccess().getConditionsKeyword_2_0());
								
					otherlv_3=(Token)match(input,47,FOLLOW_4); 

									newLeafNode(otherlv_3, grammarAccess.getGlobalConditionsAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1843:4: ( (lv_conditions_4_0= ruleCondition ) )
					// InternalDsl.g:1844:5: (lv_conditions_4_0= ruleCondition )
					{
					// InternalDsl.g:1844:5: (lv_conditions_4_0= ruleCondition )
					// InternalDsl.g:1845:6: lv_conditions_4_0= ruleCondition
					{

											newCompositeNode(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_38);
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

					// InternalDsl.g:1862:4: (otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) ) )*
					loop31:
					while (true) {
						int alt31=2;
						int LA31_0 = input.LA(1);
						if ( (LA31_0==16) ) {
							alt31=1;
						}

						switch (alt31) {
						case 1 :
							// InternalDsl.g:1863:5: otherlv_5= ',' ( (lv_conditions_6_0= ruleCondition ) )
							{
							otherlv_5=(Token)match(input,16,FOLLOW_4); 

												newLeafNode(otherlv_5, grammarAccess.getGlobalConditionsAccess().getCommaKeyword_2_3_0());
											
							// InternalDsl.g:1867:5: ( (lv_conditions_6_0= ruleCondition ) )
							// InternalDsl.g:1868:6: (lv_conditions_6_0= ruleCondition )
							{
							// InternalDsl.g:1868:6: (lv_conditions_6_0= ruleCondition )
							// InternalDsl.g:1869:7: lv_conditions_6_0= ruleCondition
							{

														newCompositeNode(grammarAccess.getGlobalConditionsAccess().getConditionsConditionParserRuleCall_2_3_1_0());
													
							pushFollow(FOLLOW_38);
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
							break loop31;
						}
					}

					otherlv_7=(Token)match(input,48,FOLLOW_2); 

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
	// InternalDsl.g:1896:1: entryRuleRenaming returns [EObject current=null] :iv_ruleRenaming= ruleRenaming EOF ;
	public final EObject entryRuleRenaming() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleRenaming =null;

		try {
			// InternalDsl.g:1896:49: (iv_ruleRenaming= ruleRenaming EOF )
			// InternalDsl.g:1897:2: iv_ruleRenaming= ruleRenaming EOF
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
	// InternalDsl.g:1903:1: ruleRenaming returns [EObject current=null] : ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? ) ;
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
			// InternalDsl.g:1909:2: ( ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? ) )
			// InternalDsl.g:1910:2: ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? )
			{
			// InternalDsl.g:1910:2: ( () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )? )
			// InternalDsl.g:1911:3: () otherlv_1= 'Renaming' (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )?
			{
			// InternalDsl.g:1911:3: ()
			// InternalDsl.g:1912:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getRenamingAccess().getRenamingAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,25,FOLLOW_40); 

						newLeafNode(otherlv_1, grammarAccess.getRenamingAccess().getRenamingKeyword_1());
					
			// InternalDsl.g:1922:3: (otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}' )?
			int alt34=2;
			int LA34_0 = input.LA(1);
			if ( (LA34_0==40) ) {
				alt34=1;
			}
			switch (alt34) {
				case 1 :
					// InternalDsl.g:1923:4: otherlv_2= 'renames' otherlv_3= '{' ( (lv_rename_4_0= ruleRename ) ) ( (lv_rename_5_0= ruleRename ) )* otherlv_6= '}'
					{
					otherlv_2=(Token)match(input,40,FOLLOW_6); 

									newLeafNode(otherlv_2, grammarAccess.getRenamingAccess().getRenamesKeyword_2_0());
								
					otherlv_3=(Token)match(input,47,FOLLOW_6); 

									newLeafNode(otherlv_3, grammarAccess.getRenamingAccess().getLeftCurlyBracketKeyword_2_1());
								
					// InternalDsl.g:1931:4: ( (lv_rename_4_0= ruleRename ) )
					// InternalDsl.g:1932:5: (lv_rename_4_0= ruleRename )
					{
					// InternalDsl.g:1932:5: (lv_rename_4_0= ruleRename )
					// InternalDsl.g:1933:6: lv_rename_4_0= ruleRename
					{

											newCompositeNode(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_2_0());
										
					pushFollow(FOLLOW_41);
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

					// InternalDsl.g:1950:4: ( (lv_rename_5_0= ruleRename ) )*
					loop33:
					while (true) {
						int alt33=2;
						int LA33_0 = input.LA(1);
						if ( (LA33_0==47) ) {
							alt33=1;
						}

						switch (alt33) {
						case 1 :
							// InternalDsl.g:1951:5: (lv_rename_5_0= ruleRename )
							{
							// InternalDsl.g:1951:5: (lv_rename_5_0= ruleRename )
							// InternalDsl.g:1952:6: lv_rename_5_0= ruleRename
							{

													newCompositeNode(grammarAccess.getRenamingAccess().getRenameRenameParserRuleCall_2_3_0());
												
							pushFollow(FOLLOW_41);
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
							break loop33;
						}
					}

					otherlv_6=(Token)match(input,48,FOLLOW_2); 

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
	// InternalDsl.g:1978:1: entryRuleRename returns [EObject current=null] :iv_ruleRename= ruleRename EOF ;
	public final EObject entryRuleRename() throws RecognitionException {
		EObject current = null;


		EObject iv_ruleRename =null;

		try {
			// InternalDsl.g:1978:47: (iv_ruleRename= ruleRename EOF )
			// InternalDsl.g:1979:2: iv_ruleRename= ruleRename EOF
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
	// InternalDsl.g:1985:1: ruleRename returns [EObject current=null] : ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' ) ;
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
			// InternalDsl.g:1991:2: ( ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' ) )
			// InternalDsl.g:1992:2: ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' )
			{
			// InternalDsl.g:1992:2: ( () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}' )
			// InternalDsl.g:1993:3: () otherlv_1= '{' otherlv_2= 'type' ( (lv_type_3_0= ruleEString ) ) otherlv_4= 'function' ( (lv_function_5_0= ruleEString ) ) otherlv_6= 'newName' ( (lv_newName_7_0= ruleEString ) ) otherlv_8= '}'
			{
			// InternalDsl.g:1993:3: ()
			// InternalDsl.g:1994:4: 
			{

							current = forceCreateModelElement(
								grammarAccess.getRenameAccess().getRenameAction_0(),
								current);
						
			}

			otherlv_1=(Token)match(input,47,FOLLOW_42); 

						newLeafNode(otherlv_1, grammarAccess.getRenameAccess().getLeftCurlyBracketKeyword_1());
					
			otherlv_2=(Token)match(input,43,FOLLOW_4); 

						newLeafNode(otherlv_2, grammarAccess.getRenameAccess().getTypeKeyword_2());
					
			// InternalDsl.g:2008:3: ( (lv_type_3_0= ruleEString ) )
			// InternalDsl.g:2009:4: (lv_type_3_0= ruleEString )
			{
			// InternalDsl.g:2009:4: (lv_type_3_0= ruleEString )
			// InternalDsl.g:2010:5: lv_type_3_0= ruleEString
			{

								newCompositeNode(grammarAccess.getRenameAccess().getTypeEStringParserRuleCall_3_0());
							
			pushFollow(FOLLOW_43);
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
					
			// InternalDsl.g:2031:3: ( (lv_function_5_0= ruleEString ) )
			// InternalDsl.g:2032:4: (lv_function_5_0= ruleEString )
			{
			// InternalDsl.g:2032:4: (lv_function_5_0= ruleEString )
			// InternalDsl.g:2033:5: lv_function_5_0= ruleEString
			{

								newCompositeNode(grammarAccess.getRenameAccess().getFunctionEStringParserRuleCall_5_0());
							
			pushFollow(FOLLOW_44);
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
					
			// InternalDsl.g:2054:3: ( (lv_newName_7_0= ruleEString ) )
			// InternalDsl.g:2055:4: (lv_newName_7_0= ruleEString )
			{
			// InternalDsl.g:2055:4: (lv_newName_7_0= ruleEString )
			// InternalDsl.g:2056:5: lv_newName_7_0= ruleEString
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

			otherlv_8=(Token)match(input,48,FOLLOW_2); 

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
	public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000C28200100220L});
	public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000004000000000L});
	public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000004200000L});
	public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000021060L});
	public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000020060L});
	public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00000000001AC800L});
	public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x00000000001AE800L});
	public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000004041002L});
	public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000022060L});
	public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000012000L});
	public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000000040002L});
	public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000020020L});
	public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000008020060L});
	public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x00000000C0000000L});
	public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000200000000002L});
	public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0001000000010000L});
	public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000010000002L});
	public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000010000000002L});
	public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0001800000000000L});
	public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000001000000000L});
}
