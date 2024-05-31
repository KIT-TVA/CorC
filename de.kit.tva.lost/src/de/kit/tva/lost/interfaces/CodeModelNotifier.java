package de.kit.tva.lost.interfaces;

public class CodeModelNotifier extends AbstractModel {
    public void notifyCodeChangedListeners() {
	this.listeners.forEach(l -> ((CodeListener) l).updateCode());
    }

    public void notifyViewChangedListeners() {
	this.listeners.forEach(l -> ((CodeListener) l).updateView());
    }
}
