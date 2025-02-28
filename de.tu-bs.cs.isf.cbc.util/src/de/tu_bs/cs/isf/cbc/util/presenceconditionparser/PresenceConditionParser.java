package de.tu_bs.cs.isf.cbc.util.presenceconditionparser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.ovgu.featureide.fm.core.configuration.Selection;

/**
 * This class holds a parser, which parses the presence condition of
 * variable predicates. It parses them into a set of 
 * partial feature configurations which can then be used to calculate all
 * required configurations for a full proof of such a variable predicate.
 * 
 * @author Markus
 */
public class PresenceConditionParser {
	private static final String OPERATORS = "&|-()!";
	private final String presenceCondition;
	private final List<String> featureList;

	private int cursor;
	private char current;
	private List<Set<SelectionInfo>> requiredFeatureConfigs;


	
	public PresenceConditionParser(String presenceCondition, List<String> featureList) {
		this.cursor = 0;
		this.featureList = featureList;
		this.requiredFeatureConfigs = new ArrayList<Set<SelectionInfo>>();
		//remove spaces from presence condition
		this.presenceCondition = presenceCondition.replaceAll("\\s", "");
		this.current = presenceCondition.charAt(0);
	}
	
	public List<Set<SelectionInfo>> parseCondition() {
		PresenceConditionNode root = parse();
		List<List<SelectionInfo>> list = new ArrayList<List<SelectionInfo>>();
		list.add(new ArrayList<PresenceConditionParser.SelectionInfo>());
		requiredFeatureConfigs = evaluateAST(root, false);
		return List.copyOf(this.requiredFeatureConfigs);
	}
	
	public List<Set<SelectionInfo>> evaluateAST(PresenceConditionNode node, boolean not) {
		List<Set<SelectionInfo>> current = new ArrayList<Set<SelectionInfo>>();

		if (node instanceof PresenceConditionAndNode andNode) {
			List<Set<SelectionInfo>> listA = evaluateAST(andNode.getPartA(), false);
			List<Set<SelectionInfo>> listB = evaluateAST(andNode.getPartB(), false);
			
			for (Set<SelectionInfo> as: listA) {
				for (Set<SelectionInfo> bs: listB) {
					as.addAll(bs);
				}
			}
			current.addAll(listA);
		}
		
		if (node instanceof PresenceConditionOrNode orNode) {
			current.addAll(evaluateAST(orNode.getPartA(), false));
			current.addAll(evaluateAST(orNode.getPartB(), false));
		}
		
		if (node instanceof PresenceConditionNotNode notNode) {
			current.addAll(evaluateAST(notNode.getExpr(), !not));
		}
		
		if (node instanceof PresenceConditionBracketNode bracketNode) {
			current.addAll(evaluateAST(bracketNode.getExpr(), not));
		}
		
		if (node instanceof PresenceConditionFeatureNode featureNode) {
			if (current.isEmpty()) {
				current.add(new HashSet<PresenceConditionParser.SelectionInfo>());
			}
			current.forEach(list -> list.add(new SelectionInfo(featureNode.getName(), !not ? Selection.SELECTED : Selection.UNSELECTED)));
		}
		
		return current;
	}
	
	public PresenceConditionNode parse() {
		PresenceConditionNode left = null;
		PresenceConditionNode rhs = null;

		if (!OPERATORS.contains(current + "")) {
			 left = parseFeature();
		}

		while(cursor < presenceCondition.length() - 1) {
			if (current == ')')
				break;
			switch (current) {
				case '&':
					this.next();
					rhs = parse();
					left = new PresenceConditionAndNode(left, rhs);
					break;
				case '|':
					this.next();
					rhs = parse();
					left = new PresenceConditionOrNode(left, rhs);
					break;
				case '!':
					this.next();
					rhs = parse();
					left = new PresenceConditionNotNode(rhs);
					break;
				case '-':
					this.next();
					this.next();
					rhs = parse();
					left = new PresenceConditionOrNode(new PresenceConditionNotNode(left), rhs);
					break;
				case '(':
					this.next();
					rhs = parse();
					if (current != ')') {
						throw new RuntimeException("Bad Token");
					}
					this.next();
					left = new PresenceConditionBracketNode(rhs);
					break;
				default:
					throw new RuntimeException("Bad Token");
			}
		}
		
		return left;
	}
	
	private PresenceConditionNode parseFeature() {
		StringBuilder nameBuilder = new StringBuilder();
		while(!OPERATORS.contains(this.current + "")) {
			nameBuilder.append(this.current);
			if (!this.next()) 
				break;
		}
		
		String featureName = nameBuilder.toString(); 
		
		/*If feature model is null skip feature semantic check*/
		if (this.featureList != null && !this.featureList.isEmpty()) {
			if (!this.featureList.contains(featureName)) {
				String errorInfo = presenceCondition.substring(0, cursor-1) + "  <-ERROR-  " + presenceCondition.substring(cursor);
				throw new RuntimeException("Illegal Feature mentioned: " + errorInfo);
			}
		}
		
		return new PresenceConditionFeatureNode(featureName);
	}
	
	private boolean next() {
		if (this.cursor < this.presenceCondition.length() - 1) {
			this.cursor++;
			current=this.presenceCondition.charAt(cursor);
			return true;
		}
		
		return false;
	}

	public class SelectionInfo {
		private final String featureName;
		private final Selection selection;
		
		public SelectionInfo(String featureName, Selection selection) {
			this.featureName = featureName;
			this.selection = selection;
		}
		
		public String getName() {
			return this.featureName;
		}
		
		public Selection getSelection() {
			return selection;
		}
		
		@Override
		public String toString() {
			return " (" + featureName + "," + selection +") ";
		}
	}
}


