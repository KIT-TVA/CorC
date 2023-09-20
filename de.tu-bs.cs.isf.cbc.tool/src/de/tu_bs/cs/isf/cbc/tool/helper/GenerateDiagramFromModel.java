package de.tu_bs.cs.isf.cbc.tool.helper;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.SelectionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.SmallRepetitionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.StrengthWeakStatement;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class GenerateDiagramFromModel {
	private Diagram diagram;
	
	public Diagram getDiagram() {
		return diagram;
	}

	public GenerateDiagramFromModel() {
	}
	
	public void execute(Resource resource) {
		CbCFormula formula = null;
		JavaVariables vars = null;
		GlobalConditions conds = null;
		Renaming renaming = null;
		for (EObject content : resource.getContents()) {
			if (content instanceof CbCFormula) {
				formula = (CbCFormula) content;
			} else if (content instanceof JavaVariables) {
				vars = (JavaVariables) content;
			} else if (content instanceof GlobalConditions) {
				conds = (GlobalConditions) content;
			} else if (content instanceof Renaming) {
				renaming = (Renaming) content;
			}
		}
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = resource.getURI().trimFileExtension();
		uri = uri.appendFileExtension("diagram");
		// Create the diagram and its file
		diagram = Graphiti.getPeCreateService().createDiagram("cbc", formula.getName(), true);
		Resource diagramResource = resourceSet.createResource(uri);
		diagramResource.getContents().add(diagram);

		
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
				"de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
		IFeatureProvider featureProvider = dtp.getFeatureProvider();

		// Add all classes to diagram
		addFormula(featureProvider, formula, diagram, 20, 20);
		if (vars != null)
			addElement(featureProvider, vars, diagram, 600, 20);
		if (conds != null)
			addElement(featureProvider, conds, diagram, 600, 220);
		if (renaming != null)
			addElement(featureProvider, renaming, diagram, 600, 420);
		//if(javaClass != null)
			//addElement(featureProvider, javaClass, diagram, 600, 20);
		
		UpdateDiagram.run(diagram);
		
		try {
			diagramResource.save(Collections.EMPTY_MAP);
			diagramResource.setTrackingModification(true);
			String projectPath = FileUtil.getProjectLocation(diagram.eResource().getURI());
			URI fullUri = URI.createURI("file:/" + projectPath.substring(0, projectPath.lastIndexOf("/") + 1) + uri.toString().replace("platform:/resource/", ""));
			IPath iLocation = Path.fromOSString(fullUri.toFileString());
			IFile ifile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(iLocation);
			//ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			ifile.getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Local refresh not available!");
		}
	}

	private void addFormula(IFeatureProvider featureProvider, CbCFormula formula, Diagram diagram, int x, int y) {
		AddContext addContext = new AddContext();
		addContext.setNewObject(formula);
		addContext.setTargetContainer(diagram);
		addContext.setX(x);
		addContext.setY(y);
		IAddFeature addFeature = featureProvider.getAddFeature(addContext);
		if (addFeature.canAdd(addContext)) {
			addFeature.add(addContext);
			if (formula.getStatement().getRefinement() != null) {
				Shape pe = (Shape) featureProvider.getPictogramElementForBusinessObject(formula.getStatement());
				addNextElement(featureProvider, formula.getStatement().getRefinement(), diagram, x, y + 200, pe);
			}
		}
		
	}
	
	private void addNextElement(IFeatureProvider featureProvider, AbstractStatement statement, Diagram diagram, int x, int y, Shape sourcePe) {
		AddContext addContext = new AddContext();
		addContext.setNewObject(statement);
		addContext.setTargetContainer(diagram);
		addContext.setX(x);
		addContext.setY(y);
		
		IAddFeature addFeature = featureProvider.getAddFeature(addContext);
		if (addFeature.canAdd(addContext)) {
			Shape pe = (Shape) addFeature.add(addContext);
			AddConnectionContext addConContext = new AddConnectionContext(sourcePe.getAnchors().get(0), pe.getAnchors().get(0));
			addFeature = featureProvider.getAddFeature(addConContext);
			if (addFeature.canAdd(addConContext)) {
				addFeature.add(addConContext);
			}
			
			if (statement instanceof SmallRepetitionStatement) {
				SmallRepetitionStatement repetitionStatement = (SmallRepetitionStatement) statement;
				if (repetitionStatement.getLoopStatement().getRefinement() != null) {
					Shape repPe = (Shape) findPictogramElementForBusinessObject(diagram, repetitionStatement.getLoopStatement());
					addNextElement(featureProvider, repetitionStatement.getLoopStatement().getRefinement(), diagram, x, y + 350, repPe);
				}
			} else if (statement instanceof SelectionStatement) {
				SelectionStatement selectionStatement = (SelectionStatement) statement;
				x = x - 200;
				y = y + 250;
				for (AbstractStatement childStatement : selectionStatement.getCommands()) {
					if (childStatement.getRefinement() != null) {
						Shape selPe = (Shape) findPictogramElementForBusinessObject(diagram, childStatement);
						addNextElement(featureProvider, childStatement.getRefinement(), diagram, x, y, selPe);
						x = x + 400;
					}
				}
			}  else if (statement instanceof CompositionStatement) {
				CompositionStatement compositionStatement = (CompositionStatement) statement;
				
				if (compositionStatement.getFirstStatement().getRefinement() != null) {			
					Shape st1Pe = (Shape)findPictogramElementForBusinessObject(diagram, compositionStatement.getFirstStatement());
					//Shape st1Pe = (Shape) featureProvider.getPictogramElementForBusinessObject(compositionStatement.getFirstStatement());
					addNextElement(featureProvider, compositionStatement.getFirstStatement().getRefinement(), diagram, x - 100, y + 350, st1Pe);
				}
				if (compositionStatement.getSecondStatement().getRefinement() != null) {
					Shape st2Pe = (Shape) findPictogramElementForBusinessObject(diagram, compositionStatement.getSecondStatement());
					addNextElement(featureProvider, compositionStatement.getSecondStatement().getRefinement(), diagram, x + 300, y + 350, st2Pe);
				}
			} else if (statement instanceof StrengthWeakStatement) {
				StrengthWeakStatement strengthWeakStatement = (StrengthWeakStatement) statement;
				if (strengthWeakStatement.getRefinement() != null) {
					Shape stPe = (Shape) findPictogramElementForBusinessObject(diagram, strengthWeakStatement);
					ContainerShape container = (ContainerShape) stPe;
					for (Shape childShape : container.getChildren()) {
						for (Property property : childShape.getGraphicsAlgorithm().getProperties()) {
							if (property.getValue().equals("statementText")) {
								stPe = childShape;
							}
						}
					}
					addNextElement(featureProvider, strengthWeakStatement.getRefinement(), diagram, x, y + 150, stPe);
				}
			}
		}
	}

	/**
	 * (this method replaces the previously used
	 * 'featureProvider.getPictogramElementForBusinessObject(statement)' ) returns
	 * the pictogram element which represents the given statement
	 * 
	 * @param diagram
	 * @param statement
	 * @return
	 */
	private PictogramElement findPictogramElementForBusinessObject(Diagram diagram, AbstractStatement statement) {
		PictogramElement pe = null;
		Collection<PictogramLink> pictogramLinks = diagram.getPictogramLinks();
		for (PictogramLink pictogramLink : pictogramLinks) {
			List<EObject> businessObjects = pictogramLink.getBusinessObjects();
			for (EObject obj : businessObjects) {
				if (obj.toString().equals(statement.toString())) {
					pe = pictogramLink.getPictogramElement();
				}
			}
		}
		return pe;
	}

	private void addElement(IFeatureProvider featureProvider, EObject object, Diagram diagram, int x, int y) {
		AddContext addContext = new AddContext();
		addContext.setNewObject(object);
		addContext.setTargetContainer(diagram);
		addContext.setX(x);
		addContext.setY(y);
		IAddFeature addFeature = featureProvider.getAddFeature(addContext);
		if (addFeature.canAdd(addContext)) {
			PictogramElement pe = addFeature.add(addContext);
			UpdateContext uContext = new UpdateContext(pe);
			featureProvider.updateIfPossible(uContext);
			LayoutContext lContext = new LayoutContext(pe);
			featureProvider.layoutIfPossible(lContext);
		}
	}
}
