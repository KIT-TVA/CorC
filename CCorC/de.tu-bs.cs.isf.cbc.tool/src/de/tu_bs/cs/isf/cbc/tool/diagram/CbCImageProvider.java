package de.tu_bs.cs.isf.cbc.tool.diagram;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class CbCImageProvider extends AbstractImageProvider {

	// The prefix for all identifiers of this image provider
    protected static final String PREFIX =
              "de.tu_bs.cs.isf.cbc.tool.";
 
    // The image identifier for an EReference.
    public static final String IMG_PROVEN = PREFIX + "proven";
    public static final String IMG_UNPROVEN = PREFIX + "unproven";
    public static final String IMG_HIGH = PREFIX + "high";
    public static final String IMG_LOW = PREFIX + "low";
    
    @Override
    protected void addAvailableImages() {
        // register the path for each image identifier
        addImageFilePath(IMG_PROVEN, "icons/YESGRN.gif");
        addImageFilePath(IMG_UNPROVEN, "icons/NORED.gif");
        addImageFilePath(IMG_HIGH, "icons/high.gif");
        addImageFilePath(IMG_LOW, "icons/low.gif");
    }

}
