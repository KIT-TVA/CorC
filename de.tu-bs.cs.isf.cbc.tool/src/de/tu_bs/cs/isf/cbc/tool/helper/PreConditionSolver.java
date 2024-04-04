package de.tu_bs.cs.isf.cbc.tool.helper;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.runner.manipulation.Ordering$Context;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Model;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.exceptions.ExceptionMessages;
import de.tu_bs.cs.isf.cbc.exceptions.IdentifierNotFoundException;
import de.tu_bs.cs.isf.cbc.exceptions.PreConditionSolverException;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;
import de.tu_bs.cs.isf.cbc.exceptions.TestFailedException;
import de.tu_bs.cs.isf.cbc.exceptions.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileHandler;
import de.tu_bs.cs.isf.cbc.util.InputData;
import de.tu_bs.cs.isf.cbc.util.TokenType;
import de.tu_bs.cs.isf.cbc.util.Variable;
import de.tu_bs.cs.isf.cbc.util.conditionparser.ConditionParser;
import de.tu_bs.cs.isf.cbc.util.conditionparser.Node;
import de.tu_bs.cs.isf.cbc.util.conditionparser.NotNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.OpNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.QuantorNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.RelNode;
import de.tu_bs.cs.isf.cbc.util.conditionparser.SingleNode;

/**
 * Translates an AST into the syntax of the Z3-Solver.
 * Z3 is used to get values for variables 
 * given multiple preconditions. Currently only supports the primitive types 
 * [boolean, byte, short, int, long].
 * @author Fynn Demmler
 */
public class PreConditionSolver {
	private Context context;
	private List<Variable> jVars = null;
	private boolean showWarnings = false;
	private boolean usePreConditions = true;
	
	public void showWarnings(boolean b) {
		showWarnings = b;
	}
	
	public void usePreCondition(boolean b) {
		usePreConditions = b;
	}
	
	private void setup() {
		HashMap<String, String> cfg = new HashMap<String, String>();
		cfg.put("model", "true");
		this.context = new Context(cfg);
	}
	
	/**
	 * Constructor for the Conditions Solver.
	 * @param preConditions All preconditions (precondition from the hoare tripel + global invariants) in JavaDL syntax.
	 * @throws SettingsException 
	 */
	public PreConditionSolver(final JavaVariables vars) throws SettingsException {
		setup();
		this.jVars = Variable.getAllVars(vars);
	}
	
    private Model check(Context ctx, BoolExpr f, Status sat) throws TestFailedException
    {
        Solver s = ctx.mkSolver();
        s.add(f);
        if (s.check() != sat)
            throw new TestFailedException();
        if (sat == Status.SATISFIABLE)
            return s.getModel();
        else
            return null;
    }
    
    private String getVarType(String varName) {
    	for (var v : this.jVars) {
    		if (v.getName().equals(varName)) {
    			return v.getType();
    		}
    	}
    	return "";
    }   
    
    private boolean isIntType(String type) {
    	type = type.replaceAll("\\[\\]", "");
    	switch (type) {
	    	case "byte":
	    	case "Byte":
	    	case "short":
	    	case "Short":
	    	case "int":
	    	case "Integer":
	    	case "long":
	    	case "Long":
	    		return true;
	    	default:
	    		return false;
    	}
    }
    
    private boolean isBoolType(String type) {
    	return type.equals("boolean") | type.equals("Boolean");
    }
    
