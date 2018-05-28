package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

import de.tu_bs.cs.isf.taxonomy.graphiti.model.TaxonomyModelUtil;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * pastes a copied statement
 */
public class PasteAlgorithmFeature extends AbstractPasteFeature {
	
	public PasteAlgorithmFeature (IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public void paste(IPasteContext context) {
		PictogramElement[] pes = context.getPictogramElements();
        Diagram diagram = (Diagram) pes[0];
        Object[] objects = getFromClipboard(); //bos
        for (int i = 0; i < objects.length; i++) {
        	//paste for Algorithm Method
        	if (objects[i] instanceof Algorithm) {
        		Algorithm pasteAlgorithm = TaxonomyFactory.eINSTANCE.createAlgorithm();
        		Algorithm copiedAlgorithm = (Algorithm) objects[i];
        		//sets standard stuff
        		pasteAlgorithm.setName(copiedAlgorithm.getName() + "Copy");
        		pasteAlgorithm.setPreCondition(copiedAlgorithm.getPreCondition());
        		pasteAlgorithm.setPostCondition(copiedAlgorithm.getPreCondition());
        		pasteAlgorithm.setInvariant(copiedAlgorithm.getInvariant());
        		pasteAlgorithm.setImports(copiedAlgorithm.getImports());
        		pasteAlgorithm.setAbstract(copiedAlgorithm.isAbstract());
        		//takes all method from copied Algorithm and gives them to pasteAlgorithm
        		for (Method copiedMethod : copiedAlgorithm.getMethods()) {
        			Method pasteMethod = TaxonomyFactory.eINSTANCE.createMethod();
        			for (DataType copiedDataType : copiedMethod.getDataTypes()) {
        				DataType pasteDataType = TaxonomyFactory.eINSTANCE.createDataType();
        				pasteDataType.setName(copiedDataType.getName());
        				pasteMethod.getDataTypes().add(pasteDataType);
        			}
        			pasteMethod.setName(copiedMethod.getName());
        			pasteMethod.setPostCondition(copiedMethod.getPostCondition());
        			pasteMethod.setPreCondition(copiedMethod.getPreCondition());
        			pasteMethod.setPseudoCode(copiedMethod.getPseudoCode());
        			pasteAlgorithm.getMethods().add(pasteMethod);
        		}
        		//übergibt Datenstrukturen
        		for (DataStructure copiedData : copiedAlgorithm.getDataStructures()) {
        			DataStructure pasteData = TaxonomyFactory.eINSTANCE.createDataStructure();
        			DataType copiedDataType = copiedData.getDataType();
    				DataType pasteDataType = TaxonomyFactory.eINSTANCE.createDataType();
    				pasteDataType.setName(copiedDataType.getName());
    				pasteData.setDataType(pasteDataType);
    				pasteData.setName(copiedData.getName());
    				pasteData.setInitialValue(copiedData.getInitialValue());
    				pasteAlgorithm.getDataStructures().add(pasteData);
        			}
        		try {
        			try {
        				TaxonomyModelUtil.saveAlgorithmToModelFile(pasteAlgorithm, getDiagram());
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		} catch (CoreException e) {
        			e.printStackTrace();
        		}
        		AddContext ac = new AddContext();
                ac.setLocation(context.getX(), context.getY()); 
                ac.setTargetContainer(diagram);
                addGraphicalRepresentation(ac, pasteAlgorithm); 
        	}
        }
	}

	@Override
	public boolean canPaste(IPasteContext context) {
		//only support pasting directly in the diagram (nothing else selected)
        PictogramElement[] pes = context.getPictogramElements();
        if (pes.length != 1 || !(pes[0] instanceof Diagram)) {
            return false;
        }
        //checks that clipboard is not empty (so that you can't paste if you havn't copied anything)
        Object[] fromClipboard = getFromClipboard();
        if (fromClipboard == null || fromClipboard.length == 0) {
            return false;
        }
        return true;
	}
}
