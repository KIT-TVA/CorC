public interface PersonInter{

	public int destination;
	public boolean destinationReached;
	public String name;
	public int weight;
	public int origin;
	public int[] additionalButtons;


	/*@
	@ normal_behavior
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null && (this instanceof Person) && namex != null && envx != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable this.destination,this.destinationReached,this.name,this.origin,this.weight;
	@*/
	public /*@helper@*/ default void createPerson(String namex, int weightx, int originx, int destinationx, Environment envx) {
		Floor tmpFloor = null;
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
	@ requires e != null&& e != null&& this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null && e != null && e.floorButtons != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default void enterElevator(Elevator e) {
		e.pressInLiftFloorButton(this.destination);

	}


	/*@
	@ normal_behavior
	@ requires true && this.name != null;
	@ ensures this.name.equals(\result) == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default String getName() {
		String result = null;
		result = this.name;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true && this.origin >= 0;
	@ ensures \result == this.origin && \result >= 0;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default int getOrigin() {
		int result = 0;
		result = this.origin;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.weight >= 0;
	@ ensures \result == this.weight;
	@ assignable \nothing;
	@*/
	public /*@pure helper@*/ default int getWeight() {
		int result = 0;
		result = this.weight;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires true && this.destinationReached != null;
	@ ensures \result != null && \result == this.destinationReached&& (this.destinationReached == true ==> \result == true)&& (this.destinationReached == false ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default boolean isDestinationReached() {
		boolean result = false;
		result = this.destinationReached;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.destinationReached == false && this.destinationReached != null;
	@ ensures this.destinationReached == true;
	@ assignable this.destinationReached;
	@*/
	public /*@helper@*/ default void leaveElevator() {
		this.destinationReached = true;

	}

	/*@
	@ normal_behavior
	@ requires true && this.destination >= 0;
	@ ensures \result == this.destination && (\result instanceof int);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ default int getDestination() {
		int result = 0;
		result = this.destination;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable this.destination,this.destinationReached,this.name,this.origin,this.weight;
	@*/
	public /*@helper@*/ default void PersonInterC(String namex, int weightx, int originx, int destinationx, Environment envx) {
		Floor tmpFloor = null;
		tmpFloor = envx.getFloor(originx);
		this.weight = weightx;
		this.origin = originx;
		this.destination = destinationx;
		this.destinationReached = false;
		this.name = namex;
		tmpFloor.addWaitingPerson(this);

	}
}