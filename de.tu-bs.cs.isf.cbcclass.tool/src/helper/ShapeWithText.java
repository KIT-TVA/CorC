package helper;

import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ShapeImpl;

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
