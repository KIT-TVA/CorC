public class Account_helper {
	
	int interest;
	int balance;
	
	//begin
	final static int INTEREST_RATE = 2;
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculateDaily() {
		int result;
		result = balance * INTEREST_RATE / 36500;
		return result; 
	}
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (balance >= 0 ==> \result >= 0) && (balance <= 0 ==> \result <= 0);
	@ assignable \nothing;
	@*/
	public /*@helper pure@*/ int interestCalculateHourly() {
		int result;
		result = balance * INTEREST_RATE / 36500;
		result = result / 24;
		return result; 
	}
}
