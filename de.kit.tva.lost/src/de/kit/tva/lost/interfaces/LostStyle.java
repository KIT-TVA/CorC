package de.kit.tva.lost.interfaces;

import org.eclipse.swt.graphics.Color;

/**
 * The colors that must be defined for highlighting LOST code.
 * 
 * Initializers: D, F, Vars, ... Indicators: pre, post, ... Tags: verification
 * status, test status, ...
 */
public interface LostStyle {
    Color getInitializerColor();

    Color getRefinementColor();

    Color getIndicatorColor();

    Color getTagColor();

    Color getCodeColor();

    Color getBackgroundColor();
}
