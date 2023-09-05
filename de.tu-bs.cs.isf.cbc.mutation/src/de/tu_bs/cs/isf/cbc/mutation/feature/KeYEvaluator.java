package de.tu_bs.cs.isf.cbc.mutation.feature;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.key_project.util.collection.ImmutableSet;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IEvaluator;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IReport;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.JavaInfo;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.java.abstraction.Method;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.util.KeYTypeUtil;
import de.uka.ilkd.key.util.MiscTools;

public class KeYEvaluator implements IEvaluator {

	@Override
	public IReport evaluate(File[] files) {
		
//		File[] files = new File[] { new File(
//				"/Users/leonschaer/Desktop/MA Workspace/MutationFramework/muJava/muJavaMutantStructure/result/Traditional/int_return1()/SDL_1/MutationTarget3.java"),
//				new File(
//						"/Users/leonschaer/Desktop/MA Workspace/MutationFramework/muJava/muJavaMutantStructure/result/Traditional/int_return2()/SDL_2/MutationTarget3.java") };
		// File location = new File("/Users/leonschaer/Desktop/MA
		// Workspace/MutationFramework/muJava/muJavaMutantStructure/result/Traditional/int_return1()/SDL_1/MutationTarget3.java");
		// // Path to the source code folder/file or to a *.proof file
		List<File> classPaths = null; // Optionally: Additional specifications for API classes
		File bootClassPath = null; // Optionally: Different default specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to consider
		
		JavaDirectoryLoader loader = new JavaDirectoryLoader();
		if(Framework.getTargetConfigString("ProjectPath") != null) {
			File[] otherJavaFiles = loader.loadData(Framework.getTargetConfigString("ProjectPath"));
			classPaths = new LinkedList<File>();
			classPaths.add(new File(Framework.getTargetConfigString("ProjectPath")));
//			for (File f: otherJavaFiles) {
//				classPaths.add(f);
//			}
		}
		
		
		KeYReport report = new KeYReport();
		report.mutationCount = files.length;

		for (File f : files) {
			try {
				// Ensure that Taclets are parsed
				if (!ProofSettings.isChoiceSettingInitialised()) {
					KeYEnvironment<?> env = KeYEnvironment.load(f, classPaths, bootClassPath, includes);
					env.dispose();
				}
				// Set Taclet options
				ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
				HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
				HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
				newSettings.putAll(MiscTools.getDefaultTacletOptions());
				choiceSettings.setDefaultChoices(newSettings);
				// Load source code
				KeYEnvironment<?> env = KeYEnvironment.load(f, classPaths, bootClassPath, includes);
				// env.getLoadedProof() returns the performed proof if a *.proof file is loaded
				try {
					
					//setup for location to extract method name
					JavaInfo jf = env.getJavaInfo();
					Method methodToProof = null;
					String[] pathParts = f.getAbsolutePath().split(File.separator);
					String muOp = pathParts[pathParts.length-2];
					String MethodPart = pathParts[pathParts.length-3];
					String MethodName = MethodPart.substring(MethodPart.indexOf("_") + 1,
					MethodPart.indexOf("("));
					System.out.println(MethodPart + " : " + MethodName);
//					for (Method m: jf.getMethods(jf.getKeYJavaType(f.getName().substring(0, f.getName().indexOf("."))))) {
//						System.out.println(m.getName());
//						if(m.getName().equals(MethodName))
//							methodToProof = m;
//					}
					
					// List all specifications of all types in the source location (not classPaths
					// and bootClassPath)
					final List<Contract> proofContracts = new LinkedList<Contract>();
					Set<KeYJavaType> kjts = env.getJavaInfo().getAllKeYJavaTypes();
					for (KeYJavaType type : kjts) {
						if (!KeYTypeUtil.isLibraryClass(type)) {
							ImmutableSet<IObserverFunction> targets = env.getSpecificationRepository()
									.getContractTargets(type);
							// System.out.println(targets.isEmpty()); //gibt Contracte mit getAllContracts!
							for (IObserverFunction target : targets) {
								ImmutableSet<Contract> contracts = env.getSpecificationRepository().getContracts(type,
										target);
								for (Contract contract : contracts) {
									System.err.println(contract.getTarget());
									String[] contractTargetParts = contract.getTarget().toString().split(":");
									if (contractTargetParts[contractTargetParts.length - 1].equals(MethodName)) {
										proofContracts.add(contract);									
									}
								}
							}
						}
					}
					//if we have filtered all valid contracts and thus proofContracts is empty:
					if (proofContracts.isEmpty()) {
						report.mutationCount--;
					}
					
					// for (Contract contract: env.getSpecificationRepository().getAllContracts()) {
					// System.out.println(contract.getName());
					// }
					// List<IProgramMethod> methods = new LinkedList<IProgramMethod>();
					// for(KeYJavaType kjt: jf.getAllKeYJavaTypes()) {
					// System.err.println(kjt.getFullName());
					//
					// for(IProgramMethod m: jf.getAllProgramMethods(kjt)) {
					// methods.add(m);
					// System.out.println(m.getFullName());
					// }
					// }
					

					// Perform proofs
					for (Contract contract : proofContracts) {
						Proof proof = null;
						try {
							// Create proof
							proof = env.createProof(contract.createProofObl(env.getInitConfig(), contract));
							// Set proof strategy options
							StrategyProperties sp = proof.getSettings().getStrategySettings()
									.getActiveStrategyProperties();
							sp.setProperty(StrategyProperties.METHOD_OPTIONS_KEY, StrategyProperties.METHOD_CONTRACT);
							sp.setProperty(StrategyProperties.DEP_OPTIONS_KEY, StrategyProperties.DEP_ON);
							sp.setProperty(StrategyProperties.QUERY_OPTIONS_KEY, StrategyProperties.QUERY_ON);
							sp.setProperty(StrategyProperties.NON_LIN_ARITH_OPTIONS_KEY,
									StrategyProperties.NON_LIN_ARITH_DEF_OPS);
							sp.setProperty(StrategyProperties.STOPMODE_OPTIONS_KEY,
									StrategyProperties.STOPMODE_NONCLOSE);
							proof.getSettings().getStrategySettings().setActiveStrategyProperties(sp);
							// Make sure that the new options are used
							int maxSteps = 10000;
							ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setMaxSteps(maxSteps);
							ProofSettings.DEFAULT_SETTINGS.getStrategySettings().setActiveStrategyProperties(sp);
							proof.getSettings().getStrategySettings().setMaxSteps(maxSteps);
							proof.setActiveStrategy(
									proof.getServices().getProfile().getDefaultStrategyFactory().create(proof, sp));
							// Start auto mode
							env.getUi().getProofControl().startAndWaitForAutoMode(proof);
							// Show proof result
							boolean closed = proof.openGoals().isEmpty();
							System.out.println("Contract '" + contract.getDisplayName() + "' of " + contract.getTarget()
									+ " is " + (closed ? "verified" : "still open") + ".");
							if(closed) {
								report.mutantMap.put(muOp, MethodName);
							}
							if(!closed) report.killedMutants++;
						} catch (ProofInputException e) {
							System.out.println("Exception at '" + contract.getDisplayName() + "' of "
									+ contract.getTarget() + ":");
							e.printStackTrace();
							report.killedMutants++;
						} finally {
							if (proof != null) {
								proof.dispose(); // Ensure always that all instances of Proof are disposed
							}
						}
					}
				} finally {
					env.dispose(); // Ensure always that all instances of KeYEnvironment are disposed
				}
			} catch (ProblemLoaderException e) {
				System.out.println("Exception at '" + f + "':");
				e.printStackTrace();
				report.killedMutants++;
			}

		}
//		System.out.println(report.print());
		return report;
	}

}
