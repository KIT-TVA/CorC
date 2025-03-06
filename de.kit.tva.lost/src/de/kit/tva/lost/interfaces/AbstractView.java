package de.kit.tva.lost.interfaces;

import java.util.ArrayList;

public abstract class AbstractView implements View {
    ArrayList<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
	if (!listeners.contains(listener)) {
	    listeners.add(listener);
	}
    }

    public void removeListener(Listener listener) {
	if (listeners.contains(listener)) {
	    listeners.remove(listener);
	}
    }

    public void notifyListeners() {
	listeners.forEach(l -> l.update());
    }
}
