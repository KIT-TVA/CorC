public class Person{

	private /*@spec_public@*/ int destination;
	private /*@spec_public@*/ boolean destinationReached;
	private /*@spec_public@*/ String name;
	private /*@spec_public@*/ int weight;
	private /*@spec_public@*/ int origin;
	private /*@spec_public@*/ int[] additionalButtons;

	/*@
	@ normal_behavior
	@ requires namex != null&& destinationx >= 0 && envx.floors != null&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length && envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null&& additionalButtonsx != null && namex != null && additionalButtonsx != null && envx != null;
	@ ensures this.weight == weightx
&& this.origin == originx
&& this.destination == destinationx
&& this.destinationReached == false
&& this.name.equals(namex) == true
&& this.additionalButtons == additionalButtonsx;
	@ assignable this.additionalButtons,this.destination,this.destinationReached,this.name,this.origin,this.weight;
	@*/
	public /*@helper@*/ void createEvilPerson(String namex, int weightx, int originx, int destinationx, int[] additionalButtonsx, Environment envx) {
		Floor tmpFloor = null;
		tmpFloor = envx.getFloor(originx);
		this.weight = weightx;
		this.origin = originx;
		this.destination = destinationx;
		this.destinationReached = false;
		this.name = namex;
		tmpFloor.addWaitingPerson(this);
		this.additionalButtons = additionalButtonsx;

	}

	/*@
	@ normal_behavior
	@ requires e != null
&& e != null
&& this.destination >= 0 
&& this.destination < e.floorButtons.length
&& e.floorButtons != null
&& e.floorButtons[this.destination] != null
&& this.additionalButtons != null 
&& this.additionalButtons != null && e != null && e.floorButtons != null && this.additionalButtons != null;
	@ ensures e.floorButtons[this.destination] == true&& (\forall int j;(((
j >= 0 && this.additionalButtons[j] >= 0 
&& this.additionalButtons[j] < e.floorButtons.length 
&& e.floorButtons[this.additionalButtons[j]] != null
)) ==> (e.floorButtons[this.additionalButtons[j]] == true )));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void evilEnterElevator(Elevator e) {
		int index = 0;
		e.pressInLiftFloorButton(this.destination);
		index = 0;
		//@ loop_invariant index >= 0&& e.floorButtons != null && this.additionalButtons != null && (\forall int j;(((	j<index 	&& j >= 0 	&& this.additionalButtons[j] >= 0 	&& this.additionalButtons[j] < e.floorButtons.length 	&& e.floorButtons[this.additionalButtons[j]] != null)) ==> (e.floorButtons[this.additionalButtons[j]] == true )));
		//@ decreases this.additionalButtons.length - index;
		while (index < this.additionalButtons.length) {
			e.pressInLiftFloorButton(this.additionalButtons[index]);
			index++;
		}

	}


	/*@
	@ normal_behavior
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null && (this instanceof Person) && namex != null && envx != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable this.destination,this.destinationReached,this.name,this.origin,this.weight;
	@*/
	public /*@helper@*/ void createPerson(String namex, int weightx, int originx, int destinationx, Environment envx) {
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
	public /*@helper@*/ void enterElevator(Elevator e) {
		e.pressInLiftFloorButton(this.destination);
	
	}

	/*@
	@ normal_behavior
	@ requires e != null&& e != null&& this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null && e != null && e.floorButtons != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void enterElevator(ComposedElevator e) {
		e.pressInLiftFloorButton(this.destination);

	}
	
	/*@
	@ normal_behavior
	@ requires e != null&& e != null&& this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null && e != null && e.floorButtons != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void enterElevator(ElevatorInter e) {
		e.pressInLiftFloorButton(this.destination);

	}


	/*@
	@ normal_behavior
	@ requires true && this.name != null;
	@ ensures this.name.equals(\result) == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getName() {
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
	public /*@helper@*/ int getOrigin() {
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
	public /*@pure helper@*/ int getWeight() {
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
	public /*@helper@*/ boolean isDestinationReached() {
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
	public /*@helper@*/ void leaveElevator() {
		this.destinationReached = true;

	}

	/*@
	@ normal_behavior
	@ requires true && this.destination >= 0;
	@ ensures \result == this.destination && (\result instanceof int);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getDestination() {
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
	public /*@helper@*/ Person(String namex, int weightx, int originx, int destinationx, Environment envx) {
		Floor tmpFloor = null;
		tmpFloor = envx.getFloor(originx);
		this.weight = weightx;
		this.origin = originx;
		this.destination = destinationx;
		this.destinationReached = false;
		this.name = namex;
		tmpFloor.addWaitingPerson(this);

	}
	
	public Person() {}
}