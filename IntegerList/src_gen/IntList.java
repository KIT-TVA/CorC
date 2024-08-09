public class IntList {

    public int[] data;
    public int LIMIT;

    /*@ invariant data != null; @*/
    /*@ invariant this != null; @*/


	/*@
	@ normal_behavior
	@ requires true;
	@ ensures (\exists int v; (0 <= v && v < length(data) && data[v] == newTop))&& ((\forall int k; (0 <= k && k < length(\old(data)) ==> (\exists int z; (0 <= z && z < length(data) && data[z] == \old(data)[k])))));
	@ assignable \nothing;
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
    @ ensures (\old(data).length < LIMIT) ==> (\exists int v; (0 <= v && v < length(data) && data[v] == newTop))&& ((\forall int k; (0 <= k && k < length(\old(data)) ==> (\exists int z; (0 <= z && z < length(data) && data[z] == \old(data)[k]))))) && data != null && this != null;
    @ assignable \nothing;
    @*/
	public void original_push(int newTop) {
		if (data.length < LIMIT) {
			original_original_push(newTop);
		} else if (data.length >= LIMIT) {
			;
		}

	}

	/*@
	@ normal_behavior
	@ requires true && ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ ensures (\old(data).length < LIMIT) ==> (\exists int v; (0 <= v && v < length(data) && data[v] == newTop))&& ((\forall int k; (0 <= k && k < length(\old(data)) ==> (\exists int z; (0 <= z && z < length(data) && data[z] == \old(data)[k]))))) && ((\forall int k; (0 <= k && k < data.length-1 ==> (data[k] >= data[k+1]))) || (\forall int k; (0 <= k && k < data.length-1 ==> (data[k] <= data[k+1]))));
	@ assignable \nothing;
	@*/
	public void push(int newTop) {
		original_push(newTop);
		sort();

	}

	/*@
    @ public normal_behavior
    @ requires data == \old(data);
    @ ensures ((\forall int k; (0 <= k && k < length(\old(data)) ==> (\exists int z; (0 <= z && z < length(data) && data[z] == \old(data)[k])))))&& (\forall int k; (0 <= k && k < length(data)-1 ==> (data[k] <= data[k+1]))) && data != null && this != null;
    @ assignable \nothing;
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
