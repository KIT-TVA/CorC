package de.kit.tva.lost.interfaces;

public interface TestListener extends Listener {
	void testsDone();

	void testDone(Result testee);
}
