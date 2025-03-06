package de.kit.tva.lost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.common.util.BasicEList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.kit.tva.lost.models.diagrams.DiagramTranslator;
import de.kit.tva.lost.models.diagrams.DiagramTranslatorException;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;

/*
 * Requires endless mocking of cbc model parts. Ain't nobody got time for that.
 */
public class DiagramTranslatorTests {
    private DiagramTranslator dt;

    private void init(Renaming renMock, GlobalConditions condsMock, JavaVariables varsMock, CbCFormula formulaMock) {
	try {
	    dt = new DiagramTranslator();
	    dt.load(renMock, condsMock, varsMock, formulaMock);
	} catch (DiagramTranslatorException e) {
	    e.printStackTrace();
	}
    }

    private String createMockedFormula(final CbCFormula formulaMock) {
	Mockito.when(formulaMock.getName()).thenReturn("test");
	var statementMock = Mockito.mock(AbstractStatement.class);
	var preConMock = Mockito.mock(Condition.class);
	var postConMock = Mockito.mock(Condition.class);
	Mockito.when(statementMock.getPreCondition()).thenReturn(preConMock);
	Mockito.when(statementMock.getPostCondition()).thenReturn(postConMock);
	Mockito.when(formulaMock.getStatement()).thenReturn(statementMock);
	Mockito.when(preConMock.getName()).thenReturn("x");
	Mockito.when(postConMock.getName()).thenReturn("x");
	Mockito.when(formulaMock.getStatement().getPreCondition()).thenReturn(preConMock);
	Mockito.when(formulaMock.getStatement().getPostCondition()).thenReturn(postConMock);
	return "\tF(pre: x; post: x)\n\t\tz;";
    }

    @Test
    public void vars() {
	var formulaMock = Mockito.mock(CbCFormula.class);
	var expectedFormula = createMockedFormula(formulaMock);
	var refinementMock = Mockito.mock(AbstractStatement.class);
	Mockito.when(refinementMock.getName()).thenReturn("z;");
	Mockito.when(formulaMock.getStatement().getRefinement()).thenReturn(refinementMock);
	var varsMock = Mockito.mock(JavaVariables.class);
	var varMock = Mockito.mock(JavaVariable.class);
	var var2Mock = Mockito.mock(JavaVariable.class);
	Mockito.when(varMock.getName()).thenReturn("int x");
	Mockito.when(var2Mock.getName()).thenReturn("String a");
	var varLst = new BasicEList<JavaVariable>();
	varLst.add(varMock);
	varLst.add(var2Mock);
	Mockito.when(varsMock.getVariables()).thenReturn(varLst);
	Mockito.when(varsMock.getFields()).thenReturn(null);
	Mockito.when(varsMock.getParams()).thenReturn(null);

	init(null, null, varsMock, formulaMock);
	var lostCode = dt.getTranslatedCode();

	assertEquals("D(name: test)\n" + "\tVars\n" + "\t\tLOCAL int x\n" + "\t\tLOCAL String a\n" + expectedFormula,
		lostCode);
    }

    /*
     * @Test public void composition() { var formulaMock =
     * Mockito.mock(CbCFormula.class); var expectedFormula =
     * createMockedFormula(formulaMock); var compositionMock =
     * Mockito.mock(CompositionStatement.class); var intmMock =
     * Mockito.mock(Condition.class);
     * Mockito.when(intmMock.getName()).thenReturn("a");
     * Mockito.when(formulaMock.getStatement().getRefinement()).thenReturn(
     * compositionMock);
     * Mockito.when(compositionMock.getIntermediateCondition()).thenReturn(intmMock)
     * ; // Mockito.when(compositionMock.getFirstStatement().thenReturn)
     * 
     * var lostCode = dt.getTranslatedCode();
     * 
     * assertEquals("D(name: test)\n" + expectedFormula +
     * "\n\t\tC(intm: a)\n\t\t\tx;\n\t\t\ty;", lostCode); }
     */

}
