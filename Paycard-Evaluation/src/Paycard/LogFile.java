package Paycard;
import JDK.ArrayList;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class LogFile {
    
    /*@ public invariant
      @    logArray.length == logFileSize && 
      @    currentRecord < logFileSize && 
      @    currentRecord >= 0 && \nonnullelements(logArray);
      @*/
    private /*@ spec_public @*/ @SecurityLevel("high") static int logFileSize = 3;
    private /*@ spec_public @*/ @SecurityLevel("high") int currentRecord;
    private /*@ spec_public @*/ @SecurityLevel("high") @MutationModifier(MDF.MUTABLE) ArrayList logArray;
    
    public /*@pure@*/ LogFile() {
    @SecurityLevel("high") int i=0;
	while(i<logArray.size()){//high guard
	    logArray.add(new LogRecord());
	}
	currentRecord = 0;
    }
    
    
    /*@ public normal_behavior
      @    requires balance >= 0;
      @    ensures \old(currentRecord) + 1 != logFileSize ? 
      @        currentRecord == \old(currentRecord) + 1 : currentRecord == 0;
      @    ensures logArray[currentRecord].balance == balance;
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
    public void addRecord(@SecurityLevel("high") int balance) {//high method high balance
		currentRecord++;
		if (currentRecord == logFileSize) { //high guard
			currentRecord = 0;
		}
		logArray.get(currentRecord).setRecord(balance);
    }
    
    
    /*@ public normal_behavior
      @    ensures (\forall int i; 0 <= i && i<logArray.length;
      @             logArray[i].balance <= \result.balance);
      @    diverges true;
      @ */
    @MethodReceiver(SL = "low", MDF = MDF.READ)
    public /*@pure@*/ @SecurityLevel("high") LogRecord getMaximumRecord() {//return high
    	@SecurityLevel("high") LogRecord max = logArray.get(0);
    	@SecurityLevel("high") int i=1;
	/*@ loop_invariant
	  @   0<=i && i <= logArray.length 
	  @   && max!=null &&
	  @   (\forall int j; 0 <= j && j<i; 
	  @     max.balance >= logArray[j].balance);
	  @ assignable max, i;
	  @*/
		while(i<logArray.size()){//high guard
			@SecurityLevel("high") LogRecord lr = logArray.get(i);
			i++;
		    if (lr.getBalance() > max.getBalance()){ //high guard
			max = lr;//high variables
		    }
		}
		return max;
    }
}
