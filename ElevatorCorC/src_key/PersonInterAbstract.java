public class PersonInterAbstract{

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
	public /*@helper@*/ void createPerson(String namex, int weightx, int originx, int destinationx, Environment envx);

	/*@
	@ normal_behavior
	@ requires e != null&& e != null&& this.destination >= 0 && this.destination < e.floorButtons.length&& e.floorButtons != null&& e.floorButtons[this.destination] != null && e != null && e.floorButtons != null;
	@ ensures e.floorButtons[this.destination] == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ void enterElevator(Elevator e);


	/*@
	@ normal_behavior
	@ requires true && this.name != null;
	@ ensures this.name.equals(\result) == true;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getName();

	/*@
	@ normal_behavior
	@ requires true && this.origin >= 0;
	@ ensures \result == this.origin && \result >= 0;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getOrigin();

	/*@
	@ normal_behavior
	@ requires this.weight >= 0;
	@ ensures \result == this.weight;
	@ assignable \nothing;
	@*/
	public /*@pure helper@*/ int getWeight();

	/*@
	@ normal_behavior
	@ requires true && this.destinationReached != null;
	@ ensures \result != null && \result == this.destinationReached&& (this.destinationReached == true ==> \result == true)&& (this.destinationReached == false ==> \result == false);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ boolean isDestinationReached();
	
	/*@
	@ normal_behavior
	@ requires this.destinationReached == false && this.destinationReached != null;
	@ ensures this.destinationReached == true;
	@ assignable this.destinationReached;
	@*/
	public /*@helper@*/ void leaveElevator();

	/*@
	@ normal_behavior
	@ requires true && this.destination >= 0;
	@ ensures \result == this.destination && (\result instanceof int);
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getDestination();

	/*@
	@ normal_behavior
	@ requires namex != null&& envx.floors != null&& destinationx >= 0&& envx != null&& weightx >= 0&& originx >= 0&& originx < envx.floors.length&& envx.floors[originx] != null&& envx.floors[originx].waiting != null && envx.floors[originx].waiting.elements != null;
	@ ensures this.weight == weightx&& this.origin == originx&& this.destination == destinationx&& this.destinationReached == false&& this.name.equals(namex) == true;
	@ assignable this.destination,this.destinationReached,this.name,this.origin,this.weight;
	@*/
	public /*@helper@*/ void PersonInterAbstractC(String namex, int weightx, int originx, int destinationx, Environment envx);
	
}