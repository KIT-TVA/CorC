public class  AccountInterAbstract {
	public int getOVERDRAFT_LIMIT();

	public int getINTEREST_RATE();
	public int getDAILY_LIMIT();
	public int getBalance();
	public int setBalance(int balance);
	public int getWithdraw();
	public int setWithdraw(int withdraw);
	public int getInterest();
	public boolean getLock();
	public void setLock(boolean lock);
	
	/*@
	 @ public normal_behavior
	 @ requires true;
	 @ ensures (!\result == true ==> getBalance() == \old(getBalance())) 
	 @   && (\result == true ==> getBalance() == \old(getBalance()) + x && getBalance()>=getOVERDRAFT_LIMIT()); 
	 @*/
	public /*@helper@*/ boolean update(int x);

	/*@
	 @ public normal_behavior
	 @  requires true;
	 @  ensures (!\result ==> getBalance() == \old(getBalance())) 
	 @   && (\old(getBalance()) - x >= getOVERDRAFT_LIMIT() ==> getBalance() == \old(getBalance()) - x && \result == true);
	 @*/
	public /*@helper@*/  boolean undoUpdate(int x);
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (getBalance() >= 0 ==> \result >= 0) && (getBalance() <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculate();
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures interestCalculate() >= 0 ==> \result >= getInterest();
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestEstimated(int daysLeft);

	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures this.getLock() == true;
	 @*/
	public /*@helper@*/ void lock();
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures !this.getLock() == true;
	 @*/
	public /*@helper@*/ void unLock();
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures \result == this.getLock();
	 @ assignable \nothing;
	 @*/
	public /*@helper@*/ boolean isLocked();
	
	/*@
	  @ normal_behavior
	  @ requires destination != null && source != null && source != destination;
	  @ ensures \result == true ==> source.isLocked() == true && destination.isLocked() == true;
	  @ assignable \nothing;
	 @*/
	public /*@helper@*/ boolean lockBoth(AccountInter source, AccountInter destination);
	
	/*@ requires destination != null && source != null && source != destination;
	  @ ensures \result == true ==> (\old(destination.getBalance()) + amount == destination.getBalance())
	  @ && \result == true ==> (\old(source.getBalance()) - amount == source.getBalance())
	  @ && !\result == true ==> (\old(destination.getBalance()) == destination.getBalance())
	  @ && !\result == true ==> (\old(source.getBalance()) == source.getBalance());
	  @ assignable \nothing;
	  @*/
	public /*@helper@*/ boolean transfer(AccountInter source, AccountInter destination, int amount);

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> getBalance() == \old(getBalance())) && (\result == true ==> getBalance() == \old(getBalance()) - x);
		@*/
		public /*@helper@*/ boolean bankAccountUndoUpdate(int x);

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> getBalance() == \old(getBalance())) && (\result == true ==> getBalance() == \old(getBalance()) + x);
		@*/
		public boolean bankAccountUpdate(int x);


		/*@
		@ normal_behavior
		@ requires amount > 0;
		@ ensures getBalance() >= amount <==> \result == true;
		@ assignable \nothing;
		@*/
		public boolean creditAccountCredit(int amount);

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> (getWithdraw() == \old(getWithdraw()) &&  getBalance() == \old(getBalance()))) && (\result == true ==> (getWithdraw() <= \old(getWithdraw()) ) &&  getBalance() == \old(getBalance()) + x);
		@*/
		public boolean dailyAccountUpdate(int x);

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures \result == true ==> source.isLocked() == true && destination.isLocked() == true;
		@ assignable \everything;
		@*/
		public boolean transactionAccountLock(AccountInter source, AccountInter destination);

		/*@
		@ normal_behavior
		@ requires source != destination;
		@ ensures (\result == true ==> (\old(destination.getBalance()) + amount == destination.getBalance())) && (\result == true ==> (\old(source.getBalance()) - amount == source.getBalance())) 
		@ 	&& (\result == false ==> (\old(destination.getBalance()) == destination.getBalance())) && (\result == false ==> (\old(source.getBalance()) == source.getBalance()));
		@ assignable \everything;
		@*/
		public boolean transactionAccountTransfer2(AccountInter source, AccountInter destination, int amount);
	}