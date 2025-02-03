public class IntList {

    public int[] data;

    /*@ invariant data != null; @*/
    /*@ invariant this != null; @*/



	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures true;
	@ assignable \nothing;
	@*/
	int /*@helper pure @*/ length(int[] arr) {return arr.length;}



	/*@
	@ public normal_behavior
	@ requires true;
	@ ensures false;
	@ assignable \nothing;
	@*/
	boolean /*@ pure @*/ noResolve() {return false;}


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\exists int v; (0 <= v && v < data.length ==> data[v] == newTop))&& ((\forall int k; (0 <= k && k < \old(data).length ==> (\exists int z; (0 <= z && z < data.length && data[z] == \old(data)[k])))));
	@ assignable data[*];
	@*/
	public void push(int newTop) {
		int i;
		int[] tmp;
		tmp = new int[data.length+1];
		tmp[tmp.length-1] = newTop;
		i = 0;
		while (i < data.length) {
			tmp[i] = data[i];
			i++;
		}
		data = tmp;

	}
}