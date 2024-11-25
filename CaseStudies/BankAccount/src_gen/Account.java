public class Account {

    public int OVERDRAFT_LIMIT;
    public int balance;
    public int withdrawDaily;
    public int DAILY_LIMIT;
    public int HOURLY_LIMIT;
    public int withdrawHourly;
    public int interest;

    /*@ invariant balance >= OVERDRAFT_LIMIT; @*/



	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	int /*@helper pure @*/ length(int[] arr) {return arr.length;}


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + -x);
	@ assignable balance;
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
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + -x)  && (\result == false ==> withdrawDaily == \old(withdrawDaily)) &&  (\result == true ==> withdrawDaily >= \old(withdrawDaily)) && (\result == false ==> withdrawHourly == \old(withdrawHourly)) &&  (\result == true ==> withdrawHourly >= \old(withdrawHourly));
	@ assignable withdrawDaily,withdrawHourly;
	@*/
	public boolean undoUpdate(int x) {
		int newWithdrawDaily;
		int newWithdrawHourly;
		boolean ret;
		newWithdrawDaily = withdrawDaily;
		newWithdrawHourly = withdrawHourly;
		if (x < 0) {
			newWithdrawDaily -= x;
			newWithdrawHourly -= x;
			if (!newWithdrawDaily >= DAILY_LIMIT && newWithdrawHourly >= HOURLY_LIMIT) {
				ret = false;
				return ret;
			} else if (newWithdrawDaily >= DAILY_LIMIT && newWithdrawHourly >= HOURLY_LIMIT) {
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
			withdrawDaily = newWithdrawDaily;
			withdrawHourly = newWithdrawHourly;
			ret = true;
			return ret;
		}

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + x);
	@ assignable balance;
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
    @ public normal_behavior
    @ requires true;
    @ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + x)  && (\result == false ==> (withdrawDaily == \old(withdrawDaily))) && (\result == true ==> (withdrawDaily <= \old(withdrawDaily))) && balance >= OVERDRAFT_LIMIT;
    @ assignable withdrawDaily,withdrawHourly;
    @*/
	public boolean update(int x) {
		int newWithdrawDaily;
		int newWithdrawHourly;
		boolean ret;
		newWithdrawDaily = withdrawDaily;
		newWithdrawHourly = withdrawHourly;
		if (x < 0) {
			newWithdrawDaily += x;
			newWithdrawHourly += x;
			if (!newWithdrawDaily >= DAILY_LIMIT && newWithdrawHourly >= HOURLY_LIMIT) {
				ret = false;
				return ret;
			} else if (newWithdrawDaily >= DAILY_LIMIT && newWithdrawHourly >= HOURLY_LIMIT) {
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
			withdrawDaily = newWithdrawDaily;
			withdrawHourly = newWithdrawHourly;
			ret = true;
			return ret;
		}

	}

// Code from C:/Important/Work/CorC/BankAccount/src/Account_helper.java
	final static int INTEREST_RATE = 2;
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculateDaily() {
		int result;
		result = balance * INTEREST_RATE / 36500;
		return result; 
	}
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculateHourly() {
		int result;
		result = balance * INTEREST_RATE / 36500;
		result = result / 24;
		return result; 
	}
// End of code from C:/Important/Work/CorC/BankAccount/src/Account_helper.java
}
