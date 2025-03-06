package de.kit.tva.lost.interfaces;

public interface VerifyListener extends Listener {
	void verificationDone();

	void verificationDone(Result verifiee);
}
