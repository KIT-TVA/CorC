// This is a mutant program.
// Author : ysma

public class Account
{

    public final int OVERDRAFT_LIMIT = -5000;

//@ invariant balance >= OVERDRAFT_LIMIT;
    int balance = 0;


    Account()
    {
    }

/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) + x); @*/
    private  boolean update__wrappee__BankAccount( int x )
    {
        int newBalance = balance + x;
        if (newBalance < OVERDRAFT_LIMIT) {
            return false;
        }
        balance = newBalance;
        return true;
    }

/*@ 
	 ensures ( (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) + x) ) 
	  && (!\result ==> withdraw == \old(withdraw)) 
	  && (\result ==> withdraw <= \old(withdraw)); @*/
     boolean update( int x )
    {
        int newWithdraw = withdraw;
        if (x < 0) {
            newWithdraw += x;
            if (newWithdraw < DAILY_LIMIT) {
                return false;
            }
        }
        if (!update__wrappee__BankAccount( x )) {
            return false;
        }
        withdraw = newWithdraw;
        return true;
    }

/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) - x); @*/
    private  boolean undoUpdate__wrappee__BankAccount( int x )
    {
        int newBalance = balance - x;
        if (newBalance < OVERDRAFT_LIMIT) {
            return false;
        }
        balance = newBalance;
        return true;
    }

/*@ 
	 ensures ( (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) - x) ) 
	  && (!\result ==> withdraw == \old(withdraw)) 
	  && (\result ==> withdraw >= \old(withdraw)); @*/
     boolean undoUpdate( int x )
    {
        int newWithdraw = withdraw;
        if (x < 0) {
            newWithdraw -= ++x;
            if (newWithdraw < DAILY_LIMIT) {
                return false;
            }
        }
        if (!undoUpdate__wrappee__BankAccount( x )) {
            return false;
        }
        withdraw = newWithdraw;
        return true;
    }

    static final int INTEREST_RATE = 2;

    int interest = 0;

/*@ 
	 ensures (balance >= 0 ==> \result >= 0) 
	   && (balance <= 0 ==> \result <= 0); @*/
	 /*@pure@*/  int calculateInterest() 
    {
        return balance * INTEREST_RATE / 36500;
    }

/*@ 
	 requires daysLeft >= 0;
	ensures calculateInterest() >= 0 ==> \result >= interest; @*/
	 /*@pure@*/  int estimatedInterest(int daysLeft) 
    {
        return interest + daysLeft * calculateInterest();
    }

/*@ 
	 requires amount >= 0;
	ensures balance >= amount <==> \result; @*/
     boolean credit( int amount )
    {
        return balance >= amount;
    }

    static final int DAILY_LIMIT = -1000;

//@ invariant withdraw >= DAILY_LIMIT;
    int withdraw = 0;

    private boolean lock = false;

     void lock()
    {
        lock = true;
    }

     void unLock()
    {
        lock = false;
    }

     boolean isLocked()
    {
        return lock;
    }

}
