package de.tu_bs.cs.isf.cbc.tool.features;

import javax.swing.JOptionPane;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionTechnique;

public class EditCompositionTechniqueOfFormula extends AbstractCustomFeature {

	private static final String CONTRACT_OVERRIDING = "Contract Overriding";
	private static final String EXPLICIT_CONTRACTING = "Explicit Contracting";
	private static final String CONJUNCTIVE_CONTRACT_REFINEMENT = "Conjunctive Contract Refinement";

	private boolean hasDoneChanges = false;

	public EditCompositionTechniqueOfFormula(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Edit Composition Technique of Formula";
	}

	@Override
	public String getDescription() {
		return "Edit the Composition Technique of the Formula";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof CbCFormula) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof CbCFormula) {
				CbCFormula formula = (CbCFormula) bo;
				CompositionTechnique currentTechnique = formula.getCompositionTechnique();
				String[] compositionTechniques = { CONTRACT_OVERRIDING, EXPLICIT_CONTRACTING,
						CONJUNCTIVE_CONTRACT_REFINEMENT };
				// create selection dialog
				String input = (String) JOptionPane.showInputDialog(null, "Choose Composition Technique",
						"Composition Technique", JOptionPane.QUESTION_MESSAGE, null, compositionTechniques,
						compositionTechniques[0]);

				CompositionTechnique newTechnique = convertToCompositionTechnique(input);
				if (newTechnique != null && !newTechnique.equals(currentTechnique)) {
					this.hasDoneChanges = true;
					formula.setCompositionTechnique(newTechnique);
					updatePictogramElement(pes[0]);
				}
			}
		}
	}

	private CompositionTechnique convertToCompositionTechnique(String compositionTechString) {
		switch (compositionTechString) {
		case CONTRACT_OVERRIDING:
			return CompositionTechnique.CONTRACT_OVERRIDING;
		case EXPLICIT_CONTRACTING:
			return CompositionTechnique.EXPLICIT_CONTRACTING;
		case CONJUNCTIVE_CONTRACT_REFINEMENT:
			return CompositionTechnique.CONJUNCTIVE_CONTRACTING;
		default:
			return null;
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}
}