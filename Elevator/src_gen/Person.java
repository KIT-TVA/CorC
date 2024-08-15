public class Person {

    private /*@spec_public@*/  int destination;
    private /*@spec_public@*/  boolean destinationReached;
    private /*@spec_public@*/  String name;
    private /*@spec_public@*/  int weight;
    private /*@spec_public@*/  int origin;
    private /*@spec_public@*/  int[] additionalButtons;




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
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable \nothing;
	@*/
	public void createPerson(String namex, int weightx, int originx, int destinationx, Environment envx) {
		Floor tmpFloor;
		tmpFloor = envx.getFloor(originx);
		this.weight = weightx;
		this.origin = originx;
		this.destination = destinationx;
		this.destinationReached = false;
		this.name = namex;
		tmpFloor.addWaitingPerson(this);

	}

	/*@
	@ normal_behavior
	@ requires e != null && this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public void enterElevator(Elevator e) {
		e.pressInLiftFloorButton(this.destination);

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.destination;
	@ assignable \nothing;
	@*/
	public int getDestination() {
		int ret;
		ret = this.destination;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.name.equals(\result) == true;
	@ assignable \nothing;
	@*/
	public String getName() {
		String ret;
		ret = this.name;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.origin;
	@ assignable \nothing;
	@*/
	public int getOrigin() {
		int ret;
		ret = this.origin;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.weight;
	@ assignable \nothing;
	@*/
	public int getWeight() {
		int ret;
		ret = this.weight;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result != null && \result == this.destinationReached && (this.destinationReached == true ==> \result == true) && (this.destinationReached == false==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean isDestinationReached() {
		boolean ret;
		ret = this.destinationReached;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.destinationReached == false;
	@ ensures this.destinationReached == true;
	@ assignable \nothing;
	@*/
	public void leaveElevator() {
		this.destinationReached = true;

	}

    private /*@spec_public@*/  int destination;
    private /*@spec_public@*/  boolean destinationReached;
    private /*@spec_public@*/  String name;
    private /*@spec_public@*/  int weight;
    private /*@spec_public@*/  int origin;
    private /*@spec_public@*/  int[] additionalButtons;



	/*@
	@ normal_behavior
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable envx,destination,destinationReached,name,origin,weight;
	@*/
	public void createPerson(String namex, int weightx, int originx, int destinationx, Environment envx) {
		Floor tmpFloor;
		tmpFloor = envx.getFloor(originx);
		this.weight = weightx;
		this.origin = originx;
		this.destination = destinationx;
		this.destinationReached = false;
		this.name = namex;
		tmpFloor.addWaitingPerson(this);

	}

	/*@
	@ normal_behavior
	@ requires e != null && this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public void enterElevator(Elevator e) {
		e.pressInLiftFloorButton(this.destination);

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.destination;
	@ assignable \nothing;
	@*/
	public int getDestination() {
		int ret;
		ret = this.destination;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.name.equals(\result) == true;
	@ assignable \nothing;
	@*/
	public String getName() {
		String ret;
		ret = this.name;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.origin;
	@ assignable \nothing;
	@*/
	public int getOrigin() {
		int ret;
		ret = this.origin;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.weight;
	@ assignable \nothing;
	@*/
	public int getWeight() {
		int ret;
		ret = this.weight;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result != null && \result == this.destinationReached && (this.destinationReached == true ==> \result == true) && (this.destinationReached == false==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean isDestinationReached() {
		boolean ret;
		ret = this.destinationReached;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.destinationReached == false;
	@ ensures this.destinationReached == true;
	@ assignable \nothing;
	@*/
	public void leaveElevator() {
		this.destinationReached = true;

	}
}