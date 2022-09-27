package de.tu_bs.cs.isf.cbc.tool.propertiesview;


import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.ModelClass;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

public class InheritanceSection extends GFPropertySection implements ITabbedPropertyConstants {
	
	private Composite parent;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	private Display display;
	
	// Defining the UI properties
	private Label inheritanceLabel;
	private Label inheritanceLabelText;
	
	private Label preLabel;
	private StyledText preCode;
	private Label postLabel;
	private StyledText postCode;

	
	private Object bo;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		
		Device device = Display.getCurrent();
		display = Display.getCurrent();
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);
		
		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
		Color white = new Color (device, 255, 255, 255);
		
		inheritanceLabel = new Label(composite, SWT.PUSH);
		inheritanceLabel.setBackground(white);
		inheritanceLabel.setText("");
		
		inheritanceLabelText = new Label(composite, SWT.PUSH);
		inheritanceLabelText.setBackground(white);
		inheritanceLabelText.setText("");
		
		preLabel = new Label(composite, SWT.PUSH);
		preLabel.setBackground(white);
		preLabel.setText("Precondition (or): ");
		
		preCode = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData preOutputGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		preOutputGridData.widthHint = 800;
		preCode.setText("There is no other contract that needs to be considered!");
		preCode.setLayoutData(preOutputGridData);
		preCode.setBackground(white);
		preCode.setEditable(false);
		
		postLabel = new Label(composite, SWT.PUSH);
		postLabel.setBackground(white);
		postLabel.setText("Postcondition (and): ");
		
		postCode = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData postOutputGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		postOutputGridData.widthHint = 800;
		postCode.setText("There is no other contract that needs to be considered!");
		postCode.setLayoutData(postOutputGridData);
		postCode.setBackground(white);
		postCode.setEditable(false);
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider diagramProvider = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		bo = diagramProvider.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if(bo instanceof CbCFormulaImpl) {
			CbCFormula formula = ((CbCFormula) bo);
			ModelClass mc = (ModelClass) formula.getMethodObj().eContainer();
			Condition pre = null;
			Condition post = null;
			if (mc.getInheritsFrom() != null) {
				for (Method m : mc.getInheritsFrom().getMethods()) {
					if (m.getName().equals(formula.getMethodName())) {
						pre = m.getCbcStartTriple().getStatement().getPreCondition();
						post = m.getCbcStartTriple().getStatement().getPostCondition();
					}
				}
				if (pre != null && post != null) {
					inheritanceLabelText.setText("This method has a super-implementation in the class " + mc.getInheritsFrom().getName() + ".\nPlease note that the inherited conditions are attached to the conditions of this method. \nThe inherited precondition is attached with \"or\" ( | ), the postcondition is attached with \"and\" ( && ) due to the Liskov Substitution Principle.");
					preCode.setText(CodeReaderSection.formateCode(pre.getName()));
					CodeReaderSection.styleText(preCode, display);
					postCode.setText(CodeReaderSection.formateCode(post.getName()));
					CodeReaderSection.styleText(postCode, display);
				} else {
					inheritanceLabelText.setText("This method has no super-implementation in the class " + mc.getInheritsFrom().getName() + ".");
				}
				parent.pack();
				tabbedPropertySheetPage.resizeScrolledComposite();
			} else {
				inheritanceLabelText.setText("No inheritance for this class.");
			}
		}
	}
}