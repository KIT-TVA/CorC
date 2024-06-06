package de.kit.tva.lost.interfaces;

public class TestModelNotifier extends AbstractModel {
    public void testsFinished() {
	this.listeners.forEach(l -> ((TestListener) l).testsDone());
    }

    public void testFinished(Testee testee) {
	this.listeners.forEach(l -> ((TestListener) l).testDone(testee));
    }
}
