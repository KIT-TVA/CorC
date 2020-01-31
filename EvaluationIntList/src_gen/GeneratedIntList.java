public class GeneratedIntList{

 /*@
@ public normal_behavior
@requires data == old_data;
@ensures (\exists int z; (0 <= z && z < \result.length && \result[z] == newTop)) && (\forall int k; (0 <= k && k < old_data.length ==> (\exists int z; (0 <= z && z < \result.length && \result[z] == old_data[k]))))&& \result[\result.length-1] == newTop;
@assignable data[*];
@*/
    public static int[] push_Cons(int newTop, int[] old_data, int[] data) {    	    }
}