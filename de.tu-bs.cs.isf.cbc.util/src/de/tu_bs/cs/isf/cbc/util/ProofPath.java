package de.tu_bs.cs.isf.cbc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.StreamSupport;

import de.uka.ilkd.key.logic.SequentFormula;
import de.uka.ilkd.key.proof.Node;

/**
 * A {@link ProofPath} object keeps track of one individual branch of a proof
 * tree. It is instantiated for the root {@link Node} of the proof tree and
 * traverses iteratively through the {@link Node}s. For child of a new branch, a
 * new ProofPath will instantiated recursively.
 * 
 * When the traversing is done and the current branch / {@link ProofPath}
 * contains an open goal, it is appended to the {@link OpenGoalHelper#list}.
 * 
 */
public class ProofPath {
	// The mapping from sequent formulas to their respective type at the current
	// node
	public HashMap<SequentFormula, CEType> map;
	// A list of assumptions that has been made (string form)
	public ArrayList<String> assumptions;
	// The current and the next node of the proof tree
	public Node current;
	public Node next;

	/**
	 * Instantiates a ProofPath object
	 * 
	 * @param node
	 *            the starting {@link Node} of this {@link ProofPath} object
	 * @param map
	 *            the {@link SequentFormula} => {@link CEType} mapping for the
	 *            {@link Node} provided
	 * @param assumptions
	 *            the list of assumptions for this branch
	 */
	public ProofPath(Node node, HashMap<SequentFormula, CEType> map, ArrayList<String> assumptions) {
		this.map = map;
		this.assumptions = assumptions;
		current = node;

		if (!current.isClosed() && current.childrenCount() > 0) {
			next = current.child(0);
			traverse();
		}

		// We check whether we arrived at an open goal and then save it in the list
		if (current.name().equals("OPEN GOAL")) {
			CounterExampleGenerator.list.add(this);
		}
	}

	/**
	 * Updates the {@link SequentFormula} => {@link CEType} mapping a single time
	 * for the {@link Node}s provided.
	 * 
	 * @param old
	 *            the {@link SequentFormula} which has a rule applied to it
	 * @param currentNode
	 *            the current {@link Node}
	 * @param nextNode
	 *            the following {@link Node}
	 * @param workingMap
	 *            the current {@link SequentFormula} => {@link CEType} mapping
	 */
	void update(SequentFormula old, Node currentNode, Node nextNode, HashMap<SequentFormula, CEType> workingMap) {
		int diff = nextNode.sequent().size() - currentNode.sequent().size();
		if (diff == -1) {
			workingMap.remove(old);
		} else if (diff >= 0) {
			nextNode.sequent().iterator().forEachRemaining((formula) -> {
				if (!workingMap.containsKey(formula)) {
					CEType type = workingMap.get(old);
					workingMap.put(formula, type);
				}
			});
			if (diff == 0 || StreamSupport.stream(nextNode.sequent().spliterator(), false)
					.noneMatch((seq) -> old.equals(seq))) {
				CEType type = workingMap.remove(old);
			}
		} else {
			// This should never happen, as KeY removes at max one formula from the sequent
			Console.println("Error while calculating tree");
		}
	}

	/**
	 * Finds out what a KeY created variable in a branch label stands for.
	 * 
	 * @deprecated This function is currently not used.
	 * 
	 * @param formula
	 *            the complete formula
	 * @param variable
	 *            the variable in question
	 * @return the variable instantiation
	 */
	private String findFormula(String formula, String variable) {
		int start = formula.indexOf("elem-update(" + variable + ")(if-then-else(") + 27 + variable.length();
		int end = start;
		int parentheses = 1;
		boolean x = true;
		while (parentheses > 0) {
			if (formula.charAt(end) == '(') {
				if (x) {
					x = false;
				} else {
					parentheses++;
				}
			} else if (formula.charAt(end) == ')') {
				parentheses--;
			}
			end++;
		}
		return formula.substring(start, end);
	}

	/**
	 * Adds an assumption to the list of assumptions. If the assumption is already
	 * in the list, does not add anything.
	 * 
	 * @param assumptions
	 *            the list of assumptions
	 * @param str
	 *            the assumption to be added.
	 */
	private void addAssumption(ArrayList<String> assumptions, String str) {
		if (!assumptions.contains(str)) {
			assumptions.add(str);
		}
	}

	/**
	 * Iteratively traverse the current branch until we either hit the last node (an
	 * open goal) or a branching point. If a branching point is hit, we recursively
	 * create a new {@link ProofPath} object for every new branch.
	 */
	private void traverse() {
		while (!current.leaf()) {
			// This returns the sequent formula which is affected by the rule application
			// (in its before state)
			SequentFormula old = current.getAppliedRuleApp().posInOccurrence().sequentFormula();
			if (current.childrenCount() > 1) {
				// if we have multiple children, we are at a branching point
				// create new ProofPath for each child with its corresponding new root node
				current.childrenIterator().forEachRemaining((newRoot) -> {
					// these commands make copies of map and list
					HashMap<SequentFormula, CEType> newMap = new HashMap<SequentFormula, CEType>(map);
					ArrayList<String> newAssumptions = new ArrayList<String>(assumptions);

					if (newRoot.getNodeInfo().getBranchLabel() != null) {
						// Checks for a special branch label (assumption), where KeY creates a helper
						// variable
						// E.g. the label can be "if x = TRUE", where x is a variable created by KeY,
						// thus the user has no information about it
						if (newRoot.getNodeInfo().getBranchLabel().startsWith("if ")) {
							String[] splits = newRoot.getNodeInfo().getBranchLabel().split(" ");
							// String seq_formula =
							// newRoot.getAppliedRuleApp().posInOccurrence().sequentFormula().toString();
							// addAssumption(newAssumptions, findFormula(seq_formula, splits[1]) + " " +
							// splits[2]);
							addAssumption(newAssumptions, newRoot.getNodeInfo().getBranchLabel().substring(3)
									+ ", please check with KeY what " + splits[1] + " stands for.");
						} else {
							addAssumption(newAssumptions, newRoot.getNodeInfo().getBranchLabel());
						}
					} else {
						addAssumption(newAssumptions, "Please check with KeY for assumptions that we couldn't label.");
					}
					update(old, current, newRoot, newMap);
					new ProofPath(newRoot, newMap, newAssumptions);
				});
				return;
			} else {
				update(old, current, next, map);
				try {
					current = next;
					next = next.child(0);
				} catch (Exception ex) {
					return;
				}
			}
		}
	}
}