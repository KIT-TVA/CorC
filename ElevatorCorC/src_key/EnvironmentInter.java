public class EnvironmentInter{

	private /*@spec_public@*/ Floor[] floors;

	/*@
	@ normal_behavior
	@ requires numFloors >= 0 && this.floors != null && (this.floors instanceof Floor[]);
	@ ensures this.floors.length == numFloors&& (\forall int k;(k>0 && k<this.floors.length ==> (this.floors[k].thisFloorID == k && this.floors[k].env == this)));
	@ assignable this.floors[*];
	@*/
	public /*@helper@*/   void createEnvironment(int numFloors) {
		int index = 0;
		index = 0;
		this.floors = new Floor[numFloors];
		//@ loop_invariant index >= 0 && this.floors.length == numFloors &&(\forall int k;((k>0 && k<index && k<this.floors.length) ==> (this.floors[k].thisFloorID == k && this.floors[k].env == this )));
		//@ decreases (this.floors.length - index);
		while (index < this.floors.length) {
			this.floors[index] = new Floor();
			this.floors[index].createFloor(this, index++);
		}

	}

	/*@
	@ normal_behavior
	@ requires numFloors >= 0;
	@ ensures this.floors.length == numFloors&& (\forall int k;(k>0 && k<this.floors.length ==> (this.floors[k].thisFloorID == k)));
	@ assignable this.floors[*];
	@*/
	public /*@helper@*/   void EnvironmentInterC(int numFloors) {
		int index = 0;
		index = 0;
		this.floors = new Floor[numFloors];
		//@ loop_invariant index >= 0 && this.floors.length == numFloors &&(\forall int k;((k>0 && k<index && k<this.floors.length) ==> (this.floors[k].thisFloorID == k && this.floors[k].env == this )));
		//@ decreases (this.floors.length - index);
		while (index < this.floors.length) {
			this.floors[index] = new Floor(this, index++);

		}

	}


	/*@
	@ normal_behavior
	@ requires this.floors != null && 0 <= id;
	@ ensures (id < this.floors.length&& id >= 0) ==> \result == this.floors[id];
	@ assignable \nothing;
	@*/
	public /*@helper@*/   Floor getFloorResult(int id) {
		Floor floor = null;
		if (this.floors != null && id < this.floors.length
&& id >= 0) {
			floor = this.floors[id];
		} else if (!(this.floors != null && id < this.floors.length
&& id >= 0)) {
			;
		}
		return floor;
	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && this.floors instanceof Floor[];
	@ ensures \result == this.floors;
	@ assignable \nothing;
	@*/
	public /*@pure helper@*/   Floor[] getFloors() {
		Floor[] tmpFloors = null;
		tmpFloors = this.floors;
		return tmpFloors;

	}

	/*@
	@ normal_behavior
	@ requires this.floors != null && this.floors.length >= 0 && id >= 0 && this.floors != null;
	@ ensures (id == (this.floors.length - 1) ==> \result == true)&& (id != (this.floors.length - 1) ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/   boolean isTopFloor(int id) {
		boolean result = false;
		result = (id == this.floors.length - 1);
		return result;

	}


	/*@
	@ normal_behavior
	@ requires this.floors != null && 0 <= id && id < this.floors.length;
	@ ensures (id < this.floors.length&& id >= 0) ==> \result == this.floors[id];
	@ assignable \nothing;
	@*/
	public /*@helper@*/   Floor getFloor(int id) {
		return this.floors[id];

	}
}