package de.tu_bs.cs.isf.cbc.tool.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.BinaryNode;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.tool.helper.conditionparser.QuantorNode;

public class TranslatedPredicate {
	final private LinkedList<Branch> branches;
	final private int branchNr;
	int pos;
	
	public TranslatedPredicate(Node predicate, int branchNr) {
		branches = new LinkedList<Branch>();
		this.branchNr = branchNr;
		translatePredicate(predicate);
		pos = this.branches.size() - 1;
	}
	
	private boolean hasNext() {
		return pos >= 0;
	}
	
	public Branch getNext() {
		if (hasNext()) {
			final var branch = branches.get(pos);
			pos--;
			return branch;
		}
		pos = this.branches.size() - 1;
		return null;
	}
	
	public String getRep() {
		String code = "";
		Branch branch;
		if (containsExists()) {
			code += "boolean exists" + this.branchNr + " = false;\n";
		}
		while ((branch = getNext()) != null) {
			if (branch.getType() == BranchType.NONE) {
				code += genAssertion(branch.getBranchCondition());
			} else if (branch.getType() == BranchType.IMPL) {
				code += genBranch(branch.getBranchCondition());
			} else if (branch.getType() == BranchType.EQUI) {
				code += genBranch(branch.getBranchCondition());
			} else if (branch.getType() == BranchType.EXISTS) {
				code += genExists(branch);
			} else if (branch.getType() == BranchType.FORALL) {
				code += genForAll(branch);
			} else if (branch.getType() == BranchType.OR) {
				// TODO handle quantor node used with '|' (like forall... | exists...)
			}
		}
		code = closeBrackets(code);
		return CodeHandler.indentCode(code, 0);
	}
	
	private boolean containsExists() {
		for (final var b : this.branches) {
			if (b.getType() == BranchType.EXISTS) {
				return true;
			}
		}
		return false;
	}
	
	private String closeBrackets(String code) {
		while(CodeHandler.countBrackets(code, '{') > 0) {
			code += "\n}";
		}
		return code;
	}
	
	private String genAssertion(final String assertion) {
		return "Assert.assertTrue(" + assertion + ");";
	}
	
	private String genBranch(final String condition) {
		return "if (" + condition + ")" + " {\n";
	}
	
	private String genExists(final Branch branch) {
		String firstCondition = removeIdentifier(branch.getIterConditions().get(0), branch.getIterName());
		String initialValue = getIterValue(firstCondition);
		String secondCondition = removeIdentifier(branch.getIterConditions().get(1), branch.getIterName());
		String lastValue = getIterValue(secondCondition);
		//branch.getIterConditions().remove(0);
		//branch.getIterConditions().remove(0);
		String exists = "for (" + branch.getIterType() + " " + branch.getIterName() + " = " 
				+ initialValue + "; " + branch.getIterName() + " < " + lastValue + "; "
				+ branch.getIterName() + "++" + ") " + "{\n";
		if (branch.getIterConditions().size() > 2) {
			exists += "if (" + branch.getIterConditions().stream().reduce((f, s) -> f + " && " + s).get() + ") {\n";
		}
		exists += "exists" + this.branchNr + " = true;\n}\n";
		if (!branch.getIterConditions().isEmpty()) {
			exists += "}\n";
		}
		exists += genAssertion("exists" + this.branchNr + " == true")
				+ "\nexists" + this.branchNr + " = false;";
		return exists;
		/*return 	"for (" + branch.getIterType() + " " + branch.getIterName() + " = " 
				+ Variable.getWrapper(branch.getIterType()) + ".MIN_VALUE; " + branch.getIterName() + " < " + Variable.getWrapper(branch.getIterType()) + ".MAX_VALUE; " 
				+ branch.getIterName() + "++" + ")" + "{\n" 
				+ "if (" + branch.getIterConditions().stream().reduce((f, s) -> f + " && " + s).get() + ") {\n"
				+ "exists" + this.branchNr + " = true;\n"
				+ "}\n}\n" 
				+ genAssertion("exists" + this.branchNr + " == true")
				+ "\nexists" + this.branchNr + " = false;";*/
	}
		
	private String genForAll(final Branch branch) {
		String firstCondition = removeIdentifier(branch.getIterConditions().get(0), branch.getIterName());
		String initialValue = getIterValue(firstCondition);
		String secondCondition = removeIdentifier(branch.getIterConditions().get(1), branch.getIterName());
		String lastValue = getIterValue(secondCondition);
		//branch.getIterConditions().remove(0);
		//branch.getIterConditions().remove(0);
		String forLoop = "for (" + branch.getIterType() + " " + branch.getIterName() + " = " 
				+ initialValue + "; " + branch.getIterName() + " < " + lastValue + "; "
				+ branch.getIterName() + "++" + ") " + "{\n";
		if (branch.getIterConditions().size() > 2) {
			//forLoop += "if (" + branch.getIterConditions().stream().reduce((f, s) -> f + " && " + s).get() + ") {\n";
		}
		return 	forLoop;
	} // Variable.getWrapper(branch.getIterType()) + ".MAX_VALUE; "
	
