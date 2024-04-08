package de.tu_bs.cs.isf.cbc.util;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class StatementsCollector extends VoidVisitorAdapter<Void> {
	private NodeList<Statement> statements;
	
	public StatementsCollector() {
		this.statements = new NodeList<>();
	}
	
	//TODO: visit all Statements needed in GenerateModelFromCode in Method handleStatement
	
	@Override
	public void visit(BlockStmt blockStmt, Void a) {
		super.visit(blockStmt, a);
		for (Statement stmt : blockStmt.getStatements()) {
			stmt.removeComment();
			this.statements.add(stmt);
		}
	}
	
	@Override
	public void visit(ForStmt forStmt, Void a) {
		super.visit(forStmt, a);
		
	}
	
	public NodeList<Statement> getStatements() {
        return statements;
    }

    public void resetStatements() {
        statements = new NodeList <>();
    }
}
