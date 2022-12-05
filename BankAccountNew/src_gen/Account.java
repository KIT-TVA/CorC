public class Account {

    public int OVERDRAFT_LIMIT;
    public int balance;
    public int withdraw;
    public int DAILY_LIMIT;
    public int interest;

    /*@ invariant balance >= OVERDRAFT_LIMIT; @*/


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + -x);
	@ assignable \nothing;
	@*/
	public boolean original_undoUpdate(int x) {
		int newBalance;
		boolean ret;
		newBalance = balance - x;
		if (newBalance < this.OVERDRAFT_LIMIT) {
			ret = false;
		} else if (newBalance >= this.OVERDRAFT_LIMIT) {
			balance = newBalance;
			ret = true;
		}
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + -x) && (\result == false ==> withdraw == \old(withdraw)) &&  (\result == true ==> withdraw >= \old(withdraw));
	@ assignable \nothing;
	@*/
	public boolean undoUpdate(int x) {
		int newWithdraw;
		boolean ret;
		newWithdraw = withdraw;
		if (x < 0) {
			newWithdraw -= x;
			if (newWithdraw < DAILY_LIMIT) {
				ret = false;
				return ret;
			} else if (newWithdraw >= DAILY_LIMIT) {
				;
			}
		} else if (x >= 0) {
			;
		}
		ret =original_undoUpdate(x);
		if (ret == false) {
			ret = false;
			return ret;
		} else if (ret == true) {
			withdraw = newWithdraw;
			ret = true;
			return ret;
		}

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + x);
	@ assignable \nothing;
	@*/
	public boolean original_update(int x) {
		int newBalance;
		boolean ret;
		newBalance = balance + x;
		if (newBalance < this.OVERDRAFT_LIMIT) {
			ret = false;
		} else if (newBalance >= this.OVERDRAFT_LIMIT) {
			balance = newBalance;
			ret = true;
		}
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + x)  && (\result == false ==> (withdraw == \old(withdraw))) && (\result == true ==> (withdraw <= \old(withdraw)));
	@ assignable \nothing;
	@*/
	public boolean update(int x) {
		int newWithdraw;
		boolean ret;
		newWithdraw = withdraw;
		if (x < 0) {
			newWithdraw += x;
			if (newWithdraw < DAILY_LIMIT) {
				ret = false;
				return ret;
			} else if (newWithdraw >= DAILY_LIMIT) {
				;
			}
		} else if (x >= 0) {
			;
		}
		ret = original_update(x);
		if (ret == false) {
			ret = false;
			return ret;
		} else if (ret == true) {
			withdraw = newWithdraw;
			ret = true;
			return ret;
		}

	}

// Code from C:/Users/mko/Documents/ISF/0_partialproofs/CorC/BankAccountNew/src/Account_helper.java
	final static int INTEREST_RATE = 2;
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculate() {
		int result;
		result = balance * INTEREST_RATE / 36500;
		return result; 
	}
// End of code from C:/Users/mko/Documents/ISF/0_partialproofs/CorC/BankAccountNew/src/Account_helper.java
}