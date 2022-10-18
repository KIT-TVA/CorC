package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.impl.TextImpl;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.mm.pictograms.impl.ContainerShapeImpl;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.Method;
import de.tu_bs.cs.isf.cbc.cbcclass.model.cbcclass.impl.MethodImpl;
import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CbCFormula;
import de.tu_bs.cs.isf.cbc.cbcmodel.Condition;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.CbCFormulaImpl;
import de.tu_bs.cs.isf.cbc.tool.diagram.CbCDiagramTypeProvider;

import de.tu_bs.cs.isf.cbc.util.Console;

import de.tu_bs.cs.isf.cbc.tool.diagram.CbCFeatureProvider;
import de.tu_bs.cs.isf.cbc.util.FileUtil;

public class BasicsSection extends GFPropertySection implements ITabbedPropertyConstants {
	

	Display display = Display.getCurrent();// for UI updating of back-process
	boolean variationalProject = false;
	
	// Defining the UI properties
	private Label classLabel;
	private Label classLabelText;

	private Label invariantLabel;
	private List invariantList;
	
	private Label methodSignatureLabel;
	private StyledText methodSignatureLabelText;
	private boolean methodSignatureLabelChanged = false;
	
	private Label featureLabel;
	private Label featureLabelText;
	
	private Label compositionTechniqueLabel;
	private Label compositionTechniqueLabelText;
	
	private Button saveButton;
	private Object bo;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Device device = Display.getCurrent();
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();

		Composite composite = factory.createFlatFormComposite(parent);
		
		// Defining GridLayout for properties-view
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.verticalSpacing = 20;
		composite.setLayout(gridLayout);
		Color white = new Color (device, 255, 255, 255);
		
		// featureLabel
		featureLabel = new Label(composite, SWT.PUSH);
		featureLabel.setBackground(white);
		featureLabel.setText("Feature: ");
		
		// featureLabelText
		featureLabelText = new Label(composite, SWT.PUSH);
		featureLabelText.setBackground(white);
		
		// compositionTechniqueLabel
		compositionTechniqueLabel = new Label(composite, SWT.PUSH);
		compositionTechniqueLabel.setBackground(white);
		compositionTechniqueLabel.setText("Composition technique: ");
						
		// compositionTechniqueLabelText
		compositionTechniqueLabelText = new Label(composite, SWT.PUSH);
		compositionTechniqueLabelText.setBackground(white);	
		
		// classLabel
		classLabel = new Label(composite, SWT.PUSH);
		classLabel.setBackground(white);
		classLabel.setText("Class: ");
		
		// classLabelText
		classLabelText = new Label(composite, SWT.PUSH);
		classLabelText.setBackground(white);
		
		// methodSignatureLabel
		methodSignatureLabel = new Label(composite, SWT.PUSH);
		methodSignatureLabel.setText("MethodSignature: ");
		methodSignatureLabel.setBackground(white);
		
