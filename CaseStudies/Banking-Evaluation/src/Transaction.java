import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Transaction {
	/*@ 
	 requires destination != null && source != null;
	  requires source != destination;
	  ensures \result ==> (\old(destination.balance) + amount == destination.balance);
	  ensures \result ==> (\old(source.balance) - amount == source.balance);
	  ensures !\result ==> (\old(destination.balance) == destination.balance);
	  ensures !\result ==> (\old(source.balance) == source.balance);
	  assignable \nothing; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("high") boolean transfer(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account source, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account destination, 
			@SecurityLevel("low") int amount) {
		if (!lock(source, destination)) return false;
//		try {
			if (amount <= 0) {
				return false;
			}
			if (!source.update(amount * -1)) {
				return false;
			}
			@SecurityLevel("high") boolean result = !destination.update(amount);
			undo(source, amount, declassify(result));
			if (result) {
				return false;
			} else {
				return true;
			}
//		} finally {
//			source.unLock();
//			destination.unLock();
//		}
	}

	private static @SecurityLevel("low") boolean declassify(@SecurityLevel("high") boolean result) {
		return result;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	private void undo(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account source, 
			@SecurityLevel("low") int amount,
			@SecurityLevel("low") boolean result) {
		if (result) {
			source.undoUpdate(amount * -1);
		}
	}

	/*@ 
	 requires destination != null && source != null;
	  requires source != destination;
	  ensures \result ==> source.isLocked() && destination.isLocked();
	  assignable \nothing; @*/
	private static @SecurityLevel("low") boolean lock(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account source, 
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account destination) {
		if (source.isLocked()) return false;
		if (destination.isLocked()) return false;
		source.lock();
		destination.lock();
		return true;
	}


}
