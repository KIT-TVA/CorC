import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class SecureGUI implements GUIInterface {
	
	@SecurityLevel("highOne") Database databaseOne = new Database("one");
	@SecurityLevel("highTwo") Database databaseTwo = new Database("two");
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void printEntry(@SecurityLevel("high") @MutationModifier(MDF.IMMUTABLE) String entry) {
		System.out.println(entry);
	}
}
