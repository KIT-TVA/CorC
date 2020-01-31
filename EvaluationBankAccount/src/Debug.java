public class Debug {
	/*@
	@ normal_behavior
	@ requires @ requires true;
;
	@ ensures @ ensures (result == false ==> balance == old_balance) && (result == true ==> balance == old_balance + x);
;
	@*/
	public static void BankAccountUpdate(int OVERDRAFT_LIMIT, int balance, int old_balance, int newBalance, boolean result, int x) {
		newBalance = balance+x;
		if (newBalance < OVERDRAFT_LIMIT) {
			result = false;
		} else if (newBalance >= OVERDRAFT_LIMIT) {
			balance = newBalance;
			result = true;
		}

	}
}