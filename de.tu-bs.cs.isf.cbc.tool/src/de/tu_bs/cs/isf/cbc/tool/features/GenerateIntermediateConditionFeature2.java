package de.tu_bs.cs.isf.cbc.tool.features;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.CompositionStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.GlobalConditions;
import de.tu_bs.cs.isf.cbc.cbcmodel.JavaVariables;
import de.tu_bs.cs.isf.cbc.cbcmodel.Renaming;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.tool.helper.DiagramPartsExtractor;
import de.tu_bs.cs.isf.cbc.tool.helper.UpdateConditionsOfChildren;
import de.tu_bs.cs.isf.cbc.util.CompareMethodBodies;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.FileUtil;
import de.tu_bs.cs.isf.cbc.util.ProveWithKey;

/**
 * Class that generates the weakest precondition with key
 * @author Tobias
 *
 */
public class GenerateIntermediateConditionFeature2 extends MyAbstractAsynchronousCustomFeature {
    
    /**
     * Constructor of the class
     * @param fp	The FeatureProvider
     */
    public GenerateIntermediateConditionFeature2(IFeatureProvider fp) {
        super(fp);
    }
 
    @Override
    public String getName() {
        return "Generate intermediate condition with wp calculus";
    }
 
    @Override
    public String getDescription() {
        return "Generates a intermediate condition from an assignment and a post condition";
    }
 
    @Override
    public boolean canExecute(ICustomContext context) {
        boolean ret = false;
        PictogramElement[] pes = context.getPictogramElements();
        if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            if (bo != null && bo.getClass().equals(AbstractStatementImpl.class)) {
            		ret = true;
            }
        }
        return ret;
    }
 
    @Override
    public void execute(ICustomContext context, IProgressMonitor monitor) {
    	monitor.beginTask("Verify statement", IProgressMonitor.UNKNOWN);
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				AbstractStatement statement = (AbstractStatement) bo;
				CompositionStatement compoStatement = null;
				if (statement.getParent() != null && statement.getParent().eContainer() != null
						&& statement.getParent().eContainer() instanceof CompositionStatement) {
					compoStatement = (CompositionStatement) statement.getParent().eContainer();
					if (!compoStatement.getSecondStatement().equals(statement.getParent())) {
						compoStatement = null;
	            	}
				}
				DiagramPartsExtractor extractor = new DiagramPartsExtractor(getDiagram());
				JavaVariables vars = extractor.getVars();
				GlobalConditions conds = extractor.getConds();
				Renaming renaming = extractor.getRenaming();
				String weakestPre = "";

				if (CompareMethodBodies.readAndTestMethodBodyWithJaMoPP2(statement.getName())) {
					String uriString = getDiagram().eResource().getURI().toPlatformString(true);
					ProveWithKey prove = new ProveWithKey(statement, vars, conds, renaming, monitor, uriString, null, new FileUtil(uriString), "");
					weakestPre = prove.proveUseWeakestPreWithKey();
				} else {
					Console.println("Statement is not in correct format.");
				}
				if (compoStatement != null) {
					compoStatement.getIntermediateCondition().setName(weakestPre);
					UpdateConditionsOfChildren.updateConditionsofChildren(compoStatement.getIntermediateCondition());
					updatePictogramElement(pes[0]);
					updatePictogramElement(((Shape)pes[0]).getContainer());
				}
			}
		}
		monitor.done();
    }
}