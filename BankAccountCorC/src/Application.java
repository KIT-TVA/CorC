public class Application {
	
	public Account account = new Account();

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures account.withdraw == 0;
	 @ assignable account.withdraw;
	 @*/
	public void nextDay() {
		account.withdraw = 0;
	}

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures true;
	 @ assignable \nothing;
	 @*/
	public void nextYear() {
	}
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (account.balance >= 0 ==> \result >= 0) && (account.balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestApplicationCalculate() {
		int result;
		result = account.balance * Account.INTEREST_RATE / 36500;
		return result; 

	}
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures account.interestCalculate() >= 0 ==> \result >= account.interest;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestApplicationEstimated(int daysLeft) {
		int result;
		result = account.interest + daysLeft * account.interestCalculate();
		return result;
	}
	
	/*@
	@ normal_behavior
	@ requires account != null;
	@ ensures (account.balance >= 0 ==> account.interest >= \old(account.interest))  && (account.balance <= 0 ==> account.interest <= \old(account.interest)) && account.withdraw == 0;
	@ assignable account.interest, account.withdraw;
	@*/
	public /*@helper@*/ void interestApplicationNextDay() {
		nextDay();
		account.interest += interestApplicationCalculate();

	}
	
	/*@
	@ normal_behavior
	@ requires account != null;
	@ ensures account.balance == \old(account.balance) + \old(account.interest) && account.interest == 0;
	@ assignable account.interest, account.balance;
	@*/
	public /*@helper@*/ void interestApplicationNextYear() {
		nextYear();
		account.balance += account.interest;
		account.interest = 0;
	}

}