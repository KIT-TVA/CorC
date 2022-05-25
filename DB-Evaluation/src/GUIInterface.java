import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public interface GUIInterface {

	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void printEntry(@SecurityLevel("low") String entry);
}
