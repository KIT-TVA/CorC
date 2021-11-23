package EmailSystem; 

public  class Client {
	static Client[] clients = new Client[4];
	protected int id;
	protected String name;
	protected KeyringEntry keyring;
	protected int privateKey;
	protected boolean autoResponse;
	//protected ArrayList<AddressBookEntry> addressbook = new ArrayList<AddressBookEntry>();
	protected Client forwardReceiver;

	public int getId() {
		return id;
	}



	static void deliver(Client client, Email msg) {
	}



	private static void incoming__wrappee__Keys(Client client, Email msg) {
		deliver(client, msg);
	}

	/*@ ensures msg.isEncrypted() ==> encryptedMails.contains(msg); @*/

	private static void incoming__wrappee__Encrypt(Client client, Email msg) {
		//TODO add to encryptedMails if msg.isEncrypted()
		incoming__wrappee__Keys(client, msg);
	}

	/*@ ensures msg.isEncrypted() ==> encryptedMails.contains(msg); @*/

	private static void incoming__wrappee__Sign(Client client, Email msg) {
		incoming__wrappee__Encrypt(client, msg);
		if (client.isAutoResponse()) {
			autoRespond(client, msg);
		}
	}

	/*@ ensures msg.isEncrypted() ==> encryptedMails.contains(msg); @*/

	private static void incoming__wrappee__Forward(Client client, Email msg) {
		incoming__wrappee__Sign(client, msg);
		Client receiver = client.getForwardReceiver();
		if (receiver != null) {
			msg.setEmailTo(receiver.getName());
			forward(client, msg);
			incoming(receiver, msg);
		}
	}

	/*@ ensures msg.isEncrypted() ==> encryptedMails.contains(msg); @*/

	private static void incoming__wrappee__Verify(Client client, Email msg) {
		verify(client, msg);
		incoming__wrappee__Forward(client, msg);
	}

	/*@ ensures msg.isEncrypted() ==> encryptedMails.contains(msg); @*/

	static void incoming(Client client, Email msg) {
		// decrypt

		int privkey = client.getPrivateKey();
		if (privkey != 0) {
			if (msg.isEncrypted()
					&& isKeyPairValid(msg.getEmailEncryptionKey(), privkey)) {
				msg.setEmailIsEncrypted(false);
				msg.setEmailEncryptionKey(0);
			}
		}
		// end decrypt

		incoming__wrappee__Verify(client, msg);
	}



	private static void mail__wrappee__Keys(Client client, Email msg) {
	}

	/*@ requires msg.isEncrypted() ==> !unEncryptedMails.contains(msg);
	 requires !msg.isEncrypted() ==> !encryptedMails.contains(msg);
	 requires encryptedMails.contains(msg) ==> msg.isEncrypted();
	 ensures msg.isEncrypted() ==> encryptedMails.contains(msg);
	 ensures !msg.isEncrypted() ==> unEncryptedMails.contains(msg); @*/

	private static void mail__wrappee__Addressbook  (Client client, Email msg) {
		//TODO add to encryptedMails if msg.isEncrypted(), else to unEncryptedMails
		mail__wrappee__Keys(client, msg);
	}

	/*@ ensures msg.isSigned() ==> signedMails.contains(msg); @*/

	private static void mail__wrappee__Forward(Client client, Email msg) {
		//TODO add to signedMails if msg.isSigned()
		mail__wrappee__Addressbook(client, msg);
	}

	/*@ requires !msg.isSignatureVerified(); @*/

	static void mail(Client client, Email msg) {
		mail__wrappee__Forward(client, msg);
	}



	private static void outgoing__wrappee__Keys(Client client, Email msg) {
		msg.setEmailFrom(client);
		mail(client, msg);
	}



	private static void outgoing__wrappee__AutoResponder(Client client, Email msg) {
		// encrypt
		Client receiver = getClientByAdress(msg.getEmailTo());
		int pubkey = client.getKeyringPublicKeyByClient(receiver);
		if (pubkey != 0) {
			msg.setEmailEncryptionKey(pubkey);
			msg.setEmailIsEncrypted(true);
		}

		// end encrypt

		outgoing__wrappee__Keys(client, msg);
	}



//	private static void outgoing__wrappee__Addressbook(Client client, Email msg) {
//		List<String> aliasReceivers = client
//				.getAddressBookReceiversForAlias(msg.getEmailTo());
//		if (!aliasReceivers.isEmpty()) {
//			// found alias, sending to the addresses that are associated with
//			// this alias (to addresses 1,2,...) address 0 will be handled by the other methods
//			for (int i = 1; i < aliasReceivers.size(); i++) {
//				String receiverAddress = aliasReceivers.get(i);
//				msg.setEmailTo(receiverAddress);
//				outgoing(client, msg);
//				incoming(Client.getClientByAdress(receiverAddress), msg);
//			}
//			msg.setEmailTo(aliasReceivers.get(0));
//			outgoing__wrappee__AutoResponder(client, msg);
//		} else {
//			// no alias, must be a normal address
//			outgoing__wrappee__AutoResponder(client, msg);
//		}
//	}



