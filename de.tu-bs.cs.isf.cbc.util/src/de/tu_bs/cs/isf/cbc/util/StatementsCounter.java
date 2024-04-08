package de.tu_bs.cs.isf.cbc.util;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;

import java.util.Optional;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.VarType;


public class StatementsCounter extends VoidVisitorAdapter<Void>{
	private int statementsCounter;
	
	public StatementsCounter() {
		this.statementsCounter = 0;
	}
	
	//TODO: Visit all Statements. Block Statements don't count, only the statements in them.
	//TODO: Not finished
	
	@Override
	public void visit(AssertStmt assertStmt, Void a) {
		super.visit(assertStmt, a);
		statementsCounter += 1;
	}
	
	@Override
	public void visit(BreakStmt breakStmt, Void a) {
		super.visit(breakStmt, a);
		statementsCounter++;
	}
	
	@Override
	public void visit(ContinueStmt continueStmt, Void a) {
		super.visit(continueStmt, a);
		statementsCounter++;
	}
	
	@Override
	public void visit(DoStmt doStmt, Void a) {
		super.visit(doStmt, a);
		statementsCounter++;
	}
	
	@Override
	public void visit(ForStmt forStmt, Void a) {
		super.visit(forStmt, a);
		statementsCounter += forStmt.getInitialization().size();
		if (forStmt.getCompare().isPresent()) {
			statementsCounter += 1;
		}
		System.out.println(forStmt.getInitialization().get(0));
	}
	
	@Override
	public void visit(ExpressionStmt expressionStmt, Void a) {
		super.visit(expressionStmt, a);
		statementsCounter++;
	}
	
	public int getStatementsCounter() {
        return statementsCounter;
    }
}
