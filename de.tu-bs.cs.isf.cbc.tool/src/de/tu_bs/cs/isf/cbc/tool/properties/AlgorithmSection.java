package de.tu_bs.cs.isf.cbc.tool.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
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

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;

/**
 * Class to show the property for statements
 * @author Tobias
 *
 */
public class AlgorithmSection extends GFPropertySection implements ITabbedPropertyConstants {
	 
	/**
	 * name of the statement
	 */
	private Text name;
	private Text confsPre;
	private Text confsPost;
	private Text context;

	
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
	    
	    
	    confsPre = factory.createText(composite, "", SWT.MULTI);
	    confsPre.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(name, VSPACE);
	    confsPre.setLayoutData(data);
	
	    CLabel valueLabelPre = factory.createCLabel(composite, "PreVars:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(confsPre, -HSPACE);
	    data.top = new FormAttachment(confsPre, 0, SWT.CENTER);
	    valueLabelPre.setLayoutData(data);
	    
	    
	    confsPost = factory.createText(composite, "", SWT.MULTI);
	    confsPost.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(confsPre, VSPACE);
	    confsPost.setLayoutData(data);
	
	    CLabel valueLabelPost = factory.createCLabel(composite, "PostVars:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(confsPost, -HSPACE);
	    data.top = new FormAttachment(confsPost, 0, SWT.CENTER);
	    valueLabelPost.setLayoutData(data);
	    
	    context = factory.createText(composite, "", SWT.MULTI);
	    context.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(confsPost, VSPACE);
	    context.setLayoutData(data);
	
	    CLabel valueLabelContext = factory.createCLabel(composite, "Context:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(context, -HSPACE);
	    data.top = new FormAttachment(context, 0, SWT.CENTER);
	    valueLabelContext.setLayoutData(data); 
	}
	
	@Override
	public void refresh() {
	    PictogramElement pe = getSelectedPictogramElement();
	    if (pe != null) {
	        Object bo = Graphiti.getLinkService()
	             .getBusinessObjectForLinkedPictogramElement(pe);
	        if (bo == null)
	            return;
	        if (bo instanceof AbstractStatement) {
	        	 AbstractStatement statement = (AbstractStatement) bo;
	        	 String name = statement.getName().replaceAll(System.lineSeparator(), "");
	        	 String preMap = MapToString(statement.getPreCondition());
	        	 String postMap = MapToString(statement.getPostCondition());
	        	 String context = statement.getContext();
	        	 this.name.setText(name == null ? "" : name);
	        	 this.confsPre.setText(preMap == null ? "" : preMap);
	        	 this.confsPost.setText(postMap == null ? "" : postMap);
	        	 this.context.setText(context == null ? "" : context);
	        }
	    }
	}
	
	private String MapToString(Condition condition) {
		EMap<String, EList<String>> map = condition.getConfToVarsMap();
		String returnString = "";
		for (String key : map.keySet()) {
			returnString += "{" + key + " : " + map.get(key) + "} ";
		}
		return returnString;
	}
}
