public class Floor {

    public ArrayList waiting;
    private /*@spec_public@*/  boolean elevatorCall;
    private /*@spec_public@*/  int thisFloorID;
    public Environment env;



	/*@
	@ normal_behavior
	@ requires person != null;
	@ ensures this.hasCall() == true && this.waiting.contains(person) == true;
	@ assignable \nothing;
	@*/
	public void addWaitingPerson(Person person) {
		this.callElevator();
		this.waiting.add(person);

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.elevatorCall == true;
	@ assignable elevatorCall;
	@*/
	public void callElevator() {
		this.elevatorCall = true;

	}

	/*@
	@ normal_behavior
	@ requires envx != null&& idx >= 0;
	@ ensures envx != null && idx != null && this.thisFloorID == idx && this.env == envx && this.waiting != null;
	@ assignable env,thisFloorID,waiting;
	@*/
	public void createFloor(Environment envx, int idx) {
		this.thisFloorID = idx;
		this.env = envx;
		this.waiting = new ArrayList();

	}

	/*@
	@ normal_behavior
	@ requires this.thisFloorID>= 0;
	@ ensures \result == this.thisFloorID;
	@ assignable \nothing;
	@*/
	public int getFloorID() {
		int ret;
		ret = this.thisFloorID;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (this.elevatorCall == true ==> \result == true)&& (this.elevatorCall == false ==> \result == false);
	@ assignable \nothing;
	@*/
	public boolean hasCall() {
		boolean ret;
		ret = this.elevatorCall;
		return ret;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.waiting.isEmpty() == true && this.hasCall() == false;
	@ assignable \nothing;
	@*/
	public void processWaitingPersons(Elevator e) {
		Person tmpPerson;
		int index;
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
	@ requires true;
	@ ensures this.elevatorCall == false;
	@ assignable elevatorCall;
	@*/
	public void reset() {
		this.elevatorCall = false;

	}

// Code from C:/Users/mko/Documents/ISF/0_corc2.0_kodetzki/runtime-EclipseApplication/ElevatorOO/src/Floor_helper.java
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
// End of code from C:/Users/mko/Documents/ISF/0_corc2.0_kodetzki/runtime-EclipseApplication/ElevatorOO/src/Floor_helper.java
}