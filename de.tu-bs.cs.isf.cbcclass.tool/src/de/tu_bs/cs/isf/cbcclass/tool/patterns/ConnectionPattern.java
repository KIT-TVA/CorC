package de.tu_bs.cs.isf.cbcclass.tool.patterns;

import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractConnectionPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import de.tu_bs.cs.isf.cbc.cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcclass.impl.ModelClassImpl;

/**
 * Class that creates the graphical representation of the parent hierarchy between Algorithms
 * @author Tobias
 *
 */
public class ConnectionPattern extends AbstractConnectionPattern {
	
	@Override
	public String getCreateName() {
		return "Refinement";
	}
	
	@Override
	public String getCreateDescription() {
		return "Firstly, select the class, then the method.";
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

		Connection connection = peCreateService.createFreeFormConnection(getDiagram());
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
		
		if (domainObject != null && domainObject instanceof ModelClassImpl) {
			if (context.getSourcePictogramElement() instanceof ContainerShape) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		PictogramElement sourcePictogramElement = context.getSourcePictogramElement();
		PictogramElement targetPictogramElement = context.getTargetPictogramElement();

		if (sourcePictogramElement == null || targetPictogramElement == null || targetPictogramElement.equals(getDiagram()) || !(targetPictogramElement instanceof ContainerShape)) {
			return false;
		}
		
		Object sourceDomainObject = getBusinessObjectForPictogramElement(sourcePictogramElement);
		Object targetDomainObject = getBusinessObjectForPictogramElement(targetPictogramElement);

		if (!(sourceDomainObject instanceof ModelClassImpl)) {
			return false;
		}
		
		if (!(targetDomainObject instanceof MethodImpl)) {
			return false;
		}
		return true;
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