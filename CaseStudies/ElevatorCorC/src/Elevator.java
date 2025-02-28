public class Elevator{

	private /*@spec_public@*/ boolean[] floorButtons;
	private /*@spec_public@*/ boolean verbose;
	private /*@spec_public@*/ int currentHeading;
	private /*@spec_public@*/ int currentFloorID;
	private /*@spec_public@*/ int doors;
	private /*@spec_public@*/ Environment env;
	private /*@spec_public@*/ ArrayList persons;
	private /*@spec_public@*/ int weight;
	private /*@spec_public@*/ boolean blocked;
	private /*@spec_public@*/ int executiveFloor;
	private /*@spec_public@*/ boolean old_contains;
	private /*@spec_public@*/ int old_weight;
	private /*@spec_public@*/ int old_currentFloorID;
	private /*@spec_public@*/ int old_collectionSize;
	private /*@spec_public@*/ int maximumWeight;

	/*@
	@ normal_behavior
	@ requires this.doors >= 0 && this.doors <= 1 && this.doors >=0 && this.doors <=1;
	@ ensures (this.doors == 0 ==> \result == true)&& (this.doors == 1 ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean areDoorsOpen() {
		boolean result = false;
		result = (this.doors == 0);
		return result;

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null && floorID < this.floorButtons.length && this.floorButtons != null;
	@ ensures \result == this.floorButtons[floorID];
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean buttonForFloorIsPressed(int floorID) {
		boolean result = false;
		result = this.floorButtons[floorID];
		return result;

	}

	/*@
	@ normal_behavior
	@ requires dir >= 0 && dir <= 1 && this.currentHeading >= 0 && this.currentFloorID >= 0&& this.env != null && this.env.floors != null && this.env != null && this.env.floors != null;
	@ ensures ((this.currentHeading == 1 && this.env.isTopFloor(this.currentFloorID) == true) ==> this.currentHeading == this.reverse()) && ((this.currentHeading == 0 && this.currentFloorID == 0) ==> this.currentHeading == this.reverse())&& ((this.currentHeading == 1 ) ==> this.currentFloorID == this.currentFloorID + 1)&& ((this.currentHeading != 1 ) ==> this.currentFloorID == this.currentFloorID - 1);
	@ assignable this.currentHeading,this.old_currentFloorID;
	@*/
	public /*@helper@*/ void continueInDirection__wrappee__Base(int dir) {
		this.old_currentFloorID = this.currentFloorID;
		this.currentHeading = dir;
		if (this.currentHeading == 1) {
			if (this.env.isTopFloor(this.currentFloorID) == true) {
				this.currentHeading = this.reverse();
			} else if (this.env.isTopFloor(this.currentFloorID) != true) {
//				statement
			}
		} else if (this.currentHeading != 1) {
			if (this.currentFloorID == 0) {
				this.currentHeading = this.reverse();
			} else if (this.currentFloorID != 0) {
				;
			}
		}
		if (this.currentHeading == 1) {
			this.currentFloorID = this.currentFloorID + 1;
		} else if (this.currentHeading != 1) {
			this.currentFloorID = this.currentFloorID - 1;
		}

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.destination>= 0 && p.destination < this.floorButtons.length && p != null && (p instanceof Person);
	@ ensures this.persons.contains(p) == true&& this.floorButtons[p.destination] == true&& this.persons.size() ==  \old(this.persons.size()) + 1;
	@ assignable this.persons.collectionSize,this.persons.elements[*];
	@*/
	public /*@helper@*/ void enterElevator__wrappee__Base(Person p) {
		p.enterElevator(this);
		this.persons.add(p);

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.destination >= 0 && p.destination < this.floorButtons.length  && p.weight >= 0 && p.getWeight() >= 0 && p != null && this.weight >= 0;
	@ ensures this.weight == \old(this.weight) + p.weight && this.persons.contains(p) == true && this.floorButtons[p.destination] == true;
	@ assignable this.weight;
	@*/
	public /*@helper@*/ void enterElevator(Person p) {
		this.enterElevator__wrappee__Base(p);
		this.weight = this.weight + p.getWeight();

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && d >= 0 && d<= 1&& this.currentFloorID >= 0 && d<=1 && this.floorButtons != null && this != null;
	@ ensures (this.floorButtons != null && d == 1 && (\exists int k;(k>=this.getCurrentFloorID() && k<this.floorButtons.length && this.buttonForFloorIsPressed(k) == true))) ==> \result == true
	&& (this.floorButtons != null && d == 0 && (\exists int k;(k<=this.getCurrentFloorID() && this.buttonForFloorIsPressed(k) == true))) ==> \result == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean existInLiftCallsInDirection(int d) {
		boolean result = false;
		int index = 0;
		index = this.getCurrentFloorID();
		result = false;
		if (d == 1) {
			//@ loop_invariant ((\exists int j;(j>=this.getCurrentFloorID() && j<index && j>=0 && this.floorButtons != null && this.floorButtons[j] != null && this.buttonForFloorIsPressed(j) == true)) ==> result == true);
			//@ decreases this.floorButtons.length - index;
			while (index < this.floorButtons.length) {
				if ((this.buttonForFloorIsPressed(index) == true )) {
					result = true;
				} else if (this.buttonForFloorIsPressed(index) != true) {
					;
				}
				index++;
			}
		} else if (d != 1) {
			//@ loop_invariant index >= 0 && this.getCurrentFloorID() >= 0 && ((\exists int j;(j<=this.getCurrentFloorID() && j>index && this.floorButtons != null && this.getCurrentFloorID() < this.floorButtons.length && index < this.floorButtons.length && this.floorButtons[j] != null && this.buttonForFloorIsPressed(j) == true)) ==> result == true);
			//@ decreases index + 1;
			while (index >= 0) {
				if (this.buttonForFloorIsPressed(index) == true) {
					result = true;
				} else if (this.buttonForFloorIsPressed(index) != true) {
					;
				}
				index--;
			}
		}
		return result;

	}


	/*@
	@ normal_behavior
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures \result == this.currentHeading;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getCurrentDirection() {
		int result = 0;
		result = this.currentHeading;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.currentFloorID>= 0;
	@ ensures \result == this.currentFloorID;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getCurrentFloorID() {
		int result = 0;
		result = this.currentFloorID;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env != null;
	@ ensures \result == this.env;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Environment getEnv() {
		Environment result = null;
		result = this.env;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.blocked != null;
	@ ensures ((this.blocked == true ==> \result == true)&& (this.blocked == false ==> \result == false));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isBlocked() {
		boolean result = false;
		result = this.blocked;
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null && this.persons != null;
	@ ensures \result == this.persons.isEmpty();
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isEmpty() {
		boolean result = false;
		result = this.persons.isEmpty();
		return result;

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.executiveFloor >= 0;
	@ ensures (floorID == this.executiveFloor ==> \result == true) && (floorID != this.executiveFloor ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isExecutiveFloor(int floorID) {
		boolean result = false;
		result = (floorID == this.executiveFloor);
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.executiveFloor >= 0 && this.env != null && this.env.floors != null;
	@ ensures this.env != null && this.env.floors != null && \result == true ==> (\exists int k;(k>=0 && k<this.env.floors.length && this.env.floors[k].thisFloorID == this.executiveFloor && this.env.floors[k].elevatorCall == true));
	@ assignable this.env;
	@*/
	public /*@helper@*/ boolean isExecutiveFloorCalling() {
		boolean result = false;
		int index = 0;
		Floor tmpFloor = null;
		index = 0;
		result = false;
		//@ loop_invariant index >= 0 && this.env.floors != null && tmpFloor != null&& tmpFloor != null&& (\exists int k;((k>=0 && k<index 		&& this.env.getFloor(k).thisFloorID == this.executiveFloor 		&& this.env.getFloor(k).elevatorCall == true) ==> result == true));
		//@ decreases this.env.floors.length - index;
		while (index < this.env.getFloors().length) {
			tmpFloor = this.env.getFloor(index);
			if (tmpFloor.getFloorID() == this.executiveFloor && tmpFloor.hasCall() == true) {
				result = true;
			} else if (!(
tmpFloor.getFloorID() == this.executiveFloor && tmpFloor.hasCall() == true )) {
				;
			}
		}
		return result;

	}

//	/*@
//	@ normal_behavior
//	@ requires p != null && this.persons != null && this.persons.elements != null&& this.floorButtons != null&& p.getWeight() >= 0 && p != null && this.floorButtons != null && this.persons != null;
//	@ ensures (this.old_contains == true ==>	(\result == true && p.isDestinationReached() == true	&& this.persons.contains(p) == false	&& this.weight == this.old_weight - p.getWeight()))&& this.old_contains == false ==> \result == false&& (this.old_contains == true && this.persons.isEmpty() == true) ==> (\forall int k;((k>=0 && k<this.floorButtons.length) ==> this.floorButtons[k] == false));
//	@ assignable this.floorButtons[*],this.old_contains,this.old_weight;
//	@*/
//	public /*@helper@*/ boolean leaveElevator(Person p) {
//		boolean result = false;
//		boolean tmpResult = false;
//		int index = 0;
//		this.old_contains = this.persons.contains(p);
//		this.old_weight = this.weight;
//		result = false;
//		if (this.old_contains == true) {
//			this.leaveElevator__wrappee__Weight(p);
//			index = 0;
//			result = true;
//			if (this.persons.isEmpty() == true) {
//				//@ loop_invariant index >= 0 && result == true && this.old_contains == true && p.isDestinationReached() == true && this.persons.contains(p) == false && this.weight == this.old_weight - p.getWeight() && (\forall int k;((k>=0 && k<index && this.floorButtons != null) ==> this.floorButtons[k] == false ));
//				//@ decreases this.floorButtons.length - index;
//				while (index < this.floorButtons.length) {
//					this.floorButtons[index] = false;
//					index++;
//				}
//			} else if (this.persons.isEmpty() != true) {
//				;
//			}
//		} else if (this.old_contains == false) {
//			;
//		}
//		return result;
//
//	}

	/*@
	@ normal_behavior
	@ requires p != null&& this.persons != null&& this.persons.elements != null && p != null && p.destinationReached == false;
	@ ensures (this.old_contains == true ==> (\result == true && p.isDestinationReached() == true && this.persons.contains(p) == false))&& this.old_contains == false ==> \result == false;
	@ assignable this.old_contains,this.persons.collectionSize,this.persons.elements[*];
	@*/
	public /*@helper@*/ boolean old_leaveElevator__wrappee__Base(Person p) {
		boolean result = false;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.persons.remove(p);
			p.leaveElevator();
			result = true;
		} else if (this.old_contains == false) {
			result = false;
		}
		return result;

	}

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null && p != null;
	@ ensures (this.old_contains == true ==>	(\result == true && p.isDestinationReached() == true	&& this.persons.contains(p) == false	&& this.weight == this.old_weight - p.getWeight()))&& this.old_contains == false ==> \result == false;
	@ assignable this.old_contains,this.old_weight,this.weight;
	@*/
	public /*@helper@*/ boolean leaveElevator__wrappee__Weight(Person p) {
		boolean result = false;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.leaveElevator__wrappee__Base(p);
			this.old_weight = this.weight;
			this.weight-=p.getWeight();
			result = true;
		} else if (this.old_contains == false) {
			;
		}
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null&& floorID >= 0&& floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures this.floorButtons[floorID] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void pressInLiftFloorButton(int floorID) {
		this.pressInLiftFloorButton__wrappee__Base(floorID);

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures (this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null) ==> this.floorButtons[floorID] == true;
	@ assignable this.floorButtons[*];
	@*/
	public /*@helper@*/ void pressInLiftFloorButton__wrappee__Base(int floorID) {
		this.floorButtons[floorID] = true;

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null&& floorID < this.floorButtons.length && this.floorButtons != null;
	@ ensures this.floorButtons[floorID] == false;
	@ assignable this.floorButtons[*];
	@*/
	public /*@helper@*/ void resetFloorButton(int floorID) {
		this.floorButtons[floorID] = false;

	}

	/*@
	@ normal_behavior
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures (this.currentHeading == 0 ==> \result == 1) && (this.currentHeading == 1 ==> \result == 0);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int reverse() {
		int result = 0;
		result = this.currentHeading;
		if (this.currentHeading == 0) {
			result = 1;
		} else if (this.currentHeading == 1) {
			result = 0;
		}
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.weight >= 0 && this.maximumWeight >= 0&& this.currentFloorID >= 0 && this.floorButtons != null && this.currentFloorID < this.floorButtons.length&& this.floorButtons[this.currentFloorID] != null;
	@ ensures ((this.weight > (this.maximumWeight*2/3)) ==> \result == (this.floorButtons[this.currentFloorID]))
	&& ((this.weight <= (this.maximumWeight*2/3)) ==> \result == this.stopRequestedAtCurrentFloor__wrappee__ExecutiveFloor());
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor() {
		boolean result = false;
		result = false;
		if (this.weight > (this.maximumWeight*2/3)) {
			result = (this.floorButtons[this.currentFloorID] == true);
		} else if (this.weight <= (this.maximumWeight*2/3)) {
			result = this.stopRequestedAtCurrentFloor__wrappee__ExecutiveFloor();
		}
		return result;

	}
	

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.floorButtons != null && this.currentFloorID >= 0 && this.currentFloorID < this.env.floors.length&& this.currentFloorID < this.floorButtons.length && this.env.floors[this.currentFloorID] != null && this.env != null && this.env.floors != null;
	@ ensures ((this.env.getFloor(this.currentFloorID).hasCall() == true) ==> \result == true)
&& ((this.floorButtons[this.currentFloorID] == true) ==> \result == true);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor__wrappee__Base() {
		boolean result = false;
		result = (this.env.getFloor(this.currentFloorID).hasCall() == true || this.floorButtons[this.currentFloorID] == true);
		return result;

	}

	/*@
	@ normal_behavior
	@ requires this.currentFloorID >= 0;
	@ ensures (this.isExecutiveFloorCalling() == true && this.isExecutiveFloor(this.currentFloorID) == false) ==> \result == false
&& (this.isExecutiveFloorCalling() == false || this.isExecutiveFloor(this.currentFloorID) == true) ==> \result == this.stopRequestedAtCurrentFloor__wrappee__Base();
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor__wrappee__ExecutiveFloor() {
		boolean result = false;
		result = false;
		if (this.isExecutiveFloorCalling() == true && this.isExecutiveFloor(this.currentFloorID) == false) {
			;
		} else if (this.isExecutiveFloorCalling() == false || this.isExecutiveFloor(this.currentFloorID) == true) {
			result = true;
		}
		return result;

	}

	/*@
	@ normal_behavior
	@ requires envx != null && verbosex != null&& envx.getFloors() != null && envx.getFloors().length >= 0&& envx.getFloors() != null && envx.floors != null;
	@ ensures this.env == envx&& this.verbose == verbosex&& this.currentHeading == 1&& this.currentFloorID == 0&& this.doors == 0&& this.persons != null&& this.persons != null&& this.floorButtons != null&& this.floorButtons != null&& this.floorButtons.length == envx.getFloors().length&& this.blocked == false&& this.executiveFloor == 4;
	@ assignable this.blocked,this.currentFloorID,this.currentHeading,this.doors,this.env,envx,this.executiveFloor,this.floorButtons,this.persons,this.verbose,this.weight;
	@*/
	public /*@helper@*/ Elevator(Environment envx, boolean verbosex) {
		this.verbose = verbosex;
		this.currentHeading = 1;
		this.currentFloorID = 0;
		this.doors = 0;
		this.env = envx;
		this.floorButtons = new boolean[envx.getFloors().length];
		this.persons = new ArrayList();
		this.weight = 0;
		this.blocked = false;
		this.executiveFloor = 4;

	}

	/*@
	@ normal_behavior
	@ requires envx != null&& envx.getFloors() != null && envx.getFloors().length != null && verbosex != null && floorx >= 0 && headingUpx != null && envx.floors.length != null && envx.floors != null;
	@ ensures this.env == envx&& this.verbose == verbosex&& this.currentFloorID == floorx&& (headingUpx == true ==> this.currentHeading == 1)&& (headingUpx == false ==> this.currentHeading == 0)&& this.doors == 0&& this.floorButtons.length == this.env.floors.length&& this.blocked == false&& this.executiveFloor == 4;
	@ assignable this.blocked,this.currentFloorID,this.currentHeading,this.doors,this.env,this.executiveFloor,this.floorButtons,this.persons,this.verbose,this.weight;
	@*/
	public /*@helper@*/ Elevator(Environment envx, boolean verbosex, int floorx, boolean headingUpx) {
		this.verbose = verbosex;
		this.currentHeading = (headingUpx ? 1 : 0);
		this.currentFloorID = floorx;
		this.doors = 0;
		this.env = envx;
		this.floorButtons = new boolean[this.env.getFloors().length];
		this.persons = new ArrayList();
		this.weight = 0;
		this.blocked = false;
		this.executiveFloor = 4;

	}
	

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null&& this.floorButtons != null&& p.getWeight() >= 0 && p.weight >= 0 && this.weight >= 0;
	@ ensures (this.old_contains == true ==>	(\result == true && p.isDestinationReached() == true	&& this.persons.contains(p) == false	&& this.weight == \old(this.weight) - p.getWeight()))&& this.old_contains == false ==> \result == false&& (this.old_contains == true && this.persons.isEmpty() == true) ==> (\forall int k;((k>=0 && k<this.floorButtons.length) ==> this.floorButtons[k] == false));
	@ assignable this.floorButtons[*],this.old_contains;
	@*/
	public /*@helper@*/ boolean leaveElevator(Person p) {
		boolean tmpResult = false;
		int index = 0;
		boolean result = false;
		this.old_contains = this.persons.contains(p);
		result = false;
		if (this.old_contains == true) {
			this.leaveElevator__wrappee__Weight(p);
			index = 0;
			result = true;
			if (this.persons.isEmpty() == true) {
				//@ loop_invariant index >= 0 && result == true && this.old_contains == true && p.isDestinationReached() == true && this.persons.contains(p) == false && this.weight == \old(this.weight) - p.getWeight()&& (\forall int k;((k>=0 && k<index && this.floorButtons != null) ==> this.floorButtons[k] == false ));
				//@ decreases this.floorButtons.length - index;
				while (index < this.floorButtons.length) {
					this.floorButtons[index] = false;
					index++;
				}
			} else if (this.persons.isEmpty() != true) {
				;
			}
		} else if (this.old_contains == false) {
			;
		}
		return result;

	}
	

	/*@
	@ normal_behavior
	@ requires p != null&& this.persons != null&& this.persons.elements != null && p != null && p.destinationReached == false;
	@ ensures (this.old_contains == true ==> (\result == true && p.isDestinationReached() == true && this.persons.contains(p) == false))&& this.old_contains == false ==> \result == false;
	@ assignable this.old_contains,this.persons.collectionSize,this.persons.elements[*];
	@*/
	public /*@helper@*/ boolean leaveElevator__wrappee__Base(Person p) {
		boolean result = false;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.persons.remove(p);
			p.leaveElevator();
			result = true;
		} else if (this.old_contains == false) {
			result = false;
		}
		return result;

	}
}