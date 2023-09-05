package de.tu_bs.cs.isf.cbc.mutation.feature;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.key_project.util.collection.ImmutableSet;

import de.tu_bs.cs.isf.cbc.mutation.interfaces.IEvaluator;
import de.tu_bs.cs.isf.cbc.mutation.interfaces.IReport;
import de.uka.ilkd.key.control.KeYEnvironment;
import de.uka.ilkd.key.java.JavaInfo;
import de.uka.ilkd.key.java.PrettyPrinter;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.java.abstraction.Method;
import de.uka.ilkd.key.java.declaration.ParameterDeclaration;
import de.uka.ilkd.key.logic.op.IObserverFunction;
import de.uka.ilkd.key.logic.op.IProgramMethod;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ChoiceSettings;
import de.uka.ilkd.key.settings.ProofSettings;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.strategy.StrategyProperties;
import de.uka.ilkd.key.util.KeYTypeUtil;
import de.uka.ilkd.key.util.MiscTools;
import mujava.MutationSystem;

public class KeYMethodEvaluator implements IEvaluator {

	private String signature = "public static int[] DutchFlag ( int[]  A)";
	
	@Override
	public IReport evaluate(File[] files) {
		List<File> classPaths = null; // Optionally: Additional specifications for API classes
		File bootClassPath = null; // Optionally: Different default specifications for Java API
		List<File> includes = null; // Optionally: Additional includes to consider
		
		String SourceFileLocation = Framework.getTargetConfigString("Source");
		File verificationTarget = new File(Framework.getTargetConfigString("ProjectPath"));
		signature = Framework.getTargetConfigString("MethodSignature");
		
		KeYReport report = new KeYReport();
		report.mutationCount = files.length;
		
		String muOp = "";

		for (File file : files) {
			try {
				//Replace Original with Mutant for Verification
				try {
					FileUtils.copyFile(file, new File(SourceFileLocation));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//setup for location to extract method name
				String[] pathParts = file.getAbsolutePath().split(Pattern.quote(File.separator));
				muOp = pathParts[pathParts.length-2];
				String MethodPart = pathParts[pathParts.length-3];
				String MethodName = MethodPart.substring(MethodPart.indexOf("_") + 1,
				MethodPart.indexOf("("));
				String MethodReturn = MethodPart.substring(0, MethodPart.indexOf("_"));
				String MethodParams = MethodPart.substring(MethodPart.indexOf("(") + 1).replace(")", "");
				
				//File f = new File(SourceFileLocation);
				
				// Ensure that Taclets are parsed
				if (!ProofSettings.isChoiceSettingInitialised()) {
					KeYEnvironment<?> env = KeYEnvironment.load(verificationTarget, classPaths, bootClassPath, includes);
					env.dispose();
				}
				// Set Taclet options
				ChoiceSettings choiceSettings = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
				HashMap<String, String> oldSettings = choiceSettings.getDefaultChoices();
				HashMap<String, String> newSettings = new HashMap<String, String>(oldSettings);
				newSettings.putAll(MiscTools.getDefaultTacletOptions());
				choiceSettings.setDefaultChoices(newSettings);
				// Load source code
				KeYEnvironment<?> env = KeYEnvironment.load(verificationTarget, classPaths, bootClassPath, includes);
				// env.getLoadedProof() returns the performed proof if a *.proof file is loaded
				try {
					
//					
//					JavaInfo jf = env.getJavaInfo();
//					Method methodToProof = null;
//					//String[] pathParts = file.getAbsolutePath().split(File.separator);
					
//					System.out.println(MethodPart + " : " + MethodReturn + " : " + MethodName + " : " + MethodParams);
////					for (Method m: jf.getMethods(jf.getKeYJavaType(f.getName().substring(0, f.getName().indexOf("."))))) {
////						System.out.println(m.getName());
////						if(m.getName().equals(MethodName))
////							methodToProof = m;
////					}
//					
//					// List all specifications of all types in the source location (not classPaths
//					// and bootClassPath)
//					final List<Contract> proofContracts = new LinkedList<Contract>();
//					Set<KeYJavaType> kjts = env.getJavaInfo().getAllKeYJavaTypes();
//					for (KeYJavaType type : kjts) {
//						if (!KeYTypeUtil.isLibraryClass(type)) {
//							ImmutableSet<IObserverFunction> targets = env.getSpecificationRepository()
//									.getContractTargets(type);
//							// System.out.println(targets.isEmpty()); //gibt Contracte mit getAllContracts!
//							for (IObserverFunction target : targets) {
//								ImmutableSet<Contract> contracts = env.getSpecificationRepository().getContracts(type,
//										target);
//								for (Contract contract : contracts) {
////									System.err.println(contract.getTarget());
////									System.out.println("target string: " + contract.getTarget().getType());
////									for (KeYJavaType kjt: contract.getTarget().getParamTypes()) {
////										System.out.println("param: " + kjt);
////									}
//									
//									System.out.println(contract.getName());
//									System.out.println("PARAMS JAVA TARGET");
//									for (KeYJavaType t: contract.getTarget().getParamTypes()) {
//										System.out.println(t.getJavaType().getFullName());
//									}
//									System.out.println(contract.getTarget().getType().getJavaType().getFullName());
//									
//									for (IProgramMethod m: jf.getAllProgramMethods(type)) {
//										if(m.getMethodDeclaration().getParameters().size() >= 1 && m.getMethodDeclaration().getTypeReference() != null) {
//											System.out.println(m.getName());
//											for (ParameterDeclaration param: m.getMethodDeclaration().getParameters()) {
//												System.out.println(param);
//											}
//											//System.out.println(m.getMethodDeclaration().getParameters().get(0).toString());
//											System.out.println(m.getMethodDeclaration().getTypeReference().toString());
//											StringWriter sw = new StringWriter();
//											System.out.println("SW PP: " + m.getMethodDeclaration().toString(new PrettyPrinter(sw), sw));
//											System.out.println("MetDecl toString: " + m.getMethodDeclaration());
//											System.out.println(m.getMethodDeclaration().toString().contains("\n"));
//											System.out.println("Met Return: " + m.getReturnType().getJavaType());
//										//System.out.println(m.toString());
//										}
//									}
//									
//									String[] contractTargetParts = contract.getTarget().toString().split(":");
//									System.out.println(contract.getDisplayName() + " : " + file.getName().substring(0, file.getName().length() - 6));
//									if (contractTargetParts[contractTargetParts.length - 1].equals(MethodName) &&
//										contractTargetParts[0].equals(file.getName().substring(0, file.getName().length() - 6))) {
//										proofContracts.add(contract);									
//									}
//								}
//							}
//						}
//					}
					
					List<Contract> proofContracts = getContractsForMethod(env);
					
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
					
					boolean allVerified = true;
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
							report.timingMap.put(muOp, proof.getStatistics().timeInMillis);
							System.out.println("Contract '" + contract.getDisplayName() + "' of " + contract.getTarget()
									+ " is " + (closed ? "verified" : "still open") + ".");
//							if(closed) {
//								
//								report.mutantMap.put(muOp, MethodName);
//							}
							if(!closed) allVerified = false;
						} catch (ProofInputException e) {
							System.out.println("Exception at '" + contract.getDisplayName() + "' of "
									+ contract.getTarget() + ":");
							e.printStackTrace();
							report.exceptionMap.put(muOp, e.getCause().toString());
							report.killedMutants++;
						} finally {
							if (proof != null) {
								proof.dispose(); // Ensure always that all instances of Proof are disposed
							}
						}
					}
					if(allVerified) {
						report.mutantMap.put(muOp, MethodName);
					} else {
						report.killedMutants++;
						String muOpName = muOp.split("_")[0];
						if (report.notVerifiedMap.containsKey(muOpName)) {
							report.notVerifiedMap.replace(muOpName, report.notVerifiedMap.get(muOpName) + 1);
						} else {
							report.notVerifiedMap.put(muOpName, 1);
						}
					}
				} finally {
					env.dispose(); // Ensure always that all instances of KeYEnvironment are disposed
				}
			} catch (ProblemLoaderException e) {
				System.out.println("Exception at '" + file + "':");
				e.printStackTrace();
				report.exceptionMap.put(muOp, e.getCause().toString());
				report.killedMutants++;
			}

		}
//		System.out.println(report.print());
		//Replace last mutant with the original
		try {
			FileUtils.copyFile(new File(MutationSystem.SYSTEM_HOME + File.separator + "src").listFiles()[0], new File(SourceFileLocation));
			System.out.println("Wrote Original back into ProjectPath");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return report;
	}
	
