package de.tu_bs.cs.isf.cbc.statistics.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.cbc.statistics.CSVHelper;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsDatabase;
import de.tu_bs.cs.isf.cbc.statistics.StatisticsEntry;
import de.tu_bs.cs.isf.cbc.util.Console;
import de.tu_bs.cs.isf.cbc.util.Features;


public class StatisticsDialog extends TitleAreaDialog {
	
	private List<?> paths = null;
	private int numberOfDiagrams;
	private boolean isSpl = false;
	private final List<StatisticsEntry> entries = new LinkedList<StatisticsEntry>();
	private final List<String> configs = new ArrayList<String>(); 
	private HashMap<StatisticsEntry, String> configEntries = new HashMap<StatisticsEntry, String>();
	private List<IFile> selectedDiagramFiles = new LinkedList<IFile>();
	private boolean configView = false;
	private Features features = null;
	
	public StatisticsDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Statistics Viewer");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createBrowser(container);

		return area;
	}

	private void createBrowser(Composite parent) {
		Browser browser = new Browser(parent, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		browser.setLayoutData(gridData);
		
		HtmlHandler htmlSite = new HtmlHandler();
		htmlSite.setConfigView(this.configView);
		htmlSite.setDiagramPaths(paths);
		final String templateHTML;

		if (this.isSpl) {
			htmlSite.setDataSPL(numberOfDiagrams, configEntries, configs, selectedDiagramFiles, features);
			templateHTML = htmlSite.getHtmlStringSPL();
		} else {
			htmlSite.setData(numberOfDiagrams, entries, selectedDiagramFiles);
			templateHTML = htmlSite.getHtmlString();
		}
		if (templateHTML == null) {
			Console.clear();
			Console.println("No up to date prove data found.");
			return;
		}
		browser.setText(templateHTML);
		browser.setFocus();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
//		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		if (this.isSpl && this.configView) {
			createButton(parent, IDialogConstants.OK_ID, "Show Avg View", true);
		} else if (this.isSpl && !this.configView) {
			createButton(parent, IDialogConstants.OK_ID, "Show Config View", true);
		}
		createButton(parent, IDialogConstants.CLIENT_ID, "Export CSV File", false);
		createButton(parent, IDialogConstants.CANCEL_ID, "Close", true);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			showConfigView();
		} else if (IDialogConstants.CLIENT_ID == buttonId) {
			exportCSV();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}
	
	private void showConfigView() {
		if (configEntries == null || configEntries.isEmpty()
				|| selectedDiagramFiles == null || selectedDiagramFiles.isEmpty()) {
			return;
		}
		this.toggleConfigView();
		for (var config : configEntries.values()) {
			setDataSPL(selectedDiagramFiles, config, features);
		}
		this.close();
		this.create();
		this.open();
	}

	private void toggleConfigView() {
		this.configView = !this.configView ;
	}

	private void exportCSV() {
	    FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
	    dialog.setFilterNames(new String[] { "CSV Files", "All Files (*.*)" });
	    dialog.setFilterExtensions(new String[] { "*.csv", "*.*" });
	    
	    dialog.setFilterPath(selectedDiagramFiles.get(0).getParent().getFullPath().toFile().getAbsolutePath().toString());
	    dialog.setFileName("statistics.csv");
	    
	    String path = dialog.open();
		CSVHelper helper = new CSVHelper();
		
		String errorMessage = helper.generateCSVFile(path, entries);
		if(errorMessage == null) {
			MessageDialog.openInformation(getShell(), "Success", "The CSV file was successfully exported.");
		}
		else {
			MessageDialog.openError(getShell(), "Error", "The CSV could not be exported:\n\n"+errorMessage);
		}
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		super.cancelPressed();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(1200, 800);
	}

	public boolean setDataSPL(final List<IFile> allDiagramFiles, final String configName, final Features features) {
		this.isSpl = true;
		this.features = features;
		if (!this.configs.contains(configName)) {
			this.configs.add(configName);
		}
		if (allDiagramFiles.size() < 1) {
			de.tu_bs.cs.isf.cbc.util.Console.println("No diagram files selected.");
			return false;
		}
		else {
			for (IFile file : allDiagramFiles) {
				for (StatisticsEntry entry : StatisticsDatabase.instance.getConfigEntries(file, configName)) {
					configEntries.put(entry, configName);
				}
			}
			numberOfDiagrams = allDiagramFiles.size();
			selectedDiagramFiles = allDiagramFiles;
		}
		return true;
	}

	public boolean setData(List<IFile> allDiagramFiles) {
		this.isSpl = false;
		if (allDiagramFiles.size() < 1) {
			de.tu_bs.cs.isf.cbc.util.Console.println("No diagram files selected.");
			return false;
		}
		else {
			for (IFile file : allDiagramFiles) {
				entries.addAll(StatisticsDatabase.instance.getEntriesRelatedTo(file));
			}
			numberOfDiagrams = allDiagramFiles.size();
			selectedDiagramFiles = allDiagramFiles;
		}
		return true;
	}

//	private boolean multipleDiagrams(List<StatisticsEntry> entries) {
//
//		boolean moreThanOne = false;
//		
//		for (int i = 0; entries.size() > i; i++) {
//			String outterDiagramName = entries.get(i).getMapping().getCorcDiagramName();
//			for (int j= i + 1; entries.size() > j; j++) {
//				String innerDiagramName = entries.get(j).getMapping().getCorcDiagramName();
//				if (!outterDiagramName.equals(innerDiagramName)) {
//					moreThanOne = true;
//				}
//			}
//			
//		}
//		return moreThanOne;
//	}
}