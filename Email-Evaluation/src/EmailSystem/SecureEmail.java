package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class SecureEmail implements IEmail {
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void printMail(@SecurityLevel("high") @MutationModifier(MDF.IMMUTABLE) Email entry) {
		System.out.println(entry);
	}
}
