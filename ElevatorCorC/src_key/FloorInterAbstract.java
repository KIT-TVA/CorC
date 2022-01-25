public class FloorInterAbstract{

	public ArrayList waiting;
	public boolean elevatorCall;
	public int thisFloorID;
	public Environment env;

	/*@
	@ normal_behavior
	@ requires true && person != null && this.waiting != null && (person instanceof Person) && this.waiting.elements != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true && this.waiting.collectionSize == \old(this.waiting.collectionSize);
	@ assignable this.waiting.collectionSize,this.waiting.elements[*],this.waiting;
	@*/
	public /*@helper@*/ void addWaitingPerson(Person person);

	/*@
	@ normal_behavior
	@ requires true && this.elevatorCall != null;
	@ ensures this.elevatorCall == true;
	@ assignable this.elevatorCall;
	@*/
	public /*@helper@*/ void callElevator();

	/*@
	@ normal_behavior
	@ requires true && this.thisFloorID>= 0 && this != null;
	@ ensures \result == this.thisFloorID;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getFloorID();

	/*@
	@ normal_behavior
	@ requires true && this.elevatorCall != null;
	@ ensures (this.elevatorCall == true ==> \result == true)&& (this.elevatorCall == false ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean hasCall();

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.elevatorCall == false;
	@ assignable this.elevatorCall;
	@*/
	public /*@helper@*/ void reset();

	/*@
	@ normal_behavior
	@ requires true && e != null && this.hasCall() != null && this.waiting.elements != null && e.persons.elements != null && this.waiting.collectionSize >= 0 && e.floorButtons != null && e.persons != null && e.persons.collectionSize >= 0 && this.waiting != null;
	@ ensures this.waiting.isEmpty() == true && this.hasCall() == false;
	@ assignable this.waiting;
	@*/
	public /*@helper@*/ void processWaitingPersons(Elevator e);
	
	/*@
	@ normal_behavior
	@ requires envx != null&& idx >= 0;
	@ ensures envx != null && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public /*@helper@*/ void createFloor(Environment envx, int idx);

	/*@
	@ normal_behavior
	@ requires envx != null && idx >= 0 ;
	@ ensures envx != null && (envx instanceof Environment) && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null && this.waiting != null;
	@ assignable this.env,this.thisFloorID,this.waiting;
	@*/
	public /*@helper@*/ void FloorInterAbstract(Environment envx, int idx);
}