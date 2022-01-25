public class ComposedFloor {

public boolean[]floorButtons;
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
private Floor[]floors;
public ArrayList waiting;
public boolean elevatorCall;
public int thisFloorID;


	/*@
	@ normal_behavior
	@ requires true && person != null && this.waiting != null && (person instanceof Person) && this.waiting.elements != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true && this.waiting.collectionSize == \old(this.waiting.collectionSize);
	@ assignable this.waiting.collectionSize,this.waiting.elements[*],this.waiting;
	@*/
	public void addWaitingPerson(Person person) {
		this.callElevator();
		this.waiting.add(person);
	}


	/*@
	@ normal_behavior
	@ requires true && this.elevatorCall != null;
	@ ensures this.elevatorCall == true;
	@ assignable this.elevatorCall;
	@*/
	public void callElevator() {
		this.elevatorCall = true;
	}



	/*@
	@ normal_behavior
	@ requires true && this.thisFloorID>= 0 && this != null;
	@ ensures \result == this.thisFloorID;
	@ assignable \nothing;
	@*/
	public int getFloorID() {
		int floorid = 0;
		floorid = this.thisFloorID;
		return floorid;
	}


	/*@
	@ normal_behavior
	@ requires true && this.elevatorCall != null;
	@ ensures (this.elevatorCall == true ==> \result == true)&& (this.elevatorCall == false ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean hasCall() {
		boolean result = false;
		result = this.elevatorCall;
		return result;
	}


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.elevatorCall == false;
	@ assignable this.elevatorCall;
	@*/
	public void reset() {
		this.elevatorCall = false;
	}


	/*@
	@ normal_behavior
	@ requires true && e != null && this.hasCall() != null && this.waiting.elements != null && e.persons.elements != null && this.waiting.collectionSize >= 0 && e.floorButtons != null && e.persons != null && e.persons.collectionSize >= 0 && this.waiting != null;
	@ ensures this.waiting.isEmpty() == true && this.hasCall() == false;
	@ assignable this.waiting;
	@*/
	public void processWaitingPersons(Elevator e) {
		int index = 0;
		Person tmpPerson = null;
		index = 0;
		while (index < this.waiting.size()) {
			e.enterElevator(this.waiting.get(index));
			index++;
		}
		this.waiting.clear();
		this.reset();
	}

	
	/*@
	@ normal_behavior
	@ requires envx != null&& idx >= 0;
	@ ensures envx != null && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public void createFloor(Environment envx,int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();
	}


	/*@
	@ normal_behavior
	@ requires envx != null && idx >= 0 ;
	@ ensures envx != null && (envx instanceof Environment) && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public void FloorInter(Environment envx,int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();
	}


	/*@
	@ normal_behavior
	@ requires envx != null && idx >= 0 ;
	@ ensures envx != null && (envx instanceof Environment) && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public void FloorInterAbstract(Environment envx, int idx);
}