package de.tu_bs.cs.isf.cbc.util;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class StatementsCollector extends VoidVisitorAdapter<Void> {
	private NodeList<Statement> statements;
	private NodeList<Statement> assertStatements;
	
	public StatementsCollector() {
		this.statements = new NodeList<>();
		this.assertStatements = new NodeList<>();
	}
	
	//visit all Statements needed in the method handleStatement in GenerateModelFromCode
	
	@Override
	public void visit(BlockStmt blockStmt, Void a) {
		super.visit(blockStmt, a);
		for (Statement stmt : blockStmt.getStatements()) {
			stmt.removeComment();
			if (stmt.isAssertStmt()) {
				this.assertStatements.add(stmt);
			} else {
				this.statements.add(stmt);
			}
		}
	}
	
	public NodeList<Statement> getStatements() {
        return statements;
    }
	
	public NodeList<Statement> getAssertStatements() {
		return assertStatements;
	}

    public void resetStatements() {
        statements = new NodeList <>();
    }
}
