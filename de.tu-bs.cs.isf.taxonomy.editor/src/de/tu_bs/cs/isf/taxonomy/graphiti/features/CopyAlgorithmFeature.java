package de.tu_bs.cs.isf.taxonomy.graphiti.features;


import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractCopyFeature;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * copies an statement
 */
public class CopyAlgorithmFeature extends AbstractCopyFeature { 
	//constructor
	public CopyAlgorithmFeature (IFeatureProvider fp) {
		super(fp);
	}
	public static Algorithm copyAlgorithm = TaxonomyFactory.eINSTANCE.createAlgorithm();
	@Override
	/**
	 * checks if the statement can be copied
	 */
	public boolean canCopy(ICopyContext context) {
		final PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length == 0) {
            return false;
        }
		Object[] bos = new Object[pes.length];
		//checks if thing you want to copy is an Algorithm
		for (int i = 0; i < pes.length; i++) {
			PictogramElement pe = pes[i];
			bos[i] = getBusinessObjectForPictogramElement(pe);
			if (bos[i] instanceof Algorithm) {
				return true;
			}
		}
        return false;
	}
	
	@Override
	/**
	 * copies the statement, if canCopy == true
	 */
	public void copy(ICopyContext context) {
		//copies full graphical context and puts it to the clipboard
		PictogramElement[] pes = context.getPictogramElements();
        Object[] bos = new Object[pes.length];
        for (int i = 0; i < pes.length ; i++) {
            PictogramElement pe = pes[i];
            bos[i] = getBusinessObjectForPictogramElement(pe);
        }
        // put all business objects to the clipboard:: TRY TO PUT ONLY ALGORITHM TO CLIPBOARD
        putToClipboard(bos);
	}
}
