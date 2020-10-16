package de.tu_bs.cs.isf.taxonomy.graphiti.features;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractAsynchronousCustomFeature;


/**
 * Extended class to override the initializeJob method
 * @author Tobias
 *
 */
public abstract class MyAbstractAsynchronousCustomFeature extends AbstractAsynchronousCustomFeature {

	/**
	 * Constructor
	 * @param fp 	the FeatureProvider
	 */
	public MyAbstractAsynchronousCustomFeature(IFeatureProvider fp) {
		super(fp);
	}

	protected Job initializeJob(final ICustomContext context) {
		return new Job(getName()) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {

				final TransactionalEditingDomain editingDomain = getDiagramBehavior().getEditingDomain();

				final RecordingCommand command = new RecordingCommand(editingDomain, getName()) {

					private IStatus result = null;

					@Override
					protected void doExecute() {
						try {
							MyAbstractAsynchronousCustomFeature.this.execute(context, monitor);
							result = Status.OK_STATUS;
						} catch (OperationCanceledException e) {
							result = Status.CANCEL_STATUS;
						}
					}

					@Override
					public Collection<?> getResult() {
						return result == null ? Collections.EMPTY_LIST : Collections.singletonList(result);
					}
				};

				// Execute (synchronously) the defined command in a proper EMF
				// transaction
				editingDomain.getCommandStack().execute(command);

				// Update the dirty state of the diagram
				//getDiagramBehavior().getDiagramContainer().updateDirtyState();

				// Callback
				afterJobExecution();

				return (IStatus) command.getResult().iterator().next();
			}
		};
	}

}
