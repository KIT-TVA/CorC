public class IntList {

	public IntList() {
    	//data = new int[0];
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
