package de.tu_bs.cs.isf.cbc.util;

import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.Node;
import de.uka.ilkd.key.logic.SequentFormula;
import de.uka.ilkd.key.logic.Term;
import de.uka.ilkd.key.pp.LogicPrinter;
import de.uka.ilkd.key.pp.NotationInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uka.ilkd.key.java.Services;
import de.uka.ilkd.key.smt.SMTProblem;
import de.uka.ilkd.key.smt.SMTSolverResult;
import de.uka.ilkd.key.smt.SMTSolverResult.ThreeValuedTruth;
import de.uka.ilkd.key.smt.SolverLauncher;
import de.uka.ilkd.key.smt.solvertypes.SolverType;
import de.uka.ilkd.key.smt.solvertypes.SolverTypeImplementation;
import de.uka.ilkd.key.smt.solvertypes.SolverTypes;
import de.uka.ilkd.key.settings.DefaultSMTSettings;
/*
import de.uka.ilkd.key.smt.solvertypes.SolverType;
import de.uka.ilkd.key.smt.solvertypes.SolverTypeImplementation;
import de.uka.ilkd.key.smt.solvertypes.SolverTypes;
import de.uka.ilkd.key.settings.DefaultSMTSettings;*/
import de.uka.ilkd.key.settings.ProofIndependentSMTSettings;
import de.uka.ilkd.key.settings.ProofIndependentSettings;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Variant;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

import java.util.stream.StreamSupport;

public class CounterExampleGenerator {
	public static ArrayList<ProofPath> list;

	private static Services serv;

	private static Variant variant;
	private static String postCondition;
	private static HashMap<Condition, CEType> conditions = new HashMap<>();
	private static ArrayList<Renamer> renames;

	private static final String OPEN = Pattern.quote("\\<{");
	private static final String CLOSE = Pattern.quote("}\\>");

	private static void calculateProofPaths(Node current) {
		HashMap<String, CEType> conditionMapping = getConditionMapping();
		HashMap<SequentFormula, CEType> map = new HashMap<>();
		list = new ArrayList<>();

		// Skip initial simplification statements
		while (current.getAppliedRuleApp().rule().displayName() != "andLeft"
				&& current.getAppliedRuleApp().rule().displayName() != "orRight"
				&& current.getAppliedRuleApp().rule().displayName() != "notLeft") {
			current = current.child(0);
		}

		// Skip initial andLeft statements
		while (current.getAppliedRuleApp().rule().displayName() == "andLeft"
				|| current.getAppliedRuleApp().rule().displayName() == "orRight"
				|| current.getAppliedRuleApp().rule().displayName() == "notLeft") {
			// intercept the notLeft rule applications and TODO
			if (current.getAppliedRuleApp().rule().displayName() == "notLeft") {
				SequentFormula seq = current.getAppliedRuleApp().posInOccurrence().sequentFormula();
				String seqString = convertInequality(prettify(seq));
				if (conditionMapping.containsKey(seqString)) {
					CEType x = conditionMapping.remove(seqString);
					map.put(seq, x);
					updateOnce(seq, current, current.child(0), map);
				}
			}
			current = current.child(0);
		}

		Iterator<SequentFormula> iter = current.sequent().iterator();
		ArrayList<SequentFormula> remainingSequents = new ArrayList<>();

		int count = 0;

		while (iter.hasNext()) {
			SequentFormula seq = iter.next();
			if (map.containsKey(seq)) {
				continue;
			}
			String seqString = prettify(seq);
			if (conditionMapping.containsKey(seqString)) {
				map.put(seq, conditionMapping.remove(seqString));
			} else {
				if (seqString.startsWith("wellFormed(")) {
					map.put(seq, CEType.OTHER);
				} else if (seqString.endsWith(".<created> = TRUE")) {
					map.put(seq, CEType.OTHER);
				} else if (seqString.endsWith(".<created> = FALSE")) {
					map.put(seq, CEType.IMP);
				} else if (isPostCondition(seq)) {
					map.put(seq, CEType.POST);
				} else if (isVariant(seq)) {
					map.put(seq, CEType.VAR);
				} else {
					remainingSequents.add(seq);
					count++;
				}
			}
		}

		String[] remainingConditions = conditionMapping.keySet().toArray(new String[0]);

		if (count == 1 && conditionMapping.size() == 1) {
			current.sequent().iterator().forEachRemaining((seq) -> {
				if (!map.containsKey(seq)) {
					CEType type = conditionMapping.entrySet().iterator().next().getValue();
					map.put(seq, type);
				}
			});
		} else if (count > 1) {

			if (conditionMapping.size() == 0) {
				for (SequentFormula seq : remainingSequents) {
					map.put(seq, CEType.UN);
				}
			} else {

				int[][] best = new int[conditionMapping.size()][count];

				for (int i = 0; i < best.length; i++) {
					for (int j = 0; j < best[i].length; j++) {
						best[i][j] = LevenshteinCompare.getDistance(remainingConditions[i],
								prettify(remainingSequents.get(j)));
					}
				}

				for (int i = 0; i < count; i++) {
					int[] sol = min(best);
					map.put(remainingSequents.get(sol[1]), conditionMapping.get(remainingConditions[sol[0]]));
					for (int k = 0; k < best[0].length; k++) {
						best[k][sol[1]] = Integer.MAX_VALUE;
					}
				}
			}

		}

		map.forEach((x, y) -> {
			if (y == null) {
				map.put(x, CEType.UN);
			}
		});

		new ProofPath(current, map, new ArrayList<>()); // TODO: Transform succedent formulas to antecedent
	}

