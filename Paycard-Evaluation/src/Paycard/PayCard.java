package Paycard;
import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class PayCard {
    
    /*@
      @ public instance invariant balance >= 0;
      @ public instance invariant limit > 0;
      @ public instance invariant unsuccessfulOperations >= 0;
      @*/
    
    /*@ spec_public @*/ @SecurityLevel("high") int limit=1000; 
    /*@ spec_public @*/ @SecurityLevel("high") int unsuccessfulOperations; 
    /*@ spec_public @*/ @SecurityLevel("low") int id;
    /*@ spec_public @*/ @SecurityLevel("high") int balance=0; 
    /*@ spec_public @*/ @SecurityLevel("high") protected LogFile log; 
    
    public PayCard(@SecurityLevel("high") int limit, @SecurityLevel("high") int id) {
		balance = 0;
		unsuccessfulOperations=0;
		this.limit = limit;
		this.id = id;
		log = new LogFile();
    }
    
    public PayCard() {
    	balance=0;
		unsuccessfulOperations=0;
    }
    
    /*@
      @ ensures \result.limit==100;
      @*/
    public static PayCard createJuniorCard() {
    	return new PayCard(100, 0);
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
      @     ensures unsuccessfulOperations == \old(unsuccessfulOperations) + 1; 
      @     assignable unsuccessfulOperations;
      @ |}
      @ 	
      @ also
      @
      @ public exceptional_behavior
      @ requires amount <= 0;
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
    public @SecurityLevel("high") boolean charge(@SecurityLevel("low") int amount) {
		if (amount <= 0) {
		    return false;
		}
		@SecurityLevel("high") int intermediate = this.balance;
		@SecurityLevel("high") boolean result = this.balance+amount<this.limit;
	    if (result) { //high check
	        intermediate=this.balance+amount;
	    }
	    this.balance = intermediate;
	    if (result) {
	        return true;
	    } else {
	        return false;
	    }
    }
    
    /*@
      @ public normal_behavior
      @ requires amount>0;
      @ assignable \everything;
      @ ensures balance >= \old(balance);
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
    public void chargeAndRecord(@SecurityLevel("low") int amount) {
		if (charge(amount)) {//high guard
//		    try {
			log.addRecord(balance);//log high (with exp-promotion this update is possible)
//		    } catch (CardException e){
//			throw new IllegalArgumentException();
//		    }
		}
    }
    
    /*@
      @ public normal_behavior
      @ requires true;
      @ ensures \result == (unsuccessfulOperations<=3); 
      @ assignable \nothing;
      @*/
    @MethodReceiver(SL = "low", MDF = MDF.READ)
    public /*@pure@*/ @SecurityLevel("high") boolean isValid() {
		if (unsuccessfulOperations<=3) {
		    return true;
		} else {
		    return false;
		}
    }
    
    @MethodReceiver(SL = "low", MDF = MDF.READ) 
    public @SecurityLevel("high") String infoCardMsg() {//getter of high field
    	return (" Current balance on card is " + balance);
    }
    
    @MethodReceiver(SL = "low", MDF = MDF.READ)
    public @SecurityLevel("low") int getID() {//getter of low field
    	return id;
    }
}
