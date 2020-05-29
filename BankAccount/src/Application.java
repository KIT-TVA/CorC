public class Application {
	/*@ invariant account != null; @*/
	public static Account account = new Account();

	/*@
	  @ public normal_behavior
	 @ requires true;
	 @ ensures account.withdraw == 0 && \result == true;
	 @ assignable account.withdraw;
	 @*/
	static boolean nextDay_Daily() {
		account.withdraw = 0;
	}
	
	/*@
	  @ public normal_behavior
	 @ requires true;
	 @ ensures \result == false && account.withdraw == \old(account).withdraw;
	 @ assignable \nothing;
	 @*/
	static boolean nextDay_BankAccount() {
	}

	/*@
	 @ requires true;
	 @ ensures true;
	 @ assignable \nothing;
	 @*/
	void nextYear() {
	}

}