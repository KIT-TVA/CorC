public class Transaction {

    public Account source;
    public Account destination;




	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	int /*@helper pure @*/ length(int[] arr) {return arr.length;}


	/*@
	@ normal_behavior
	@ requires source != destination;
	@ ensures \result == true ==> (\old(source.balance) - amount == source.balance) && \result == false ==> (\old(source.balance) == source.balance) && \result == true ==> (\old(destination.balance) + amount == destination.balance) && \result == false ==> (destination.balance == \old(destination.balance));
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
