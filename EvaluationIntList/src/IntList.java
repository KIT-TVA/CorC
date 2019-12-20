
public class IntList {
    ///*@ invariant this != null;@*/ 
	///*@ invariant data != null;@*/
    //public int[] data;
    
    public IntList() {
        //data = new int[0];
    }
    
    /*@
    @ public normal_behavior
    @ requires data == old_data;
    @ ensures (\exists int z; (0 <= z && z < \result.length && \result[z] == newTop)) && (\forall int k; (0 <= k && k < old_data.length ==> (\exists int z; (0 <= z && z < \result.length && \result[z] == old_data[k]))))&&\result[\result.length-1] == newTop;
    @ assignable data[*];
    @*/
    public static int[] push(int newTop, int[] old_data, int[] data) {
    }
    
    /*@
	@ public normal_behavior
	@ requires data == old_data;
    @ ensures (\exists int z; (0 <= z && z < \result.length && \result[z] == newTop)) && (\forall int k; (0 <= k && k < old_data.length ==> (\exists int z; (0 <= z && z < \result.length && \result[z] == old_data[k]))))&& \result[\result.length-1] == newTop;
    @ assignable data[*];
    @*/
    public static int[] push_Cons(int newTop, int[] old_data, int[] data) {
    	
    }
    
    /*@ 
      @ public normal_behavior
	  @ assignable data;
	  @ requires data == old_data;
	  @ ensures (\forall int k; 0 <= k && k < \result.length-1; \result[k] >= \result[k+1]) && (\forall int k; 0 <= k && k < old_data.length  ==> (\exists int z; 0 <= z && z < \result.length && \result[z] == old_data[k]));
	  @*/
	private static int[] sort(int[] data, int[] old_data) {
	}
    
}
