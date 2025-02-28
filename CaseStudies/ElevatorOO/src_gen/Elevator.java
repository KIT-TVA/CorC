public class Elevator {

    private /*@spec_public@*/  int old_currentFloorID;
    public boolean[] floorButtons;
    private /*@spec_public@*/  boolean verbose;
    private /*@spec_public@*/  int currentHeading;
    private /*@spec_public@*/  int currentFloorID;
    private /*@spec_public@*/  int doors;
    private /*@spec_public@*/  Environment env;
    private /*@spec_public@*/  ArrayList persons;
    private /*@spec_public@*/  boolean blocked;
    private /*@spec_public@*/  boolean old_contains;
    private /*@spec_public@*/  int old_weight;
    private /*@spec_public@*/  int weight;
    private /*@spec_public@*/  int maximumWeight;
    private /*@spec_public@*/  int executiveFloor;



	/*@
	@ normal_behavior
	@ requires this.doors >= 0 && this.doors <= 1;
	@ ensures (this.doors == 0 ==> \result == true)&& (this.doors == 1 ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean areDoorsOpen() {
		boolean ret;
		ret = (this.doors == 0);
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null && floorID < this.floorButtons.length;
	@ ensures \result == this.floorButtons[floorID];
	@ assignable \nothing;
	@*/
	public boolean buttonForFloorIsPressed(int floorID) {
		boolean ret;
		ret = this.floorButtons[floorID];
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires dir >= 0 && dir <= 1 && this.currentHeading >= 0 && this.currentFloorID >= 0&& this.env != null && this.env.floors != null;
	@ ensures ((this.currentHeading == 1 && this.env.isTopFloor(this.currentFloorID) == true) ==> this.currentHeading == this.reverse()) && ((this.currentHeading == 0 && this.currentFloorID == 0) ==> this.currentHeading == this.reverse())&& ((this.currentHeading == 1 ) ==> this.currentFloorID == this.currentFloorID + 1)&& ((this.currentHeading != 1 ) ==> this.currentFloorID == this.currentFloorID - 1);
	@ assignable currentHeading,currentFloorID;
	@*/
	public void continueInDirection(int dir) {
		this.currentHeading = dir;
		if (this.currentHeading == 1) {
			if (this.env.isTopFloor(this.currentFloorID) == true) {
				this.currentHeading = this.reverse();
			} else if (this.env.isTopFloor(this.currentFloorID) == false) {
				;
			}
		} else if (this.currentHeading != 0) {
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
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures \result == this.currentHeading;
	@ assignable \nothing;
	@*/
	public int getCurrentDirection() {
		int ret;
		ret = this.currentHeading;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.currentFloorID>= 0;
	@ ensures \result == this.currentFloorID;
	@ assignable \nothing;
	@*/
	public int getCurrentFloorID() {
		int ret;
		ret = this.currentFloorID;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env != null;
	@ ensures \result == this.env;
	@ assignable \nothing;
	@*/
	public Environment getEnv() {
		Environment ret;
		ret = this.env;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.blocked != null;
	@ ensures ((this.blocked == true ==> \result == true)&& (this.blocked == false ==> \result == false));
	@ assignable \nothing;
	@*/
	public boolean isBlocked() {
		boolean ret;
		ret = this.blocked;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null;
	@ ensures \result == this.persons.isEmpty();
	@ assignable \nothing;
	@*/
	public boolean isEmpty() {
		boolean ret;
		ret = this.persons.isEmpty();
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.floorButtons != null&& floorID < this.floorButtons.length && this.floorButtons != null;
	@ ensures this.floorButtons[floorID] == false;
	@ assignable \nothing;
	@*/
	public void resetFloorButton(int floorID) {
		this.floorButtons[floorID] = false;

	}

	/*@
	@ normal_behavior
	@ requires this.currentHeading >= 0 && this.currentHeading <= 1;
	@ ensures (this.currentHeading == 0 ==> \result == 1) && (this.currentHeading == 1 ==> \result == 0);
	@ assignable \nothing;
	@*/
	public int reverse() {
		int result;
		int ret;
		result = this.currentHeading;
		if (this.currentHeading == 0) {
			result = 1;
		} else if (this.currentHeading == 1) {
			result = 0;
		}
		ret = result;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.getDestination()>= 0 && p.getDestination() < this.floorButtons.length;
	@ ensures this.persons.contains(p) == true&& this.floorButtons[p.getDestination()] == true&& this.persons.size() == \old(this.persons.size()) + 1;
	@ assignable \nothing;
	@*/
	public void original_enterElevator(Person p) {
		p.enterElevator(this);
		this.persons.add(p);

	}

	/*@
	@ normal_behavior
	@ requires this.persons != null &&this.persons.elements != null &&  this.floorButtons != null && p != null && p.getDestination() >= 0 && p.getDestination() < this.floorButtons.length  && p.getWeight() >= 0;
	@ ensures this.weight == \old(this.weight) + p.getWeight() && this.persons.contains(p) == true && this.floorButtons[p.getDestination()] == true;
	@ assignable weight;
	@*/
	public void enterElevator(Person p) {
		this.original_enterElevator(p);
		this.weight = this.weight + p.getWeight();

	}

	/*@
	@ normal_behavior
	@ requires p != null&& this.persons != null&& this.persons.elements != null;
	@ ensures (this.old_contains == true ==> (\result == true && p.isDestinationReached() == true && this.persons.contains(p) == false))&& this.old_contains == false ==> \result == false;
	@ assignable p,old_contains,persons;
	@*/
	public boolean original_original_leaveElevator(Person p) {
		boolean result;
		boolean ret;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.persons.remove(p);
			p.leaveElevator();
			result = true;
		} else if (this.old_contains == false) {
			result = false;
		}
		ret = result;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null;
	@ ensures (this.old_contains == true ==>(\result == true && p.isDestinationReached() == true&& this.persons.contains(p) == false&& this.weight == \old(this.weight) - p.getWeight()))&& this.old_contains == false ==> \result == false;
	@ assignable old_contains,p,weight;
	@*/
	public boolean original_leaveElevator(Person p) {
		boolean result;
		boolean ret;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.original_original_leaveElevator(p);
			this.weight = this.weight -p.getWeight();
			result = true;
		} else if (this.old_contains == false) {
			;
		}
		ret = result;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires p != null && this.persons != null && this.persons.elements != null;
	@ ensures (this.old_contains == true ==>(\result == true && p.isDestinationReached() == true&& this.persons.contains(p) == false&& this.weight == \old(this.weight) - p.getWeight()))&& this.old_contains == false ==> \result == false;
	@ assignable old_contains,weight;
	@*/
	public boolean leaveElevator(Person p) {
		boolean result;
		boolean ret;
		result = false;
		this.old_contains = this.persons.contains(p);
		if (this.old_contains == true) {
			this.original_leaveElevator(p);
			this.weight = this.weight -p.getWeight();
			result = true;
		} else if (this.old_contains == false) {
			;
		}
		ret = result;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures (this.floorButtons != null && floorID >= 0 && floorID < this.floorButtons.length&& this.floorButtons[floorID] != null) ==> this.floorButtons[floorID] == true;
	@ assignable \nothing;
	@*/
	public void original_pressInLiftFloorButton(int floorID) {
		this.floorButtons[floorID] = true;

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null&& floorID >= 0&& floorID < this.floorButtons.length&& this.floorButtons[floorID] != null;
	@ ensures this.floorButtons[floorID] == true;
	@ assignable \nothing;
	@*/
	public void pressInLiftFloorButton(int floorID) {
		this.original_pressInLiftFloorButton(floorID);

	}

	/*@
	@ normal_behavior
	@ requires floorID >= 0 && this.executiveFloor >= 0;
	@ ensures (floorID == this.executiveFloor ==> \result == true) && (floorID != this.executiveFloor ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean isExecutiveFloor(int floorID) {
		boolean ret;
		ret = (floorID == this.executiveFloor);
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.executiveFloor >= 0 && this.env != null && this.env.floors != null;
	@ ensures this.env != null && this.env.floors != null && result == true ==> (\exists int k;((k>=0 && k<this.env.floors.length && this.env.floors[k].getFloorID() == this.executiveFloor && this.env.floors[k].hasCall() == true)));
	@ assignable \nothing;
	@*/
	public boolean isExecutiveFloorCalling() {
		boolean result;
		int index;
		Floor tmpFloor;
		boolean ret;
		result = false;
		index = 0;
		tmpFloor = null;
		index = 0;
		result = false;
		while (index < this.env.getFloors().length) {
			tmpFloor = this.env.getFloor(index);
			if (tmpFloor.getFloorID() == this.executiveFloor&&tmpFloor.hasCall() == true) {
				result = true;
			} else if (!(tmpFloor.getFloorID() == this.executiveFloor&&tmpFloor.hasCall() == true)) {
				;
			}
		}
		ret=result;
		ret = ret;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.env != null && this.env.floors != null && this.floorButtons != null && this.currentFloorID >= 0 && this.currentFloorID < this.env.floors.length&& this.currentFloorID < this.floorButtons.length && this.env.floors[this.currentFloorID] != null;
	@ ensures ((this.env.getFloor(this.currentFloorID).hasCall() == true)==> \result == true)&& ((this.floorButtons[this.currentFloorID] == true) ==> \result == true);
	@ assignable env;
	@*/
	public boolean original_original_stopRequestedAtCurrentFloor() {
		boolean ret;
		ret = (this.env.getFloor(this.currentFloorID).hasCall() == true || this.floorButtons[this.currentFloorID] == true);
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.floorButtons != null && d >= 0 && d<= 1&& this.currentFloorID >= 0 && d<=1 && this.floorButtons != null && this != null;
	@ ensures (this.floorButtons != null && d == 1 && (\exists int k;((k>=this.getCurrentFloorID() && k<this.floorButtons.length && this.buttonForFloorIsPressed(k) == true)))) ==> result == true&& (this.floorButtons != null && d == 0 && (\exists int k;((k<=this.getCurrentFloorID() && this.buttonForFloorIsPressed(k) == true)))) ==> result == true;
	@ assignable \nothing;
	@*/
	public boolean existInLiftCallsInDirection(int d) {
		int index;
		boolean result;
		boolean ret;
		result = false;
		index = 0;
		index = this.getCurrentFloorID();
		result = false;
		if (d == 1) {
			while (index < this.floorButtons.length) {
				if ((this.buttonForFloorIsPressed(index) == true)) {
					result = true;
				} else if (this.buttonForFloorIsPressed(index) != true) {
					;
				}
				index++;
			}
		} else if (d != 1) {
			while (index >= 0) {
				if (this.buttonForFloorIsPressed(index) == true) {
					result = true;
				} else if (this.buttonForFloorIsPressed(index) != true) {
					;
				}
				index--;
			}
		}
		result = result;
		ret = result ;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.currentFloorID >= 0;
	@ ensures (this.isExecutiveFloorCalling() == true && this.isExecutiveFloor(this.currentFloorID) == false) ==> \result == false&& (this.isExecutiveFloorCalling() == false || this.isExecutiveFloor(this.currentFloorID) == true) ==> \result == this.original_original_stopRequestedAtCurrentFloor();
	@ assignable \nothing;
	@*/
	public boolean original_stopRequestedAtCurrentFloor() {
		boolean result;
		boolean ret;
		result = false;
		if (this.isExecutiveFloorCalling() == true && this.isExecutiveFloor(this.currentFloorID) == false) {
			;
		} else if (this.isExecutiveFloorCalling() == false || this.isExecutiveFloor(this.currentFloorID) == true) {
			result = true;
		}
		ret = result;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires this.weight >= 0 && this.maximumWeight >= 0&& this.currentFloorID >= 0 && this.floorButtons != null && this.currentFloorID < this.floorButtons.length&& this.floorButtons[this.currentFloorID] != null;
	@ ensures ((this.weight > (this.maximumWeight*2/3)) ==> result == (this.floorButtons[this.currentFloorID]))&& ((this.weight <= (this.maximumWeight*2/3)) ==> result == this.original_stopRequestedAtCurrentFloor());
	@ assignable \nothing;
	@*/
	public boolean stopRequestedAtCurrentFloor() {
		boolean result;
		boolean ret;
		result = false;
		result = false;
		if (this.weight > (this.maximumWeight * 2 / 3)) {
			result = (this.floorButtons[this.currentFloorID] == true);
		} else if (this.weight <= (this.maximumWeight * 2 / 3)) {
			result = this.original_stopRequestedAtCurrentFloor();
		}
		result = result;
		ret = result ;
		return ret;

	}
}