public class ApplicationInterAbstract {
	
	public Account account = new Account();

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures this.account.withdraw == 0;
	 @ assignable this.account.withdraw;
	 @*/
	public void nextDay();

	/*@
	  @ normal_behavior
	 @ requires true;
	 @ ensures true;
	 @ assignable \nothing;
	 @*/
	public void nextYear();
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (this.account.balance >= 0 ==> \result >= 0) && (this.account.balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestApplicationCalculate();
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures this.account.interestCalculate() >= 0 ==> \result >= this.account.interest;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestApplicationEstimated(int daysLeft);
	
	/*@
	@ normal_behavior
	@ requires this.account != null;
	@ ensures (this.account.balance >= 0 ==> this.account.interest >= \old(this.account.interest))  && (this.account.balance <= 0 ==> this.account.interest <= \old(this.account.interest)) && this.account.withdraw == 0;
	@ assignable this.account.interest, this.account.withdraw;
	@*/
	public /*@helper@*/ void interestApplicationNextDay();
	
	/*@
	@ normal_behavior
	@ requires this.account != null;
	@ ensures this.account.balance == \old(this.account.balance) + \old(this.account.interest) && this.account.interest == 0;
	@ assignable this.account.interest, this.account.balance;
	@*/
	public /*@helper@*/ void interestApplicationNextYear();

}