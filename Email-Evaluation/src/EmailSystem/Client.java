package EmailSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Client {

	protected @SecurityLevel("low") int id;

	protected @SecurityLevel("low") String name;

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") int getId() {
		return id;
	}

	private Client(
			@SecurityLevel("low") int id, 
			@SecurityLevel("low") String name, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<KeyringEntry> keyring,
			@SecurityLevel("high") int privateKey, 
			@SecurityLevel("low") boolean autoResponse,
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<AddressBookEntry> addressbook,
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client forwardReceiver) {
		this.id = id;
		this.name = name;

		setPrivateKey(privateKey);
		setAutoResponse(autoResponse);
	}

	public static @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client createClient(
			@SecurityLevel("low") String name, 
			@SecurityLevel("high") int privateKey,
			@SecurityLevel("low") boolean autoResponse) {
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client = new Client(clientCounter++, name, new ArrayList<KeyringEntry>(), privateKey, autoResponse, new ArrayList<AddressBookEntry>(), null);
		clients[client.getId()] = client; // clients[] low
		return client;
	}
	


	public static @SecurityLevel("low") Client createClientMutableName(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) String name, 
			@SecurityLevel("high") int privateKey,
			@SecurityLevel("low") boolean autoResponse) {
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client = new Client(clientCounter++, name, new ArrayList<KeyringEntry>(), privateKey, autoResponse, new ArrayList<AddressBookEntry>(), null);
		clients[client.getId()] = client; // clients[] low
		return client;
	}

	static void deliver(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		Util.prompt("mail delivered\n");
	}


	/*@ 
	 ensures msg.isEncrypted() ==> encryptedMails.contains(msg);
	 assignable \nothing; @*/
	private static void incoming__wrappee__AutoResponder(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		deliver(client, msg);
		if (client.isAutoResponse()) {
			autoRespond(client, msg);
		}
	}

	/*@ 
	 ensures msg.isEncrypted() ==> encryptedMails.contains(msg);
	 assignable \nothing; @*/
	private static void incoming__wrappee__Forward(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		incoming__wrappee__AutoResponder(client, msg);
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client receiver = client.getForwardReceiver();
		if (receiver != null) {
			msg.setEmailTo(receiver.getName());
			forward(client, msg);
			incoming(receiver, msg);
		}
	}

	/*@ 
	 ensures msg.isEncrypted() ==> encryptedMails.contains(msg);
	 assignable \nothing; @*/
	private static void incoming__wrappee__Verify(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client,
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		verify(client, msg);
		incoming__wrappee__Forward(client, msg);
	}

	/*@ 
	 ensures msg.isEncrypted() ==> encryptedMails.contains(msg);
	 assignable \nothing; @*/
	static void incoming(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {

		@SecurityLevel("high") int privkey = client.getPrivateKey(); //receiver key
		@SecurityLevel("high") boolean intermediateBool = msg.isEncrypted();
		@SecurityLevel("high") int intermediateInt = msg.getEmailEncryptionKey();
		if (privkey != 0) {
			if (msg.isEncrypted() && isKeyPairValid(msg.getEmailEncryptionKey(), privkey)) { //high context, only assignments to high
				intermediateBool = false;
				intermediateInt = 0;
			}
		}
		msg.setEmailIsEncrypted(intermediateBool);
		msg.setEmailEncryptionKey(intermediateInt);
		incoming__wrappee__Verify(client, msg);
	}

	/*@ 
	 requires !msg.isSignatureVerified(); 
	 assignable \nothing; @*/
	static void mail(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		Util.prompt("mail sent");
	}

	private static void outgoing__wrappee__Base(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		msg.setEmailFrom(client);
		mail(client, msg);
	}

	private static void outgoing__wrappee__Encrypt(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {

		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client receiver = getClientByAdress(msg.getEmailTo());
		@SecurityLevel("low") int pubkey = client.getKeyringPublicKeyByClient(receiver);
		if (pubkey != 0) { // low
			msg.setEmailEncryptionKey(pubkey); //pubkey promoted to high
			msg.setEmailIsEncrypted(true);
			Util.prompt("Encrypted Mail " + msg.getId());
		}

		outgoing__wrappee__Base(client, msg);
	}

	private static void outgoing__wrappee__Addressbook(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) List<String> aliasReceivers = client.getAddressBookReceiversForAlias(msg.getEmailTo());
		if (!aliasReceivers.isEmpty()) {

			for (int i = 1; i < aliasReceivers.size(); i++) {
				@SecurityLevel("low") String receiverAddress = aliasReceivers.get(i);
				msg.setEmailTo(receiverAddress);
				outgoing(client, msg);
				incoming(Client.getClientByAdress(receiverAddress), msg);
			}
			msg.setEmailTo(aliasReceivers.get(0));
			outgoing__wrappee__Encrypt(client, msg);
		} else {

			outgoing__wrappee__Encrypt(client, msg);
		}
	}

	static void outgoing(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		sign(client, msg);
		outgoing__wrappee__Addressbook(client, msg);
	}

	public static @SecurityLevel("low") int sendEmail(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client sender, 
			@SecurityLevel("low") String receiverAddress, 
			@SecurityLevel("low") String subject, 
			@SecurityLevel("high") String body) {
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email email = Email.createEmail(sender, receiverAddress, subject, body);
		Util.prompt("sending Mail " + email.getId());
		outgoing(sender, email);
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client receiver = Client.getClientByAdress(email.getEmailTo());
		if (receiver != null) {
			incoming(receiver, email);
		} else {
//			throw new IllegalArgumentException("Receiver " + receiverAddress + " Unknown");
		}
		return 0;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") String getName() {
		return name;
	}

	static @SecurityLevel("low") int clientCounter = 1;

	static @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client[] clients = new Client[4];

	static @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client getClientById(@SecurityLevel("low") int id) {
		return clients[id];
	}

	static @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client getClientByAdress(@SecurityLevel("low") String address) {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] != null && clients[i].getName().equals(address)) {
				return clients[i];
			}
		}
		return null;