	private String removeIdentifier(String str, String identifier) {
		List<Integer> positions = new ArrayList<Integer>();
		String originalStr = str;
		int offset = 0;
		
		while(str.length() > 0) {
			int start = str.indexOf(identifier);
			if (start == -1) {
				break;
			}
			int end = start + identifier.length();
			if (start > 0 && Character.isAlphabetic(str.charAt(start-1))) {
				str = str.substring(end, str.length());
				offset += end+1;
				continue;
			}
			if (end < str.length() && Character.isAlphabetic(str.charAt(end))) {
				str = str.substring(end, str.length());
				offset += end+1;
				continue;
			}
			positions.add(start+offset);
			if (end >= str.length()) {
				break;
			}
			str = str.substring(end, str.length());
		}
		
		for (var i : positions) {
			originalStr = originalStr.substring(0, i) 
					+ originalStr.substring(i + identifier.length(), originalStr.length());
		}
		
		return originalStr;
	}
	
	private String getIterValue(String condition) {
		condition = condition.replaceAll("<", "");
		condition = condition.replaceAll("\\s", "");
		condition = condition.replaceAll(">", "");
		condition = condition.replaceAll("=", "");
		condition = condition.replaceAll("\\(", "");
		condition = condition.replaceAll("\\)", "");
		return condition;
	}
	
	private void getIterConditions(Node node, final List<String> lst) {
		if (node == null) {
			return;
		}
		if (node.getType() != TokenType.REL && !(node instanceof BinaryNode) && (node.getLeft() != null || node.getRight() != null)) {
			return;
		}
		getIterConditions(node.getLeft(), lst);
		getIterConditions(node.getRight(), lst);
		if (node.getType() == TokenType.REL) {
			lst.add(node.getRep());
		}
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
			
	private void translatePredicate(Node pred) {
		BranchType type;
		String branchCondition;
		List<String> dummy = new ArrayList<String>();
		
		if (pred.getType() == TokenType.KEY) {
			var quantorBodyConditions = new ArrayList<String>();
			var iterConditions = new ArrayList<String>();
			if (pred.getRep().contains("forall") || pred.getRep().contains("exists")) {
				var nextImpl = findNextImpl(pred);
				type = pred.getRep().contains("forall") ? BranchType.FORALL : BranchType.EXISTS;
				branchCondition = "";
				var branch = new Branch(type, branchCondition, dummy);
				getIterConditions(((QuantorNode)pred).getConditions(), iterConditions);
				branch.setIterType(((QuantorNode)pred).getIteratorType());
				branch.setIterName(((QuantorNode)pred).getIterator());
				branch.setIterConditions(iterConditions);
				this.branches.push(branch);
				if (nextImpl != null) {
					translatePredicate(nextImpl.getRight());
				}
			}
		} else if (pred.getType() == TokenType.IMPL) {
			type = BranchType.IMPL;
			branchCondition = pred.getLeft().getRep();
			this.branches.push(new Branch(type, branchCondition, dummy));
			translatePredicate(pred.getRight());
		} else if (pred.getType() == TokenType.EQUI) {
			type = BranchType.EQUI;
			branchCondition = pred.getLeft().getRep();
			this.branches.push(new Branch(type, branchCondition, dummy));
			translatePredicate(pred.getRight());
		} else if (pred.getType() == TokenType.OR) {
			/*type = BranchType.OR;
			branchCondition = pred.getLeft().getRep();
			this.branches.push(new Branch(type, branchCondition, dummy));
			translatePredicate(pred.getRight());*/
		} else {
			type = BranchType.NONE;
			branchCondition = pred.getRep();
			this.branches.push(new Branch(type, branchCondition, dummy));
		}
	}

	public BranchType getType() {
		if (this.branches.size() > 0) {
			return this.branches.getLast().getType();
		} else {
			return null;
		}
	}

	public String getRepWithContext(int testNumber, int branchCounter, List<TestCaseData> inputs, String instanceName) {	
		String code = "";
		Branch branch;
		if (containsExists()) {
			code += "boolean exists" + this.branchNr + " = false;\n";
		}
		while ((branch = getNext()) != null) {
			if (branch.getType() == BranchType.NONE) {
				List<String> varNames = Variable.getVarNames(branch.getBranchCondition(), inputs.get(0).getInputDataTupel().getGlobalVarNames(), inputs.get(0).getInputDataTupel().getParameterNames(), instanceName);
				for (var v : varNames) {
					code += "context.setAttribute(\"" + testNumber + v + "\", " + v + ");\n";
				}
				code += genAssertion(branch.getBranchCondition());
			} else if (branch.getType() == BranchType.IMPL) {
				code += genBranch(branch.getBranchCondition());
				code += "context.setAttribute(\"" + testNumber + "branch" + branchCounter + "\", \"" + branch.getBranchCondition() + "\");\n";
			} else if (branch.getType() == BranchType.EQUI) {
				code += genBranch(branch.getBranchCondition());
				code += "context.setAttribute(\"" + testNumber + "branch" + branchCounter + "\", \"" + branch.getBranchCondition() + "\");\n";
			} else if (branch.getType() == BranchType.EXISTS) {
				code += genExists(branch);
			} else if (branch.getType() == BranchType.FORALL) {
				code += genForAll(branch);
			}
		}
		code = closeBrackets(code);
		return CodeHandler.indentCode(code, 2) + "\n";
	}
}
