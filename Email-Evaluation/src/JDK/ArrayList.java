package JDK;

import EmailSystem.AddressBookEntry;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class ArrayList {
	
	public ArrayList () {
		
	}
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public int size() {
		return 0;
	};
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public int indexOf(@SecurityLevel("low") AddressBookEntry e) {
		return 0;
	};
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public boolean isEmpty() {
		return true;
	};
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public String get(@SecurityLevel("low") int i) {
		return null;
	};
}
