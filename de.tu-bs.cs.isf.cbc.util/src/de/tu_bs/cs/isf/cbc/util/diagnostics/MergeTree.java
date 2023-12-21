package de.tu_bs.cs.isf.cbc.util.diagnostics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MergeTree {
	private static final String EDGE_SYMBOL = " -> ";
	private final List<List<Vertex>> heights;

	public MergeTree(final List<Path> paths) {
		this.heights = new ArrayList<List<Vertex>>();
		constructTreeHeights(paths);
		calculateEdgeEnds();
	}

	// TODO refractor into multiple methods
	private void calculateEdgeEnds() {
		final var lines = new ArrayList<HeightString>();
		final var strLens = new ArrayList<Integer>();
		final var edgeEnds = new ArrayList<ArrayList<Integer>>();
		final var spaces = new ArrayList<Integer>();
		int i = 0;
		for (final var height : heights) {
			strLens.set(i, height.size() * maxWordLen(height) + 2 * height.size());
			for (int j = 0; j < strLens.size(); j++) {
				if (edgeEnds.get(i) == null) {
					edgeEnds.set(i, new ArrayList<Integer>());
				}
				edgeEnds.get(i).add(strLens.get(i) / (2 * strLens.size()) + j * strLens.size()); 
			}
			i++;
		}
		for (i = 0; i < this.heights.size(); i++) {
			spaces.add((maxLen(strLens) - strLens.get(i)) / 2);
		}
		for (i = 0; i < strLens.size(); i++) {
			strLens.set(i, strLens.get(i) + spaces.get(i));
		}
		for (final var e : edgeEnds) {
			for (i = 0; i < e.size(); i++) {
				e.set(i, e.get(i) + spaces.get(i));
			}
		}
		final var m = new HashMap<Vertex, Edge>();
		// calc position of edge start
		for (i = 1; i < this.heights.size(); i++) {
			int j = 0;
			for (final var v : this.heights.get(i)) {
				int position = getParentPosition(v, i-1);
				int b = edgeEnds.get(i-1).get(position);
				m.put(v, new Edge(b, edgeEnds.get(i).get(j)));
				j++;
			}
		}
		// construct edge connections TODO
		for (final var v : getDistinctRefinements()) {
			int h = getHeight(v);
			for (i = 0; i < h; i++) {
				//lines.set(i, new HeightString(
			}
		}
	}
	
	private int getHeight(final Vertex v) {
		int i = -1;
		
		for (final var h : heights) {
			for (final var r : h) {
				if (r.equals(v)) {
					return i;
				}
			}
			i++;
		}
		return -1;
	}
	
	private List<Vertex> getDistinctRefinements() {
		final var distinctRefinements = new ArrayList<Vertex>();
		for (final var h : heights) {
			for (final var r : h) {
				if (!distinctRefinements.contains(r)) {
					distinctRefinements.add(r);
				}
			}
		}
		return distinctRefinements;
	}

	private int getParentPosition(final Vertex v, final int height) {
		for (int j = 0; j < this.heights.get(height).size(); j++) {
			if (this.heights.get(height).get(j).getNext().equals(v)) {
				return j;
			}
		}
		return -1;
	}
	
	private int maxLen(final List<Integer> lengths) {
		int max = -1;
		for (var len : lengths) {
			if (max < len) {
				max = len;
			}
		}
		return max;
	}
	
	private int maxWordLen(final List<Vertex> height) {
		int max = -1;
		for (Vertex v : height) {
			if (max < v.getName().length()) {
				max = v.getName().length();
			}
		}
		return max;
	}
	
	private void constructTreeHeights(final List<Path> paths) {
		var height = findLeafHeight(paths);
		for (int h = 0; h < height; h++) {
			if (heights.get(h) == null) {
				heights.set(h, new ArrayList<Vertex>());
			}
			for (var path : paths) {
				if (h < path.getNumNodes() && !heights.get(h).contains(path.getVertex(h))) {
					heights.get(h).add(path.getVertex(h));
				}
			}
		}
	}
	
	private int findLeafHeight(final List<Path> paths) {
		int max = -1;
		for (final var path : paths) {
			if (max < path.getNumNodes()) {
				max = path.getNumNodes();
			}
		}
		return max;
	}
	
}
