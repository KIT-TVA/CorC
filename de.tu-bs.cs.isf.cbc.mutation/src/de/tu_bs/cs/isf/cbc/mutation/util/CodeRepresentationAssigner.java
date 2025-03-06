package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.util.DiagramPartsExtractor;

public class CodeRepresentationAssigner {
	public CodeRepresentationAssigner() {
	}

	/**
	 * Assign the code representation for every refinement of the given @diagram.
	 * 
	 * @param diagram
	 *            The cbc diagram.
	 * @return Whether all refinements have been assigned a code representation.
	 */
	public boolean assignAll(Diagram diagram) {
		DiagramPartsExtractor dpe = new DiagramPartsExtractor(diagram);
		if (dpe.getFormula() == null) {
			return false;
		}
		AbstractStatement firstStatement = dpe.getFormula().getStatement().getRefinement();
		if (!assign(firstStatement)) {
			return false;
		}
		try {
			dpe.getFormula().eResource().save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Assigns the code representation for the given @refinement and every child
	 * refinement.
	 * 
	 * @refinement A cbc refinement.
	 * @return Whether the assignments where successful.
	 */
	public boolean assign(AbstractStatement refinement) {
		if (refinement == null) {
			return true;
		}
		if (refinement instanceof SelectionStatement) {
			var selS = (SelectionStatement) refinement;
			if (selS.getGuards().get(0) == null || selS.getGuards().get(0).getName() == null)
				return false;
			try {
				selS.setCodeRepresentation("if (" + selS.getGuards().get(0).getName() + ") {\n");
			} catch (Exception e) {
			}
			for (int i = 0; i < selS.getCommands().size(); ++i)
				assign(selS.getCommands().get(i));
		} else if (refinement instanceof SmallRepetitionStatement) {
			var repS = (SmallRepetitionStatement) refinement;
			if (repS.getGuard() == null || repS.getGuard().getName() == null)
				return false;
			try {
				repS.setCodeRepresentation("while (" + repS.getGuard().getName() + ") {\n");
			} catch (Exception e) {
			}
			assign(repS.getLoopStatement());
		} else if (refinement instanceof CompositionStatement) {
			assign(((CompositionStatement) refinement).getFirstStatement());
			assign(((CompositionStatement) refinement).getSecondStatement());
		} else {
			assign(refinement.getRefinement());
		}
		return true;
	}
}
