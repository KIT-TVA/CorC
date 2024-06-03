package de.tu_bs.cs.isf.cbc.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.key_project.util.collection.ImmutableSet;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.tool.FileSystemProofRepository;
import de.tu_bs.cs.isf.cbc.tool.IProofRepository;
import de.tu_bs.cs.isf.cbc.util.statistics.StatDataCollector;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofGraphCollection;
import de.tu_bs.cs.isf.commands.toolbar.handler.proofgraphs.ProofNode;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.control.ProofControl;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.java.ConvertException;
import de.uka.ilkd.key.java.PosConvertException;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.macros.CompleteAbstractProofMacro;
import de.uka.ilkd.key.macros.ContinueAbstractProofMacro;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.Statistics;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.util.KeYTypeUtil;
import de.uka.ilkd.key.util.MiscTools;

public class KeYInteraction {
	private static String lastErrorMessage = "";
	public static int num = 0;
	
	public final static String ABSTRACT_PROOF_FULL = "abstract_full_proof";
	public final static String ABSTRACT_PROOF_BEGIN = "abstract_proof_begin";
	public final static String ABSTRACT_PROOF_COMPLETE = "abstract_proof_complete";
	public final static String ABSTRACT_PROOF_GRAPH_BEGIN = "abstract_proof_graph_begin";
	
