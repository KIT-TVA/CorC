package JDK;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class String {
	
	@SecurityLevel("low")
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	boolean equals(@SecurityLevel("low") String string) {
		return false;
	}
}
