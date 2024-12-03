public class Floor_helper {
	public ArrayList waiting; // Base
    private /*@spec_public@*/  boolean elevatorCall; // Base
    private /*@spec_public@*/  int thisFloorID; // Base
    public Environment env; // Base

	
	
    //begin
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
}