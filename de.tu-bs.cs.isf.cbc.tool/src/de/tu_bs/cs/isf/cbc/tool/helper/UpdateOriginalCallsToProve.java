package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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

import PropertiesView.FeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.OriginalStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCFeatureProvider;

/**
 * Class for handling originals-calls which could be affect due
 * condition-changes of contracts
 * 
 * @author David
 */
public class UpdateOriginalCallsToProve {

	public static void updateOriginalCallsToProve(Condition condition) {

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
		String feature = uri.segment(uri.segmentCount() - 3);

		// get current Method
		String method = uri.trimFileExtension().lastSegment();

		// get all features which could be affected in reverse order
		List<String> affectedOriginalFeatures = new ArrayList<String>();
		for (int i = featModel.getFeatureOrderList().size() - 1; i > 0; i--) {
			if (featModel.getFeatureOrderList().get(i).equals(feature)) {
				break;
			} else {
				// Checks if the method of the changed contract is part of a valid replacement
				// for the possible affected method
				FeatureModel fm = new FeatureModel(featModel.getFeatureOrderList().get(i));
				fm.getOriginalFeatureNames(method);// Need to be updated internally in FeatureModel
				List<String> affectedOriginalFeature = new ArrayList<String>();
				affectedOriginalFeature.add(featModel.getFeatureOrderList().get(i));
				List<String> replacementConfigs = fm.getFeatureConfigs(affectedOriginalFeature, "original");
				if (!replacementConfigs.isEmpty()) {
					affectedOriginalFeatures.add(featModel.getFeatureOrderList().get(i));
				}
			}
		}
		// To get the composition order
		Collections.reverse(affectedOriginalFeatures);
		// Get diagramUri from CbCUri
		uri = uri.trimFileExtension();
		uri = uri.appendFileExtension("diagram");
		// Get URIs for all features
		List<URI> diagramURIs = new ArrayList<URI>();
		for (int i = 0; i < affectedOriginalFeatures.size(); i++) {
			String[] lastSegments = { affectedOriginalFeatures.get(i), "diagram", uri.lastSegment() };
			diagramURIs.add(uri.trimSegments(uri.segmentCount() - 3).appendSegments(lastSegments));
		}
		// Throw out features which doesn't implement the method
		ResourceSet rs = new ResourceSetImpl();
		for (int i = 0; i < diagramURIs.size(); i++) {
			if (!rs.getURIConverter().exists(diagramURIs.get(i), null)) {
				affectedOriginalFeatures.remove(diagramURIs.get(i).segment(diagramURIs.get(i).segmentCount() - 3));
			}
		}
		// Get all valid URIs for all features
		List<URI> validDiagramURIs = new ArrayList<URI>();
		for (int i = 0; i < affectedOriginalFeatures.size(); i++) {
			String[] lastSegments = { affectedOriginalFeatures.get(i), "diagram", uri.lastSegment() };
			validDiagramURIs.add(uri.trimSegments(diagramURIs.get(i).segmentCount() - 3).appendSegments(lastSegments));
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
			boolean hasOriginal = false;
			for (int j = 0; j < pes.size(); j++) {
				bos.add(test.getFeatureProvider().getBusinessObjectForPictogramElement(pes.get(j)));
				if (bos.get(j).getClass().equals(OriginalStatementImpl.class)) {
					hasOriginal = true;
					((OriginalStatementImpl) bos.get(j)).setProven(false);
				}
				UpdateContext context = new UpdateContext(pes.get(j));
				featureProvider.updateIfPossible(context);
			}
			// Save URIs which implement an original
			if (hasOriginal == true) {
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
		if (!validDiagramsAffectedURIs.isEmpty())

		{
			Display display = PlatformUI.getWorkbench().getDisplay();
			AbstractNotificationPopup notification = new NotificationPopup(display, validDiagramsAffectedURIs,
					"original");
			notification.setFadingEnabled(false);
			notification.setDelayClose(0L);
			notification.open();
		}

	}
}
