public class Application {

    public Account a;



	/*@
	@ normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	public void original_original_nextDay() {
		;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures a.withdraw == 0;
	@ assignable \nothing;
	@*/
	public void original_nextDay() {
		original_original_nextDay();
		a.withdraw = 0;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (a.balance >= 0 ==> a.interest >= \old(this.a.interest)) && (a.balance <= 0 ==> a.interest <= \old(this.a.interest));
	@ assignable \nothing;
	@*/
	public void nextDay() {
		original_nextDay();
		a.interest += a.interestCalculate();

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	public void original_nextYear() {
		;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures a.balance == \old(a.balance) + \old(a.interest) && a.interest == 0;
	@ assignable \nothing;
	@*/
	public void nextYear() {
		original_nextYear();
		a.balance += a.interest;
		a.interest = 0;

	}
}