//		throw new IllegalArgumentException("Receiver " + address + " Unknown");
	}

	// @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) - 
	public static void resetClients() {
		clientCounter = 1;
		for (int i = 0; i < clients.length; i++) {
			clients[i] = null;
		}
	}

	@Override
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") String toString() {
		return name;
	}

	protected @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<KeyringEntry> keyring;

	protected @SecurityLevel("high") int privateKey;

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setPrivateKey(@SecurityLevel("high") int privateKey) { 
		this.privateKey = privateKey;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("high") int getPrivateKey() { 
		return privateKey;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void addKeyringEntry(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") int publicKey) {
		this.keyring.add(new KeyringEntry(client, publicKey));
	}

	
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("low") int getKeyringPublicKeyByClient(@SecurityLevel("low") @MutationModifier(MDF.READ) Client client) {
		for (@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) KeyringEntry e : keyring) {
			if (e.getKeyOwner().equals(client)) {
				return e.getPublicKey();
			}
		}
		return 0;
	}

	public /*@pure@*/ static @SecurityLevel("high") boolean isKeyPairValid(
			@SecurityLevel("high") int publicKey, 
			@SecurityLevel("high") int privateKey) {
		//Util.prompt("keypair valid " + publicKey + " " + privateKey);
		if (publicKey == 0 || privateKey == 0)
			return false;
		return privateKey == publicKey; //should be a secure validation. This is just an example.
	}

	static class KeyringEntry {
		private @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client keyOwner;

		private @SecurityLevel("low") int publicKey;

		public KeyringEntry(
				@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client keyOwner, 
				@SecurityLevel("low") int publicKey) {
			super();
			this.keyOwner = keyOwner;
			this.publicKey = publicKey;
		}

		@MethodReceiver(SL = "low", MDF = MDF.READ)
		public @SecurityLevel("low") @MutationModifier(MDF.READ) Client getKeyOwner() {
			return keyOwner;
		}

		@MethodReceiver(SL = "low", MDF = MDF.READ)
		public @SecurityLevel("low") int getPublicKey() {
			return publicKey;
		}

	}

	///*@model@*/ Set<Email> encryptedMails = new HashSet<Email>(2);

	///*@model@*/ Set<Email> unEncryptedMails = new HashSet<Email>(2);

	protected @SecurityLevel("low") boolean autoResponse;

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	private void setAutoResponse(@SecurityLevel("low") boolean autoResponse) {
		this.autoResponse = autoResponse;
	}

	@MethodReceiver(SL = "high", MDF = MDF.READ)
	public @SecurityLevel("low") boolean isAutoResponse() {
		return autoResponse;
	}

	/*@ 
	 requires !msg.isReadable();
	 assignable \nothing; @*/
	static void autoRespond(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		Util.prompt("sending autoresponse\n");
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client sender = msg.getEmailFrom();
		msg.setEmailTo(sender.getName());
		outgoing(client, msg);
		incoming(sender, msg);
	}

	protected @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<AddressBookEntry> addressbook;

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<String> getAddressBookReceiversForAlias(@SecurityLevel("low") String alias) {
		for (@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) AddressBookEntry e : addressbook) {
			if (e.getAlias().equals(alias)) {
				return e.getReceivers();
			}
		}
		return new ArrayList<>(Collections.emptyList());
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void addAddressbookEntry(@SecurityLevel("low") String alias, @SecurityLevel("low") String receiver) {
		for (@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) AddressBookEntry e : addressbook) { 
			if (e.getAlias().equals(alias)) {
				e.addReceiver(receiver);
				return;
			}
		}
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) AddressBookEntry newEntry = new AddressBookEntry(alias, new ArrayList<String>());
		newEntry.addReceiver(receiver);
		addressbook.add(newEntry);
	}

	static void sign(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) { 
		@SecurityLevel("high") int privkey = client.getPrivateKey();
		@SecurityLevel("high") boolean intermediateBool = msg.isSigned();
		@SecurityLevel("high") int intermediateInt = msg.getEmailSignKey();
		if (privkey == 0) {
			
		} else {
			intermediateBool = true;
			intermediateInt = privkey;
		}
		msg.setEmailIsSigned(intermediateBool);
		msg.setEmailSignKey(intermediateInt);
	}

	///*@model@*/ Set<Email> signedMails = new HashSet<Email>(2);

	/*@ 
	 requires !msg.isReadable(); 
	 assignable \nothing; @*/
	static void verify(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) { //receiver of message
		@SecurityLevel("low") int pubkey = client.getKeyringPublicKeyByClient(msg.getEmailFrom());
		@SecurityLevel("high") boolean intermediateBool = msg.isSignatureVerified();
		if (pubkey != 0 && isKeyPairValid(msg.getEmailSignKey(), pubkey)) { //high guard, only high assignments
			intermediateBool = true;
		}
		msg.setIsSignatureVerified(intermediateBool);
	}

	protected @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client forwardReceiver;

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setForwardReceiver(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client forwardReceiver) {
		this.forwardReceiver = forwardReceiver;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") @MutationModifier(MDF.READ) Client getForwardReceiver() {
		return forwardReceiver;
	}

	static void forward(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		Util.prompt("Forwarding message.\n");
		Email.printEmail(msg);
		outgoing(client, msg);
	}
}
