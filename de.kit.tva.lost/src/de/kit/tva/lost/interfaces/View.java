package de.kit.tva.lost.interfaces;

public interface View {
	public void addListener(Listener listener);

	public void removeListener(Listener listener);

	public void notifyListeners();
}
