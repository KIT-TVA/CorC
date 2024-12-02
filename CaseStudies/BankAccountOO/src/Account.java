public class Account {
	
	public int OVERDRAFT_LIMIT = 0;
	public static int INTEREST_RATE = 2;
	public static int DAILY_LIMIT = -1000;
	public int balance = 0;
	public int withdraw = 0;
	public int interest = 0;
	public boolean lock = false;
	
	/*@
	  @ requires true;
	 @ ensures balance == 0;
	 @ assignable balance;
	 @*/
	Account() {
	}
	
	/*@
	 @ public normal_behavior
	 @ requires true;
	 @ ensures (!\result ==> balance == \old(balance)) 
	 @   && (\result ==> balance == \old(balance) + x && balance>=OVERDRAFT_LIMIT); 
	 @ assignable balance;
	 @*/
	public /*@helper@*/ boolean update(int x) {
		int newBalance = balance + x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}

	/*@
	 @ public normal_behavior
	 @  requires true;
	 @  ensures (!\result ==> balance == \old(balance)) 
	 @   && (\old(balance) - x >= OVERDRAFT_LIMIT ==> balance == \old(balance) - x && \result);
	 @ assignable balance;
	 @*/
	public /*@helper@*/ boolean undoUpdate(int x) {
		int newBalance = balance - x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		balance = newBalance;
		return true;
	}
	
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
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures interestCalculate() >= 0 ==> \result >= interest;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int interestEstimated(int daysLeft) {
		int result;
		result = interest + daysLeft * interestCalculate();
		return result;
	}

	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures this.lock == true;
	 @ assignable lock;
	 @*/
	public /*@helper@*/ void lock() {
		lock = true;
	}
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures !this.lock;
	 @ assignable lock;
	 @*/
	public /*@helper@*/ void unLock() {
		lock = false;
	}
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures \result == this.lock;
	 @ assignable \nothing;
	 @*/
	public /*@helper@*/ boolean isLocked() {
		return lock;
	}
	
	/*@
	  @ normal_behavior
	  @ requires destination != null && source != null && source != destination;
	  @ ensures \result ==> source.isLocked() && destination.isLocked();
	  @ assignable \nothing;
	 @*/
	public /*@helper@*/ static synchronized boolean lock(Account source, Account destination) {
		if (source.isLocked()) return false;
		if (destination.isLocked()) return false;
		source.lock();
		destination.lock();
		return true;
	}
	
	/*@ requires destination != null && source != null && source != destination;
	  @ ensures \result ==> (\old(destination.balance) + amount == destination.balance)
	  @ && \result ==> (\old(source.balance) - amount == source.balance)
	  @ && !\result ==> (\old(destination.balance) == destination.balance)
	  @ && !\result ==> (\old(source.balance) == source.balance);
	  @ assignable \nothing;
	  @*/
	public /*@helper@*/ boolean transfer(Account source, Account destination, int amount) {
		if (!lock(source, destination)) return false;
		try {
			if (amount <= 0) {
				return false;
			}
			if (!source.update(amount * -1)) {
				return false;
			}
			if (!destination.update(amount)) {
				source.undoUpdate(amount * -1);
				return false;
			}
			return true;
		} finally {
			source.unLock();
			destination.unLock();
		}
	}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) - x);
		@ assignable balance;
		@*/
		public /*@helper@*/ boolean bankAccountUndoUpdate(int x) {
			int newBalance;
			boolean result;
			newBalance = balance - x;
			result = false;
			if (newBalance < this.OVERDRAFT_LIMIT) {
				result = false;
			} else if (newBalance >= this.OVERDRAFT_LIMIT) {
				balance = newBalance;
				result = true;
			}
			return result;

		}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> balance == \old(balance)) && (\result == true ==> balance == \old(balance) + x);
		@ assignable balance;
		@*/
		public boolean bankAccountUpdate(int x) {
			int newBalance;
			boolean result;
			result = false;
			newBalance = balance + x;
			if (newBalance < this.OVERDRAFT_LIMIT) {
				result = false;
			} else if (newBalance >= this.OVERDRAFT_LIMIT) {
				balance = newBalance;
				result = true;
			}
			return result;

		}


		/*@
		@ normal_behavior
		@ requires amount > 0;
		@ ensures balance >= amount <==> \result == true;
		@ assignable \nothing;
		@*/
		public boolean creditAccountCredit(int amount) {
			boolean result;
			result = balance >= amount;
			return result;
		}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> (withdraw == \old(withdraw) &&  balance == \old(balance))) && (\result == true ==> (withdraw <= \old(withdraw) ) &&  balance == \old(balance) + x);
		@ assignable balance, withdraw;
		@*/
		public boolean dailyAccountUpdate(int x) {
			int newWithdraw;
			boolean tmp;
			boolean result;
			result = false;
			newWithdraw = withdraw;
			if (x<0) {
				newWithdraw += x;
				if (newWithdraw < DAILY_LIMIT) {
					result = false;
					return result;
				} else if (newWithdraw >= DAILY_LIMIT) {
					;
				}
			} else if (x>=0) {
				;
			}
			tmp = update(x);
			if (tmp == false) {
				result = false;
				return result;
			} else if (tmp == true) {
				withdraw = newWithdraw;
				result = true;
				return result;
			}
			return false;
		}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures \result == true ==> source.isLocked() == true && destination.isLocked() == true;
		@ assignable \everything;
		@*/
		public boolean transactionAccountLock(Account source, Account destination) {
			boolean result;
			if (source.isLocked() == true) {
				result = false;
				return result;
			} else if (source.isLocked() == false) {
				;
			}
			if (destination.isLocked() == true) {
				result = false;
				return result; 
			} else if (destination.isLocked() == false) {
				;
			}
			source.lock();
			destination.lock();
			result = true;
			return result;

		}

		/*@
		@ normal_behavior
		@ requires source != destination;
		@ ensures (\result == true ==> (\old(destination.balance) + amount == destination.balance)) && (\result == true ==> (\old(source.balance) - amount == source.balance)) 
		@ 	&& (\result == false ==> (\old(destination.balance) == destination.balance)) && (\result == false ==> (\old(source.balance) == source.balance));
		@ assignable \everything;
		@*/
		public boolean transactionAccountTransfer2(Account source, Account destination, int amount) {
			boolean result;
			boolean tmp;
			boolean tmp2;
			if (Account.lock(source, destination) == false) {
				result = false;
				return result;
			} else if (Account.lock(source, destination) == true) {
				if (amount <= 0) {
					result = false;
					return result;
				} else if (amount > 0) {
					tmp = source.update(amount * -1);
					if (false == tmp) {
						result = false;
						return result;
					} else if (true == tmp) {
						tmp2 = destination.update(amount);
						if (false == tmp2) {
							source.undoUpdate(amount * -1);
							result = false;
							return result;
						} else if (true == tmp2) {
							result = true;
							return result;
						}
					}
					return false;
				}
			}
			return false;

		}
	}