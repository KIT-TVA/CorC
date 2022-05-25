package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class System {
	
	@SecurityLevel("low")
	@MutationModifier(MDF.IMMUTABLE)
	public static Printer out;
	
	class Printer {
		
		@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
		public void println(@SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) final Object string) {
			
		}
	}
}
