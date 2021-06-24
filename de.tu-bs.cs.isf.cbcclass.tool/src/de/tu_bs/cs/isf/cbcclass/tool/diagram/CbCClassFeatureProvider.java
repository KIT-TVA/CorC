package de.tu_bs.cs.isf.cbcclass.tool.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import de.tu_bs.cs.isf.cbcclass.tool.features.RenameConditionFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameStatementFeature;
import de.tu_bs.cs.isf.cbcclass.tool.features.RenameVariableFeature;
import de.tu_bs.cs.isf.cbcclass.tool.patterns.InvariantClassPattern;
import de.tu_bs.cs.isf.cbcclass.tool.patterns.FieldClassPattern;
import de.tu_bs.cs.isf.cbcclass.tool.patterns.MethodClassPattern;
import de.tu_bs.cs.isf.cbcclass.tool.patterns.ModelClassPattern;

public class CbCClassFeatureProvider extends DefaultFeatureProviderWithPatterns {

	public CbCClassFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new ModelClassPattern());
		addPattern(new FieldClassPattern());
		addPattern(new InvariantClassPattern());
		addPattern(new MethodClassPattern());

	}
	
	
	@Override
	public ICreateFeature[] getCreateFeatures() {
		ICreateFeature[] oldArray = super.getCreateFeatures();
		ICreateFeature[] array = new ICreateFeature[oldArray.length];
		for (int i = 0; i < oldArray.length; i++) {
			array[i] = oldArray[i];
		}
		//array[array.length - 1] = new CreateExtraSelectionFeature(this);
		return array;
	}
	
	
	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] { 
					
				new RenameConditionFeature(this),
				new RenameStatementFeature(this),
				new RenameVariableFeature(this)};
		}
		
}
	
	
