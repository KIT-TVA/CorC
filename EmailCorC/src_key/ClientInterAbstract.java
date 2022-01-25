public class ClientInterAbstract{
	
	public Client client = null;

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Client createClientResultOld(String name);

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0 && name != null && Client.clients != null;
	@ ensures \result != null && \result.getId() == Client.old_clientCounter && \result.getName() == name && Client.clientCounter == Client.old_clientCounter + 1&& Client.clients[\result.getId()] == \result;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Client createClientResult(String name) ;

	/*@
	@ normal_behavior
	@ requires Client.clients != null && id >= 0 && id < Client.clients.length;
	@ ensures \result == Client.clients[id];
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Client getClientById(int id);

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.client.id;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getId();

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures  \result == this.client.name;
	@ assignable \nothing;
	@*/
	public String getName();

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.client.name;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getNameWithKeyword();

	/*@
	@ normal_behavior
	@ requires client != null && msg != null;
	@ ensures msg.from == client;
	@*/
	public /*@helper@*/ void outgoing(Client client, Email msg);
	
	/*@
	@ normal_behavior
	@ requires Client.clients != null&& Client.clientCounter >= 0 && Client.clients != null;
	@ ensures (\forall int k;((k>=0 && k<Client.clients.length) ==> Client.clients[k] == null)) && Client.clientCounter == 1;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void resetClients();

	/*@
	@ normal_behavior
	@ requires true && idx >= 0 && namex != null;
	@ ensures (idx >= 0 && namex != null) ==> (this.client.id == idx && this.client.name == namex);
	@ assignable this.client.id,this.client.name;
	@*/
	public /*@helper@*/ void constructClient(int idx, String namex);

	/*@
	@ normal_behavior
	@ requires name != null && Client.clients != null&& Client.clientCounter < Client.clients.length&& Client.clientCounter >= 0;
	@ ensures \result != null && \result != null && \result.getId() == \old(Client.clientCounter) && \result.getName() == name && Client.clientCounter == \old(Client.clientCounter) + 1&& Client.clients[\result.getId()] == \result;
	@*/
	public /*@helper@*/ Client createClient(String name);

	/*@
	@ normal_behavior
	@ requires Client.clients != null && address != null;
	@ ensures (\exists int k;((k>=0 && k<Client.clients.length  && Client.clients[k].getName() != null && Client.clients[k].getName().equals(address) == true) ==> \result == Client.clients[k]));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Client getClientByAdress(String address);
}