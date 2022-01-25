public class Client{

	 /*@spec_public@*/ String name;
	 /*@spec_public@*/ int id;
	 /*@spec_public@*/ static Client[] clients;
	 /*@spec_public@*/ static int clientCounter;
	 /*@spec_public@*/ static int old_clientCounter;

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
	@ ensures  \result == this.name;
	@ assignable \nothing;
	@*/
	public String getName() {
		return this.name;

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
	@*/
	public /*@helper@*/ static void outgoing(Client client, Email msg) {
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
	@ requires Client.clients != null && address != null;
	@ ensures (\exists int k;((k>=0 && k<Client.clients.length  && Client.clients[k].getName() != null && Client.clients[k].getName().equals(address) == true) ==> \result == Client.clients[k]));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ static Client getClientByAdress(String address) {
		int index = 0;
		Client result = null;
		index = 0;
		result = null;
		//@ loop_invariant Client.clients != null && index >= 0 &&(\exists int k;((k>=0 && k<index && Client.clients[k].getName() != null&& Client.clients[k].getName().equals(address) == true) ==> result == Client.clients[k]));
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
}