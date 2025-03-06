package de.tu_bs.cs.isf.cbc.tool.diagram;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;

public class CbCDiagramDecorator extends LabelProvider implements ILabelDecorator {

	public CbCDiagramDecorator() {
		super();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
	/**
	 * @author Hayreddin Ciner
	 * 
	 *         Loads the cbcmodel file of a diagram and returns the isProven
	 *         property of the formula statement in the model.
	 * 
	 * @param filepath
	 * @param rSet
	 * @return isProven property of the formula of the diagram in filepath.
	 */
	private boolean getProvenProperty(String filepath, ResourceSet rSet) {
		URI resourceURI = URI.createFileURI(filepath.replace(".diagram", ".cbcmodel"));
		resourceURI = rSet.getURIConverter().normalize(resourceURI);
		Resource cbcResource = rSet.getResource(resourceURI, true);
		boolean isProven = false;
		for (EObject content : cbcResource.getContents()) {
			if (content instanceof CbCFormula) {
				isProven = ((CbCFormula) content).isProven();
			}
		}
		return isProven;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		Image img = null;
		ResourceSet rSet = new ResourceSetImpl();
		Bundle bundle = Platform.getBundle("de.tu-bs.cs.isf.cbc.tool");
		IPath imagePath = null;
		/*
		 * TODO Masterarbeit Ciner
		 * 
		 * If the folder contains diagram files, replace the folder icon tha represents
		 * the verification status of all diagrams.
		 */
		if (element instanceof IFolder) {
			IFolder decFolder = (IFolder) element;
			int numberOfDiagrams = 0;
			int numberOfProvenDiagrams = 0;
			for (java.io.File f : decFolder.getRawLocation().toFile().listFiles()) {
				if (!f.isDirectory() && f.getName().endsWith(".diagram")) {
					if (new java.io.File(f.getAbsolutePath().toString().replace(".diagram", ".cbcmodel")).exists()) {
						numberOfDiagrams++;
						boolean isProven = getProvenProperty(
								f.getAbsolutePath().toString().replace(".diagram", ".cbcmodel"), rSet);
						if (isProven) {
							numberOfProvenDiagrams++;
						}
					}

				}
			}
			// Check for at least one diagram file.
			if (numberOfDiagrams > 0 && numberOfDiagrams == numberOfProvenDiagrams) {
				imagePath = new Path("icons/folderGreen.gif");
			} else if (numberOfDiagrams > 0 && numberOfDiagrams != numberOfProvenDiagrams) {
				imagePath = new Path("icons/folderRed.gif");
			} else {
				return null;
			}
		} else if (element instanceof IFile) {
			/*
			 * Change the icon of the diagram file according to their verification status.
			 */
			IFile diagramFile = (IFile) element;
			if (diagramFile.getName().endsWith(".diagram")) {
				if (new java.io.File(diagramFile.getRawLocation().toString().replace(".diagram", ".cbcmodel"))
						.exists()) {
					boolean isProven = getProvenProperty(
							diagramFile.getRawLocation().toString().replace(".diagram", ".cbcmodel"), rSet);
					if (isProven) {
						// imagePath = new Path("icons/diagramGreen.gif");
					} else {
						imagePath = new Path("icons/diagramRed.gif");
					}
				}
			}
		}
		if (imagePath != null) {
			URL imageUrl = Platform.find(bundle, imagePath);
			ImageDescriptor desc = ImageDescriptor.createFromURL(imageUrl);
			img = desc.createImage();
		}
		return img;
	}

	@Override
	public String decorateText(String text, Object element) {
		String decorationText = null;
		ResourceSet rSet = new ResourceSetImpl();
		/*
		 * TODO Masterarbeit Hayreddin For folders: Add text decoration in the format
		 * '[numberOfProvenDiagrams/numberOfDiagrams]' For diagram files: Add the
		 * verification status "VERIFIED" or "PENDING" depending on the verification
		 * status.
		 */
		if (element instanceof IFolder) {
			IFolder decFolder = (IFolder) element;
			int numberOfDiagrams = 0;
			int numberOfProvenDiagrams = 0;
			for (java.io.File f : decFolder.getRawLocation().toFile().listFiles()) {
				if (!f.isDirectory() && f.getName().endsWith(".diagram")) {
					if (new java.io.File(f.getAbsolutePath().toString().replace(".diagram", ".cbcmodel")).exists()) {
						numberOfDiagrams++;
						boolean isProven = getProvenProperty(
								f.getAbsolutePath().toString().replace(".diagram", ".cbcmodel"), rSet);
						if (isProven) {
							numberOfProvenDiagrams++;
						}
					}
				}
			}
			// Check for at least one diagram file.
			if (numberOfDiagrams > 0) {
				decorationText = "[" + numberOfProvenDiagrams + "/" + numberOfDiagrams + " ] " + text;
			}
		} else if (element instanceof IFile) {
			IFile diagramFile = (IFile) element;
			if (diagramFile.getName().endsWith(".diagram")) {
				if (new java.io.File(diagramFile.getRawLocation().toString().replace(".diagram", ".cbcmodel"))
						.exists()) {
					boolean isProven = getProvenProperty(
							diagramFile.getRawLocation().toString().replace(".diagram", ".cbcmodel"), rSet);
					if (isProven) {
						decorationText = "[VERIFIED] " + text;
					} else {
						decorationText = "[PENDING] " + text;
					}
				}
			}
		}
		return decorationText;
	}

}
