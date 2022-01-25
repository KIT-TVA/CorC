public interface ApplicationInter {
	
	public Account account = new Account();

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures this.account.withdraw == 0;
	 @ assignable this.account.withdraw;
	 @*/
	public default void nextDay() {
		this.account.withdraw = 0;
	}

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures true;
	 @ assignable \nothing;
	 @*/
	public default void nextYear() {
	}
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (this.account.balance >= 0 ==> \result >= 0) && (this.account.balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default int interestApplicationCalculate() {
		int result;
		result = this.account.balance * Account.INTEREST_RATE / 36500;
		return result; 

	}
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures this.account.interestCalculate() >= 0 ==> \result >= this.account.interest;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default int interestApplicationEstimated(int daysLeft) {
		int result;
		result = this.account.interest + daysLeft * this.account.interestCalculate();
		return result;
	}
	
	/*@
	@ normal_behavior
	@ requires this.account != null;
	@ ensures (this.account.balance >= 0 ==> this.account.interest >= \old(this.account.interest))  && (this.account.balance <= 0 ==> this.account.interest <= \old(this.account.interest)) && this.account.withdraw == 0;
	@ assignable this.account.interest, this.account.withdraw;
	@*/
	public /*@helper@*/ default void interestApplicationNextDay() {
		nextDay();
		this.account.interest += interestApplicationCalculate();

	}
	
	/*@
	@ normal_behavior
	@ requires this.account != null;
	@ ensures this.account.balance == \old(this.account.balance) + \old(this.account.interest) && this.account.interest == 0;
	@ assignable this.account.interest, this.account.balance;
	@*/
	public /*@helper@*/ default void interestApplicationNextYear() {
		nextYear();
		this.account.balance += this.account.interest;
		this.account.interest = 0;
	}

}