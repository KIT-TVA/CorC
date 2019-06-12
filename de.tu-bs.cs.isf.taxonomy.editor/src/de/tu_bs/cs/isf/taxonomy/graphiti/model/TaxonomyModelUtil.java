package de.tu_bs.cs.isf.taxonomy.graphiti.model;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.GlobalConditions;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.JavaVariables;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Renaming;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Taxonomy;
import de.tu_bs.cs.isf.taxonomy.model.taxonomy.TaxonomyFactory;

/**
 * Util class that tracks the model in the background
 * @author Tobias
 *
 */
public class TaxonomyModelUtil {

	/**
	 * Adds an Algorithm to the model
	 * @param algo	The new Algorithm
	 * @param d	The diagram
	 * @throws CoreException	Possible core exception
	 * @throws IOException	Possible IO exception
	 */
	public static void saveAlgorithmToModelFile(final Algorithm algo, final Diagram d) throws CoreException, IOException {
		final Resource resource = getResource(d);
		Taxonomy taxonomy = getTaxonomy(resource);
		taxonomy.getAlgorithms().add(algo);
	}
	
	/**
	 * Finds or creates the corresponding resource of the diagram
	 * @param d	The diagram
	 * @return	The resource of the diagram (Model in the background)
	 * @throws CoreException	Possible core exception
	 * @throws IOException	Possible IO exception
	 */
	public static Resource getResource(final Diagram d) throws CoreException, IOException {
		URI uri = d.eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("taxonomy");
		System.out.println(uri);
		ResourceSet rSet = d.eResource().getResourceSet();
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource file = workspaceRoot.findMember(uri.toPlatformString(true));
		if (file == null || !file.exists()) {
			Resource createResource = rSet.createResource(uri);
			createResource.save(Collections.emptyMap());
			createResource.setTrackingModification(true);
			final Resource resource = rSet.getResource(uri, true);
			resource.getContents().add(TaxonomyFactory.eINSTANCE.createTaxonomy());
			((Taxonomy) resource.getContents().get(0)).setName(d.getName());
		}
		final Resource resource = rSet.getResource(uri, true);
		return resource;

	}
	
	/**
	 * Gets the taxonomy that is saved in the resource
	 * @param resource	The resource
	 * @return	The taxonomy in the resource
	 */
	public static Taxonomy getTaxonomy(Resource resource) {
		return ((Taxonomy) resource.getContents().get(0));
	}
	
	/**
	 * Gets the taxonomy that is saved in the model of the diagram
	 * @param d	The diagram
	 * @return	The taxonomy of the diagram
	 * @throws CoreException	Possible core exception
	 * @throws IOException	Possible IO exception
	 */
	public static Taxonomy getTaxonomy(Diagram d) throws CoreException, IOException {
		final Resource resource = getResource(d);
		Taxonomy taxonomy = getTaxonomy(resource);
		return taxonomy;
	}
	
	public static void saveGlobalConditionsToModelFile(GlobalConditions conditions, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(conditions);
	}
	
	public static void saveVariablesToModelFile(final JavaVariables variables, final Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(variables);
	}
	
	public static void saveRenamingToModelFile(Renaming renaming, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(renaming);
	}
}