public class Environment {

    public Floor[] floors;




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
	@ requires numFloors >= 0;
	@ ensures this.floors.length == numFloors && (\forall int k; ((k>0 && k<this.floors.length ==> (this.floors[k].thisFloorID == k && this.floors[k].env == this))));
	@ assignable \nothing;
	@*/
	public void createEnvironment(int numFloors) {
		int index;
		index = 0;
		this.floors = new Floor[numFloors];
		while (index < this.floors.length) {
			this.floors[index] = new Floor(this, index);
			index = index + 1;
		}

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && 0 <= id && id < this.floors.length;
	@ ensures (id < this.floors.length&& id >= 0) ==> \result == this.floors[id];
	@ assignable \nothing;
	@*/
	public Floor getFloor(int id) {
		Floor ret;
		ret = this.floors[id];
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null;
	@ ensures \result == this.floors;
	@ assignable \nothing;
	@*/
	public Floor[] getFloors() {
		Floor[] ret;
		ret = this.floors;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && this.floors.length >= 0 && id >= 0;
	@ ensures (id == (this.floors.length - 1) ==> \result == true) && (id != (this.floors.length - 1) ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean isTopFloor(int id) {
		boolean ret;
		ret = (id == this.floors.length - 1);
		return ret;

	}

    public Floor[] floors;



	/*@
	@ normal_behavior
	@ requires numFloors >= 0;
	@ ensures this.floors.length == numFloors && (\forall int k; ((k>0 && k<this.floors.length ==> (this.floors[k].thisFloorID == k && this.floors[k].env == this))));
	@ assignable \nothing;
	@*/
	public void createEnvironment(int numFloors) {
		int index;
		index = 0;
		this.floors = new Floor[numFloors];
		while (index < this.floors.length) {
			this.floors[index] = new Floor(this, index);
			index = index + 1;
		}

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && 0 <= id && id < this.floors.length;
	@ ensures (id < this.floors.length&& id >= 0) ==> \result == this.floors[id];
	@ assignable \nothing;
	@*/
	public Floor getFloor(int id) {
		Floor ret;
		ret = this.floors[id];
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null;
	@ ensures \result == this.floors;
	@ assignable \nothing;
	@*/
	public Floor[] getFloors() {
		Floor[] ret;
		ret = this.floors;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && this.floors.length >= 0 && id >= 0;
	@ ensures (id == (this.floors.length - 1) ==> \result == true) && (id != (this.floors.length - 1) ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean isTopFloor(int id) {
		boolean ret;
		ret = (id == this.floors.length - 1);
		return ret;

	}
}