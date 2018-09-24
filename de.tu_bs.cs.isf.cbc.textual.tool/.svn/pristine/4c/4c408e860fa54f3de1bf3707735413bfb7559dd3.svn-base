package de.tu_bs.cs.isf.cbc.textual.tool.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem;

public class GetCbCFileUtil {
	
	 public static Collection<CbCFormula> getCbCFormulas(IProject p) {
	       final List<IFile> files = getCbCFiles(p);
	       final List<CbCFormula> formulaList = new ArrayList<CbCFormula>();
	       final ResourceSet rSet = new ResourceSetImpl();
	       for (final IFile file : files) {
	            final CbCFormula formula = getFormulaFromFile(file, rSet);
	            if (formula != null) {
	                formulaList.add(formula);
	            }
	       }
	       return formulaList;
	    }
	 
	    private static List<IFile> getCbCFiles(IContainer folder) {
	       final List<IFile> ret = new ArrayList<IFile>();
	       try {
	            final IResource[] members = folder.members();
	            for (final IResource resource : members) {
	                 if (resource instanceof IContainer) {
	                     ret.addAll(getCbCFiles((IContainer) resource));
	                 } else if (resource instanceof IFile) {
	                     final IFile file = (IFile) resource;
	                     if (file.getName().endsWith(".cbctxt")) {
	                          ret.add(file);
	                     }
	                 }
	            }
	       } catch (final CoreException e) {
	                e.printStackTrace();
	       }
	       return ret;
	    }
	 
	    private static CbCFormula getFormulaFromFile(IFile file,
	                                              ResourceSet resourceSet) {
	       // Get the URI of the model file.
	       final URI resourceURI = getFileURI(file, resourceSet);
	       // Demand load the resource for this file.
	       Resource resource;
	       try {
	            resource = resourceSet.getResource(resourceURI, true);
	            if (resource != null) {
	            // does resource contain a cbcproblem as root object?
	            final EList<EObject> contents = resource.getContents();
	            for (final EObject object : contents) {
	                 if (object instanceof CbCProblem) {
	                      return ((CbCProblem) object).getCbcformula();
	                 }
	             }
	       }
	       } catch (final WrappedException e) {
	                e.printStackTrace();
	       }
	       return null;
	    }
	 
	    private static URI getFileURI(IFile file, ResourceSet resourceSet) {
	       final String pathName = file.getFullPath().toString();
	       URI resourceURI = URI.createFileURI(pathName);
	       resourceURI = resourceSet.getURIConverter().normalize(resourceURI);
	       return resourceURI;
	    }
}
