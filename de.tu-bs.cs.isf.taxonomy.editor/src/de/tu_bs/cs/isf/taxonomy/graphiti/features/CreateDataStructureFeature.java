package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataType;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Class that creates a new DataStructure in the diagram
 * @author Tobias
 *
 */
public class CreateDataStructureFeature  extends AbstractCreateFeature {

	/**
	 * Constructor of the class
	 * @param fp The FeatureProvider
	 */
	public CreateDataStructureFeature(IFeatureProvider fp) {
		super(fp, "DataStructure", "Create DataStructure");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof Algorithm;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Algorithm algorithm = (Algorithm) getBusinessObjectForPictogramElement(context.getTargetContainer());
		DataStructure data = TaxonomyFactory.eINSTANCE.createDataStructure();
		data.setName(createNewName(algorithm));
		DataType type = TaxonomyFactory.eINSTANCE.createDataType();
		type.setName("int");
		data.setDataType(type);
		algorithm.getDataStructures().add(data);

		return new Object[] { data };
	}

	/**
	 * Creates an unique name
	 * @param algorithm	The parent Algorithm
	 * @return	An unique name
	 */
	private String createNewName(Algorithm algorithm) {
		String initialName = "newDataStructure";
		String name = initialName;
		int number = 0;
		while (findDataStructure(name, algorithm) != null) {
			number++;
			name = initialName + number;
		}
		return name;
	}

	/**
	 * Method to search for DataStructures with same name
	 * @param name	The input name that should be searched
	 * @param algorithm	The parent Algorithm
	 * @return	A DataStructure with the same name or null
	 */
	private DataStructure findDataStructure(String name, Algorithm algorithm) {
		for (DataStructure data : algorithm.getDataStructures()) {
			if (name.equals(data.getName())) {
				return data;
			}
		}
		return null;
	}
}
