// This is a mutant program.
// Author : ysma

package BankAccount;


public class Account
{

    final int OVERDRAFT_LIMIT = -5000;

/*@ invariant balance >= OVERDRAFT_LIMIT; @*/
    int balance = 0;


    Account()
    {
    }

/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) + x); 
	assignable balance; @*/
     boolean update( int x )
    {
        int newBalance = balance + x;
        if (newBalance >= OVERDRAFT_LIMIT) {
            return false;
        }
        balance = newBalance;
        return true;
    }

/*@ 
	 ensures (!\result ==> balance == \old(balance)) 
	  && (\result ==> balance == \old(balance) - x);
	assignable balance; @*/
     boolean undoUpdate( int x )
    {
        int newBalance = balance - x;
        if (newBalance < OVERDRAFT_LIMIT) {
            return false;
        }
        balance = newBalance;
        return true;
    }

/*@ 
	 requires amount >= 0;
	ensures balance >= amount <==> \result;
	assignable \nothing; @*/
     boolean credit( int amount )
    {
        return balance >= amount;
    }

    private boolean lock = false;

/*@ 
	 ensures this.lock;
	assignable lock; @*/
     void lock()
    {
        lock = true;
    }

/*@ 
	 ensures !this.lock;
	assignable lock; @*/
     void unLock()
    {
        lock = false;
    }

/*@ 
	 ensures \result == this. lock;
	assignable \nothing; @*/
     boolean isLocked()
    {
        return lock;
    }

}
