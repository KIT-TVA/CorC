package helper;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public interface IClassUtil {

	final static String ID_CBC_MODEL = ".cbcmodel";
	final static String ID_CBC_DIAGRAM = ".diagram";
	final static String ID_CBCCLASS_MODEL = ".cbcclass";
	
	public void ClassUtil(String name);	

	public IProject refreshProject(String path);
	
	public Resource getCbcModelResource(String path, String methodName, String feature, String className);
	
	public Resource getCbcDiagramResource(String path, String methodName);
	
	public Resource getClassModelResource(String path, String className);
	
	public Resource getClassModelResource(String path, String className, String feature);
	
	public List<IFile> getFilesOfType(IContainer folder, String type);
		
	public Resource getResourceFromFile(IFile file, ResourceSet resourceSet);

	public CbCFormula createFormula(String name);
}