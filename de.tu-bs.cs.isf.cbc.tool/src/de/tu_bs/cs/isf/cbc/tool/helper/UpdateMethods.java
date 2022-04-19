package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.impl.ContainerShapeImpl;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.mylyn.commons.ui.dialogs.AbstractNotificationPopup;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.MethodStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.MethodStatementImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.OriginalStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCFeatureProvider;

public class UpdateMethods {

	public static void updateMethods(Condition condition) {

		// Get Uri of Condition
		URI uri = condition.eResource().getURI();

		// get corresponding FeatureModel
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = window.getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();
		IEditorInput input = activeEditor.getEditorInput();
		IResource diagramResource = input.getAdapter(IResource.class);
		IResource projectResource = diagramResource.getParent().getParent().getParent().getParent();
		IPath projectPath = projectResource.getLocation();
		IPath modelPath = projectPath.append("model.xml");
		IFile modelFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(modelPath);
		Path path = Paths.get(modelFile.getLocationURI());
		IFeatureModel featModel = FeatureModelManager.load(path);

		// get current Feature
		String feature = uri.segment(3);

		// get current Method
		String method = uri.trimFileExtension().lastSegment().toLowerCase();

		// get all features which could be affected
		List<String> affectedOriginalFeatures = featModel.getFeatureOrderList();

		// Get all valid URIs for all features
		IProject project = projectResource.getProject();
		List<URI> validDiagramURIs = new ArrayList<URI>();
		for (int i = 0; i < affectedOriginalFeatures.size(); i++) {
			IFolder folder = project.getFolder(("features/" + affectedOriginalFeatures.get(i) + "/diagram"));
			try {
				for (int x = 0; x < folder.members().length; x++) {
					if (folder.members()[x].getName().contains(".diagram")) {
						String[] lastSegments = { affectedOriginalFeatures.get(i), "diagram",
								folder.members()[x].getName() };
						validDiagramURIs.add(uri.trimSegments(uri.segmentCount() - 3).appendSegments(lastSegments));
					}
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Forall valid URIs
		List<URI> validDiagramsAffectedURIs = new ArrayList<URI>();
		for (int i = 0; i < validDiagramURIs.size(); i++) {
			// Get DiagramResource
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource diagram_Resource = resourceSet.getResource(validDiagramURIs.get(i), true);
			Resource cbc_Resource = resourceSet
					.getResource(validDiagramURIs.get(i).trimFileExtension().appendFileExtension("cbcmodel"), true);
			// Get Diagram
			Diagram diagram = null;
			try {
				if (diagram_Resource != null) {
					// does resource contain a diagram as root object?
					final EList<EObject> contents = diagram_Resource.getContents();
					for (final EObject object : contents) {
						if (object instanceof Diagram) {
							diagram = (Diagram) object;
						}
					}
				}
			} catch (final WrappedException e) {
				e.printStackTrace();
			}

			// Get Diagram Pictograms
			List<PictogramElement> pes = new ArrayList<PictogramElement>();
			List<Object> bos = new ArrayList<Object>();
			CbCDiagramTypeProvider test = new CbCDiagramTypeProvider();
			IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
					"de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
			CbCFeatureProvider featureProvider = (CbCFeatureProvider) dtp.getFeatureProvider();

			// Only add Pictograms to Pictogram-List
			for (int j = 0; j < diagram_Resource.getContents().get(0).eContents().size(); j++) {
				if (diagram_Resource.getContents().get(0).eContents().get(j).getClass()
						.equals(ContainerShapeImpl.class)) {
					pes.add((PictogramElement) diagram_Resource.getContents().get(0).eContents().get(j));
				}
			}
			// Manipulate Pictograms
			boolean hasMethod = false;
			for (int j = 0; j < pes.size(); j++) {
				bos.add(test.getFeatureProvider().getBusinessObjectForPictogramElement(pes.get(j)));
				if (bos.get(j).getClass().equals(MethodStatementImpl.class)) {
					if (((MethodStatement) bos.get(j)).getName().contains(method + "(")) {
						hasMethod = true;
						((MethodStatementImpl) bos.get(j)).setProven(false);
					}
				}
				// In cases where method call occures in pre or postcondition of a CbC-Formula
				if (bos.get(j).getClass().equals(CbCFormulaImpl.class)) {
					if (((CbCFormulaImpl) bos.get(j)).getStatement().getPreCondition().getName().contains(method)) {
						hasMethod = true;
						((CbCFormulaImpl) bos.get(j)).setProven(false);
					} else if (((CbCFormulaImpl) bos.get(j)).getStatement().getPostCondition().getName()
							.contains(method)) {
						hasMethod = true;
						((OriginalStatementImpl) bos.get(j)).setProven(false);
					}
				}
				UpdateContext context = new UpdateContext(pes.get(j));
				featureProvider.updateIfPossible(context);
			}
			// Save URIs which implement a method-call
			if (hasMethod == true) {
				validDiagramsAffectedURIs.add(validDiagramURIs.get(i));
			}
			// Save Diagram
			try {
				diagram_Resource.save(Collections.EMPTY_MAP);
				diagram_Resource.setTrackingModification(true);
				cbc_Resource.save(Collections.EMPTY_MAP);
				cbc_Resource.setTrackingModification(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Setup Notification
		if (!validDiagramsAffectedURIs.isEmpty()) {
			Display display = PlatformUI.getWorkbench().getDisplay();
			AbstractNotificationPopup notification = new NotificationPopup(display, validDiagramsAffectedURIs,
					"method");
			notification.setFadingEnabled(false);
			notification.setDelayClose(0L);
			notification.open();
		}

	}
}
