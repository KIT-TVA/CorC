package JDK;

import Paycard.LogRecord;
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
	public boolean isEmpty() {
		return true;
	};
	
	@SecurityLevel("high")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "high", MDF = MDF.MUTABLE)
	public LogRecord get(@SecurityLevel("high") int i) {
		return null;
	};
	
	
	@SecurityLevel("low")
	@MutationModifier(MDF.MUTABLE)
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void add(@SecurityLevel("low") LogRecord record) {
		return;
	};
}
