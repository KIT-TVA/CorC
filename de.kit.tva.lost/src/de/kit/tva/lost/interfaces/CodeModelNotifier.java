package de.kit.tva.lost.interfaces;

public class CodeModelNotifier extends AbstractModel {
    public void notifyCodeChanged() {
	this.listeners.forEach(l -> ((CodeListener) l).updateCode());
    }
}
