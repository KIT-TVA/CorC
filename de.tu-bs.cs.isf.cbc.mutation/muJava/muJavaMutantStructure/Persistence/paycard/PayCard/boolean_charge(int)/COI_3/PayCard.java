// This is a mutant program.
// Author : ysma

package paycard;


public class PayCard
{

/*@ public invariant balance >= 0; 
      @ public invariant limit > 0;
      @ public invariant unsuccessfulOperations >= 0; 
      @ public invariant log != null;
      @*/
    public int limit = 1000;

    public int unsuccessfulOperations;

    public int id;

    public int balance = 0;

    public protected LogFile log;


    public PayCard( int limit )
    {
        balance = 0;
        unsuccessfulOperations = 0;
        this.limit = limit;
        this.log = new LogFile();
    }


    public PayCard()
    {
        balance = 0;
        unsuccessfulOperations = 0;
        this.log = new LogFile();
    }

/*@ public normal_behavior
      @ requires LogRecord.transactionCounter >= 0;
      @ ensures \result.limit==100;
      @ ensures LogRecord.transactionCounter >= 0;
      @  ensures \result.balance == 0 && \result.unsuccessfulOperations == 0 && 
      @     \result.log != null && \fresh(\result);
      @*/
    public static  paycard.PayCard createJuniorCard()
    {
        return new paycard.PayCard( 100 );
    }

/*@
      @ public normal_behavior
      @     requires amount>0;
      @     requires amount + balance < limit && isValid() == true;
      @     ensures \result == true && amount == \old(amount);
      @     ensures balance == amount + \old(balance);
      @     ensures unsuccessfulOperations == \old(unsuccessfulOperations);
      @     assignable balance, unsuccessfulOperations;
      @
      @     also
      @
      @ public normal_behavior
      @     requires amount>0 ;
      @     requires amount + balance >= limit || isValid() == false;
      @     ensures \result == false && amount == \old(amount);
      @     ensures unsuccessfulOperations == \old(unsuccessfulOperations) + 1; 
      @     ensures balance == \old(balance);
      @     assignable balance, unsuccessfulOperations;
      @ 	
      @ also
      @
      @ public exceptional_behavior
      @     requires amount <= 0;
      @     assignable \nothing;
      @*/
    public  boolean charge( int amount )
        throws java.lang.IllegalArgumentException
    {
        if (amount <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!(this.balance + amount < this.limit && this.isValid())) {
            this.balance = this.balance + amount;
            return true;
        } else {
            this.unsuccessfulOperations++;
            return false;
        }
    }

/*@
     @ public normal_behavior
     @ requires amount>0;
     @ requires log != null && this != null;
     @ requires LogRecord.transactionCounter >= 0;
     @ ensures balance >= \old(balance);  
     @*/
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

/*@
      @ public normal_behavior
      @ requires true;
      @ ensures \result == (unsuccessfulOperations<=3); 
      @ assignable \nothing;
      @*/
    public /*@ pure @*/ boolean isValid() 
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

}