	static void outgoing(Client client, Email msg) {
		sign(client, msg);
		outgoing__wrappee__AutoResponder(client, msg);
	}



	public static int sendEmail(Client sender, String receiverAddress,
			String subject, String body) {
		Email email = Email.createEmail(sender, receiverAddress, subject, body);
		outgoing(sender, email);
		Client receiver = Client.getClientByAdress(email.getEmailTo());
		if (receiver != null) {
			incoming(receiver, email);
		} else {
			throw new IllegalArgumentException("Receiver " + receiverAddress + " Unknown");
		}
		return 0; // die Zeile kommt von mir
	}



	private Client(int id, String name) {
		this.id = id;
		this.name = name;
	}



	public String getName() {
		return name;
	}

	static int clientCounter = 1;



	public static Client createClient(String name) {
		Client client = new Client(clientCounter++, name);
		clients[client.getId()] = client;
		return client;
	}

	static Client getClientById(int id) {
		return clients[id];
	}



	static Client getClientByAdress(String address) {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] != null && clients[i].getName().equals(address)) {
				return clients[i];
			}
		}
		return null;
	}



	public static void resetClients() {
		clientCounter = 1;
		for (int i = 0; i < clients.length; i++) {
			clients[i] = null;
		}
	}



	@Override
	public String toString() {
		return name;
	}

	public void setPrivateKey(int privateKey) {
		this.privateKey = privateKey;
	}



	public /*@pure*/ int getPrivateKey() {
		return privateKey;
	}



	public static void generateKeyPair(Client client, int seed) {
		client.setPrivateKey(seed);
	}



//	public void addKeyringEntry(Client client, int publicKey) {
//		this.keyring.add(new KeyringEntry(client, publicKey));
//	}



	public /*@pure@*/ int getKeyringPublicKeyByClient(Client client) {
//		for (KeyringEntry e : keyring) {
			if (keyring.getKeyOwner().equals(client)) {
				return keyring.getPublicKey();
			}
//		}
		return 0;
	}



	public /*@pure@*/  static boolean isKeyPairValid(int publicKey, int privateKey) {
		if (publicKey == 0 || privateKey == 0)
			return false;
		return privateKey == publicKey;
	}

	public void setAutoResponse(boolean autoResponse) {
		this.autoResponse = autoResponse;
	}

	public boolean isAutoResponse() {
		return autoResponse;
	}

	/*@ requires !msg.isReadable(); @*/

	static void autoRespond(Client client, Email msg) {
		Client sender = msg.getEmailFrom();
		msg.setEmailTo(sender.getName());
		outgoing(client, msg);
		incoming(sender, msg);
	}

//	public List<String> getAddressBookReceiversForAlias(String alias) {
//		for (AddressBookEntry e : addressbook) {
//			if (e.getAlias().equals(alias)) {
//				return e.getReceivers();
//			}
//		}
//		return Collections.emptyList();
//	}



//	public void addAddressbookEntry(String alias, String receiver) {
//		for (AddressBookEntry e : addressbook) {
//			if (e.getAlias().equals(alias)) {
//				e.addReceiver(receiver);
//				return;
//			}
//		}
//		AddressBookEntry newEntry = new AddressBookEntry(alias);
//		newEntry.addReceiver(receiver);
//		addressbook.add(newEntry);
//	}

	static void sign(Client client, Email msg) {
		int privkey = client.getPrivateKey();
		if (privkey == 0)
			return;
		msg.setEmailIsSigned(true);
		msg.setEmailSignKey(privkey);
	}


	/*@ requires !msg.isReadable(); @*/

	static void verify  (Client client, Email msg) {
		int pubkey = client.getKeyringPublicKeyByClient(msg.getEmailFrom());
		if (pubkey != 0 && isKeyPairValid(msg.getEmailSignKey(), pubkey)) {
			msg.setIsSignatureVerified(true);
		}
	}

	public void setForwardReceiver(Client forwardReceiver) {
		this.forwardReceiver = forwardReceiver;
	}

	public Client getForwardReceiver() {
		return forwardReceiver;
	}

	static void forward(Client client, Email msg) {
		Email.printMail(msg);
		outgoing(client, msg);
	}
}