	private static HashMap<String, CEType> getConditionMapping() {
		HashMap<String, CEType> conditionMapping = new HashMap<>();

		if (conditions != null) {
			conditions.forEach((cond, type) -> removeAnds(cond.getName())
					.forEach((y) -> conditionMapping.put(applyRenaming(y), type)));
		}

		if (variant != null) {
			removeAnds(variant.getName()).forEach((x) -> conditionMapping.put(applyRenaming(x), CEType.VAR));
		}

		return conditionMapping;
	}

	private static String prettify(Term t) {
		LogicPrinter logicPrinter = LogicPrinter.purePrinter(new NotationInfo(), serv);
		try {
			logicPrinter.printTerm(t);
			String result = logicPrinter.result().toString();
			result = result.replace("\n", " ");
			return result.strip().replace("  ", " ");
		} catch (Exception ex) {
			return "Error while printing SequentFormula";
		}
	}

	private static String prettify(SequentFormula seq) {
		return prettify(seq.formula());
	}

	private static String convertInequality(String str) {
		return str.replaceAll("^([!])([^|&<>=*/+-]+ ?)=( ?[^|&<>=*/+-]+) ?$", "$2!=$3");
	}

	private static void updateOnce(SequentFormula old, Node currentNode, Node nextNode,
			HashMap<SequentFormula, CEType> workingMap) {
		nextNode.sequent().iterator().forEachRemaining((seq) -> {
			if (StreamSupport.stream(currentNode.sequent().spliterator(), false)
					.noneMatch((oldseq) -> oldseq.equals(seq))) {
				workingMap.put(seq, workingMap.remove(old));
			}
		});

	}

