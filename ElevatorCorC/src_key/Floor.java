public class Floor{

	private /*@spec_public@*/ ArrayList waiting;
	private /*@spec_public@*/ boolean elevatorCall;
	private /*@spec_public@*/ int thisFloorID;
	private /*@spec_public@*/ Environment env;

	/*@
	@ normal_behavior
	@ requires true && person != null && this.waiting != null && (person instanceof Person) && this.waiting.elements != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true && this.waiting.collectionSize == \old(this.waiting.collectionSize);
	@ assignable this.waiting.collectionSize,this.waiting.elements[*],this.waiting;
	@*/
	public /*@helper@*/ void addWaitingPerson(Person person) {
		this.callElevator();
		this.waiting.add(person);

	}
	
	/*@
	@ normal_behavior
	@ requires true && person != null && this.waiting != null && (person instanceof Person) && this.waiting.elements != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true && this.waiting.collectionSize == \old(this.waiting.collectionSize);
	@ assignable this.waiting.collectionSize,this.waiting.elements[*],this.waiting;
	@*/
	public /*@helper@*/ void addWaitingPerson(PersonInter person) {
		this.callElevator();
		//this.waiting.add(person);

	}
	
	/*@
	@ normal_behavior
	@ requires true && person != null && this.waiting != null && (person instanceof Person) && this.waiting.elements != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true && this.waiting.collectionSize == \old(this.waiting.collectionSize);
	@ assignable this.waiting.collectionSize,this.waiting.elements[*],this.waiting;
	@*/
	public /*@helper@*/ void addWaitingPerson(ComposedPerson person) {
		this.callElevator();
		//this.waiting.add(person);

	}

	/*@
	@ normal_behavior
	@ requires true && this.elevatorCall != null;
	@ ensures this.elevatorCall == true;
	@ assignable this.elevatorCall;
	@*/
	public /*@helper@*/ void callElevator() {
		this.elevatorCall = true;

	}

	public Floor() {}

	/*@
	@ normal_behavior
	@ requires true && this.thisFloorID>= 0 && this != null;
	@ ensures \result == this.thisFloorID;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getFloorID() {
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
	public /*@helper@*/ boolean hasCall() {
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
	public /*@helper@*/ void reset() {
		this.elevatorCall = false;

	}

	/*@
	@ normal_behavior
	@ requires true && e != null && this.hasCall() != null && this.waiting.elements != null && e.persons.elements != null && this.waiting.collectionSize >= 0 && e.floorButtons != null && e.persons != null && e.persons.collectionSize >= 0 && this.waiting != null;
	@ ensures this.waiting.isEmpty() == true && this.hasCall() == false;
	@ assignable this.waiting;
	@*/
	public /*@helper@*/ void processWaitingPersons(Elevator e) {
		int index = 0;
		Person tmpPerson = null;
		index = 0;
		//@ loop_invariant index>=0 && this.waiting.elements != e.persons.elements && e.floorButtons != null && (\forall int i;(((i>=0 && i<index &&  i < this.waiting.collectionSize && this.waiting.get(i) != null && e.persons.contains(this.waiting.get(i)) == false) ==> (e.persons.contains(this.waiting.get(i)) == true && e.floorButtons[this.waiting.get(i).destination] == true ))));
		//@ decreases this.waiting.collectionSize - index;
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
	public /*@helper@*/ void createFloor(Environment envx, int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();

	}
	
	/*@
	@ normal_behavior
	@ requires envx != null&& idx >= 0;
	@ ensures envx != null && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public /*@helper@*/ void createFloor(ComposedEnvironment envx, int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();

	}
	
	/*@
	@ normal_behavior
	@ requires envx != null&& idx >= 0;
	@ ensures envx != null && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public /*@helper@*/ void createFloor(EnvironmentInter envx, int idx) {
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
	public /*@helper@*/ Floor(Environment envx, int idx) {
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
	public /*@helper@*/ Floor(ComposedEnvironment envx, int idx) {
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
	public /*@helper@*/ Floor(EnvironmentInter envx, int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();

	}
}