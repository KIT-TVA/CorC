package de.kit.tva.lost.interfaces;

public class CodeModelNotifier extends AbstractModel {
	public void codeChanged() {
		this.listeners.forEach(l -> ((CodeListener) l).updateCode());
	}

	public void viewChanged() {
		this.listeners.forEach(l -> ((CodeListener) l).updateView());
	}
}
