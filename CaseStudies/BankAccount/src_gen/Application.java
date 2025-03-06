public class Application {

    public Account a;




	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	int /*@helper pure @*/ length(int[] arr) {return arr.length;}


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
	public void original_original_nextDay() {
		;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures a.withdrawDaily == 0;
	@ assignable a.withdrawDaily;
	@*/
	public void original_nextDay() {
		original_original_nextDay();
		a.withdrawDaily = 0;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (a.balance >= 0 ==> a.interest >= \old(this.a.interest)) && (a.balance <= 0 ==> a.interest <= \old(this.a.interest));
	@ assignable a.interest;
	@*/
	public void nextDay() {
		original_nextDay();
		a.interest += a.interestCalculateDaily();

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures a.withdrawHourly == 0;
	@ assignable a.withdrawHourly;
	@*/
	public void original_nextHour() {
		original_original_nextHour();
		a.withdrawHourly = 0;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (a.balance >= 0 ==> a.interest >= \old(this.a.interest)) && (a.balance <= 0 ==> a.interest <= \old(this.a.interest));
	@ assignable a.interest;
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
	@ assignable a.balance,a.interest;
	@*/
	public void nextYear() {
		original_nextYear();
		a.balance += a.interest;
		a.interest = 0;

	}
}
