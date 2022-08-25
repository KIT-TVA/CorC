public class IntList {

    public int[] data;

    /*@ invariant data != null; @*/
    /*@ invariant this != null; @*/


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\exists int z;(0 <= z && z < data.length&& data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k])))) && data[data.length - 1] == newTop;
	@ assignable data[*];
	@*/
	public void original_original_push(int newTop) {
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

	/*@
    @ public normal_behavior
    @ requires true;
    @ ensures (\exists int z;(0 <= z && z < data.length&& data[z] == newTop))&& (\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k])))) && data[data.length - 1] == newTop && data[data.length - 1] == newTop && data != null && this != null;
    @ assignable \nothing;
    @*/
	public void original_push(int newTop) {
		original_original_push(newTop);

	}

	/*@
	@ normal_behavior
	@ requires (\forall int k; (0 <= k && k < data.length-1 ==> data[k] >= data[k+1]));
	@ ensures (\forall int k; (0 <= k && k < \old(data).length ==> (\exists int z; (0 <= z && z < data.length && data[z] == \old(data)[k]))))&& ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ assignable \nothing;
	@*/
	public void push(int newTop) {
		original_push(newTop);
		sort();

	}

	/*@
    @ public normal_behavior
    @ requires data == \old(data);
    @ ensures ((\forall int k; (0 <= k && k < \old(data).length==> (\exists int z; (0 <= z && z < data.length&& data[z] == \old(data)[k]))))&& (\forall int k; (0 <= k && k < data.length-1 ==> data[k] <= data[k+1]))) && data != null && this != null;
    @ assignable data[*];
    @*/
	public void sort() {
		int tmp;
		int j;
		int i;
		i = 0;
		while (i < data.length) {
			j = data.length-2;
			while (j>=i) {
				if (data[j] > data[j+1]) {
					tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
				} else if (data[j] <= data[j+1]) {
					;
				}
				j--;
			}
			i++;
		}

	}
}
