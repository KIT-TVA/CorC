package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Class to create Methods in the diagram
 * @author Tobias
 *
 */
public class CreateMethodFeature extends AbstractCreateFeature {

	/**
	 * The constructor of the class
	 * @param fp	The FeatureProvider
	 */
	public CreateMethodFeature(IFeatureProvider fp) {
		super(fp, "Method", "Create Method");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof Algorithm;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Algorithm algorithm = (Algorithm) getBusinessObjectForPictogramElement(context.getTargetContainer());
		Method method = TaxonomyFactory.eINSTANCE.createMethod();
		method.setName(createNewName(algorithm));
		algorithm.getMethods().add(method);

		return new Object[] { method };
	}

	/**
	 * Creates an unique name
	 * @param algorithm	The parent Algorithm
	 * @return	An unique name
	 */
	private String createNewName(Algorithm algorithm) {
		String initialName = "newMethod";
		String name = initialName;
		int number = 0;
		while (findMethod(name + "(para)", algorithm) != null) {
			number++;
			name = initialName + number;
		}
		name = name + "(para)";
		return name;
	}

	/**
	 * Searches for Methods with same name
	 * @param name	The input name that should be searched
	 * @param algorithm	The parent Algorithm
	 * @return	A Method with the same name or null
	 */
	private Method findMethod(String name, Algorithm algorithm) {
		for (Method method : algorithm.getMethods()) {
			if (name.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}
}
