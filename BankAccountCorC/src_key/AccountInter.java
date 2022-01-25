public class  AccountInter {
	public int getOVERDRAFT_LIMIT();

	public int getINTEREST_RATE();
	public int getDAILY_LIMIT();
	public   int getBalance() {return 1;}
	public int setBalance(int balance);
	public   int getWithdraw() {return 1;}
	public int setWithdraw(int withdraw);
	public   int getInterest() {return 1;}
	public   boolean getLock() {return true;};
	public void setLock(boolean lock);
	
	/*@
	 @ public normal_behavior
	 @ requires true;
	 @ ensures (!\result == true ==> getBalance() == \old(getBalance())) 
	 @   && (\result == true ==> getBalance() == \old(getBalance()) + x && getBalance()>=getOVERDRAFT_LIMIT()); 
	 @*/
	public /*@helper@*/   boolean update(int x) {
		int newBalance = getBalance() + x;
		if (newBalance < getOVERDRAFT_LIMIT())
			return false;
		setBalance(newBalance);
		return true;
	}

	/*@
	 @ public normal_behavior
	 @  requires true;
	 @  ensures (!\result == true ==> getBalance() == \old(getBalance())) 
	 @   && (\old(getBalance()) - x >= getOVERDRAFT_LIMIT() ==> getBalance() == \old(getBalance()) - x && \result == true);
	 @*/
	public /*@helper@*/   boolean undoUpdate(int x) {
		int newBalance = getBalance() - x;
		if (newBalance < getOVERDRAFT_LIMIT())
			return false;
		setBalance(newBalance);
		return true;
	}
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (getBalance() >= 0 ==> \result >= 0) && (getBalance() <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/   int interestCalculate() {
		int result;
		result = getBalance() * getINTEREST_RATE() / 36500;
		return result; 
	}
	
	/*@
	@ normal_behavior
	@ requires daysLeft > 0;
	@ ensures interestCalculate() >= 0 ==> \result >= getInterest();
	@ assignable \nothing;
	@*/
	public /*@helper@*/   int interestEstimated(int daysLeft) {
		int result;
		result = getInterest() + daysLeft * interestCalculate();
		return result;
	}

	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures getLock() == true;
	 @*/
	public /*@helper@*/   void lock() {
		setLock(true);
	}
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures !getLock() == true;
	 @*/
	public /*@helper@*/   void unLock() {
		setLock(false);
	}
	
	/*@
	  @ normal_behavior
	  @ requires true;
	 @ ensures \result == getLock();
	 @ assignable \nothing;
	 @*/
	public /*@helper@*/   boolean isLocked() {
		return getLock();
	}
	
	/*@
	  @ normal_behavior
	  @ requires destination != null && source != null && source != destination;
	  @ ensures \result == true ==> source.isLocked()  == true && destination.isLocked()  == true;
	  @ assignable \nothing;
	 @*/
	public /*@helper@*/   boolean lockBoth(AccountInter source, AccountInter destination) {
		if (source.isLocked()) return false;
		if (destination.isLocked()) return false;
		source.lock();
		destination.lock();
		return true;
	}
	
	/*@ requires destination != null && source != null && source != destination;
	  @ ensures \result == true ==> (\old(destination.getBalance()) + amount == destination.getBalance())
	  @ && \result == true ==> (\old(source.getBalance()) - amount == source.getBalance())
	  @ && !\result == true ==> (\old(destination.getBalance()) == destination.getBalance())
	  @ && !\result == true ==> (\old(source.getBalance()) == source.getBalance());
	  @ assignable \nothing;
	  @*/
	public /*@helper@*/   boolean transfer(AccountInter source, AccountInter destination, int amount) {
		if (!lockBoth(source, destination)) return false;
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
		@ ensures (\result == false ==> getBalance() == \old(getBalance())) && (\result == true ==> getBalance() == \old(getBalance()) - x);
		@*/
		public /*@helper@*/   boolean bankAccountUndoUpdate(int x) {
			int newBalance;
			boolean result;
			newBalance = getBalance() - x;
			result = false;
			if (newBalance < this.getOVERDRAFT_LIMIT()) {
				result = false;
			} else if (newBalance >= this.getOVERDRAFT_LIMIT()) {
				setBalance(newBalance);
				result = true;
			}
			return result;

		}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> getBalance() == \old(getBalance())) && (\result == true ==> getBalance() == \old(getBalance()) + x);
		@*/
		public   boolean bankAccountUpdate(int x) {
			int newBalance;
			boolean result;
			result = false;
			newBalance = getBalance() + x;
			if (newBalance < this.getOVERDRAFT_LIMIT()) {
				result = false;
			} else if (newBalance >= this.getOVERDRAFT_LIMIT()) {
				setBalance(newBalance);
				result = true;
			}
			return result;

		}


		/*@
		@ normal_behavior
		@ requires amount > 0;
		@ ensures getBalance() >= amount <==> \result == true;
		@ assignable \nothing;
		@*/
		public   boolean creditAccountCredit(int amount) {
			boolean result;
			result = getBalance() >= amount;
			return result;
		}

		/*@
		@ normal_behavior
		@ requires true;
		@ ensures (\result == false ==> (getWithdraw() == \old(getWithdraw()) &&  getBalance() == \old(getBalance()))) && (\result == true ==> (getWithdraw() <= \old(getWithdraw()) ) &&  getBalance() == \old(getBalance()) + x);
		@*/
		public   boolean dailyAccountUpdate(int x) {
			int newWithdraw;
			boolean tmp;
			boolean result;
			result = false;
			newWithdraw = getWithdraw();
			if (x<0) {
				newWithdraw += x;
				if (newWithdraw < getDAILY_LIMIT()) {
					result = false;
					return result;
				} else if (newWithdraw >= getDAILY_LIMIT()) {
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
				setWithdraw(newWithdraw);
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
		public   boolean transactionAccountLock(AccountInter source, AccountInter destination) {
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
		@ ensures (\result == true ==> (\old(destination.getBalance()) + amount == destination.getBalance())) && (\result == true ==> (\old(source.getBalance()) - amount == source.getBalance())) 
		@ 	&& (\result == false ==> (\old(destination.getBalance()) == destination.getBalance())) && (\result == false ==> (\old(source.getBalance()) == source.getBalance()));
		@ assignable \everything;
		@*/
		public   boolean transactionAccountTransfer2(AccountInter source, AccountInter destination, int amount) {
			boolean result;
			boolean tmp;
			boolean tmp2;
			if (lockBoth(source, destination) == false) {
				result = false;
				return result;
			} else if (lockBoth(source, destination) == true) {
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