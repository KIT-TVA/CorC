// This is a mutant program.
// Author : ysma

package account;


public class Account
{

//@ public invariant bal >= 0;
    public int bal;


    public Account( int amt )
    {
        bal = amt;
    }


    public Account( account.Account acc )
    {
        bal = acc.getBalance();
    }

/*@ requires amt > 0 && amt <= acc.getBalance();
    @ assignable bal, acc.bal;
    @ ensures bal == \old(bal) + amt
    @   && acc.bal == \old(acc.bal) - amt; @*/
    public  void transferAmount( int amt, account.Account acc )
    {
        acc.withdrawAmount( amt );
        depositAmount( amt );
    }

/*@ requires amt > 0 && amt <= bal;
    @ assignable bal;
    @ ensures bal == \old(bal) - amt; @*/
    public  void withdrawAmount( int amt )
    {
        bal -= amt;
    }

/*@ requires amt > 0;
    @ assignable bal;
    @ ensures bal == \old(bal) + amt; @*/
    public  void depositAmount( int amt )
    {
        bal += amt++;
    }

//@ ensures \result == bal;
  public /*@ pure @*/ int getBalance() 
    {
        return bal;
    }

}
