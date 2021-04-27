public class Account {
	final static int OVERDRAFT_LIMIT = 0;
	final static int DAILY_LIMIT = -1000;

	final static int INTEREST_RATE = 2;
	
	/*@ invariant balance >= OVERDRAFT_LIMIT; @*/
	public static int balance = 0;
	public static int interest = 0;
	/*@ invariant withdraw >= DAILY_LIMIT; @*/
	public static int withdraw = 0;
	
	/*@
	 @ ensures balance == 0 && interest == 0 && withdraw == 0;
	 @ assignable balance, interest, withdraw;
	 @*/
	Account() {
	}

	/*@
	  @public normal_behavior
	  @ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	  @ assignable \nothing;
	  @*/
	public static int calculateInterest() {
	}
	
	
	
}