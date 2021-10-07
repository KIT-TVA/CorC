package helper;

import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;
import org.eclipse.graphiti.pattern.id.IdUpdateContext;
import org.eclipse.graphiti.services.Graphiti;

public class ShapeWithText extends ShapeImpl {
	
	
	private Shape shape;
	private Text text;
	
	private ShapeWithText(Shape Shape) {
		this.shape = shape;
		
	}
	
	
	
	public static ShapeWithText getInstance(Shape shape) {
		return new ShapeWithText(shape);
	
	}
	
	public void setText(Text text) {
		this.text = text;
		
	}
	
	public Text getText() {
		return text;
	}
	
	

}
