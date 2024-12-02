public class Transaction {

    public Account source;
    public Account destination;




	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	int /*@ pure @*/ length(int[] arr) {return arr.length;}




	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures false;
	@ assignable \nothing;
	@*/
	boolean /*@ pure @*/ noResolve() {return false;}


	/*@
	@ normal_behavior
	@ requires source != destination;
	@ ensures \result == true ==> (\old(destination.balance) + amount == destination.balance)  && \result == true ==> (\old(source.balance) - amount == source.balance)  && \result == false ==> (\old(destination.balance) == destination.balance)   && \result == false ==> (\old(source.balance) == source.balance);
	@ assignable \nothing;
	@*/
	public boolean transfer(int amount) {
		boolean ret;
		if (amount <= 0) {
			ret = false;
			return ret;
		} else if (amount > 0) {
			;
		}
		ret = source.update(amount * -1);
		if (ret == false) {
			ret = false;
			return ret;
		} else if (ret == true) {
			;
		}
		ret = destination.update(amount);
		if (ret == true) {
			ret = true;
			return ret;
		} else if (ret == false) {
			ret = source.undoUpdate(amount * -1);
			ret = false;
			return ret;
		}

	}
}