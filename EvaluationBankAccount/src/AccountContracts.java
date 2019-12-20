public class AccountContracts {


	/*@ 
	  @ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	  @ 
	  @*/
	public /*@ pure @*/static int calculateInterest(int balance) {
	}
	
	/*@
	  @ requires true;
	  @ ensures true;
	  @ assignable \nothing;
	  @*/
	public static void nextYear_BankAccount() {
		
	}
	
	/*@
	 @ public normal_behavior
	 @ ensures (!\result ==> a.balance == \old(a.balance)) && (\result ==> a.balance == \old(a.balance) + x); 
	 @ assignable a.balance;
	 @*/
	public static boolean update(int x, Account a) {
	}
	
}