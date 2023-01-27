package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.ReturnStatement;
import de.tu_bs.cs.isf.cbc.tool.helper.ReferenceException;
import de.tu_bs.cs.isf.cbc.tool.helper.UnexpectedTokenException;
import de.tu_bs.cs.isf.cbc.tool.helper.Util;
import de.tu_bs.cs.isf.cbc.util.Console;

/**
 * Feature for testing all statements.
 * @author Fynn
 */
public class TestAllStatements extends MyAbstractAsynchronousCustomFeature{
	private final IFeatureProvider fp;
	public static final Color blue = new Color(new RGB(10, 10, 200));
	
	public TestAllStatements(IFeatureProvider fp) {
		super(fp);
		this.fp = fp;
	}
	
	@Override
	public String getName() {
		return "Test all statements";
	}

	@Override
	public String getDescription() {
		return "Generates statement tests for the diagram.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor monitor) {
		Console.clear();
		Console.println("Start testing...\n");
		final long startTime = System.nanoTime();
		
		testDiagram(getDiagram());
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		Console.println("Testing finished.");
		Console.println("Time needed: " + duration + "ms");	
	}
	
	public URI getUri(final Diagram diag) {
		final URI uri = diag.eResource().getURI();
		return uri;
	}
	
	public void testDiagram(final Diagram diag) {
		final URI uri;
		boolean returnStatement;
		
		JavaVariables vars = null;
		GlobalConditions conds = null;
		CbCFormula formula = null;
		for (Shape shape : diag.getChildren()) {
			Object obj = getBusinessObjectForPictogramElement(shape);
			if (obj instanceof JavaVariables) {
				vars = (JavaVariables) obj;
			} else if (obj instanceof GlobalConditions) {
				conds = (GlobalConditions) obj;
			} else if (obj instanceof CbCFormula) {
				formula = (CbCFormula) obj;
			}
		}	
		final TestStatement ts = new TestStatement(fp);
		uri = getUri(diag);
		ts.setUri(uri);
		Util.clearLog(diag.eResource().getURI());
		var allStatements = TestStatement.collectAllStatements(formula);
		for (var statement : allStatements) {
			Console.println(" > Testing path:", blue);
			Console.println("\t" + ts.getStatementPath(statement));
			try {
				ts.testStatement((AbstractStatement)statement, vars, conds, formula, statement instanceof ReturnStatement);
			} catch (TestAndAssertionGeneratorException |TestStatementException | ReferenceException | UnexpectedTokenException e) {
				Console.println(e.getMessage());
				e.printStackTrace();
				return;
			}
		}
	}

}
