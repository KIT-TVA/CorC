package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Class to create a new DataType for Methods
 * @author Tobias
 *
 */
public class CreateDataTypeFeature  extends AbstractCreateFeature {

	/**
	 * The Constructor of the class
	 * @param fp	The FeatureProvider
	 */
	public CreateDataTypeFeature(IFeatureProvider fp) {
		super(fp, "MethodType", "Create MethodType");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof Method;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Method method = (Method) getBusinessObjectForPictogramElement(context.getTargetContainer());
		DataType type = TaxonomyFactory.eINSTANCE.createDataType();
		type.setName("int");
		method.getDataTypes().add(type);
		updatePictogramElement(context.getTargetContainer().getContainer());
		return new Object[] { method };
	}
}
