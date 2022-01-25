
public interface IntListSmallStepInterAbstract {
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
     @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
     @ assignable \everything;
	 @*/
	public void push(int[] data,  int newTop);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
    @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
    @ assignable \everything;
	 @*/
	public void pushFront(int[] data, int newTop);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
    @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
    @ assignable \everything;
	 @*/
	public void pushStartFront(int[] data, int newTop);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
   @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
   @ assignable \everything;
	 @*/
	public void pushStart(int[] data, int newTop);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0 && tmp.length == data.length + 1;
	 @ ensures tmp.length == data.length + 1 && (\forall int j; 0 <= j & j < data.length; data[j] == tmp[j]);
    @ assignable \everything;
	 @*/
	public void pushLoop(int[] data, int[] tmp);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures tmp[i] == data[i];
   @ assignable \nothing;
	 @*/
	public void pushAssign(int[] data, int[] tmp, int i);
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures tmp[i] == data[i];
  @ assignable \nothing;
	 @*/
	public void pushAssignFront(int[] data, int[] tmp, int i);
}