    private Expr makeCondition(Node tree) {
    	if (tree == null || usePreConditions == false) {
    		return context.mkTrue();
    	}
    	if (tree.getType() == TokenType.BOOL) {
    		if (tree.getRep().equals("false")) {
    			return context.mkBool(false);
    		} else {
    			return context.mkBool(true);
    		}
    	} else if (tree.getType() == TokenType.NUMBER) {
    		final var singleNode = (SingleNode)tree;
    		return context.mkInt(singleNode.getValue());
    	} else if (tree.getType() == TokenType.IDENT) {
    		final var singleNode = (SingleNode)tree;
    		String val = singleNode.getValue();
    		if (val.startsWith("appears")) {
    			var params = val.substring(val.indexOf("(") + 1, val.lastIndexOf(")")).split(",");
    			var type = getVarType(params[0].strip());
    			Expr a;
    			Expr eq;
    			if (isIntType(type)) {
    				a = context.mkIntConst(params[0].strip() + "[" + params[2].strip() + "]");
    				eq = context.mkIntConst(params[1].strip());
    			} else if (isBoolType(type)) {
        			a = context.mkBoolConst(params[0].strip() + "[" + params[2].strip() + "]");
        			eq = context.mkBoolConst(params[1].strip());
    			} else {
    				a = null;
    				eq = null;
    			}
    			return context.mkEq(a, eq);
    		} else if (val.contains("[")) {
    			var identifier = val.substring(0, val.indexOf("["));
    			var type = getVarType(identifier);
    			if (isIntType(type)) {
    				return context.mkIntConst(val);
    			} else if (isBoolType(type))  {
    				return context.mkBoolConst(val);
    			}
    		} else if (val.contains(".")) {
    			var identifier = val.substring(0, val.indexOf("."));
    			var variable = val.substring(val.indexOf(".")+1, val.length());
    			var identType = getVarType(identifier);
    			var varType = getVarType(variable);
    			if (identType.contains("[")) {
    				if (variable.equals("length")) {
    					return context.mkIntConst(val);
    				}
    			}
    			if (isIntType(varType)) {
    				return context.mkIntConst(val);
    			} else if (isBoolType(varType)) {
    				return context.mkBoolConst(val);
    			} 
    			/*
    			if (!InputData.isBuiltInType(getVarType(identifier))) {
    				
    			}*/
    		}
    		final var type = getVarType(val);
    		if (isIntType(type)) {
    			return context.mkIntConst(val);
    		} else if (type.equals("boolean") || type.equals("Boolean")) {
    			return context.mkBoolConst(val);
    		} else if (val.equals("null")) {
    			return context.mkBoolConst(val);
    		}
    	} else if (tree.getType() == TokenType.REL) {
    		if (tree.getRep().startsWith("!")) {
    			final var notNode = (NotNode)tree;
    			return context.mkNot(makeCondition(notNode.getLeft()));
    		}
			final var relNode = (RelNode)tree;
			if (relNode.getValue().equals("<")) {
				return context.mkLt(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
			} else if (relNode.getValue().equals("<=")) {
				return context.mkLe(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
			} else if (relNode.getValue().equals("=") || relNode.getValue().equals("==")) {
				return context.mkEq(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
			} else if (relNode.getValue().equals(">=")) {
				return context.mkGe(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
			} else if (relNode.getValue().equals(">")) {
				return context.mkGt(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
			} else if (relNode.getValue().equals("!=")) {
				BoolExpr boolExpr = context.mkEq(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
				boolExpr = context.mkNot(boolExpr);
				return boolExpr;
			}
		} else if (tree.getType() == TokenType.OP) {
			final var opNode = (OpNode)tree;
			if (opNode.getOperator().equals("+")) {
				return context.mkAdd(makeCondition(opNode.getLeft()), makeCondition(opNode.getRight()));
			} else if (opNode.getOperator().equals("-")) {
				return context.mkSub(makeCondition(opNode.getLeft()), makeCondition(opNode.getRight()));
			} else if (opNode.getOperator().equals("*")) {
				return context.mkMul(makeCondition(opNode.getLeft()), makeCondition(opNode.getRight()));
			} else if (opNode.getOperator().equals("/")) {
				return context.mkDiv(makeCondition(opNode.getLeft()), makeCondition(opNode.getRight()));
			} else if (opNode.getOperator().equals("%")) {
				return context.mkMod(makeCondition(opNode.getLeft()), makeCondition(opNode.getRight()));
			}
		} else if (tree.getType() == TokenType.IMPL) {
			return context.mkImplies(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
		} else if (tree.getType() == TokenType.EQUI) {
			return context.mkIff(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
		} else if (tree.getType() == TokenType.AND) {
			return context.mkAnd(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
		} else if (tree.getType() == TokenType.OR) {
			return context.mkOr(makeCondition(tree.getLeft()), makeCondition(tree.getRight()));
		} else if (tree.getType() == TokenType.KEY) {
			if (tree.getRep().contains("old_")) {
				var type = getVarType(tree.getRep().substring("old_".length(), tree.getRep().length()));
				if (isIntType(type)) {
					return context.mkIntConst(tree.getRep());
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					return context.mkBoolConst(tree.getRep());
				}
			} else if (tree.getRep().contains("result")) {
				var type = getVarType(tree.getRep());
				if (isIntType(type)) {
					return context.mkIntConst(tree.getRep());
				} else if (type.equals("boolean") || type.equals("Boolean")) {
					return context.mkBoolConst(tree.getRep());
				}
				return context.mkIntConst(tree.getRep());
			} else {
				// ignore quantifiers in preconditions for now
				final var quantorNode = (QuantorNode)tree;
				if (quantorNode.getIteratorType().equals("forall")) {
					return context.mkTrue();
				} else if (quantorNode.getIteratorType().equals("exists")) {
					return context.mkTrue();
				}
			}
		}
		Console.printWarn("PreConditionSolverWarning: Type of identifier '" + tree.getRep() + "' is not supported.");
    	FileHandler.instance.log("PreConditionSolverWarning: Type of identifier '" + tree.getRep() + "' is not supported.");
		return context.mkTrue();
    }
    
    private void extractVars(Expr expr, final List<Expr> vars) {
    	if (expr == null) {
    		return;
    	}
    	if (expr.isConst() || expr.isVar()) {
    		vars.add(expr);
    	}
    	else {
    		var subexprs = expr.getArgs();
    		int len = subexprs.length;
    		for (int i = 0; i < len; i++) {
    			extractVars(subexprs[i], vars);
    		}
    	}
    }
    
    private String getValue(String varName, Expr value) {
    	var type = "";
		for (var jVar : this.jVars) {
			if (jVar.getName().equals(varName)) {
				type = jVar.getType();
				break;
			}
		}
		String val = "";
		if (value.toString().equals(varName)) {
			if (type.equals("boolean") || type.equals("Boolean")) {
				val = "false";
			} else if (isIntType(type)) {
				val = "-1";
			}
		} else {
			val = value.toString();
		}
		return val;
    }
    
    /**
     * Evaluates expression inside array indexing brackets '[...]'.
     * @param indexTree
     * @param vars
     * @param model
     * @return The index.
     */
    private int evalIndexTree(final Node indexTree, final List<Expr> vars, final Model model) throws IdentifierNotFoundException, PreConditionSolverException {
    	int curVal = 0;
    	
    	if (indexTree == null) {
    		return 0;
    	}
    	if (indexTree.getType() == TokenType.NUMBER) {
    		curVal = Integer.parseInt(((SingleNode)indexTree).getValue());
    	} else if (indexTree.getType() == TokenType.IDENT) {
    		boolean found = false;
    		for (var v : vars) {
    			if (v.toString().equals(((SingleNode)indexTree).getValue())) {
    				curVal = Integer.parseInt(model.evaluate(v, false).toString());
    				found = true;
    			}
    		}
    		if (!found) {
    			throw new IdentifierNotFoundException(((SingleNode)indexTree).getValue());
    		}
    	} else if (indexTree.getType() == TokenType.OP) {
    		OpNode curNode = (OpNode)indexTree;
    		switch (curNode.getOperator()) {
    			case "+":
    				curVal = evalIndexTree(curNode.getLeft(), vars, model) + evalIndexTree(curNode.getRight(), vars, model);
    			case "-":
    				curVal = evalIndexTree(curNode.getLeft(), vars, model) - evalIndexTree(curNode.getRight(), vars, model);
    			case "*":
    				curVal = evalIndexTree(curNode.getLeft(), vars, model) * evalIndexTree(curNode.getRight(), vars, model);
    			case "/":
    				curVal = evalIndexTree(curNode.getLeft(), vars, model) / evalIndexTree(curNode.getRight(), vars, model);
    			case "%":
    				curVal = evalIndexTree(curNode.getLeft(), vars, model) % evalIndexTree(curNode.getRight(), vars, model);
    		}
    	} else {
    		throw new PreConditionSolverException(ExceptionMessages.invalidSymbol(indexTree.getRep()));
    	}
    	int leftTreeVal = evalIndexTree(indexTree.getLeft(), vars, model);
    	int rightTreeVal = evalIndexTree(indexTree.getRight(), vars, model);
    	return curVal + evalIndexTree(indexTree.getLeft(), vars, model) + evalIndexTree(indexTree.getRight(), vars, model);
    }
    
    private boolean constructData(Expr v, List<InputData> output, final ArrayList<Expr> vars, final Model model) throws PreConditionSolverException, IdentifierNotFoundException, UnexpectedTokenException, SettingsException {
		boolean valueIsSet = false;
		boolean found = false;
		var value = model.evaluate(v, false);
		InputData newData = null;
		
		if (jVars != null ) {
			if (v.toString().equals("null")) {
				return false;
			} else if (v.toString().contains("old_")) {
				String refVarName = v.toString().substring("old_".length(), v.toString().length());
				String type = "";
				for (var jVar : this.jVars) {
					if (jVar.getName().equals(refVarName)) {
						type = jVar.getType();
						break;
					}
				}
				newData = new InputData(v.toString(), type);
			} else if (v.toString().contains(".")) {
				String refVarName = v.toString().substring(0, v.toString().indexOf("."));
				String refVal = v.toString().substring(v.toString().indexOf(".") + 1, v.toString().length());
				if (refVal.equals("length")) {
					for (var d : output) {
						if (d.getName().equals(refVarName)) {
							found = true;
							var vals = d.getValues();
							var newValsLen = Integer.parseInt(getValue(v.toString(), value));
							if (vals.length > newValsLen) {
								// There might be data allocated to the array, that we don't want to delete
								break;
							}
							int minLen = newValsLen < vals.length ? newValsLen : vals.length;
							var newVals = new String[minLen];
							for (int i = 0; i < minLen; i++) 
								newVals[i] = vals[i];			
							d.setValues(newVals);
							valueIsSet = true;
						}
					}
					if (!found) {
						for (var jVar : this.jVars) {
							if (jVar.getName().equals(refVarName)) {
								newData = new InputData(jVar.getName(), jVar.getType());
								String[] vals = newData.getValues();
								var newValsLen = Integer.parseInt(getValue(v.toString(), value));
								int minLen = newValsLen < vals.length ? newValsLen : vals.length;
								var newVals = new String[minLen];
								for (int i = 0; i < minLen; i++) 
									newVals[i] = vals[i];	
								newData.setValues(newVals);
								valueIsSet = true;
								break;
							}
						}
					}
				} else {
					// Not implemented.
					return false;
				}
			} else if (v.toString().contains("[")) {
				String name = v.toString().replaceAll("\\|", "");
				String refVarName = name.substring(0, name.indexOf("["));
				
				final ConditionParser parser = new ConditionParser();
				String refVar = name.substring(name.indexOf("[") + 1, name.indexOf("]")); // TODO: Parse this term to get the actual var
				final var indexTree = parser.parse(refVar);
				int refVal = 0;
				try {
					refVal = evalIndexTree(indexTree, vars, model);
				} catch(IdentifierNotFoundException e) {
					throw e;
				} catch(PreConditionSolverException e) {
					throw e;
				}
				
				for (var d : output) {
					if (d.getName().equals(refVarName)) {
						found = true;
						var vals = d.getValues();
						int idx = refVal;
						if (idx >= vals.length) {
							String[] newVals = new String[idx + 1];
							for (int i = 0; i < newVals.length; i++) {
								if (i < vals.length) {
									newVals[i] = vals[i];
								} else {
									newVals[i] = vals[0];
								}
							}
							newVals[idx] = getValue(name, value);
							d.setValues(newVals);
						} else {
							vals[idx] = getValue(name, value);
							d.setValues(vals);
						}
						valueIsSet = true;
						break;
					}
				}
				if (!found) {
					for (var jVar : this.jVars) {
						if (jVar.getName().equals(refVarName)) {
							newData = new InputData(jVar.getName(), jVar.getType());
							String[] vals = newData.getValues();
							int idx = refVal;
							if (idx >= vals.length) {
								String[] newVals = new String[idx + 1];
								for (int i = 0; i < newVals.length; i++) {
									if (i < vals.length) {
										newVals[i] = vals[i];
									} else {
										newVals[i] = vals[0];
									}
								}
								newVals[idx] = getValue(name, value);
								newData.setValues(newVals);
							} else {
								vals[idx] = getValue(name, value);
								newData.setValues(vals);
							}
							valueIsSet = true;
							break;
						}
					}
				}		
			} else {
				for (var jVar : this.jVars) {
					if (jVar.getName().equals(v.toString())) {
						newData = new InputData(jVar.getName(), jVar.getType());
						break;
					}
				}
			}
		} else {
			throw new PreConditionSolverException(ExceptionMessages.noVarsGiven());
		}
		if (found) {
			return false;
		}
		if (newData == null) {
			// happens when an identifier couldn't be recognized.
			throw new IdentifierNotFoundException(v.toString());
		} else {
			String val = getValue(newData.getName(), value);
			String[] values = new String[]{val};
			if (!valueIsSet) {
				newData.setValues(values);
			}
			output.add(newData);
		}
		return true;
    }
    
    public List<InputData> solve(final String preCon) throws PreConditionSolverException, UnexpectedTokenException, SettingsException {
		final List<InputData> output = new ArrayList<InputData>();
		final var vars = new ArrayList<Expr>();
    	ConditionParser p = new ConditionParser();
    	// remove <...> keywords from precon
    	var tree = p.parse(preCon);
    	Expr finalCondition;
    	try {
    		if (tree == null) {
    			return null;
    		}
    		finalCondition = makeCondition(tree);
			if (!(finalCondition instanceof BoolExpr)) {
				throw new PreConditionSolverException(ExceptionMessages.translateToSolverSyntax());
			}
    	} catch (Exception e) {
    		throw e;
    		/*if (showWarnings)
    			Console.println("SolverWarning: Defaulting to precondition 'true' because of one or more previous errors.");
    		finalCondition = context.mkTrue();*/
    	}
    	for (var expr : finalCondition.getArgs()) {
    		var newVars = new ArrayList<Expr>();
    		extractVars(expr, newVars);
    		for (var v : newVars) {
    			if (!vars.contains(v) && !v.toString().equals("true") && !v.toString().equals("false")) {
    				vars.add(v);
    			}
    		}
    	}
		// run z3 and try to find a model
		try {			
			Model model = check(context, (BoolExpr)finalCondition, Status.SATISFIABLE);
			if (model == null) {
				return null;
			}
			// get value for every variable
			for (var v : vars) {
				try {
					constructData(v, output, vars, model);
				} catch (PreConditionSolverException e) {
					Console.println(e.getMessage());
					return null;
				} catch (IdentifierNotFoundException e) {
					Console.println(e.getMessage());
					return null;
				}
			}
		} catch (TestFailedException e) {
			Console.printWarn("PreConditionSolverWarning: Preconditions are not satisfiable.");
			return null;
		}
		return output;	
    }
}