	public List<Contract> getContractsForMethod(KeYEnvironment env) {
		List<Contract> contracts = new LinkedList<Contract>();
		JavaInfo jf = env.getJavaInfo();
		Set<KeYJavaType> kjts = jf.getAllKeYJavaTypes();
		
		String targetFilePath = Framework.getTargetConfigString("Source");
		//TODO quadra backslah shouldn't be compatible with Mac
		String[] pathParts = targetFilePath.split(Pattern.quote(File.separator));
		String className = pathParts[pathParts.length - 1];
		className = className.split("\\.")[0];
		System.out.println(className);
		
		for (KeYJavaType kjt: kjts) {
			for (IProgramMethod pm: jf.getAllProgramMethods(kjt)) {
				StringWriter sw = new StringWriter();
//				if (pm.getMethodDeclaration().toString(new PrettyPrinter(sw), sw).trim().contains(signature.trim())) {
//				System.out.println(pm.getMethodDeclaration().toString(new PrettyPrinter(sw), sw).replaceAll("[ \t\b\f\r\n]", ""));
				if (pm.getMethodDeclaration().toString(new PrettyPrinter(sw), sw).replaceAll("[ \t\b\f\r\n]", "").contains(signature.replaceAll("[ \t\b\f\r\n]", ""))) {
					for (Contract c: env.getSpecificationRepository().getOperationContracts(kjt, pm)) {
						String contractName = c.getName().split("\\[")[0];
						//contractName can contain . in case of packages, see bank.BankCard
						//Therefore className is always the last component
						if (contractName.contains(".")) {
							contractName = contractName.split("\\.")[contractName.split("\\.").length - 1];
						}
//						System.out.println("contractName: " + contractName);
						if (contractName.equals(className)) {
							contracts.add(c);
							System.err.println(c.getName());
						}
					}
				}
			}
		}
		
		return contracts;
	}

}