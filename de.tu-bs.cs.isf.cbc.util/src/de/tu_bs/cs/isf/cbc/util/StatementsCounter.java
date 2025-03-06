package de.tu_bs.cs.isf.cbc.util;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.YieldStmt;

public class StatementsCounter extends VoidVisitorAdapter<Void> {
	private int statementsCounter;

	public StatementsCounter() {
		this.statementsCounter = 0;
	}

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
	public void visit(ExplicitConstructorInvocationStmt stmt, Void a) {
		super.visit(stmt, a);
		statementsCounter++;
	}

	@Override
	public void visit(ExpressionStmt expressionStmt, Void a) {
		super.visit(expressionStmt, a);
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
	public void visit(LabeledStmt labeledStmt, Void a) {
		super.visit(labeledStmt, a);
		statementsCounter++;
	}

	public void visit(ReturnStmt returnStmt, Void a) {
		super.visit(returnStmt, a);
		statementsCounter++;
	}

	@Override
	public void visit(YieldStmt yieldStmt, Void a) {
		super.visit(yieldStmt, a);
		statementsCounter++;
	}

	public int getStatementsCounter() {
		return statementsCounter;
	}
}
