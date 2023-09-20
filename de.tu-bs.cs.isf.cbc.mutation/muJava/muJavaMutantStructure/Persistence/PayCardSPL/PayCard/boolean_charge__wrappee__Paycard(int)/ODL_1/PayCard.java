// This is a mutant program.
// Author : ysma

public class PayCard
{

/*@
      @ public instance invariant balance >= 0;
      @ public instance invariant limit > 0;
      @*/
    public int limit = 1000;

    public int id;

    public int balance = 0;


    public PayCard( int limit )
    {
        balance = 0;
        this.limit = limit;
    }


    public PayCard()
    {
        balance = 0;
    }

/*@
      @ ensures \result.limit==100;
      @*/
    public static  PayCard createJuniorCard()
    {
        return new PayCard( 100 );
    }

/*@
      @ public normal_behavior
      @ requires amount>0;
      @ {|
      @     requires amount + balance < limit && isValid() == true;
      @     ensures \result == true;
      @     ensures balance == amount + \old(balance);
      @     assignable balance;
      @
      @     also
      @
      @     requires amount + balance >= limit || isValid() == false;
      @     ensures \result == false;
      @ |}
      @ 	
      @ also
      @
      @ public exceptional_behavior
      @ requires amount <= 0;
      @*/
    private  boolean charge__wrappee__Paycard( int amount )
        throws java.lang.IllegalArgumentException
    {
        if (amount) {
            throw new java.lang.IllegalArgumentException();
        }
        if (this.balance + amount < this.limit && this.isValid()) {
            this.balance = this.balance + amount;
            return true;
        } else {
            return false;
        }
    }

/*@ also
      @
      @ public normal_behavior
      @ requires amount>0;
      @ requires amount + balance >= limit || isValid() == false;
      @ ensures unsuccessfulOperations == \old(unsuccessfulOperations) + 1; 
      @ assignable unsuccessfulOperations;
      @*/
    public  boolean charge( int amount )
        throws java.lang.IllegalArgumentException
    {
        boolean success = charge__wrappee__Paycard( amount );
        if (!success) {
            this.unsuccessfulOperations++;
        }
        return success;
    }

// contract remains identical
    public  void chargeAndRecord( int amount )
    {
        if (charge( amount )) {
            try {
                log.addRecord( balance );
            } catch ( CardException e ) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

// contract remains identical
    public /*@pure@*/ boolean isValid() 
    {
        if (unsuccessfulOperations <= 3) {
            return true;
        } else {
            return false;
        }
    }

    public  java.lang.String infoCardMsg()
    {
        return " Current balance on card is " + balance;
    }

    public protected LogFile log;

/*@
      @ public instance invariant unsuccessfulOperations >= 0;
      @*/
    public int unsuccessfulOperations = 0;

}
