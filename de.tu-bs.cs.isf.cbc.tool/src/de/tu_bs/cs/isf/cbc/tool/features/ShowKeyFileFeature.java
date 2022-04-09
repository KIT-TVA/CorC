package de.tu_bs.cs.isf.cbc.tool.features;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ISetSelectionTarget;

import de.tu_bs.cs.isf.cbc.cbcmodel.AbstractStatement;
import de.tu_bs.cs.isf.cbc.cbcmodel.impl.AbstractStatementImpl;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsDatabase;
import de.tu_bs.cs.isf.cbc.util.Console;

public class ShowKeyFileFeature extends AbstractCustomFeature {

	public ShowKeyFileFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public String getName() {
		return "Show KeY file(s)";
	}

	@Override
	public String getDescription() {
		return "Finds related KeY files for this refinement step.";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo != null && (bo.getClass().equals(AbstractStatementImpl.class) || bo instanceof AbstractStatement)) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {		
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof AbstractStatement) {
				
				//get content from diagram
				AbstractStatement statement = (AbstractStatement) bo;
				String statementId = statement.getId();
				
				List<IFile> keyFiles = new LinkedList<IFile>();
				keyFiles = StatisticsDatabase.instance.getKeYFilesForId(statementId);
				
				if (keyFiles == null || keyFiles.isEmpty()) {
					Console.println("No related KeY files found.");
				}
				else {
					// TODO: mark key files within Project Explorer
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IViewPart view = page.findView(IPageLayout.ID_PROJECT_EXPLORER);
					((ISetSelectionTarget)view).selectReveal(new StructuredSelection(keyFiles));
				}
			}
		}
	}
}
