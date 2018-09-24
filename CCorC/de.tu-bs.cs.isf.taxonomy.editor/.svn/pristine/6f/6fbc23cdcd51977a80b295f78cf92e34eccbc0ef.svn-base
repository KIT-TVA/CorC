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

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.Method;

/**
 * Class to show the property for method objects
 * @author Tobias
 *
 */
public class MethodSection extends GFPropertySection implements ITabbedPropertyConstants {
	
	/**
	 * the name of the method
	 */
	private Text name;
	/**
	 * the code of the method
	 */
	private Text code;
	/**
	 * the pre-condition of the method
	 */
	private Text pre;
	/**
	 * the post-condition of the method
	 */
	private Text post;
	
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
	    
	
	    pre = factory.createText(composite, "", SWT.MULTI);
	    pre.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(name, VSPACE);
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
	    
	    
	    code = factory.createText(composite, "", SWT.MULTI);
	    code.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(post, VSPACE);
	    code.setLayoutData(data);
	
	    CLabel valueLabelCode = factory.createCLabel(composite, "Code:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(code, -HSPACE);
	    data.top = new FormAttachment(code, 0, SWT.CENTER);
	    valueLabelCode.setLayoutData(data);
	}
	
	@Override
	public void refresh() {
	    PictogramElement pe = getSelectedPictogramElement();
	    if (pe != null) {
	        Object bo = Graphiti.getLinkService()
	             .getBusinessObjectForLinkedPictogramElement(pe);
	        if (bo == null)
	            return;
	        String name = ((Method) bo).getName();
	        this.name.setText(name == null ? "" : name);
	        String code = ((Method) bo).getPseudoCode();
	        this.code.setText(code == null ? "" : code);
	        String pre = ((Method) bo).getPreCondition();
	        this.pre.setText(pre == null ? "" : pre);
	        String post = ((Method) bo).getPostCondition();
	        this.post.setText(post == null ? "" : post);
	    }
	}
}
