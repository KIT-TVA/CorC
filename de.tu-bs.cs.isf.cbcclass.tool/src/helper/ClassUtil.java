package helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelFactory;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariable;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.VariableKind;
import de.tu_bs.cs.isf.cbc.tool.helper.GetDiagramUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

public class ClassUtil {

	String projectName;

	public ClassUtil(String name) {
		this.projectName = name;
	}

	public void writeJavaClass(String location, Resource cbcclass, Method method) {
		File javaFile = new File(location);
		try {
			javaFile.createNewFile();

			FileWriter fw = new FileWriter(javaFile);
			BufferedWriter bw = new BufferedWriter(fw);

			String vars = "";
			String methods = "\n\n";
			
			String className = method.getParentClass().getName();
			className = className.substring(0,1).toUpperCase() + className.substring(1, className.length());
			
			bw.write("public class " + className + " {\n\n");

			for (EObject obj : cbcclass.getContents()) {
				if (obj instanceof ModelClass) {
					EList<Method> me = ((ModelClass) obj).getMethods();
					for (Method methode : me) {
						if (!methode.getName().equals(method.getName())) {
							methods = methods + methode.getVisibility().toString().toLowerCase() + methode.getSignature().substring(methode.getSignature().indexOf(" ")) + " {\n\n}\n\n";
						}
					}
				}
			}
			bw.write(vars);
			bw.write(methods);
			bw.write(method.getVisibility().toString().toLowerCase() + method.getSignature().substring(method.getSignature().indexOf(" ")) + " {\n\n}\n\n");
			bw.write("}");
			bw.close();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(javaFile.getAbsolutePath());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
		}
	}

	// fertig und wird gebraucht
	public String getClassPath() {
		java.net.URI uri = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
		String path = uri.toString() + "\\" + projectName + "\\";
		path = path.replace("file:/", "");
		return path;
	}

	// get parameters from method signature including return parameter
	public static JavaVariables getParamsFromSignature(JavaVariables variables, String input) {
		String[] parameters = input.substring(input.indexOf("(") + 1, input.indexOf(")")).split(",");
		for (String param : parameters) {
			if (!param.equals("")) {
				JavaVariable javaVariable = CbcmodelFactory.eINSTANCE.createJavaVariable();
				javaVariable.setName(param.trim());
				javaVariable.setKind(VariableKind.PARAM);
				variables.getVariables().add(javaVariable);
			}
		}
		String returnType = input.replaceFirst("private ", "").replaceFirst("public ", "").replaceFirst("protected ", "").replaceFirst("static ", "");
		returnType = returnType.split(" ")[0];
		if (!returnType.equals("void")) {
			JavaVariable returnParam = CbcmodelFactory.eINSTANCE.createJavaVariable();
			returnParam.setName(returnType + " ret");
			returnParam.setKind(VariableKind.RETURN);
			variables.getVariables().add(returnParam);
		}
		
		return variables;
	}

	public static void saveResource(Resource r) {
		try {
			r.save(Collections.EMPTY_MAP);
			r.setTrackingModification(true);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath iLocation = Path.fromOSString(r.getURI().toFileString());
			IFile ifile = workspace.getRoot().getFileForLocation(iLocation);
			ifile.getParent().refreshLocal(1, null);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
		}
	}

	public static void refreshProject(String path) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public static Resource getCbcModelResource(String path, String methodName) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();

		final List<IFile> filess = getCbCModelFiles(thisProject);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = GetDiagramUtil.getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equals(methodName + ".cbcmodel")) {
				return resource;
			}
		}
		return null;
	}
	
	public static Resource getCbcDiagramResource(String path, String methodName) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();

		final List<IFile> filess = getCbCDiagramFiles(thisProject);
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
	
	public static Resource getClassDiagramResource(String path, String className) {
		return getClassDiagramResource(path, className, "");
	}

	public static Resource getClassDiagramResource(String path, String className, String feature) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		final List<IFile> filess = getClassDiagramFiles(thisProject);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equalsIgnoreCase(className + ".diagram") && (file.getParent().getParent().getName().equals(feature) || feature.equals(""))) {
				return resource;
			}
		}
		return null;
	}
	
	public static Resource getClassModelResource(String path, String className, String feature) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		final List<IFile> filess = getCbCClassFiles(thisProject);
		final ResourceSet rSets = new ResourceSetImpl();
		for (final IFile file : filess) {
			final Resource resource = getResourceFromFile(file, rSets);
			if (resource != null && file.getName().equalsIgnoreCase(className + ".cbcclass") && (file.getParent().getParent().getName().equals(feature) || feature.equals("") || feature.equals("default"))) {
				return resource;
			}
		}
		return null;
	}
	
	public static List<IFile> getAllCbCClassFiles(String path, String className) {
		path = path.replace('\\', '/');
		IProject thisProject = null;

		for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (p.getLocation().toPortableString().equals(path) || (p.getLocation().toPortableString() + "/").equals(path)) {
				thisProject = p;
			}
		}
		return getAllCbCClassFiles(thisProject);
	}
	
	public static List<IFile> getAllCbCClassFiles(IProject thisProject) {
		try {
			thisProject.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = thisProject.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getCbCClassFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".cbcclass")) {
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
		// Get the URI of the model file.
		final URI resourceURI = getFileURI(file, resourceSet);
		// Demand load the resource for this file.
		Resource resource = resourceSet.getResource(resourceURI, true);
		return resource;
	}

	private static URI getFileURI(IFile file, ResourceSet resourceSet) {
		final String pathName = file.getLocation().toPortableString();
		URI resourceURI = URI.createFileURI(pathName);
		resourceURI = resourceSet.getURIConverter().normalize(resourceURI);
		return resourceURI;
	}

	private static List<IFile> getCbCModelFiles(IContainer folder) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getCbCModelFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".cbcmodel")) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private static List<IFile> getCbCDiagramFiles(IContainer folder) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getCbCDiagramFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".diagram")) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static List<IFile> getCbCClassFiles(IContainer folder) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getCbCClassFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".cbcclass")) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private static List<IFile> getClassDiagramFiles(IContainer folder) {
		final List<IFile> ret = new ArrayList<IFile>();
		try {
			final IResource[] members = folder.members();
			for (final IResource resource : members) {
				if (resource instanceof IContainer) {
					ret.addAll(getClassDiagramFiles((IContainer) resource));
				} else if (resource instanceof IFile) {
					final IFile file = (IFile) resource;
					if (file.getName().endsWith(".diagram")) {
						ret.add(file);
					}
				}
			}
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public CbCFormula createFormula(String name) {
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