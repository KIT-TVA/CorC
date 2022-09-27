package EmailSystem; 


public  class Client {
	private /*@spec_public@*/ /*@non_null*/ String name;
	private /*@spec_public@*/ int id;
	private /*@spec_public@*/ static Client[] clients;
	private /*@spec_public@*/ static int clientCounter;
	private /*@spec_public@*/ static int old_clientCounter;
	protected KeyringEntry keyring;
	protected int privateKey;
	protected boolean autoResponse;
	private Email[] mailbox = new Email[0];
	//protected ArrayList<AddressBookEntry> addressbook = new ArrayList<AddressBookEntry>();
	protected /*@spec_public@*/ static Client forwardReceiver;
	
	
	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && (Client.clients instanceof Client[]) && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client createClientResultOld(String name) {
		Client result = null;
		result = new Client();
		result.constructClient(Client.clientCounter++, name);
		Client.clients[result.getId()] = result;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && (Client.clients instanceof Client[]) && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == Client.old_clientCounter && \result.getName() == name && Client.clientCounter == Client.old_clientCounter + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client createClientResult(String name) {
		Client result = null;
		Client.old_clientCounter = Client.clientCounter;
		result = new Client();
		result.constructClient(Client.clientCounter++, name);
		Client.clients[result.getId()] = result;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires Client.clients != null && id >= 0 && id < Client.clients.length;
	@ ensures \result == Client.clients[id];
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client getClientById(int id) {
		Client result = null;
		result = Client.clients[id];
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.id;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getId() {
		int result = 0;
		result = this.id;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (this.name != null ==> \result == this.name) && (this.name == null ==> \result == null);
	@ assignable \nothing;
	@*/
	public /*@pure*//*@helper*/ String getName() {
		String result = null;
		result = this.name;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.name;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getNameWithKeyword() {
		String result = null;
		result = this.name;
		return result;
	}

	/*@
	@ normal_behavior
	@ requires client != null && msg != null;
	@ ensures msg.from == client;
	@ assignable msg.from;
	@*/
	public static void outgoing(Client client, Email msg) {
		msg.setEmailFrom(client);

	}

	/*@
	@ normal_behavior
	@ requires Client.clients != null&& Client.clientCounter >= 0 && Client.clients != null;
	@ ensures (\forall int k;((k>=0 && k<Client.clients.length) ==> Client.clients[k] == null)) && Client.clientCounter == 1;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static void resetClients() {
		int index = 0;
		index = 0;
		Client.clientCounter = 1;
		//@ loop_invariant Client.clientCounter == 1 && index >= 0 && (\forall int k;((k>=0 && k<index && k<Client.clients.length) ==> Client.clients[k] == null));
		//@ decreases Client.clients.length - index;
		while (index < Client.clients.length) {
			Client.clients[index] = null;
			index++;
		}

	}

	/*@
	@ normal_behavior
	@ requires true && idx >= 0 && namex != null;
	@ ensures (idx >= 0 && namex != null) ==> (this.id == idx && this.name == namex);
	@ assignable this.id,this.name;
	@*/
	public /*@helper@*/ Client(int idx, String namex) {
		this.id = idx;
		this.name = namex;

	}

	/*@
	@ normal_behavior
	@ requires true && idx >= 0 && namex != null;
	@ ensures (idx >= 0 && namex != null) ==> (this.id == idx && this.name == namex);
	@ assignable this.id,this.name;
	@*/
	public /*@helper@*/ void constructClient(int idx, String namex) {
		this.id = idx;
		this.name = namex;

	}

	public Client() {
		
	}

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0;
	@ ensures \result != null && \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client createClient(String name) {
		Client result = null;
		result = new Client();
		result.constructClient(Client.clientCounter++, name);
		Client.clients[result.getId()] = result;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires Client.clients != null;
	@ ensures (\exists int k;((k>=0 && k<Client.clients.length  && Client.clients[k].getName() != null && Client.clients[k].getName().equals(address) == true) ==> \result == Client.clients[k]));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client getClientByAdress(String address) {
		int index = 0;
		Client result = null;
		index = 0;
		result = null;
		//@ loop_invariant index >= 0 &&(\exists int k;((k>=0 && k<index && Client.clients[k].getName() != null&& Client.clients[k].getName().equals(address) == true) ==> result == Client.clients[k]));
		//@ decreases Client.clients.length - index;
		while (index < Client.clients.length) {
			if (Client.clients[index] != null && Client.clients[index].getName().equals(address) == true) {
				result = Client.clients[index];
			} else if (!(Client.clients[index] != null && Client.clients[index].getName().equals(address) == true )) {
				;
			}
			index++;
		}
		return result;

	}

	
	/*@
	  @ public normal_behavior
	  @ requires  msg != null;
	  @ ensures msg.isDelivered == true;
	  @ assignable msg.isDelivered;
	  @*/
	void deliver(Client client, Email msg) {
		msg.setEmailIsDelivered(true);
		addEmailToMailbox(client, msg);
	}
	
	/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures mailboxContainsEmail(client, msg);
	  @ assignable client.mailbox;
	  @*/
	private void addEmailToMailbox(Client client, Email msg) {
		Email[] tmp = new Email[client.mailbox.length+1];
		tmp[client.mailbox.length+1] = msg;
		for(int i = 0; i< client.mailbox.length; i++) {
			tmp[i] = client.mailbox[i];
		}
		client.mailbox = tmp;
	}
	
	/*@
	  @ public normal_behavior
	  @ requires client != null && client.mailbox != null;
	  @ ensures \result <==> (\exists int i; i>= 0 && i< client.mailbox.length; client.mailbox[i] == msg);
	  @ assignable \nothing;
	  @*/
	private /*@ pure*/ /*@ helper*/ boolean mailboxContainsEmail(Client client, Email msg) {
		/*@
		  @ loop_invariant (\forall int j; j>=0 && j<i; client.mailbox[j] != msg);
		  @ decreases client.mailbox.length -i;
		  @*/
		for(int i = 0; i< client.mailbox.length; i++) {
			if(client.mailbox[i].equals(msg)) {
				return true;
			}
		}
		return false;
	}
	
	/*@
	  @ public normal_behavior
	  @ requires client != null && msg != null;
	  @ ensures msg.from == client && msg.isDelivered == false ;
	  @ assignable msg.from, msg.isDelivered;
	  @*/
	void forward(Client client, Email msg) {
		msg.setEmailIsDelivered(false);
		outgoing(client, msg);
	}

	/*@
	  @ public normal_behavior
	  @ requires  client!=null && msg!=null;
	  @ ensures msg.isDelivered == true;
	  @ assignable msg.isDelivered;
	  @*/
	private void incoming__Base(Client client, Email msg) {
		deliver(client, msg);
	}
	
	/*@
	  @ public normal_behavior
	  @ requires  client!=null && msg!=null;
	  @ ensures msg.isDelivered == true;
	  @ ensures (client.forwardReceiver != null && client.forwardReceiver.name != null) ==> (msg.to == client.forwardReceiver && msg.from == client);
	  @ assignable msg.isDelivered, msg.to, msg.from;
	  @ diverges true;
	  @*/
	 private void incoming__Forward(Client client, Email msg) {
		deliver(client, msg);
		Client receiver = client.getForwardReceiver();
		if (receiver != null && receiver.getName() != null) {
			msg.setEmailTo(receiver.getName());
			forward(client, msg);
			incoming__Forward(receiver, msg);
		}
	}
//	private static void incoming__wrappee__Keys(Client client, Email msg) {
//		deliver(client, msg);
//	}

//	private static void incoming__wrappee__Encrypt(Client client, Email msg) {
//		//TODO add to encryptedMails if msg.isEncrypted()
//		incoming__wrappee__Keys(client, msg);
//	}


//	private static void incoming__wrappee__Sign(Client client, Email msg) {
//		incoming__wrappee__Encrypt(client, msg);
//		if (client.isAutoResponse()) {
//			autoRespond(client, msg);
//		}
//	}


//	private static void incoming__wrappee__Forward(Client client, Email msg) {
//		incoming__wrappee__Sign(client, msg);
//		Client receiver = client.getForwardReceiver();
//		if (receiver != null) {
//			msg.setEmailTo(receiver.getName());
//			forward(client, msg);
//			incoming(receiver, msg);
//		}
//	}


//	private static void incoming__wrappee__Verify(Client client, Email msg) {
//		verify(client, msg);
//		incoming__wrappee__Forward(client, msg);
//	}

	/*@
	  @ public normal_behavior
	  @ requires client != null && msg != null && \invariant_for(msg) && \invariant_for(client);
	  @ ensures msg.isDelivered;
	  @ ensures (client.privateKey != 0 && msg.isEncrypted && isKeyPairValid(msg.encryptionKey, client.privateKey)) ==> (!msg.isEncrypted && msg.encryptionKey == 0);
	  @ assignable msg.isEncrypted, msg.encryptionKey, msg.isDelivered;
	  @*/
	 private void incoming_Decrypt(Client client, Email msg) {
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

		deliver(client, msg);
	}
	
	/*@
	  @ public normal_behavior
	  @ requires client != null && msg != null;
	  @ ensures msg.isDelivered();
	  @ ensures (client.forwardReceiver != null && client.forwardReceiver.name != null) ==> (msg.to == client.forwardReceiver && msg.from == client);
	  @ ensures (client.privateKey != 0 && msg.isEncrypted && isKeyPairValid(msg.encryptionKey, client.privateKey)) ==> (!msg.isEncrypted && msg.encryptionKey == 0);
	  @ assignable msg.isEncrypted, msg.encryptionKey, msg.isDelivered, msg.to, msg.from;
	  @ diverges true;
	  @*/
	private void incoming_Decrypt_Forward(Client client, Email msg) {
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

		deliver(client, msg);
		Client receiver = client.getForwardReceiver();
		if (receiver != null && receiver.getName() != null) {
			msg.setEmailTo(receiver.getName());
			forward(client, msg);
			incoming_Decrypt_Forward(receiver, msg);
		}
	}
	
	



	private static void mail__wrappee__Keys(Client client, Email msg) {
	}


	private static void mail__wrappee__Addressbook  (Client client, Email msg) {
		//TODO add to encryptedMails if msg.isEncrypted(), else to unEncryptedMails
		mail__wrappee__Keys(client, msg);
	}


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


	public int sendEmail(Client sender, String receiverAddress,
			String subject, String body) {
		Email email = Email.createEmail(sender, receiverAddress, subject, body);
		outgoing(sender, email);
		Client receiver = Client.getClientByAdress(email.getEmailTo());
		if (receiver != null) {
			incoming__Base(receiver, email);
		} else {
			throw new IllegalArgumentException("Receiver " + receiverAddress + " Unknown");
		}
		return 0; // die Zeile kommt von mir
	}

//	@Override
//	public String toString() {
//		return name;
//	}

	public void setPrivateKey(int privateKey) {
		this.privateKey = privateKey;
	}


	 /*@
	   @ public normal_behavior
	   @ requires true;
	   @ ensures \result == privateKey;
	   @ assignable \nothing;
	   @*/
	public /*@helper*//*@pure*/ int getPrivateKey() {
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


	/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures ((publicKey == 0 || privateKey ==0) ==> !\result) && (!(publicKey == 0 || privateKey ==0) ==> \result == (privateKey == publicKey));
	  @ assignable \nothing;
	  @*/
	public /*@pure@*/  boolean isKeyPairValid(int publicKey, int privateKey) {
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

	 void autoRespond(Client client, Email msg) {
		Client sender = msg.getEmailFrom();
		msg.setEmailTo(sender.getName());
		outgoing(client, msg);
		incoming__Base(sender, msg);
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

	 void verify  (Client client, Email msg) {
		int pubkey = client.getKeyringPublicKeyByClient(msg.getEmailFrom());
		if (pubkey != 0 && isKeyPairValid(msg.getEmailSignKey(), pubkey)) {
			msg.setIsSignatureVerified(true);
		}
	}

	public void setForwardReceiver(Client forwardReceiver) {
		this.forwardReceiver = forwardReceiver;
	}

	/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures \result == forwardReceiver;
	  @ assignable \nothing;
	  @*/
	public /*@pure*//*@helper*/ Client getForwardReceiver() {
		return forwardReceiver;
	}


}
