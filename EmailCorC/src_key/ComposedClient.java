public class ComposedClient {

public Client client = null;


	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public Client createClientResultOld(String name) {
		Client result = null;
		result = new Client();
		result.constructClient(Client.clientCounter++,name);
		Client.clients[result.getId()] = result;
		return result;
	}


	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == Client.old_clientCounter && \result.getName() == name && Client.clientCounter == Client.old_clientCounter + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public Client createClientResult(String name) {
		Client result = null;
		Client.old_clientCounter = Client.clientCounter;
		result = new Client();
		result.constructClient(Client.clientCounter++,name);
		Client.clients[result.getId()] = result;
		return result;
	}


	/*@
	@ normal_behavior
	@ requires Client.clients != null && id >= 0 && id < Client.clients.length;
	@ ensures \result == Client.clients[id];
	@ assignable \nothing;
	@*/
	public Client getClientById(int id) {
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
	public int getId() {
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
	public String getName() {
		return this.client.name;
	}


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.client.name;
	@ assignable \nothing;
	@*/
	public String getNameWithKeyword() {
		String result = null;
		result = this.client.name;
		return result;
	}


	/*@
	@ normal_behavior
	@ requires client != null && msg != null;
	@ ensures msg.from == client;
	@*/
	public void outgoing(Client client,Email msg) {
		msg.setEmailFrom(client);
	}


	/*@
	@ normal_behavior
	@ requires Client.clients != null&& Client.clientCounter >= 0 && Client.clients != null;
	@ ensures (\forall int k;((k>=0 && k<Client.clients.length) ==> Client.clients[k] == null)) && Client.clientCounter == 1;
	@ assignable \nothing;
	@*/
	public void resetClients() {
		int index = 0;
		index = 0;
		Client.clientCounter = 1;
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
	public void constructClient(int idx,String namex) {
		this.client.id = idx;
		this.client.name = namex;
	}


	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0;
	@ ensures \result != null && \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@*/
	public Client createClient(String name) {
		Client result = null;
		result = new Client();
		result.constructClient(Client.clientCounter++,name);
		Client.clients[result.getId()] = result;
		return result;
	}


	/*@
	@ normal_behavior
	@ requires Client.clients != null && address != null;
	@ ensures (\exists int k;((k>=0 && k<Client.clients.length  && Client.clients[k].getName() != null && Client.clients[k].getName().equals(address) == true) ==> \result == Client.clients[k]));
	@ assignable \nothing;
	@*/
	public Client getClientByAdress(String address) {
		int index = 0;
		Client result = null;
		index = 0;
		result = null;
		while (index < Client.clients.length) {
			if (Client.clients[index] != null&&Client.clients[index].getName().equals(address) == true) {
				result = Client.clients[index];
			}else if (!(Client.clients[index] != null&&Client.clients[index].getName().equals(address) == true)) {
				;
			}
			index++;
		}
		return result;
	}
}