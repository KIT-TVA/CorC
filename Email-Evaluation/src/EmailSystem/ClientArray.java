package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class ClientArray {
	
	private Client[] clients;
	
	public ClientArray(Client[] clients) {
		this.clients = clients;
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void add(
			@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) int index,
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client client) {
		clients[index] = client; 
	}
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public Client get(@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) int index) {
		return clients[index]; 
	}
	
	@SecurityLevel("low")
	@MutationModifier(MDF.IMMUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public int size() {
		return clients.length; 
	}
}
