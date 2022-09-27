package de.tu_bs.cs.isf.commands.toolbar.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCProblem;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbcmodelPackage;
import de.tu_bs.cs.isf.cbc.tool.helper.GenerateDiagramFromModel;

public class GenerateHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			List<IFile> fileList = strucSelection.toList();
			if (fileList.size() != 1) {
				throw new ExecutionException("Select only one File.");
			}
			IFile iFile = fileList.get(0);
			if (!iFile.getFileExtension().equals("cbcmodel")) {
				throw new ExecutionException("Select exactly one cbcmodel which should be transformed.");
			}
			
			CbcmodelPackage.eINSTANCE.eClass();
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("cbcmodel", new XMIResourceFactoryImpl());
			
			ResourceSet rs = new ResourceSetImpl();
			URI uri = URI.createFileURI(iFile.getLocation().toPortableString());
			Resource oldResource = rs.getResource(uri, true);
			CbCProblem problem = (CbCProblem) oldResource.getContents().get(0);
			CbCFormula formula = TraverseFormulaAndTransform.traverseFormulaAndTransform(problem.getCbcformula());
			problem.setCbcformula(formula);
			
			uri = uri.trimFileExtension();
			String lastSegment =  uri.segment(uri.segmentCount() - 1);
			uri = uri.trimSegments(1);
			uri = uri.appendSegment(lastSegment + "Graphical");
			uri = uri.appendFileExtension("cbcmodel");
			Resource newResource = rs.createResource(uri);
			newResource.getContents().add(formula);
			if (problem.getJavaVariable() != null)
				newResource.getContents().add(problem.getJavaVariable());
			if (problem.getGlobalcondition() != null)
			newResource.getContents().add(problem.getGlobalcondition());
			if (problem.getRenaming() != null)
			newResource.getContents().add(problem.getRenaming());
			
			GenerateDiagramFromModel generator = new GenerateDiagramFromModel();
			generator.execute(newResource);
			
			try {
				newResource.save(Collections.EMPTY_MAP);
				newResource.setTrackingModification(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
