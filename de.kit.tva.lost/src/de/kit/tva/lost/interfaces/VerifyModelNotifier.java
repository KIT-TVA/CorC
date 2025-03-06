package de.kit.tva.lost.interfaces;

public class VerifyModelNotifier extends AbstractModel {
	public void verificationFinished() {
		this.listeners.forEach(l -> ((VerifyListener) l).verificationDone());
	}

	public void verificationFinished(Result verifiee) {
		this.listeners.forEach(l -> ((VerifyListener) l).verificationDone(verifiee));
	}
}
