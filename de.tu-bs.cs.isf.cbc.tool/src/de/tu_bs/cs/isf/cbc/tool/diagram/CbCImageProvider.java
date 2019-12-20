package de.tu_bs.cs.isf.cbc.tool.diagram;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class CbCImageProvider extends AbstractImageProvider {

	// The prefix for all identifiers of this image provider
    protected static final String PREFIX =
              "de.tu_bs.cs.isf.cbc.tool.";
 
    // The image identifier for an EReference.
    public static final String IMG_PROVEN= PREFIX + "proven";
    public static final String IMG_UNPROVEN= PREFIX + "unproven";
    
    @Override
    protected void addAvailableImages() {
        // register the path for each image identifier
        addImageFilePath(IMG_PROVEN, "icons/YESGRN.gif");
        addImageFilePath(IMG_UNPROVEN, "icons/NORED.gif");
    }

}
