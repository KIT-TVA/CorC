package de.tu_bs.cs.isf.cbc.tool.diagram;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.pattern.DefaultFeatureProviderWithPatterns;

import de.tu_bs.cs.isf.cbc.tool.features.ChangeNameOfAssociatedClassFeature;
import de.tu_bs.cs.isf.cbc.tool.features.ChangeNameOfAssociatedMethodFeature;
import de.tu_bs.cs.isf.cbc.tool.features.ChangeNameOfFormulaFeature;
import de.tu_bs.cs.isf.cbc.tool.features.CreateExtraSelectionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.DeleteConnectionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.EditCommentFeature;
import de.tu_bs.cs.isf.cbc.tool.features.EditCompositionTechniqueOfFormula;
import de.tu_bs.cs.isf.cbc.tool.features.ExtractMethodStubsFeature;
import de.tu_bs.cs.isf.cbc.tool.features.GenerateIntermediateConditionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.GenerateIntermediateConditionFeature2;
import de.tu_bs.cs.isf.cbc.tool.features.GenerateTextualRepresentation;
import de.tu_bs.cs.isf.cbc.tool.features.LayoutFeature;
import de.tu_bs.cs.isf.cbc.tool.features.PrintFormulaFeature;
import de.tu_bs.cs.isf.cbc.tool.features.ReconnectionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameConditionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameMethodClassFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameMethodSignatureFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameRenamingFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameStatementFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameVariableFeature;
import de.tu_bs.cs.isf.cbc.tool.features.RenameVariantFeature;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyAllStatements;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPostRepetitionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPreRepetitionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyPreSelectionStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatement;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStatementInlining;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyStrengthWeakCorrect;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyVariantWithInnerLoops;
import de.tu_bs.cs.isf.cbc.tool.features.VerifyVariantWithoutInnerLoops;
import de.tu_bs.cs.isf.cbc.tool.features.intermediate.AboveCompositionFirstFeature;
import de.tu_bs.cs.isf.cbc.tool.features.intermediate.AboveCompositionSecondFeature;
import de.tu_bs.cs.isf.cbc.tool.features.intermediate.AboveRepetitionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.intermediate.AboveSelectionFeature;
import de.tu_bs.cs.isf.cbc.tool.features.intermediate.BelowImplementationFeature;
import de.tu_bs.cs.isf.cbc.tool.patterns.ClassPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.CompositionPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.ConditionPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.ConnectionPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.FormulaPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.GlobalConditionsPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.MethodSignaturePattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.RenamePattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.RenamingPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.ReturnPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.SelectionPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.SkipStatementPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.SmallRepetitionPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.StatementPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.StrengthWeakStatementPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.VariablePattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.VariablesPattern;
import de.tu_bs.cs.isf.cbc.tool.patterns.VariantPattern;

public class CbCFeatureProvider extends DefaultFeatureProviderWithPatterns {

	public CbCFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
		addPattern(new FormulaPattern());
		addPattern(new CompositionPattern());
		addPattern(new SelectionPattern());
		addPattern(new SmallRepetitionPattern());
		addPattern(new SkipStatementPattern());
		addPattern(new ReturnPattern());
		addPattern(new StrengthWeakStatementPattern());
		addPattern(new StatementPattern());
		addPattern(new GlobalConditionsPattern());
		addPattern(new ConditionPattern());
		addPattern(new VariablesPattern());
		addPattern(new VariablePattern());
		addPattern(new RenamingPattern());
		addPattern(new RenamePattern());
		addPattern(new ClassPattern());
		addPattern(new MethodSignaturePattern());
		addPattern(new VariantPattern()); //this one is remove by getCreateFeatures() below
		addConnectionPattern(new ConnectionPattern());
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		ICreateFeature[] oldArray = super.getCreateFeatures();
		ICreateFeature[] array = new ICreateFeature[oldArray.length];
		for (int i = 0; i < oldArray.length; i++) {
			array[i] = oldArray[i];
		}
		array[array.length - 1] = new CreateExtraSelectionFeature(this);
		return array;
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		return new ReconnectionFeature(this);
	}

	// @Override
	// public IUpdateFeature getUpdateFeature(IUpdateContext context) {
	// PictogramElement pe = context.getPictogramElement();
	// Object bo = getBusinessObjectForPictogramElement(pe);
	// if (bo instanceof JavaVariable) {
	// return new UpdateVariableFeature(this);
	// }
	// return super.getUpdateFeature(context);
	// }

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		if (context.getPictogramElement() instanceof Connection) {
			return new DeleteConnectionFeature(this);
		}
		return super.getDeleteFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		return new ICustomFeature[] { 
				new PrintFormulaFeature(this),
				new ExtractMethodStubsFeature(this), //!
				new GenerateTextualRepresentation(this), 
				new VerifyStatement(this),
				new VerifyPreRepetitionStatement(this), 
				new VerifyPostRepetitionStatement(this),
				new VerifyPreSelectionStatement(this), 
				new VerifyStrengthWeakCorrect(this),
				// new VerifyVariant(this),
				new VerifyVariantWithInnerLoops(this), 
				new VerifyVariantWithoutInnerLoops(this), //!
				new EditCommentFeature(this),
				new EditCompositionTechniqueOfFormula(this), //!
				new ChangeNameOfFormulaFeature(this), 
				new RenameStatementFeature(this), 
				new RenameConditionFeature(this), 
				new RenameVariantFeature(this),
				new RenameVariableFeature(this), 
				new RenameRenamingFeature(this),
				new RenameMethodClassFeature(this),
				new RenameMethodSignatureFeature(this),
				new LayoutFeature(this), 
				new GenerateIntermediateConditionFeature(this), 
				new GenerateIntermediateConditionFeature2(this),
	    		new ChangeNameOfAssociatedClassFeature(this),
	    		new ChangeNameOfAssociatedMethodFeature(this),
	    		new AboveCompositionFirstFeature(this),
	    		new AboveCompositionSecondFeature(this),
	    		new AboveSelectionFeature(this),
	    		new AboveRepetitionFeature(this),
	    		new BelowImplementationFeature(this),
	    		new VerifyAllStatements(this),
				new VerifyStatementInlining(this)};
	}
}
