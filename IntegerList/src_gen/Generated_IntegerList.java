public class Generated_IntegerList {

    /*@
    @ public normal_behavior
    @ requires data == old_data;
    @ ensures ((\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k]))))&& (\forall int k; (0 <= k && k < \result.length-1 ==> \result[k] >= \result[k+1])));
    @ assignable \everything;
    @*/
    public static int[] generated_decreasing(int[] data, int[] old_data) {  }

 public static boolean original; 


    /*@
    @ public normal_behavior
    @ requires data == old_data;
    @ ensures ((\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k]))))&& (\forall int k; (0 <= k && k < \result.length-1 ==> \result[k] <= \result[k+1])));
    @ assignable \everything;
    @*/
    public static int[] generated_increasing(int[] data, int[] old_data) {  }

    /*@
    @ normal_behavior
    @ requires data == old_data;
    @ ensures ((\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k]))))&& (\forall int k; (0 <= k && k < \result.length-1 ==> \result[k] >= \result[k+1])));
    @ assignable \everything;
    @*/
    public static int[] decreasing(int[] data, int[] old_data) {
    }

    /*@
    @ normal_behavior
    @ requires data == old_data;
    @ ensures ((\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k]))))&& (\forall int k; (0 <= k && k < \result.length-1 ==> \result[k] <= \result[k+1])));
    @ assignable \everything;
    @*/
    public static int[] increasing(int[] data, int[] old_data) {
    }
}
