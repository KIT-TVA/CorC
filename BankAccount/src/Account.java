public class Account {
	final int OVERDRAFT_LIMIT = 0;
	final int DAILY_LIMIT = -1000;

	final int INTEREST_RATE = 2;
	
	/*@ invariant balance >= OVERDRAFT_LIMIT; @*/
	public int balance = 0;
	public int interest = 0;
	/*@ invariant withdraw >= DAILY_LIMIT; @*/
	public int withdraw = 0;
	
	/*@
	 @ ensures balance == 0 && interest == 0 && withdraw == 0;
	 @ assignable balance, interest, withdraw;
	 @*/
	Account() {
	}
	
	/*@
	 @ public normal_behavior
	 @ ensures (!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) + x); 
	 @ assignable balance;
	 @*/
	boolean update(int x) {

	}

	/*@
	  @ public normal_behavior
	 @  ensures (!\result ==> balance == \old(balance)) && (\result ==> balance == \old(balance) - x);
	 @   assignable balance;
	 @*/
	boolean undoUpdate(int x) {
	}

	/*@
	  @public normal_behavior
	  @ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	  @ assignable \nothing;
	  @*/
	public int calculateInterest() {
	}
}