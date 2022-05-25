package de.tu_bs.cs.isf.cbc.tool.model;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.IFbCSpecification;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Method;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodLink;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.Security;

public class CbcModelUtil {
	
	public static void saveFormulaToModelFile(final CbCFormula formula, final Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(formula);
	}
	
	public static void saveMethodToModelFile(final Method method, final Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(method);
	}
	
	public static void saveVariablesToModelFile(final JavaVariables variables, final Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(variables);
	}
	
	private static void saveSecurityToModelFile(final Security security, final Resource resource) throws CoreException, IOException {
		resource.getContents().add(security);
	}
	
	public static CbCFormula readFormula(URI uri) {
		CbcmodelPackage.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("cbcmodel", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.getResource(uri, true);
		for (EObject obj :  r.getContents()) {
			if (obj instanceof CbCFormula) {
				CbCFormula formula = (CbCFormula) obj;
				return formula;
			}
		}
		return null;
	}

	public static void saveGlobalConditionsToModelFile(GlobalConditions conditions, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(conditions);
	}

	public static void saveMethodLinkToModelFile(MethodLink methodLink, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(methodLink);
	}

	public static void saveIFbCSpecificationToModelFile(IFbCSpecification spec, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(spec);
	}
	
	public static void saveRenamingToModelFile(Renaming renaming, Diagram d) throws CoreException, IOException {
		Resource resource = getResource(d);
		resource.getContents().add(renaming);
	}
	
	public static Resource getResource(Diagram d) throws CoreException, IOException {
		URI uri = d.eResource().getURI();
		uri = uri.trimFragment();
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("cbcmodel");
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
