public class ClientInter{
	
	public Client client = null;

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/   Client createClientResultOld(String name) {
		Client result = null;
		result = new Client();
		result.constructClient(Client.clientCounter++, name);
		Client.clients[result.getId()] = result;
		return result;
	}

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == Client.old_clientCounter && \result.getName() == name && Client.clientCounter == Client.old_clientCounter + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/   Client createClientResult(String name) {
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
	public /*@helper@*/   Client getClientById(int id) {
		Client result = null;
		result = Client.clients[id];
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.client.id;
	@ assignable \nothing;
	@*/
	public /*@helper@*/   int getId() {
		int result = 0;
		result = this.client.id;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures  \result == this.client.name;
	@ assignable \nothing;
	@*/
	public   String getName() {
		return this.client.name;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.client.name;
	@ assignable \nothing;
	@*/
	public /*@helper@*/   String getNameWithKeyword() {
		String result = null;
		result = this.client.name;
		return result;
	}

	/*@
	@ normal_behavior
	@ requires client != null && msg != null;
	@ ensures msg.from == client;
	@*/
	public /*@helper@*/   void outgoing(Client client, Email msg) {
		msg.setEmailFrom(client);

	}

	/*@
	@ normal_behavior
	@ requires Client.clients != null&& Client.clientCounter >= 0 && Client.clients != null;
	@ ensures (\forall int k;((k>=0 && k<Client.clients.length) ==> Client.clients[k] == null)) && Client.clientCounter == 1;
	@ assignable \nothing;
	@*/
	public /*@helper@*/   void resetClients() {
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
	@ ensures (idx >= 0 && namex != null) ==> (this.client.id == idx && this.client.name == namex);
	@ assignable this.client.id,this.client.name;
	@*/
	public /*@helper@*/   void constructClient(int idx, String namex) {
		this.client.id = idx;
		this.client.name = namex;

	}

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0;
	@ ensures \result != null && \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@*/
	public /*@helper@*/   Client createClient(String name) {
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
	public /*@helper@*/   Client getClientByAdress(String address) {
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