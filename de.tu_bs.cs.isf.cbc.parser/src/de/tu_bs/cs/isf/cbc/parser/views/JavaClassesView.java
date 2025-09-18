package de.tu_bs.cs.isf.cbc.parser.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.key_project.util.lookup.Inject;
import de.tu_bs.cs.isf.cbc.parser.JavaClasses;
import de.tu_bs.cs.isf.cbc.parser.data.JavaClass;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class JavaClassesView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.tu_bs.cs.isf.cbc.parser.views.JavaClassesView";

	@Inject
	IWorkbench workbench;

	private TreeViewer viewer;
	// private DrillDownAdapter drillDownAdapter;
	private Action refresh;
	// private Action action2;
	private Action doubleClickAction;

	class TreeObject implements IAdaptable {
		private String name;
		private TreeParent parent;

		public TreeObject(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public TreeParent getParent() {
			return parent;
		}

		@Override
		public String toString() {
			return getName();
		}

		@Override
		public <T> T getAdapter(Class<T> key) {
			return null;
		}
	}

	class TreeParent extends TreeObject {
		private List<TreeObject> children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList<>();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

		public void removeChildren() {
			children.forEach(child -> child.setParent(null));
			children.clear();
		}

		public TreeObject[] getChildren() {
			return (TreeObject[]) children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}
	}

	class ViewContentProvider implements ITreeContentProvider {
		private TreeParent invisibleRoot;

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null)
					initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
			return false;
		}

		/*
		 * We will set up a dummy model to initialize tree heararchy. In a real code,
		 * you will connect to a real model and expose its hierarchy.
		 */
		private void initialize() {
			invisibleRoot = new TreeParent("");

			JavaClasses.addPropertyChangeListener(new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					System.out.println("Java classes changed.");
					Display.getDefault().asyncExec(() -> {
						buildClassTree();
						viewer.refresh();
						viewer.expandAll();
					});
				}
			});

			// TreeObject to1 = new TreeObject("Leaf 1");
			// TreeObject to2 = new TreeObject("Leaf 2");
			// TreeObject to3 = new TreeObject("Leaf 3");
			// TreeParent p1 = new TreeParent("Parent 1");
			// p1.addChild(to1);
			// p1.addChild(to2);
			// p1.addChild(to3);
			//
			// TreeObject to4 = new TreeObject("Leaf 4");
			// TreeParent p2 = new TreeParent("Parent 2");
			// p2.addChild(to4);
			//
			// TreeParent root = new TreeParent("Root");
			// root.addChild(p1);
			// root.addChild(p2);
			//
			// invisibleRoot.addChild(root);
		}

		public void buildClassTree() {
			// Clear old tree
			invisibleRoot.removeChildren();

			// Build new tree
			final Map<String, Map<String, JavaClass>> javaClasses = JavaClasses.getJavaClasses();

			// Scan projects
			javaClasses.forEach((k, v) -> {
				final TreeParent projectRoot = new TreeParent(k);

				// Scan classes per project
				v.forEach((className, clazz) -> {
					// Class properties
					final TreeParent classRoot = new TreeParent(className);
					classRoot.addChild(new TreeObject("Class name: " + clazz.getClassName()));
					classRoot.addChild(new TreeObject("Parse time: " + clazz.getParseTime().toString()));
					classRoot.addChild(new TreeObject("Source file: " + clazz.getSourceFile()));
					classRoot.addChild(new TreeObject("Constructor: " + clazz.getConstructor()));

					// Extended types
					final TreeParent extendedTypesRoot = new TreeParent("Extended types");
					clazz.getSubtypes().getExtendedTypes().forEach(extendedTypes -> {
						extendedTypesRoot.addChild(new TreeObject(extendedTypes));
					});
					classRoot.addChild(extendedTypesRoot);

					// Implemented types
					final TreeParent implemenedTypesRoot = new TreeParent("Implemented types");
					clazz.getSubtypes().getImplementedTypes().forEach(implementedTypes -> {
						implemenedTypesRoot.addChild(new TreeObject(implementedTypes));
					});
					classRoot.addChild(implemenedTypesRoot);

					// Fields and methods
					final TreeParent fieldsAndMethodsRoot = new TreeParent("Fields and methods");
					clazz.getFieldsAndMethods().forEach(fieldOrMethod -> {
						fieldsAndMethodsRoot.addChild(new TreeObject(fieldOrMethod.toString()));
					});
					classRoot.addChild(fieldsAndMethodsRoot);

					// Add class data to project
					projectRoot.addChild(classRoot);
				});
				invisibleRoot.addChild(projectRoot);
			});
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return workbench.getSharedImages().getImage(imageKey);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		// drillDownAdapter = new DrillDownAdapter(viewer);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setInput(getViewSite());
		viewer.setLabelProvider(new ViewLabelProvider());

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "de.tu_bs.cs.isf.cbc.parser.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				JavaClassesView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(refresh);
		// manager.add(new Separator());
		// manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(refresh);
		// manager.add(action2);
		// manager.add(new Separator());
		// drillDownAdapter.addNavigationActions(manager);
		// // Other plug-ins can contribute there actions here
		// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refresh);
		// manager.add(action2);
		// manager.add(new Separator());
		// drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		refresh = new Action() {
			public void run() {
				ViewContentProvider contentProvider = (ViewContentProvider) viewer.getContentProvider();
				contentProvider.buildClassTree();
				viewer.refresh();
				viewer.expandAll();
			}
		};
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh java classes");
		refresh.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

		// action2 = new Action() {
		// public void run() {
		// showMessage("Double-click detected on " + obj.toString());
		// }
		// };
		// action2.setText("Action 2");
		// action2.setToolTipText("Action 2 tooltip");
		// action2.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		// doubleClickAction = new Action() {
		// public void run() {
		// IStructuredSelection selection = viewer.getStructuredSelection();
		// Object obj = selection.getFirstElement();
		// showMessage("Double-click detected on " + obj.toString());
		// }
		// };
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Java Classes View", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
