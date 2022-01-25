public class Composed {
	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
     @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
     @ assignable \everything;
	 @*/
	public void push(int[]data,int newTop) {
		int[]tmp = new int[data.length + 1];
		tmp[tmp.length - 1] = newTop;
		for (int i = 0;i < data.length;i++) {
			tmp[i] = data[i];
		}
		data = tmp;
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
    @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
    @ assignable \everything;
	 @*/
	public void pushFront(int[]data,int newTop) {
		int[]tmp = new int[data.length + 1];
		tmp[0] = newTop;
		for (int i = 0;i < data.length;i++) {
			tmp[i + 1] = data[i];
		}
		data = tmp;
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
    @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
    @ assignable \everything;
	 @*/
	public void pushStartFront(int[]data,int newTop) {
		int[]tmp = new int[data.length + 1];
		tmp[0] = newTop;
		pushLoop(data,tmp);
		data = tmp;
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures data[data.length-1] == newTop
   @ && (\forall int k; 0 <= k && k < \old(data.length); data[k] == \old(data[k]));
   @ assignable \everything;
	 @*/
	public void pushStart(int[]data,int newTop) {
		int[]tmp = new int[data.length + 1];
		tmp[tmp.length - 1] = newTop;
		pushLoop(data,tmp);
		data = tmp;
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0 && tmp.length == data.length + 1;
	 @ ensures tmp.length == data.length + 1 && (\forall int j; 0 <= j & j < data.length; data[j] == tmp[j]);
    @ assignable \everything;
	 @*/
	public void pushLoop(int[]data,int[]tmp) {
		for (int i = 0;i < data.length;i++) {
			pushAssign(data,tmp,i);
		}
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures tmp[i] == data[i];
   @ assignable \nothing;
	 @*/
	public void pushAssign(int[]data,int[]tmp,int i) {
		tmp[i] = data[i];
	}

	
	/*@
	 @ normal_behavior
	 @ requires data!=null && data.length>=0;
	 @ ensures tmp[i] == data[i];
  @ assignable \nothing;
	 @*/
	public void pushAssignFront(int[]data,int[]tmp,int i) {
		tmp[i + 1] = data[i];
	}
}