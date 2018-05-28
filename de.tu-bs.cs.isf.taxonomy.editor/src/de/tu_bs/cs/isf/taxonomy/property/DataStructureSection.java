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

import de.tu_bs.cs.isf.taxonomy.model.taxonomy.DataStructure;

/**
 * Class to show the property for method objects
 * @author Tobias
 *
 */
public class DataStructureSection extends GFPropertySection implements ITabbedPropertyConstants {
	
	/**
	 * the name of the dataStructure
	 */
	private Text name;
	/**
	 * the type of the dataStructure
	 */
	private Text type;
	/**
	 * the initial value of the dataStructure
	 */
	private Text value;
	
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
	    
	
	    type = factory.createText(composite, "", SWT.MULTI);
	    type.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(name, VSPACE);
	    type.setLayoutData(data);
	
	    CLabel valueLabelType = factory.createCLabel(composite, "DataType:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(type, -HSPACE);
	    data.top = new FormAttachment(type, 0, SWT.CENTER);
	    valueLabelType.setLayoutData(data);
	    
	    
	    value = factory.createText(composite, "", SWT.MULTI);
	    value.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(type, VSPACE);
	    value.setLayoutData(data);
	
	    CLabel valueLabelPost = factory.createCLabel(composite, "Initial Value:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(value, -HSPACE);
	    data.top = new FormAttachment(value, 0, SWT.CENTER);
	    valueLabelPost.setLayoutData(data);
	}
	
	@Override
	public void refresh() {
	    PictogramElement pe = getSelectedPictogramElement();
	    if (pe != null) {
	        Object bo = Graphiti.getLinkService()
	             .getBusinessObjectForLinkedPictogramElement(pe);
	        if (bo == null)
	            return;
	        String name = ((DataStructure) bo).getName();
	        this.name.setText(name == null ? "" : name);
	        String type = ((DataStructure) bo).getDataType().getName();
	        this.type.setText(type == null ? "" : type);
	        String value = ((DataStructure) bo).getInitialValue();
	        this.value.setText(value == null ? "" : value);
	    }
	}
}
