package de.tu_bs.cs.isf.taxonomy.graphiti.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICopyFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IPasteFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.CurvedConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import de.tu_bs.cs.isf.taxonomy.graphiti.features.AddImportsToAlgorithmFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.AddInitialValueToDataStructuresFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.AddPseudoCodeToMethodFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.ChangeAbstractAlgorithmFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.CopyAlgorithmFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.CreateDataStructureFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.CreateDataTypeFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.CreateMethodFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.DeleteConnectionFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.DeleteDataTypeFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.DeleteMethodFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditDataStructureFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditDataTypeFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditInvariantFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditMethodFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditPostConditionFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.EditPreConditionFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.LayoutConnectionFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.MoveFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.PasteAlgorithmFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.ReconnectionFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.ResizeFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.RunToolkitGenerationFeature;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.VerifyPostImplPost;
import de.tu_bs.cs.isf.taxonomy.graphiti.features.VerifyPreImplPre;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.AlgorithmPattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.ConditionPattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.ConnectionPattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.GlobalConditionsPattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.RenamePattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.RenamingPattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.VariablePattern;
import de.tu_bs.cs.isf.taxonomy.graphiti.patterns.VariablesPattern;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Own implementation of a FeatureProviderWithPatterns
 * @author Tobias
 *
 */
public class TaxonomyFeatureProvider extends DefaultFeatureProviderWithPatterns {

	/**
	 * Constructor of the class that adds the pattern
	 * @param dtp	The DiagramTypeProvider
	 */
	public TaxonomyFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new AlgorithmPattern());
		addPattern(new GlobalConditionsPattern());
        addPattern(new ConditionPattern());
        addPattern(new VariablesPattern());
        addPattern(new VariablePattern());
        addPattern(new RenamingPattern());
        addPattern(new RenamePattern());
		addConnectionPattern(new ConnectionPattern());
	}
	
	
	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		if (context.getPictogramElement() instanceof Connection) {
			return new DeleteConnectionFeature(this);
		} else if (getBusinessObjectForPictogramElement(context.getPictogramElement()) instanceof DataType) {
			return new DeleteDataTypeFeature(this);
		} else if (getBusinessObjectForPictogramElement(context.getPictogramElement()) instanceof Method) {
			return new DeleteMethodFeature(this);
		}
		return super.getDeleteFeature(context);
	}
	
	@Override
	public ICreateFeature[] getCreateFeatures() {
		ICreateFeature[] oldArray = super.getCreateFeatures();
		ICreateFeature[] array = new ICreateFeature[oldArray.length + 3];
		for (int i = 0; i < oldArray.length; i++) {
			array[i] = oldArray[i];
		}
		array[array.length - 3] = new CreateDataStructureFeature(this);
		array[array.length - 2] = new CreateMethodFeature(this);
		array[array.length - 1] = new CreateDataTypeFeature(this);
	   return array;
	}
	
	@Override
	public IDirectEditingFeature getDirectEditingFeature(
	    IDirectEditingContext context) {
	    PictogramElement pe = context.getPictogramElement();
	    Object bo = getBusinessObjectForPictogramElement(pe);
	    if (bo instanceof DataStructure) {
	        return new EditDataStructureFeature(this);
	    } else if (bo instanceof DataType) {
	        return new EditDataTypeFeature(this);
	    } else if (bo instanceof Method) {
	        return new EditMethodFeature(this);
	    }
	    return super.getDirectEditingFeature(context);
	}
	
	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
	    Shape shape = context.getShape();
	    Object bo = getBusinessObjectForPictogramElement(shape);
	    if (bo instanceof Method || bo instanceof DataStructure || bo instanceof DataType) {
	        return new MoveFeature(this);
	    }
	    return super.getMoveShapeFeature(context);
	}
	
	@Override
	public IResizeShapeFeature getResizeShapeFeature(
	        IResizeShapeContext context) {
	    Shape shape = context.getShape();
	    Object bo = getBusinessObjectForPictogramElement(shape);
	    if (bo instanceof Method || bo instanceof DataStructure || bo instanceof DataType) {
	        return new ResizeFeature(this);
	    }
	    return super.getResizeShapeFeature(context);
	}
	
	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		return new ReconnectionFeature(this);
	}
	
	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
	    PictogramElement pictogramElement = context.getPictogramElement();
	    if (pictogramElement instanceof CurvedConnection) {
	        return new LayoutConnectionFeature(this);
	    }
	    return super.getLayoutFeature(context);
	} 
	
	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
	    return new ICustomFeature[] { new AddPseudoCodeToMethodFeature(this), 
	    		new ChangeAbstractAlgorithmFeature(this), 
	    		new RunToolkitGenerationFeature(this), 
	    		new EditPreConditionFeature(this), 
	    		new EditPostConditionFeature(this),
	    		new EditInvariantFeature(this),
	    		new AddImportsToAlgorithmFeature(this),
	    		new AddInitialValueToDataStructuresFeature(this),
	    		new VerifyPreImplPre(this),
	    		new VerifyPostImplPost(this)};
	} 
	
	@Override
	public ICopyFeature getCopyFeature(ICopyContext context) {
	    return new CopyAlgorithmFeature(this);
	}
	
	@Override
	public IPasteFeature getPasteFeature(IPasteContext context) {
	    return new PasteAlgorithmFeature(this);
	}

}
