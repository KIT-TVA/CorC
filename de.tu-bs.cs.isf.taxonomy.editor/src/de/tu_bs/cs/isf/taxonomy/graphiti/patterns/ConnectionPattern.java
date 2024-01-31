package de.tu_bs.cs.isf.taxonomy.graphiti.patterns;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;

/**
 * Class that creates the graphical representation of the parent hierarchy between Algorithms
 * @author Tobias
 *
 */
public class ConnectionPattern extends AbstractConnectionPattern {
	
	@Override
	public String getCreateName() {
		return "ParentHierarchy";
	}
	
	@Override
	public String getCreateDescription() {
		return "Firsty, select the parent, then the child.";
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return context instanceof IAddConnectionContext;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addConContext = (IAddConnectionContext) context;
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();
		
		int parentX = addConContext.getSourceAnchor().getParent().getGraphicsAlgorithm().getX();
		int childX = addConContext.getTargetAnchor().getParent().getGraphicsAlgorithm().getX();
		double curve;
		if (parentX > childX) {
			curve = 50d;
		} else {
			curve = -50d;
		}

		Connection connection = peCreateService.createCurvedConnection(new double[] { 0d, 0d, 0.5d, curve, 1d, 0d },
				getDiagram());
		connection.setStart(addConContext.getSourceAnchor());
		connection.setEnd(addConContext.getTargetAnchor());

		Polyline polyline = gaService.createPlainPolyline(connection);
		polyline.setForeground(manageColor(IColorConstant.BLACK));

		ConnectionDecorator cd;
		cd = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
		createArrow(cd);
		
		return connection;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		// Defines the start of the connection; allowed are objects that may
		// contain other objects
		Object domainObject = getBusinessObjectForPictogramElement(context.getSourcePictogramElement());
		return domainObject instanceof Algorithm;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		PictogramElement sourcePictogramElement = context.getSourcePictogramElement();
		PictogramElement targetPictogramElement = context.getTargetPictogramElement();

		if (sourcePictogramElement == null || targetPictogramElement == null || targetPictogramElement.equals(getDiagram())) {
			return false;
		}
		
		Object sourceDomainObject = getBusinessObjectForPictogramElement(sourcePictogramElement);
		Object targetDomainObject = getBusinessObjectForPictogramElement(targetPictogramElement);

		if (sourceDomainObject instanceof Algorithm && targetDomainObject instanceof Algorithm) {
			Algorithm targetAlgo = (Algorithm) targetDomainObject;
			Algorithm sourceAlgo = (Algorithm) sourceDomainObject;
			if (checkLoop(sourceAlgo, targetAlgo)) {
				return false;
			}
			if (sourceAlgo.getChildAlgorithms().contains(targetAlgo)) {
				return false;
			}
			if (sourceAlgo.equals(targetAlgo)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if the connection creates a loop in the taxonomy
	 * @param sourceAlgo	the source algorithm of the connection
	 * @param targetAlgo	the target algorithm of the connection
	 * @return	boolean if the connection creates a loop
	 */
	private boolean checkLoop(Algorithm sourceAlgo, Algorithm targetAlgo) {
		boolean isParent = false;
		if (targetAlgo.getChildAlgorithms().contains(sourceAlgo)) {
			return true;
		}
		for (Algorithm parentOfSource : sourceAlgo.getParentAlgorithms()) {
			isParent = checkLoop(parentOfSource, targetAlgo);
			if (isParent) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		Anchor sourceAnchor = context.getSourceAnchor();
		Anchor targetAnchor = context.getTargetAnchor();

		if (targetAnchor == null) {
			Shape shape = (Shape) context.getTargetPictogramElement();
			while (shape.getAnchors().isEmpty()) {
				shape = shape.getContainer();
			}
			targetAnchor = shape.getAnchors().get(0);
		}

		Algorithm sourceObject = (Algorithm) getBusinessObjectForPictogramElement(sourceAnchor.getParent());
		Algorithm targetObject = (Algorithm) getBusinessObjectForPictogramElement(targetAnchor.getParent());
		
		sourceObject.getChildAlgorithms().add(targetObject);
		//add pre, inv, post if empty in child
		if (targetObject.getPreCondition().isEmpty()) {
			targetObject.setPreCondition(sourceObject.getPreCondition());
		}
		if (targetObject.getInvariant().isEmpty()) {
			targetObject.setInvariant(sourceObject.getInvariant());
		}
		if (targetObject.getPostCondition().isEmpty()) {
			targetObject.setPostCondition(sourceObject.getPostCondition());
		}

		AddConnectionContext addContext = new AddConnectionContext(sourceAnchor, targetAnchor);
		Connection connection = (Connection) getFeatureProvider().addIfPossible(addContext);

		return connection;
	}
	
	/**
	 * Helper method to create an arrow at the end of the connection
	 * @param gaContainer	the connection decorator
	 * @return	A polyline that is formed like an arrow
	 */
	private Polyline createArrow(GraphicsAlgorithmContainer gaContainer) {
		Polyline polyline = Graphiti.getGaCreateService().createPlainPolyline(gaContainer,
				new int[] { -15, 10, 0, 0, -15, -10 });
		polyline.setForeground(manageColor(IColorConstant.BLACK));
		return polyline;
	}
}
