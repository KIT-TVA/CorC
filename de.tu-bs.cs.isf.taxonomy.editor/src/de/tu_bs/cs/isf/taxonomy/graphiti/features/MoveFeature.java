package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Class that forbids the move of inner elements of Algorithms
 * @author Tobias
 *
 */
public class MoveFeature extends DefaultMoveShapeFeature {
	 
	/**
	 * Constructor of the class
	 * @param fp	The FeatureProvider
	 */
    public MoveFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public boolean canMoveShape(IMoveShapeContext context) {
        boolean canMove = super.canMoveShape(context);
 
        if (canMove) {
            Shape shape = context.getShape();
            Object bo = getBusinessObjectForPictogramElement(shape);
            if (bo instanceof Method || bo instanceof DataStructure || bo instanceof DataType) {
               canMove = false;
            }
        }
        return canMove;
    }
}
