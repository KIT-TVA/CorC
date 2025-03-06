package de.tu_bs.cs.isf.commands.toolbar.handler.family;

import java.util.ArrayList;

public class CurrentFeatureConfigTrackingStack {
	private ArrayList<String> trackingList;

	public CurrentFeatureConfigTrackingStack() {
		trackingList = new ArrayList<String>();
	}

	public void push(String currentConfig) {
		trackingList.add(currentConfig);
	}

	public void pop() {
		trackingList.remove(trackingList.size() - 1);
	}

	public String toConjunction() {
		String conj = " & (";

		for (int i = 0; i < trackingList.size(); i++) {
			conj += trackingList.get(i);
			conj += (i == trackingList.size() - 1) ? "" : " & ";
		}
		conj += ")";
		return conj;
	}
}
