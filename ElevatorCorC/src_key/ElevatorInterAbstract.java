public class ElevatorInterAbstract{

	public boolean[] floorButtons;
	public boolean verbose;
	public int currentHeading;
	public int currentFloorID;
	public int doors;
	public Environment env;
	public ArrayList persons;
	public int weight;
	public boolean blocked;
	public int executiveFloor;
	public boolean old_contains;
	public int old_weight;
	public int old_currentFloorID;
	public int old_collectionSize;
	public int maximumWeight;

	/*@
	@ normal_behavior
	@ requires this.doors >= 0 && this.doors <= 1 && this.doors >=0 && this.doors <=1;
	@ ensures (this.doors == 0 ==> \result == true)&& (this.doors == 1 ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean areDoorsOpen();

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null && floorID < this.floorButtons.length && this.floorButtons != null;
	@ ensures \result == this.floorButtons[floorID];
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean buttonForFloorIsPressed(int floorID);

	/*@
	@ normal_behavior
	@ requires dir >= 0 && dir <= 1 && this.currentHeading >= 0 && this.currentFloorID >= 0&& this.env != null && this.env.floors != null && this.env != null && this.env.floors != null;
	@ ensures ((this.currentHeading == 1 && this.env.isTopFloor(this.currentFloorID) == true) ==> this.currentHeading == this.reverse()) && ((this.currentHeading == 0 && this.currentFloorID == 0) ==> this.currentHeading == this.reverse())&& ((this.currentHeading == 1 ) ==> this.currentFloorID == this.currentFloorID + 1)&& ((this.currentHeading != 1 ) ==> this.currentFloorID == this.currentFloorID - 1);
	@ assignable this.currentHeading,this.old_currentFloorID;
	@*/
	public /*@helper@*/ void continueInDirection__wrappee__Base(int dir);

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.destination>= 0 && p.destination < this.floorButtons.length && p != null && (p instanceof Person);
	@ ensures this.persons.contains(p) == true&& this.floorButtons[p.destination] == true&& this.persons.size() ==  \old(this.persons.size()) + 1;
	@ assignable this.persons.collectionSize,this.persons.elements[*];
	@*/
	public /*@helper@*/ void enterElevator__wrappee__Base(Person p);	

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.destination >= 0 && p.destination < this.floorButtons.length  && p.weight >= 0 && p.getWeight() >= 0 && p != null && this.weight >= 0;
	@ ensures this.weight == \old(this.weight) + p.weight && this.persons.contains(p) == true && this.floorButtons[p.destination] == true;
	@ assignable this.weight;
	@*/
	public /*@helper@*/ void enterElevator(Person p);

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && d >= 0 && d<= 1&& this.currentFloorID >= 0 && d<=1 && this.floorButtons != null && this != null;
	@ ensures (this.floorButtons != null && d == 1 && (\exists int k;(k>=this.getCurrentFloorID() && k<this.floorButtons.length && this.buttonForFloorIsPressed(k) == true))) ==> \result == true
	&& (this.floorButtons != null && d == 0 && (\exists int k;(k<=this.getCurrentFloorID() && this.buttonForFloorIsPressed(k) == true))) ==> \result == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean existInLiftCallsInDirection(int d);


	/*@
	@ normal_behavior
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures \result == this.currentHeading;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getCurrentDirection();
	
	/*@
	@ normal_behavior
	@ requires this.currentFloorID>= 0;
	@ ensures \result == this.currentFloorID;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getCurrentFloorID();

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env != null;
	@ ensures \result == this.env;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Environment getEnv();

	/*@
	@ normal_behavior
	@ requires this.blocked != null;
	@ ensures ((this.blocked == true ==> \result == true)&& (this.blocked == false ==> \result == false));
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isBlocked();

	/*@
	@ normal_behavior
	@ requires this.persons != null && this.persons != null;
	@ ensures \result == this.persons.isEmpty();
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isEmpty();

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.executiveFloor >= 0;
	@ ensures (floorID == this.executiveFloor ==> \result == true) && (floorID != this.executiveFloor ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isExecutiveFloor(int floorID);

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.executiveFloor >= 0 && this.env != null && this.env.floors != null;
	@ ensures this.env != null && this.env.floors != null && \result == true ==> (\exists int k;(k>=0 && k<this.env.floors.length && this.env.floors[k].thisFloorID == this.executiveFloor && this.env.floors[k].elevatorCall == true));
	@ assignable this.env;
	@*/
	public /*@helper@*/ boolean isExecutiveFloorCalling();

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
	public /*@helper@*/ boolean old_leaveElevator__wrappee__Base(Person p);

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null && p != null;
	@ ensures (this.old_contains == true ==>	(\result == true && p.isDestinationReached() == true	&& this.persons.contains(p) == false	&& this.weight == this.old_weight - p.getWeight()))&& this.old_contains == false ==> \result == false;
	@ assignable this.old_contains,this.old_weight,this.weight;
	@*/
	public /*@helper@*/ boolean leaveElevator__wrappee__Weight(Person p);

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null&& floorID >= 0&& floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures this.floorButtons[floorID] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void pressInLiftFloorButton(int floorID);

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures (this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null) ==> this.floorButtons[floorID] == true;
	@ assignable this.floorButtons[*];
	@*/
	public /*@helper@*/ void pressInLiftFloorButton__wrappee__Base(int floorID);

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null&& floorID < this.floorButtons.length && this.floorButtons != null;
	@ ensures this.floorButtons[floorID] == false;
	@ assignable this.floorButtons[*];
	@*/
	public /*@helper@*/ void resetFloorButton(int floorID);

	/*@
	@ normal_behavior
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures (this.currentHeading == 0 ==> \result == 1) && (this.currentHeading == 1 ==> \result == 0);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int reverse();

	/*@
	@ normal_behavior
	@ requires this.weight >= 0 && this.maximumWeight >= 0&& this.currentFloorID >= 0 && this.floorButtons != null && this.currentFloorID < this.floorButtons.length&& this.floorButtons[this.currentFloorID] != null;
	@ ensures ((this.weight > (this.maximumWeight*2/3)) ==> \result == (this.floorButtons[this.currentFloorID]))
	&& ((this.weight <= (this.maximumWeight*2/3)) ==> \result == this.stopRequestedAtCurrentFloor__wrappee__ExecutiveFloor());
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor();
	

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.floorButtons != null && this.currentFloorID >= 0 && this.currentFloorID < this.env.floors.length&& this.currentFloorID < this.floorButtons.length && this.env.floors[this.currentFloorID] != null && this.env != null && this.env.floors != null;
	@ ensures ((this.env.getFloor(this.currentFloorID).hasCall() == true) ==> \result == true)
&& ((this.floorButtons[this.currentFloorID] == true) ==> \result == true);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor__wrappee__Base();

	/*@
	@ normal_behavior
	@ requires this.currentFloorID >= 0;
	@ ensures (this.isExecutiveFloorCalling() == true && this.isExecutiveFloor(this.currentFloorID) == false) ==> \result == false
&& (this.isExecutiveFloorCalling() == false || this.isExecutiveFloor(this.currentFloorID) == true) ==> \result == this.stopRequestedAtCurrentFloor__wrappee__Base();
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean stopRequestedAtCurrentFloor__wrappee__ExecutiveFloor();

	/*@
	@ normal_behavior
	@ requires envx != null&& envx.getFloors() != null && envx.getFloors().length != null && verbosex != null && floorx >= 0 && headingUpx != null && envx.floors.length != null && envx.floors != null;
	@ ensures this.env == envx&& this.verbose == verbosex&& this.currentFloorID == floorx&& (headingUpx == true ==> this.currentHeading == 1)&& (headingUpx == false ==> this.currentHeading == 0)&& this.doors == 0&& this.floorButtons.length == this.env.floors.length&& this.blocked == false&& this.executiveFloor == 4;
	@ assignable this.blocked,this.currentFloorID,this.currentHeading,this.doors,this.env,this.executiveFloor,this.floorButtons,this.persons,this.verbose,this.weight;
	@*/
	public /*@helper@*/ void ElevatorInterAbstractC(Environment envx, boolean verbosex, int floorx, boolean headingUpx);
	

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null&& this.floorButtons != null&& p.getWeight() >= 0 && p.weight >= 0 && this.weight >= 0;
	@ ensures (this.old_contains == true ==>	(\result == true && p.isDestinationReached() == true	&& this.persons.contains(p) == false	&& this.weight == \old(this.weight) - p.getWeight()))&& this.old_contains == false ==> \result == false&& (this.old_contains == true && this.persons.isEmpty() == true) ==> (\forall int k;((k>=0 && k<this.floorButtons.length) ==> this.floorButtons[k] == false));
	@ assignable this.floorButtons[*],this.old_contains;
	@*/
	public /*@helper@*/ boolean leaveElevator(Person p);
	

	/*@
	@ normal_behavior
	@ requires p != null&& this.persons != null&& this.persons.elements != null && p != null && p.destinationReached == false;
	@ ensures (this.old_contains == true ==> (\result == true && p.isDestinationReached() == true && this.persons.contains(p) == false))&& this.old_contains == false ==> \result == false;
	@ assignable this.old_contains,this.persons.collectionSize,this.persons.elements[*];
	@*/
	public /*@helper@*/ boolean leaveElevator__wrappee__Base(Person p);
}