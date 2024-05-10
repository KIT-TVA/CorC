import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public  class Account {
	@SecurityLevel("high") int OVERDRAFT_LIMIT  = -5000;
	
	/*@ invariant withdraw >= DAILY_LIMIT; @*/

	@SecurityLevel("high")  static int DAILY_LIMIT = -1000;

	@SecurityLevel("low") int withdraw = 0;

	@SecurityLevel("low") static int INTEREST_RATE = 2;

	@SecurityLevel("high") @MutationModifier(MDF.IMMUTABLE) int interest = 0;

	/*@ invariant balance >= OVERDRAFT_LIMIT; @*/

	@SecurityLevel("high") int balance = 0;

	/*@ 
	 ensures balance == 0;
	assignable balance; @*/
	Account() {
	}

	/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) + x); 
	assignable balance; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	private @SecurityLevel("high") boolean updateBalance (@SecurityLevel("low") int x) {
		@SecurityLevel("high") int newBalance = balance + x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}

	/*@ 
	 ensures (!\result ==> withdraw == \old(withdraw)) 
	  && (\result ==> withdraw <= \old(withdraw));
	assignable withdraw; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("high") boolean update(@SecurityLevel("low") int x) {
		@SecurityLevel("low") int newWithdraw = withdraw;
		if (x < 0)  {
			newWithdraw += x;
			if (newWithdraw < DAILY_LIMIT) 
				return false;
		}
		if (!updateBalance(x))
			return false;
		withdraw = newWithdraw;
		return true;
	}

	/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) - x);
	assignable balance; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	private @SecurityLevel("high") boolean undoUpdateBalance (@SecurityLevel("low") int x) {
		@SecurityLevel("high") int newBalance = balance - x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}

	/*@ 
	 ensures (!\result ==> withdraw == \old(withdraw)) 
	  && (\result ==> withdraw >= \old(withdraw));
	assignable withdraw; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("high") boolean undoUpdate(@SecurityLevel("low") int x) {
		@SecurityLevel("low") int newWithdraw = withdraw;
		if (x < 0)  {
			newWithdraw -= x;
			if (newWithdraw < DAILY_LIMIT) 
				return false;
		}
		if (!undoUpdateBalance(x))
			return false;
		withdraw = newWithdraw;
		return true;
	}

	/*@ 
	 ensures (balance >= 0 ==> \result >= 0) 
	   && (balance <= 0 ==> \result <= 0); @*/
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	 public /*@pure@*/  @SecurityLevel("high") int calculateInterest() {
		return balance * INTEREST_RATE / 36500;
	}

	/*@ 
	 requires daysLeft >= 0;
	ensures calculateInterest() >= 0 ==> \result >= interest; @*/
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("high") int estimatedInterest(@SecurityLevel("low") int daysLeft) {
		return interest + daysLeft * calculateInterest();
	}

	/*@ 
	 requires amount >= 0;
	ensures balance >= amount <==> \result;
	assignable \nothing; @*/
	@MethodReceiver(SL = "high", MDF = MDF.READ)
	public @SecurityLevel("high") boolean credit(@SecurityLevel("low") int amount) {
		return balance >= amount;
	}

	private @SecurityLevel("low") boolean lock = false;

	/*@ 
	 ensures this.lock;
	assignable lock; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void lock() {
		lock = true;
	}

	/*@ 
	 ensures !this.lock;
	assignable lock; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void unLock() {
		lock = false;
	}

	/*@ 
	 ensures \result == this. lock;
	assignable \nothing; @*/
	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") boolean isLocked() {
		return lock;
	}


}