	private static boolean isPostCondition(SequentFormula seq) {
		if (variant != null) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(OPEN + ".*" + CLOSE + "\\(.*" + postCondition + ".*\\)$");
			Matcher matcher = pattern.matcher(prettify(seq).replace(" ", "").replace("\n", ""));
			return matcher.find();
		}
	}

	private static boolean isVariant(SequentFormula seq) {
		if (variant != null) {
			String v = Pattern.quote(applyRenaming(variant.getName()));
			Pattern pattern = Pattern.compile("\\{variant:=" + v + "\\}.*\\(" + v + " < variant & " + v + " >= 0\\)$");
			Matcher matcher = pattern.matcher(prettify(seq).replace("\n", ""));
			return matcher.find();
		} else {
			return false;
		}
	}

	private static String applyRenaming(String str) {
		for (Renamer renamer : renames) {
			str = renamer.rename(str);
		}
		return str.replace("  ", " ").strip();
	}

	private static int[] min(int[][] array) {
		int[] re = new int[2];
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] < min) {
					min = array[i][j];
					re[0] = i;
					re[1] = j;
				}
			}
		}
		return re;
	}

	private static ArrayList<String> removeAnds(String str) {
		ArrayList<String> list = new ArrayList<>();
		int parentheses = 0;
		int last = 0;

		while (str.startsWith("(") && str.endsWith(")") && isEnclosed(str)) {
			str = str.substring(1, str.length() - 1);
		}

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				parentheses++;
			} else if (str.charAt(i) == ')') {
				parentheses--;
			}
			if (parentheses == 0 && str.charAt(i) == '&') {
				list.addAll(removeAnds(removeLeadingAmpersand(str.substring(last, i))));
				last = i;
			}
		}
		if (list.isEmpty()) {
			list.add(removeLeadingAmpersand(str.substring(last)));
		} else {
			list.addAll(removeAnds(removeLeadingAmpersand(str.substring(last))));
		}
		return list;
	}

	private static boolean isEnclosed(String str) {
		int parentheses = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				parentheses++;
			} else if (str.charAt(i) == ')') {
				parentheses--;
			}
			if (parentheses == 0 && i < str.length() - 1) {
				return false;
			}
		}
		return true;
	}

	private static String removeLeadingAmpersand(String str) {
		if (str.startsWith("&")) {
			str = str.substring(1);
		}
		return str.strip();
	}

	public static void calculateExample(Proof proof) throws SettingsException {
		Console.println("  Start generating a counter example...");
		serv = proof.getServices();
		calculateProofPaths(proof.root());
		SMTProblem problem = null;

		for (int i = 0; i < list.size(); i++) {
			var path = list.get(i);
			problem = new SMTProblem(proof.getOpenGoal(path.current));
			SMTSolverResult result = runZ3(problem, proof);
			if (result.isValid() == ThreeValuedTruth.FALSIFIABLE) {
				String counterexample = problem.getSolvers().iterator().next().getRawSolverOutput();
				SolverOutputCleaner cleaner = new SolverOutputCleaner();
				cleaner.clean(counterexample);
				Console.print(cleaner.cleaned(), Colors.BLUE);
				Console.println();
				return;
			}
		}
		if (list.size() == 0) {
			Console.println("  Proof does not have a valid proof path.");
		} else if (problem.getFinalResult().isValid() == ThreeValuedTruth.VALID) {
			Console.println("  Z3 could prove that the program fulfills it's specification.");
		} else if (problem.getFinalResult().isValid() == ThreeValuedTruth.UNKNOWN) {
			Console.println("  A counterexample could not be generated.");
		}
	}

	private static SMTSolverResult runZ3(SMTProblem problem, Proof proof) {
		ProofIndependentSMTSettings sett = ProofIndependentSettings.DEFAULT_INSTANCE.getSMTSettings();
		sett.setStoreSMTTranslationToFile(true);
		DefaultSMTSettings settings = new DefaultSMTSettings(proof.getSettings().getSMTSettings(), sett,
				proof.getSettings().getNewSMTSettings(), proof);
		SolverLauncher launcher = new SolverLauncher(settings);
		SolverType z3 = SolverTypes.getSolverTypes().stream()
				.filter(it -> it.getClass().equals(SolverTypeImplementation.class) && it.getName().equals("Z3_CE"))
				.findFirst().orElse(null);
		launcher.launch(problem, serv, z3);

		SMTSolverResult result = problem.getFinalResult();
		return result;
	}
}
