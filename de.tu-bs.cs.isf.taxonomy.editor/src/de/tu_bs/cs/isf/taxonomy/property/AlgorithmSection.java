package de.tu_bs.cs.isf.taxonomy.property;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Algorithm;

/**
 * Class to show the property for algorithm objects
 * @author Tobias
 *
 */
public class AlgorithmSection extends GFPropertySection implements ITabbedPropertyConstants {
	 
	/**
	 * name of the algorithm
	 */
	private Text name;
	/**
	 * abstract value of the algorithm
	 */
	private Text isAbstract;
	/**
	 * pre-condition of the algorithm
	 */
	private Text pre;
	/**
	 * pre-condition of the algorithm
	 */
	private Text post;
	/**
	 * invariant of the algorithm
	 */
	private Text invariant;
	/**
	 * imports of the algorithm
	 */
	private Text imports;
	
	@Override
	public void createControls(Composite parent,
	    TabbedPropertySheetPage tabbedPropertySheetPage) {
	    super.createControls(parent, tabbedPropertySheetPage);
	
	    TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
	    Composite composite = factory.createFlatFormComposite(parent);
	    FormData data;
	
	    name = factory.createText(composite, "", SWT.MULTI);
	    name.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(0, VSPACE);
	    name.setLayoutData(data);
	
	    CLabel valueLabelName = factory.createCLabel(composite, "Name:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(name, -HSPACE);
	    data.top = new FormAttachment(name, 0, SWT.CENTER);
	    valueLabelName.setLayoutData(data);
	    
	    
	    isAbstract = factory.createText(composite, "", SWT.MULTI);
	    isAbstract.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(name, VSPACE);
	    isAbstract.setLayoutData(data);
	
	    CLabel valueLabelAbstract = factory.createCLabel(composite, "Abstract:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(isAbstract, -HSPACE);
	    data.top = new FormAttachment(isAbstract, 0, SWT.CENTER);
	    valueLabelAbstract.setLayoutData(data);
	    
	    
	    pre = factory.createText(composite, "", SWT.MULTI);
	    pre.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(isAbstract, VSPACE);
	    pre.setLayoutData(data);
	
	    CLabel valueLabelPre = factory.createCLabel(composite, "Pre-condition:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(pre, -HSPACE);
	    data.top = new FormAttachment(pre, 0, SWT.CENTER);
	    valueLabelPre.setLayoutData(data);
	    
	    
	    post = factory.createText(composite, "", SWT.MULTI);
	    post.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(pre, VSPACE);
	    post.setLayoutData(data);
	
	    CLabel valueLabelPost = factory.createCLabel(composite, "Post-condition:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(post, -HSPACE);
	    data.top = new FormAttachment(post, 0, SWT.CENTER);
	    valueLabelPost.setLayoutData(data);
	    
	    
	    invariant = factory.createText(composite, "", SWT.MULTI);
	    invariant.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(post, VSPACE);
	    invariant.setLayoutData(data);
	
	    CLabel valueLabelInvariant = factory.createCLabel(composite, "Invariant:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(invariant, -HSPACE);
	    data.top = new FormAttachment(invariant, 0, SWT.CENTER);
	    valueLabelInvariant.setLayoutData(data);
	    
	    imports = factory.createText(composite, "", SWT.MULTI);
	    imports.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(invariant, VSPACE);
	    imports.setLayoutData(data);
	
	    CLabel valueLabelImports = factory.createCLabel(composite, "Imports:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(imports, -HSPACE);
	    data.top = new FormAttachment(imports, 0, SWT.CENTER);
	    valueLabelImports.setLayoutData(data);
	}
	
	@Override
	public void refresh() {
	    PictogramElement pe = getSelectedPictogramElement();
	    if (pe != null) {
	        Object bo = Graphiti.getLinkService()
	             .getBusinessObjectForLinkedPictogramElement(pe);
	        if (bo == null)
	            return;
	        String name = ((Algorithm) bo).getName();
	        this.name.setText(name == null ? "" : name);
	        if (((Algorithm) bo).isAbstract()) {
	        	 this.isAbstract.setText("true");
	        } else {
	        	this.isAbstract.setText("false");
	        }
	        String pre = ((Algorithm) bo).getPreCondition();
	        this.pre.setText(pre == null ? "" : pre);
	        String post = ((Algorithm) bo).getPostCondition();
	        this.post.setText(post == null ? "" : post);
	        String invariant = ((Algorithm) bo).getInvariant();
	        this.invariant.setText(invariant == null ? "" : invariant);
	        String imports = ((Algorithm) bo).getImports();
	        this.imports.setText(imports == null ? "" : imports);
	    }
	}
}
