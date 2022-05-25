package EmailSystem;

import java.util.ArrayList;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class AddressBookEntry {
	@SecurityLevel("low") String alias;

	@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<String> receivers;

	public AddressBookEntry(
			@SecurityLevel("low") String alias, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<String> receivers
			) {
		super();
		this.alias = alias;
		this.receivers = receivers;
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void addReceiver(@SecurityLevel("low") String address) { 
		receivers.add(address); 
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") String getAlias() {
		return alias;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) ArrayList<String> getReceivers() {
		return receivers;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	@SecurityLevel("low") @MutationModifier(MDF.MUTABLE)
	public boolean equals(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) String arg0) {
		return super.equals(arg0);
	}	
}
