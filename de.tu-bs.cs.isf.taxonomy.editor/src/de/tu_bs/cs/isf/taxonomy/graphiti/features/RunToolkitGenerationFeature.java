package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;

import de.tu_bs.cs.isf.toolkit.support.ToolkitConstruction;

/**
 * Class that changes the abstract value of algorithms
 * @author Tobias
 *
 */
public class RunToolkitGenerationFeature extends MyAbstractAsynchronousCustomFeature {
	 
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public RunToolkitGenerationFeature(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Generates Toolkit";
    }
 
    @Override
    public String getDescription() {
        return "Generates the corresponding toolkit for the taxonomy.";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        return true;
    }
 
    @Override
    public void execute(ICustomContext context, IProgressMonitor monitor) {
    	monitor.beginTask("Generate Toolkit", IProgressMonitor.UNKNOWN);
		URI uri = getDiagram().eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("taxonomy");
        ToolkitConstruction.runToolkitGeneration(uri, monitor);
		monitor.done();
    }
}