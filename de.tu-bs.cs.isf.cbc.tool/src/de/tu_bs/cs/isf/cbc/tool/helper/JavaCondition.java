package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.Node;

/**
 * This class can be used to translate conditions given as an AST into java.
 * @author Fynn
 */
public class JavaCondition {
	final private List<Node> predicates;
	final private List<TranslatedPredicate> translatedPredicates;
	final private Node root;
	int pos;
	
	public JavaCondition(Node root) {
		translatedPredicates = new ArrayList<TranslatedPredicate>();
		predicates = new ArrayList<Node>();
		pos = 0;
		this.root = root;
		getPredicates(root);
		createBranches();
	}
		
	private void createBranches() {
		int branchNr = 0;
		
		for (var pred : predicates) {
			TranslatedPredicate trans = new TranslatedPredicate(pred, branchNr);
			this.translatedPredicates.add(trans);
			branchNr++;
		}
	}
	
	private void getPredicates(Node cur) {
		if (cur == null) {
			return;
		}
		if (cur.getType() == TokenType.AND) {
			getPredicates(cur.getLeft());
			getPredicates(cur.getRight());
			return;
		}
		predicates.add(cur);
	}
	
	private boolean hasNext() {
		return pos < translatedPredicates.size();
	}
	
	public List<String> getBranchConditions() {
		final var output = new ArrayList<String>();
		for (var b : translatedPredicates) {
			//output.add(b.getBranchCondition());
		}
		return output;
	}
	
	public TranslatedPredicate getNext() {
		if (hasNext()) {
			final var branch = translatedPredicates.get(pos);
			pos++;
			return branch;
		}
		pos = 0;
		return null;
	}

	public String getRep() {
		return root.getRep();
	}
}
