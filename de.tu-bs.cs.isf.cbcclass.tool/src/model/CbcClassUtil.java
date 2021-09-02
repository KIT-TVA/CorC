package model;

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

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Field;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodSignature;

public class CbcClassUtil {
	
	
	
	public static void saveModelClass(ModelClass modelClass, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(modelClass);
	}
	
	public static void saveField(Field field, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(field);
	}
	
	public static void saveCondition(Condition condition, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(condition);
	}
	
	public static void saveMethod(Method method, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(method);
	}
	
	public static void saveMethodSignature(MethodSignature signature, Diagram diagram) throws CoreException, IOException {
		Resource resource = getResource(diagram);
		resource.getContents().add(signature);			
	}
	
	public static void saveFormulaToModelFile(final CbCFormula formula, final Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(formula);
	}
	
	
	
	public static Resource getResource(Diagram d) throws CoreException, IOException {
		URI uri = d.eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("cbcclass");
		ResourceSet rSet = d.eResource().getResourceSet();
		final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource file = workspaceRoot.findMember(uri.toPlatformString(true));
		if (file == null || !file.exists()) {
			Resource createResource = rSet.createResource(uri);
			createResource.save(Collections.emptyMap());
			createResource.setTrackingModification(true);
		}
		return rSet.getResource(uri, true);
	}

}
