package EmailSystem;


public class KeyringEntry {
	private Client keyOwner;
	private int publicKey;

	public KeyringEntry(Client keyOwner, int publicKey) {
		super();
		this.keyOwner = keyOwner;
		this.publicKey = publicKey;
	}

	public Client getKeyOwner() {
		return keyOwner;
	}

	public int getPublicKey() {
		return publicKey;
	}
}
