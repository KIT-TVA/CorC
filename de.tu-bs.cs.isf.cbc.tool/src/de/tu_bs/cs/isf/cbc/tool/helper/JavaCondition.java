package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.BinaryNode;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.QuantorNode;

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
	
	private void getAtomReps(Node node, final List<String> lst) {
		if (node == null) {
			return;
		}
		if (node.getType() != TokenType.REL && (node.getLeft() != null || node.getRight() != null)) {
			getAtomReps(node.getLeft(), lst);
			getAtomReps(node.getRight(), lst);
			return;
		}
		lst.add(node.getRep());
	}
	
	private Node findNextImpl(Node node) {
		if (node == null) {
			return null;
		}
		if (node.getType() == TokenType.IMPL) {
			return node;
		}
		var left = findNextImpl(node.getLeft());
		if (left != null) {
			return left;
		}
		var right = findNextImpl(node.getRight());
		if (right != null) {
			return right;
		}
		return null;
	}
		
	private void createBranches() {
		BranchType type;
		String branchCondition;
		List<String> branchAssertions = new ArrayList<String>();
		final Pattern p = Pattern.compile("\\w+\\.\\s*\\<\\s*\\w+\\s*\\>");
		Matcher m;
		int branchNr = 0;
		
		var testung = "";
		
		for (var pred : predicates) {
			TranslatedPredicate trans = new TranslatedPredicate(pred, branchNr);
			this.translatedPredicates.add(trans);
			branchNr++;
			/*
			//translatePredicate(pred);
			// TODO: write function that translates each predicate correctly..
			// ignore corc keywords until they are supported by the generator
			m = p.matcher(pred.getRep());
			if (m.find()) {
				continue;
			}
			if (pred.getType() == TokenType.KEY) {
				var quantorBodyConditions = new ArrayList<String>();
				var iterConditions = new ArrayList<String>();
				if (pred.getRep().contains("forall") || pred.getRep().contains("exists")) {
					var nextImpl = findNextImpl(pred);
					type = pred.getRep().contains("forall") ? BranchType.FORALL : BranchType.EXISTS;
					branchCondition = "";
					var branch = new Branch(type, branchCondition, branchAssertions);
					getAtomReps(pred, branchAssertions);
					if (nextImpl != null) {
						getAtomReps(nextImpl.getRight(), quantorBodyConditions);
					}
					for (var rep : branchAssertions) {
						if (!quantorBodyConditions.contains(rep)) {
							iterConditions.add(rep);
						}
					}
					branch.setIterType(((QuantorNode)pred).getIteratorType());
					branch.setIterName(((QuantorNode)pred).getIterator());
					branch.setIterConditions(iterConditions);
					branch.setQuantorBodyConditions(quantorBodyConditions);
					this.branches.add(branch);
				}
			} else if (pred.getType() == TokenType.IMPL) {
				type = BranchType.IMPL;
				branchCondition = pred.getLeft().getRep();
				getAtomReps(pred.getRight(), branchAssertions);
				this.branches.add(new Branch(type, branchCondition, branchAssertions));
			} else if (pred.getType() == TokenType.EQUI) {
				type = BranchType.EQUI;
				branchCondition = pred.getLeft().getRep();
				getAtomReps(pred.getRight(), branchAssertions);
				this.branches.add(new Branch(type, branchCondition, branchAssertions));
			} else {
				type = BranchType.NONE;
				branchCondition = "";
				branchAssertions.add(pred.getRep());
				this.branches.add(new Branch(type, branchCondition, branchAssertions));
				//Console.println("JavaConditionError: Branch type couldn't be identified.");
			}
			branchAssertions.clear(); */
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
