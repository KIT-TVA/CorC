import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Application {
	/*@ invariant account != null; @*/

	@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Account account = new Account();

	/*@ 
	 ensures (account.balance >= 0 ==> account.interest >= \old(account.interest)) 
	  && (account.balance <= 0 ==> account.interest <= \old(account.interest));
	assignable account.interest; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void nextDay() {
		account.withdraw = 0;
		account.interest += account.calculateInterest();
	}

	/*@ 
	 ensures account.balance == \old(account.balance) + \old(account.interest) 
	  && account.interest == 0;
	assignable account.interest, account.balance; @*/
	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void nextYear() {
		account.balance += account.interest;
		account.interest = 0;
	}


}