	public static Proof startKeyProof(String proofType, File location, IProgressMonitor monitor, boolean inlining, CbCFormula formula,
			AbstractStatement statement, String problem, String uri, String forbiddenRules) {
		Proof proof = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			Map<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			proof = env.getLoadedProof();
						
			// Set proof strategy options
			StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
			if (inlining)
				sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
			else
				sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_CONTRACT);// METHOD_EXPAND
			sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_EXPAND);
			sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
			sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);//
			sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY, StrategyProperties.NON_LIN_ARITH_DEF_OPS);
			sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_NONCLOSE);
			sp.setProperty(StrategyProperties.ABSTRACT_PROOF_FIRST_ORDER_GOALS_FORBIDDEN, "true");
			if (proofType.equals(ABSTRACT_PROOF_BEGIN)) {
				sp.setProperty(StrategyProperties.ABSTRACT_PROOF_FORBIDDEN_RULE_SETS, forbiddenRules + ",expand_def,cut,cut_direct"); //default
				sp.setProperty(StrategyProperties.ABSTRACT_PROOF_FORBIDDEN_RULES, forbiddenRules + ",definition_axiom,ifthenelse_split"); //default
			} else {
				sp.setProperty(StrategyProperties.ABSTRACT_PROOF_FORBIDDEN_RULE_SETS, "");
				sp.setProperty(StrategyProperties.ABSTRACT_PROOF_FORBIDDEN_RULES, "");
			}
			//sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_ON);
			//sp.setProperty(StrategyProperties.QUERYAXIOM_OPTIONS_KEY, StrategyProperties.QUERYAXIOM_OFF);
			proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
			
			// Make sure that the new options are used
			int maxSteps = Integer.MAX_VALUE;
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
			ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
			proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
			proof.setActiveStrategy(proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));

			// Handle type of proof
			ProofControl proofControl = env.getProofControl();
			switch (proofType) {
			case ABSTRACT_PROOF_FULL:
				Console.println("  Start proof: " + location.getName());
				if (monitor != null) {
					env.getUi().getProofControl().startAutoMode(proof);
					while (env.getUi().getProofControl().isInAutoMode()) {
						if (monitor.isCanceled()) {
							env.getUi().getProofControl().stopAndWaitAutoMode();
							Console.println("  Proof is canceled.");
						}
					}
				} else {
					env.getUi().getProofControl().startAndWaitForAutoMode(proof);
				}
				break;
			case ABSTRACT_PROOF_BEGIN:
				Console.println("  Start partial proof: " + location.getName());
		        proofControl.runMacro(proof.root(), new ContinueAbstractProofMacro(), null);
		        proofControl.waitWhileAutoMode();		        
				break;
			case ABSTRACT_PROOF_COMPLETE:
				Console.println("  Finish partial proof: " + location.getName());
				proofControl.runMacro(proof.root(), new CompleteAbstractProofMacro(), null);
		        proofControl.waitWhileAutoMode();		        
				break;
			}			

			// Show proof result
			try {
				//DEBUG START
				/*Consumer<RuleApp> rule = x -> System.out.println(x.rule().displayName());
				proof.root().name();
				Node n = proof.root();
				while(n.childrenIterator().hasNext()) {
					n = n.childrenIterator().next();
					//System.out.println(n.serialNr() + n.name()); //TODO debug
				}		
				//System.out.println("-----------------------");
				//System.out.println("	Proof Type: " + proofType);
				//System.out.println("	Proof nodes: " + proof.countNodes());
				Console.println("time: " + proof.getAutoModeTime() + "        nodes:" + proof.countNodes());
				//System.out.println("-----------------------");*/
				//DEBUG END
				
				
				String locationWithoutFileEnding = location.toString().substring(0,
						location.toString().indexOf("."));
				var keyFile = new File(locationWithoutFileEnding + ".proof");

				/* TODO: KeyNewVersion */
				proof.saveToFile(location);
				
				
				try {
					// TODO: inlining may be important too
					StatDataCollector collector = new StatDataCollector();
					collector.collectCorcStatistics(proof, formula, statement, problem, uri);
				} catch (RuntimeException e) {
						Console.println(
								"Error: Statistical data collection failed. Please add Ids by right click on diagram in project explorer.");	
				}

//				printStatistics(proof, inlining);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (ProblemLoaderException e) {
			handleError(e);
		}
		return proof;
	}
	
	private static void handleError(ProblemLoaderException ex) {
		Throwable error = ex.getCause();
		
		if (lastErrorMessage != null && lastErrorMessage.equals(ex.getMessage())) {
			Console.println(ex.getMessage());
			return;
		} else {
			lastErrorMessage = ex.getMessage();
		}
		
		if (error.getClass() == ProofInputException.class) {
			Console.println("  A ProofInputException occured.");
			Console.println("  This happens when the function or method could not be located.");
			Console.println();
			Console.println("To fix this error, try:");
			Console.println("  > Check the function’s spelling in the diagram as well as in the java file");
			Console.println("  > Check whether the function is correctly declared static / nonstatic");
			Console.println("  > Check the renaming field for any spelling errors");
			Console.println("  > Check whether the function resides in the right class / file");
			Console.println("  > Check whether the return type of the function matches");
			/*
			if (error.getCause() instanceof RecognitionException) {
				RecognitionException recE = (RecognitionException)error.getCause();
				var inputStream = recE.input.toString();
				var beforeError = inputStream.substring(0, recE.index);
				var afterError = inputStream.substring(recE.index, inputStream.length());
				String line = CodeHandler.createHorizontalLine(beforeError + "\n" + afterError);
				beforeError = CodeHandler.createVerticalLines(beforeError, line);
				afterError = CodeHandler.createVerticalLines(afterError, line);
				Console.println("Input:");
				Console.println(line);
				Console.println(beforeError, Colors.GREEN);
				Console.println(afterError, Colors.RED);
				Console.println(line);
			} else {
			}*/
			error.printStackTrace();
		} else if (error.getClass() == PosConvertException.class) {
			Console.println("  A PosConvertException occured.");
			Console.println("  This happens when the function or method decleration does not correspond with its use in the CorC editor.");
			String info = ex.getCause().toString();
			info = info.substring(info.indexOf("\n") + 1);
			Console.println();
			Console.println("  " + info);
			Console.println();
			Console.println("  To fix this error, try:");
			Console.println("  > Check the the function's definition, especially the parameters");
			Console.println("  > Check your usage of the function in the CorC editor");
			Console.println("  > Consider using a classpath if this is a classtype that cannot be resolved");
		} else if (error.getClass() == ConvertException.class) {
			Console.println("  A ConvertException occured.");
			Console.println("  This happens when a java file has syntactic errors in it.");
			String info = ex.getCause().toString();
			info = info.substring(info.indexOf(":") + 2, info.indexOf("\n"));
			Console.println();
			Console.println("  " + info);
			Console.println();
			Console.println("  To fix this error, try:");
			Console.println("  > Check the file for syntax errors");
		} else if (error.getClass() == IllegalArgumentException.class) {
			Console.println("  An IllegalArgumentException occured.");
			Console.println("  This happens when a parameter mismatch between the usage in CorC diagrams and its implementation in the java file exist.");
			String info = ex.getCause().toString();
			info = info.substring(info.indexOf("\n") + 1);
			Console.println();
			Console.println("  " + info);
			Console.println();
			Console.println("  To fix this error, try:");
			Console.println("  > Check the the function's definition, especially the parameters");
			Console.println("  > Check your usage of the function in the CorC editor");
		} else {
			Console.println("  Exception at '" + ex.getCause() + "'");
			ex.printStackTrace();
		}
	}

	public static void startKeYProofFirstContract(File location, int proofCounter) {
		File keyFile = null;
		List<File> classPaths = null; // Optionally: Additional specifications
										// for API classes
		File bootClassPath = null; // Optionally: Different default
									// specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to
									// consider
		try {
			// Ensure that Taclets are parsed
			if (!ProofSettings.isChoiceSettingInitialised()) {
				KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
				env.dispose();
			}
			// Set Taclet options
			ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
			Map<String, String> oldSettings = choiceSettings.getDefaultChoices();
			HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
			newSettings.putAll(MiscTools.getDefaultTacletOptions());
			newSettings.put("runtimeExceptions", "runtimeExceptions:ban");
			choiceSettings.setDefaultChoices(newSettings);
			// Load source code
			KeYEnvironment<?> env = KeYEnvironment.load(location, classPaths, bootClassPath, includes);
			// proof = env.getLoadedProof();
			try {
				// List all specifications of all types in the source location
				// (not classPaths and bootClassPath)
				final List<Contract> proofContracts = new LinkedList<Contract>();
				Set<KeYJavaType> kjts = env.getJavaInfo().getAllKeYJavaTypes();
				for (KeYJavaType type : kjts) {
					if (!KeYTypeUtil.isLibraryClass(type)) {
						ImmutableSet<IObserverFunction> targets = env.getSpecificationRepository()
								.getContractTargets(type);
						for (IObserverFunction target : targets) {
							ImmutableSet<Contract> contracts = env.getSpecificationRepository().getContracts(type,
									target);
							for (Contract contract : contracts) {
								proofContracts.add(contract);
							}
						}
					}
				}
				// Perform proofs
				Contract contract = proofContracts.get(0);
				Proof proof = null;
				try {
					// Create proof
					proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
					// Set proof strategy options
					StrategyProperties sp = proof.getSettings().getStrategySettings().getActiveStrategyProperties();
					sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_EXPAND);
					sp.setProperty(StrategyProperties.LOOP_OPTIONS_KEY, StrategyProperties.LOOP_INVARIANT);
					sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
					sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_RESTRICTED);// StrategyProperties.QUERY_ON
					sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY,
							StrategyProperties.NON_LIN_ARITH_DEF_OPS);
					sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY, StrategyProperties.STOPMODE_DEFAULT);
					proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
					// Make sure that the new options are used
					int maxSteps = 5000;
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
					ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
					proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
					proof.setActiveStrategy(
							proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
					// Start auto mode
//						MainWindow.getInstance().setVisible(true);
					env.getUi().getProofControl().startAndWaitForAutoMode(proof);
					// Show proof result
					Console.println("  Proof is closed: " + proof.openGoals().isEmpty() + "\n");
					try {
						String locationWithoutFileEnding = location.toString().substring(0,
								location.toString().indexOf("."));
						keyFile = new File(locationWithoutFileEnding + ".proof");
		/* TODO: KeyNewVersion */
//						ProofSaver.saveToFile(keyFile, proof);
						proof.saveToFile(keyFile);
						IWorkspace workspace = ResourcesPlugin.getWorkspace();
						IPath iLocation = Path.fromOSString(keyFile.getAbsolutePath());
						IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
						ifile.refreshLocal(0, null);
					} catch (IOException | CoreException e) {
						e.printStackTrace();
					}
				} catch (ProofInputException e) {
					Console.println(
							"Exception at '" + contract.getDisplayName() + "' of " + contract.getTarget() + ":");
					e.printStackTrace();
				} finally {
					if (proof != null) {
						proof.dispose(); // Ensure always that all instances
											// of Proof are disposed
					}
				}
			} finally {
				env.dispose(); // Ensure always that all instances of
								// KeYEnvironment are disposed
			}
			MainWindow.getInstance().loadProblem(keyFile);
			MainWindow.getInstance().setVisible(true);
		} catch (ProblemLoaderException e) {
			Console.println("Exception at '" + e.getCause() + "'");
			e.printStackTrace();
		}
	}

	private static void printStatistics(Proof proof, boolean inlining) throws IOException {
		Statistics s = proof.getStatistics();
		if (inlining)
			Console.println("Inlining");
		else
			Console.println("Contracting");
		Console.println("Statistics: \n\t nodes: " + s.nodes // + "\n\t rule apps: " + s.totalRuleApps
				+ "\n\t time in Millis: " + s.timeInMillis);

//		RHelper helper = new RHelper();
//		
//		String exampleFileString = helper.createStatisticFileString(s, proof);
//		
//		helper.createStatisticFiles("test", exampleFileString);

	}

}
