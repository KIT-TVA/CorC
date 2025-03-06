package styles;

import org.eclipse.swt.graphics.Color;

import de.kit.tva.lost.interfaces.LostStyle;

public class DefaultStyle implements LostStyle {
	@Override
	public Color getInitializerColor() {
		return new Color(124, 181, 24);
	}

	@Override
	public Color getRefinementColor() {
		return new Color(92, 128, 1);
	}

	@Override
	public Color getIndicatorColor() {
		return new Color(233, 138, 21);
	}

	@Override
	public Color getTagColor() {
		return new Color(244, 162, 97);
	}

	@Override
	public Color getCodeColor() {
		return new Color(0, 0, 0);
	}

	@Override
	public Color getBackgroundColor() {
		return new Color(255, 255, 255);
	}

}
