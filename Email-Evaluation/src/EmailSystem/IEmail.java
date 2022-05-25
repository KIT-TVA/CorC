package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public interface IEmail {
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void printMail(@SecurityLevel("low") Email entry);
}
