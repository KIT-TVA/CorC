package de.kit.tva.lost.interfaces;

public interface Model {
    public void addListener(Listener listener);

    public void removeListener(Listener listener);

    public void notifyListeners();
}
