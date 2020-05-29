public class Generated_IntegerList {

    /*@
    @ public normal_behavior
    @ requires (true) & (true);
    @ ensures (\result[\result.length - 1] == newTop) & ((\exists int z;(0 <= z && z < \result.length&& \result[z] == newTop))&& (\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k])))) && \result[\result.length - 1] == newTop);
    @ assignable \everything;
    @*/
    public static int[] generated_cons(int[] data, int[] old_data, int newTop) {  }

    /*@
    @ public normal_behavior
    @ requires true;
    @ ensures (\exists int z;(0 <= z && z < \result.length&& \result[z] == newTop))&& (\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k])))) && \result[\result.length - 1] == newTop;
    @ assignable \everything;
    @*/
    public static int[] generated_base(int[] data, int[] old_data, int newTop) {  }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures (\exists int z;(0 <= z && z < \result.length&& \result[z] == newTop))&& (\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k])))) && \result[\result.length - 1] == newTop;
    @ assignable \everything;
    @*/
    public static int[] base(int[] data, int[] old_data, int newTop) {
    }

    /*@
    @ normal_behavior
    @ requires true;
    @ ensures \result[\result.length - 1] == newTop;
    @ assignable data;
    @*/
    public static int[] cons(int[] data, int[] old_data, int newTop) {
    }

    /*@
    @ normal_behavior
    @ requires (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1]));
    @ ensures (\exists int u; (0 <= u && u < \result.length ==> \result[u] == newTop)) &&((\forall int k; (0 <= k && k < old_data.length==> (\exists int z; (0 <= z && z < \result.length&& \result[z] == old_data[k]))))&& (\forall int k; (0 <= k && k < \result.length-1 ==> (\result[k] >= \result[k+1]) | (\result[k] <= \result[k+1]))));
    @ assignable data;
    @*/
    public static int[] sorted(int[] data, int[] old_data, int newTop) {
    }
}
