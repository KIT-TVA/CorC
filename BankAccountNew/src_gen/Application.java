public class Application {

    public Account a;



	/*@
	@ normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	public void original_original_nextHour() {
		;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	public void original_nextDay() {
		;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (a.balance >= 0 ==> a.interest >= \old(this.a.interest)) && (a.balance <= 0 ==> a.interest <= \old(this.a.interest));
	@ assignable \nothing;
	@*/
	public void nextDay() {
		original_nextDay();
		a.interest += a.interestCalculateDaily();

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures a.withdrawHourly == 0;
	@ assignable \nothing;
	@*/
	public void original_nextHour() {
		original_original_nextHour();
		a.withdrawHourly = 0;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (a.balance >= 0 ==> a.interest >= \old(this.a.interest)) && (a.balance <= 0 ==> a.interest <= \old(this.a.interest));
	@ assignable \nothing;
	@*/
	public void nextHour() {
		original_nextHour();
		a.interest += a.interestCalculateHourly();

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