		// methodSignatureLabelText
		methodSignatureLabelText = new StyledText(composite, SWT.WRAP | SWT.PUSH | SWT.BORDER);
		GridData methodSignatureLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		methodSignatureLabelText.setEditable(true);
		methodSignatureLabelText.setLayoutData(methodSignatureLabelTextGridData);
		methodSignatureLabelText.setBackground(white);
		methodSignatureLabelText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				methodSignatureLabelChanged = true;
				saveButton.setEnabled(true);
			}
		});
		
		// invariantLabel
		invariantLabel = new Label(composite, SWT.PUSH);
		invariantLabel.setText("Invariants: ");
		invariantLabel.setBackground(white);

		// invariantLabelText
		invariantList = new List(composite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		invariantList.setItems();
	    int listHeight = invariantList.getItemHeight() * 5;

	    Rectangle trim = invariantList.computeTrim(0, 0, 0, listHeight);
	    GridData invariantLabelTextGridData = new GridData(SWT.FILL, SWT.FILL, true, false);
	    invariantLabelTextGridData.heightHint = trim.height;
	    invariantList.setLayoutData(invariantLabelTextGridData);
	    invariantList.setBackground(white);
	    invariantList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		// generateButton
		saveButton = new Button(composite, SWT.PUSH);
		saveButton.setText("Save");
		saveButton.setEnabled(false);
		saveButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(bo);
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {						
						if(methodSignatureLabelChanged) {
							String text = methodSignatureLabelText.getText();
							changeMethodSignature(text);
							methodSignatureLabelChanged = false;
						}
						saveButton.setEnabled(false);
						refresh();
					}
				});
			}
		});
		
	}

	@Override
	public void refresh() {
		CbCDiagramTypeProvider diagramProvider = new CbCDiagramTypeProvider();
		PictogramElement pe = getSelectedPictogramElement();
		bo = diagramProvider.getFeatureProvider().getBusinessObjectForPictogramElement(pe);
		if(bo instanceof CbCFormulaImpl) {
			updateData(bo);			
		}
	}
	
	public void changeMethodSignature(String newMethodSignature) {
		Method methodobj = null;
		if(bo instanceof CbCFormula) {
			methodobj = ((CbCFormula) bo).getMethodObj();
		}else if(bo instanceof AbstractStatement) {
			CbCFormula formula = getFormula((AbstractStatement)bo);
			methodobj = formula.getMethodObj();
		}else {
			Console.println("Click on formula!");
		}
		
		if(methodobj != null) {
			String oldName = methodobj.getName();
			String oldSignature = methodobj.getSignature();
			methodobj.setSignature(newMethodSignature.trim());
			if (!oldName.equals(methodobj.getName())) {
				methodobj.setSignature(oldSignature);
				JOptionPane.showMessageDialog(null, "Can not change name of method in the this state of CorC.");
				return;
			}
		}
		getDiagramTypeProvider().getDiagramBehavior().refresh();
		
		//automatic update of vp in cbcmodel
		Resource diagram_Resource = ClassUtil.getCbcDiagramResource(FileUtil.getProjectLocation(getDiagram().eResource().getURI()), methodobj.getName());
		Diagram diagram = (Diagram) diagram_Resource.getContents().get(0);
		ArrayList<PictogramElement> pes = new ArrayList<PictogramElement>();
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram, "de.tu-bs.cs.isf.cbc.tool.CbCDiagramTypeProvider");
		CbCFeatureProvider featureProvider = (CbCFeatureProvider) dtp.getFeatureProvider();
		
		for (int j = 0; j < diagram_Resource.getContents().get(0).eContents().size(); j++) {
			if (diagram_Resource.getContents().get(0).eContents().get(j).getClass().equals(ContainerShapeImpl.class)) {
				pes.add((PictogramElement) diagram_Resource.getContents().get(0).eContents().get(j));
			}
		}
		for (PictogramElement pe : pes) {
			ContainerShape container = (ContainerShape) pe;
			Shape shape = (Shape) container.getChildren().get(0);
			if (shape.getGraphicsAlgorithm() instanceof TextImpl) {
				Text text = (Text) container.getChildren().get(0).getGraphicsAlgorithm();
				if (text.getValue().equals("Variables")) {
					UpdateContext updateContext = new UpdateContext(pe);
					featureProvider.updateIfPossible(updateContext);
				}	
			}			
		}		
	}

	public void updateData(Object bo) {
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				boolean isVariational = false;
				// update methodSignature, invariants, feature
				if(((CbCFormulaImpl) bo).getMethodObj() instanceof MethodImpl) {
					Method methodObj = (Method) ((CbCFormulaImpl) bo).getMethodObj();
					methodSignatureLabelText.setText(methodObj.getSignature());
					classLabelText.setText(methodObj.getParentClass().getName() + (methodObj.getParentClass().getInheritsFrom() != null ? (" extends " + methodObj.getParentClass().getInheritsFrom().getName()) : ""));
					EList<Condition> classInvariants = methodObj.getParentClass().getClassInvariants();
					EList<Condition> inheritedClassInvariants = null;
					if (methodObj.getParentClass().getInheritsFrom() != null) {
						inheritedClassInvariants = methodObj.getParentClass().getInheritsFrom().getClassInvariants();
					}
					String[] invariants = new String[classInvariants.size() + (inheritedClassInvariants != null ? inheritedClassInvariants.size() : 0)];
					for (int i = 0; i < classInvariants.size(); i++){
						invariants[i] = classInvariants.get(i).getName();
					}

					if (inheritedClassInvariants != null) {
						for (int i = classInvariants.size(); i<invariants.length; i++){
							invariants[i] = inheritedClassInvariants.get(i-classInvariants.size()).getName() + " (inherited)";
						}
					}
					invariantList.setItems(invariants);	
					
					if (methodObj.getParentClass().getFeature() != null && methodObj.getParentClass().getFeature().length() > 0 && !methodObj.getParentClass().getFeature().equals("default")) {
						isVariational = true;
						featureLabelText.setText(methodObj.getParentClass().getFeature());
					}
				}
				if (isVariational) {
					// update compositionTechnique
					if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString().equals("CONJUNCTIVE_CONTRACTING")) {
						compositionTechniqueLabelText.setText("Conjunctive Contract Refinement");
					} else if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString()
							.equals("EXPLICIT_CONTRACTING")) {
						compositionTechniqueLabelText.setText("Explicit Contracting");
					} else if ((((CbCFormulaImpl) bo).getCompositionTechnique()).toString().equals("CONTRACT_OVERRIDING")) {
						compositionTechniqueLabelText.setText("Contract Overriding");
					}
				} else {
					featureLabel.dispose();
					featureLabelText.dispose();
					compositionTechniqueLabel.dispose();
					compositionTechniqueLabelText.dispose();
				}
			}
		});
	}
	
	public CbCFormula getFormula(AbstractStatement statement) {
		if (statement.getParent() != null) {
			return getFormula(statement.getParent());
		}
		EObject parent = statement.eContainer();
		if (parent != null && parent instanceof AbstractStatement) {
			return getFormula((AbstractStatement) parent);
		} else if (parent != null && parent instanceof CbCFormula) {
			return (CbCFormula) parent;
		}
		return null;
	}
}