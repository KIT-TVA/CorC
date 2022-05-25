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
import de.tu_bs.cs.isf.cbc.cbcmodel.AtType;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.Security;

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
	private Text AtTypesPre;
	private Text AtTypesPost;
	private Text usedCapsulesPre;
	private Text usedCapsulesPost;
	private Text context;
	private Text typeableResult;
	private Text typeableText;

	
	@Override
	public void createControls(Composite parent,
	    TabbedPropertySheetPage tabbedPropertySheetPage) {
	    super.createControls(parent, tabbedPropertySheetPage);
	    
	    // TODO: support for other diagram types like composition -> intermediate condition	    
	    System.out.println("Constructing properties view");
	
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
	    
	    usedCapsulesPre = factory.createText(composite, "", SWT.MULTI);
	    usedCapsulesPre.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(confsPost, VSPACE);
	    usedCapsulesPre.setLayoutData(data);
	
	    CLabel valueCapsulePre = factory.createCLabel(composite, "Capsules Pre:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(usedCapsulesPre, -HSPACE);
	    data.top = new FormAttachment(usedCapsulesPre, 0, SWT.CENTER);
	    valueCapsulePre.setLayoutData(data);	    
	    
	    usedCapsulesPost = factory.createText(composite, "", SWT.MULTI);
	    usedCapsulesPost.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(usedCapsulesPre, VSPACE);
	    usedCapsulesPost.setLayoutData(data);
	
	    CLabel valueCapsulePost = factory.createCLabel(composite, "Capsules Post:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(usedCapsulesPost, -HSPACE);
	    data.top = new FormAttachment(usedCapsulesPost, 0, SWT.CENTER);
	    valueCapsulePost.setLayoutData(data);
	    
	    context = factory.createText(composite, "", SWT.MULTI);
	    context.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(usedCapsulesPost, VSPACE);
	    context.setLayoutData(data);
	
	    CLabel valueLabelContext = factory.createCLabel(composite, "Context:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(context, -HSPACE);
	    data.top = new FormAttachment(context, 0, SWT.CENTER);
	    valueLabelContext.setLayoutData(data);
	    
	    AtTypesPre = factory.createText(composite, "", SWT.MULTI);
	    AtTypesPre.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(context, VSPACE);
	    AtTypesPre.setLayoutData(data);
	
	    CLabel valueLabelAtTypesPre = factory.createCLabel(composite, "PreTypes:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(AtTypesPre, -HSPACE);
	    data.top = new FormAttachment(AtTypesPre, 0, SWT.CENTER);
	    valueLabelAtTypesPre.setLayoutData(data);
	    
	    AtTypesPost = factory.createText(composite, "", SWT.MULTI);
	    AtTypesPost.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(AtTypesPre, VSPACE);
	    AtTypesPost.setLayoutData(data);
	
	    CLabel valueLabelAtTypesPost = factory.createCLabel(composite, "PostTypes:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(AtTypesPost, -HSPACE);
	    data.top = new FormAttachment(AtTypesPost, 0, SWT.CENTER);
	    valueLabelAtTypesPost.setLayoutData(data);
	    
	    typeableResult = factory.createText(composite, "", SWT.MULTI);
	    typeableResult.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(AtTypesPost, VSPACE);
	    typeableResult.setLayoutData(data);
	
	    CLabel valueLabelTypeable = factory.createCLabel(composite, "Typeable:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(typeableResult, -HSPACE);
	    data.top = new FormAttachment(typeableResult, 0, SWT.CENTER);
	    valueLabelTypeable.setLayoutData(data);
	    
	    typeableText = factory.createText(composite, "", SWT.MULTI);
	    typeableText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 15);
	    data.right = new FormAttachment(100, 0);
	    data.top = new FormAttachment(typeableResult, VSPACE);
	    typeableText.setLayoutData(data);
	
	    CLabel valueLabelTypeableText = factory.createCLabel(composite, "Typeable Info:");
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(typeableText, -HSPACE);
	    data.top = new FormAttachment(typeableText, 0, SWT.CENTER);
	    valueLabelTypeableText.setLayoutData(data);
	}
	
	@Override
	public void refresh() {
	    PictogramElement pe = getSelectedPictogramElement();
	    if (pe != null) {
	        Object bo = Graphiti.getLinkService()
	             .getBusinessObjectForLinkedPictogramElement(pe);
	        if (bo == null)
	            return;
	        
	        if (bo instanceof CbCFormula){
	        	final CbCFormula formula = (CbCFormula) bo;
	        	final AbstractStatement statement = formula.getStatement();	        	
	        	if (statement == null) {
	        		return;
	        	}	        	
	        	bo = statement;
	        }
	        
	        if (bo instanceof CompositionStatement) {
	        	// TODO: read pre, post, intermediate conditions
	        } else if (bo instanceof AbstractStatement) {
	        	 AbstractStatement statement = (AbstractStatement) bo;
	        	 String name = statement.getName().replaceAll(System.lineSeparator(), "");
	        	 String preMap = confsMapToString(statement.getPreCondition());
	        	 String postMap = confsMapToString(statement.getPostCondition());
	        	 String preAtMap = atMapToString(statement.getPreCondition());
	        	 String postAtMap = atMapToString(statement.getPostCondition());
	        	 String preCapsules = capsulesUsedToString(statement.getPreCondition());
	        	 String postCapsules = capsulesUsedToString(statement.getPostCondition());
	        	 String context = statement.getContext();
	        	 this.name.setText(name == null ? "" : name);
	        	 this.confsPre.setText(preMap == null ? "" : preMap);
	        	 this.confsPost.setText(postMap == null ? "" : postMap);
	        	 this.context.setText(context == null ? "" : context);
	        	 this.AtTypesPre.setText(preMap == null ? "" : preAtMap);
	        	 this.AtTypesPost.setText(postMap == null ? "" : postAtMap);
	        	 this.typeableResult.setText(statement.getTypeableResult() == null ? "" : statement.getTypeableResult());
	        	 this.typeableText.setText(statement.getTyleableText() == null ? "" : statement.getTyleableText());
	        	 this.usedCapsulesPre.setText(preCapsules == null ? "" : preCapsules);
	        	 this.usedCapsulesPost.setText(postCapsules == null ? "" : postCapsules);
	        }
	    }
	}
	
	private String confsMapToString(Condition condition) {
		EMap<String, Security> map = condition.getConfToVarsMap();
		String returnString = "";
		for (String key : map.keySet()) {
			final Security security = map.get(key);
			final String level = security != null ? security.getLevel() : "";
			returnString += "{" + key + " : " + level + "} ";
		}
		return returnString;
	}
	
	private String atMapToString(Condition condition) {
		EMap<String, EList<AtType>> map = condition.getAtTypesToVarsMap();
		String returnString = "";
		for (String key : map.keySet()) {
			returnString += "{" + key + " : ";
			for (AtType value : map.get(key)) {
				returnString += value.getName() +",";
			}
			returnString = returnString.substring(0, returnString.length()-1);
			returnString += "} ";
		}
		return returnString;
	}
	
	private String capsulesUsedToString(Condition condition) {
		EList<String> map = condition.getCapsulesUsed();
		if (map == null || map.isEmpty()) {
			return "";
		}
		String returnString = "{";
		for (String key : map) {
			returnString += key + ", ";
		}
		returnString = returnString.substring(0, returnString.length()-2);
		returnString += "} ";
		return returnString;
	}

}

