package JDK;

import java.util.List;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Collections {
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	List<String> emptyList() {
		return java.util.Collections.emptyList();
	}
}
