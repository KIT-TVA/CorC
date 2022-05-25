package Paycard;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class LogRecord {
    
    /*@ public instance invariant
      @     !empty ==> (balance >= 0 && transactionId >= 0);
      @ public static invariant transactionCounter >= 0;
      @*/
    
    private /*@ spec_public @*/ @SecurityLevel("low") static int transactionCounter = 0;
    
    private /*@ spec_public @*/ @SecurityLevel("high") int balance = -1;
    private /*@ spec_public @*/ @SecurityLevel("low") int transactionId = -1;
    private /*@ spec_public @*/ @SecurityLevel("low") boolean empty = true;
    
    public /*@pure@*/ LogRecord() {}
    
    
    /*@ public normal_behavior
      @   requires balance >= 0;
      @   assignable empty, this.balance, transactionId, transactionCounter;
      @   ensures this.balance == balance && 
      @           transactionId == \old(transactionCounter);
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
    public void setRecord(@SecurityLevel("high") int balance) {
	if(balance < 0){
	    return;
	}
	this.empty = false;
	this.balance = balance;
	this.transactionId = transactionCounter++;
    }
    
    /*@ public normal_behavior
      @   ensures \result == balance;
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.READ)
    public /*@pure@*/ @SecurityLevel("high") int getBalance() {//high getter
	return balance;
    }
    
    /*@ public normal_behavior
      @   ensures \result == transactionId;
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.READ)
    public /*@pure@*/ @SecurityLevel("low") int getTransactionId() {//if called with high receiver, high return value
	return transactionId;
    }
}
