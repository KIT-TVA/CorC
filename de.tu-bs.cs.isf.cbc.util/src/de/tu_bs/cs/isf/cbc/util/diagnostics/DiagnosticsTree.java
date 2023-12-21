package de.tu_bs.cs.isf.cbc.util.diagnostics;

import java.util.ArrayList;

/**
 * Transforms diagnostics data into a tree view.
 * @author Fynn
 *
 */
public class DiagnosticsTree {
	private final DiagnosticsData data;

	public DiagnosticsTree(DiagnosticsData data) {
		this.data = data;
		createTree();
	}
	
	private void createTree() {
		if (!data.isSplData()) {
			if (data instanceof TestStatementData) {
				var paths = new ArrayList<Path>();
				for (var timedObj : data.getData()) {
					paths.add(new Path(timedObj));
				}
				MergeTree tree = new MergeTree(paths);
			}
		}
	}
}
