package helper;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.util.GetDiagramUtil;

import org.eclipse.emf.common.util.URI;

public class ClassUtil {

	private final static String ID_CBC_MODEL = ".cbcmodel";
	private final static String ID_CBC_DIAGRAM = ".diagram";
	private final static String ID_CBCCLASS_MODEL = ".cbcclass";
	
	public ClassUtil(String name) {
		
	}	

	public static IProject refreshProject(String path) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path) || p.getName().equals(path.replace("platform:/resource/", ""))) {
				thisProject = p;
			}
		}
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			return null;
		}
		return thisProject;
	}
	
	public static Resource getCbcModelResource(String path, String methodName, String feature, String className) {		
		final List<IFile> filess = getFilesOfType(refreshProject(path), ID_CBC_MODEL);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = GetDiagramUtil.getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equals(methodName + ".cbcmodel") && (file.getParent().getParent().getName().equals(feature) || feature.equals(""))) {
				if (resource.getContents().get(0) instanceof CbCFormula) {
					if (((CbCFormula) resource.getContents().get(0)).getClassName().equals(className)) {
						return resource;
					}
				}
			}
		}
		return null;
	}
	
	public static Resource getCbcDiagramResource(String path, String methodName) {
		final List<IFile> filess = getFilesOfType(refreshProject(path), ID_CBC_DIAGRAM);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = GetDiagramUtil.getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equals(methodName + ".diagram")) {
				return resource;
			}
		}
		return null;
	}
	
	public static Resource getClassModelResource(String path, String className) {
		return getClassModelResource(path, className, "");
	}
	
	public static Resource getClassModelResource(String path, String className, String feature) {		
		if (path == null || path.length() == 0) return null;
		final List<IFile> filess = getFilesOfType(refreshProject(path), ID_CBCCLASS_MODEL);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equalsIgnoreCase(className + ".cbcclass") && (file.getParent().getParent().getName().equals(feature) || feature.equals("") || feature.equals("default"))) {
				return resource;
			}
		}
		return null;
	}
	
	public static List<IFile> getFilesOfType(IContainer folder, String type) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getFilesOfType((IContainer) resource, type));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(type)) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}
		
	public static Resource getResourceFromFile(IFile file, ResourceSet resourceSet) {
		URI resourceURI = URI.createPlatformResourceURI(file.getFullPath().toString());
		// Demand load the resource for this file.
		Resource resource = resourceSet.getResource(resourceURI, true);
		return resource;
	}

	public static CbCFormula createFormula(String name) {
		CbCFormula formula = CbcmodelFactory.eINSTANCE.createCbCFormula();
		formula.setName(name);
		AbstractStatement statement = CbcmodelFactory.eINSTANCE.createAbstractStatement();
		statement.setName("statement");
		formula.setStatement(statement);
		Condition preCondition = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition.setName("pre");
		statement.setPreCondition(preCondition);
		Condition preCondition2 = CbcmodelFactory.eINSTANCE.createCondition();
		preCondition2.setName("pre");
		formula.setPreCondition(preCondition2);
		Condition postCondition = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition.setName("post");
		statement.setPostCondition(postCondition);
		Condition postCondition2 = CbcmodelFactory.eINSTANCE.createCondition();
		postCondition2.setName("post");
		formula.setPostCondition(postCondition2);
		return formula;
	}
}