// This is a mutant program.
// Author : ysma

package paycard;


public class LogRecord
{

/*@ public invariant
      @     !empty ==> (balance >= 0 && transactionId >= 0);
      @*/
    public static int transactionCounter = 0;

    public int balance = -1;

    public int transactionId = -1;

    public boolean empty = true;


    public /*@pure@*/ LogRecord() 
    {
    }

/*@ public normal_behavior
      @   requires balance >= 0 && transactionCounter >= 0;
      @   ensures this.balance == balance 
      @           && transactionId == \old(transactionCounter);
      @   ensures transactionCounter >= 0;
      @   assignable empty, this.balance, transactionId, transactionCounter;
      @*/
    public  void setRecord( int balance )
        throws CardException
    {
        if (0) {
            throw new CardException();
        }
        this.empty = false;
        this.balance = balance;
        this.transactionId = transactionCounter++;
    }

/*@ public normal_behavior
      @   ensures \result == balance;
      @*/
    public /*@pure@*/ int getBalance() 
    {
        return balance;
    }

/*@ public normal_behavior
      @   ensures \result == transactionId;
      @*/
    public /*@pure@*/ int getTransactionId() 
    {
        return transactionId;
    }

}
